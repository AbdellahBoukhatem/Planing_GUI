package view.planing.graphicalTask;

import data.ObservableTask;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import util.TimeFormatter;


public class HermesFactory implements GraphicalTask
{

	@Override
	public Group getGraphic(ObservableTask data, double secondPerPixel)
	{
		AnchorPane container = new AnchorPane();

		addRectangles(container, data, secondPerPixel);
		addText(container, data);

		return new Group(container);
	}


	private void addRectangles(AnchorPane container, ObservableTask data, double secondPerPixel)
	{

		double width = data.getEndingHour() - data.getStartingHour();
		width /= secondPerPixel;

		Rectangle rec = new Rectangle(0, 40, width, 5);
		container.getChildren().add(rec);
	}


	private void addText(AnchorPane container, ObservableTask data)
	{
		//origin
		Text ot = new Text();
		ot.setText(data.getOrigin());
		ot.setLayoutY(30);
		AnchorPane.setLeftAnchor(ot, 0.);

		//destination
		Text dt = new Text();
		dt.setText(data.getDestination());
		dt.setLayoutY(30);
		AnchorPane.setRightAnchor(dt, 0.);

		//starting hour
		Text st = new Text();
		st.setText(TimeFormatter.fromLongtoString(data.getStartingHour(), TimeFormatter.timeInMinute).split(":")[1]);
		st.setLayoutY(55);
		AnchorPane.setLeftAnchor(st, 0.);

		//ending hour
		Text et = new Text();
		et.setText(TimeFormatter.fromLongtoString(data.getEndingHour(), TimeFormatter.timeInMinute).split(":")[1]);
		et.setLayoutY(60);
		AnchorPane.setRightAnchor(et, 0.);


		container.getChildren().add(ot);
		container.getChildren().add(dt);
		container.getChildren().add(st);
		container.getChildren().add(et);
	}

}
