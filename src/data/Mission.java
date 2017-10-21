package data;

public class Mission extends Task
{	
	public static int nbObject = 0;
	
	/**
	 * ID of the mission as task
	 */
	private final int taskID;
	
	/**
	 * ID of the mission needed for the Excel file
	 */
	private final int id;
	
	/**
	 * ID given by the user
	 */
	private final String number;
	
	/**
	 * Code of the mission
	 */
	private final String code;
	
	/**
	 * The station where the mission start
	 */
	private final String startingStation;
	
	/**
	 * The time in second where the mission start
	 */
	private final long startingHour;
	
	/**
	 * The station where the mission end
	 */
	private final String endingStation;
	
	/**
	 * Time in second where the mission ends
	 */
	private final long endingHour;
	
	/**
	 * List of materials that can do this mission. A list of materialID of the material
	 * @see Material#getMaterialID()
	 */
	private final String compatibleMaterials;
	
	/**
	 * Number of material required to do the mission
	 */
	private final int materialsRequirements;
	
	/**
	 * The maximum number of materials that can do this mission
	 */
	private final int materialsCapacity;

	/**
	 * the categories of the mission
	 */
	private final String categories;
	
	/**
	 * Direction of the mission (useful in checking turning back)
	 */
	private char direction;
	
	/**
	 * Distance in kilometers done by the mission
	 */
	private final double distance;

	
	/**
	 * Transilien constructor
	 * @param num number
	 * @param code code
	 * @param dS departure Station
	 * @param dH departure Hour
	 * @param eS ending Station
	 * @param eH ending Hour
	 * @param matIDs materials IDs
	 * @param matRequir required material
	 * @param dist distance in km
	 * @param category categories of the mission
	 */
	public Mission(String num, String code, String dS, long dH, String eS, long eH, String matIDs, int matRequir, double dist, String category)
	{
		this.number = num;
		this.code = code;
		this.startingHour = dH;
		this.startingStation = dS;
		this.endingHour = eH;
		this.endingStation = eS;
		
		this.compatibleMaterials = matIDs;
		this.materialsRequirements = matRequir;
		this.materialsCapacity = matRequir;
		this.categories = category;
		this.direction = '*';
		this.distance = dist;
		
		this.id = nbObject;
		nbObject++;
		
		this.taskID = Task.nbObjects;
		Task.nbObjects ++;
	}
	
	/**
	 *  FRET Constructor
	 * @param num number
	 * @param dS departure Station
	 * @param dH departure Hour
	 * @param eS ending Station
	 * @param eH ending Hour
	 * @param matIDs materials IDs
	 * @param matRequir required material
	 * @param matCapacity maximum material capacity
	 * @param dist distance in km
	 */
	public Mission(String num, String dS, long dH, String eS, long eH, String matIDs, int matRequir, int matCapacity, double dist)
	{
		this.number = num;
		this.code = "";
		this.startingHour = dH;
		this.startingStation = dS;
		this.endingHour = eH;
		this.endingStation = eS;
		
		this.compatibleMaterials = matIDs;
		this.materialsRequirements = matRequir;
		this.materialsCapacity = matCapacity;
		this.categories = "*";
		this.direction = '*';
		this.distance = dist;
		
		this.id = nbObject;
		nbObject++;
		
		this.taskID = Task.nbObjects;
		Task.nbObjects ++;
	}
	
	
	public void setDirection(char direction){
		this.direction = direction;
	}
	
	@Override
	public int getTaskID(){
		return taskID;
	}
	
	@Override
	public int getID(){
		return id;
	}
	
	public String getNumber() {
		return number;
	}

	public String getCode() {
		return code;
	}
	
	@Override
	public String getCompatibleMaterialsID() {
		return compatibleMaterials;
	}
	
	@Override
	public char getDirection() {
		return direction;
	}
	
	public double getDistance() {
		return distance;
	}
	
	@Override
	public String getStartingStation() {
		return startingStation;
	}
	
	@Override
	public long getStartingHour() {
		return startingHour;
	}
	
	@Override
	public String getEndingStation() {
		return endingStation;
	}
	
	@Override
	public long getEndingHour() {
		return endingHour;
	}
	
	@Override
	public String getCategories() {
		return categories;
	}
	
	@Override
	public int getRequiredMaterials() {
		return materialsRequirements;
	}
	
	@Override
	public int getCapacity() {
		return materialsCapacity;
	}
	
	@Override
	public String toString(){
		return "S" + (id+2) ;//+ " \t" + direction;
	}
	
	
	public String toString2() {
		return "Mission [taskID=" + taskID + ", id=" + id + ", number=" + number + ", code=" + code
				+ ", startingStation=" + startingStation + ", startingHour=" + startingHour + ", endingStation="
				+ endingStation + ", endingHour=" + endingHour + ", compatibleMaterials=" + compatibleMaterials
				+ ", materialsRequirements=" + materialsRequirements + ", materialsCapacity=" + materialsCapacity
				+ ", categories=" + categories + ", direction=" + direction + ", distance=" + distance + "]";
	}
}
