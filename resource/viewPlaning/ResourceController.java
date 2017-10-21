package viewPlaning;

import data.ObservableTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ResourceController
{

	protected static int nbObject = 0;

	@FXML
	private AnchorPane line;

	@FXML
	private AnchorPane detail;

	@FXML
	private AnchorPane planing;

	@FXML
	private Text previousIdText;

	@FXML
	private Text idText;

	@FXML
	private Text nextIdText;

	/**
	 * This list represent the task done by this resource.
	 */
	private ObservableList<ObservableTask> taskPlaning;

	/**
	 * This list represent the graphic task in the {@code planing}.
	 */
	private ObservableList<GraphicTask> graphicTaskPlanig;






	@FXML
	public void initialize()
	{
		nbObject++;

		idText.setText(String.valueOf(nbObject));
		previousIdText.setText(String.valueOf(nbObject));
		nextIdText.setText(String.valueOf(nbObject));


		taskPlaning = FXCollections.observableArrayList();
		graphicTaskPlanig = FXCollections.observableArrayList();

//		idText.textProperty().bindBidirectional(new SimpleBooleanProperty(false), new IntegerStringConverter());
	}



	/**
	 * Add a single task to the planing
	 * @param oTask observable task to add.
	 * @param gTask the graphic representation of the task to add.
	 */
	public void addGraphicalTask(ObservableTask oTask, GraphicTask gTask)
	{
		taskPlaning.add(oTask);
		graphicTaskPlanig.add(gTask);
		planing.getChildren().add(gTask.getContainer());
	}

	/**
	 * Add a task list to the planing
	 * @param tasks list of observable task to add.
	 * @param gTasks list of graphic tasks to add.
	 */
	public void addGraphicalTasks(ObservableList<ObservableTask> tasks, ObservableList<GraphicTask> gTasks)
	{
		taskPlaning.addAll(tasks);
		graphicTaskPlanig.addAll(gTasks);
		for(GraphicTask t : gTasks)
			planing.getChildren().add(t.getContainer());
	}



	/**
	 * Return the container of the line
	 */
	public AnchorPane getLine() {
		return line;
	}

	/**
	 * Return the container of the detail of the resource
	 */
	public AnchorPane getDetail() {
		return detail;
	}

	/**
	 * Return the container of the graphical planing
	 */
	public AnchorPane getPlaning() {
		return planing;
	}

	public ObservableList<ObservableTask> getTaskPlaning() {
		return taskPlaning;
	}

	public ObservableList<GraphicTask> getGraphicTaskPlanig() {
		return graphicTaskPlanig;
	}

	public String getPreviousIdText() {
		return previousIdText.getText();
	}

	public void setPreviousIdText(String previousIdText) {
		this.previousIdText.setText(previousIdText);
	}

	public String getIdText() {
		return idText.getText();
	}

	public void setIdText(String idText) {
		this.idText.setText(idText);
	}

	public String getNextIdText() {
		return nextIdText.getText();
	}

	public void setNextIdText(String nextIdText) {
		this.nextIdText.setText(nextIdText);
	}

}
