package models;

import org.joda.time.DateTime;

public class DailyForecast {
	public float uv;
	public float max_temp;
	public float wind_speed;	
	public float min_temp;
	public String valid_date;
	
	public DateTime getDate() {
		return DateTime.parse(valid_date);
		
	}
		
}
