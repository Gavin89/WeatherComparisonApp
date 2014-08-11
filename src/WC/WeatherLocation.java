package WC;

public class WeatherLocation {
	private double latitude;
	private double longitude;
	private String locationName;

	public WeatherLocation(String locatioName, double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.locationName = locatioName;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public String getLocationName() {
		return this.locationName;
	}
}
