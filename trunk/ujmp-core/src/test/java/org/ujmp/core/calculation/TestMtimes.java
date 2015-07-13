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

package org.ujmp.core.calculation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class TestMtimes {

	@Test
	public void test1() {
		Matrix a = Matrix.Factory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 } });
		Matrix b = Matrix.Factory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 },
				{ 7, 8, 9 } });

		Matrix c_correct = a.mtimes(Ret.NEW, true, b);
		Matrix c1 = a.mtimes(b);

		assertEquals(c_correct, c1);
	}

	@Test
	public void test2() {
		Matrix a = Matrix.Factory.linkToArray(new double[][] { { 1, 2, 3 }, { 4, 5, 6 } });
		Matrix b = Matrix.Factory.linkToArray(new double[][] { { 1, 2 }, { 4, 5 }, { 7, 8 } });

		Matrix c_correct = a.mtimes(Ret.NEW, true, b);
		Matrix c1 = a.mtimes(b);

		assertEquals(c_correct, c1);
	}

	@Test
	public void test3() {
		Matrix a = Matrix.Factory.linkToArray(new double[][] { { 1, 2 }, { 4, 5 }, { 7, 8 } });
		Matrix b = Matrix.Factory.linkToArray(new double[][] { { 4, 5, 6 }, { 7, 8, 9 } });

		Matrix c_correct = a.mtimes(Ret.NEW, true, b);
		Matrix c1 = a.mtimes(b);

		assertEquals(c_correct, c1);
	}

}
