
package com.study.android.model;

import com.study.android.basicData.DataUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * ����νṹ�������������ԣ��εĸ�ʽ�Ͷ��������dataUnit����
 *@author ���˸�
 *@version �������� 2008-8-5 ����04:33:52
 */
public class ParagraphUnit {

	/** �ε�Ĭ�ϸ�ʽ */
	private static ParagraphUnit defaultParagraph = null;
	
	/** �öεĵ�һ����Ԫ��ȫ������ */
	private int globalIndex;
	
	/** �εĸ�ʽ */
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

	/** ��öεĸ�ʽ */
	public void setParagraphFormat(ParagraphFormat paragraphFormat) {
		this.paragraphFormat = paragraphFormat;
	}
	
	/** ���öεĸ�ʽ */
	public ParagraphFormat getParaFormat() {
		return this.paragraphFormat;
	}

	/** ��øöε�һ����Ԫ������ */
	public int getGlobalIndex() {
		return globalIndex;
	}

	/** ���øöε�һ����Ԫ������ */
	public void setGlobalIndex(int globalIndex) {
		this.globalIndex = globalIndex;
	}
	
	
}
