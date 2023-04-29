package Monte_Carlo_Simulation;

import java.util.ArrayList;

public class LinearRegression {
    private ArrayList<Double> xValues;
    private ArrayList<Double> yValues;
    private int n;
    private double slope;
    private double intercept;

    public LinearRegression() {
        xValues = new ArrayList<Double>();
        yValues = new ArrayList<Double>();
    }

    public void addDataPoint(double x, double y) {
        xValues.add(x);
        yValues.add(y);
    }

    public void clearDataPoints() {
        xValues.clear();
        yValues.clear();
    }

    public void calculateCoefficients() {
        int n = xValues.size();
        double sumX = 0.0, sumY = 0.0, sumXX = 0.0, sumXY = 0.0;
        for (int i = 0; i < n; i++) {
            double x = xValues.get(i);
            double y = yValues.get(i);
            sumX += x;
            sumY += y;
            sumXX += x * x;
            sumXY += x * y;
        }
        double meanX = sumX / n;
        double meanY = sumY / n;
        this.slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        this.intercept = meanY - slope * meanX;
    }

    public double getSlope() {
        return this.slope;
    }

    public double getIntercept() {
        return this.intercept;
    }

    public double calculateRSquared() {
        int n = xValues.size();
        double sumYY = 0.0, sumY = 0.0, sumSS = 0.0;
        for (int i = 0; i < n; i++) {
            double predictedY = this.getSlope()*xValues.get(i) + this.getIntercept();
            sumYY += (yValues.get(i) - predictedY)*(yValues.get(i) - predictedY);
            sumY += yValues.get(i);
        }
        double meanY = sumY/n;
        for (int i = 0; i < n; i++) {
            sumSS += (yValues.get(i) - meanY)*(yValues.get(i) - meanY);
        }
        double rSquared = 1.0 - sumYY/sumSS;
        return rSquared;
    }

    public void drawRegressionLine() {
        // TODO: Implement code to draw regression line
    }
}

