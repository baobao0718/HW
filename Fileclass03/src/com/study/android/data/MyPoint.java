package com.study.android.data;
//package hitsz.handwriting.data;
//
//import java.io.Serializable;
//
///**
// * ������Ϣ�ĵ�
// * 
// * @author: tanqiang
// * @create-time: 2007-12-23
// */
//public class MyPoint implements Serializable {// ��װ�����Ϣ�������������ꡢ�ʻ���ɫ��ȡ��Ƿ��ϵ��
//
//	private int x, y;// x��y����
//
//	private boolean cutFlag = false;// һ�ʵĽ�����־���༴����̧��released
//
//	private float strokeWidth = 2;// �ʻ����
//	
//	private short interval = 16;// �õ���ϴ�����֮���ʱ����
//
//	public MyPoint(int x, int y, boolean cutFlag) {
//		this.x = x;
//		this.y = y;
//		this.cutFlag = cutFlag;
//		this.strokeWidth = 2;
//	}
//
//	public MyPoint(int x, int y, float strokeWidth, boolean cutFlag) {
//		this.x = x;
//		this.y = y;
//		this.cutFlag = cutFlag;
//		this.strokeWidth = strokeWidth;
//	}
//    
//	public MyPoint(MyPoint a){
//		this.x=a.x;
//		this.y=a.y;
//		this.cutFlag=a.cutFlag;
//		this.strokeWidth=a.strokeWidth;
//		this.interval=a.interval;
//	}
//	public int getX() {
//		return x;
//	}
//
//	public int getY() {
//		return y;
//	}
//	
//	public boolean getFlag() {
//		return this.cutFlag;
//	}
//
//	public void setFlag(boolean flag) {
//		this.cutFlag = flag;
//	}
//
//	public void setStrokeWidth(float w) {
//		this.strokeWidth = w;
//	}
//
//	public float getStrokeWidth() {
//		return strokeWidth;
//	}
//
//	public String toString() {
//		StringBuffer sb = new StringBuffer("points: ");
//		sb.append(" x==" + this.x);
//		sb.append(", y==" + this.y);
//		sb.append(", strokeWidth==" + strokeWidth);
//		return sb.toString();
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//	
//	public void setInterval(short t) {
//		this.interval = t;
//	}
//	
//	public short getInterval() {
//		return this.interval;
//	}
//}