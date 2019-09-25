package rmit.sef.assignment1.core;

public enum ApplicantStatus {
	Available("Available"), Pending("Pending"), Employed("Employed"), Unknown("Unknown");

	String title;

	ApplicantStatus(String t) {
		title = t;
	}

	public String toString() {
		return title;
	}

	public static ApplicantStatus fromString(String str) {
		if (str.equals("Available")) {
			return Available;
		}
		if (str.equals("Pending")) {
			return Pending;
		}
		if (str.equals("Employed")) {
			return Employed;
		}
		return Unknown;

	}
}
