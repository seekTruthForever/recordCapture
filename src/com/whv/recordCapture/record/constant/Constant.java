package com.whv.recordCapture.record.constant;

import java.awt.Toolkit;

/**
 * ����Ĭ��ֵ����Ҫ������<br>
 * 1.ͼ��λ�����ã�8,16,24����Ĭ��Ϊ16λ<br>
 * 2.��Ļ��ˢ�����ʣ�1-30����Ĭ��Ϊ12<br>
 * 3.����ƶ����ʣ�1-30����Ĭ��Ϊ12<br>
 * 4.�����ɫ��ʾ���ã���ɫ���ɫ��Ĭ��Ϊ��ɫ<br>
 * 5.��Ļ�Ŀ�Ⱥ͸߶ȣ�Ĭ���Զ���ȡ��Ļ�Ŀ�Ⱥ͸߶�<br>
 * <em>��д���ڣ�2014��2��8�� 9:07</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7������
 *
 */
public class Constant  extends ConfigurableContants {

    static {
        init("/system.properties");
    }
    /**
     * ¼��״̬����ʼ¼��
     */
    public static final int RECORD_ON = 0;
    /**
     * ¼��״̬����ͣ¼��
     */
    public static final int RECORD_PAUSE = 1;
    /**
     * ¼��״̬������¼��
     */
    public static final int RECORD_CONTINUE = 2;
    /**
     * ¼��״̬������¼��
     */
    public static final int RECORD_OFF = 3;
    /**
     * ͼ��λ��(8,16,24)
     */
    public static final int BIT_DEPTH = Integer.parseInt(getProperty("bit_depth","16"));
    
    /**
     * ��Ļ����(1-30)
     */
    public static final float SCREEN_RATE = Float.parseFloat(getProperty("screen_rate","12"));
    
    /**
     * �������(1-30)
     */
    public static final float MOUSE_RATE = Float.parseFloat(getProperty("mouse_rate","12"));
    
    /**
     * �����ʽ(BLACK,WHITE,NONE)
     */
    public static final String CURSOR_STYLE = getProperty("cursor_style","WHITE");
    
    /**
     * ��Ļ�Ŀ��
     */
    public static int WIDTH= Integer.parseInt(getProperty("width",Toolkit.getDefaultToolkit().getScreenSize().width+""));
    
    /**
     * ��Ļ�ĸ߶�
     */
    public static int HEIGHT= Integer.parseInt(getProperty("height",Toolkit.getDefaultToolkit().getScreenSize().height+""));
    /**
     * ��Ļ����ʼX����
     */
    public static int RECTX= Integer.parseInt(getProperty("rectX","0"));
    
    /**
     * ��Ļ����ʼY����
     */
    public static int RECTY= Integer.parseInt(getProperty("rectY","0"));

}
