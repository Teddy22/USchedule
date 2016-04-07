package schedulerutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import schedulerentities.Instructor;
import schedulerentities.Subject;
import schedulerentities.Term;
import schedulerutils.ScheduleParser;

public class Tests {

	public static void main(String[] args) throws IOException {
		
		ClassSchedulerData data = new ClassSchedulerData(); // create data object
		
		/**
		 *  get list of terms available on KSU web site
		 */
		ArrayList<Term> terms = data.getAllTerms();		
		//System.out.println(terms);
		
		//Document doc = ScheduleParser.getSubjectsResultPageByCourseNameAndNumberHTML(201601, 0, "CS", 1301);
		
		//HashMap<String, String> map = ScheduleDataExtractor.getSubjectsMap(201601);
		//System.out.println(map);
		//ScheduleParser.getAllSubjectsForTermPageHTML(201601, 0);
		PrintWriter pw = new PrintWriter(new File("stuff.html"));
		
		/*for(Term term: terms) {
			ArrayList<Instructor> instructors = data.getAllInstructors(term);
			// System.out.println("****** " + term + " ******");
			pw.write("****** " + term + " ******\n");
			for(Instructor instructor: instructors) {
				pw.write(instructor.toString() + "\n");
				System.out.println(instructor);
			}
			//System.out.println("\n\n");
			pw.write("\n\n");
			
		}*/
		String x = "<tr><td>&nbsp;</td><td>X</td><td>&nbsp;</td><td>X</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
		
		//Document d = Jsoup.parse(x, "", Parser.xmlParser());
		//Elements e = d.select("td");
		
		//System.out.println(e.size());
		//Document doc = ScheduleParser.getAllSubjectsForTermPageHTML(201601, 0);
		ScheduleDataExtractor datum = new ScheduleDataExtractor();
		datum.getSectionMaps(201601);
		
		//pw.write(doc.toString());
		pw.close();		
		
		
	}
	
	static void create() {
		final int x = 0;
	}

}
