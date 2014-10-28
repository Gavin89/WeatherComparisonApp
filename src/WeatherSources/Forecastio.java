package WeatherSources;

import java.util.Date;

import WC.WeatherLocation;
import WeatherSource.WeatherSource;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import dme.forecastiolib.ForecastIO;

public class Forecastio extends WeatherSource {

	ForecastIO fio;

	public Forecastio(WeatherLocation location) {
		super(location);

		fio = new ForecastIO("cc450ce1a780afa5207fd28ea384c27b"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI); //sets the units as SI - optional
		
		fio.getForecast(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));//sets the latitude and longitude - not optional
		System.out.println(fio.getTime());
	}


	@Override
	public Double getTemp() {
		// TODO Auto-generated method stub

		Double currently = fio.getCurrently().get("temperature").asDouble();
		double temp = Math.round(currently * 100.0) / 100.0;
		return temp;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		JsonArray hourly = fio.getHourly().get("data").asArray();

		for (int i = 0; i < hourly.size(); i++) {
			JsonObject hourlyObject = hourly.get(i).asObject();
			String summary = hourlyObject.get("summary").asString();
			return summary;
		}

		return null;
	}

	@Override
	public double getWindSpeed()  {
		Double windSpeed = fio.getCurrently().get("windSpeed").asDouble();
		double windspeedMPH = windSpeed * 2.2369;
		return windspeedMPH;
	}

	@Override
	public WeatherLocation getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName(){
		return "ForecastIO";
	}

	public long getUnixDate(){
		Date currentUnixDate = new Date();
		return currentUnixDate.getTime() / 1000;
	}
}
