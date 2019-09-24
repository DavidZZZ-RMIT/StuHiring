package rmit.sef.assignment1.core;

import java.time.format.DateTimeFormatter;

public final class util {
	final public static String DATA_FORMAT = "hh:mm:ss dd/MM/YYYY";
	final public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATA_FORMAT);

}
