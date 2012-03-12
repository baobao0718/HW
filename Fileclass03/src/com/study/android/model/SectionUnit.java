
package com.study.android.model;

import com.study.android.basicData.DataUnit;

//import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * �ڵĶ��壬�����������ԣ��ڵĸ�ʽ��������Ķ�������
 *@author ���˸�
 *@version �������� 2008-8-5 ����04:32:51
 */
public class SectionUnit {
	/** �ڵ�Ĭ�ϸ�ʽ */
	private static SectionUnit defaultSection = null;
	
	/** �ýڵĵ�һ��ҳ�� */
	private int pageIndex;
	
	private SectionFormat secFormat;
	
	/**
	 * ��Ĭ�ϵĽڸ�ʽ����һ���ڣ��ýڵĵ�һ��ҳ����0
	 */
	public SectionUnit() {
		secFormat = SectionFormat.getDefaultSectionformat();
		pageIndex = 0;
	}
	
	/**
	 * TODO ��ҳ����pageIndex �� �ڸ�ʽsectionFormat ����һ���ڵ�Ԫ
	 * @param pageIndex
	 * @param sectionFormat
	 */
	public SectionUnit(int pageIndex, SectionFormat sectionFormat) {
		this.pageIndex = pageIndex;
		this.secFormat = sectionFormat;
	}
	
	/** ���Ĭ�ϵĽ� */
	public static SectionUnit getDefaultSection() {
		if(defaultSection == null) {
			defaultSection = new SectionUnit();
		}
		return defaultSection;
	}

	/** ���ýڵĸ�ʽ */
	public void setSectionFormat(SectionFormat sectionFormat) {
		this.secFormat = sectionFormat;
	}
	
	/**
	 *TODO ��ýڵĸ�ʽ
	 *
	 *@return secFormat
	 */
	public SectionFormat getSectionFormat() {
		return this.secFormat; 
	}

	/**
	 *TODO ���ظýڵĵ�һҳ��ҳ��
	 *
	 *@return pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 *TODO ���øýڵĵ�һҳ��ҳ��
	 *
	 *@param pageIndex
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
}
