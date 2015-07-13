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

package org.ujmp.jackcess;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;
import org.ujmp.core.util.VerifyUtil;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

public class JackcessDenseObjectMatrix2D extends AbstractDenseObjectMatrix2D implements Closeable {
	private static final long serialVersionUID = -6342663672866315180L;

	private final Database database;

	private final Table table;

	private List<? extends Column> columns = null;

	private Cursor cursor = null;

	private Integer curPos = null;

	public JackcessDenseObjectMatrix2D(File file, String tablename) throws IOException {
		super(0, 0);
		database = DatabaseBuilder.open(file);
		VerifyUtil.verifyNotNull(database, "database could not be opened");
		table = database.getTable(tablename);
		VerifyUtil.verifyNotNull(table, "table not found in database");
		columns = table.getColumns();
		cursor = CursorBuilder.createCursor(table);

		for (int i = 0; i < columns.size(); i++) {
			setColumnLabel(i, columns.get(i).getName());
		}
		setLabel(tablename);
	}

	public JackcessDenseObjectMatrix2D(File file, Matrix matrix) throws IOException {
		this(file, "ujmp-matrix", matrix);
	}

	public JackcessDenseObjectMatrix2D(File file, String tablename, Matrix matrix)
			throws IOException {
		super(matrix.getRowCount(), matrix.getColumnCount());
		try {
			database = DatabaseBuilder.create(FileFormat.V2010, file);

			TableBuilder tb = new TableBuilder(tablename);

			for (int i = 0; i < matrix.getColumnCount(); i++) {
				ColumnBuilder cb = new ColumnBuilder("Column" + i);
				switch (matrix.getValueType()) {
				case DOUBLE:
					cb.setSQLType(Types.DOUBLE);
					break;
				case INT:
					cb.setSQLType(Types.INTEGER);
					break;
				default:
					cb.setSQLType(Types.VARCHAR);
					break;
				}
				tb.addColumn(cb.toColumn());
			}

			table = tb.toTable(database);

			for (int r = 0; r < matrix.getRowCount(); r++) {
				Object[] data = new Object[(int) matrix.getColumnCount()];
				for (int c = 0; c < matrix.getColumnCount(); c++) {
					data[c] = matrix.getAsObject(r, c);
				}
				table.addRow(data);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized Object getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	public synchronized Object getObject(int row, int column) {
		if (columns == null || cursor == null) {
			return null;
		}
		try {
			Column c = columns.get(column);
			if (curPos == null) {
				cursor.reset();
				cursor.moveNextRows(row + 1);
			} else {
				int diff = row + 1 - curPos;
				if (diff > 0) {
					cursor.moveNextRows(diff);
				} else if (diff < 0) {
					cursor.movePreviousRows(-diff);
				}
			}
			curPos = row + 1;
			return cursor.getCurrentRowValue(c);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setObject(Object value, long row, long column) {
	}

	public void setObject(Object value, int row, int column) {
	}

	public long[] getSize() {
		size[ROW] = table.getRowCount();
		size[COLUMN] = table.getColumnCount();
		return size;
	}

	public void close() throws IOException {
		database.close();
	}

}
