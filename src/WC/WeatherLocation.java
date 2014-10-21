package WC;

public class WeatherLocation {
	private double latitude;
	private double longitude;
	private String locationName;
	private String locationId;

	public WeatherLocation(String locationName, double latitude, double longitude, String locationId) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.locationName = locationName;
		this.locationId = locationId;
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
	
	public String getLocationId() {
		return this.locationId;
	}
}
