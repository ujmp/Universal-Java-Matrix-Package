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

package org.ujmp.core.calculation;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasDoubleArray;
import org.ujmp.core.interfaces.HasDoubleArray2D;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.matrix.DenseMatrix2D;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.concurrent.PFor;

public interface Transpose<T> {

	public static Transpose<Matrix> INSTANCE = new Transpose<Matrix>() {

		public void calc(final Matrix source, final Matrix target) {
			if (source == target) {
				throw new MatrixException("cannot transpose into original matrix");
			}
			if (source instanceof DenseMatrix && target instanceof DenseMatrix) {
				Transpose.DENSEMATRIX.calc((DenseMatrix) source, (DenseMatrix) target);
			} else if (source instanceof SparseMatrix && target instanceof SparseMatrix) {
				Transpose.SPARSEMATRIX.calc((SparseMatrix) source, (SparseMatrix) target);
			} else {
				for (long[] c : source.allCoordinates()) {
					Object o = source.getAsObject(c);
					target.setAsObject(o, Coordinates.transpose(c));
				}
			}
		}
	};

	public static Transpose<DenseMatrix> DENSEMATRIX = new Transpose<DenseMatrix>() {

		public void calc(final DenseMatrix source, final DenseMatrix target) {
			if (source instanceof DenseMatrix2D && target instanceof DenseMatrix2D) {
				Transpose.DENSEMATRIX2D.calc((DenseMatrix2D) source, (DenseMatrix2D) target);
			} else {
				for (long[] c : source.allCoordinates()) {
					Object o = source.getAsObject(c);
					target.setAsObject(o, Coordinates.transpose(c));
				}
			}
		}
	};

	public static Transpose<SparseMatrix> SPARSEMATRIX = new Transpose<SparseMatrix>() {

		public void calc(final SparseMatrix source, final SparseMatrix target) {
			for (long[] c : source.availableCoordinates()) {
				Object o = source.getAsObject(c);
				target.setAsObject(o, Coordinates.transpose(c));
			}
		}
	};

	public static Transpose<DenseMatrix2D> DENSEMATRIX2D = new Transpose<DenseMatrix2D>() {

		public void calc(final DenseMatrix2D source, final DenseMatrix2D target) {
			if (source instanceof DenseDoubleMatrix2D && target instanceof DenseDoubleMatrix2D) {
				Transpose.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source,
						(DenseDoubleMatrix2D) target);
			} else {
				for (int r = (int) source.getRowCount(); --r != -1;) {
					for (int c = (int) source.getColumnCount(); --c != -1;) {
						Object o = source.getAsObject(r, c);
						target.setAsObject(o, c, r);
					}
				}
			}
		}
	};

	public static Transpose<DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new Transpose<DenseDoubleMatrix2D>() {

		public void calc(final DenseDoubleMatrix2D source, final DenseDoubleMatrix2D target) {
			if (source instanceof HasDoubleArray2D && target instanceof HasDoubleArray2D) {
				calc(((HasDoubleArray2D) source).getDoubleArray2D(), ((HasDoubleArray2D) target)
						.getDoubleArray2D());
			} else if (source instanceof HasDoubleArray && target instanceof HasDoubleArray) {
				calc((int) source.getRowCount(), (int) source.getColumnCount(),
						((HasDoubleArray) source).getDoubleArray(), ((HasDoubleArray) target)
								.getDoubleArray());
			} else {
				for (int r = (int) source.getRowCount(); --r != -1;) {
					for (int c = (int) source.getColumnCount(); --c != -1;) {
						target.setDouble(source.getDouble(r, c), c, r);
					}
				}
			}
		}

		private void calc(final double[][] source, final double[][] target) {
			final int retcols = source.length;
			final int retrows = source[0].length;
			if (retcols * retrows > 10000) {
				new PFor(0, retrows - 1) {
					@Override
					public void step(int i) {
						for (int c = 0; c < retcols; c++) {
							target[i][c] = source[c][i];
						}
					}
				};
			} else {
				for (int r = 0; r < retrows; r++) {
					for (int c = 0; c < retcols; c++) {
						target[r][c] = source[c][r];
					}
				}
			}
		}

		private void calc(final int rows, final int cols, final double[] source,
				final double[] target) {
			if (source.length > 10000) {
				new PFor(0, rows - 1) {

					@Override
					public void step(int i) {
						for (int r = 0; r < cols; r++) {
							target[i * cols + r] = source[r * rows + i];
						}
					}
				};
			} else {
				for (int c = 0; c < rows; c++) {
					for (int r = 0; r < cols; r++) {
						target[c * cols + r] = source[r * rows + c];
					}
				}
			}
		}
	};

	public void calc(T source, T target);

}