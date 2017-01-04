package model;

/**
 * Gibt eine eineindeutige Id zur Laufzeit wieder.
 * @author max
 *
 */
public class IdCounter {
	private static Integer num = 0;
	
	public static Integer getId() {
		return ++num;
	}
}

// IdCounter.getId() = Int