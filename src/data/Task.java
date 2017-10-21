package data;

import java.io.Serializable;

/**
 * This class represent a task, any activity that can be done by a Resource (in Hermes the resource is a material)
 *
 * @see Mission
 * @see MaintenanceSlot
 * @see ParkingTask
 */

public abstract class Task implements Serializable
{

	private static final long serialVersionUID = 1L;

	protected static int nbObjects =0;

	/**
	 * The task ID may be different from the object ID of the object
	 * @return the task ID
	 */
	public abstract int getTaskID();


	/**
	 * Needed for the Exel file
	 * @return the ID of the specific Object
	 */
	public abstract int getID();

	/**
	 * Return the name of the starting station
	 * @return a {@code String} represent the name of the starting station
	 */
	public abstract String getStartingStation();

	/**
	 * Return the starting hour of the task
	 * @return the starting hour (in second) of the task
	 */
	public abstract long getStartingHour();

	/**
	 * Return the name of the ending station
	 * @return a {@code String} represent the name of the ending station
	 */
	public abstract String getEndingStation();

	/**
	 * Return the ending hour of the task
	 * @return the ending hour (in second) of the task
	 */
	public abstract long getEndingHour();

	/**
	 * Return the compatible materials with this task
	 * @return a String containing the ID (user) of the materials that are compatible with this task
	 * @see Material#getMaterialID()
	 */
	public abstract String getCompatibleMaterialsID();

	/**
	 * Return the direction of the task
	 * @return a {@code char} representing the direction of the task
	 */
	public abstract char getDirection();

	/**
	 * Return the required number of materials for this task
	 * @return required number of materials for this task
	 */
	public abstract int getRequiredMaterials();

	/**
	 * Return the maximum number of material that can undertake this task. Can be different (bigger) from the required material.
	 * @return the maximum number of materials for this task
	 */
	public abstract int getCapacity();

	/**
	 * Return the category of the task
	 * @return the category of the task
	 */
	public abstract String getCategories();


//	@Override
//	public boolean equals(Object o)
//	{
//		if(o==null)
//			return false;
//
//		if(this.getClass().isAssignableFrom(o.getClass()))
//			if(((Task)o).getID() == getID())
//				return true;
//
//
//		if(ObservableTask.class.isAssignableFrom(o.getClass()))
//			if(((ObservableTask)o).getTrainNumber()== getID())
//				return true;
//
//		return false;
//
//	}
}
