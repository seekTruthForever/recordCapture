package com.whv.recordCapture.record.constant;

import java.awt.Toolkit;

/**
 * 配置默认值。主要包括：<br>
 * 1.图像位数配置（8,16,24），默认为16位<br>
 * 2.屏幕的刷新速率（1-30），默认为12<br>
 * 3.鼠标移动速率（1-30），默认为12<br>
 * 4.鼠标颜色演示配置：黑色或白色，默认为白色<br>
 * 5.屏幕的宽度和高度，默认自动获取屏幕的宽度和高度<br>
 * <em>编写日期：2014年2月8日 9:07</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7及以上
 *
 */
public class Constant  extends ConfigurableContants {

    static {
        init("/system.properties");
    }
    /**
     * 录屏状态：开始录屏
     */
    public static final int RECORD_ON = 0;
    /**
     * 录屏状态：暂停录屏
     */
    public static final int RECORD_PAUSE = 1;
    /**
     * 录屏状态：继续录屏
     */
    public static final int RECORD_CONTINUE = 2;
    /**
     * 录屏状态：结束录屏
     */
    public static final int RECORD_OFF = 3;
    /**
     * 图像位数(8,16,24)
     */
    public static final int BIT_DEPTH = Integer.parseInt(getProperty("bit_depth","16"));
    
    /**
     * 屏幕速率(1-30)
     */
    public static final float SCREEN_RATE = Float.parseFloat(getProperty("screen_rate","12"));
    
    /**
     * 鼠标速率(1-30)
     */
    public static final float MOUSE_RATE = Float.parseFloat(getProperty("mouse_rate","12"));
    
    /**
     * 光标样式(BLACK,WHITE,NONE)
     */
    public static final String CURSOR_STYLE = getProperty("cursor_style","WHITE");
    
    /**
     * 屏幕的宽度
     */
    public static int WIDTH= Integer.parseInt(getProperty("width",Toolkit.getDefaultToolkit().getScreenSize().width+""));
    
    /**
     * 屏幕的高度
     */
    public static int HEIGHT= Integer.parseInt(getProperty("height",Toolkit.getDefaultToolkit().getScreenSize().height+""));
    /**
     * 屏幕的起始X坐标
     */
    public static int RECTX= Integer.parseInt(getProperty("rectX","0"));
    
    /**
     * 屏幕的起始Y坐标
     */
    public static int RECTY= Integer.parseInt(getProperty("rectY","0"));

}
