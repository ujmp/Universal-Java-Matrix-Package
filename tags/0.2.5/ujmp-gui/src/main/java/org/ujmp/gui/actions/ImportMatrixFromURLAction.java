/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.ujmp.gui.actions;

import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.interfaces.GUIObject;

public class ImportMatrixFromURLAction extends ObjectAction {
	private static final long serialVersionUID = -7585669703654474086L;

	public ImportMatrixFromURLAction(JComponent c, GUIObject m) {
		super(c, m);
		putValue(Action.NAME, "from URL...");
		putValue(Action.SHORT_DESCRIPTION,
				"import a matrix from a location on the web");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
	}

	
	public Object call() {
		try {
			URL url = null;
			while (url == null) {
				String s = JOptionPane.showInputDialog("Enter URL:", "http://");
				url = new URL(s);
			}
			FileFormat fileFormat = FileFormat.values()[JOptionPane
					.showOptionDialog(getComponent(), "Select format",
							"Import Matrix", JOptionPane.OK_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, FileFormat
									.values(), FileFormat.CSV)];

			Matrix m = MatrixFactory.importFromURL(fileFormat, url);
			m.showGUI();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
