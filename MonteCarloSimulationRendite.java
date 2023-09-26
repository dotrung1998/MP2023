/**
 * @author Viet Quang Dang
 * @author Nhu Phuong Nguyen
 * @author The Trung Do
 * @version
 */
package MainProjekt;

import plotter.Graphic;
import plotter.Plotter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Diese Klasse stellt eine Monte-Carlo-Simulation für die Berechnung des Portfoliowerts dar.
 * Sie verwendet eine lineare Regression, um zufällige Renditen zu generieren und den Portfoliowert über einen Zeitraum zu verfolgen.
 */
public class MonteCarloSimulationRendite {
    private ArrayList<Double> xWertZufall;
    private ArrayList<Double> yWertZufall;
    private Random randomWert;

    /**
     * Konstruktor für die MonteCarloSimulationRendite-Klasse.
     * Initialisiert die ArrayLists für zufällige Werte.
     */
    public MonteCarloSimulationRendite() {
        xWertZufall = new ArrayList<Double>();
        yWertZufall = new ArrayList<Double>();
    }

    /**
     * Berechnet den Portfoliowert über einen Zeitraum unter Verwendung einer Monte-Carlo-Simulation.
     * @param lr Die lineare Regression, die zur Generierung von Renditen verwendet wird.
     * @param iterationen Die Anzahl der Iterationen für die Simulation.
     * @param betrag Der Startbetrag des Portfolios.
     * @param zeitraum Die Anzahl der Tage, für die der Portfoliowert verfolgt wird.
     * @return Der durchschnittliche Portfoliowert über alle Iterationen.
     */
    public double BerechnenPortfolioswert(LinearRegression lr, int iterationen,double betrag, int zeitraum){
        Graphic graph = new Graphic("Portfolio Verlauf");
        Plotter plotter = graph.getPlotter();

        double[] portfolioVerlauf = new double[zeitraum];
        double portfolio;
        double portfolioSumme=0.0;
        double erwartungswert = ErwartungswertRendite(lr);
        double standardabweichung = StandardabweichungRendite(lr);
        int farbe = 0;
        Color[] grafikFarben = {Color.RED,Color.BLACK,Color.BLUE,Color.CYAN,Color.PINK,Color.GREEN,Color.YELLOW,Color.ORANGE, Color.DARK_GRAY,Color.MAGENTA};

        for (int i = 0; i<iterationen;i++){
            xWertZufall.clear();
            yWertZufall.clear();

            randomWert = new Random();

            for (int a = 0; a<zeitraum; a++){
                double newValues = erwartungswert + randomWert.nextGaussian()*standardabweichung;
                xWertZufall.add(newValues);
                yWertZufall.add(lr.getSteigung()*xWertZufall.get(a) + lr.getyAchsenabschnitt());
            }

            portfolio = betrag;
            for (int a = 0; a<yWertZufall.size();a++){
                portfolioVerlauf[a] = portfolio;
                portfolio *= 1+yWertZufall.get(a);
            }

            portfolioSumme += portfolio;

            for (int x = 0; x < zeitraum; x++) {
                plotter.add(x, portfolioVerlauf[x]);
                plotter.setDataColor(grafikFarben[farbe%10]);
                plotter.setXLabelFormat("%.0f");
                plotter.setYLabelFormat("%.0f");
                plotter.setAutoXgrid(zeitraum/10);
                plotter.setAutoYgrid(betrag/10);
            }
            plotter.nextDataSet();
            farbe++;

        }
        return portfolioSumme/iterationen;
    }

    /**
     * Berechnet den Erwartungswert der Rendite basierend auf der linearen Regression.
     * @param lr Die lineare Regression, für die der Erwartungswert berechnet werden soll.
     * @return Der Erwartungswert der Rendite.
     */
    private double ErwartungswertRendite(LinearRegression lr) {
        int anzahlPunkte = lr.getAnzahlderPunkte();
        double summe = 1.0;

        for (int i = 0; i<anzahlPunkte; i++){
            summe *= 1 + lr.getXWert(i);
        }
        double erwartungswert = Math.pow(summe,1/(anzahlPunkte*1.0))-1 ;
        return erwartungswert;
    }

    /**
     * Berechnet die Standardabweichung der Rendite basierend auf der linearen Regression.
     * @param lr Die lineare Regression, für die die Standardabweichung berechnet werden soll.
     * @return Die Standardabweichung der Rendite.
     */
    private double StandardabweichungRendite(LinearRegression lr) {
        int anzahlPunkte = lr.getAnzahlderPunkte();
        double residuen = 0.0;
        double quadratsumme =0.0;
        for (int i = 0; i<anzahlPunkte; i++){
            residuen = Math.pow(lr.getXWert(i)-ErwartungswertRendite(lr),2);
            quadratsumme =quadratsumme + residuen;
        }
        double standardabweichung = Math.sqrt(quadratsumme/anzahlPunkte);
        return standardabweichung;
    }

}
