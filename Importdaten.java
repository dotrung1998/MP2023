package MainProjekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Importdaten {

    private String pfad;

    private int spalte1;

    private int spalte2;

    public Importdaten(String pfad, int spalte1, int spalte2){
        this.pfad = pfad;
        this.spalte1 = spalte1;
        this.spalte2 = spalte2;
    }

    public void AddDatenAusCSV(LinearRegression linearRegression) {
        String zeile = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(pfad));
            br.readLine();

            while ((zeile = br.readLine()) != null) {
                String[] values = zeile.split(",");
                linearRegression.AddPunkte(Double.parseDouble(values[spalte1 -1]),Double.parseDouble(values[spalte2 -1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
