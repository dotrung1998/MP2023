package MainProjekt;

import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class LinearRegression {
    private ArrayList<Double> xWert;
    private ArrayList<Double> yWert;
    private double steigung;
    private double yAchsenabschnitt;
    private ArrayList<Integer> xRang;
    private ArrayList<Integer> yRang;

    public LinearRegression() {
        xWert = new ArrayList<Double>();
        yWert = new ArrayList<Double>();
        xRang = new ArrayList<Integer>();
        yRang = new ArrayList<Integer>();
    }

    public double getXWert(int i){
        return xWert.get(i);
    }

    public double getYWert(int i){
        return yWert.get(i);
    }

    public int getAnzahlderPunkte(){
        return xWert.size();
    }
    public int getXRang(int i){ return xRang.get(i); }

    public int getYRang(int i){ return yRang.get(i); }
    public double getSteigung() { return this.steigung; }

    public double getyAchsenabschnitt() { return this.yAchsenabschnitt; }

    public void AddPunkte(double x, double y) {
        xWert.add(x);
        yWert.add(y);
    }

    public void LoeschenPunkte() {
        xWert.clear();
        yWert.clear();
    }

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
        this.steigung = (anzahlPunkte * xySumme - xWertSumme * yWertSumme) / (anzahlPunkte * xWertQuadratSumme - xWertSumme * xWertSumme);
        this.yAchsenabschnitt = yMittelwert - steigung * xMittelwert;
    }

    public double BerechnenRSquared() {
        int anzahlPunkte = xWert.size();
        double SQT = 0.0, yWertSumme = 0.0, SQR = 0.0;
        // SQT: Summe der Quadrate der Totalabstände, misst die Gesamtvariation der abhängigen Variable (Y) in den Daten.
        // SQR: Summe der quadrierten Regressionsabweichungen, misst die durch das lineare Regressionsmodell erklärte Variation der abhängigen Variable (Y).
        for (int i = 0; i < anzahlPunkte; i++) {
            double schaetzWert = this.getSteigung() * xWert.get(i) + this.getyAchsenabschnitt();
            SQR += (yWert.get(i) - schaetzWert) * (yWert.get(i) - schaetzWert);
            yWertSumme += yWert.get(i);
        }
        double yMittelwert = yWertSumme / anzahlPunkte;
        for (int i = 0; i < anzahlPunkte; i++) {
            SQT += (yWert.get(i) - yMittelwert) * (yWert.get(i) - yMittelwert);
        }
        double rSquared = 1.0 - SQR / SQT;
        return rSquared;
    }

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

    // Rangkorrelation //
 public double BerechnenRangkorrelation() {
     int n = xWert.size();

     // Ranglisten für x und y erstellen
     xRang = BerechnenRang(xWert);
     yRang = BerechnenRang(yWert);

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

   private ArrayList<Integer> BerechnenRang(ArrayList<Double> values) {
        // Die Werte in ein neues ArrayList kopieren, um die Originalreihenfolge beizubehalten
        ArrayList<Double> sortedValues = new ArrayList<Double>(values);

        // Die Werte in aufsteigender Reihenfolge sortieren
        Collections.sort(sortedValues);

        // Eine Liste für die Ränge erstellen
        ArrayList<Integer> rang = new ArrayList<Integer>();

        // Die Werte der Ränge zuordnen
        for (Double value : values) {
            int index = sortedValues.indexOf(value);
            rang.add(index + 1);
        }
        return rang;
    }

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
        plotter.setAutoYgrid(xRang.size()/10);
        plotter.setAutoXgrid(yRang.size()/10);
        plotter.setXrange(0,xRang.size());
        plotter.setYrange(0,yRang.size());
    }
}





