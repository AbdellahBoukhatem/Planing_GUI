package data;

import util.TimeLapse;

// comment : we don't use the {@code TimeLapse} class for the window of availability cause the capacity or the compatible material can be different from time to time.
public class EmptyRide
{

	/**
	 * the first station
	 */
	private String station1;

	/**
	 * the second station
	 */
	private String station2;

	/**
	 * List of materials that can park in this Parking. A list of materialID of the material
	 * @see Material#getMaterialID()
	 */
	private String compatibleMaterials;

	/**
	 * The starting time where this empty ride is available (in second)
	 */
	private final long startingHour;

	/**
	 * The ending time where this empty ride is available (in second)
	 */
	private final long endingHour;

	/**
	 * the duration of the trip (in second)
	 */
	private long duration;

	/**
	 * the distance between the two station (in kilometer)
	 */
	private double distance;


	/**
	 * Instantiate an empty ride (FRET constructor)
	 * @param sd station of the departure
	 * @param sa station of arrival
	 * @param duration duration of the travel
	 * @param distance distance of the travel
	 * @param compatibleMaterials compatible material id capable of doing this trip
	 */
	public EmptyRide(String sd, String sa, long duration, double distance, String compatibleMaterials)
	{
			this.station1 = sd;
			this.station2 = sa;
			this.duration = duration;
			this.compatibleMaterials = compatibleMaterials;
			this.distance = distance;
			this.startingHour =0;
			this.endingHour =604800; //seven day in second
	}

	/**
	 * Instantiate an empty ride (Transilien constructor)
	 * @param station1 station of the departure
	 * @param station2 station of arrival
	 * @param duration duration of the travel
	 * @param distance distance of the travel
	 * @param compatibleMaterials compatible material id capable of doing this trip
	 * @param startingHour the starting moment where the trip can be done
	 * @param endingHour the last moment where the trip can be done
	 */
	public EmptyRide(String station1, String station2, long duration, double distance, String compatibleMaterials,
			long startingHour, long endingHour)
	{
		this.station1 = station1;
		this.station2 = station2;
		this.duration = duration;
		this.distance = distance;
		this.compatibleMaterials = compatibleMaterials;
		this.startingHour = startingHour;
		this.endingHour = endingHour;
	}


	/**
	 * Determine if the trip from "s1" to "s2" correspond to this empty ride
	 * @param s1 a station
	 * @param s2 another station
	 * @return true if the trip from "s1" to "s2" correspond to this empty ride, false otherwise
	 */
	public boolean sameAs(String s1, String s2)
	{
		if( (station1.equals(s1) && station2.equals(s2)) || (station1.equals(s2) && station2.equals(s1)) )
			return true;

		return false;
	}


	/**
	 * Determine if we can do this empty ride at "starting" time
	 * @param starting the time to be check
	 * @return true if "starting" is in the available time of this empty ride, false otherwise
	 */
	public boolean isAvailableAt(long starting)
	{
		if(TimeLapse.isAfter(starting, startingHour) && TimeLapse.isBefore(starting, endingHour))
			return true;
		return false;
	}

	/**
	 * Determine whether the material given in argument is compatible with the empty ride
	 * @param m a material
	 * @return true if the compatibleMaterial contain the id of the material  given in argument, false otherwise
	 */
	public boolean isCompatibleWith(Material m)
	{
		if(compatibleMaterials.contains(m.getMaterialID()))
			return true;
		return false;
	}


	public String getStation1() {
		return station1;
	}


	public String getStation2() {
		return station2;
	}


	public long getDuration() {
		return duration;
	}


	public double getDistance() {
		return distance;
	}


	public String getCompatibleMaterials() {
		return compatibleMaterials;
	}


	public long getStartingHour() {
		return startingHour;
	}


	public long getEndingHour() {
		return endingHour;
	}


	@Override
	public String toString() {
		return "EmptyRide [station1=" + station1 + ", station2=" + station2 + ", compatibleMaterials="
				+ compatibleMaterials + ", startingHour=" + startingHour + ", endingHour=" + endingHour + ", duration="
				+ duration + ", distance=" + distance + "]";
	}
}
