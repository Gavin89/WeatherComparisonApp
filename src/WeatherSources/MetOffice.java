package WeatherSources;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import WC.ForecastItem;
import WC.MetOfficeLocationProvider;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class MetOffice extends  WeatherSource{

private HashMap<String, String> summaryList;

public MetOffice(WeatherLocation location) {

	super(location);
	summaryList = new HashMap<String, String>();

	this.summaryList();
		try {		

			WeatherLocation newLocation = MetOfficeLocationProvider.getSpecifiedLocation(location.getLocationName());	
			String locationId = newLocation.getLocationId();

			try {
				for(int i = 0; i < 48; i+=3){
					JSONObject json = new JSONObject(readUrl("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + locationId + "?res=3hourly&time=" + this.getTomorrowDate() + "T" + i + "Z&key=cb3f0007-c6a0-4633-9166-7fbbc8e76c9f"));

					JSONObject siteRep = json.getJSONObject("SiteRep");
					JSONObject dv = siteRep.getJSONObject("DV");
					JSONObject locationObj = dv.getJSONObject("Location");
					JSONObject periods = locationObj.getJSONObject("Period");
					JSONObject rep = periods.getJSONObject("Rep");

					int time = Integer.parseInt(rep.getString("$"));
					int newTime = time/60;

					ForecastItem item = new ForecastItem(newTime, rep.getDouble("T"), rep.getDouble("S"), 
							summaryList.get(rep.getString("W")),this.parseDate(periods.getString("value")),j);
					this.addForecast(item);
				}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
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

public double getRoundedTemp(double temperature){
	
	 if (temperature - Math.floor(temperature) >=0.5) { 
		 double roundDown = Math.ceil(temperature); 
		 return roundDown;
		 }else{ 
		 double roundUp = Math.floor(temperature); 
		 return roundUp;
		 } 
}

public String parseDate(String value) throws ParseException{

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse(value);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		return formatter.format(date);
	}

public String parseTime(String value){

		int posT = value.indexOf('T');

		String time = value.substring(posT + 1, value.length() - 1);

		return time;
	}

public String getName(){
		return "MettOffice";
	}

public void summaryList() {

		summaryList.put("NA", "Not available");
		summaryList.put("0", "Clear night");
		summaryList.put("1", "Sunny day");
		summaryList.put("2", "Partly cloudy (night)");
		summaryList.put("3", "Partly cloudy (day)");
		summaryList.put("4", "Not used");
		summaryList.put("5", "Mist");
		summaryList.put("6", "Fog");
		summaryList.put("7", "Cloudy");
		summaryList.put("8", "Overcast");
		summaryList.put("9", "Light rain shower (night)");
		summaryList.put("10", "Light rain shower (day)");
		summaryList.put("11", "Drizzle");
		summaryList.put("12", "Light rain");
		summaryList.put("13", "Heavy rain shower (night)");
		summaryList.put("14", "Heavy rain shower (day)");
		summaryList.put("15", "Heavy rain");
		summaryList.put("16", "Sleet shower(night)");
		summaryList.put("17", "Sleet shower (day)");
		summaryList.put("18", "Sleet");
		summaryList.put("19", "Hail shower (night)");
		summaryList.put("20", "Hail shower (day)");
		summaryList.put("21", "Hail");
		summaryList.put("22", "Light snow shower (night)");
		summaryList.put("23", "Light snow shower (day)");
		summaryList.put("24", "Light snow");
		summaryList.put("25", "Heavy snow shower (night)");
		summaryList.put("26", "Heavy snow shower (day)");
		summaryList.put("27", "Heavy snow");
		summaryList.put("28", "Thunder shower (night)");
		summaryList.put("29", "Thunder shower (day)");
		summaryList.put("30", "Thunder");
	}
}
