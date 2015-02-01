package WC;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public class MongoDB {
	private static MongoClient mongo = null;
	
	protected MongoDB() {
		
	}
	
	public static MongoClient getMongoInstance() {
		if (mongo == null) {
			try {
				mongo = new MongoClient("localhost",27017);
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mongo;
	}
	
	public static void closeMongoInstance() {
		if (mongo !=null) {
			mongo.close();
		}
	}
}
