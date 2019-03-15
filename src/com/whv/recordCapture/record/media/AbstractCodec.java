package com.whv.recordCapture.record.media;

/**
 * ��Ƶ�ֽ����ȡ��������
 * @author mengfieyang
 * @version 1.0
 *
 */
public abstract class AbstractCodec implements Codec {

	protected Format inputFormat;
	protected Format outputFormat;
	protected float quality = 1;

	@Override
	public Format setInputFormat(Format f) {
		this.inputFormat = f;
		return f;
	}

	@Override
	public Format setOutputFormat(Format f) {
		this.outputFormat = f;
		return f;
	}

	@Override
	public void setQuality(float newValue) {
		quality = newValue;
	}

	@Override
	public float getQuality() {
		return quality;
	}
}
