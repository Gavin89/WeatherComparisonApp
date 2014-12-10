package WC;
import java.net.UnknownHostException;
import java.util.ArrayList;

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
		
		this.locProvider = new LocationsProvider();
		this.collect();
	}
	

	private void collect() throws Exception {
		long startTime = System.currentTimeMillis();

		for (WeatherLocation loc : this.locProvider) {

			//System.out.println("Getting data for location "+loc.getLocationName());
			
			WeatherSourcesProvider wsProvider = new WeatherSourcesProvider(loc);
			
			for (WeatherSource ws : wsProvider) {
				ArrayList<ForecastItem> items = ws.getForecasts();
				for(ForecastItem item : items){

//					System.out.println("\t Getting data from " + ws.getName());
//					System.out.println("\t Time: " + item.getTime());
//					System.out.println("\t Time: " + item.getUnixTime());
//					System.out.println("\t Temp: " + item.getTemp());
//					System.out.println("\t Windspeed: " +item.getWindspeed());
//					System.out.println("\t Date: " +item.getDate());
//					System.out.println("\t Longitude: " + ws.getLongitude());
//					System.out.println("\t Latitude: " + ws.getLatitude());
//					System.out.println("\t Summary: " + item.getSummary());
//					System.out.println("");	
					
					this.populateTempDB(loc.getLocationName(), ws.getName(), item.getTime(), item.getTemp(), item.getWindspeed(), 
							item.getDate(), ws.getLongitude(), ws.getLatitude(), item.getSummary());
					
				}
				

			}
			
			/**
			 *  will need to find a way to populate db with every location and source
			 *  insert ws.getName + ws.getTemp + ws.getWindspeed + ws.getlong + ws.getLat + ws.getSummary + loc.getLocationName
			 *  also need time stamp + date
			 *  The database will then have per object id the following values
			 *  locationName : Birmingham
			 *  Temp: 20
			 *  Windspeed : 5
			 *  Longitude : 2.1
			 *  Latitude : 0.1
			 *  Summary : Windy
			 *  WeatherSource : MetOffice
			 *  Time : 0300
			 *  Date : 20/10/2014
			 *  
			 *  The web app will then request this data
			 *  Psuedoh = GET temp + windspeed + summary FROM database WHERE locationName OR lat + long = X. AND WHERE Time = 0300 AND Source = MetOfficvae
			 */
			
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}
	
	public void populateTempDB(String locationName, String sourceName, int time,
			Double temp, Double windspeed, String date, Double longitude,
			Double latitude, String summary) throws UnknownHostException{
			mongo = new MongoClient("localhost",27017);
			
		    db = mongo.getDB("weatherDB");

		// get a single collection
		collection = db.getCollection("weatherData");
		DBObject dbObject = new BasicDBObject("time", 9).append("weather_source", sourceName).append("location_name", locationName).append("temperature", temp)
				.append("windspeed", windspeed).append("date",  date).append("latitude", latitude).append("longitude", longitude).append("summary", summary);
		collection.insert(dbObject);
		// TODO Auto-generated method stub
	
	}
	
}
