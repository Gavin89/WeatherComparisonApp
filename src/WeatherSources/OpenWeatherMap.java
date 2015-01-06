package WeatherSources;

import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;


import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class OpenWeatherMap extends  WeatherSource {

	private WeatherData owmClient;
	
	public OpenWeatherMap(WeatherLocation location) {
		super(location);
		
		OwmClient owm = new OwmClient();
		
		WeatherStatusResponse owmResponse = null;;
		try {
			owmResponse = owm.currentWeatherAtCity(location.getLatitude().floatValue(), location.getLongitude().floatValue(), 1);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		owmClient = owmResponse.getWeatherStatus().get(0);
	}
	
	public String getName(){
		return "Open Weather Map";
	}
}
