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

package org.ujmp.core.doublematrix.calculation.general.statistical;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;

public class Sum extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 1070433312716157660L;

	boolean ignoreNaN = false;

	public Sum(Matrix matrix) {
		this(ROW, true, matrix);
	}

	public Sum(int dimension, boolean ignoreNaN, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
		MapMatrix<String, Object> aold = matrix.getMetaData();
		if (aold != null) {
			MapMatrix<String, Object> a = new DefaultMapMatrix<String, Object>();
			a.put(Matrix.LABEL, aold.get(Matrix.LABEL));
			if (dimension == ROW) {
				// a.setDimensionMatrix(ROW, aold.getDimensionMatrix(ROW));
			} else if (dimension == COLUMN) {
				// a.setDimensionMatrix(COLUMN,
				// aold.getDimensionMatrix(COLUMN));
			}
			setMetaData(a);
		}
	}

	public double getDouble(long... coordinates) {
		double sum = 0;

		if (ignoreNaN) {

			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += MathUtil.ignoreNaN(getSource().getAsDouble(r, coordinates[COLUMN]));
				}
				return sum;
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += MathUtil.ignoreNaN(getSource().getAsDouble(coordinates[ROW], c));
				}
				return sum;
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += MathUtil.ignoreNaN(getSource().getAsDouble(r, c));
					}
				}
				return sum;
			default:
				return 0.0;
			}
		} else {
			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += getSource().getAsDouble(r, coordinates[COLUMN]);
				}
				return sum;
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += getSource().getAsDouble(coordinates[ROW], c);
				}
				return sum;
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += getSource().getAsDouble(r, c);
					}
				}
				return sum;
			default:
				return 0.0;
			}
		}

	}

	public long[] getSize() {
		switch (getDimension()) {
		case ROW:
			return new long[] { 1, getSource().getSize()[COLUMN] };
		case COLUMN:
			return new long[] { getSource().getSize()[ROW], 1 };
		case ALL:
			return new long[] { 1, 1 };
		}
		return null;
	}

}
