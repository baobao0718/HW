package com.study.android.basicData.type;

import java.util.ArrayList;
import java.util.List;

import com.study.android.HWtrying.Mview;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.MyStroke;

/**
 * 图像类型
 * @author: tanqiang
 * @create-time: 2008-7-16
 */
public class ImageUnit extends DataUnit{

	private static final long serialVersionUID = 1L;

	/** 笔划是图像的基本单元 */
	private List<MyStroke> strokes;
	
	public ImageUnit(short width, short height) {
		super(width, height, DataType.TYPE_IMAGE);
		strokes = new ArrayList<MyStroke>();
	}
	
	public ImageUnit(ImageUnit imageUnit) {
		super(imageUnit.getWidth(), imageUnit.getHeight(), DataType.TYPE_IMAGE);
		this.strokes = imageUnit.getStrokes();

	}

	public ImageUnit clone() {
		return new ImageUnit(this);
	}

	public List<BasePoint> getPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPoint(BasePoint p, int... color) {
		// TODO Auto-generated method stub
//		if(colorIndex == null || colorIndex.length == 0) {
//			System.err.println("，样点添加不成功：图像单元需要指定颜色索引！");
//			return;
//		}
		int size = this.strokes.size();
		if(size == 0) {// 笔划数为0
			if(p.isEnd() == true) return;// 没有笔划的情况下，不允许添加结束点
			
			MyStroke ch = new MyStroke();
			ch.setStrokeWidth(p.getStrokeWidth());
			ch.addPoint(p);
			this.strokes.add(ch);
		} else {
			MyStroke ch = this.strokes.get(size - 1);
			List<BasePoint> list = ch.getPoints();
			int lastSize = list.size();
			boolean b = list.get(lastSize - 1).isEnd();
			if(b == false) {// 一笔仍未结束
				ch.addPoint(p);
				if (p.isEnd() == true)
					ch.setColor(color[0]);               //这是在干什么？？？
			} else if(b == true && p.isEnd() == false){// 新的一笔开始
				MyStroke chUnit = new MyStroke();
				chUnit.setStrokeWidth(p.getStrokeWidth());
				chUnit.addPoint(p);
				this.strokes.add(chUnit);
			}
			// else 新的一笔开始，但是添加的是一个结束点则不做添加操作
		}
	}

	public List<MyStroke> getStrokes() {
		return this.strokes;
	}
	
	public void addStrokes(MyStroke stroke) {
		this.strokes.add(stroke);
	}
}
