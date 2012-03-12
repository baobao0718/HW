 package com.study.android.basicData;

import java.io.Serializable;

public class CharPoint extends BasePoint implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private float width = 2.0f;

	public CharPoint(short x, short y) {
		super(x, y);
	}
	
	public CharPoint(short x, short y, float w) {
		super(x, y);
		this.width = w;
	}
	
	public CharPoint(short x, short y, boolean end, float width){
		super(x, y);
		this.width = width;
		this.setEnd(end);
	}

	public float getStrokeWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	public void setStrokeWidth(float w) {
		// TODO Auto-generated method stub
		this.width = w;
	}

}
