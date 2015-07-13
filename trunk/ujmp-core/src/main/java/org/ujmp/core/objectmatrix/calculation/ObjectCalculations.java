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

package org.ujmp.core.objectmatrix.calculation;

import java.util.Collection;

import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.ValueType;

public interface ObjectCalculations {

	public Matrix sortrows(Ret returnType, long column, boolean reverse);

	public Matrix shuffle(Ret returnType);

	public Matrix bootstrap(Ret returnType);

	public Matrix bootstrap(Ret returnType, int count);

	/**
	 * Generates annotation such as row or column labels from the content of
	 * this matrix. This is useful for example for data imported from CSV files
	 * with labels in the first line.
	 * 
	 * @param returnType
	 *            Specify whether to return a new matrix, or a link
	 * @param dimension
	 *            Which axis should be shortened to extract the labels. E.g. if
	 *            you want column labels, you must specify
	 *            <code>Matrix.ROW</code>, which does not seem very intuitive at
	 *            first glance. However, if you're dealing with
	 *            multi-dimensional data, this becomes more clear. If you want
	 *            annotation generated for all dimensions, you can specify
	 *            <code>Matrix.ALL</code> or omit the dimensions parameter.
	 * @return new Matrix with annotation generated from content. @
	 */
	public Matrix extractAnnotation(Ret returnType, int dimension);

	public Matrix includeAnnotation(Ret returnType, int dimension);

	public Matrix reshape(Ret returnType, long... newSize);

	public Matrix squeeze(Ret returnType);

	public Matrix unique(Ret returnType);

	public Matrix uniqueValueCount(Ret returnType, int dimension);

	public Matrix tril(Ret returnType, int k);

	public Matrix triu(Ret returnType, int k);

	public Matrix toColumnVector(Ret returnType);

	public Matrix toRowVector(Ret returnType);

	public Matrix swap(Ret returnType, int dimension, long pos1, long pos2);

	/**
	 * Returns a matrix with equal size, where all entries are set to a desired
	 * value.
	 * 
	 * @param value
	 *            fill with this value
	 * @return Matrix with ones.
	 */
	public Matrix fill(Ret ret, Object value);

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
	 * @return matrix with modified entries @
	 */
	public Matrix replace(Ret returnType, Object search, Object replacement);

	/**
	 * Returns the transpose of the Matrix, where rows and columns are
	 * exchanged. This works also if the Matrix has more than two dimensions.
	 * 
	 * @return transposed Matrix.
	 */
	public Matrix transpose();

	/**
	 * Returns the transpose of the Matrix, where rows and columns are
	 * exchanged. This works also if the Matrix has more than two dimensions.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @return transposed Matrix.
	 */
	public Matrix transpose(Ret returnType);

	public Matrix transpose(Ret returnType, int dimension1, int dimension2);

	public Matrix flipdim(Ret returnType, int dimension);

	public Matrix diag(Ret returnType);

	/**
	 * Creates a copy of the matrix with the desired type for matrix entries.
	 * 
	 * @param newValueType
	 *            defines the new format of the matrix
	 * @return Matrix with the same entries in the new format
	 */
	public Matrix convert(ValueType newValueType);

	/**
	 * Selects rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns @
	 */
	public Matrix select(Ret returnType, long[]... selection);

	/**
	 * Delete rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns @
	 */
	public Matrix delete(Ret returnType, long[]... selection);

	/**
	 * Selects rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns @
	 */
	public Matrix select(Ret returnType, Collection<? extends Number>... selection);

	/**
	 * Delete rows and columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param selection
	 *            selected rows and columns
	 * @return Link to original Matrix with desired rows and columns @
	 */
	public Matrix delete(Ret returnType, Collection<? extends Number>... selection);

	/**
	 * Selects rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows @
	 */
	public Matrix selectRows(Ret returnType, long... rows);

	/**
	 * Deletes rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows @
	 */
	public Matrix deleteRows(Ret returnType, long... rows);

	/**
	 * Selects rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows @
	 */
	public Matrix selectRows(Ret returnType, Collection<? extends Number> rows);

	/**
	 * Deletes rows in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param rows
	 *            selected rows
	 * @return Link to original Matrix with desired rows @
	 */
	public Matrix deleteRows(Ret returnType, Collection<? extends Number> rows);

	/**
	 * Selects columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns @
	 */
	public Matrix selectColumns(Ret returnType, long... colums);

	/**
	 * Deletes columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns @
	 */
	public Matrix deleteColumns(Ret returnType, long... colums);

	/**
	 * Selects columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns @
	 */
	public Matrix selectColumns(Ret returnType, Collection<? extends Number> columns);

	/**
	 * Deletes columns in the Matrix and returns a link to it.
	 * 
	 * @param returnType
	 *            Defines if a new Matrix or a link should be returned.
	 * @param columns
	 *            selected columns
	 * @return Link to original Matrix with desired columns @
	 */
	public Matrix deleteColumns(Ret returnType, Collection<? extends Number> columns);

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
	 * @return Link to original Matrix with desired rows and columns @
	 */
	public Matrix select(Ret returnType, String selection);

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
	 * @param minAndMaxCoordinates
	 *            start and end coordinates
	 * @return Link to original Matrix with desired rows and columns deleted @
	 */
	public Matrix delete(Ret returnType, String selection);

	public Matrix subMatrix(Ret returnType, long... minAndMaxCoordinates);

}
