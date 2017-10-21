package data;

/**
 * 
 * @author pabm09921
 *
 */
public class Material 
{
	public static int nbObject = 0;
	

	/**
	 * Id of the material
	 */
	private final int id;
	
	/**
	 * Name of the material
	 */
	private final String name;
	
	/**
	 * The id that the user give to this material (one character in preference)
	 */
	private final String materialID;
	
	/**
	 * The number of materials available
	 */
	private final int stock;
	
	/**
	 * The number of time this material is bigger then the smallest material of an instance
	 */
	private final int length;

	/**
	 * The maximum number of day between 2 passage to the maintenance
	 */
	private final int maintenanceCycle;
	
	/**
	 * The time (in second) a driver take to switch cabin for one unit 
	 */
	private final long switchingCabTime;
	
	/**
	 * The cost of using a unit of this material (used in the objective function)
	 */
	private final double cost;
	



	/**
	 * Transilien and FRET constructor
	 * @param name name of the material
	 * @param matID id given by the user
	 * @param stock number of material available
	 * @param length length of the material
	 * @param cost cost of using one unit
	 * @param cycle number of day for the check up cycle
	 */
	public Material(String name, String matID, int stock, int length, double cost, int cycle)
	{
		this.id = Material.nbObject;
		this.name = name;
		this.materialID = matID;
		this.stock = stock;
		this.length = length;
		this.cost = cost;
		this.maintenanceCycle = cycle;
		this.switchingCabTime = 0;
		
		nbObject++;
	}
	
	/**
	 * Transilien constructor
	 * @param name name of the material
	 * @param matID id given by the user
	 * @param stock number of material available
	 * @param length length of the material
	 * @param cost cost of using one unit
	 * @param cycle number of day for the check up cycle
	 * @param switchingTime time to switch between cab (in second)
	 */
	public Material(String name, String matID, int stock, int length, double cost, int cycle, long switchingTime)
	{
		this.id = Material.nbObject;
		this.name = name;
		this.materialID = matID;
		this.stock = stock;
		this.length = length;
		this.cost = cost;
		this.maintenanceCycle = cycle;
		this.switchingCabTime = switchingTime;
		
		nbObject++;
	}
	
	/**
	 * Check if this material is compatible with the tasks in argument
	 * @param t a task
	 * @return true if the task in argument contains the ID of the material, false otherwise.
	 */
	public boolean isCompatibleWith(Task t)
	{
		if( t.getCompatibleMaterialsID().contains(materialID))
			return true;
		
		return false;
	}
	

	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	/**
	 * @return the material id given by the user
	 * @see #materialID
	 */
	public String getMaterialID() {
		return materialID;
	}


	public int getStock() {
		return stock;
	}

	/**
	 * @return value of {@linkplain #length}
	 */
	public int getLength() {
		return length;
	}


	public double getCost() {
		return cost;
	}

	
	public int getMaintenanceCycle() {
		return maintenanceCycle;
	}	
	
	public long getSwitchingCabTime() {
		return switchingCabTime;
	}

	@Override
	public String toString(){
		return materialID;
	}
	
	
	public String toString2() {
		return "Material [id=" + id + ", name=" + name + ", materialID=" + materialID + ", stock=" + stock + ", length="
				+ length + ", maintenanceCycle=" + maintenanceCycle + ", switchingCabTime=" + switchingCabTime
				+ ", cost=" + cost + "]";
	}

}
