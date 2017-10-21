package data;

import java.util.ArrayList;

import util.ActionAt;
import util.TimeLapse;

public class Station
{
	/**
	 * Name of the station or the platform, must be unique.
	 */
	private final String name;

	/**
	 * The list of the minimum turning back time per period of time of this station (or platform)
	 */
	private final ArrayList<ActionAt> minTimeToTurnBack;

	/**
	 * The list of the maximum parking time per period of time of this station (or platform)
	 */
	private final ArrayList<ActionAt> maxTimeParking;

	/**
	 * boolean that tell us if it's possible to turn back or not.
	 */
	private final boolean turnBack;

	/**
	 * An inclusive time at the departure from the station
	 */
	private final long inclusiveTimeAtDeparture;

	/**
	 * An inclusive time at the arrival to the station
	 */
	private final long inclusiveTimeAtArrival;

	/**
	 * Indicate if a visit at arrival can be performed at this station
	 */
	private final boolean varPossible;


	/**
	 * Transilien constructor
	 */
	public Station(String name, boolean turnBack)
	{
		this.name = name;
		this.turnBack = turnBack;
		this.minTimeToTurnBack = new ArrayList<ActionAt>();
		this.maxTimeParking = new ArrayList<ActionAt>();
		this.inclusiveTimeAtDeparture = 0;
		this.inclusiveTimeAtArrival =0;
		this.varPossible = false;
	}


	/**
	 * Transilien constructor
	 * @param name
	 * @param minTimeToTurnBack
	 * @param maxTimeParking
	 * @param turnBack
	 */
	public Station(String name, ArrayList<ActionAt> minTimeToTurnBack, ArrayList<ActionAt> maxTimeParking, boolean turnBack)
	{
		this.name = name;
		this.minTimeToTurnBack = minTimeToTurnBack;
		this.maxTimeParking = maxTimeParking;
		this.turnBack = turnBack;
		this.inclusiveTimeAtDeparture = 0;
		this.inclusiveTimeAtArrival =0;
		this.varPossible = false;
	}

	/**
	 * FRET constructeur
	 * @param name name of the station
	 * @param inclusiveTimeAtDeparture time in second
	 * @param inclusiveTimeAtArrival time in second
	 */
	public Station(String name, long inclusiveTimeAtDeparture, long inclusiveTimeAtArrival)
	{
		this.name = name;
		this.turnBack = true;
		this.minTimeToTurnBack = null;
		this.maxTimeParking = null;
		this.inclusiveTimeAtDeparture = inclusiveTimeAtDeparture;
		this.inclusiveTimeAtArrival =inclusiveTimeAtArrival;
		this.varPossible = false;
	}

	/**
	 * FRET with VAR
	 */
	public Station(String name, long inclusiveTimeAtDeparture, long inclusiveTimeAtArrival, boolean varPossible)
	{
		this.name = name;
		this.turnBack = true;
		this.minTimeToTurnBack = new ArrayList<ActionAt>();
		this.maxTimeParking = new ArrayList<ActionAt>();
		this.inclusiveTimeAtDeparture = inclusiveTimeAtDeparture;
		this.inclusiveTimeAtArrival =inclusiveTimeAtArrival;
		this.varPossible = varPossible;
	}

	/**
	 * FRET with VAR
	 */
	public Station(String name, long inclusiveTimeAtDeparture, long inclusiveTimeAtArrival, double parkTime, boolean varPossible)
	{
		this.name = name;
		this.turnBack = true;
		this.minTimeToTurnBack = new ArrayList<ActionAt>();
		this.maxTimeParking = new ArrayList<ActionAt>();
		this.inclusiveTimeAtDeparture = inclusiveTimeAtDeparture;
		this.inclusiveTimeAtArrival =inclusiveTimeAtArrival;
		this.varPossible = varPossible;
	}



	/**
	 * @deprecated non stable because the window {@code arg} can be astride to 2 other windows <p>
	 *
	 * Return the necessary time to turn back in this station at {@code arg} time window
	 *
	 * @param arg a time window
	 * @return if it's possible return the necessary time to turn back, else where return -1
	 */
	public long timeToTurnBackAt(TimeLapse arg)//TODO
	{
		if(turnBack)
			for(ActionAt t : minTimeToTurnBack)
				if(t.isPossibleToActAt(arg))
					return t.getActionDuration();
		return -1;
	}

	/**
	 * Return the necessary time to turn back in this station at {@code arg} time window
	 * @param arg a time (date)
	 * @return if it's possible return the necessary time to turn back, else where return -1
	 */
	public long timeToTurnBackAt(long arg)
	{
		if(turnBack)
			for(ActionAt t : minTimeToTurnBack)
				if(t.isPossibleToActAt(arg))
					return t.getActionDuration();
		return -1;
	}


	/**
	 * Return the maximum parking time in the window {@code arg}
	 * @param arg a time window
	 * @return if it's possible to park return the max parking time, else where return -1
	 * @deprecated non stable because the window {@code arg} can be astride to 2 other windows
	 */
	public long parkingTimeAt(TimeLapse arg)
	{
		for(ActionAt t : maxTimeParking)
			if(t.isPossibleToActAt(arg))
				return t.getActionDuration();
		return -1;
	}


	/**
	 * Return the maximum parking time at {@code arg} for this station
	 * @param arg a time (date)
	 * @return if it's possible to park return the max parking time, else where return -1
	 */
	public long parkingTimeAt(long arg)
	{
		for(ActionAt t : maxTimeParking)
			if(t.isPossibleToActAt(arg))
				return t.getActionDuration();
		return -1;
	}


	/**
	 * Add a slot of turning back time with the specified arguments
	 * @param duration duration of turning back in the slot
	 * @param startingHour starting time of the slot
	 * @param endingHour ending time of the slot
	 */
	public void addTurningBackSlot(long duration, long startingHour, long endingHour) {
		minTimeToTurnBack.add(new ActionAt(duration, startingHour, endingHour));
	}


	/**
	 * Add a slot of parking time with the specified arguments
	 * @param duration duration of park in the slot
	 * @param startingHour starting time of the slot
	 * @param endingHour ending time of the slot
	 */
	public void addParkingSlot(long duration, long startingHour, long endingHour) {
		maxTimeParking.add(new ActionAt(duration, startingHour, endingHour));
	}


	public boolean isSameAs(String arg){
		return this.name.equals(arg);
	}


	public String getName() {
		return name;
	}


	public ArrayList<ActionAt> getMinTimeToTurnBack() {
		return minTimeToTurnBack;
	}


	public ArrayList<ActionAt> getMaxTimeParking() {
		return maxTimeParking;
	}


	public boolean isTurnBack() {
		return turnBack;
	}

	public long getInclusiveTimeAtDeparture() {
		return inclusiveTimeAtDeparture;
	}

	public long getInclusiveTimeAtArrival() {
		return inclusiveTimeAtArrival;
	}

	/**
	 * @return the varPossible
	 */
	public boolean isVarPossible() {
		return varPossible;
	}


	@Override
	public String toString() {
		return "Station [name=" + name + ", minTimeToTurnBack=" + minTimeToTurnBack + ", maxTimeParking="
				+ maxTimeParking + ", turnBack=" + turnBack + "]";
	}

	@Override
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;

		if(arg.getClass() == Station.class )
			return this.name.equals(((Station)arg).name);

		if(arg.getClass() == String.class )
			return this.name.equals(arg);

		return false;


	}
}
