/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2016 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <mseifert[at]error-reports.org>
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
package de.erichseifert.gral.data;

import java.util.Arrays;
import java.util.List;

import de.erichseifert.gral.data.statistics.Statistics;

/**
 * <p>Class for accessing a specific column of a data source. The data of the
 * column can be accessed using the {@code get(int)} method.</p>
 *
 * <p>Example for accessing value at column 2, row 3 of a data source:</p>
 * <pre>
 * Column col = new Column(dataSource, 2);
 * Number v = col.get(3);
 * </pre>
 *
 * @see DataSource
 */
public class Column<T extends Comparable<T>> extends DataAccessor {
	/** Version id for serialization. */
	private static final long serialVersionUID = 7380420622890027262L;

	private final Class<T> dataType;
	private final List<T> data;

	public Column(Class<T> dataType, T... data) {
		super(null, -1);
		this.dataType = dataType;
		this.data = Arrays.asList(data);
	}

	@Override
	public Comparable<?> get(int row) {
		return row > data.size() ? null : data.get(row);
	}

	@Override
	public int size() {
		return data.size();
	}

	/**
	 * Returns whether this column only contains numbers.
	 * @return {@code true} if this column is numeric, otherwise {@code false}.
	 */
	public boolean isNumeric() {
		return Number.class.isAssignableFrom(getType());
	}

	public Class<? extends Comparable<?>> getType() {
		return dataType;
	}

	@Override
	public double getStatistics(String key) {
		return new Statistics(data).get(key);
	}

	@Override
	public int hashCode() {
		return dataType.hashCode() ^ data.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}
}
