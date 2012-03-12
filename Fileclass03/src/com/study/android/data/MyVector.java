package com.study.android.data;
/**
 * 向量
 * @author 武文博
 *
 */
public class MyVector {
	public double i;
	public double j;
	/**
	 * 构造一个向量
	 * @param x1 向量起点横坐标
	 * @param y1 向量起点纵坐标
	 * @param x2 向量终点横坐标
	 * @param y2 向量终点纵坐标
	 */
	public MyVector(int x1,int y1,int x2,int y2){
		i=x2-x1;
		j=y2-y1;
	}
	/**
	 * 构造一个向量
	 * @param x1 向量起点横坐标
	 * @param y1 向量起点纵坐标
	 * @param x2 向量终点横坐标
	 * @param y2 向量终点纵坐标
	 */
	public MyVector(double x1,double y1,double x2,double y2){
		i=x2-x1;
		j=y2-y1;
	}
	public MyVector(double i,double j){
		this.i=i;
		this.j=j;
	}
	/**
	 * 获得该向量的模
	 * @return
	 */
	public double mod(){
		return Math.hypot(i, j);
	}
}