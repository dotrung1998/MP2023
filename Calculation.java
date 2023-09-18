package MainPorjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ZielloserSpaziergang {
    private int x;
    private int y;

    public ZielloserSpaziergang(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        Random rand = new Random();
        int direction = rand.nextInt(4);

        switch(direction) {
            case 0:
                x += 1;
                break;
            case 1:
                x -= 1;
                break;
            case 2:
                y += 1;
                break;
            case 3:
                y -= 1;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class BerechnenLinearRegression {
    private List<Double> xList;
    private List<Double> yList;

    public BerechnenLinearRegression(List<Double> xList, List<Double> yList) {
        this.xList = xList;
        this.yList = yList;
    }

    public double[] Rechnen() {
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXY = 0.0;
        double sumXX = 0.0;
        int n = xList.size();

        for (int i = 0; i < n; i++) {
            double x = xList.get(i);
            double y = yList.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumXX += x * x;
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;
        double[] result = {slope, intercept};
        return result;
    }
}


class Main {
    public static void main(String[] args) {
        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();
        ZielloserSpaziergang z = new ZielloserSpaziergang(0, 0);

        for (int i = 0; i < 1000; i++) {
            z.move();
            xList.add((double) z.getX());
            yList.add((double) z.getY());
        }

        BerechnenLinearRegression lr = new BerechnenLinearRegression(xList, yList);
        double[] result = lr.Rechnen();
        System.out.println("Slope: " + result[0]);
        System.out.println("Intercept: " + result[1]);
    }
}
