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

package org.ujmp.gui.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ConcurrentModificationException;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.UJMPFormat;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.util.ColorUtil;
import org.ujmp.gui.util.GraphicsUtil;
import org.ujmp.gui.util.UIDefaults;

public class MatrixHeatmapRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 942689931503793487L;

	private Matrix matrix = null;

	private int width = 0;

	private int height = 0;

	private static int PADDINGX = UIManager.getInt("Table.paddingX");

	private static int PADDINGY = UIManager.getInt("Table.paddingY");

	public MatrixHeatmapRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (value instanceof MatrixGUIObject) {
			matrix = ((MatrixGUIObject) value).getMatrix();
		} else if (value instanceof Matrix) {
			matrix = (Matrix) value;
		} else {
			matrix = null;
		}

		width = table.getColumnModel().getColumn(column).getWidth() - 1;
		height = table.getRowHeight(row) - 1;

		if (isSelected) {
			super.setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			super.setForeground(table.getForeground());
			super.setBackground(table.getBackground());
		}

		setFont(table.getFont());

		if (hasFocus) {
			Border border = null;
			if (isSelected) {
				border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
			}
			if (border == null) {
				border = UIManager.getBorder("Table.focusCellHighlightBorder");
			}
			setBorder(border);

			if (!isSelected && table.isCellEditable(row, column)) {
				Color col;
				col = UIManager.getColor("Table.focusCellForeground");
				if (col != null) {
					super.setForeground(col);
				}
				col = UIManager.getColor("Table.focusCellBackground");
				if (col != null) {
					super.setBackground(col);
				}
			}
		} else {
			setBorder(noFocusBorder);
		}

		return this;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(getBackground());
		g2d.fillRect(0, 0, width, height);

		try {
			if (matrix != null) {

				int width = getWidth();
				int height = getHeight();
				width = width == 0 ? 1 : width;
				height = height == 0 ? 1 : height;
				long totalColumn = matrix.getColumnCount();
				long totalRows = matrix.getRowCount();
				int xsize = (int) Math.min(totalColumn, width);
				int ysize = (int) Math.min(totalRows, height);
				xsize = xsize == 0 ? 1 : xsize;
				ysize = ysize == 0 ? 1 : ysize;

				double stepsizeX = (double) totalColumn / (double) width;
				double stepsizeY = (double) totalRows / (double) height;
				if (stepsizeX < 1.0) {
					stepsizeX = 1.0;
				}
				if (stepsizeY < 1.0) {
					stepsizeY = 1.0;
				}

				BufferedImage bufferedImage = new BufferedImage(xsize, ysize, BufferedImage.TYPE_INT_RGB);

				int[] pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

				if (stepsizeX != 1.0 || stepsizeY != 1.0) {
					int pos = 0;
					for (int y = 0; y < ysize; y++) {
						for (int x = 0; x < xsize; x++) {
							long mx = (long) Math.floor(x * stepsizeX);
							long my = (long) Math.floor(y * stepsizeY);
							Color col = ColorUtil.fromObject(matrix.getAsObject(my, mx));
							pixels[pos++] = (col.getRed() << 16) + (col.getGreen() << 8) + col.getBlue();
						}
					}
				} else {
					Iterable<long[]> cos = matrix.allCoordinates();
					for (long[] c : cos) {
						if (c != null) {
							Color col = ColorUtil.fromObject(matrix.getAsObject(c[0], c[1]));
							int pos = getPosition(totalColumn, c[Coordinates.ROW], c[Coordinates.COLUMN]);
							pixels[pos] = (col.getRed() << 16) + (col.getGreen() << 8) + col.getBlue();
						}
					}
				}

				g2d.drawImage(bufferedImage, PADDINGX, PADDINGY, width - PADDINGX - PADDINGX, height - PADDINGY
						- PADDINGY, null);

				if (width > 20 && matrix.isScalar()) {
					Color col = ColorUtil.fromObject(matrix.getAsObject(0, 0));
					g2d.setColor(ColorUtil.contrastBW(col));
					String s = UJMPFormat.getSingleLineInstance().format(matrix.getAsObject(0, 0));
					if (s != null && s.length() > 25) {
						s = s.substring(0, 25) + "...";
					}
					GraphicsUtil.drawString(g2d, width / 2.0, height / 2.0 - 1.0, GraphicsUtil.ALIGNCENTER,
							GraphicsUtil.ALIGNCENTER, s);
				}

			} else {
				g2d.setColor(Color.GRAY);
				g2d.drawLine(PADDINGX, PADDINGY, width - PADDINGX, height - PADDINGY);
				g2d.drawLine(PADDINGX, height - PADDINGY, width - PADDINGX, 0 + PADDINGY);
			}
		} catch (ConcurrentModificationException e) {
			// not too bad
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int getPosition(long totalColumn, long currentRow, long currentColumn) {
		return (int) (totalColumn * currentRow + currentColumn);
	}

	public static void paintMatrix(Graphics g, Matrix matrix, int width, int height) {
		if (g == null)
			return;

		Graphics2D g2d = (Graphics2D) g;
		g2d.addRenderingHints(UIDefaults.AALIAS);

		if (matrix == null) {
			g2d.setColor(Color.GRAY);
			g2d.drawLine(PADDINGX, PADDINGY, width - PADDINGX, height - PADDINGY);
			g2d.drawLine(width - PADDINGX, PADDINGY, PADDINGX, height - PADDINGY);
		} else {
			g2d.translate(PADDINGX, PADDINGX);
			paintMatrixOriginal(g2d, matrix, width - PADDINGX - PADDINGX, height - PADDINGY - PADDINGY);
			g2d.translate(-PADDINGX, -PADDINGX);
		}
	}

	private static void paintMatrixOriginal(Graphics g, Matrix matrix, int width, int height) {
		try {
			long cols = matrix.getColumnCount();
			long rows = matrix.getRowCount();

			width = width < 1 ? 1 : width;
			height = height < 1 ? 1 : height;
			long xSize = cols > 0 ? cols : 1;
			long ySize = rows > 0 ? rows : 1;

			xSize = xSize > 0 ? xSize : 1;
			ySize = ySize > 0 ? ySize : 1;

			long xStepSize = (long) Math.ceil((double) xSize / (double) width);
			long yStepSize = (long) Math.ceil((double) ySize / (double) height);

			int imgWidth = (int) (xSize / xStepSize);
			int imgHeight = (int) (ySize / yStepSize);
			imgWidth = imgWidth > 0 ? imgWidth : 1;
			imgHeight = imgHeight > 0 ? imgWidth : 1;

			BufferedImage bufferedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) g;
			Graphics2D bg = (Graphics2D) bufferedImage.getGraphics();
			bg.addRenderingHints(UIDefaults.AALIAS);

			for (long col = 0; col < cols; col += xStepSize) {
				for (long row = 0; row < rows; row += yStepSize) {
					bg.setColor(ColorUtil.fromObject(matrix.getAsDouble(row, col)));
					bg.fillRect((int) (col / xStepSize), (int) (row / yStepSize), 1, 1);
				}
			}
			g2d.drawImage(bufferedImage, 0, 0, width, height, 0, 0, bufferedImage.getWidth(),
					bufferedImage.getHeight(), null);
			if (width > 20 && matrix.isScalar()) {
				String s = UJMPFormat.getSingleLineInstance().format(matrix.getAsObject(0, 0));
				if (s != null && s.length() > 25) {
					s = s.substring(0, 25) + "...";
				}
				g2d.setColor(ColorUtil.contrastBW(bg.getColor()));
				GraphicsUtil.drawString(g2d, width / 2.0, height / 2.0 - 1.0, GraphicsUtil.ALIGNCENTER,
						GraphicsUtil.ALIGNCENTER, s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

}
