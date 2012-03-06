package com.study.android.retrieval;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.study.android.HWtrying.PView;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataUnit;
import com.study.android.data.ListTable;

//public class Retrieveview extends View implements OnTouchListener, OnGestureListener
public class Retrieveview extends View 
{
	//用于索引显示的字体大小和间距大小是固定的，跟设置独立开来的
	public ArrayList<BasePoint> array;
	public ArrayList<DataUnit> Title;
	private static int locationofy	=1;
	/**
	 * 2011.09.27 liqiang
	 * 日志列表中绘画标题时的偏移量
	 */
	public static int retrieveviewleftmargin = 0;
	/**
	 * 2011.10.24 liqiang 
	 * 日志列表中标题右边距
	 */
	public int titlerightmargin = 0;
	private static boolean lastofunitisenter = false;
	private static short screenwidth=ListTable.ScreenWidth,screenheight=ListTable.ScreenHeight;
	@SuppressWarnings("unchecked")
	public Retrieveview (Context context,AttributeSet attrit) {
		super(context,attrit);
		this.setBackgroundColor(Color.TRANSPARENT);
		//Title = (ArrayList<DataUnit>) ListTable.templist.clone();
		Title = (ArrayList<DataUnit>) MyAdapter.templist.clone();
		//ListTable.templist.clear();
		MyAdapter.templist.clear();
		Log.e("debug in retriview","clear !!!");
	}

	protected void onDraw(Canvas canvas)					//这个的绘图方法是直接复制Kview 中的绘图方法的！！！
	{ 
		screenwidth=PView.screenwidth;
		screenheight=PView.screenheight;
		titlerightmargin = screenwidth/10;//2011.10.24 liqiang
		if(screenwidth == 0 || screenheight == 0 )
			return;
		
		locationofy	=1;
		lastofunitisenter = false;
		Paint pat = new Paint();	        
		pat.setAntiAlias(true);			//去锯齿
        pat.setStrokeWidth(3);
       // pat.setColor(Color.RED);
       // pat.setColor(Color.rgb(0,128,0));				//RGB:0,128,0 --> 深绿色
        pat.setColor(Color.rgb(255,80,0));
      //  pat.setColor(Color.rgb(0,100,0));			//各颜色rgb值见收藏的网页
//        pat.setColor(Color.MAGENTA);
		super.onDraw(canvas);
        canvas.translate(10, 1);
        //canvas.drawColor(Color.WHITE);   
        canvas.drawColor(Color.TRANSPARENT);
    	Paint paint = new Paint();
    	paint.setColor(Color.RED);
    	paint.setAntiAlias(true);			//去锯齿
        paint.setStrokeWidth(3);
        Paint paintline = new Paint();
        paintline.setColor(Color.RED);    
        if(Title.size()!=0)
        {
        	Log.i("debug in retrieveview ","title size is "+Title.size()); 
        	int relativelocation =0;
        	
        	for(int i = 0;  i < Title.size();i++)
        	{	
            	DataUnit data  = Title.get(i);
            	lastofunitisenter = false;
     //       	if((relativelocation+data.getWidth())/320>0)
            	//2011.03.06 bug divide by zero ***********************************	
            	if((relativelocation+data.getWidth()+8)/screenwidth>0)	
            		//这快需要改动！！不应该用ListTable.screenwidth来代表宽度，而应该用ShowRetrieve.java 自身的静态变量，
            	{	
            		locationofy++;
            		ListTable.cursor.setX((short)0);
                	ListTable.cursor.setY((short)(locationofy*36));
                	relativelocation =0;

            	}
            	switch(data.getDataType())
            	{
            	case TYPE_CHAR:
            		if(relativelocation==0)//2011.05.17 liqiang
            			relativelocation = retrieveviewleftmargin;//2011.05.17 liqiang
            		Log.v("debug in retrieveview  ","retrieveviewleftmargin is "+retrieveviewleftmargin);
            		short offy = (short) (locationofy*36-data.getHeight()+3);
            		array = (ArrayList<BasePoint>)data.getPoints();
            		for(int t=0;t<=array.size()-2;)
            		{
            			if(!array.get(t+1).isEnd())
                		{
//            				if((array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY())
//        							||array.get(t+1).isEnd())
//        					{
//            					Log.i("debug in retrieve ","losing !!!!!!!!!");
//        						pat.setStrokeWidth(array.get(t).getStrokeWidth()*3);
//        						canvas.drawPoint(array.get(t).getX()+relativelocation, array.get(t).getY()+offy, pat);
//        					}
//            				else
//            				{
            					pat.setStrokeWidth(3);
            				    canvas.drawLine(array.get(t).getX()+relativelocation, 
            						array.get(t).getY()+offy,array.get(t+1).getX()+relativelocation,
            						array.get(t+1).getY()+offy,pat);
//            				}
            				t++;
                		}
            			else
            			{
            				if((array.get(t).getX()==array.get(t+1).getX()&&array.get(t).getY()==array.get(t+1).getY()))
        					{
//            					Log.i("debug in retrieve ","losing !!!!!!!!!");
        						pat.setStrokeWidth(array.get(t).getStrokeWidth());
        						canvas.drawPoint(array.get(t).getX()+relativelocation, array.get(t).getY()+offy, pat);
        					}
            				else
            				{
            					pat.setStrokeWidth(3);
            					canvas.drawLine(array.get(t).getX()+relativelocation, 
                						array.get(t).getY()+offy,array.get(t+1).getX()+relativelocation,
                						array.get(t+1).getY()+offy,pat);
            				}
            				t+=2;
            			}
            		}
                break;
            	case TYPE_IMAGE:
            		
            		break;
            	case TYPE_CONTROL:
            		switch(data.getCtrlType())
	  				{
            		case TYPE_CTRL_ENTER:
	  					locationofy++;
	                	relativelocation =0;
	                	lastofunitisenter = true;
	  					break;
            		case TYPE_CTRL_SPACE:
	  					break;
	  				}
            		break;
            	default:
            		break;	
            	}
           	relativelocation += data.getWidth();
           	Log.i("debug in retrieveview ","height and width is "+ListTable.ScreenHeight+" "+ListTable.ScreenWidth+" "+i);
           	if(relativelocation>ListTable.ScreenWidth-titlerightmargin)//2011.10.24
           		break;
           	if(lastofunitisenter== false)				//为了空格单元改过,空格单元有宽度，而换行单元没有，这是在构造函数里实现的
           	{
           		relativelocation += 8;
           	}
        	}    
        }
        else//标题大小为0
        {
        	
        }

	}

	}
