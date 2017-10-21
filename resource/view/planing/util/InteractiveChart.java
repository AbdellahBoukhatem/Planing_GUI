package view.planing.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;


public class InteractiveChart
{

	private final static double SCALE_DELTA = 1.1;

	private InteractiveChart(){};


	public static void makeChartInteractive(XYChart<Number, ?> chart)
	{
		XAxisHandler handler = new XAxisHandler((ValueAxis<Number>) chart.getXAxis());
		chart.addEventFilter(ScrollEvent.SCROLL, handler.zoom);
		chart.addEventFilter(MouseEvent.MOUSE_PRESSED, handler.drag.onPressedEvent);
		chart.addEventFilter(MouseEvent.MOUSE_CLICKED, handler.drag.onClickedEvent);
		chart.addEventFilter(MouseEvent.MOUSE_DRAGGED, handler.drag.onDraggedEvent);
	}


	/**
	 *
	 */
	protected static class XAxisHandler
	{
		private ZoomHandler zoom;

		private DragHandler drag;

		/**
		 * Minimum value of the axis (smallest value of the data)
		 */
		private DoubleProperty minValue;
		/**
		 * Maximum value of the axis (biggest value of the data)
		 */
		private DoubleProperty maxValue;

		private ValueAxis<Number> axis;


		private XAxisHandler(ValueAxis<Number> xAxis)
		{
			this.axis = xAxis;
			this.minValue = new SimpleDoubleProperty();
			this.maxValue = new SimpleDoubleProperty();
			this.zoom = new ZoomHandler(this);
			this.drag = new DragHandler(this);

			if(xAxis.isAutoRanging())
			{
				minValue.bind(xAxis.lowerBoundProperty());
				maxValue.bind(xAxis.upperBoundProperty());
			}

			xAxis.autoRangingProperty().addListener((obs, old, newV) ->
			{
				if(newV)
				{
					minValue.bind(xAxis.lowerBoundProperty());
					maxValue.bind(xAxis.upperBoundProperty());
				}
				else
				{
					minValue.unbind();
					maxValue.unbind();
				}
			});
		}

		private void setAxisViewBound(double lower, double upper)
		{
			double newWidth = Math.min(upper - lower, maxValue.get() - minValue.get());
			double newMin = Math.min(Math.min(upper, maxValue.get()) - newWidth, lower);
			newMin = Math.max(newMin, minValue.get());
			axis.setUpperBound(newMin + newWidth);
			axis.setLowerBound(newMin);
		}





		protected static class ZoomHandler implements EventHandler<ScrollEvent>
		{
			private XAxisHandler handler;

			private ZoomHandler(XAxisHandler handler) {
				this.handler = handler;
			}

			@Override
			public void handle(ScrollEvent event)
			{

				if(event.isControlDown())
				{
					handler.axis.setAutoRanging(false);

					double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1. / SCALE_DELTA;
					double actualMin = handler.axis.getLowerBound();
					double actualMax = handler.axis.getUpperBound();
					double length = (actualMax - actualMin) / scaleFactor;
					double newMin = (actualMax + actualMin) / 2 - length / 2;
					handler.setAxisViewBound(newMin, newMin + length);

					if(handler.axis instanceof TimeAxis)
						((TimeAxis)handler.axis).updateTicks();
				}
			}

		}




		protected static class DragHandler
		{
			private XAxisHandler handler;

			private double previousPosition;


			private EventHandler<MouseEvent> onPressedEvent = new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event)
				{
					if(event.isSecondaryButtonDown())
						previousPosition = event.getX();
				}
			};

			private EventHandler<MouseEvent> onClickedEvent = new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.SECONDARY && event.getClickCount() == 2)
						handler.axis.setAutoRanging(true);
				}
			};

			private EventHandler<MouseEvent> onDraggedEvent = new EventHandler<MouseEvent>()
			{
				@Override
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.SECONDARY)
					{
						handler.axis.setAutoRanging(false);
						double delta = (event.getX() - previousPosition)*(handler.axis.getUpperBound() - handler.axis.getLowerBound()) / handler.axis.getWidth();
						handler.setAxisViewBound(handler.axis.getLowerBound() - delta, handler.axis.getUpperBound() - delta);
						previousPosition = event.getX();
					}
				}
			};


			private DragHandler(XAxisHandler handler) {
				this.handler = handler;
			}

		}
	}
}
