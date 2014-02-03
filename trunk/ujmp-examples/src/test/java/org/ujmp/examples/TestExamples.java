/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.examples;

import org.junit.Test;

public class TestExamples {

	private static final String[] args = new String[0];

	@Test
	public void testExploreLocalhost() throws Exception {
		ExploreLocalhostExample.main(args);
	}

	@Test
	public void testExtractExcelData() throws Exception {
		ExtractExcelDataExample.main(args);
	}

	@Test
	public void testGraphExample() throws Exception {
		GraphMatrixExample.main(args);
	}

	@Test
	public void testHugeFileMatrixOnDisk() throws Exception {
		HugeFileMatrixOnDiskExample.main(args);
	}

	@Test
	public void testHugeSparseMatrix() throws Exception {
		HugeSparseMatrixExample.main(args);
	}

	@Test
	public void testLoadImage() throws Exception {
		ImageMatrixExample.main(args);
	}

	@Test
	public void testRandomMatrix() throws Exception {
		RandomMatrixExample.main(args);
	}

	@Test
	public void testSimilarityMatrix() throws Exception {
		SimilarityMatrixExample.main(args);
	}

	@Test
	public void testTreeMatrixExample() throws Exception {
		TreeMatrixExample.main(args);
	}

}