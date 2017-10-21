package view.planing;

import java.util.List;

import data.ObservablePlaning;
import data.ObservableTask;
import javafx.geometry.Side;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import view.planing.graphicalTask.GraphicalTask;
import view.planing.util.InteractiveChart;
import view.planing.util.InteractiveChart2;
import view.planing.util.TimeAxis;

public class PlaningFrame2
{

	private final GanttChart2 planing;

	private final GraphicalTask factoryPolicy;




	public PlaningFrame2(GraphicalTask factoryPolicy)
	{
		this.factoryPolicy = factoryPolicy;
		this.planing = new GanttChart2(generateXAxis(), generateYAxis());

		InteractiveChart.makeChartInteractive(planing);
	}

	/**
	 *
	 * @param solution can be a complete/partial solution
	 */
	public PlaningFrame2(GraphicalTask factoryPolicy, List<ObservablePlaning> solution)
	{
		this.factoryPolicy = factoryPolicy;
		this.planing = new GanttChart2(generateXAxis(), generateYAxis());

		for(ObservablePlaning s : solution)
			addResource(s);

		InteractiveChart.makeChartInteractive(planing);
		InteractiveChart2.makeChartInteractive(planing);
	}


	protected ValueAxis<Number> generateXAxis()
	{
		TimeAxis xAxis = new TimeAxis();
//		xAxis.setForceZeroInRange(false);
		xAxis.setSide(Side.TOP);
		return xAxis;
	}


	protected NumberAxis generateYAxis()
	{
		NumberAxis yAxis = new NumberAxis();
		yAxis.setTickUnit(1);
		return yAxis;
	}



	public void addResource()
	{
		this.planing.getData().add(new Series<Number, Number>());
	}


	public void addResource(ObservablePlaning p)
	{
		Series<Number, Number> temp = new Series<Number, Number>();

		//update the categories of yAxis
//		planing.getYAxis().add(p.getId());

		for(ObservableTask t : p.getResourcePlanig())
		{
			Data<Number, Number> temp2 = new Data<Number, Number>(t.getStartingHour(), p.getId(), t);
			temp2.setNode(factoryPolicy.getGraphic(t, 1000));
			temp.getData().add(temp2);
		}

		this.planing.getData().add(temp);
	}



	public GanttChart2 getChart(){
		return planing;
	}
}
