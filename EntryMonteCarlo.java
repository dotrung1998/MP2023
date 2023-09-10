package Monte_Carlo_Simulation;

public class EntryMonteCarlo {
    public static void main(String[] args) {
        /* int numIterations = 10;
        int numDataPoints = 500;
        double[] xRange = {65, 72};
        double[] yRange = {45,120};
        MonteCarloSimulation simulation = new MonteCarloSimulation(numIterations, numDataPoints, xRange, yRange);
        double averageRSquared = simulation.runSimulation();
        System.out.println("Average R-squared value: " + averageRSquared);
         */
        MonteCarloSimulationWurfel test = new MonteCarloSimulationWurfel(1000);
        int[] ergebnis = test.wurfeln();

        double[] verteilung = test.ergebnisVerteilung();
        System.out.println(test.toString());

    }

}
