package Monte_Carlo_Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    private JTextField xFeld, yFeld;
    private JButton addButton, loschenButton, berechnenButton, addDatenAusCSVButton, MonteCarloButton, YRandomButton;
    private JTextArea punktenListe, ergebnis;
    private LinearRegression lr;

    private Importdaten importdaten;
    private MonteCarloSimulation mcl;
    private JFrame rahmen;

    public GUI() {

        lr = new LinearRegression();
        mcl = new MonteCarloSimulation();

        rahmen = new JFrame();
        rahmen.setTitle("Lineare Regression");
        rahmen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rahmen.setSize(500,500);
        rahmen.setLayout(new BorderLayout());

        JLabel xLabel = new JLabel("X-Wert:");
        xLabel.setFont(new Font("Abadi",Font.BOLD,12));
        xLabel.setHorizontalAlignment(JLabel.CENTER);
        xFeld = new JTextField();
        JLabel yLabel = new JLabel("Y-Wert:");
        yLabel.setFont(new Font("Abadi",Font.BOLD,12));
        yLabel.setHorizontalAlignment(JLabel.CENTER);
        yFeld = new JTextField();

        addButton = new JButton("Hinzufügen");
        loschenButton = new JButton("Leeren");
        berechnenButton = new JButton("Berechnen");
        berechnenButton.setBackground(Color.PINK);
        addDatenAusCSVButton = new JButton("CSV Daten");
        YRandomButton =new JButton("Random Y Werte");
        MonteCarloButton = new JButton("Monte Carlo Simulation");


        punktenListe = new JTextArea(15, 20);
        punktenListe.setEditable(false);
        punktenListe.setAlignmentX(10);
        JScrollPane scrollPane1 = new JScrollPane(punktenListe);
        ergebnis = new JTextArea(10, 20);
        ergebnis.setEditable(false);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(xLabel);
        inputPanel.add(xFeld);
        inputPanel.add(yLabel);
        inputPanel.add(yFeld);

        JPanel buttonPanel = new JPanel(new GridLayout(2,3));
        buttonPanel.add(addButton);
        buttonPanel.add(loschenButton);
        buttonPanel.add(berechnenButton);
        buttonPanel.add(addDatenAusCSVButton);
        buttonPanel.add(YRandomButton);
        buttonPanel.add(MonteCarloButton);


        JPanel outputPanel = new JPanel(new GridLayout(2, 1));
        outputPanel.add(scrollPane1);
        outputPanel.add(ergebnis);

        inputPanel.setPreferredSize(new Dimension(80,80));
        buttonPanel.setPreferredSize(new Dimension(100,100));
        outputPanel.setPreferredSize(new Dimension(325,325));

        rahmen.add(inputPanel, BorderLayout.NORTH);
        rahmen.add(outputPanel, BorderLayout.SOUTH);
        rahmen.add(buttonPanel, BorderLayout.CENTER);

        rahmen.setVisible(true);

        addButton.addActionListener(this);
        loschenButton.addActionListener(this);
        berechnenButton.addActionListener(this);
        addDatenAusCSVButton.addActionListener(this);
        YRandomButton.addActionListener(this);
        MonteCarloButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addDatenAusCSVButton){
            String pfad = JOptionPane.showInputDialog("Geben Sie bitte den Pfad der CSV-Datei ein:");
            String inputWert1 = JOptionPane.showInputDialog("Wählen Sie bitte die erste Spalte aus:");
            String inputWert2 = JOptionPane.showInputDialog("Wählen Sie bitte die zweite Spalte aus:");
            int spalte1 = Integer.parseInt(inputWert1);
            int spalte2 = Integer.parseInt(inputWert2);

            importdaten = new Importdaten(pfad.replace("\\", "/"), spalte1, spalte2);
            importdaten.AddDatenAusCSV(lr);


            //lr.AddDatenAusCSV(path.replace("\\", "/"),colNum1,colNum2);

            for (int i = 0; i<lr.getAnzahlderPunkte(); i++){
                punktenListe.append(lr.getXWert(i) + ", " + lr.getYWert(i) + "\n");
            }
        } else if (event.getSource() == YRandomButton) {
            String inputWert = JOptionPane.showInputDialog("Geben Sie bitte die Standardabweichung ein:");
            double standardabweichung = Double.parseDouble(inputWert);
            mcl.YRandomErstellen(lr,standardabweichung);
            punktenListe.setText("");
            ergebnis.setText("");
            for (int i = 0; i<lr.getAnzahlderPunkte(); i++){
                punktenListe.append(lr.getXWert(i) + ", " + lr.getYWert(i) + "\n");
            }
        } else if (event.getSource()==MonteCarloButton) {
            String inputWert1 = JOptionPane.showInputDialog("Geben Sie bitte die Standardabweichung:");
            String inputWert2 = JOptionPane.showInputDialog("Wie viele Iterationen sollen durchgeführt werden?");
            String[] optionen = { "RSquared", "Rendite" };
            var inputWert3 = JOptionPane.showOptionDialog(null, "Wählen Sie bitte eine Simualation aus", "Monte-Carlo-Simulation",0,3,null, optionen, optionen[0]);
            double standardabweichung = Double.parseDouble(inputWert1);
            int iterationen = Integer.parseInt(inputWert2);

            if (inputWert3 ==0) {
                double RSquaredMittelwert = mcl.MCL_RSquared(lr,standardabweichung, iterationen)[0];
                double SteigungMittelwert = mcl.MCL_RSquared(lr,standardabweichung, iterationen)[1];
                ergebnis.setText("Mittelwert von RSquared: " + RSquaredMittelwert + "\n" + "Mittelwert von Steigungen: " + SteigungMittelwert);
                punktenListe.setText("");
            } /*else {
                result.setText("Slope_Konfidenzintervall: (" + lr.MCL_Steigung_Konfidenzintervall(standardabweichung,iterations)[0] + ", " + lr.MCL_Steigung_Konfidenzintervall(standardabweichung,iterations)[1] + ")");
            }*/
        } else if (event.getSource() == addButton) {
            double xWert = Double.parseDouble(xFeld.getText());
            double yWert = Double.parseDouble(yFeld.getText());
            lr.AddPunkte(xWert, yWert);
            punktenListe.append(xWert + ", " + yWert + "\n");
            xFeld.setText("");
            yFeld.setText("");
        } else if (event.getSource() == loschenButton) {
            lr.PunkteLoeschen();
            punktenListe.setText("");
            ergebnis.setText("");
        } else if (event.getSource() == berechnenButton) {
            lr.KoeffizientenBerechnung();
            ergebnis.setText(String.format("Regressionsgleichung: y = %.2fx + %.2f", lr.getSteigung(), lr.getyAchsenabschnitt()) + "\n" +"RSquared: " + lr.RSquaredBerechnen());
            lr.regressiongeradeZeichnen();
        }
    }
}
