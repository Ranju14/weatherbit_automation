package requests;

import static com.jayway.restassured.RestAssured.given;

import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import models.WeatherForecastResponse;
import utils.Constants;

public class WeatherBitForcastRequest {

	public static WeatherForecastResponse getWeatherForecastByPostalCode(String postalCode, String country, String noOfDays) {
		
		Response response = given().
							parameter("postal_code", postalCode).
							parameter("country", country).
							parameter("days", noOfDays).
							parameter("key", Constants.WEATHER_BIT_API_KEY).
							when().
							get(Constants.DAILY_FORECAST_API).then().contentType(ContentType.JSON).
							assertThat().
							statusCode(200).
							extract().response();
		
		return new Gson().fromJson(response.asString(), WeatherForecastResponse.class);
	}
	
}
