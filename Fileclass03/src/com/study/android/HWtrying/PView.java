package com.study.android.HWtrying;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.MyStroke;
import com.study.android.basicData.type.ImageUnit;
import com.study.android.data.ListTable;
import com.study.android.retrieval.ShowRetrieve;

public  class PView extends View
{
	public ArrayList<BasePoint> array1=new ArrayList<BasePoint>();
	private static int locationofy	=1;
	private static int offy	=0;
	private static int relativeofx =0;
	private static int reallocationofy =1;
	private static int templocationofy =0;
	private int tempt=0;
	private int bgcolor = ListTable.bgcolor;
	private static short spacewidth = (short) ListTable.spacewidth;
	private short baselineheight= (short) (ListTable.charactersize+16);							//行线基准高度，可以动态设置
	private short BASEHEIGHT = (short)ListTable.charactersize;
	public static short screenwidth=ListTable.ScreenWidth,screenheight=ListTable.ScreenHeight;
	private static boolean lastofunitisenter = false;
	private boolean preisimage = false;
	private DataType PreUnitType=DataType.TYPE_CALLIGRAPHY;	
	Object lock= new Object();
	private TimecounterBitmap timecounterbitmap = new TimecounterBitmap(1000);
	private Path    mPath;
	public static Paint pat = new Paint();
	private volatile  boolean  readover=false;
	
	//private GlobalApp myApp;
	private float pointsize = 3f;//2011.04.21 liqiang


	
	
	public PView(Context context,AttributeSet attrit)
	{
		super(context,attrit);
		readover=false;
		mPath = new Path();
		//timecounterbitmap.start();					//用于启动canvas到bitmap的转换线程！！
//		Log.i("debug","pview construct");				
		 	      
	}
	public PView(Context context)
	{
		super(context);
//		Log.i("debug","pview construct another");
	}


	
	protected void onDraw(Canvas canvas)					//这个的绘图方法是直接复制Kview 中的绘图方法的！！！
	{ 
//		Log.i("debug in pview ","pview ondraw");
		super.onDraw(canvas);
		locationofy	=1;
		offy	=0;
		reallocationofy =1;
		lastofunitisenter = false;
		PreUnitType=DataType.TYPE_CALLIGRAPHY;		     
        pat.setAntiAlias(true);			//去锯齿
        pat.setStrokeWidth(3);
        pat.setStyle(Paint.Style.STROKE);
        pat.setStrokeJoin(Paint.Join.ROUND);
        pat.setStrokeCap(Paint.Cap.ROUND);
        spacewidth = (short) ListTable.spacewidth;
        baselineheight=(short) (ListTable.charactersize+16);
        BASEHEIGHT = (short)ListTable.charactersize;
		
       // canvas.translate(10, 10);		    
        canvas.drawColor(bgcolor);					//我们设定的背景颜色
        Paint paintline = new Paint();
        paintline.setColor(Color.GRAY);
	    for(int i=0;i!=ListTable.Sectionnum*12;i++)
	    {
	    	canvas.drawLine(0,i*baselineheight,screenwidth,i*baselineheight,paintline);
	    }
	    
        if(ListTable.globalIndexTable.size()!=0)
        {
        	 
        	int relativelocation =0;
        	templocationofy =0;
        	for(int i = 0;  i < ListTable.globalIndexTable.size();i++)
        	{	
        		
            	DataUnit data  = ListTable.globalIndexTable.get(i);
            	lastofunitisenter = false;
            	if(screenwidth!=0)
            	{
            	if((relativelocation+data.getWidth())>PView.screenwidth)
            	{	
            		locationofy++;
                	relativelocation =0;
                	if(data.getDataType()==DataType.TYPE_IMAGE)
                	{
                		locationofy--;
                		preisimage = true;					//这个变量是在表示，当图片单元连着写要换行时候，进行标记，
                	}
                	if(data.getDataType()==DataType.TYPE_CHAR&&PreUnitType==DataType.TYPE_IMAGE)		//这样是为了防止出现这样的情况，一行图片单元写满后，再写一个charunit，此时自动换行，已经自动加1，而到下面的时候，根据字符前面如果是图片单元，行高又会自动加一，所以先在这里减1即可
                		locationofy--;
            	}
            	}
            	else
            	{
            		System.exit(0);
            	}
            	switch(data.getDataType())
            	{
            	case TYPE_CHAR:
            		if(PreUnitType==DataType.TYPE_IMAGE)
        			{	
        				relativelocation =0;
        				locationofy+=templocationofy;
        				locationofy++;
        				templocationofy=0;
        				}

            		short offy = (short) (locationofy*baselineheight-BASEHEIGHT);
            		
            		pat.setColor(Color.BLACK);
            		ArrayList<BasePoint> array = (ArrayList)data.getPoints(); 
            		for(int t=0;t<=array.size()-2;)
            		{
            			if(t+1<=array.size()-1)
            			{
            				if(!array.get(t+1).isEnd())
            				{
            				
            				tempt=t+1;
            				if(array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY())
            				{
            					pat.setStrokeWidth(pointsize);
            					canvas.drawPoint(array.get(t).getX()+relativelocation, array.get(t).getY()+offy, pat);
            				}
            				else
            				{
            					pat.setStrokeWidth(array.get(t).getStrokeWidth());
            					canvas.drawLine(array.get(t).getX()+relativelocation, array.get(t).getY()+offy,array.get(tempt).getX()+relativelocation,array.get(tempt).getY()+offy,pat);
            				}
            					t++;
            				}
            				else
            				{
            					if(array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY())
            					{
            						pat.setStrokeWidth(pointsize);
            						canvas.drawPoint(array.get(t).getX()+relativelocation, array.get(t).getY()+offy, pat);
            					}
            					else
                				{
                					pat.setStrokeWidth(array.get(t).getStrokeWidth());
                					canvas.drawLine(array.get(t).getX()+relativelocation, array.get(t).getY()+offy,array.get(tempt).getX()+relativelocation,array.get(tempt).getY()+offy,pat);
                				}
                					t+=2;
            				}
            			}
            			else
            				break;
            		}
            		
            		
            		//array.clear();
            		
            		
            		/*	
            		//利用drawpath画图！！		
            		for(int t=0;t<=array.size()-1;)
            		{
            				touch_start(array.get(t).getX()+relativelocation,array.get(t).getY()+offy);
            				if(t+2<=array.size()-1)
            				{
            					while(!array.get(t+2).isEnd()&&(t+2)<=array.size()-1)
            					{
            		        	touch_move(array.get(t+1).getX()+relativelocation,array.get(t+1).getY()+offy);
            		        	t++;
            					}
            				}
            		        
            		        if((t+1)<=array.size()-1)
            		        	touch_move(array.get(t+1).getX()+relativelocation,array.get(t+1).getY()+offy);
            		        if((t+2)<=array.size()-1)
            		        	touch_move(array.get(t+2).getX()+relativelocation,array.get(t+2).getY()+offy);
            		//        pat.setStrokeWidth();
            		        touch_up(canvas);
            				t+=3;
            		}
            		
            		*/
                break;
            	case TYPE_IMAGE:
            		ImageUnit imageunit = (ImageUnit)data.clone();
            		int strokeSize = imageunit.getStrokes().size();		
            		int height = imageunit.getHeight();
            		short offyimage=0;
            		if(height/baselineheight>=1)
            		{
            			if(PreUnitType==DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)
            			{
            				
    
            				if(height%baselineheight!=0)
            					height=height/baselineheight+1;			//其实是height=(height/baselineheight+1)+1
            				else
            					height = height/baselineheight;
            				
            				relativelocation =0;
            				templocationofy =0;
            				templocationofy =height>templocationofy?height:templocationofy;
            				preisimage= false;
            			}
            			else if(PreUnitType==DataType.TYPE_CTRL_ENTER)
            			{
            				
            				if(height%baselineheight!=0)
            					height=height/baselineheight+1;			
            				else
            					height = height/baselineheight;
            				
            				locationofy--;	
            				templocationofy =0;
        					templocationofy =height>templocationofy?height:templocationofy;
            				relativelocation =0;
        					preisimage= false;
            			}
            			else if(PreUnitType==DataType.TYPE_IMAGE)
            			{
            				if(height%baselineheight!=0)
            					height=height/baselineheight+1;			
            				else
            					height = height/baselineheight;
            				
            				
            				if(preisimage==true) 
            				{
            					relativelocation =0;
            					locationofy+=templocationofy;
            			//		locationofy--;					//这里之所以要再减一，就是因为自动换行的时候locationofy已经自动加一了！！
            					preisimage= false;
            					templocationofy=0;
            					templocationofy =height>templocationofy?height:templocationofy;
            				}
            				else
            					templocationofy =height>templocationofy?height:templocationofy;
            			}
            		}
            		else if(height/baselineheight<1)
            			{
            			if(PreUnitType==DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)
            			{
            				height = 1;
            				templocationofy =0;
            				templocationofy =height>templocationofy?height:templocationofy;
            				relativelocation =0;
            				preisimage= false;
            			}
            			else if(PreUnitType==DataType.TYPE_CTRL_ENTER)
            			{
            				height = 1;
            				locationofy--;	
            				templocationofy =0;
            				templocationofy =height>templocationofy?height:templocationofy;
        					relativelocation =0;
        					preisimage= false;
            			}
            			else if(PreUnitType==DataType.TYPE_IMAGE)
            			{
            				height = 1;
            				if(preisimage==true) 
            				{
            				relativelocation =0;
        					locationofy+=templocationofy;
        				//	locationofy--;					
        					preisimage= false;
        					templocationofy=0;
        					templocationofy =height>templocationofy?height:templocationofy;
            				}
            				else
            					templocationofy =height>templocationofy?height:templocationofy;
            			}
            			}


                //	offyimage=(short) ((locationofy)*36);
            		offyimage=(short) ((locationofy)*baselineheight);
            		
            		for (int index = 0; index < strokeSize; index++) {
            			
            			MyStroke stroke = imageunit.getStrokes().get(index);
            			pat.setColor(stroke.getColor());
            			
            			float strokeWidth = stroke.getStrokeWidth();
            			pat.setStrokeWidth(strokeWidth);
            			ArrayList<BasePoint> list = (ArrayList)stroke.getPoints();
            			int size = list.size();
            			for(int t=0;t!=size-2;t++)
            			{
            				if (list.get(t).getX() == list.get(t+1).getX()&&list.get(t).getY()==list.get(t+1).getY())
								canvas.drawPoint(list.get(t).getX()+ relativelocation, list.get(t).getY()+ offyimage, pat);
							else
								canvas.drawLine(list.get(t).getX()+ relativelocation, list.get(t).getY()+ offyimage,
								list.get(t + 1).getX()+ relativelocation,list.get(t + 1).getY()+ offyimage, pat);
            			}

            			}
            		break;
            	case TYPE_CONTROL:
            		switch(data.getCtrlType())
	  				{
            		case TYPE_CTRL_ENTER:
            		//	Log.i("DEBUG--->","ENTERUNIT");
            			if(PreUnitType==DataType.TYPE_IMAGE)
            			{
            				locationofy+=templocationofy;
        					templocationofy=0;
        					locationofy++;
            			}
            			else
            				locationofy++;

	                	relativelocation =0;
	                	lastofunitisenter = true;
	  					break;
	  				
            		case TYPE_CTRL_SPACE:
            	
            			if(PreUnitType==DataType.TYPE_IMAGE)
            			{
            			//	Log.i("DEBUG--->","locat  "+i);
            				relativelocation =0;
//            				Log.i("DEBUG--->","templocationofy  "+templocationofy);
            				locationofy+=templocationofy;
            				locationofy++;
            				templocationofy=0;
            			}
            			break;
            		}
            		break;
            	default:
            		break;	
            	}
           	relativelocation += data.getWidth();	
           	if(lastofunitisenter== false)				//为了空格单元改过,空格单元有宽度，而换行单元没有，这是在构造函数里实现的
           	{
           		//relativelocation += 8;					//8应该替换为我们设定的间距宽度
           		relativelocation +=spacewidth;
           	}

            
            PreUnitType = data.getDataType();				//用来记录前一个单元的类型
            if(PreUnitType==DataType.TYPE_CONTROL)
            	 PreUnitType=data.getCtrlType();   
        	}   
        	
        	 
        	readover=true;
        
        }
        

	}
	
	
	
	public void canvastobitmap()														
	{
//		 if(ListTable.bitmap!=null&&!ListTable.bitmap.isRecycled())										//资源回收，以免占用内存以及良好的编程习惯，android中，图片占用超过6M,即报内存溢出异常
//		    	ListTable.bitmap.recycle();
//		
//		 ListTable.bitmap= Bitmap.createBitmap(this.getWidth(),this.getHeight(), Bitmap.Config.ARGB_8888);
//		 //Bitmap b = Bitmap.createBitmap(this.getWidth(),this.getHeight(), Bitmap.Config.ARGB_8888);
//		Log.i("DEBUG--->","PVIEW WIDTH HEIHGT "+this.getWidth()+"  "+this.getHeight());
//	  //  Canvas newcanvas = new Canvas(b);
//		Canvas newcanvas = new Canvas(ListTable.bitmap);
//	    this.draw(newcanvas);													//这句是核心！！把当前视图的内容画到新的canvas中，VIEW.draw(Canvas)
	    //ListTable.bitmap=b.copy(Config.ARGB_8888,false);
	    
	   // if(!b.isRecycled())
	   // 	b.recycle();														//回收，以免占用内存

	}
	
	
     @Override  
     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)   
     {  
//     Log.v("onMeasure", "Scroll View on measure...");  
//     Log.v("SECTION--->",""+ListTable.Sectionnum);
     int height=0;
     height =(ListTable.Sectionnum+1) *baselineheight;
     if(height<screenheight)								//480才是屏幕高度!!
    	 setMeasuredDimension(screenwidth,screenheight); 		//因为为了填充满整个屏幕再只有一页的情况下！
     else
    	// setMeasuredDimension(320,height); 
    	 setMeasuredDimension(screenwidth,height+10); 
     }  
    
      @Override  
      protected void onScrollChanged(int l, int t, int oldl, int oldt) {  
      super.onScrollChanged(l, t, oldl, oldt); 
     }
      
      
      
      
      private Timer timerbitmap= new Timer();
      

  	  class TimecounterBitmap {
  		  
  	  private int milseconds=0;
  	  
  	  public TimecounterBitmap(int second)
  		{
  			this.milseconds = second;
  		}
  	  
  		public void start()
  		{
  			timerbitmap.schedule(new TimerTask()
  			{
  				
  				public void run()
  				{	
  						canvastobitmap();
  				}
  			}, milseconds);
  		}	
  	}
  	
  	
  	
      
  		
  		private float mX, mY;
  	    private static final float TOUCH_TOLERANCE = 4;
  	    
  		 private void touch_start(float x, float y) {
  	         mPath.reset();
  	         mPath.moveTo(x, y);
  	         mX = x;
  	         mY = y;
  	     }
  	     private void touch_move(float x, float y) {
  	         float dx = Math.abs(x - mX);
  	         float dy = Math.abs(y - mY);
  	         if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
  	             mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
  	             mX = x;
  	             mY = y;
//  	           Log.i("debug","pview touchmoving");
  	         }
  	     }
  	     private void touch_up(Canvas canvas) {
  	         mPath.lineTo(mX, mY);
  	         // commit the path to our offscreen
  	      //   mCanvas.drawPath(mPath, pat);
  	         canvas.drawPath(mPath, pat);
  	         // kill this so we don't double draw
  	         mPath.reset();
  	     }
  	     
  	     
  	     
}


