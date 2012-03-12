
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
 *@create-time: 2009-1-14 下午03:35:27
 */
public class CharFormat implements Serializable{

	/**
	 * 字体样式枚举类
	 *
	 */
	public interface Style{
		public static final byte NONE = 0;
		public static final byte UNDERLINE = 1;
	}
	
	/**
	 * 比较器
	 */
//	private static Comparator comp = null;
	
	/**
	 * 字体格式集合
	 */
	private static final ArraySet charFormatSet = new ArraySet(getComparator());
	
	/**字符单元的间距 8 个像素*/
	public static final byte unitInterval = 8;
	
	/**
	 * 默认字体高度 32
	 */
	public static final byte defaultHeight = 32;
	
	/**
	 * 默认字体笔画基准宽度 2.0
	 */
	public static final byte defaultStrokeWidth = 2;
	
	/**
	 * 默认格式
	 */
	private static CharFormat defaultFormat = null;
	
	/**
	 * 字体颜色，使用RGB值，而不是索引
	 */
	private int color;
	
	/**
	 * 字体高度
	 */
	private byte height;
	
	/**
	 * 字体笔画基准宽度
	 */
	private byte strokeWidth;
	
	/**
	 * 字体样式<br>
	 * 000 = 0 无样式<br>
	 * 001 = 1 下划线<br>
	 */
	private byte style;
	
	/**
	 * 比较器
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
	 * 警告：复制的格式要调用getCharFormat放到集合中
	 * @return
	 */
	public CharFormat clone(){          //clone 即克隆的意思
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
			comp = new Comparator() {               //什么用法？？不理解
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
