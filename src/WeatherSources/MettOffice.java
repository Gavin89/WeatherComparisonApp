
package WeatherSources;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import WC.MetOfficeLocationProvider;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class MettOffice extends  WeatherSource{
	
	private JSONObject json;
	private JSONObject repObj;
	private HashMap<String, String> summaryList;
	
	public MettOffice(WeatherLocation location) {
		
		super(location);
		summaryList = new HashMap<String, String>();
		//this.summaryList();
		try {		
			
			WeatherLocation newLocation = MetOfficeLocationProvider.getSpecifiedLocation(location.getLocationName());	
			//System.out.println("Location name: " + newLocation.getLocationName() + "Location id: " + newLocation.getLocationId());
			String locationId = newLocation.getLocationId();
			
			try {
			     json = new JSONObject(readUrl("http://datapoint.metoffice.gov.uk/public/data/val/wxfcs/all/json/" + locationId + "?res=3hourly&key=cb3f0007-c6a0-4633-9166-7fbbc8e76c9f	"));
			     JSONObject siteRep = json.getJSONObject("SiteRep");
			     JSONObject wx = siteRep.getJSONObject("Wx");
			     JSONObject dv = siteRep.getJSONObject("DV");
			     JSONObject locationObj = dv.getJSONObject("Location");
			     JSONArray periods = locationObj.getJSONArray("Period");
			     
			     for (int i = 0; i < periods.length(); i++) {
			    	JSONObject period = periods.getJSONObject(i);
			    	JSONArray reps = period.getJSONArray("Rep");
			    	for (int repI = 0; repI < reps.length(); repI++) {
			    		repObj = reps.getJSONObject(repI);
			    		int numOfMinutes = repObj.getInt("$");
			    		//System.out.println("NoM: "+numOfMinutes);
			    	}
			     }
			    
			    
			    //System.out.println(json);
			    //String temp = (String) json.get("title");
			    
			} catch (Exception e) {
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

	@Override
	public Double getTemp()  {
		// TODO Auto-generated method stub
		Double currentTemp = null;
		try {
			currentTemp = (Double) repObj.getDouble("T");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentTemp;
	}

	@Override
	public String getSummary()  {
		// TODO Auto-generated method stub
		this.summaryList();
		String currentSummary = "";
		try {
			currentSummary = repObj.getString("F");
			currentSummary = summaryList.get(currentSummary);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return currentSummary;
	}

	@Override
	public double getWindSpeed() {
		// TODO Auto-generated method stub
		Double currentWindspeed = null;
		try {
			currentWindspeed = repObj.getDouble("S");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentWindspeed;
	}

	@Override
	public WeatherLocation getLocation()  {
		// TODO Auto-generated method stub
		return null;
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
