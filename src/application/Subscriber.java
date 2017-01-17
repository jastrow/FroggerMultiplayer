package application;

/**
 * Klasse definiert Struktur der ObserverTrigger
 * 
 * @author Die UMeLs
 *
 */
public class Subscriber {
	
	public String trigger;
	public Object listener;
	
	
	
	/**
	 * Konstruktor
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
	 * Konstruktor
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
	 * listenTo
	 *
	 * @param keyword / Schluesselwort
	 * @param daten / Daten des Trigger
	 * 
	 */
	public void listenTo(String keyword, SubscriberDaten daten) {
		((SubscriberInterface) this.listener).calling(keyword, daten);
	}
	
	
	/** 
	 * getTrigger
	 *
	 * @return String / trigger
	 * 
	 */
	public String getTrigger() {
		return trigger;
	}
	
	
	/** 
	 * getListener
	 *
	 * @return Object / Listenerobjekt
	 */
	public Object getListener() {
		return this.listener;
	}
}
