package Monte_Carlo_Simulation;

import java.util.Random;

public class MonteCarloSimulation {
    private int numIterations;
    private int numDataPoints;
    private double[] xRange;
    private double[] yRange;
    private Random random;

    public MonteCarloSimulation(int numIterations, int numDataPoints, double[] xRange, double[] yRange) {
        this.numIterations = numIterations;
        this.numDataPoints = numDataPoints;
        this.xRange = xRange;
        this.yRange = yRange;
        this.random = new Random();
    }

    private double[] generateRandomData() {
        double[] data = new double[2*numDataPoints];
        for (int i = 0; i < numDataPoints; i++) {
            data[2*i] = xRange[0] + (xRange[1] - xRange[0])*random.nextDouble();
            data[2*i+1] = yRange[0] + (yRange[1] - yRange[0])*random.nextDouble();
        }
        return data;
    }

    public double runSimulation() {
        double sumRSquared = 0.0;
        for (int i = 0; i < numIterations; i++) {
            double[] data = generateRandomData();
            LinearRegression regression = new LinearRegression();
            for (int j = 0; j < numDataPoints; j++) {
                regression.addDataPoint(data[2*j], data[2*j+1]);
            }
            regression.calculateCoefficients();
            sumRSquared += regression.calculateRSquared();
        }
        double averageRSquared = sumRSquared/numIterations;
        return averageRSquared;
    }
}
