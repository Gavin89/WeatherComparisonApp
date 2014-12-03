package WC;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class ObservationsHarvester {

	private JSONObject json;

	public ObservationsHarvester()  throws Exception {
		this.collect();
	}

	private void collect() throws Exception{
		try {
			json = new JSONObject(readUrl("http://datapoint.metoffice.gov.uk/public/data/val/wxobs/all/json/all?res=hourly&time=" + this.getYesterdayDate() + "T18Z&key=cb3f0007-c6a0-4633-9166-7fbbc8e76c9f"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MongoClient mongo = new MongoClient();
		DB db = mongo.getDB("tempDB");

		// get a single collection
		DBCollection collection = db.getCollection("tempData");

		JSONObject siteRep = json.getJSONObject("SiteRep");
		JSONObject dv = siteRep.getJSONObject("DV");
		JSONArray locationArr = dv.getJSONArray("Location");
		for(int i = 0; i < locationArr.length(); i++){
			String id = (String) locationArr.getJSONObject(i).get("i");
			String latitude = (String) locationArr.getJSONObject(i).get("lat");
			String longitude = (String) locationArr.getJSONObject(i).get("lon");
			String name = (String) locationArr.getJSONObject(i).get("name");	
			JSONObject period = locationArr.getJSONObject(i).getJSONObject("Period");
			JSONObject rep = period.getJSONObject("Rep");
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
			System.out.println(this.words(name)); //name.charAt(0) + name.substring(1, name.length()).toLowerCase());
			//only add locations to db if they have temp and windspeed
			if(rep.has("T") && rep.has("S")){

				DBObject dbObject = new BasicDBObject("time", newTime).append("weather_source", "Observations").append("location_name", this.words(name)).append("id", id).append("temperature", temperature)
						.append("windspeed", windspeed).append("date",  this.parseDate(date)).append("latitude", latitude).append("longitude", longitude);
				collection.insert(dbObject);
				System.out.println("Adding: "+name+"\n");

			}
			else {

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
		//return date//simpleDateFormat.format(dateObj);

	}

	public String getYesterdayDate() {
		java.util.Date  now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);	
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = cal.getTime();
		SimpleDateFormat formatter5=new SimpleDateFormat("yyyy-MM-dd");
		String formats1 = formatter5.format(yesterday);
		System.out.println(yesterday);
		return formats1;
	}


}