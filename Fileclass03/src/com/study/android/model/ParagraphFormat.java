
package com.study.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.study.android.basicData.DataUnit;
import com.study.android.basicData.type.CharFormat;
import com.study.android.tool.ArraySet;
import com.study.android.tool.Comparator;

/**
 *@author ���˸�
 *@version �������� 2008-9-2 ����10:41:09
 */
public class ParagraphFormat implements Serializable{

	/** Ĭ�������� */
	public static int DefaultLeftIndent = 0;//��λ��mm  1Ӣ�� = 96���� = 25.4mm  1mm == 3.78����
	
	/** Ĭ�������� */
	public static int DefaultRightIndent = 0;
	
	/** Ĭ���������� */
	public static int DefaultRowIndent = 0;//Ĭ��12����
	
	/** Ĭ�϶�ǰ��� */
	public static int DefaultIntervalBeforePara = 0;
	
	/** Ĭ�϶κ��� */
	public static int DefaultIntervalAfterPara = 0;
	
	/** Ĭ�϶��뷽ʽ */
	public static AlignMode DefaultAlignMode = AlignMode.BothSideAlign;
	
	/** Ĭ���о൥λ */
	public static RowSpaceUnit defaultRowSpaceUnit = RowSpaceUnit.BaseSpce;//Ĭ���о൥λ��0.5���ĵ����о�
	
	/** Ĭ���о�ֵΪ 2 �� Ҳ���ǵ����о� */
	public static int DefatultRowSpaceValue = 2;
	
	/** Ĭ�ϵ����о�<b>   10   </b> �ڴ˴������ʱ��Ӧ����һ����ʼֵ ������Ϊ��λ��
	 * <br> �ڼ����о�ʱ�������������ĸ߶ȼ���DefaultSingleSpace
	 * */
	public static final int DefaultSingleSpace = 8;
	
	/** Ĭ�ϵ����о�<b>   DefaultSingleSpace + CharFormat.defaultHeight   </b> 
	 * <br> �ڼ����ǰ�Լ��κ����ʱ��ʹ��
	 * */
	public static final int DefaultRowSpace = DefaultSingleSpace + CharFormat.defaultHeight;
	
	/** ������ */
	private int leftIndent;
	
	/** ������ */
	private int rightIndent;
	
	/** �������� */
	private int rowIndent;
	
	/** �κ��� */
	private int intervalAfterPara;
	
	/** ��ǰ��� */
	private int intervalBeforePara;
	
	/** ���뷽ʽ */
	private AlignMode alignMode;
	
	/** �о൥λ */
	private RowSpaceUnit rowSpaceUnit;
	
	/** �о�ֵ */
	private int rowSpaceValue;
	
	private static ParagraphFormat defaultParaFormat = null;
	
	/**
	 * �Ƚ���
	 */
	private static Comparator comp = null;
	
	/**
	 * �θ�ʽ����
	 */
	private static final ArraySet paraFormatSet = new ArraySet(getComparator());;
	
	private ParagraphFormat(int leftIndent, int rightIndent, int rowIndent, int intervalAfterPara,
			int intervalBeforePara, AlignMode alignMode, RowSpaceUnit rowSpaceUnit, int rowSpaceValue) {
		this.leftIndent = leftIndent;
		this.rightIndent = rightIndent;
		this.rowIndent = rowIndent;
		this.intervalAfterPara = intervalAfterPara;
		this.intervalBeforePara = intervalBeforePara;
		this.alignMode = alignMode;
		this.rowSpaceUnit = rowSpaceUnit;
		this.rowSpaceValue = rowSpaceValue;
	}
	
	/**
	 *TODO ���ݸ����Ĳ�������һ���ε�Ԫ
	 *
	 *@param leftIndent
	 *@param rightIndent
	 *@param rowIndent
	 *@param intervalAfterPara
	 *@param intervalBeforePara
	 *@param alignMode
	 *@param rowSpaceUnit
	 *@param rowSpaceValue
	 *@return
	 */
	public static ParagraphFormat getParagraphFormat(int leftIndent, int rightIndent, int rowIndent,
			int intervalAfterPara, int intervalBeforePara, AlignMode alignMode, 
			RowSpaceUnit rowSpaceUnit, int rowSpaceValue) {
		ParagraphFormat paraForm = new ParagraphFormat(leftIndent, rightIndent, rowIndent,
				intervalAfterPara, intervalBeforePara, alignMode, rowSpaceUnit, rowSpaceValue);
		
		return (ParagraphFormat) paraFormatSet.add(paraForm);
	}
	
	public static ParagraphFormat getDefaultParaFormat() {
		if(defaultParaFormat == null) {
			defaultParaFormat = new ParagraphFormat(ParagraphFormat.DefaultLeftIndent, ParagraphFormat.DefaultRightIndent,
					ParagraphFormat.DefaultRowIndent, ParagraphFormat.DefaultIntervalAfterPara,
					ParagraphFormat.DefaultIntervalBeforePara, ParagraphFormat.DefaultAlignMode,
					ParagraphFormat.defaultRowSpaceUnit, ParagraphFormat.DefatultRowSpaceValue);
		}
		return defaultParaFormat;
	}
	
	public ParagraphFormat clone(){
		return new ParagraphFormat(this.leftIndent, this.rightIndent, this.rowIndent, this.intervalAfterPara,
				this.intervalBeforePara, this.alignMode, this.rowSpaceUnit, this.rowSpaceValue);
	}
	
	/** ȡ��������ֵ */
	public int getLeftIndentValue() {
		return (int) (leftIndent  * SectionFormat.UnitOfInterval.Millimetre.getUnit());
	}

	/** ����������ֵ */
	public void setLeftIndent(int leftIndent) {
		this.leftIndent = leftIndent;
	}

	/** ȡ��������ֵ */
	public int getRightIndentValue() {
		return (int) (rightIndent * SectionFormat.UnitOfInterval.Millimetre.getUnit());
	}

	/** ����������ֵ */
	public void setRightIndent(int rightIndent) {
		this.rightIndent = rightIndent;
	}
	
	public int getRightIndent() {
		return rightIndent;
	}

	/** ȡ����������ֵ */
	public int getRowIndentValue() {
		return (int) (rowIndent * SectionFormat.UnitOfInterval.Millimetre.getUnit());
	}
	
	public int getLeftIndent() {
		return leftIndent;
	}

	/** ������������ֵ */
	public void setRowIndent(int rowIndent) {
		this.rowIndent = rowIndent;
	}

	public int getRowIndent() {
		return rowIndent;
	}

	/** ȡ�ö�ǰ���ֵ */
	public int getIntervalBeforeParaValue() {
		int intervalBeforeParaValue = (int) (intervalBeforePara * DefaultRowSpace /(float)2);
		return intervalBeforeParaValue;
	}
	
	public int getIntervalBeforePara() {
		return intervalBeforePara;
	}

	/** ���ö�ǰ���ֵ */
	public void setIntervalBeforePara(int intervalBeforePara) {
		this.intervalBeforePara = intervalBeforePara;
	}

	/** ȡ�öκ���ֵ */
	public int getIntervalAfterParaValue() {
		int intervalAfterParaValue = (int) (intervalAfterPara * DefaultRowSpace /(float)2);
		return intervalAfterParaValue;
	}

	public int getIntervalAfterPara() {
		return intervalAfterPara;
	}
	
	/** ���öκ���ֵ */
	public void setIntervalAfterPara(int intervalAfterPara) {
		this.intervalAfterPara = intervalAfterPara;
	}

	/** ȡ�ö��뷽ʽ�������ҡ����С����� */
	public AlignMode getAlignMode() {
		return alignMode;
	}

	/** ���ö��뷽ʽ�������ҡ����С����� */
	public void setAlignMode(AlignMode alignMode) {
		this.alignMode = alignMode;
	}


	/** ����о൥λ */
	public RowSpaceUnit getRowSpaceUnit() {
		return rowSpaceUnit;
	}

	/** �����о൥λ */
	public void setRowSpaceUnit(RowSpaceUnit rowSpaceUnit) {
		this.rowSpaceUnit = rowSpaceUnit;
	}

	/** ����о�ֵ */
	public float getRowSpaceValue() {
		float value = rowSpaceValue /(float) 2;
		if(this.rowSpaceUnit == RowSpaceUnit.Pound) {
			value = -(float)(value * RowSpaceUnit.Pound.getUnit());
		}
		return value;
	}
	
	public int getRowSpace() {
		return rowSpaceValue;
	}

	/** �����о�ֵ */
	public void setRowSpaceValue(int rowSpaceValue) {
		this.rowSpaceValue = rowSpaceValue;
	}
	
	/** �εĶ��뷽ʽ<br><b>������룬�Ҷ��룬���ж��룬���˶���</b> */
	public enum AlignMode {
		/** ����� */
		LeftAlign(1),
		/** �Ҷ��� */
		RightAlign(2),
		/** ���ж��� */
		MinddleAlign(3),
		/** ���˶��� */
		BothSideAlign(4);
		
		AlignMode(int i) {
			this.value = i;
		}
		
		public int getValue() {
			return this.value;
		}
		private int value;
	}
	
	/**
	 * �о൥λ <b>0.5���о� �� �� ���� </b><br>
	 * 1�� = 1/72 Ӣ�磬 1Ӣ�� = 96���أ�С����ʱ���� 1Ӣ�� = 120���أ�������ʱ��
	 * �˴���Ϊ 1Ӣ�� = 96����
	 * �����о൥λ���ת��Ϊ������
	 */
	public enum RowSpaceUnit {
		/** 0.5����λ�о� */
		BaseSpce(0.5*DefaultSingleSpace),
		/**<b> ���� ת���������� 1�� = 1.3333����</b>*/
		Pound(1.3333);
		
		RowSpaceUnit(double unit) {
			this.unit = unit;
		}
		
		public double getUnit() {
			return this.unit;
		}
		private double unit;
	}
	public static Comparator getComparator(){
		if (comp == null)
			comp = new Comparator() {
				public int compare(Object o1, Object o2) {
					ParagraphFormat paraFormat1 = (ParagraphFormat) o1;
					ParagraphFormat paraFormat2 = (ParagraphFormat) o2;
					if (paraFormat1.leftIndent < paraFormat2.leftIndent) return -1;
					if (paraFormat1.leftIndent > paraFormat2.leftIndent) return 1;

					if (paraFormat1.rightIndent < paraFormat2.rightIndent) return -1;
					if (paraFormat1.rightIndent > paraFormat2.rightIndent) return 1;

					if (paraFormat1.rowIndent < paraFormat2.rowIndent) return -1;
					if (paraFormat1.rowIndent > paraFormat2.rowIndent) return 1;

					if (paraFormat1.intervalAfterPara < paraFormat2.intervalAfterPara) return -1;
					if (paraFormat1.intervalAfterPara < paraFormat2.intervalAfterPara) return 1;

					if (paraFormat1.intervalBeforePara < paraFormat2.intervalBeforePara) return -1;
					if (paraFormat1.intervalBeforePara > paraFormat2.intervalBeforePara) return 1;

					if (paraFormat1.alignMode.getValue() < paraFormat2.alignMode.getValue()) return -1;
					if (paraFormat1.alignMode.getValue() > paraFormat2.alignMode.getValue()) return 1;

					if (paraFormat1.rowSpaceValue < paraFormat2.rowSpaceValue) return -1;
					if (paraFormat1.rowSpaceValue > paraFormat2.rowSpaceValue) return 1;

					if (paraFormat1.rowSpaceUnit.getUnit() < paraFormat2.rowSpaceUnit.getUnit()) return -1;
					if (paraFormat1.rowSpaceUnit.getUnit() > paraFormat2.rowSpaceUnit.getUnit()) return 1;

					return 0;
				}
			};
	    return comp;
	}
	
	public boolean equals(ParagraphFormat paraFormat) {
		if(paraFormat == null) {
			return false;
		} else {
			return paraFormat.leftIndent == leftIndent &&
			       paraFormat.rightIndent == rightIndent &&
			       paraFormat.rowIndent == rowIndent &&
			       paraFormat.intervalAfterPara == intervalAfterPara &&
			       paraFormat.intervalBeforePara == intervalBeforePara &&
			       paraFormat.alignMode == alignMode && 
			       paraFormat.rowSpaceValue == rowSpaceValue && 
			       paraFormat.rowSpaceUnit == rowSpaceUnit;
		}
	}
}
