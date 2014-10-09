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
				System.out.println(("\t Windspeed from "+ws.getName()+" is "+ws.getWindSpeed()));
				System.out.println("Testing");
			}
		}
		
	}

	@Override
	public void run() {
		this.collect();
	}
}
