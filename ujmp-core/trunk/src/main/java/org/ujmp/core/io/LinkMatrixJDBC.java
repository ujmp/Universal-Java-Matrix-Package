/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.ujmp.core.io;

import java.io.File;
import java.lang.reflect.Method;

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DB;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;

public class LinkMatrixJDBC {

	public static ObjectMatrix2D toDatabase(String url, String tablename, String username,
			String password) {
		try {
			Class<?> c = Class.forName("org.ujmp.jdbc.ImportMatrixJDBC");
			Method method = c.getMethod("fromFile", new Class[] { File.class, Object[].class });
			Matrix matrix = (Matrix) method.invoke(null, file, parameters);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public static ObjectMatrix2D toDatabase(DB type, String host, int port, String database,
			String tablename, String username, String password) {
		return null;
	}

}