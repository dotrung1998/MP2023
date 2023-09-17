package MainPorjekt;

import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

import java.awt.*;
import java.util.ArrayList;

public class LinearRegression {
    private ArrayList<Double> xWert;
    private ArrayList<Double> yWert;
    private double steigung;
    private double yAchsenabschnitt;


    public LinearRegression() {
        xWert = new ArrayList<Double>();
        yWert = new ArrayList<Double>();
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

    //ValuesBackup für den Monte-Carlo
    public void AddPunkte(double x, double y) {
        xWert.add(x);
        yWert.add(y);
    }

    public void PunkteLoeschen() {
        xWert.clear();
        yWert.clear();
    }

    public void KoeffizientenBerechnung() {
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

    public double getSteigung() {
        return this.steigung;
    }

    public double getyAchsenabschnitt() {
        return this.yAchsenabschnitt;
    }

    public double RSquaredBerechnen() {
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

 ////////////////////// Rankkorrelation einfügen ////////////////////////////
    
    public void regressiongeradeZeichnen() {
        Graphic graph = new Graphic("lineare Regression");
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
            plotter.add(x, this.getSteigung() * x + this.getyAchsenabschnitt());
            plotter.setDataColor(Color.RED);
        }
        graph.repaint();

        plotter.setYLabelFormat("%.2f");
        plotter.setAutoYgrid((yMax - yMin) * 0.2);
        plotter.setXLabelFormat("%.2f");
        plotter.setAutoXgrid((xMax - xMin) * 0.2);

    }

}





