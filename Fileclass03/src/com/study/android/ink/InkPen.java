
package com.study.android.ink;

//import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.MyPoint;

/**
 *@author: fuxinggang
 *@create-time: 2009-4-2 上午09:29:30
 */
public class InkPen {
	
	private static InkPen inkPen = null;
	
	/**
	 * 如果基本笔画宽度在该阈值(4.0f)内则不进行<b>&nbsp;惯性添加点&nbsp;</b>的操作(4.0 是pc机器版本)
	 * 如果基本笔画宽度在该阈值(3.0f)内则不进行<b>&nbsp;惯性添加点&nbsp;</b>的操作（3.0是android版本）
	 */
	private static final float strokeWidthThreshole = 1.0f;
	
	/**
	 * <b>收笔</b>时根据惯性添加点的个数
	 */
	private static final int stopPointsNum = 5;
		
	/** 
	 * 笔划宽度 
	 */
	private float strokeWidth;
	
	/**
	 * 笔划宽度的上限
	 */
	private float upperboundWidth;

	/**
	 * 笔划宽度的下限
	 */
	private float lowerboundWidth;
	/**
	 * 笔划宽度的下限(专门用于写大字的时候)
	 * 
	 */
	private static float  frontlowerboundWidth = 1.0f;
	public static InkPen getInstance(){
		if(inkPen == null){
			inkPen = new InkPen();
		}
		return inkPen;
	}
	
	/**
	 * 设定笔划的基本宽度，并依照此基本宽度计算出宽度调整的上、下阈值
	 * 
	 * @param w：基本宽度
	 */
	public void setStrokeWidth(float width) {
		if (width == strokeWidth)                  //传递的width等于成员strokeWidth则返回
			return;
		strokeWidth = width;
		float low = strokeWidth / 2.0f;	
		upperboundWidth = strokeWidth + 1f;		//！！
		lowerboundWidth = low >1.0f ? low - 1.0f : 1.0f;
	}
	
	/**
	 * 起笔的动作模拟：根据停顿的时间长短 设置起笔点的宽度
	 * 
	 * @param p：起笔的点
	 */
	public void modifyStartPoint(BasePoint p) {
		//float width = this.strokeWidth / 3f;
		float width = this.strokeWidth/2f;
		float w = width > this.upperboundWidth ? this.upperboundWidth : width;
		p.setStrokeWidth(w);
	}
	
	/**
	 * 基于速度（距离）的笔划修改算法，点必须是MyPoint类型， p1的宽度已经确定，p2的宽度需要确定，p3的宽度未知。
	 * 本方法调用之前必须先调用setStrokeWidth设定笔划初始宽度
	 * 
	 * @param p1：第一个点
	 * @param p2：第二个点
	 * @param p3：当前点（第三个）
	 */
	public void modifyStroke(BasePoint p1, BasePoint p2, BasePoint p3) {		//这个函数的作用是根据p1,p2,p3 三点的位置直线距离，以及已知p1的宽度，来确定p2的宽度
		int x1 = p1.getX();
		int y1 = p1.getY();

		int x2 = p2.getX();
		int y2 = p2.getY();

		int x3 = p3.getX();
		int y3 = p3.getY();

		float d1 = (float) Math.pow(((x1 - x2) * (x1 - x2) + (y1 - y2)
			* (y1 - y2)), 0.5);
		float d2 = (float) Math.pow(((x2 - x3) * (x2 - x3) + (y2 - y3)
			* (y2 - y3)), 0.5);
		
		if(d2 == 0){
			d2 = (float) 0.0001;
		}
		float rate = d1 / d2;             //任意两个点间隔时间都是固定的，这里是16 MyPoint.java中。故两点的距离能够量度速度
		if (rate >= 0.95 && rate <= 1.1) {// 匀速
			p2.setStrokeWidth(p1.getStrokeWidth());// 保持不变
			//Log.i("DEBUG-->","rate 0.95--1.10  ---->"+p2.getStrokeWidth());
		} else if (rate > 1.1) {// 减速则笔划渐粗
			float delta = rate / 2.0f;
			float width = p1.getStrokeWidth() + delta;
			p2.setStrokeWidth(width > upperboundWidth ? upperboundWidth : width);
			//Log.i("DEBUG-->","rate >1.1  ---->"+p2.getStrokeWidth());
		} else {// 加速则笔划渐细
			float delta = rate / 2.0f;
			float width = p1.getStrokeWidth() - delta;
			p2.setStrokeWidth(width < lowerboundWidth ? lowerboundWidth : width);
			//Log.i("DEBUG-->","rate <0.95  ---->"+p2.getStrokeWidth());
		}
	}
	
	/**
	 *TODO 还原点的原始宽度 
	 *
	 *@param list
	 */
	public void modifyStroke(List<BasePoint> list) {         
		BasePoint startPoint = list.get(0);
		modifyStartPoint(startPoint);
		
		BasePoint p1 = null, p2 = null, p3 = null;
		
		int size = list.size();
		if(size>=3)
		{
			p1 = list.get(0);
			p2 = list.get(1);
			p3 = list.get(2);
			for (int index = 2; index < size; ) {								//如果index不够p1,p2,p3分配则退出循环
				modifyStroke(p1, p2, p3);										//处理list中的每个点
				if(p3.isEnd()){
					p3.setStrokeWidth(strokeWidth);
					if(index-5>=0&&strokeWidth > strokeWidthThreshole){			//如果p3前面有五个点则分别调整这5个点的宽度并且strokeWidth > strokeWidthThreshole
						p1=list.get(index-5);
						p1.setStrokeWidth(strokeWidth);
						float dw = (strokeWidth - 2) / stopPointsNum;
						for(int j=index-5; j<index; j++){
							p2=list.get(j+1);
							p2.setStrokeWidth((short)(p1.getStrokeWidth() - dw));
							p1=p2;
						}
					}
					if(index<size-1){				//如果p3是一个笔画的结束点但不是字符单元的结束点
						p1=p3;
						p2=list.get(index+1);
						p3=list.get(index+2);
						index+=2;					
					}else 							//如果p3是字符单元的最后一点则退出循环
						break;
				}else {
					p1=p2;
					p2=p3;
					p3=list.get(index+1);
					index+=1;
				}
			}
		}
	}
	
	
	//绘大字时候的数字墨水
	public static void modifyStrokePoints(BasePoint p1, BasePoint p2,float strokeWidth) {
		float x1 = p1.getX();
		float y1 = p1.getY();

		float x2 = p2.getX();
		float y2 = p2.getY();

		double t = ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		
		if(24000 / (t + 0.1)<1)					//距离比较长
			p1.setStrokeWidth(frontlowerboundWidth);
		else
			p1.setStrokeWidth((float) (Math.abs(Math.pow(Math.log(24000 / (t + 0.1)) / 0.31, 0.33)* strokeWidth) / 2));		
		//Log.i("debug","p1 width"+p1.getStrokeWidth());
		}
	
	
	
	//charunit B样插值
	 public static ArrayList<BasePoint>  insertPoints(List<BasePoint> array,boolean isImageTmp)
	 {
	 	
	 	ArrayList<Short> arrayx=new ArrayList<Short>();
	 	ArrayList<Short> arrayy=new ArrayList<Short>();
	 	ArrayList<BasePoint> listinkpen=new ArrayList<BasePoint>();
	 	int numTmp =0;
	 	int numPointsInsert= 2;
	 	int k=0;
	 	if(isImageTmp ==true){//图画每两点之间插3点，文字每两个点之间插一个点
	 		numPointsInsert = 3;	
	 	}
	 	Log.i("debug in insertpoints ","array size "+array.size());
	 	BasePoint temppoint = array.get(array.size()-1);
	 	Log.i("debug in insertpoints","array.get(size-1) "+temppoint.getX()+" "+temppoint.getY()+" "+temppoint.isEnd());
//	 	array.get(array.size()-1).setEnd(true);//2011.12.02 liqiang
//	 	if(!array.get(array.size()-1).isEnd())//2011.12.02 liqiang
//	 		array.remove(array.size()-1);//2011.12.02 liqiang
	 	for(;k!=array.size();)
	 	{
	 	
	 		//下面是对一笔进行插入值
	 
	 		arrayx.add(array.get(k).getX());
	 		arrayy.add(array.get(k).getY());
	 		
	 		numTmp =0;
	 		while(!array.get(k).isEnd())
	 		{
	 			numTmp++;
	 			k++;
	 			
//	 			Log.i("DEBUG--->","get(k) "+k+" "+array.get(k).getX()+"  "+array.get(k).getY());
//	 			Log.i("DEBUG--->","get(k).isEnd   "+array.get(k).isEnd());
	 		}
	 		
	 		numTmp++;
	 		k++;
	 		int pointsNum = numTmp+2;				//pointsNum 标志一笔里面的点数
	 		short pointx,pointy;
	 		
	 		for(int i = 1; i <pointsNum-1; i++)
	 		{
	 			BasePoint point = array.get(k-numTmp+i-1);
	 			pointx = point.getX();
	 			pointy = point.getY();	
	 			arrayx.add(pointx);
	 			arrayy.add(pointy);
	 		}
	 		
	 		short tempx=arrayx.get(numTmp);
	 		short tempy=arrayy.get(numTmp);
	 		arrayx.add(tempx);
	 		arrayy.add(tempy);
	 		
	 		
	 		float t,t1,t2,a,b,c;
	 		t=(float) (1.0/(numPointsInsert+1));
	 		short xx,yy;
	 	
	 		xx = arrayx.get(0);
	 		yy = arrayy.get(0);
	 	
	 		BasePoint inkPenPoint=new BasePoint(xx,yy);
	 		inkPenPoint.setEnd(false);
	 		listinkpen.add(inkPenPoint);
	 	
	 		for(int i=0;i<(pointsNum -2);i++) //点*（x+pointsNum-1）不使用
	 		{ 
	 			for(int j=1;j<(numPointsInsert+1);j++) 
	 			{ 
	 			t1=j*t; 
	 			t2=t1*t1;
	 			a=(float) ((t2-2*t1+1)/2.0);
	 			b=(float) (t1-t2+1/2.0); 
	 			c=(float) (t2/2.0); 
	 			xx=(short) Math.ceil(a*arrayx.get(i)+b*arrayx.get(i+1)+c*arrayx.get(i+2));
	 			yy=(short) Math.ceil(a*arrayy.get(i)+b*arrayy.get(i+1)+c*arrayy.get(i+2));	
	 			inkPenPoint=new BasePoint(xx,yy);
	 			inkPenPoint.setEnd(false);
	 			listinkpen.add(inkPenPoint);
	 			} 
	 		}
	 		listinkpen.get(listinkpen.size()-1).setEnd(true);
	 		arrayx.clear();
	 		arrayy.clear();
	 		//Log.i("DEBUG--->","YI BI CHU LI WAN BI");
	 }
	 	
	 	return listinkpen;
	}
	 

	
}
