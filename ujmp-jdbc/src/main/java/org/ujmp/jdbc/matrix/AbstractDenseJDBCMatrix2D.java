/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public abstract class AbstractDenseJDBCMatrix2D extends
		AbstractDenseObjectMatrix2D implements Closeable {
	private static final long serialVersionUID = -9077208839474846706L;

	private String url = null;

	private String username = null;

	private String password = null;

	private String sqlStatement = null;

	private PreparedStatement preparedStatement = null;

	private Connection connection = null;

	private ResultSet resultSet = null;

	private long[] size = null;

	public AbstractDenseJDBCMatrix2D(String url, String sqlStatement,
			String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.sqlStatement = sqlStatement;
	}

	public String getSQLStatement() {
		return sqlStatement;
	}

	public synchronized Object getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public synchronized Object getObject(int row, int column) {
		try {
			ResultSet rs = getResultSet(row);
			return rs.getObject(column + 1);
		} catch (SQLException e) {
			if ("S1009".equals(e.getSQLState())) {
				// ignore Value '0000-00-00' can not be represented
				return null;
			}
			throw new MatrixException(e);
		}
	}

	public final String getSelectString() {
		return sqlStatement;
	}

	public synchronized void setObject(Object value, long row, long column) {
	}

	public synchronized void setObject(Object value, int row, int column) {
	}

	public synchronized long[] getSize() {
		try {
			if (size == null) {
				ResultSet rs = getResultSet(1);
				ResultSetMetaData rsMetaData = rs.getMetaData();
				long columnCount = rsMetaData.getColumnCount();
				rs.last();
				long rowCount = rs.getRow();
				size = new long[] { rowCount, columnCount };
			}
			return size;
		} catch (SQLException e) {
			throw new MatrixException(e);
		}
	}

	public synchronized void close() throws IOException {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			throw new IOException(e.toString());
		}
	}

	public synchronized ResultSet getResultSet(long row) throws SQLException {
		if (resultSet == null) {
			PreparedStatement ps = getSelectStatement();
			resultSet = ps.executeQuery();
			if (getLabelObject() == null) {
				setLabel(getUrl() + " " + getSelectString());
				ResultSetMetaData rsm = resultSet.getMetaData();
				for (int c = 0; c < rsm.getColumnCount(); c++) {
					setColumnLabel(c, rsm.getColumnLabel(c + 1));
				}
			}
		}
		return resultSet;
	}

	public synchronized PreparedStatement getSelectStatement()
			throws SQLException {
		if (preparedStatement == null) {
			preparedStatement = getConnection().prepareStatement(
					getSelectString(), ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		}
		return preparedStatement;
	}

	public synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(getUrl(), getUsername(),
					getPassword());
		}
		return connection;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}