package schedulerentities;

public class Course implements Comparable<Course>{
	private Subject subject;
	private String courseNum;
	private String courseTitle;
	
	
	public Course(Subject subject, String courseNum, String courseTitle) {
		this.subject = subject;
		this.courseTitle = courseNum;
		this.courseTitle = courseTitle;
	}
	
	public Course(Subject subject, Integer subjectNum, String subjectTitle) {
		this.subject = subject;
		this.courseTitle = subjectNum.toString();
		this.courseTitle = subjectTitle;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public String getCourseNum() {
		return this.courseNum;
	}

	public String getCourseTitle() {
		return this.courseTitle;
	}

	@Override
	public int compareTo(Course course) {
		if(this.getSubject().compareTo(course.getSubject()) > 0) {
			return 1;
		} else if(this.getSubject().compareTo(course.getSubject()) < 0) {
			return -1;
		} else {
			if(this.getCourseNum().compareTo(course.getCourseNum()) > 0) {
				return 1;
			} else if(this.getCourseNum().compareTo(course.getCourseNum()) < 0) {
				return -1;
			} else {
				if(this.getCourseTitle().compareTo(course.getCourseTitle()) > 0) {
					return 1;
				} else if(this.getCourseTitle().compareTo(course.getCourseTitle()) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	
}
