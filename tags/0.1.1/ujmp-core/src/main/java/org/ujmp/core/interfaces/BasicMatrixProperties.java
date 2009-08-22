/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.interfaces;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public interface BasicMatrixProperties {

	public ValueType getValueType();

	public long getValueCount();

	public boolean isReadOnly();

	public boolean equals(Object o);

	public boolean equalsContent(Object o);

	public boolean equalsAnnotation(Object o);

	public int rank() throws MatrixException;

	public double trace() throws MatrixException;

	public boolean isDiagonal() throws MatrixException;

	public boolean isSquare();

	public boolean isSymmetric();

	public boolean isEmpty() throws MatrixException;

	public boolean isColumnVector();

	public boolean isRowVector();

	public boolean isScalar();

	public boolean isResizeable();

	public boolean isMultidimensionalMatrix();

	public boolean isSparse();

	public boolean isTransient();

	public boolean containsMissingValues() throws MatrixException;

	public double getDoubleValue() throws MatrixException;

	public double getMinValue() throws MatrixException;

	public double getMeanValue() throws MatrixException;

	public double getStdValue() throws MatrixException;

	public double getMaxValue() throws MatrixException;

	public double getEuklideanValue() throws MatrixException;

	public double getValueSum() throws MatrixException;

	public double getAbsoluteValueSum() throws MatrixException;

	public double getAbsoluteValueMean() throws MatrixException;

	public double getRMS() throws MatrixException;

	public long getRowCount();

	public long getColumnCount();

	public long getZCount();

	public long getSize(int dimension);

	public long[] getSize();

	/**
	 * Sets the size of the matrix. This is an optional method that is not
	 * implemented for all matrizes. If this method is not implemented, a
	 * <code>MatrixException</code> is thrown.
	 * 
	 * @param size
	 *            the new size of the matrix
	 */
	public void setSize(long... size);

	public int getDimensionCount();

	public String toString();

}