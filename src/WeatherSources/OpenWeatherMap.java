package WeatherSources;

import java.io.IOException;
import java.text.DecimalFormat;

import org.bitpipeline.lib.com.OwmClient;
import org.bitpipeline.lib.com.WeatherData;
import org.bitpipeline.lib.com.WeatherStatusResponse;
import org.json.JSONException;

import WC.WeatherLocation;
import WeatherSource.WeatherSource;

public class OpenWeatherMap extends  WeatherSource {

	private WeatherData owmClient;
	
	public OpenWeatherMap(WeatherLocation location) {
		super(location);
		
		OwmClient owm = new OwmClient();
		
		WeatherStatusResponse owmResponse = null;;
		try {
			owmResponse = owm.currentWeatherAtCity(location.getLocationName(), "UK");
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(owmResponse.getMessage());

		owmClient = owmResponse.getWeatherStatus().get(0);
		
		System.out.println(owmClient.getTemp());
		
		
	}
	
	@Override
	public Double getTemp() {
		
		Double temp = (double) owmClient.getTemp();	
        DecimalFormat twoDecimals = new DecimalFormat("#.");
        return Double.valueOf(twoDecimals.format(temp));
	}

	@Override
	public String getSummary()  {
		owmClient.getWeatherConditions();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWindSpeed() {
		
		return (int) owmClient.getWindSpeed();
	}

	@Override
	public WeatherLocation getLocation() {
		// TODO Auto-generated method stub
		
		return null;
	}


	public String getName(){
		return "Open Weather Map";
	}
}
