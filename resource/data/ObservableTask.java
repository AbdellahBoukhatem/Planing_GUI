package data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


/**
 * The base class for the objects of the gui
 */
public class ObservableTask
{

	protected static int nbObjects =0;

	private final IntegerProperty taskID;

	private final StringProperty trainNumber;

	private final StringProperty origin;

	private final LongProperty startingHour;

//	private final StringProperty startingHour;

	private final StringProperty destination;

	private final LongProperty endingHour;

//	private final StringProperty endingHour;

	private final StringProperty materials;

	private final ObjectProperty<Paint> color;




	public ObservableTask(Task task)
	{
		if(Mission.class.isAssignableFrom(task.getClass()))
			this.trainNumber = new SimpleStringProperty(String.valueOf(((Mission)task).getNumber()));
		else
			this.trainNumber = new SimpleStringProperty(String.valueOf(task.getID()));
		this.origin = new SimpleStringProperty(task.getStartingStation());
		this.startingHour = new SimpleLongProperty(task.getStartingHour());
		this.destination = new SimpleStringProperty(task.getEndingStation());
		this.endingHour = new SimpleLongProperty(task.getEndingHour());
		this.materials = new SimpleStringProperty(task.getCompatibleMaterialsID());
		this.color = new SimpleObjectProperty<Paint>(new Color(0, 0, 0, 1));

		taskID = new SimpleIntegerProperty(nbObjects);
		nbObjects++;
	}



	public ObservableTask(String trainNumber, String origin, long startingHour, String destination, long endingHour, String materials)
	{
		this.trainNumber = new SimpleStringProperty(trainNumber);
		this.origin = new SimpleStringProperty(origin);
		this.startingHour = new SimpleLongProperty(startingHour);
		this.destination = new SimpleStringProperty(destination);
		this.endingHour = new SimpleLongProperty(endingHour);
		this.materials = new SimpleStringProperty(materials);
		this.color = new SimpleObjectProperty<Paint>(new Color(0, 0, 0, 1));

		taskID = new SimpleIntegerProperty(nbObjects);
		nbObjects++;
	}



	/**
	 * Return the id of the
	 */
	public int getTasksID(){
		return taskID.get();
	}

	/**
	 * Return the observable object wrapping the id
	 */
	public IntegerProperty getTaskIDProperty() {
		return taskID;
	}


	/**
	 *Return the number of the train
	 */
	public String getTrainNumber() {
		return trainNumber.get();
	}

	/**
	 *Return the observable object wrapping the train number
	 */
	public StringProperty getTrainNumberProperty() {
		return trainNumber;
	}

	/**
	 * Set the value of the train number
	 * @param trainNumber the new value
	 */
	public void setTrainNumber(String trainNumber) {
		this.trainNumber.set(trainNumber);
	}


	/**
	 * Return the origin
	 */
	public String getOrigin() {
		return origin.get();
	}

	/**
	 * Return the observable object wrapping the origin
	 */
	public StringProperty getOriginProperty() {
		return origin;
	}

	/**
	 * Set the value of the origin
	 * @param origin the new value
	 */
	public void setOrigin(String origin) {
		this.origin.set(origin);;
	}


	/**
	 * Return the starting hour
	 */
	public long getStartingHour() {
		return startingHour.get();
	}

	/**
	 * Return the observable object wrapping the starting hour
	 */
	public LongProperty getStartingHourProperty() {
		return startingHour;
	}

	/**
	 * Set the value of the starting hour
	 * @param origin the new value
	 */
	public void setStartingHour(long startingHour) {
		this.startingHour.set(startingHour);;
	}


	/**
	 * Return the destination
	 */
	public String getDestination() {
		return destination.get();
	}

	/**
	 * Return the observable object wrapping the destination
	 */
	public StringProperty getDestinationProperty() {
		return destination;
	}

	/**
	 * Set the value of the destination
	 * @param origin the new value
	 */
	public void setDestination(String destination) {
		this.destination.set(destination);;
	}


	/**
	 * Return the ending hour
	 */
	public long getEndingHour() {
		return endingHour.get();
	}

	/**
	 * Return the observable object wrapping the ending hour
	 */
	public LongProperty getEndingHourProperty() {
		return endingHour;
	}

	/**
	 * Set the value of the ending hour
	 * @param origin the new value
	 */
	public void setEndingHour(long endingHour) {
		this.endingHour.set(endingHour);;
	}


	/**
	 * Return the materials
	 */
	public String getMaterials() {
		return materials.get();
	}

	/**
	 * Return the observable object wrapping the materials
	 */
	public StringProperty getMaterialsProperty() {
		return materials;
	}

	/**
	 * Set the value of the Material
	 * @param origin the new value
	 */
	public void setMaterials(String materials) {
		this.materials.set(materials);;
	}



	/**
	 * Return the color of the task
	 */
	public Paint getColor() {
		return color.get();
	}

	/**
	 * Return the observable object wrapping the color
	 */
	public ObjectProperty<Paint> getColorProperty() {
		return color;
	}

	/**
	 * Set the value of the ending hour
	 * @param color the new value
	 */
	public void setColor(Paint color) {
		this.color.set(color);;
	}
}
