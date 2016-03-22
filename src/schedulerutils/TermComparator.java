package schedulerutils;

import java.util.Comparator;

import schedulerentities.Term;

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
