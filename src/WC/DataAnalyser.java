package WC;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class DataAnalyser {
	private DBCollection collection;
	private DB db;
	
	public LocationForecastWSMap getWeatherSourcesByLocationName(String locName, int time, String date, int leadTime) {

		db = MongoDB.getMongoInstance().getDB("weatherDB");
		collection = db.getCollection("weatherData");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("location_name", locName);
		whereQuery.put("time", time);
		whereQuery.put("date", date);
		whereQuery.put("lead_time", leadTime);
		DBCursor cursor = collection.find(whereQuery);
		//System.out.println(cursor.toString());
		LocationForecastWSMap entries = new LocationForecastWSMap(locName);

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

				//System.out.println("WS: "+weather_source+", Date: "+date+", Time: "+time+", Temperature: "+temperature);

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

	private void processWeatherLocation(String name) {
		ArrayList<ForecastEntry> metoffice_fe = new ArrayList<ForecastEntry>();
		ArrayList<ForecastEntry> forecastio_fe = new ArrayList<ForecastEntry>();
		ArrayList<ForecastEntry> observations_fe = new ArrayList<ForecastEntry>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		Date date1 = cal.getTime();
		String reportDate = sdf.format(date1);
		for (int date = 0; date < 5; date++) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			date1 = cal.getTime();
			reportDate = sdf.format(date1);
			//System.out.println(reportDate);
			for (int leadTime = 0; leadTime < 2; leadTime++){
				for (int time = 9; time <= 18; time += 3) {
					LocationForecastWSMap forecasts = this.getWeatherSourcesByLocationName(name, time, String.valueOf(reportDate), leadTime);
					metoffice_fe.add(forecasts.get("MettOffice"));
					forecastio_fe.add(forecasts.get("ForecastIO"));
					observations_fe.add(forecasts.get("Observations"));
					//System.out.println(time);
				}
			}
		}

		WSErrors metoffice_err;
		WSErrors forecastio_err;
		try {
			metoffice_err = calculateErrorByWeatherSource(metoffice_fe, observations_fe);
			forecastio_err = calculateErrorByWeatherSource(forecastio_fe, observations_fe);
			System.out.println("Getting Calulations for " + name);
			System.out.println("Metoffice Bias: "+metoffice_err.calculateBias());
			System.out.println("Metoffice RMSE: "+metoffice_err.calculateRMSE());
			System.out.println("ForecastIO Bias: "+forecastio_err.calculateBias());
			System.out.println("ForecastIO RMSE: "+forecastio_err.calculateRMSE());
			System.out.println("\n");
		} catch (Exception e) {
			System.out.println("Skipping "+name+". "+e.getMessage()); 
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
			processWeatherLocation(locationName);
		}
	}
}
