package view.planing;

import java.util.List;

import data.ObservablePlaning;
import data.ObservableTask;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import view.planing.graphicalTask.GraphicalTask;
import view.planing.util.InteractiveChart;

public class PlaningFrame
{

	private final GanttChart planing;

	private final GraphicalTask factoryPolicy;




	public PlaningFrame(GraphicalTask factoryPolicy)
	{
		this.factoryPolicy = factoryPolicy;
		this.planing = new GanttChart(generateXAxis(), generateYAxis());

		InteractiveChart.makeChartInteractive(planing);
	}

	/**
	 *
	 * @param solution can be a complete/partial solution
	 */
	public PlaningFrame(GraphicalTask factoryPolicy, List<ObservablePlaning> solution)
	{
		this.factoryPolicy = factoryPolicy;
		this.planing = new GanttChart(generateXAxis(), generateYAxis());

		for(ObservablePlaning s : solution.subList(5, 10))
			addResource(s);

		InteractiveChart.makeChartInteractive(planing);
	}


	protected NumberAxis generateXAxis()
	{
		NumberAxis xAxis = new NumberAxis();
		xAxis.setSide(Side.TOP);
		return xAxis;
	}


	protected CategoryAxis generateYAxis()
	{
		CategoryAxis yAxis = new CategoryAxis();
		return yAxis;
	}



	public void addResource()
	{
		this.planing.getData().add(new Series<Number, String>());
	}


	public void addResource(ObservablePlaning p)
	{
		Series<Number, String> temp = new Series<Number, String>();

		//update the categories of yAxis
		((CategoryAxis)planing.getYAxis()).getCategories().add("Res " + p.getId());

		for(ObservableTask t : p.getResourcePlanig())
		{
			Data<Number, String> temp2 = new Data<Number, String>(t.getStartingHour(), "Res " + p.getId(), t);
			temp2.setNode(factoryPolicy.getGraphic(t, 1000));
			temp.getData().add(temp2);
		}

		this.planing.getData().add(temp);
	}



	public GanttChart getChart(){
		return planing;
	}
}
