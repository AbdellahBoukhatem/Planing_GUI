package view.planing.graphicalTask;

import data.ObservableTask;
import javafx.scene.Node;

public interface GraphicalTask
{

	public abstract Node getGraphic(ObservableTask data, double secondPerPixel);

}
