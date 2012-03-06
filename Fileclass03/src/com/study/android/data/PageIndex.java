package com.study.android.data;

/**
 * 定义页索引表对象的类型
 * 
 * @author 武文博 
 * @date 2008/01/12
 */
public class PageIndex {
	
	/**该页对应第一行的行号*/
	private int rowNo;
	
	/**该页当前的页面设置(预留)*/
	private int sectionIndex;

	public PageIndex(int rowNum, int sectionIndex) {
		this.rowNo = rowNum;
		this.sectionIndex = sectionIndex;
	}

	/**
	 * 获得该页对应第一行的行号
	 */
	public int getRowNo() {
		return rowNo;
	}

	public int getSectionIndex() {
		return sectionIndex;
	}

	public void setRowNo(int rowNum) {
		this.rowNo = rowNum;
	}

	public void setSectionIndex(int sectionIndex) {
		this.sectionIndex = sectionIndex;
	}
}