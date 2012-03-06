package com.study.android.retrieval;

import java.util.ArrayList;

import com.study.android.basicData.DataUnit;

public class BodyDatastruct 
{
	private int characternumoftitle;
	private int existflag;					// 0 表示不存在，1 表示存在
	private int serianumberoffile;			//文件编号
	private int date;		//例如：19000315
	private int vectoroffile;
	private String filename;//zhuxiaoqing 2011.08.21
	private ArrayList<DataUnit> title = new ArrayList<DataUnit>();   	//用于存储标题
	public int getVectoroffile()
	{
		return this.vectoroffile; 
	}

	public void setVectoroffile(int vectoroffile)
	{
		this.vectoroffile = vectoroffile;
	}


	public String getFilename()
	{
		return this.filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	
	
	public void setcharacternumoftitle(int i)
	{
		this.characternumoftitle = i;
	}
	
	public int getcharacternumoftitle()
	{
		return this.characternumoftitle;
	}
	
	public void setexistflag(int flag)
	{
		this.existflag = flag;
	}
	
	public int getexistflag()
	{
		return this.existflag;
	}
	
	public void setserianumberoffile(int seria)
	{
		this.serianumberoffile = seria;
	}
	
	public int getserianumberoffile()
	{
		return this.serianumberoffile;
	}
	
	public void setdate(int datetime)
	{
		this.date = datetime;
	}
	
	public int getdate()
	{
		return this.date;
	}
	
	public ArrayList<DataUnit> gettitle()
	{
		return this.title;
	}
	
	public void settitle(ArrayList<DataUnit> titleinf)
	{
		this.title = (ArrayList<DataUnit>) titleinf.clone();
	}
}
