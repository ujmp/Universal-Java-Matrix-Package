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

package org.ujmp.core.objectmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;

public class Repmat extends AbstractObjectCalculation {
	private static final long serialVersionUID = -7603784696235009832L;

	private long[] size = null;

	public Repmat(Matrix matrix, long... count) {
		super(matrix);
		size = Coordinates.times(matrix.getSize(), count);
	}

	public Object getObject(long... coordinates) {
		return getSource().getAsObject(Coordinates.modulo(coordinates, getSource().getSize()));
	}

	public long[] getSize() {
		return size;
	}

	public void setObject(Object value, long... coordinates) {
		getSource().setAsObject(value, Coordinates.modulo(coordinates, getSource().getSize()));
	}

}
