package MainPorjekt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinearRegressionTest {
    @Test
    public void testKoeffizientenBerechnung() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        assertEquals(2.0, regression.getSteigung(), 0.001);
        assertEquals(0.0, regression.getyAchsenabschnitt(), 0.001);
    }

    @Test
    public void testRSquaredBerechnen() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        regression.BerechnenRegressionskoeffizienten(false);
        double rSquared = regression.RSquaredBerechnen();
        assertEquals(1.0, rSquared, 0.001);
    }

    @Test
    public void RangkorrelationBerechnen() {
        LinearRegression regression = new LinearRegression();
        regression.AddPunkte(1.0, 2.0);
        regression.AddPunkte(2.0, 4.0);
        regression.AddPunkte(3.0, 6.0);
        double rangCorrelation = regression.RangkorrelationBerechnen();
        assertEquals(1.0, rangCorrelation, 0.001);
    }
}