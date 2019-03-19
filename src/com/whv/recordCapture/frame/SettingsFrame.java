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
    //������ص�Label��ǩ
    private JLabel captureDir_label = new JLabel("��ͼ�洢Ŀ¼:");
    private JLabel recordDir_label = new JLabel("¼���洢Ŀ¼:");
    private JLabel flvDir_label = new JLabel("¼��ת����FLV�洢Ŀ¼:");    
    //������ص��ı���
    private JTextField captureDir_textfield = new JTextField(20);
    private JTextField recordDir_textfield = new JTextField(20);
    private JTextField flvDir_textfield = new JTextField(20);
    //������ť
    private JButton captureDir_button = new JButton("...");
    private JButton recordDir_button = new JButton("...");
    private JButton flvDir_button = new JButton("..."); 
    private JButton setting_button = new JButton("Ӧ��");
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
        mainframe = new JFrame("����");
        // Setting the width and height of frame
        mainframe.setSize(this.width, this.height);
        mainframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainframe.setResizable(false);//�̶������С

        Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰� 
        Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ� 
        int screenWidth = screenSize.width/2; // ��ȡ��Ļ�Ŀ�
        int screenHeight = screenSize.height/2; // ��ȡ��Ļ�ĸ�
        int height = mainframe.getHeight(); //��ȡ���ڸ߶�
        int width = mainframe.getWidth(); //��ȡ���ڿ��
        mainframe.setLocation(screenWidth-width/2, screenHeight-height/2);//���������õ���Ļ���в�             
        //������У�c��Component��ĸ�����
        //mainframe.setLocationRelativeTo(c);   
//        Image myimage=kit.getImage("resourse/hxlogo.gif"); //��tool��ȡͼ��
//        mainframe.setIconImage(myimage);
        initPanel();//��ʼ�����
        mainframe.add(panel);
        mainframe.setVisible(true);
    }
     /* ������壬��������� HTML �� div ��ǩ
     * ���ǿ��Դ��������岢�� JFrame ��ָ��λ��
     * ��������ǿ�������ı��ֶΣ���ť�����������
     */
    public void initPanel(){
        this.panel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();    // ���ֹ�����
        GridBagConstraints c = null;                    // Լ��
        panel.setLayout(gridBag);
        c = new GridBagConstraints();
        c.ipadx=5;
        c.anchor = GridBagConstraints.LINE_END;
        gridBag.addLayoutComponent(captureDir_label, c); // �ڲ�ʹ�õĽ��� c �ĸ���
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
        gridBag.addLayoutComponent(recordDir_label, c); // �ڲ�ʹ�õĽ��� c �ĸ���
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
        gridBag.addLayoutComponent(flvDir_label, c); // �ڲ�ʹ�õĽ��� c �ĸ���
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

        //���Ӷ�������
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
     * ����������������
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
            //�ж�����ѡ��ť����Ӧ����
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
     * ��ѡ���ļ����ڲ������ļ�
     * @param type
     * @return
     */
    public File openChoseWindow(int type){
        JFileChooser jfc=new JFileChooser();  
        jfc.setFileSelectionMode(type);//ѡ����ļ�����(�ļ���or�ļ�)  
        jfc.showDialog(new JLabel(), "ѡ��");  
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
