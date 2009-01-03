/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.calculation.AbstractCalculation;
import org.ujmp.core.enums.AnnotationTransfer;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Convert extends AbstractCalculation {
	private static final long serialVersionUID = 6393277198816850597L;

	public Convert(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return getSource().getAsDouble(coordinates);
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setObject(Object value, long... coordinates) throws MatrixException {
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setDouble(double value, long... coordinates) throws MatrixException {
	}

	// this method is doing nothing, but it has to be there for submatrix or
	// selection where it is overridden
	public void setBoolean(boolean value, long... coordinates) throws MatrixException {
	}

	@Override
	public final boolean getBoolean(long... coordinates) throws MatrixException {
		return MathUtil.getBoolean(getObject(coordinates));
	}

	public static Matrix calcNew(ValueType valueType, AnnotationTransfer annotationTransfer,
			Matrix source) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(valueType, source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setObject(source.getObject(c), c);
		}
		switch (annotationTransfer) {
		case LINK:
			ret.setAnnotation(source.getAnnotation());
			break;
		case COPY:
			Annotation a = source.getAnnotation();
			if (a != null) {
				ret.setAnnotation(a.clone());
			}
			break;
		default:
			break;
		}
		return ret;
	}

	public static Matrix calcNew(AnnotationTransfer annotationTransfer, Matrix matrix)
			throws MatrixException {
		return calcNew(matrix.getValueType(), annotationTransfer, matrix);
	}

	@Override
	public ValueType getValueType() {
		return getSource().getValueType();
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		return getSource().getObject(coordinates);
	}

	@Override
	public String getString(long... coordinates) throws MatrixException {
		return getSource().getAsString(coordinates);
	}

	public Matrix calcNew() throws MatrixException {
		return calcNew(AnnotationTransfer.LINK, getSource());
	}

	public Matrix calcOrig() throws MatrixException {
		throw new MatrixException("not implemented");
	}

}
