package application;

/**
 * @author Die UMeLs
 *
 */
public class Subscriber {
	
	public String trigger;
	public Object listener;
	
	
	
	/**
	 * Konstruktor
	 *
	 * @param subscriber
	 * @param keyword
	 *
	 */
	public Subscriber(Object subscriber, String keyword) {
		this.trigger = keyword;
		this.listener = subscriber;
	}
	
	/**
	 * Konstruktor
	 *
	 * @param subscriber
	 * @param keyword
	 *
	 */
	public Subscriber(String keyword, Object subscriber) {
		this.trigger = keyword;
		this.listener = subscriber;
	}
	
	
	/** 
	 * listenTo
	 *
	 * @param keyword
	 * @param daten 
	 * @return void
	 */
	public void listenTo(String keyword, SubscriberDaten daten) {
		((SubscriberInterface) this.listener).calling(keyword, daten);
	}
	
	
	/** 
	 * getTrigger
	 *
	 * @return String
	 */
	public String getTrigger() {
		return trigger;
	}
	
	
	/** 
	 * getListener
	 *
	 * @return Object
	 */
	public Object getListener() {
		return this.listener;
	}
}
