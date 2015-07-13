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

package org.ujmp.core.implementations;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.ujmp.core.stringmatrix.impl.TestDefaultDenseStringMatrix2D;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestDenseFileMatrix.class, TestArrayDenseDoubleMatrix2D.class,
		TestDefaultDenseDoubleMatrix2D.class, TestDefaultDenseObjectMatrix2D.class,
		TestMortonDenseDoubleMatrix2D.class, TestDefaultSparseColumnObjectMatrix2D.class,
		TestDefaultSparseRowObjectMatrix2D.class, TestDefaultDenseStringMatrix2D.class,
		TestDefaultDenseDoubleMatrixMultiD.class, TestDefaultTiledObjectMatrix2D.class,
		TestDefaultSparseDoubleMatrix.class, TestDefaultSparseRowDoubleMatrix2D.class })
public class AllTests {
}
