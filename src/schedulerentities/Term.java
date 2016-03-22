package schedulerentities;

public class Term {
	private int termID;
	private String termName;

	public Term(int termID, String termName) {
		this.termID = termID;
		this.termName = termName;
	}

	public int getTermID() {
		return this.termID;
	}

	public String getTermName() {
		return this.termName;
	}
	
	@Override
	public String toString() {
		return this.termName;
	}
}
