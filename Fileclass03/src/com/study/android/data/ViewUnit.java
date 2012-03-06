package com.study.android.data;


import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;

/**
 * 存储当前屏幕中所有视图单元的坐标和高宽信息
 * @wuwenbo
 * @date 08/07/21
 */
public class ViewUnit {
	private int globalIndex;//该视图单元对应的全局索引值(只有在绘画到屏幕时才能确定,初始为-1)
	private int x, y;//该单元左上角在显示面板中的坐标
	private int width;//单元宽度
	private int height;//单元高度
	private DataUnit dataUnit;
	
	/**
	 * 
	 * @param global_index: 全局索引
	 * @param x：x坐标
	 * @param y：y坐标
	 * @param width：宽度
	 * @param height：高度
	 * @param id：数据类型,DataType或者ControlType枚举
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
	
	/**按相同比例生成DataUnit的ViewUnit映像*/
//	public ViewUnit(DataUnit a) {
//		this.width = a.getWidth();
//		this.height = a.getHeight();
//		this.globalIndex = -1;
//		this.x = 0;
//		this.y = 0;
//		this.dataUnit = a;
//	}
	
	/**按比例ratio生成DataUnit的ViewUnit映像(排版时用)*/
	public ViewUnit(DataUnit a, double ratio) {
		this.dataUnit = a;
		if (a.getDataType() == DataType.TYPE_CHAR) {// 如果是文字单元，需要重新缩放高宽
			this.width = (int) (a.getWidth() * ratio);
			this.height = (int) (a.getHeight() * ratio);
		} else {// 如果是其他单元，则不需要重新计算高宽
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