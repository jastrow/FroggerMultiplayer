package application;

/**
 * Klasse definiert Struktur der ObserverTrigger.
 * 
 * @author Die UMeLs
 *
 */
public class Subscriber {
	
	public String trigger;
	public Object listener;
	
	
	
	/**
	 * Konstruktor.
	 *
	 * @param subscriber / subscriber
	 * @param keyword / Schluesselwort des Trigger
	 *
	 */
	public Subscriber(Object subscriber, String keyword) {
		this.trigger = keyword;
		this.listener = subscriber;
	}
	
	/**
	 * Konstruktor.
	 *
	 * @param subscriber / subscriber
	 * @param keyword / Schluesselwort des Trigger
	 *
	 */
	public Subscriber(String keyword, Object subscriber) {
		this.trigger = keyword;
		this.listener = subscriber;
	}
	
	
	/** 
	 * Methode ListenTo.
	 *
	 * @param keyword / Schluesselwort
	 * @param daten / Daten des Trigger
	 * 
	 */
	public void listenTo(String keyword, SubscriberDaten daten) {
		((SubscriberInterface) this.listener).calling(keyword, daten);
	}
	
	
	/** 
	 * Methode gibt Trigger zurueck.
	 *
	 * @return String / trigger
	 * 
	 */
	public String getTrigger() {
		return trigger;
	}
	
	
	/** 
	 * Methode getListener.
	 *
	 * @return Object / Listenerobjekt
	 */
	public Object getListener() {
		return this.listener;
	}
}
