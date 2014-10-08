package WC;

import java.net.UnknownHostException;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MetOfficeLocationProvider {
	
	public static WeatherLocation getSpecifiedLocation(String location) throws UnknownHostException{
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("locations");
		DBCollection coll = db.getCollection("MetOfficeLocations");
		BasicDBObject query = new BasicDBObject("name", location);
		
		DBCursor cursor = coll.find(query);

		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
		
		return null;
	}
}
