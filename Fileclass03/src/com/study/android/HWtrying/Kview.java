package com.study.android.HWtrying;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.study.android.ContextState.Context_STATE;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.MyStroke;
import com.study.android.basicData.type.ImageUnit;
import com.study.android.data.ListTable;
import com.study.android.zoom.Zoom;


public class Kview extends View
//public class Kview extends SurfaceView implements SurfaceHolder.Callback
{
	static int i=0;
	public static SurfaceHolder surfhold ;
	public ArrayList<BasePoint> array=new ArrayList<BasePoint>();
	private int timeturn=1;								//记录当前记录所占用的页面数！！
	/**
	 * 行数，从1开始，每次重绘都置成1
	 */
	public int locationofy	=1;
	public int maxlocationofy = -1;
	/**
	 * 行数，相对于整个画布，从第1行开始
	 */
	private static int reallocationofy =1;
	public static int copyofreallocation=-1;
	private static int templocationofy =0;
	public static short realtiveposition = 0;
	private short spacewidth = (short) ListTable.spacewidth;
	public short baselineheight=  (short) (ListTable.charactersize+16);
	private short BASEHEIGHT = (short)ListTable.charactersize;
	static short shuxiejizhun = (short) ListTable.shuxiejizhun;
	private boolean preisimage = false;
	private DataType PreUnitType=DataType.TYPE_CALLIGRAPHY;						//记录前一个单元的类型
	private DataType CursorUnitType=DataType.TYPE_CALLIGRAPHY;					//记录光标处单元的类型
	private static boolean lastofunitisenter = false;
	public static int maxscroll=0;
	public static boolean scrollstatus=false;
	/**
	 * 屏幕的左上角（坐标系的（0，0）位置）相对于整个view的偏移距离
	 */
	public static int maxscrollvice=0;		
	public static short screenwidth=ListTable.ScreenWidth,screenheight=ListTable.ScreenHeight;
	public static short offsetofreallocationofy=0; 
	private int MAXROWINPAGE = screenheight/(ListTable.charactersize+16);
	/**
	 * 背景线的条数
	 */
	private int linenumber = 0;     
	public static Paint pat = new Paint();
	public static Paint mpaint = new Paint();
	public static Paint mnwepaint = new Paint();
	int titlewidth=0;//zhuxiaoqing 2011.09.24
	int relativelocation1=0;
	/**
	 * 标题过长时只显示一行，这个变量是记录一行最多显示的字数，这不是个常量，
	 * 根据用户写字的宽度会有所变化
	 */
	int linelimitindex = 0;//2011.10.21 liqiang
	private float pointsize = 3f;
	/***********************************************************************/
	/***********************************************************************/
	/**
	 * 选中区域的背景颜色
	 */
	private Drawable tempImage;
	short temp=0;
	public static short selectedleftindex,selectedrightindex;
	Path p ;
	public Canvas kviewcanvas;//2011.05.26 liqiang

	public Kview(Context context,AttributeSet attrit) {
		super(context,attrit);

		/***********************************************************************/
		/*    下面的这几条是为了采用darwpath代替drawline而新引进的变量！！        		    */
		/***********************************************************************/
//		Log.i("debug","kview construct");
		
		setFocusable(true);  
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{			
			int tempwidth = ListTable.ScreenWidth,tempheight = ListTable.ScreenHeight;
			ListTable.ScreenHeight = (short) (tempwidth>tempheight?tempwidth:tempheight); 
			ListTable.ScreenWidth = (short) (tempwidth<tempheight?tempwidth:tempheight);
		}
		else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			int tempwidth = ListTable.ScreenWidth,tempheight = ListTable.ScreenHeight;
			ListTable.ScreenHeight = (short) (tempwidth<tempheight?tempwidth:tempheight); 
			ListTable.ScreenWidth = (short) (tempwidth>tempheight?tempwidth:tempheight);
		}
		screenwidth=ListTable.ScreenWidth;
		screenheight=ListTable.ScreenHeight;
 		 pat.setAntiAlias(true);			//去锯齿
         pat.setStrokeWidth(3);
         pat.setStyle(Paint.Style.STROKE);
         pat.setStrokeJoin(Paint.Join.ROUND);
         pat.setStrokeCap(Paint.Cap.ROUND);  
         mnwepaint.setColor(Color.RED);
         
//         timeturn=1;//liqiang 2011.12.20
         spacewidth = (short) ListTable.spacewidth;
         baselineheight=(short) (ListTable.charactersize+16);
         BASEHEIGHT  = (short) ListTable.charactersize;
         shuxiejizhun = (short) ListTable.shuxiejizhun;
         tempImage =context.getResources().getDrawable(R.drawable.green);
         tempImage.setAlpha(110);
//         Log.i("debug in kview create ","kview screenheight is "+Kview.screenheight);
        
         Zoom.FirstLineLocation=(short) (Kview.screenheight-Mview.bottomofshuxiebaseline-3*shuxiejizhun);
         MAXROWINPAGE =Zoom.FirstLineLocation/baselineheight;
//         Log.i("debug in kview create ","MAXROWINPAGE is "+MAXROWINPAGE);
         maxscrollvice=0;
         
         pat.setColor(ListTable.charactercolor);//2011.10.19 liqiang 
         Log.i("debug in kview ","character pat color is "+ListTable.charactercolor);
// 		if(ListTable.timesofoncreat==1)//2011.10.17 liqiang
// 		{
// 			mpaint.setColor(Color.GRAY);
// 			pat.setColor(Color.BLACK);
// 		}
 		
 		 
		// TODO Auto-generated constructor stub
	}
	
	public void isTitle()
	{
		 short globallistsize1 =(short) ListTable.globalIndexTable.size();
		 int i=0;
		if(ListTable.titleok==true){
			if(ListTable.isexport==false){
				 for(;i<globallistsize1;i++)
				 {
					 DataUnit data1  = ListTable.globalIndexTable.get(i);//zhuxiaoqing 2011.09.22
		     	   	if(data1.getCtrlType()!= DataType.TYPE_CTRL_ENTER)//zhuxiaoqing 2011.09.22
		         	{
		         		titlewidth+=data1.getWidth();   
		         		//2011.10.21 liqiang 
		         		if(titlewidth>ListTable.ScreenWidth-ListTable.leftmargin-ListTable.rightmargin)
		         		{
		         			linelimitindex = i;
		         			titlewidth -=data1.getWidth()*3;
		         			relativelocation1=(Kview.screenwidth- titlewidth)/2;
		             		titlewidth=0;
		         			break;
		         		}//2011.10.21 liqiang
		         	}
		         	else 
		         	{
		         		linelimitindex = i;
		         		relativelocation1=(Kview.screenwidth- titlewidth)/2;
		         		titlewidth=0;
		         		break;
		         	}
				 }
			}else
			{
				 for(;i<globallistsize1;i++)
				 {
					 DataUnit data1  = ListTable.globalIndexTable.get(i);//zhuxiaoqing 2011.09.22
		     	   	if(data1.getCtrlType()!= DataType.TYPE_CTRL_ENTER)//zhuxiaoqing 2011.09.22
		         	{
		         		titlewidth+=data1.getWidth();   
		         		//2011.10.21 liqiang 
		         		if(titlewidth>ListTable.pngwidth-ListTable.leftmargin-ListTable.rightmargin)
		         		{
		         			linelimitindex = i;
		         			titlewidth -=data1.getWidth()*3;
		         			relativelocation1=(ListTable.pngwidth- titlewidth)/2;
		             		titlewidth=0;
		         			break;
		         		}//2011.10.21 liqiang
		         	}
		         	else 
		         	{
		         		linelimitindex = i;
		         		relativelocation1=(ListTable.pngwidth- titlewidth)/2;
		         		titlewidth=0;
		         		break;
		         	}
				 }
				
			}
		}else{
			if(ListTable.isexport==false){
			 for(;i<globallistsize1;i++)
			 {
				 DataUnit data1  = ListTable.globalIndexTable.get(i);//zhuxiaoqing 2011.09.22
				 titlewidth+=data1.getWidth();	
				//2011.10.21 liqiang 
	         	if(titlewidth>ListTable.ScreenWidth-ListTable.leftmargin-ListTable.rightmargin)
	         	{
	         		linelimitindex = i;
	         		titlewidth -=data1.getWidth()*3;
	         		relativelocation1=(Kview.screenwidth- titlewidth)/2;
	             	titlewidth=0;
	         		break;
	         	}//2011.10.21 liqiang
			 }
			 linelimitindex = globallistsize1-1;
			 relativelocation1=(Kview.screenwidth- titlewidth)/2;
      		titlewidth=0;	
		}else{
			for(;i<globallistsize1;i++)
			 {
				 DataUnit data1  = ListTable.globalIndexTable.get(i);//zhuxiaoqing 2011.09.22
				 titlewidth+=data1.getWidth();	
				//2011.10.21 liqiang 
	         	if(titlewidth>ListTable.pngwidth-ListTable.leftmargin-ListTable.rightmargin)
	         	{
	         		linelimitindex = i;
	         		titlewidth -=data1.getWidth()*3;
	         		relativelocation1=(ListTable.pngwidth- titlewidth)/2;
	             	titlewidth=0;
	         		break;
	         	}//2011.10.21 liqiang
			 }
			 linelimitindex = globallistsize1-1;
			 relativelocation1=(ListTable.pngwidth- titlewidth)/2;
     		titlewidth=0;	
		}
			
		}
	}
	private void drawBaseLine(Canvas canvas)//2011.12.20 liqiang
	{
		int j=2;//从第3条背景线开始画，腾出工具栏的空间
		linenumber = (timeturn+1)*(screenheight /baselineheight);
		Log.i("","linenumber is "+linenumber);
	    for(;j!=linenumber;j++) 
	    { 
	    	if(WritebySurfaceView.getmailflag==false&&WritebySurfaceView.getpdfflag==false)
	    	{
	    	if((j*baselineheight > maxscrollvice)&&((j+1)*baselineheight<(maxscrollvice+screenheight)))
	    	{
	    		if(ListTable.isexport==false)
	    			canvas.drawLine(ListTable.leftmargin,j*baselineheight,screenwidth-ListTable.rightmargin,j*baselineheight,mpaint);  //2011.07.07 liqiang
	    		else
	    			canvas.drawLine(ListTable.leftmargin,j*baselineheight,ListTable.pngwidth-ListTable.rightmargin,j*baselineheight,mpaint);//zhuxiaoqing 2011.11.01
	    	}
	    	}
	    	else
	    	{	if(ListTable.isexport==false)
	    			canvas.drawLine(ListTable.leftmargin,j*baselineheight,screenwidth-ListTable.rightmargin,j*baselineheight,mpaint);
	    		else
	    			canvas.drawLine(ListTable.leftmargin,j*baselineheight,ListTable.pngwidth-ListTable.rightmargin,j*baselineheight,mpaint);
	    		
	    	}
	    }
	    if(reallocationofy-maxscrollvice>0)
	    {
	    	for(;j!=linenumber+MAXROWINPAGE;j++) 
	    	{
	    		if(WritebySurfaceView.getmailflag==false&&WritebySurfaceView.getpdfflag==false)
	    		{
//	    			Log.i("debug ","draw baseline 22222!!!!");
	    		if((j*baselineheight > maxscrollvice)&&((j+1)*baselineheight<(maxscrollvice+screenheight)))
	    		{
	    			if(ListTable.isexport==false)
	    				canvas.drawLine(ListTable.leftmargin,j*baselineheight,screenwidth-ListTable.rightmargin,j*baselineheight,mpaint);  //2011.07.07 liqiang
	    			else
	    				canvas.drawLine(ListTable.leftmargin,j*baselineheight,ListTable.pngwidth-ListTable.rightmargin,j*baselineheight,mpaint); 
	    		}
	    		}
	    		else
	    		{
	    			if(ListTable.isexport==false)
	    				canvas.drawLine(ListTable.leftmargin,j*baselineheight,screenwidth-ListTable.rightmargin,j*baselineheight,mpaint); 
	    			else
	    				canvas.drawLine(ListTable.leftmargin,j*baselineheight,ListTable.pngwidth-ListTable.rightmargin,j*baselineheight,mpaint);
	    		}
	    	}
	    }
	}
	public void onDraw(Canvas canvas)
	{
		kviewcanvas = canvas;
		
//		System.out.println("export here");
		if(ListTable.fillcolor == true)
		{
			selectedleftindex= (short) ListTable.selectedMargin[0];
			selectedrightindex= (short) ListTable.selectedMargin[1];
		}
		Log.i("debug in kview ondraw ","kview ondrawing ");
		
		drawBaseLine(canvas);//2011.12.20 liqiang
	    
//	   Log.i("debug in kview ","baselinenum is !!!!!! "+j);
	    MAXROWINPAGE =Zoom.FirstLineLocation/baselineheight;
//	    Log.i("debug in kview ","zoom firstlinelocation is "+Zoom.FirstLineLocation+" "+baselineheight);
        locationofy =1;									//每次重绘图的时候，locationofy置成1，表示从头开始
        ListTable.Sectionnum =0;
        PreUnitType=DataType.TYPE_CALLIGRAPHY;	
        short globallistsize =(short) ListTable.globalIndexTable.size();
        
        if(globallistsize!=0)
        {
        	int i = 0;
        	int relativelocation =ListTable.leftmargin;//2011.07.07 liqiang  每一行的横坐标
        	templocationofy =0;
        	for(; i < globallistsize;i++)
        	{	
        		if(i==0)//zhuxiaoqing 2011.09.22
        		{
        				isTitle();//zhuxiaoqing 2011.09.24
        				
        				relativelocation=relativelocation1;//zhuxiaoqing 2011.09.24
//        				Log.i("!!titleok","relativelocation"+relativelocation);
        				
        				
        		}
        		//2011.10.21 liqiang
        		if(linelimitindex<ListTable.globalTitle.size()&&i==linelimitindex-3)
        		{//-3的原因：如果不减去一个量，显示的文字就会占满一整行，减去一个量就是要少显示一些字
//        			Log.i("debug in kview ","ListTable.globalTitle.size() is "+ListTable.globalTitle.size());
        			i = ListTable.globalTitle.size()+1;
        			locationofy++;
        			relativelocation=ListTable.leftmargin;
        			linelimitindex = 0;
        		}//2011.10.21 liqiang
            	DataUnit data  = ListTable.globalIndexTable.get(i);
            	lastofunitisenter = false;
            	//if((relativelocation+data.getWidth())>screenwidth)
            	//2011.07.07 liqiang 加上右页边距
            	if(ListTable.isexport==false)
            	{
	            	if((relativelocation+data.getWidth()+spacewidth+ListTable.rightmargin)>screenwidth)
	            	{//超过屏幕的宽度要换行	
	            		locationofy++;
	            		ListTable.cursor.setX((short)0);
	            		ListTable.cursor.setY((short)(locationofy*baselineheight));
	                	relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
	                	if(data.getDataType()==DataType.TYPE_IMAGE)
	                		{
	                		locationofy--;
	                		preisimage = true;//这个变量是在表示，当图片单元连着写要换行时候，进行标记，
	                		}
	                	if(data.getDataType()==DataType.TYPE_CHAR&&PreUnitType==DataType.TYPE_IMAGE)
	                		//这样是为了防止出现这样的情况，一行图片单元写满后，再写一个charunit，此时自动换行，
	                		//已经自动加1，而到下面的时候，根据字符前面如果是图片单元，行高又会自动加一，
	                		//所以先在这里减1即可
	                		locationofy--;
	            	}
            	}
            	else//zhuxiaoqing 2011.11.01
            	{
            		if((relativelocation+data.getWidth()+spacewidth+ListTable.rightmargin)>ListTable.pngwidth)
	            	{//超过屏幕的宽度要换行	
	            		locationofy++;
	            		ListTable.cursor.setX((short)0);
	            		ListTable.cursor.setY((short)(locationofy*baselineheight));
	                	relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
	                	if(data.getDataType()==DataType.TYPE_IMAGE)
	                		{
	                		locationofy--;
	                		preisimage = true;//这个变量是在表示，当图片单元连着写要换行时候，进行标记，
	                		}
	                	if(data.getDataType()==DataType.TYPE_CHAR&&PreUnitType==DataType.TYPE_IMAGE)
	                		//这样是为了防止出现这样的情况，一行图片单元写满后，再写一个charunit，此时自动换行，
	                		//已经自动加1，而到下面的时候，根据字符前面如果是图片单元，行高又会自动加一，
	                		//所以先在这里减1即可
	                		locationofy--;
	            	}
            	}
            	switch(data.getDataType())
            	{
            	case TYPE_CHAR:
        			if(Mview.patchangefordrawing== false)
        			{
        				pat.setColor(ListTable.charactercolor);//2011.10.19 liqiang
        				pat.setStrokeWidth(3);
        			}
        			
        			if(PreUnitType==DataType.TYPE_IMAGE)
        			{	
        				relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
        				locationofy+=templocationofy;
        				locationofy++;
        				templocationofy=0;
        			}
        			short offy = (short) ((locationofy)*baselineheight+16);//2011.06.23 liqiang
            		array = (ArrayList<BasePoint>)data.getPoints();			//这些点现在都是已经有了宽度的
//            		Log.i("debug in kview ondraw","array.size is "+array.size());
            		if(ListTable.fillcolor==true&&i>=selectedleftindex&&i<=selectedrightindex)
            		{
            			selectMargin(canvas,relativelocation,offy,data);
            		}
            		if(WritebySurfaceView.getpdfflag==false)
    				{
    				if(WritebySurfaceView.getmailflag||((locationofy-1)*baselineheight>=(maxscrollvice)&&
    					(locationofy+2)*baselineheight<=(maxscrollvice+screenheight)))
    				{
            		for(int t=0;t<=array.size()-2;)
            		{
//            			if(t==globallistsize-1)
//            			   Log.i("debug ","array max y is "+array.get(t).getY()+offy+" " +globallistsize);
            			if(!array.get(t+1).isEnd())
                		{
            				
            					if(array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY()
            							&&array.get(t+1).isEnd())
            					{
            						pat.setStrokeWidth(array.get(t).getStrokeWidth());
            						canvas.drawPoint(array.get(t).getX()+relativelocation, array.get(t).getY()+offy, pat);
            					}
            					else
            					{
            						pat.setStrokeWidth(array.get(t).getStrokeWidth());
            						canvas.drawLine(array.get(t).getX()+relativelocation, array.get(t).getY()+offy,array.get(t+1).getX()+relativelocation,array.get(t+1).getY()+offy,pat);
            					}
            				t++;

                		}
            			else
            			{
            					if(array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY()
            							&&array.get(t+1).isEnd())
            					{
            						pat.setStrokeWidth(array.get(t).getStrokeWidth());
            						canvas.drawPoint(array.get(t).getX()+relativelocation, array.get(t).getY()+offy, pat);
            					}
            					else
            					{
            						pat.setStrokeWidth(array.get(t).getStrokeWidth());
            						canvas.drawLine(array.get(t).getX()+relativelocation, array.get(t).getY()+offy,array.get(t+1).getX()+relativelocation,array.get(t+1).getY()+offy,pat);
            					}
            				t+=2;
            			}	
            		}}}
            		else 
            		{//getpdf
            			if(locationofy*baselineheight>=WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight
    							&&locationofy*baselineheight<=(WritebySurfaceView.pdfpagecount+1)*WritebySurfaceView.pdfpageheight)
            			{
//            				canvas.drawColor(0xffffff);//2011.10.24 liqiang
//            				Log.i("debug in getpdf in kview222 ","locationofy*baselineheight and i is "+locationofy*baselineheight+" "+i);
            			for(int t=0;t<=array.size()-2;)
                		{
                			if(!array.get(t+1).isEnd())
                    		{
                				if(array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY()
                						&&array.get(t+1).isEnd())
                				{
                					pat.setStrokeWidth(array.get(t).getStrokeWidth());
                					canvas.drawPoint(array.get(t).getX()+relativelocation, 
                					array.get(t).getY()+offy-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight,
                					pat);
                				}
                				else
                				{
                					pat.setStrokeWidth(array.get(t).getStrokeWidth());
                					canvas.drawLine(array.get(t).getX()+relativelocation, array.get(t).getY()
                					+offy-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight,
                					array.get(t+1).getX()+relativelocation,
                					array.get(t+1).getY()+offy-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight,pat);
                				}
                				t++;
                    		}
                			else
                			{
                				if(array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY()
                						&&array.get(t+1).isEnd())
                				{
                					pat.setStrokeWidth(array.get(t).getStrokeWidth());
                					canvas.drawPoint(array.get(t).getX()+relativelocation, 
                					array.get(t).getY()+offy-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight, pat);
                				}
                				else
                				{
                					pat.setStrokeWidth(array.get(t).getStrokeWidth());
                					canvas.drawLine(array.get(t).getX()+relativelocation, 
                					array.get(t).getY()+offy-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight,
                					array.get(t+1).getX()+relativelocation,array.get(t+1).getY()+offy-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight
                							,pat);
                				}
                				t+=2;
                			}	
                		}
            			}
            		}
          
            		break;
            	case TYPE_IMAGE:						
            		//为了方便每个IMAGEUNIT 后面的自动换行，都默认在IMAGEUNIT 
            		//后面添加一个ENTERUNIT，具体添加见addtoListTable.java 里的图片添加部分
            		ImageUnit imageunit = (ImageUnit)data.clone();
            		int strokeSize = imageunit.getStrokes().size();		
            		float height = imageunit.getHeight();
            		/*float width=imageunit.getWidth();//zhuxiaoqing 2011.11.18
            		float rate=1;
            		rate=imageunit.getImageCompactRate(screenwidth, screenheight);
//            		System.out.println("yasuo rate "+rate);
            		
            		//rate=()0.5;
            		
            		height=height*rate;
            		width=width*rate;*/
            		short offyimage=0;
     
            		if(height/baselineheight>=1)
            		{
            			if(PreUnitType==DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)
            			{
	
            				if(height%baselineheight!=0)
            					height=height/baselineheight+1;			
            				else
            					height = height/baselineheight;
            				
            				relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
            				templocationofy =0;
            				templocationofy =(int)(height>templocationofy?height:templocationofy);
            				preisimage= false;
            			
            			}
            			else if(PreUnitType==DataType.TYPE_CTRL_ENTER)
            			{
  
            				if(height%baselineheight!=0)
            					height=height/baselineheight+1;			//其实是height=(height/36+1)+1
            				else
            					height = height/baselineheight;
            				
        					locationofy--;
        					templocationofy =0;
        					templocationofy =(int)(height>templocationofy?height:templocationofy);
        					relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
        					preisimage= false;
            			}
            			else if(PreUnitType==DataType.TYPE_IMAGE)
            			{
        
            				if(height%baselineheight!=0)
            					height=height/baselineheight+1;			
            				else
            					height = height/baselineheight;
            				
            				if(preisimage==true) 			//换行了
            				{
            					relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
            				//	locationofy+=height;
            					locationofy+=templocationofy;
            	//				locationofy--;					
            					preisimage= false;
            					templocationofy=0;
            					templocationofy =(int)(height>templocationofy?height:templocationofy);
            				}
            				else
            					templocationofy =(int)(height>templocationofy?height:templocationofy);
            			}
            		}
            		else if(height/baselineheight<1)
            			{
            			if(PreUnitType==DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)
            			{
            				height =1;
            				relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
            				templocationofy =0;
            				templocationofy =(int)(height>templocationofy?height:templocationofy);
            				preisimage= false;
            			}
            			else if(PreUnitType==DataType.TYPE_CTRL_ENTER)
            			{
            				height =1;
        					locationofy--;	
        					templocationofy =0;
        					templocationofy =(int)(height>templocationofy?height:templocationofy);
        					relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
        					preisimage= false;
            			}
            			else if(PreUnitType==DataType.TYPE_IMAGE)
            			{
            				height =1;
            				if(preisimage==true) 
            				{
            				relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
        					locationofy+=templocationofy;
        			//		locationofy--;					//这里之所以要再减一，就是因为自动换行的时候locationofy已经自动加一了！！
        					preisimage= false;
        					templocationofy=0;
        					templocationofy =(int)(height>templocationofy?height:templocationofy);
            				}
            				else
            					templocationofy =(int)(height>templocationofy?height:templocationofy);
            			}
            			}
            		ListTable.cursor.setX((short)0);
            		ListTable.cursor.setY((short)(locationofy*baselineheight));

            		offyimage=(short) ((locationofy)*baselineheight+baselineheight);//2011.07.07 liqiang 多加了一个baselineheight
            		if(ListTable.fillcolor&&i>=selectedleftindex&&i<=selectedrightindex)
            		{
            			selectMarginForImage(canvas,relativelocation,offyimage,data);
            		}
            		
            		
            		for (int index = 0; index < strokeSize; index++) {
            			MyStroke stroke = imageunit.getStrokes().get(index);
            			if(Mview.patchangefordrawing== false)
            			{
            				pat.setColor(stroke.getColor());
            				pat.setStrokeWidth(stroke.getStrokeWidth());
            			}

            			ArrayList<BasePoint> list = (ArrayList<BasePoint>)stroke.getPoints();
            			int size = list.size();	
            			Log.e("debug in kview ","stroke.getStrokeWidth() is  "+stroke.getStrokeWidth());
            			if(WritebySurfaceView.getpdfflag==false)
                		{
    					for (int t = 0; t != size - 2; t++)
    					{
//    						if(t==globallistsize-2)
//    	            			   Log.i("debug ","image array max y is "+list.get(t + 1).getY()+ offyimage+" "+globallistsize);
    						if(WritebySurfaceView.getmailflag||(list.get(t).getY()+ offyimage>=(maxscrollvice+ListTable.topmargin+40)&&
    								list.get(t).getY()+ offyimage<=(maxscrollvice+screenheight-ListTable.bottommargin-ListTable.topmargin-50)))
    		    			{
    							
//    							Log.i("debug in kview ","drawing pic !!!"+temp1+" "+temp2);
    							if (list.get(t).getX() == list.get(t+1).getX()&&list.get(t).getY()==list.get(t+1).getY())
    								canvas.drawPoint(list.get(t).getX()+ relativelocation, list.get(t).getY()+ offyimage, pat);
    							else
    							{
    								if(list.get(t+1).getY()+ offyimage>=(maxscrollvice+ListTable.topmargin+40)&&
    	    								list.get(t+1).getY()+ offyimage<=(maxscrollvice+screenheight-ListTable.bottommargin-ListTable.topmargin-50))
    								canvas.drawLine(list.get(t).getX()+ relativelocation, list.get(t).getY()+ offyimage,
    								list.get(t + 1).getX()+ relativelocation,list.get(t + 1).getY()+ offyimage, pat);
    							}
    						}
    					}
                		}
                		else
                		{//getpdf
                			for (int t = 0; t != size - 2; t++)
        					{
        						if(list.get(t).getY()+ offyimage>=(WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight)&&
        								list.get(t).getY()+ offyimage<=((WritebySurfaceView.pdfpagecount+1)*WritebySurfaceView.pdfpageheight))
        		    			{
        							if (list.get(t).getX() == list.get(t+1).getX()&&list.get(t).getY()==list.get(t+1).getY())
        								canvas.drawPoint(list.get(t).getX()+ relativelocation, 
        								list.get(t).getY()+ offyimage-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight, pat);
        							else
        								canvas.drawLine(list.get(t).getX()+ relativelocation, 
        								list.get(t).getY()+ offyimage-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight,
        								list.get(t + 1).getX()+ relativelocation,
        								list.get(t + 1).getY()+ offyimage-WritebySurfaceView.pdfpagecount*WritebySurfaceView.pdfpageheight, pat);
        						}
        					}
                		}

            		}
            		break;
            	case TYPE_CONTROL:
            		switch(data.getCtrlType())
	  				{
            		case TYPE_CTRL_ENTER:
            			if(PreUnitType==DataType.TYPE_IMAGE)
            			{

            				locationofy+=templocationofy;
        					templocationofy=0;
        					locationofy++;

            			}
            			else
            				locationofy++;
            				
	            		ListTable.cursor.setX((short)0);
	            		ListTable.cursor.setY((short)(locationofy*baselineheight));
	            		relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
	                	lastofunitisenter = true;
	  					break;
	  			
            		case TYPE_CTRL_SPACE:
            		{
            	
            			if(PreUnitType==DataType.TYPE_IMAGE)
            			{
            				relativelocation =ListTable.leftmargin;//2011.07.07 liqiang 
//            				Log.i("DEBUG--->","templocationofy  "+templocationofy);
            				locationofy+=templocationofy;
            				locationofy++;
            				templocationofy=0;
//            				Log.i("DEBUG--->","locationofy  "+locationofy);
            			}
            			else
            				;
            			
            			
            			short tempoffy = (short) ((locationofy)*baselineheight-BASEHEIGHT);
            			if(ListTable.fillcolor&&i>=selectedleftindex&&i<=selectedrightindex)
                		{
                			selectMargin(canvas,relativelocation,tempoffy,data);
                		}
            			break;
            		}
	  				}
            		break;
            	default:
            		break;	
            	}
           	relativelocation += data.getWidth();	
           	if(lastofunitisenter== false)				//为了空格单元改过,空格单元有宽度，而换行单元没有，这是在构造函数里实现的
           		relativelocation += spacewidth;
            if((ListTable.temppositionofaddcharunit-1)== i)
            {
             	ListTable.templocationofmouse = 0;
             	ListTable.templocationofmouse = relativelocation;
             	reallocationofy =locationofy;
             	CursorUnitType = ListTable.globalIndexTable.get(i).getDataType();
             	
            }
            
            
            PreUnitType = data.getDataType();				//用来记录前一个单元的类型
            if(PreUnitType==DataType.TYPE_CONTROL)
            	 PreUnitType=data.getCtrlType();
        	}
        	
        	
        	ListTable.cursor.setX((short)ListTable.templocationofmouse);
        	if( CursorUnitType==DataType.TYPE_IMAGE)
        		ListTable.cursor.setY((short)((reallocationofy+1)*baselineheight));
        	else
        		ListTable.cursor.setY((short)(reallocationofy*baselineheight));
        	
        	maxscroll = (reallocationofy-maxscrollvice/baselineheight)- MAXROWINPAGE;
        	int size=ListTable.temppositionofaddcharunit-1;
        	int imageheight=0;

        	if(ListTable.globalIndexTable.size()>size&&ListTable.globalIndexTable.get(size).getDataType()==DataType.TYPE_IMAGE)
        	{
        		imageheight=ListTable.globalIndexTable.get(size).getHeight();
        		imageheight=imageheight/baselineheight;
        	}
        	else
        		imageheight=0;
        	
        	//控制滚屏逻辑
        	if(maxscroll +imageheight>0&&scrollstatus==false&&ListTable.TypeofUnit!=Context_STATE.Context_STATE_2)				//光标位置超过屏幕高度并且不是处在通过上翻、下翻按钮状态
        	{	
        		
        			if(ListTable.edittype== false)			//新建文件翻页情况
        			{
        				//maxscrollvice = MAXROWINPAGE*baselineheight*timeturn;		//记录翻页前的最后坐标
        	   
        				maxscrollvice = baselineheight*(reallocationofy-1);		//记录翻页前的最后坐标
        				maxscrollvice+=imageheight;
        				this.scrollTo(0,maxscrollvice);	
//        				Log.i("DEBUG--->","maxscroll >0  "+(reallocationofy-1));
        				timeturn++;
        			}
        			else 							//编辑文件翻页情况
        			{
        			timeturn = reallocationofy /MAXROWINPAGE;
        			//maxscrollvice = MAXROWINPAGE*baselineheight*timeturn;		//记录翻页前的最后坐标
        			maxscrollvice = baselineheight*(reallocationofy-1);		//记录翻页前的最后坐标
        			maxscrollvice+=imageheight;
        			this.scrollTo(0,maxscrollvice);
//        			Log.i("DEBUG--->","KVIEW    "+Kview.maxscrollvice);
        			Log.i("DEBUG--->","  "+timeturn);
        			timeturn++;
        			}
        		 Log.i("debug in scroll 0","timeturn is "+timeturn );
        	}
        	else if(maxscroll +imageheight<0&&(ListTable.cursor.getY()-maxscrollvice)<=0&&scrollstatus==false&&ListTable.TypeofUnit!=Context_STATE.Context_STATE_2)
        	{
        		timeturn--;
        		int y =baselineheight*(reallocationofy-MAXROWINPAGE+1);
        		y=y>0?y:0;
        		this.scrollTo(0,y);
        		maxscrollvice = y;	
        		 Log.i("debug in scroll 1","timeturn is "+timeturn );
        	}
        	else
        	{
//        		if(maxscrollvice/)
        		timeturn = maxscrollvice/screenheight + 1;
        		Log.i("debug in scroll 2","screenheight is "+screenheight);
        		Log.i("debug in scroll 2","maxscroll is "+maxscroll+" "+reallocationofy+" "+maxscrollvice);
        		Log.i("debug in scroll 2","timeturn is "+timeturn );
        	}
        	ListTable.Sectionnum=locationofy;
        	if(ListTable.globalIndexTable.get(globallistsize-1).getDataType()==DataType.TYPE_IMAGE&&templocationofy!=0)
        	{
        		ListTable.Sectionnum+=templocationofy;
        		templocationofy =0;
        	}
        	if(ListTable.isexport==false)
        	{
        	if((ListTable.cursor.getY()+16 > maxscrollvice+baselineheight)&&(ListTable.cursor.getY()+baselineheight*2<(maxscrollvice+screenheight)))
        	canvas.drawLine(ListTable.cursor.getX(),ListTable.cursor.getY()+16,
        			ListTable.cursor.getX(),ListTable.cursor.getY()+baselineheight, mpaint);//2011.06.23 liqiang
        	}
        	}
        else
        	if(ListTable.isexport==false)
        	{
        	canvas.drawLine(screenwidth/2,baselineheight+16,
        			screenwidth/2,baselineheight*2, mpaint);//zhuxiaoqing 2011.09.27
        	}
        Log.v("DEBUG--->","  "+timeturn);
        drawBaseLine(canvas);//2011.12.20 liqiang
	}   
     private void selectMargin(Canvas canvas,int relativelocation,int offy,DataUnit data)
     {
    	 tempImage.setBounds(relativelocation,offy-(baselineheight-BASEHEIGHT),relativelocation+data.getWidth()+spacewidth,data.getHeight()+offy);
		 tempImage.draw(canvas);
     }
    
     private void selectMarginForImage(Canvas canvas,int relativelocation,int offy,DataUnit data)
     {
    	 tempImage.setBounds(relativelocation,offy,relativelocation+data.getWidth()+spacewidth,data.getHeight()+offy);
		 tempImage.draw(canvas);
     }
     
     
     
     @Override  
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)   
     {  
//     Log.v("onMeasure", "Scroll View on measure...");  
     
     int height=0;
     if(height<screenheight)								//480才是屏幕高度!!
    	 setMeasuredDimension(screenwidth,screenheight); 		//因为为了填充满整个屏幕再只有一页的情况下！
     else
    	 setMeasuredDimension(screenwidth,reallocationofy); 
//     Log.v("SECTION--->",""+ListTable.Sectionnum);
     }  
    
      @Override  
      protected void onScrollChanged(int l, int t, int oldl, int oldt) {  
      super.onScrollChanged(l, t, oldl, oldt); 
     }
     
     
 	
 	

}
