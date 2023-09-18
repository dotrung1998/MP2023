package Monte_Carlo_Simulation;

import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LinearRegression {
    private ArrayList<Double> xWert;
    private ArrayList<Double> yWert;
    private ArrayList<Double> xRank;
    private ArrayList<Double> yRank;
    private ArrayList<Double> yWertBackup;

    private int n;
    private double steigung;
    private double yAchsenabschnitt;
    private double steigungRank;
    private double yAchsenabschnittRank;
    private Random randomWert;


    public LinearRegression() {
        xWert = new ArrayList<Double>();
        yWert = new ArrayList<Double>();
        yWertBackup = new ArrayList<Double>();
        xRank = new ArrayList<Double>();
        yRank = new ArrayList<Double>();
    }

    public double getXWert(int i){
        return xWert.get(i);
    }

    public double getYWert(int i){
        return yWert.get(i);
    }

    public double getXRank(int i){
        return xRank.get(i);
    }

    public double getYRank(int i){
        return yRank.get(i);
    }

    public double getYWertBackup(int i){
        return yWertBackup.get(i);
    }

    public Double setYWert(int i, double neuWert){
        return yWert.set(i,neuWert);
    }

 // Anzahl der Punkte
    public int getAnzahlderPunkte(){
        return xWert.size();
    }

    //ValuesBackup für den Monte-Carlo
    public void AddPunkte(double x, double y) {
        xWert.add(x);
        yWert.add(y);
        yWertBackup.add(y);
    }

    public void PunkteLoeschen() {
        xWert.clear();
        yWert.clear();
        yWertBackup.clear();
    }

    public void KoeffizientenRankBerechnung() {
        int anzahlRankPunkte = xRank.size();
        double xRankSumme = 0.0, yRankSumme = 0.0, xRankQuadratSumme = 0.0, xyRankSumme = 0.0;
        for (int i = 0; i < anzahlRankPunkte; i++) {
            double x = xRank.get(i);
            double y = yRank.get(i);
            xRankSumme += x;
            yRankSumme += y;
            xRankQuadratSumme += x * x;
            xyRankSumme += x * y;
        }
        double xRankMittelwert = xRankSumme / anzahlRankPunkte;
        double yRankMittelwert = yRankSumme / anzahlRankPunkte;
        this.steigungRank = (anzahlRankPunkte * xyRankSumme - xRankSumme * yRankSumme) / (anzahlRankPunkte * xRankQuadratSumme - xRankSumme * xRankSumme);
        this.yAchsenabschnittRank = yRankMittelwert - steigungRank * xRankMittelwert;
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

    public double getSteigungRank() {
        return this.steigungRank;
    }

    public double getyAchsenabschnittRank() {
        return this.yAchsenabschnittRank;
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

    public double RangkorrelationBerechnen() {
        int n = xWert.size();

        // Ranglisten für x und y erstellen
        xRank = RerechnenRank(xWert);
        yRank = RerechnenRank(yWert);

        // Die Differenzen zwischen den Ranglisten berechnen
        ArrayList<Double> rankDifferenzen = new ArrayList<Double>();
        for (int i = 0; i < n; i++) {
            rankDifferenzen.add(xRank.get(i) - yRank.get(i));
        }

        // Der Rangkorrelationskoeffizienten berechnen
        double summeRankDifferenzQuadrat = 0.0;
        for (Double diff : rankDifferenzen) {
            summeRankDifferenzQuadrat += Math.pow(diff, 2);
        }

        double rankKorrelation = 1 - (6 * summeRankDifferenzQuadrat) / (n * (n * n - 1));
        return rankKorrelation;
    }

    public ArrayList<Double> RerechnenRank(ArrayList<Double> values) {
        // Die Werte in ein neues ArrayList kopieren, um die Originalreihenfolge beizubehalten
        ArrayList<Double> sortedValues = new ArrayList<Double>(values);

        // Die Werte in aufsteigender Reihenfolge sortieren
        Collections.sort(sortedValues);

        // Eine Liste für die Ränge erstellen
        ArrayList<Double> rank = new ArrayList<Double>();

        // Die Werte der Ränge zuordnen
        for (Double value : values) {
            int index = sortedValues.indexOf(value);
            rank.add((double) (index + 1));
        }
        return rank;
    }
    
   /* public void regressiongeradeZeichnen() {
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

    }*/

    public void regressiongeradeZeichnen(boolean mitRank) {
        Graphic graph;
        ArrayList<Double> xWerte, yWerte;
        double steigungZeichen, yAchsenabschnittZeichen;

        if (mitRank) {
            graph = new Graphic("Lineare Regression mit Rank");
            xWerte = xRank;
            yWerte = yRank;
            steigungZeichen = this.getSteigungRank();
            yAchsenabschnittZeichen = this.getyAchsenabschnittRank();
        } else {
            graph = new Graphic("Lineare Regression");
            xWerte = xWert;
            yWerte = yWert;
            steigungZeichen = this.getSteigung();
            yAchsenabschnittZeichen = this.getyAchsenabschnitt();
        }

        Plotter plotter = graph.getPlotter();

        int i = 0;
        double xMax = xWerte.get(0), xMin = xWerte.get(0), yMax = yWerte.get(0), yMin = yWerte.get(0);

        while (i < xWerte.size()) {
            if (xMax < xWerte.get(i)) {
                xMax = xWerte.get(i);
            }

            if (xMin > xWerte.get(i)) {
                xMin = xWerte.get(i);
            }

            if (yMax < yWerte.get(i)) {
                yMax = yWerte.get(i);
            }

            if (yMin > yWerte.get(i)) {
                yMin = yWerte.get(i);
            }

            plotter.add(xWerte.get(i), yWerte.get(i));
            plotter.addDataLineStyle(LineStyle.FILLED_SYMBOL);
            plotter.setSymbolSize(6);
            plotter.setDataColor(Color.LIGHT_GRAY);
            i++;
        }

        plotter.nextDataSet();

        for (double x = xMin - (xMax - xMin) * 0.2; x <= xMax + (xMax - xMin) * 0.2; x += 0.05) {
            plotter.add(x, steigungZeichen * x + yAchsenabschnittZeichen);
            plotter.setDataColor(Color.RED);
        }
        graph.repaint();

        plotter.setYLabelFormat("%.2f");
        plotter.setAutoYgrid((yMax - yMin) * 0.2);
        plotter.setXLabelFormat("%.2f");
        plotter.setAutoXgrid((xMax - xMin) * 0.2);
    }

}





