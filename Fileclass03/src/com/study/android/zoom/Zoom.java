package com.study.android.zoom;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.Mview;
import com.study.android.HWtrying.WritebySurfaceView;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.CharPoint;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.MyStroke;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.CharUnit;
import com.study.android.basicData.type.ImageUnit;
import com.study.android.data.ListTable;

public class Zoom {

	 private static List<BasePoint> slice;//���ź����ıʻ�����Ƭ��Ԫ��ֵ��ʾ��ǰ��Ƭ����xֵ��ÿ����Ԫ��BasePoint(CharPoint)����
	 public static short BASEHEIGHT = 0;
	 private static short shuxiejizhun = (short) ListTable.shuxiejizhun;
	 private static short anchor = 2;												//��ʱ����Ϊ�ڸ�Ϊ200�ĵط�
	 private static short Interval = 40;
	 private static short temp = (short) (Kview.screenheight/2-shuxiejizhun/2);
	 public static float ratecompress =0;
	 public static ArrayList<DataUnit> SplitIndexTable = new ArrayList<DataUnit>(); 
	 private static List<BasePoint> strokes;//��ʻ�
	 private static List<BasePoint> points;
	 private static short minSplitSpace;
	 private static short maxSplitSpace;
	 private static short minWidth;												//��׼�ߵ���ײ�������Ļ�·��ľ���
	 public static short FirstLineLocation =(short) (Kview.screenheight-Mview.bottomofshuxiebaseline-3*shuxiejizhun);
	 
	 public static CharUnit compression(CharUnit charunit1)								   //����ѹ��charunit
	    {
	    	CharUnit dataUnit = (CharUnit) charunit1.clone();
			float ratioI = dataUnit.getFormatHeight() / (float) dataUnit.getHeight();
	    	//ratioI = 32 / (float) dataUnit.getHeight();
//			Log.v("DEBUG----->","YA SUO BI "+ratioI);
			dataUnit.setWidth((short) ((short)dataUnit.getWidth()*ratioI));
			dataUnit.setHeight((short) ((short)dataUnit.getHeight()*ratioI));          //ѹ���߶�����ʽ�߶�
			if (dataUnit.getPoints() != null) {// �����ַ�û�е�
				for (BasePoint point : dataUnit.getPoints()) {
					if (point.isEnd() == true) {
						point.setX((short) Math
								.ceil((float) (point.getX() * ratioI)));
						point.setEnd(true);
					} else {
						point.setX((short) Math
								.ceil((float) (point.getX() * ratioI)));
					}
					point.setY((short) Math.round((float) (point.getY() * ratioI)));
					point.setStrokeWidth((float)(ratioI * point.getStrokeWidth()));
				}
			}
	    	return dataUnit;
	    }

	   
	    public static CharUnit compression(DataUnit charunit1,short formatheight)								   //����ѹ��charunit
	    {
	    	CharUnit dataUnit = (CharUnit) charunit1.clone();
			float ratioI = formatheight / (float) dataUnit.getHeight();
			dataUnit.setWidth((short) ((short)dataUnit.getWidth()*ratioI));
			dataUnit.setHeight((short) ((short)dataUnit.getHeight()*ratioI));          //ѹ���߶�����ʽ�߶�
			if (dataUnit.getPoints() != null) {// �����ַ�û�е�
				for (BasePoint point : dataUnit.getPoints()) {
					if (point.isEnd() == true) {
						point.setX((short) Math
								.ceil((float) (point.getX() * ratioI)));
						point.setEnd(true);
					} else {
						point.setX((short) Math
								.ceil((float) (point.getX() * ratioI)));
					}
					point.setY((short) Math.round((float) (point.getY() * ratioI)));
					point.setStrokeWidth((float)(ratioI * point.getStrokeWidth()));
				}
			}
	    	return dataUnit;
	    }
	    
	    
	    public static ImageUnit compression(ImageUnit imageunit1,short realscreenwidth)								   //����ѹ��charunit
	    {
	    	ImageUnit dataUnit = (ImageUnit) imageunit1.clone();
		//	ratioI = formatheight / (float) dataUnit.getHeight();
	    	float ratioI = realscreenwidth / (float) dataUnit.getWidth();
//			Log.v("DEBUG----->","YA SUO BI "+ratioI);
			dataUnit.setWidth((short) ((short)dataUnit.getWidth()*ratioI));
			dataUnit.setHeight((short) ((short)dataUnit.getHeight()*ratioI));          //ѹ���߶�����ʽ�߶�
			
			if (dataUnit.getStrokes()!= null) {// �����ַ�û�е�
				for(int i=0;i!=dataUnit.getStrokes().size()	;i++)
				{
					MyStroke mystroke= dataUnit.getStrokes().get(i);
					ArrayList<BasePoint> strokepoints = (ArrayList<BasePoint>) mystroke.getPoints();
					for (int j=0;j!=strokepoints.size();j++) {
						BasePoint point = strokepoints.get(j);
						if (point.isEnd() == true) {
						point.setX((short) Math
								.ceil((float) (point.getX() * ratioI)));
						point.setEnd(true);
						} 
						else {
						point.setX((short) Math
								.ceil((float) (point.getX() * ratioI)));
						}
					point.setY((short) Math.round((float) (point.getY() * ratioI)));
					//point.setStrokeWidth((float)(ratioI * point.getStrokeWidth()));
					}
					mystroke.setStrokeWidth((float) (ratioI*mystroke.getStrokeWidth()));
				}
			}
	    	return dataUnit;
	    }
	    
	    
	    public static ArrayList<DataUnit> compressionWithDiffHeight(ArrayList<BasePoint> array ,short height ,short lowY ,short highY)		//����������ܹ��洢��ͬ�߶ȵĵ�Ԫ��ѹ������������CharUnit ԭʼ������������Ӧ�ı���ѹ��
	    {
	    	CharUnit dataUnit = null; 	
	    	
	    	
	    	int sliceSize=slice.size();
	    	//Log.i("DEBUG---->","slice.size  "+sliceSize);
	    	ArrayList<ArrayList<BasePoint>> pvs = new ArrayList<ArrayList<BasePoint>>();
	    	
	    	for(int i=0;i!=sliceSize;i++)
			{
			ArrayList<BasePoint> v1 = new ArrayList<BasePoint>();
			pvs.add(v1);
			}
	    	
	    	
	    	for(int i = 0 ; i <array.size(); i++){
	    		
	    		BasePoint p = array.get(i);
	    		for(int j=0;j < sliceSize;j++){
	    			BasePoint slicePoint = slice.get(j);
	    			short rightValue = slicePoint.getY();
		
	    			if(p.getX() <= rightValue){
	    				pvs.get(j).add(p);
	    				break;
	    			}
	    		}
	    		
	    	}
	    	
	    
	    	//Log.i("DEBUG---->","slice.size11111  "+sliceSize);
	    	
	    	double rate=0;
	    	short offsetY=0;
	    	BASEHEIGHT = (short) ListTable.charactersize;
	    	shuxiejizhun = (short) ListTable.shuxiejizhun;
	   	    
	    	
	    	//temp = (short) (Kview.screenheight/2-shuxiejizhun/2);
	    	//FirstLineLocation =(short) (temp-shuxiejizhun);

	    	
	    	temp = (short) (Kview.screenheight-Mview.bottomofshuxiebaseline);
	    	FirstLineLocation =(short) (temp-3*shuxiejizhun);
	    	//Log.i("DEBUG---->","slice.size222222  "+sliceSize);
			if(height > (BASEHEIGHT - (anchor << 1))){
	    		rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / height;
	    		offsetY = anchor;
	    		//Log.i("DEBUG--->","rate    "+rate);
	    	}else {
	    		rate = 1.0;
	    		offsetY = (short) ((BASEHEIGHT - height)/2);
	    	}
			
			int firstLine = FirstLineLocation;
			int forthLine = temp;
			
			//int forthLine = FirstLineLocation+Interval*3;
			//��һ���ߺ����һ����֮��ľ���
			int BaseH = forthLine-firstLine+1;
			//��ߵ��ֵĸ߶�
			int tmpH = BaseH;
			int max = 0;
			int offset = 0;
			
			//Log.i("DEBUG--->","  lowY  firstLine  forthLine  "+lowY+"  "+firstLine+"  "+forthLine);
			if(lowY < firstLine){//�ϳ���һ����
				
				if(highY <= forthLine){//�²������һ����
					
					if(highY < firstLine){//�ֵ������ڵ�һ���ߵ�����
						//��������
						//Log.i("DEBUG  -->","FDSFD");
					}else
					{
						if((highY-firstLine)<0.3*(highY-lowY)){//���ķ�Χ��������߶ȵ�30%����������
						}
						else
						{
							tmpH = BaseH + 2 * (firstLine - lowY);
							offset = 0;
							rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
							offsetY	= (short) (offset * rate + anchor);
						}
					}
					
				}else{//�³����һ����
					max = (firstLine - lowY)>(highY - forthLine)?(firstLine - lowY):(highY - forthLine);
					tmpH = BaseH + 2 * max;
					offset = max - (firstLine - lowY);
					rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
					offsetY	= (short) (offset * rate + anchor);
				}
				
			}else{//�ϲ�����һ����
				
				if(highY <= forthLine){//�²������һ����
					
					tmpH = BaseH;
					offset = lowY - firstLine;
					rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
					offsetY	= (short) (offset * rate + anchor);
//					Log.i("DEBUG--->","DAO LE ....");
				}else{//�³����һ����
					
					if(lowY >= forthLine){//�ֵ����������һ���ߵ�����
						//��������
					}else
					{
						if((forthLine-lowY)<0.3*(highY-lowY))
						{//���ķ�Χ���������ָ߶ȵ�50%����������
						}
						else {
							tmpH = BaseH + 2 * (highY - forthLine);
							offset = (lowY - firstLine)+(highY - forthLine);
							rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
							offsetY	= (short) (offset * rate + anchor);					
						}
					}
				}
			}

			ratecompress=(float) rate;
			//Log.i("DEBUG--->","rate   &&&&  "+ratecompress);
			
			for(int i=0;i!=slice.size();i++)
			{
				//dataUnit1=(CharUnit) zoomwithDiffHeight(dataunit,height,rate ,offsetY);
				dataUnit=(CharUnit) zoomwithDiffHeight(pvs.get(i),rate ,lowY,offsetY);
				SplitIndexTable.add(dataUnit);
			}
			
	    	return SplitIndexTable;
	    	
	    }
	    
	    
	    public static CharUnit compressionWithDiffHeightread(DataUnit dataunit ,short height ,short lowY ,short highY)		//����������ܹ��洢��ͬ�߶ȵĵ�Ԫ��ѹ������������CharUnit ԭʼ������������Ӧ�ı���ѹ��
	    {
	    	CharUnit dataUnit1 = null;
	    	
	    	double rate=0;
	    	short offsetY=0;
	    	BASEHEIGHT = (short) ListTable.charactersize;

				rate = 1.0 * (BASEHEIGHT) / height;
	  
			 dataUnit1=(CharUnit) zoomwithDiffHeightRead(dataunit,height,rate,offsetY);
	    	return dataUnit1;
	    }
	    
	    
	    
	    public static DataUnit compressionForInnerText(ArrayList<BasePoint> array ,short height ,short lowY ,short highY,short InnerTextBaseHeight)		//�������������Ƕ�ֵ�Ԫ����ѹ���ķ���
	    
	    {
	    	CharUnit dataUnit = null; 	
	    	double rate=0;
	    	short offsetY=0;
	    	//BASEHEIGHT = (short) ListTable.charactersize;
	    	BASEHEIGHT = InnerTextBaseHeight;
	    	shuxiejizhun = (short) ListTable.shuxiejizhun;
	   	    
	    	//temp = (short) (Kview.screenheight/2-shuxiejizhun/2);
	    	//FirstLineLocation =(short) (temp-shuxiejizhun);
	    	
	    	temp = (short) (Kview.screenheight-Mview.bottomofshuxiebaseline);
	    	FirstLineLocation =(short) (temp-3*shuxiejizhun);
	    	

			if(height > (BASEHEIGHT - (anchor << 1))){
	    		rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / height;
	    		offsetY = anchor;
	    		//Log.i("DEBUG--->","rate    "+rate);
	    	}else {
	    		rate = 1.0;
	    		offsetY = (short) ((BASEHEIGHT - height)/2);
	    	}
			
			int firstLine = FirstLineLocation;
			int forthLine = temp;
			
			//int forthLine = FirstLineLocation+Interval*3;
			//��һ���ߺ����һ����֮��ľ���
			int BaseH = forthLine-firstLine;
			//��ߵ��ֵĸ߶�
			int tmpH = BaseH;
			int max = 0;
			int offset = 0;
			
			if(lowY < firstLine){//�ϳ���һ����
				
				if(highY <= forthLine){//�²������һ����
					
					if(highY < firstLine){//�ֵ������ڵ�һ���ߵ�����
						//��������
						//Log.i("DEBUG  -->","FDSFD");
					}else
					{
						if((highY-firstLine)<0.3*(highY-lowY)){//���ķ�Χ��������߶ȵ�30%����������
						}
						else
						{
							tmpH = BaseH + 2 * (firstLine - lowY);
							offset = 0;
							rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
							offsetY	= (short) (offset * rate + anchor);
							
						}
					}
					
				}else{//�³����һ����
					max = (firstLine - lowY)>(highY - forthLine)?(firstLine - lowY):(highY - forthLine);
					tmpH = BaseH + 2 * max;
					offset = max - (firstLine - lowY);
					rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
					offsetY	= (short) (offset * rate + anchor);
				}
				
			}else{//�ϲ�����һ����
				
				if(highY <= forthLine){//�²������һ����
					
					tmpH = BaseH;
					offset = lowY - firstLine;
					rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
					offsetY	= (short) (offset * rate + anchor);
				}else{//�³����һ����
					
					if(lowY >= forthLine){//�ֵ����������һ���ߵ�����
						//��������
					}else
					{
						if((forthLine-lowY)<0.3*(highY-lowY))
						{//���ķ�Χ���������ָ߶ȵ�50%����������
						}
						else {
							tmpH = BaseH + 2 * (highY - forthLine);
							offset = (lowY - firstLine)+(highY - forthLine);
							rate = 1.0 * (BASEHEIGHT - (anchor << 1)) / tmpH;
							offsetY	= (short) (offset * rate + anchor);					
						}
					}
				}
			}
				dataUnit=(CharUnit) zoomForInnerText(array,rate ,lowY,offsetY,BASEHEIGHT);
	    	return dataUnit;
	    }
	    
	    
	    //���������ר�Ÿ������ļ���ȡTitle�õ�
	    public static CharUnit compressionWithDiffHeightreadForTitle(DataUnit dataunit ,short height ,short lowY ,short highY,short listheight)		//����������ܹ��洢��ͬ�߶ȵĵ�Ԫ��ѹ������������CharUnit ԭʼ������������Ӧ�ı���ѹ��
	    {
	    	CharUnit dataUnit1 = null;
	    	
	    	double rate=0;
	    	short offsetY=0;
			rate = 1.0 * listheight/ height;
			dataUnit1=(CharUnit) zoomwithDiffHeightForTitle(dataunit,height,rate,offsetY,listheight);
			
	    	return dataUnit1;
	    	
	    }
	    
	  
	    public static DataUnit zoomwithDiffHeight(ArrayList<BasePoint> pointsTmp,double rateTmp ,short lowY,short offsetY)
	    {
	    	
	    	// ������ұ߽磬������
	    	
	    	short lowX, highX;
	    	BasePoint p =pointsTmp.get(0);
	    	short x,y ;
	    	
	    	lowX = p.getX();
	    	highX = lowX;
	    	
	    	for(int i = 1; i <pointsTmp.size(); i++)
	    	{
	    	
	    		p = pointsTmp.get(i);
	    		x = p.getX();
	    		if(x < lowX){
	    			lowX = x;
	    		}else if(x > highX){
	    			highX = x;
	    		}
	    	}
	    	
	    	
	    	CharUnit dataunit = new CharUnit((short)(highX-lowX),(short)BASEHEIGHT,Color.BLUE);  //��MemoryCoder.java �� startCharUnit ()����
	  		dataunit.setCharFormat(CharFormat.getDefaultCharFormat());	 

	    	short width  = (short)(highX - lowX + 1);
	    	width = (short)((int)(width * rateTmp+0.5) + (anchor << 1));
	    	int offsetX = CharUnit.ANCHOR;
	    	int tmpNum = 0;
	    	dataunit.setWidth(width);
	    	dataunit.setHeight(BASEHEIGHT);
	    	
	 
	    		for(int i =0 ;i!= pointsTmp.size();i++)
	      		{	
	    			p = pointsTmp.get(i);
	    	    	
	        		x = p.getX();
	        		x -= lowX;
	        		x = (short)((int)(x * rateTmp + 0.5) + offsetX);
	        		
	        		y = p.getY();
	        		y -=lowY;
	        		y=(short) (y * rateTmp + 0.5 + offsetY);
	        		
	      		//	BasePoint point = new BasePoint((short)(array.get(i).getX()-minX),(short)(array.get(i).getY()-minY),array.get(i).isEnd(),array.get(i).getStrokeWidth());
	      			BasePoint point = new CharPoint(x,y);
	      			
	      			point.setEnd(p.isEnd());
	      			dataunit.addPoint(point);
	      		}
	    	
	    	
	    	return dataunit;
	    }

	    
	    public static DataUnit zoomwithDiffHeightRead(DataUnit charunit1, short height,double rateTmp ,short offsetY)
	    {
	    	
	    	short width = charunit1.getWidth();    	
	    	int tmpNum = 0;
	    	width = (short)((int)(width * rateTmp) + (anchor << 1));
	    	CharUnit dataUnit = (CharUnit) charunit1.clone();
			dataUnit.setWidth(width);
			dataUnit.setHeight((short) ((short)BASEHEIGHT)); 
			
			if (dataUnit.getPoints() != null) {// �����ַ�û�е�
				for (BasePoint point : dataUnit.getPoints()) {
					if (point.isEnd() == true) {
						point.setX((short) Math
								.ceil((float) (point.getX() * rateTmp)));
						point.setEnd(true);
					} else {
						point.setX((short) Math
								.ceil((float) (point.getX() * rateTmp)));
					}
					point.setY((short) Math.round((float) (point.getY() * rateTmp ) + offsetY));
					//point.setStrokeWidth((float)(rateTmp* point.getStrokeWidth()));
				}
			}
		
	    	return dataUnit;
	    }
	    public static DataUnit zoomwithDiffHeightForTitle(DataUnit charunit1, short height,double rateTmp ,short offsetY,short listheight)
	    {
	    	
	    	short width = charunit1.getWidth();    	
	    	int tmpNum = 0;
	    	width = (short)((int)(width * rateTmp) + (anchor << 1));
	    	CharUnit dataUnit = (CharUnit) charunit1.clone();
	    	
			dataUnit.setWidth(width);
			//dataUnit.setHeight((short) ((short)charunit1.getHeight()*rateTmp));          //ѹ���߶�����ʽ�߶�,ratioI��Ҫ���滻����������
			dataUnit.setHeight((short) ((short)listheight)); 
			
			if (dataUnit.getPoints() != null) {// �����ַ�û�е�
				for (BasePoint point : dataUnit.getPoints()) {
					if (point.isEnd() == true) {
						point.setX((short) Math
								.ceil((float) (point.getX() * rateTmp)));
						point.setEnd(true);
					} else {
						point.setX((short) Math
								.ceil((float) (point.getX() * rateTmp)));
					}
					point.setY((short) Math.round((float) (point.getY() * rateTmp ) + offsetY));
					point.setStrokeWidth((float)(rateTmp* point.getStrokeWidth()));
				}
			}
			
	    	return dataUnit;
	    }
	    
	    
	    public static DataUnit zoomForInnerText(ArrayList<BasePoint> pointsTmp,double rateTmp ,short lowY,short offsetY,short InnerTextBaseHeight)
	    {
	    	
	    	// ������ұ߽磬������
//	    	Log.v("SHI JIAN DAO LA 33333333!!!!!", "!!!");
	    	short lowX, highX;
	    	BasePoint p =pointsTmp.get(0);
//	    	Log.v("SHI JIAN DAO LA --------!!!!!", "!!!");
	    	short x,y ;
	    	
	    	lowX = p.getX();
	    	highX = lowX;
	    	
	    	
	    	for(int i = 1; i <pointsTmp.size(); i++)
	    	{
	    	
	    		p = pointsTmp.get(i);
	    		x = p.getX();
	    		if(x < lowX){
	    			lowX = x;
	    		}else if(x > highX){
	    			highX = x;
	    		}
	    	}
	    	
	    	
	    	CharUnit dataunit = new CharUnit((short)(highX-lowX),(short)InnerTextBaseHeight,Color.BLUE);  //��MemoryCoder.java �� startCharUnit ()����
	  		dataunit.setCharFormat(CharFormat.getDefaultCharFormat());	 

	    	short width  = (short)(highX - lowX + 1);
	    	width = (short)((int)(width * rateTmp+0.5) + (anchor << 1));
	    	int offsetX = CharUnit.ANCHOR;
	    	int tmpNum = 0;
	    	
	    	dataunit.setWidth(width);
	    	dataunit.setHeight(InnerTextBaseHeight);
	 
	    		for(int i =0 ;i!= pointsTmp.size();i++)
	      		{	
	    			p = pointsTmp.get(i);
	    	    	
	        		x = p.getX();
	        		x -= lowX;
	        		x = (short)((int)(x * rateTmp + 0.5) + offsetX);
	        		
	        		y = p.getY();
	        		y -=lowY;
	        		y=(short) (y * rateTmp + 0.5 + offsetY);
	        		
	      		//	BasePoint point = new BasePoint((short)(array.get(i).getX()-minX),(short)(array.get(i).getY()-minY),array.get(i).isEnd(),array.get(i).getStrokeWidth());
	      			BasePoint point = new CharPoint(x,y);
	      			point.setEnd(p.isEnd());
	      			//point.setStrokeWidth(p.getStrokeWidth());
	      			dataunit.addPoint(point);
	      		}
	    	
	    	
	    	return dataunit;
	    }

	    
	   
	    
	    public static void split(DataUnit dataunit,short minY,short maxY)							//�����dataunit�����Ǻܶ��������dataunitд��һ����
	    {
	    	SplitIndexTable.clear();
	    	// ��һ������������зֵĸ߶�
	    	int height = maxY - minY + 1;
	    	BASEHEIGHT = (short) ListTable.charactersize;
	    	if(height < BASEHEIGHT){
	    		height = BASEHEIGHT;
	    	}
	    	
	    	
	    	// ������������
	    	if(ListTable.isAutoSpace == false)//isAutoSpace Ӧ��Ϊĳ��ȫ�ֱ�������Button�����ƣ�˵������д���ģ��������ĵ��зַ���
	    	{
	    		minSplitSpace = (short)(height * 0.17); // �з���С������������зֵļ��
	    		maxSplitSpace = (short)(height * 0.26);  // �з��������������Ҫ�п��ļ��
	    		minWidth      = (short)(height * 0.36);  // ����������С��ȣ�С�ڸÿ���������Һϲ�	
	    	}
	    	else 
	    	{
	    		minSplitSpace = (short)(height * 0.26); // �з���С������������зֵļ��
	    		maxSplitSpace = (short)(height * 0.26);  // �з��������������Ҫ�п��ļ��
	    		minWidth  = (short)(height * 0.36);  // ����������С��ȣ�С�ڸÿ���������Һϲ�	
	    	}
	    	
	    	
	    	// �ڶ������ַ�
	    	coarseSplit(dataunit);
	    	
	    	
	    	// �����������������Ⱥ����Ҽ�����кϲ�
	    	merge();
	    	
	    }
	    
	    
	    
	    private static void coarseSplit(DataUnit dataunit)	//���з�
	    {
	    	
	    	
	    	strokes= new ArrayList<BasePoint>();//��ʻ�
	    	points = new ArrayList<BasePoint>();
	    	slice = new ArrayList<BasePoint>();//���ź����ıʻ�����Ƭ��Ԫ��ֵ��ʾ��ǰ��Ƭ����xֵ��ÿ����Ԫ��BasePoint(CharPoint)����
	    	
	    	// �ѵ���Ϣ���ձʻ����֣��ʻ����ʹ��Point�࣬����x��ʾ��ߣ�y��ʾ�ұ�
	    	
	    	
	    	short left=0, right=0;
	    	
	    	left = -1;
	    	points = dataunit.getPoints();
	    	int pointsSize =points.size();
	    	
	    	BasePoint p;
	    	BasePoint tmp;
	    	
	    	for(int i = 0; i < pointsSize; i++)
	    	{
	    		p =points.get(i);
	    		if(left == -1)
	    		{
	    			left =p.getX();
	    			right = left;
	    		}
	    		else 
	    		{
	    			short x = p.getX();
	    			if(x < left)
	    			{
	    				left = x;
	    			}
	    			else if(x > right)
	    			{
	    				right = x;
	    			}
	    		}
	    		

	    		if(p.isEnd()){

	    			tmp =new BasePoint(left,right);
	    			strokes.add(tmp);
	    			left = -1;
	    			//Log.i("DEBUG--->","strokes -->"+strokes.size());
	    		} 
	    	}
	    	
	    	// �ϲ�x�����ӽ��ıʻ�
	    	short strokesSize =(short) strokes.size();
	    	
	    	BasePoint pi;
	    	for(int i = strokesSize - 1; i>0; --i){
	    		pi = strokes.get(i);
	    		
	    		BasePoint pj;
	    		for(int j = i - 1; j>=0; --j){
	    			pj =strokes.get(j);
	    			
	    			if(((pi.getX() - pj.getY()) < minSplitSpace) && ((pj.getX() - pi.getY()) < minSplitSpace)){
	    				// �ϲ������ʻ�Ϊһ����Ԫ
	    			
	    				if(pi.getX() < pj.getX()){
	    					pj.setX(pi.getX());
	    				}
	    				if(pi.getY()> pj.getY()){
	    					pj.setY(pi.getY());
	    				}
	    				strokes.remove(i);
	    				break;
	    			}
	    		}
	    	}
	    	
	    	
	    	// �Աʻ�����x������ʹ�ü򵥵�ѡ������
	    	for(int i = 0 ; i <strokes.size(); i++)
	    	{
	    		BasePoint ps =strokes.get(i);
	    		slice.add(ps);
	    	}
	    	
	    	short sliceSize = (short) slice.size();
	    	if(sliceSize > 1){
	    		//����С����ǰ
	    		selectSort(slice);
	    	}

	    	strokes.removeAll(strokes);
	    }
	    
	    
	    static void merge()//���������Ⱥ����Ҽ�����кϲ�
	    {
	    	
	    	
	    	int sliceSize = slice.size();
	    	if(slice.size()== 1){
	    		return; 
	    	}
	    	
	    	
	    	// ���Ժϲ��ڶ���֮����Ƭ
	    	for(int i=sliceSize - 1; i>=0;--i){
	    		
	    		
	    		if((slice.get(i).getY() -slice.get(i).getX()+ 1) < minWidth){
	    			// ���������С����С��ȣ����������Ƿ���Ҫ�ϲ���ֻ�ܺϲ�С��һ��
	    			int left = maxSplitSpace;
	    			int right = maxSplitSpace;
	    			if(i>0){
	    				
	    				left = slice.get(i).getX() -slice.get(i-1).getY() + 1;
	    			}
	    			if(i<sliceSize - 1){
	    				
	    				right = slice.get(i+1).getX() - slice.get(i).getY() + 1;
	    			}
	    			
	    			if(right < maxSplitSpace && right < left)
	    			{
	    				// ����ұ߱���߼����С����ϲ��ұ�
	    				slice.get(i).setY(slice.get(i+1).getY());
	    				slice.remove(i+1);		
	    				--sliceSize;
	    				
	    			}
	    			else if(left < maxSplitSpace)
	    			{
	    				// ����������ҲС������зּ������ϲ����
	    				slice.get(i-1).setY(slice.get(i).getY());
	    				slice.remove(i);	
	    				--sliceSize;
	    				
	    			}
	    			else 
	    			{
	    				// �����������е�С���������Ҽ�����󣬲��˺ϲ�
	    			}
	    		}
	    	}
	    }
	  
	    
	    private static void selectSort(List<BasePoint> arr)
	    {
	    	
	    	if(arr.isEmpty() ||arr.size()<2)
	    	{
	    		return;
	    	}
	    	
	    	for(int i=1; i<arr.size(); ++i){
	    		BasePoint o = arr.get(i);//arr[i];
	    		int j=i-1;
	    		for(; j>=0; --j){
	    			BasePoint oo = arr.get(j);
	    			if(oo.getX() <= o.getX())
	    			{
	    				//�ҵ�oҪ�����λ��
	    				break;
	    			}	
	    		}
	    		if(j != i-1){
	    		
	    			arr.remove(i); //removeObject:o];
	    			arr.add(j+1,o);	// [arr insertObject:o atIndex:(j+1)];	
	    		}
	    	}
	    }	    
}
