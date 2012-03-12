
package com.study.android.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.study.android.basicData.DataUnit;
import com.study.android.basicData.type.CharFormat;
import com.study.android.tool.ArraySet;
import com.study.android.tool.Comparator;

/**
 *@author 付兴刚
 *@version 创建日期 2008-9-2 下午10:41:09
 */
public class ParagraphFormat implements Serializable{

	/** 默认左缩进 */
	public static int DefaultLeftIndent = 0;//单位是mm  1英寸 = 96像素 = 25.4mm  1mm == 3.78像素
	
	/** 默认右缩进 */
	public static int DefaultRightIndent = 0;
	
	/** 默认首行缩进 */
	public static int DefaultRowIndent = 0;//默认12毫米
	
	/** 默认段前间距 */
	public static int DefaultIntervalBeforePara = 0;
	
	/** 默认段后间距 */
	public static int DefaultIntervalAfterPara = 0;
	
	/** 默认对齐方式 */
	public static AlignMode DefaultAlignMode = AlignMode.BothSideAlign;
	
	/** 默认行距单位 */
	public static RowSpaceUnit defaultRowSpaceUnit = RowSpaceUnit.BaseSpce;//默认行距单位是0.5倍的单倍行距
	
	/** 默认行距值为 2 ， 也就是单倍行距 */
	public static int DefatultRowSpaceValue = 2;
	
	/** 默认单倍行距<b>   10   </b> 在此处定义的时候应该设一个初始值 以像素为单位的
	 * <br> 在计算行距时，该行最大字体的高度加上DefaultSingleSpace
	 * */
	public static final int DefaultSingleSpace = 8;
	
	/** 默认单倍行距<b>   DefaultSingleSpace + CharFormat.defaultHeight   </b> 
	 * <br> 在计算段前以及段后间距的时候使用
	 * */
	public static final int DefaultRowSpace = DefaultSingleSpace + CharFormat.defaultHeight;
	
	/** 左缩进 */
	private int leftIndent;
	
	/** 右缩进 */
	private int rightIndent;
	
	/** 首行缩进 */
	private int rowIndent;
	
	/** 段后间距 */
	private int intervalAfterPara;
	
	/** 段前间距 */
	private int intervalBeforePara;
	
	/** 对齐方式 */
	private AlignMode alignMode;
	
	/** 行距单位 */
	private RowSpaceUnit rowSpaceUnit;
	
	/** 行距值 */
	private int rowSpaceValue;
	
	private static ParagraphFormat defaultParaFormat = null;
	
	/**
	 * 比较器
	 */
	private static Comparator comp = null;
	
	/**
	 * 段格式集合
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
	 *TODO 根据给定的参数构造一个段单元
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
	
	/** 取得左缩进值 */
	public int getLeftIndentValue() {
		return (int) (leftIndent  * SectionFormat.UnitOfInterval.Millimetre.getUnit());
	}

	/** 设置左缩进值 */
	public void setLeftIndent(int leftIndent) {
		this.leftIndent = leftIndent;
	}

	/** 取得右缩进值 */
	public int getRightIndentValue() {
		return (int) (rightIndent * SectionFormat.UnitOfInterval.Millimetre.getUnit());
	}

	/** 设置右缩进值 */
	public void setRightIndent(int rightIndent) {
		this.rightIndent = rightIndent;
	}
	
	public int getRightIndent() {
		return rightIndent;
	}

	/** 取得首行缩进值 */
	public int getRowIndentValue() {
		return (int) (rowIndent * SectionFormat.UnitOfInterval.Millimetre.getUnit());
	}
	
	public int getLeftIndent() {
		return leftIndent;
	}

	/** 设置首行缩进值 */
	public void setRowIndent(int rowIndent) {
		this.rowIndent = rowIndent;
	}

	public int getRowIndent() {
		return rowIndent;
	}

	/** 取得段前间距值 */
	public int getIntervalBeforeParaValue() {
		int intervalBeforeParaValue = (int) (intervalBeforePara * DefaultRowSpace /(float)2);
		return intervalBeforeParaValue;
	}
	
	public int getIntervalBeforePara() {
		return intervalBeforePara;
	}

	/** 设置段前间距值 */
	public void setIntervalBeforePara(int intervalBeforePara) {
		this.intervalBeforePara = intervalBeforePara;
	}

	/** 取得段后间距值 */
	public int getIntervalAfterParaValue() {
		int intervalAfterParaValue = (int) (intervalAfterPara * DefaultRowSpace /(float)2);
		return intervalAfterParaValue;
	}

	public int getIntervalAfterPara() {
		return intervalAfterPara;
	}
	
	/** 设置段后间距值 */
	public void setIntervalAfterPara(int intervalAfterPara) {
		this.intervalAfterPara = intervalAfterPara;
	}

	/** 取得对齐方式，有左、右、居中、两端 */
	public AlignMode getAlignMode() {
		return alignMode;
	}

	/** 设置对齐方式，有左、右、居中、两端 */
	public void setAlignMode(AlignMode alignMode) {
		this.alignMode = alignMode;
	}


	/** 获得行距单位 */
	public RowSpaceUnit getRowSpaceUnit() {
		return rowSpaceUnit;
	}

	/** 设置行距单位 */
	public void setRowSpaceUnit(RowSpaceUnit rowSpaceUnit) {
		this.rowSpaceUnit = rowSpaceUnit;
	}

	/** 获得行距值 */
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

	/** 设置行距值 */
	public void setRowSpaceValue(int rowSpaceValue) {
		this.rowSpaceValue = rowSpaceValue;
	}
	
	/** 段的对齐方式<br><b>有左对齐，右对齐，居中对齐，两端对齐</b> */
	public enum AlignMode {
		/** 左对齐 */
		LeftAlign(1),
		/** 右对齐 */
		RightAlign(2),
		/** 居中对齐 */
		MinddleAlign(3),
		/** 两端对齐 */
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
	 * 行距单位 <b>0.5倍行距 和 磅 两种 </b><br>
	 * 1磅 = 1/72 英寸， 1英寸 = 96像素（小字体时）， 1英寸 = 120像素（大字体时）
	 * 此处认为 1英寸 = 96像素
	 * 这样行距单位最后都转化为像素了
	 */
	public enum RowSpaceUnit {
		/** 0.5倍单位行距 */
		BaseSpce(0.5*DefaultSingleSpace),
		/**<b> 磅数 转化成像素了 1磅 = 1.3333像素</b>*/
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
