package Monte_Carlo_Simulation;

public class MainKlasse {
    public static void main(String[] args) {
        int numIterations = 10000;
        int numDataPoints = 12;
        double[] xRange = {45, 70};
        double[] yRange = {153, 177};
        MonteCarloSimulation simulation = new MonteCarloSimulation(numIterations, numDataPoints, xRange, yRange);
        double averageRSquared = simulation.runSimulation();
        System.out.println("Average R-squared value: " + averageRSquared);





        //GUI gui =new GUI();
    }
}
