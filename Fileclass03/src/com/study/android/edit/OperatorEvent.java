package com.study.android.edit;

/**
 * 操作符事件
 * 
 * @author: 谭强
 * @create-time: 2008-9-3
 */
public class OperatorEvent {

	/** 左选择事件 */
	public final static OperatorEvent leftSelectEvent = new OperatorEvent(OperatorType.OP_ID_LEFTSELECT);

	/** 右选择事件 */
	public final static OperatorEvent rightSelectEvent = new OperatorEvent(OperatorType.OP_ID_RIGHTSELECT);

	/** 第一个删除操作 */
	public final static OperatorEvent firstDeleteEvent = new OperatorEvent(OperatorType.OP_ID_HALFDEL);

	/** 第二个删除操作 */
	public final static OperatorEvent secondDeleteEvent = new OperatorEvent(OperatorType.OP_ID_HALFDEL);

	/** 操作符类型 */
	private OperatorType type = null;

	/** 操作符作用的全局索引 */
	private int index = EditDFA.INVALID_UNIT_INDEX;

	/** 操作符作用的第二个全局索引，如：删除操作等 */
	private int nextIndex = EditDFA.INVALID_UNIT_INDEX;

	/**
	 * 构造方法
	 * @param type：操作类型
	 */
	private OperatorEvent(OperatorType type) {
		this.type = type;
	}

	/**
	 * 获取构造类型
	 * @return
	 */
	public OperatorType getType() {
		return this.type;
	}

	/**
	 * 获取操作符的作用全局索引
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 设定操作符作用的第二个全局所用
	 * @param index
	 */
	public void setNextIndex(int index) {
		this.nextIndex = index;
	}

	/**
	 * 获取操作符作用的全局索引
	 * @return：操作符作用的全局所用
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * 获取操作符作用的第二个全局索引
	 * @return：操作符作用的第二个全局索引
	 */
	public int getNextIndex() {
		return this.nextIndex;
	}

	/**
	 * 将操作符作用的全局索引设置为无效
	 */
	public void reset() {
		this.index = EditDFA.INVALID_UNIT_INDEX;
		this.nextIndex = EditDFA.INVALID_UNIT_INDEX;
	}

	public boolean equals(Object obj) {
		if(obj == null || obj instanceof OperatorEvent == false) {
			return false;
		}
		if(this == obj) {
			return true;
		}
		OperatorEvent evnet = (OperatorEvent) obj;
		if(this.type == evnet.type
			&& this.index == evnet.index
			&& this.nextIndex == evnet.nextIndex) {
			return true;
		}
		return false;
	}
}
