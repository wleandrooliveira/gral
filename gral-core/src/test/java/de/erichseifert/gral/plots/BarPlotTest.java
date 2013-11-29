/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2013 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <michael[at]erichseifert.de>
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
package de.erichseifert.gral.plots;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.erichseifert.gral.TestUtils;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DummyData;
import de.erichseifert.gral.graphics.DrawingContext;
import de.erichseifert.gral.plots.BarPlot.BarRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;

public class BarPlotTest {
	private MockBarPlot plot;

	private static final class MockBarPlot extends BarPlot {
		/** Version id for serialization. */
		private static final long serialVersionUID = -6215127935611125964L;

		public boolean isDrawn;

		public MockBarPlot(DataSource... data) {
			super(data);
		}

		@Override
		public void draw(DrawingContext context) {
			super.draw(context);
			isDrawn = true;
		}
	}

	@Before
	public void setUp() {
		DataSource data = new DummyData(2, 1, 1.0);
		plot = new MockBarPlot(data);

		BarRenderer pointRenderer = (BarRenderer) plot.getPointRenderer(data);
		pointRenderer.setStroke(new BasicStroke());
	}

	@Test
	public void testDraw() {
		plot.getAxis(BarPlot.AXIS_X).setRange(-1.0, 3.0);
		plot.getAxis(BarPlot.AXIS_Y).setRange(-1.0, 2.0);
		BufferedImage image = new BufferedImage(320, 240, BufferedImage.TYPE_INT_ARGB);
		plot.setBounds(0.0, 0.0, image.getWidth(), image.getHeight());
		DrawingContext context = new DrawingContext((Graphics2D) image.getGraphics());
		plot.draw(context);
		assertTrue(plot.isDrawn);
	}

	@Test
	public void testSerialization() throws IOException, ClassNotFoundException {
		BarPlot original = plot;
		BarPlot deserialized = TestUtils.serializeAndDeserialize(original);

		TestUtils.assertSettings(original, deserialized);

		assertEquals(original.getBackground(), deserialized.getBackground());
		assertEquals(original.getBorder(), deserialized.getBorder());

		List<DataSource> dataSourcesOriginal = original.getData();
		List<DataSource> dataSourcesDeserialized = deserialized.getData();
		assertEquals(dataSourcesOriginal.size(), dataSourcesDeserialized.size());
		for (int index = 0; index < dataSourcesOriginal.size(); index++) {
			PointRenderer pointRendererOriginal = original.getPointRenderer(
							dataSourcesOriginal.get(index));
			PointRenderer pointRendererDeserialized = deserialized.getPointRenderer(
							dataSourcesDeserialized.get(index));
			testPointRendererSerialization(pointRendererOriginal, pointRendererDeserialized);
		}
    }

	private static void testPointRendererSerialization(
			PointRenderer originalRenderer, PointRenderer deserializedRenderer) {
		BarRenderer original = (BarRenderer) originalRenderer;
		BarRenderer deserialized = (BarRenderer) deserializedRenderer;
		assertEquals(original.getStroke(), deserialized.getStroke());
		assertEquals(original.getStrokeColor(), deserialized.getStrokeColor());
	}
}
