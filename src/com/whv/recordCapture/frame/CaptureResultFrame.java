package com.whv.recordCapture.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.whv.recordCapture.capture.ScreenCapture;

public class CaptureResultFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6556739126999469649L;
	private JFrame frame;
	private JPanel panel;
	private JLabel imagebox;
	private int width;
	private int height;
	private ScreenCapture capture;
	public CaptureResultFrame() {
		this.width = 400;
		this.height = 300;
		this.capture = ScreenCapture.getInstance();
	}
	public CaptureResultFrame(int width,int height) {
		this.width = width;
		this.height = height;
		this.capture = ScreenCapture.getInstance();
	}
	public CaptureResultFrame(ScreenCapture capture) {
		this.width = capture.getPickedIcon().getIconWidth();
		this.height = capture.getPickedIcon().getIconHeight();
		this.capture = capture;
	}
	private void initPanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		imagebox = new JLabel();
		imagebox.setIcon(capture.getPickedIcon());
		panel.add(BorderLayout.CENTER, imagebox);
	}
	
	public void show() {
		frame = new JFrame("½ØÍ¼½á¹û");
		frame.setSize(this.width, this.height);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(true);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width/2;
		int screenHeight = screenSize.height/2;
		int width = frame.getWidth();
		int height = frame.getHeight();
		frame.setLocation(screenWidth-width/2, screenHeight-height/2);
		initPanel();
		frame.setContentPane(panel);
		//frame.add(panel);
		frame.setVisible(true);
	}
	public void windowClosed(WindowEvent e) {
        frame.dispose();
    } 
    public void windowClosing(WindowEvent arg0) { 
    	frame.dispose();
    }
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public ScreenCapture getCapture() {
		return capture;
	}
	public void setCapture(ScreenCapture capture) {
		this.capture = capture;
	}
}
