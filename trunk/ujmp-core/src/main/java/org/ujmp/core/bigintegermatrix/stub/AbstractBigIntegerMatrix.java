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

package org.ujmp.core.bigintegermatrix.stub;

import java.math.BigInteger;

import org.ujmp.core.bigintegermatrix.BigIntegerMatrix;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.stub.AbstractGenericMatrix;

public abstract class AbstractBigIntegerMatrix extends AbstractGenericMatrix<BigInteger> implements
		BigIntegerMatrix {
	private static final long serialVersionUID = 3292181924433131789L;

	
	public final BigInteger getObject(long... coordinates) throws MatrixException {
		return getBigInteger(coordinates);
	}

	
	public final void setObject(BigInteger o, long... coordinates) throws MatrixException {
		setBigInteger(o, coordinates);
	}

	
	public final BigInteger getAsBigInteger(long... coordinates) throws MatrixException {
		return getBigInteger(coordinates);
	}

	
	public final void setAsBigInteger(BigInteger value, long... coordinates) throws MatrixException {
		setBigInteger(value, coordinates);
	}

	
	public final ValueType getValueType() {
		return ValueType.BIGINTEGER;
	}

}
