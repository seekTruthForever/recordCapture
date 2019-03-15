package com.whv.recordCapture.frame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class BackgroundImage extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5459184398473698230L;
	int lineX, lineY;
	int x, y, h, w;
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.drawRect(x, y, w, h);
		String area = Integer.toString(w) + " * " + Integer.toString(h);
		g.drawString(area, x + (int) w / 2 - 15, y + (int) h / 2);
		g.drawLine(lineX, 0, lineX, getHeight());
		g.drawLine(0, lineY, getWidth(), lineY);
	}

	public void drawRectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		h = height;
		w = width;
		repaint();
	}

	public void drawCross(int x, int y) {
		lineX = x;
		lineY = y;
		repaint();
	}
}
