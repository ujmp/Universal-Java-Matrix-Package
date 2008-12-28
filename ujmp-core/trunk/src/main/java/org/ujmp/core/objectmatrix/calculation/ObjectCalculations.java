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

package org.ujmp.core.objectmatrix.calculation;

import java.util.Collection;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.AnnotationTransfer;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public interface ObjectCalculations {

	public Matrix distinct(Ret returnType) throws MatrixException;

	public Matrix sort(Ret returnType) throws MatrixException;

	public Matrix shuffle(Ret returnType) throws MatrixException;

	public Matrix bootstrap(Ret returnType) throws MatrixException;

	public Matrix bootstrap(Ret returnType, int count) throws MatrixException;

	public Matrix reshape(long... newSize) throws MatrixException;

	public Matrix toColumnVector() throws MatrixException;

	public Matrix toRowVector() throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to a desired
	 * value.
	 * 
	 * @param value
	 *            fill with this value
	 * @return Matrix with ones.
	 */
	public Matrix fill(Ret ret, Object value) throws MatrixException;

	/**
	 * Replaces matching values in the matrix with another value
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param search
	 *            Object to search for
	 * @param replacement
	 *            Object used to replace the original value
	 * @return matrix with modified entries
	 * @throws MatrixException
	 */
	public Matrix replace(Ret returnType, Object search, Object replacement) throws MatrixException;

	/**
	 * Returns the transpose of the Matrix, where rows and columns are
	 * exchanged. This works also if the Matrix has more than two dimensions.
	 * 
	 * @return transposed Matrix.
	 */
	public Matrix transpose() throws MatrixException;

	/**
	 * Creates a copy of the matrix. Annotation is linked by default.
	 * 
	 * @return Matrix with the same entries
	 * @see clone()
	 */
	public Matrix copy() throws MatrixException;

	/**
	 * Creates a copy of the matrix.
	 * 
	 * @param annotationTransfer
	 *            defines how Annotation should be treated
	 * @return Matrix with the same entries
	 * @see clone()
	 */
	public Matrix copy(AnnotationTransfer annotationTransfer) throws MatrixException;

	/**
	 * Creates a link to the matrix. All operations take effect in the linked
	 * and the original matrix simultaneously.
	 * 
	 * @return A Matrix linked to the original Matrix
	 */
	public Matrix link() throws MatrixException;

	/**
	 * Creates a copy of the matrix with the desired type for matrix entries.
	 * 
	 * @param newValueType
	 *            defines the new format of the matrix
	 * @return Matrix with the same entries in the new format
	 */
	public Matrix convert(ValueType newValueType) throws MatrixException;

	/**
	 * Creates a copy of the matrix with the desired type for matrix entries.
	 * 
	 * @param newValueType
	 *            defines the new format of the matrix
	 * @param annotationTransfer
	 *            defines how annotation should be treated
	 * @return Matrix with the same entries in the new format
	 */
	public Matrix convert(ValueType newValueType, AnnotationTransfer annotationTransfer)
			throws MatrixException;

	/**
	 * Selects rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns
	 * @throws MatrixException
	 */
	public Matrix select(Ret returnType, long[]... selection) throws MatrixException;

	/**
	 * Delete rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns
	 * @throws MatrixException
	 */
	public Matrix delete(Ret returnType, long[]... selection) throws MatrixException;

	/**
	 * Selects rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns
	 * @throws MatrixException
	 */
	public Matrix select(Ret returnType, Collection<? extends Number>... selection)
			throws MatrixException;

	/**
	 * Delete rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns
	 * @throws MatrixException
	 */
	public Matrix delete(Ret returnType, Collection<? extends Number>... selection)
			throws MatrixException;

	/**
	 * Selects rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows
	 * @throws MatrixException
	 */
	public Matrix selectRows(Ret returnType, long... rows) throws MatrixException;

	/**
	 * Deletes rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows
	 * @throws MatrixException
	 */
	public Matrix deleteRows(Ret returnType, long... rows) throws MatrixException;

	/**
	 * Selects rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows
	 * @throws MatrixException
	 */
	public Matrix selectRows(Ret returnType, Collection<? extends Number> rows)
			throws MatrixException;

	/**
	 * Deletes rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows
	 * @throws MatrixException
	 */
	public Matrix deleteRows(Ret returnType, Collection<? extends Number> rows)
			throws MatrixException;

	/**
	 * Selects columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns
	 * @throws MatrixException
	 */
	public Matrix selectColumns(Ret returnType, long... colums) throws MatrixException;

	/**
	 * Deletes columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns
	 * @throws MatrixException
	 */
	public Matrix deleteColumns(Ret returnType, long... colums) throws MatrixException;

	/**
	 * Selects columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns
	 * @throws MatrixException
	 */
	public Matrix selectColumns(Ret returnType, Collection<? extends Number> columns)
			throws MatrixException;

	/**
	 * Deletes columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns
	 * @throws MatrixException
	 */
	public Matrix deleteColumns(Ret returnType, Collection<? extends Number> columns)
			throws MatrixException;

	/**
	 * Selects rows and columns in the Matrix and returns a link to it.
	 * Selections can be made in Matlab/Octave style or similar, e.g.
	 * "1,2,5-6,8:5;*". Dimensions are separated by ';'. Selections in one
	 * dimension are separated by spaces or ','. Ranges are selected using '-'
	 * or ':'. A whole dimension can be selected with '*'.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            String defining the selection
	 * @return Link to original Matrix with desired rows and columns
	 * @throws MatrixException
	 */
	public Matrix select(Ret returnType, String selection) throws MatrixException;

	/**
	 * Deletes rows and columns in the Matrix and returns a link to it.
	 * Selections can be made in Matlab/Octave style or similar, e.g.
	 * "1,2,5-6,8:5;*". Dimensions are separated by ';'. Selections in one
	 * dimension are separated by spaces or ','. Ranges are selected using '-'
	 * or ':'. A whole dimension can be selected with '*'.
	 * 
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            String defining the selection
	 * @return Link to original Matrix with desired rows and columns deleted
	 * @throws MatrixException
	 */
	public Matrix delete(Ret returnType, String selection) throws MatrixException;

	public Matrix subMatrix(Ret returnType, long startRow, long startColumn, long endRow,
			long endColumn) throws MatrixException;

}