package Monte_Carlo_Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
// Comment from Trung
    private JTextField xField, yField;
    private JButton addButton, clearButton, calculateButton;
    private JTextArea pointList, result;
    private LinearRegression lr;
    private JFrame frame;

    public GUI() {

        lr = new LinearRegression();

        frame = new JFrame();
        frame.setTitle("Lineare Regression");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(new BorderLayout());

        JLabel xLabel = new JLabel("X-Wert:");
        xLabel.setFont(new Font("Abadi",Font.BOLD,12));
        xLabel.setHorizontalAlignment(JLabel.CENTER);
        xField = new JTextField(10);
        JLabel yLabel = new JLabel("Y-Wert:");
        yLabel.setFont(new Font("Abadi",Font.BOLD,12));
        yLabel.setHorizontalAlignment(JLabel.CENTER);
        yField = new JTextField(10);

        addButton = new JButton("Hinzufügen");
        clearButton = new JButton("Leeren");
        calculateButton = new JButton("Berechnen");
        calculateButton.setBackground(Color.PINK);

        pointList = new JTextArea(15, 20);
        pointList.setEditable(false);
        pointList.setAlignmentX(10);
        JScrollPane scrollPane1 = new JScrollPane(pointList);
        result = new JTextArea(10, 20);
        result.setEditable(false);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(xLabel);
        inputPanel.add(xField);
        inputPanel.add(yLabel);
        inputPanel.add(yField);

        JPanel buttonPanel = new JPanel(new GridLayout(1,3));
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(calculateButton);


        JPanel outputPanel = new JPanel(new GridLayout(2, 1));
        outputPanel.add(scrollPane1);
        outputPanel.add(result);

        inputPanel.setPreferredSize(new Dimension(80,80));
        buttonPanel.setPreferredSize(new Dimension(100,100));
        outputPanel.setPreferredSize(new Dimension(325,325));

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.SOUTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);

        addButton.addActionListener(this);
        clearButton.addActionListener(this);
        calculateButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            double xValue = Double.parseDouble(xField.getText());
            double yValue = Double.parseDouble(yField.getText());
            lr.addDataPoint(xValue, yValue);
            pointList.append(xValue + ", " + yValue + "\n");
            xField.setText("");
            yField.setText("");
        } else if (e.getSource() == clearButton) {
            lr.clearDataPoints();
            pointList.setText("");
            result.setText("");
        } else if (e.getSource() == calculateButton) {
            lr.calculateCoefficients();
            result.setText(String.format("Regressionsgleichung: y = %.2fx + %.2f", lr.getSlope(), lr.getIntercept()) + "\n" +"RSquared: " + lr.calculateRSquared());
            lr.drawRegressionLine();
        }
    }
}
