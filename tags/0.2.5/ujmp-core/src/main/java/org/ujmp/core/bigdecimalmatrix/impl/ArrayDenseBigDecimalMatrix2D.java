/*
 * Copyright (C) 2008-2010 by Holger Arndt
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
import org.ujmp.core.exceptions.MatrixException;

public class ArrayDenseBigDecimalMatrix2D extends AbstractDenseBigDecimalMatrix2D {
	private static final long serialVersionUID = 5701752483223767209L;

	private BigDecimal[][] values = null;

	public ArrayDenseBigDecimalMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof ArrayDenseBigDecimalMatrix2D) {
			BigDecimal[][] v = ((ArrayDenseBigDecimalMatrix2D) m).values;
			this.values = new BigDecimal[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new BigDecimal[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setAsBigDecimal(m.getAsBigDecimal(c), c);
			}
		}
	}

	public ArrayDenseBigDecimalMatrix2D(BigDecimal[]... v) {
		this.values = v;
	}

	public ArrayDenseBigDecimalMatrix2D(long... size) {
		values = new BigDecimal[(int) size[ROW]][(int) size[COLUMN]];
		for (int r = values.length; --r != -1;) {
			Arrays.fill(values[r], BigDecimal.ZERO);
		}
	}

	public ArrayDenseBigDecimalMatrix2D(BigDecimal... v) {
		this.values = new BigDecimal[v.length][1];
		for (int r = v.length; --r >= 0;) {
			values[r][0] = v[r];
		}
	}

	public long[] getSize() {
		return new long[] { values.length, values.length == 0 ? 0 : values[0].length };
	}

	public long getRowCount() {
		return values.length;
	}

	public long getColumnCount() {
		return values.length == 0 ? 0 : values[0].length;
	}

	public BigDecimal getBigDecimal(long row, long column) {
		return values[(int) row][(int) column];
	}

	public void setBigDecimal(BigDecimal value, long row, long column) {
		values[(int) row][(int) column] = value;
	}

	public BigDecimal getBigDecimal(int row, int column) {
		return values[row][column];
	}

	public void setBigDecimal(BigDecimal value, int row, int column) {
		values[row][column] = value;
	}

}
