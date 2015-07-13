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

package org.ujmp.core.annotation;

import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasDescription;
import org.ujmp.core.interfaces.HasId;
import org.ujmp.core.interfaces.HasLabel;
import org.ujmp.core.mapmatrix.MapMatrix;

public interface HasMetaData extends HasLabel, HasDescription, HasId {

	public static final String DIMENSIONMETADATA = "DimensionMetaData";

	public MapMatrix<String, Object> getMetaData();

	public void setMetaData(MapMatrix<String, Object> metaData);

	public Object getMetaData(Object key);

	public Matrix getMetaDataMatrix(Object key);

	public double getMetaDataDouble(Object key);

	public String getMetaDataString(Object key);

	public void setMetaData(String key, Object value);

	public void setMetaDataDimensionMatrix(int dimension, Matrix matrix);

	public Matrix getMetaDataDimensionMatrix(int dimension);

	public Object getDimensionMetaData(int dimension, long... position);

	public String getDimensionLabel(int dimension);

	public void setDimensionMetaData(int dimension, Object label, long... position);

	public void setDimensionLabel(int dimension, Object label);

	public String getColumnLabel(long col);

	public String getRowLabel(long row);

	public void setColumnLabel(long col, Object label);

	public void setRowLabel(long row, Object label);

	public long getRowForLabel(Object object);

	public long getColumnForLabel(Object object);

	public long[] getPositionForLabel(int dimension, Object label);

}
