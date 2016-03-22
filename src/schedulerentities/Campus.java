package schedulerentities;

public class Campus {
	
	private String campusID;
	private String campusName;
	
	public Campus(String campusID, String campusName) {
		this.campusID = campusID;
		this.campusName = campusName;
	}

	public String getCampusID() {
		return this.campusID;
	}

	public String getCampusName() {
		return this.campusName;
	}
	
}
