package com.whv.recordCapture.record.media.convert;

import java.io.File;

import com.whv.recordCapture.record.constant.Constant;


/**
 * ���κ���Ƶ��ʽתΪflv.<br>
 *  1.ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�<br>
 * 	2.��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��),����mencoderת��Ϊavi��ʽ.<br>
 * @author mengfeiyang��JavaAlpha
 * @date 2014-2-10
 * @version V 1.0
 */

public class VideoConvert {
	public static String targetPath;
	public static String resourcePath;
	public static String result;
	public static int retryTimes;

	public static void process(String resourcePath,String targetPath) {
		//System.out.println("Դ��ַ��"+resourcePath);
		//System.out.println("Ŀ���ַ0��"+targetPath);
		if(!checkExists(resourcePath)) {
			System.err.println("Դ��Ƶ�ļ�������");
			return;
		}
		if(targetPath == null||"".equals(targetPath)) {
			targetPath = resourcePath.substring(0, resourcePath.lastIndexOf(File.separator)+1)+".flv";
		}
		String targertDir = targetPath.substring(0, targetPath.lastIndexOf(File.separator)+1);
		if(!checkExists(targertDir)) {
			File file = new File(targertDir);
			file.mkdirs();
		}
		VideoConvert.targetPath = targetPath;
		VideoConvert.resourcePath = resourcePath;
		int type = checkContentType(resourcePath);

		if (type == 0) {
			processFLV(resourcePath);// ֱ�ӽ��ļ�תΪflv�ļ�
		} else if (type == 1) {
			processFLV(resourcePath);// ��rmvbתΪflv
		}
	}

	private static int checkContentType(String resourcePath) {
		String type = resourcePath.substring(resourcePath.lastIndexOf(".") + 1, resourcePath.length()).toLowerCase();
		// ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		} else if (type.equals("mpeg")) {
			return 0;
		} else if (type.equals("mpe")) {
			return 0;
		}
		// ��ffmpeg�޷��������ļ���ʽ(wmv9��rm��rmvb��),
		// �������ñ�Ĺ��ߣ�mencoder��ת��Ϊavi(ffmpeg�ܽ�����)��ʽ.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) {
			return 1;
		}
		return 9;
	}

	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}
	private static boolean checkExists(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		return true;
	}

	// ffmpeg�ܽ����ĸ�ʽ����asx��asf��mpg��wmv��3gp��mp4��mov��avi��flv�ȣ�

	private static void processFLV(String resourcePath) {
		if (!checkfile(resourcePath)) {
			System.err.println(resourcePath + " is not file");
		}
		 String path = System.getProperty("java.class.path");
         int firstIndex = path.lastIndexOf(System.getProperty("path.separator")) + 1;
         int lastIndex = path.lastIndexOf(File.separator) + 1;
         path = path.substring(firstIndex, lastIndex);
        String comm = "";
        comm = comm + path+"drivers"+File.separator+"ffmpeg.exe";
        comm = comm + " -i ";
        comm = comm + resourcePath.replaceAll(" ", "\" \"");
        comm = comm + " -ab";
        comm = comm + " 56";
        comm = comm + " -ar";
        comm = comm + " 22050";
        comm = comm + " -qscale";
        comm = comm + " 8";
        comm = comm + " -r";
        comm = comm + " 15";
        comm = comm + " -s ";
        comm = comm + Constant.WIDTH+"x"+Constant.HEIGHT+" ";
        comm = comm + targetPath.replaceAll(" ", "\" \"");
		try {
/*			Runtime runtime = Runtime.getRuntime();
			Process proce = null;
			String cmd = "";
			String cut = "F://ffmpeg.exe -i" + resourcePath + " -y -f image2   -ss   8   -t   0.001   -s   1366x768   D:\\snapshot\\" + savename + ".jpg";
			String cutCmd = cmd + cut;
			//proce = runtime.exec(comm);
*/			
			Runtime.getRuntime().exec(comm);
			retryTimes = 60;
			
			//System.out.println("Ŀ���ַ��"+targetPath + "video.flv");
			System.out.println("����ת����Ƶ...");
			File oldFile = new File(targetPath);
			boolean aFlag = true;
			int RetryTimes = 0;
			while(aFlag){
				RetryTimes++;
				if(RetryTimes == retryTimes){
					RetryTimes = 0;
					System.out.println("ת����Ƶʧ�ܣ����Դ�����" + retryTimes +"*1s");
					result = "ת����Ƶʧ�ܣ����Դ�����" + retryTimes +"*1s";
					aFlag = false;
				}
				if(oldFile.exists()){
					System.out.println("ת����Ƶ��ɣ�");
				   result = "ת����Ƶ��ɣ�";
				   aFlag = false;
				}
				   Thread.sleep(1000);
			}
			//new File(resourcePath).delete();
						
		} catch (Exception e) {
			System.err.println("�����쳣��ת����Ƶʧ�ܣ��쳣��Ϣ��"+e.getMessage());
			result = "�����쳣��ת����Ƶʧ�ܣ�";
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		//VideoConvert.process(GlobalParameter.ReportPath+"test.avi",GlobalParameter.ReportPath);

	}
}
