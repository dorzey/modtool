package uk.ac.liv.csc.semanticweblab.modtool.utils;


/**
 * This class can be used to time how long a process takes to compute.
 * 
 * @author pdoran
 */
public class ProcessTimer {
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Variables~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ~~~~
	/**
	 * The time when the process begun.
	 */
	private long startTime;
	/**
	 * The time when the process ended.
	 */
	private long endTime;

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Constructor~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ~~~~
	public ProcessTimer() {
	}

	/**
	 * Instantiates the {@link #startTime} time.
	 */
	public void setStartTime() {
		this.startTime = System.nanoTime();
	}

	/**
	 * Instantiates the {@link #endTime} time.
	 */
	public void setEndTime() {
		this.endTime = System.nanoTime();
	}

	/**
	 * Gets the time the process {@link #setStartTime started}.
	 * 
	 * @return {@link #setStartTime setStartTime} - the time the process was
	 *         started.
	 */
	public long getStartTime() {
		return this.startTime;
	}

	/**
	 * Gets the time the process {@link #setEndTime ended}.
	 * 
	 * @return {@link #setEndTime setEndTime} - the time the process was ended.
	 */
	public long getEndTime() {
		return this.endTime;
	}

	/**
	 * Calculates the difference between the two times. Returns the time as a
	 * string.
	 * 
	 * @return time the time in a string, ready to display, format depends on
	 *         time length.
	 */
	public String getTimeDifference() {
		long z = this.endTime - this.startTime;
		return z + " nanoseconds";
	}
}// End class ProcessTimer.