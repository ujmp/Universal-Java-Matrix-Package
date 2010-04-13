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

package org.ujmp.core.doublematrix.factory;

import org.ujmp.core.doublematrix.DenseDoubleMatrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.DefaultDenseDoubleMatrixMultiD;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrix.factory.AbstractMatrixFactory;

public class DefaultDenseDoubleMatrixFactory extends AbstractMatrixFactory implements
		DenseDoubleMatrixFactory {
	private static final long serialVersionUID = -3522250062306401611L;

	public DenseDoubleMatrix zeros(long... size) throws MatrixException {
		if (size.length == 2) {
			return DenseDoubleMatrix2D.factory.zeros(size);
		} else {
			return new DefaultDenseDoubleMatrixMultiD(size);
		}
	}
}
