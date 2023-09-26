/**
 * @author Viet Quang Dang
 * @author Nhu Phuong Nguyen
 * @author The Trung Do
 * @version
 */
package MainProjekt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Diese Klasse enthält JUnit-Testmethoden für die verschiedenen Klassen des Projekts.
 */
class JUnitTest {

    /**
     * Testet die Methode `BerechnenKoeffizienten` der Klasse `LinearRegression`.
     * Es wird überprüft, ob die Steigung und der y-Achsenabschnitt korrekt berechnet werden.
     */
    @Test
    public void testBerechnenKoeffizient() {
        // Testdaten erstellen
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);

        // Koeffizienten berechnen
        regression.BerechnenKoeffizienten();

        // Erwartete Ergebnisse vergleichen
        assertEquals(2.0, regression.getSteigung(), 0.001);
        assertEquals(0.0, regression.getyAchsenabschnitt(), 0.001);
    }

    /**
     * Testet die Methode `BerechnenRSquared` der Klasse `LinearRegression`.
     * Es wird überprüft, ob der RSquared-Wert korrekt berechnet wird.
     */
    @Test
    public void testRSquaredBerechnen() {
        // Testdaten erstellen
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);

        // Koeffizienten berechnen
        regression.BerechnenKoeffizienten();

        // RSquared-Wert berechnen
        double rSquared = regression.BerechnenRSquared();
        assertEquals(1.0, rSquared, 0.001);
    }

    /**
     * Testet die Methode `BerechnenRangkorrelation` der Klasse `Korrelation`.
     * Es wird überprüft, ob die Rangkorrelation korrekt berechnet wird.
     */
    @Test
    public void testBerechnenRangkorrelation() {
        // Testdaten erstellen
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        Korrelation korrelation = new Korrelation();

        // Rangkorrelation berechnen
        double rangKorrelation = korrelation.BerechnenRangkorrelation(regression);

        // Erwartete Ergebnisse vergleichen
        assertEquals(1.0, rangKorrelation, 0.001);
    }

    /**
     * Testet die Methode `BerechnenPearsonKorrelation` der Klasse `Korrelation`.
     * Es wird überprüft, ob die Pearson-Korrelation korrekt berechnet wird.
     */
    @Test
    public void testBerechnenPearsonKorrelation() {
        // Testdaten erstellen
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        Korrelation korrelation = new Korrelation();

        // Pearson-Korrelation berechnen
        double pearsonKorrelation = korrelation.BerechnenPearsonKorrelation(regression);

        // Erwartete Ergebnisse vergleiche
        assertEquals(1.0, pearsonKorrelation, 0.001);
    }

    /**
     * Testet die Methode zum Werfen eines Würfels in der Klasse MonteCarloSimulationWurfel.
     */
    @Test
    public void testWurfeln() {
        int anzahlderWuerfe = 100000;
        MonteCarloSimulationWurfel simulation = new MonteCarloSimulationWurfel(anzahlderWuerfe);
        int[] ergebnis = simulation.Wurfeln();

        // Überprüfen, ob die Anzahl der Würfe korrekt ist
        assertEquals(anzahlderWuerfe, simulation.getAnzahlWuerfe());

        // Überprüfen, ob die Ergebnisse im erwarteten Bereich liegen (2 bis 12)
        for (int i = 2; i <= 12; i++) {
            assertTrue(ergebnis[i - 2] >= 0);
        }
    }

    /**
     * Testet die Methode zur Berechnung der Verteilung der Ergebnisse in der Klasse MonteCarloSimulationWurfel.
     */
    @Test
    public void testErgebnisVerteilung() {
        int anzahlderWuerfe = 100000;
        MonteCarloSimulationWurfel simulation = new MonteCarloSimulationWurfel(anzahlderWuerfe);
        simulation.Wurfeln();
        double[] haufigkeit = simulation.ErgebnisVerteilung();

        // Überprüfen, ob die Haufigkeiten zwischen 0 und 1 liegen
        for (double value : haufigkeit) {
            assertTrue(value >= 0.0 && value <= 1.0);
        }
    }
}