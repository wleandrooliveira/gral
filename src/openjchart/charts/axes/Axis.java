package openjchart.charts.axes;

import java.util.ArrayList;
import java.util.List;

public class Axis {
	private final List<AxisListener> axisListeners;

	private Number min;
	private Number max;

	/**
	 * Creates an <code>Axis</code> object with the specified minimum and maximum values.
	 * @param min minimum value
	 * @param max maximum value
	 */
	public Axis(Number min, Number max) {
		axisListeners = new ArrayList<AxisListener>();

		this.min = min;
		this.max = max;
	}

	/**
	 * Adds the specified <code>AxisListener</code> to this Axis.
	 * The Listeners will be notified if changes to the Axis occur,
	 * for Example if the minimum or maximum value changes.
	 * @param listener Listener to be added
	 * @see AxisListener
	 */
	public void addAxisListener(AxisListener listener) {
		axisListeners.add(listener);
	}

	/**
	 * Removes the specified <code>AxisListener</code> from this Axis.
	 * @param listener Listener to be removed
	 * @see AxisListener
	 */
	public void removeAxisListener(AxisListener listener) {
		axisListeners.remove(listener);
	}

	/**
	 * Notifies all registered <code>AxisListener</code>s that the value
	 * range has changed.
	 * @param min new minimum value
	 * @param max new maximum value
	 */
	private void fireRangeChanged(Number min, Number max) {
		for (AxisListener listener : axisListeners) {
			listener.rangeChanged(min, max);
		}
	}

	/**
	 * Returns the relative position of the specified value on the <code>Axis</code>.
	 * The values range from 0.0 to 1.0, whereas 0.0 represents the minimum
	 * value and 1.0 represents the maximum value.
	 * @param value value to calculate the relative position of
	 * @return relative position to the minimum value of the axis
	 */
	public double getPos(Number value) {
		double val = value.doubleValue();
		double displayRange = max.doubleValue() - min.doubleValue();
		double displayPercentage = (val - min.doubleValue()) / displayRange;

		return displayPercentage;
	}

	public Number getMin() {
		return min;
	}

	public void setMin(Number min) {
		setRange(min, this.max);
	}

	public Number getMax() {
		return max;
	}

	public void setMax(Number max) {
		setRange(this.min, max);
	}

	public Number getRange() {
		return max.doubleValue() - min.doubleValue();
	}

	public void setRange(Number min, Number max) {
		this.min = min;
		this.max = max;
		fireRangeChanged(min, max);
	}
}