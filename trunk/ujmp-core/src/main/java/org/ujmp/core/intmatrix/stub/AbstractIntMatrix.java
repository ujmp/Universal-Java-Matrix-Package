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

package org.ujmp.core.intmatrix.stub;

import static org.ujmp.core.util.VerifyUtil.verifyTrue;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;
import org.ujmp.core.intmatrix.IntMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractIntMatrix extends AbstractGenericMatrix<Integer> implements IntMatrix {
	private static final long serialVersionUID = -5153569448031492210L;

	public AbstractIntMatrix(long... size) {
		super(size);
	}

	public final Integer getObject(long... coordinates) {
		return getInt(coordinates);
	}

	public final void setObject(Integer o, long... coordinates) {
		setInt(o, coordinates);
	}

	public final int getAsInt(long... coordinates) {
		return getInt(coordinates);
	}

	public final void setAsInt(int value, long... coordinates) {
		setInt(value, coordinates);
	}

	public final double getAsDouble(long... coordinates) {
		return getInt(coordinates);
	}

	public final void setAsDouble(double value, long... coordinates) {
		verifyTrue(!MathUtil.isNaNOrInfinite(value), "Nan, Inf and -Inf not allowed in this matrix");
		setInt((int) value, coordinates);
	}

	public final ValueType getValueType() {
		return ValueType.INT;
	}

}
