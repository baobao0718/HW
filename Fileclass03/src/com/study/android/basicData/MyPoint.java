package com.study.android.basicData;

import java.io.Serializable;

public class MyPoint extends BasePoint implements Serializable{

	private static final long serialVersionUID = 1L;

	private float strokeWidth = 2;// 笔划宽度
	
	private short interval = 16;// 该点和上次样点之间的时间间隔

	private int color = 0;//使用rgb值， 默认是黑色
	
	public MyPoint(short x, short y, boolean cutFlag) {
		super(x, y);
		this.setEnd(cutFlag);
		// 默认值
		this.strokeWidth = 2;
	}

	public MyPoint(short x, short y, float strokeWidth, boolean cutFlag) {
		super(x, y);
		this.strokeWidth = strokeWidth;
		this.setEnd(cutFlag);
	}
	
	public MyPoint (MyPoint point) {
		super (point);
		this.interval = point.interval;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("point: ");
		sb.append(" x==" + getX());
		sb.append(", y==" + getY());
		sb.append(", strokeWidth==" + strokeWidth);
		return sb.toString();
	}

	public void setInterval(short t) {
		this.interval = t;
	}
	
	public short getInterval() {
		return this.interval;
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
