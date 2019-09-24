package rmit.sef.assignment1.db;

import org.dizitart.no2.Nitrite;

public class DataKeeper {
	private static Nitrite db = null;

	public static Nitrite getInstance() {
		if (db == null) {
			db = Nitrite.builder().filePath("./unicorn.db").openOrCreate();
		}
		return db;
	}
}
