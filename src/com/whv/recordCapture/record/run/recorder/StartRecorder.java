package com.whv.recordCapture.record.run.recorder;

import java.util.Scanner;

import com.whv.recordCapture.record.run.recorder.ScreenRecorderMain;

public class StartRecorder {

	public static void main(String[] args) {
		ScreenRecorderMain main = new ScreenRecorderMain();
		main.start("test");
//		while(true){
			System.out.println("��Ҫֹͣ��������(stop)�������ֹͣ��");
			Scanner sc = new Scanner(System.in);
			if (sc.next().equalsIgnoreCase("stop")) {
				main.stop();
			}else{
				System.out.println("Input errors,Please re-enter!!");
			}
//		}
	}
}
