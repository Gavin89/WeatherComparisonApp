package WC;

public class ForecastEntry {
	
    private WeatherLocation location;
    private Double temperature;
    private Double windspeed;
    private String date;
    private boolean isObservation;
    private String weatherSource;

    public ForecastEntry(WeatherLocation location, Double temperature, Double windspeed, String date, String weatherSource) {
        this.location = location;
        this.temperature = temperature;
        this.windspeed = windspeed;
        this.date = date;
        this.isObservation = weatherSource.equals("Observations");
        this.weatherSource = weatherSource;
    }

    public WeatherLocation getLocation() {
        return location;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindspeed() {
        return windspeed;
    }

    public String getDate() {
        return date;
    }

    public boolean isObservation() {
        return isObservation;
    }

    public String getWeatherSource() {
        return weatherSource;
    }
}