package MainProjekt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTest {

    // Test Klasse LinearRegression
    @Test
    public void testBerechnenKoeffizient() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        regression.BerechnenKoeffizienten();
        assertEquals(2.0, regression.getSteigung(), 0.001);
        assertEquals(0.0, regression.getyAchsenabschnitt(), 0.001);
    }

    @Test
    public void testRSquaredBerechnen() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        regression.BerechnenKoeffizienten();
        double rSquared = regression.BerechnenRSquared();
        assertEquals(1.0, rSquared, 0.001);
    }

    // Test klasse Korrelation
    @Test
    public void testBerechnenRangkorrelation() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        Korrelation korrelation = new Korrelation();
        double rangKorrelation = korrelation.BerechnenRangkorrelation(regression);
        assertEquals(1.0, rangKorrelation, 0.001);
    }

    @Test
    public void testBerechnenPearsonKorrelation() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        Korrelation korrelation = new Korrelation();
        double pearsonKorrelation = korrelation.BerechnenPearsonKorrelation(regression);
        assertEquals(1.0, pearsonKorrelation, 0.001);
    }

    // Test Klasse MonteCarloSimulationWurfel
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