package WC;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSErrors {
	ArrayList<Double> errs;
	private Logger logger;

	public WSErrors() {
		logger = LoggerFactory.getLogger(WSErrors.class);
		this.errs = new ArrayList<Double>();
	}

	public void addError(double err) {
		this.errs.add(err);
	}

	public double calculateBias() {
		Double sum = 0.0;
		if(this.errs.size() > 0){
			for (Double err : this.errs) {
				sum += err;
			}
		}
		else {
			logger.warn("BIAS could not be cacultated - no enough values");
		}
		double bias = sum / this.errs.size();
		
		DecimalFormat df = new DecimalFormat("#.##");
		double finalBIAS = Double.valueOf(df.format(bias));
		 
		return finalBIAS;
	}

	public double calculateRMSE() {
		double rmse = 0.0;
		int n = errs.size();
		if(errs.size() > 2) {

			for (Double err : this.errs) {
				rmse += Math.pow(err, 2);
			}
		}
		else
		{
			logger.warn("RMSE could not be calculated - not enough values");
		}

		double RMSE = Math.sqrt(rmse / (n - 1));
		
		DecimalFormat df = new DecimalFormat("#.##");
		double finalRMSE = Double.valueOf(df.format(RMSE));
		return finalRMSE;
	}

}