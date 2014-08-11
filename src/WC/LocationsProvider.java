package WC;

import java.util.HashMap;
import java.util.Iterator;

public class LocationsProvider implements Iterable<WeatherLocation> {

	private HashMap<String, WeatherLocation> locations;
	
	public LocationsProvider() {
		this.locations = new HashMap<String, WeatherLocation>();
		
		this.populateLocations();
	}
	
	private void populateLocations() {
		this.locations.put("London", new WeatherLocation("London", 12.00, 12.55));
		this.locations.put("Birmingham", new WeatherLocation("Birmingham", 12.00, 12.55));
		this.locations.put("Southampton", new WeatherLocation("Southampton", 12.00, 12.55));
		this.locations.put("Manchester", new WeatherLocation("Manchester", 12.00, 12.55));
	}
	
	public WeatherLocation getLocationByName(String name) {
		return this.locations.get(name);
	}
	
	@Override
	public Iterator<WeatherLocation> iterator() {
		// TODO Auto-generated method stub
		return this.locations.values().iterator();
	}
	
	
	
}
