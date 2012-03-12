
package com.study.android.code;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.study.android.ui.HwFunctionWindow;

import android.util.Log;


/**
 * 文件编码器，将内存中的数据存入到文件中
 *@author 付兴刚
 *@version 创建日期 2008-7-26 下午03:46:08
 */
public class DocumentCoder implements DataHandler{
	/** 文件的输出流 */
	private OutputStream outputStream = null;
	
	private byte color = -1;
	private byte height = -1;
	private byte width = -1;
	
	/** 控制单元掩码 0x80 1*** **** */
	public static final int CTRL_MASK = 0x80;
	
	/** 字符单元掩码 0x40 01** **** */
	public static final int CHAR_MASK = 0x40;
	
	/** 控制单元类型掩码 0xF0 1111 **** */
	public static final int CTRL_TYPE_MASK = 0xF0;
	
	/** 空格单元 0x90 1001 **** */
	public static final int SPACE = 0x90;
	
	/** 段单元 0xB0 1011 **** */
	public static final int PARA = 0xB0;
	
	public static final int CHAR_C = 0x20;  // 字符颜色掩码
	public static final int CHAR_H = 0x10;  // 字符格式高度掩码
	public static final int CHAR_W = 0x08;  // 字符笔画基准宽度掩码
	public static final int CHAR_S = 0x07;  // 字符样式掩码
	
	public static final int CHAR_COLOR = 0x20;  // 字符颜色掩码
	public static final int CHAR_HEIGHT = 0x10;  // 字符格式高度掩码
	public static final int CHAR_WIDTH = 0x08;  // 字符笔画基准宽度掩码
	
	/** 图像掩码 0x48 */
	public static final int IMAGE_STYLE = 0x48;  // 图像掩码
	public static final int CALLIGRAPHY_STYLE = 0x50;//书法掩码
	
	/** 节单元 0xA0 1010 **** */
	public static final int SECTION_SYTLE = 0xA0;
	
	/** 笔画结束标志 0x8000 1000 0000 0000 0000*/
	public static final int STROKE_END = 0x8000;  
	
	/** 字符结束标志 0x8080 1000 0000 1000 0000*/
	public static final int CHAR_END = 0x8080; 
	
	private short pointX = -1;
	private short pointY = -1;
	
	int prePointX = 0;
	int prePointY = 0;
	
	boolean paraFirst = true;
	byte preFirstRowIndent, preLeftIndent, preRightIndent, preAlignStyle;
	byte preLineSpaceType, preLineSpace, preBeforeParaSpace, preAfterParaSpace;
	
	public DocumentCoder(OutputStream outStream){
		this.outputStream = outStream;
	}
	
	/**
	 * 设置颜色表
	 * @param colors 颜色数组
	 * @throws ParseException
	 */
	public void setColorTable(int[] colors)  throws ParseException{
		int size = colors.length;
		try{
			byte colorNum = (byte)size;
			outputStream.write(colorNum);
//			System.err.println("colorNum : " + colorNum);
			for(int i = 0; i < size; i++){
				byte alpha = (byte) ((colors[i] >> 24) & 0xff);
				byte red  = (byte) ((colors[i] >> 16) & 0xff);
				byte green = (byte) ((colors[i] >> 8) & 0xff);
				byte blue = (byte) ((colors[i]) & 0xff);
				outputStream.write(alpha);
				outputStream.write(red);
				outputStream.write(green);
				outputStream.write(blue);
			}
		}catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder setColorTable " + e.getMessage());
		}
	}
	
	/**
	 * 开始字符单元
	 * @param style 样式
	 * @param color 颜色
	 * @param formatHeight 字体格式高度
	 * @param strokeWidth 笔画基准宽度
	 * @throws ParseException
	 */
	public void startCharUnit(byte style, byte color, byte formatHeight,
			byte strokeWidth, short charHeight) throws ParseException{
		//int dataHead;//字符单元头，00CHWSSS  style 默认为0
		boolean colorFlag = true;
		boolean heightFlag = true;
		boolean widthFlag = true;
		try {
		/*	if (this.color == -1 && this.height == -1 && this.width == -1) {// 此时说明是第一个字符单元
				dataHead = style | CHAR_COLOR | CHAR_HEIGHT | CHAR_WIDTH;//0x38
				outputStream.write(dataHead);
				outputStream.write(color);
				outputStream.write(formatHeight);
				outputStream.write(strokeWidth);
				
				outputStream.write((charHeight & 0xFF00) >> 8);
				outputStream.write(charHeight & 0xFF);
				
				this.color = color;
				this.height = formatHeight;
				this.width = strokeWidth;
				//Log.v("DEBUG---STARTCHARUNIT  ","fdsafdfd000000");
				return;
			}
			//Log.v("DEBUG---STARTCHARUNIT  ","fdsafdfd1111111");
			dataHead = style;//0000 0***, 高位必为0，也就是标识了字符文字单元
			if (this.color != color){// 判断是否延续了颜色
				dataHead = dataHead | CHAR_COLOR;
				colorFlag = false;
				this.color = color;
//				System.err.println("\ncolor is changed");
			}
			
			if(this.height != formatHeight){//判断是否延续了基准高度
				dataHead = dataHead | CHAR_HEIGHT;
				heightFlag = false;
				this.height = formatHeight;
			}
			
			if(this.width != strokeWidth){//判断是否延续了基准笔画宽度
				dataHead = dataHead | CHAR_WIDTH;
				widthFlag = false;
				this.width = strokeWidth;
//			System.err.println("\nstrokeWidth is changed");
			}*/
			//char dataHead=(char)style;
			char dataHead=0x38;
			outputStream.write(dataHead);
//			System.out.println("\n dataHead is : " + dataHead);
			
			/*if(colorFlag == false)//写入新的颜色信息
				outputStream.write(color);
			
			if(heightFlag == false)//写入新的字体高度信息
				outputStream.write(formatHeight);
			
			if(widthFlag == false)//写入新的笔画宽度信息
				outputStream.write(strokeWidth);*/
			
			outputStream.write((charHeight & 0xFF00) >> 8);
			outputStream.write(charHeight & 0xFF);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder startCharUnit " + e.getMessage());
		}
	}
	
	/**
	 * 添加点，当end = true时，<b>x和y无效</b>
	 * @param x
	 * @param y
	 * @param end 是否结束点
	 * @throws ParseException
	 */
	public void addPoint(short x, short y, boolean end) throws ParseException {
		int tempX = 0, tempY = 0;
		int mid;
		
		try {
			if(this.pointX == -1 && this.pointY == -1) {// 笔画的第一个点
				
				this.pointX = x;
				this.pointY = y;

				prePointX =  x;
				prePointY =  y;

				tempX = (x & 0xFF0) >> 4;
				tempY = y & 0xFF;
				mid = ((x & 0xF) << 4) | ((y & 0xF00) >> 8);
				
				outputStream.write(tempX);					//注意，write(int) 可以写入一个字节范围内的整数（2的8次方大小），读出时候直接使用read()即可读出来，若超过256的范围，则读出的数为本身值减去256！！
				outputStream.write(mid);
				outputStream.write(tempY);
				
				
//				System.err.printf("tempX : %x tempY : %x\n", tempX, tempY );
			} else {
				
//				System.err.println("Point x : " + x + "  y : " + y);
				tempX = (x & 0x7FFF) - prePointX;
				tempY = (y & 0x7FFF) - prePointY;
				if(tempX < 0)tempX = 128 - tempX;
				if(tempY < 0)tempY = 128 - tempY;
				//if(tempX < 0)tempX = 165 - tempX;//zhuxiaoqing 2011.06.14
				//if(tempY < 0)tempY = 165 - tempY;
				outputStream.write(tempX);
				outputStream.write(tempY);
//				System.err.println("tempX : " + Integer.toHexString(tempX) +
//						" tempY : " + Integer.toHexString(tempY));
				prePointX = x;
				prePointY = y;

				if (end == true) {
					//Log.v("DEBUG---->","JIN LAI LE MEI");
					outputStream.write((STROKE_END & 0xFF00) >> 8);// 写入笔画结束标志
					outputStream.write(STROKE_END & 0x00FF);// 写入笔画结束标志
					this.pointX = -1;
					this.pointY = -1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder addPoint(short x," +
					" short y, boolean end) " + e.getMessage());
		}
	}
	
	/**
	 * 结束字符单元
	 * @throws ParseException
	 */
	public void endCharUnit() throws ParseException{
		try{
			outputStream.write((CHAR_END & 0xFF00) >> 8);
			outputStream.write(CHAR_END & 0x00FF);
			this.pointX = -1;
			this.pointY = -1;
		}catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder endCharUnit " + e.getMessage());
		}
	}
	
	/**
	 * 开始层单元，一个嵌入的小文档
	 * @param roundStyle 环绕方式
	 * @param height 高度
	 * @param width 宽度
	 * @throws ParseException
	 */
	public void startLayerUnit(byte roundStyle, int height, int width) throws ParseException{
		
	}
	
	/**
	 * 结束层单元
	 * @throws ParseException
	 */
	public void endLayerUnit() throws ParseException{
		
	}
	
	/**
	 * 开始图形单元
	 * @param roundStyle 环绕方式
	 * @param height 高度
	 * @param width 宽度
	 * @throws ParseException
	 * @see 添加开始点请使用 addPoint(short x, short y, byte width, byte color)
	 * @see 添加普通点请使用 addPoint(short x, short y, boolean end)
	 */
	public void startImageUnit(byte roundStyle, int height, int width) throws ParseException{
		byte head =(byte) (IMAGE_STYLE | roundStyle);
		int tempHeight = height >> 4;
		int tempWidth = width & 0xFF;
		int high = (height & 0x0F) << 4;
		int low = width >> 8;
		int middle = high | low;
	//	Log.v("DEBUG--->","IN WRITTING IMAGE UNIT :"+"head->"+head+  "tempHeight->"+tempHeight+"  high->"+high+"  low->"+low);
		try {
			outputStream.write(head);
			outputStream.write(tempHeight);//高宽占用三个字节
			outputStream.write(middle);
			outputStream.write(tempWidth);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder startImageUnit " + e.getMessage());
		}
	}
	
	/**
	 * 添加开始点，适用于图形单元和书法单元
	 * @param x
	 * @param y
	 * @param width ？？
	 * @param color 颜色
	 * @throws ParseException
	 * @see 添加普通点请使用 addPoint(short x, short y, boolean end)
	 */
	public void addPoint(short x, short y, byte width, byte color) throws ParseException{
		this.pointX = x;
		this.pointY = y;
		
		prePointX =  x;
		prePointY =  y;
		
		int tempWidth = 0<< 4;
		int strokewidth = (int)width;
		Log.e("DEBUG--->","Stroke width in addPoint  "+strokewidth);
/*		boolean colorFlag = true;
		if (this.color != color) {
			colorFlag = false;
			this.color = color;
		}*/
		int highX = x >> 6;
		int lowX = (x & 0x3F) << 2;
		int highY = y >> 8; 
		int lowY = y & 0xFF;
		
//		System.err.println("colorFlag : " + colorFlag);
		int firstByte = highX | tempWidth;
//		if (colorFlag == false)
//			firstByte = firstByte | 0x80;
		int secondByte = lowX | highY;
		try {
			outputStream.write(strokewidth);//2012.03.09 liqiang
			Log.e("DEBUG--->222","Stroke width in addPoint  "+strokewidth);
			outputStream.write(firstByte);
			outputStream.write(secondByte);
			outputStream.write(lowY);
//			System.err.println(" x : " + x + " y : " + y);
//			System.err.printf("firstByte : %x  secondByte : %x lowY : %x\n", firstByte ,secondByte,lowY);
		//	if (colorFlag == false) {
				//Log.i("DEBUG--->zhuxiaoqing xie","color "+color);
				//Log.i("DEBUG--->zhuxiaoqing xie","zhuxiaoqing "+color);
				int color1=HwFunctionWindow.colortable.get(color);//zhuxiaoqing 2011.06.14
				
//				Log.i("DEBUG--->zhuxiaoqing xie","color1 "+color1);
				byte alpha = (byte) (((color1 >> 24) & 0xff));
				byte red  = (byte) (((color1 >> 16) & 0xff));
				byte green = (byte) (((color1 >> 8) & 0xff));
				byte blue = (byte) (((color1) & 0xff));
				
//				Log.i("DEBUG--->zhuxiaoqing xie","colorindex"+color);
//				Log.i("DEBUG--->zhuxiaoqing xie","alpha "+alpha);
//				Log.i("DEBUG--->zhuxiaoqing xie","red"+red);
//				Log.i("DEBUG--->zhuxiaoqing xie","green "+green);
//				Log.i("DEBUG--->zhuxiaoqing xie","blue "+blue);
				
				outputStream.write(red);
				outputStream.write(green);
				outputStream.write(blue);
				outputStream.write(alpha);
				//outputStream.write(alpha);
				//outputStream.write(color);
	//			System.err.println(" color in Writetofile：" + color);
	//		}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder addPoint(short x, short y, " +
					"byte width, byte color) " + e.getMessage());
		}
	}
	
	/**
	 * 结束图形单元
	 * @throws ParseException
	 */
	public void endImageUnit() throws ParseException{
		try {
			outputStream.write((CHAR_END & 0xFF00) >> 8);
			outputStream.write(CHAR_END & 0x00FF);
			this.pointX = -1;
			this.pointY = -1;
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder endImageUnit " + e.getMessage());
		}
	}
	
	/**
	 * 添加位图单元
	 * @param roundStyle 环绕方式
	 * @param height 高度
	 * @param width 宽度
	 * @param pixels 位图数据
	 * @throws ParseException
	 */
	public void addBitmapUnit(byte roundStyle, int height, int width, int[] pixels) throws ParseException{
		
	}
	
	/**
	 * 添加空格
	 * @param width 空格宽度
	 * @throws ParseException
	 */
	public void addSpaceUnit(short width) throws ParseException {
		int space = 0x09;//空格标志
		int high = width >> 8;
		space = space << 4;
		high = space | high;
		int low = (width << 24) >> 24;
		try {
			outputStream.write(high);
			outputStream.write(low);
//			System.out.println("\nspace Unit " + "high : " + high + "  low : " + low);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder addSpaceUnit " + e.getMessage());
		}
	}
	
	/**
	 * 添加换行
	 * @param width 换行单元宽度（为0）
	 * @throws ParseException
	 */
	public void addEnterUnit(short width) throws ParseException {
		int space = 0xB0;//换行标志

		byte target;
        target= (byte)(space& 0xff);//最低位
		try {
			outputStream.write(target);			//target 刚好就是 10110000（0XB0 （因为是整型，共四个字节）的最后一个字节）
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder addSpaceUnit " + e.getMessage());
		}
	}
	
	
	/**
	 * 开始页（节）控制单元，随后设置属性
	 * @throws ParseException
	 */
	public void startPageUnit(boolean changFlag, byte style) throws ParseException{
		int sectionHead = SECTION_SYTLE;
		try {
			if(changFlag == true) {
				sectionHead = sectionHead | 0x08;
			} else {
				sectionHead = sectionHead | 0x00;
			}
			sectionHead = sectionHead | style;
			outputStream.write(sectionHead);
//			System.err.println("\n section style : " + sectionHead);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder startPageUnit " + e.getMessage());
		}
	}
	
	/**
	 * 设置纸张大小
	 * @param height 纸张高度
	 * @param width 纸张宽度
	 * @throws ParseException
	 */
	public void setPageSize(short height, short width) throws ParseException{
		try {
			outputStream.write((height & 0xFF00) >> 8);
			outputStream.write(height & 0x00FF);
			
			outputStream.write((width & 0xFF00) >> 8);
			outputStream.write(width & 0x00FF);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder setPageSize " + e.getMessage());
		}
	}
	
	/**
	 * 设置页边距
	 * @param up
	 * @param down
	 * @param left
	 * @param right
	 * @throws ParseException
	 */
	public void setPageMargin(short up, short down, short left, short right) throws ParseException{
		try {
			outputStream.write((up & 0xFF00) >> 8);
			outputStream.write(up & 0x00FF);
			
			outputStream.write((down & 0xFF00) >> 8);
			outputStream.write(down & 0x00FF);
			
			outputStream.write((left & 0xFF00) >> 8);
			outputStream.write(left & 0x00FF);
			
			outputStream.write((right & 0xFF00) >> 8);
			outputStream.write(right & 0x00FF);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder setPageMargin " + e.getMessage());
		}
	}
	
	/**
	 * 设置页码
	 * @param position 页码位置
	 * @param isShowSum 是否显示总页码
	 * @param style 样式
	 * @param startNumber 起始页码
	 * @throws ParseException
	 */
	public void setPageNumber(byte position, byte isShowSum, byte style, short startNumber) throws ParseException{
		
	}
	
	/**
	 * 设置背景，当前版本未定义
	 * @throws ParseException
	 */
	public void setBackGround() throws ParseException{
		
	}
	
	/**
	 * 设置纵横方向
	 * @param orient true纵，false横
	 * @throws ParseException
	 */
	public void setOrient(boolean orient) throws ParseException{
		
	}
	
	/**
	 * 设置页边距的单位
	 * @param ut
	 * @throws ParseException
	 */
	public void setUnitType(byte ut) throws ParseException{
		
	}
	
	/**
	 * 设置分栏数
	 * @param snaking
	 * @throws ParseException
	 */
	public void setSnakingNumber(byte snaking) throws ParseException{
		
	}
	
	/**
	 * 开始页眉
	 * @throws ParseException
	 */
	public void startPageHeader() throws ParseException{
		
	}
	
	/**
	 * 结束页眉
	 * @throws ParseException
	 */
	public void endPageHeader() throws ParseException{
		
	}
	
	/**
	 * 开始页脚
	 * @throws ParseException
	 */
	public void startPageFooter() throws ParseException{
		
	}
	
	/**
	 * 结束页脚
	 * @throws ParseException
	 */
	public void endPageFooter() throws ParseException{
		
	}
	
	/**
	 * 结束页（节）控制单元
	 * @throws ParseException
	 */
	public void endPageUnit() throws ParseException{
		
	}
	
	/**
	 * TODO 开始段单元，之后考虑是否设置段的属性
	 * @throws ParseException
	 */
	public void startParagraph() throws ParseException {
		
	}
	
	
	public void setcolor(byte num) throws ParseException
	{
		this.color=num;
	}
	/**
	 * 添加段单元标志
	 * @param firstRowIndent 首行缩进
	 * @param leftIndent 左缩进
	 * @param rightIndent 右缩进
	 * @param alignStyle 对齐方式
	 * @param lineSpaceType 行距单位
	 * @param lineSpace 行距值
	 * @param beforeParaSpace 段前间距
	 * @param afterParaSpace 段后间距
	 * @throws ParseException
	 */
	public void addParagraph(byte firstRowIndent, byte leftIndent, byte rightIndent, byte alignStyle, 
			byte lineSpaceType, byte lineSpace, byte beforeParaSpace, byte afterParaSpace) throws ParseException{
		int head = PARA;
		int temp = 0;
		boolean leftIndentChange = false, rightIndentChange = false, rowIndentChange = false;
//		System.out.println(" firstRowIndent :" + firstRowIndent
//				+ "  leftIndent" + leftIndent + "  rightIndent " + rightIndent
//				+ "  alignStyle : " + alignStyle + " lineSpaceType "
//				+ lineSpaceType + "  lineSpace " + lineSpace
//				+ " beforeParaSpace " + beforeParaSpace + "  afterParaSpace  "
//				+ afterParaSpace);
		if(paraFirst == true) {
			preFirstRowIndent = firstRowIndent;//首行缩进不存在负值的情况
			preLeftIndent = leftIndent;
			preRightIndent = rightIndent;
			preAlignStyle = alignStyle;
			preLineSpaceType = lineSpaceType;
			preLineSpace = lineSpace;
			preBeforeParaSpace = beforeParaSpace;
			preAfterParaSpace = afterParaSpace;
			
			try {
				outputStream.write((head | 0x0F ) & 0xFF);
				
				temp = alignStyle;
				temp = (temp << 1) | lineSpaceType;
				temp = (temp << 5) | lineSpace;
				
				outputStream.write(temp);//写入对齐方式，行距单位，行距值等信息
//				System.err.println("\n段 ********* ： " + Integer.toHexString(temp) + "
//				temp : " + temp);
				
				temp = beforeParaSpace;
				temp = (temp << 4) | afterParaSpace;
				outputStream.write(temp);//写入段前段后间距
				
				outputStream.write(firstRowIndent);//写入首行缩进
				
				temp = leftIndent;
				if(temp < 0) {
					temp = 128 - leftIndent;
				}
				outputStream.write(temp);//写入左缩进
				
				temp = rightIndent;
				if(temp < 0) {
					temp = 128 - rightIndent;
				}
				outputStream.write(temp);//写入右缩进
				
				paraFirst = false;
			} catch (IOException e) {
				e.printStackTrace();
				throw new ParseException("DocumentCoder addParagraph 首行缩进不存在负值的情况 " +
						"" + e.getMessage());
			}
		} else {
			if (preAlignStyle != alignStyle
					|| preLineSpaceType != lineSpaceType
					|| preLineSpace != lineSpace
					|| preBeforeParaSpace != beforeParaSpace
					|| preAfterParaSpace != afterParaSpace) {
				head |= 0x08;//总变化为1  **** 1***
			}

			if (preFirstRowIndent != firstRowIndent) {
				head |= 0x09; //总变化为1  **** 1**1
				rowIndentChange = true;
			}
			if (preLeftIndent != leftIndent) {
				head |= 0x0C; //总变化为1  **** 11**
				leftIndentChange = true;
			}
			if (preRightIndent != rightIndent) {
				head |= 0x0A; //总变化为1  **** 1*1*
				rightIndentChange = true;
			}
			
			try {
				outputStream.write(head);
				
				if((head & 0x08) == 0x08 ){//如果总变化为1
					
					temp = alignStyle;
					temp = (temp << 2) | lineSpaceType;
					temp = (temp << 5) | lineSpace;
					
					outputStream.write(temp);//写入对齐方式，行距单位，行距值等信息
					
					temp = beforeParaSpace;
					temp = (temp << 4) | afterParaSpace;
					outputStream.write(temp);//写入段前段后间距
					
					if(rowIndentChange == true) {
						outputStream.write(firstRowIndent);//写入首行缩进
					}
					
					if(leftIndentChange == true) {
						temp = leftIndent;
						if(temp < 0) {
							temp = 128 - leftIndent;
						}
						outputStream.write(temp);//写入左缩进
					}
					
					if(rightIndentChange == true) {
						temp = rightIndent;
						if(temp < 0) {
							temp = 128 - rightIndent;
						}
						outputStream.write(temp);//写入右缩进
					}
				}
				
				preFirstRowIndent = firstRowIndent;
				preLeftIndent = leftIndent;
				preRightIndent = rightIndent;
				preAlignStyle = alignStyle;
				preLineSpaceType = lineSpaceType;
				preLineSpace = lineSpace;
				preBeforeParaSpace = beforeParaSpace;
				preAfterParaSpace = afterParaSpace;
					
			} catch (IOException e) {
				e.printStackTrace();
				throw new ParseException("DocumentCoder addParagraph  " +
						"" + e.getMessage());
			}
		}
	}

	@Override
	public void endCharUnit(short charactersize) throws ParseException {
		// TODO Auto-generated method stub
		
	}

}
