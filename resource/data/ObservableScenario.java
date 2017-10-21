package data;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import results.Service;

/**
 * This class regroup all the data and a solution of a problem.<p>
 *
 * @author Abdellah
 */
public class ObservableScenario
{

	/**
	 * List of all the tasks to be done.
	 */
	private final ObservableList<ObservableTask> tasks;


	private final ObservableList<ObservablePlaning> solution;



	/**
	 * Create a new empty instance of scenario.
	 */
	public ObservableScenario()
	{
		this.tasks = FXCollections.observableArrayList();
		this.solution = FXCollections.observableArrayList();
	}



	/**
	 * Create a new instance of scenario without solution.
	 * @param tasks list of tasks of the problem
	 */
	public ObservableScenario(ArrayList<Task> tasks)
	{
		this.tasks = FXCollections.observableArrayList();
		this.solution = FXCollections.observableArrayList();

		for(Task t : tasks)
			this.tasks.add(new ObservableTask(t));
	}


	/**
	 * Create a new instance of scenario.
	 * @param tasks tasks list of tasks of the problem
	 * @param solution solution of the problem.
	 */
	public ObservableScenario(ArrayList<Task> tasks, ArrayList<Service> solution)
	{
		this.tasks = FXCollections.observableArrayList();
		this.solution = FXCollections.observableArrayList();

		for(Task t : tasks)
			this.tasks.add(new ObservableTask(t));

		for(Service s : solution)
		{
			ObservablePlaning temp = new ObservablePlaning();
			for(Task t : s.getDailyTasks())
				for(ObservableTask ot : this.tasks)
					if(t.getID() == ot.getTasksID())
						temp.addTaskToPlaning(ot);

			this.solution.add(temp);
		}
	}


	public ObservableList<ObservableTask> getTasks() {
		return tasks;
	}


	public ObservableList<ObservablePlaning> getSolution() {
		return solution;
	}





}
