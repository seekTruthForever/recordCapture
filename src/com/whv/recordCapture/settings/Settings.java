package com.whv.recordCapture.settings;

import java.io.Serializable;
/**
 * ���ò�������
 * @author whv
 *
 */
public class Settings implements Serializable {

	private static final long serialVersionUID = 4868809563887327338L;
	/**
	 * ����Ŀ¼
	 */
	private String captureDir;
	/**
	 * ¼��Ŀ¼
	 */
	private String recordDir;
	/**
	 * ���ת�����flv��Ŀ¼
	 */
	private String flvDir;
	public String getCaptureDir() {
		return captureDir;
	}
	public void setCaptureDir(String captureDir) {
		this.captureDir = captureDir;
	}
	public String getRecordDir() {
		return recordDir;
	}
	public void setRecordDir(String recordDir) {
		this.recordDir = recordDir;
	}
	public String getFlvDir() {
		return flvDir;
	}
	public void setFlvDir(String flvDir) {
		this.flvDir = flvDir;
	}

}
