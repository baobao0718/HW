package com.study.android.edit;

/**
 * ö���ࣺ�༭�����
 * 
 * @author: tanqiang
 * @create-time: 2008-7-18
 */
public enum OperatorType {
	/** �༭�����ͣ�<b>�Ƿ�������</b> */
	OP_ID_INVALID(new String("invalid operation")),//���Ϸ�����

	/** �༭�����ͣ�<b>��ѡ���<b>0</b> */
	OP_ID_LEFTSELECT(new String("left selecting operation")),//��ѡ�����

	/** �༭�����ͣ�<b>��ѡ���</b> */
	OP_ID_RIGHTSELECT(new String("right selecting operation")),// ��ѡ�����

	/** �༭�����ͣ�<b>���Ʒ�</b> */
	OP_ID_COPY(new String("copy operatin")),// ���Ʋ���

	/** �༭�����ͣ�<b>ճ����</b> */
	OP_ID_PASTE(new String("paste operation")),// ճ������

	/** �༭�����ͣ�<b>�������</b> */
	OP_ID_INSERT(new String("insert operation")),// �������

	/** �༭�����ͣ�<b>ɾ������һ��</b> */
	OP_ID_HALFDEL(new String("half deleting operation")),// ɾ������

	/** �༭�����ͣ�<b>����з�</b> */
	OP_ID_LEFTCUT(new String(" left cutting operation")),//����в���

	/** �༭�����룺<b>�ո��</b> */
	OP_ID_SPACE(new String(" space operation")),	//�ո����

	/** �༭�����ͣ�<b>�Ҽ���</b> */
	OP_ID_RIGHTCUT(new String("right cutting operation")),// �Ҽ��в���

	/** �༭�����ͣ�<b>���˷�</b> */
	OP_ID_BACKSPACE(new String("backspace operation")),// ���˲���

	/** �༭�����ͣ�<b>���з�</b> */
	OP_ID_ENTER(new String(" enter operation")),// ���в���

	/** �༭�����ͣ�<b>������</b> */
	OP_ID_UNDO(new String("undo operation")),//��������

	/** �༭�����ͣ�<b>���з�</b> */
	OP_ID_RESEG_1(new String("resegmenting operation"));// ���зֲ���

	private String type = null;

	private OperatorType(String str) {
		this.type = str;
	}

	/** ��ȡ�༭���������� */
	public String getType() {
		return this.type;
	}
}
