package com.study.android.CollectionduringWriting;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.Mview;
import com.study.android.HWtrying.WritebySurfaceView;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.CharPoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.MyStroke;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.CharUnit;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.basicData.type.ImageUnit;
import com.study.android.data.ListTable;
import com.study.android.ink.InkPen;
import com.study.android.zoom.Zoom;

public class addtoListTable {
	private static MyStroke stroke;
	private static DataType PreUnitType=DataType.TYPE_CALLIGRAPHY;		
	private static short screenwidth=Kview.screenwidth;
	private static short spacewidth = (short) ListTable.spacewidth;
	private static short baselineheight=  (short) (ListTable.charactersize+16);
	

	public static void addcharunittoListTable(ArrayList<BasePoint> array, short minX, short minY,
			short maxX, short maxY) 
	{
		// TODO Auto-generated constructor stub
		spacewidth = (short) ListTable.spacewidth;
		baselineheight=  (short) (ListTable.charactersize+16);
		
  		CharUnit charunit1 = new CharUnit((short)(maxX-minX),(short)(maxY-minY),Color.BLUE);  //见MemoryCoder.java 的 startCharUnit ()部分
  		charunit1.setCharFormat(CharFormat.getDefaultCharFormat());	 
  		charunit1.setWidth((short) (charunit1.getWidth() + (CharUnit.ANCHOR << 1)));
  		if(array.size()>5)//鼠标单击的的情况并不添加到ListTable.globalIndexTable里面,点的个数小于8个则认为为单击动作！！
  		{
  			
  		//应该在这地方对array的相对坐标进行重新计算，主要是加上相对于它在一行中上行线的偏移量	
  		
  			
  		for(int i =0 ;i!= array.size();i++)
  		{	
  			BasePoint point = new CharPoint((short)(array.get(i).getX()),(short)(array.get(i).getY()));
  			point.setEnd(array.get(i).isEnd());
  			point.setStrokeWidth(array.get(i).getStrokeWidth());
  			charunit1.addPoint(point);
  		}
  		

  		CharUnit charunit;
  		
  		ArrayList<DataUnit> DataUnitList;
  		ArrayList<BasePoint> basepoint=new ArrayList<BasePoint>();
  		
  		//HWtrying.split(charunit1, minY, maxY);									//切分
  		//DataUnitList  =HWtrying.compressionWithDiffHeight(array,(short) (maxY-minY),minY,maxY);	//切分后返回一系列DATAUNIT
  		
  		Zoom.split(charunit1, minY, maxY);									//切分
  		
  		DataUnitList  =Zoom.compressionWithDiffHeight(array,(short) (maxY-minY),minY,maxY);	//切分后返回一系列DATAUNIT
  		
  		for(int j=0;j!=DataUnitList.size();j++)
  		{
  		charunit = (CharUnit) DataUnitList.get(j);
  		charunit.setCharFormat(CharFormat.getDefaultCharFormat());	
		charunit.setWidth((short) (charunit.getWidth() + (CharUnit.ANCHOR << 1)));
  		int position = ListTable.temppositionofaddcharunit;
  		
  		// * 线性插值部分，主要问题在于，Basepoint里面的x和y都是short，导致插入点后，很多点都是重复的

  		basepoint=InkPen.insertPoints(charunit.getPoints(),false);

  		CharUnit charunitinsert = new CharUnit(charunit.getWidth(),charunit.getHeight(),Color.BLUE);  //见MemoryCoder.java 的 startCharUnit ()部分
  		
  		for(int i =0 ;i!= basepoint.size();i++)
  		{	
  			BasePoint point = new CharPoint((short)(basepoint.get(i).getX()),(short)(basepoint.get(i).getY()));
  			point.setEnd(basepoint.get(i).isEnd());
  			point.setStrokeWidth(basepoint.get(i).getStrokeWidth());
  			charunitinsert.addPoint(point);
  			charunitinsert.setCharFormat(CharFormat.getDefaultCharFormat());	
  		}
  		modifyStroke(charunitinsert.getPoints(),2);	
  		ListTable.globalIndexTable.add(position,charunitinsert);
  		
  		Log.v("ListTable.charunit--->",""+ListTable.temppositionofaddcharunit);
  		
  		
  		ListTable.temppositionofaddcharunit++;
  		if(ListTable.isAutoSpace==true)
  		{
  			ControlUnit controlunit = new ControlUnit(DataType.TYPE_CTRL_SPACE,
  			CharFormat.getDefaultCharFormat());
  			ListTable.globalIndexTable.add(ListTable.temppositionofaddcharunit,controlunit);
  			ListTable.temppositionofaddcharunit++;
  		}
  		
  		}
  		
  		Kview.scrollstatus=false;
  		}
  		else if(array.size()<=5 && array.size()>0 && (array.get(0).getY()+Kview.maxscrollvice)>baselineheight)			//光标定位	,后面那一个条件是为了避免修改标题，让光标定位无法到底第一行！！								  //认定是鼠标单机的动作，如果鼠标单击的位置包含有字符，则重新定义光标的位置
  		{

  			short tempx =(short)(array.get(0).getX()-ListTable.leftmargin);//2011.07.07 liqiang 因为加了页边距，定位光标位置要左移
  			short tempy =(short) (array.get(0).getY()-baselineheight);//2011.06.29 liqiang 因为加了工具栏，定位光标位置要下移
//  			Log.i("debug in addtolist ","tempx and y is "+tempx+" "+tempy);
  			cursorloacteinaddtolist(tempx,tempy);				//文字状态下光标定位
//  			Log.v("ListTable.charunit--->",""+ListTable.temppositionofaddcharunit);
  		}
	}
	
	
//	@SuppressWarnings("unchecked")
	public static void addimageunittoListTable(ArrayList<BasePoint> array, short minX, short minY,
			short maxX, short maxY) 
	{
		stroke = new MyStroke();
		stroke.setStrokeWidth(WritebySurfaceView.paintstrokesize);
		baselineheight=  (short) (ListTable.charactersize+16);
		ArrayList<BasePoint> basepoint=new ArrayList<BasePoint>();
		int zanshi =0;
		ImageUnit imageunit = new ImageUnit((short) ((maxX-minX) + (CharUnit.ANCHOR << 1)),
				(short) ((maxY-minY) + (CharUnit.ANCHOR << 1)));
		
		short realwidth =0,realheight=0;
		realwidth=Kview.screenwidth>Kview.screenheight?Kview.screenheight:Kview.screenwidth;
		realheight = Kview.screenwidth<Kview.screenheight?Kview.screenheight:Kview.screenwidth;
		if(array.size()>5)														  //鼠标单击的的情况并不添加到ListTable.globalIndexTable里面
  		{
			Log.i("DEBUG--->","array.size image  "+array.size()+" "+array.get(array.size()-1).isEnd());
//			basepoint=InkPen.insertPoints(array,true);//2011.12.13 liqiang
//			array.clear();
//			array=(ArrayList<BasePoint>) basepoint.clone();
			Log.i("DEBUG--->","array.size image after insert "+array.size()+" "+array.get(array.size()-1).isEnd());
			Log.e("debug in add","listtablecolorindex is "+ListTable.globalcolorIndex.size());
		for(int i =0 ;i!= array.size();i++)
  		{	
  	
  			if(array.get(i).isEnd()==true)
  			{	
  				Log.i("one stroke ","one stroke !!!!");
  				BasePoint point = new  BasePoint((short)(array.get(i).getX()-minX),(short)(array.get(i).getY()-minY));	
  	  			point.setEnd(array.get(i).isEnd());
  	  		    stroke.addPoint(point);                           //太粗心，开始没在这里添加一笔结束点，请问怎么能读取正确呢？
  	  		    int color=0,strokesize=0;
  	  		    color = ListTable.globalcolorIndex.get(zanshi);
  	  		    strokesize = Mview.paintstrokesizelist.get(zanshi);
  	  		    stroke.setColor(color);
  	  		    stroke.setStrokeWidth(strokesize);
  	  		    imageunit.addStrokes(stroke);
  	  			stroke = new MyStroke();			
  	  			zanshi++;
  	  			Log.e("debug in addto list","strokesize is "+strokesize);
  			}
  			else
  			{ 				
  			BasePoint point = new  BasePoint((short)(array.get(i).getX()-minX),(short)(array.get(i).getY()-minY));
  			point.setEnd(array.get(i).isEnd());
  			stroke.addPoint(point);
  			} 			
  		}
		basepoint.clear();
		Kview.scrollstatus=false;
  		}
		else if(array.size()<=5)
			;
		
		int pos = ListTable.temppositionofaddcharunit;

		if(imageunit.getWidth()>realwidth||imageunit.getHeight()>realheight)
		{
			//ImageUnit imageunitcompress=HWtrying.compression( imageunit,realwidth);
			ImageUnit imageunitcompress=Zoom.compression( imageunit,realwidth);
			ListTable.globalIndexTable.add(pos,imageunitcompress);
		}
		else
			ListTable.globalIndexTable.add(pos,imageunit);
		ListTable.temppositionofaddcharunit++;
	
		}
	
	 private static void modifyStroke(List<BasePoint> list, float strokeWidth) {
	 		InkPen InkPenonMview = InkPen.getInstance();
	 		InkPenonMview.setStrokeWidth(strokeWidth);
	 		InkPenonMview.modifyStroke(list);
	 	}
	 
	 
	 
	 
	 private static void cursorloacteinaddtolist(short tempx,short tempy)
	 {
		 	DataType datatype;
	  		int tempwidth,tempsum,tempcursofy=baselineheight;
	  		int locat =0;
	  		int rowcount =1;
	  	    int tempheight =0;								//用来记录一横条范围内的所有图片单元的最高高度
	  	    int width =0;									//用来记录一横条图片单元的累计宽度
	  	    boolean firstimageunitperline = true;
			int size= ListTable.globalIndexTable.size();
			PreUnitType=DataType.TYPE_CALLIGRAPHY;	
			//short tempx =array.get(0).getX();
			//short tempy =array.get(0).getY();
		
//			Log.v("tempX--->",""+tempx);
//			Log.v("tempY--->",""+tempy);
			
			tempy +=Kview.maxscrollvice;					//这个是为了得到相对于视图的坐标
			tempwidth = 0;
			tempsum = 0;
		for(locat=ListTable.globalTitle.size(); locat!= ListTable.globalIndexTable.size();locat++)
		{
			DataUnit dataunit = ListTable.globalIndexTable.get(locat);
			datatype =dataunit.getCtrlType();
			if(datatype!= DataType.TYPE_CTRL_ENTER)
			{
				tempsum += ListTable.globalIndexTable.get(locat).getWidth();
				tempsum += spacewidth;
				tempwidth+= ListTable.globalIndexTable.get(locat).getWidth()/2.0;

				if(datatype==null)
	  				datatype = ListTable.globalIndexTable.get(locat).getDataType();
				
				if((datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)&&PreUnitType==DataType.TYPE_IMAGE)			//碰到这种情况应该让tempsum＝0；
				{
					tempwidth = 0;
					tempsum = 0;			
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
				}
				if((PreUnitType == DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)&&datatype==DataType.TYPE_IMAGE)			//碰到这种情况应该让tempsum＝0；
				{
					tempwidth = 0;
					tempsum = 0;			
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
				}
			}
			
			if(datatype==null)
				datatype = ListTable.globalIndexTable.get(locat).getDataType();
		
			//if(tempsum/Kview.screenwidth>0||datatype == DataType.TYPE_CTRL_ENTER)		//对于图片的鼠标逻辑控制仍然有问题，能正确定位到图片后无法正确定位到图片前，先放一放
			if(tempsum>Kview.screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改
			{	
				if(PreUnitType == DataType.TYPE_IMAGE&&datatype==DataType.TYPE_IMAGE)
				{
					if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
					tempheight=0;
				}
				else 
					rowcount++;
				tempcursofy	= rowcount*baselineheight;
				tempwidth = 0;
				tempsum = 0;
				tempheight =0;
				width =0;
				firstimageunitperline = true;
				
				//if(datatype!= DataType.TYPE_CONTROL)				//这是在干什么？
				if(datatype!= DataType.TYPE_CTRL_ENTER)
				{
				tempsum = ListTable.globalIndexTable.get(locat).getWidth();
				tempsum += spacewidth;
	  			tempwidth+= ListTable.globalIndexTable.get(locat).getWidth()/2.0;

				}

				if(tempwidth<tempx&&(tempcursofy-tempy)>=baselineheight&&datatype!= DataType.TYPE_CTRL_ENTER)
	  			{
//	  				Log.v("DEBUG---.","jue ding chu lai");
//	  				Log.i("DEBUG--->","tempcursofy-tempy  "+tempcursofy+ "  -  "+tempy);
//	  				Log.i("DEBUG--->","  locat "+locat);
	  				ListTable.temppositionofaddcharunit = locat;
	  				break;
	  			}

			}
			
			if(datatype == DataType.TYPE_IMAGE)
			{
				int height = dataunit.getHeight();
				if(PreUnitType==DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)
				{
					tempwidth = 0;
					tempsum = 0;
					firstimageunitperline =true;		
					tempsum += dataunit.getWidth();
					tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
		  			width =0;
				}
				else if(PreUnitType==DataType.TYPE_CTRL_ENTER)
				{
					tempwidth = 0;
					tempsum = 0;
					firstimageunitperline =true;
					tempsum += dataunit.getWidth();
					tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
					rowcount--;
					width =0;
				}
		
				int templocat= locat+1;

				DataType tempdatatype=DataType.TYPE_CALLIGRAPHY;
				if(templocat!=size && firstimageunitperline ==true)
				{
					width = dataunit.getWidth();
					width+= spacewidth;
					tempdatatype = ListTable.globalIndexTable.get(templocat).getDataType();
					tempheight = dataunit.getHeight();
					
  				width += ListTable.globalIndexTable.get(templocat).getWidth();
					width+=spacewidth;
					
					//用于统计同一条上的所有图片单元的最高高度，每一条上就只要在第一个图片单元处统计即可，后面同一条上的单元不需要统计
					
					while(tempdatatype==DataType.TYPE_IMAGE && templocat!=size&&width<Kview.screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改
					{
					short heigh = ListTable.globalIndexTable.get(templocat).getHeight();
					tempheight=tempheight>heigh?tempheight:heigh;
					templocat++;
					if(templocat!=size)
					{
						tempdatatype = ListTable.globalIndexTable.get(templocat).getDataType();
						if(tempdatatype==DataType.TYPE_IMAGE)
						{
						width += ListTable.globalIndexTable.get(templocat).getWidth();
						width+=spacewidth;
						}
						else
							break;
					}
					else
						break;			
					}
					firstimageunitperline=false;
				}
				else if(templocat==size && firstimageunitperline ==true)		//最后一个单元为图片单元
					tempheight = dataunit.getHeight();
				
				height =tempheight;
				tempcursofy	= rowcount*baselineheight;
				


				//似乎可以屏蔽掉这个判断
	  		//	if(tempwidth<tempx&&(tempcursofy-tempy)>=0)
	  		//	{	
	  		//		ListTable.temppositionofaddcharunit = locat+1;
	  		//		Log.v("DEBUG---.","jue ding chu lai  ListTable.temppositionofaddcharunit   "+ListTable.temppositionofaddcharunit);	
	  		//		
	  		//		break;
	  		//	}
	  			
			
				
	  		//	else if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)	
				if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)//着事我根据今天重新显示规则后生成的确定坐标方式
	  			{	

	  				ListTable.temppositionofaddcharunit = locat;
//	  				Log.i("DEBUG--->","^^^^^^^^^^^^^^   locat "+locat );
//	  				Log.i("DEBUG--->","^^^^^^^^^^^^^^^^^  height   "+height );
//	  				Log.i("DEBUG--->","^^^^^^^^^^^^^^   tempy-tempcursofy   "+tempy+"   "+tempcursofy );
	  				break;		//在一串图片单元中发现添加位置
	  			}
	  			
	  			else if(tempwidth<tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)		//表示点到某一条的所有图片单元的最后面
	  			{
	  				

	  				ListTable.temppositionofaddcharunit = locat+1;
	  				if((locat+1)!=size&&ListTable.globalIndexTable.get(locat+1).getDataType()!=DataType.TYPE_IMAGE)
	  				{	
	  					ListTable.temppositionofaddcharunit = locat+1;
//	  					Log.i("DEBUG---->","rowcount  "+rowcount);
	  					break;
	  				}
	  				
	  				else if((locat+1)!=size&&ListTable.globalIndexTable.get(locat+1).getDataType()==DataType.TYPE_IMAGE)
	  				{
	  					int sumvice = tempsum;
	  					DataUnit dataunittemp = ListTable.globalIndexTable.get(locat+1);
	  					sumvice += dataunittemp.getWidth();
	  					sumvice += spacewidth;

	  					if(sumvice>Kview.screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改	
	  					{
	  						ListTable.temppositionofaddcharunit = locat+1;
//	  						Log.i("DEBUG---->","####  "+rowcount);
//	  						Log.i("DEBUG----->","FDFDF   sumvice  screenwidth  "+sumvice+"  "+screenwidth);
		  					break;
	  					}
	  					else
			  			{
			  				tempwidth=tempsum;
			  				ListTable.temppositionofaddcharunit = locat+1;
			  			}
	  				}

	  			}
				
				//if(tempwidth<tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)
				ListTable.temppositionofaddcharunit = locat+1;				//只要在没有判断退出之前，都必须对光标位置进行实时定位
			}
			
			else if(datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)			//在图片单元下方如果是字符单元
			{
				if(PreUnitType==DataType.TYPE_IMAGE)
				{
					tempwidth = 0;
					tempsum = 0;				
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
		  			if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
		  			rowcount++;				//本身需要换行
		  			
		  			
		  			tempcursofy	= rowcount*baselineheight;
//		  			Log.i("DEBUG--->","rowcount "+rowcount);
				}

				 //下面这两个if判断其实是用来定位CHARUNIT 和SPACEUNIT 的，对于图片单元的定位，在图片单元本身那块
					if(tempx>=tempwidth)	
	  				{
						
						tempwidth = tempsum;
						ListTable.temppositionofaddcharunit = locat+1;
						
						DataUnit tempdata;
						DataType tempdatatype = null;
						if((locat+1)!=size)
						{
							tempdata =  ListTable.globalIndexTable.get(locat+1);
							tempdatatype= tempdata.getDataType();				//用来记录前一个单元的类型
							if(tempdatatype==DataType.TYPE_CONTROL)
			            	tempdatatype=tempdata.getCtrlType();  
						}
						else
							break;
						
						
						if((locat+1)!=size&&(tempdatatype==DataType.TYPE_IMAGE||tempdatatype==DataType.TYPE_CTRL_ENTER )&&(tempy-tempcursofy)<=0&&(datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE))
						{
//	  					Log.i("DEBUG--->","rowcount  ffddfdf" );
	  					break;
	  				
						}
	  				}	
					else if((tempy-tempcursofy)<=0)						//说明这是我要添加的位置
	  				{	
						ListTable.temppositionofaddcharunit = locat;
//						Log.i("DEBUG--->","tempcursofy  **********" );
//						Log.i("DEBUG--->","tempy-tempcursofy   "+tempy+"--"+tempcursofy );
						break;
	  				}
					
					 ListTable.temppositionofaddcharunit = locat+1;       //只要在没有判断退出之前，都必须对光标位置进行实时定位
			}
			else if(datatype == DataType.TYPE_CTRL_ENTER )
			{
				
				
				if(PreUnitType==DataType.TYPE_IMAGE)
 			{
					if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
					tempheight=0;
					rowcount++;							
 			}
				else 
					rowcount++;	
				
				tempcursofy	= rowcount*baselineheight;
				tempwidth = 0;
				tempsum = 0;
				tempheight =0;
				width =0;
				firstimageunitperline = true;
					
			}
			

			 PreUnitType = dataunit.getDataType();				//用来记录前一个单元的类型
          if(PreUnitType==DataType.TYPE_CONTROL)
          	 PreUnitType=dataunit.getCtrlType();    
		}
//		Log.v("ListTable.charunit--->",""+ListTable.temppositionofaddcharunit);
		
  			
  			
		}
	 
	 					
	 
	 //用来响应编辑状态下双击图片时候的光标定位动作
	 public static void cursorloacteinaddtolist(short tempx,short tempy,boolean doubleclick)
	 {
		 	DataType datatype;
	  		int tempwidth,tempsum,tempcursofy=baselineheight;
	  		int locat =0;
	  		int rowcount =1;
	  	    int tempheight =0;								//用来记录一横条范围内的所有图片单元的最高高度
	  	    int width =0;									//用来记录一横条图片单元的累计宽度
	  	    boolean firstimageunitperline = true;
			int size= ListTable.globalIndexTable.size();
			PreUnitType=DataType.TYPE_CALLIGRAPHY;	
			//short tempx =array.get(0).getX();
			//short tempy =array.get(0).getY();
		
			Log.v("tempX--->",""+tempx);
			Log.v("tempY--->",""+tempy);
			
			tempy +=Kview.maxscrollvice;					//这个是为了得到相对于视图的坐标
			tempwidth = 0;
			tempsum = 0;
			
		for(locat=ListTable.globalTitle.size(); locat!= ListTable.globalIndexTable.size();locat++)
		{
			DataUnit dataunit = ListTable.globalIndexTable.get(locat);
			datatype =dataunit.getCtrlType();
			if(datatype!= DataType.TYPE_CTRL_ENTER)
			{
				tempsum += ListTable.globalIndexTable.get(locat).getWidth();
				tempsum += spacewidth;
				tempwidth+= ListTable.globalIndexTable.get(locat).getWidth()/2.0;

				if(datatype==null)
	  				datatype = ListTable.globalIndexTable.get(locat).getDataType();
				
				if((datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)&&PreUnitType==DataType.TYPE_IMAGE)			//碰到这种情况应该让tempsum＝0；
				{
					tempwidth = 0;
					tempsum = 0;			
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
				}
				if((PreUnitType == DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)&&datatype==DataType.TYPE_IMAGE)			//碰到这种情况应该让tempsum＝0；
				{
					tempwidth = 0;
					tempsum = 0;			
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
				}
			}
			
			if(datatype==null)
				datatype = ListTable.globalIndexTable.get(locat).getDataType();
		
			//if(tempsum/Kview.screenwidth>0||datatype == DataType.TYPE_CTRL_ENTER)		//对于图片的鼠标逻辑控制仍然有问题，能正确定位到图片后无法正确定位到图片前，先放一放
			if(tempsum>Kview.screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改
			{	
				if(PreUnitType == DataType.TYPE_IMAGE&&datatype==DataType.TYPE_IMAGE)
				{
					if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
					tempheight=0;
				}
				else 
					rowcount++;
				tempcursofy	= rowcount*baselineheight;
				tempwidth = 0;
				tempsum = 0;
				tempheight =0;
				width =0;
				firstimageunitperline = true;
				
				//if(datatype!= DataType.TYPE_CONTROL)				//这是在干什么？
				if(datatype!= DataType.TYPE_CTRL_ENTER)
				{
				tempsum = ListTable.globalIndexTable.get(locat).getWidth();
				tempsum += spacewidth;
	  			tempwidth+= ListTable.globalIndexTable.get(locat).getWidth()/2.0;

				}

				if(tempwidth<tempx&&(tempcursofy-tempy)>=baselineheight&&datatype!= DataType.TYPE_CTRL_ENTER)
	  			{
//	  				Log.v("DEBUG---.","jue ding chu lai");
//	  				Log.i("DEBUG--->","tempcursofy-tempy  "+tempcursofy+ "  -  "+tempy);
//	  				Log.i("DEBUG--->","  locat "+locat);
	  				ListTable.temppositionofaddcharunit = locat;
	  				break;
	  			}
			}
			
			if(datatype == DataType.TYPE_IMAGE)
			{
				int height = dataunit.getHeight();
				if(PreUnitType==DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)
				{
					tempwidth = 0;
					tempsum = 0;
					firstimageunitperline =true;		
					tempsum += dataunit.getWidth();
					tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
		  			width =0;
				}
				else if(PreUnitType==DataType.TYPE_CTRL_ENTER)
				{
					tempwidth = 0;
					tempsum = 0;
					firstimageunitperline =true;
					tempsum += dataunit.getWidth();
					tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
					rowcount--;
					width =0;
				}
		
				int templocat= locat+1;

				DataType tempdatatype=DataType.TYPE_CALLIGRAPHY;
				if(templocat!=size && firstimageunitperline ==true)
				{
					width = dataunit.getWidth();
					width+= spacewidth;
					tempdatatype = ListTable.globalIndexTable.get(templocat).getDataType();
					tempheight = dataunit.getHeight();
					
					width += ListTable.globalIndexTable.get(templocat).getWidth();
					width+=spacewidth;
					
					//用于统计同一条上的所有图片单元的最高高度，每一条上就只要在第一个图片单元处统计即可，后面同一条上的单元不需要统计
					
					while(tempdatatype==DataType.TYPE_IMAGE && templocat!=size&&width<Kview.screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改
					{
					short heigh = ListTable.globalIndexTable.get(templocat).getHeight();
					tempheight=tempheight>heigh?tempheight:heigh;
					templocat++;
					if(templocat!=size)
					{
						tempdatatype = ListTable.globalIndexTable.get(templocat).getDataType();
						if(tempdatatype==DataType.TYPE_IMAGE)
						{
						width += ListTable.globalIndexTable.get(templocat).getWidth();
						width+=spacewidth;
						}
						else
							break;
					}
					else
						break;			
					}
					firstimageunitperline=false;
				}
				else if(templocat==size && firstimageunitperline ==true)		//最后一个单元为图片单元
					tempheight = dataunit.getHeight();
				
				height =tempheight;
				tempcursofy	= rowcount*baselineheight;
				
	  		//	else if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)	
				if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)//着事我根据今天重新显示规则后生成的确定坐标方式
	  			{	
	  				ListTable.temppositionofaddcharunit = locat;
//	  				Log.i("DEBUG--->","^^^^^^^^^^^^^^   locat "+locat );
//	  				Log.i("DEBUG--->","^^^^^^^^^^^^^^^^^  height   "+height );
//	  				Log.i("DEBUG--->","^^^^^^^^^^^^^^   tempy-tempcursofy   "+tempy+"   "+tempcursofy );
	  				break;		//在一串图片单元中发现添加位置
	  			}
	  			
	  			else if(tempwidth<tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)		//表示点到某一条的所有图片单元的最后面
	  			{
	  				

	  				ListTable.temppositionofaddcharunit = locat+1;
	  				if((locat+1)!=size&&ListTable.globalIndexTable.get(locat+1).getDataType()!=DataType.TYPE_IMAGE)
	  				{	
	  					ListTable.temppositionofaddcharunit = locat+1;
//	  					Log.i("DEBUG---->","rowcount  "+rowcount);
	  					break;
	  				}
	  				
	  				else if((locat+1)!=size&&ListTable.globalIndexTable.get(locat+1).getDataType()==DataType.TYPE_IMAGE)
	  				{
	  					int sumvice = tempsum;
	  					DataUnit dataunittemp = ListTable.globalIndexTable.get(locat+1);
	  					sumvice += dataunittemp.getWidth();
	  					sumvice += spacewidth;

	  					if(sumvice>Kview.screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改	
	  					{
	  						ListTable.temppositionofaddcharunit = locat+1;
//	  						Log.i("DEBUG---->","####  "+rowcount);
//	  						Log.i("DEBUG----->","FDFDF   sumvice  screenwidth  "+sumvice+"  "+screenwidth);
		  					break;
	  					}
	  					else
			  			{
			  				tempwidth=tempsum;
			  				ListTable.temppositionofaddcharunit = locat+1;
			  			}
	  				}

	  			}
				
				//if(tempwidth<tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)
				ListTable.temppositionofaddcharunit = locat+1;				//只要在没有判断退出之前，都必须对光标位置进行实时定位
			}
			
			else if(datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)			//在图片单元下方如果是字符单元
			{
				if(PreUnitType==DataType.TYPE_IMAGE)
				{
					tempwidth = 0;
					tempsum = 0;				
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
		  			if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
		  			rowcount++;				//本身需要换行
		  			
		  			
		  			tempcursofy	= rowcount*baselineheight;
//		  			Log.i("DEBUG--->","rowcount "+rowcount);
				}

				 //下面这两个if判断其实是用来定位CHARUNIT 和SPACEUNIT 的，对于图片单元的定位，在图片单元本身那块
					if(tempx>=tempwidth)	
	  				{
						
						tempwidth = tempsum;
						ListTable.temppositionofaddcharunit = locat+1;
						
						DataUnit tempdata;
						DataType tempdatatype = null;
						if((locat+1)!=size)
						{
							tempdata =  ListTable.globalIndexTable.get(locat+1);
							tempdatatype= tempdata.getDataType();				//用来记录前一个单元的类型
							if(tempdatatype==DataType.TYPE_CONTROL)
			            	tempdatatype=tempdata.getCtrlType();  
						}
						else
							break;

						if((locat+1)!=size&&(tempdatatype==DataType.TYPE_IMAGE||tempdatatype==DataType.TYPE_CTRL_ENTER )&&(tempy-tempcursofy)<=0&&(datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE))
						{
//	  					Log.i("DEBUG--->","rowcount  ffddfdf" );
	  					break;
	  				
						}
	  				}	
					else if((tempy-tempcursofy)<=0)						//说明这是我要添加的位置
	  				{	
						ListTable.temppositionofaddcharunit = locat;
//						Log.i("DEBUG--->","tempcursofy  **********" );
//						Log.i("DEBUG--->","tempy-tempcursofy   "+tempy+"--"+tempcursofy );
						break;
	  				}
					
					 ListTable.temppositionofaddcharunit = locat+1;       //只要在没有判断退出之前，都必须对光标位置进行实时定位
			}
			else if(datatype == DataType.TYPE_CTRL_ENTER )
			{
				
				
				if(PreUnitType==DataType.TYPE_IMAGE)
 			{
					if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
					tempheight=0;
					rowcount++;							
 			}
				else 
					rowcount++;	
				
				tempcursofy	= rowcount*baselineheight;
				tempwidth = 0;
				tempsum = 0;
				tempheight =0;
				width =0;
				firstimageunitperline = true;
					
			}
			
			 PreUnitType = dataunit.getDataType();				//用来记录前一个单元的类型
          if(PreUnitType==DataType.TYPE_CONTROL)
          	 PreUnitType=dataunit.getCtrlType();    
		}
		ListTable.temppositionofaddcharunit--;
		Log.v("ListTable.charunit--->",""+ListTable.temppositionofaddcharunit);	
		}

}



