package WC;

import java.util.Date;

import dme.forecastiolib.ForecastIO;

public class ForecastioWrapper extends ForecastIO{
	
	Date date;

	public ForecastioWrapper(String API_KEY) {
		super(API_KEY);
		// TODO Auto-generated constructor stub
	}
	
	public void getForecast(String lat, String lon, String date, String time){
		
	}

	public Date getDate(){
		date = new Date();
		date.getDate();
		
		return date;
	}
	

}
