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

package org.ujmp.core.filematrix;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;

public class FileMatrix extends AbstractMapMatrix<String, Matrix> implements FileOrDirectoryMatrix {
	private static final long serialVersionUID = -4912495890644097086L;

	private final File file;

	private final Map<String, Matrix> map = new TreeMap<String, Matrix>();

	public FileMatrix(String filename) {
		this(new File(filename));
	}

	public FileMatrix(File file) {
		this.file = file;
		if (file == null) {
			setLabel("/");
		} else {
			setLabel(file.getName());
			setMetaData(PATH, file.getPath());
			setMetaData(FILENAME, file.getName());
			setMetaData(CANEXECUTE, file.canExecute());
			setMetaData(CANREAD, file.canRead());
			setMetaData(CANWRITE, file.canWrite());
			setMetaData(ISHIDDEN, file.isHidden());
			setMetaData(ISDIRECTORY, file.isDirectory());
			setMetaData(ISFILE, file.isFile());
			setMetaData(LASTMODIFIED, file.lastModified());
			setMetaData(SIZE, file.length());
		}
	}

	public Matrix get(Object key) {
		loadContent();
		return map.get(key);
	}

	public Set<String> keySet() {
		loadContent();
		return map.keySet();
	}

	private void loadContent() {
		if (map.isEmpty()) {
			try {
				FileFormat format = FileFormat.guess(file);
				setMetaData(FILEFORMAT, format);
				map.put(FILEFORMAT, Matrix.Factory.linkToValue(format));
				switch (format) {
				case BMP:
				case GIF:
				case JPG:
				case JPEG2000:
				case PNG:
				case TIF:
					map.put(IMAGE, Matrix.Factory.linkToImage(file));
					break;
				case DB:
					map.put(DATA, Matrix.Factory.linkToJDBC(file));
				case ZIP:
					map.put(DATA, Matrix.Factory.linkToZipFile(file));
				default:
					break;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	protected void clearMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Matrix removeFromMap(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Matrix putIntoMap(String key, Matrix value) {
		throw new UnsupportedOperationException();
	}

}
