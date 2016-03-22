package schedulerutils;

import java.util.ArrayList;

import schedulerentities.Campus;
import schedulerentities.Instructor;
import schedulerentities.MeetingPlaceTime;
import schedulerentities.Section;
import schedulerentities.Session;
import schedulerentities.Subject;
import schedulerentities.Term;

public abstract class ScheduleData {

	// Terms
	public abstract ArrayList<Term> getAllTerms();
	public abstract Term getTerm(String termName);
	public abstract Term getTerm(int termID);
	
	// Subjects
	public abstract ArrayList<Subject> getAllSubjectsForTerm(Term term);
	public abstract ArrayList<Subject> getSubjects(Term term, String subjectName, Integer subjectNum); // for subject name and number with different titles
	public abstract Subject getSubject(Term term, String subjectName, String subjectNum);
	public abstract Subject getSubject(Term term, String subjectName, Integer subjectNum);
	
	// Instructors
	public abstract ArrayList<Instructor> getAllInstructors(Term term);
	public abstract ArrayList<Instructor> getInstructors(Term term, Subject subject);
	public abstract ArrayList<Instructor> getInstructors(Section section);
	
	// Sections
	public abstract ArrayList<Section> getAllSections(Term term);
	public abstract ArrayList<Section> getSections(Subject... subjects);
	public abstract ArrayList<Section> getSections(Instructor... instructors);
	public abstract Section getSection(Integer CRN);
	
	// Meeting Place Times
	public abstract ArrayList<MeetingPlaceTime> getMeetingPlaceTimes(Section section);
	public abstract ArrayList<MeetingPlaceTime> getMeetingPlaceTimes(Integer CRN);
	
	// Campuses
	public abstract ArrayList<Campus> getAllCampuses(Term term);
	public abstract ArrayList<Campus> getCampuses(Subject... subjects);
	public abstract Campus getCampus(Section section); // null if online class
	
	// Sessions
	public abstract ArrayList<Session> getAllSessions(Term term);
	public abstract ArrayList<Session> getAvailableSessions(Term term, Subject subject);
	public abstract Session getSession(int CRN);
	
	// Instructional Methods
	public abstract ArrayList<String> getInstructionalMethods(Term term);
	public abstract ArrayList<String> getInstructionalMethods(Subject... subjects);
	public abstract ArrayList<String> getInstructionalMethod(Section section);
	public abstract ArrayList<String> getInstructionalMethod(Integer CRN);
}
