package com.study.android.basicData;

import java.io.Serializable;

public class MyPoint extends BasePoint implements Serializable{

	private static final long serialVersionUID = 1L;

	private float strokeWidth = 2;// �ʻ����
	
	private short interval = 16;// �õ���ϴ�����֮���ʱ����

	private int color = 0;//ʹ��rgbֵ�� Ĭ���Ǻ�ɫ
	
	public MyPoint(short x, short y, boolean cutFlag) {
		super(x, y);
		this.setEnd(cutFlag);
		// Ĭ��ֵ
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
