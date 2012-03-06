
package com.study.android.ink;

//import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.MyPoint;

/**
 *@author: fuxinggang
 *@create-time: 2009-4-2 ����09:29:30
 */
public class InkPen {
	
	private static InkPen inkPen = null;
	
	/**
	 * ��������ʻ�����ڸ���ֵ(4.0f)���򲻽���<b>&nbsp;������ӵ�&nbsp;</b>�Ĳ���(4.0 ��pc�����汾)
	 * ��������ʻ�����ڸ���ֵ(3.0f)���򲻽���<b>&nbsp;������ӵ�&nbsp;</b>�Ĳ�����3.0��android�汾��
	 */
	private static final float strokeWidthThreshole = 1.0f;
	
	/**
	 * <b>�ձ�</b>ʱ���ݹ�����ӵ�ĸ���
	 */
	private static final int stopPointsNum = 5;
		
	/** 
	 * �ʻ���� 
	 */
	private float strokeWidth;
	
	/**
	 * �ʻ���ȵ�����
	 */
	private float upperboundWidth;

	/**
	 * �ʻ���ȵ�����
	 */
	private float lowerboundWidth;
	/**
	 * �ʻ���ȵ�����(ר������д���ֵ�ʱ��)
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
	 * �趨�ʻ��Ļ�����ȣ������մ˻�����ȼ������ȵ������ϡ�����ֵ
	 * 
	 * @param w���������
	 */
	public void setStrokeWidth(float width) {
		if (width == strokeWidth)                  //�����ʲô��˼����
			return;
		strokeWidth = width;
		float low = strokeWidth / 2.0f;	
		upperboundWidth = strokeWidth + 1f;		//�����ҸĹ�����
		lowerboundWidth = low >1.0f ? low - 1.0f : 1.0f;
	}
	
	/**
	 * ��ʵĶ���ģ�⣺����ͣ�ٵ�ʱ�䳤�� ������ʵ�Ŀ��
	 * 
	 * @param p����ʵĵ�
	 */
	public void modifyStartPoint(BasePoint p) {
		//float width = this.strokeWidth / 3f;
		float width = this.strokeWidth/2f;
		float w = width > this.upperboundWidth ? this.upperboundWidth : width;
		p.setStrokeWidth(w);
	}
	
	/**
	 * �����ٶȣ����룩�ıʻ��޸��㷨���������MyPoint���ͣ� p1�Ŀ���Ѿ�ȷ����p2�Ŀ����Ҫȷ����p3�Ŀ��δ֪��
	 * ����������֮ǰ�����ȵ���setStrokeWidth�趨�ʻ���ʼ���
	 * 
	 * @param p1����һ����
	 * @param p2���ڶ�����
	 * @param p3����ǰ�㣨��������
	 */
	public void modifyStroke(BasePoint p1, BasePoint p2, BasePoint p3) {		//��������������Ǹ���p1,p2,p3 �����λ��ֱ�߾��룬�Լ���֪p1�Ŀ�ȣ���ȷ��p2�Ŀ��
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
		float rate = d1 / d2;             //������������ʱ�䶼�ǹ̶��ģ�������16 MyPoint.java�С�������ľ����ܹ������ٶ�
		if (rate >= 0.95 && rate <= 1.1) {// ����
			p2.setStrokeWidth(p1.getStrokeWidth());// ���ֲ���
			//Log.i("DEBUG-->","rate 0.95--1.10  ---->"+p2.getStrokeWidth());
		} else if (rate > 1.1) {// ������ʻ�����
			float delta = rate / 2.0f;
			float width = p1.getStrokeWidth() + delta;
			p2.setStrokeWidth(width > upperboundWidth ? upperboundWidth : width);
			//Log.i("DEBUG-->","rate >1.1  ---->"+p2.getStrokeWidth());
		} else {// ������ʻ���ϸ
			float delta = rate / 2.0f;
			float width = p1.getStrokeWidth() - delta;
			p2.setStrokeWidth(width < lowerboundWidth ? lowerboundWidth : width);
			//Log.i("DEBUG-->","rate <0.95  ---->"+p2.getStrokeWidth());
		}
	}
	
	/**
	 *TODO ��ԭ���ԭʼ��� 
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
		if (strokeWidth <= strokeWidthThreshole) {
			for (int i = 0; i < size; ++i) {
				if(i+2<size)
				{
				p1 = list.get(i);
				p2 = list.get(++i);
				p3 = list.get(++i);
				while (true) {
					modifyStroke(p1, p2, p3);	//���ú󣬴�ʱ��p2�Ŀ���Ѿ�ȷ����˳�����ӣ���p2��Ϊp1��p3��Ϊp2
					p1 = p2;                  //��һ���ֵ����ǰһ�㣬��Ҫ��������ʼ�����õ��ǳ�ʼ��ȣ��������ᣬ����õ���
					p2 = p3;
					if(i == size - 1)
						break;
					
					p3 = list.get(++i);
					if (p3.isEnd() == true) {// p3�ǽ�����Ļ�
						modifyStroke(p1, p2, p3);
						p3.setStrokeWidth(strokeWidth);
						if (i < (size - 1))       	//�����������ʱ��һ���ַ���Ԫ����δ������ֻ��һ���ʻ������˶���s         
							i--; 						 //ΪʲôҪ��ô������
						break;
					}
				}
				}
			}
		} else {
			for (int i = 0; i < size; ++i) {
				if(i+2<size)
				{
				p1 = list.get(i);
				p2 = list.get(++i);
				p3 = list.get(++i);
				while (true) {
					modifyStroke(p1, p2, p3);
					p1 = p2;
					p2 = p3;
					if(i == size - 1)
						break;
					p3 = list.get(++i);
					if (p3.isEnd() == true&&(i-5)>=0) {// p3�ǽ�����Ļ�
							p1 = list.get(i - 5);          
						p1.setStrokeWidth(strokeWidth);
						
						float dw = (strokeWidth - 2) / stopPointsNum;
						for (int j = i - 4; j <= i; ++j) {// ���ݹ�������5��
							p1 = list.get(j - 1);
							p2 = list.get(j);                       //�������ֳ��������ˣ���
							float width = p1.getStrokeWidth();
							p2.setStrokeWidth((short)(width - dw));
						}
						if (i < (size - 1))
							i--;
						break;
					} 
				}
				}
			}
		}
		}
	}
	
	
	//�����ʱ�������īˮ
	public static void modifyStrokePoints(BasePoint p1, BasePoint p2,float strokeWidth) {
		float x1 = p1.getX();
		float y1 = p1.getY();

		float x2 = p2.getX();
		float y2 = p2.getY();

		double t = ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		
		if(24000 / (t + 0.1)<1)
			p1.setStrokeWidth(frontlowerboundWidth);
		else
			p1.setStrokeWidth((float) (Math.abs(Math.pow(Math.log(24000 / (t + 0.1)) / 0.31, 0.33)* strokeWidth) / 2));		
		//Log.i("debug","p1 width"+p1.getStrokeWidth());
		}
	
	
	
	//charunit B����ֵ
	 public static ArrayList<BasePoint>  insertPoints(List<BasePoint> array,boolean isImageTmp)
	 {
	 	
	 	ArrayList<Short> arrayx=new ArrayList<Short>();
	 	ArrayList<Short> arrayy=new ArrayList<Short>();
	 	ArrayList<BasePoint> listinkpen=new ArrayList<BasePoint>();
	 	int numTmp =0;
	 	int numPointsInsert= 2;
	 	int k=0;
	 	if(isImageTmp ==true){//ͼ��ÿ����֮���3�㣬����ÿ������֮���һ����
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
	 	
	 		//�����Ƕ�һ�ʽ��в���ֵ
	 
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
	 		int pointsNum = numTmp+2;				//pointsNum ��־һ������ĵ���
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
	 	
	 		BasePoint inkPenPoint=null;
	 		inkPenPoint=new BasePoint(xx,yy);
	 		inkPenPoint.setEnd(false);
	 		listinkpen.add(inkPenPoint);
	 	
	 		for(int i=0;i<(pointsNum -2);i++) //��*��x+pointsNum-1����ʹ��
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
