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

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Diese Klasse stellt Methoden zur Berechnung und Darstellung von Korrelationskoeffizienten
 * zwischen zwei Datensätzen bereit. Sie ermöglicht die Berechnung von Rangkorrelation
 * und Pearson-Korrelation sowie die grafische Darstellung der Rangdaten.
 */
public class Korrelation {
    private ArrayList<Integer> xRang;
    private ArrayList<Integer> yRang;

    /**
     * Gibt den Rang eines Elements in der x-Rangliste an der gegebenen Indexposition zurück.
     * @param i Der Index der Position des Elements in der x-Rangliste.
     * @return Der Rang des Elements in der x-Rangliste.
     */
    public int getXRang(int i){ return xRang.get(i); }

    /**
     * Gibt den Rang eines Elements in der y-Rangliste an der gegebenen Indexposition zurück.
     * @param i Der Index der Position des Elements in der y-Rangliste.
     * @return Der Rang des Elements in der y-Rangliste.
     */
    public int getYRang(int i){ return yRang.get(i); }

    /**
     * Berechnet den Rangkorrelationskoeffizienten (Spearman's Rho) zwischen den x- und y-Werten basierend auf einer linearen Regression.
     * @param lr Das LineareRegression-Objekt, das die Daten enthält.
     * @return Der Rangkorrelationskoeffizient.
     */
    public double BerechnenRangkorrelation(LinearRegression lr) {
        int n = lr.getAnzahlderPunkte();

        // Ranglisten für x und y erstellen
        xRang = BerechnenRang(lr.getxWert());
        yRang = BerechnenRang(lr.getyWert());

        // Die Differenzen zwischen den Ranglisten berechnen
        ArrayList<Integer> rangDifferenzen = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            rangDifferenzen.add(xRang.get(i) - yRang.get(i));
        }

        // Der Rangkorrelationskoeffizienten berechnen
        double summeRangDifferenzQuadrat = 0.0;
        for (Integer diff : rangDifferenzen) {
            summeRangDifferenzQuadrat += Math.pow(diff, 2);
        }

        double rangKorrelation = 1 - (6 * summeRangDifferenzQuadrat) / (n * (n * n - 1));
        return rangKorrelation;
    }

    /**
     * Berechnet den Rang für eine Datenliste
     * @param liste Datenliste
     * @return ein Array von Rang.
     */
    private ArrayList<Integer> BerechnenRang(ArrayList<Double> liste) {
        // Die Werte in ein neues ArrayList kopieren, um die Originalreihenfolge beizubehalten
        ArrayList<Double> sortedValues = new ArrayList<Double>(liste);

        // Die Werte in aufsteigender Reihenfolge sortieren
        Collections.sort(sortedValues);

        // Eine Liste für die Ränge erstellen
        ArrayList<Integer> rang = new ArrayList<Integer>();

        // Die Werte der Ränge zuordnen
        for (Double value : liste) {
            int index = sortedValues.indexOf(value);
            rang.add(index + 1);
        }
        return rang;
    }

    /**
     * Zeigt die Rangdaten in einem Grafikplot an.
     */
    public void DarstellenRangdaten(){
        Graphic graph = new Graphic("Rangdaten");
        Plotter plotter = graph.getPlotter();
        int i = 0;

        while (i < xRang.size()) {
            plotter.add(xRang.get(i), yRang.get(i));
            plotter.addDataLineStyle(LineStyle.FILLED_SYMBOL);
            plotter.setSymbolSize(6);
            plotter.setDataColor(Color.LIGHT_GRAY);
            i++;
        }
        plotter.setXLabelFormat("%.0f");
        plotter.setYLabelFormat("%.0f");
        plotter.setAutoYgrid(xRang.size()/10);
        plotter.setAutoXgrid(yRang.size()/10);
        plotter.setXrange(1,xRang.size());
        plotter.setYrange(1,yRang.size());
    }

    /**
     * Berechnet den Pearson-Korrelationskoeffizienten zwischen den x- und y-Daten basierend auf einer linearen Regression.
     * @param lr Das LineareRegression-Objekt, das die Daten enthält.
     * @return Der Pearson-Korrelationskoeffizient.
     */
    public double BerechnenPearsonKorrelation(LinearRegression lr) {
        int n = lr.getAnzahlderPunkte();
        double xWertSumme = 0.0, yWertSumme = 0.0;
        for (int i = 0; i < n; i++) {
            double x = lr.getXWert(i);
            double y = lr.getYWert(i);
            xWertSumme += x;
            yWertSumme += y;
        }
        double xMittelwert = xWertSumme / n;
        double yMittelwert = yWertSumme / n;

        double zaehler = 0.0, nennerX = 0.0, nennerY = 0.0;

        for (int i = 0; i < n; i++) {
            double x = lr.getXWert(i);
            double y = lr.getYWert(i);
            zaehler += (x - xMittelwert) * (y - yMittelwert);
            nennerX += Math.pow(x - xMittelwert, 2);
            nennerY += Math.pow(y - yMittelwert, 2);
        }
        if (nennerX == 0 || nennerY == 0) {
            return 0.0; // Vermeiden Sie eine Division durch Null
        }
        double pearsonKorrelation = zaehler / (Math.sqrt(nennerX) * Math.sqrt(nennerY));
        return pearsonKorrelation;
    }
}
