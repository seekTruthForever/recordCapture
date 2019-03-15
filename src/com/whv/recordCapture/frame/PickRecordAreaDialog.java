package com.whv.recordCapture.frame;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;


public class PickRecordAreaDialog {
//	private Robot robot;
	private static JDialog dialog = new JDialog();
	private BackgroundImage labFullScreenImage = new BackgroundImage();
	private int x1, y1, x2, y2;
	private static int recX, recY, recH, recW; // 截取的图像
	private static boolean isFirstPoint = true;
//	private BufferedImage fullScreenImage;
	public  PickRecordAreaDialog() {
		this(new MouseAdapter() {
			public void mouseReleased(MouseEvent evn) {
				isFirstPoint = true;
				dialog.setVisible(false);
			}
		});
	}
	public  PickRecordAreaDialog(MouseListener mouseListener) {
//		try {
//			robot = new Robot();
//		} catch (AWTException e) {
//			System.err.println("Internal Error: " + e);
//			e.printStackTrace();
//		}
		JPanel cp = (JPanel) dialog.getContentPane();
		cp.setLayout(new BorderLayout());
		final MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent evn) {
				if (isFirstPoint) {
					x1 = evn.getX();
					y1 = evn.getY();
					isFirstPoint = false;
				} else {
					x2 = evn.getX();
					y2 = evn.getY();
					int maxX = Math.max(x1, x2);
					int maxY = Math.max(y1, y2);
					int minX = Math.min(x1, x2);
					int minY = Math.min(y1, y2);
					recX = minX;
					recY = minY;
					recW = maxX - minX;
					recH = maxY - minY;
					labFullScreenImage.drawRectangle(recX, recY, recW, recH);
				}
			}

			public void mouseMoved(MouseEvent e) {
				labFullScreenImage.drawCross(e.getX(), e.getY());
			}
		};
//		final MouseListener mouseListener1 = mouseListener==null? new MouseAdapter() {
//			public void mouseReleased(MouseEvent evn) {
//				isFirstPoint = true;
//				dialog.setVisible(false);
////				dialog.setCursor(Cursor.getDefaultCursor());
////				labFullScreenImage.removeMouseMotionListener(mouseMotionListener);
////				labFullScreenImage.removeMouseListener(this);
//			}
//		}:mouseListener;
		labFullScreenImage.addMouseListener(mouseListener);
		labFullScreenImage.addMouseMotionListener(mouseMotionListener);
		cp.add(BorderLayout.CENTER, labFullScreenImage);
		dialog.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		dialog.setAlwaysOnTop(true);
		dialog.setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		dialog.setUndecorated(true);
		dialog.setSize(dialog.getMaximumSize());
		dialog.setModal(true);
		dialog.setBackground(Color.WHITE);
		dialog.setOpacity(0.3f);
	}
	/**
	 * 捕捉屏幕的一个矫形区域
	 */
	public void captureImage() {
//		fullScreenImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//		ImageIcon icon = new ImageIcon(fullScreenImage);
		//labFullScreenImage.setIcon(icon);
		dialog.setVisible(true);
	}
	public static JDialog getDialog() {
		return dialog;
	}
	public static void setDialog(JDialog dialog) {
		PickRecordAreaDialog.dialog = dialog;
	}
	public static int getRecX() {
		return recX;
	}
	public static void setRecX(int recX) {
		PickRecordAreaDialog.recX = recX;
	}
	public static int getRecY() {
		return recY;
	}
	public static void setRecY(int recY) {
		PickRecordAreaDialog.recY = recY;
	}
	public static int getRecH() {
		return recH;
	}
	public static void setRecH(int recH) {
		PickRecordAreaDialog.recH = recH;
	}
	public static int getRecW() {
		return recW;
	}
	public static void setRecW(int recW) {
		PickRecordAreaDialog.recW = recW;
	}
	public static boolean isFirstPoint() {
		return isFirstPoint;
	}
	public static void setFirstPoint(boolean isFirstPoint) {
		PickRecordAreaDialog.isFirstPoint = isFirstPoint;
	}
	
}
