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

package org.ujmp.core.intmatrix;

import org.ujmp.core.genericmatrix.GenericMatrix2D;
import org.ujmp.core.intmatrix.factory.DefaultIntMatrix2DFactory;

public interface IntMatrix2D extends IntMatrix, GenericMatrix2D<Integer> {

	public static DefaultIntMatrix2DFactory Factory = new DefaultIntMatrix2DFactory();

	public int getInt(long row, long column);

	public void setInt(int value, long row, long column);

	public int getInt(int row, int column);

	public void setInt(int value, int row, int column);
}
