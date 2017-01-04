package application;

public class SubscriberDaten {
	public String name;		// Identifizierung Classname (zB Frog)
	public Integer id;			// Eineindeutige ID der ObjektInstanz
	public Integer xPosition; 	// Rasterfeld
	public Integer yPosition; 	// Rasterfeld 
	public String typ;			// Was ist passiert (move, new, delete)
	public Integer time;		// aktueller Spielzeitpunkt in Millisekunden
}
