package WeatherSource;

import java.util.Date;

import WC.WeatherLocation;

public class WeatherSource implements IWeatherSource {

	private WeatherLocation location;
	
	public WeatherSource(WeatherLocation location) {
		this.location = location;
	}
	
	@Override
	public Double getTemp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getWindSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public WeatherLocation getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate() {
		return null;
		// TODO Auto-generated method stub	
	}

	@Override
	public long getUnixDate() {
		// TODO Auto-generated method stub
		return 0;
	}

}
