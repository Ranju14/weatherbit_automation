package tests;
import utils.Constants;

import static com.jayway.restassured.RestAssured.given;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class GetRequest {

	private static final String POST_CODE = "2026";
	private static final String AUSTRALIA_COUNTRY_CODE = "AU";
	private static final String MAX_NUMBER_DAYS = "90";


	@Test
	public void findBestSurfingPointByPostCode() {
		Response res = getWeatherForecast(POST_CODE);
		extractValues(res);

	}
	
	private Response getWeatherForecast(String POST_CODE) {
		Response res = given().
				parameter("postal_code", POST_CODE).
				parameter("country", AUSTRALIA_COUNTRY_CODE).
				parameter("days", MAX_NUMBER_DAYS).
				parameter("key", Constants.WEATHER_BIT_API_KEY).
				when().
				get(Constants.DAILY_FORECAST_API).then().contentType(ContentType.JSON).
				assertThat().
				statusCode(200).
				extract().response();
		return res ;
	}

	private void extractValues(Response response) {

		//extract lat and long
		Object latitude = response.jsonPath().get("lat");
		Object longitude = response.jsonPath().get("lon");

		//extract all the  parameters from all the array
		List data = response.jsonPath().get("data");
	    List filteredData = filterData(data);
	    suggestSurfingDay(filteredData,latitude,longitude);
		
	}

	private List filterData(List data) {
		List filteredData = new ArrayList();
		for (Object obj : data) {
			Map<String, Object> item = (Map<String, Object>) obj;
			float minTemp = Float.parseFloat(item.get("min_temp").toString());
			float maxTemp = Float.parseFloat(item.get("max_temp").toString());
			float windSpeed = Float.parseFloat(item.get("wind_spd").toString());
			float uvIndex = Float.parseFloat(item.get("uv").toString());
			if (minTemp > 12 && maxTemp < 30 && windSpeed > 3 && windSpeed < 9 && uvIndex <= 12 ) {
				filteredData.add(obj);
			}
		}
		return filteredData;
	}
	
	 private void suggestSurfingDay(List filterdata, Object lat,Object lon) {
	        List filteredData = filterdata;
	        Object latitude = lat;
	        Object longitute = lon;
	        if (!filteredData.isEmpty()) {
	            for (Object obj : filteredData) {
	                Map<String, Object> item = (Map<String, Object>) obj;
	                System.out.println(String.format("The best surfing location Lat : %s Lon : %s ", latitude ,longitute));
	            }    
	    }
	 }
}
