package schedulerutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.jsoup.nodes.Document;

import schedulerentities.Campus;
import schedulerentities.Instructor;
import schedulerentities.MeetingPlaceTime;
import schedulerentities.Section;
import schedulerentities.Session;
import schedulerentities.Subject;
import schedulerentities.Course;
import schedulerentities.Term;

public class ClassSchedulerData extends ScheduleData {

	HashMap<Integer,Document> termDocs;
	
	@Override
	public ArrayList<Term> getAllTerms() {
		HashMap<String, String> termsMap = ScheduleDataExtractor.extractTermsMap();
		ArrayList<Term> termList = new ArrayList<Term>();
		
		for(String key: termsMap.keySet()) {
			int termID = Integer.parseInt(key);
			String termName = termsMap.get(key);
			
			Term term = new Term(termID, termName);
			
			termList.add(term);
		}
		
		Collections.sort(termList, new TermComparator().reversed());
		
		return termList;
	}


	@Override
	public ArrayList<Course> getAllCourses(Term term) {
		return null;
	}

	@Override
	public ArrayList<Course> getSubjects(Term term, Subject subject) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param Term object
	 * @return ArrayList of Instructors for given term
	 */
	@Override
	public ArrayList<Instructor> getAllInstructors(Term term) {
		HashMap<String, String> map = ScheduleDataExtractor.getInstructorsMap(term.getTermID());
		ArrayList<Instructor> instructors = new ArrayList<>();
		
		for(String key: map.keySet()) {
			Instructor instructor = new Instructor(key, map.get(key));
			instructors.add(instructor);
		}
		return instructors;
	}
	

	@Override
	public ArrayList<Section> getAllSections(Term term) {
		
		return null;
	}

	@Override
	public ArrayList<Section> getSections(Course... subjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Section> getSections(Instructor... instructors) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Section getSection(Integer CRN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MeetingPlaceTime> getMeetingPlaceTimes(Section section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<MeetingPlaceTime> getMeetingPlaceTimes(Integer CRN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Campus> getAllCampuses(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Campus> getCampuses(Course... subjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Campus getCampus(Section section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Session> getAllSessions(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Session> getAvailableSessions(Term term, Course subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getInstructionalMethods(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getInstructionalMethods(Course... subjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getInstructionalMethod(Section section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getInstructionalMethod(Integer CRN) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param term: Term object
	 * @return Subject Objects for subjects in a Term
	 */
	@Override
	public ArrayList<Subject> getAllSubjects(Term term) {
		HashMap<String, String> map = ScheduleDataExtractor.getSubjectsMap(term.getTermID());
		
		ArrayList<Subject> subjects= new ArrayList<>();
		
		for(String key: map.keySet()) {
			Subject subject = new Subject(key, map.get(key));
			subjects.add(subject);
		}
		
		return subjects;
	}

}
