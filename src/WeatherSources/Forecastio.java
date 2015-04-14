package WeatherSources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import WC.ForecastItem;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;

/**
 * 
 * @author gavin
 *
 *Weather source API used to gather weather forecast data for the next 48hours
 */
public class Forecastio extends WeatherSource {

	private HashMap<String, String> summaryList;
	
	public Forecastio(WeatherLocation location) throws Exception {
		super(location);
		summaryList = new HashMap<String, String>();

		this.summaryList();
		try {
			JSONObject json = new JSONObject(readUrl("https://api.forecast.io/forecast/cc450ce1a780afa5207fd28ea384c27b/"
				+ location.getLatitude() + "," + this.getLongitude()));

			JSONObject hourly = json.getJSONObject("hourly");
			JSONArray datas = hourly.getJSONArray("data");
			ForecastItem item = null;
			//get forecasts for the next 48hours
			for(int i = 0; i < 48; i++){
				
				JSONObject data = datas.getJSONObject(i);
				if(this.getDate(data.getInt("time")).equals(this.getParsedDate())){
					//adds the most current forecast with the lead_time value of 0 
					item = new ForecastItem((int) this.parseUnixDate(data.getInt("time")), this.getCelsiusTemp(data.getDouble("temperature")), 
							this.getRoundedWindspeed(data.getDouble("windSpeed")), 
							summaryList.get(data.getString("icon")), this.getDate(data.getInt("time")),0);
				}
				else {
					item = new ForecastItem((int) this.parseUnixDate(data.getInt("time")), this.getCelsiusTemp(data.getDouble("temperature")), 
							this.getRoundedWindspeed(data.getDouble("windSpeed")), 
							summaryList.get(data.getString("icon")), this.getDate(data.getInt("time")),1);
				}

				this.addForecast(item);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

/**
 * Method used to parse the web URL
 * @param urlString
 * @return
 * @throws Exception
 */
	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read); 

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public String getName(){
		return "ForecastIO";
	}
	
	/**
	 * 
	 * @param windspeed
	 * @return
	 */
	public double getRoundedWindspeed(double windspeed){
		 
		 if (windspeed - Math.floor(windspeed) >=0.5) { 
			 double roundDown = Math.ceil(windspeed); 
			 return roundDown;
			 }else{ 
			 double roundUp = Math.floor(windspeed); 
			 return roundUp;
			 } 
	}

	/**
	 * Converts UNIX time in to GMT time
	 * @param unix_time
	 * @return
	 */
	public int parseUnixDate(int unix_time){

		Date date = new Date(unix_time*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("HH"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
		return new Integer(formattedDate);
	}

	/**
	 * 
	 * @param unix_time
	 * @return
	 */
	public String getDate(int unix_time){

		Date date = new Date(unix_time*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
	
	/**
	 * 
	 * @param temp
	 * @return
	 */
	public double getCelsiusTemp(double temp){
		 double temperature = ((temp - 32)*5)/9;
		 if (temperature - Math.floor(temperature) >=0.5) { 
			 double roundDown = Math.ceil(temperature); 
			 return roundDown;
			 }else{ 
			 double roundUp = Math.floor(temperature); 
			 return roundUp;
			 } 
	}
	
	/**
	 * Provides tomorrows date
	 */
	@Override
	public String getTomorrowParsedDate() {
		java.util.Date  now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);	
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = cal.getTime();
		SimpleDateFormat formatter5=new SimpleDateFormat("dd-MM-yyyy");
		String formats1 = formatter5.format(tomorrow);
		
		return formats1;
	}
	
	public void summaryList() {
	

		summaryList.put("clear-day", "Sunny");
		summaryList.put("clear-night", "Clear night");
		summaryList.put("rain", "Rain");
		summaryList.put("snow", "Snow");
		summaryList.put("sleet", "Sleet");
		summaryList.put("wind", "Wind");
		summaryList.put("fog", "Fog");
		summaryList.put("cloudy", "Cloudy");
		summaryList.put("partly-cloudy-day", "Partly cloudy");
		summaryList.put("partly-cloudy-night", "Cloudy night");
		summaryList.put("hail", "Hail");
		summaryList.put("thunderstorm", "Thunder");
	}
	
}
