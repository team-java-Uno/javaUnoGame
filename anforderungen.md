
# Projektaufgabe: Entwicklung eines UNO-Kartenspiels in Java

## Überblick
In dieser Projektaufgabe werden Sie in Gruppen zu je 4 Personen das klassische Kartenspiel UNO in Java implementieren. Sie haben 4 Tage Zeit, um eine funktionsfähige Version des Spiels zu erstellen, die verschiedene grundlegende und fortgeschrittene Java-Konzepte demonstriert.

## Lernziele
- Anwendung von objektorientierten Programmierkonzepten
- Implementierung von Klassen, abstrakten Klassen und Interfaces
- Verwendung von Schleifen, Methoden und Enums
- Umgang mit Listen und anderen Datenstrukturen
- Implementierung von Ausnahmebehandlung (Exceptions)
- Grundlegende Dateiverwaltung (File Handling)
- Teamarbeit und Projektverwaltung

## Anforderungen

### 1. Spiellogik
- Implementieren Sie die grundlegenden UNO-Spielregeln wie in der bereitgestellten PDF-Datei beschrieben.
- Das Spiel sollte für 2-10 Spieler spielbar sein.
- Implementieren Sie alle Kartentypen: Zahlenkarten, "Zieh Zwei", "Retour", "Aussetzen", "Farbenwahl" und "Zieh Vier Farbenwahl".
- Seven-O (Sieben-Null): Wird eine Null gespielt, so muss jeder Spieler sein komplettes Blatt an den Spielnachbarn in Uhrzeigerrichtugn weitergeben. Wird dagegen eine Sieben ausgespielt, so darf dieser Spieler sein komplettes Blatt mit einem anderen Spieler seiner Wahl tauschen.
- Doppeln: Hat ein Spieler zwei Karten von der selben Farbe und Zahl auf der Hand, so darf er diese zusammen ablegen. Dies gilt jedoch nicht für die +2- und +4-Karten. Diese müssen weiterhin nacheinander abgelegt werden.
- Kumulieren: Kann ein Spieler, der von eine +2-Karte betroffen ist, selbst mit einer solchen Karte aufwarten, so ist dies erlaubt und der nächste Spieler nach ihm muss nun 4 Karten ziehen. Hat dieser ebenfalls eine +2-Karte, so ist auch dies zulässig. Gleiches gilt entsprechend für die +4-Karten. Die Karten dürfen allerdings nicht kombiniert werden.
- Fügen Sie einen KI-Gegner hinzu.

### 2. Klassenstruktur
- Erstellen Sie eine geeignete Klassenstruktur für das Spiel, einschließlich (aber nicht beschränkt auf):
    - Eine abstrakte `Card`-Klasse
    - Konkrete Klassen für verschiedene Kartentypen
    - Eine `Player`-Klasse
    - Eine `Deck`-Klasse
    - Eine `Game`-Klasse zur Verwaltung des Spielablaufs

### 3. Java-Konzepte
Stellen Sie sicher, dass Ihr Projekt die folgenden Java-Konzepte demonstriert:
- Vererbung
- Abstrakte Klassen und Methoden
- Interfaces
- Enums
- Ausnahmebehandlung
- Verwendung von Listen und/oder HashMaps
- Testen sie mindestens eine Klasse mit Hilfe von JUnit (Unittesting)

### 4. Benutzeroberfläche
- Implementieren Sie eine einfache, textbasierte Benutzeroberfläche für das Spiel.
- Die Oberfläche sollte den aktuellen Spielstand anzeigen und Benutzereingaben verarbeiten.

### 5. Dateiverarbeitung
- Implementieren Sie eine Funktion zum Speichern und Laden des Spielstands.

### 6. Zusätzliche Herausforderungen (optional)
- Jump-In: Wenn ein Spieler exakt die gleiche Karte auf der Hand hat, welche soeben ausgespielt wurde (gleiche Farbe und Zahl), so kann er diese sofort ablegen – auch wenn er nicht am Zug ist. Die Runde geht jedoch bei dem Spieler weiter, welcher regulär an der Reihe gewesen wäre.
- Partner: Es werden Teams gebildetet bei denen sich jeweils die Teamspieler gegenüber sitzen. Gewinnt einer der Teamspieler die Runde, so erhält das gesamte Team den Sieg. Die Punkte der übrigen Mitspieler werden für für das jeweilige Team als Strafpunkte gewertet.
- Strafpunkte: Diese Spielvariante kehrt das eigentliche Spiel um. Nicht wer als erste eine bestimmte Anzahl an Punkten erreicht gewinnt, sondern verliert das Spiel. Die maximale Anzahl an Punkten kann hierbei variieren, üblich sind jedoch 500 Punkte.

## Zeitplan
- Tag 1: Planung, Entwurf der Klassenstruktur, Beginn der Implementierung von Basisklassen
- Tag 2: Implementierung der Hauptspiellogik und Kartenaktionen
- Tag 3: Entwicklung der Benutzeroberfläche, Implementierung der Ausnahmebehandlung
- Tag 4: Implementierung der Dateiverwaltung, Testen, Debuggen und Feinschliff

## Abgabe
Am Ende des vierten Tages soll jede Gruppe Folgendes einreichen:
1. Den vollständigen Quellcode des Projekts
2. Eine kurze Dokumentation als Markdown, die die Struktur des Programms und die Aufgabenverteilung innerhalb der Gruppe beschreibt
3. Eine Präsentation (5-10 Minuten) zur Vorstellung des Projekts, einschließlich einer Live-Demonstration

## Bewertungskriterien
- Funktionalität und Korrektheit des Spiels
- Codequalität und -organisation
- Anwendung der geforderten Java-Konzepte
- Teamarbeit und gleichmäßige Aufgabenverteilung

Viel Erfolg und viel Spaß beim Programmieren!
