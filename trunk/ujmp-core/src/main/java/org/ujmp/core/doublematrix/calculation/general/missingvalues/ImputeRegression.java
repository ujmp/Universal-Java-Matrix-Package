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

package org.ujmp.core.doublematrix.calculation.general.missingvalues;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute.ImputationMethod;
import org.ujmp.core.util.MathUtil;

public class ImputeRegression extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 2147234720707721364L;

	Matrix firstGuess = null;

	Matrix imputed = null;

	public ImputeRegression(Matrix matrix) {
		super(matrix);
	}

	public ImputeRegression(Matrix matrix, Matrix firstGuess) {
		super(matrix);
		this.firstGuess = firstGuess;
	}

	public synchronized double getDouble(long... coordinates) {
		if (imputed == null) {
			createMatrix();
		}
		double v = getSource().getAsDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			return imputed.getAsDouble(coordinates);
		} else {
			return v;
		}
	}

	private synchronized void createMatrix() {
		try {
			Matrix x = getSource();

			if (firstGuess == null) {
				firstGuess = getSource().impute(Ret.NEW, ImputationMethod.ColumnMean);
			}

			imputed = Matrix.Factory.zeros(x.getSize());

			ExecutorService executor = Executors.newFixedThreadPool(1);
			List<Future<Long>> futures = new ArrayList<Future<Long>>();

			long t0 = System.currentTimeMillis();

			for (long c = 0; c < x.getColumnCount(); c++) {
				if (containsMissingValues(c)) {
					futures.add(executor.submit(new PredictColumn(c)));
				}
			}

			for (Future<Long> f : futures) {
				Long completedCols = f.get();
				long elapsedTime = System.currentTimeMillis() - t0;
				long remainingCols = x.getColumnCount() - completedCols;
				double colsPerMillisecond = (double) (completedCols + 1) / (double) elapsedTime;
				long remainingTime = (long) (remainingCols / colsPerMillisecond / 1000.0);
				System.out.println((completedCols * 1000 / x.getColumnCount() / 10.0)
						+ "% completed (" + remainingTime + " seconds remaining)");
			}

			executor.shutdown();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean containsMissingValues(long c) {
		for (int r = 0; r < getSource().getRowCount(); r++) {
			if (MathUtil.isNaNOrInfinite(getSource().getAsDouble(r, c))) {
				return true;
			}
		}
		return false;
	}

	class PredictColumn implements Callable<Long> {

		long column = 0;

		public PredictColumn(long column) {
			this.column = column;
		}

		public Long call() throws Exception {
			Matrix newColumn = replaceInColumn(getSource(), firstGuess, column);
			for (int r = 0; r < newColumn.getRowCount(); r++) {
				imputed.setAsDouble(newColumn.getAsDouble(r, 0), r, column);
			}
			return column;
		}

	}

	private static Matrix replaceInColumn(Matrix original, Matrix firstGuess, long column) {

		Matrix x = firstGuess.deleteColumns(Ret.NEW, column);
		Matrix y = original.selectColumns(Ret.NEW, column);

		List<Long> missingRows = new ArrayList<Long>();
		for (long i = y.getRowCount(); --i >= 0;) {
			double v = y.getAsDouble(i, 0);
			if (MathUtil.isNaNOrInfinite(v)) {
				missingRows.add(i);
			}
		}

		if (missingRows.isEmpty()) {
			return y;
		}

		Matrix xdel = x.deleteRows(Ret.NEW, missingRows);
		Matrix bias1 = DenseDoubleMatrix2D.Factory.ones(xdel.getRowCount(), 1);
		Matrix xtrain = Matrix.Factory.horCat(xdel, bias1);
		Matrix ytrain = y.deleteRows(Ret.NEW, missingRows);

		Matrix xinv = xtrain.pinv();
		Matrix b = xinv.mtimes(ytrain);
		Matrix bias2 = DenseDoubleMatrix2D.Factory.ones(x.getRowCount(), 1);
		Matrix yPredicted = Matrix.Factory.horCat(x, bias2).mtimes(b);

		// set non-missing values back to original values
		for (int row = 0; row < y.getRowCount(); row++) {
			double v = y.getAsDouble(row, 0);
			if (!Double.isNaN(v)) {
				yPredicted.setAsDouble(v, row, 0);
			}
		}
		
		if (yPredicted.containsMissingValues()) {
			throw new RuntimeException("imputation did not work");
		}

		return yPredicted;
	}

}
