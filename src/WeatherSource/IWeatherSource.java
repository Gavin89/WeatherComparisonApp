package WeatherSource;

import java.util.Date;

import WC.WeatherLocation;


public interface IWeatherSource {

	
	//Returns location from weather source
	public WeatherLocation getLocation();
		
	//Returns name of the weather source
	public String getName();
		
	//Return the current date
	public Date getDate();

	//Returns longitude of location
	public Double getLongitude();
	
	//Returns latitude of location
	public Double getLatitude();
	
	//Returns tomorrows date
	public String getTomorrowDate();

	public String getTomorrowParsedDate();
}
