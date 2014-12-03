package WC;

import java.net.UnknownHostException;
import WC.DataAnalyser;

public class Main {

	public static void main(String[] args) throws UnknownHostException {
//		new Thread(new Runnable()
//		{
//
//			@Override
//			public void run() {
//
//					try {
//						new ObservationsHarvester();
//						new DataCollector();
//
//					} catch (Exception e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//			}
//		}).start();

        DataAnalyser data = new DataAnalyser();
        data.getData();
	}
}
