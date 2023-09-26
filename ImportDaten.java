/**
 * @author Viet Quang Dang
 * @author Nhu Phuong Nguyen
 * @author The Trung Do
 * @version
 */
package MainProjekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Diese Klasse ermöglicht das Importieren von Daten aus einer CSV-Datei und das Hinzufügen dieser Daten zu einer LinearRegression-Instanz.
 * Sie ermöglicht das Auswählen von zwei Spalten aus der CSV-Datei, um die x- und y-Werte für die lineare Regression zu extrahieren und hinzuzufügen.
 */
public class ImportDaten {

    private String pfad;

    private int spalte1;

    private int spalte2;

    /**
     * Konstruktor für die ImportDaten-Klasse.
     * @param pfad Der Pfad zur CSV-Datei, aus der die Daten importiert werden sollen.
     * @param spalte1 Die Nummer der Spalte, die als x-Werte verwendet werden soll.
     * @param spalte2 Die Nummer der Spalte, die als y-Werte verwendet werden soll.
     */
    public ImportDaten(String pfad, int spalte1, int spalte2){
        this.pfad = pfad;
        this.spalte1 = spalte1;
        this.spalte2 = spalte2;
    }

    /**
     * Fügt Daten aus der CSV-Datei zu einer LinearRegression-Instanz hinzu.
     * Die Methode liest die CSV-Datei Zeile für Zeile und extrahiert die x- und y-Werte aus den ausgewählten Spalten.
     * Die extrahierten Daten werden der LinearRegression-Instanz hinzugefügt.
     * @param lr Die LinearRegression-Instanz, zu der die Daten hinzugefügt werden sollen.
     */
    public void AddDatenAusCSV(LinearRegression lr) {
        String zeile = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(pfad));
            br.readLine();  // Überspringen die Titelzeile

            while ((zeile = br.readLine()) != null) {
                String[] values = zeile.split(",");
                lr.AddPunkte(Double.parseDouble(values[spalte1 -1]),Double.parseDouble(values[spalte2 -1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
