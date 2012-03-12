package com.study.android.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储切分后的图像buffer中的基本信息
 * @冯交交 
 * @version 1.00 07/06/21
 */
public class Picpixel {

	private int width, height, startX;
	private List<Integer> data;

	public Picpixel(int width, int height, int startX) {
		this.width = width;
		this.height = height;
		this.startX = startX;
		data = new ArrayList<Integer>();
	}

	public Picpixel(int width, int height, int startX, List<Integer> listData) {
		this.width = width;
		this.height = height;
		this.startX = startX;
		data = listData;
	}

	public void addData(Integer data) {
		this.data.add(data);
	}

	public List<Integer> getData() {
		return data;
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
	
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	public int getStartX() {
		return this.startX;
	}
}