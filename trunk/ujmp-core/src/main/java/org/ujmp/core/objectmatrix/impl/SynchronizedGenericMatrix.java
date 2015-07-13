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

import org.ujmp.core.Matrix;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;

public class SynchronizedGenericMatrix<T> extends AbstractGenericMatrix<T> {
	private static final long serialVersionUID = -4456493053286654056L;

	private final Matrix matrix;

	public SynchronizedGenericMatrix(Matrix source) {
		super(source.getSize());
		this.matrix = source;
		setMetaData(source.getMetaData());
	}

	public synchronized long[] getSize() {
		size = matrix.getSize();
		return size;
	}

	public synchronized double getAsDouble(long... coordinates) {
		return matrix.getAsDouble(coordinates);
	}

	public synchronized long getValueCount() {
		return matrix.getValueCount();
	}

	public synchronized void setAsDouble(double value, long... coordinates) {
		matrix.setAsDouble(value, coordinates);
	}

	@SuppressWarnings("unchecked")
	public synchronized T getObject(long... c) {
		return (T) matrix.getAsObject(c);
	}

	public synchronized void setObject(T value, long... c) {
		matrix.setAsObject(value, c);
	}

	public synchronized boolean containsCoordinates(long... coordinates) {
		return matrix.containsCoordinates(coordinates);
	}

	public synchronized boolean isReadOnly() {
		return matrix.isReadOnly();
	}

	public final boolean isSparse() {
		return matrix.isSparse();
	}

	public synchronized Iterable<long[]> availableCoordinates() {
		return matrix.availableCoordinates();
	}

	public final void clear() {
		matrix.clear();
	}
}
