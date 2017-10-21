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


public class GanttChart extends XYChart<Number, String>
{
	public GanttChart(Axis<Number> xAxis, Axis<String> yAxis){
		this(xAxis, yAxis, FXCollections.observableArrayList());
	}


	public GanttChart(Axis<Number> xAxis, Axis<String> yAxis, ObservableList<Series<Number, String>> data)
	{
		super(xAxis, yAxis);

		if(!(xAxis instanceof ValueAxis))
			throw new IllegalArgumentException("The x axis must extend Number");

		setData(data);
	}




	@Override
	protected void dataItemAdded(Series<Number, String> series, int itemIndex, Data<Number, String> item)
	{
		getPlotChildren().add(item.getNode());
	}

	@Override
	protected void dataItemChanged(Data<Number, String> item)
	{
	}

	@Override
	protected void dataItemRemoved(Data<Number, String> item, Series<Number, String> series)
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
			Series<Number, String> series = getData().get(s);
			Iterator<Data<Number,String>> iter = getDisplayedDataIterator(series);

			while(iter.hasNext())
			{
				Data<Number,String> item = iter.next();
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
	protected void seriesAdded(Series<Number, String> series, int seriesIndex)
	{
		for(Data<Number, String> d : series.getData())
			getPlotChildren().add(d.getNode());
	}

	@Override
	protected void seriesRemoved(Series<Number, String> series)
	{
		for(Data<Number, String> d : series.getData())
			getPlotChildren().remove(d.getNode());

		removeSeriesFromDisplay(series);
	}


	@Override
	protected void updateAxisRange()
	{
		final Axis<Number> xa = getXAxis();
		final Axis<String> ya = (Axis<String>) getYAxis();
		List<Number> xData = null;
		List<String> yData = null;

		if(xa.isAutoRanging()) xData = new ArrayList<Number>();
		if(ya.isAutoRanging()) yData = new ArrayList<String>();

		if(xData != null || yData != null)
		{
			for(Series<Number, String> s : getData())
				for(Data<Number, String> d : s.getData())
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
