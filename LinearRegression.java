package Monte_Carlo_Simulation;

import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class LinearRegression {
    private ArrayList<Double> xWert;
    private ArrayList<Double> yWert;
    private ArrayList<Double> yWertBackup;

    private int n;
    private double steigung;
    private double yAchsenabschnitt;
    private Random randomWert;


    public LinearRegression() {
        xWert = new ArrayList<Double>();
        yWert = new ArrayList<Double>();
        yWertBackup = new ArrayList<Double>();
    }

    public double getXWert(int i){
        return xWert.get(i);
    }

    public double getYWert(int i){
        return yWert.get(i);
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

 ////////////////////// Anfang: Spearmann ////////////////////////////

    public double calculateRankCorrelation() {
        int n = xWert.size();

        // Erstellen Sie Ranglisten für x und y
        ArrayList<Double> xRank = calculateRank(xWert);
        ArrayList<Double> yRank = calculateRank(yWert);

        // Berechnen Sie die Differenzen zwischen den Ranglisten
        ArrayList<Double> rankDifferences = new ArrayList<Double>();
        for (int i = 0; i < n; i++) {
            rankDifferences.add(xRank.get(i) - yRank.get(i));
        }

        // Berechnen Sie den Rangkorrelationskoeffizienten
        double sumRankDifferencesSquared = 0.0;
        for (Double diff : rankDifferences) {
            sumRankDifferencesSquared += Math.pow(diff, 2);
        }

        double rankCorrelation = 1 - (6 * sumRankDifferencesSquared) / (n * (n * n - 1));
        return rankCorrelation;
    }

    ////
    private ArrayList<Double> calculateRank(ArrayList<Double> values) {
        ArrayList<Double> sortedValues = new ArrayList<Double>(values);
        Collections.sort(sortedValues);

        ArrayList<Double> rank = new ArrayList<Double>();
        for (Double value : values) {
            int index = sortedValues.indexOf(value);
            rank.add((double) (index + 1));
        }
        return rank;
    }

    ////////////////////// Ende: Spearmann ////////////////////////////


    ////////////////////// Anfang: Pearson Korrelation //////////////////////

        // Fügen Sie die Methode calculatePearsonCorrelation() hinzu
       /* public double calculatePearsonCorrelation() {
            int n = xWert.size();
            double sumX = 0.0, sumY = 0.0, sumXX = 0.0, sumYY = 0.0, sumXY = 0.0;

            for (int i = 0; i < n; i++) {
                double x = xWert.get(i);
                double y = yWert.get(i);
                sumX += x;
                sumY += y;
                sumXX += x * x;
                sumYY += y * y;
                sumXY += x * y;
            }

            double meanX = sumX / n;
            double meanY = sumY / n;

            double numerator = 0.0, denominatorX = 0.0, denominatorY = 0.0;

            for (int i = 0; i < n; i++) {
                double x = xValues.get(i);
                double y = yValues.get(i);
                numerator += (x - meanX) * (y - meanY);
                denominatorX += Math.pow(x - meanX, 2);
                denominatorY += Math.pow(y - meanY, 2);
            }

            if (denominatorX == 0 || denominatorY == 0) {
                return 0.0; // Vermeiden Sie eine Division durch Null
            }

            return numerator / (Math.sqrt(denominatorX) * Math.sqrt(denominatorY));
        } */
        // Andere vorhandene Methoden und Variablen


    ////////////////////// Ende: Pearson Korrelation //////////////////////
    
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

        //plotter.setYrange(yMin*0.8, yMax*1.2);
        //plotter.setXLine (0);
        //plotter.setYLine (0);
        //double[] xgrid = {1,2,3,4,5,6,7,8,9};
        plotter.setYLabelFormat("%.2f");
        plotter.setAutoYgrid((yMax - yMin) * 0.2);
        plotter.setXLabelFormat("%.2f");
        plotter.setAutoXgrid((xMax - xMin) * 0.2);
        //plotter.setXGrid(xgrid );

    }
/*Fehler bei Umtausch der Spalten
    public void AddDatenAusCSV(String pfad, int spalte1, int spalte2) {
        String zeile = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(pfad));
            br.readLine();

            while ((zeile = br.readLine()) != null) {
                String[] values = zeile.split(",");
                xWert.add(Double.parseDouble(values[spalte1 -1]));
                yWert.add(Double.parseDouble(values[spalte2 -1]));
                yWertBackup.add(Double.parseDouble(values[spalte2 -1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void YRandomErstellen(double standardabweichung) {
        randomWert = new Random();

        for (int i = 0; i< yWert.size(); i++){
                double newValues = yWertBackup.get(i) + randomWert.nextGaussian()*standardabweichung;
                yWert.set(i,newValues);
        }
    }

    /*public double[] MCL_RSquared(double standardabweichung,int numIterations) {
        double sumRSquared = 0.0;
        double sumSlope = 0.0;
        for (int i = 0; i<numIterations;i++){
            addNoisetoY(standardabweichung);
            KoeffizientenBerechnung();
            regressiongeradeZeichnen();
            sumRSquared += RSquaredBerechnen();
            sumSlope += steigung;
        }
        double averageRSquared = sumRSquared/numIterations;
        double averageSlope = sumSlope/numIterations;
        double[] ergebnis = {averageRSquared, averageSlope};
        return ergebnis;
    }*/

    /*public double[] MCL_Steigung_Konfidenzintervall(double standardabweichung, int numIterations){
        double sumSlope = 0.0;
        double sumIntercept = 0.0;
        for (int i = 0; i<numIterations;i++){
            YRandomErstellen(standardabweichung);
            KoeffizientenBerechnung();
            regressiongeradeZeichnen();
            sumSlope += steigung;
            sumIntercept += yAchsenabschnitt;
        }
        double averageSlop = sumSlope/numIterations;
        double averageIntercept = sumIntercept/numIterations;

        // SquaredResiduen berechnen
        double[] residuals = new double[xWert.size()];
        double sumSquaredResiduals = 0.0;
        double sumXValues = 0.0;

        for(int i = 0; i< xWert.size(); i++){
            residuals[i] = yWertBackup.get(i) - (averageIntercept + averageSlop * xWert.get(i));
            sumSquaredResiduals += Math.pow(residuals[i],2);
            sumXValues += xWert.get(i);
        }

        //Standardfehler der Slope berechnen
        double xMean = sumXValues/ xWert.size();
        double standardError = Math.sqrt(sumSquaredResiduals/((xWert.size() - 2) * sumSquaredDeviation(xWert,xMean)));

        //Konfidenzintervall berechnen
        double criticalValue = 1.96; // Für ein 95%-Konfidenzintervall (standard normal distribution)
        double[] slopeKonfidenzIntervall = {averageSlop - criticalValue * standardError, averageSlop + criticalValue * standardError};

        return slopeKonfidenzIntervall;
    }

    private double sumSquaredDeviation(ArrayList<Double> data, double mean) {
        double sum = 0;
        for (int i=0; i<data.size(); i++) {
            sum += Math.pow(data.get(i) - mean, 2);
        }
        return sum;
    }*/

}





