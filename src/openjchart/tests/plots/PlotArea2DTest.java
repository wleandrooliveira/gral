/* OpenJChart : a free plotting library for the Java(tm) platform
 *
 * (C) Copyright 2009, by Erich Seifert and Michael Seifert.
 *
 * This file is part of OpenJChart.
 *
 * OpenJChart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenJChart is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenJChart.  If not, see <http://www.gnu.org/licenses/>.
 */

package openjchart.tests.plots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import openjchart.PlotArea2D;
import openjchart.plots.Plot;

import org.junit.Before;
import org.junit.Test;

public class PlotArea2DTest {
	private PlotArea2D plotArea;

	@Before
	public void setUp() {
		plotArea = new PlotArea2D() {
			@Override
			public void draw(Graphics2D g2d) {
				drawBackground(g2d);
				drawBorder(g2d);
				drawPlot(g2d);
			}
			@Override
			protected void drawPlot(Graphics2D g2d) {
			}
		};
	}

	@Test
	public void testSettings() {
		// Get
		assertEquals(Color.WHITE, plotArea.getSetting(PlotArea2D.KEY_BACKGROUND));

		// Set
		plotArea.setSetting(PlotArea2D.KEY_BACKGROUND, "foobar");
		assertEquals("foobar", plotArea.<String>getSetting(PlotArea2D.KEY_BACKGROUND));

		// Remove
		plotArea.removeSetting(PlotArea2D.KEY_BACKGROUND);
		assertNull(plotArea.getSetting(PlotArea2D.KEY_BACKGROUND));
	}

	@Test
	public void testDraw() {
		plotArea.setSetting(Plot.KEY_BACKGROUND, Color.WHITE);
		plotArea.setSetting(Plot.KEY_BORDER, new BasicStroke(1f));

		BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();
		plotArea.setBounds(0.0, 0.0, image.getWidth(), image.getHeight());
		plotArea.draw(g2d);
	}

}
