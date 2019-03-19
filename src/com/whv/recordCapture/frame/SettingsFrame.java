package com.whv.recordCapture.frame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.whv.recordCapture.settings.Settings;
import com.whv.recordCapture.utils.SerializeUtil;

public class SettingsFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -5624558684733439930L;
	private JFrame mainframe;
    private JPanel panel;
    //创建相关的Label标签
    private JLabel captureDir_label = new JLabel("截图存储目录:");
    private JLabel recordDir_label = new JLabel("录屏存储目录:");
    private JLabel flvDir_label = new JLabel("录屏转换的FLV存储目录:");    
    //创建相关的文本域
    private JTextField captureDir_textfield = new JTextField(20);
    private JTextField recordDir_textfield = new JTextField(20);
    private JTextField flvDir_textfield = new JTextField(20);
    //创建按钮
    private JButton captureDir_button = new JButton("...");
    private JButton recordDir_button = new JButton("...");
    private JButton flvDir_button = new JButton("..."); 
    private JButton setting_button = new JButton("应用");
    private int width;
	private int height;
	public SettingsFrame() {
		this.width = 500;
		this.height = 200;
	}
	public SettingsFrame(int width,int height) {
		this.width = width;
		this.height = height;
	}
    public void show(){
        mainframe = new JFrame("设置");
        // Setting the width and height of frame
        mainframe.setSize(this.width, this.height);
        mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainframe.setResizable(false);//固定窗体大小

        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包 
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸 
        int screenWidth = screenSize.width/2; // 获取屏幕的宽
        int screenHeight = screenSize.height/2; // 获取屏幕的高
        int height = mainframe.getHeight(); //获取窗口高度
        int width = mainframe.getWidth(); //获取窗口宽度
        mainframe.setLocation(screenWidth-width/2, screenHeight-height/2);//将窗口设置到屏幕的中部             
        //窗体居中，c是Component类的父窗口
        //mainframe.setLocationRelativeTo(c);   
//        Image myimage=kit.getImage("resourse/hxlogo.gif"); //由tool获取图像
//        mainframe.setIconImage(myimage);
        initPanel();//初始化面板
        mainframe.add(panel);
        mainframe.setVisible(true);
    }
     /* 创建面板，这个类似于 HTML 的 div 标签
     * 我们可以创建多个面板并在 JFrame 中指定位置
     * 面板中我们可以添加文本字段，按钮及其他组件。
     */
    public void initPanel(){
        this.panel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();    // 布局管理器
        GridBagConstraints c = null;                    // 约束
        panel.setLayout(gridBag);
        c = new GridBagConstraints();
        c.ipadx=5;
        c.anchor = GridBagConstraints.LINE_END;
        gridBag.addLayoutComponent(captureDir_label, c); // 内部使用的仅是 c 的副本
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(captureDir_textfield, c);
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(captureDir_button, c);
        c = new GridBagConstraints();
        c.ipadx=5;
        c.anchor = GridBagConstraints.LINE_END;
        gridBag.addLayoutComponent(recordDir_label, c); // 内部使用的仅是 c 的副本
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(recordDir_textfield, c);
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(recordDir_button, c);
        c = new GridBagConstraints();
        c.ipadx=5;
        c.anchor = GridBagConstraints.LINE_END;
        gridBag.addLayoutComponent(flvDir_label, c); // 内部使用的仅是 c 的副本
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.BOTH;
        gridBag.addLayoutComponent(flvDir_textfield, c);
        c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(flvDir_button, c);
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_END;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.addLayoutComponent(setting_button, c);
        this.panel.add(captureDir_label);
        this.panel.add(captureDir_textfield);
        this.panel.add(captureDir_button);

        this.panel.add(recordDir_label);
        this.panel.add(recordDir_textfield);
        this.panel.add(recordDir_button);

        this.panel.add(flvDir_label);
        this.panel.add(flvDir_textfield);
        this.panel.add(flvDir_button);

        this.panel.add(setting_button);

        //增加动作监听
        captureDir_button.addActionListener(this);
        recordDir_button.addActionListener(this);
        flvDir_button.addActionListener(this);
        setting_button.addActionListener(this);
        Settings settings = RecordCaptureMainFrame.getSettings();
        captureDir_textfield.setText(settings.getCaptureDir());
        recordDir_textfield.setText(settings.getRecordDir());
        flvDir_textfield.setText(settings.getFlvDir());
        
    }
    /**
     * 单击动作触发方法
     * @param event
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == setting_button){
                Settings settings = new Settings();
                settings.setCaptureDir(captureDir_textfield.getText());
                settings.setRecordDir(recordDir_textfield.getText());
                settings.setFlvDir(flvDir_textfield.getText());
//                String path = SettingsFrame.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                String path = System.getProperty("java.class.path");
                int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
                int lastIndex = path.lastIndexOf(File.separator) + 1;
                path = path.substring(firstIndex, lastIndex);
                try {
        			path = java.net.URLDecoder.decode(path, "UTF-8");
        		} catch (UnsupportedEncodingException e1) {
        			e1.printStackTrace();
        		}
                File file = new File(path+"setting.dat");
                try {
					SerializeUtil.store(settings, new FileOutputStream(file));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					RecordCaptureMainFrame.setSettings(settings);
					mainframe.dispose();
				}
        }else{
            //判断三个选择按钮并对应操作
            if(event.getSource() == captureDir_button) {
                File file = openChoseWindow(JFileChooser.DIRECTORIES_ONLY);
                if(file == null)
                    return;
                captureDir_textfield.setText(file.getAbsolutePath());
                recordDir_textfield.setText(file.getAbsolutePath()+File.separator+"avi");
                flvDir_textfield.setText(file.getAbsolutePath()+File.separator+"flv");
            }else if(event.getSource() == recordDir_button){
                File file = openChoseWindow(JFileChooser.DIRECTORIES_ONLY);
                if(file == null)
                    return;
                recordDir_textfield.setText(file.getAbsolutePath());
            }else if(event.getSource() == flvDir_button){
                File file = openChoseWindow(JFileChooser.DIRECTORIES_ONLY);
                if(file == null)
                    return;
                flvDir_textfield.setText(file.getAbsolutePath());
            }           
        }
    }
    /**
     * 打开选择文件窗口并返回文件
     * @param type
     * @return
     */
    public File openChoseWindow(int type){
        JFileChooser jfc=new JFileChooser();  
        jfc.setFileSelectionMode(type);//选择的文件类型(文件夹or文件)  
        jfc.showDialog(new JLabel(), "选择");  
        File file=jfc.getSelectedFile();
        return file;
    }
    public void windowClosed(WindowEvent arg0) {         
        System.exit(0);
    } 
    public void windowClosing(WindowEvent arg0) { 
        System.exit(0);
    }

}
