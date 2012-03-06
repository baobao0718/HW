package com.study.android.basicData.type;

import java.util.List;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.data.ListTable;
import com.study.android.model.ParagraphFormat;

/**
 * 控制单元：包括缩进，换行，空格等等
 * 
 * @author: tanqiang
 * @create-time: 2008-7-16
 */
public class ControlUnit extends DataUnit {

	private static final long serialVersionUID = 1L;
	
	private DataType ctrlType;
	
	/** 格式化宽度，主要是对空格单元而言*/
	private short formatWidth;
	
	/** 段落格式，主要是对回车单元而言*/
	private ParagraphFormat paraFormat;

	/**
	 * 构造控制单元类型
	 * 
	 * @param type：控制单元的类型，例如换行、空格、缩进等
	 * type类型为：回车控制符<code>DataType.TYPE_CTRL_ENTER, </code>
	 * 空格控制符<code>TYPE_CTRL_SPACE, </code>
	 * 缩进控制符<code>TYPE_CTRL_INDENT, </code>
	 * 新页符<code>TYPE_NEWPAGE_ID, </code>
	 * 虚拟结束符<code>TYPE_VIRTUEL_ENDUNIT_ID </code>
	 * @see DataType
	 */
//	public ControlUnit(short width, short height, DataType type) {
//		super(width, height, DataType.TYPE_CONTROL);
//		this.ctrlType = type;
//	}
	
	/**
	 * TODO 至此，控制单元只有空格单元和回车单元了，缩进单元由多个空格单元替代
	 * @param charFormat 
	 * @param type 具体的控制单元类型
	 */
	public ControlUnit(DataType type, CharFormat charFormat) {
		super(DataType.TYPE_CONTROL, charFormat);
		this.ctrlType = type;
		//this.paraFormat = ParagraphFormat.getDefaultParaFormat();			//先屏蔽这条语句
		this.formatWidth =ListTable.SpaceUnitWidth;					
		//this.formatWidth = (short) (ListTable.charactersize/3);					//设置空格的宽度值=其高度值/3
		if(type == DataType.TYPE_CTRL_ENTER)
		{
			setWidth((short)0);		//为了空格单元而改过
			setHeight((short)0);
		}
		else if(type == DataType.TYPE_CTRL_SPACE)
		{
			setWidth(this.formatWidth);
			setHeight((short) ListTable.charactersize);
		}
		//this.formatWidth =0;
		//setWidth((short)0);		//为了空格单元而改过
		//setHeight((short)0);
		
	}
	
	/**
	 * TODO 浅复制应该不会有问题的吧，有问题再改吧！
	 * @param controlUnit
	 */
	public ControlUnit(ControlUnit controlUnit){
		super(controlUnit.getWidth(), controlUnit.getHeight(), DataType.TYPE_CONTROL);
		this.ctrlType = controlUnit.ctrlType;
		this.formatWidth = controlUnit.formatWidth;
		this.format = controlUnit.getCharFormat();
		this.paraFormat = controlUnit.getParaFormat();
	}

	/**
	 * 获取控制单元的类型，例如换行、空格、缩进等
	 * 
	 * @return: CtrlType 控制单元的类型
	 */
	public DataType getCtrlType() {
		return this.ctrlType;
	}
	
	public short getFormatWidth() {
		return this.formatWidth;
	}

	public ParagraphFormat getParaFormat() {
		return paraFormat;
	}
	
	public void setParaFormat(ParagraphFormat paraFormat){
		this.paraFormat = paraFormat;
	}
	public ControlUnit clone() {
		// TODO Auto-generated method stub
		return new ControlUnit(this);
	}

	public List<BasePoint> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPoint(BasePoint p, int... color) {
		// TODO Auto-generated method stub
		
	}

}
