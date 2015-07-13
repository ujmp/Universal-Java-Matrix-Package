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

package org.ujmp.core.longmatrix.impl;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.impl.DefaultSparseGenericMatrix;
import org.ujmp.core.longmatrix.LongMatrix;
import org.ujmp.core.util.MathUtil;

public class DefaultSparseLongMatrix extends DefaultSparseGenericMatrix<Long> implements LongMatrix {
	private static final long serialVersionUID = -7047230020224347032L;

	public DefaultSparseLongMatrix(Matrix m) {
		super(m, -1);
	}

	public DefaultSparseLongMatrix(Matrix m, int maximumNumberOfEntries) {
		super(m, maximumNumberOfEntries);
	}

	public DefaultSparseLongMatrix(long... size) {
		super(size);
	}

	public final ValueType getValueType() {
		return ValueType.LONG;
	}

	public long getLong(long... coordinates) {
		return MathUtil.getLong(getObject(coordinates));
	}

	public void setLong(long value, long... coordinates) {
		setObject(value, coordinates);
	}

}
