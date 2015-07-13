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

package org.ujmp.core.treematrix;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ujmp.core.collections.list.ArrayIndexList;

public class ObjectTreeMatrix extends AbstractTreeMatrix<Object> {
	private static final long serialVersionUID = -7343649063964349539L;

	private final List<Object> objects = new ArrayIndexList<Object>();

	private Object root = null;

	private final Map<Object, List<Object>> childrenMap = new HashMap<Object, List<Object>>();

	private final Map<Object, Object> parentMap = new HashMap<Object, Object>();

	public ObjectTreeMatrix(Object o) {
		root = new NameAndValue("Root", o);
		addSuperclass("Root", o);
		addFields("Root", o);
	}

	public Map<Object, Object> getParentMap() {
		return parentMap;
	}

	public final void clear() {
		objects.clear();
		childrenMap.clear();
		parentMap.clear();
		root = null;
	}

	public Iterable<long[]> availableCoordinates() {
		throw new RuntimeException("not implemented");
	}

	private void addSuperclass(String name, Object o) {
		Class<?> superclass = o.getClass().getSuperclass();
		if (superclass != null && !Object.class.equals(superclass)
				&& !Number.class.equals(superclass)) {

			addSuperclass("super", superclass);
			addFields("super", superclass);

			addChild(new NameAndValue(name, o), new NameAndValue("super", superclass));
		}
	}

	private void addFields(String name, Object o) {
		if (o == null) {
			return;
		}

		NameAndValue no = new NameAndValue(name, o);

		if (objects.contains(no)) {
			return;
		}

		objects.add(no);

		addSuperclass(name, o);

		if (o instanceof Long) {
			return;
		}
		if (o instanceof Integer) {
			return;
		}
		if (o instanceof String) {
			return;
		}
		if (o instanceof Boolean) {
			return;
		}
		if (o instanceof Float) {
			return;
		}
		if (o instanceof Short) {
			return;
		}
		if (o instanceof Byte) {
			return;
		}

		Field[] fields = o.getClass().getDeclaredFields();
		if (fields != null) {
			for (Field f : fields) {
				try {
					f.setAccessible(true);
					Object child = f.get(o);

					System.out.println(f.getName() + "=" + child);

					addFields(f.getName(), child);

					addChild(new NameAndValue(name, o), new NameAndValue(f.getName(), child));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<Object> getChildren(Object o) {
		List<Object> children = childrenMap.get(o);
		if (children == null) {
			children = new LinkedList<Object>();
			childrenMap.put(o, children);
		}
		return children;
	}

	public Collection<Object> getObjectList() {
		return objects;
	}

	public Object getRoot() {
		return root;
	}

	public void setRoot(Object o) {
		root = o;
	}

}

class NameAndValue {

	private String name = null;

	private Object value = null;

	public NameAndValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String toString() {
		String s = name + " = " + value;
		if (s.length() > 50) {
			s = s.substring(0, 50) + "...";
		}
		return s;
	}

	public int hashCode() {
		return (name + " - " + value).hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof NameAndValue) {
			NameAndValue no = (NameAndValue) o;
			return (name + " - " + value).equals(no.name + " - " + no.value);
		} else {
			return false;
		}
	}

}
