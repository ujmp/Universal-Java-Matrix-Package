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

package org.ujmp.core.doublematrix.calculation.entrywise.basic;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;

public class Power extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -6766560469728046231L;

	public Power(Matrix m1, Matrix m2) {
		super(m1, m2);
		if (m2.isScalar() && !Coordinates.equals(m1.getSize(), m2.getSize())) {
			getSources()[1] = MatrixFactory.fill(m2.getAsDouble(0, 0), m1.getSize());
		}
	}

	public Power(Matrix m1, double v2) throws MatrixException {
		this(m1, MatrixFactory.fill(v2, m1.getSize()));
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return Math.pow(getSource().getAsDouble(coordinates), getSources()[1]
				.getAsDouble(coordinates));
	}

	public static Matrix calc(Matrix source, Matrix power) throws MatrixException {
		if (power.isScalar() && !Coordinates.equals(source.getSize(), power.getSize())) {
			power = MatrixFactory.fill(power.getAsDouble(0, 0), source.getSize());
		}
		Matrix ret = Matrix.factory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setAsDouble(Math.pow(source.getAsDouble(c), power.getAsDouble(c)), c);
		}
		return ret;
	}

	public static Matrix calc(Matrix source, double power) throws MatrixException {
		Matrix ret = Matrix.factory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setAsDouble(Math.pow(source.getAsDouble(c), power), c);
		}
		return ret;
	}

}
