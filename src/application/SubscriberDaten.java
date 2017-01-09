package application;

public class SubscriberDaten {
	public String name;			// Identifizierung Classname (zB Frog)
	public Integer id;			// Eineindeutige ID der ObjektInstanz
	public String typ;			// Was ist passiert (move, new, delete)
	public String facing;		// Ausrichtung des Frosches [n,s,w,o]
	public Integer xPosition; 	// Rasterfeld
	public Integer yPosition; 	// Rasterfeld 
	public Integer time;		// aktueller Spielzeitpunkt in Millisekunden
	public Integer length;		// Länge des Balken 1/ 2 /3 // bei Frosch Spieler: 1 / 2 
	public Boolean leftToRight; // Ob das Objekt sich von links nach rechts bewegt oder umgekehrt (Tree, Car)
	
	@Override
	public String toString() {
		String ausgabe = "";
		ausgabe += "name: "+this.name+"\r\n";
		ausgabe += "id: "+this.id+"\r\n";
		ausgabe += "typ: "+this.typ+"\r\n";
		ausgabe += "facing: "+this.facing+"\r\n";
		ausgabe += "xPosition: "+this.xPosition+"\r\n";
		ausgabe += "yPosition: "+this.yPosition+"\r\n";
		ausgabe += "time: "+this.time+"\r\n";
		ausgabe += "length: "+this.length+"\r\n";
		ausgabe += "leftToRight: "+this.leftToRight+"\r\n";
		return ausgabe;
	}
}
