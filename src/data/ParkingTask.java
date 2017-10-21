package data;

import util.TimeLapse;

/**
 * This class represent a parking like a task. The starting and ending time to park are not a window where the material can park, but MUST park. <p>
 *
 * comment : we don't use the {@code TimeLapse} class for the window of availability cause the capacity or the compatible material can be different from time to time.
 *
 */
public class ParkingTask extends Task
{
	private static int nbObject = 0;

	/**
	 * ID of the park as task
	 */
	private final int taskID;

	/**
	 * ID of the park needed for the Excel file
	 */
	private final int id;

	/**
	 * name of the location or the platform
	 */
	private final String name;

	/**
	 * Number of materials that can park
	 */
	private final int capacity;

	/**
	 * Starting time at which the material park
	 */
	private final long startingHour;

	/**
	 * Ending time at which the material park
	 */
	private final long endingHour;

	/**
	 * List of materials that can park in this Parking. A list of materialID of the material
	 * @see Material#getMaterialID()
	 */
	private final String compatibleMaterials;



	public ParkingTask(String name, String materialsID, long sH, long eH, int capacity)
	{
		this.name = name;
		this.capacity = capacity;
		this.compatibleMaterials = materialsID;

		this.startingHour = sH;
		this.endingHour = eH;

		id = nbObject;
		nbObject++;

		this.taskID = Task.nbObjects;
		Task.nbObjects ++;
	}

	/**
	 * Check if it's possible to park between "start" and "end"
	 * @param start starting time of parking
	 * @param end ending time of parking
	 * @return
	 */
	public boolean ableToParkBetween(long start, long end)
	{
		TimeLapse t = new TimeLapse(this.startingHour, this.endingHour);

		return t.include(start, end);
	}

	@Override
	public int getTaskID(){
		return taskID;
	}

	@Override
	public int getID() {
		return id;
	}


	@Override
	public String getStartingStation() {
		return name;
	}


	@Override
	public long getStartingHour() {
		return startingHour;
	}


	@Override
	public String getEndingStation() {
		return name;
	}


	@Override
	public long getEndingHour() {
		return endingHour;
	}


	@Override
	public String getCompatibleMaterialsID() {
		return compatibleMaterials;
	}


	@Override
	public char getDirection() {
		return '*';
	}


	@Override
	public int getRequiredMaterials() {
		return 0;
	}


	@Override
	public int getCapacity() {
		return capacity;
	}


	@Override
	public String getCategories() {
		return "*";
	}

}
