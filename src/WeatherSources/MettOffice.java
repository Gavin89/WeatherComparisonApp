
package WeatherSources;


import java.net.UnknownHostException;

import WC.MetOfficeLocationProvider;
import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class MettOffice extends  WeatherSource{
	
	WeatherLocation newLocation;
	public MettOffice(WeatherLocation location) {
		super(location);
		try {
			 newLocation = MetOfficeLocationProvider.getSpecifiedLocation(location.getLocationName());
			System.out.println(newLocation);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public Double getTemp()  {
		// TODO Auto-generated method stub
		return 0.0;
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
		return this.newLocation;
		
	}

	public String getName(){
		return "MettOffice";
	}
}
