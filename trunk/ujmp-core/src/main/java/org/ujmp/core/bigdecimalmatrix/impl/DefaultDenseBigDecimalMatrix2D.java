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

package org.ujmp.core.bigdecimalmatrix.impl;

import java.math.BigDecimal;
import java.util.Arrays;

import org.ujmp.core.Matrix;
import org.ujmp.core.bigdecimalmatrix.stub.AbstractDenseBigDecimalMatrix2D;

public class DefaultDenseBigDecimalMatrix2D extends AbstractDenseBigDecimalMatrix2D {
	private static final long serialVersionUID = -5227328974882402868L;

	private final BigDecimal[] values;

	private int rows = 0;
	private int cols = 0;

	public DefaultDenseBigDecimalMatrix2D(Matrix m) {
		super(m.getRowCount(), m.getColumnCount());
		this.rows = (int) m.getRowCount();
		this.cols = (int) m.getColumnCount();
		this.size = new long[] { rows, cols };
		if (m instanceof DefaultDenseBigDecimalMatrix2D) {
			BigDecimal[] v = ((DefaultDenseBigDecimalMatrix2D) m).values;
			this.values = new BigDecimal[v.length];
			System.arraycopy(v, 0, this.values, 0, v.length);
		} else {
			this.values = new BigDecimal[rows * cols];
			for (long[] c : m.allCoordinates()) {
				setBigDecimal(m.getAsBigDecimal(c), c);
			}
		}
	}

	public DefaultDenseBigDecimalMatrix2D(int rows, int columns) {
		super(rows, columns);
		this.rows = rows;
		this.cols = columns;
		this.values = new BigDecimal[rows * columns];
		Arrays.fill(values, BigDecimal.ZERO);
	}

	public DefaultDenseBigDecimalMatrix2D(BigDecimal[] v, int rows, int cols) {
		super(rows, cols);
		this.rows = rows;
		this.cols = cols;
		this.values = v;
	}

	public BigDecimal getBigDecimal(long row, long column) {
		return values[(int) (column * rows + row)];
	}

	public void setBigDecimal(BigDecimal value, long row, long column) {
		values[(int) (column * rows + row)] = value;
	}

	public BigDecimal getBigDecimal(int row, int column) {
		return values[column * rows + row];
	}

	public void setBigDecimal(BigDecimal value, int row, int column) {
		values[column * rows + row] = value;
	}

}
