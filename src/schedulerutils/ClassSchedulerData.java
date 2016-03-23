package schedulerutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import schedulerentities.Campus;
import schedulerentities.Instructor;
import schedulerentities.MeetingPlaceTime;
import schedulerentities.Section;
import schedulerentities.Session;
import schedulerentities.Course;
import schedulerentities.Term;

public class ClassSchedulerData extends ScheduleData {

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
	public Term getTerm(String termName) {
		
		String[] args = termName.trim().split("\\s+");
		
		/*if(args.length == 2 && args[1].) {
			ScheduleDataExtractor.extractTermsMap(args[0], args[1]);
			
			Term term = new Term(termID, termName);
		}
		
		return null;*/
		throw new RuntimeException();
	}

	@Override
	public Term getTerm(int termID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> getAllSubjectsForTerm(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Course> getSubjects(Term term, String subjectName, Integer subjectNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course getSubject(Term term, String subjectName, String subjectNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Course getSubject(Term term, String subjectName, Integer subjectNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Instructor> getAllInstructors(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Instructor> getInstructors(Term term, Course subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Instructor> getInstructors(Section section) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Section> getAllSections(Term term) {
		// TODO Auto-generated method stub
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

}
