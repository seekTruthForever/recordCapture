package com.whv.recordCapture.record.run.recorder;

import java.util.Scanner;

import com.whv.recordCapture.record.run.recorder.ScreenRecorderMain;

public class StartRecorder {

	public static void main(String[] args) {
		ScreenRecorderMain main = new ScreenRecorderMain();
		main.start("test");
//		while(true){
			System.out.println("ÄãÒªÍ£Ö¹Âğ£¿ÇëÊäÈë(stop)£¬³ÌĞò»áÍ£Ö¹¡£");
			Scanner sc = new Scanner(System.in);
			if (sc.next().equalsIgnoreCase("stop")) {
				main.stop();
			}else{
				System.out.println("Input errors,Please re-enter!!");
			}
//		}
	}
}
