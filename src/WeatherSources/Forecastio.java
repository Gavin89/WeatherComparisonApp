package WeatherSources;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import WC.WeatherLocation;
import WeatherSource.WeatherSource;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.github.dvdme.ForecastIOLib.ForecastIO;

public class Forecastio extends WeatherSource {

	ForecastIO fio;
	long[] dates;

	public Forecastio(WeatherLocation location) {
		super(location);

		fio = new ForecastIO("cc450ce1a780afa5207fd28ea384c27b"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI); //sets the units as SI - optional

		dates = new long[5];

		for (int i = 0; i < dates.length; i++){
			long currentDate = this.getUnixDate();
			dates[i] = currentDate;
			currentDate += 1440 * 60;
			fio.getForecast(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), dates[i]);   //sets the latitude and longitude - not optional
			
		}
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
		}

		return null;
	}

	@Override
	public double getWindSpeed()  {
		Double windSpeed = fio.getCurrently().get("windSpeed").asDouble();
		return windSpeed;
	}

	@Override
	public WeatherLocation getLocation() {
		// TODO Auto-generated method stub
		return null;//new WeatherLocation("London", 54.22, 215.22);
	}

	public String getName(){
		return "ForecastIO";
	}

	public long getUnixDate(){
		Date currentUnixDate = new Date();
		return currentUnixDate.getTime() / 1000;
	}

	public Date getDate(){
		Date currentDate = new Date();
		return currentDate;
	}

}
