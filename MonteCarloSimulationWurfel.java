package MainPorjekt;

import java.util.Random;
import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

public class MonteCarloSimulationWurfel {

    private int anzahlderWuerfe;
    private int[] ergebnis = new int[11];

    private double[] haufigkeit = new double[11];

    Random random = new Random();

    public MonteCarloSimulationWurfel(int anzahlderWuerfe){
        this.anzahlderWuerfe = anzahlderWuerfe;
    }

    public int[] wurfeln () {
        for (int i = 0; i < anzahlderWuerfe; i++) {
            int wuerfel1 = random.nextInt(6) + 1;
            int wuerfel2 = random.nextInt(6) + 1;
            int summe = wuerfel1 + wuerfel2;

            ergebnis[summe - 2]++;
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

    public void ergebnisDarstellen(){
        Graphic graph = new Graphic("Häufigkeitsdarstellung");
        Plotter plotter = graph.getPlotter();

        for (int i=2; i<=12; i++){
                plotter.add(i,ergebnis[i-2]);
        }
        plotter.addDataLineStyle(LineStyle.HISTOGRAM);

        double[] xGrid = {2,3,4,5,6,7,8,9,10,11,12};
        plotter.setXrange(1,13);
        plotter.setXGrid(xGrid);

    }
}

