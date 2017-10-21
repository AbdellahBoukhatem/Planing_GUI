package view;

import java.io.IOException;

import data.ObservableTask;
import data.Task;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ScrollEvent;


public class MainFarmeController
{
	@FXML
	private TabPane data;

	@FXML
	private Tab tasks;

	@FXML
	private Tab undoneTasks;

	@FXML
	private Tab graphic;

	@FXML
	private Group groupe;


	private DataViewController tasksController;


	private ObservableList<ObservableTask> taskList;


	@FXML
	public void initialize()
	{
//		taskList = FXCollections.observableArrayList();
//
//		try
//		{
//			ArrayList<Mission> temp = (new ReaderExcelTransilien("SA 2018 identique_0.xlsx")).readingMissions();
//			for(Task t : temp)
//				taskList.add(new ObservableTask(t));
//		}
//		catch (Exception e1)
//		{
//			e1.printStackTrace();
//		}


//		groupe.setAutoSizeChildren(true);



		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dataView.fxml"));
		try
		{
			tasks.setContent(loader.load());
			tasksController = loader.getController();
			tasksController.bindToParent(data);
//			tasksController.setList(taskList);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		//initialize the side bar to hidden
		data.setMaxWidth(0);
		data.setMinWidth(0);
		data.setVisible(false);

//		//teeeeeeeeeeeeeeeeeeeeeeest
//		graphic.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>()
//		{
//			public void handle(ScrollEvent event)
//			{
//				if(event.isControlDown())
//				{
//					double rate = graphic.getContent().getScaleX();
//
//		            if (event.getDeltaY() < 0)
//		            	rate /= 1.5;
//		            else
//		            	rate *= 1.5;
//
//		            graphic.getContent().setScaleX(rate);
//		            graphic.getContent().setScaleY(rate);
//
//
////		            graphic.setViewportBounds(graphic.getContent().getBoundsInParent());
//
//
//		            System.out.println(graphic.getContent().getLayoutBounds().getWidth());
//		            System.out.println(graphic.getContent().getBoundsInParent().getWidth());
//		            System.out.println(graphic.getContent().getBoundsInLocal().getWidth());
//
//		            System.out.println(graphic.getViewportBounds().getWidth());
//
////		            graphic.viewportBoundsProperty().bind(graphic.getContent().boundsInParentProperty());
//
////		            event.consume();
//
//		            graphic.layout();
//				}
//			}
//
//		});

	}


	/**
	 * Delete the parameter given from the list, if it exist.
	 * @param t object to be deleted
	 * @return {@code true} if the object is deleted, {@code false} otherwise.
	 */
	public boolean removeTask(ObservableTask t) {
		return taskList.remove(t);
	}

	/**
	 * Delete the parameter given from the list, if it exist.
	 * @param t object to be deleted
	 * @return {@code true} if the object is deleted, {@code false} otherwise.
	 */
	public boolean removeTask(Task t) {
		return taskList.remove(t);
	}

	public ObservableTask removeTask(int index){
		return taskList.remove(index);
	}



	/**
	 * Return the container of the data.
	 */
	public TabPane getData() {
		return data;
	}

	/**
	 * Return the container of the data table.
	 */
	public Tab getTasks() {
		return tasks;
	}

	/**
	 * Return the container of the undone tasks table.
	 */
	public Tab getUndoneTasks() {
		return undoneTasks;
	}

	/**
	 *Return the container of the graphics
	 */
	public Tab getGraphic() {
		return graphic;
	}

	/**
	 * Return the controller of the data view
	 */
	public DataViewController getTasksController() {
		return tasksController;
	}

	/**
	 * Return the list of data
	 */
	public ObservableList<ObservableTask> getTaskList() {
		return taskList;
	}
}
