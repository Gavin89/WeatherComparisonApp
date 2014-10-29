package WeatherSources;


import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class Observations extends  WeatherSource{

	public Observations(WeatherLocation location) {
		super(location);
		// TODO Auto-generated constructor stub
	}


	@Override
	public WeatherLocation getLocation(){
		// TODO Auto-generated method stub
		return null;
	}

	public String getName(){
		return "Observations";
	}


}
