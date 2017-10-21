package data;

public class MaintenanceSlot extends Task
{
	
	public static int nbObject = 0;
	
	/**
	 * ID of the maintenance as task
	 */
	private final int taskID;
	
	/**
	 * ID of the maintenance needed for the Excel file
	 */
	private final int id;
	
	/**
	 * Name of the station
	 */
	private final String station;
	
	/**
	 * Starting time for the maintenance slot
	 */
	private final long startingTime;
	
	/**Ending time for the maintenance slot
	 * 
	 */
	private final long endingTime;
	
	/**
	 * List of materials that can park in this Parking. A list of materialID of the material 
	 * @see Material#getMaterialID()
	 */
	private final String compatibleMaterials;
	
	/**
	 * The number of material that can be receive in this slot
	 */
	private final int capacity;
	
	/**
	 * The number of material that must be receive in this slot
	 */
	private final int requirements;

	
	
	public MaintenanceSlot(String name, String compatibleMat, long he, long hs, int cap)
	{
		this.station = name;
		this.startingTime = he;
		this.endingTime = hs;
		
		this.compatibleMaterials = compatibleMat;
		this.capacity = cap;
		
		this.requirements = 0;
		
		this.id = nbObject;
		nbObject++;
		
		this.taskID = Task.nbObjects;
		Task.nbObjects ++;
		
	}
	
	
	public MaintenanceSlot(String code, String classe, long he, long hs, int cap, double rate)
	{
		this.station = code;
		this.startingTime = he;
		this.endingTime = hs;
		
		this.compatibleMaterials = classe;
		this.capacity = cap;
		
		this.requirements = (int) Math.ceil(rate * cap);
		
		this.id = nbObject;
		nbObject++;
		
		this.taskID = Task.nbObjects;
		Task.nbObjects ++;
	}

	
	@Override
	public int getTaskID(){
		return taskID;
	}
	
	@Override
	public int getID()	{
		return id;
	}
	
	@Override
	public String getCompatibleMaterialsID() {
		return compatibleMaterials;
	}
	
	@Override
	public String getStartingStation() {
		return station;
	}
	
	@Override
	public long getStartingHour() {
		return startingTime;
	}
	
	@Override
	public String getEndingStation() {
		return station;
	}
	
	@Override
	public long getEndingHour() {
		return endingTime;
	}
	
	@Override
	public char getDirection() {
		return '*';
	}
	
	@Override
	public String getCategories() {
		return "*";
	}
	
	@Override
	public int getRequiredMaterials() {
		return requirements;
	}
	
	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public String toString(){
		return "M" + (id+2);
	}
	
	public String toString2() {
		return "MaintenanceSlot [taskID=" + taskID + ", id=" + id + ", station=" + station + ", startingTime="
				+ startingTime + ", endingTime=" + endingTime + ", compatibleMaterials=" + compatibleMaterials
				+ ", capacity=" + capacity + ", requirements=" + requirements + "]";
	}
}
