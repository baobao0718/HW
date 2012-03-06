package com.study.android.retrieval;

public class Head0Datastruct {
	
	private int creatednumoffile;
	
	private int presentmonth;			//记录在案的月份数
	
	private int locationofnewrecord;
	
	public int getcreatednumoffile()
	{
		return this.creatednumoffile;
	}
	
	public void setcreatednumoffile(int num)
	{
		this.creatednumoffile = num;
	}
	
	public int getpresentmonth()
	{
		return this.presentmonth;
	} 
	
	public void setpresentmonth(int present)
	{
		this.presentmonth = present;
	}
	
	public int getlocationofnewrecord()
	{
		return this.locationofnewrecord;
	}
	
	public void setlocationofnewrecord(int location)
	{
		this.locationofnewrecord =  location;
	}
	
	public Head0Datastruct()
	{
		this.setcreatednumoffile(0);
		this.setpresentmonth(0);
		this.setlocationofnewrecord(0);
	}
}
