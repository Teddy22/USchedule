package schedulerutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import schedulerentities.Term;
import schedulerutils.ScheduleParser;

public class Tests {

	public static void main(String[] args) throws IOException {
		
		ClassSchedulerData data = new ClassSchedulerData(); // create data object
		
		/**
		 *  get list of terms available on KSU web site
		 */
		ArrayList<Term> terms = data.getAllTerms();		
		System.out.println(terms);
		
		
		
		PrintWriter pw = new PrintWriter(new File("stuff.txt"));
		pw.close();		
		
	}

}
