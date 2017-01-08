package application;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


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
	public Queue<Subscriber> subscriber = new ConcurrentLinkedQueue<Subscriber>();
	
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
			if(subscriber.getTrigger() == trigger) {
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
	public void addSubscriber(String trigger, Object obj) {
		this.subscriber.add( new Subscriber(trigger, obj) );
	}
	/**
	 * Statischer Aufruf von addSubscriber.
	 * @param obj
	 * @param trigger
	 */
	public static void add(String trigger, Object obj) {
		Observer obs = Observer.getInstance();
		obs.addSubscriber(trigger, obj);
	}
	
	
	/**
	 * Entfernt einen Subscriber mit all seinen Triggern.
	 */
	public static void removeMe(Object obj) {
		Observer obs = Observer.getInstance();
		obs.removeMeByInstance(obj);
	}
	public void removeMeByInstance(Object obj) {
		for(Subscriber sub: this.subscriber) {
			if(sub.getListener() == obj) {
				this.subscriber.remove(sub);
			}
		}
	}
	
	
}