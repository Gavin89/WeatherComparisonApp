package WC;

import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) throws UnknownHostException {
		new Thread(new DataCollector());
}
}
