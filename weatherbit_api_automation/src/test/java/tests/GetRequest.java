package tests;

import static com.jayway.restassured.RestAssured.given;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

public class GetRequest {

	private static final String AUSTRALIA_COUNTRY_CODE = "AU";
	private static final String MAX_NUMBER_DAYS = "90";
	private static final String WEATHER_BIT_API_KEY = "5a55d544a7eb4bb18016cdcc2bb7b895";
	private static final String DAILY_FORECAST_API = "http://api.weatherbit.io/v2.0/forecast/dailynull";
	private static final String POST_CODE = "2026";


	@Test
	public void getParametersValuesFromWeatherForecast() {
		Response res = getWeatherForecast(POST_CODE);
		extractValues(res);
	}

	private Response getWeatherForecast(String POST_CODE) {
		Response res = given().
				parameter("postal_code", POST_CODE).
				parameter("country", AUSTRALIA_COUNTRY_CODE).
				parameter("days", MAX_NUMBER_DAYS).
				parameter("key", WEATHER_BIT_API_KEY).
				when().
				get(DAILY_FORECAST_API).then().contentType(ContentType.JSON).
				assertThat().
				statusCode(200).
				extract().response();
//		res.prettyPrint();
		return res ;
	}

	private void extractValues(Response response) {
		
		//extract lat and long
		Object latitude = response.jsonPath().get("lat");
		Object longitude = response.jsonPath().get("lon");
		System.out.println(latitude);
		System.out.println(longitude);
		
		//extract all the  parameters from all the array
	    List data = response.jsonPath().get("data");
	    System.out.println(data);
		
		//extract parameters validDate from all the array
		ArrayList<String> date = response.jsonPath().get("data.valid_date");
		for (String m: date) {
			System.out.println(m);
		}
	}

}
