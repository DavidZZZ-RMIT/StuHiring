package rmit.sef.assignment1.core;

public enum Gender {
	Male("Male"), Female("Female");
	String title;

	Gender(String title) {
		this.title = title;
	}

	public String toString() {
		return title;
	}

	public static Gender getGender(String type) {
		if (type.equals("Male"))
			return Male;
		if (type.equals("Female"))
			return Female;
		return Male;
	}
}
