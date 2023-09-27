# Java-Projekt: Datenanalyse durch Lineare Regression und Monte-Carlo-Simulation

## Beschreibung
Dieses Java-Projekt bietet eine praktische Einführung in die Grundlagen der linearen Regression und Korrelation sowie die Anwendung von Monte-Carlo-Simulationen. In diesem Projekt werden die folgenden Konzepte behandelt:
Lineare Regression: Das Programm stellt nicht nur die lineare Regressionsgleichung und deren grafische Darstellung zur Verfügung, sondern ermittelt auch den R-Quadrat-Wert, einer wichtigen Kennzahl für den Zusammenhang zwischen der linearen Regressionsgeraden und den Daten 
Korrelation nach Spearmann und Pearson: die Kennzahlen, die den Zusammenhang zwischen den Daten analysiert. Dies ermöglichte es, die Ergebnisse der Korrelation und Regression zu vergleichen, um die Genauigkeit der Ergebnisse von Kennzahlen sicherzustellen. 
Monte-Carlo-Simulation: Eine Technik zur Modellierung und Analyse von Unsicherheit durch wiederholte zufällige Experimente. In diesem Projekt werden zwei Beispiele verwendet: eine einfache Monte-Carlo-Simulation mit Würfeln und eine Vorhersage der Aktienrenditen unter Verwendung einer Kombination aus linearer Regression und Monte-Carlo-Simulation.
Die Ergebnisse dieser Analysen werden mithilfe einer grafischen Benutzeroberfläche (GUI) anschaulich dargestellt.

## Features
-	Berechnung und Visualisierung der linearen Regressionsgleichung.
-	Bestimmung der Häufigkeit von Würfelergebnissen in Bezug auf die Anzahl der Würfe durch die Verwendung einer Monte-Carlo-Simulation.
-	Analyse der Daten mithilfe von R-Quadrat-Wert, Korrelationen.
-	Analyse und Vorhersage der Renditendaten mithilfe von R-Quadrat-Wert, Korrelationen und Kombination aus linearer Regression und Monte-Carlo-Simulation.

## Voraussetzungen
Um dieses Java-Projekt auszuführen, sind folgende Voraussetzungen erforderlich:
Java Development Kit (JDK) (Version 1.8.0_201).
Integrierte Entwicklungsumgebung (IDE) für Java (z.B. IntelliJ IDEA, Eclipse).
Die Java-Bibliotheken für GUI-Entwicklung (Plotter).
Grundlegende Kenntnisse in Java-Programmierung.

## Installation
1.	Klonen oder laden Sie das Projekt von GitHub herunter.

2.	Importiere das Projekt in deine bevorzugte Java-Entwicklungsumgebung (z.B. Eclipse, IntelliJ).

## Verwendung
1.	Öffne das Projekt in die Java-Entwicklungsumgebung.
2.	Führe das Hauptklasse „Entry GUI“ aus, um die GUI zu starten.
3.	Bereite die Daten vor, die für Analyse durch lineare Regression, Korrelation und Monte-Carlo-Simulation verwendet werden sollen.
4.	Verwende die GUI, um die importierten Daten durch die lineare Regression, Korrelation und Monte-Carlo-Simulation zu analysieren und die Ergebnisse zu visualisieren.
5.	Nutze die Anwendungsbeispiele, um die Monte-Carlo-Simulation mit Würfeln auszuführen oder Aktienrenditen vorherzusagen.

## GUI-Schaltflächen
In der grafischen Benutzeroberfläche (GUI) der Anwendung finden Sie verschiedene Schaltflächen, die Ihnen ermöglichen, die mathematischen Konzepte und Anwendungsbeispiele zu nutzen. Hier ist eine kurze Beschreibung der wichtigsten Schaltflächen:
-	**Hinzufügen:**  Durch diese Schaltfläche werden einzelne Datenpunkte hinzufügen.
-	**Importieren:** Diese Schaltfläche ermöglicht das Laden von Daten, um bereits vorhandene Daten in die Anwendung zu importieren.
-	**Leeren:** Dieser Button löscht alle aktuellen Eingaben und Berechnungen, um einen neuen Vorgang zu starten.
-	**Berechnen:** Diese Schaltfläche startet die Berechnung und Visualisierung der linearen Regressionsgleichung. 
-	**Monte Carlo Simulation:** Bei Auswahl dieser Schaltfläche können Sie die Monte-Carlo-Simulation mit dem angegebenen Parameter (Anzahl der Würfe) starten, um die Häufigkeit von Würfelergebnisse zu berechnen und zu visualisieren.
-	**Analysieren:** Durch Klicken auf diese Schaltfläche öffnen sich drei weitere Schaltflächen, die zur Analyse von Daten dienen. Je nach dem, welche Datenkategorie ausgewählt ist, können die folgenden Optionen zur Verfügung stehen:
  - **R-Quadrat-Wert:** Wenn Sie diese Option auswählen, wird der R-Quadrat-Wert für die ausgewählten Daten berechnet und angezeigt.
  - **Korrelation:** Mit dieser Option können Sie die Korrelation zwischen den Daten analysieren und visualisieren, um Zusammenhänge zu erkennen.
  - **Renditevorhersage (nur für Renditendaten):** Falls Renditendaten ausgewählt sind, steht diese Option zur Verfügung. Sie ermöglicht es, die Daten mithilfe einer Monte-Carlo-Simulation zu analysieren und zukünftige Entwicklungen vorherzusagen.

## Beispiel
Hier ist ein einfaches Beispiel, wie Sie das Java-Projekt verwenden können:
### Monte-Carlo-Simulation mit Würfeln
-	Öffne das Projekt in Ihrer IDE.
-	Klicke auf der Schaltfläche „Monte Carlo Simulation“ und gebe die Anzahl der Würfe ein.
### Vorhersage von Aktienrenditen
-	Bereite die Aktiendaten vor, indem CSV-Datei importiert wird.
-	Implementiere die lineare Regression und die Berechnung des R-Quadrat-Werts für die Daten.
-	Führen Sie die Korrelationsanalyse für die Daten durch, um die Genauigkeit des Datenzusammenhang sicherzustellen.
-	Falls der Zusammenhang zwischen den 2 Aktiendaten groß genug ist (R-Quadrat > , Korrelation > ), führe die Renditevorhersage aus, um Aktienrenditen vorherzusagen und visualisieren Sie die Ergebnisse mithilfe der GUI.
  
## Autoren
- Viet Quang Dang
- Nhu Phuong Nguyen
- The Trung Do
- Kontakt: Bei Fragen oder Anregungen können Sie uns unter dangvietquang2010@gmail.com oder nhuphuong.ngn0604@gmail.com oder dotrung1998@gmail.com erreichen.

## Dank
Wir danken allen Open-Source-Projekten und -Bibliotheken, auf denen dieses Projekt aufbaut.
