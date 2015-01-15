package WC;

import java.net.UnknownHostException;
import WC.DataAnalyser;

public class Main {

	public static void main(String[] args) throws UnknownHostException {
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
			DataAnalyser da = new DataAnalyser();
			da.run();
		}
	}
}

