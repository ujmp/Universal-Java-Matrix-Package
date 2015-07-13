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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.Impute.ImputationMethod;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.ImputeBPCA;
import org.ujmp.core.doublematrix.calculation.general.missingvalues.ImputeLS;

public class TestMissingValueImputation {

	@Test
	public void testImputeMissingValues() throws Exception {
		for (ImputationMethod method : ImputationMethod.values()) {

			if (ImputationMethod.BPCA.equals(method)) {
				if (!ImputeBPCA.isAvailable()) {
					continue;
				}
			}
			if (ImputationMethod.LSimputeAdaptive.equals(method)) {
				if (!ImputeLS.isAvailable()) {
					continue;
				}
			}
			if (ImputationMethod.LSimputeArray.equals(method)) {
				if (!ImputeLS.isAvailable()) {
					continue;
				}
			}
			if (ImputationMethod.LSimputeCombined.equals(method)) {
				if (!ImputeLS.isAvailable()) {
					continue;
				}
			}
			if (ImputationMethod.LSimputeGene.equals(method)) {
				if (!ImputeLS.isAvailable()) {
					continue;
				}
			}
			if (ImputationMethod.EMimputeArray.equals(method)) {
				if (!ImputeLS.isAvailable()) {
					continue;
				}
			}
			if (ImputationMethod.EMimputeGene.equals(method)) {
				if (!ImputeLS.isAvailable()) {
					continue;
				}
			}

			Matrix orig = DenseDoubleMatrix2D.Factory.randn(10, 10);
			orig.addMissing(Ret.ORIG, Matrix.ALL, 0.05);
			assertTrue(method.toString(), orig.containsMissingValues());
			Matrix m = orig.impute(Ret.NEW, method);
			assertFalse(method.toString(), m.containsMissingValues());
			assertTrue(method.toString(), Coordinates.equals(m.getSize(), orig.getSize()));
		}

	}
}
