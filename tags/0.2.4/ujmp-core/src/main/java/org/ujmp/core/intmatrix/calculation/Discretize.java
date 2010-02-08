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

package org.ujmp.core.intmatrix.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.entrywise.rounding.Round;
import org.ujmp.core.exceptions.MatrixException;

public class Discretize extends AbstractIntCalculation {
	private static final long serialVersionUID = -2045926868254834270L;

	private Matrix discretized = null;

	public enum DiscretizationMethod {
		ROUND, STRINGS, STANDARDBINNING, EQUALBINNING, INFORMATIONGAIN
	};

	public Discretize(Matrix matrix, int dimension, DiscretizationMethod method, int numberOfBins) {
		super(dimension, matrix);
		switch (method) {
		case ROUND:
			discretized = new Round(matrix).calcLink();
			break;
		case STANDARDBINNING:
			discretized = new DiscretizeStandardBinning(dimension, matrix, numberOfBins).calcLink();
			break;
		default:
			throw new MatrixException("method not yet implemented");
		}
	}

	public int getInt(long... coordinates) throws MatrixException {
		return discretized.getAsInt(coordinates);
	}

}
