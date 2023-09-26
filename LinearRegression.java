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

/**
 * Die Klasse LinearRegression implementiert eine einfache lineare Regression,
 * um eine lineare Beziehung zwischen einer unabhängigen Variable (x) und einer abhängigen
 * Variable (y) zu modellieren. Sie ermöglicht das Hinzufügen von Punkten, das Berechnen
 * der Regressionskoeffizienten (Steigung und y-Achsenabschnitt), das Berechnen von R-Quadrat
 * und das Zeichnen der Regressionsgeraden.
 */
public class LinearRegression {
    private ArrayList<Double> xWert;
    private ArrayList<Double> yWert;
    private double steigung;
    private double yAchsenabschnitt;

    /**
     * Konstruktor für die LinearRegression-Klasse.
     * Initialisiert die xWert- und yWert-Listen für die Punkte.
     */
    public LinearRegression() {
        xWert = new ArrayList<Double>();
        yWert = new ArrayList<Double>();
    }

    /**
     * Gibt die Liste der x-Werte der Punkte zurück.
     * @return Die Liste der x-Werte.
     */
    public ArrayList<Double> getxWert(){ return this.xWert; }

    /**
     * Gibt die Liste der y-Werte der Punkte zurück.
     * @return Die Liste der y-Werte.
     */
    public ArrayList<Double> getyWert(){ return this.yWert; }

    /**
     * Gibt den x-Wert eines Punktes an der gegebenen Indexposition zurück.
     * @param i Der Index der Position des Punktes.
     * @return Der x-Wert des Punktes an der angegebenen Position.
     */
    public double getXWert(int i){
        return xWert.get(i);
    }

    /**
     * Gibt den y-Wert eines Punktes an der gegebenen Indexposition zurück.
     * @param i Der Index der Position des Punktes.
     * @return Der y-Wert des Punktes an der angegebenen Position.
     */
    public double getYWert(int i){
        return yWert.get(i);
    }

    /**
     * Gibt die Anzahl der Punkte in den Listen zurück.
     * @return Die Anzahl der Punkte.
     */
    public int getAnzahlderPunkte(){
        return xWert.size();
    }

    /**
     * Gibt die berechnete Steigung (M) der Regressionsgeraden zurück.
     * @return Die Steigung der Regressionsgeraden.
     */
    public double getSteigung() { return this.steigung; }

    /**
     * Gibt den berechneten y-Achsenabschnitt (B) der Regressionsgeraden zurück.
     * @return Der y-Achsenabschnitt der Regressionsgeraden.
     */
    public double getyAchsenabschnitt() { return this.yAchsenabschnitt; }

    /**
     * Fügt einen Punkt mit den gegebenen x- und y-Werten zur Liste hinzu.
     * @param x Der x-Wert des Punktes.
     * @param y Der y-Wert des Punktes.
     */
    public void AddPunkte(double x, double y) {
        xWert.add(x);
        yWert.add(y);
    }

    /**
     * Löscht alle Punkte aus den Listen.
     */
    public void LoeschenPunkte() {
        xWert.clear();
        yWert.clear();
    }

    /**
     * Berechnet die Koeffizienten der Regressionsgeraden (Steigung und y-Achsenabschnitt).
     */
    public void BerechnenKoeffizienten() {
        int anzahlPunkte = xWert.size();
        double xWertSumme = 0.0, yWertSumme = 0.0, xWertQuadratSumme = 0.0, xySumme = 0.0;
        for (int i = 0; i < anzahlPunkte; i++) {
            double x = xWert.get(i);
            double y = yWert.get(i);
            xWertSumme += x;
            yWertSumme += y;
            xWertQuadratSumme += x * x;
            xySumme += x * y;
        }
        double xMittelwert = xWertSumme / anzahlPunkte;
        double yMittelwert = yWertSumme / anzahlPunkte;
        this.steigung = (xySumme - anzahlPunkte * xMittelwert * yMittelwert) / (xWertQuadratSumme - anzahlPunkte * xMittelwert*xMittelwert);
        this.yAchsenabschnitt = yMittelwert - steigung * xMittelwert;
    }

    /**
     * Berechnet das Bestimmtheitsmaß (R-squared) für die Regressionsgerade.
     * @return Das Bestimmtheitsmaß (R-squared).
     */
    public double BerechnenRSquared() {
        int anzahlPunkte = xWert.size();
        double erklärteVariation = 0.0, Gesamtvariation = 0.0, yWertSumme = 0.0;
        // erklärte Variation:
        // Gesamtvariation: Summe der Quadrate der Totalabstände, misst die Gesamtvariation der abhängigen Variable (Y) in den Daten.

        for (int i = 0; i < anzahlPunkte; i++) {
            yWertSumme += yWert.get(i);
        }
        double yMittelwert = yWertSumme / anzahlPunkte;

        for (int i = 0; i < anzahlPunkte; i++) {
            double schaetzWert = this.getSteigung() * xWert.get(i) + this.getyAchsenabschnitt();
            erklärteVariation += (schaetzWert - yMittelwert) * (schaetzWert - yMittelwert);
            Gesamtvariation += (yWert.get(i) - yMittelwert) * (yWert.get(i) - yMittelwert);
        }
        double rSquared = erklärteVariation/Gesamtvariation;
        return rSquared;
    }

    /**
     * Zeichnet die Regressionsgerade und die Punkte auf einem Grafikplot.
     */
    public void ZeichnenRegressiongerade() {
        Graphic graph = new Graphic("Lineare Regression");
        Plotter plotter = graph.getPlotter();

        int i = 0;
        double xMax = xWert.get(0), xMin = xWert.get(0), yMax = yWert.get(0), yMin = yWert.get(0);

        while (i < xWert.size()) {
            if (xMax < xWert.get(i)) {
                xMax = xWert.get(i);
            }

            if (xMin > xWert.get(i)) {
                xMin = xWert.get(i);
            }

            if (yMax < yWert.get(i)) {
                yMax = yWert.get(i);
            }

            if (yMin > yWert.get(i)) {
                yMin = yWert.get(i);
            }

            plotter.add(xWert.get(i), yWert.get(i));
            plotter.addDataLineStyle(LineStyle.FILLED_SYMBOL);
            plotter.setSymbolSize(6);
            plotter.setDataColor(Color.LIGHT_GRAY);
            i++;
        }

        plotter.nextDataSet();

        for (double x = xMin - (xMax - xMin) * 0.2; x <= xMax + (xMax - xMin) * 0.2; x += 0.05) {
            plotter.add(x, steigung * x + yAchsenabschnitt);
            plotter.setDataColor(Color.RED);
        }
        graph.repaint();

        plotter.setYLabelFormat("%.2f");
        plotter.setAutoYgrid((yMax - yMin) * 0.2);
        plotter.setXLabelFormat("%.2f");
        plotter.setAutoXgrid((xMax - xMin) * 0.2);

    }
}