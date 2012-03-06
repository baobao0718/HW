
package com.study.android.model;

import com.study.android.basicData.DataUnit;

//import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 节的定义，节有两个属性：节的格式、节里面的段落数据
 *@author 付兴刚
 *@version 创建日期 2008-8-5 下午04:32:51
 */
public class SectionUnit {
	/** 节的默认格式 */
	private static SectionUnit defaultSection = null;
	
	/** 该节的第一个页号 */
	private int pageIndex;
	
	private SectionFormat secFormat;
	
	/**
	 * 以默认的节格式构造一个节，该节的第一个页号是0
	 */
	public SectionUnit() {
		secFormat = SectionFormat.getDefaultSectionformat();
		pageIndex = 0;
	}
	
	/**
	 * TODO 以页索引pageIndex 和 节格式sectionFormat 构造一个节单元
	 * @param pageIndex
	 * @param sectionFormat
	 */
	public SectionUnit(int pageIndex, SectionFormat sectionFormat) {
		this.pageIndex = pageIndex;
		this.secFormat = sectionFormat;
	}
	
	/** 获得默认的节 */
	public static SectionUnit getDefaultSection() {
		if(defaultSection == null) {
			defaultSection = new SectionUnit();
		}
		return defaultSection;
	}

	/** 设置节的格式 */
	public void setSectionFormat(SectionFormat sectionFormat) {
		this.secFormat = sectionFormat;
	}
	
	/**
	 *TODO 获得节的格式
	 *
	 *@return secFormat
	 */
	public SectionFormat getSectionFormat() {
		return this.secFormat; 
	}

	/**
	 *TODO 返回该节的第一页的页号
	 *
	 *@return pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 *TODO 设置该节的第一页的页号
	 *
	 *@param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
}
