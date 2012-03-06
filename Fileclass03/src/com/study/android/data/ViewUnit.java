package com.study.android.data;


import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;

/**
 * �洢��ǰ��Ļ��������ͼ��Ԫ������͸߿���Ϣ
 * @wuwenbo
 * @date 08/07/21
 */
public class ViewUnit {
	private int globalIndex;//����ͼ��Ԫ��Ӧ��ȫ������ֵ(ֻ���ڻ滭����Ļʱ����ȷ��,��ʼΪ-1)
	private int x, y;//�õ�Ԫ���Ͻ�����ʾ����е�����
	private int width;//��Ԫ���
	private int height;//��Ԫ�߶�
	private DataUnit dataUnit;
	
	/**
	 * 
	 * @param global_index: ȫ������
	 * @param x��x����
	 * @param y��y����
	 * @param width�����
	 * @param height���߶�
	 * @param id����������,DataType����ControlTypeö��
	 */
	public ViewUnit(int global_index, int x, int y, int width,int height, DataUnit dataUnit) {
		this.width = width;
		this.height = height;
		this.globalIndex = global_index;
		this.x = x;
		this.y = y;
		this.dataUnit = dataUnit;
	}

//	public ViewUnit(ViewUnit a) {
//		this.width = a.getWidth();
//		this.height = a.getHeight();
//		this.globalIndex = -1;
//		this.x = a.getX();
//		this.y = a.getY();
//		this.dataUnit = a.getDataUnit();
//	}
	
	/**����ͬ��������DataUnit��ViewUnitӳ��*/
//	public ViewUnit(DataUnit a) {
//		this.width = a.getWidth();
//		this.height = a.getHeight();
//		this.globalIndex = -1;
//		this.x = 0;
//		this.y = 0;
//		this.dataUnit = a;
//	}
	
	/**������ratio����DataUnit��ViewUnitӳ��(�Ű�ʱ��)*/
	public ViewUnit(DataUnit a, double ratio) {
		this.dataUnit = a;
		if (a.getDataType() == DataType.TYPE_CHAR) {// ��������ֵ�Ԫ����Ҫ�������Ÿ߿�
			this.width = (int) (a.getWidth() * ratio);
			this.height = (int) (a.getHeight() * ratio);
		} else {// �����������Ԫ������Ҫ���¼���߿�
			this.width = a.getWidth();
			this.height = a.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public DataUnit getDataUnit() {
		return dataUnit;
	}

	public void setDataUnit(DataUnit dataUnit) {
		this.dataUnit = dataUnit;
	}

	public int getGlobalIndex() {
		return globalIndex;
	}

	public void setGlobalIndex(int globalIndex) {
		this.globalIndex = globalIndex;
	}
}