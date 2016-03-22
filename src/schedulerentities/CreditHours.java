package schedulerentities;

import schedulerexceptions.SchedulerEntityException;

/**
 * 
 * @author Teddy
 * 
 * Object represents credit hours which can belong to a particular section. 
 * CreditHours can either be a single value or a range of two values with an upper and lower bound (variable)
 *
 */
public class CreditHours {
	private boolean hasRange;
	private int lowerBound;
	private int upperBound;
	
	private CreditHours() {}

	public CreditHours(boolean hasRange, int lowerBound, int upperBound) {
		if(hasRange) {
			if(lowerBound < 0 || upperBound < 0) {
				throw new SchedulerEntityException("credit hour values should be zero or greater");
			}
			
			if(upperBound > lowerBound) {
				this.lowerBound = lowerBound;
				this.upperBound = upperBound;
				this.hasRange = hasRange;
			} else {
				throw new SchedulerEntityException("error creating CreditHours object. lower bound should be less than the upperbound");
			}
		} else {
			throw new SchedulerEntityException("illegal contructor used to initialize a CreditHours object without a range");
		}
	}

	public CreditHours(boolean hasRange, int creditHoursValue) {
		if(hasRange) {
			throw new SchedulerEntityException("illegal contructor used to initialize a CreditHours object with a range");
		} else if(creditHoursValue < 0) {
			throw new SchedulerEntityException("credit hour values should be zero or greater");
		} else {
			this.lowerBound = creditHoursValue;
			this.upperBound = creditHoursValue;
			this.hasRange = hasRange;
		}
			
	}

	public boolean hasRange() {
		return this.hasRange;
	}

	public int getLowerBound() {
		return this.lowerBound;
	}

	public int getUpperBound() {
		return this.upperBound;
	}
	
	
}
