package schedulerutils;

import java.rmi.dgc.Lease;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import schedulerentities.Term;
import schedulerexceptions.ScheduleDataExtractorException;

public class ScheduleDataExtractor {
	
	/**
	 * 
	 * @return a HahMap of termID and termName Strings as key value pairs
	 */
	private static final int TIME_OUT = 0; // time out value when making HTTP calls
	
	/**
	 * 
	 * @return HashpMap of all semesters(Terms) currently found in web site in string key value pairs
	 * where key is the term id and value is the term name 
	 */
	public static HashMap<String, String> extractTermsMap() {
		
		HashMap<String, String> terms = new HashMap<>();
		
		Elements elements = ScheduleParser.getTermSelectOptionsHTML();
		
		for(Element element: elements) {
			String termID = element.attr("value").trim();
			String termName = element.text().replace("(View only)", "").replace("None", "").replace("Semester", "").replaceAll("\\s+", " ").trim();
			
			if(termID != "" && termName != "") {
				terms.put(termID, termName);
			}
		}
		return terms;
	}
	
	
	/**
	 * 
	 * @param termID - Integer representing the Term ID - e.g. 201601 for Spring 2016
	 * @return
	 */
	public static HashMap<String, String> getSubjectsMap(Integer termID) {
		Elements elements = ScheduleParser.getSubjectSelectOptionsHTML(termID, TIME_OUT);
		HashMap<String, String> subjects = new HashMap<>();
		
		for(Element element: elements) {
			String[] subj = element.text().split("-");
			
			if(subj.length == 1) {
				if(subj[0] != "All") {
					subjects.put(subj[0].trim().replaceAll("\\s+", " "), subj[0].trim().replaceAll("\\s+", " "));
				}
			} else if(subj.length == 2) {
				subjects.put(subj[0].trim().replaceAll("\\s+", " "), subj[1].trim().replaceAll("\\s+", " "));
			} else if(subj.length > 2) {
				String longStr = "";
				for(int i = 1; i < subj.length; i++) {
					longStr += subj[i] + "-";
				}
				if(!Character.isAlphabetic(longStr.charAt(longStr.length()-1))) {
					subjects.put(subj[0].trim().replaceAll("\\s+", " "), longStr.trim().replaceAll("\\s+", " ").substring(0, longStr.length()-1));
				} else {
					subjects.put(subj[0], longStr);
				}
			} else {
				throw new ScheduleDataExtractorException("error exracting the term's subject names from HTML");
			}
		}
		
		return subjects;
	}
	
	/**
	 * 
	 * @param termId
	 * @return
	 */
	public static HashMap<String, String> getInstructorsMap(Integer termId) {
		Elements elements = ScheduleParser.getInstructorSelectOptionsHTML(termId, TIME_OUT);
		
		HashMap<String, String> instructors = new HashMap<>();
		
		for(Element element: elements) {
			String instructorId = element.attr("value").trim();
			String instructorName = element.text().trim().replace("//s+", " ");
			
			if(instructorId != "" && instructorName != "") {
				instructors.put(instructorId, instructorName);
			}
		}
		
		return instructors;
	}
	
	public static List<Element> getSectionsMap(Integer termId) {
		HashMap<String,String> map = new HashMap<>();
		
		Document doc = ScheduleParser.getAllSubjectsForTermPageHTML(termId, 0);
		ArrayList<List<Element>> sections = ScheduleParser.isolateCourseListingsTableRowsHTML(doc);
		
		List<Elements> els = new ArrayList<>();
		
		for(List<Element> list: sections) {
			for(Element el: list) {
				System.out.println(el);
			}
			break;
		}
		return null;
	}
}
