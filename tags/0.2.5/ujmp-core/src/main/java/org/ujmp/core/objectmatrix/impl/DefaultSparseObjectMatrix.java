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

package org.ujmp.core.objectmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.objectmatrix.ObjectMatrix;
import org.ujmp.core.objectmatrix.factory.DefaultSparseObjectMatrixFactory;
import org.ujmp.core.objectmatrix.factory.SparseObjectMatrixFactory;

public class DefaultSparseObjectMatrix extends DefaultSparseGenericMatrix<Object> implements
		ObjectMatrix {
	private static final long serialVersionUID = -1130331544425728230L;

	public static SparseObjectMatrixFactory factory = new DefaultSparseObjectMatrixFactory();

	public DefaultSparseObjectMatrix(Matrix m) throws MatrixException {
		super(m, -1);
	}

	public DefaultSparseObjectMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseObjectMatrix(long... size) {
		super(size);
	}

	public DefaultSparseObjectMatrix(int maximumNumberOfEntries, long... size) {
		super(maximumNumberOfEntries, size);
	}

	public final ValueType getValueType() {
		return ValueType.OBJECT;
	}

	public SparseObjectMatrixFactory getFactory() {
		return factory;
	}

}
