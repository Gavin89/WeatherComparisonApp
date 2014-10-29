package WeatherSources;

import java.io.IOException;
import java.text.DecimalFormat;

import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;
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
			owmResponse = owm.currentWeatherAtCity(location.getLatitude().floatValue(), location.getLongitude().floatValue(), 1);
	
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		owmClient = owmResponse.getWeatherStatus().get(0);
	}
	
//	@Override
//	public Double getTemp() {
//		
//		Double temp = (double) owmClient.getTemp();	
//        DecimalFormat twoDecimals = new DecimalFormat("#.");
//        return Double.valueOf(twoDecimals.format(temp));
//	}
//
//	@Override
//	public String getSummary()  {
//		owmClient.getWeatherConditions();
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public double getWindSpeed() {
//		
//		double windSpeed = Math.round(owmClient.getWindSpeed() * 100.0) / 100.0;
//		
//		return windSpeed;
//	}

	@Override
	public WeatherLocation getLocation() {
		// TODO Auto-generated method stub
		
		return null;
	}


	public String getName(){
		return "Open Weather Map";
	}
}
