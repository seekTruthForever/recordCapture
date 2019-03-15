package com.whv.recordCapture.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class SerializeUtil {
	 public static void storeXML(Object obj, OutputStream out)
	   {
	      XMLEncoder encoder = new XMLEncoder(out);
	      encoder.writeObject(obj);
	      encoder.flush();
	      encoder.close();
	   }
	 
	   /**
	    * ��XML�м��ض���
	    * 
	    * @param in
	    * @return
	    */
	   public static Object loadXML(InputStream in)
	   {
	      XMLDecoder decoder = new XMLDecoder(in);
	      Object obj = decoder.readObject();
	      decoder.close();
	      return obj;
	   }
	 
	   /**
	    * �־û�����
	    * 
	    * @param obj
	    * @param out
	    * @throws IOException
	    */
	   public static void store(Object obj, OutputStream out) throws IOException
	   {
	      ObjectOutputStream outputStream = new ObjectOutputStream(out);
	      outputStream.writeObject(obj);
	      outputStream.flush();
	      outputStream.close();
	   }
	 
	   /**
	    * ���ض���
	    * 
	    * @param in
	    * @return
	    * @throws IOException
	    * @throws ClassNotFoundException
	    */
	   public static Object load(InputStream in) throws IOException,
	         ClassNotFoundException
	   {
	      ObjectInputStream inputStream = new ObjectInputStream(in);
	      Object obj = inputStream.readObject();
	      inputStream.close();
	      return obj;
	   }
}
