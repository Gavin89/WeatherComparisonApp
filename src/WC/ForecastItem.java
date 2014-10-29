package WC;

import java.util.Date;

public class ForecastItem {

	private Double temp;
	private Double windspeed;
	private int time;
	private Date unixTime;

	public Double getTemp() {
		return temp;
	}

	public Date getUnixTime(Date unixTime){
		return this.unixTime = unixTime;
	}
	
	public void setUnixTime(Date unixTime){
		this.unixTime = unixTime;
	}
	
	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public Double getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(Double windspeed) {
		this.windspeed = windspeed;
	}


	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public int getTime() {
		return this.time;
	}

	private String summary;
	
	public ForecastItem(int time, Double temp, Double windspeed, String summary) {
		this.temp = temp;
		this.windspeed = windspeed;
		this.summary = summary;
		this.time = time;
	}

	public ForecastItem(Date unixTime, double temp, double windspeed,
			String summary) {
		this.temp = temp;
		this.windspeed = windspeed;
		this.summary = summary;
		this.unixTime = unixTime;
		// TODO Auto-generated constructor stub
	}
	
	
}
