package application;

public class Subscriber {
	
	public String trigger;
	public Object listener;
	
	public Subscriber(Object subscriber, String keyword) {
		this.trigger = keyword;
		this.listener = subscriber;
	}
	public Subscriber(String keyword, Object subscriber) {
		this.trigger = keyword;
		this.listener = subscriber;
	}
	
	public String listenTo(String keyword, SubscriberDaten daten) {

		((SubscriberInterface) this.listener).calling(keyword, daten);
		
		return "wurde ausgeführt";
	}
	
	public String getTrigger() {
		return trigger;
	}
	public Object getListener() {
		return this.listener;
	}
}
