package util;

/**
 *
 * This class represent a the duration (in second) of an action in a specific period of a day (week, month,..)
 *
 */
public class ActionAt
{
	/**
	 * The action duration (in second)
	 */
	private final long actionDuration;

	/**
	 * The time window
	 */
	private final TimeLapse lapse;


	/**
	 * Instantiate an action that will last {@code duration} in the lapse {@code lapse}.
	 * @param duration the duration of the action (in second)
	 * @param lapse the lapse where the action can be done
	 */
	public ActionAt(long duration, TimeLapse lapse)
	{
		this.actionDuration = duration;
		this.lapse = lapse;
	}

	/**
	 * Instantiate an action that will last {@code duration} between {@code startingHour} and {@code endingHour}.
	 * @param duration the duration of the action (in second)
	 * @param startingHour the first moment of the window (in second)
	 * @param endingHour the last moment of the window (in second)
	 */
	public ActionAt(long duration, long startingHour, long endingHour)
	{
		this.actionDuration = duration;
		this.lapse = new TimeLapse(startingHour, endingHour);
	}


	/**
	 * @deprecated non stable because the window arg can be astride to 2 other windows <p>
	 *
	 * Determine if it's possible to do this action in {@code arg}
	 * @param arg time window to be check
	 * @return true if the lapse {@code arg} is included in the lapse of this action
	 */
	public boolean isPossibleToActAt(TimeLapse arg)
	{
		if(this.lapse.include(arg))
			return true;
		return false;
	}

	/**
	 * Determine if it's possible to do this action at the exact time {@code arg}
	 * @param arg a date (time in second)
	 * @return true if the moment in argument is included in this lapse
	 */
	public boolean isPossibleToActAt(long arg)
	{
		if(this.lapse.include(arg))
			return true;
		return false;
	}


	public long getActionDuration() {
		return actionDuration;
	}


	public TimeLapse getLapse() {
		return lapse;
	}


	@Override
	public String toString() {
		return "[actionDuration=" + actionDuration + ", lapse=" + lapse.toString() + "]";
	}


}
