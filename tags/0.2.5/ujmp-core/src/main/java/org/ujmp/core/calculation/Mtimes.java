/*
 * Copyright (C) 2008-2010 by Holger Arndt, Frode Carlsen
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

import static org.ujmp.core.util.VerifyUtil.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.BlockDenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.impl.BlockMatrixLayout;
import org.ujmp.core.doublematrix.impl.BlockMultiply;
import org.ujmp.core.doublematrix.impl.BlockMatrixLayout.BlockOrder;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.matrix.DenseMatrix;
import org.ujmp.core.matrix.DenseMatrix2D;
import org.ujmp.core.matrix.SparseMatrix;
import org.ujmp.core.util.AbstractPlugin;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.VerifyUtil;
import org.ujmp.core.util.concurrent.PFor;
import org.ujmp.core.util.concurrent.UJMPThreadPoolExecutor;

public class Mtimes {
	public static int THRESHOLD = 100;

	public static final MtimesCalculation<Matrix, Matrix, Matrix> MATRIX = new MtimesMatrix();

	public static final MtimesCalculation<DenseMatrix, DenseMatrix, DenseMatrix> DENSEMATRIX = new MtimesDenseMatrix();

	public static final MtimesCalculation<DenseMatrix2D, DenseMatrix2D, DenseMatrix2D> DENSEMATRIX2D = new MtimesDenseMatrix2D();

	public static final MtimesCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new MtimesDenseDoubleMatrix2D();

	public static final MtimesCalculation<SparseMatrix, Matrix, Matrix> SPARSEMATRIX1 = new MtimesSparseMatrix1();

	public static final MtimesCalculation<Matrix, SparseMatrix, Matrix> SPARSEMATRIX2 = new MtimesSparseMatrix2();

	public static MtimesCalculation<Matrix, Matrix, Matrix> MTIMES_JBLAS = null;

	public static final boolean RESET_BLOCK_ORDER = false;

	static {
		init();
	}

	@SuppressWarnings("unchecked")
	public static void init() {
		try {
			AbstractPlugin p = (AbstractPlugin) Class.forName("org.ujmp.jblas.Plugin")
					.newInstance();
			if (p.isAvailable()) {
				MTIMES_JBLAS = (MtimesCalculation<Matrix, Matrix, Matrix>) Class.forName(
						"org.ujmp.jblas.calculation.Mtimes").newInstance();
			}
		} catch (Throwable t) {
		}
	}

}

class MtimesMatrix implements MtimesCalculation<Matrix, Matrix, Matrix> {

	public final void calc(final Matrix source1, final Matrix source2, final Matrix target) {
		if (source1 instanceof DenseDoubleMatrix2D && source2 instanceof DenseDoubleMatrix2D
				&& target instanceof DenseDoubleMatrix2D) {
			Mtimes.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source1,
					(DenseDoubleMatrix2D) source2, (DenseDoubleMatrix2D) target);
		} else if (source1 instanceof DenseMatrix2D && source2 instanceof DenseMatrix2D
				&& target instanceof DenseMatrix2D) {
			Mtimes.DENSEMATRIX2D.calc((DenseMatrix2D) source1, (DenseMatrix2D) source2,
					(DenseMatrix2D) target);
		} else if (source1 instanceof DenseMatrix && source2 instanceof DenseMatrix
				&& target instanceof DenseMatrix) {
			Mtimes.DENSEMATRIX.calc((DenseMatrix) source1, (DenseMatrix) source2,
					(DenseMatrix) target);
		} else if (source1 instanceof SparseMatrix) {
			Mtimes.SPARSEMATRIX1.calc((SparseMatrix) source1, source2, target);
		} else if (source2 instanceof SparseMatrix) {
			Mtimes.SPARSEMATRIX2.calc(source1, (SparseMatrix) source2, target);
		} else {
			gemm(source1, source2, target);
		}
	}

	private final void gemm(final Matrix A, final Matrix B, final Matrix C) {
		VerifyUtil.assert2D(A);
		VerifyUtil.assert2D(B);
		VerifyUtil.assert2D(C);
		final int m1RowCount = (int) A.getRowCount();
		final int m1ColumnCount = (int) A.getColumnCount();
		final int m2RowCount = (int) B.getRowCount();
		final int m2ColumnCount = (int) B.getColumnCount();
		VerifyUtil.assertEquals(m1ColumnCount, m2RowCount, "matrices have wrong sizes");
		VerifyUtil.assertEquals(m1RowCount, C.getRowCount(), "matrices have wrong sizes");
		VerifyUtil.assertEquals(m2ColumnCount, C.getColumnCount(), "matrices have wrong sizes");

		if (m1RowCount >= Mtimes.THRESHOLD && m1ColumnCount >= Mtimes.THRESHOLD
				&& m2ColumnCount >= Mtimes.THRESHOLD) {
			new PFor(0, m2ColumnCount - 1) {

				@Override
				public void step(int i) {
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C.setAsDouble(0.0d, irow, i);
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						final double temp = B.getAsDouble(lcol, i);
						if (temp != 0.0d) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			};
		} else {
			for (int i = 0; i < m2ColumnCount; i++) {
				for (int irow = 0; irow < m1RowCount; ++irow) {
					C.setAsDouble(0.0d, irow, i);
				}
				for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
					final double temp = B.getAsDouble(lcol, i);
					if (temp != 0.0d) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(
									C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol) * temp,
									irow, i);
						}
					}
				}
			}
		}
	}
};

class MtimesDenseMatrix implements MtimesCalculation<DenseMatrix, DenseMatrix, DenseMatrix> {

	public final void calc(final DenseMatrix source1, final DenseMatrix source2,
			final DenseMatrix target) {
		if (source1 instanceof DenseMatrix2D && source2 instanceof DenseMatrix2D
				&& target instanceof DenseMatrix2D) {
			Mtimes.DENSEMATRIX2D.calc((DenseMatrix2D) source1, (DenseMatrix2D) source2,
					(DenseMatrix2D) target);
		} else {
			gemm(source1, source2, target);
		}
	}

	private final void gemm(final DenseMatrix A, final DenseMatrix B, final DenseMatrix C) {
		VerifyUtil.assert2D(A);
		VerifyUtil.assert2D(B);
		VerifyUtil.assert2D(C);
		final int m1RowCount = (int) A.getRowCount();
		final int m1ColumnCount = (int) A.getColumnCount();
		final int m2RowCount = (int) B.getRowCount();
		final int m2ColumnCount = (int) B.getColumnCount();
		VerifyUtil.assertEquals(m1ColumnCount, m2RowCount, "matrices have wrong sizes");
		VerifyUtil.assertEquals(m1RowCount, C.getRowCount(), "matrices have wrong sizes");
		VerifyUtil.assertEquals(m2ColumnCount, C.getColumnCount(), "matrices have wrong sizes");

		if (m1RowCount >= Mtimes.THRESHOLD && m1ColumnCount >= Mtimes.THRESHOLD
				&& m2ColumnCount >= Mtimes.THRESHOLD) {
			new PFor(0, m2ColumnCount - 1) {

				@Override
				public void step(int i) {
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C.setAsDouble(0.0d, irow, i);
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						final double temp = B.getAsDouble(lcol, i);
						if (temp != 0.0d) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			};
		} else {
			for (int i = 0; i < m2ColumnCount; i++) {
				for (int irow = 0; irow < m1RowCount; ++irow) {
					C.setAsDouble(0.0d, irow, i);
				}
				for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
					final double temp = B.getAsDouble(lcol, i);
					if (temp != 0.0d) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(
									C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol) * temp,
									irow, i);
						}
					}
				}
			}
		}
	}
};

class MtimesSparseMatrix1 implements MtimesCalculation<SparseMatrix, Matrix, Matrix> {

	public final void calc(final SparseMatrix source1, final Matrix source2, final Matrix target) {
		VerifyUtil.assert2D(source1);
		VerifyUtil.assert2D(source2);
		VerifyUtil.assert2D(target);
		VerifyUtil.assertEquals(source1.getColumnCount(), source2.getRowCount(),
				"matrices have wrong sizes");
		VerifyUtil.assertEquals(target.getRowCount(), source1.getRowCount(),
				"matrices have wrong sizes");
		VerifyUtil.assertEquals(target.getColumnCount(), source2.getColumnCount(),
				"matrices have wrong sizes");
		target.clear();
		for (long[] c1 : source1.availableCoordinates()) {
			final double v1 = source1.getAsDouble(c1);
			if (v1 != 0.0d) {
				for (long col2 = source2.getColumnCount(); --col2 != -1;) {
					final double v2 = source2.getAsDouble(c1[1], col2);
					final double temp = v1 * v2;
					if (temp != 0.0d) {
						final double v3 = target.getAsDouble(c1[0], col2);
						target.setAsDouble(v3 + temp, c1[0], col2);
					}
				}
			}
		}
	}
};

class MtimesSparseMatrix2 implements MtimesCalculation<Matrix, SparseMatrix, Matrix> {

	public final void calc(final Matrix source1, final SparseMatrix source2, final Matrix target) {
		VerifyUtil.assert2D(source1);
		VerifyUtil.assert2D(source2);
		VerifyUtil.assert2D(target);
		VerifyUtil.assertEquals(source1.getColumnCount(), source2.getRowCount(),
				"matrices have wrong sizes");
		VerifyUtil.assertEquals(target.getRowCount(), source1.getRowCount(),
				"matrices have wrong sizes");
		VerifyUtil.assertEquals(target.getColumnCount(), source2.getColumnCount(),
				"matrices have wrong sizes");
		target.clear();
		for (long[] c2 : source2.availableCoordinates()) {
			final double v2 = source2.getAsDouble(c2);
			if (v2 != 0.0d) {
				for (long row1 = source1.getRowCount(); --row1 != -1;) {
					final double v1 = source1.getAsDouble(row1, c2[0]);
					final double temp = v1 * v2;
					if (temp != 0.0d) {
						final double v3 = target.getAsDouble(row1, c2[1]);
						target.setAsDouble(v3 + temp, row1, c2[1]);
					}
				}
			}
		}
	}
};

class MtimesDenseMatrix2D implements MtimesCalculation<DenseMatrix2D, DenseMatrix2D, DenseMatrix2D> {

	public final void calc(final DenseMatrix2D source1, final DenseMatrix2D source2,
			final DenseMatrix2D target) {
		if (source1 instanceof DenseDoubleMatrix2D && source2 instanceof DenseDoubleMatrix2D
				&& target instanceof DenseDoubleMatrix2D) {
			Mtimes.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source1,
					(DenseDoubleMatrix2D) source2, (DenseDoubleMatrix2D) target);
		} else {
			gemm(source1, source2, target);
		}
	}

	private final void gemm(final DenseMatrix2D A, final DenseMatrix2D B, final DenseMatrix2D C) {
		VerifyUtil.assert2D(A);
		VerifyUtil.assert2D(B);
		VerifyUtil.assert2D(C);
		final int m1RowCount = (int) A.getRowCount();
		final int m1ColumnCount = (int) A.getColumnCount();
		final int m2RowCount = (int) B.getRowCount();
		final int m2ColumnCount = (int) B.getColumnCount();
		VerifyUtil.assertEquals(m1ColumnCount, m2RowCount, "matrices have wrong size");
		VerifyUtil.assertEquals(m1RowCount, C.getRowCount(), "matrices have wrong size");
		VerifyUtil.assertEquals(m2ColumnCount, C.getColumnCount(), "matrices have wrong size");

		if (m1RowCount >= Mtimes.THRESHOLD && m1ColumnCount >= Mtimes.THRESHOLD
				&& m2ColumnCount >= Mtimes.THRESHOLD) {
			new PFor(0, m2ColumnCount - 1) {

				@Override
				public void step(int i) {
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C.setAsDouble(0.0d, irow, i);
					}
					for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
						final double temp = B.getAsDouble(lcol, i);
						if (temp != 0.0d) {
							for (int irow = 0; irow < m1RowCount; ++irow) {
								C.setAsDouble(C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol)
										* temp, irow, i);
							}
						}
					}
				}
			};
		} else {
			for (int i = 0; i < m2ColumnCount; i++) {
				for (int irow = 0; irow < m1RowCount; ++irow) {
					C.setAsDouble(0.0d, irow, i);
				}
				for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
					final double temp = B.getAsDouble(lcol, i);
					if (temp != 0.0d) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setAsDouble(
									C.getAsDouble(irow, i) + A.getAsDouble(irow, lcol) * temp,
									irow, i);
						}
					}
				}
			}
		}
	}
};

/**
 * Contains matrix multiplication methods for different matrix implementations
 * 
 * @author Holger Arndt
 * @author Frode Carlsen
 * 
 */
class MtimesDenseDoubleMatrix2D implements
		MtimesCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> {

	public final void calc(final DenseDoubleMatrix2D source1, final DenseDoubleMatrix2D source2,
			final DenseDoubleMatrix2D target) {
		assertTrue(source1 != null, "a == null");
		assertTrue(source2 != null, "b == null");
		assertTrue(target != null, "c == null");
		assertTrue(source1.getColumnCount() == source2.getRowCount(), "a.cols!=b.rows");
		assertTrue(source1.getRowCount() == target.getRowCount(), "a.rows!=c.rows");
		assertTrue(source2.getColumnCount() == target.getColumnCount(), "a.cols!=c.cols");
		if (source1.getRowCount() >= Mtimes.THRESHOLD
				&& source1.getColumnCount() >= Mtimes.THRESHOLD
				&& source2.getColumnCount() >= Mtimes.THRESHOLD) {
			if (Mtimes.MTIMES_JBLAS != null && UJMPSettings.isUseJBlas()) {
				Mtimes.MTIMES_JBLAS.calc((DenseDoubleMatrix2D) source1,
						(DenseDoubleMatrix2D) source2, (DenseDoubleMatrix2D) target);
			} else if (UJMPSettings.isUseBlockMatrixMultiply()) {
				calcBlockMatrixMultiThreaded(source1, source2, target);
			} else if (source1 instanceof HasColumnMajorDoubleArray1D
					&& source2 instanceof HasColumnMajorDoubleArray1D
					&& target instanceof HasColumnMajorDoubleArray1D) {
				calcDoubleArrayMultiThreaded(((HasColumnMajorDoubleArray1D) source1)
						.getColumnMajorDoubleArray1D(), (int) source1.getRowCount(), (int) source1
						.getColumnCount(), ((HasColumnMajorDoubleArray1D) source2)
						.getColumnMajorDoubleArray1D(), (int) source2.getRowCount(), (int) source2
						.getColumnCount(), ((HasColumnMajorDoubleArray1D) target)
						.getColumnMajorDoubleArray1D());
			} else if (source1 instanceof HasRowMajorDoubleArray2D
					&& source2 instanceof HasRowMajorDoubleArray2D
					&& target instanceof HasRowMajorDoubleArray2D) {
				calcDoubleArray2DMultiThreaded(((HasRowMajorDoubleArray2D) source1)
						.getRowMajorDoubleArray2D(), ((HasRowMajorDoubleArray2D) source2)
						.getRowMajorDoubleArray2D(), ((HasRowMajorDoubleArray2D) target)
						.getRowMajorDoubleArray2D());
			} else {
				calcDenseDoubleMatrix2DMultiThreaded(source1, source2, target);
			}
		} else {
			if (source1 instanceof HasColumnMajorDoubleArray1D
					&& source2 instanceof HasColumnMajorDoubleArray1D
					&& target instanceof HasColumnMajorDoubleArray1D) {
				gemmDoubleArraySingleThreaded(((HasColumnMajorDoubleArray1D) source1)
						.getColumnMajorDoubleArray1D(), (int) source1.getRowCount(), (int) source1
						.getColumnCount(), ((HasColumnMajorDoubleArray1D) source2)
						.getColumnMajorDoubleArray1D(), (int) source2.getRowCount(), (int) source2
						.getColumnCount(), ((HasColumnMajorDoubleArray1D) target)
						.getColumnMajorDoubleArray1D());
			} else if (source1 instanceof HasRowMajorDoubleArray2D
					&& source2 instanceof HasRowMajorDoubleArray2D
					&& target instanceof HasRowMajorDoubleArray2D) {
				calcDoubleArray2DSingleThreaded(((HasRowMajorDoubleArray2D) source1)
						.getRowMajorDoubleArray2D(), ((HasRowMajorDoubleArray2D) source2)
						.getRowMajorDoubleArray2D(), ((HasRowMajorDoubleArray2D) target)
						.getRowMajorDoubleArray2D());
			} else {
				calcDenseDoubleMatrix2DSingleThreaded(source1, source2, target);
			}
		}
	}

	private void calcBlockMatrixMultiThreaded(DenseDoubleMatrix2D source1,
			DenseDoubleMatrix2D source2, DenseDoubleMatrix2D target) {
		BlockDenseDoubleMatrix2D a = null;
		BlockDenseDoubleMatrix2D b = null;
		BlockDenseDoubleMatrix2D c = null;
		if (source1 instanceof BlockDenseDoubleMatrix2D) {
			a = (BlockDenseDoubleMatrix2D) source1;
		} else {
			a = new BlockDenseDoubleMatrix2D(source1);
		}
		if (source2 instanceof BlockDenseDoubleMatrix2D
				&& a.getBlockStripeSize() == ((BlockDenseDoubleMatrix2D) source2)
						.getBlockStripeSize()) {
			b = (BlockDenseDoubleMatrix2D) source2;
		} else {
			b = new BlockDenseDoubleMatrix2D(source2, a.getBlockStripeSize(),
					BlockOrder.COLUMNMAJOR);
		}
		final int arows = (int) a.getRowCount();
		final int bcols = (int) b.getColumnCount();
		if (target instanceof BlockDenseDoubleMatrix2D
				&& a.getBlockStripeSize() == ((BlockDenseDoubleMatrix2D) target)
						.getBlockStripeSize()) {
			c = (BlockDenseDoubleMatrix2D) target;
		} else {
			c = new BlockDenseDoubleMatrix2D(arows, bcols, a.getBlockStripeSize(),
					BlockOrder.ROWMAJOR);
		}

		// force optimal block order
		BlockOrder prevA = a.setBlockOrder(BlockOrder.ROWMAJOR);
		BlockOrder prevB = b.setBlockOrder(BlockOrder.COLUMNMAJOR);

		blockMultiplyMultiThreaded(a, b, c);

		if (c != target) {
			for (int j = bcols; --j != -1;) {
				for (int i = arows; --i != -1;) {
					target.setDouble(c.getDouble(i, j), i, j);
				}
			}
		}

		// reset block order
		if (Mtimes.RESET_BLOCK_ORDER) {
			a.setBlockOrder(prevA);
			b.setBlockOrder(prevB);
		}
	}

	private final void gemmDoubleArraySingleThreaded(final double[] A, final int m1RowCount,
			final int m1ColumnCount, final double[] B, final int m2RowCount,
			final int m2ColumnCount, final double[] C) {

		for (int j = 0; j < m2ColumnCount; j++) {
			final int jcolTimesM1RowCount = j * m1RowCount;
			final int jcolTimesM1ColumnCount = j * m1ColumnCount;
			Arrays.fill(C, jcolTimesM1RowCount, jcolTimesM1RowCount + m1RowCount, 0.0d);
			for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
				final double temp = B[lcol + jcolTimesM1ColumnCount];
				if (temp != 0.0d) {
					final int lcolTimesM1RowCount = lcol * m1RowCount;
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C[irow + jcolTimesM1RowCount] += A[irow + lcolTimesM1RowCount] * temp;
					}
				}
			}
		}
	}

	private final void calcDoubleArrayMultiThreaded(final double[] A, final int m1RowCount,
			final int m1ColumnCount, final double[] B, final int m2RowCount,
			final int m2ColumnCount, final double[] C) {
		new PFor(0, m2ColumnCount - 1) {
			@Override
			public void step(int i) {
				final int jcolTimesM1RowCount = i * m1RowCount;
				final int jcolTimesM1ColumnCount = i * m1ColumnCount;
				Arrays.fill(C, jcolTimesM1RowCount, jcolTimesM1RowCount + m1RowCount, 0.0d);
				for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
					final double temp = B[lcol + jcolTimesM1ColumnCount];
					if (temp != 0.0d) {
						final int lcolTimesM1RowCount = lcol * m1RowCount;
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C[irow + jcolTimesM1RowCount] += A[irow + lcolTimesM1RowCount] * temp;
						}
					}
				}
			}
		};
	}

	private final void calcDoubleArray2DSingleThreaded(final double[][] m1, final double[][] m2,
			final double[][] ret) {
		final int columnCount = m1[0].length;
		final double[] columns = new double[columnCount];

		for (int c = m2[0].length; --c != -1;) {
			for (int k = columnCount; --k != -1;) {
				columns[k] = m2[k][c];
			}
			for (int r = m1.length; --r != -1;) {
				double sum = 0.0d;
				final double[] row = m1[r];
				for (int k = columnCount; --k != -1;) {
					sum += row[k] * columns[k];
				}
				ret[r][c] = sum;
			}
		}
	}

	private final void calcDoubleArray2DMultiThreaded(final double[][] m1, final double[][] m2,
			final double[][] ret) {
		final int columnCount = m1[0].length;
		final double[] columns = new double[columnCount];

		new PFor(0, m2[0].length - 1) {
			@Override
			public void step(int i) {
				for (int k = columnCount; --k != -1;) {
					columns[k] = m2[k][i];
				}
				for (int r = m1.length; --r != -1;) {
					double sum = 0.0d;
					final double[] row = m1[r];
					for (int k = columnCount; --k != -1;) {
						sum += row[k] * columns[k];
					}
					ret[r][i] = sum;
				}
			}
		};
	}

	private final void calcDenseDoubleMatrix2DSingleThreaded(final DenseDoubleMatrix2D A,
			final DenseDoubleMatrix2D B, final DenseDoubleMatrix2D C) {
		final int m1RowCount = (int) A.getRowCount();
		final int m1ColumnCount = (int) A.getColumnCount();
		final int m2ColumnCount = (int) B.getColumnCount();

		for (int i = 0; i < m2ColumnCount; i++) {
			for (int irow = 0; irow < m1RowCount; ++irow) {
				C.setDouble(0.0d, irow, i);
			}
			for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
				final double temp = B.getDouble(lcol, i);
				if (temp != 0.0d) {
					for (int irow = 0; irow < m1RowCount; ++irow) {
						C.setDouble(C.getDouble(irow, i) + A.getDouble(irow, lcol) * temp, irow, i);
					}
				}
			}
		}
	}

	private final void calcDenseDoubleMatrix2DMultiThreaded(final DenseDoubleMatrix2D A,
			final DenseDoubleMatrix2D B, final DenseDoubleMatrix2D C) {
		final int m1RowCount = (int) A.getRowCount();
		final int m1ColumnCount = (int) A.getColumnCount();
		final int m2ColumnCount = (int) B.getColumnCount();

		new PFor(0, m2ColumnCount - 1) {
			@Override
			public void step(int i) {
				for (int irow = 0; irow < m1RowCount; ++irow) {
					C.setDouble(0.0d, irow, i);
				}
				for (int lcol = 0; lcol < m1ColumnCount; ++lcol) {
					final double temp = B.getDouble(lcol, i);
					if (temp != 0.0d) {
						for (int irow = 0; irow < m1RowCount; ++irow) {
							C.setDouble(C.getDouble(irow, i) + A.getDouble(irow, lcol) * temp,
									irow, i);
						}
					}
				}
			}
		};
	}

	/**
	 * Multiply two matrices concurrently with the given Executor to handle
	 * parallel tasks.
	 * 
	 * @param b
	 *            - matrix to multiply this with.
	 * @param executorService
	 *            - to handle concurrent multiplication tasks.
	 * @return new matrix C containing result of matrix multiplication C = A x
	 *         B.
	 */
	/**
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	private BlockDenseDoubleMatrix2D blockMultiplyMultiThreaded(final BlockDenseDoubleMatrix2D a,
			final BlockDenseDoubleMatrix2D b, final BlockDenseDoubleMatrix2D c) {
		final BlockMatrixLayout al = a.getBlockLayout();
		final BlockMatrixLayout bl = b.getBlockLayout();
		assertTrue(al.columns == bl.rows, "b.rows != this.columns");
		assertTrue(al.blockStripe == bl.blockStripe, "block sizes differ: %s != %s",
				al.blockStripe, bl.blockStripe);

		final List<Callable<Void>> tasks = new LinkedList<Callable<Void>>();

		final int kMax = (int) b.getColumnCount();
		final int jMax = (int) a.getColumnCount();
		final int iMax = (int) a.getRowCount();

		final int bColSlice = Math.min(al.blockStripe, kMax);
		final int aColSlice = Math.min(al.blockStripe, jMax);
		final int aRowSlice = Math.min(al.blockStripe, iMax);

		// Number of blocks to take for each concurrent task.
		final int blocksPerTask = 1;
		final int blocksPerTaskDimJ = selectBlocksPerTaskDimJ(al.blockStripe, iMax, jMax, kMax);

		for (int k = 0, kStride; k < kMax; k += kStride) {
			kStride = Math.min(blocksPerTask * bColSlice, kMax - k);

			for (int j = 0, jStride; j < jMax; j += jStride) {
				jStride = Math.min(blocksPerTaskDimJ * aColSlice, jMax - j);

				for (int i = 0, iStride; i < iMax; i += iStride) {
					iStride = Math.min(blocksPerTask * aRowSlice, iMax - i);

					tasks.add(new BlockMultiply(a, b, c, i, (i + iStride), j, (j + jStride), k,
							(k + kStride)));
				}

			}
		}

		// wait for all tasks to complete.
		try {
			for (Future<Void> f : UJMPThreadPoolExecutor.getInstance().invokeAll(tasks)) {
				f.get();
			}
		} catch (ExecutionException e) {
			StringBuilder sb = new StringBuilder(
					"Execution exception - while awaiting completion of matrix multiplication ["
							+ e.getMessage() + "]:");
			if (e.getCause() != null) {
				for (StackTraceElement stackTraceElement : e.getCause().getStackTrace()) {
					sb.append(stackTraceElement).append("  *  ");
				}
			}
			throw new RuntimeException(sb.toString(), e.getCause());
		} catch (final InterruptedException e) {
			String msg = "Interrupted - while awaiting completion of matrix multiplication.";
			throw new RuntimeException(msg + ": cause [" + e.getMessage() + "]", e);
		}

		return c;
	}

	// pick a suitable number of blocks to process per task for dimension J
	// - if too small , then incurs extra gc and contention for synchronization
	// - if set too large, then may not fully exploit parallelism
	private int selectBlocksPerTaskDimJ(int blockStripe, int iMax, int jMax, int kMax) {
		int adjust = (jMax % blockStripe > 0) ? 1 : 0;
		if (jMax < (5 * blockStripe) || jMax <= iMax) {
			// do not break this dimension into parallel tasks
			return jMax / blockStripe + adjust;
		} else {
			// assume 2 parallell tasks
			return Math.max(1, (jMax / blockStripe + adjust) / 2);
		}
		// may need something if jMax >>> iMax
	}
};
