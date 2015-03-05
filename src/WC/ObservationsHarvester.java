package WC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class ObservationsHarvester {

	private JSONObject json;
	private DB db;
	private Logger logger;

	public ObservationsHarvester()  throws Exception {
		logger = LoggerFactory.getLogger(ObservationsHarvester.class);
		this.collect();	}

	private void collect() throws Exception{


		logger.info("Adding Observations to database");
		for(int j = 9; j <=18; j+=3){
			try {
				json = new JSONObject(readUrl("http://datapoint.metoffice.gov.uk/public/data/val/wxobs/all/json/all?res=hourly&time=" + this.getYesterdayDate() + "T" + j + "Z&key=cb3f0007-c6a0-4633-9166-7fbbc8e76c9f"));
				if(json == null){
					logger.warn("Observation unavailable", json.toString());
					continue;
				}
				try{
					db = MongoDB.getMongoInstance().getDB("weatherDB");
				}
				catch (Exception e){
					e.printStackTrace();
				}

				// get a single collection
				DBCollection collection = db.getCollection("weatherData");

				JSONObject siteRep = json.getJSONObject("SiteRep");
				JSONObject dv = siteRep.getJSONObject("DV");
				JSONArray locationArr = dv.getJSONArray("Location");
				for(int i = 0; i < locationArr.length(); i++){
					String id = (String) locationArr.getJSONObject(i).get("i");
					String latitude = (String) locationArr.getJSONObject(i).get("lat");
					String longitude = (String) locationArr.getJSONObject(i).get("lon");
					String name = (String) locationArr.getJSONObject(i).get("name");	
					JSONObject period = locationArr.getJSONObject(i).getJSONObject("Period");
					JSONObject rep = null;

					try {
						if (period.has("Period")) {	
							JSONObject period2 = period.getJSONObject("Period");
							rep = period2.getJSONObject("Rep");
						} else {
							rep = period.getJSONObject("Rep");
						}
					} catch (JSONException e) {
						System.out.println(locationArr.getJSONObject(i).toString());
					}

					if (rep != null) {

						String date = (String) period.get("value");
						String temperature = " ";
						String windspeed = " ";
						if(rep.has("T")){
							temperature = (String) rep.get("T");
						}
						if(rep.has("S")){
							windspeed = (String) rep.get("S");
						}
						int time = Integer.parseInt(rep.getString("$"));
						int newTime = time/60;	

						//only add locations to db if they have temp and windspeed
						if(rep.has("T") && rep.has("S")){

							DBObject dbObject = new BasicDBObject("time", newTime).append("weather_source", "Observations").append("location_name", this.words(name)).append("id", id).append("temperature", temperature)
									.append("windspeed", windspeed).append("date",  this.parseDate(date)).append("latitude", latitude).append("longitude", longitude);
							collection.insert(dbObject);						
						}
						else {
						}

					} else {
						logger.warn("Observation " + name + " was not added");
					}
				}

				logger.info("Observations added Successfully");
			}
			catch (JSONException | IOException e){
				if(e.getMessage().startsWith("Server returned HTTP response code: 504 for URL")){
					logger.error("Unable to get Observations for " + j);
					continue;
				}else {
				e.printStackTrace();
				logger.error("Unable to populate observations", json.toString());
				}
			}
		} 

	}

	public String words(String word){

		StringBuffer res = new StringBuffer();
		String lowerCaseWord = word.toLowerCase();
		String[] strArr = lowerCaseWord.split(" ");

		int index = 0;
		for (String str : strArr) {
			char[] stringArray = str.trim().toCharArray();

			index++;

			if (stringArray.length > 0) {
				stringArray[0] = Character.toUpperCase(stringArray[0]);
				str = new String(stringArray);

				if (index == strArr.length) {
					res.append(str);
				} else {
					res.append(str).append(" ");
				}
			}
		}
		return res.toString();
	}

	private static String readUrl(String urlString) throws Exception {
		BufferedReader reader = null;
		try {
			URL url = new URL(urlString);
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1)
				buffer.append(chars, 0, read); 

			return buffer.toString();
		} finally {
			if (reader != null)
				reader.close();
		}
	}

	public String parseDate(String value) throws ParseException{

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse(value);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		return formatter.format(date);
	}

	public String getYesterdayDate() {
		java.util.Date  now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);	
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = cal.getTime();
		SimpleDateFormat formatter5=new SimpleDateFormat("yyyy-MM-dd");
		String formats1 = formatter5.format(yesterday);

		return formats1;
	}
}
