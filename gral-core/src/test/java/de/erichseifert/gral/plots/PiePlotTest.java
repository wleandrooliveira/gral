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

import static de.erichseifert.gral.TestUtils.assertNotEmpty;
import static de.erichseifert.gral.TestUtils.createTestImage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import de.erichseifert.gral.plots.PiePlot.PieSliceRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;

public class PiePlotTest {
	private static final double DELTA = 1e-7;

	private DataSource data;
	private MockPiePlot plot;

	private static final class MockPiePlot extends PiePlot {
		/** Version id for serialization. */
		private static final long serialVersionUID = -4466331273825538939L;

		public boolean isDrawn;

		public MockPiePlot(DataSource data) {
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
		data = new DummyData(1, 3, 1.0);
		plot = new MockPiePlot(data);
	}

	@Test
	public void testDraw() {
		BufferedImage image = createTestImage();
		plot.setBounds(0.0, 0.0, image.getWidth(), image.getHeight());
		DrawingContext context = new DrawingContext((Graphics2D) image.getGraphics());
		plot.draw(context);
		assertTrue(plot.isDrawn);
		assertNotEmpty(image);
	}

	@Test
	public void testAddRemoveData() {
		plot.remove(data);
		assertEquals(0, plot.getData().size());
		plot.add(data);
		assertEquals(1, plot.getData().size());
		try {
			plot.add(data);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void testSerialization() throws IOException, ClassNotFoundException {
		PiePlot original = plot;
		PiePlot deserialized = TestUtils.serializeAndDeserialize(original);

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
		PieSliceRenderer original = (PieSliceRenderer) originalRenderer;
		PieSliceRenderer deserialized = (PieSliceRenderer) deserializedRenderer;
		assertEquals(original.getRadiusInner(), deserialized.getRadiusInner(), DELTA);
		assertEquals(original.getRadiusOuter(), deserialized.getRadiusOuter(), DELTA);
		assertEquals(original.getGap(), deserialized.getGap(), DELTA);
	}
}
