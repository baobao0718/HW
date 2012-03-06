package com.study.android.data;
/**
 * ����
 * @author ���Ĳ�
 *
 */
public class MyVector {
	public double i;
	public double j;
	/**
	 * ����һ������
	 * @param x1 ������������
	 * @param y1 �������������
	 * @param x2 �����յ������
	 * @param y2 �����յ�������
	 */
	public MyVector(int x1,int y1,int x2,int y2){
		i=x2-x1;
		j=y2-y1;
	}
	/**
	 * ����һ������
	 * @param x1 ������������
	 * @param y1 �������������
	 * @param x2 �����յ������
	 * @param y2 �����յ�������
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
	 * ��ø�������ģ
	 * @return
	 */
	public double mod(){
		return Math.hypot(i, j);
	}
}