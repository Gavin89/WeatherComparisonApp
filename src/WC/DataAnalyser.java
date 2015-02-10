package WC;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataAnalyser {
	private DBCollection collection;
	private DBCollection collection1;
	private DB db;
	private String weather_source;
	private String reportDate;
	private DBCursor cursor;
	private Logger logger;

	public LocationForecastWSMap getWeatherSourcesByLocationName(String locName, int time, String date, int leadTime) {

		logger = LoggerFactory.getLogger(DataAnalyser.class);
		LocationForecastWSMap entries = new LocationForecastWSMap(locName);
		db = MongoDB.getMongoInstance().getDB("weatherDB");
		collection = db.getCollection("weatherData");
		collection1 = db.getCollection("weatherCalculations");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("location_name", locName);
		whereQuery.put("time", time);
		whereQuery.put("date", date);
		whereQuery.put("lead_time", leadTime);
		try{
			cursor = collection.find(whereQuery);
		}
		catch (Exception e){
			logger.warn("No Entry Found at " + date.toString());
			return entries;
		}
		//System.out.println(cursor);
		try {
			while(cursor.hasNext()) {

				DBObject obj = cursor.next();

				//Create ForecastEntry
				String lat = obj.get("latitude").toString();
				String lng = obj.get("longitude").toString();

				WeatherLocation location = new WeatherLocation(locName, Double.parseDouble(lat) ,
						Double.parseDouble(lng), "");

				String weather_source = (String) obj.get("weather_source");

				String temperature = obj.get("temperature").toString();

				String windspeed = obj.get("windspeed").toString();

				ForecastEntry fe = new ForecastEntry(location, Double.parseDouble(temperature),
						Double.parseDouble(windspeed), obj.get("date").toString(), weather_source);

				entries.add(weather_source, fe);
			}
		} finally {
			cursor.close();
		}
		return entries;
	}

	private WSErrors calculateErrorByWeatherSource(ArrayList<ForecastEntry> wsFEs, ArrayList<ForecastEntry> obsFEs) throws Exception {
		WSErrors wsErrors = new WSErrors();

		for (int i = 0; i < 4; i++) {

			ForecastEntry wsFE = wsFEs.get(i);
			ForecastEntry obsFE = obsFEs.get(i);
			if (obsFE.getTemperature() != null && wsFE.getTemperature() !=null) {
				wsErrors.addError(wsFE.getTemperature() - obsFE.getTemperature());
			} else {
			}           
		}

		return wsErrors;
	}

	private void processWeatherLocation(String locationName) {
		ArrayList<ForecastEntry> metoffice_fe = new ArrayList<ForecastEntry>();
		ArrayList<ForecastEntry> forecastio_fe = new ArrayList<ForecastEntry>();
		ArrayList<ForecastEntry> observations_fe = new ArrayList<ForecastEntry>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		Date date1 = cal.getTime();
		for (int date = 0; date < 5; date++) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			date1 = cal.getTime();

			reportDate = sdf.format(date1);

			for (int leadTime = 0; leadTime < 2; leadTime++){

				for (int time = 9; time <= 18; time += 3) {

					LocationForecastWSMap forecasts = this.getWeatherSourcesByLocationName(locationName, time, String.valueOf(reportDate), leadTime);
					if(forecasts.hasData()){
						metoffice_fe.add(forecasts.get("MetOffice"));
						forecastio_fe.add(forecasts.get("ForecastIO"));
						observations_fe.add(forecasts.get("Observations"));
					}
				}
			}
		}

	WSErrors metoffice_err;
	WSErrors forecastio_err;

	System.out.println("Getting Calulations for " + locationName);

	try {
		metoffice_err = calculateErrorByWeatherSource(metoffice_fe, observations_fe);
		forecastio_err = calculateErrorByWeatherSource(forecastio_fe, observations_fe);
		System.out.println(forecastio_err.calculateRMSE());
		DBObject dbObject = new BasicDBObject("location_name", locationName).append("Weather_Source", "MetOffice").append("BIAS", metoffice_err.calculateBias())
				.append("RMSE",metoffice_err.calculateRMSE());
		collection1.insert(dbObject);
		DBObject dbObject1 = new BasicDBObject("location_name", locationName).append("Weather_Source", "ForecastIO").append("BIAS", forecastio_err.calculateBias())
				.append("RMSE",forecastio_err.calculateRMSE());
		collection1.insert(dbObject1);
	} catch (Exception e) {
		System.out.println("Skipping "+locationName+". "+e.getMessage()); 
		System.out.println("\n");
	}

}

public void run() throws UnknownHostException {

	DB db1 = MongoDB.getMongoInstance().getDB("locations");
	DBCollection collection1 = db1.getCollection("locations");
	BasicDBObject object = new BasicDBObject();
	DBCursor cur = collection1.find(object);
	while(cur.hasNext()){
		DBObject resultElement = null;
		resultElement = cur.next();
		String locationName = (String) resultElement.get("name");	
		//System.out.println(locationName);
		processWeatherLocation(locationName);
	}
}
}
