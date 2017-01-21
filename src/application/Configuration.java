package application;

/**
 * statische Klasse fuer modifizierbare Spielvariablen
 * 
 * @author Die UMeLs
 *
 */
public class Configuration {
	
	public final static Integer xFields = 19; // Number of horizontal Fields in Gamezone
	public final static Integer yFields = 12; // Number of vertically Fields in Gamezone
	public final static Integer timeEnd = 60000; // Milliseconds
	public final static Integer timeSteps = 40; // Milliseconds (25fps = 40)

	public final static Integer[] streetLines = {50,100,450,500}; // Y-Positions (num of = length) 2,3,10,11
	public final static Integer carMaxPerStreet = 3; // Maximale Anzahl von Autos auf einer Straße
	public final static Integer carSpeed = 3; 	// Bewegungsgeschwindigkeit Millisekunden pro Pixel

	public final static Integer[] riverLines = {200,250,300,350}; // Y-Positions (num = length)
	public final static Integer treeMaxPerLane = 4; // Maximale Anzahl von Baeumen auf einer Flussbahn
	public final static Integer treeSpeed = 7; 	// Bewegungsgeschwindigkeit Millisekunden pro Pixel
	
	public final static Integer flyEatenPoints = 500; // Punkte wenn der Frosch eine Fliege fängt
	public final static Integer flyRandom = 5; // Wahrscheinlichkeit jeder x-te Baumstaum eine Fliege

	public final static Integer frogHop = 50; // Sprungweite des Frosches
		
	
	// Dimensionen in Pixel
	public final static Integer xGameZone = 950; // Spielfeldbreite
	public final static Integer yGameZone = 600; // Spielfeldhöhe
	
	public final static Integer yElements = 50; // Höhe der Baumstämme
	public final static Integer xCar = 100; // Breite der Autos
	public final static Integer[] xTree = {100,150,200}; // Baumstammlängen
	public final static Integer yTree = 50; // Höhe der Baumstämme
	public final static Integer xFrog = 50; // Breite Frosch
	public final static Integer yFrog = 50; // Höhe Frosch
	
	
	
	
}
