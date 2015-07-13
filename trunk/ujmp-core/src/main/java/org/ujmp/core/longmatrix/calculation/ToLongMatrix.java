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

package org.ujmp.core.longmatrix.calculation;

import org.ujmp.core.Matrix;

public class ToLongMatrix extends AbstractLongCalculation {
	private static final long serialVersionUID = 807079620849420977L;

	public ToLongMatrix(Matrix matrix) {
		super(matrix);
	}

	public long getLong(long... coordinates) {
		return getSource().getAsLong(coordinates);
	}

	public void setLong(long value, long coordinates) {
		getSource().setAsLong(value, coordinates);
	}

}
