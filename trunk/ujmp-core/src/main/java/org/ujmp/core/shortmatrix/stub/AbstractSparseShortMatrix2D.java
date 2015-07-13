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

package org.ujmp.core.shortmatrix.stub;

import org.ujmp.core.shortmatrix.SparseShortMatrix2D;

public abstract class AbstractSparseShortMatrix2D extends AbstractSparseShortMatrix implements
		SparseShortMatrix2D {
	private static final long serialVersionUID = 6786095600309951623L;

	public AbstractSparseShortMatrix2D(long rows, long columns) {
		super(rows, columns);
	}

	public final short getShort(long... coordinates) {
		return getShort(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setShort(long value, long... coordinates) {
		setShort(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Short getObject(long row, long column) {
		return getShort(row, column);
	}

	public final Short getObject(int row, int column) {
		return getShort(row, column);
	}

	public final void setObject(Short value, long row, long column) {
		setShort(value, row, column);
	}

	public final void setObject(Short value, int row, int column) {
		setShort(value, row, column);
	}

	public final int getDimensionCount() {
		return 2;
	}
}
