package view.planing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.ObservableTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Rectangle;


public class GanttChart2 extends XYChart<Number, Number>
{
	public GanttChart2(Axis<Number> xAxis, Axis<Number> yAxis){
		this(xAxis, yAxis, FXCollections.observableArrayList());
	}


	public GanttChart2(Axis<Number> xAxis, Axis<Number> yAxis, ObservableList<Series<Number, Number>> data)
	{
		super(xAxis, yAxis);

		if(!(xAxis instanceof ValueAxis))
			throw new IllegalArgumentException("The x axis must extend Number");

		setData(data);
	}




	@Override
	protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item)
	{
		getPlotChildren().add(item.getNode());
	}

	@Override
	protected void dataItemChanged(Data<Number, Number> item)
	{
	}

	@Override
	protected void dataItemRemoved(Data<Number, Number> item, Series<Number, Number> series)
	{
		getPlotChildren().remove(item.getNode());
		removeDataItemFromDisplay(series, item);
	}

	@Override
	protected void layoutPlotChildren()
	{
		double secondPerPixel = (((ValueAxis<Number>)getXAxis()).getUpperBound() - ((ValueAxis<Number>)getXAxis()).getLowerBound()) / getXAxis().getWidth();

		for(int s=0; s < getData().size(); s++)
		{
			Series<Number, Number> series = getData().get(s);
			Iterator<Data<Number,Number>> iter = getDisplayedDataIterator(series);

			while(iter.hasNext())
			{
				Data<Number,Number> item = iter.next();
				double x = getXAxis().getDisplayPosition(item.getXValue());
				double y = getYAxis().getDisplayPosition(item.getYValue());

				if (Double.isNaN(x) || Double.isNaN(y))
					continue;

				Parent container = (Parent) item.getNode();

				double width = ((ObservableTask)item.getExtraValue()).getEndingHour() - ((ObservableTask)item.getExtraValue()).getStartingHour();
				width /= secondPerPixel;

				for(Node n : ((Parent)container.getChildrenUnmodifiable().get(0)).getChildrenUnmodifiable())
					if (n instanceof Rectangle)
						((Rectangle) n).setWidth(width);

				container.setLayoutX(x);
				container.setLayoutY(y - 75/2);
			}
		}
	}

	@Override
	protected void seriesAdded(Series<Number, Number> series, int seriesIndex)
	{
		for(Data<Number, Number> d : series.getData())
			getPlotChildren().add(d.getNode());
	}

	@Override
	protected void seriesRemoved(Series<Number, Number> series)
	{
		for(Data<Number, Number> d : series.getData())
			getPlotChildren().remove(d.getNode());

		removeSeriesFromDisplay(series);
	}


	@Override
	protected void updateAxisRange()
	{
		final Axis<Number> xa = getXAxis();
		final Axis<Number> ya = getYAxis();
		List<Number> xData = null;
		List<Number> yData = null;

		if(xa.isAutoRanging()) xData = new ArrayList<Number>();
		if(ya.isAutoRanging()) yData = new ArrayList<Number>();

		if(xData != null || yData != null)
		{
			for(Series<Number, Number> s : getData())
				for(Data<Number, Number> d : s.getData())
				{
					ObservableTask task = (ObservableTask) d.getExtraValue();
					if(xData != null)
					{
						xData.add(task.getStartingHour());
						xData.add(task.getEndingHour());
					}
					if(yData != null)
						yData.add(d.getYValue());
				}
			if(xData != null) xa.invalidateRange(xData);
			if(yData != null) ya.invalidateRange(yData);
		}
	}


}
