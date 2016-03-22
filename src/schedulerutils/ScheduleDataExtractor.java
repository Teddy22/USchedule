package schedulerutils;

import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import schedulerexceptions.ScheduleDataExtractorException;

public class ScheduleDataExtractor {
	
	public static HashMap<String, String> extractTermsMap() {
		
		HashMap<String, String> terms = new HashMap<>();
		
		Elements elements = ScheduleParser.getTermSelectOptionsHTML();
		
		for(Element element: elements) {
			String termID = element.attr("value").trim();
			String termName = element.text().replace("(View only)", "").replace("None", "").replace("Semester", "").replaceAll("\\s+", " ").trim();
			
			if(termID != "" && termName != "")
				terms.put(termID, termName);
		}
		return terms;
	}
	
	public static HashMap<String, String> extractTermsMap(String season, int year) { 
		Elements elements = ScheduleParser.getTermSelectOptionsHTML();
		elements = elements.select(season + " " + year);
		
		HashMap<String, String> term = new HashMap();
		
		if(elements.size() == 1) {
			String termID = elements.get(0).attr("value").trim();
			String termName = elements.get(0).text().replace("(View only)", "").replace("None", "").replace("Semester", "").replaceAll("\\s+", " ").trim();
		}
		
		return term;
	}
}
