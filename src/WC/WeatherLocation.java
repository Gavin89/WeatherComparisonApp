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
	
	public Double getLatitude() {
		return this.latitude;
	}
	
	public Double getLongitude() {
		return this.longitude;
	}
	
	public String getLocationName() {
		return this.locationName;
	}
}
