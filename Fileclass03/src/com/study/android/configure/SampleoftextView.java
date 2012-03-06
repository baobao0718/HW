package com.study.android.configure;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;

import com.study.android.HWtrying.HWtrying;
import com.study.android.HWtrying.PView;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataUnit;
import com.study.android.code.DocumentDecoder;
import com.study.android.configure.InputCharacterConfigure;
import com.study.android.data.ListTable;
import com.study.android.ui.HwFunctionWindow;

public class SampleoftextView extends View{
	
	//用于索引显示的字体大小和间距大小是固定的，跟设置独立开来的
	public ArrayList<BasePoint> array;
	public ArrayList<DataUnit> SampleofText=new ArrayList<DataUnit>();
	private static int locationofy	=1;
	private static int offy	=0;
	private static int relativeofx =0;
	private static int reallocationofy =1;
	private static boolean lastofunitisenter = false;
	private static float x =0;
	private static float y =0;
	private static short screenwidth=ListTable.ScreenWidth,screenheight=ListTable.ScreenHeight;
	private DocumentDecoder documentDecoder;
	private short spacewidth = (short) ListTable.spacewidth;
	private short baselineheight=  (short) (ListTable.charactersize+16);
	private short BASEHEIGHT = (short)ListTable.charactersize;
	private short LANDOFFSET=3;
	private short PORTRAITOFFSET=40;
	public short sampleoftextoffset=PORTRAITOFFSET;

	public SampleoftextView(Context context,AttributeSet attrit) {
		super(context,attrit);
		this.setBackgroundColor(Color.TRANSPARENT);
	}

		
	protected void onDraw(Canvas canvas)					//这个的绘图方法是直接复制Kview 中的绘图方法的！！！
	{ 
		
		InputCharacterConfigure.fhwe.reset();
		InputStream fhwe=InputCharacterConfigure.fhwe;
		
		if(fhwe==null)
			return;
		readDataFromFileForSampleText(fhwe);
		
		canvas.drawColor(Color.WHITE);
		
		screenwidth=PView.screenwidth;
		screenheight=PView.screenheight;
		spacewidth = (short) ListTable.spacewidth;
		baselineheight=  (short) (ListTable.charactersize+16);
		BASEHEIGHT = (short)ListTable.charactersize;
		 
		locationofy	=0;
		offy	=0;
		relativeofx =0;
		reallocationofy =1;
		lastofunitisenter = false;
	
		if(screenwidth<screenheight)
			sampleoftextoffset=PORTRAITOFFSET;
		else
			sampleoftextoffset=LANDOFFSET;
		
		Paint pat = new Paint();	        
		pat.setAntiAlias(true);			//去锯齿
        pat.setStrokeWidth(3);
//        pat.setColor(Color.MAGENTA);
        pat.setColor(ListTable.charactercolor);
		super.onDraw(canvas);
        canvas.translate(10, 10);
        canvas.drawColor(Color.TRANSPARENT);
    
        if(SampleofText.size()!=0)
        {
        	 
        	int relativelocation =0;
        	//Log.i("DEBUG--->"," SampleofTextfddddddddd  "+ SampleofText.size());
        	//Log.i("DEBUG--->"," SampleofTextfeeeeeeeee  "+ ListTable.ScreenWidth+"  "+ListTable.ScreenHeight);
//        	Log.i("debug in sample ","sampleoftextsize is "+SampleofText.size());
        	for(int i = 0;  i < SampleofText.size();i++)
        	{	
            	DataUnit data  = SampleofText.get(i);
            	lastofunitisenter = false;
     //       	if((relativelocation+data.getWidth())/320>0)
            	if((relativelocation+data.getWidth()+spacewidth)/screenwidth>0)									//这快需要改动！！不应该用ListTable.screenwidth来代表宽度，而应该用ShowRetrieve.java 自身的静态变量，
            	{	
            		locationofy++;
            		ListTable.cursor.setX((short)0);
                	//ListTable.cursor.setY((short)(locationofy*36));
                	ListTable.cursor.setY((short)(locationofy*baselineheight));
                	relativelocation =0;
                	relativeofx =i-1;
            	}
            	switch(data.getDataType())
            	{
            	case TYPE_CHAR:
            		//short offy = (short) (locationofy*36-data.getHeight());
            		short offy = (short) ((locationofy)*baselineheight-BASEHEIGHT);
            		array = (ArrayList)data.getPoints();
            		for(int t=0;t<=array.size()-2;)
            		{
            			if(!array.get(t+1).isEnd())
                		{
            				
            				canvas.drawLine(array.get(t).getX()+relativelocation, 
            						array.get(t).getY()+offy+sampleoftextoffset,
            						array.get(t+1).getX()+relativelocation,
            						array.get(t+1).getY()+offy+sampleoftextoffset,pat);
            				t++;
                		}
            			else
                		t+=2;
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
	                	relativeofx =i-1;
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
           	if(lastofunitisenter== false)				//为了空格单元改过,空格单元有宽度，而换行单元没有，这是在构造函数里实现的
           	{
           		//relativelocation += 8;
           		relativelocation += spacewidth;
           	}
        	}    
        }

	}
	
	
	private void readDataFromFileForSampleText(InputStream fhwe)
	{
		HWtrying.readDataFromFileForSample(fhwe);
		SampleofText.clear();
		int size=ListTable.globalIndexTable.size();
		for(int i=5;i!=size;i++)
		{
			DataUnit dataunit=(DataUnit) ListTable.globalIndexTable.get(i);
			SampleofText.add(dataunit);
		}
		
		//Log.i("DEBUG--->"," SampleofText  "+ SampleofText.size());
		ListTable.globalIndexTable.clear();
	}
}
