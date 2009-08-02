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

package org.ujmp.core.charmatrix;

import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.genericmatrix.AbstractGenericMatrix;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractCharMatrix extends AbstractGenericMatrix<Character> implements
		CharMatrix {

	@Override
	public final Character getObject(long... coordinates) throws MatrixException {
		return getChar(coordinates);
	}

	@Override
	public final void setObject(Object o, long... coordinates) throws MatrixException {
		setChar(MathUtil.getChar(o), coordinates);
	}

	@Override
	public final char getAsChar(long... coordinates) throws MatrixException {
		return getChar(coordinates);
	}

	@Override
	public final void setAsChar(char value, long... coordinates) throws MatrixException {
		setChar(value, coordinates);
	}

	@Override
	public final double getAsDouble(long... coordinates) throws MatrixException {
		return getChar(coordinates);
	}

	@Override
	public final void setAsDouble(double value, long... coordinates) throws MatrixException {
		setChar((char) value);
	}

	public final ValueType getValueType() {
		return ValueType.CHAR;
	}

}
