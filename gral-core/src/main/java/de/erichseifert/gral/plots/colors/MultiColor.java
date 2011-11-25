/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2011 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <michael.seifert[at]gmx.net>
 *
 * This file is part of GRAL.
 *
 * GRAL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GRAL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GRAL.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.erichseifert.gral.plots.colors;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.erichseifert.gral.util.MathUtils;

/**
 * Linearly blends different colors for values between 0.0 and 1.0.
 */
public class MultiColor extends ScaledColorMapper {
	/** Colors that will be used for blending. **/
	private final List<Color> colors;

	/**
	 * Creates a new instance with at least two colors.
	 * @param color1 First color.
	 * @param color2 Second color.
	 * @param colors More colors.
	 */
	public MultiColor(Color color1, Color color2, Color... colors) {
		this.colors = new ArrayList<Color>();
		this.colors.add(color1);
		this.colors.add(color2);
		this.colors.addAll(Arrays.asList(colors));
	}

	/**
	 * Returns the Paint according to the specified value.
	 * @param value Value of color.
	 * @return Paint.
	 */
	public Paint get(double value) {
		double x = scale(value);
		int colorMax = colors.size() - 1;
		double pos = MathUtils.limit(x*colorMax, 0.0, colorMax);

		if (pos == 0.0) {
			return colors.get(0);
		}
		if (pos == colorMax) {
			return colors.get(colorMax);
		}

		double fract = pos - (int) pos;
		Color color1 = colors.get((int) pos);

		if (fract == 0.0) {
			return color1;
		}

		double fractInv = 1.0 - fract;
		Color color2 = colors.get((int) pos + 1);

		double r = fractInv*color1.getRed()   + fract*color2.getRed();
		double g = fractInv*color1.getGreen() + fract*color2.getGreen();
		double b = fractInv*color1.getBlue()  + fract*color2.getBlue();
		double a = fractInv*color1.getAlpha() + fract*color2.getAlpha();

		return new Color(
			(int) Math.round(r),
			(int) Math.round(g),
			(int) Math.round(b),
			(int) Math.round(a)
		);
	}

	/**
	 * Returns the colors that are used for blending.
	 * @return A list of colors in the order they will be used for blending.
	 */
	public List<Color> getColors() {
		return Collections.unmodifiableList(colors);
	}
}