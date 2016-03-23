package schedulerentities;

import java.sql.Time;
import java.util.ArrayList;

public class Section {
	private Term term;
	private Session session;
	
	private Course subject;
	private int CRN;
	
	private CreditHours creditHours;
	
	private int capacity;
	private int seatsTaken;
	private int seatsAvailable;
	
	private boolean waitlistAvailable;
	private boolean sectionOpen;
	
	private int waitlistCapacity;
	private int waitlistSpotsTaken;
	private int waitlistSpotsAvailable;
	
	private ArrayList<MeetingPlaceTime> meetingPlaceTimes;
	
	private boolean hasMultipleMeetingPlaceTimes;
	
	private Section() {}
	
	public Section(Term term, Integer CRN) {
		throw new RuntimeException("constructor not yet implemented");
	}
	
	public String getInstructionalMethod() {
		throw new RuntimeException("method not yet implemented");
	}
	
	public Campus getCampus() {
		throw new RuntimeException("method not yet implemented");
	}
	
	public ArrayList<Campus> getCampuses() {
		throw new RuntimeException("method not yet implemented");
	}
	
	public Time getEarliestStartTime() {
		throw new RuntimeException("method not yet implemented");
	}
	
	public Time getLatestEndTime() {
		throw new RuntimeException("method not yet implemented");
	}
	
	public ArrayList<Instructor> getInstructors() {
		throw new RuntimeException("method not yet implemented");
	}
}