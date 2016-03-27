package schedulerutils;

import java.io.File;

/**
 * @author Teddy
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.KeyVal;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import schedulerexceptions.SchedulerException;
import schedulerexceptions.SchedulerParserException;

public class ScheduleParser {
	
	private static Document sendPostRequestAndGetDoc(String strURL, ArrayList<KeyVal> params, int timeout)  {
		
		URL url;
		
		try {
		 url = new URL(strURL);
		} catch(MalformedURLException e) {	
			throw new SchedulerParserException("bad URL format");
		}
		
		Document doc;
		
		try {
			doc = Jsoup.connect(url.toString()).data(params).timeout(timeout).maxBodySize(0).post();
		} catch(IOException ioe) {
			doc = Jsoup.parse("");
			throw new SchedulerException("file not found or could not be read");
		} 
		
		return doc;
	}
	/**
	 * 
	 * @param termValue:int value for term e.g 201601 for Spring 2016
	 * 
	 * @return all class listings for given semester
	 * @throws IOException 
	 */
	public static Document selectedTermResultPageHTML(Integer termValue, int timeout) {
		
		ArrayList<KeyVal> params = getDefaultCourseListPostParams(termValue);
		String url = "https://owlexpress.kennesaw.edu/prodban/bwckschd.p_get_crse_unsec";
		Document doc = sendPostRequestAndGetDoc(url, params, timeout);
		// Document doc = Jsoup.connect(url).data(params).timeout(timeout).maxBodySize(0).post();
		
		return doc;
	}
	
	
	/**
	 * 
	 * @param termValue: - value of semester ex. 201601 for Spring 2016
	 * @param timeout:int - if timeout elapses before response is received, an exception is thrown
	 * @param courses - enter one or more courses to display listings
	 * @return Document: a JSOUP Document object of HTML representing provided criteria
	 * @throws IOException 
	 * 
	 */
	public static Document getSubjectsResultPageBySubjectNamesOnlyHTML(Integer termValue, int timeout, String...courses) {
		ArrayList<KeyVal> params = getDefaultCourseListPostParams(termValue);
		
		changeHttpParams(params, "sel_subj", "dummy");
		
		for(int i = 0; i < courses.length; i++) {
			params.add(HttpConnection.KeyVal.create("sel_subj", courses[i]));
		}
		
		String url = "https://owlexpress.kennesaw.edu//prodban/bwckschd.p_get_crse_unsec";
		Document doc = sendPostRequestAndGetDoc(url, params, timeout);
		
		// Document doc = Jsoup.connect(url).data(params).timeout(timeout).header("Accept-Encoding", "gzip, deflate, br").maxBodySize(0).post();
		
		return doc;
	}
	
	
	/**
	 * 
	 * @param termValue:Integer - value of semester ex. 201601 for Spring 2016
	 * @param timeout:int - if timeout elapses before response is received, an exception is thrown. 0 for infinite time
	 * @param courseName:String - course short name ex. CHEM, MATH, CS
	 * @param courseNumber:Integer - number of course ex. 1211 for CHEM 1211
	 * 
	 * @return Document: a JSOUP Document object with HTML representing listing of given course name and number
	 * @throws IOException 
	 * 
	 */
	public static Document getSubjectsResultPageByCourseNameAndNumberHTML(Integer termValue, int timeout, String courseName, Integer courseNumber) {
		return getSubjectsResultPageByCourseNameAndNumberHTML(termValue, timeout, courseName, courseNumber.toString());
	}

	
	/**
	 * 
	 * @param termValue:Integer - value of semester ex. 201601 for Spring 2016
	 * @param timeout:int - if timeout elapses before response is received, an exception is thrown. 0 for infinite time
	 * @param courseName:String - course short name ex. CHEM, MATH, CS
	 * @param courseNumber:String - number string for a course ex. 1211L for CHEM 1211L
	 * 
	 * @return Document: a JSOUP Document object with HTML representing listing of given course name and number
	 * @throws IOException 
	 * 
	 */
	public static Document getSubjectsResultPageByCourseNameAndNumberHTML(Integer termValue, int timeout, String courseName, String courseNumber) {
		ArrayList<KeyVal> params = getDefaultCourseListPostParams(termValue);
		
		changeHttpParams(params, "sel_subj", "dummy");
		removeHttpParams(params, "sel_crse");
		HashMap<String, String> newParams = new HashMap<String, String>();
		newParams.put("sel_subj", courseName);
		newParams.put("sel_crse", courseNumber);
		
		addHttpParams(params, newParams);
		
		String url = "https://owlexpress.kennesaw.edu//prodban/bwckschd.p_get_crse_unsec";
		Document doc = sendPostRequestAndGetDoc(url, params, 0);
		//Document doc = Jsoup.connect(url).data(params).timeout(timeout).header("Accept-Encoding", "gzip, deflate, br").maxBodySize(0).post();
		
		return doc;
	}
	
	
	public static Document getSubjectResultsPageByInstructor(Integer termValue, int timeout, String instructorID) {
		
		if(instructorID == "") {
			throw new SchedulerParserException("empty string found for instructor id");
		}
		
		ArrayList<KeyVal> params = getDefaultCourseListPostParams(termValue);
		
		changeHttpParams(params, "sel_instr", "dummy");
		
		Map<String, String> additionalParams = new HashMap<String, String>();
		additionalParams.put("sel_instr", instructorID);
		
		addHttpParams(params, additionalParams);
		
		for(KeyVal kv: params) {
			System.out.println(kv.key() + ": " + kv.value());
		}
		//System.exit(0);
		String url = "https://owlexpress.kennesaw.edu//prodban/bwckschd.p_get_crse_unsec";
		Document doc = sendPostRequestAndGetDoc(url, params, timeout);
	
		return doc;
	}
	
	public static Document getAllSubjectsForTermPageHTML(Integer termValue, int timeout) {
		ArrayList<KeyVal> params = getDefaultCourseListPostParams(termValue);
		
		String url = "https://owlexpress.kennesaw.edu//prodban/bwckschd.p_get_crse_unsec";
		Document doc = sendPostRequestAndGetDoc(url, params, timeout);
	
		return doc;
	}
	
	
	/**
	 * 
	 * @param html:String - HTML from a course listing page
	 * @return ArrayList<List<Elements>> - an ArrayList which contains Lists, for which each of the List
	 * consists of all the table rows associated with an individual course
	 */
	public static ArrayList<List<Element>> isolateCourseListingsTableRowsHTML(String html) {
		Document doc = Jsoup.parse(html);
		return isolateCourseListingsTableRowsHTML(doc);
	}
	

	/**
	 * 
	 * @param doc:Document - JSoup Document object with HTML from a course listing page
	 * @return ArrayList<List<Elements>> - an ArrayList which contains Lists, for which each of the List
	 * consists of all the table rows associated with an individual course
	 */
	public static ArrayList<List<Element>> isolateCourseListingsTableRowsHTML(Document doc) {
		
		Elements elements = Jsoup.parse("clear").getAllElements();
		elements.clear();
		
		try {
			elements.clear();
			elements = doc.select("div.pagebodydiv").select("table:contains(Sections Found) > tbody").get(0).children();
		} catch(IndexOutOfBoundsException e) {
			elements.clear();
		}
		
		
		if(elements.size() % 8 != 0) {
			throw new SchedulerParserException("unexpected number of course detail table rows found in html from course listing page");
		}
		
		ArrayList<List<Element>> result = new ArrayList<List<Element>>();
		
		for(int i = 0; i < elements.size(); i+=8) {
			List<Element> subject = elements.subList(i, i+8);
			
			if(subject.size() != 8) {
				throw new SchedulerParserException("unexpected number of table rows for subject while isolating course");
			}
			
			result.add(subject);
		}
		
		return result;
	}
	
	
	/**
	 * 
	 * @param termValue:Integer - represents term/semester e.g. 201601 for Spring 2016
	 * @return ArrayList - list of default JSoup KeyVal representing POST parameters for sending POST requests for course listings
	 * 					   the default POST parameters grabs every course for given term/semester
	 */
	private static ArrayList<KeyVal> getDefaultCourseListPostParams(Integer termValue) {
		ArrayList<KeyVal> params = new ArrayList<>();
		
		params.add(HttpConnection.KeyVal.create("term_in", termValue.toString()));
		params.add(HttpConnection.KeyVal.create("sel_subj", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_day", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_schd", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_insm", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_camp", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_levl", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_sess", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_instr", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_ptrm", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_attr", "dummy"));
		params.add(HttpConnection.KeyVal.create("sel_subj", "%"));
		params.add(HttpConnection.KeyVal.create("sel_crse", ""));
		params.add(HttpConnection.KeyVal.create("sel_title", ""));
		params.add(HttpConnection.KeyVal.create("sel_insm", "%"));
		params.add(HttpConnection.KeyVal.create("sel_from_cred", ""));
		params.add(HttpConnection.KeyVal.create("sel_to_cred", ""));
		params.add(HttpConnection.KeyVal.create("sel_camp", "%"));
		params.add(HttpConnection.KeyVal.create("sel_levl", "%"));
		params.add(HttpConnection.KeyVal.create("sel_ptrm", "%"));
		params.add(HttpConnection.KeyVal.create("sel_instr", "%"));
		params.add(HttpConnection.KeyVal.create("begin_hh", "0"));
		params.add(HttpConnection.KeyVal.create("begin_mi", "0"));
		params.add(HttpConnection.KeyVal.create("begin_ap", "a"));
		params.add(HttpConnection.KeyVal.create("end_hh", "0"));
		params.add(HttpConnection.KeyVal.create("end_mi", "0"));
		params.add(HttpConnection.KeyVal.create("end_ap", "a"));
		
		return params;
	}

	
	/**
	 * 
	 * @param params:ArrayList<KeyVal> - an ArrayList of JSoup KeyVal objects representing HTTP parameters
	 * @param keys
	 */
	private static void removeHttpParams(ArrayList<KeyVal> params, String...keys) {
		HashSet<String> prop = new HashSet<String>(Arrays.asList(keys));
		Iterator<KeyVal> iterator = params.iterator();
		
		while(iterator.hasNext()) {
			if(prop.contains(iterator.next().key())) {
				iterator.remove();
			}
		}
	}
	
	
	/**
	 * 
	 * @param params:ArrayList<KeyVal> - existing or ArrayList of KeyVal HTTP parameters
	 * @param newParams:Map<String, String> - Map of new parameters to add to default HTTP parameters
	 */
	private static void addHttpParams(ArrayList<KeyVal> params, Map<String, String> newParams) {
		
		for(String key: newParams.keySet()) {
			params.add(HttpConnection.KeyVal.create(key, newParams.get(key)));
		}
	}
	
	
	/**
	 * 
	 * @param params
	 * @param key
	 * @param newValue
	 */
	private static void changeHttpParams(ArrayList<KeyVal> params, String key, String newValue) {
		
		removeHttpParams(params, key);
		
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(key, newValue);
		
		addHttpParams(params, hashMap);
	}
	
	
	/**
	 * 
	 * @param termValue:Integer Object representing semester
	 * @param timeout:int timeout value in milliseconds
	 * @return JSoup Elements - list of subject name options for given semester as HTML
	 * @throws IOException
	 */
	public static Elements getSubjectSelectOptionsHTML(Integer termValue, int timeout) {
		Document doc = getTermParamSelectionsPageHTML(termValue, timeout);
		Elements elements = doc.getElementById("subj_id").select("option");
		
		return elements;
	}
	
	
	/**
	 * 
	 * @param termValue:Integer Object representing semester
	 * @param timeout:int timeout value in milliseconds
	 * @return JSoup Elements - list of instructor options for given semester as HTML
	 * @throws IOException
	 */
	public static Elements getInstructorSelectOptionsHTML(Integer termValue, int timeout) {
		Document doc = getTermParamSelectionsPageHTML(termValue, timeout);
		Elements elements = doc.getElementById("instr_id").select("option");
		
		return elements;
	} 
	
	
	/**
	 * 
	 * @return term options from at KSU's select term web site
	 * @throws IOException
	 */
	public static Elements getTermSelectOptionsHTML() {
		Document doc = getSchoolTermSelectWebPageHTML();
		
		Elements elements = doc.getElementById("term_input_id").select("option");
		
		return elements;
	}
	
	
	/**
	 * 
	 * @return a JSoup Document representing the web page that let's you select a term/semester
	 * @throws IOException
	 */
	private static Document getSchoolTermSelectWebPageHTML() {
		String url = "https://owlexpress.kennesaw.edu/prodban/bwckschd.p_disp_dyn_sched";
		Document doc = getPageHTMLDoc(url);
		
		return doc;
	}
	
	
	/**
	 * 
	 * @param termValue:Integer Object representing semester
	 * @param timeout:int timeout value in milliseconds
	 * @return JSoup Document HTML representing the school web page with multiple class and class related selections
	 *         related to the semester
	 * @throws IOException
	 */
	private static Document getTermParamSelectionsPageHTML(Integer termValue, int timeout) {
		
		ArrayList<KeyVal> params = new ArrayList<>();
	
		params.add(HttpConnection.KeyVal.create("p_calling_proc", "bwckschd.p_disp_dyn_sched"));
		params.add(HttpConnection.KeyVal.create("p_term", termValue.toString()));
		
		
		String url = "https://owlexpress.kennesaw.edu/prodban/bwckgens.p_proc_term_date";
		
		Document doc = sendPostRequestAndGetDoc(url, params, 0);
		return doc;
	}
	
	
	/**
	 * 
	 * @param strURL:URL object representing an existing web page URL
	 * @return JSoup Document representing the HTML for retrieved web page
	 * @throws IOException
	 */
	private static Document getPageHTMLDoc(String strURL) {
		URL url;
		
		try {
		 url = new URL(strURL);
		} catch(MalformedURLException e) {	
			throw new SchedulerParserException("bad URL format");
		}
		
		Document doc;
		
		try{
			doc = Jsoup.connect(url.toString()).get();
		} catch(IOException ioe) {
			doc = Jsoup.parse("<h1>Error</h1>");
			
		}
		
		return doc;
	}
	
}
