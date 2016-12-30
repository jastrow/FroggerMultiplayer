package application;

import java.util.ArrayList;
import java.util.List;


/**
 * Observer ermöglicht es, dass verschiedene Klassen sich hier anmelden können,
 * und bei bestimmten Ereignissen benachrichtigt werden.
 * Dafür müssen die zu benachrichtigen Klassen (Subscriber) das Interface
 * "SubscriberInterface" implementieren, damit der Observer dann 
 * über die Methode "calling" die von anderen Systemen erstellten Daten 
 * "SubscriberDaten" übergeben werden können.
 * Eine Klasse kann sich mehrmals beim Observer anmelden:
 * addSubscriber( trigger, SELF )
 * trigger ist ein String. Wenn der Observer Daten mit diesen String erhält,
 * werden alle, die sich dafür angemeldet benachrichtigt.
 * SELF ist die Instanz des Subscribers (this).
 * Der Observer arbeitet intern zum Verwalten der Subscriber mit 
 * einer Subscriber-Klasse, in welcher der trigger und die Instanz 
 * gespeichert werden.
 */
public class Observer {

	/* Singleton des Observers */
	public static Observer instance;
	
	/* Liste der Subscriber Instanzen */
	public List<Subscriber> subscriber = new ArrayList<Subscriber>(); 
	
	/**
	 * (Erstellt und) Gibt die Observer-Instanz zurück.
	 * @return Observer instance
	 */
	public static Observer getInstance() {
		if(instance == null) {
			instance = new Observer();
		}
		
		return instance;
	}
	
	/**
	 * Löst eine Nachricht aus, an alle die es interessiert (durch trigger),
	 * und übergibt jenen die SubscriberDaten durch die Methode "calling".
	 * @param trigger Benachrichtigungs Codewort
	 * @param data Daten in Form von SubscriberDaten
	 */
	public void triggerObserver(String trigger, SubscriberDaten data) {
		for(Subscriber subscriber: this.subscriber) {
			if(subscriber.getKeyword() == trigger) {
				System.out.println("triggered subscriber found ");
				subscriber.listenTo(trigger, data);
			}
		}
	}
	/**
	 * Statischer Aufruf von triggerObserver.
	 * @param trigger
	 * @param data
	 */
	public static void trigger(String trigger, SubscriberDaten data) {
		Observer obs = Observer.getInstance();
		obs.triggerObserver(trigger, data);
	}
	
	/**
	 * Fügt einen Subscriber hinzu.
	 * @param obj Der Subscriber selbst (this).
	 * @param trigger Das Auslöser-Codewort.
	 */
	public void addSubscriber(Object obj, String trigger) {
		this.subscriber.add( new Subscriber(obj, trigger) );
	}
	/**
	 * Statischer Aufruf von addSubscriber.
	 * @param obj
	 * @param trigger
	 */
	public static void add(Object obj, String trigger) {
		Observer obs = Observer.getInstance();
		obs.addSubscriber(obj, trigger);
	}
	
	/**
	 * Entfernt einen Subscriber, benötigt die betroffene Instanz und den Trigger.
	 * @param obj
	 * @param trigger
	 */
	public void removeSubscriber(Object obj, String trigger) {
		
	}
	
	/**
	 * Entfernt einen Subscriber mit all seinen Triggern.
	 */
	public void removeSubscriberByInstance(Object obj) {
		
	}
	
	/**
	 * Entfernt alle Subscriber mit einem bestimmten Trigger.
	 */
	public void removeSubscriberByTrigger(String trigger) {
		
	}
	
}