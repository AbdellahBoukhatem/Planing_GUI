package viewPlaning;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import util.TimeConstant;
import data.*;


public class PlaningFrame
{
	/**
	 * This property represent the zooming rate.
	 */
	private static int zoominigRate;
	/**
	 * Planing duration in second (could be a day, a week...etc.)
	 */
	private final long planingDuration;
	/**
	 * Number of second per pixel in the View. Change with the rate
	 */
	private int secondPerPixel;
	/**
	 * Number of pixel of the planing. (duration/secondPerPixel)
	 */
	private double pixelInGraphic;

	/**
	 *
	 */
	private VBox container;

	/**
	 * List of all the tasks to be done.
	 */
	private final ObservableList<ObservableTask> tasksList;

	/**
	 * List of graphic task of all the tasks.
	 */
	private final ObservableList<GraphicTask> graphicTaskList;


	private final ObservableList<ResourceController> controllerList;

	private final ObservableList<ObservablePlaning> planingList;



	/**
	 * Create a new empty planing
	 * @param duration duration in second.
	 * @see TimeConstant
	 */
	public PlaningFrame(long duration)
	{
		this.planingDuration = duration;
		this.secondPerPixel = 60;
		pixelInGraphic = (double)duration / (double)secondPerPixel;


		container = new VBox();
		container.setSpacing(2);

//		container = new Group();

		tasksList = FXCollections.observableArrayList();
		graphicTaskList = FXCollections.observableArrayList();

		planingList = FXCollections.observableArrayList();
		controllerList = FXCollections.observableArrayList();
	}

	/**
	 * Create a new planing with a complete solution.
	 * @param scenario a scenario for the planing
	 * @param duration duration of the planing in second.
	 */
	public PlaningFrame(ObservableScenario scenario, long duration)
	{
		this.planingDuration = duration;
		this.secondPerPixel = 60;
		pixelInGraphic = (double)duration / (double)secondPerPixel;


		container = new VBox();
		container.setSpacing(2);

//		container = new Group();

		tasksList = scenario.getTasks();
		graphicTaskList = FXCollections.observableArrayList();

		planingList = scenario.getSolution();
		controllerList = newResource(planingList);
	}



	/**
	 * Return a new empty resource (line) in the planing.
	 */
	public ResourceController newResource()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/viewPlaning/resourceLine.fxml"));

		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ResourceController temp = loader.getController();

		temp.getPlaning().setPrefWidth(pixelInGraphic);

		return temp;
	}

	/**
	 * Return a new resource (line) in the planing with a complete"" planing
	 * @param planing the planing to be affected to the resource
	 */
	protected ResourceController newResource(ObservablePlaning planing)
	{
		ResourceController temp = newResource();

		temp.setIdText(String.valueOf(planing.getId()));//TODO : binding
//		temp.setPreviousIdText(String.valueOf(p.getNextService().getID()));//TODO : update previous
//		temp.setNextIdText(String.valueOf(p.getNextService().getID()));//TODO : update previous

		for(ObservableTask ot : planing.getResourcePlanig())
			temp.addGraphicalTask(ot, new GraphicTask(ot, secondPerPixel));


		return temp;
	}

	/**
	 * Return a list of new resources with complete "" planing
	 * @param planingList planing list
	 */
	protected ObservableList<ResourceController> newResource(ObservableList<ObservablePlaning> planingList)
	{
		ObservableList<ResourceController> res = FXCollections.observableArrayList();

		for(ObservablePlaning p : planingList)
		{
			ResourceController temp = newResource(p);
			container.getChildren().add(temp.getLine());
			res.add(temp);
		}

		return res;
	}



	/**
	 * useless for the moment
	 */
	public void addResource(ResourceController resource)
	{
		controllerList.add(resource);

		ObservablePlaning temp = new ObservablePlaning(resource.getTaskPlaning());
		planingList.add(temp);
	}


	public Node getContainer(){
		return container;
	}

}
