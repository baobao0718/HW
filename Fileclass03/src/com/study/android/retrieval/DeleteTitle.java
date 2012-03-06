package com.study.android.retrieval;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.util.Log;

public class DeleteTitle {
		
	public static void delete(int addressinBody,String classname) throws IOException		//addressinBody 是当月要修改记录在Body的首地址
	{
		try {
			File filedir =new File("/sdcard/eFinger/"+classname+"/Retrieve/");
			 
			 if(filedir.exists()==false)
				 filedir.mkdirs();
			
			 Log.v("DEBUG--->","DeleteTitle *********   "+addressinBody);
			RandomAccessFile s=new RandomAccessFile("/sdcard/eFinger/"+classname+"/Retrieve/retrievalfile.retrie","rw");	//以读写进行操作
			s.skipBytes(addressinBody);	//跳过前面这么多字节后，刚好到达要修改那月的记录在Body中的首地址
			
			
			//对第recordnum（序号为《recordnum-1》）个记录单独处理
			byte[] bytecharacternumoftitletemp = new byte[4];
			
			s.read(bytecharacternumoftitletemp);
			s.write(Transform.InttoBytes(0));
			
			s.close();
			ShowRetrieve.locationshowofbutton =-1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
}
