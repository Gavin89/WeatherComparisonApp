package WeatherSource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	public String getParsedDate() {
		java.util.Date now = new Date();
		SimpleDateFormat formatter5=new SimpleDateFormat("dd/MM/yyyy");
		String formats1 = formatter5.format(now);
		
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
	
	
	
}
