package rmit.sef.assignment1.core;

public enum JobApplicationStatus {
	Requesting("Requesting"), UnderReview("UnderReview"), Rejected("Rejected"), Accepted("Accepted"),
	Unknown("Unknown");

	private String title;

	JobApplicationStatus(String title) {
		this.title = title;
	}

	public String toString() {
		return title;
	}

	public static JobApplicationStatus getStatu(String type) {
		if (type.equals("Requesting"))
			return Requesting;
		if (type.equals("UnderReview"))
			return UnderReview;
		if (type.equals("Rejected"))
			return Rejected;
		if (type.equals("Accepted"))
			return Accepted;
		return Unknown;
	}
}
