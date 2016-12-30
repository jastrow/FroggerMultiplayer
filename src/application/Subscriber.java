package application;

public class Subscriber {
	
	public String keyword;
	public Object listener;
	
	public Subscriber(Object subscriber, String keyword) {
		this.keyword = keyword;
		this.listener = subscriber;
	}
	public Subscriber(String keyword, Object subscriber) {
		this.keyword = keyword;
		this.listener = subscriber;
	}
	
	public String listenTo(String keyword, SubscriberDaten daten) {

		((SubscriberInterface) this.listener).calling(keyword, daten);
		
		return "wurde ausgeführt";
	}
	
	public String getKeyword() {
		return keyword;
	}
}
