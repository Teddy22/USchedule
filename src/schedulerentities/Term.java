package schedulerentities;

import java.util.ArrayList;
import java.util.Date;

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
	
	// get all sessions for the term e.g maymesters for summer
	public ArrayList<Session> getSessions() {
		throw new RuntimeException("method not yet implemented");
	}
	
	public Date getStartDate() {
		throw new RuntimeException("method not yet implemented");
	}
	
	public Date getEndDate() {
		throw new RuntimeException("method not yet implemented");
	}
}
