package openjchart.charts;

import openjchart.Drawable;
import openjchart.data.DataSeries;
import openjchart.data.DataTable;
import openjchart.util.SettingsStorage;

/**
 * An interface providing functions for rendering shapes in a diagram.
 * It defines methods for:
 * <ul>
 * <li>Retrieving the shape of a certain cell in a DataTable</li>
 * <li>Getting and setting the shapes color</li>
 * <li>Getting and setting the bounds of the shape</li>
 * </ul>
 */
public interface ShapeRenderer extends SettingsStorage {
	/** Form of the shape */
	static final String KEY_SHAPE = "shape";
	/** Color of the shape */
	static final String KEY_COLOR = "shape.color";

	/**
	 * Returns the shape to be drawn for the specified data value.
	 * @param data data provided
	 * @param series view on the data
	 * @param row row of the value in the DataTable
	 * @return Drawable that represents the shape
	 */
	Drawable getShape(DataTable data, DataSeries series, int row);
}
