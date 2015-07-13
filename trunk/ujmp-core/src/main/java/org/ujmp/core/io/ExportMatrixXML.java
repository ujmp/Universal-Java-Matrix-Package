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

package org.ujmp.core.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.objectmatrix.impl.EmptyMatrix;
import org.ujmp.core.util.io.IntelligentFileWriter;

public class ExportMatrixXML {

	private static final String XMLHEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	private static boolean createXMLHeader = true;

	private static boolean createInfo = true;

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException {
		IntelligentFileWriter writer = new IntelligentFileWriter(file);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toStream(OutputStream outputStream, Matrix matrix, Object... parameters)
			throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toWriter(Writer writer, MapMatrix<?, ?> map, Object... parameters)
			throws IOException {
		final String EOL = System.getProperty("line.separator");
		writer.write("<map>");
		writer.write(EOL);
		for (Map.Entry<?, ?> e : map.entrySet()) {
			Object key = e.getKey();
			Object value = e.getValue();
			writer.write("<" + key + ">");
			writer.write(String.valueOf(value));
			writer.write("</" + key + ">");
			writer.write(EOL);
		}
		writer.write("</map>");
		writer.write(EOL);
	}

	public static void toWriter(Writer writer, Matrix matrix, Object... parameters)
			throws IOException {
		final String EOL = System.getProperty("line.separator");

		if (parameters != null && parameters.length > 0) {
			if (parameters[0] instanceof Boolean) {
				createXMLHeader = (Boolean) createXMLHeader;
			}
		}

		if (createXMLHeader == true) {
			writer.write(XMLHEADER);
			writer.write(EOL);
		}

		if (matrix == null || matrix instanceof EmptyMatrix) {
			writer.write("<emptyMatrix></emptyMatrix>");
			writer.write(EOL);
		} else if (matrix instanceof MapMatrix<?, ?>) {
			toWriter(writer, (MapMatrix<?, ?>) matrix, parameters);
		} else if (matrix instanceof ListMatrix<?>) {
			toWriter(writer, (ListMatrix<?>) matrix, parameters);
		} else {
			String size = Coordinates.toString(matrix.getSize());
			String vt = matrix.getValueType().name();

			writer.write("<matrix ");
			writer.write("size=\"");
			writer.write(size);
			writer.write("\" valueType=\"");
			writer.write(vt);
			writer.write("\">");
			writer.write(EOL);

			// if (createInfo == true) {
			// eventWriter.add(eventFactory.createStartElement("", "", "info"));
			// eventWriter.add(newline);
			// eventWriter.add(eventFactory.createStartElement("", "",
			// "creator"));
			// eventWriter.add(eventFactory.createCharacters("UJMP"));
			// eventWriter.add(eventFactory.createEndElement("", "",
			// "creator"));
			// eventWriter.add(newline);
			// eventWriter.add(eventFactory.createStartElement("", "",
			// "version"));
			// eventWriter.add(eventFactory.createCharacters(UJMP.UJMPVERSION));
			// eventWriter.add(eventFactory.createEndElement("", "",
			// "version"));
			// eventWriter.add(newline);
			// eventWriter.add(eventFactory.createStartElement("", "", "os"));
			// eventWriter.add(eventFactory.createCharacters(System.getProperty("os.name")));
			// eventWriter.add(eventFactory.createEndElement("", "", "os"));
			// eventWriter.add(newline);
			// eventWriter.add(eventFactory.createStartElement("", "", "java"));
			// eventWriter.add(eventFactory.createCharacters(System.getProperty("java.version")));
			// eventWriter.add(eventFactory.createEndElement("", "", "java"));
			// eventWriter.add(newline);
			// eventWriter.add(eventFactory.createStartElement("", "", "date"));
			// eventWriter.add(eventFactory.createCharacters(new
			// Date().toString()));
			// eventWriter.add(eventFactory.createEndElement("", "", "date"));
			// eventWriter.add(newline);
			// eventWriter.add(eventFactory.createEndElement("", "", "info"));
			// eventWriter.add(newline);
			// }

			// Annotation annotation = matrix.getAnnotation();
			// if (annotation != null) {
			// eventWriter.add(eventFactory.createStartElement("", "",
			// "annotation"));
			// eventWriter.add(newline);
			//
			// Object label = annotation.getMatrixAnnotation();
			// if (label != null) {
			// eventWriter.add(eventFactory.createStartElement("", "",
			// "label"));
			// eventWriter.add(eventFactory.createCharacters("" + label));
			// eventWriter.add(eventFactory.createEndElement("", "", "label"));
			// eventWriter.add(newline);
			// }
			//
			// eventWriter.add(eventFactory.createEndElement("", "",
			// "annotation"));
			// eventWriter.add(newline);
			// }

			// eventWriter.add(eventFactory.createStartElement("", "", "data"));
			// eventWriter.add(newline);
			//
			// for (long[] c : matrix.availableCoordinates()) {
			// eventWriter.add(eventFactory.createStartElement("", "", "cell"));
			// String pos = Coordinates.toString(c);
			// eventWriter.add(eventFactory.createAttribute("pos", pos));
			//
			// eventWriter.add(eventFactory.createCharacters("" +
			// matrix.getAsObject(c)));
			//
			// eventWriter.add(eventFactory.createEndElement("", "", "cell"));
			// eventWriter.add(newline);
			// }
			//
			// eventWriter.add(eventFactory.createEndElement("", "", "data"));
			// eventWriter.add(newline);

			writer.write("</matrix>");
			writer.write(EOL);
		}
	}
}
