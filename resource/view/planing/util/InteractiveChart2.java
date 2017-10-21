package view.planing.util;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.input.ScrollEvent;

public class InteractiveChart2
{
	private final static int VISIBLE_CATEGORIES = 10;

	private final static double SCROLLING_PERCENT = 0.05;

	private InteractiveChart2(){};


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void makeChartInteractive(XYChart chart)
	{
		ValueAxis<Number> yAxis = (ValueAxis<Number>)chart.getYAxis();
		YAxisHandler handler = new YAxisHandler(yAxis, chart.getData());

		if(chart.getData().size() > VISIBLE_CATEGORIES)
		{
			yAxis.setAutoRanging(false);
			yAxis.setLowerBound(0);
			yAxis.setUpperBound(VISIBLE_CATEGORIES);
		}

		chart.addEventFilter(ScrollEvent.SCROLL, handler.scroll);
	}




	private static class YAxisHandler
	{
		private ScrollHandler scroll;

		/**
		 * number of categories
		 */
		private double size;

		private ValueAxis<Number> axis;




		private YAxisHandler(ValueAxis<Number> axis, ObservableList<Series<?, Number>> data)
		{
			this.axis = axis;
			this.size = data.size() + 1;
			this.scroll = new ScrollHandler(this);

			data.addListener((ListChangeListener<? super Series<?,Number>>)change ->
			{
				while (change.next())
				{
					if(change.wasRemoved())
						this.size--;
					if(change.wasAdded())
						this.size++;
				}
			});
		}

		private void setAxisViewBound(double lower, double upper)
		{
			double newMin = Math.min(Math.min(upper, size) - VISIBLE_CATEGORIES, lower);
			newMin = Math.max(newMin, 0);
			axis.setUpperBound(newMin + VISIBLE_CATEGORIES);
			axis.setLowerBound(newMin);
		}






		private static class ScrollHandler implements EventHandler<ScrollEvent>
		{
			private YAxisHandler handler;

			private ScrollHandler(YAxisHandler handler) {
				this.handler = handler;
			}

			@Override
			public void handle(ScrollEvent event)
			{
				if(!event.isControlDown() && !event.isAltDown() && handler.size > VISIBLE_CATEGORIES)
				{
					handler.axis.setAutoRanging(false);
					((NumberAxis)handler.axis).setTickUnit(1);
					int delta = (event.getDeltaY() > 0) ? 1 : -1;
					delta *= (handler.size * SCROLLING_PERCENT);
					handler.setAxisViewBound(handler.axis.getLowerBound() + delta, handler.axis.getUpperBound() + delta);
				}
			}
		}
	}
}
