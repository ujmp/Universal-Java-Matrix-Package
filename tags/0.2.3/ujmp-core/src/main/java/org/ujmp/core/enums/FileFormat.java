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

package org.ujmp.core.enums;

import java.io.File;
import java.util.Arrays;

import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;
import org.ujmp.core.util.io.UJMPFileFilter;

/**
 * Import and export formats that are supported.
 */
public enum FileFormat {
	CSV("Comma Separated Files", "csv"), //
	TXT("Text Files", "txt"), //
	M("Matlab Script Files", "m"), //
	MAT("Matlab Data Files", "mat"), //
	GIF("GIF Image Files", "gif"), //
	FILE("Text Files", "*"), //
	MDB("Microsoft Access Files", "mdb"), //
	R("R Files", "r"), //
	JPG("JPG Image Files", "jpg", "jpeg"), //
	HTML("HTML Files", "html", "htm"), //
	MTX("Matrix Data Format", "mtx"), //
	XLS("Microsoft Excel Files", "xls"), //
	SER("Serialized Data Files", "ser", "obj", "dat"), //
	GraphML("GraphML Files", "gml", "graphml"), //
	TEX("Latex Files", "tex"), //
	WAV("Wave Audio Files", "wav"), //
	BMP("BMP Image Files", "bmp"), //
	TIFF("TIFF Image Files", "tif"), //
	PLT("GnuPlot Files", "plt"), //
	PDF("PDF Files", "pdf"), //
	PNG("PNG Images Files", "png"), //
	XML("XML Files", "xml"), //
	AML("AML Files", "aml"), //
	ARFF("ARFF Files", "arf"), //
	ATT("ATT Files", "att"), //
	LOG("Log Files", "log"), //
	NET("Net Files", "net"), //
	STRING("String files", "txt"), //
	SPARSECSV("Sparse CSV Files", "csv"), //
	RAW("Binary Files", "raw", "bin"), //
	ImapMessages("Imap Messages", "imap"), //
	ImapFolders("Imap Folders", "imap");

	private String[] extensions = null;

	private String description = null;

	private javax.swing.filechooser.FileFilter fileFilter = null;

	private static Matrix matrix = null;

	FileFormat(String description, String... extensions) {
		this.extensions = extensions;
		this.description = description;
		this.fileFilter = new UJMPFileFilter(getDescription(), getExtensions());
	}

	public String[] getExtensions() {
		return extensions;
	}

	public javax.swing.filechooser.FileFilter getFileFilter() {
		return fileFilter;
	}

	public String getDescription() {
		return description;
	}

	public static FileFormat guess(File file) {
		String name = file.getName().toLowerCase();
		for (FileFormat f : FileFormat.values()) {
			for (String e : f.getExtensions()) {
				if (name.endsWith(e)) {
					return f;
				}
			}
		}
		return FileFormat.TXT;
	}

	public static Matrix getMatrix() {
		if (matrix == null) {
			matrix = new FileFormatMatrix();
		}
		return matrix;
	}
}

class FileFormatMatrix extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = 1386986795129799225L;

	public FileFormatMatrix() {
		setLabel("Supported File Formats");
		setColumnLabel(0, "File Format");
		setColumnLabel(1, "Description");
		setColumnLabel(2, "Extensions");
	}

	@Override
	public Object getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	@Override
	public Object getObject(int row, int column) {
		FileFormat f = FileFormat.values()[row];
		switch (column) {
		case 1:
			return f.getDescription();
		case 2:
			return Arrays.asList(f.getExtensions());
		default:
			return f.name();
		}
	}

	@Override
	public void setObject(Object value, long row, long column) {
	}

	@Override
	public void setObject(Object value, int row, int column) {
	}

	@Override
	public long[] getSize() {
		return new long[] { FileFormat.values().length, 3 };
	}

}