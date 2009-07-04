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

package org.ujmp.core.objectmatrix.impl;

import java.util.HashMap;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;
import org.ujmp.core.objectmatrix.stub.AbstractMapToTiledMatrix2DWrapper;

public class DefaultTiledObjectMatrix2D extends AbstractMapToTiledMatrix2DWrapper {
	private static final long serialVersionUID = 6745798685307431625L;

	private Map<Coordinates, ObjectMatrix2D> values = null;

	public DefaultTiledObjectMatrix2D(long... size) {
		super(size);
	}

	public DefaultTiledObjectMatrix2D(Matrix source) {
		super(source);
	}

	@Override
	public Map<Coordinates, ObjectMatrix2D> getMap() {
		if (values == null) {
			values = new HashMap<Coordinates, ObjectMatrix2D>();
		}
		return values;
	}

	@Override
	public void setMap(Map<Coordinates, ObjectMatrix2D> map) {
		this.values = map;
	}

}
