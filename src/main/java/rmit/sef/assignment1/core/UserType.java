package rmit.sef.assignment1.core;

public enum UserType {
	Stuff("Stuff"), Student("Student"), Employer("Employer"), Unknown("Unknown");
	
	private String title;

	UserType(String title) {
		this.title = title;
	}

	public String toString() {
		return title;
	}

	public static UserType getUserType(String type) {
		if (type.equals("Stuff"))
			return Stuff;
		if (type.equals("Student"))
			return Student;
		if (type.equals("Employer"))
			return Employer;
		return Unknown;
	}
	
	public boolean equals(UserType t) {
		return this.title.equals(t.toString());
	}
}
