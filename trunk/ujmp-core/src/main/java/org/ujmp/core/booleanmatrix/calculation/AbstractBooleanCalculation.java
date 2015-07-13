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

package org.ujmp.core.booleanmatrix.calculation;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.booleanmatrix.BooleanMatrix;
import org.ujmp.core.booleanmatrix.impl.BooleanCalculationMatrix;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.enums.ValueType;

public abstract class AbstractBooleanCalculation extends AbstractCalculation implements
		BooleanCalculation {
	private static final long serialVersionUID = 1357508932960938265L;

	public AbstractBooleanCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractBooleanCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public final BooleanMatrix calcNew() {
		BooleanMatrix result = (BooleanMatrix) Matrix.Factory.zeros(ValueType.BOOLEAN, getSize());
		for (long[] c : result.allCoordinates()) {
			result.setAsBoolean(getBoolean(c), c);
		}
		if (getMetaData() != null) {
			result.setMetaData(getMetaData().clone());
		}
		return result;
	}

	public final BooleanMatrix calcLink() {
		return new BooleanCalculationMatrix(this);
	}

	public final Matrix calcOrig() {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new RuntimeException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsBoolean(getBoolean(c), c);
		}
		getSource().fireValueChanged();
		return getSource();
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setBoolean(boolean value, long... coordinates) {
	}

	public final ValueType getValueType() {
		return ValueType.BOOLEAN;
	}

}
