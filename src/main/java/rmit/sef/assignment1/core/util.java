package rmit.sef.assignment1.core;

import java.time.format.DateTimeFormatter;

public final class util {
	final public static String DATA_FORMAT = "HH:mm:ss dd/MM/yyyy";
	final public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATA_FORMAT);
}
