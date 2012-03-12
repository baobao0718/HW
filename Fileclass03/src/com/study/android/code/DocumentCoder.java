
package com.study.android.code;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.study.android.ui.HwFunctionWindow;

import android.util.Log;


/**
 * �ļ������������ڴ��е����ݴ��뵽�ļ���
 *@author ���˸�
 *@version �������� 2008-7-26 ����03:46:08
 */
public class DocumentCoder implements DataHandler{
	/** �ļ�������� */
	private OutputStream outputStream = null;
	
	private byte color = -1;
	private byte height = -1;
	private byte width = -1;
	
	/** ���Ƶ�Ԫ���� 0x80 1*** **** */
	public static final int CTRL_MASK = 0x80;
	
	/** �ַ���Ԫ���� 0x40 01** **** */
	public static final int CHAR_MASK = 0x40;
	
	/** ���Ƶ�Ԫ�������� 0xF0 1111 **** */
	public static final int CTRL_TYPE_MASK = 0xF0;
	
	/** �ո�Ԫ 0x90 1001 **** */
	public static final int SPACE = 0x90;
	
	/** �ε�Ԫ 0xB0 1011 **** */
	public static final int PARA = 0xB0;
	
	public static final int CHAR_C = 0x20;  // �ַ���ɫ����
	public static final int CHAR_H = 0x10;  // �ַ���ʽ�߶�����
	public static final int CHAR_W = 0x08;  // �ַ��ʻ���׼�������
	public static final int CHAR_S = 0x07;  // �ַ���ʽ����
	
	public static final int CHAR_COLOR = 0x20;  // �ַ���ɫ����
	public static final int CHAR_HEIGHT = 0x10;  // �ַ���ʽ�߶�����
	public static final int CHAR_WIDTH = 0x08;  // �ַ��ʻ���׼�������
	
	/** ͼ������ 0x48 */
	public static final int IMAGE_STYLE = 0x48;  // ͼ������
	public static final int CALLIGRAPHY_STYLE = 0x50;//�鷨����
	
	/** �ڵ�Ԫ 0xA0 1010 **** */
	public static final int SECTION_SYTLE = 0xA0;
	
	/** �ʻ�������־ 0x8000 1000 0000 0000 0000*/
	public static final int STROKE_END = 0x8000;  
	
	/** �ַ�������־ 0x8080 1000 0000 1000 0000*/
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
	 * ������ɫ��
	 * @param colors ��ɫ����
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
	 * ��ʼ�ַ���Ԫ
	 * @param style ��ʽ
	 * @param color ��ɫ
	 * @param formatHeight �����ʽ�߶�
	 * @param strokeWidth �ʻ���׼���
	 * @throws ParseException
	 */
	public void startCharUnit(byte style, byte color, byte formatHeight,
			byte strokeWidth, short charHeight) throws ParseException{
		//int dataHead;//�ַ���Ԫͷ��00CHWSSS  style Ĭ��Ϊ0
		boolean colorFlag = true;
		boolean heightFlag = true;
		boolean widthFlag = true;
		try {
		/*	if (this.color == -1 && this.height == -1 && this.width == -1) {// ��ʱ˵���ǵ�һ���ַ���Ԫ
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
			dataHead = style;//0000 0***, ��λ��Ϊ0��Ҳ���Ǳ�ʶ���ַ����ֵ�Ԫ
			if (this.color != color){// �ж��Ƿ���������ɫ
				dataHead = dataHead | CHAR_COLOR;
				colorFlag = false;
				this.color = color;
//				System.err.println("\ncolor is changed");
			}
			
			if(this.height != formatHeight){//�ж��Ƿ������˻�׼�߶�
				dataHead = dataHead | CHAR_HEIGHT;
				heightFlag = false;
				this.height = formatHeight;
			}
			
			if(this.width != strokeWidth){//�ж��Ƿ������˻�׼�ʻ����
				dataHead = dataHead | CHAR_WIDTH;
				widthFlag = false;
				this.width = strokeWidth;
//			System.err.println("\nstrokeWidth is changed");
			}*/
			//char dataHead=(char)style;
			char dataHead=0x38;
			outputStream.write(dataHead);
//			System.out.println("\n dataHead is : " + dataHead);
			
			/*if(colorFlag == false)//д���µ���ɫ��Ϣ
				outputStream.write(color);
			
			if(heightFlag == false)//д���µ�����߶���Ϣ
				outputStream.write(formatHeight);
			
			if(widthFlag == false)//д���µıʻ������Ϣ
				outputStream.write(strokeWidth);*/
			
			outputStream.write((charHeight & 0xFF00) >> 8);
			outputStream.write(charHeight & 0xFF);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder startCharUnit " + e.getMessage());
		}
	}
	
	/**
	 * ��ӵ㣬��end = trueʱ��<b>x��y��Ч</b>
	 * @param x
	 * @param y
	 * @param end �Ƿ������
	 * @throws ParseException
	 */
	public void addPoint(short x, short y, boolean end) throws ParseException {
		int tempX = 0, tempY = 0;
		int mid;
		
		try {
			if(this.pointX == -1 && this.pointY == -1) {// �ʻ��ĵ�һ����
				
				this.pointX = x;
				this.pointY = y;

				prePointX =  x;
				prePointY =  y;

				tempX = (x & 0xFF0) >> 4;
				tempY = y & 0xFF;
				mid = ((x & 0xF) << 4) | ((y & 0xF00) >> 8);
				
				outputStream.write(tempX);					//ע�⣬write(int) ����д��һ���ֽڷ�Χ�ڵ�������2��8�η���С��������ʱ��ֱ��ʹ��read()���ɶ�������������256�ķ�Χ�����������Ϊ����ֵ��ȥ256����
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
					outputStream.write((STROKE_END & 0xFF00) >> 8);// д��ʻ�������־
					outputStream.write(STROKE_END & 0x00FF);// д��ʻ�������־
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
	 * �����ַ���Ԫ
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
	 * ��ʼ�㵥Ԫ��һ��Ƕ���С�ĵ�
	 * @param roundStyle ���Ʒ�ʽ
	 * @param height �߶�
	 * @param width ���
	 * @throws ParseException
	 */
	public void startLayerUnit(byte roundStyle, int height, int width) throws ParseException{
		
	}
	
	/**
	 * �����㵥Ԫ
	 * @throws ParseException
	 */
	public void endLayerUnit() throws ParseException{
		
	}
	
	/**
	 * ��ʼͼ�ε�Ԫ
	 * @param roundStyle ���Ʒ�ʽ
	 * @param height �߶�
	 * @param width ���
	 * @throws ParseException
	 * @see ��ӿ�ʼ����ʹ�� addPoint(short x, short y, byte width, byte color)
	 * @see �����ͨ����ʹ�� addPoint(short x, short y, boolean end)
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
			outputStream.write(tempHeight);//�߿�ռ�������ֽ�
			outputStream.write(middle);
			outputStream.write(tempWidth);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder startImageUnit " + e.getMessage());
		}
	}
	
	/**
	 * ��ӿ�ʼ�㣬������ͼ�ε�Ԫ���鷨��Ԫ
	 * @param x
	 * @param y
	 * @param width ����
	 * @param color ��ɫ
	 * @throws ParseException
	 * @see �����ͨ����ʹ�� addPoint(short x, short y, boolean end)
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
	//			System.err.println(" color in Writetofile��" + color);
	//		}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder addPoint(short x, short y, " +
					"byte width, byte color) " + e.getMessage());
		}
	}
	
	/**
	 * ����ͼ�ε�Ԫ
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
	 * ���λͼ��Ԫ
	 * @param roundStyle ���Ʒ�ʽ
	 * @param height �߶�
	 * @param width ���
	 * @param pixels λͼ����
	 * @throws ParseException
	 */
	public void addBitmapUnit(byte roundStyle, int height, int width, int[] pixels) throws ParseException{
		
	}
	
	/**
	 * ��ӿո�
	 * @param width �ո���
	 * @throws ParseException
	 */
	public void addSpaceUnit(short width) throws ParseException {
		int space = 0x09;//�ո��־
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
	 * ��ӻ���
	 * @param width ���е�Ԫ��ȣ�Ϊ0��
	 * @throws ParseException
	 */
	public void addEnterUnit(short width) throws ParseException {
		int space = 0xB0;//���б�־

		byte target;
        target= (byte)(space& 0xff);//���λ
		try {
			outputStream.write(target);			//target �պþ��� 10110000��0XB0 ����Ϊ�����ͣ����ĸ��ֽڣ������һ���ֽڣ�
		} catch (IOException e) {
			e.printStackTrace();
			throw new ParseException("DocumentCoder addSpaceUnit " + e.getMessage());
		}
	}
	
	
	/**
	 * ��ʼҳ���ڣ����Ƶ�Ԫ�������������
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
	 * ����ֽ�Ŵ�С
	 * @param height ֽ�Ÿ߶�
	 * @param width ֽ�ſ��
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
	 * ����ҳ�߾�
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
	 * ����ҳ��
	 * @param position ҳ��λ��
	 * @param isShowSum �Ƿ���ʾ��ҳ��
	 * @param style ��ʽ
	 * @param startNumber ��ʼҳ��
	 * @throws ParseException
	 */
	public void setPageNumber(byte position, byte isShowSum, byte style, short startNumber) throws ParseException{
		
	}
	
	/**
	 * ���ñ�������ǰ�汾δ����
	 * @throws ParseException
	 */
	public void setBackGround() throws ParseException{
		
	}
	
	/**
	 * �����ݺ᷽��
	 * @param orient true�ݣ�false��
	 * @throws ParseException
	 */
	public void setOrient(boolean orient) throws ParseException{
		
	}
	
	/**
	 * ����ҳ�߾�ĵ�λ
	 * @param ut
	 * @throws ParseException
	 */
	public void setUnitType(byte ut) throws ParseException{
		
	}
	
	/**
	 * ���÷�����
	 * @param snaking
	 * @throws ParseException
	 */
	public void setSnakingNumber(byte snaking) throws ParseException{
		
	}
	
	/**
	 * ��ʼҳü
	 * @throws ParseException
	 */
	public void startPageHeader() throws ParseException{
		
	}
	
	/**
	 * ����ҳü
	 * @throws ParseException
	 */
	public void endPageHeader() throws ParseException{
		
	}
	
	/**
	 * ��ʼҳ��
	 * @throws ParseException
	 */
	public void startPageFooter() throws ParseException{
		
	}
	
	/**
	 * ����ҳ��
	 * @throws ParseException
	 */
	public void endPageFooter() throws ParseException{
		
	}
	
	/**
	 * ����ҳ���ڣ����Ƶ�Ԫ
	 * @throws ParseException
	 */
	public void endPageUnit() throws ParseException{
		
	}
	
	/**
	 * TODO ��ʼ�ε�Ԫ��֮�����Ƿ����öε�����
	 * @throws ParseException
	 */
	public void startParagraph() throws ParseException {
		
	}
	
	
	public void setcolor(byte num) throws ParseException
	{
		this.color=num;
	}
	/**
	 * ��Ӷε�Ԫ��־
	 * @param firstRowIndent ��������
	 * @param leftIndent ������
	 * @param rightIndent ������
	 * @param alignStyle ���뷽ʽ
	 * @param lineSpaceType �о൥λ
	 * @param lineSpace �о�ֵ
	 * @param beforeParaSpace ��ǰ���
	 * @param afterParaSpace �κ���
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
			preFirstRowIndent = firstRowIndent;//�������������ڸ�ֵ�����
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
				
				outputStream.write(temp);//д����뷽ʽ���о൥λ���о�ֵ����Ϣ
//				System.err.println("\n�� ********* �� " + Integer.toHexString(temp) + "
//				temp : " + temp);
				
				temp = beforeParaSpace;
				temp = (temp << 4) | afterParaSpace;
				outputStream.write(temp);//д���ǰ�κ���
				
				outputStream.write(firstRowIndent);//д����������
				
				temp = leftIndent;
				if(temp < 0) {
					temp = 128 - leftIndent;
				}
				outputStream.write(temp);//д��������
				
				temp = rightIndent;
				if(temp < 0) {
					temp = 128 - rightIndent;
				}
				outputStream.write(temp);//д��������
				
				paraFirst = false;
			} catch (IOException e) {
				e.printStackTrace();
				throw new ParseException("DocumentCoder addParagraph �������������ڸ�ֵ����� " +
						"" + e.getMessage());
			}
		} else {
			if (preAlignStyle != alignStyle
					|| preLineSpaceType != lineSpaceType
					|| preLineSpace != lineSpace
					|| preBeforeParaSpace != beforeParaSpace
					|| preAfterParaSpace != afterParaSpace) {
				head |= 0x08;//�ܱ仯Ϊ1  **** 1***
			}

			if (preFirstRowIndent != firstRowIndent) {
				head |= 0x09; //�ܱ仯Ϊ1  **** 1**1
				rowIndentChange = true;
			}
			if (preLeftIndent != leftIndent) {
				head |= 0x0C; //�ܱ仯Ϊ1  **** 11**
				leftIndentChange = true;
			}
			if (preRightIndent != rightIndent) {
				head |= 0x0A; //�ܱ仯Ϊ1  **** 1*1*
				rightIndentChange = true;
			}
			
			try {
				outputStream.write(head);
				
				if((head & 0x08) == 0x08 ){//����ܱ仯Ϊ1
					
					temp = alignStyle;
					temp = (temp << 2) | lineSpaceType;
					temp = (temp << 5) | lineSpace;
					
					outputStream.write(temp);//д����뷽ʽ���о൥λ���о�ֵ����Ϣ
					
					temp = beforeParaSpace;
					temp = (temp << 4) | afterParaSpace;
					outputStream.write(temp);//д���ǰ�κ���
					
					if(rowIndentChange == true) {
						outputStream.write(firstRowIndent);//д����������
					}
					
					if(leftIndentChange == true) {
						temp = leftIndent;
						if(temp < 0) {
							temp = 128 - leftIndent;
						}
						outputStream.write(temp);//д��������
					}
					
					if(rightIndentChange == true) {
						temp = rightIndent;
						if(temp < 0) {
							temp = 128 - rightIndent;
						}
						outputStream.write(temp);//д��������
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
