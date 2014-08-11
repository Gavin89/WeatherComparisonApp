package WC;
import WeatherSource.WeatherSource;

public class DataCollector implements Runnable {
	

	private	LocationsProvider locProvider;
	
	
	public DataCollector() {
		
		this.locProvider = new LocationsProvider();
		this.run();
	}
	

	private void collect() {
		for (WeatherLocation loc : this.locProvider) {
			System.out.println("Getting data for location "+loc.getLocationName());
			
			WeatherSourcesProvider wsProvider = new WeatherSourcesProvider(loc);
			
			for (WeatherSource ws : wsProvider) {
				System.out.println("\t Temp from "+ws.getName()+" is "+ws.getTemp());
			}
		}
		
	}

	@Override
	public void run() {
		this.collect();
	}
}
