/**
 * @author Viet Quang Dang
 * @author Nhu Phuong Nguyen
 * @author The Trung Do
 * @version
 */
package MainProjekt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Diese Klasse stellt eine grafische Benutzeroberfläche (GUI) für die Lineare Regression und Monte-Carlo-Simulationen dar.
 * Sie ermöglicht das Hinzufügen von Datenpunkten, das Berechnen der linearen Regression, das Importieren von Daten aus einer CSV-Datei
 * sowie verschiedene Analysen und Monte-Carlo-Simulationen.
 */
public class GUI extends JFrame implements ActionListener {

    private JTextField xFeld, yFeld;
    private JButton addButton, loschenButton, berechnenButton, addDatenAusCSVButton, analyseButton, mclWuerfelButton;
    private JTextArea punktenListe, ergebnisBereich;
    private LinearRegression lr;
    private ImportDaten importdaten;
    private JFrame rahmen;

    /**
     * Konstruktor für die GUI-Klasse.
     * Initialisiert die GUI-Komponenten und die erforderlichen Listener.
     */
    public GUI() {

        lr = new LinearRegression();

        rahmen = new JFrame();
        rahmen.setTitle("Lineare Regression");
        rahmen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rahmen.setSize(550,550);
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
        analyseButton =new JButton("Analysieren");
        mclWuerfelButton = new JButton("Monte Carlo Simulation");

        punktenListe = new JTextArea();
        punktenListe.setEditable(false);
        punktenListe.setAlignmentX(10);
        JScrollPane scrollPane1 = new JScrollPane(punktenListe);
        ergebnisBereich = new JTextArea();

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
        buttonPanel.add(analyseButton);
        buttonPanel.add(mclWuerfelButton);


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
        analyseButton.addActionListener(this);
        mclWuerfelButton.addActionListener(this);
    }

    /**
     * Diese Methode wird aufgerufen, wenn ein GUI-Element (z.B. Button) angeklickt wird.
     * Sie behandelt die entsprechenden Aktionen, je nachdem, welches Element ausgelöst wurde.
     * @param event Das ausgelöste ActionEvent.
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addButton) {
            // Hinzufügung und Anzeigen der Datenpunkte in der GUI, wenn X-Wert und Y-Wert nicht leer sind
            String xEingabe = xFeld.getText().trim();
            String yEingabe = yFeld.getText().trim();

            if (!xEingabe.isEmpty() && !yEingabe.isEmpty()) {
                try {
                    double xWert = Double.parseDouble(xEingabe);
                    double yWert = Double.parseDouble(yEingabe);
                    lr.AddPunkte(xWert, yWert);
                    punktenListe.append(xWert + ", " + yWert + "\n");
                    xFeld.setText("");
                    yFeld.setText("");
                } catch (NumberFormatException e) {
                    // Wenn keine Zahl eingegeben wurde, wird eine Fehlermeldung angezeigt
                    JOptionPane.showMessageDialog(null, "Sie haben keine gültige Zahl eingegeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                // Wenn X-Wert oder Y-Wert leer sind, wird ein Hinweis angezeigt
                JOptionPane.showMessageDialog(null, "X-Wert und Y-Wert dürfen nicht leer sein.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        else if (event.getSource() == loschenButton) {
            // Löschung aller Daten
            lr.LoeschenPunkte();
            punktenListe.setText("");
            ergebnisBereich.setText("");
        }

        else if (event.getSource() == berechnenButton) {
            if (lr.getAnzahlderPunkte() == 0) {
                // Der Benutzer wurde aufgefordert, X- und Y-Werte einzugeben oder Daten aus einer CSV-Datei zu importieren.
                JOptionPane.showMessageDialog(null, "Sie müssen zuerst X- und Y-Werte eingeben oder Daten aus einer CSV-Datei importieren.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Berechnung der Regressionskoeffizienten und Zeichnung der Regressionsgerade
                lr.BerechnenKoeffizienten();
                ergebnisBereich.setText(String.format("Regressionsgleichung: y = %.6fx + %.6f", lr.getSteigung(), lr.getyAchsenabschnitt()));
                lr.ZeichnenRegressiongerade();
            }
        }

        else if (event.getSource() == addDatenAusCSVButton) {
            String pfad = "";
            int spalte1 = -1;
            int spalte2 = -1;

            while (true) {
                pfad = JOptionPane.showInputDialog("Geben Sie bitte den Pfad der CSV-Datei ein (z.B. C:\\Benutzer\\Nutzername\\Dokumente\\MeinCSVFile.csv):");
                if (pfad == null) {
                    // Der Benutzer hat Abbrechen oder das Schließen-Symbol gewählt, daher wird nichts unternommen
                    return;
                }

                if (!pfad.isEmpty()) {
                    // Überprüfen, ob der Pfad eine CSV-Datei ist und ob sie existiert
                    File csvDatei = new File(pfad);
                    if (csvDatei.exists() && csvDatei.isFile() && pfad.toLowerCase().endsWith(".csv")) {
                        break; // Die Schleife verlassen, wenn der Pfad gültig ist
                    } else {
                        // Wenn der Pfad keine gültige CSV-Datei ist oder nicht existiert, eine Fehlermeldung anzeigen
                        JOptionPane.showMessageDialog(null, "Ungültiger Pfad zur CSV-Datei. Stellen Sie sicher, dass die Datei existiert und die Erweiterung '.csv' hat.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Wenn der Benutzer leere Eingabe macht, wird eine Benachrichtigung angezeigt
                    JOptionPane.showMessageDialog(null, "Sie müssen einen gültigen Pfad zur CSV-Datei eingeben.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            while (true) {
                String inputWert1 = JOptionPane.showInputDialog("Wählen Sie bitte die erste Spalte aus (Zahlen z.B. 1):");
                if (inputWert1 == null) {
                    // Der Benutzer hat Abbrechen oder das Schließen-Symbol gewählt, daher wird nichts unternommen
                    return;
                }
                try {
                    spalte1 = Integer.parseInt(inputWert1);
                    break; // Die Schleife verlassen, wenn eine gültige Zahl eingegeben wurde
                } catch (NumberFormatException e) {
                    // Fehlermeldung anzeigen, wenn keine gültige Zahl eingegeben wurde
                    JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine gültige Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }

            while (true) {
                String inputWert2 = JOptionPane.showInputDialog("Wählen Sie bitte die zweite Spalte aus (Zahlen z.B. 2):");
                if (inputWert2 == null) {
                    // Der Benutzer hat Abbrechen oder das Schließen-Symbol gewählt, daher wird nichts unternommen
                    return;
                }
                try {
                    spalte2 = Integer.parseInt(inputWert2);
                    break; // Die Schleife verlassen, wenn eine gültige Zahl eingegeben wurde
                } catch (NumberFormatException e) {
                    // Fehlermeldung anzeigen, wenn keine gültige Zahl eingegeben wurde
                    JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine gültige Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }

            importdaten = new ImportDaten(pfad.replace("\\", "/"), spalte1, spalte2);
            importdaten.AddDatenAusCSV(lr);

            // Anzeigen der Datenpunkte in der GUI
            punktenListe.append("Daten aus CSV:" + "\n");
            for (int i = 0; i < lr.getAnzahlderPunkte(); i++) {
                punktenListe.append(String.format("%.6f, %.6f", lr.getXWert(i), lr.getYWert(i)) + "\n");
            }
        }

        else if (event.getSource() == analyseButton) {
            if (lr.getAnzahlderPunkte() == 0) {
                // Der Benutzer wurde aufgefordert, X- und Y-Werte einzugeben oder Daten aus einer CSV-Datei zu importieren.
                JOptionPane.showMessageDialog(null, "Sie müssen zuerst X- und Y-Werte eingeben oder Daten aus einer CSV-Datei importieren.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Hier können Sie die Analyse durchführen, da gültige Daten vorhanden sind

                String[] optionen = { "RSquared", "Korrelation", "Renditevorhersage" };
                int inputWert = JOptionPane.showOptionDialog(null, "Wählen Sie eine Option zur Analyse", "Analysieren", 0, 3, null, optionen, optionen[0]);

                if (inputWert >= 0) {
                    // Eine Analyseoption wurde ausgewählt
                    if (inputWert == 0) {
                        // Berechnung und Anzeigen des RSquared-Wertes
                        ergebnisBereich.append("\n" + String.format("RSquared: %.6f", lr.BerechnenRSquared()));
                    } else if (inputWert == 1) {
                        // Berechnung der Rangkorrelation und Pearson Korrelation
                        Korrelation korrelation = new Korrelation();
                        double pearsonKorrelation = korrelation.BerechnenPearsonKorrelation(lr);
                        double rangKorrelation = korrelation.BerechnenRangkorrelation(lr);

                        // Anzeigen der Ränge von Daten in der Punkten-Liste
                        punktenListe.append("\n" + "Ränge von Daten:" + "\n");
                        for (int i = 0; i < lr.getAnzahlderPunkte(); i++) {
                            punktenListe.append(korrelation.getXRang(i) + ", " + korrelation.getYRang(i) + "\n");
                        }

                        // Anzeigen der beiden Korrelationen im Ergebnisbereich
                        ergebnisBereich.append(String.format("\n" + String.format("Pearson Korrelation: %.6f", pearsonKorrelation)));
                        ergebnisBereich.append(String.format("\n" + String.format("Rangkorrelation: %.6f", rangKorrelation)));
                        korrelation.DarstellenRangdaten();
                    } else {
                        // Durchführung von Monte Carlo Simulation für Daten von Renditen und Anzeigen des Ergebnisses
                        int iterationen = -1;
                        double betrag = -1.0;
                        int investitionZeitraum = -1;

                        while (true) {
                            String inputWert1 = JOptionPane.showInputDialog("Wie viele Iterationen sollen durchgeführt werden? (Zahlen z.B. 10)");
                            if (inputWert1 == null) {
                                // Der Benutzer hat Abbrechen oder das Schließen-Symbol gewählt, daher wird nichts unternommen
                                return;
                            }
                            try {
                                iterationen = Integer.parseInt(inputWert1);
                                break; // Die Schleife verlassen, wenn eine gültige Zahl eingegeben wurde
                            } catch (NumberFormatException e) {
                                // Fehlermeldung anzeigen, wenn keine gültige Zahl eingegeben wurde
                                JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine gültige Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        while (true) {
                            String inputWert2 = JOptionPane.showInputDialog("Investitionsbetrag (Zahlen z.B. 1000):");
                            if (inputWert2 == null) {
                                // Der Benutzer hat Abbrechen oder das Schließen-Symbol gewählt, daher wird nichts unternommen
                                return;
                            }
                            try {
                                betrag = Double.parseDouble(inputWert2);
                                break; // Die Schleife verlassen, wenn eine gültige Zahl eingegeben wurde
                            } catch (NumberFormatException e) {
                                // Fehlermeldung anzeigen, wenn keine gültige Zahl eingegeben wurde
                                JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine gültige Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        while (true) {
                            String inputWert3 = JOptionPane.showInputDialog("Wie lange soll der Betrag investiert werden? (in Tagen z.B 1095)");
                            if (inputWert3 == null) {
                                // Der Benutzer hat Abbrechen oder das Schließen-Symbol gewählt, daher wird nichts unternommen
                                return;
                            }
                            try {
                                investitionZeitraum = Integer.parseInt(inputWert3);
                                break; // Die Schleife verlassen, wenn eine gültige Zahl eingegeben wurde
                            } catch (NumberFormatException e) {
                                // Fehlermeldung anzeigen, wenn keine gültige Zahl eingegeben wurde
                                JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine gültige Zahl ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        lr.BerechnenKoeffizienten();
                        MonteCarloSimulationRendite mcl_rendite = new MonteCarloSimulationRendite();
                        double Portfolioswert = mcl_rendite.BerechnenPortfolioswert(lr, iterationen, betrag, investitionZeitraum);
                        ergebnisBereich.append("\n" + String.format("Portfolio in %d Tagen: %.6f", investitionZeitraum, Portfolioswert));
                    }
                }
            }
        }

        else if (event.getSource() == mclWuerfelButton) {
            String inputWert;
            int anzahlWurfe;
            while (true) {
                inputWert = JOptionPane.showInputDialog("Wie oft sollte dieser Würfel geworfen werden? (Zahlen: z.B. 1)");
                if (inputWert != null) { // Überprüfen, ob der Benutzer "Cancel" ausgewählt hat
                    if (!inputWert.isEmpty()) {
                        try {
                            anzahlWurfe = Integer.parseInt(inputWert);
                            break; // Die Schleife verlassen, wenn eine gültige Zahl eingegeben wurde
                        } catch (NumberFormatException e) {
                            // Wenn keine Zahl eingegeben wurde, wird eine Fehlermeldung angezeigt
                            JOptionPane.showMessageDialog(null, "Sie haben keine gültige Zahl eingegeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        // Wenn der Benutzer leere Eingabe macht, wird eine Benachrichtigung angezeigt
                        JOptionPane.showMessageDialog(null, "Sie müssen eine Zahl für die Anzahl der Würfe eingeben.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    // Wenn der Benutzer "Cancel" auswählt, wird keine Aktion ausgeführt
                    return; // Die Methode beenden, da keine gültige Eingabe vorliegt
                }
            }

            // Hier können Sie die Variable 'anzahlWurfe' verwenden, die die gültige Anzahl von Würfen enthält
            MonteCarloSimulationWurfel mcl_wuerfel = new MonteCarloSimulationWurfel(anzahlWurfe);
            int[] result = mcl_wuerfel.Wurfeln();
            double[] haufigkeit = mcl_wuerfel.ErgebnisVerteilung();

            punktenListe.setText("");
            for (int i = 0; i < 11; i++) {
                punktenListe.append(String.format("Summe %d entstand %d Mal -> Häufigkeit: %.3f", i + 2, result[i], haufigkeit[i]) + "\n");
            }
            mcl_wuerfel.ErgebnisDarstellung();

        }
    }
}
