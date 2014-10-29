package WeatherSources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import WC.ForecastItem;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import dme.forecastiolib.ForecastIO;

public class Forecastio extends WeatherSource {

	ForecastIO fio;
	private JSONObject dataObj;

	public Forecastio(WeatherLocation location) throws Exception {
		super(location);

		fio = new ForecastIO("cc450ce1a780afa5207fd28ea384c27b"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI); //sets the units as SI - optional
		
		fio.getForecast(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));//sets the latitude and longitude - not optional
		//System.out.println(fio.getTime());
		for(int i = 0; i < 24; i+=3){
			try {
		     JSONObject json = new JSONObject(readUrl("https://api.forecast.io/forecast/cc450ce1a780afa5207fd28ea384c27b/" + location.getLongitude() + "," + this.getLatitude()));
			
		     JSONObject hourly = json.getJSONObject("hourly");
		     JSONArray datas = hourly.getJSONArray("data");
		     for(int j = 0; j < datas.length(); j++){
		     JSONObject data = datas.getJSONObject(j);
		     
		     ForecastItem item = new ForecastItem(this.parseUnixDate(data.getLong("time")), data.getDouble("temperature"), data.getDouble("windSpeed"), 
		     data.getString("summary"));
		     this.addForecast(item);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			}
	}

//
//	@Override
//	public Double getTemp() {
//		// TODO Auto-generated method stub
//
//		Double currently = fio.getCurrently().get("temperature").asDouble();
//		double temp = Math.round(currently * 100.0) / 100.0;
//		return temp;
//	}
//
//	@Override
//	public String getSummary() {
//		// TODO Auto-generated method stub
//		JsonArray hourly = fio.getHourly().get("data").asArray();
//
//		for (int i = 0; i < hourly.size(); i++) {
//			JsonObject hourlyObject = hourly.get(i).asObject();
//			String summary = hourlyObject.get("summary").asString();
//			return summary;
//		}
//
//		return null;
//	}

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

	@Override
	public WeatherLocation getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName(){
		return "ForecastIO";
	}

	public void parseUnixDate(long unix_time){
		Date date = new Date ();
		date.setTime((long)unix_time*1000);
	}
}
