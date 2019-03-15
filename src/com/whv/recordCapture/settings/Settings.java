package com.whv.recordCapture.settings;

import java.io.Serializable;
/**
 * 设置参数对象
 * @author whv
 *
 */
public class Settings implements Serializable {

	private static final long serialVersionUID = 4868809563887327338L;
	/**
	 * 截屏目录
	 */
	private String captureDir;
	/**
	 * 录屏目录
	 */
	private String recordDir;
	/**
	 * 存放转换后的flv的目录
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
