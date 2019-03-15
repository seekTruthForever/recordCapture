package com.whv.recordCapture.frame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.whv.recordCapture.record.constant.Constant;
import com.whv.recordCapture.record.media.convert.VideoConvert;
import com.whv.recordCapture.record.run.recorder.ScreenRecorderMain;

public class RecordToolFrame extends JFrame  implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3538908009875243240L;
	private JFrame frame;
	private JPanel panel;
	private JButton startButton = new JButton("¿ªÊ¼");
	private JButton pauseButton = new JButton("ÔÝÍ£");
	private JButton stopButton = new JButton("Í£Ö¹");
	private int width;
	private int height;
	private ScreenRecorderMain main = null;
	private String recordDir = RecordCaptureMainFrame.getSettings().getRecordDir();
	private String flvDir = RecordCaptureMainFrame.getSettings().getFlvDir();
	private String videoName = "Â¼ÆÁ";
	public RecordToolFrame() {
		this.width = 400;
		this.height = 100;
	}
	public RecordToolFrame(int width,int height) {
		this.width = width;
		this.height = height;
	}
	private void initPanel() {
		panel = new JPanel(new GridLayout(1, 3));
		panel.add(startButton);
		panel.add(pauseButton);
		panel.add(stopButton);
		startButton.addActionListener(this);
		pauseButton.addActionListener(this);
		stopButton.addActionListener(this);
		startButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
	}
	public void show() {
		frame = new JFrame("Â¼ÆÁ¹¤¾ß");
		frame.setSize(this.width, this.height);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = screenSize.width/2;
		int screenHeight = screenSize.height/2;
		int width = frame.getWidth();
		int height = frame.getHeight();
		frame.setLocation(screenWidth-width/2, screenHeight-height/2);
		initPanel();
		frame.add(panel);
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==startButton) {
			PickRecordAreaDialog pickRecordAreaDialog = new PickRecordAreaDialog(new MouseAdapter() {
				public void mouseReleased(MouseEvent evn) {
					PickRecordAreaDialog.setFirstPoint(true);
					PickRecordAreaDialog.getDialog().setVisible(false);
					Constant.RECTX = PickRecordAreaDialog.getRecX();
					Constant.RECTY = PickRecordAreaDialog.getRecY();
					Constant.WIDTH = PickRecordAreaDialog.getRecW();
					Constant.HEIGHT = PickRecordAreaDialog.getRecH();
					main = new ScreenRecorderMain();
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					String timeStr = sdf.format(cal.getTime());
					videoName = "Â¼ÆÁ"+timeStr;
					main.start(videoName,recordDir);
					startButton.setEnabled(false);
					pauseButton.setEnabled(true);
					stopButton.setEnabled(true);
					PickRecordAreaDialog.getDialog().dispose();
				}
			});
			pickRecordAreaDialog.captureImage();
		}else if(e.getSource()==pauseButton) {
			if(main != null) {
				if("ÔÝÍ£".equals(e.getActionCommand())) {
					main.pause();
					pauseButton.setText("¼ÌÐø");
				}else {
					main.continueRecord();
					pauseButton.setText("ÔÝÍ£");
				}
			}
		}else if(e.getSource()==stopButton) {
			if(main != null) {
				main.stop();
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
				pauseButton.setEnabled(false);
				pauseButton.setText("ÔÝÍ£");
				VideoConvert.process(recordDir+File.separator+videoName+".avi", flvDir+File.separator+videoName+".flv");
			}
		}else{
			
		}
		
	}
	public void windowClosed(WindowEvent arg0) {   
        System.exit(0);
    } 
    public void windowClosing(WindowEvent arg0) { 
        System.exit(0);
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
    
}
