package com.whv.recordCapture.frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.whv.recordCapture.capture.ScreenCapture;
import com.whv.recordCapture.settings.Settings;
import com.whv.recordCapture.utils.SerializeUtil;

public class RecordCaptureMainFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2733851789917741479L;
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JButton captureButton = new JButton("ΩÿÕº");
	private JButton recordButton = new JButton("¬º∆¡");
	private JButton settingButton = new JButton("…Ë÷√");
	private int width;
	private int height;
	private static Settings settings = new Settings();
	public RecordCaptureMainFrame() {
		this.width = 200;
		this.height = 100;
	}
	public RecordCaptureMainFrame(int width,int height) {
		this.width = width;
		this.height = height;
	}
	private void initPanel() {
		mainPanel = new JPanel(new GridLayout(1, 3));
		mainPanel.add(captureButton);
		mainPanel.add(recordButton);
		mainPanel.add(settingButton);
		captureButton.addActionListener(this);
		recordButton.addActionListener(this);
		settingButton.addActionListener(this);
	}
	public void show() {
		mainFrame = new JFrame("ΩÿÕº∫Õ¬º∆¡π§æﬂ");
		mainFrame.setSize(this.width, this.height);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width/2;
		int screenHeight = screenSize.height/2;
		int width = mainFrame.getWidth();
		int height = mainFrame.getHeight();
		mainFrame.setLocation(screenWidth-width/2, screenHeight-height/2);
		initPanel();
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==captureButton) {//ΩÿÕº
			ScreenCapture capture = ScreenCapture.getInstance();
			capture.captureImage();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStr = sdf.format(cal.getTime());
			String fileName = "ΩÿÕº"+timeStr;
			File file = new File(getSettings().getCaptureDir()+File.separator+fileName+".png");
			try {
				capture.saveAsPNG(file);
			} catch (IOException e1) {
			}
			CaptureResultFrame captureResultFrame = new CaptureResultFrame(capture);
			captureResultFrame.show();
		}else if(e.getSource()==recordButton) {//¬º∆¡
			RecordToolFrame recordToolFrame = new RecordToolFrame(200, 60);
			recordToolFrame.show();
		}else if(e.getSource()==settingButton) {//…Ë÷√
			SettingsFrame settingsFrame = new SettingsFrame(500,200);
			settingsFrame.show();
		}else{
			
		}
		
	}
	public void windowClosed(WindowEvent arg0) {   
        System.exit(0);
    } 
    public void windowClosing(WindowEvent arg0) { 
        System.exit(0);
    }
    public static void main(String[] args) {
    	File file = new File("setting.dat");
        if(file.exists()) {
        	try {
				settings = (Settings) SerializeUtil.load(new FileInputStream(file));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else {
        	try {
				file.createNewFile();
				SerializeUtil.store(new Settings(), new FileOutputStream(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	RecordCaptureMainFrame recordCaptureMainFrame = new RecordCaptureMainFrame(200,60);
    	recordCaptureMainFrame.show();
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
	public static Settings getSettings() {
		return settings;
	}
	public static void setSettings(Settings settings) {
		RecordCaptureMainFrame.settings = settings;
	}
}
