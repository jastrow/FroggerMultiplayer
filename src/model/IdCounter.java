package model;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gibt eine eineindeutige Id zur Laufzeit wieder.
 * @author Die UMeLs
 *
 */
public class IdCounter {
	
	private static final AtomicInteger counter = new AtomicInteger();
	
	/** 
	 * Methode liefert Eineindeutige ID zur Laufzeit.
	 *
	 * @return Integer / ID
	 */
	public static Integer getId() {
		return counter.getAndIncrement();			  
	}
	
}


