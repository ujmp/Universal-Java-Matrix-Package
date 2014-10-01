/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.link.source;

import java.io.File;
import java.io.IOException;

import org.ujmp.core.Matrix;
import org.ujmp.core.stringmatrix.impl.DenseCSVStringMatrix2D;

public class DefaultMatrixFileLinkSource extends AbstractMatrixFileLinkSource {

	public DefaultMatrixFileLinkSource(File file) {
		super(file);
	}

	public DefaultMatrixFileLinkSource(String file) {
		super(file);
	}

	public Matrix asDenseCSV() throws IOException {
		return asDenseCSV('\0');
	}

	public Matrix asDenseCSV(char columnSeparator) throws IOException {
		return asDenseCSV(columnSeparator, '\0');
	}

	public Matrix asDenseCSV(char columnSeparator, char enclosingCharacter) throws IOException {
		Matrix m = new DenseCSVStringMatrix2D(columnSeparator, enclosingCharacter, getFile());
		return m;
	}

}
