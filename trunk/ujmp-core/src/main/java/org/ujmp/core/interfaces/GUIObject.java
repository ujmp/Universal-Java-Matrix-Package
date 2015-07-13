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

package org.ujmp.core.interfaces;

import java.awt.Image;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.ujmp.core.Coordinates;

/**
 * A GUIObject is an object that can be displayed in a Frame. This interface is
 * needed to indicate that an object can be displayed when the package
 * org.ujmp.gui is available. In org.ujmp.matrix is also known that such an
 * object exists, but not what methods it provides. The object will be created
 * using the Reflection Api.
 * 
 * @author Holger Arndt
 * 
 */
public interface GUIObject extends Serializable, Cloneable, Clearable, HasLabel, HasDescription,
		HasToolTip {

	public static final String PRELOADER = "[loading...]";

	/**
	 * Indicates that changed in the object have been made, that should be
	 * updated on the screen.
	 */
	public void fireValueChanged();

	public JFrame showGUI();

	public Image getIcon();

	public int getModCount();

	/**
	 * This method will return a JFrame for this object.
	 */
	public JFrame getFrame();

	/**
	 * This method will return a JPanel for this object.
	 */
	public JPanel getPanel();

	public CoreObject getCoreObject();

	public void fireValueChanged(Coordinates coordinates, Object object);

	public void fireValueChanged(Coordinates start, Coordinates end);

}
