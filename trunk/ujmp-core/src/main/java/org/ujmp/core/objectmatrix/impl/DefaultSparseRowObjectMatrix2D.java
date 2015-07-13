/*
 * Copyright (C) 2008-2015 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.core.objectmatrix.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.objectmatrix.stub.AbstractSparseObjectMatrix2D;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseRowObjectMatrix2D extends AbstractSparseObjectMatrix2D implements
		Wrapper<Map<Long, Matrix>> {
	private static final long serialVersionUID = -5291604525500706427L;

	private Map<Long, Matrix> rows = new HashMap<Long, Matrix>();

	public DefaultSparseRowObjectMatrix2D(long rows, long columns) {
		super(rows, columns);
		setSize(new long[] { rows, columns });
	}

	public DefaultSparseRowObjectMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		setSize(m.getSize());
		for (long[] c : m.availableCoordinates()) {
			setObject(m.getAsObject(c), c);
		}
		if (m.getMetaData() != null) {
			setMetaData(m.getMetaData().clone());
		}
	}

	public final void clear() {
		rows.clear();
	}

	public Object getObject(long row, long column) {
		Matrix m = rows.get(row);
		return m == null ? null : m.getAsObject(0, column);
	}

	public Object getObject(int row, int column) {
		return getObject((long) row, (long) column);
	}

	// TODO: this is certainly not the optimal way to do it!
	public Iterable<long[]> availableCoordinates() {
		List<long[]> coordinates = new ArrayList<long[]>();
		for (Long r : rows.keySet()) {
			Matrix m = rows.get(r);
			for (long[] c : m.availableCoordinates()) {
				coordinates.add(Coordinates.plus(c, new long[] { r, 0 }));
			}
		}
		return coordinates;
	}

	public boolean containsCoordinates(long... coordinates) {
		if (Coordinates.isSmallerThan(coordinates, size)) {
			return getObject(coordinates) != null;
		} else {
			return false;
		}
	}

	public double getAsDouble(long... coordinates) {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public void setAsDouble(double value, long... coordinates) {
		setObject(value, coordinates);
	}

	public void setObject(Object o, long row, long column) {
		Matrix m = rows.get(row);
		if (m == null) {
			// TODO: there should be a faster implementation than this:
			m = new DefaultSparseObjectMatrix((long) 1, getColumnCount());
			rows.put(row, m);
		}
		m.setAsObject(o, 0, column);
	}

	public void setObject(Object o, int row, int column) {
		setObject(o, (long) row, (long) column);
	}

	public void setSize(long... size) {
		if (this.size[COLUMN] != size[COLUMN]) {
			for (Matrix m : rows.values()) {
				m.setSize(1, size[COLUMN]);
			}
		}
		this.size = size;
	}

	public Matrix getRow(long row) {
		return rows.get(row);
	}

	public Matrix max(Ret returnType, int dimension) {
		if (returnType == Ret.NEW) {

			if (dimension == ROW) {
				Matrix ret = Matrix.Factory.zeros(1, getColumnCount());
				for (long[] c : availableCoordinates()) {
					double v = getAsDouble(c);
					if (v > ret.getAsDouble(0, c[COLUMN])) {
						ret.setAsDouble(v, 0, c[COLUMN]);
					}
				}
				return ret;
			} else if (dimension == COLUMN) {
				Matrix ret = Matrix.Factory.zeros(getRowCount(), 1);
				for (long[] c : availableCoordinates()) {
					double v = getAsDouble(c);
					if (v > ret.getAsDouble(c[ROW], 0)) {
						ret.setAsDouble(v, c[ROW], 0);
					}
				}
				return ret;
			}

		}
		throw new RuntimeException("not supported");
	}

	public Matrix selectRows(Ret returnType, long... rows) {
		if (returnType == Ret.LINK && rows.length == 1) {
			return getRow(rows[0]);
		}
		return super.selectRows(returnType, rows);
	}

	public Map<Long, Matrix> getWrappedObject() {
		return rows;
	}

	public void setWrappedObject(Map<Long, Matrix> object) {
		this.rows = object;
	}

}
