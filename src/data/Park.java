package data;

import util.TimeLapse;

/**
 * This is consider as the park where the material come from at the beginning of it's service, and the go to at the ending of it's service.
 * And where the material can park in the middle of a service. The {@code strtingHour} and the {@code endingHour} are the AVAILABLE window to park.<p>
 *
 *
 * comment : we don't use the {@code TimeLapse} class for the window of availability cause the capacity or the compatible material can be different from time to time.
 *
 * @author boukhatem
 */
public class Park
{

	/**
	 * ID of the park needed for the Excel file (represent the line of park
	 */
	private final int id;

	/**
	 * name of the location or the platform
	 */
	private final String name;

	/**
	 * List of materials that can park in this Parking. A list of materialID of the material
	 * @see Material#getMaterialID()
	 */
	private final String compatibleMaterials;

	/**
	 * Starting time at which the material park
	 */
	private final long startingHour;

	/**
	 * Ending time at which the material park
	 */
	private final long endingHour;

	/**
	 * Number of materials that can park
	 */
	private final int capacity;



	public Park(int id, String name, String materialsID, long sH, long eH, int capacity)
	{
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.compatibleMaterials = materialsID;

		this.startingHour = sH;
		this.endingHour = eH;

	}


	/**
	 * Check if it's possible to park between {@code start} and {@code end}
	 * @param start starting time of parking
	 * @param end ending time of parking
	 * @return true if the window {@code [start, end]} is included in the available window
	 */
	public boolean ableToParkBetween(long start, long end)
	{
		TimeLapse t = new TimeLapse(this.startingHour, this.endingHour);

		return t.include(start, end);
	}

	/**
	 * Determine whether the material given in argument is compatible with the parking
	 * @param m a material
	 * @return true if the compatibleMaterials contain the id of the material given in argument, false otherwise
	 */
	public boolean isCompatibleWith(Material m)
	{
		if(compatibleMaterials.contains(m.getMaterialID()))
			return true;
		return false;
	}

	/**
	 * Return true if this park is a night park (the material can park at the end of the service,
	 * or unpark from it at the beginning of a service).
	 *
	 * The actual test is to check whether the starting service hour is included in the available window to park
	 *
	 * @param nightTime end of service
	 *
	 * @return true if it's a night park; false otherwise
	 */
	public boolean isNightPark(long nightTime)
	{
		if(TimeLapse.isAfter(nightTime, startingHour) && TimeLapse.isBefore(nightTime, endingHour))
			return true;

		return false;
	}


	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public long getStartingHour() {
		return startingHour;
	}

	public long getEndingHour() {
		return endingHour;
	}

	public String getCompatibleMaterials() {
		return compatibleMaterials;
	}


	@Override
	public String toString(){
		return "P" + id;
	}


	public String toString2() {
		return "Park [id=" + id + ", name=" + name + ", compatibleMaterials=" + compatibleMaterials + ", startingHour="
				+ startingHour + ", endingHour=" + endingHour + ", capacity=" + capacity + "]";
	}

}
