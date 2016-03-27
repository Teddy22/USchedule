package schedulerentities;

public class Subject implements Comparable<Subject>{
	private String subjectName;
	private String subjectNameAbbreviation;
	
	public Subject(String subjectNameAbbreviation, String subjectName) {
		
		if(subjectNameAbbreviation.length() > subjectName.length())
		{
			
		} else {
			this.subjectNameAbbreviation = subjectNameAbbreviation;
			this.subjectName = subjectName;
		}
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public String getSubjectNameAbbreviation() {
		return this.subjectNameAbbreviation;
	}
	
	@Override
	public String toString() {
		return this.subjectName;
	}

	@Override
	public int compareTo(Subject subject) {
		if(this.getSubjectName().compareTo(subject.getSubjectName()) > 0) {
			return 1;
		} else if(this.getSubjectName().compareTo(subject.getSubjectName()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
