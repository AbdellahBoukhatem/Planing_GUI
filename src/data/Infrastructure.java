package data;

import java.util.ArrayList;

/**
 * the infrastructure generally is a graph
 * @author pabm09921
 * 
 */
public class Infrastructure
{
	/**
	 * The returned time to turn back when it's impossible to turn
	 */
	private final static long sevenDaysInSecond = 604800;
	
	/**
	 * Represent a turning back at the ending station of the first task, when we chain two different tasks
	 */
	public final static int turnigBackFirstTask = -1;
	/**
	 * Represent the absence of turning back, when we chain two different tasks
	 */
	public final static int noTurningBack = 0;
	/**
	 * Represent a turning back at the starting station of the second task, when we chain two different tasks
	 */
	public final static int turningBackSecondTask = 1;
	/**
	 * Represent a turning back at the ending station of the first task and the starting station of the second task, when we chain two different tasks
	 */
	public final static int turningBackBothSide = 2;
	
	
	/**
	 * List of the station of the infrastructure (ordered from the north to the south)
	 */
	private final ArrayList<Station> stations;
	
	/**
	 * List of the feasible empty ride
	 */
	private ArrayList<EmptyRide> emptyRides;
	
	 /**
	  * List of night parking
	  */
	private final ArrayList<Park> parkings;

	
	
	public Infrastructure(ArrayList<Station> stations, ArrayList<EmptyRide> emptyRides)
	{
		this.stations = stations;
		this.emptyRides = emptyRides;
		this.parkings = new ArrayList<Park>();
	}
	
	
	public Infrastructure(ArrayList<Station> stations, ArrayList<EmptyRide> emptyRides, ArrayList<Park> nightPark)
	{
		this.stations = stations;
		this.emptyRides = emptyRides;
		this.parkings = nightPark;
	}
	
	
	
	/***
	 * Return the direction of the mission {@code arg}
	 * @param arg a mission 
	 * @return the character of the direction. return * if its not to the north or the south
	 * @throws Exception if no station correspond to the starting and the ending of the mission {@code arg}
	 */
	public char getDirection(Mission arg) throws Exception
	{
		if(arg.getStartingStation().equals(arg.getEndingStation()))
			return '*';
		
		for(Station s : stations)
		{
			//if we meet the starting station first then the mission is going to the south
			if(s.isSameAs(arg.getStartingStation()))
				return 'S';
			//if we meet the ending station first then the mission is going to the north
			if(s.isSameAs(arg.getEndingStation()))
				return 'N';
		}
		//TODO : only if the two aren't referenced 
		throw new Exception("La gare " + arg.getStartingStation().toString() + " n\'est pas referencées");
	}
	
	
	
	/**
	 * @deprecated an empty ride must have time
	 * We do not take consideration of the order (arg0 &rarr; arg1 is the same as arg1 &rarr; arg0)
	 * @param arg0 name of a station
	 * @param arg1 name another station
	 * @param mat a material
	 * @return the corresponding empty ride, null otherwise 
	 */
	public EmptyRide getEmptyRideBetween(String arg0, String arg1, Material mat)
	{
		for(EmptyRide e : emptyRides)
			if(e.sameAs(arg0, arg1) && e.isCompatibleWith(mat))
				return e;
		return null;
	}
	
	
	/**
	 * Return the empty ride between the station in arguments with the material {@code mat} at {@code time}
	 * @param arg0 first station
	 * @param arg1 second station
	 * @param mat a material
	 * @param time the time in second
	 * @return an EmptyRide object corresponding to the empty ride between arg0 and arg1 with material mat at time, null if it doen't exist or is impossible
	 */
	public EmptyRide getEmptyRideBetweenWithAt(String arg0, String arg1, Material mat, long time)
	{
		for(EmptyRide e : emptyRides)
			if(e.sameAs(arg0, arg1) && e.isCompatibleWith(mat) && e.isAvailableAt(time) && e.getDistance() >= 0)
				return e;

		return null;
	}
	
	
	
	/**
	 * Return the parking time of the station {@code station} at {@code t}, return -1 if it's not possible to park.
	 * @param station the name of the station
	 * @param t a date (time)
	 * @return return the parking time if it's possible, -1 otherwise.
	 */
	public long getParkingTimeAt(String station, long t)
	{
		for(Station s : stations)
			if(s.equals(station))
				return s.parkingTimeAt(t);
		
		return -1;
	}
	
	
	/**
	 * Return the parking time between the tasks in arguments
	 * @param t1 first task
	 * @param t2 second task
	 * @return the time (in second) of parking between the tasks in arguments
	 */
	public long getParkTime(Task t1, Task t2)
	{
		if(t1.getClass() == MaintenanceSlot.class || t2.getClass() == MaintenanceSlot.class)
			return sevenDaysInSecond;
		
		long park1 = getParkingTimeAt(t1.getEndingStation(), t1.getEndingHour()),
				park2 = getParkingTimeAt(t2.getStartingStation(), t2.getStartingHour());
		
		if(park1 < 0 || park2 < 0)
			return 0;
		
		if(t1.getEndingStation().equals(t2.getStartingStation()))
			return park1;
		
		return park1 + park2;
	}
	
	
	
	
	
	/**
	 * Return the inclusive time a the departure from "station", less then 0 if {@code station} is't found
	 * @param station a station
	 * @return the inclusive time (in second) at the departure from "station", less then 0 if the argument isn't found
	 */
	public long getInclusiveTimeAtDeparture(String station)
	{
		for(Station s : stations)
			if(s.equals(station))
				return s.getInclusiveTimeAtDeparture();
		return -1;
	}
	
	
	/**
	 * Return the inclusive time a the arrival to "station", less then 0 if {@code station} is't found
	 * @param station a station
	 * @return the inclusive time (in second) at the arrival to "station", less then 0 if the argument isn't found
	 */
	public long getInclusiveTimeAtArrival(String station)
	{
		for(Station s : stations)
			if(s.equals(station))
				return s.getInclusiveTimeAtArrival();
		return -1;
	}
	
	
	/**
	 * Return the turning back time of the station {@code station} at {@code t}, return -1 if it's not possible to turn.
	 * @param station the name of the station
	 * @param t a date (time)
	 * @return return the turning back time if it's possible, -1 otherwise.
	 */
	public long getTurnigBackTimeAt(String station, long t)
	{
		for(Station s : stations)
			if(s.equals(station))
				return s.timeToTurnBackAt(t);
		
		return -1;
	}
	
	
	/**
	 * Return the nature of the turning back between the two tasks {@code t1} and {@code t2}
	 * @param t1 first task
	 * @param t2 second task
	 * @return the nature of the turning back
	 * @see #getNatureTurningBack(String, char, String, char)
	 */
	public int getNatureTurningBack(Task t1, Task t2)
	{
		return getNatureTurningBack(t1.getEndingStation(), t1.getDirection(), t2.getStartingStation(), t2.getDirection());
	}
	
	
	
	/**
	 * Return the turning back time between the 2 tasks in arguments. At first we define where the turning back is done. And then we apply the corresponding time.
	 * If it's impossible to turn back it return 7 days in second
	 * @param t1 first task
	 * @param t2 second task
	 * @return the time in second for the turning, more then 7 days if impossible
	 * @see #getNatureTurningBack(Task, Task)
	 */
	public long getTurningBackTime(Task t1, Task t2)
	{
		int nature = getNatureTurningBack(t1, t2);
		
		switch(nature)
		{
			case turnigBackFirstTask 	: 
				{
					long turn = getTurnigBackTimeAt(t1.getEndingStation(), t1.getEndingHour());
					
					if(turn < 0)
						return sevenDaysInSecond;
					return turn;
				}
			case turningBackSecondTask :
			{
				long turn = getTurnigBackTimeAt(t2.getStartingStation(), t2.getStartingHour());
				
				if(turn < 0)
					return sevenDaysInSecond;
				return turn;
			}
			case turningBackBothSide	:
			{
				long turn1 = getTurnigBackTimeAt(t1.getEndingStation(), t1.getEndingHour()),
					turn2 = getTurnigBackTimeAt(t2.getStartingStation(), t2.getStartingHour());
				
				if(turn1 < 0 || turn2 < 0)
					return sevenDaysInSecond;
				
				return turn1 + turn2;
			}
			default : return 0;
		}
	}
	
	

	
	
	/**
	 * Determine if there is a turning back and where ( at {@code s1}, {@code s2} or both). The turning back is based on
	 * the direction of the material when it arrive to {@code s1} and the direction when it depart from {@code s1}
	 * and the list of the station ordered from the north to the south.
	 * 
	 * @param s1 the station where the material arrive (represent the ending station of a first task)
	 * @param dir1 direction of the material when it's arrive to {@code s1}
	 * @param s2 the station where the material is going to depart (represent the starting station of a second task)
	 * @param dir2 direction of the material when it'll depart from {@code s1}
	 * @return the nature of turning back.
	 * 
	 * @see #turnigBackFirstTask
	 * @see #noTurningBack
	 * @see #turningBackSecondTask
	 * @see #turningBackBothSide
	 */
	public int getNatureTurningBack(String s1, char dir1, String s2, char dir2)
	{
		if(s1.equals(s2) && dir1 == dir2)
			return noTurningBack;
		
		for(Station s : stations)
		{
			if(s.equals(s1))
			{
				if((dir1 == 'S' || dir1 == '*') && dir2 == 'N')
					return turningBackSecondTask;
				
				if(dir1 == 'N' && dir2 == 'N')
					return turningBackBothSide;
				
				if(dir1 == 'N') // (&& dir2 == 'S' || dir2 == '*'))
					return turnigBackFirstTask;
				
				//if((dir1 == 'S' || dir1 == '*') && (dir2 == 'S' || dir2 == '*'))
				return noTurningBack;
			}
			
			if(s.equals(s2))
			{
				if((dir2 == 'N' || dir2 == '*') && dir1 == 'S')
					return turnigBackFirstTask;
				
				if(dir1 == 'S' && dir2 == 'S')
					return turningBackBothSide;
				
				if(dir2 == 'S') // (&& dir1 == 'N' || dir1 == '*'))
					return turningBackSecondTask;
				
				//if((dir1 == 'N' || dir1 == '*') && (dir2 == 'N' || dir2 == '*'))
				return noTurningBack;
			}
		}
		
		return noTurningBack;
	}
	
	
	/**
	 * Return the object Station that have the same name as {@code s}
	 * @param s name of the station
	 * @return the station corresponding to "s"
	 */
	public Station getStation(String s)
	{
		for(Station stat : stations)
			if(stat.equals(s))
				return stat;
		return null;
	}
	
	
	public ArrayList<Park> getParkings(){
		return parkings;
	}


	@Override
	public String toString() {
		String temp = "Infrastructure \n [stations=\n";
		
		for(Station s : stations)
			temp += s.toString() + "\n";
		
	temp += ", nightPark=\n";
	
	for(Park p : parkings)
		temp += p.toString() + "\n";
		
		temp += ", emptyRides=\n";
		
		for(EmptyRide e : emptyRides)
			temp += e.toString() + "\n";
		
		
		return temp;
	}	
}
