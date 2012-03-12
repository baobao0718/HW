package com.study.android.HWtrying;

import com.study.android.basicData.BasePoint;
import com.study.android.code.DocumentDecoder;
import com.study.android.configure.CreatePath;
import com.study.android.configure.DefaultConfigure;
import com.study.android.data.ListTable;
import com.study.android.retrieval.ShowRetrieve;
import com.study.android.ui.HwFunctionWindow;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
public class HWtrying extends Activity {
   
	/** Called when the activity is first created. */
	private static DocumentDecoder documentDecoder;
	private static double ratioI=0.5;
	public ArrayList<BasePoint> array;
	public static int tempclickbrowsenumber=0;
	private int serionumber =-1;
	private Button button_back;
	private Button button_edit;
	private Button button_mail;
	public static short BASEHEIGHT = 0;
	private float version = (float) 1.14;	
	public static boolean pviewtokview=false;
	private String classname = "";//2011.01.07  liqiang

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //2010.01.07 liqiang
        Intent intent =getIntent();
        Bundle bundle =intent.getExtras();
		serionumber = bundle.getInt("numberoffile");
		tempclickbrowsenumber = bundle.getInt("clickbrowsenumber");
		
        classname= bundle.getString("classname");
//        Log.i("hwtrying classname is ",""+classname);
        CreatePath.MakeFileDir(classname);
       	
        String InfFilePath ="/sdcard/eFinger/ConFigure/ConfigInfFile.inf";
        
		File Infofile = new File(InfFilePath);
		
		BufferedInputStream inputStream = null;
		
		if(ListTable.configinfread==false)
		{
			
			if(Infofile.exists()==true )							//文件存在,且未读取过
			{
				try {
				inputStream = new BufferedInputStream(
						new FileInputStream(Infofile));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				WritebySurfaceView.readConfigureinf(inputStream);
				if(ListTable.version!=version)
					DefaultConfigure.defaultconfigure(version);
			}
			else if(Infofile.exists()==false)
				DefaultConfigure.defaultconfigure(version);
		
			ListTable.configinfread=true;
			BASEHEIGHT = (short) ListTable.charactersize;
		}
		
		
		if(ListTable.isAutoSpaceValue==1)
			ListTable.isAutoSpace=true;
		else
			ListTable.isAutoSpace=false;
		
		

        setContentView(R.layout.scroltry);
        
        button_back = (Button) findViewById(R.id.buttonback);
        button_edit = (Button) findViewById(R.id.buttonedit);
        button_mail = (Button) findViewById(R.id.buttonmail);
        int color = ListTable.bgcolor;
        TableLayout linearLayout = (TableLayout) findViewById(R.id.tablelayout1);
        linearLayout.setBackgroundColor(color);
       
		
       // File fhwe = new File("/data/data/com.study.android.HWtrying/files/11.hwe"); //文件必须以这样的格式读取 /data/data/包/文件。。。与JAVA区别的地方,这个文件放在File Explorer中，相当于手机上的文件系统！！在Windows－－》showview－》other－》File Explorer即可看到
		/*Intent newintent =getIntent();
		
		Bundle newbundle = newintent.getExtras();
		serionumber = newbundle.getInt("numberoffile");
		tempclickbrowsenumber = newbundle.getInt("clickbrowsenumber");*/
        
        //2011.01.11 liqiang
		String file ="/sdcard/eFinger/"+classname+"/AhweFile/"+ serionumber;
		file += ".hwep";
		File fhwe = new File(file);
//		Log.i("debug","file path is "+file);
        Log.v("DEBUG!-->","WENJIANCHANGDUfhwe:"+fhwe.length());
        readDataFromFile(fhwe);//读日志信息
        
        
        
        
        
        button_edit.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
    		{
    			 Intent backintent=new Intent();
    			 backintent.setClass(HWtrying.this,WritebySurfaceView.class); 
//    			Log.i("debug","edit button is click in hwtrying !!");
    			 Bundle bundle = new Bundle();
    			 bundle.putString("Type","Edit");
    			 bundle.putString("classname",classname);//2011.01.10 liqiang
    			 bundle.putInt("serionumber",serionumber);
    			
    			 backintent.putExtras(bundle);
    			 startActivity(backintent);
    			//2011.05.26 liqiang 
    			 HWtrying.this.finish();
		        //2011.05.26 liqiang
    		}
    	}
    	);
        
        button_mail.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
    		{
    			
    	
    		/*
    			File f = new File("/sdcard/eFinger/MailImage/0.png");
                try {
					f.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                FileOutputStream fOut = null;
                try {
                        fOut = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
                ListTable.bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                
                ListTable.bitmap.recycle();
                
                
                try {
                        fOut.flush();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                try {
                        fOut.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
               */
    			
    			sendmail(classname);//2011.01.10 liqiang
                Intent mailintent=new Intent();
                mailintent.setClass(HWtrying.this,Mailretry.class);
                startActivity(mailintent); 
                //2011.05.26 liqiang 
   			    HWtrying.this.finish();
		        //2011.05.26 liqiang
    		}	  
    	}
    	);
    }
   

	 
    short tempwidth,tempheight;
    
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { 
			
        	tempwidth=0;
			tempheight=0;
			
        	tempwidth = Kview.screenwidth>Kview.screenheight?Kview.screenwidth:Kview.screenheight;
			tempheight =Kview.screenwidth<Kview.screenheight?Kview.screenwidth:Kview.screenheight;
			Kview.screenwidth = tempwidth;
			Kview.screenheight = tempheight;
			
			tempwidth=0;
			tempheight=0;
			
        	tempwidth = PView.screenwidth>PView.screenheight?PView.screenwidth:PView.screenheight;
			tempheight =PView.screenwidth<PView.screenheight?PView.screenwidth:PView.screenheight;
			PView.screenwidth = tempwidth;
			PView.screenheight = tempheight;
			} 
			else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { 
				
				tempwidth=0;
				tempheight=0;
				
				tempwidth = Kview.screenwidth<Kview.screenheight?Kview.screenwidth:Kview.screenheight;
				tempheight =Kview.screenwidth>Kview.screenheight?Kview.screenwidth:Kview.screenheight;
				Kview.screenwidth = tempwidth;
				Kview.screenheight = tempheight;
				
				tempwidth=0;
				tempheight=0;
				
				
				tempwidth = PView.screenwidth<PView.screenheight?PView.screenwidth:PView.screenheight;
				tempheight =PView.screenwidth>PView.screenheight?PView.screenwidth:PView.screenheight;
				PView.screenwidth = tempwidth;
				PView.screenheight = tempheight;
			}
      

        setContentView(R.layout.scroltry);
        

        int color = ListTable.bgcolor;
        TableLayout linearLayout = (TableLayout) findViewById(R.id.tablelayout1);
        linearLayout.setBackgroundColor(color);
        button_edit = (Button) findViewById(R.id.buttonedit);
        button_mail = (Button) findViewById(R.id.buttonmail);
        
        
      
        button_edit.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
    		{
    			 Intent backintent=new Intent();
    			 backintent.setClass(HWtrying.this,WritebySurfaceView.class); 
    			 pviewtokview=true;
    			 Bundle bundle = new Bundle();
    			 bundle.putString("Type","Edit");
    			 bundle.putString("classname",classname);//2011.01.10 liqiang
    			 bundle.putInt("serionumber",serionumber);
    			
    			 backintent.putExtras(bundle);
    			 startActivity(backintent);
    			//2011.05.26 liqiang 
    			 HWtrying.this.finish();
		        //2011.05.26 liqiang
    		}
    	}
    	);
        
        button_mail.setOnClickListener(new OnClickListener(){
    		public void onClick(View v)
    		{
    			PView pview=(PView)findViewById(R.id.pviewid);
    			pview.canvastobitmap();
		
    			sendmail(classname);//2011.01.10 liqiang
                Intent mailintent=new Intent();
                mailintent.setClass(HWtrying.this,Mailretry.class);
                startActivity(mailintent);  
              //2011.05.26 liqiang 
   			 HWtrying.this.finish();
		        //2011.05.26 liqiang
    		}   		  
    	}
    	);

    }
    /**
     * 读日志信息
     * @param abstractFile 文件路径
     */
    public static void readDataFromFile(File abstractFile) {
		BufferedInputStream inputStream;
		ListTable.sectionTable.clear();
		ListTable.globalIndexTable.clear();
		ListTable.paragraphTable.clear();
		HwFunctionWindow.colortable.clear();
		HwFunctionWindow.Initcolortable();
		ListTable.Sectionnum =0;
		ListTable.temppositionofaddcharunit =0;			//这里之所以要用到这个，主要是为了对读取的当前文件做编辑处理的时候作铺垫，因为编辑的时候需要确定下一个单元应该添加的位置，就由该变量来表示

		//Log.v("DEBUG--->","colortable.size"+HwFunctionWindow.colortable.size());
		try {
			inputStream = new BufferedInputStream(new FileInputStream(abstractFile));
			//inputStream =new BufferedInputStream(abstractFile);
			//GZIPInputStream gin = new GZIPInputStream(inputStream);       //文件是按一定格式压缩了的，所以读出时候必须用GZIPInputStream解压，压缩用GZIPOutputStream
			//documentDecoder = new DocumentDecoder(gin); 
			documentDecoder = new DocumentDecoder(inputStream); 
			documentDecoder.headDecoder();				//读取文件的头文件
			documentDecoder.dataDecoder();				//读取文件的数据文件
			//gin.close();
			inputStream.close();		
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}
    
    
    public static void readDataFromFileForSample(InputStream abstractStream) {
		BufferedInputStream inputStream;
		ListTable.sectionTable.clear();
		ListTable.globalIndexTable.clear();
		ListTable.paragraphTable.clear();
		HwFunctionWindow.colortable.clear();
		HwFunctionWindow.Initcolortable();
		ListTable.Sectionnum =0;
		ListTable.temppositionofaddcharunit =0;			//这里之所以要用到这个，主要是为了对读取的当前文件做编辑处理的时候作铺垫，因为编辑的时候需要确定下一个单元应该添加的位置，就由该变量来表示

		//Log.v("DEBUG--->","colortable.size"+HwFunctionWindow.colortable.size());
		try {
			inputStream = new BufferedInputStream(abstractStream);
			//inputStream =new BufferedInputStream(abstractFile);
			//GZIPInputStream gin = new GZIPInputStream(inputStream);       //文件是按一定格式压缩了的，所以读出时候必须用GZIPInputStream解压，压缩用GZIPOutputStream
			//documentDecoder = new DocumentDecoder(gin);
			documentDecoder = new DocumentDecoder(inputStream);
			documentDecoder.headDecoder();				//读取文件的头文件
			documentDecoder.dataDecoder();				//读取文件的数据文件
			//gin.close();
			inputStream.close();		
		}catch (IOException e) {
			e.printStackTrace();
		}	
	}
    
    
    
    
    
    @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	          
	        //按下键盘上返回按钮  
	        if(keyCode == KeyEvent.KEYCODE_BACK){  
//	        	Log.i("DEBUG-->","HWtrying KEYBACK!!!");
	        	 if(ListTable.bitmap!=null&&!ListTable.bitmap.isRecycled())										//资源回收，以免占用内存以及良好的编程习惯，android中，图片占用超过6M,即报内存溢出异常
	 		    	ListTable.bitmap.recycle();
	        	ListTable.boolflag=true;
	        	Bundle bundle = new Bundle();
	        	bundle.putString("classname",classname);//2011.01.10  liqiang
	    		bundle.putInt("clickbrowsenumber",tempclickbrowsenumber);	    		
	        	Intent intent = new Intent(HWtrying.this,ShowRetrieve.class);
	        	intent.putExtras(bundle);
				startActivity(intent);
				//2011.05.26 liqiang 
   	    		 HWtrying.this.finish();
		        //2011.05.26 liqiang
	        	return true;
       }else{  
           return super.onKeyDown(keyCode, event);  
       }  
    }
    
    public static void sendmail(String classname)//2011.01.10 liqiang 
    {
    	File f = new File("/sdcard/eFinger/"+classname+"/MailImage/0.png");
        try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        FileOutputStream fOut = null;
        try {
                fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
        if(ListTable.bitmap!=null&&!ListTable.bitmap.isRecycled())
        	ListTable.bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        
        //ListTable.bitmap.recycle();
        if(ListTable.bitmap!=null&&!ListTable.bitmap.isRecycled())										//资源回收，以免占用内存以及良好的编程习惯，android中，图片占用超过6M,即报内存溢出异常
			 ListTable.bitmap.recycle();
        
        
        try {
                fOut.flush();
        } catch (IOException e) {
                e.printStackTrace();
        }
        try {
                fOut.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

}