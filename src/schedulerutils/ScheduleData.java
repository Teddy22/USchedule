package schedulerutils;

import java.util.ArrayList;

import schedulerentities.Campus;
import schedulerentities.Instructor;
import schedulerentities.MeetingPlaceTime;
import schedulerentities.Section;
import schedulerentities.Session;
import schedulerentities.Course;
import schedulerentities.Term;

public abstract class ScheduleData {

	// Terms
	public abstract ArrayList<Term> getAllTerms();
	public abstract Term getTerm(String termName);
	public abstract Term getTerm(int termID);
	
	// Subjects
	public abstract ArrayList<Course> getAllSubjectsForTerm(Term term);
	public abstract ArrayList<Course> getSubjects(Term term, String subjectName, Integer subjectNum); // for subject name and number with different titles
	public abstract Course getSubject(Term term, String subjectName, String subjectNum);
	public abstract Course getSubject(Term term, String subjectName, Integer subjectNum);
	
	// Instructors
	public abstract ArrayList<Instructor> getAllInstructors(Term term);
	public abstract ArrayList<Instructor> getInstructors(Term term, Course subject);
	public abstract ArrayList<Instructor> getInstructors(Section section);
	
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
