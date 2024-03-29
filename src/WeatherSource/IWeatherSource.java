package WeatherSource;

import java.util.Date;

public interface IWeatherSource {
		
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

	//Returns tomorrows date parsed
	public String getTomorrowParsedDate();

	//Returns current date parsed
	public String getParsedDate();
}
