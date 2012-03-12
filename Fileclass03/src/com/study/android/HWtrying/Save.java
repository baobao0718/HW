package com.study.android.HWtrying;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.zip.GZIPOutputStream;

import com.study.android.code.MemoryDecoder;
import com.study.android.data.ListTable;
import com.study.android.retrieval.Operationonrretrievalfile;
import com.study.android.retrieval.ShowRetrieve;
import com.study.android.retrieval.Transform;
import com.study.android.ui.HwFunctionWindow;
import com.study.android.util.HwDocument_Temp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

public class Save extends Activity{

	public void onCreate(Bundle savedInstanceState)
	{
		 super.onCreate(savedInstanceState);
	     setContentView(R.layout.third);
	     
	}
	
 
	
    
    //SavetoFile函数用来实现日志文件和索引文件还有配置文件的保存
    //2011.01.10 liqiang
	public static void SavetoFile(String classname)
	{
		boolean FLAG =false;
	     FLAG = WritetoFile(classname);	     
	     if(FLAG)
	     {
	    	 ListTable.numoffile++;
	    	 ListTable.modify = true;
	     }
	     else
	     {
	    	 ListTable.modify = false;
	     }
	     
	     
	     
	     if(ListTable.modify==true && ListTable.edittype ==false)		//ListTable.edittype ==false表示为该日志文件是新建文件
	     {
	    	 //Log.i("zhuxiaoqing hahahahahahzh","BU !!!---<> XIU GAI SUO YIN !!!!!!!!!!!");
	    	 Operationonrretrievalfile operateonretrievalfile = new Operationonrretrievalfile();
	    	 try {
				operateonretrievalfile.savetoretrievalfile(classname);//2011.01.10 liqiang
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ListTable.modify = false;
			ListTable.edittype = false;
	     }
	     
	     else if(ListTable.modify==true && ListTable.edittype ==true)		//这两个条件保证了确实是因为在编辑文件而不用修改索引！！
	     {
//	    	 Log.i("DEBUG----->","BU !!!---<> XIU GAI SUO YIN !!!!!!!!!!!");
	    	 ListTable.modify = false;
			 ListTable.edittype = false;
	     }
	     
	     ListTable.serianumberoffilelistoffile =0;					//在文件保存成功后，计数清为0
	     ListTable.editserionumber = 0;
	     
	     //2011.01.10 liqiang
	     WritetoConfigInfFile();
	
	}
	
	//2011.01.10 liqiang 保存日志文件
	public static boolean WritetoFile(String classname)									//把所有的信息保存到文件中
	{
		 HwDocument_Temp hwdocument = new  HwDocument_Temp();
		 String file="";
		 
		 //File filedir =new File("/sdcard/eFinger/AhweFile");
		 
		// if(filedir.exists()==false)
		//	 filedir.mkdirs();
		 
		 
		 if(ListTable.edittype ==false)
		 {
			 Calendar cal = Calendar.getInstance();//zhuxiaoqing 2011.08.23
			  
			  int year = cal.get(Calendar.YEAR);//年
			  int month = cal.get(Calendar.MONTH);//月
			  int date = cal.get(Calendar.DATE);//日
			  
			  int hour = cal.get(Calendar.HOUR);//时
			  int minute = cal.get(Calendar.MINUTE);//分
			  int second = cal.get(Calendar.SECOND);//秒


		 ListTable.serianumberoffilelistoffile+=1;							//因为如果真要新建文件，序号会再增长1
		 ListTable.presentfilenum=ListTable.serianumberoffilelistoffile;
		 //file ="/sdcard/eFinger/"+classname+"/AhweFile/"+ListTable.serianumberoffilelistoffile;
		 //file+=".hwep";
		 file="/sdcard/eFinger/"+classname+"/AhweFile/"+"ad"+year+""+month+""+date+""+hour+""+minute+""+second+".hwep";
		 ListTable.filename="";//zhuxiaoqing 2011.08.21
		 //ListTable.filename=""+ListTable.serianumberoffilelistoffile+".hwep";
		 ListTable.filename="ad"+year+""+month+""+date+""+hour+""+minute+""+second+".hwep";
//		 Log.i("zhuxiaoqing writetofile-- filename",""+ListTable.filename);
		 hwdocument.setPath(file);	
		
		 boolean flg = hwdocument.saveDocument();
		 return flg;	
		 
		 }
		 else
		 {
			 //file ="/sdcard/eFinger/"+classname+"/AhweFile/"+ListTable.editserionumber;
			// file+=".hwep";	
			 file ="/sdcard/eFinger/"+classname+"/AhweFile/"+ListTable.editfilename;//zhuxiaoqing 2011.08.21
			 ListTable.presentfilenum=ListTable.editserionumber;
			 hwdocument.setPath(file);
			 boolean flg=hwdocument.saveDocument();
			 return flg;
		 }
		 				
	}
	
	
	//2011.01.10 liqiang
	public static void WritetoConfigInfFile() 					//将配置信息写入配置文件中
	{	
		
		//File filedir =new File("/sdcard/eFinger/ConFigure");
		 
		// if(filedir.exists()==false)
		//	 filedir.mkdirs();
		 
		String InfFilePath ="/sdcard/eFinger/ConFigure/ConfigInfFile.inf";
		File Inffile = new File(InfFilePath);
		
		BufferedOutputStream outputStream = null;
		float version = (float) 1.14;
		
		if(Inffile.exists()==true)							//文件存在
		{
			if(true == Inffile.delete())
			{
				
				try {
					outputStream = new BufferedOutputStream(
						new FileOutputStream(Inffile));	
				} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}

				saveconfiginftofile(outputStream);

			}
		}
		else
		{
			try {
				Inffile.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}    
			
			try {
				outputStream = new BufferedOutputStream(
					new FileOutputStream(Inffile));	
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			
	
			if(ListTable.version==0||ListTable.writespeed==0||ListTable.characterstrokewidth==0||ListTable.shuxiejizhun==0||ListTable.charactersize==0||ListTable.spacewidth==0)
			{
				ListTable.version = version;
				ListTable.writespeed = 780 ;
				ListTable.characterstrokewidth =4 ;	
				ListTable.shuxiejizhun =56;
				ListTable.charactersize =40;
				ListTable.spacewidth =8;
				ListTable.bgcolor =Color.WHITE;
				ListTable.charactercolor = Color.BLACK;
				ListTable.isAutoSpaceValue=0;								//默认为 中文
				ListTable.isAutoSubmit =0;
			}
			
			
			saveconfiginftofile(outputStream);
			/*
			try {
				outputStream.write(Transform.floattoBytes(ListTable.version));
				outputStream.write(Transform.InttoBytes(ListTable.writespeed));
				outputStream.write(Transform.InttoBytes(ListTable.characterstrokewidth));
				outputStream.write(Transform.InttoBytes(ListTable.shuxiejizhun));
				outputStream.write(Transform.InttoBytes(ListTable.charactersize));
				outputStream.write(Transform.InttoBytes(ListTable.spacewidth));
				outputStream.write(Transform.InttoBytes(ListTable.bgcolor));
				outputStream.write(Transform.InttoBytes(ListTable.isAutoSpaceValue));
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			
		}
	}
	
	
	public static void saveconfiginftofile(BufferedOutputStream outputStream)
	{
		try {
	
		outputStream.write(Transform.floattoBytes(ListTable.version));
		outputStream.write(Transform.InttoBytes(ListTable.writespeed));
		outputStream.write(Transform.InttoBytes(ListTable.characterstrokewidth));
		outputStream.write(Transform.InttoBytes(ListTable.shuxiejizhun));
		outputStream.write(Transform.InttoBytes(ListTable.charactersize));
		outputStream.write(Transform.InttoBytes(ListTable.spacewidth));
		outputStream.write(Transform.InttoBytes(ListTable.charactercolor));//2011.10.19 liqiang
		outputStream.write(Transform.InttoBytes(ListTable.backgroundimgid));//2011.12.21 liqiang
		outputStream.write(Transform.InttoBytes(ListTable.bgcolor));
		outputStream.write(Transform.InttoBytes(ListTable.isAutoSpaceValue));
		outputStream.write(Transform.InttoBytes(ListTable.isAutoSubmit));
		
		
	//	Log.i("DEBUG--->","VERSION  "+ListTable.version);
	//	Log.i("DEBUG--->","SPEED  "+ListTable.writespeed);
	//	Log.i("DEBUG--->","STROKEWIDTH  "+ListTable.characterstrokewidth);
	//	Log.i("DEBUG--->","JIZHUN  "+ListTable.shuxiejizhun);
	//	Log.i("DEBUG--->","CHARSIZE  "+ListTable.charactersize);
	//	Log.i("DEBUG--->","SPACEWIDTH  "+ListTable.spacewidth);
		Log.i("DEBUG--->","BGCOLOR  "+ListTable.charactercolor);
		Log.i("DEBUG--->","BGCOLOR  "+ListTable.bgcolor);
		Log.i("DEBUG--->","BGCOLOR  "+ListTable.backgroundimgid);
	//	Log.i("DEBUG--->","AUTOSPACE  "+ListTable.isAutoSpaceValue);
		
		outputStream.flush();
		outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
