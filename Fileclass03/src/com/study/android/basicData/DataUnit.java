package com.study.android.basicData;

import com.study.android.basicData.type.CharFormat;
import com.study.android.data.ListTable;

import java.io.Serializable;
import java.util.List;

/**
 * ���ݵ�Ԫ
 * 
 * @author: tanqiang
 * @create-time: 2008-7-15
 */
public abstract class DataUnit implements Serializable{
	protected DataType dataType;
	
	/**
	 * ��ʽ����Ϣ
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
	 * TODO �ι��췽����Ҫ������������Ƶ�Ԫ��
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
	 * ��ȡ���Ʒ����ͣ����ݵ�Ԫ���ǿ��Ʒ����򷵻�null
	 * 
	 * @return��type����Ϊ��</br>�س����Ʒ�<code>DataType.TYPE_CTRL_ENTER, </code></br>
	 * �ո���Ʒ�<code>TYPE_CTRL_SPACE, </code></br>
	 * �������Ʒ�<code>TYPE_CTRL_INDENT, </code></br>
	 * ��ҳ��<code>TYPE_NEWPAGE_ID, </code></br>
	 * ���������<code>TYPE_VIRTUEL_ENDUNIT_ID </code></br>
	 * @see DataType
	 */
	public DataType getCtrlType() {
		return null;
	}
	
	/** ʵ�ֵ�ǰ����ĸ��ƹ��ܵĳ��󷽷���������ʵ��*/
	public abstract DataUnit clone();

	public abstract List<BasePoint> getPoints();
	
	/**
	 * �����ݵ�Ԫ����ӵ����Ϣ
	 * 
	 * @param p����Ҫ��ӵĵ�
	 * @param colorIndex����ѡ������<b>byte</b>���͵���ɫ����
	 */
	public abstract void addPoint(BasePoint p, int... color);
}

