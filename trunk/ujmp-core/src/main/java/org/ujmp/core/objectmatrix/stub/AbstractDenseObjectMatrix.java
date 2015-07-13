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

package org.ujmp.core.objectmatrix.stub;

import org.ujmp.core.Coordinates;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.calculation.entrywise.creators.Zeros;
import org.ujmp.core.objectmatrix.DenseObjectMatrix;

public abstract class AbstractDenseObjectMatrix extends AbstractObjectMatrix implements
		DenseObjectMatrix {
	private static final long serialVersionUID = -4546702335648155424L;

	public AbstractDenseObjectMatrix(long... size) {
		super(size);
	}

	public final boolean containsCoordinates(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public final boolean isSparse() {
		return false;
	}

	public final Iterable<long[]> availableCoordinates() {
		return allCoordinates();
	}

	public void clear() {
		new Zeros(this).calc(Ret.ORIG);
	}
}
