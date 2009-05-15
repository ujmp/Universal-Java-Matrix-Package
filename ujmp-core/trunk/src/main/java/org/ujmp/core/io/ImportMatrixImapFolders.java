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

package org.ujmp.core.io;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public abstract class ImportMatrixImapFolders {

	public static Matrix fromURL(URL url, Object... parameters) throws IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.mail.ImportMatrixImapFolders");
			Method method = c.getMethod("fromURL", new Class[] { URL.class, Object[].class });
			Matrix matrix = (Matrix) method.invoke(null, url, parameters);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException("ujmp-mail not found in classpath", e);
		}
	}

}
