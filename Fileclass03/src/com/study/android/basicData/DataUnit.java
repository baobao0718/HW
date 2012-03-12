package com.study.android.basicData;

import com.study.android.basicData.type.CharFormat;
import com.study.android.data.ListTable;

import java.io.Serializable;
import java.util.List;

/**
 * 数据单元
 * 
 * @author: tanqiang
 * @create-time: 2008-7-15
 */
public abstract class DataUnit implements Serializable{
	protected DataType dataType;
	
	/**
	 * 格式化信息
	 */
	protected CharFormat format;

	private short width , height;
	
	public DataUnit(short width, short height, DataType dataType) {
		this.width = width;
		this.height = height;
		this.dataType = dataType;
	//	this.format = CharFormat.getDefaultCharFormat();
	}
	
	/**
	 * TODO 次构造方法主要是用来构造控制单元的
	 * @param dataType
	 * @param f
	 */
	public DataUnit(DataType dataType, CharFormat f) {
		this.dataType = dataType;
		this.format = (f != null ? f : CharFormat.getDefaultCharFormat());
	}

	public DataType getDataType() {
		// TODO Auto-generated method stub
		return this.dataType;
	}

	public short getWidth() {
		return width;
	}

	public void setWidth(short width) {
		this.width = width;
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = height;
	}
	
	public short getFormatHeight() {
		return format.getHeight();
	}
	
	public void setCharFormat(CharFormat charFormat) {
		format = charFormat;
	}
	
	public CharFormat getCharFormat() {
		return format;
	}

	protected void setDataType(DataType dataType) {
		// TODO Auto-generated method stub
		this.dataType = dataType;
	}
	public  float getImageCompactRate(int paperwidth,int paperhigh){
		float rate = 1;
		float ratetemp1 = 1, ratetemp2 = 0;
		
		ratetemp1 = (float)(paperwidth) / (float)this.getWidth();//xuzh 2011.04.23
//		System.out.println(" ratetemp1 "+this.getWidth());
		ratetemp2 = (float)(paperhigh -2 * (ListTable.charactersize+16) ) / (float)this.getHeight();
//		System.out.println(" ratetemp2 "+this.getHeight());
		if(ratetemp1 <= ratetemp2 && ratetemp1 < 1)
		{
			rate = ratetemp1;
		}
		else if(ratetemp2 < 1) 
		{
			rate = ratetemp2;
		}
		return rate;
	}
	
	/**
	 * 获取控制符类型，数据单元不是控制符，则返回null
	 * 
	 * @return：type类型为：</br>回车控制符<code>DataType.TYPE_CTRL_ENTER, </code></br>
	 * 空格控制符<code>TYPE_CTRL_SPACE, </code></br>
	 * 缩进控制符<code>TYPE_CTRL_INDENT, </code></br>
	 * 新页符<code>TYPE_NEWPAGE_ID, </code></br>
	 * 虚拟结束符<code>TYPE_VIRTUEL_ENDUNIT_ID </code></br>
	 * @see DataType
	 */
	public DataType getCtrlType() {
		return null;
	}
	
	/** 实现当前对象的复制功能的抽象方法，由子类实现*/
	public abstract DataUnit clone();

	public abstract List<BasePoint> getPoints();
	
	/**
	 * 向数据单元中添加点的信息
	 * 
	 * @param p：需要添加的点
	 * @param colorIndex：可选参数：<b>byte</b>类型的颜色索引
	 */
	public abstract void addPoint(BasePoint p, int... color);
}

