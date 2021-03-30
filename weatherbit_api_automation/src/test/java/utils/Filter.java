package utils;

import java.util.ArrayList;
import java.util.List;
import models.DailyForecast;

public class Filter {
	
	public static List<DailyForecast> filterByParameter(List<DailyForecast> dailyData, float minTemp, float maxTemp, float windSpeed, float uvIndex) {
		List<DailyForecast> filterData = new ArrayList<DailyForecast>();
		for(DailyForecast data: dailyData) {
			if (data.max_temp < maxTemp && data.min_temp > minTemp && data.uv < uvIndex &&data.min_temp < windSpeed) {
				filterData.add(data);
			}
		}
		System.out.println("Response set after filtering by Parameter max_temp, min_temp, uv, min_temp is: "+filterData.size());
		return filterData;
	}
	
	public static List<DailyForecast> filterByDayOfWeek(List<DailyForecast> dailyData) {
		List<DailyForecast> filterDataByDayOfWeek = new ArrayList<DailyForecast>();
		for(DailyForecast data: dailyData) {
			if (data.getDate().getDayOfWeek() == 1 || data.getDate().getDayOfWeek() == 5) {
				filterDataByDayOfWeek.add(data);
			}
		}
		System.out.println("Response set after filtering by Monday and Friday: "+filterDataByDayOfWeek.size());
		return filterDataByDayOfWeek;
	}
}
