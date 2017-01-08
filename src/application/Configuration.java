package application;

public class Configuration {
	// Number of horizontal Fields in Gamezone
	public final static Integer xFields = 19;
	// Number of vertically Fields in Gamezone
	public final static Integer yFields = 12;
	public final static Integer[] streetLines = {2,3,10,11}; // Y-Positions (num of = length) 2,3,10,11
	public final static Integer[] riverLines = {5,6,7,8}; // Y-Positions (num = length)
	public final static Integer[] polePosition = {12,10};
	public final static Integer timeEnd = 20000; // Milliseconds
	public final static Integer timeSteps = 100; // Milliseconds
	
	public final static Integer carMaxPerStreet = 3; // Maximale Anzahl von Autos auf einer Straße
	public final static Integer carSpeed = 300; 	// Bewegungsgeschwindigkeit Millisekunden pro Feld ()
	
	public final static Integer treeMaxPerLane = 3; // Maximale Anzahl von Baeumen auf einer Flussbahn
	public final static Integer treeSpeed = 300; 	// Bewegungsgeschwindigkeit Millisekunden pro Feld ()
}
