package WC;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;

import WeatherSource.WeatherSource;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataCollector {
	
	private	LocationsProvider locProvider;
	private DBCollection collection;
	private DB db;
	private MongoClient mongo;
	
	public DataCollector() throws Exception {
		try {
		System.out.println("Connecting to MongoDB");
		mongo = new MongoClient("localhost",27017);
		this.locProvider = new LocationsProvider();
		this.collect();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private void collect() throws Exception {

		System.out.println("Populating database");
		for (WeatherLocation loc : this.locProvider) {

			WeatherSourcesProvider wsProvider = new WeatherSourcesProvider(loc);
			
			for (WeatherSource ws : wsProvider) {
				//System.out.println("Getting forecasts from: " + ws.getName());
				ArrayList<ForecastItem> items = ws.getForecasts();
				//System.out.println("Populating Database with data from: " + ws.getName());
				for(ForecastItem item : items){
					
					this.populateTempDB(loc.getLocationName(), ws.getName(), item.getTime(), item.getTemp(), item.getWindspeed(), 
							item.getDate(), ws.getLongitude(), ws.getLatitude(), item.getSummary());				
				}	
				//System.out.println("Database successfully updated from source: " + ws.getName());
			}			
		}
		System.out.println("Database successfully populated");
	}
	
	public void populateTempDB(String locationName, String sourceName, int time,
			Double temp, Double windspeed, String date, Double longitude,
			Double latitude, String summary) throws UnknownHostException{
			
		    db = mongo.getDB("weatherDB");

		// get a single collection
		collection = db.getCollection("weatherData");
		DBObject dbObject = new BasicDBObject("time", time).append("weather_source", sourceName).append("location_name", locationName).append("temperature", temp)
				.append("windspeed", windspeed).append("date",  date).append("latitude", latitude).append("longitude", longitude).append("summary", summary);
		collection.insert(dbObject);
		// TODO Auto-generated method stub
	
	}
	
}
