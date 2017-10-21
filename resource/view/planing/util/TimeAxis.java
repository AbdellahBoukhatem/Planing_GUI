package view.planing.util;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.charts.ChartLayoutAnimator;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.beans.property.DoubleProperty;
import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.css.StyleableDoubleProperty;
import javafx.scene.chart.ValueAxis;
import javafx.util.Duration;
import javafx.util.StringConverter;


@SuppressWarnings("restriction")
public class TimeAxis extends ValueAxis<Number>
{

	private final ChartLayoutAnimator animator = new ChartLayoutAnimator(this);
	private Object currentAnimationID;


	private final static double[] TICKS_UNIT = {1, 5, 10, 30, 60, 300, 600, 1800, 3600, 7200, 10800, 86400};

	private final static int[] MINOR_TICK_COUNT = {10, 5, 10, 6, 6, 5, 6, 6, 6, 8, 6, 24};

	private final static double MAX_TICK = 20;



	//------------------------------------------------- Public Properties -----------------------------------------------------------



	private DoubleProperty tickUnit = new StyleableDoubleProperty(10) {

		@Override
		protected void invalidated()
		{
			if(! isAutoRanging())
			{
				invalidateRange();
				requestAxisLayout();
			}
		}
		@Override
		public String getName() {
			return "tickUnit";
		}

		@Override
		public Object getBean() {
			return this;
		}

		@Override //TODO : add css style
		public CssMetaData<? extends Styleable, Number> getCssMetaData() {
			return null;
		}
	};

	public double getTickUnit() { return tickUnit.get(); }

	public DoubleProperty getTickUnitProperty() { return tickUnit; }

	public void setTickUnit(double tickUnit) { this.tickUnit.set(tickUnit); }





	//-------------------------------------------------------- Constructor ----------------------------------------------------------------------


	/**
	 * create an auto ranging TimeAxis
	 */
	public TimeAxis()
	{
		setTickLabelFormatter(new TimeFormater());
	};


	/**
	 * Create a non-auto-ranging NumberAxis with the given upper bound, lower bound and tick unit
	 *
	 * @param lowerBound The lower bound for this axis, ie min plottable value
	 * @param upperBound The upper bound for this axis, ie max plottable value
	 * @param tickUnit The tick unit, ie space between tickmarks (in data length)
	 */
	public TimeAxis(double lowerBound, double upperBound, double tickUnit)
	{
		super(lowerBound, upperBound);
		setTickUnit(tickUnit);
		setTickLabelFormatter(new TimeFormater());
	}



	//------------------------------------------------------- Inherited Methods -------------------------------------------------------------------------


	@Override
	protected List<Number> calculateMinorTickMarks()
	{
		final double tickUnit = getTickUnit();
		final double lowerBound = Math.ceil(getLowerBound()/tickUnit)*tickUnit;
		final double upperBound = Math.floor(getUpperBound()/tickUnit)*tickUnit;
		final double minorUnit = tickUnit/getMinorTickCount();

		ArrayList<Number> minorTickValues = new ArrayList<Number>();

		if(tickUnit > 0 && (upperBound - lowerBound)*getMinorTickCount()/tickUnit < 10000)
			for(double major = lowerBound; major < upperBound; major += tickUnit)
				for(double minor = major + minorUnit; minor < (major + tickUnit); minor += minorUnit)
					minorTickValues.add(minor);

		return minorTickValues;
	}

	/**
	 * Calculate a list of all the data values for each tick mark in range. The data value are going to be
	 */
	@Override
	protected List<Number> calculateTickValues(double length, Object range)
	{
		double[] rangeArray = (double[]) range;
		final double tickUnit = rangeArray[2];
		final double lowerBound = Math.ceil(rangeArray[0]/tickUnit)*tickUnit;
		final double upperBound = Math.floor(rangeArray[1]/tickUnit)*tickUnit;

		ArrayList<Number> tickValues = new ArrayList<Number>();

		if(tickUnit <= 0 || lowerBound == upperBound)
			tickValues.add(lowerBound);

		else
			if(tickUnit > 0 && (upperBound - lowerBound)/tickUnit < 2000)
				for(double major = lowerBound; major <= upperBound; major += tickUnit)
					tickValues.add(major);

		return tickValues;
	}




	@Override
	protected String getTickMarkLabel(Number value)
	{
		StringConverter<Number> formatter = getTickLabelFormatter();

		if(formatter == null)
			return value.toString();

		return formatter.toString(value);
	}



	@Override
	protected Object getRange()
	{
		double[] temp = {getLowerBound(), getUpperBound(), getTickUnit(), getScale()};
		return temp;
	}


	@Override
	protected void setRange(Object range, boolean animate)
	{
		double[] rangeArray = (double[]) range;
		final double lowerBound = rangeArray[0], upperBound = rangeArray[1], tickUnit = rangeArray[2], scale = rangeArray[3];
		double oldLowerBound = getLowerBound();

		setLowerBound(lowerBound);
		setUpperBound(upperBound);
		setTickUnit(tickUnit);

		if(animate)
		{
			animator.stop(currentAnimationID);
			currentAnimationID = animator.animate(
					new KeyFrame(Duration.ZERO,
							new KeyValue(currentLowerBound, oldLowerBound)
//							new KeyValue(scalePropertyImpl(), getScale())
					),
					new KeyFrame(Duration.millis(500),
							new KeyValue(currentLowerBound, lowerBound)
//							new KeyValue(scalePropertyImpl(), getScale())
					)
			);
		}
		else
			currentLowerBound.set(lowerBound);

		setScale(scale);
	}




	@Override
	protected Object autoRange(double minValue, double maxValue, double length, double labelSize)
	{
//		final boolean vertical = Side.LEFT.equals(getSide()) || Side.RIGHT.equals(getSide());

		// check if we need to force zero into range
//		if(isForceZeroInRange())
//		{
//			if(maxValue < 0)
//				maxValue = 0;
//			else
//				if(minValue >0)
//					minValue = 0;
//		}

		final double range = maxValue-minValue;
		// pad min and max by 2%, checking if the range is zero
		final double paddedRange = (range==0) ? 2 : Math.abs(range)*1.02;
		final double padding = (paddedRange - range) / 2;
		// if min and max are not zero then add padding to them
		double paddedMin = minValue - padding;
		double paddedMax = maxValue + padding;

		// check padding has not pushed min or max over zero line
		if ((paddedMin < 0 && minValue >= 0) || (paddedMin > 0 && minValue <= 0))
			paddedMin = 0;
		if ((paddedMax < 0 && maxValue >= 0) || (paddedMax > 0 && maxValue <= 0))
			paddedMax = 0;

		double tickUnit = (paddedMax - paddedMin) / MAX_TICK;
		int i=TICKS_UNIT.length-1;
		for(; i >=0 && tickUnit <= TICKS_UNIT[i] ; i--);
		tickUnit = TICKS_UNIT[i+1];

		//set the corresponding minor count tick
		setMinorTickCount(MINOR_TICK_COUNT[i+1]);


		final double newScale = calculateNewScale(length, paddedMin, paddedMax);

		return new double[]{paddedMin, paddedMax, tickUnit, newScale};
	}


	public void updateTicks()
	{
		double tickUnit = (getUpperBound() - getLowerBound()) / MAX_TICK;
		int i=TICKS_UNIT.length-1;
		for(; i >=0 && tickUnit <= TICKS_UNIT[i] ; i--);
		tickUnit = TICKS_UNIT[i+1];

//		System.out.println(TICKS_UNIT[i+1]);

		setTickUnit(tickUnit);

		//set the corresponding minor count tick
		setMinorTickCount(MINOR_TICK_COUNT[i+1]);
	}
}
