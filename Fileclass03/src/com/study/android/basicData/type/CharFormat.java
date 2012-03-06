
package com.study.android.basicData.type;

import com.study.android.tool.ArraySet;
import com.study.android.tool.Comparator;
//import com.study.android.ui.HwFunctionWindow;
import android.graphics.Color;
//import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *@author: fuxinggang
 *@create-time: 2009-1-14 ����03:35:27
 */
public class CharFormat implements Serializable{

	/**
	 * ������ʽö����
	 *
	 */
	public interface Style{
		public static final byte NONE = 0;
		public static final byte UNDERLINE = 1;
	}
	
	/**
	 * �Ƚ���
	 */
//	private static Comparator comp = null;
	
	/**
	 * �����ʽ����
	 */
	private static final ArraySet charFormatSet = new ArraySet(getComparator());
	
	/**�ַ���Ԫ�ļ�� 8 ������*/
	public static final byte unitInterval = 8;
	
	/**
	 * Ĭ������߶� 32
	 */
	public static final byte defaultHeight = 32;
	
	/**
	 * Ĭ������ʻ���׼��� 2.0
	 */
	public static final byte defaultStrokeWidth = 2;
	
	/**
	 * Ĭ�ϸ�ʽ
	 */
	private static CharFormat defaultFormat = null;
	
	/**
	 * ������ɫ��ʹ��RGBֵ������������
	 */
	private int color;
	
	/**
	 * ����߶�
	 */
	private byte height;
	
	/**
	 * ����ʻ���׼���
	 */
	private byte strokeWidth;
	
	/**
	 * ������ʽ<br>
	 * 000 = 0 ����ʽ<br>
	 * 001 = 1 �»���<br>
	 */
	private byte style;
	
	/**
	 * �Ƚ���
	 */
	private static Comparator comp = null;
	
	public static CharFormat getDefaultCharFormat(){
		if(defaultFormat == null)
			defaultFormat = new CharFormat( Color.rgb(0,0,255), defaultHeight, defaultStrokeWidth, Style.NONE);
		return defaultFormat;
	}
	
	public static CharFormat getCharFormat(int color, byte height, byte strokeWidth, byte style){
		CharFormat cf = new CharFormat(color, height, strokeWidth, style);
		return (CharFormat)charFormatSet.add(cf);
	}
	
	private CharFormat(int color, byte height, byte strokeWidth, byte style) {
		this.color = color;
		this.height = height;
		this.strokeWidth = strokeWidth;
		this.style = style;
	}

	public int getColor() {
		return color;
	}

	public byte getHeight() {
		return height;
	}

	public byte getStyle() {
		return style;
	}

	public byte getStrokeWidth() {
		return strokeWidth;
	}

	public boolean equals(CharFormat cf) {
		if(cf == null)return false;
		return cf.color  == color  &&
				cf.height == height &&
				cf.style  == style  &&
				cf.strokeWidth == strokeWidth;
	}
	
	/**
	 * ���棺���Ƶĸ�ʽҪ����getCharFormat�ŵ�������
	 * @return
	 */
	public CharFormat clone(){          //clone ����¡����˼
		return new CharFormat(color, height, strokeWidth, style);
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setHeight(int height) {
		this.height = (byte)height;
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = (byte)strokeWidth;
	}

	public void setStyle(byte style) {
		this.style = style;
	}
	
	public static Comparator getComparator() {
		if (comp == null)
			comp = new Comparator() {               //ʲô�÷����������
				public int compare(Object o1, Object o2) {
					CharFormat cf1 = (CharFormat) o1;
					CharFormat cf2 = (CharFormat) o2;
					if (cf1.color < cf2.color) return -1;
					if (cf1.color > cf2.color) return 1;

					if (cf1.height < cf2.height) return -1;
					if (cf1.height > cf2.height) return 1;

					if (cf1.strokeWidth < cf2.strokeWidth) return -1;
					if (cf1.strokeWidth > cf2.strokeWidth) return 1;

					if (cf1.style < cf2.style) return -1;
					if (cf1.style > cf2.style) return 1;

					return 0;
				}
			};
		return comp;
	}
}
