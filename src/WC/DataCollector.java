package WC;
import java.util.ArrayList;
import java.util.Timer;

import WeatherSource.WeatherSource;

public class DataCollector {
	

	private	LocationsProvider locProvider;
	DataAnalyser data;
	
	
	public DataCollector() throws Exception {
		
		this.locProvider = new LocationsProvider();
		this.collect();
	}
	

	private void collect() throws Exception {
		long startTime = System.currentTimeMillis();

		data = new DataAnalyser();
		for (WeatherLocation loc : this.locProvider) {

			//System.out.println("Getting data for location "+loc.getLocationName());
			
			WeatherSourcesProvider wsProvider = new WeatherSourcesProvider(loc);
			
			for (WeatherSource ws : wsProvider) {
				ArrayList<ForecastItem> items = ws.getForecasts();
				for(ForecastItem item : items){

//					System.out.println("\t Getting data from " + ws.getName());
//					System.out.println("\t Time: " + item.getTime());
//					System.out.println("\t Time: " + item.getUnixTime());
//					System.out.println("\t Temp: " + item.getTemp());
//					System.out.println("\t Windspeed: " +item.getWindspeed());
//					System.out.println("\t Date: " +item.getDate());
//					System.out.println("\t Longitude: " + ws.getLongitude());
//					System.out.println("\t Latitude: " + ws.getLatitude());
//					System.out.println("\t Summary: " + item.getSummary());
//					System.out.println("");	
					
					data.populateTempDB(loc.getLocationName(), ws.getName(), item.getTime(), item.getTemp(), item.getWindspeed(), 
							item.getDate(), ws.getLongitude(), ws.getLatitude(), item.getSummary());
					
				}
				

			}
			
			/**
			 *  will need to find a way to populate db with every location and source
			 *  insert ws.getName + ws.getTemp + ws.getWindspeed + ws.getlong + ws.getLat + ws.getSummary + loc.getLocationName
			 *  also need time stamp + date
			 *  The database will then have per object id the following values
			 *  locationName : Birmingham
			 *  Temp: 20
			 *  Windspeed : 5
			 *  Longitude : 2.1
			 *  Latitude : 0.1
			 *  Summary : Windy
			 *  WeatherSource : MetOffice
			 *  Time : 0300
			 *  Date : 20/10/2014
			 *  
			 *  The web app will then request this data
			 *  Psuedoh = GET temp + windspeed + summary FROM database WHERE locationName OR lat + long = X. AND WHERE Time = 0300 AND Source = MetOfficvae
			 */
			
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
	
}
