package schedulerutils;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	 * @return a HashMap of String key-value pairs with the key and value as unique instructor ID and the instructor name
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
	
	public static List<Map<String, String>> getSectionMaps(Integer termId) {
		List<Map<String,String>> ls = new ArrayList<Map<String,String>>();
		
		// TODO change this to pick up all courses
		Document doc = ScheduleParser.getSubjectsResultPageBySubjectNamesOnlyHTML(201601, TIME_OUT, "ARH");
		ArrayList<List<Element>> sections = ScheduleParser.isolateCourseListingsTableRowsHTML(doc);
		
		for(List<Element> list: sections) {
			Map<String, String> section = extractSectionMap(list);
			ls.add(section);
		}
		return ls;
	}

	/**
	 * 
	 * @param list - A list of all html elements that make up a single section
	 * @return HashMap of key value pairs for all properties of a section made of list of the passed elements
	 */
	private static HashMap<String, String> extractSectionMap(List<Element> list) {
		
		if(list.size() !=8) {
			throw new ScheduleDataExtractorException("error extracting section's data from section html");
		} else {
			HashMap<String, String> map = new HashMap<String, String>();
		
			Map<String, String> sectionHeaderMap = getSectionHeaderMap(list.get(2)); // sections header e.g. course name
			map.putAll(sectionHeaderMap);
			
			Map<String, String> sectionStatsMap = getSectionStatsMap(list.get(3));
			map.putAll(sectionStatsMap);
			
			Map<String, String> meetingPlaceTimesMap = meetingPlaceTimesMap(list.get(3));
			map.putAll(meetingPlaceTimesMap);

			return map;
		}
	}

	/**
	 * 
	 * @param element - jsoup Element object representing web page's section header html
	 * @return
	 */
	private static Map<String, String> getSectionHeaderMap(Element element) {
		Map<String, String> map = new HashMap<>();
		
		String text = element.text().trim();
		String[] items = text.split(" - ");
		
		if(items.length != 2) {
			throw new ScheduleDataExtractorException("unexpected html data format while extracting section header data(1)");
		}
		
		String courseInfo = items[0].trim();
		
		String[] course = courseInfo.split("\\s");
		
		if(course.length != 2) {
			throw new ScheduleDataExtractorException("unexpected html data format while extracting section header data(2)");
		}
		
		String subjectAbbreviation = course[0]; // subject abbreviation
		String courseNumAndSection = course[1]; 
		
		String[] subjectNumAndSectionIdArray = courseNumAndSection.trim().split("/");
		
		if(subjectNumAndSectionIdArray.length != 2) {
			throw new ScheduleDataExtractorException("unexpected html data format while extracting section header data(3)");
		}
		
		String subjectNum; // subject number
		if(isInteger(subjectNumAndSectionIdArray[0])) {
			subjectNum = subjectNumAndSectionIdArray[0];
		} else {
			throw new ScheduleDataExtractorException("unexpected html data format while extracting section header data(4)");
		}
		
		String sectionId = subjectNumAndSectionIdArray[1]; // section id
		
		String subjectName = items[1]; // subject name
		
		map.put("SubjectAbbreviation", subjectAbbreviation);
		map.put("SubjectNumber", subjectNum);
		map.put("SectionId", sectionId);
		map.put("SubjectName", subjectName);
		
		return map;
	}
	
	/**
	 * 
	 * @param element - jsoup element object representing the stats html part of a section. stats like number of seats, waitlist availability etc.
	 * @return Map of String key value pairs for each of the stats name and value
	 */
	private static Map<String, String> getSectionStatsMap(Element element) {
		Map<String, String> map = new HashMap<>();
		
		Elements elements = element.select("tbody > tr");
		
		if(elements.size() != 2) {
			throw new ScheduleDataExtractorException("unexpected html data format while extracting section stats data(1)");
		}
		
		Elements sectionStatsHeaders = elements.get(0).select("th");
		Elements sectionStats = elements.get(1).select("td");
		
		if(sectionStatsHeaders.size() != sectionStats.size()) {
			throw new ScheduleDataExtractorException("unexpected html data format while extracting section stats data(2)");
		}
		
		for(int i = 0; i < sectionStatsHeaders.size(); i++) {
			if(sectionStatsHeaders.get(i).text().trim() == "") {
				throw new ScheduleDataExtractorException("unexpected html data format while extracting section stats data(3)");
			} else {
				String key = sectionStatsHeaders.get(i).text().replaceAll("\\s+", "");
				String value = sectionStats.get(i).text().trim().replaceAll("\\s+", "").replaceAll("\u00a0", "");
				
				if(value.length() == 0)
					value = null;
				
				map.put(key, value);
			}
		}		
		
		return map;
	}
	
	
	private static Map<String, String> meetingPlaceTimesMap(Element element) {
		Map<String, String> map = new HashMap<>();
		System.out.println("\n===================Meeting Place Time==================\n");
		System.out.println("\n====================================================\n");
		return map;
	}
	
	
	private static boolean isInteger(String str) {
		
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	private static boolean isDouble(String str) {
		
		try {
			Double.parseDouble(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
