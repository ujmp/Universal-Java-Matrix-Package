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

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.ujmp.core.interfaces.GUIObject;

public class PrintAction extends ObjectAction {
	private static final long serialVersionUID = 5977367168891486978L;

	public PrintAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Print...");
		putValue(Action.SHORT_DESCRIPTION, "Print a screenshot of this object");
	}

	
	public Object call() {
		if (getComponent() == null) {
			//logger.log(Level.WARNING, "no panel provided for printing");
		} else {
			// PrintPreviewDialog ppd = new PrintPreviewDialog(getPanel());
			// ppd.setVisible(true);
		}
		return null;
	}

}
