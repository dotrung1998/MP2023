package Monte_Carlo_Simulation;

import java.util.Arrays;
import java.util.Random;

public class MonteCarloSimulationWurfel {

    private int anzahlderWuerfe;
    private int[] ergebnis = new int[11];

    private double[] haufigkeit = new double[11];

    Random random = new Random();

    public MonteCarloSimulationWurfel(int anzahlderWuerfe){
        this.anzahlderWuerfe = anzahlderWuerfe;
    }

    public int getAnzahlderWuerfe(){
        return anzahlderWuerfe;
    }

    public int[] wurfeln () {
        for (int i = 0; i < anzahlderWuerfe; i++) {
            int wuerfel1 = random.nextInt(6) + 1;
            int wuerfel2 = random.nextInt(6) + 1;
            int sum = wuerfel1 + wuerfel2;

            ergebnis[sum - 2]++;
        }
        return ergebnis;
    }

    public double[] ergebnisVerteilung(){
        for (int i = 0; i < 11; i++){
            int sum = i + 2;
            haufigkeit[i] = (double) ergebnis[i] / anzahlderWuerfe;
        }
        return haufigkeit;
    }

    @Override
    public String toString() {
        return "anzahlderWuerfe=" + anzahlderWuerfe +
                ", ergebnis=" + Arrays.toString(ergebnis) +
                ", haufigkeit=" + Arrays.toString(haufigkeit) +
                '}';
    }
}

