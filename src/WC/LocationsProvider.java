package WC;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class LocationsProvider implements Iterable<WeatherLocation> {

	private HashMap<String, WeatherLocation> locations;

	public LocationsProvider() throws UnknownHostException {
		this.locations = new HashMap<String, WeatherLocation>();

		this.populateLocations();
	}

	private void populateLocations() throws UnknownHostException {

		DB db = (new MongoClient("localhost",27017)).getDB("locations");
		DBCollection collection = db.getCollection("locations");
		BasicDBObject object = new BasicDBObject();
		DBCursor cur = collection.find(object);
		while(cur.hasNext()){
			DBObject resultElement = null;
			resultElement = cur.next();
			String locationName = (String) resultElement.get("name");			
			String longitude =  (String) resultElement.get("latitude");		
			String latitude = (String) resultElement.get("longitude");	
			String locationId = null;	
			this.locations.put(locationName, new WeatherLocation(locationName, Double.parseDouble(longitude), Double.parseDouble(latitude), locationId));	
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
