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

package org.ujmp.core.objectmatrix.stub;

import java.util.Map;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.CoordinateSetToLongWrapper;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractMapToSparseMatrixWrapper extends AbstractSparseObjectMatrix implements
		Wrapper<Map<Coordinates, Object>> {
	private static final long serialVersionUID = -6292034262789053069L;

	private final Object defaultValue = null;

	private final Map<Coordinates, Object> values;

	private int maximumNumberOfEntries = -1;

	public AbstractMapToSparseMatrixWrapper(Map<Coordinates, Object> map, Matrix m) {
		super(m.getSize());
		this.size = Coordinates.copyOf(m.getSize());
		this.values = map;
		for (long[] c : m.allCoordinates()) {
			setObject(m.getAsObject(c), c);
		}
	}

	public AbstractMapToSparseMatrixWrapper(Map<Coordinates, Object> map, long... size) {
		super(size);
		this.size = Coordinates.copyOf(size);
		this.values = map;
	}

	public final Map<Coordinates, Object> getMap() {
		return values;
	}

	public final Map<Coordinates, Object> getWrappedObject() {
		return getMap();
	}

	public final void setWrappedObject(Map<Coordinates, Object> object) {
		throw new RuntimeException("not allowed");
	}

	public final Object getObject(long... coordinates) {
		Object v = getMap().get(Coordinates.wrap(coordinates));
		return v == null ? defaultValue : v;
	}

	public final boolean containsCoordinates(long... coordinates) {
		return getMap().containsKey(Coordinates.wrap(coordinates));
	}

	public final double getAsDouble(long... coordinates) {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public final void setAsDouble(double v, long... coordinates) {
		setObject(v, coordinates);
	}

	public final void setObject(Object o, long... coordinates) {
		if (MathUtil.getDouble(o) == 0.0) {
			getMap().remove(Coordinates.wrap(coordinates));
		} else {
			while (maximumNumberOfEntries > 0 && getMap().size() > maximumNumberOfEntries) {
				getMap().remove(getMap().keySet().iterator().next());
			}
			if (Coordinates.isSmallerThan(coordinates, getSize())) {
				getMap().put(Coordinates.wrap(coordinates).clone(), o);
			}
		}
	}

	public final int getMaximumNumberOfEntries() {
		return maximumNumberOfEntries;
	}

	public final long getValueCount() {
		return getMap().size();
	}

	public final Iterable<long[]> availableCoordinates() {
		return new CoordinateSetToLongWrapper(getMap().keySet());
	}

	public final void setMaximumNumberOfEntries(int maximumNumberOfEntries) {
		this.maximumNumberOfEntries = maximumNumberOfEntries;
	}

}
