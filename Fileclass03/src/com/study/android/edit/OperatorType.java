package com.study.android.edit;

/**
 * 枚举类：编辑符类别
 * 
 * @author: tanqiang
 * @create-time: 2008-7-18
 */
public enum OperatorType {
	/** 编辑符类型：<b>非法操作符</b> */
	OP_ID_INVALID(new String("invalid operation")),//不合法操作

	/** 编辑符类型：<b>左选择符<b>0</b> */
	OP_ID_LEFTSELECT(new String("left selecting operation")),//左选择操作

	/** 编辑符类型：<b>有选择符</b> */
	OP_ID_RIGHTSELECT(new String("right selecting operation")),// 右选择操作

	/** 编辑符类型：<b>复制符</b> */
	OP_ID_COPY(new String("copy operatin")),// 复制操作

	/** 编辑符类型：<b>粘贴符</b> */
	OP_ID_PASTE(new String("paste operation")),// 粘贴操作

	/** 编辑符类型：<b>插入符号</b> */
	OP_ID_INSERT(new String("insert operation")),// 插入操作

	/** 编辑符类型：<b>删除符的一半</b> */
	OP_ID_HALFDEL(new String("half deleting operation")),// 删除操作

	/** 编辑符类型：<b>左剪切符</b> */
	OP_ID_LEFTCUT(new String(" left cutting operation")),//左剪切操作

	/** 编辑符编码：<b>空格符</b> */
	OP_ID_SPACE(new String(" space operation")),	//空格操作

	/** 编辑符类型：<b>右剪切</b> */
	OP_ID_RIGHTCUT(new String("right cutting operation")),// 右剪切操作

	/** 编辑符类型：<b>回退符</b> */
	OP_ID_BACKSPACE(new String("backspace operation")),// 回退操作

	/** 编辑符类型：<b>换行符</b> */
	OP_ID_ENTER(new String(" enter operation")),// 换行操作

	/** 编辑符类型：<b>撤销符</b> */
	OP_ID_UNDO(new String("undo operation")),//撤销操作

	/** 编辑符类型：<b>重切分</b> */
	OP_ID_RESEG_1(new String("resegmenting operation"));// 重切分操作

	private String type = null;

	private OperatorType(String str) {
		this.type = str;
	}

	/** 获取编辑符类型名称 */
	public String getType() {
		return this.type;
	}
}
