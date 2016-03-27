package schedulerutils;

import java.util.Comparator;

import schedulerentities.Course;
import schedulerentities.Subject;
import schedulerentities.Term;

/**
 * 
 * @author Teddy
 * Comparator to compare Term Objects
 */
public class TermComparator implements Comparator<Term> {

	@Override
	public int compare(Term term1, Term term2) {
		if(term1.getTermID() > term2.getTermID()) {
			return 1;
		} else if(term1.getTermID() < term2.getTermID()) {
			return -1;
		} else {
			return 0;
		}
	}
}

/**
 * 
 * @author Teddy
 * Comparartor to compare Subject Objects
 */
class SubjectComparator implements Comparator<Subject> {
	@Override
	public int compare(Subject subj1, Subject subj2) {
		if(subj1.getSubjectName().compareTo(subj2.getSubjectName()) > 0) {
			return 1;
		} else if(subj1.getSubjectName().compareTo(subj2.getSubjectName()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}

/**
 * 
 * @author Teddy
 * Comparator Object to compare Course object
 */
class CourseComparator implements Comparator<Course> {

	@Override
	public int compare(Course course1, Course course2) {
		if(course1.getSubject().compareTo(course2.getSubject()) > 0) {
			return 1;
		} else if(course1.getSubject().compareTo(course2.getSubject()) < 0) {
			return -1;
		} else {
			if(course1.getCourseNum().compareTo(course2.getCourseNum()) > 0) {
				return 1;
			} else if(course1.getCourseNum().compareTo(course2.getCourseNum()) < 0) {
				return -1;
			} else {
				if(course1.getCourseTitle().compareTo(course2.getCourseTitle()) > 0) {
					return 1;
				} else if(course1.getCourseTitle().compareTo(course2.getCourseTitle()) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}
	
}
