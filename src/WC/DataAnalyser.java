package WC;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    public LocationForecastWSMap getWeatherSourcesByLocationName(String locName, int time) {
        db = mongo.getDB("tempDB");
        collection = db.getCollection("tempData");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("location_name", locName);
        whereQuery.put("time", time);
        //whereQuery.put("date", "12-11-2014");
        DBCursor cursor = collection.find(whereQuery);

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


            }
        } finally {
            cursor.close();
        }

        return entries;
    }


    private WSErrors calculateErrorByWeatherSource(ArrayList<ForecastEntry> wsFEs, ArrayList<ForecastEntry> obsFEs) {
        WSErrors wsErrors = new WSErrors();

        for (int i = 0; i < 4; i++) {
            ForecastEntry wsFE = wsFEs.get(i);
            ForecastEntry obsFE = obsFEs.get(i);
            wsErrors.addError(wsFE.getTemperature() - obsFE.getTemperature());

        }

        return wsErrors;
    }



    private void processWeatherLocation(String name) {
        ArrayList<ForecastEntry> metoffice_fe = new ArrayList<ForecastEntry>();
        ArrayList<ForecastEntry> forecastio_fe = new ArrayList<ForecastEntry>();
        ArrayList<ForecastEntry> observations_fe = new ArrayList<ForecastEntry>();

        for (int time = 9; time <= 18; time += 3) {
            LocationForecastWSMap forecasts = this.getWeatherSourcesByLocationName(name, 18);
            metoffice_fe.add(forecasts.get("MettOffice"));
            forecastio_fe.add(forecasts.get("ForecastIO"));
            observations_fe.add(forecasts.get("Observations"));
        }

        WSErrors metoffice_err = calculateErrorByWeatherSource(metoffice_fe, observations_fe);
        WSErrors forecastio_err = calculateErrorByWeatherSource(forecastio_fe, observations_fe);



        System.out.println("Metoffice Bias: "+metoffice_err.calculateBias());
        System.out.println("Metoffice RMSE: "+metoffice_err.calculateRMSE());

        System.out.println("ForecastIO Bias: "+forecastio_err.calculateBias());
        System.out.println("ForecastIO RMSE: "+forecastio_err.calculateRMSE());


    }

	public void run() {



        processWeatherLocation("Coleshill");

	}

   private class WSErrors {
       ArrayList<Double> errs;

       public WSErrors() {
           this.errs = new ArrayList<Double>();
       }

       public void addError(double err) {
           this.errs.add(err);
       }

       public double calculateBias() {

           Double sum = 0.0;
           for (Double err : this.errs) {
               sum += err;
           }

           return sum / this.errs.size();
       }

       public double calculateRMSE() {
           double rmse = 0.0;

           int n = errs.size();

           for (Double err : this.errs) {
               rmse += Math.pow(err, 2);
           }


           double finalRMSE = Math.sqrt(rmse / (n - 1));

           return finalRMSE;
       }

   }

   private class LocationForecastWSMap {

       private HashMap<String, ForecastEntry> map;
       private String location;

       public LocationForecastWSMap(String location) {
           this.map = new HashMap<String, ForecastEntry>();
           this.location = location;

       }

       public void add(String weather_source, ForecastEntry fe) {
           this.map.put(weather_source, fe);
       }

       public ForecastEntry get(String weather_source) {
           return this.map.get(weather_source);
       }
   }

   private class ForecastEntry {
       public WeatherLocation getLocation() {
           return location;
       }

       public Double getTemperature() {
           return temperature;
       }

       public Double getWindspeed() {
           return windspeed;
       }

       public String getDate() {
           return date;
       }

       public boolean isObservation() {
           return isObservation;
       }

       public String getWeatherSource() {
           return weatherSource;
       }



       private WeatherLocation location;
       private Double temperature;
       private Double windspeed;
       private String date;
       private boolean isObservation;
       private String weatherSource;

       public ForecastEntry(WeatherLocation location, Double temperature, Double windspeed, String date, String weatherSource) {
           this.location = location;
           this.temperature = temperature;
           this.windspeed = windspeed;
           this.date = date;
           this.isObservation = weatherSource.equals("Observations");
           this.weatherSource = weatherSource;
       }
   }
}
