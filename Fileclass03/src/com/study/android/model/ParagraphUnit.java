
package com.study.android.model;

import com.study.android.basicData.DataUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义段结构，段有两个属性：段的格式和段里包含的dataUnit链表
 *@author 付兴刚
 *@version 创建日期 2008-8-5 下午04:33:52
 */
public class ParagraphUnit {

	/** 段的默认格式 */
	private static ParagraphUnit defaultParagraph = null;
	
	/** 该段的第一个单元的全局索引 */
	private int globalIndex;
	
	/** 段的格式 */
	private ParagraphFormat paragraphFormat;
	
	
	public ParagraphUnit() {
		paragraphFormat = ParagraphFormat.getDefaultParaFormat();
		globalIndex = 0;
	}
	
	public ParagraphUnit(int globalIndex, ParagraphFormat paraFormat) {
		this.globalIndex = globalIndex;
		this.paragraphFormat = paraFormat;
	}
	
	public static ParagraphUnit getDefaultParagraphUnit() {
		if(defaultParagraph == null) {
			defaultParagraph =  new ParagraphUnit();
		}
		return defaultParagraph;
	}

	/** 获得段的格式 */
	public void setParagraphFormat(ParagraphFormat paragraphFormat) {
		this.paragraphFormat = paragraphFormat;
	}
	
	/** 设置段的格式 */
	public ParagraphFormat getParaFormat() {
		return this.paragraphFormat;
	}

	/** 获得该段第一个单元的索引 */
	public int getGlobalIndex() {
		return globalIndex;
	}

	/** 设置该段第一个单元的索引 */
	public void setGlobalIndex(int globalIndex) {
		this.globalIndex = globalIndex;
	}
	
	
}
