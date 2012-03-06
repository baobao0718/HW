package com.study.android.edit;

/**
 * �������¼�
 * 
 * @author: ̷ǿ
 * @create-time: 2008-9-3
 */
public class OperatorEvent {

	/** ��ѡ���¼� */
	public final static OperatorEvent leftSelectEvent = new OperatorEvent(OperatorType.OP_ID_LEFTSELECT);

	/** ��ѡ���¼� */
	public final static OperatorEvent rightSelectEvent = new OperatorEvent(OperatorType.OP_ID_RIGHTSELECT);

	/** ��һ��ɾ������ */
	public final static OperatorEvent firstDeleteEvent = new OperatorEvent(OperatorType.OP_ID_HALFDEL);

	/** �ڶ���ɾ������ */
	public final static OperatorEvent secondDeleteEvent = new OperatorEvent(OperatorType.OP_ID_HALFDEL);

	/** ���������� */
	private OperatorType type = null;

	/** ���������õ�ȫ������ */
	private int index = EditDFA.INVALID_UNIT_INDEX;

	/** ���������õĵڶ���ȫ���������磺ɾ�������� */
	private int nextIndex = EditDFA.INVALID_UNIT_INDEX;

	/**
	 * ���췽��
	 * @param type����������
	 */
	private OperatorEvent(OperatorType type) {
		this.type = type;
	}

	/**
	 * ��ȡ��������
	 * @return
	 */
	public OperatorType getType() {
		return this.type;
	}

	/**
	 * ��ȡ������������ȫ������
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * �趨���������õĵڶ���ȫ������
	 * @param index
	 */
	public void setNextIndex(int index) {
		this.nextIndex = index;
	}

	/**
	 * ��ȡ���������õ�ȫ������
	 * @return�����������õ�ȫ������
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * ��ȡ���������õĵڶ���ȫ������
	 * @return�����������õĵڶ���ȫ������
	 */
	public int getNextIndex() {
		return this.nextIndex;
	}

	/**
	 * �����������õ�ȫ����������Ϊ��Ч
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
