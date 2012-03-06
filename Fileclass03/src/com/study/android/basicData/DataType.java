package com.study.android.basicData;

/**
 * 数据类型：枚举
 * 
 * @author: tanqiang
 * @create-time: 2008-7-15
 */
public enum DataType {
	/** 控制单元类型 */
	TYPE_CONTROL,
	/** 图像单元类型 */
	TYPE_IMAGE,
	/** 文字单元类型 */
	TYPE_CHAR,
	/** 位图类型 */
	TYPE_BITMAP,
	/** 书法单元 */
	TYPE_CALLIGRAPHY,

	/** 换行控制符 */
	TYPE_CTRL_ENTER,
	/** 空格控制符 */
	TYPE_CTRL_SPACE,
	/** 缩进控制符 */
	TYPE_CTRL_INDENT,
//	/** 新页符 */
//	TYPE_NEWPAGE_ID,
//	/** 虚拟结束符 */
//	TYPE_VIRTUEL_ENDUNIT_ID
}
