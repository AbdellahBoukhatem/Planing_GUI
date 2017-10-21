package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import data.ObservableTask;
import util.TimeFormatter;


public class DataViewController
{

	@FXML
	private TableView<ObservableTask> taskTable;

	@FXML
	private TableColumn<ObservableTask, String> trainNumberColumn;

	@FXML
	private TableColumn<ObservableTask, String> originColumn;

	@FXML
	private TableColumn<ObservableTask, String> startingHourColumn;

	@FXML
	private TableColumn<ObservableTask, String> destinationColumn;

	@FXML
	private TableColumn<ObservableTask, String> endingHourColumn;

	@FXML
	private TableColumn<ObservableTask, String> MaterialColumn;




	@FXML
	public void initialize()
	{
		trainNumberColumn.setCellValueFactory(cell -> cell.getValue().getTrainNumberProperty());
		originColumn.setCellValueFactory(cell -> cell.getValue().getOriginProperty());
		destinationColumn.setCellValueFactory(cell -> cell.getValue().getDestinationProperty());
		startingHourColumn.setCellValueFactory(cell -> new SimpleStringProperty(TimeFormatter.fromLongtoString(cell.getValue().getStartingHour(), TimeFormatter.timeInMinute)));
		endingHourColumn.setCellValueFactory(cell -> new SimpleStringProperty(TimeFormatter.fromLongtoString(cell.getValue().getEndingHour(), TimeFormatter.timeInMinute)));
//		MaterialColumn.setCellValueFactory(cell -> cell.getValue().getMaterialsProperty());

		for(TableColumn<ObservableTask, ?> col : taskTable.getColumns())
		{
			ContextMenu m = new ContextMenu(new MenuItem("open"), new MenuItem("bof"));
			col.setContextMenu(m);
		}


		MenuItem mI = new MenuItem("open");
		mI.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				ObservableTask selection = taskTable.getSelectionModel().getSelectedItem();
				if(selection != null)
					System.out.println(selection.getTrainNumber());
			}

		});

		ContextMenu m = new ContextMenu(mI, new MenuItem("nihaw"), new SeparatorMenuItem(), new MenuItem("open"), new MenuItem("open"));
		taskTable.setContextMenu(m);


		taskTable.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event)
			{
				if( event.getButton() == MouseButton.PRIMARY && event.getClickCount() >= 2 && taskTable.getSelectionModel().getSelectedItem() != null)
				{
					System.out.println("nihaw");
				}
			}
		});


	}



	public void setList(ObservableList<ObservableTask> list){


		FilteredList<ObservableTask> filteredData = new FilteredList<ObservableTask>(list, task ->
		{
//			if(task.getDestination().equals("RBT"))
//				return true;
//			return false;
			return true;
		});

		SortedList<ObservableTask> sortedList = new SortedList<>(filteredData);

		sortedList.comparatorProperty().bind(taskTable.comparatorProperty());

		taskTable.setItems(sortedList);
	}



	public void removeTask(int index)
	{
		taskTable.getItems().remove(index);
	}



	public boolean deleteTask(ObservableTask task)
	{
		return taskTable.getItems().remove(task);
	}


	public void bindToParent(TabPane parent)
	{
		if(parent.getSide() == Side.BOTTOM || parent.getSide() == Side.TOP)
		{
			taskTable.prefWidthProperty().bind(parent.widthProperty());
			taskTable.prefHeightProperty().bind(parent.heightProperty().subtract(30));
		}
		else
		{
			taskTable.prefWidthProperty().bind(parent.widthProperty().subtract(30));
			taskTable.prefHeightProperty().bind(parent.heightProperty());
		}
	}

}
