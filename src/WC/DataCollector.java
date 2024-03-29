package WC;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import WeatherSource.WeatherSource;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class DataCollector {

	private	LocationsProvider locProvider;
	private DBCollection collection;
	private DB db;
	private Logger logger;

	public DataCollector() throws Exception {
		logger = LoggerFactory.getLogger(DataCollector.class);
		logger.info("Collecting the Data");
		try {
			this.locProvider = new LocationsProvider();
			this.collect();
		}
		catch (Exception e){
			logger.error("Connecting to database failed", e.getStackTrace());
		}
	}

	private void collect() throws Exception {

		logger.info("Populating database");
		for (WeatherLocation loc : this.locProvider) {

			WeatherSourcesProvider wsProvider = new WeatherSourcesProvider(loc);

			for (WeatherSource ws : wsProvider) {
				ArrayList<ForecastItem> items = ws.getForecasts();
				if(ws.getForecasts()!= null){

					for(ForecastItem item : items){

						this.populateTempDB(loc.getLocationName(), ws.getName(), item.getTime(), item.getTemp(), item.getWindspeed(), 
								item.getDate(), ws.getLongitude(), ws.getLatitude(), item.getSummary(), item.getLeadTime());				
					}
				}
				else {
					logger.warn("Unable to add " + ws.getName() + "to the database due to no data");
				}
			}			
		}
		logger.info("Database successfully populated");
	}

	public void populateTempDB(String locationName, String sourceName, int time,
			Double temp, Double windspeed, String date, Double longitude,
			Double latitude, String summary, int leadTime) throws UnknownHostException{

		db = MongoDB.getMongoInstance().getDB("weatherDB");

		// get a single collection
		collection = db.getCollection("weatherData");
		DBObject dbObject = new BasicDBObject("time", time).append("weather_source", sourceName).append("location_name", locationName).append("temperature", temp)
				.append("windspeed", windspeed).append("date",  date).append("summary", summary).append("lead_time",  leadTime);
		
		//geoNear spatial mongodb
		double x = longitude;
		double y = latitude;
		
		double [] location = new double [] {x, y};
		dbObject.put("loc", location);
		try{			
			collection.insert(dbObject);
			// TODO Auto-generated method stub
		}
		catch (Exception e){
			logger.error("Inserting data into database failed : " + e.getMessage());
		}
	}

}
