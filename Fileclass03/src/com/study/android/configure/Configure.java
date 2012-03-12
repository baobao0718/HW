package com.study.android.configure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;

import com.study.android.Category.Fileclass;
import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.PView;
import com.study.android.HWtrying.R;
import com.study.android.HWtrying.Save;
import com.study.android.HWtrying.WritebySurfaceView;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.type.CharUnit;
import com.study.android.data.ListTable;
import com.study.android.retrieval.Operationonrretrievalfile;
import com.study.android.retrieval.ShowRetrieve;
import com.study.android.zoom.Zoom;

public class Configure extends ListActivity implements OnTouchListener, OnGestureListener
{
	Button buttonshezhiback;
	float version = (float) 1.14;
	String filepath;//zhuxiaoqing 2011.08.20
	String classname;
	Drawable backgrounddrawable;//2011.09.29 liqiang
	
	static LinkedList list = new LinkedList();//zhuxiaoqing 2011.11.09

	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        ListTable.activityTable.add(this);//2011.06.28 liqiang
	       // Log.i("DEBUG--->","ZENM HUI SHI???");
	        Intent conf = getIntent();
	        //Log.i("debug","classname is "+classname);
	        String InfFilePath ="/sdcard/eFinger/ConFigure/ConfigInfFile.inf";
//	        Log.i("debug","path in configer is "+InfFilePath);
	        //filepath="/sdcard/eFinger/"+classname+"/AhweFile";//zhuxiaoqing 2011.08.20
	        File file=new File("/sdcard/eFinger/");
	        File []filetemp=file.listFiles();
	        for (int i = 0; i < filetemp.length; i++) { 

	        	if (filetemp[i].isDirectory()&&(filetemp[i].toString().equals("/sdcard/eFinger/ClassInfo")==false)&&(filetemp[i].toString().equals("/sdcard/eFinger/ConFigure")==false)) { 

	        		String strFileName = filetemp[i].getAbsolutePath();
	        		//String strFileName = filetemp[i].;
	        		list.add(filetemp[i]);
//		        	System.out.println("---"+i+"mingzi"+strFileName);

	        	} 

	        	

	        	//filetemp.add(filetemp[i].getAbsolutePath()); 

	        }	


	        
	        
	        
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
			}
			
			backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);//2011.09.29 liqiang
			this.getWindow().setBackgroundDrawable(backgrounddrawable);//2011.09.29 liqiang
			
	        setContentView(R.layout.shezhi);	
	        
	        ConfigAdapter myadapter = new ConfigAdapter(this);
			this.setListAdapter(myadapter);
			
		
	}
	
	public static void ReBuildIndexFile()
	{
		
		String filepathname;
		for(int m=0;m<list.size();m++){
			filepathname=list.get(m).toString();
			System.out.println("!!xiufu"+filepathname);
			//filepathname+="/AhweFile";
		File file=new File(filepathname+"/AhweFile");
		File[] subfile;
		//File retrievalfile = new File("/sdcard/eFinger/"+classname+"/Retrieve/retrievalfile.retrie");
		File retrievalfile = new File(filepathname+"/Retrieve/retrievalfile.retrie");
		if(retrievalfile.exists()==true)
		{
			retrievalfile.delete();
			
		}
		if(file.exists())
		{
//			 System.out.println("zhuxiaoqing 1");
			subfile=file.listFiles();
			for(int k=0;k<subfile.length;k++)
			{
				ListTable.globalTitle.clear();
				 String filename = subfile[k].getName();
//				 Log.i("zhuxiaoqing shujuxiufu--->>.",""+filename);
				// String[] filename2;
				// filename2=filename.trim().split(".");
				 ListTable.filename="";
				 ListTable.filename=filename;
				// ListTable.filename=filename2[0];
//				 Log.i("zhuxiaoqing shujuxiufu ListTABLE--->>.",""+ListTable.filename.length());
				 File da=new File(filepathname+"/AhweFile/"+filename);
//				 Log.i("zhuxiaoqing wenjianlujing",""+da );
				 WritebySurfaceView.readDataFromFile(da);//读取日志文件
				 //ShowRetrieve.readDataFromFileForTitle(da);
//				 Log.v("DEBUG--->shujuxiufu","globalIndexTable  size---+++  "+ListTable.globalIndexTable.size());
				if (ListTable.globalIndexTable.size() > 0)
					{
						if (ListTable.globalTitle.size() == 0)
						{
							//for (int i = 0; i != ListTable.globalIndexTable.size(); i++) // 
							for(int i=0;i<ListTable.titlenumber;i++)
							{

								DataUnit dataunit = (DataUnit) ListTable.globalIndexTable
										.get(i);
								if(dataunit.getCtrlType()==DataType.TYPE_CTRL_ENTER)
									{
//									System.out.println("zhuxiaoqing2");
									break;}
								if ((dataunit.getDataType() != DataType.TYPE_CONTROL))
								{
									// CharUnit charunitnew
									// =HWtrying.compression(dataunit,(short)40);
									// //不管我们设定的字的高度是多少，但对于索引里面的字的高度统一为40
									CharUnit charunitnew = Zoom.compression(
											dataunit, (short) 40);
									ListTable.globalTitle.add(charunitnew);
									
									//if(dataunit.getDataType()!=DataType.TYPE_CTRL_ENTER)
//									Log.i("zengjia--","--");
								} else if (dataunit.getCtrlType() == DataType.TYPE_CTRL_SPACE)
								{
									;
								}
							}
						}
						
						ListTable.modify=true; 
//						Log.v("DEBUG---> xiufu","title  size---+++  "+ListTable.globalTitle.size());
						 Operationonrretrievalfile operateonretrievalfile = new Operationonrretrievalfile();
				    	 try {
//				    		 System.out.println("classname&&zhuxiaoqing"+filepathname.substring(16));
//				    		 System.out.println("##zhuxiaoqing"+ListTable.globalTitle.size());
							operateonretrievalfile.savetoretrievalfile(filepathname.substring(16));//
							ListTable.globalTitle.clear();
							ListTable.globalIndexTable.clear();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else
						;
				
			}
		}
		System.out.println("okok");
		}
//		Thread th = new Thread();
//		try
//		{
//			th.sleep(3000);
//		} catch (InterruptedException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		ListTable.xiufu=false;
		System.out.println("okok2");
	}
	public class PThread extends Thread
	{
		public void run()
		{
			ReBuildIndexFile();
			processdlg.dismiss();
		}
	}
	Handler handler = new Handler()
	{
		 public void handleMessage(Message msg) {  
	            switch (msg.what) {  
	            case 1:  
	            	processdlg.show();
	            	PThread pt = new PThread();
	            	pt.start();
	            	
	                break;  
	            }  
	        };  
	};
	Dialog processdlg;
	protected void onListItemClick(ListView l,View v,int position,long id)
	{
		super.onListItemClick(l, v, position, id);
//		Log.i("DEBUG-->"," "+position);
		Intent todointent = null;
		
		switch (position)
		{
			case 0:
				todointent = new Intent(Configure.this,SystemAbout.class); 
				
				break;
			case 1:
				todointent= new Intent(Configure.this,InputformatConfigure.class); 
				
				break;
			case 2:
				todointent= new Intent(Configure.this,InputCharacterConfigure.class); 
				break;
			case 3:
				todointent= new Intent(Configure.this,InputBgColorConfigure.class); 
				break;
			case 4:
				//todointent= new Intent(Configure.this,InputBgColorConfigure.class); 
				ListTable.xiufu=true;//zhuxiaoqing 2011.11.17
				System.out.println("@@@@@@@@@@@@@xiufu wei chu");
				processdlg = new Dialog(Configure.this);
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.processdlg,
					     (ViewGroup)findViewById(R.id.proglayout));
				processdlg.setContentView(layout);
				processdlg.setTitle("正在修复");
				layout.setVisibility(View.VISIBLE);
				processdlg.show();
				Message mes = new Message();
				mes.what = 1;
				handler.sendMessage(mes);
				//ListTable.xiufu=false;
//				Log.i("zhuxiaoqing","duihuakuang");
				/**/
				
					
				
				
			default:
				break;
	}
		if(position!=4)
			startActivity(todointent);
//		this.finish();
	}
	

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
          
        //按下键盘上返回按钮  
        if(keyCode == KeyEvent.KEYCODE_BACK){  
//        	Log.i("DEBUG-->","KEYBACK!!!");
        	 //Intent help = new Intent(Configure.this,ShowRetrieve.class);        //打开网页链接
	        //   startActivity(help);
        	
        	//onDestroy();  
        	
        	//或者下面这种方式  
        	//android.os.Process.killProcess(android.os.Process.myPid());  
        	Save.WritetoConfigInfFile(); // 2011.10.14 liqiang
        	this.backgrounddrawable = null;
        	//zhuxiaoqing 2011.08.23
        	Intent fanhui = new Intent(Configure.this,Fileclass.class);        //打开网页链接
        	
  			startActivity(fanhui);
  			Configure.this.finish();
        	 return true;
        }else{  
            return super.onKeyDown(keyCode, event);   
        }  
        }
  /*  @Override
    protected void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();
    System.exit(0);
    }
    */
    
    
    short tempwidth,tempheight;
    
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { 
        	tempwidth = Kview.screenwidth>Kview.screenheight?Kview.screenwidth:Kview.screenheight;
			tempheight =Kview.screenwidth<Kview.screenheight?Kview.screenwidth:Kview.screenheight;
			Kview.screenwidth = tempwidth;
			Kview.screenheight = tempheight;
			
			tempwidth=0;
			tempheight=0;
			
			//PView用于读取，Kview用于写入
			tempwidth = PView.screenwidth>PView.screenheight?PView.screenwidth:PView.screenheight;
			tempheight =PView.screenwidth<PView.screenheight?PView.screenwidth:PView.screenheight;
			PView.screenwidth = tempwidth;
			PView.screenheight = tempheight;
			
			} 
			else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { 
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
        
        
        setContentView(R.layout.shezhi);
        backgrounddrawable = null;
        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
        this.getWindow().setBackgroundDrawable(backgrounddrawable);//2011.09.29 liqiang
    }

}

