package Monte_Carlo_Simulation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinearRegressionTest {
    @Test
    public void testKoeffizientenBerechnung() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        regression.KoeffizientenBerechnung();
        assertEquals(2.0, regression.getSteigung(), 0.001);
        assertEquals(0.0, regression.getyAchsenabschnitt(), 0.001);
    }

    @Test
    public void testRSquaredBerechnen() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        regression.KoeffizientenBerechnung();
        double rSquared = regression.RSquaredBerechnen();
        assertEquals(1.0, rSquared, 0.001);
    }

    @Test
    public void RangkorrelationBerechnen() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        double rankCorrelation = regression.RangkorrelationBerechnen();
        assertEquals(1.0, rankCorrelation, 0.001);
    }

/*    @Test
    public void testCalculatePearsonCorrelation() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        double pearsonCorrelation = regression.calculatePearsonCorrelation();
        assertEquals(1.0, pearsonCorrelation, 0.001);
    }

    @Test
    public void testAddDataPointFromCSV() {
        LinearRegression regression = new LinearRegression();
        regression.addDatenAusCSVButton("test_data.csv", 1, 2);
        assertEquals(3, regression.getAnzahlderPunkte());
        assertEquals(1.0, regression.getXWert(0), 0.001);
        assertEquals(2.0, regression.getYWert(0), 0.001);
    }

/*    @Test
    public void testMCL_RSquared() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        double[] result = regression.MCL_RSquared(0.1, 100);
        assertEquals(1.0, result[0], 0.1);
    }

   @Test
    public void testMCL_Steigung_Konfidenzintervall() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        double[] result = regression.MCL_Steigung_Konfidenzintervall(0.1, 100);
        assertEquals(2.0, result[0], 0.1);
*/
}