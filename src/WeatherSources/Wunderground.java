package WeatherSources;

import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class Wunderground extends  WeatherSource{

	public Wunderground(WeatherLocation location) {
		super(location);
		// TODO Auto-generated constructor stub
	}

	public String getName(){
		return "Wunderground";
	}
}
