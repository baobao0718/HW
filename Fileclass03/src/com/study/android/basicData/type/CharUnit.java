package com.study.android.basicData.type;

import java.util.ArrayList;
import java.util.List;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.CharPoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
//import hitsz.handwriting.data.DataUnit.CharPoint;



/**
 * 文字单元
 * @author: tanqiang
 * @create-time: 2008-7-16
 */
public class CharUnit extends DataUnit{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 字符存储的基准高度 63
	 */
	public static final byte BASE_HEIGHT = 63;
	
	/**
	 * 字体的边缘 2
	 */
	public static final byte ANCHOR = 2;	

	private int color;
	
	private float strokeWidth;
	
	/**字体格式宽度*/
	private short formatWidth;
	
	private ArrayList<BasePoint> points;
	
	public CharUnit(short width, short height) {
		super(width, height, DataType.TYPE_CHAR);
		points = new ArrayList<BasePoint>();
	}

	public CharUnit(short width, short height, int color) {
		super(width, height, DataType.TYPE_CHAR);
		this.color = color;
		points = new ArrayList<BasePoint>();
	}
	
	public CharUnit(CharUnit charUnit) {
		super(charUnit.getWidth(), charUnit.getHeight(), DataType.TYPE_CHAR);
		this.setWidth(charUnit.getWidth()) ;
		this.setHeight(charUnit.getHeight());
		points = new ArrayList<BasePoint>();
		for(BasePoint a : charUnit.getPoints()){//深层克隆
			BasePoint b = new CharPoint(a.getX(), a.getY(), a.isEnd(), a.getStrokeWidth());
			points.add(b);
		}
		this.format = charUnit.getCharFormat();
	//	this.color = charUnit.getColor();
		this.formatWidth = charUnit.formatWidth;
	}
	

	/**
	 *TODO 由参数format 设置字体格式，并修改字体的格式高度
	 *
	 *@param format 
	 */
	public void setCharFormat(CharFormat format) {
		this.format = format;
		this.formatWidth = (short) Math.ceil(1.0 * this.getWidth()       
				* format.getHeight() / this.getHeight());      //Math.ceil 作用：主要任务是截掉小数以后的位数.ceil总是把数字变大
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public float getStrokeWidth(){
		return strokeWidth;
	}
	
	public void setStrokeWidth(float strokeWidth){
		this.strokeWidth = strokeWidth;
	}
	
//	public short getFormatWidth() {
//		return this.formatWidth;
//	}
	
	public DataUnit clone() {
		// TODO Auto-generated method stub
		return new CharUnit(this);
	}

	//public void addPoint(BasePoint p, int... color) {          //int ...什么意思？？,这里我先改下｀｀｀｀｀｀
	public void addPoint(BasePoint p){
		this.points.add((CharPoint) p);
	}

	public List<BasePoint> getPoints() {
		// TODO Auto-generated method stub
		return this.points;
	}

	@Override
	public void addPoint(BasePoint p, int... color) {
		// TODO Auto-generated method stub
		
	}

}
