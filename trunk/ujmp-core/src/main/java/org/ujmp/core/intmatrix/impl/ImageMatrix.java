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

package org.ujmp.core.intmatrix.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.ujmp.core.intmatrix.stub.AbstractDenseIntMatrix2D;
import org.ujmp.core.util.io.HttpUtil;

public class ImageMatrix extends AbstractDenseIntMatrix2D {
	private static final long serialVersionUID = -1354524587823816194L;

	private final BufferedImage bufferedImage;

	public ImageMatrix(String filename) throws IOException {
		this(new File(filename));
	}

	public ImageMatrix(BufferedImage bufferedImage) {
		super(bufferedImage.getHeight(), bufferedImage.getWidth());
		this.bufferedImage = bufferedImage;
	}

	public ImageMatrix(File file) throws IOException {
		this(ImageIO.read(file));
	}

	public ImageMatrix(byte[] bytes) throws IOException {
		this(ImageIO.read(new ByteArrayInputStream(bytes)));
	}

	public ImageMatrix(InputStream stream) throws IOException {
		this(ImageIO.read(stream));
	}

	public ImageMatrix(URL url) throws IOException {
		this(HttpUtil.getBytesFromUrl(url));
	}

	public int getInt(long row, long column) {
		return getInt((int) row, (int) column);
	}

	public void setInt(int value, long row, long column) {
		setInt(value, (int) row, (int) column);
	}

	public int getInt(int row, int column) {
		return bufferedImage.getRGB(column, row);
	}

	public void setInt(int value, int row, int column) {
		bufferedImage.setRGB(column, row, value);
	}

}
