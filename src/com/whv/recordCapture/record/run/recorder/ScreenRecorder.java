package com.whv.recordCapture.record.run.recorder;

import static java.lang.Math.max;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.whv.recordCapture.frame.BackgroundImage;
import com.whv.recordCapture.record.camera.Capture;
import com.whv.recordCapture.record.constant.Constant;
import com.whv.recordCapture.record.media.MovieWriter;
import com.whv.recordCapture.record.media.avi.AVIWriter;
import com.whv.recordCapture.record.media.color.Colors;
import com.whv.recordCapture.record.media.convert.VideoConvert;
import com.whv.recordCapture.record.media.image.Images;

/**
 * ��Ƶ��ʽ�����ࡣ������֯������������ָ������Ƶ�ļ�
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7������
 *
 */
public class ScreenRecorder {
	private String cursor;
	private int depth = 24;
	private MovieWriter w;
	private long startTime;
	private long time;
	private float screenRate = 15;
	private float mouseRate = 30;
	private int aviSyncInterval = (int) (max(screenRate, mouseRate) * 60);
	private long maxFrameDuration = 1000;
	private BufferedImage screenCapture;
	private List<MouseCapture> mouseCaptures;
	private BufferedImage videoImg;
	private Graphics2D videoGraphics;
	private ScheduledThreadPoolExecutor screenTimer;
	private ScheduledThreadPoolExecutor mouseTimer;
	private BufferedImage cursorImg;
	private Point cursorOffset = new Point(-8, -5);
	private final Object sync = new Object();
	//private Thread audioRunner;
	public static String mediaName;
	private static String videoDir;
	/**
	 * ¼��״̬
	 */
	private static int recordState;
	static{
		File folder = new File(System.getProperty("user.dir")+"\\");
		if (!folder.exists()) {
			folder.mkdirs();
		} 
		videoDir = folder.getAbsolutePath();
	}
	public static void setVideoDir(String path) throws IOException {
		if(path==null || "".equals(path)) return;
		if("/".equals(File.separator)) {
			path = path.replaceAll("\\\\", File.separator);
		}else {
			path = path.replaceAll("/", File.separator);
		}
		if(!path.endsWith(File.separator)) {
			path = path.concat(File.separator);
		}
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		} else if (!folder.isDirectory()) {
			throw new IOException("\"" + folder + "\" is not a directory.");
		}
		videoDir = folder.getAbsolutePath();
	}
	public ScreenRecorder(String mediaName,GraphicsConfiguration cfg, int depth, String cursorStyle, float screenRate, float mouseRate)
			throws IOException, AWTException {
		ScreenRecorder.mediaName = mediaName;
		if (screenRate <= 0)
			throw new IllegalArgumentException("screenRate=" + screenRate + ". Value > 0 required.");
		if (mouseRate <= 0)
			throw new IllegalArgumentException("mouseRate=" + mouseRate + ". Value > 0 required.");
		this.depth = depth;
		this.cursor = cursorStyle;
		this.screenRate = screenRate;
		this.mouseRate = mouseRate;
		aviSyncInterval = (int) (max(screenRate, mouseRate) * 60);
		if (depth == 24) {
			videoImg = new BufferedImage(Constant.WIDTH, Constant.HEIGHT, BufferedImage.TYPE_INT_RGB);
		} else if (depth == 16) {
			videoImg = new BufferedImage(Constant.WIDTH, Constant.HEIGHT, BufferedImage.TYPE_USHORT_555_RGB);
		} else if (depth == 8) {
			videoImg = new BufferedImage(Constant.WIDTH, Constant.HEIGHT, BufferedImage.TYPE_BYTE_INDEXED,
					Colors.createMacColors());
		} else {
			throw new IOException("Unsupported color depth " + depth);
		}
		videoGraphics = videoImg.createGraphics();
		videoGraphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		videoGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		videoGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		mouseCaptures = Collections.synchronizedList(new LinkedList<MouseCapture>());
		if ("BLACK".equals(this.cursor)) {
			cursorImg = Images.toBufferedImage(Images.createImage(Images.class, "Cursor.black.png"));
		} else {
			
			cursorImg = Images.toBufferedImage(Images.createImage(Images.class, "Cursor.white.png"));
		}
		createMovieWriter();
	}

	protected void createMovieWriter() throws IOException {
		
		AVIWriter aviw;
		w = aviw = new AVIWriter(new File(videoDir, mediaName+ ".avi"));
		aviw.addVideoTrack(AVIWriter.VIDEO_JPEG, 1, (int) mouseRate, Constant.WIDTH, Constant.HEIGHT, depth, (int) aviSyncInterval);
		if (depth == 8) {
			aviw.setPalette(0, (IndexColorModel) videoImg.getColorModel());
		}
	}

	public void start() {
		startTime = time = System.currentTimeMillis();
		screenTimer = new ScheduledThreadPoolExecutor(1);
		screenTimer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					if(recordState == Constant.RECORD_PAUSE) {
					}else if(recordState == Constant.RECORD_CONTINUE) {
						recordState = Constant.RECORD_ON;
					}
					if(recordState == Constant.RECORD_ON) {
						grabScreen();
					}
				} catch (Exception ex) {
					//ex.printStackTrace();
				}
			}
		}, (int) (1000 / screenRate), (int) (1000 / screenRate), TimeUnit.MILLISECONDS);
		mouseTimer = new ScheduledThreadPoolExecutor(1);
		mouseTimer.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
					try {
						if(recordState == Constant.RECORD_PAUSE) {
						}else if(recordState == Constant.RECORD_CONTINUE) {
							recordState = Constant.RECORD_ON;
						}
						if(recordState == Constant.RECORD_ON) {
							grabMouse();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}, (int) (1000 / mouseRate), (int) (1000 / mouseRate), TimeUnit.MILLISECONDS);
		recordState = Constant.RECORD_ON;
	}

	public void stop() throws IOException {
		mouseTimer.shutdown();
		screenTimer.shutdown();
		//Thread T = audioRunner;
		//audioRunner = null;
		try {
			mouseTimer.awaitTermination((int) (1000 / mouseRate), TimeUnit.MILLISECONDS);
			screenTimer.awaitTermination((int) (1000 / screenRate), TimeUnit.MILLISECONDS);
/*			if (T != null) {
				T.join();
				
			}*/
		} catch (InterruptedException ex) {
			//ex.printStackTrace();
		}

		synchronized (sync) {			
			w.close();
			w = null;
		}
		videoGraphics.dispose();
		videoImg.flush();
		recordState = Constant.RECORD_OFF;
		System.out.println(mediaName+" ��Ƶ�����ɣ�¼�ƹ�����ֹͣ��");
		
		//System.exit(0);
	}
	public void pause() throws IOException, InterruptedException {
		recordState = Constant.RECORD_PAUSE;
	}
	public void continueRecord() throws IOException, InterruptedException {
		recordState = Constant.RECORD_CONTINUE;
	}
	private void grabScreen() throws Exception {
		screenCapture = Capture.capture();
		long now = System.currentTimeMillis();
		videoGraphics.drawImage(screenCapture, 0, 0, null);
		boolean hasMouseCapture = false;
		if (!"NONE".equals(this.cursor)) {
			Point previous = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
			while (!mouseCaptures.isEmpty() && mouseCaptures.get(0).time < now) {
				MouseCapture pc = mouseCaptures.remove(0);
				if (pc.time > time) {
					hasMouseCapture = true;
					Point p = pc.p;
					p.x -= 0;
					p.y -= 0;
					synchronized (sync) {
						if (!w.isVFRSupported() || p.x != previous.x || p.y != previous.y || pc.time - time > maxFrameDuration) {
							previous.x = p.x;
							previous.y = p.y;
							videoGraphics.drawImage(cursorImg, p.x + cursorOffset.x, p.y + cursorOffset.y, null);
							if (w == null) {
								return;
							}
							try {
								w.writeFrame(0, videoImg, (int) (pc.time - time));
							} catch (Throwable t) {
								System.out.flush();
								t.printStackTrace();
								System.err.flush();
								System.exit(10);
							}
							time = pc.time;
							videoGraphics.drawImage(
									screenCapture,
									p.x + cursorOffset.x,
									p.y + cursorOffset.y,
									p.x + cursorOffset.x + cursorImg.getWidth() - 1,
									p.y + cursorOffset.y + cursorImg.getHeight() - 1,
									p.x + cursorOffset.x,
									p.y + cursorOffset.y,
									p.x + cursorOffset.x + cursorImg.getWidth() - 1,
									p.y + cursorOffset.y + cursorImg.getHeight() - 1,
									null);
						}
						if (w.isDataLimitReached() || now - startTime > 60 * 60 * 1000) {
							System.out.println("ScreenRecorder dataLimit:" + w.isDataLimitReached() + " timeLimit=" + (now - startTime > 60 * 60 * 1000));
							new Thread() {
								@Override
								public void run() {
									try {
										w.close();
									} catch (IOException ex) {
										ex.printStackTrace();
									}
								}
							}.start();
							createMovieWriter();
							startTime = now;
						}
					}
				}
			}
		}
		if (!hasMouseCapture) {
			if (!"NONE".equals(this.cursor)) {
				PointerInfo info = MouseInfo.getPointerInfo();
				Point p = info.getLocation();
				videoGraphics.drawImage(cursorImg, p.x + cursorOffset.x, p.x + cursorOffset.y, null);
			}
			synchronized (sync) {
				w.writeFrame(0, videoImg, (int) (now - time));
			}
			time = now;
		}
	}
	
	private void grabMouse() {
		long now = System.currentTimeMillis();
		PointerInfo info = MouseInfo.getPointerInfo();
		if(info.getLocation().getX()>Constant.RECTX&&info.getLocation().getX()<(Constant.RECTX+Constant.WIDTH)
				&&info.getLocation().getY()>Constant.RECTY&&info.getLocation().getY()<(Constant.RECTY+Constant.HEIGHT))
		mouseCaptures.add(new MouseCapture(now, info.getLocation()));
	}
	
	private static class MouseCapture {

		public long time;
		public Point p;

		public MouseCapture(long time, Point p) {
			this.time = time;
			this.p = p;
		}
	}
}
