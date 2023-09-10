package Monte_Carlo_Simulation;

import java.util.Random;

public class MonteCarloSimulation {

    private int Iterationszahl;
    private int DatenpunktenAnzahl;
    private double[] xIntervall;
    private double[] yIntervall;
    private Random randomWert;

    public MonteCarloSimulation() {
    }

    public MonteCarloSimulation(int Iterationszahl, int DatenpunktenAnzahl, double[] xIntervall, double[] yIntervall) {
        this.Iterationszahl = Iterationszahl;
        this.DatenpunktenAnzahl = DatenpunktenAnzahl;
        this.xIntervall = xIntervall;
        this.yIntervall = yIntervall;
        this.randomWert = new Random();
    }

    private double[] generateRandomData() {
        double[] daten = new double[2 * DatenpunktenAnzahl];
        for (int i = 0; i < DatenpunktenAnzahl; i++) {
            daten[2 * i] = xIntervall[0] + (xIntervall[1] - xIntervall[0]) * randomWert.nextDouble();
            daten[2 * i + 1] = yIntervall[0] + (yIntervall[1] - yIntervall[0]) * randomWert.nextDouble();
        }
        return daten;
    }

    public double runSimulation() {
        double RSquaredSumme = 0.0;

        for (int i = 0; i < Iterationszahl; i++) {
            double[] daten = generateRandomData();
            LinearRegression regression = new LinearRegression();
            for (int j = 0; j < DatenpunktenAnzahl; j++) {
                regression.AddPunkte(daten[2 * j], daten[2 * j + 1]);
            }
            regression.KoeffizientenBerechnung();
            regression.regressiongeradeZeichnen();
            RSquaredSumme += regression.RSquaredBerechnen();
        }

        /*if (numIterations*numDataPoints<30000){
            regressionSum.calculateCoefficients();
            regressionSum.drawRegressionLine();
            regressionSum.calculateRSquared();}*/

        double RSquaredMittelwert = RSquaredSumme / Iterationszahl;
        return RSquaredMittelwert;

    }

    public void YRandomErstellen(LinearRegression linearregression, double standardabweichung) {
        randomWert = new Random();

        for (int i = 0; i< linearregression.getAnzahlderPunkte(); i++){
            double neuWert = linearregression.getYWertBackup(i) + randomWert.nextGaussian()*standardabweichung;
            linearregression.setYWert(i, neuWert);
        }
    }


    public double[] MCL_RSquared(LinearRegression linearregression, double standardabweichung, int Iterationszahl) {
        double RSquaredSumme = 0.0;
        double steigungSumme = 0.0;
        for (int i = 0; i < Iterationszahl; i++) {
            YRandomErstellen(linearregression, standardabweichung);
            linearregression.KoeffizientenBerechnung();
            linearregression.regressiongeradeZeichnen();
            RSquaredSumme += linearregression.RSquaredBerechnen();
            steigungSumme += linearregression.getSteigung();
        }
        double RSquaredMittelwert = RSquaredSumme / Iterationszahl;
        double SteigungMitelwert = steigungSumme / Iterationszahl;
        double[] ergebnis = {RSquaredMittelwert, SteigungMitelwert};
        return ergebnis;
    }


}
