/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.ujmp.core.booleanmatrix.stub;

import static org.ujmp.core.util.VerifyUtil.assertTrue;

import org.ujmp.core.Matrix;
import org.ujmp.core.booleanmatrix.BooleanMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractBooleanMatrix extends AbstractGenericMatrix<Boolean> implements
		BooleanMatrix {

	private static final long serialVersionUID = -6190735100426536876L;

	public AbstractBooleanMatrix() {
		super();
	}

	public AbstractBooleanMatrix(Matrix m) {
		super(m);
	}

	public AbstractBooleanMatrix(long... size) {
		super(size);
	}

	public final Boolean getObject(long... coordinates) throws MatrixException {
		return getBoolean(coordinates);
	}

	public final void setObject(Boolean o, long... coordinates) throws MatrixException {
		setBoolean(o, coordinates);
	}

	public final boolean getAsBoolean(long... coordinates) throws MatrixException {
		return getBoolean(coordinates);
	}

	public final void setAsBoolean(boolean value, long... coordinates) throws MatrixException {
		setBoolean(value, coordinates);
	}

	public final double getAsDouble(long... coordinates) throws MatrixException {
		return getBoolean(coordinates) ? 1 : 0;
	}

	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		assertTrue(!MathUtil.isNaNOrInfinite(value), "Nan, Inf and -Inf not allowed in this matrix");
		setBoolean(value != 0, coordinates);
	}

	public final ValueType getValueType() {
		return ValueType.BOOLEAN;
	}

}
