package util;

/**
 * This class represent a time lapse in second and all the useful methods
 */
public class TimeLapse
{
	/**
	 * The approximation that we admit for the time in second.
	 * Here it's 5 seconds.
	 */
	private final static long epsilon = 5;


	/**
	 * The starting time of the lapse in second
	 */
	private long starting;


	/**
	 * the ending time of the lapse in second
	 */
	private long ending;




	public TimeLapse(long starting, long ending)
	{
		this.starting = starting;
		this.ending = ending;
	}


	/**
	 * Determine if this lapse include the lapse {@code arg}
	 * @param arg time lapse to be check
	 * @return true if the lapse {@code arg} is in this time lapse
	 */
	public boolean include(TimeLapse arg)
	{
		if(this.starting <= (arg.starting + epsilon) && this.ending >= (arg.ending - epsilon))
			return true;
		return false;
	}

	/**
	 * Determine if this lapse include the interval [s,e]
	 * @param s the starting time (in second)
	 * @param e the ending time (in second)
	 * @return true if the interval [s,e] is in this lapse, else otherwise.
	 */
	public boolean include(long s, long e)
	{
		if(this.starting <= (s + epsilon) && this.ending >= (e - epsilon))
			return true;
		return false;
	}

	/**
	 * Determine if {@code arg} is in the lapse
	 * @param arg a time in second
	 * @return true if {@code arg} is in the lapse
	 */
	public boolean include(long arg)
	{
		if(this.starting <= (arg + epsilon) && this.ending >= (arg - epsilon))
			return true;
		return false;
	}


	/**
	 * Determine if this lapse is in the lapse {@code arg}.
	 * @param arg time lapse to be check
	 * @return true if this lapse is in the lapse {@code arg}, else otherwise.
	 */
	public boolean isIn(TimeLapse arg)
	{
		if(this.starting >= (arg.starting - epsilon) && this.ending <= (arg.ending + epsilon))
			return true;
		return false;
	}

	/***
	 * Determine if this lapse is in the interval {@code [s,e]}
	 * @param s the starting time (in second)
	 * @param e the ending time (in second)
	 * @return true if this lapse is in the interval {@code [s,e]}, else otherwise.
	 */
	public boolean isIn(long s, long e)
	{
		if(this.starting >= (s - epsilon) && this.ending <= (e + epsilon))
			return true;
		return false;
	}

	/**
	 *
	 * @param t Time lapse to be checked
	 * @return true if this time lapse start after the time lapse {@code t}
	 */
	public boolean startAfter(TimeLapse t)
	{
		if(this.starting >= (t.starting - epsilon))
			return true;
		return false;
	}


	/**
	 * Return true if {@code t1} is after {@code t2} (bigger), false otherwise
	 * @param t1 first time in second
	 * @param t2 second time in second
	 * @return true if {@code t1} is after {@code t2}, false otherwise
	 */
	public static boolean isAfter(long t1, long t2)
	{
		if(t1 >= (t2 - epsilon))
			return true;
		return false;
	}

	/**
	 *
	 * @param t Time lapse to be checked
	 * @return true if this time lapse start before the time lapse {@code t}
	 */
	public boolean startBefore(TimeLapse t)
	{
		if(this.starting <= (t.starting + epsilon))
			return true;
		return false;
	}

	/**
	 * Return true if {@code t1} is before {@code t2} (smaller), false otherwise
	 * @param t1 first time in second
	 * @param t2 second time in second
	 * @return true if {@code t1} is before {@code t2}, false otherwise
	 */
	public static boolean isBefore(long t1, long t2)
	{
		if(t1 <= (t2 + epsilon))
			return true;
		return false;
	}

	/**
	 *
	 * @param t Time lapse to be checked
	 * @return true if this time lapse start after the time lapse {@code t}
	 */
	public boolean endAfter(TimeLapse t)
	{
		if(this.ending > (t.ending - epsilon))
			return true;
		return false;
	}

	/**
	 *
	 * @param t Time lapse to be checked
	 * @return true if this time lapse start before the time lapse {@code t}
	 */
	public boolean endBefore(TimeLapse t)
	{
		if(this.ending < (t.ending + epsilon))
			return true;
		return false;
	}

	/**
	 * Check if {@code t1} and {@code t2} are the same with &epsilon; error.
	 * @param t1 a time in second
	 * @param t2 another time in second
	 * @return true if {@code t1} equal {@code t2}, false otherwise
	 */
	public static boolean isEqual(long t1, long t2)
	{
		if(isBefore(t1, t2) && isAfter(t1, t2))
			return true;
		return false;
	}


	public long getStarting() {
		return starting;
	}


	public long getEnding() {
		return ending;
	}


	@Override
	public String toString() {
		return "[start=" + starting + ", end=" + ending + "]";
	}

}
