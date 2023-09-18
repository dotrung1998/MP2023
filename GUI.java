package Monte_Carlo_Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    private JTextField xFeld, yFeld;
    private JButton addButton, loschenButton, berechnenButton, addDatenAusCSVButton, AnalyseButton, MCLWürfelButton;
    private JTextArea punktenListe, ergebnisBereich;
    private LinearRegression lr;

    private ImportDaten importdaten;
    private JFrame rahmen;

    public GUI() {

        lr = new LinearRegression();

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
        AnalyseButton = new JButton("Analysieren");
        MCLWürfelButton = new JButton("Monte Carlo Simulation");

        punktenListe = new JTextArea(15, 20);
        punktenListe.setEditable(false);
        punktenListe.setAlignmentX(10);
        JScrollPane scrollPane1 = new JScrollPane(punktenListe);
        ergebnisBereich = new JTextArea(10, 20);
        ergebnisBereich.setEditable(false);

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
        buttonPanel.add(AnalyseButton);
        buttonPanel.add(MCLWürfelButton);

        JPanel outputPanel = new JPanel(new GridLayout(2, 1));
        outputPanel.add(scrollPane1);
        outputPanel.add(ergebnisBereich);

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
        AnalyseButton.addActionListener(this);
        MCLWürfelButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addDatenAusCSVButton) {
            String pfad = JOptionPane.showInputDialog("Geben Sie bitte den Pfad der CSV-Datei ein:");
            String inputWert1 = JOptionPane.showInputDialog("Wählen Sie bitte die erste Spalte aus:");
            String inputWert2 = JOptionPane.showInputDialog("Wählen Sie bitte die zweite Spalte aus:");
            int spalte1 = Integer.parseInt(inputWert1);
            int spalte2 = Integer.parseInt(inputWert2);

            importdaten = new ImportDaten(pfad.replace("\\", "/"), spalte1, spalte2);
            importdaten.AddDatenAusCSV(lr);

            //lr.AddDatenAusCSV(path.replace("\\", "/"),colNum1,colNum2);
            punktenListe.append("Daten aus CSV:" + "\n");
            for (int i = 0; i < lr.getAnzahlderPunkte(); i++) {
                punktenListe.append(lr.getXWert(i) + ", " + lr.getYWert(i) + "\n");
            }

        /*} else if (event.getSource() == BerechnenMitRankButton) {
            double rankKorrelation = lr.RangkorrelationBerechnen();
            punktenListe.append("\n" + "Ränge von Daten:" + "\n" );
            /*punktenListe.setText("Ränge von Daten:" + "\n");*/
            /*for (int i = 0; i < lr.getAnzahlderPunkte(); i++) {
                punktenListe.append(lr.getXRank(i) + ", " + lr.getYRank(i) + "\n");
            }

            lr.KoeffizientenRankBerechnung();
            ergebnis.setText(String.format("Regressionsgleichung: y = %.2fx + %.2f", lr.getSteigungRank(), lr.getyAchsenabschnittRank()) + "\n" +"Spearman's Rank Korrelation: " + rankKorrelation);
            //Punkte neu zuordnen und Grafik zeichnen
            lr.regressiongeradeRankZeichnen();*/

            //////////////////////////////////////////////////////////////////////////
        } else if (event.getSource() == AnalyseButton) {
            String[] optionen = { "RSquared", "Rangkorrelation", "MonteCarloSimulation" };
            var inputWert = JOptionPane.showOptionDialog(null, "Wähl eine Option zur Analyse", "Analysieren",0,3,null,optionen,optionen[0]);

            if (inputWert==0){
                ergebnisBereich.append("\n" + String.format("RSquared: %.6f", lr.RSquaredBerechnen()));
            } else if (inputWert==1) {
                //// Rangkorrelation /////
                double rankKorrelation = lr.RangkorrelationBerechnen();
                punktenListe.append("\n" + "Ränge von Daten:" + "\n" );
                //punktenListe.setText("Ränge von Daten:" + "\n");
                for (int i = 0; i < lr.getAnzahlderPunkte(); i++) {
                    punktenListe.append(lr.getXRank(i) + ", " + lr.getYRank(i) + "\n");
                }
                lr.KoeffizientenRankBerechnung();
                ergebnisBereich.setText(String.format("Regressionsgleichung mit Rank: y = %.2fx + %.2f", lr.getSteigungRank(), lr.getyAchsenabschnittRank()) + "\n" +"Spearman's Rank Korrelation: " + rankKorrelation);
                //Punkte neu zuordnen und Grafik zeichnen
                lr.regressiongeradeZeichnen(true);
            } else {
                String inputWert1 = JOptionPane.showInputDialog("Wie viele Iterationen sollen durchgeführt werden?");
                String inputWert2 = JOptionPane.showInputDialog("Investitionsbetrag:");
                String inputWert3 = JOptionPane.showInputDialog("Wie lange soll der Betrag investiert werden? (in Tagen)");

                int iterationen = Integer.parseInt(inputWert1);
                double betrag = Double.parseDouble(inputWert2);
                int investitionZeitraum = Integer.parseInt(inputWert3);

                MonteCarloSimulationRendite mcl_rendite = new MonteCarloSimulationRendite();
                lr.KoeffizientenBerechnung();
                ergebnisBereich.append("\n" + String.format("Portfolio in %d Tagen: %.6f" ,investitionZeitraum,mcl_rendite.PortfolioswertBerechnen(lr,iterationen,betrag,investitionZeitraum)));
            }
        } else if (event.getSource()==MCLWürfelButton) {
            String inputWert = JOptionPane.showInputDialog("Wie oft sollte dieser Würfel geworfen werden?");
            int anzahlWurfe = Integer.parseInt(inputWert);
            MonteCarloSimulationWurfel mcl_wuerfel  = new MonteCarloSimulationWurfel(anzahlWurfe);
            int[] result = mcl_wuerfel.wurfeln();
            double[] haufigkeit = mcl_wuerfel.ergebnisVerteilung();

            punktenListe.setText("");
            for (int i = 0; i<11; i++) {
                punktenListe.append(String.format("Summe %d entstand %d Mal -> Häufigkeit: %.3f", i + 2, result[i],haufigkeit[i]) + "\n");
            }
            mcl_wuerfel.ergebnisDarstellen();

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
            ergebnisBereich.setText("");

        } else if (event.getSource() == berechnenButton) {
            lr.KoeffizientenBerechnung();
            ergebnisBereich.setText(String.format("Regressionsgleichung: y = %.2fx + %.2f", lr.getSteigung(), lr.getyAchsenabschnitt()));
            lr.regressiongeradeZeichnen(false);
        }
    }
}
