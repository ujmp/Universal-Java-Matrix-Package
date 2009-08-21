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

package org.ujmp.jmathplot;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import org.ujmp.core.Matrix;
import org.ujmp.gui.interfaces.CanBeRepainted;
import org.ujmp.gui.util.GraphicsExecutor;

public abstract class AbstractJMathPlotPanel extends JPanel implements
		ComponentListener, CanBeRepainted {
	private static final long serialVersionUID = 2083997325942788081L;

	private Matrix matrix = null;

	private JPanel panel = null;

	public Matrix getMatrix() {
		return matrix;
	}

	public AbstractJMathPlotPanel(Matrix matrix) {
		this.matrix = matrix;
		addComponentListener(this);
	}

	public void changeState() {
		if (panel == null && isShowing()) {
			GraphicsExecutor.scheduleUpdate(this);
		} else if (panel != null && !isShowing()) {
			destroy();
		}
	}

	public void destroy() {
		remove(panel);
		panel = null;
	}

	
	public void componentHidden(ComponentEvent e) {
		changeState();
	}

	
	public void componentMoved(ComponentEvent e) {
		changeState();
	}

	
	public void componentResized(ComponentEvent e) {
		changeState();
	}

	
	public void componentShown(ComponentEvent e) {
		changeState();
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}
