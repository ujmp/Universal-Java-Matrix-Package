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

package org.ujmp.core.booleanmatrix.stub;

import org.ujmp.core.booleanmatrix.DenseBooleanMatrix2D;

public abstract class AbstractDenseBooleanMatrix2D extends AbstractDenseBooleanMatrix implements
		DenseBooleanMatrix2D {
	private static final long serialVersionUID = 1382622388004356999L;

	public AbstractDenseBooleanMatrix2D(long rows, long columns) {
		super(rows, columns);
	}

	public final boolean getBoolean(long... coordinates) {
		return getBoolean(coordinates[ROW], coordinates[COLUMN]);
	}

	public final void setBoolean(boolean value, long... coordinates) {
		setBoolean(value, coordinates[ROW], coordinates[COLUMN]);
	}

	public final Boolean getObject(long row, long column) {
		return getBoolean(row, column);
	}

	public final void setObject(Boolean o, long row, long column) {
		setBoolean(o, row, column);
	}

	public final Boolean getObject(int row, int column) {
		return getBoolean(row, column);
	}

	public final void setObject(Boolean o, int row, int column) {
		setBoolean(o, row, column);
	}

	public final int getDimensionCount() {
		return 2;
	}

}
