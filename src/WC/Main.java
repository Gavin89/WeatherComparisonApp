package WC;

import java.net.UnknownHostException;
import WC.DataAnalyser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	public static void main(String[] args) throws UnknownHostException {
	    final Logger logger = LoggerFactory.getLogger(Main.class);
	    logger.info("Starting Application");
	    
		if(args[0].equals("all")){
			new Thread(new Runnable()
			{

				@Override
				public void run() {

					try {
						new ObservationsHarvester();
						new DataCollector();
						new DataAnalyser();

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}).start();
		}

		
		if(args[0].equals("collect")){
			new Thread(new Runnable()
			{

				@Override
				public void run() {

					try {
						new ObservationsHarvester();
						new DataCollector();

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}).start();
		}

		if(args[0].equals("calculate")){
			new DataAnalyser();

		}
		
	}
}

