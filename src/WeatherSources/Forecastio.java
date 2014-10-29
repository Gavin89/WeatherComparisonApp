package WeatherSources;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import WC.ForecastItem;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;
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

		try {
			JSONObject json = new JSONObject(readUrl("https://api.forecast.io/forecast/cc450ce1a780afa5207fd28ea384c27b/" + location.getLongitude() + "," + this.getLatitude()));

			JSONObject hourly = json.getJSONObject("hourly");
			JSONArray datas = hourly.getJSONArray("data");
			for(int j = 0; j < datas.length(); j++){
				JSONObject data = datas.getJSONObject(j);
				//System.out.println(this.parseUnixDate(data.getLong("time")));

				ForecastItem item = new ForecastItem(this.parseUnixDate(data.getInt("time")), data.getDouble("temperature"), data.getDouble("windSpeed"), 
						data.getString("summary"), this.getDate(data.getInt("time")));
				this.addForecast(item);
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}


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

	public String parseUnixDate(int unix_time){

		Date date = new Date(unix_time*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("HH"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}

	public String getDate(int unix_time){

		Date date = new Date(unix_time*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formattedDate = sdf.format(date);
		return formattedDate;
	}
}
