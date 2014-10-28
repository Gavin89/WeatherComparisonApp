package WC;

import java.util.ArrayList;
import java.util.Iterator;

import WeatherSource.WeatherSource;
import WeatherSources.Forecastio;
import WeatherSources.MetOffice;
import WeatherSources.Observations;
import WeatherSources.OpenWeatherMap;
import WeatherSources.Wunderground;


public class WeatherSourcesProvider implements Iterable<WeatherSource> {
	private ArrayList<WeatherSource> weatherSources;
	private WeatherLocation location;
	
	private void registerSources() {
		this.registerWeatherSource(new Forecastio(location));
		this.registerWeatherSource(new MetOffice(location));
		this.registerWeatherSource(new OpenWeatherMap(location));
		this.registerWeatherSource(new Wunderground(location));
		this.registerWeatherSource(new Observations(location));
	}
	
	public WeatherSourcesProvider(WeatherLocation loc) {
		
		this.location = loc;
		
		this.weatherSources = new ArrayList<>();
		
		this.registerSources();
	}

	private void registerWeatherSource(WeatherSource weatherSource) {
		this.weatherSources.add(weatherSource);
	}

	@Override
	public Iterator<WeatherSource> iterator() {
		return this.weatherSources.iterator();
	}
}
