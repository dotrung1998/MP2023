package MainProjekt;

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
    public ArrayList<Double> getxWert(){ return this.xWert; }
    public ArrayList<Double> getyWert(){ return this.yWert; }

    public double getXWert(int i){
        return xWert.get(i);
    }

    public double getYWert(int i){
        return yWert.get(i);
    }

    public int getAnzahlderPunkte(){
        return xWert.size();
    }
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
        this.steigung = (xySumme - anzahlPunkte * xMittelwert * yMittelwert) / (xWertQuadratSumme - anzahlPunkte * xMittelwert*xMittelwert);
        this.yAchsenabschnitt = yMittelwert - steigung * xMittelwert;
    }

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