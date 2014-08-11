package WeatherSources;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import dme.forecastiolib.ForecastIO;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class Forecastio extends WeatherSource {
	
	ForecastIO fio;

	public Forecastio(WeatherLocation location) {
		super(location);
		
		fio = new ForecastIO("cc450ce1a780afa5207fd28ea384c27b"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
		
		fio.getForecast(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));   //sets the latitude and longitude - not optional
	}
	
	@Override
	public Double getTemp() {
		// TODO Auto-generated method stub

		Double currently = fio.getCurrently().get("temperature").asDouble();
		return currently;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		JsonArray hourly = fio.getHourly().get("data").asArray();
		
		for (int i = 0; i < hourly.size(); i++) {
			JsonObject hourlyObject = hourly.get(i).asObject();
			String summary = hourlyObject.get("summary").asString();
		}
		
		return null;
	}

	@Override
	public int getWindSpeed()  {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WeatherLocation getLocation() {
		// TODO Auto-generated method stub
		return new WeatherLocation("London", 54.22, 215.22);
	}
	
	public String getName(){
		return "ForecastIO";
	}

}
