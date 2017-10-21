package viewPlaning;

import data.ObservableTask;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import util.TimeFormatter;

public class GraphicTask
{
	/**
	 * Id of the object same as ObservableTask
	 */
	private final int id;

	/**
	 * The pane containing the rectangle (representing the length of the task) and detail of the task
	 */
	private final AnchorPane container;




	//TODO : bind property of task to Graphical task
	protected GraphicTask(ObservableTask task, int secondPerPixel)
	{
		this.id = task.getTasksID();
		this.container = new AnchorPane();

		double ts=0;
		try	{
			ts = task.getStartingHour();
		}catch(Exception e){	e.printStackTrace();}

		this.container.setTranslateX(ts / secondPerPixel);
		this.container.translateXProperty().bind(task.getStartingHourProperty().divide(secondPerPixel));

		initRectangle(task, secondPerPixel);
		initTextField(task);
	}

	/**
	 * Draw the rectangle representing the task, and bind the color and the width to corresponding value in the observable task
	 * @param task observable task representing the graphic
	 * @param secondPerPixel number of second per pixel
	 */
	private void initRectangle(ObservableTask task, int secondPerPixel)
	{
		double ts=0, te=0;

		ts = task.getStartingHour();
		te = task.getEndingHour();


		double widthTask = (te - ts) / secondPerPixel;

		Rectangle rec = new Rectangle(0, 40, widthTask, 5);

		rec.fillProperty().bind(task.getColorProperty());



		//add a listener to the starting hour to change the width of the rectangle
		//the width decrease as the hour increase and vis versa
		task.getStartingHourProperty().addListener( (observable, oldValue, newValue) ->
		{
			double temp=0, temp2=0;

			try
			{
				temp = oldValue.doubleValue();
				temp2 = newValue.doubleValue();
			}
			catch (Exception e) {e.printStackTrace();}

			rec.setWidth(rec.getWidth() - (temp2-temp)/secondPerPixel);

		});


		//add a listener to the ending hour to change the width of the rectangle
		//the width increase as the hour increase and vis versa
		task.getEndingHourProperty().addListener( (observable, oldValue, newValue) ->
		{
			double temp=0, temp2=0;

			try
			{
				temp = oldValue.doubleValue();
				temp2 = newValue.doubleValue();
			}
			catch (Exception e) {e.printStackTrace();}

			rec.setWidth(rec.getWidth() + (temp2-temp)/secondPerPixel);

		});


		this.container.getChildren().add(rec);
	}

	private void initTextField(ObservableTask task)
	{
		//origin
		Text ot = new Text();
		ot.textProperty().bind(task.getOriginProperty());
		ot.setLayoutY(30);
		AnchorPane.setLeftAnchor(ot, 0.);

		//destination
		Text dt = new Text();
		dt.textProperty().bind(task.getDestinationProperty());
		dt.setLayoutY(30);
		AnchorPane.setRightAnchor(dt, 0.);

		//starting hour
		Text st = new Text();
		st.setText(TimeFormatter.fromLongtoString(task.getStartingHour(), TimeFormatter.timeInMinute).split(":")[1]);
		st.setLayoutY(55);
		AnchorPane.setLeftAnchor(st, 0.);

		//add listener to the property
		task.getStartingHourProperty().addListener((observable, oldValue, newValue) ->
		{
			st.setText(TimeFormatter.fromLongtoString(newValue.longValue(), TimeFormatter.timeInMinute).split(":")[1]);
		});

		//ending hour
		Text et = new Text();
		et.setText(TimeFormatter.fromLongtoString(task.getEndingHour(), TimeFormatter.timeInMinute).split(":")[1]);
		et.setLayoutY(60);
		AnchorPane.setRightAnchor(et, 0.);

		//add listener to the property
		task.getEndingHourProperty().addListener((observable, oldValue, newValue) ->
		{
			et.setText(TimeFormatter.fromLongtoString(newValue.longValue(), TimeFormatter.timeInMinute).split(":")[1]);
		});

		this.container.getChildren().add(ot);
		this.container.getChildren().add(dt);
		this.container.getChildren().add(st);
		this.container.getChildren().add(et);
	}


	public int getId() {
		return id;
	}

	public AnchorPane getContainer() {
		return container;
	}

}
