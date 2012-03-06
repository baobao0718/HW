package com.study.android.data;

/**
 * ����ҳ��������������
 * 
 * @author ���Ĳ� 
 * @date 2008/01/12
 */
public class PageIndex {
	
	/**��ҳ��Ӧ��һ�е��к�*/
	private int rowNo;
	
	/**��ҳ��ǰ��ҳ������(Ԥ��)*/
	private int sectionIndex;

	public PageIndex(int rowNum, int sectionIndex) {
		this.rowNo = rowNum;
		this.sectionIndex = sectionIndex;
	}

	/**
	 * ��ø�ҳ��Ӧ��һ�е��к�
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