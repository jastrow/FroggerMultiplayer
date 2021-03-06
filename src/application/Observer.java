package application;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Observer ermoeglicht es, dass verschiedene Klassen sich hier anmelden koennen,
 * und bei bestimmten Ereignissen benachrichtigt werden.
 * Dafuer muessen die zu benachrichtigen Klassen (Subscriber) das Interface
 * "SubscriberInterface" implementieren, damit der Observer dann 
 * ueber die Methode "calling" die von anderen Systemen erstellten Daten 
 * "SubscriberDaten" uebergeben werden koennen.
 * Eine Klasse kann sich mehrmals beim Observer anmelden:
 * addSubscriber( trigger, SELF )
 * trigger ist ein String. Wenn der Observer Daten mit diesen String erhaelt,
 * werden alle, die sich dafuer angemeldet haben benachrichtigt.
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
	 * Erstellt und gibt die Observer-Instanz zurueck.
	 * @return Observer instance
	 */
	public static Observer getInstance() {
		if(instance == null) {
			instance = new Observer();
		}
		
		return instance;
	}
	
	/**
	 * Loest eine Nachricht aus, an alle die es interessiert (durch trigger),
	 * und uebergibt jenen die SubscriberDaten durch die Methode "calling".
	 * 
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
	 * 
	 * @param data / Subscriberdaten
	 * @param trigger / Triggertyp
	 */
	public static void trigger(String trigger, SubscriberDaten data) {
		Observer obs = Observer.getInstance();
		obs.triggerObserver(trigger, data);
	}
	
	/**
	 * Fuegt einen Subscriber hinzu.
	 * 
	 * @param obj Der Subscriber selbst (this).
	 * @param trigger Das Ausloeser-Codewort.
	 */
	public void addSubscriber(String trigger, Object obj) {
		this.subscriber.add( new Subscriber(trigger, obj) );
	}
	
	/**
	 * Statischer Aufruf von addSubscriber.
	 * 
	 * @param trigger / Triggertyp
	 * @param obj / Datenobjekt
	 * 
	 */
	public static void add(String trigger, Object obj) {
		Observer obs = Observer.getInstance();
		obs.addSubscriber(trigger, obj);
	}
	
	
	/**
	 * Entfernt einen Subscriber mit all seinen Triggern.
	 * 
	 * @param obj / Subscriberobjekt
	 * 
	 */
	public static void removeMe(Object obj) {
		Observer obs = Observer.getInstance();
		obs.removeMeByInstance(obj);
	}
	
	
	/** 
	 * Entfernt einen Subscriber mit all seinen Triggern.
	 *
	 * @param obj / Subscriberobjekt
	 */
	public void removeMeByInstance(Object obj) {
		for(Subscriber sub: this.subscriber) {
			if(sub.getListener().equals(obj)) {
				this.subscriber.remove(sub);
			}
		}
	}
	
	
	/** 
	 * Gibt die groesse der SubscriberQueue zurueck.
	 *
	 * @return Integer / Queuegroesse
	 */
	public Integer size() {
		return this.subscriber.size();
	}
	
	
	/** 
	 * Methode zeigt Subscriber.
	 * 
	 */
	public static void showSubs() {
		Observer obs = Observer.getInstance();
		obs.showSubscriber();
	}
	
	/** 
	 * Methode gibt SubscriberListe aus.
	 *
	 */
	public void showSubscriber() {
		System.out.println("############### START OBSERVERLIST #################");
		System.out.println("OBSERVER Subscriber: "+this.subscriber.size());
		for(Subscriber sub: this.subscriber) {
			String name = sub.getListener().getClass().getName();
			name += " : "+sub.getTrigger();
			System.out.println(name);
		}
		System.out.println("############### END OBSERVERLIST #################");
	}
	
}

/*
Einigung von triggern im Spiel:
start		Startet das Spiel
stop		Stoppt das Spiel (Pause?)
reset		Alle Spieleinstellung wieder auf Anfang setzen

time		Timer meldet Spielzeit

frog		Frosch meldet Änderung
tree		Baumstamm meldet Änderung
car			Auto meldet Änderung

Für die übertragenen Daten siehe Klasse: SubscriberDaten
*/