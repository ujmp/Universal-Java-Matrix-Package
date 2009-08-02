/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.matrices.stubs.AbstractDenseObjectMatrix2D;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

public class DenseJackcessMatrix2D extends AbstractDenseObjectMatrix2D implements Closeable {
	private static final long serialVersionUID = -6342663672866315180L;

	private Database database = null;

	private Table table = null;

	private List<Column> columns = null;

	Cursor cursor = null;

	public DenseJackcessMatrix2D(File file, String tablename) throws IOException {
		database = Database.open(file);
		table = database.getTable(tablename);
		columns = table.getColumns();
		cursor = Cursor.createCursor(table);
	}

	public DenseJackcessMatrix2D(File file, Matrix matrix) throws IOException {
		this(file, "ujmp-matrix", matrix);
	}

	public DenseJackcessMatrix2D(File file, String tablename, Matrix matrix) throws IOException {
		try {
			database = Database.create(file);

			TableBuilder tb = new TableBuilder(tablename);

			for (int i = 0; i < matrix.getColumnCount(); i++) {
				ColumnBuilder cb = new ColumnBuilder("Column" + i);
				switch (matrix.getEntryType()) {
				case DOUBLE:
					cb.setSQLType(Types.DOUBLE);
					break;
				case INTEGER:
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
					data[c] = matrix.getObject(r, c);
				}
				table.addRow(data);
			}
		} catch (SQLException e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public synchronized Object getObject(long row, long column) throws MatrixException {
		if (columns == null || cursor == null) {
			return null;
		}
		try {
			Column c = columns.get((int) column);
			cursor.reset();
			cursor.moveNextRows((int) row + 1);
			return cursor.getCurrentRowValue(c);
		} catch (IOException e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public void setObject(Object value, long row, long column) {
	}

	@Override
	public long[] getSize() {
		if (table == null) {
			return new long[] { 0, 0 };
		} else {
			return new long[] { table.getRowCount(), table.getColumnCount() };
		}
	}

	public void close() throws IOException {
		database.close();
	}
}
