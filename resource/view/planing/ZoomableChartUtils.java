package view.planing;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class ZoomableChartUtils {
	private static final double SCALE_DELTA = 1.1;

	public static void makeChartZoomable(XYChart<Number, ?> graphe) {
		AxisHandler handler = new AxisHandler((ValueAxis<Number>) graphe.getXAxis());
		graphe.setOnScroll(handler.getZoomHandler());
		graphe.setOnMousePressed(handler.getDragHandler());
		graphe.setOnMouseDragged(handler.getDragHandler());
	}








	private static class AxisHandler {
		private ZoomHandler zoom;
		private DragHandler drag;
		private DoubleProperty minValue;
		private DoubleProperty maxValue;
		private ValueAxis<Number> xAxis;

		public AxisHandler(ValueAxis<Number> xAxis) {
			this.xAxis = xAxis;
			this.zoom = new ZoomHandler(this);
			this.drag = new DragHandler(this);
			this.minValue = new SimpleDoubleProperty();
			this.maxValue = new SimpleDoubleProperty();
			System.out.println(xAxis.getLowerBound() + "  " + xAxis.getUpperBound());

			addAutoRangeListener();
		}

		private void addAutoRangeListener() {
			if (xAxis.isAutoRanging()) bindBounds();
			else unbindBounds();
			xAxis.autoRangingProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) -> {
				if (newValue.booleanValue()) bindBounds();
				else unbindBounds();
			});
		}

		private void bindBounds() {
			minValue.bind(xAxis.lowerBoundProperty());
			maxValue.bind(xAxis.upperBoundProperty());
		}

		private void unbindBounds() {
			minValue.unbind();
			maxValue.unbind();
		}

		public void setAxisBounds(double lowerBound, double upperBound) {
			double newWidth = Math.min(maxValue.get() - minValue.get(), upperBound - lowerBound);
			double newMin = Math.min(Math.min(upperBound, maxValue.get()) - newWidth, lowerBound);
			newMin = Math.max(newMin, minValue.get());
			xAxis.setLowerBound(newMin);
			xAxis.setUpperBound(newMin + newWidth);
		}

		public double getAxisLowerBound() {
			return xAxis.getLowerBound();
		}

		public double getAxisUpperBound() {
			return xAxis.getUpperBound();
		}

		public void setAxisAutoRanging(boolean autoranging) {
			xAxis.setAutoRanging(autoranging);
		}

		public double computeRelativePosition(double x) {
			return Math.min((x - xAxis.getLayoutX())/xAxis.getWidth(), 1);
		}

		public double computeAxisRatioValuesWidth() {
			return (xAxis.getUpperBound() - xAxis.getLowerBound())/(xAxis.getWidth());
		}

		public ZoomHandler getZoomHandler() {
			return zoom;
		}

		public DragHandler getDragHandler() {
			return drag;
		}
	}












	private static class ZoomHandler implements EventHandler<ScrollEvent> {
		private AxisHandler mainHandler;

		public ZoomHandler(AxisHandler mainHandler) {
			this.mainHandler = mainHandler;
		}

		@Override
		public void handle(ScrollEvent event)
		{
			event.consume();
	        if (event.getDeltaY() == 0) return;

	        double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
	        Platform.runLater(() -> {
	        	mainHandler.setAxisAutoRanging(false);
	        	double newXmin = mainHandler.getAxisLowerBound();
	        	double newXmax = mainHandler.getAxisUpperBound();
	        	double newLength = Math.ceil((newXmax - newXmin)/scaleFactor);
	        	double eventRelativePosition = mainHandler.computeRelativePosition(event.getX());
	        	double eventPosX = newXmin + (newXmax - newXmin)*eventRelativePosition;
	        	newXmin = eventPosX - eventRelativePosition*newLength;
	        	newXmax = newXmin + newLength;
	        	mainHandler.setAxisBounds(newXmin, newXmax);
	        });
		}
	}







	private static class DragHandler implements EventHandler<MouseEvent> {
		private AxisHandler mainHandler;
		private double previousPos;

		public DragHandler(AxisHandler mainHandler) {
			this.mainHandler = mainHandler;
		}

		@Override
		public void handle(MouseEvent event) {
			if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
				if (event.getClickCount() == 2) {
		        	Platform.runLater(() -> {
		        		mainHandler.setAxisAutoRanging(true);
		        	});
		        }
				Platform.runLater(() -> {
					previousPos = event.getX();
				});
			} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
				Platform.runLater(() -> {
					mainHandler.setAxisAutoRanging(false);
					double pos = event.getX();
					if (pos != previousPos) {
						double diffX =  (pos - previousPos)*mainHandler.computeAxisRatioValuesWidth();
						mainHandler.setAxisBounds(mainHandler.getAxisLowerBound() - diffX, mainHandler.getAxisUpperBound() - diffX);
			        	previousPos = pos;
					}
				});
			}
		}

	}
}
