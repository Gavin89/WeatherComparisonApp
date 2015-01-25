package WC;

public class ForecastItem {

	private Double temp;
	private Double windspeed;
	private int time;
	private String unixTime;
	private String date;
	private int leadTime;

	public String getDate(){
		return date;
	}
	
	public String setDate(String date){
		return this.date = date;
	}
	
	public int getLeadTime(){
		return leadTime;
	}
	
	public int setLeadTime(int leadTime){
		return this.leadTime = leadTime;
	}
	public Double getTemp() {
		return temp;
	}

	public String getUnixTime(){
		return unixTime;
	}
	
	public void setUnixTime(String unixTime){
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
	
	public ForecastItem(int time, Double temp, Double windspeed, String summary, String date, int leadTime) {
		this.temp = temp;
		this.windspeed = windspeed;
		this.summary = summary;
		this.time = time;
		this.date = date;
		this.leadTime = leadTime;
	}

	
}
