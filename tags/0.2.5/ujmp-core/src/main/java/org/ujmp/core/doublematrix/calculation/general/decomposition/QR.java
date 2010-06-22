package org.ujmp.core.doublematrix.calculation.general.decomposition;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.DecompositionOps;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPSettings;

/**
 * QR Decomposition.
 * <P>
 * For an m-by-n matrix A with m >= n, the QR decomposition is an m-by-n
 * orthogonal matrix Q and an n-by-n upper triangular matrix R so that A = Q*R.
 * <P>
 * The QR decompostion always exists, even if the matrix does not have full
 * rank, so the constructor will never fail. The primary use of the QR
 * decomposition is in the least squares solution of nonsquare systems of
 * simultaneous linear equations. This will fail if isFullRank() returns false.
 */

public interface QR<T> {

	public static int THRESHOLD = 100;

	public T[] calc(T source);

	public T solve(T source, T b);

	public static final QR<Matrix> MATRIX = new QR<Matrix>() {

		public final Matrix[] calc(Matrix source) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGESINGLETHREADED.calc(source);
				} else {
					return MATRIXSMALLSINGLETHREADED.calc(source);
				}
			} else {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGEMULTITHREADED.calc(source);
				} else {
					return MATRIXSMALLMULTITHREADED.calc(source);
				}
			}
		}

		public final Matrix solve(Matrix source, Matrix b) {
			if (UJMPSettings.getNumberOfThreads() == 1) {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGESINGLETHREADED.solve(source, b);
				} else {
					return MATRIXSMALLSINGLETHREADED.solve(source, b);
				}
			} else {
				if (source.getRowCount() >= THRESHOLD && source.getColumnCount() >= THRESHOLD) {
					return MATRIXLARGEMULTITHREADED.solve(source, b);
				} else {
					return MATRIXSMALLMULTITHREADED.solve(source, b);
				}
			}
		}
	};

	public static final QR<Matrix> MATRIXLARGESINGLETHREADED = new QR<Matrix>() {
		public final Matrix[] calc(Matrix source) {
			QR<Matrix> qr = null;
			if (UJMPSettings.isUseOjalgo()) {
				qr = DecompositionOps.QR_OJALGO;
			}
			if (qr == null && UJMPSettings.isUseEJML()) {
				qr = DecompositionOps.QR_EJML;
			}
			if (qr == null && UJMPSettings.isUseMTJ()) {
				qr = DecompositionOps.QR_MTJ;
			}
			if (qr == null) {
				qr = UJMP;
			}
			return qr.calc(source);
		}

		public final Matrix solve(Matrix source, Matrix b) {
			QR<Matrix> qr = null;
			if (UJMPSettings.isUseOjalgo()) {
				qr = DecompositionOps.QR_OJALGO;
			}
			if (qr == null && UJMPSettings.isUseEJML()) {
				qr = DecompositionOps.QR_EJML;
			}
			if (qr == null && UJMPSettings.isUseMTJ()) {
				qr = DecompositionOps.QR_MTJ;
			}
			if (qr == null) {
				qr = UJMP;
			}
			return qr.solve(source, b);
		}
	};

	public static final QR<Matrix> MATRIXLARGEMULTITHREADED = new QR<Matrix>() {
		public final Matrix[] calc(Matrix source) {
			QR<Matrix> qr = null;
			if (UJMPSettings.isUseOjalgo()) {
				qr = DecompositionOps.QR_OJALGO;
			}
			if (qr == null && UJMPSettings.isUseEJML()) {
				qr = DecompositionOps.QR_EJML;
			}
			if (qr == null && UJMPSettings.isUseMTJ()) {
				qr = DecompositionOps.QR_MTJ;
			}
			if (qr == null) {
				qr = UJMP;
			}
			return qr.calc(source);
		}

		public final Matrix solve(Matrix source, Matrix b) {
			QR<Matrix> qr = null;
			if (UJMPSettings.isUseOjalgo()) {
				qr = DecompositionOps.QR_OJALGO;
			}
			if (qr == null && UJMPSettings.isUseEJML()) {
				qr = DecompositionOps.QR_EJML;
			}
			if (qr == null && UJMPSettings.isUseMTJ()) {
				qr = DecompositionOps.QR_MTJ;
			}
			if (qr == null) {
				qr = UJMP;
			}
			return qr.solve(source, b);
		}
	};

	public static final QR<Matrix> INSTANCE = MATRIX;

	public static final QR<Matrix> UJMP = new QR<Matrix>() {

		public final Matrix[] calc(Matrix source) {
			if (source.getRowCount() >= source.getColumnCount()) {
				QRMatrix qr = new QRMatrix(source);
				return new Matrix[] { qr.getQ(), qr.getR() };
			} else {
				throw new MatrixException("only matrices m>=n are allowed");
			}
		}

		public final Matrix solve(Matrix source, Matrix b) {
			if (source.getRowCount() >= source.getColumnCount()) {
				QRMatrix qr = new QRMatrix(source);
				return qr.solve(b);
			} else {
				throw new MatrixException("only matrices m>=n are allowed");
			}
		}
	};

	public static final QR<Matrix> MATRIXSMALLMULTITHREADED = UJMP;

	public static final QR<Matrix> MATRIXSMALLSINGLETHREADED = UJMP;

	public class QRMatrix {
		private static final long serialVersionUID = 2137461328307048867L;

		/**
		 * Array for internal storage of decomposition.
		 * 
		 * @serial internal array storage.
		 */
		private final double[][] QR;

		/**
		 * Row and column dimensions.
		 * 
		 * @serial column dimension.
		 * @serial row dimension.
		 */
		private final int m, n;

		/**
		 * Array for internal storage of diagonal of R.
		 * 
		 * @serial diagonal of R.
		 */
		private final double[] Rdiag;

		/*
		 * ------------------------ Constructor ------------------------
		 */

		/**
		 * QR Decomposition, computed by Householder reflections.
		 * 
		 * @param A
		 *            Rectangular matrix
		 * @return Structure to access R and the Householder vectors and compute
		 *         Q.
		 */

		public QRMatrix(Matrix A) {
			QR = A.toDoubleArray();
			m = (int) A.getRowCount();
			n = (int) A.getColumnCount();
			Rdiag = new double[n];

			// Main loop.
			for (int k = 0; k < n; k++) {
				// Compute 2-norm of k-th column without under/overflow.
				double nrm = 0;
				for (int i = k; i < m; i++) {
					nrm = MathUtil.hypot(nrm, QR[i][k]);
				}

				if (nrm != 0.0) {
					// Form k-th Householder vector.
					if (QR[k][k] < 0) {
						nrm = -nrm;
					}
					for (int i = k; i < m; i++) {
						QR[i][k] /= nrm;
					}
					QR[k][k] += 1.0;

					// Apply transformation to remaining columns.
					for (int j = k + 1; j < n; j++) {
						double s = 0.0;
						for (int i = k; i < m; i++) {
							s += QR[i][k] * QR[i][j];
						}
						s = -s / QR[k][k];
						for (int i = k; i < m; i++) {
							QR[i][j] += s * QR[i][k];
						}
					}
				}
				Rdiag[k] = -nrm;
			}
		}

		/*
		 * ------------------------ Public Methods ------------------------
		 */

		/**
		 * Is the matrix full rank?
		 * 
		 * @return true if R, and hence A, has full rank.
		 */

		public final boolean isFullRank() {
			for (int j = 0; j < n; j++) {
				if (Rdiag[j] == 0)
					return false;
			}
			return true;
		}

		/**
		 * Return the Householder vectors
		 * 
		 * @return Lower trapezoidal matrix whose columns define the reflections
		 */

		public final Matrix getH() {
			final double[][] H = new double[m][n];
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (i >= j) {
						H[i][j] = QR[i][j];
					}
				}
			}
			return MatrixFactory.linkToArray(H);
		}

		/**
		 * Return the upper triangular factor
		 * 
		 * @return R
		 */

		public final Matrix getR() {
			final double[][] R = new double[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i < j) {
						R[i][j] = QR[i][j];
					} else if (i == j) {
						R[i][j] = Rdiag[i];
					} else {
						R[i][j] = 0.0;
					}
				}
			}
			return MatrixFactory.linkToArray(R);
		}

		/**
		 * Generate and return the (economy-sized) orthogonal factor
		 * 
		 * @return Q
		 */

		public final Matrix getQ() {
			final double[][] Q = new double[m][n];
			for (int k = n - 1; k >= 0; k--) {
				for (int i = 0; i < m; i++) {
					Q[i][k] = 0.0;
				}
				Q[k][k] = 1.0;
				for (int j = k; j < n; j++) {
					if (QR[k][k] != 0) {
						double s = 0.0;
						for (int i = k; i < m; i++) {
							s += QR[i][k] * Q[i][j];
						}
						s = -s / QR[k][k];
						for (int i = k; i < m; i++) {
							Q[i][j] += s * QR[i][k];
						}
					}
				}
			}
			return MatrixFactory.linkToArray(Q);
		}

		/**
		 * Least squares solution of A*X = B
		 * 
		 * @param B
		 *            A Matrix with as many rows as A and any number of columns.
		 * @return X that minimizes the two norm of Q*R*X-B.
		 * @exception IllegalArgumentException
		 *                Matrix row dimensions must agree.
		 * @exception RuntimeException
		 *                Matrix is rank deficient.
		 */

		public final Matrix solve(Matrix B) {
			if (B.getRowCount() != m) {
				throw new IllegalArgumentException("Matrix row dimensions must agree.");
			}
			if (!this.isFullRank()) {
				throw new RuntimeException("Matrix is rank deficient.");
			}

			// Copy right hand side
			final int nx = (int) B.getColumnCount();
			final double[][] X = B.toDoubleArray();

			// Compute Y = transpose(Q)*B
			for (int k = 0; k < n; k++) {
				for (int j = 0; j < nx; j++) {
					double s = 0.0;
					for (int i = k; i < m; i++) {
						s += QR[i][k] * X[i][j];
					}
					s = -s / QR[k][k];
					for (int i = k; i < m; i++) {
						X[i][j] += s * QR[i][k];
					}
				}
			}
			// Solve R*X = Y;
			for (int k = n - 1; k >= 0; k--) {
				for (int j = 0; j < nx; j++) {
					X[k][j] /= Rdiag[k];
				}
				for (int i = 0; i < k; i++) {
					for (int j = 0; j < nx; j++) {
						X[i][j] -= X[k][j] * QR[i][k];
					}
				}
			}
			return MatrixFactory.linkToArray(X).subMatrix(Ret.NEW, 0, 0, n - 1, nx - 1);
		}
	}
}
