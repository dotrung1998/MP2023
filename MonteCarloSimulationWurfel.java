/**
 * @author Viet Quang Dang
 * @author Nhu Phuong Nguyen
 * @author The Trung Do
 * @version
 */
package MainProjekt;

import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

import java.util.Random;

/**
 * Diese Klasse repräsentiert eine Monte-Carlo-Simulation für das Werfen von zwei Würfeln.
 * Sie ermöglicht das Durchführen von Würfen, die Berechnung der Verteilung der Ergebnisse
 * und die Darstellung der Ergebnisse in einem grafischen Plot.
 */
public class MonteCarloSimulationWurfel {

    private int anzahlderWuerfe;
    private int[] ergebnis = new int[11];

    private double[] haufigkeit = new double[11];

    private Random random = new Random();

    /**
     * Konstruktor für die MonteCarloSimulationWurfel-Klasse.
     * @param anzahlderWuerfe Die Anzahl der Würfe, die in der Simulation durchgeführt werden sollen.
     */
    public MonteCarloSimulationWurfel(int anzahlderWuerfe){
        this.anzahlderWuerfe = anzahlderWuerfe;
    }

    /**
     * Gibt die Anzahl der Würfe zurück, die in der Simulation durchgeführt werden.
     * @return Die Anzahl der Würfe.
     */
    public int getAnzahlWuerfe(){
        return anzahlderWuerfe;
    }

    /**
     * Simuliert das Würfeln mit zwei Würfeln für die angegebene Anzahl von Würfen.
     * Berechnet die Verteilung der Ergebnisse.
     * @return Ein Array mit der Verteilung der Ergebnisse.
     */
    public int[] Wurfeln() {
        for (int i = 0; i < anzahlderWuerfe; i++) {
            int wuerfel1 = random.nextInt(6) + 1;
            int wuerfel2 = random.nextInt(6) + 1;
            int sum = wuerfel1 + wuerfel2;

            ergebnis[sum - 2]++;
        }
        return ergebnis;
    }

    /**
     * Berechnet die Verteilung der Ergebnisse als Häufigkeit der Summen der beiden geworfenen Würfel.
     * @return Ein Array, das die Verteilung der Ergebnisse als Häufigkeiten enthält.
     */
    public double[] ErgebnisVerteilung(){
        for (int i = 0; i < 11; i++){
            int sum = i + 2;
            haufigkeit[i] = (double) ergebnis[i] / anzahlderWuerfe;
        }
        return haufigkeit;
    }

    /**
     * Zeigt die Verteilung der Ergebnisse in einem grafischen Plot an.
     */
    public void ErgebnisDarstellung(){
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