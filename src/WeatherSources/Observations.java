package WeatherSources;


import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class Observations extends  WeatherSource{

	public Observations(WeatherLocation location) {
		super(location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double getTemp() {
		// TODO Auto-generated method stub
		return 0.0;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWindSpeed()  {
		// TODO Auto-generated method stub
		return 0;
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
