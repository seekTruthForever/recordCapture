package com.whv.recordCapture.record.media.png;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;

import com.whv.recordCapture.record.media.AbstractVideoCodec;
import com.whv.recordCapture.record.media.Buffer;
import com.whv.recordCapture.record.media.io.ByteArrayImageOutputStream;

/**
 * ����ʱ֧���������͵�ͼƬ��ʽ��JPEG��PNG����������������PNG��ʽ��ͼƬ��
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 ������
 *
 */
public class PNGCodec extends AbstractVideoCodec {

	@Override
	public void process(Buffer in, Buffer out) {
		if ((in.flags & Buffer.FLAG_DISCARD) != 0) {
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
		out.format = outputFormat;
		BufferedImage image = getBufferedImage(in);
		if (image == null) {
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}

		ByteArrayImageOutputStream tmp;
		if (out.data instanceof byte[]) {
			tmp = new ByteArrayImageOutputStream((byte[]) out.data);
		} else {
			tmp = new ByteArrayImageOutputStream();
		}

		try {
			ImageWriter iw = (ImageWriter) ImageIO.getImageWritersByMIMEType("image/png").next();
			ImageWriteParam iwParam = iw.getDefaultWriteParam();
			iw.setOutput(tmp);
			IIOImage img = new IIOImage(image, null, null);
			iw.write(null, img, iwParam);
			iw.dispose();
			out.flags = Buffer.FLAG_KEY_FRAME;
			out.data = tmp.getBuffer();
			out.offset = 0;
			out.length = (int) tmp.getStreamPosition();
		} catch (IOException ex) {
			ex.printStackTrace();
			out.flags = Buffer.FLAG_DISCARD;
			return;
		}
	}
}
