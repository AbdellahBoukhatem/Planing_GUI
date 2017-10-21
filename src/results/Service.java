package results;

import java.io.Serializable;
import java.util.ArrayList;

import data.*;


public class Service implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	public static int nbObject = 0;
	/**
	 * ID of the service
	 */
	private int id;
	/**
	 * Reference to the next service
	 */
	transient private Service nextService;
	/**
	 * the material used in the service
	 */
	transient private Material material;
	/**
	 * A list of tasks done in the service
	 */
	private ArrayList<Task> dailyTasks;
	/**
	 * The night parking
	 */
	transient private Park parking;
	/**
	 * morning park
	 */
	transient private Park morningPark;
	/**
	 *
	 */
	private boolean tc;


	/**
	 * Constructor with all the parameters
	 * @param id ID of the service
	 * @param mat material used in the service
	 * @param tasks list of tasks done in the service
	 * @param parking night park
	 */
	public Service(int id, Material mat, ArrayList<Task> tasks, Park parking)
	{
		this.id = id;
		this.material = mat;
		this.dailyTasks = tasks;
		this.parking = parking;
		this.nextService = null;

		nbObject++;
	}


	/**
	 *
	 * @param id ID of the service
	 * @param mat material used in the service
	 * @param tasks list of tasks done in the service
	 */
	public Service(int id, Material mat, ArrayList<Task> tasks)
	{
		this.id = id;
		this.material = mat;
		this.dailyTasks = tasks;

		this.tc = haveMaintenace(tasks);

		nbObject++;
	}

	/**
	 * @param mat material used in the service
	 * @param tasks list of tasks done in the service
	 */
	public Service(Material mat, ArrayList<Task> tasks)
	{
		this.material = mat;
		this.dailyTasks = tasks;

		this.tc = haveMaintenace(tasks);

		this.id = Service.nbObject;
		nbObject++;
	}


	/**
	 *
	 * @param mat the material used in the service
	 * @param tasks list of tasks done in this service
	 * @param morning the morning parking (night parking of the previous service)
	 * @param night the night parking
	 */
	public Service(Material mat, ArrayList<Task> tasks, Park morning, Park night)
	{
		this.material = mat;
		this.dailyTasks = tasks;
		this.morningPark = morning;
		this.parking = night;

		this.tc = haveMaintenace(tasks);

		this.id = nbObject;
		nbObject++;
	}


	/**
	 * Check if there is a maintenance task in the list given in parameters
	 * @param services list of tasks
	 * @return true if a maintenance is in the list in parameters, false otherwise
	 */
	private boolean haveMaintenace(ArrayList<Task> services)
	{
		for(Task t : services)
			if(t.getClass() == MaintenanceSlot.class)
				return true;
		return false;
	}


	/**
	 *
	 * @return the ID of the service
	 */
	public int getID() {
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	/**
	 *
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 *
	 * @return the list of the tasks of this service
	 */
	public ArrayList<Task> getDailyTasks() {
		return dailyTasks;
	}

	/**
	 * Set the actual {@link #dailyTasks} to arg0
	 * @param arg0 the new daily tasks done by the service
	 */
	public void setDailyTasks(ArrayList<Task> arg0){
		this.dailyTasks = arg0;
	}

	/**
	 *
	 * @return the length of the material
	 */
	public int getMaterialLength() {
		return material.getLength();
	}

	/**
	 *
	 * @return the service that is done after this one
	 */
	public Service getNextService() {
		return nextService;
	}

	/**
	 *
	 * @return the parking use by the service at the beginning of the service (night park of the previous service)
	 */
	public Park getMorningPark(){
		return morningPark;
	}

	/**
	 *
	 * @return the parking used by the service at night
	 */
	public Park getParking() {
		return parking;
	}

	public boolean isTc() {
		return tc;
	}

	@Override
	public String toString()
	{
		return id + " : " + dailyTasks.toString();
	}
}
