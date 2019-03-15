package com.whv.recordCapture.record.camera;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import com.whv.recordCapture.record.constant.Constant;

/**
 * ��ʼ��Java robot�����Զ���Ļ���н�ͼ����ͼǰ���Զ���ȡ��Ļ��С��
 * <em>��д���ڣ�2014��2��8�� 9:07</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7������
 *
 */
public class Capture {

  //  private  static Rectangle rectangle = new Rectangle(Constant.RECTX,Constant.RECTY,Constant.WIDTH,Constant.HEIGHT);
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage capture() throws Exception {
        return robot.createScreenCapture(getRectangle());
    }

	public static Rectangle getRectangle() {
		return new Rectangle(Constant.RECTX,Constant.RECTY,Constant.WIDTH,Constant.HEIGHT);
	}

}
