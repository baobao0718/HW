
package com.study.android.data;
/**
 *@author: fuxinggang
 *@create-time: 2009-3-3 ����04:34:39
 */
public class RowIndex {

	/**���е�һ����Ԫ��ȫ������*/
	private int globalIndex;
	
	/**�����ڵ�ǰҳ�е�<b> Y </b>����ֵ*/
	private int valueOfY;

	/**
	 * TODO ����һ����������Ԫ
	 * @param globalIndex ���е�һ����Ԫ��ȫ������
	 * @param valueOfY �������ҳ��� <b> Y </b> ����ƫ��
	 */
	public RowIndex(int globalIndex, int valueOfY) {
		this.globalIndex = globalIndex;
		this.valueOfY = valueOfY;
	}

	public int getGlobalIndex() {
		return globalIndex;
	}

	public void setGlobalIndex(int globalIndex) {
		this.globalIndex = globalIndex;
	}

	public int getValueOfY() {
		return valueOfY;
	}

	public void setValueOfY(int valueOfY) {
		this.valueOfY = valueOfY;
	}

	
}
