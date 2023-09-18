package MainPorjekt;

import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class LinearRegression {
    private ArrayList<Double> xWert; // Liste der x-Werte der Datenpunkte
    private ArrayList<Double> yWert; // Liste der y-Werte der Datenpunkte
    private ArrayList<Double> xRang; // Liste der Rang-werte für x
    private ArrayList<Double> yRang; // Liste der Rang-werte für y
    private ArrayList<Double> yWertBackup; // Backup der y-Werte für Monte-Carlo-Simulation


    private double steigung; // Steigung der Regressionsgeraden
    private double yAchsenabschnitt; // y-Achsenabschnitt der Regressionsgeraden
    private double steigungRang; // Steigung der Regressionsgeraden mit Rang
    private double yAchsenabschnittRang; // y-Achsenabschnitt der Regressionsgeraden mit Rang

    public LinearRegression() {
        xWert = new ArrayList<Double>();
        yWert = new ArrayList<Double>();
        yWertBackup = new ArrayList<Double>();
        xRang = new ArrayList<Double>();
        yRang = new ArrayList<Double>();
    }

    public double getXWert(int i){
        return xWert.get(i);
    }

    public double getYWert(int i){
        return yWert.get(i);
    }

    public double getXRank(int i){
        return xRang.get(i);
    }

    public double getYRank(int i){
        return yRang.get(i);
    }

    public double getYWertBackup(int i){
        return yWertBackup.get(i);
    }

    public Double setYWert(int i, double neuWert){
        return yWert.set(i,neuWert);
    }

    // Anzahl der vorhandenen Datenpunkte abrufen
    public int getAnzahlderPunkte(){
        return xWert.size();
    }

    // Die Hinzufügung der Datenpunkte für die Monte-Carlo-Simulation
    public void AddPunkte(double x, double y) {
        xWert.add(x);
        yWert.add(y);
        yWertBackup.add(y);
    }

    // Löschung aller Datenpunkte
    public void PunkteLoeschen() {
        xWert.clear();
        yWert.clear();
        yWertBackup.clear();
    }

    public void BerechnenRegressionskoeffizienten(boolean mitRang) {
        int anzahlPunkte = xWert.size();
        ArrayList<Double> xDaten = mitRang ? xRang : xWert;
        ArrayList<Double> yDaten = mitRang ? yRang : yWert;

        double xWertSumme = 0.0, yWertSumme = 0.0, xWertQuadratSumme = 0.0, xySumme = 0.0;

        for (int i = 0; i < anzahlPunkte; i++) {
            double x = xDaten.get(i);
            double y = yDaten.get(i);
            xWertSumme += x;
            yWertSumme += y;
            xWertQuadratSumme += x * x;
            xySumme += x * y;
        }

        double xMittelwert = xWertSumme / anzahlPunkte;
        double yMittelwert = yWertSumme / anzahlPunkte;

        if (mitRang) {
            this.steigungRang = (anzahlPunkte * xySumme - xWertSumme * yWertSumme) / (anzahlPunkte * xWertQuadratSumme - xWertSumme * xWertSumme);
            this.yAchsenabschnittRang = yMittelwert - steigungRang * xMittelwert;
        } else {
            this.steigung = (anzahlPunkte * xySumme - xWertSumme * yWertSumme) / (anzahlPunkte * xWertQuadratSumme - xWertSumme * xWertSumme);
            this.yAchsenabschnitt = yMittelwert - steigung * xMittelwert;
        }
    }

    // Abrufen der Steigung der Regressionsgeraden ohne Rang
    public double getSteigung() {
        return this.steigung;
    }

    // Abrufen des y-Achsenabschnitts der Regressionsgeraden ohne Rang
    public double getyAchsenabschnitt() {
        return this.yAchsenabschnitt;
    }

    // Abrufen des y-Achsenabschnitts der Regressionsgeraden mit Rang
    public double getSteigungRang() {
        return this.steigungRang;
    }

    // Abrufen des y-Achsenabschnitts der Regressionsgeraden mit Rang
    public double getyAchsenabschnittRang() {
        return this.yAchsenabschnittRang;
    }

    // Berechnung des R-squared-Wertes zur Analyse der Korrekation zwischen Daten
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

    // Berechnung des Rangkorrelationskoeffizienten für Datenpunkte
    public double RangkorrelationBerechnen() {
        int n = xWert.size();

        // Ranglisten für x und y erstellen
        xRang = RerechnenRang(xWert);
        yRang = RerechnenRang(yWert);

        // Berechnung der Differenzen zwischen den Ranglisten
        ArrayList<Double> rankDifferenzen = new ArrayList<Double>();
        for (int i = 0; i < n; i++) {
            rankDifferenzen.add(xRang.get(i) - yRang.get(i));
        }

        // Berechnung der Rangkorrelationskoeffizienten
        double summeRankDifferenzQuadrat = 0.0;
        for (Double diff : rankDifferenzen) {
            summeRankDifferenzQuadrat += Math.pow(diff, 2);
        }

        double rangKorrelation = 1 - (6 * summeRankDifferenzQuadrat) / (n * (n * n - 1));
        return rangKorrelation;
    }

    // Berechnung der Ranglisten für eine Liste von Werten
    public ArrayList<Double> RerechnenRang(ArrayList<Double> values) {
        // Kopieren der Werte in ein neues ArrayList, um die Originalreihenfolge beizubehalten
        ArrayList<Double> sortedValues = new ArrayList<Double>(values);

        // Sortierung der Werte in aufsteigender Reihenfolge
        Collections.sort(sortedValues);

        // Erstellung einer Liste für die Ränge
        ArrayList<Double> rank = new ArrayList<Double>();

        // Zuordnung der Werte der Ränge
        for (Double value : values) {
            int index = sortedValues.indexOf(value);
            rank.add((double) (index + 1));
        }
        return rank;
    }

   // Methode zur Zeichnung der Regressionsgeraden (mit und ohne Rang)
    public void regressiongeradeZeichnen(boolean mitRang) {
        Graphic graph;
        ArrayList<Double> xWerte, yWerte;
        double steigungZeichen, yAchsenabschnittZeichen;

        if (mitRang) {
            graph = new Graphic("Lineare Regression mit Rang");
            xWerte = xRang;
            yWerte = yRang;
            steigungZeichen = this.getSteigungRang();
            yAchsenabschnittZeichen = this.getyAchsenabschnittRang();
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





