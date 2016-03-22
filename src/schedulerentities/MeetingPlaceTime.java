package schedulerentities;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

public class MeetingPlaceTime {
	private int sectionCRN;
	private Campus campus;
	private String instructionalMethod;
	private String meetingPlace; // e.g. Social Sciences Bldg Room 3010, 	Online Course Information
	private ArrayList<DayOfWeek> daysOfWeek;
	private Date startDate;
	private Date endDate;
	private Time startTime;
	private Time endTime;
	private String meetingPlaceType; // can be null
	private ArrayList<Instructor> instructors;
	
	
	public MeetingPlaceTime(int CRN) {
		// TODO implement constructor
		throw new RuntimeException("constructor not yet implemented");
	}


	public int getSectionCRN() {
		return this.sectionCRN;
	}


	public Campus getCampus() {
		return this.campus;
	}


	public String getInstructionalMethod() {
		return this.instructionalMethod;
	}


	public String getMeetingPlace() {
		return this.meetingPlace;
	}


	public ArrayList<DayOfWeek> getDaysOfWeek() {
		return this.daysOfWeek;
	}


	public Date getStartDate() {
		return this.startDate;
	}


	public Date getEndDate() {
		return this.endDate;
	}


	public Time getStartTime() {
		return this.startTime;
	}


	public Time getEndTime() {
		return endTime;
	}


	public String getMeetingPlaceType() {
		return this.meetingPlaceType;
	}


	public ArrayList<Instructor> getInstructors() {
		return this.instructors;
	}
	
	
}
