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

package org.ujmp.core.intmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseIntMatrix extends DefaultSparseGenericMatrix<Integer> implements
		IntMatrix {
	private static final long serialVersionUID = 7865221689702604727L;

	public DefaultSparseIntMatrix(Matrix m) {
		super(m, -1);
	}

	public DefaultSparseIntMatrix(Matrix m, int maximumNumberOfEntries) {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseIntMatrix(long... size) {
		super(size);
	}

	public final ValueType getValueType() {
		return ValueType.INT;
	}

	public int getInt(long... coordinates) {
		return MathUtil.getInt(getObject(coordinates));
	}

	public void setInt(int value, long... coordinates) {
		setObject(value, coordinates);
	}

}
