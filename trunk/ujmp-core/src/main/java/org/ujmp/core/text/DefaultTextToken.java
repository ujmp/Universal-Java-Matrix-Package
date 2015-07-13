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

package org.ujmp.core.text;

import org.ujmp.core.mapmatrix.DefaultMapMatrix;

public class DefaultTextToken extends DefaultMapMatrix<String, Object> implements TextToken {
	private static final long serialVersionUID = 2168924027244676193L;

	private static int tokenId = 1;

	public DefaultTextToken(String id, String tokenText, String tag) {
		put(ID, id);
		put(TOKEN, tokenText);
		put(TAG, tag);
	}

	public DefaultTextToken(String tokenText, String tag) {
		this("Token" + (tokenId++), tokenText, tag);
	}

	public DefaultTextToken(String tokenText) {
		this(tokenText, NOTAG);
	}

	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("    {\n");
		sb.append("      \"Type\": \"Token\",\n");
		sb.append("      \"Id\": ");
		sb.append("\"");
		sb.append(get("Id"));
		sb.append("\",\n");
		sb.append("      \"Token\": ");
		sb.append("\"");
		sb.append(escape(String.valueOf(get("Token"))));
		sb.append("\"");
		if (get("Tag") != null) {
			sb.append(",\n");
			sb.append("      \"Tag\": ");
			sb.append("\"");
			sb.append(get("Tag"));
			sb.append("\"");
		}
		sb.append("\n    }");
		return sb.toString();
	}

	private String escape(String string) {
		string = string.replace("\\", "\\\\");
		string = string.replace("\"", "\\\"");
		return string;
	}

	public String getText() {
		return String.valueOf(get(TOKEN));
	}

	public String getTag() {
		return String.valueOf(get(TAG));
	}

	public void setTag(String tag) {
		put(TAG, tag);
	}

}
