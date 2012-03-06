
package com.study.android.basicData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *@author 付兴刚
 *@version 创建日期 2008-9-5 上午09:38:52
 */
public class MyStroke implements Serializable {

	private int color;
	
	private List<BasePoint> points;
	
	private float strokeWidth;
	
	public MyStroke() {
		points = new ArrayList<BasePoint>();
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public List<BasePoint> getPoints() {
		return points;
	}

	public void addPoint(BasePoint point) {
		this.points.add(point);
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
}
