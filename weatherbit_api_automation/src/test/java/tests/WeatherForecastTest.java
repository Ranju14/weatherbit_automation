package tests;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import models.DailyForecast;
import models.WeatherForecastResponse;
import requests.WeatherBitForcastRequest;
import utils.Filter;

public class WeatherForecastTest {

	private static final String POST_CODE1 = "2026";
	private static final String POST_CODE2 = "2108";
	private static final String AUSTRALIA_COUNTRY_CODE = "AU";
	private static final String MAX_NUMBER_DAYS = "90";
	private static final float MIN_TEMP = 15;
	private static final float MAX_TEMP = 30;
	private static final float UV_INDEX = 12;
	private static final float WIND_SPEED = 90;

	@DataProvider(name = "postCodes")
	public static Object[][] postalCodes() {
		return new Object[][]{{POST_CODE1}, {POST_CODE2}};
	}


	@Test(dataProvider = "postCodes")
	public void findBestSurfingPointByPostCode(String postCode) {
		
		//getWeatherRequestByPostalCode
		WeatherForecastResponse res =WeatherBitForcastRequest.getWeatherForecastByPostalCode(postCode, AUSTRALIA_COUNTRY_CODE, MAX_NUMBER_DAYS);
		System.out.println("Initial response set: "+res.data.size());
		
		//Filter the response based on parameter max_temp, min_temp, wind_speed,uv
		List<DailyForecast> filterData = Filter.filterByParameter(res.data, MIN_TEMP , MAX_TEMP, WIND_SPEED, UV_INDEX);
		
		//Filter response based on day of the week
		List<DailyForecast> filterDataByDayOfWeek = Filter.filterByDayOfWeek(filterData);
		
		// Print the suitable day for surfing
		if (!filterDataByDayOfWeek.isEmpty()) {
			for(DailyForecast data: filterDataByDayOfWeek) {
				System.out.println("The best surfing day is for the postal code "+postCode+ " is: " + data.getDate()+" at latitude " + res.latitude+ " and at longitude " +res.lon);

			}	
		}
		else {
			System.out.println("Unfortunately weather is not suitable for surfing for the postcode: "+postCode);
		}
	}
}

