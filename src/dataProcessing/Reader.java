package dataProcessing;

import java.util.ArrayList;

import data.*;


/**
 * The {@code Reader} interface represent the general pattern for the classes that will parse the data file.
 * 
 */
public interface Reader 
{	
	/**
	 * Return a list of material
	 * @return a list of material
	 * @throws Exception if a problem occur
	 * @see Material
	 */
	public abstract ArrayList<Material> readingMaterials() throws Exception;
	
	/**
	 * Return a list of missions read from a source
	 * @return A list of missions to be done
	 * @throws Exception if a problem occur
	 * @see Mission
	 * 
	 */
	public abstract ArrayList<Mission> readingMissions() throws Exception;
	
	/**
	 * Return a list of maintenance slots
	 * @return A list of maintenance slots
	 * @throws Exception if a problem occur
	 * @see MaintenanceSlot
	 */
	public abstract ArrayList<MaintenanceSlot> readingMaintenance() throws Exception;

	/**
	 * Return the infrastructure
	 * @return an infrastructure
	 * @throws Exception if a problem occur 
	 * @see Infrastructure
	 */
	public abstract Infrastructure readingInfrastructure() throws Exception;
	
	/**
	 * @deprecated
	 * @return
	 * @throws Exception if a problem occur
	 * @see EmptyRide
	 */
	public abstract ArrayList<EmptyRide> readingEmptyRide() throws Exception;
	
	/**
	 * Return a list of parking tasks
	 * @return a list of parking tasks
	 * @throws Exception if a problem occur
	 * @see ParkingTask
	 */
	public abstract ArrayList<ParkingTask> readingParks() throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception if a problem occur
	 * @see OD
	 */
	public abstract ArrayList<OD> lectureOD() throws Exception;
	
	/**
	 * Return the parameters of the optimization
	 * @return the parametres
	 * @throws Exception if a problem occur
	 * @see Parameters
	 */
	public abstract Parameters readingParameters() throws Exception;

	
	/**
	 * Close the resource that was open to read the data (the resource can be a link to a DB or a file ...). <p>
	 * @throws Exception if a problem occur
	 */
	abstract public void close() throws Exception;
}
