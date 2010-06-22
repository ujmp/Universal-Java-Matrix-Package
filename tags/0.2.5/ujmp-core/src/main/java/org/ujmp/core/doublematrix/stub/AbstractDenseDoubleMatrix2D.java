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

package org.ujmp.core.doublematrix.stub;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.DivideScalar;
import org.ujmp.core.calculation.MinusMatrix;
import org.ujmp.core.calculation.MinusScalar;
import org.ujmp.core.calculation.Mtimes;
import org.ujmp.core.calculation.PlusMatrix;
import org.ujmp.core.calculation.PlusScalar;
import org.ujmp.core.calculation.TimesMatrix;
import org.ujmp.core.calculation.TimesScalar;
import org.ujmp.core.calculation.Transpose;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.DefaultDenseDoubleMatrix2DFactory;
import org.ujmp.core.doublematrix.factory.DenseDoubleMatrix2DFactory;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.CoordinateIterator2D;

public abstract class AbstractDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix implements
		DenseDoubleMatrix2D {

	public static DenseDoubleMatrix2DFactory factory = new DefaultDenseDoubleMatrix2DFactory();

	private static final long serialVersionUID = 4518790844453035022L;

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public final double getDouble(long... coordinates) {
		return getDouble(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setDouble(double value, long... coordinates) {
		setDouble(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Double getObject(long row, long column) throws MatrixException {
		return getDouble(row, column);
	}

	public final void setObject(Double o, long row, long column) throws MatrixException {
		setDouble(o, row, column);
	}

	public final Double getObject(int row, int column) throws MatrixException {
		return getDouble(row, column);
	}

	public final void setObject(Double o, int row, int column) throws MatrixException {
		setDouble(o, row, column);
	}

	public double getAsDouble(long row, long column) {
		return getDouble(row, column);
	}

	public double getAsDouble(int row, int column) {
		return getDouble(row, column);
	}

	public void setAsDouble(double value, int row, int column) {
		setDouble(value, row, column);
	}

	public void setAsDouble(double value, long row, long column) {
		setDouble(value, row, column);
	}

	public Matrix mtimes(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = factory.zeros(getRowCount(), m2.getColumnCount());
			Mtimes.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.mtimes(m2);
		}
	}

	public Matrix times(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = factory.zeros(getRowCount(), getColumnCount());
			TimesMatrix.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.times(m2);
		}
	}

	public Matrix divide(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = factory.zeros(getRowCount(), getColumnCount());
			DenseDoubleMatrix2D.divideMatrix.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.divide(m2);
		}
	}

	public Matrix plus(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = getFactory().zeros(getRowCount(), getColumnCount());
			PlusMatrix.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.plus(m2);
		}
	}

	public Matrix minus(Matrix m2) {
		if (m2 instanceof DenseDoubleMatrix2D) {
			final DenseDoubleMatrix2D result = getFactory().zeros(getRowCount(), getColumnCount());
			MinusMatrix.DENSEDOUBLEMATRIX2D.calc(this, (DenseDoubleMatrix2D) m2, result);
			return result;
		} else {
			return super.minus(m2);
		}
	}

	public Matrix minus(double v) {
		final DenseDoubleMatrix2D result = getFactory().zeros(getRowCount(), getColumnCount());
		MinusScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix plus(double v) {
		final DenseDoubleMatrix2D result = getFactory().zeros(getRowCount(), getColumnCount());
		PlusScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix times(double v) {
		final DenseDoubleMatrix2D result = getFactory().zeros(getRowCount(), getColumnCount());
		TimesScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix divide(double v) {
		final DenseDoubleMatrix2D result = getFactory().zeros(getRowCount(), getColumnCount());
		DivideScalar.DENSEDOUBLEMATRIX2D.calc(this, v, result);
		return result;
	}

	public Matrix transpose() {
		final DenseDoubleMatrix2D result = getFactory().zeros(getColumnCount(), getRowCount());
		Transpose.DENSEDOUBLEMATRIX2D.calc(this, result);
		return result;
	}

	public DenseDoubleMatrix2DFactory getFactory() {
		return factory;
	}

}
