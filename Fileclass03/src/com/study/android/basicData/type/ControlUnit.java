package com.study.android.basicData.type;

import java.util.List;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.data.ListTable;
import com.study.android.model.ParagraphFormat;

/**
 * ���Ƶ�Ԫ���������������У��ո�ȵ�
 * 
 * @author: tanqiang
 * @create-time: 2008-7-16
 */
public class ControlUnit extends DataUnit {

	private static final long serialVersionUID = 1L;
	
	private DataType ctrlType;
	
	/** ��ʽ����ȣ���Ҫ�ǶԿո�Ԫ����*/
	private short formatWidth;
	
	/** �����ʽ����Ҫ�ǶԻس���Ԫ����*/
	private ParagraphFormat paraFormat;

	/**
	 * ������Ƶ�Ԫ����
	 * 
	 * @param type�����Ƶ�Ԫ�����ͣ����绻�С��ո�������
	 * type����Ϊ���س����Ʒ�<code>DataType.TYPE_CTRL_ENTER, </code>
	 * �ո���Ʒ�<code>TYPE_CTRL_SPACE, </code>
	 * �������Ʒ�<code>TYPE_CTRL_INDENT, </code>
	 * ��ҳ��<code>TYPE_NEWPAGE_ID, </code>
	 * ���������<code>TYPE_VIRTUEL_ENDUNIT_ID </code>
	 * @see DataType
	 */
//	public ControlUnit(short width, short height, DataType type) {
//		super(width, height, DataType.TYPE_CONTROL);
//		this.ctrlType = type;
//	}
	
	/**
	 * TODO ���ˣ����Ƶ�Ԫֻ�пո�Ԫ�ͻس���Ԫ�ˣ�������Ԫ�ɶ���ո�Ԫ���
	 * @param charFormat 
	 * @param type ����Ŀ��Ƶ�Ԫ����
	 */
	public ControlUnit(DataType type, CharFormat charFormat) {
		super(DataType.TYPE_CONTROL, charFormat);
		this.ctrlType = type;
		//this.paraFormat = ParagraphFormat.getDefaultParaFormat();			//�������������
		this.formatWidth =ListTable.SpaceUnitWidth;					
		//this.formatWidth = (short) (ListTable.charactersize/3);					//���ÿո�Ŀ��ֵ=��߶�ֵ/3
		if(type == DataType.TYPE_CTRL_ENTER)
		{
			setWidth((short)0);		//Ϊ�˿ո�Ԫ���Ĺ�
			setHeight((short)0);
		}
		else if(type == DataType.TYPE_CTRL_SPACE)
		{
			setWidth(this.formatWidth);
			setHeight((short) ListTable.charactersize);
		}
		//this.formatWidth =0;
		//setWidth((short)0);		//Ϊ�˿ո�Ԫ���Ĺ�
		//setHeight((short)0);
		
	}
	
	/**
	 * TODO ǳ����Ӧ�ò���������İɣ��������ٸİɣ�
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
	 * ��ȡ���Ƶ�Ԫ�����ͣ����绻�С��ո�������
	 * 
	 * @return: CtrlType ���Ƶ�Ԫ������
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
