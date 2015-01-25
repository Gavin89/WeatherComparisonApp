package WeatherSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import WC.ForecastItem;
import WC.WeatherLocation;

public class WeatherSource implements IWeatherSource {

	private WeatherLocation location;
	private ArrayList<ForecastItem> forecasts;
	
	public WeatherSource(WeatherLocation location) {
		this.location = location;
		forecasts = new ArrayList<ForecastItem>();
	}
	
	protected void addForecast(ForecastItem item) {
		this.forecasts.add(item);
	}
	
	public ArrayList<ForecastItem> getForecasts() {
		return this.forecasts;
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
	public String getTomorrowParsedDate() {
		java.util.Date  now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);	
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = cal.getTime();
		SimpleDateFormat formatter5=new SimpleDateFormat("dd/MM/yyyy");
		String formats1 = formatter5.format(tomorrow);
		
		return formats1;
	}

	@Override
	public String getTomorrowDate() {
		java.util.Date  now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);	
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = cal.getTime();
		SimpleDateFormat formatter5=new SimpleDateFormat("yyyy-MM-dd");
		String formats1 = formatter5.format(tomorrow);
		
		return formats1;
	}
	@Override
	public Double getLongitude() {
		// TODO Auto-generated method stub
		return this.location.getLongitude();
	}

	@Override
	public Double getLatitude() {
		// TODO Auto-generated method stub
		return this.location.getLatitude();
	}
	
	@Override
	public String getParsedDate() {
		java.util.Date  now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);	
		cal.add(Calendar.DAY_OF_YEAR, 0);
		Date tomorrow = cal.getTime();
		SimpleDateFormat formatter5=new SimpleDateFormat("dd-MM-yyyy");
		String formats1 = formatter5.format(tomorrow);
		
		return formats1;
	}
}
