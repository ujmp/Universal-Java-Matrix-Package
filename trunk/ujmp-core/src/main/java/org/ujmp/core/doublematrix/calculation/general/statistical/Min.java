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

public class Min extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 2209647170194250477L;

	public Min(int dimension, Matrix matrix) {
		super(dimension, matrix);
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
		double min = Double.MAX_VALUE;
		switch (getDimension()) {
		case ROW:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				double v = getSource().getAsDouble(r, coordinates[COLUMN]);
				if (v < min) {
					min = v;
				}
			}
			min = min == Double.MAX_VALUE ? Double.NaN : min;
			return min;
		case COLUMN:
			for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
				double v = getSource().getAsDouble(coordinates[ROW], c);
				if (v < min) {
					min = v;
				}
			}
			min = min == Double.MAX_VALUE ? Double.NaN : min;
			return min;
		case ALL:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					double v = getSource().getAsDouble(r, c);
					if (v < min) {
						min = v;
					}
				}
			}
			min = min == Double.MAX_VALUE ? Double.NaN : min;
			return min;
		}
		return 0.0;
	}

	public long[] getSize() {
		switch (getDimension()) {
		case ROW:
			return new long[] { 1, getSource().getSize()[COLUMN] };
		case COLUMN:
			return new long[] { getSource().getSize()[ROW], 1 };
		case ALL:
			return new long[] { 1, 1 };
		default:
			throw new RuntimeException("dimension not supported: " + getDimension());
		}
	}

	public static double calc(Matrix m) {
		double min = Double.MAX_VALUE;
		double v = 0.0;
		for (long[] c : m.availableCoordinates()) {
			min = (v = m.getAsDouble(c)) < min ? v : min;
		}
		min = min == Double.MAX_VALUE ? Double.NaN : min;
		return min;
	}

}
