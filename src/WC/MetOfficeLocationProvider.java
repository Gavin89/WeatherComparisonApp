package WC;

import java.net.UnknownHostException;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MetOfficeLocationProvider {
	
	public static WeatherLocation getSpecifiedLocation(String location) throws UnknownHostException{
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("test");
		DBCollection coll = db.getCollection("testCollection");
		BasicDBObject query = new BasicDBObject("name", location);

		DBCursor cur = coll.find(query);

		try {
		   while(cur.hasNext()) {
			   DBObject resultElement = null;
			   resultElement = cur.next();
			   String locationName = (String) resultElement.get("name");
			   if(locationName != location){
				   break;
			   }
			   else {
			   }
		       System.out.println(cur.next());
		   }
		} finally {
		   cur.close();
		}
		
		return null;
	}
}
