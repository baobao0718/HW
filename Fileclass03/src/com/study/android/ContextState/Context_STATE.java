package com.study.android.ContextState;


/**
 * 
 * @author: chenzou
 * @create-time: 2010-11-16
 */

public class Context_STATE {
	
	
	/** 编辑页面正处于书写文字状态*/
	
	public  static final short Context_STATE_0=0;
	
	/** 编辑页面正处于书写图片状态*/
	
	public static final short Context_STATE_1=1;
	
	/** 编辑页面正处于书写编辑状态*/
	
	public static final short Context_STATE_2=2;
	
	
	/****编辑子状态*******/
	
	
	/** 编辑页面正处于编辑换行状态*/
	
	public  static final short Ctrl_STATE_1=1;
	
	/** 编辑页面正处于编辑删除状态*/
	
	public static final short Ctrl_STATE_2=2;
	
	/** 编辑页面正处于编辑空格状态*/
	
	public static final short Ctrl_STATE_3=3;
	
	/** 编辑页面正处于编辑左选择、右选择都完成时状态*/
	
	public  static final short Ctrl_STATE_4=4;
	
	/** 编辑页面正处于编辑复制状态*/
	
	public static final short Ctrl_STATE_5=5;
	
	/** 编辑页面正处于编辑粘贴状态*/
	
	public static final short Ctrl_STATE_6=6;
	
	/** 编辑页面正处于编辑剪切状态*/
	
	public static final short Ctrl_STATE_7=7;
	
	/** 编辑页面正处于编辑回退状态*/
	
	public static final short Ctrl_STATE_8=8;
	
	
	
	/****画图子状态*******/
	
	
	/** 画图页面正处于BACKTODRAW状态*/
	public static final short Graph_STATE_0=0;	
	
	
	/** 画图页面正处于橡皮擦擦除状态*/
	public static final short Graph_STATE_1=1;	
	
	/** 画图页面正处于内嵌字状态*/
	public static final short Graph_STATE_2=2;	
	
	/** 画图页面正处却省嵌字状态*/
	public static final short Graph_STATE_Default=-1;	
}


/**
Context_STATE.Context_STATE_0

Context_STATE.Context_STATE_1

Context_STATE.Context_STATE_5
*/