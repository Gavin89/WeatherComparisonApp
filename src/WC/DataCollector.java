package WC;
import java.net.UnknownHostException;

import WeatherSource.WeatherSource;

public class DataCollector implements Runnable {
	

	private	LocationsProvider locProvider;
	
	
	public DataCollector() throws UnknownHostException {
		
		this.locProvider = new LocationsProvider();
		this.run();
	}
	

	private void collect() {
		for (WeatherLocation loc : this.locProvider) {
			System.out.println("\n");
			System.out.println("Getting data for location "+loc.getLocationName());
			
			WeatherSourcesProvider wsProvider = new WeatherSourcesProvider(loc);
			
			for (WeatherSource ws : wsProvider) {
				System.out.println("\t Temp from "+ws.getName()+" is "+ws.getTemp());
				System.out.println("\t Windspeed from "+ws.getName()+" is "+ws.getWindSpeed());
				System.out.println("\t Date is "+ws.getUnixDate());
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
		
	}

	@Override
	public void run() {
		this.collect();
	}
}
