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

package org.ujmp.core.genericmatrix.stub;

import org.ujmp.core.AbstractMatrix;
import org.ujmp.core.genericmatrix.GenericMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractGenericMatrix<T> extends AbstractMatrix implements GenericMatrix<T> {
	private static final long serialVersionUID = -7498575238134186845L;

	protected AbstractGenericMatrix(long... size) {
		super(size);
	}

	public final T getAsObject(long... coordinates) {
		return getObject(coordinates);
	}

	public T getObject(long row, long column) {
		return getAsObject(new long[] { row, column });
	}

	public void setObject(T value, long row, long column) {
		setAsObject(value, new long[] { row, column });
	}

	public T getObject(int row, int column) {
		return getAsObject(new long[] { row, column });
	}

	public void setObject(T value, int row, int column) {
		setAsObject(value, new long[] { row, column });
	}

	public void setObject(T value, long... coordinates) {
		setAsObject(value, coordinates);
	}

	@SuppressWarnings("unchecked")
	public final void setAsObject(Object o, long... coordinates) {
		switch (getValueType()) {
		case BOOLEAN:
			setAsBoolean(MathUtil.getBoolean(o), coordinates);
			break;
		case BIGINTEGER:
			setAsBigInteger(MathUtil.getBigInteger(o), coordinates);
			break;
		case BIGDECIMAL:
			setAsBigDecimal(MathUtil.getBigDecimal(o), coordinates);
			break;
		case BYTE:
			setAsByte(MathUtil.getByte(o), coordinates);
			break;
		case CHAR:
			setAsChar(MathUtil.getChar(o), coordinates);
			break;
		case DOUBLE:
			setAsDouble(MathUtil.getDouble(o), coordinates);
			break;
		case FLOAT:
			setAsFloat(MathUtil.getFloat(o), coordinates);
			break;
		case INT:
			setAsInt(MathUtil.getInt(o), coordinates);
			break;
		case LONG:
			setAsLong(MathUtil.getLong(o), coordinates);
			break;
		case OBJECT:
			setObject((T) o, coordinates);
			break;
		case SHORT:
			setAsShort(MathUtil.getShort(o), coordinates);
			break;
		case STRING:
			setAsString(StringUtil.convert(o), coordinates);
			break;
		}
	}

}
