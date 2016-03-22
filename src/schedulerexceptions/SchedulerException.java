package schedulerexceptions;

public class SchedulerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9179083221214482024L;
	
	@SuppressWarnings("unused")
	private SchedulerException() {	}
	
	
	public SchedulerException(String message) {
		super(message);
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
