
package WeatherSources;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import WC.MetOfficeLocationProvider;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class MettOffice extends  WeatherSource{
	
	private JSONObject json;
	private JSONObject repObj;
	
	public MettOffice(WeatherLocation location) {
		super(location);
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
			    
			    
			     System.out.println(json);
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
		return null;
	}

	@Override
	public double getWindSpeed() {
		// TODO Auto-generated method stub
		return 0.0;
	}

	@Override
	public WeatherLocation getLocation()  {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName(){
		return "MettOffice";
	}
}
