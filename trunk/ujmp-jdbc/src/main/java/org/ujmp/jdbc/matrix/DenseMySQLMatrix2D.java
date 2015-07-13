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

package org.ujmp.jdbc.matrix;

import java.sql.Connection;

public class DenseMySQLMatrix2D extends AbstractDenseJDBCMatrix2D {
	private static final long serialVersionUID = 6885926512790354810L;

	public DenseMySQLMatrix2D(Connection connection, String sqlStatement) throws ClassNotFoundException {
		super(connection, sqlStatement);
	}

	public DenseMySQLMatrix2D(String url, String sqlStatement, String username, String password)
			throws ClassNotFoundException {
		super(url, sqlStatement, username, password);
		Class.forName("com.mysql.jdbc.Driver");
	}

	public DenseMySQLMatrix2D(String host, int port, String databasename, String sqlStatement, String username,
			String password) throws ClassNotFoundException {
		this("jdbc:mysql://" + host + ":" + port + "/" + databasename, sqlStatement, username, password);
	}

}
