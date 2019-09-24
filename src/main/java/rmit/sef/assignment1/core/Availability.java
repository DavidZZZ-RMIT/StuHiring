package rmit.sef.assignment1.core;

public enum Availability {
	PartTime("Part-Time"), FullTime("Full-Time"), Internship("Internship"), Unknown("Unknown");

	private String title;

	Availability(String title) {
		this.title = title;
	}

	public String toString() {
		return title;
	}

	public static Availability getAvailability(String title) {
		if (title.equals("Part-Time"))
			return PartTime;
		if (title.equals("Full-Time"))
			return FullTime;
		if (title.equals("Internship"))
			return Internship;
		return Unknown;
	}
}
