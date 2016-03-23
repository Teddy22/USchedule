package schedulerentities;

import java.util.Date;

public class Session {
	private Term term;
	private String sessionName;
	private Date startDate;
	private Date endDate;
	
	public Session(Term term, String sessionName, Date startDate, Date endDate) {
		this.term = term;
		this.sessionName = sessionName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Term getTerm() {
		return term;
	}
	public String getSessionName() {
		return sessionName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	
	
}
