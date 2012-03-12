package com.study.android.basicData;

import java.io.Serializable; 
//Serializable中文即为串行化，实现Serializable的类可以通过输入输出流实现对象的存储到文件，
//此处文件的概念包括硬盘上的文件、网络等

/*
 *@author 付兴刚
 *@version 创建日期 2008-7-30 下午02:03:18
 */
public class BasePoint implements Serializable { 
	
	
	private static final long serialVersionUID = 1L;

	private short x;// x，y坐标

	private short y;
	
	private float strokeWidth = 2;// 笔划宽度,这快是我加进来的

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
		//return -1;															//???什么意思
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
				this.x= (short)-1;                                                   //防止出现x＝0 的情况
			else
				this.x = (short) -Math.abs(x);	                                        //如果是结尾，则令其值为负
		}
		else
			this.x = (short) Math.abs(x);							
	}
}
