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

package org.ujmp.core.collections;

import java.io.Serializable;

public abstract class AbstractList<T> extends java.util.AbstractList<T> implements Serializable {
	private static final long serialVersionUID = 8080725473401179259L;

	// do not create writeObject and readObject!
	// it will not work when additional data from a subclass is needed
	// to create the list
	// private void readObject(ObjectInputStream s) throws IOException,
	// ClassNotFoundException {
	// s.defaultReadObject();
	// int size = s.readInt();
	// for (int i = 0; i < size; i++) {
	// try {
	// T t = (T) s.readObject();
	// add(t);
	// } catch (OptionalDataException e) {
	// return;
	// }
	// }
	// }

	// do not create writeObject and readObject!
	// it will not work when additional data from a subclass is needed
	// to create the list
	// private void writeObject(ObjectOutputStream s) throws IOException,
	// MatrixException {
	// s.defaultWriteObject();
	// s.writeInt(size());
	// for (Object t : this) {
	// s.writeObject(t);
	// }
	// }

	public abstract T get(int index);

	public abstract boolean add(T t);

	public abstract void add(int index, T element);

	public abstract T remove(int index);

	public abstract T set(int index, T element);

}
