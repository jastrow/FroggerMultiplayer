package model;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Gibt eine eineindeutige Id zur Laufzeit wieder.
 * @author max
 *
 */
public class IdCounter {
	
	private static final AtomicInteger counter = new AtomicInteger();
	
	public static Integer getId() {
		return counter.getAndIncrement();			  
	}
	
}


