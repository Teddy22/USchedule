package schedulerutils;

import java.util.ArrayList;

import schedulerentities.Campus;
import schedulerentities.Instructor;
import schedulerentities.MeetingPlaceTime;
import schedulerentities.Section;
import schedulerentities.Session;
import schedulerentities.Subject;
import schedulerentities.Course;
import schedulerentities.Term;

public abstract class ScheduleData {

	/**
	 * @return ArrayList of all available Terms as found on web site
	 */
	public abstract ArrayList<Term> getAllTerms();

	
	/**
	 * 
	 * @param term; Term Object
	 * @return an ArrayList of Subjects for the given Term Object (semester)
	 */
	public abstract ArrayList<Subject> getAllSubjects(Term term);
	
	
	/**
	 * 
	 * @param term - Term object representing a semester
	 * @return ArrayList of Course objects
	 */
	public abstract ArrayList<Course> getAllCourses(Term term);
	
	public abstract ArrayList<Course> getSubjects(Term term, Subject subject); // for subject name and number with different titles
	
	
	// Instructors
	/**
	 * 
	 * @param term - Term object
	 * @return ArrayList of all instructors for semester
	 */
	public abstract ArrayList<Instructor> getAllInstructors(Term term);
	
	
	// Sections
	public abstract ArrayList<Section> getAllSections(Term term);
	public abstract ArrayList<Section> getSections(Course... subjects);
	public abstract ArrayList<Section> getSections(Instructor... instructors);
	public abstract Section getSection(Integer CRN);
	
	// Meeting Place Times
	public abstract ArrayList<MeetingPlaceTime> getMeetingPlaceTimes(Section section);
	public abstract ArrayList<MeetingPlaceTime> getMeetingPlaceTimes(Integer CRN);
	
	// Campuses
	public abstract ArrayList<Campus> getAllCampuses(Term term);
	public abstract ArrayList<Campus> getCampuses(Course... subjects);
	public abstract Campus getCampus(Section section); // null if online class
	
	// Sessions
	public abstract ArrayList<Session> getAllSessions(Term term);
	public abstract ArrayList<Session> getAvailableSessions(Term term, Course subject);
	
	
	// Instructional Methods
	public abstract ArrayList<String> getInstructionalMethods(Term term);
	public abstract ArrayList<String> getInstructionalMethods(Course... subjects);
	public abstract ArrayList<String> getInstructionalMethod(Section section);
	public abstract ArrayList<String> getInstructionalMethod(Integer CRN);
	
}
