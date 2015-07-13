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

package org.ujmp.core.util.matrices;

import java.util.ArrayList;
import java.util.List;

import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;
import org.ujmp.core.util.AbstractPlugin;

public class UJMPPluginsMatrix extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = 9076856922668700140L;

	private final List<String> classes = new ArrayList<String>();

	private Matrix matrix = null;

	public UJMPPluginsMatrix() {
		super(0, 5);
		addClass("ujmp-colt");
		addClass("ujmp-commonsmath");
		addClass("ujmp-core");
		addClass("ujmp-ehcache");
		addClass("ujmp-ejml");
		addClass("ujmp-elasticsearch");
		addClass("ujmp-examples");
		addClass("ujmp-gui");
		addClass("ujmp-hadoop");
		addClass("ujmp-itext");
		addClass("ujmp-jackcess");
		addClass("ujmp-jama");
		addClass("ujmp-jblas");
		addClass("ujmp-jdbc");
		addClass("ujmp-jfreechart");
		addClass("ujmp-jmatio");
		addClass("ujmp-jsch");
		addClass("ujmp-jsci");
		addClass("ujmp-jscience");
		addClass("ujmp-jung");
		addClass("ujmp-la4j");
		addClass("ujmp-lucene");
		addClass("ujmp-mail");
		addClass("ujmp-mtj");
		addClass("ujmp-ojalgo");
		addClass("ujmp-parallelcolt");
		addClass("ujmp-pdfbox");
		addClass("ujmp-poi");
		addClass("ujmp-vecmath");
		refresh();
		setLabel("UJMP Plugins");
		setColumnLabel(0, "Name");
		setColumnLabel(1, "Available");
		setColumnLabel(2, "Description");
		setColumnLabel(3, "Dependencies");
		setColumnLabel(4, "Status");
	}

	public void refresh() {
		matrix = DenseObjectMatrix2D.Factory.zeros(classes.size(), 5);

		int r = 0;

		for (String s : classes) {

			matrix.setAsString(s, r, 0);

			Class<?> cl = null;
			if (s.startsWith("ujmp")) {
				try {
					cl = Class.forName("org.ujmp." + s.substring(5) + ".Plugin");
				} catch (ClassNotFoundException e) {
				}
			} else {
				try {
					cl = Class.forName("org.jdmp." + s.substring(5) + ".Plugin");
				} catch (ClassNotFoundException e) {
				}
			}

			AbstractPlugin o = null;
			if (cl != null) {
				try {
					o = (AbstractPlugin) cl.newInstance();
				} catch (Exception e) {
				}
			}

			if (o != null) {
				try {
					matrix.setAsString("yes", r, 1);
					matrix.setAsString(o.getDescription(), r, 2);
					matrix.setAsString("" + o.getDependencies(), r, 3);
					matrix.setAsString(o.getStatus(), r, 4);
				} catch (Throwable t) {
					matrix.setAsString("no", r, 1);
					matrix.setAsString("n/a", r, 2);
					matrix.setAsString("n/a", r, 3);
					matrix.setAsString(t.getMessage(), r, 4);
				}
			} else {
				matrix.setAsString("no", r, 1);
				matrix.setAsString("n/a", r, 2);
				matrix.setAsString("n/a", r, 3);
				matrix.setAsString("n/a", r, 4);
			}

			r++;

		}
	}

	protected void addClass(String c) {
		classes.add(c);
	}

	public String getString(long row, long column) {
		return matrix.getAsString(row, column);
	}

	public void setString(String value, long row, long column) {
	}

	public long[] getSize() {
		size = matrix.getSize();
		return size;
	}

}
