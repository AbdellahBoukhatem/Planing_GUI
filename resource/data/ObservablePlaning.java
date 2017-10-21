package data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservablePlaning
{

	private static int nbObject = 0;

	private final IntegerProperty id;

	private final ObservableList<ObservableTask> tasksPlaning;



	/**
	 * Create new empty instance.
	 */
	public ObservablePlaning()
	{
		this.tasksPlaning = FXCollections.observableArrayList();

		nbObject++;
		this.id = new SimpleIntegerProperty(nbObject);
	}

	/**
	 * Create new ...
	 * @param tasksPlaning
	 */
	public ObservablePlaning(ObservableList<ObservableTask> tasksPlaning)
	{
		this.tasksPlaning = FXCollections.observableArrayList();
		this.tasksPlaning.addAll(tasksPlaning);

		nbObject++;
		this.id = new SimpleIntegerProperty(nbObject);
	}



	public int getId() {
		return id.get();
	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}


	public void addTaskToPlaning(ObservableTask task){
		tasksPlaning.add(task);
	}

	public ObservableTask getTaskAt(int index) {
		return tasksPlaning.get(index);
	}

	public ObservableTask getTask(int id)
	{
		for(ObservableTask t : tasksPlaning)
			if(t.getTasksID() == id)
				return t;

		return null;
	}

	public ObservableList<ObservableTask> getResourcePlanig() {
		return tasksPlaning;
	}
}
