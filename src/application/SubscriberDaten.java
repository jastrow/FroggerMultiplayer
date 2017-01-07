package application;

public class SubscriberDaten {
	public String name;		// Identifizierung Classname (zB Frog)
	public Integer id;			// Eineindeutige ID der ObjektInstanz
	public Integer xPosition; 	// Rasterfeld
	public Integer yPosition; 	// Rasterfeld 
	public Integer length;		// Länge des Balken 1/ 2 /3 // bei Frosch Spieler: 1 / 2 
	public String typ;			// Was ist passiert (move, new, delete)
	public Integer time;		// aktueller Spielzeitpunkt in Millisekunden
	
	@Override
	public String toString() {
		String ausgabe = "";
		ausgabe += "name: "+this.name;
		ausgabe += "id: "+this.id;
		ausgabe += "typ: "+this.typ;
		ausgabe += "length: "+this.length;
		return ausgabe;
	}
}
