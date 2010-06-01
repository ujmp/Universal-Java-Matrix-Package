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

package org.ujmp.core.calculation;

import java.io.Serializable;

import org.ujmp.core.Matrix;
import org.ujmp.core.Matrix.StorageType;
import org.ujmp.core.annotation.Annotation;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

/**
 * Interface for matrix calculations.
 * 
 * @author arndt
 */
public interface Calculation extends Serializable {

	public static final int ALL = Matrix.ALL;

	public static final int NONE = Matrix.NONE;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public enum Ret {
		NEW, LINK, ORIG
	};

	public static final Ret NEW = Ret.NEW;

	public static final Ret LINK = Ret.LINK;

	public static final Ret ORIG = Ret.ORIG;

	public Matrix calc(Ret returnType) throws MatrixException;

	public Matrix calcNew() throws MatrixException;

	public Matrix calcLink() throws MatrixException;

	public Matrix calcOrig() throws MatrixException;

	public boolean isSparse();

	public Annotation getAnnotation();

	public void setAnnotation(Annotation annotation);

	public long getValueCount();

	public Iterable<long[]> availableCoordinates();

	public Iterable<long[]> allCoordinates();

	public boolean contains(long... coordinates);

	public Matrix getSource();

	public Matrix[] getSources();

	public void setSources(Matrix... sources);

	public int getDimension();

	public void setDimension(int dimension);

	public long[] getSize();

	public ValueType getValueType();

	public Matrix[] calcMulti();

	public StorageType getStorageType();

}
