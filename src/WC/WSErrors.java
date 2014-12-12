package WC;

import java.util.ArrayList;

public class WSErrors {
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