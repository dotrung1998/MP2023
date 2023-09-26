package MainProjekt;

import plotter.Graphic;
import plotter.LineStyle;
import plotter.Plotter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Korrelation {
    private ArrayList<Integer> xRang;
    private ArrayList<Integer> yRang;

    public int getXRang(int i){ return xRang.get(i); }

    public int getYRang(int i){ return yRang.get(i); }

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
