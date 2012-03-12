package com.study.android.configure;

import java.io.File;

public class CreatPath {

	//2011.01.10  liqiang
	public static void MakeFileDir(String classname)
    {
    	File filedir =new File("/sdcard/eFinger/"+classname+"/AhweFile");
		 
		 if(filedir.exists()==false)
			 filedir.mkdirs();
		 
		 filedir =new File("/sdcard/eFinger/"+classname+"/Retrieve");
		 
		 if(filedir.exists()==false)
			 filedir.mkdirs();
		 
		 filedir =new File("/sdcard/eFinger/"+classname+"/ConFigure");
		 
		 if(filedir.exists()==false)
			 filedir.mkdirs();
		 
		 
		 filedir =new File("/sdcard/eFinger/"+classname+"/MailImage");
		 
		 if(filedir.exists()==false)
			 filedir.mkdirs();
		 filedir =new File("/sdcard/eFinger/"+classname+"/Export");
		 
		 if(filedir.exists()==false)
			 filedir.mkdirs();
    }
}