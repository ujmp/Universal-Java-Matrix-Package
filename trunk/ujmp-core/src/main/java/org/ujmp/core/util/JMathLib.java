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

package org.ujmp.core.util;

import java.lang.reflect.Method;

public abstract class JMathLib {

	public static boolean isAvailable() {
		try {
			Class.forName("org.ujmp.jmathlib.JMathLib");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static void showGUI() {
		try {
			Class<?> c = Class.forName("org.ujmp.jmathlib.JMathLib");
			Method method = c.getMethod("showGUI", new Class[] {});
			method.invoke(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
