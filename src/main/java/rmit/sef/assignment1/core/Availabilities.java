package rmit.sef.assignment1.core;

import java.util.EnumMap;

public class Availabilities {
	EnumMap<Availability, Boolean> availabilities = new EnumMap<>(Availability.class);

	public Availabilities() {
		availabilities.put(Availability.FullTime, false);
		availabilities.put(Availability.Internship, false);
		availabilities.put(Availability.PartTime, false);
	}

	public String toString() {
		return (availabilities.get(Availability.FullTime) ? "0" : "1")
				+ (availabilities.get(Availability.Internship) ? "0" : "1")
				+ (availabilities.get(Availability.PartTime) ? "0" : "1");
	}

	public void setAvailability(Availability a, boolean b) {
		availabilities.put(a, b);
	}

	public static Availabilities fromString(String str) {
		Availabilities a = new Availabilities();
		if (str.charAt(0) == '1')
			a.setAvailability(Availability.FullTime, true);
		if (str.charAt(1) == '1')
			a.setAvailability(Availability.Internship, true);
		if (str.charAt(2) == '1')
			a.setAvailability(Availability.PartTime, true);
		return a;
	}
}
