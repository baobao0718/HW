package com.study.android.retrieval;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ListView;

import com.study.android.Category.Fileclass;
import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.PView;
import com.study.android.HWtrying.R;
import com.study.android.HWtrying.Save;
import com.study.android.HWtrying.WritebySurfaceView;
import com.study.android.basicData.DataUnit;
import com.study.android.code.DocumentDecoder;
import com.study.android.configure.CreatePath;
import com.study.android.data.ListTable;
import com.study.android.ui.HwFunctionWindow;

public class ShowRetrieve extends ListActivity implements OnTouchListener, OnGestureListener
//public class ShowRetrieve extends ListActivity
{
	
	private DocumentDecoder documentDecoder;
	private int count=0;
	private static int numofmonth =0;
	private static int clickbrowsenumber =0;
	private static int tempclickbrowsenumber=0;
	private static ArrayList<ArrayList<DataUnit>> globalTitleList= new ArrayList<ArrayList<DataUnit>>();
	private BufferedInputStream inputstream = null;
//	private GestureDetector mGestureDetector; //2011.11.30 liqiang 响应这个事件后滑动方式会改变，长按之后才能上下滑动 
	private static int itemid=-1;
	public static int locationshowofbutton =-1;
	private Button button_browse_back;
	private Button button_browse_front;
	private Button button_new;
	private Button buttondelete;
	private Button button_delete;//2011.06.09 liqiang 
	private Drawable landbackgroundimage,portbackgroundimage,lastmonthimage,nextmonthimage,
	createfileimage,deleteimage,nullimage = null;
	//public static boolean configinfread = false;			    //用于记录是否读取了配置信息
	private String classname = "";// 2011.01.07 liqiang
	ListView templistview;//2011.06.14 liqiang
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	     // 2011.01.07 liqiang
			Intent intent =getIntent();
	        Bundle bundle = intent.getExtras();
			classname = bundle.getString("classname");// 获得传输来的类名
//			Log.i("zzzzzzzshowRetrievezzzz"," classname "+classname);
			
			
			CreatePath.MakeFileDir(classname);//zhuxiaoqing 2011.08.21
			if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
			{			
				
				int tempwidth = ListTable.ScreenWidth,tempheight = ListTable.ScreenHeight;
				ListTable.ScreenHeight = (short) (tempwidth>tempheight?tempwidth:tempheight); 
				ListTable.ScreenWidth = (short) (tempwidth<tempheight?tempwidth:tempheight);
				Retrieveview.retrieveviewleftmargin = (int) (ListTable.ScreenWidth*0.33);
				Log.i("debug in showretrieve port ","retrieveviewleftmargin is "+Retrieveview.retrieveviewleftmargin);
			}
			else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
			{
				
				int tempwidth = ListTable.ScreenWidth,tempheight = ListTable.ScreenHeight;
				ListTable.ScreenHeight = (short) (tempwidth<tempheight?tempwidth:tempheight); 
				ListTable.ScreenWidth = (short) (tempwidth>tempheight?tempwidth:tempheight);
				Retrieveview.retrieveviewleftmargin = (int) (ListTable.ScreenWidth*0.22);
				Log.i("debug in showretrieve port ","retrieveviewleftmargin is "+Retrieveview.retrieveviewleftmargin);
			}
			
//			if(Kview.screenheight>Kview.screenwidth)
//				Retrieveview.retrieveviewleftmargin = (int) (Kview.screenwidth*0.3);
//			else
//				Retrieveview.retrieveviewleftmargin = (int) (Kview.screenwidth*0.25);
	        ListTable.imagepaint = new Paint();
	        ListTable.imagepaint.setColor(Color.GREEN);			//初始化图片画笔的颜色！！！
	        ListTable.configinfread = false	;		
	        
			//以上两行功能一样
	        setContentView(R.layout.retrieve);	
	        templistview = this.getListView();
	        clearDrawable();
	        initDrawable();
	        findView();//按钮的初始化
	        this.getWindow().setBackgroundDrawable(landbackgroundimage);
//	        mGestureDetector = new GestureDetector(this); 
//	        mGestureDetector.setIsLongpressEnabled(true);  
	        this.getListView().setOnTouchListener(this);  			//把监听器放在整个listview上，这只是权宜之际
	        
	        //this.getListView().setDivider(getResources().getDrawable( 
           //         R.drawable.green)); 
	        this.getListView().setDividerHeight(1);
	        	    	
	    	ArrayList<Head1Datastruct> head1list = new ArrayList<Head1Datastruct>();
	    	
	    	Map<Integer,Vector<Head2Datastruct>> head2map = new HashMap<Integer,Vector<Head2Datastruct>>();
	    	
	    	ArrayList<BodyDatastruct> bodylist = new ArrayList<BodyDatastruct>();
	    	//Log.v("DEBUG--->","configinfread  "+configinfread);
			
	    	head1list.clear();
			head2map.clear();
			bodylist.clear();
			//ListTable.globalTitleList.clear();	
			globalTitleList.clear();	
			ListTable.createdfiletime.clear();
			ListTable.serianumberoffilelistofretrieve.clear();
			ListTable.filebody.clear();//zhuxiaoqing 2011.08.21
			ListTable.serianumberoffilelistoffile =0;
			ListTable.addressinbodyofrecord.clear();
			clickbrowsenumber =0;
			//2011.01.10 liqiang
			File retrievalfile = new File("/sdcard/eFinger/"+classname+"/Retrieve/retrievalfile.retrie");
			inputstream = null;
			if(retrievalfile.exists()==false)
				Log.v("DEBUG--->","MEIYOU JI LU CUN ZAI");
			else
			{
			
			try 
			{
				inputstream = new BufferedInputStream(new FileInputStream(retrievalfile));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try 
			{			
				int temptotalbyte =0;
				
				int tempskiptohead2 =0;	
				
				
				// 读取Head0 部分
				
				byte[] bytecreatednumoffile = new byte[4];
				byte[] bytepresentmonth= new byte[4];
				byte[] bytelocationofnewrecord = new byte[4];
				inputstream.read(bytecreatednumoffile);
				//inputstream.skip(4);
				inputstream.read(bytepresentmonth);
				inputstream.read(bytelocationofnewrecord);
				//inputstream.skip(4);
				temptotalbyte+=12;

				numofmonth =0;
				numofmonth = Transform.BytestoInt(bytepresentmonth);		//获得月份数
				int creatednumoffile = Transform.BytestoInt(bytecreatednumoffile);
				ListTable.serianumberoffilelistoffile = creatednumoffile;			//注意，文件编号是绝对增长的！！其实，文件删除时候，仍然存在于索引文件中，只是不让它显示而已
				
				
//				Log.v("zzzzDEBUG HEAD0--->zzzz","creatednumoffile:  "+creatednumoffile);
				Log.v("DEBUG HEAD0--->","numofmonth  "+numofmonth);
				//Log.v("DEBUG HEAD0--->","locationofnewrecord  "+locationofnewrecord);
				
				
				
			//	if(boolflag==false)
				if(ListTable.boolflag==false)
				{
					inputstream.skip((numofmonth-1)*12);							//跳过 HEAD1 中前 numofmonth-1 条记录，因为当前月份肯定是在记录的最后部分
					tempskiptohead2 = (numofmonth-1);
				}
				else 
				{
					Intent newintent =getIntent();
					Bundle newbundle = newintent.getExtras();
					clickbrowsenumber = newbundle.getInt("clickbrowsenumber");
					//if(numofmonth-clickbrowsenumber-1>=0)
					if(numofmonth-clickbrowsenumber-1>=0)
			           { 
			           //skipmonthnumber=numofmonth-1;
			         //  inputstream.skip((numofmonth-clickbrowsenumber-1)*12);
						inputstream.skip((numofmonth-clickbrowsenumber-1)*12);
			           //tempskiptohead2 = (numofmonth-clickbrowsenumber-1);
						tempskiptohead2 = (numofmonth-clickbrowsenumber-1);
			           }
					
					ListTable.boolflag=false;
				}

							
				// 读取Head1 部分
				byte[] bytetime= new byte[4];
				byte[] byterecordnumoffile = new byte[4];
				byte[] byteaddressinHead2 = new byte[4];
				inputstream.read(bytetime);
				inputstream.read(byterecordnumoffile);
				inputstream.read(byteaddressinHead2);
				
				
				temptotalbyte+= (tempskiptohead2+1)*12; 							//实际在读完某个月的HEAD1中的记录后，总共读取的字节
	
				int recordnumoffile = Transform.BytestoInt(byterecordnumoffile);	
				int addressinHead2 =Transform.BytestoInt(byteaddressinHead2);	//得到当前月的记录链在HEAD2 中的首地址
				//Log.v("DEBUG HEAD1--->","monthno:  "+time);
//				Log.v("zzzzDEBUG HEAD1--->zzzzz","recordnumoffile"+recordnumoffile);
				//Log.v("DEBUG HEAD1--->","addressinHead2  "+addressinHead2);
				//Log.v("DEBUG HEAD1--->","SKIP temptotalbyte-->"+temptotalbyte);
				//Log.v("DEBUG HEAD1----<>","addressinHead2-temptotalbyte  "+(addressinHead2-temptotalbyte));
				inputstream.skip((addressinHead2-temptotalbyte));				//现在应该是刚好跳到最后一月（当前月）的记录在HEAD2中的首地址
				
				
				
				
				// 读取Head2 部分, 读取Body 部分
				
				byte[] byteaddressinBody = new byte[4];
				inputstream.read(byteaddressinBody);
				
				int addressinBody = Transform.BytestoInt(byteaddressinBody);	//获得了当前月份的第一条记录在Boyd 中的首地址
//				Log.v("DEBUG--->","addressinBody  "+addressinBody);
				
				
				int skipbyte=addressinBody-addressinHead2-4;
				inputstream.skip(skipbyte);				//抵达当前月份的第一条记录在Boyd 中的首地址,因为 当前HEAD2的记录已读，故应该减去4
				
			//	ListTable.addressinbodyofrecord.add(addressinBody);		//当月第一条记录在body中的地址
				count = addressinBody;					//count是用来跟踪当前月的第一条记录在body中的位置
				int temptotaloftitle =0;
				int titlesize =0,existflag=0,serianumberoffile=0,filecreattime=0;
				for(int j=0;j!=recordnumoffile;j++)
				{
					
					titlesize =0;
					existflag=0;
					serianumberoffile=0;
					filecreattime=0;
					temptotaloftitle =0;
					
					byte[] bytetitlesize = new byte[4];
					byte[] byteisdelete = new byte[4];
					byte[] byteserianumberoffile = new byte[4];
					byte[] bytefilecreattime = new byte[4];
					byte[] bytevectoroffile=new byte[4];//zhuxiaoqing 2011.08.21
					ArrayList<DataUnit> temptitle = new ArrayList<DataUnit>();	
					String tempdate="";
					
					inputstream.read(bytetitlesize);
					inputstream.read(byteisdelete);
					inputstream.read(byteserianumberoffile);
					inputstream.read(bytefilecreattime);
					Log.i("zhuxiaoqing &&&&&showretrieve-- filecreattime",""+Transform.BytestoInt(bytefilecreattime));
					inputstream.read(bytevectoroffile);//zhuxiaoqing 2011.08.21
					int voffile=0;
					voffile=Transform.BytestoInt(bytevectoroffile);
					Log.i("zhuxiaoqing &&&&&showretrieve-- voffile",""+voffile);
					byte[] bytefilename=new byte[voffile];
					inputstream.read(bytefilename);
					String filename2=new String(bytefilename);
//					Log.i("zhuxiaoqing hahaha---",""+filename2);
					titlesize = Transform.BytestoInt(bytetitlesize);
					filecreattime = Transform.BytestoInt(bytefilecreattime);
					serianumberoffile = Transform.BytestoInt(byteserianumberoffile);
					existflag = Transform.BytestoInt(byteisdelete);
//					System.out.println("**showretrieve titlesize"+titlesize);
					readDataFromFileForTitle(inputstream,titlesize);
					
					temptotaloftitle=Operationonrretrievalfile.computeTitleSize(ListTable.globalIndexTable);
//					Log.i("**zhuxiaoqing showretrieve--globalIndexTable",""+ListTable.globalIndexTable.size());
					
					
					if(existflag==1)//只有当存在标志为1的时候，才会将这些信息存储起来，以备后面显示用
					{
					temptitle= (ArrayList<DataUnit>)ListTable.globalIndexTable.clone();
					tempdate=String.valueOf(filecreattime);
//					Log.i("zhu here","daole");
					//ListTable.globalTitleList.add(temptitle);
					globalTitleList.add(temptitle);
					System.out.println("showretrieve &&&&&&&&&&& date "+tempdate);
					ListTable.createdfiletime.add(tempdate);
					ListTable.serianumberoffilelistofretrieve.add(serianumberoffile);
					ListTable.filebody.add(filename2);//zhuxiaoqing 2011.08.21
					ListTable.repairflag.add(0);//zhuxiaoqing 2011.11.17
					ListTable.titlenum.add(titlesize);
					if(j!=recordnumoffile)
						ListTable.addressinbodyofrecord.add(count);
					}
					

					//count+=16;
					count=count+16+4+voffile;//zhuxiaoqing 2011.08.23
					count+= temptotaloftitle;				
					}	
				inputstream.close();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}    
			}
			addListener();
					
			MyAdapter myadapter = new MyAdapter(this,globalTitleList);
			this.setListAdapter(myadapter); 	

	}
	

	/**
	 * @author liqiang
	 * @time 2011.06.09
	 * 按钮的初始化
	 * 不同状态下按钮背景的初始化
	 */
	public void findView()
	{
		 button_browse_back = (Button) findViewById(R.id.button1); //第一个按钮，链接到上一月
		 button_browse_back.setBackgroundDrawable(lastmonthimage);		
	     button_browse_front=(Button) findViewById(R.id.button2);
	     button_browse_front.setBackgroundDrawable(nextmonthimage);	     
	     button_new=(Button) findViewById(R.id.button3);  
	     button_new.setBackgroundDrawable(createfileimage);	    
	     button_delete = (Button) findViewById(R.id.buttondelete);
		 button_delete.setBackgroundDrawable(deleteimage);	     	             
	}


	/**
	 * 
	 */
	private void initDrawable() 
	{
		landbackgroundimage = this.getResources().getDrawable(R.drawable.landbground);
		portbackgroundimage = this.getResources().getDrawable(R.drawable.portbground);
		lastmonthimage = this.getResources().getDrawable(R.drawable.lastmonth);
		 nextmonthimage = this.getResources().getDrawable(R.drawable.nextmonth);
		 createfileimage = this.getResources().getDrawable(R.drawable.createfile);
		 deleteimage = this.getResources().getDrawable(R.drawable.delete);
	}
	
		
	OnClickListener button_browse_backlistener = new OnClickListener(){         //按钮触发事件！！
        
 	   //@Override
        public void onClick( View v) {
        Intent openBrowserIntent = new Intent(ShowRetrieve.this,ShowRetrieve.class);        //打开网页链接
        tempclickbrowsenumber= clickbrowsenumber;										  //折抵方这样弄的道理，请仔细分析～～ 
        if(tempclickbrowsenumber<numofmonth-1)
        { 
        ListTable.boolflag=true;
        tempclickbrowsenumber++;

        Bundle bundleinbrowse=new Bundle();
        bundleinbrowse.putString("classname", classname);//2011.01.10 liqiang
        bundleinbrowse.putInt("clickbrowsenumber",tempclickbrowsenumber);
        openBrowserIntent.putExtras(bundleinbrowse);
        startActivity(openBrowserIntent);
        clearDrawable();
        ShowRetrieve.this.finish();
        }
        else
			{
				// Log.v("DEBUG--->","SKIP --> "+tempclickbrowsenumber);
     	   ;
			}
        }
    };
	
	OnClickListener button_browse_frontlistener = new OnClickListener(){         //按钮触发事件！！
        
 	   //@Override
        public void onClick( View v) {
        Intent openBrowserIntent = new Intent(ShowRetrieve.this,ShowRetrieve.class);        //打开网页链接
        tempclickbrowsenumber= clickbrowsenumber;										  //折抵方这样弄的道理，请仔细分析～～ 
        if(tempclickbrowsenumber>0)
        { 
        ListTable.boolflag=true;
        tempclickbrowsenumber--;
        Bundle bundleinbrowse=new Bundle();
        bundleinbrowse.putString("classname", classname);//2011.01.10 liqiang
        bundleinbrowse.putInt("clickbrowsenumber",tempclickbrowsenumber);
        openBrowserIntent.putExtras(bundleinbrowse);
        startActivity(openBrowserIntent);
        clearDrawable();
        ShowRetrieve.this.finish();
        }
        else
			{
				 //Log.v("DEBUG--->","SKIP --> "+tempclickbrowsenumber);
				 ;
			}
        }
    };
	
	OnClickListener button_newlistener = new OnClickListener(){         //按钮触发事件！！
        
 	   //@Override
        public void onClick( View v) {
        	ListTable.titleok=false;//zhuxiaoqing 2011.09.27
        Intent openNewIntent = new Intent(ShowRetrieve.this,WritebySurfaceView.class);        //打开网页链接
        ListTable.isnew=true;//zhuxiaoqing 2011.11.17
        Bundle bundle = new Bundle();
		   bundle.putString("Type","New");
		   bundle.putString("classname", classname);//2011.01.10 liqiang
		   openNewIntent.putExtras(bundle);
		   startActivity(openNewIntent);
		   clearDrawable();
		   ShowRetrieve.this.finish();
        }
    };
	boolean deleteflag = false;
    OnClickListener button_deletelistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			if(deleteflag == false)
				deleteflag = true;
			else
				deleteflag = false;
			int num = templistview.getCount();
			
			for(int i = 0;i<num;i++)
			{
				final int deleteindex = i; 
				View retrieveview = null;
			    retrieveview = templistview.getChildAt(i);		
//			    Log.i("debug in buttondelete ","num is "+num);
//			    Log.i("debug in buttondelete ","num is "+num+" "+retrieveview);
			    if(retrieveview != null)
			    {
		        final Button button_delete= (Button) retrieveview.findViewById(R.id.delete);
		        if(button_delete.getVisibility()==View.VISIBLE&&deleteflag==false)
		        {
//		        	Log.i("debug in showretrieve","invisible invisible invisible invisible");
		        	button_delete.setVisibility(View.INVISIBLE);
		        }
		        else if(button_delete.getVisibility()==View.INVISIBLE&&deleteflag==true)
		        {
//		        	Log.i("debug in showretrieve","visible visible visible visible");
		        	button_delete.setVisibility(View.VISIBLE);
		        
		        	button_delete.setOnClickListener(new OnClickListener(){
		        		
					@Override
					public void onClick(View v)
					{
						// TODO Auto-generated method stub
						//int id = ((ListView)v.getRootView().getRootView()).getId();
						//Log.i("debug id is "," id "+v.getLeft()+" "+v.getTop()+((ListView)v.getParent().getParent()));
						button_delete.setEnabled(false);
						button_delete.setVisibility(View.INVISIBLE);
				     	   try {
				     		 //  Log.i("DEBUG---->","   ListTable.addressinbodyofrecord.get(itemid)  "+itemid +"  "+ListTable.addressinbodyofrecord.get(itemid));
				     		   DeleteTitle.delete(ListTable.addressinbodyofrecord.get(deleteindex),classname);
				     	   	} catch (IOException e) 
				     	   	{
								// TODO Auto-generated catch block
								e.printStackTrace();
				     	   	}
							
							 //							Log.i("delete","deleteindex is "+deleteindex+" "+deleteserionumber);
							 //2011.01.10 liqiang
							//String file="/sdcard/eFinger/"+classname+"/AhweFile/"+deleteserionumber+".hwep";	//这么写才是对的
							String file="/sdcard/eFinger/"+classname+"/AhweFile/"+ListTable.filebody.get(deleteindex);//zhuxiaoqing 2011.08.21
							File deletefile = new File(file);
						if (true == deletefile.delete()) {
//							Log.i("delete success","CHENG GONG");
							
				        }
//						
						Intent newIntent = new Intent();
						Bundle bundleinbrowse = new Bundle();
						bundleinbrowse.putString("classname",classname);
						newIntent.putExtras(bundleinbrowse);
						newIntent.setClass(ShowRetrieve.this,ShowRetrieve.class); 
						startActivity(newIntent);
						clearDrawable();
						ShowRetrieve.this.finish();
					}});
		        
		        }}
			}
		     
		}};//2011.06.13 liqiang
	private void addListener()
	{
		button_browse_back.setOnClickListener(button_browse_backlistener);
		button_browse_front.setOnClickListener(button_browse_frontlistener);
		button_new.setOnClickListener(button_newlistener);
		button_delete.setOnClickListener(button_deletelistener);
	}
	@SuppressWarnings("unchecked")
	protected void onListItemClick(ListView l,View v,int position,long id)
	{
		super.onListItemClick(l, v, position, id);
		ListTable.globalTitle.clear();
		ListTable.globalTitle=(ArrayList<DataUnit>) globalTitleList.get(position).clone();
//		Log.i("debug in showretrieve","globalTitle"+ListTable.globalTitle.size());
		Bundle bundle = new Bundle();
//		Log.i("DEBUG-->"," "+position);
//		Log.i("debug in showretrieve","id is "+id);
		bundle.putInt("serionumber",(int)ListTable.serianumberoffilelistofretrieve.get(position));
		bundle.putString("filebody", ListTable.filebody.get(position));//zhuxiaoqing 2011.08.21
		ListTable.editfilename=ListTable.filebody.get(position);
		
//		Log.i("zhuxiaoqing filebody.get(position)",""+ListTable.filebody.get(position));
		bundle.putInt("clickbrowsenumber",tempclickbrowsenumber);
		bundle.putString("classname", classname);//2011.01.10  liqiang
		bundle.putString("Type","Edit");
		bundle.putInt("indexoftitle", position);//zhuxiaoqing 2011.11.17
		
		Intent intent = new Intent(ShowRetrieve.this,WritebySurfaceView.class); 
		intent.putExtras(bundle);
		if(ListTable.repairflag.get(position)==1)
		{
//			System.out.println("**showretrieve1");
			ListTable.isfirst=false;
		}else{
//			System.out.println("**showretrieve2");
			ListTable.repairflag.set(position, 1);//zhuxiaoqing 2011.11.17
			ListTable.isfirst=true;
		}
		
		startActivity(intent);
		clearDrawable();
		ShowRetrieve.this.finish();
	}
	
	
	
	private void clearDrawable()
	{
		// TODO Auto-generated method stub
		landbackgroundimage = nullimage;
		portbackgroundimage = nullimage;
		lastmonthimage = nullimage;
		 nextmonthimage = nullimage;
		 createfileimage = nullimage;
		 deleteimage = nullimage;
		 System.gc();//2011.12.01 liqiang
	}


	private void readDataFromFileForTitle(BufferedInputStream inputStream,int characternum) {
		ListTable.sectionTable.clear();
		ListTable.globalIndexTable.clear();
		ListTable.paragraphTable.clear();
		HwFunctionWindow.colortable.clear();
		HwFunctionWindow.Initcolortable();
		
		//inputStream = new BufferedInputStream(new FileInputStream(		//我 更改过
		//	abstractFile));

		//GZIPInputStream gin = new GZIPInputStream(inputStream);
		documentDecoder = new DocumentDecoder(inputStream);
		//documentDecoder.headDecoder();				//读取索引文件的头文件
		documentDecoder.dataDecoderForTitle(characternum);				//读取索引文件的数据文件,这里必须注意的一点是，我在读取索引文件的时候仍然是用ListTable.globalIndexTable 来承载标题信息的，之所以这样是为了减少大量的冗余代码
	}


	
	
	
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
			ListTable.ScreenHeight = tempheight;
			ListTable.ScreenWidth = tempwidth;
			this.getWindow().setBackgroundDrawable(nullimage);
			this.getWindow().setBackgroundDrawable(landbackgroundimage);
			Retrieveview.retrieveviewleftmargin = (int) (ListTable.ScreenWidth*0.18);
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
				ListTable.ScreenHeight = tempheight;
				ListTable.ScreenWidth = tempwidth;
				Retrieveview.retrieveviewleftmargin = (int) (ListTable.ScreenWidth*0.33);
				this.getWindow().setBackgroundDrawable(nullimage);
				this.getWindow().setBackgroundDrawable(portbackgroundimage);
			}
        
        setContentView(R.layout.retrieve);
        findView();//按钮的初始化
//        this.getWindow().setBackgroundDrawable(landbackgroundimage);
//        mGestureDetector = new GestureDetector(this); 
//        mGestureDetector.setIsLongpressEnabled(true);  
        this.getListView().setOnTouchListener(this);
	    addListener();    
	  }

	  

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			  final int FLING_MIN_DISTANCE = 60, FLING_MIN_VELOCITY =80; 
			  int curPos =0,presentitemid=0;
			  	
			  	try{
			  		
			  	
			  	 int x = (int) e1.getX();
		         int y = (int) e1.getY();
		         itemid = (int) this.getListAdapter().getItemId(this.getListView().pointToPosition(x,y));
		    	 View retrieveview = null;
				 curPos = this.getListView().getFirstVisiblePosition(); 
				 presentitemid = itemid-curPos;															//必须这么处理
				 if(locationshowofbutton!=-1)
				 {
					 retrieveview = this.getListView().getChildAt(locationshowofbutton);					
			         buttondelete= (Button) retrieveview.findViewById(R.id.delete);
			         if(buttondelete.getVisibility()==View.VISIBLE)
				        	buttondelete.setVisibility(View.INVISIBLE);
				 }
				 
				 
				 locationshowofbutton = presentitemid;													//用来备份当前要显示的button的所在的itemview的行值
				 retrieveview = this.getListView().getChildAt(locationshowofbutton);							//注意，getChildAt(itemid) 获得的是相对于当前视图可见的所有itemview的中的位置的itemview，并不是真正的绝对那个位置的itemview。比方说，我当前第一个可视的item的位置为1，而我用getchildat（8），得到的是从第一个这个可见的item，顺序往下取得相对这个第一个可见的item的第b8个item
		         buttondelete= (Button) retrieveview.findViewById(R.id.delete);
			    
//		         Log.v("debug in show ing ","e1.gety and e2.gety is "+e1.getX()+" "+e2.getX());
			     if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
			         // Fling left  

			        if(buttondelete.getVisibility()==View.VISIBLE)
			        	buttondelete.setVisibility(View.INVISIBLE);
			        locationshowofbutton =-1;
			        	
			     } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
			         // Fling right  
			    	 
			    	 
			         buttondelete.setVisibility(View.VISIBLE);
			         buttondelete.setOnClickListener(new OnClickListener(){         //按钮触发事件！！
				    	   //@Override
			        	 
				           public void onClick( View v) {
				        	   
				        	   buttondelete.setEnabled(false);
				        	   buttondelete.setVisibility(View.INVISIBLE);
				        	   try {
				        		 //  Log.i("DEBUG---->","   ListTable.addressinbodyofrecord.get(itemid)  "+itemid +"  "+ListTable.addressinbodyofrecord.get(itemid));
								DeleteTitle.delete(ListTable.addressinbodyofrecord.get(itemid),classname);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							ListTable.serianumberoffilelistofretrieve.get(itemid);
//							Log.i("delete","deleteindex is "+itemid+" "+deleteserionumber);
							//2011.01.10 liqiang
							//String file="/sdcard/eFinger/"+classname+"/AhweFile/"+deleteserionumber+".hwep";	//这么写才是对的
							String file="/sdcard/eFinger/"+classname+"/AhweFile/"+ListTable.filebody.get(itemid);//zhuxiaoqing 2011.08.21
//							Log.i("zhuxiaoqing shanchu-->",""+file);
							File deletefile = new File(file);
						if (true == deletefile.delete()) {
//							Log.i("DEBUG-->","CHENG GONG");
				           }
						//刷新
						Intent newIntent = new Intent();
						Bundle bundleinbrowse = new Bundle();
						bundleinbrowse.putString("classname",classname);
						newIntent.putExtras(bundleinbrowse);
						newIntent.setClass(ShowRetrieve.this,ShowRetrieve.class); 
				        startActivity(newIntent);
				        clearDrawable();
						}

				       });	     
			     } 
			  	}
			  	catch(Exception e)
			  	{
			  		
			  	}
			return true;
		}

		

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		// TODO Auto-generated method stub
		
	
//		 return mGestureDetector.onTouchEvent(event);  
		 return onTouchEvent(event);//2011.11.30 liqiang
	}





	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
	//	Log.i("MyGesture", "onDown");
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return true;
	}





	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
          
        //按下键盘上返回按钮  
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {  
        	Save.WritetoConfigInfFile();//2011.01.10 liqiang 
        	Intent classintent = new Intent(ShowRetrieve.this, Fileclass.class);
			startActivity(classintent);
			clearDrawable();
			ShowRetrieve.this.finish();
			return true;
        }
        else
        	return super.onKeyDown(keyCode, event); 
	}	
}
