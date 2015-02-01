package WC;

import java.net.UnknownHostException;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MetOfficeLocationProvider {
	
	public static WeatherLocation getSpecifiedLocation(String location) throws UnknownHostException{
		
		
		DB db = MongoDB.getMongoInstance().getDB("locations");
		DBCollection coll = db.getCollection("MetOfficeLocations");
		BasicDBObject query = new BasicDBObject("name", location);

		DBObject result = coll.findOne(query);

		if (result != null) {
			String name = result.get("name").toString();
			Double lat = Double.parseDouble(result.get("latitude").toString());
			Double lng = Double.parseDouble(result.get("longitude").toString());
			String id = (String) result.get("id");	
			WeatherLocation loc = new WeatherLocation(name, lat, lng, id);
			return loc;
		}	
		return null;
	}
}
