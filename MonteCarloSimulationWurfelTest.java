package MainPorjekt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    public class MonteCarloSimulationWurfelTest {

        @Test
        public void testWurfeln() {
            int anzahlderWuerfe = 100000;
            MonteCarloSimulationWurfel simulation = new MonteCarloSimulationWurfel(anzahlderWuerfe);
            int[] ergebnis = simulation.wurfeln();

            // Überprüfen, ob die Anzahl der Würfe korrekt ist
            assertEquals(anzahlderWuerfe, simulation.getAnzahlderWuerfe());

            // Überprüfen, ob die Ergebnisse im erwarteten Bereich liegen (2 bis 12)
            for (int i = 2; i <= 12; i++) {
                assertTrue(ergebnis[i - 2] >= 0);
            }
        }

        @Test
        public void testErgebnisVerteilung() {
            int anzahlderWuerfe = 100000;
            MonteCarloSimulationWurfel simulation = new MonteCarloSimulationWurfel(anzahlderWuerfe);
            simulation.wurfeln();
            double[] haufigkeit = simulation.ergebnisVerteilung();

            // Überprüfen, ob die Haufigkeiten zwischen 0 und 1 liegen
            for (double value : haufigkeit) {
                assertTrue(value >= 0.0 && value <= 1.0);
            }
        }
    }