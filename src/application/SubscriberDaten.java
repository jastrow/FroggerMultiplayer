package application;

public class SubscriberDaten {
	public String name;		// Identifizierung Classname (zB Frog)
	public Integer id;			// Eineindeutige ID der ObjektInstanz
	public Integer xPosition; 	// Rasterfeld
	public Integer yPosition; 	// Wenn Rasterfeld = 0 => DELETE
	public Integer time;		// aktueller Spielzeitpunkt in Millisekunden
}
