package com.study.android.basicData;

import java.io.Serializable; 
//Serializable���ļ�Ϊ���л���ʵ��Serializable�������ͨ�����������ʵ�ֶ���Ĵ洢���ļ���
//�˴��ļ��ĸ������Ӳ���ϵ��ļ��������

/*
 *@author ���˸�
 *@version �������� 2008-7-30 ����02:03:18
 */
public class BasePoint implements Serializable { 
	
	
	private static final long serialVersionUID = 1L;

	private short x;// x��y����

	private short y;
	
	private float strokeWidth = 2;// �ʻ����,������Ҽӽ�����

	public BasePoint(short x, short y) {
		this.x = x;
		this.y = y;
//		this.strokeWidth =2;
	}
	public BasePoint(short x, short y,boolean flag,float strokewidth) {
		this.x = x;
		this.y = y;
		this.setEnd(flag);
		this.strokeWidth =strokewidth;
	}

	public BasePoint(BasePoint point) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public short getX() {
		short xx = this.x;
		if (xx < 0)
			xx = (short) Math.abs(this.x);
		return xx;
	}

	public void setX(short x) {
		this.x = x;
	}

	public short getY() {
		return this.y;
	}

	public void setY(short y) {
		this.y = y;
	}

	public float getStrokeWidth() {
		//return -1;															//???ʲô��˼
		return this.strokeWidth;
	}

	public void setStrokeWidth(float w) {
		this.strokeWidth =w;
	}

	public boolean isEnd() {
		if (this.x <0)
			return true;
		else
			return false;
	}

	public void setEnd(boolean isEnd) {
		if (true == isEnd)
		{
			if(x==0)
				this.x= (short)-1;                                                   //��ֹ����x��0 �����
			else
				this.x = (short) -Math.abs(x);	                                        //����ǽ�β��������ֵΪ��
		}
		else
			this.x = (short) Math.abs(x);							
	}
}
