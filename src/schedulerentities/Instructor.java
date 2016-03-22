package schedulerentities;

public class Instructor {
	private String instructorID;
	private String instructorName;
	
	
	private Instructor() {}
	
	public Instructor(String instructorID) {
		this.instructorID = instructorID;
	}

	public String getInstructorID() {
		return instructorID;
	}

	public String getInstructorName() {
		return instructorName;
	}
	
}
