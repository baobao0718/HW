
package com.study.android.data;
/**
 *@author: fuxinggang
 *@create-time: 2009-3-3 下午04:34:39
 */
public class RowIndex {

	/**该行第一个单元的全局索引*/
	private int globalIndex;
	
	/**该行在当前页中的<b> Y </b>坐标值*/
	private int valueOfY;

	/**
	 * TODO 构造一个行索引单元
	 * @param globalIndex 该行第一个单元的全局索引
	 * @param valueOfY 该行相对页面的 <b> Y </b> 方向偏移
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
