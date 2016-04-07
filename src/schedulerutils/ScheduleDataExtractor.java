package schedulerutils;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import schedulerexceptions.ScheduleDataExtractorException;

/**
 * @author Teddy
 * 
 * This class contains various methods for extracting schedule data from the parsed HTML of the web page
 */
public class ScheduleDataExtractor {
	
	/**
	 * time out value when making HTTP calls
	 */
	private static final int TIME_OUT = 0;
	
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
	 * @return a HashMap<String, String> with the subject abbreviations and subject full names as key value pairs
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
	
	public List<Map<String, String>> getSectionMaps(Integer termId) {
		
		List<Map<String,String>> ls = new ArrayList<Map<String,String>>();
		
		// TODO change this to pick up all courses
		Document doc = ScheduleParser.getSubjectsResultPageByCourseNameAndNumberHTML(termId, TIME_OUT, "CS", 3410);
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
	private HashMap<String, String> extractSectionMap(List<Element> list) {
		assert(list != null);
		if(list.size() !=8) {
			throw new ScheduleDataExtractorException("error extracting section's data from section html");
		} else {
			HashMap<String, String> map = new HashMap<String, String>();
		
			Map<String, String> sectionHeaderMap = getSectionHeaderMap(list.get(2)); // sections header e.g. course name
			map.putAll(sectionHeaderMap);
			
			Map<String, String> sectionStatsMap = getSectionStatsMap(list.get(3));
			map.putAll(sectionStatsMap);
			
			Map<String, String> meetingPlaceTimesMap = meetingPlaceTimesMap(list.get(4));
			map.putAll(meetingPlaceTimesMap);

			return map;
		}
	}

	/**
	 * 
	 * @param element - jsoup Element object representing web page's section header html
	 * @return
	 */
	private Map<String, String> getSectionHeaderMap(Element element) {
		
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
	private Map<String, String> getSectionStatsMap(Element element) {

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
	
	
	/**
	 * 
	 * @param element
	 * @return a HasMap of Meeting Place Time data
	 */
	private Map<String, String> meetingPlaceTimesMap(Element element) {
		Map<String, String> map = new HashMap<>();
		
		System.out.println("\n===================Meeting Place Times==================\n");
		Elements elements = element.select("tbody > tr[align!=center]");
		ArrayList<Integer> ints = new ArrayList<>();
		
		for(int i = 0; i < elements.size(); i++) { // find isolated and duplicated days of week table rows
			if(isDaysOfWeekTabeRow(elements.get(i))) {
				ints.add(i);
			}
		}
		
		for(int i: ints) { // clear isolated and duplicated days of week table rows
			elements.remove(i);
		}
		
		if(elements.size() < 2) {
			throw new ScheduleDataExtractorException("unexpected number of rows of data while extracting meeting place times data");
		}
		
		List<String> meetingPlaceTimesHeaders = getMeetingPlaceTimesHeaders(elements);
		List<String> meetingPlaceTimesData = getMeetingPlaceTimesData(elements);
		
		/*if(meetingPlaceTimesHeaders.size() != meetingPlaceTimesData.size()) {
			throw new ScheduleDataExtractorException("unexpected number of rows of data while extracting meeting place times data");
		}*/
		System.out.println("\n=================================================================================\n");
		
		return map;
	}
	
	
	private List<String> getMeetingPlaceTimesHeaders(Elements elements) {
		List<String> list = new ArrayList<String>();
		
		if(elements.size() < 2) {
			throw new ScheduleDataExtractorException("unexpected number of rows of data while extracting meeting place times data");
		}
		
		Elements headers = elements.get(0).select("th");
		
		for(Element header: headers) {
			String text = header.text().trim().replaceAll("\\s+", "");
			list.add(text);
		}
		
		return list;
	}
	
	private List<String> getMeetingPlaceTimesData(Elements elements) {
		List<String> list = new ArrayList<String>();
		
		if(elements.size() < 2) {
			throw new ScheduleDataExtractorException("unexpected number of rows of data while extracting meeting place times data");
		}
		
		List<Element> data = elements.subList(1, elements.size());
		
		for(Element el: data) {
			list = extractMeetingPlaceTimesData(el);
		}
		return list;
	}

	
	private List<String> extractMeetingPlaceTimesData(Element element) {
		List<String> list = new ArrayList<String>();
		
		Elements elements = element.children();
		
		String[] ls = new String[elements.size()];
		
		for(Element dataRow: elements) {
			String text;
			if(dataRow.select("table").size() > 0) {
				text = extractDaysOfWeekString(dataRow);
			} else {
				text = dataRow.text().trim().replaceAll("\\s+", " ").replaceAll("\u00a0", "");
			}
			System.out.println(text);
		}
		
		
		
		return list;
	}

	private String extractDaysOfWeekString(Element element) {
		
		String result = "";
		
		Elements elements = element.select("tbody > tr");
		
		if(elements.size() < 2) {
			throw new ScheduleDataExtractorException("unexpected number of rows of data while extracting days of week data (1)");
		}
		
		Elements daysOfWeekTitles = elements.get(0).select("td");
		Elements daysOfWeekMarks = elements.get(1).select("td");
		
		if(daysOfWeekTitles.size() != daysOfWeekMarks.size()) {
			throw new ScheduleDataExtractorException("unexpected number of rows of data while extracting days of week data (2)");
		}
		
		for(int i = 0; i < daysOfWeekTitles.size(); i++) {
			String dayOfWeekTitle = daysOfWeekTitles.get(i).text().trim().replaceAll("\\s+", " ").replaceAll("\u00a0", "").replace("&nbsp;", "");
			String dayOfWeekMark = daysOfWeekMarks.get(i).text().trim().replaceAll("\\s+", " ").replaceAll("\u00a0", "").replaceAll("&nbsp;", "");
			
			if(dayOfWeekTitle.equals("")) {
				throw new ScheduleDataExtractorException("unexpected number of rows of data while extracting days of week data (3)");
			} else if(dayOfWeekMark == "") {
				dayOfWeekMark = "no";
			} else {
				result += dayOfWeekTitle + ":" + dayOfWeekMark + ";";
			}
		}
		return result;
	}


	private boolean isDaysOfWeekTabeRow(Element el) {
		if(el.tagName() != "tr" && el.select("td").size() == 7 && (el.text().contains("X") || el.text().contains("\u00a0"))) {
			return true;
		} else if(el.select("td").size() != 7) {
			return false;
		} else if(el.select("td").size() == 7 && (el.text().contains("X") || el.text().contains("\u00a0")))
			return true;
		else {
			throw new ScheduleDataExtractorException("unexpected data format while checking for day of week condition");
		}
	}


	private boolean isInteger(String str) {
		
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	
	private boolean isDouble(String str) {
		
		try {
			Double.parseDouble(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}
