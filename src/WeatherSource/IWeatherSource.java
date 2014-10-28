package WeatherSource;

import java.util.Date;

import WC.WeatherLocation;


public interface IWeatherSource {
	
	// Returns temperature from weather source
	public Double getTemp();
	
	//Returns summary from weather source
	public String getSummary();

	//Returns windspeed from weather source
	public double getWindSpeed();
	
	//Returns location from weather source
	public WeatherLocation getLocation();
		
	//Returns name of the weather source
	public String getName();
		
	//Return the current date
	public Date getDate();
	
	//Return date in format DD/MM/YYYY
	public String getParsedDate();
	
	//Returns longitude of location
	public Double getLongitude();
	
	//Returns latitude of location
	public Double getLatitude();
	
	//Returns tomorrows date
	public String getTomorrowDate();
}
