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

package org.ujmp.itext;

import java.io.File;

import javax.swing.JButton;

import junit.framework.TestCase;

public class TestExportPDF extends TestCase {

	public void testExport() {
		JButton b = new JButton("test");
		b.setSize(100, 100);
		File file = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "test.pdf");
		ExportPDF.save(file, b);

		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		file.delete();

		assertFalse(file.exists());

	}

}
