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

package org.ujmp.mtj.calculation;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrices;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.mtj.MTJDenseDoubleMatrix2D;

public class Inv extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 1441735603598847485L;

	private Matrix inv = null;

	public Inv(Matrix matrix) {
		super(matrix);
	}

	
	public double getDouble(long... coordinates) throws MatrixException {
		if (inv == null) {
			if (getSource() instanceof DenseMatrix) {
				DenseMatrix A = (DenseMatrix) getSource();
				DenseMatrix I = Matrices.identity((int) getSource().getColumnCount());
				DenseMatrix AI = I.copy();
				inv = new MTJDenseDoubleMatrix2D((DenseMatrix) A.solve(I, AI));
			} else {
				DenseMatrix A = new MTJDenseDoubleMatrix2D(getSource()).getWrappedObject();
				DenseMatrix I = Matrices.identity((int) getSource().getColumnCount());
				DenseMatrix AI = I.copy();
				inv = new MTJDenseDoubleMatrix2D((DenseMatrix) A.solve(I, AI));
			}
		}
		return inv.getAsDouble(coordinates);
	}

	
	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize());
	}
}