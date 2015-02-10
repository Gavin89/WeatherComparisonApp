package WC;

import java.util.HashMap;

public class LocationForecastWSMap {

    private HashMap<String, ForecastEntry> map;
    private String location;

    public LocationForecastWSMap(String location) {
        this.map = new HashMap<String, ForecastEntry>();
        this.location = location;
    }

    public void add(String weather_source, ForecastEntry fe) {
        this.map.put(weather_source, fe);
    }

    public ForecastEntry get(String weather_source) {
        return this.map.get(weather_source);
    }
    
    public ForecastEntry get(ForecastEntry fe) {
        return this.map.get(fe);
    }
    
    public boolean hasData(){
    	return !map.isEmpty();
    	}
    }
    