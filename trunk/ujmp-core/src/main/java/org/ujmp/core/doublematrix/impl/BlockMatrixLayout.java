/*
 * Copyright (C) 2010 by Frode Carlsen
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
package org.ujmp.core.doublematrix.impl;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class describes the layout (size, order) of a square block of data
 * within a {@link BlockDenseDoubleMatrix2D block matrix}.
 * 
 * @author Frode Carlsen, Holger Arndt
 */
public final class BlockMatrixLayout implements Serializable {
	private static final long serialVersionUID = 2726685238884065594L;

	/** Enum describing the layout of a block of data. */
	public enum BlockOrder {
		ROWMAJOR, COLUMNMAJOR;

		public BlockOrder transpose() {
			return (this == ROWMAJOR) ? COLUMNMAJOR : ROWMAJOR;
		}
	}

	/** Total size of a block (area). */
	protected final int blockArea;

	/** Length of one side (stripe) of a square block. */
	public final int blockStripe;

	/** Number of columns of matrix. */
	public final int columns;

	/**
	 * Whether this block is laid out in row-major (true) or column-major
	 * (false) order.
	 */
	public final BlockOrder blockOrder;

	/** number of blocks in this matrix */
	final int numberOfBlocks;

	/** @see #blockOrder */
	private final boolean rowMajor;

	/** Number of rows of matrix. */
	public final int rows;

	/** threshold for when to stop using square blocks. */
	private final int sqbColThreshold, sqbRowThreshold;

	BlockMatrixLayout(final int rows, final int columns, final int blockStripe,
			final BlockOrder blockOrder) {

		this.blockStripe = blockStripe;

		if (rows <= 0 || columns <= 0 || blockStripe <= 0) {
			throw new IllegalArgumentException(String.format(
					"One or more invalid values: rows=%s, columns=%s, blockSize=%s", rows, columns,
					blockStripe));
		}

		this.blockArea = blockStripe * blockStripe;
		this.rows = rows;
		this.columns = columns;
		this.sqbColThreshold = (columns / blockStripe) * blockStripe;
		this.sqbRowThreshold = (rows / blockStripe) * blockStripe;
		this.blockOrder = blockOrder;
		this.rowMajor = (blockOrder == BlockOrder.ROWMAJOR);
		this.numberOfBlocks = (rows / blockStripe + (rows % blockStripe > 0 ? 1 : 0))
				* (columns / blockStripe + (columns % blockStripe > 0 ? 1 : 0));
	}

	/**
	 * Get the block which contains the specified row, column
	 * 
	 * @param matrix
	 *            to get block from
	 * @param row
	 * @param column
	 * @return block containing given row, column
	 */
	final double[] getBlock(BlockDenseDoubleMatrix2D matrix, int row, int column) {
		return matrix.getBlockData(row, column);
	}

	final int getBlockIndexByColumn(final int lrow, final int lcol, final int numRows,
			final int numCols) {
		return rowMajor ? (lcol * numRows + lrow) : (lrow * numCols + lcol);
	}

	final int getBlockIndexByRow(final int lrow, final int lcol, final int numRows,
			final int numCols) {
		return rowMajor ? (lrow * numCols + lcol) : (lcol * numRows + lrow);
	}

	final int getBlockNumber(int row, int col) {
		return (col / blockStripe) + (row / blockStripe)
				* (columns / blockStripe + (columns % blockStripe > 0 ? 1 : 0));
	}

	final int getIndexInBlock(int row, int col) {
		int lrows = getRowsInBlock(row);
		int lcols = getColumnsInBlock(col);
		return getBlockIndexByRow(row % blockStripe, col % blockStripe, lrows, lcols);
	}

	final int getBlockSize(int row, int col) {
		int lrows = getRowsInBlock(row);
		int lcols = getColumnsInBlock(col);
		return lrows * lcols;
	}

	final double[] toColMajorBlock(BlockDenseDoubleMatrix2D matrix, final int rowStart, int colStart) {
		double[] block = getBlock(matrix, rowStart, colStart);
		if (!rowMajor) {
			return block;
		}
		return toColMajorBlock(block, rowStart, colStart);
	}

	final double[] toColMajorBlock(double[] block, final int rowStart, int colStart) {

		final double[] targetBlock = new double[block.length];
		final int lrows = getRowsInBlock(rowStart);
		final int lcols = getColumnsInBlock(colStart);

		// transpose block, swap cols and rows
		for (int i = 0; i < lcols; i++) {
			final int ilrows = i * lrows;
			for (int j = 0; j < lrows; j++) {
				targetBlock[ilrows + j] = block[j * lcols + i];
			}
		}

		return targetBlock;
	}

	int getColumnsInBlock(int col) {
		return (col >= sqbColThreshold) ? this.columns - this.sqbColThreshold : blockStripe;
	}

	int getRowsInBlock(final int row) {
		return (row >= sqbRowThreshold) ? this.rows - this.sqbRowThreshold : blockStripe;
	}

	final double[] toRowMajorBlock(final BlockDenseDoubleMatrix2D matrix, final int rowStart,
			int colStart) {
		final double[] block = getBlock(matrix, rowStart, colStart);
		if (rowMajor) {
			return block;
		}
		return toRowMajorBlock(block, rowStart, colStart);
	}

	final double[] toRowMajorBlock(final double[] block, final int rowStart, int colStart) {

		final double[] targetBlock = new double[block.length];
		final int lrows = getRowsInBlock(rowStart);
		final int lcols = getColumnsInBlock(colStart);

		// transpose block
		for (int i = 0; i < lrows; i++) {
			final int ilcols = i * lcols;
			for (int j = 0; j < lcols; j++) {
				targetBlock[ilcols + j] = block[j * lrows + i];
			}
		}

		return targetBlock;
	}

	@Override
	public String toString() {
		int[] rowLayout = new int[blockStripe];
		StringBuilder b = new StringBuilder(blockArea * 4 + 40);
		String msg = "\n(rows=%s, columns=%s, blockSize=%s):\n";
		b.append(String.format(msg, rows, columns, blockStripe));
		int rows = blockStripe, cols = blockStripe;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				rowLayout[j] = getBlockIndexByRow(i, j, rows, cols);
			}
			b.append(Arrays.toString(rowLayout)).append("\n");
		}

		return b.toString();
	}
}
