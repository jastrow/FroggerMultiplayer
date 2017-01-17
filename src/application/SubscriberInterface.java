package application;

/**
 * Interface zur Definition der Callingmethode an Observer
 * 
 * @author Die UMeLs
 *
 */
public interface SubscriberInterface {
	public void calling(String trigger, SubscriberDaten daten);
}
