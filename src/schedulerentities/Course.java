package schedulerentities;

public class Course {
	private String subjectName;
	private String subjectNum;
	private String subjectTitle;
	
	private Course() {}
	
	public Course(String courseName, String courseNum, String courseTitle) {
		super();
		this.subjectName = courseName;
		this.subjectNum = courseNum;
		this.subjectTitle = courseTitle;
	}
	
	public Course(String subjectName, Integer subjectNum, String subjectTitle) {
		super();
		this.subjectName = subjectName;
		this.subjectNum = subjectNum.toString();
		this.subjectTitle = subjectTitle;
	}

	public String getCourseName() {
		return subjectName;
	}

	public String getCourseNum() {
		return subjectNum;
	}

	public String getCourseTitle() {
		return subjectTitle;
	}
	
	public boolean hasMultipleTitles() {
		// TODO implement hasMultipleTitles, currently throws a runtime exception
		throw new RuntimeException("method has not yet been implemented");
	}
}
