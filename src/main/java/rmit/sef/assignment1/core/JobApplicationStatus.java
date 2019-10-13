package rmit.sef.assignment1.core;

public enum JobApplicationStatus {
	Requesting("Requesting"), UnderReview("UnderReview"), Rejected("Rejected"), Accepted("Accepted"),Cancelled("Cancelled"),
	Unknown("Unknown");

	private String title;

	JobApplicationStatus(String title) {
		this.title = title;
	}

	public String toString() {
		return title;
	}

	public static JobApplicationStatus getStatus(String type) {
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
