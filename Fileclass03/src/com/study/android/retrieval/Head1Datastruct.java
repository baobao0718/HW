package com.study.android.retrieval;

public class Head1Datastruct 
{
	private int monthnumber;												//月份号比如 201005
	private int recordnumoffile;
	private int addressinHead2; 
	
	public Head1Datastruct(int num,int numoffile,int address)			//构造方法没有返回类型！！！
	{
		this.monthnumber = num;
		this.recordnumoffile = numoffile;
		this.addressinHead2 = address;
	}
	
	public void setmonthnumber(int num)
	{
		this.monthnumber = num;
	}
	
	public int getmonthnumber()
	{
		return this.monthnumber;
	} 
	
	public void setrecordnumoffile(int numoffile)
	{
		this.recordnumoffile = numoffile;
	}
	
	public int getrecordnumoffile()
	{
		return this.recordnumoffile;
	}
	
	public void setaddressinHead2(int address)
	{
		this.addressinHead2 = address;
	}
	public int getaddressinHead2()
	{
		return this.addressinHead2;
	}
}
