package WC;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class LocationsProvider implements Iterable<WeatherLocation> {

	private HashMap<String, WeatherLocation> locations;
	private DB db;
	private Logger logger;
	private DBCollection collection;
	private BasicDBObject object;
	private DBCursor cur;

	public LocationsProvider() throws UnknownHostException {
	    logger = LoggerFactory.getLogger(LocationsProvider.class);
	    
	    logger.info("Collecting locations");
	    try{
		db = MongoDB.getMongoInstance().getDB("locations");
	    }
			catch(Exception e){
				logger.error(e.getMessage());
			}
		this.locations = new HashMap<String, WeatherLocation>();
		this.populateLocations();
	}

	private void populateLocations() throws UnknownHostException {

		try{
		collection = db.getCollection("locations");
		}
		catch (Exception e){
			logger.error(e.getMessage());
		}
		
		object = new BasicDBObject();
		cur = collection.find(object);
		while(cur.hasNext()){
			DBObject resultElement = null;
			resultElement = cur.next();
			String locationName = (String) resultElement.get("name");			
			String longitude =  (String) resultElement.get("latitude");		
			String latitude = (String) resultElement.get("longitude");	
			String locationId = null;	
			this.locations.put(locationName, new WeatherLocation(locationName, Double.parseDouble(longitude), Double.parseDouble(latitude), locationId));	
		}
		if(this.locations.size() == 133){
		logger.info("Locations Collected");
		}
		else {
			logger.error("Locations was not Collected");
		}
	}

	public WeatherLocation getLocationByName(String name) {
		return this.locations.get(name);
	}

	@Override
	public Iterator<WeatherLocation> iterator() {
		// TODO Auto-generated method stub
		return this.locations.values().iterator();
	}
}
