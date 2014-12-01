package WC;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataAnalyser {
	private MongoClient mongo;
	private DBCollection collection, collection1;
	private DB db, db1;
	public DataAnalyser () {
		try {
			mongo = new MongoClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void populateTempDB(String locationName, String sourceName, int time,
			Double temp, Double windspeed, String date, Double longitude,
			Double latitude, String summary){
		db = mongo.getDB("tempDB");

		// get a single collection
		collection = db.getCollection("tempData");
		DBObject dbObject = new BasicDBObject("time", time).append("weather_source", sourceName).append("location_name", locationName).append("temperature", temp)
				.append("windspeed", windspeed).append("date",  date).append("latitude", latitude).append("longitude", longitude);
		collection.insert(dbObject);
		// TODO Auto-generated method stub

	}

	public void getData() {

		db = mongo.getDB("tempDB");
		collection = db.getCollection("tempData");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("location_name", "Coleshill");
		whereQuery.put("time", 18);
		//whereQuery.put("date", "12-11-2014");
		DBCursor cursor = collection.find(whereQuery);
		try {
			
			while(cursor.hasNext()) {
				DBObject result = (DBObject) collection.find(cursor.next());
				System.out.println(result);
				//System.out.println(cursor.next());
				
			}
		} finally {
			cursor.close();
		}

	}
}
