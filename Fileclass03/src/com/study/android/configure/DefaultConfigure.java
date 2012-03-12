package com.study.android.configure;

import android.graphics.Color;

import com.study.android.data.ListTable;

public class DefaultConfigure {

	public static void defaultconfigure(float version)
	{
		ListTable.version = version;
		ListTable.writespeed = 780 ;
		ListTable.characterstrokewidth = 4 ;	
		ListTable.shuxiejizhun =56;
		ListTable.charactersize =40;
		ListTable.spacewidth =3;
		ListTable.bgcolor =Color.WHITE;
		ListTable.charactercolor = Color.BLACK;
		ListTable.isAutoSpaceValue=0;								//默认为中文
		ListTable.isAutoSubmit =0;									//默认为手动提交
	}
}
