
package com.study.android.code;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import com.study.android.basicData.DataType;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.data.ListTable;
import com.study.android.retrieval.Transform;
import com.study.android.tool.MyLog;
import com.study.android.ui.HwFunctionWindow;

/*
 * 文件解码器，拥有一个实现了DataHandler的内存编码器
 *@author 付兴刚
 *@version 创建日期 2008-7-28 上午09:24:06
 */
public class DocumentDecoder {
	
	/** 控制单元掩码 0x80 1*** **** */
	public static final int CTRL_MASK = 0x80;
	
	/** 字符单元掩码 0x40 01** **** */
	public static final int CHAR_MASK = 0x40;
	
	/** 非字符单元掩码 0x38 0011 1*** */
	public static final int NOT_CHAR_MASK = 0x38;
	
	/** 图像单元掩码 0x08 0000 1*** */
	public static final int IMAGE_MASK = 0x08;
	
	/** 层单元掩码 0x38 */
	public static final int LAY_MASK = 0x38;
	
	/** 书法单元掩码 0x10 0001 **** */
	public static final int CALLIGRAPHY_MASK = 0x10;
	
	/** 控制单元类型掩码 0xF0 1111 **** */
	public static final int CTRL_TYPE_MASK = 0xF0;
	
	/** 空格单元 0x90 1001 **** */
	public static final int SPACE = 0x90;
	
	/** 页单元 0xA0 1010 **** */
	public static final int PAGE = 0xA0;
	
	/** 段单元 0xB0 1011 **** */
	public static final int PARA = 0xB0;
	
	public static final int CHAR_C = 0x20;  // 字符颜色掩码
	public static final int CHAR_H = 0x10;  // 字符格式高度掩码
	public static final int CHAR_W = 0x08;  // 字符笔画基准宽度掩码
	
	/**  字符样式掩码 0x07 0000 0111*/
	public static final int CHAR_S = 0x07;  // 字符样式掩码
	
	/**  图片环绕方式掩码 0x07 0000 0111*/
	public static final int ROUND_S = 0X07; // 图片环绕方式掩码
	
	/** 取字节的第一位的掩码 0x80 */
	public static final int COLOR_MASK = 0x80;//取字节的第一位的掩码
	
	/** 图像 书法 单元的笔画宽度掩码 0x70 */
	public static final int STROKE_WIDTH_MASK = 0x70;
	
	/** 取字节的后四位位的掩码 0x0F */
	public static final int FIVE_EIGHT_Bit = 0x0F;
	
	/** 取字节的前六位位的掩码 0xFC */
	public static final int ONE_SIX_Bit = 0xFC;
	
	/** 取字节的后两位位的掩码 0x03 */
	public static final int SEVEN_EIGHT_Bit = 0x03;
	
	public static final int STROKE_END = 0x8000;  // 笔画结束标志
	public static final int CHAR_END = 0x8080;  // 字符结束标志
	
	public int preFirstRowIndent = 0, preLeftIndent = 0, preRightIndent = 0;
	
	public int preAlignStyle = 0, preLineSpaceType = 0, preLineSpace = 0;
	
	public int preBeforeParaSpace = 0, preAfterParaSpace =0 ;
	
	public int preColorIndex = 0, preStrokeWidth = 0, preFormatHeight = 0;
	
	public static int readbyte=0;
	
	/** 内存编码器，实现了DataHandler */
	private DataHandler memoryCoder;
	
	/** 文件输入流，将文件数据读入到此流中，调用 <b>memoryCoder</b> 的方法将数据解码到内存中 */
	private DataInputStream inputStream;
	
	public DocumentDecoder(InputStream inputStream) {
		this.inputStream = new DataInputStream(inputStream);
		this.memoryCoder = new MemoryCoder();
	}
	
	/** 头文件的相关信息 */
	public void headDecoder() {
		try {
			byte[] sectionnum = new byte[4];
			byte[] titlenum=new byte[2];
			byte[] creatfiletime=new byte[25];//zhuxiaoqing 2011.12.27
			inputStream.skip(12);          //头文件占用14个字节
			inputStream.read(sectionnum);
			//inputStream.skip(31);
			inputStream.skip(4);
			inputStream.read(creatfiletime);
			String oo=new String(creatfiletime);
			
			
			if(ListTable.xiufu==true)
			{
				String oo1=oo.substring(0, 4);
				oo1=oo1+oo.substring(5, 7);
				oo1=oo1+oo.substring(8, 10);
				ListTable.datetmp=Integer.parseInt(oo1); 
				
			}
			System.out.println("zhuxiaoqingxxxxxxxxx######xxxx"+"rqi"+ListTable.datetmp);
			inputStream.read(titlenum);
			ListTable.titlenumber = titlenum[0] & 0xFF;    //zhuxiaoqing 2011.11.17
			ListTable.titlenumber |= ((titlenum[1] << 8) & 0xFF00);  
			//ListTable.titlenumber=
//			System.out.println("**titlenumber"+ListTable.titlenumber);
		//	Log.v("sectionnum[]",""+sectionnum[0]);
		//	Log.v("sectionnum[]",""+sectionnum[1]);
		//	Log.v("sectionnum[]",""+sectionnum[2]);
		//	Log.v("sectionnum[]",""+sectionnum[3]);
			int targets= (sectionnum[0] & 0xff)
	        | ((sectionnum[1]<<8) & 0xff00)   // | 表示按位或
	        | ((sectionnum[2]<<24)>>>8)
	        | (sectionnum[3]<<24);
			ListTable.Sectionnum=Transform.BytestoInt(sectionnum);  //到这里，前面14个字节读完
		//	Log.v("READING--->","ListTable.Sectionnum  "+ListTable.Sectionnum);
		//	Log.v("READING--->","targets  "+targets);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 数据解码 */
	public void dataDecoder() {
		try {
//			System.out.println("**dataDecoder");
		/*	byte[] temp = new byte[4];	                              //4个字节（32位）才能表示一个颜色
			int colorTemp = 0;
			int colorNum = 0;
			colorNum = inputStream.read();
		//	Log.v("DEBUG--colorNum","NUM="+colorNum);
			int[] colors =new int [colorNum];
			for(int i=0; i < colorNum; i++) {
				inputStream.read(temp);
				colorTemp = colorTemp | (temp[0]&255);
				colorTemp = (colorTemp << 8) | (temp[1]&255);
				colorTemp = (colorTemp << 8) | (temp[2]&255);
				colorTemp = (colorTemp << 8) | (temp[3]&255);
				colors[i] = colorTemp;
				colorTemp = 0;
			}
			memoryCoder.setColorTable(colors);// 搞定颜色表
			*/
		/*	for (int i = 0; i != 5; i++)//zhuxiaoqing 2011.06.23
			{
				ControlUnit controlUnit = new ControlUnit(
						DataType.TYPE_CTRL_SPACE, CharFormat
								.getDefaultCharFormat());
				ListTable.globalIndexTable.add(
						ListTable.temppositionofaddcharunit, controlUnit);
				
				ListTable.temppositionofaddcharunit++;
			}*/
			int currentByte = inputStream.read();
			int nextByte = 0;
			int number = 0;
			int ik=0;
//			Log.v("DEBUG------","CURRENT BYTE :"+currentByte );
			while(currentByte != -1) {
//				System.err.println("\n最外维的currentByte : " + Integer.toHexString(currentByte)+ "\n");
				
				if(ik==ListTable.titlenumber/*&&ListTable.xiufu==true*/&&ListTable.ipdfile==true/*&&ListTable.isfirst==true*/)//zhuxiaoqing 2011.11.17
				{
					memoryCoder.addEnterUnit((short)32);
//					System.out.println("titlenumber"+ListTable.titlenumber);
//					System.out.println("***herehuiche11!!!");
					ListTable.temppositionofaddcharunit++;
					
				}
				ik++;
				if ((currentByte & CTRL_MASK) == 0) {// 非控制单元
//					System.out.println("**zifudanyuan "+ik);
					
					if ((currentByte & CHAR_MASK) == 0) {// 字符单元
						//	Log.v("DEBUG---->","dataType111:TYPE_CHAR");
							charUnitDecoder(currentByte, inputStream);
							ListTable.temppositionofaddcharunit++;
					} else if ((currentByte & LAY_MASK) == 0) {// 层单元
							System.err.println("层单元");
					} else if ((currentByte & IMAGE_MASK) != 0) {// 图像单元
							imageUnitDecoder(currentByte, inputStream);
							ListTable.temppositionofaddcharunit++;
					} 

					currentByte = inputStream.read();
					if(currentByte==-1&&ListTable.ipdfile==true&&ik==ListTable.titlenumber)
					{
						memoryCoder.addEnterUnit((short)32);
//						System.out.println("^^^%%%here");
						ListTable.temppositionofaddcharunit++;
					}

				} else {// 控制单元
//					System.out.println("**kongzhidanyuan");
					int high = 0;
					int low = 0;
					int width = 0;
					switch (currentByte & CTRL_TYPE_MASK) {
					case SPACE:// 空格单元
//						System.out.println("***herekongge!!!");
						high = (currentByte << 28)>>24;
						nextByte = inputStream.read();
						width = high | nextByte;
						memoryCoder.addSpaceUnit((short)width);
						ListTable.temppositionofaddcharunit++;
						break;

				//	case PAGE:// 节控制单元
				//		sectionDecoder(currentByte, inputStream);
				//		break;

					case PARA:// 段控制单元
						//paraDecoder(currentByte, inputStream);
						memoryCoder.addEnterUnit((short)32);
//						System.out.println("***herehuiche!!!");
						ListTable.temppositionofaddcharunit++;
						break;
						
					default : 
//						Log.v("DEBUG-->","there are some control unit mistake in DocumentDecoder.dataDecoder()");
						break;
					}
					
					try {
						currentByte = inputStream.read();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				number++;
			}		
			//Log.v("DEBUG-->","WENJIANDUQUJIESHU,number"+number);
		} catch (ParseException e) {
			e.printStackTrace();
			MyLog.logPrint(e.getLocalizedMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * 下面这部分是我为了在索引文件中能够存储标题而对上面函数的修改，用作标题部分的存储
	 * 
	 * 
	 * 
	 */
	
	/** 数据解码 */
	public void dataDecoderForTitle(int numofcharacter) {
		try {
			int currentByte = 0;
			if(numofcharacter!=0)
			{
				currentByte = inputStream.read();
			}
			int nextByte = 0;
			int number = 0;
//			System.out.println("**here1");
	//		Log.v("DEBUG------","CURRENT BYTE :"+currentByte );
			//Log.v("DEBUG------","number :"+number +" numofcharacter"+numofcharacter );							//因为存粗文件里会有一个节控制单元，最开始位置
			while(currentByte != -1&&number<numofcharacter) {
			//while(currentByte != -1) {	
				if ((currentByte & CTRL_MASK) == 0) {// 非控制单元
					if ((currentByte & CHAR_MASK) == 0) {// 字符单元
						//	Log.v("DEBUG--->","CHAR-MASK");
//						System.out.println("**here2");
							charUnitDecoderForTitle(currentByte, inputStream,(short)40);
					} else if ((currentByte & LAY_MASK) == 0) {// 层单元
							System.err.println("层单元");
					} else if ((currentByte & IMAGE_MASK) != 0) {// 图像单元
							imageUnitDecoder(currentByte, inputStream);
					} 
					number++;
					if(number<numofcharacter)
					{
						currentByte = inputStream.read();
					}
				} else {// 控制单元
					int high = 0;
					int low = 0;
					int width = 0;
					switch (currentByte & CTRL_TYPE_MASK) {
					case SPACE:// 空格单元
						high = (currentByte << 28)>>24;
						nextByte = inputStream.read();
						width = high | nextByte;
						memoryCoder.addSpaceUnit((short)width);
						break;

					//case PAGE:// 节控制单元
					//	sectionDecoder(currentByte, inputStream);
					//	break;

					case PARA:// 段控制单元
						memoryCoder.addEnterUnit((short)40);
						break;
						
					default : 
//						Log.v("DEBUG-->","there are some control unit mistake in DocumentDecoder.dataDecoder()");
						break;
					}
					number++;
						currentByte = inputStream.read();
				}
				
				//if(number<=numofcharacter)
				//   currentByte = inputStream.read();
			}		
			//Log.v("DEBUG-->","WENJIANDUQUJIESHU,number"+number);
		} catch (ParseException e) {
			e.printStackTrace();
			MyLog.logPrint(e.getLocalizedMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *TODO
	 *
	 *@param format
	 *@param din
	 */
	public void charUnitDecoder(int format, DataInputStream din) {
		boolean C,H,W,S;
		C = H = W = S = true; // 默认重新定义
		if((format & CHAR_C) == 0)C = false;
		if((format & CHAR_H) == 0)H = false;
		if((format & CHAR_W) == 0)W = false;
		byte style = (byte)(format & CHAR_S);
	
		int colorIndex = 0;// 颜色索引
		int formatHeight = 0;// 字体高度
		int strokeWidth = 0;// 笔画的基准宽度
		int pointX = -1;
		int pointY = -1;
		
		short charHeight = 0;
		boolean startPoint = true;
		int x = 0;
		int y = 0;
		int mid = 0;
		boolean end = false;
		
		int tempX = 0;
		int tempY = 0;	
		int temp = 0;
		int nextByte = 0;
		int nextNextByte = 0;
//		System.err.printf("\n字符单元的第一个字节 : %x\n" ,format);
		                                                           
		try {
			/*if (C) {
				colorIndex = inputStream.read();
				preColorIndex = colorIndex;
				//Log.v("DEBUG-->","colorIndex C: " + colorIndex);
				
			} else {
				colorIndex = preColorIndex;
			}
			
			if (H) {
				formatHeight = inputStream.read();
				preFormatHeight = formatHeight;
			} else {
				formatHeight = preFormatHeight;
			}
			
			if (W) {
				strokeWidth = inputStream.read();
				preStrokeWidth = strokeWidth;
			} else {
				strokeWidth = preStrokeWidth;
			}*/
			
			int high = din.read();
			int low = din.read();
			//Log.v("DEBUG--","high"+high+","+"low"+low+"\n");
			charHeight = (short) ((high << 8) | low);
			String value = "";

		//	Log.i("DEBUG-->","strokewidth  in DoucumentDecoder  "+strokeWidth);
			memoryCoder.startCharUnit((byte) style, (byte) (colorIndex),         //从文件读入数据到内存，先文件解码，那为什么出现还有内存编码呢?
							(byte) formatHeight, (byte) strokeWidth,
							charHeight);
			while (true) {
				if (startPoint == true) {
					// 读取笔画第一点
					x = din.read();         //一个点X即为一个字节
					mid = din.read();
					
					temp = (x << 8) | mid;
					if (temp == CHAR_END)
						break;
					y = din.read();
					x = (x << 4) | ((mid & 0xF0) >> 4);
					y = ((mid & 0xF) << 8) | y;
					startPoint = false;
					pointX = x;
					pointY = y;
					end = false;
					memoryCoder.addPoint((short) x, (short) y, end);
					tempX = din.read();
					tempY = din.read();
					temp = (tempX << 8) | tempY;
					String positio="";
//					positio="Firstpoint of Strokes x :"+x+"Firstpoint of Strokes y:"+y+"\n";
//					System.err.println(" temp : " + Integer.toHexString(temp) + " tempX : " + 
//							Integer.toHexString(tempX) + "  tempY : " + Integer.toHexString(tempY)); //toHexString转换为16进制
				} else {
					while (temp != STROKE_END) {
						if (tempX > 128)
							tempX = 128 - tempX;//zhuxiaoqing 2011.06.14
						if (tempY > 128)
							tempY = 128 - tempY;
						
						//if (tempX > 165)					//把128调到165是权宜之计，因为毕竟在笔画时候后面的点的坐标要更倾向于“非常大的顺差！！！”，前面大于后面很多的情况较少！！
							//tempX = 165 - tempX;
						//if (tempY > 165)
							//tempY = 165 - tempY;
						
						x = pointX + tempX;
						y = pointY + tempY;

						nextByte = din.read();           
						nextNextByte = din.read();

						temp = (nextByte << 8) | nextNextByte;
						
						if (temp == STROKE_END) {
							end = true;
							startPoint = true;
//							System.err.printf("笔画的结束点 x : %x y: %x\n", x, y);
							memoryCoder.addPoint((short) x, (short) y, end);
							break;
						} else {
							tempX = nextByte;
							tempY = nextNextByte;
							pointX = x;
							pointY = y;
							end = false;
//							System.err.printf("正常添加点 x : %x y: %x\n", x, y);
							memoryCoder.addPoint((short) x, (short) y, end);
						}
					}
				}
			}
//			System.err.println("字符单元的最后一个字节 ： " +Integer.toHexString(temp) );
			memoryCoder.endCharUnit();//当前字符单元结束
		} catch (Exception e) {
			MyLog.logPrint(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	
	//为了索引的标题读出而建立
	
	public void charUnitDecoderForTitle(int format, DataInputStream din,short charactersize) {
		boolean C,H,W,S;
		C = H = W = S = true; // 默认重新定义
		if((format & CHAR_C) == 0)C = false;
		if((format & CHAR_H) == 0)H = false;
		if((format & CHAR_W) == 0)W = false;
		byte style = (byte)(format & CHAR_S);
	
		int colorIndex = 0;// 颜色索引
		int formatHeight = 0;// 字体高度
		int strokeWidth = 0;// 笔画的基准宽度
		int pointX = -1;
		int pointY = -1;
		
		short charHeight = 0;
		boolean startPoint = true;
		int x = 0;
		int y = 0;
		int mid = 0;
		boolean end = false;
		
		int tempX = 0;
		int tempY = 0;	
		int temp = 0;
		int nextByte = 0;
		int nextNextByte = 0;
//		System.err.printf("\n字符单元的第一个字节 : %x\n" ,format);
		                                                           
		try {
			/*if (C) {
				colorIndex = inputStream.read();
				preColorIndex = colorIndex;
				//Log.v("DEBUG-->","colorIndex C: " + colorIndex);
				
			} else {
				colorIndex = preColorIndex;
			}
			
			if (H) {
				formatHeight = inputStream.read();
				preFormatHeight = formatHeight;
			} else {
				formatHeight = preFormatHeight;
			}
			
			if (W) {
				strokeWidth = inputStream.read();
				preStrokeWidth = strokeWidth;
			} else {
				strokeWidth = preStrokeWidth;
			}*/
			
			int high = din.read();
			int low = din.read();
			//Log.v("DEBUG--","high"+high+","+"low"+low+"\n");
			charHeight = (short) ((high << 8) | low);
			String value = "";

	//		Log.i("DEBUG-->","strokewidth  in DoucumentDecoder  "+strokeWidth);
			memoryCoder.startCharUnit((byte) style, (byte) (colorIndex),         //从文件读入数据到内存，先文件解码，那为什么出现还有内存编码呢?
							(byte) formatHeight, (byte) strokeWidth,
							charHeight);
			while (true) {
				if (startPoint == true) {
					// 读取笔画第一点
					x = din.read();         //一个点X即为一个字节
					mid = din.read();
					
					temp = (x << 8) | mid;
					if (temp == CHAR_END)
						break;
					y = din.read();
					x = (x << 4) | ((mid & 0xF0) >> 4);
					y = ((mid & 0xF) << 8) | y;
					startPoint = false;
					pointX = x;
					pointY = y;
					end = false;
					memoryCoder.addPoint((short) x, (short) y, end);
					tempX = din.read();
					tempY = din.read();
					temp = (tempX << 8) | tempY;
					String positio="";
//					positio="Firstpoint of Strokes x :"+x+"Firstpoint of Strokes y:"+y+"\n";
//					System.err.println(" temp : " + Integer.toHexString(temp) + " tempX : " + 
//							Integer.toHexString(tempX) + "  tempY : " + Integer.toHexString(tempY)); //toHexString转换为16进制
				} else {
					while (temp != STROKE_END) {
						if (tempX > 128)
							tempX = 128 - tempX;//zhuxiaoqing 2011.06.14
						if (tempY > 128)
							tempY = 128 - tempY;
						
						//if (tempX > 165)					//把128调到165是权宜之计，因为毕竟在笔画时候后面的点的坐标要更倾向于“非常大的顺差！！！”，前面大于后面很多的情况较少！！
							//tempX = 165 - tempX;
						//if (tempY > 165)
							//tempY = 165 - tempY;//zhuxiaoqing 2011.06.14
						
						x = pointX + tempX;
						y = pointY + tempY;

						nextByte = din.read();           
						nextNextByte = din.read();

						temp = (nextByte << 8) | nextNextByte;
						
						if (temp == STROKE_END) {
							end = true;
							startPoint = true;
//							System.err.printf("笔画的结束点 x : %x y: %x\n", x, y);
							memoryCoder.addPoint((short) x, (short) y, end);
							break;
						} else {
							tempX = nextByte;
							tempY = nextNextByte;
							pointX = x;
							pointY = y;
							end = false;
//							System.err.printf("正常添加点 x : %x y: %x\n", x, y);
							memoryCoder.addPoint((short) x, (short) y, end);
						}
					}
				}
			}
//			System.err.println("字符单元的最后一个字节 ： " +Integer.toHexString(temp) );
			memoryCoder.endCharUnit(charactersize);		//当前字符单元结束,有参数的适用于索引标题读取时候
		} catch (Exception e) {
			MyLog.logPrint(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 *TODO
	 *
	 *@param format
	 *@param din
	 */
	public void imageUnitDecoder(int format, DataInputStream din){
		int lowX, highX;
		int lowY, highY;
		int currentByte = 0;
		int strokeWidth = 0;
		int colorIndex = 0;
		
		int nextByte, nextNextByte;
		int pointX = -1;
		int pointY = -1;
		
		int tempX = 0;
		int tempY = 0;
		int temp = 0;
		
		int x, y;
		boolean colorFlag = true;
		boolean startPoint = true;
		
		boolean end = false;
		
//		System.err.println("this is an image unit\n");
			
		int roundStyle = format & ROUND_S;// SIX_EIGHT_Bit = 0x07
		
		
//		HwFunctionWindow.colortable.clear();//zhuxiaoqing 2011.06.14
		
		
		
		try {
			int tempHeight = din.read();
			int middle = din.read();
			int tempWidth = din.read();
			int height = (tempHeight << 4) | (middle >> 4);
			int width = ((middle & 0x0F) << 8) | tempWidth;
			
			int red=0;
			int green=0;
			int blue=0;
			int alpha=0;
			
//			System.err.println("image height : " + height + "  width : "
//					+ width + "\n");
//			Log.i("DEBUG--->zhuxiaoqing du","zhuxiaoqing---");
			//float rate=1;
			
			memoryCoder.startImageUnit((byte) roundStyle, height, width);
			//HwFunctionWindow.colortable.clear();//zhuxiaoqing 2011.06.14
			while (true) {
//				Log.i("DEBUG--->zhuxiaoqing du","zhuxiaoqing");
				if (startPoint == true) {// 读取笔画的第一点
					strokeWidth = din.read();//2012.03.09 liqiang
					if(strokeWidth>20)
						break;//2012.03.09 liqiang 权宜之计 之后要改，图片笔画宽度之前没有写到文件里，要加上
					currentByte = din.read();
//					if ((currentByte & COLOR_MASK) == 0) {
//						colorFlag = false;
////						Log.i("DEBUG--->zhuxiaoqing du","zhuxiaoqing  colorFlag=false");
//				//		System.err.println("ZHEN SHI WU YU  ");
//					} else {
//						colorFlag = true;
//					}
					
//					strokeWidth = (currentByte & STROKE_WIDTH_MASK) >> 4;
					highX = (currentByte & FIVE_EIGHT_Bit) << 6;
					
					Log.e("DEBUG--->","STROKEWIDTH  IN Hwtrying memoryCoder.startimageunit "+strokeWidth);
					nextByte = din.read();

					temp = (currentByte << 8) | nextByte;

					if (temp == CHAR_END)
						break;

					lowX = (nextByte & ONE_SIX_Bit) >> 2;
					x = (short) (highX | lowX);

					highY = (nextByte & SEVEN_EIGHT_Bit) << 8;// 取该字节的最后两位
					lowY = din.read();
					y = (short) (highY | lowY);

	//				System.err.println("colorFlag :  " + colorFlag);
	//				System.err.println("colorIndex:  " + colorIndex);
//					Log.i("DEBUG--->zhuxiaoqing du","zhuxiaoqingzhelizheli");
			//		if (colorFlag == true) {
						//colorIndex = din.read();
						red=din.read();//zhuxiaoqing 2011.06.14
						green=din.read();
						blue=din.read();
						alpha=din.read();
						/*Log.i("DEBUG--->zhuxiaoqing du","alpha "+alpha);
						Log.i("DEBUG--->zhuxiaoqing du","red"+red);
						Log.i("DEBUG--->zhuxiaoqing du","green "+green);
						Log.i("DEBUG--->zhuxiaoqing du","blue "+blue);*/
						//byte[] temp1 = new byte[4];	                              //4个字节（32位）才能表示一个颜色
						int colorTemp = 0;
						int colorreally=0;
						
						//inputStream.read(temp1);
						colorTemp = colorTemp | (alpha&255);
						colorTemp = (colorTemp << 8) | (red&255);
						colorTemp = (colorTemp << 8) | (green&255);
						colorTemp = (colorTemp << 8) | (blue&255);
						colorreally = colorTemp;
//						Log.i("DEBUG--->zhuxiaoqing du","colorreally"+colorreally);
						//if(HwFunctionWindow.colortable.contains(colorreally)==false)
						//{
//							Log.i("DEBUG--->zhuxiaoqing du","jinru zengjiayanse xuanxiang");
							HwFunctionWindow.colortable.add(colorreally);
						//}
						
					/*	Log.i("DEBUG--->zhuxiaoqing du","alpha "+temp1[3]);
						Log.i("DEBUG--->zhuxiaoqing du","red"+temp1[0]);
						Log.i("DEBUG--->zhuxiaoqing du","green "+temp1[1]);
						Log.i("DEBUG--->zhuxiaoqing du","blue "+temp1[2]);*/
						//colortable.add(Color.rgb(0,0,255));    
						colorIndex= HwFunctionWindow.colortable.indexOf(colorreally);//这句有毛病因为找不到颜色的索引、、朱晓庆
//						Log.i("DEBUG--->zhuxiaoqing du","colorindex"+colorIndex);
						//colorFlag = false;
			//		}

	//				System.err.println(" x : " + x + "  Y : " + y);
					System.err.println();
//					memoryCoder.addPoint((short) x, (short) y,
//							(byte) strokeWidth, (byte) colorIndex);
					memoryCoder.addPoint((short) x, (short) y,
							(byte) strokeWidth, (byte) colorIndex);
//					memoryCoder.addPoint((short) x, (short) y,
//							(byte) ListTable.characterstrokewidth, (byte) colorIndex);
					pointX = x;
					pointY = y;
					startPoint = false;

					tempX = din.read();
					tempY = din.read();

					temp = (tempX << 8) | tempY;
				} else {
					while (temp != STROKE_END) {
						if (tempX > 128)					//这地方有bug，我的模拟器上出现了后一个坐标X与前一个坐标X之间的差值为：＋200（>(＋128)即可）的情况
							tempX = 128 - tempX;
						if (tempY > 128)
							tempY = 128 - tempY;
						//if (tempX > 165)					//把128调到165是权宜之计，因为毕竟在笔画时候后面的点的坐标要更倾向于“非常大的顺差！！！”，前面大于后面很多的情况较少！！
							//tempX = 165 - tempX;
						//if (tempY > 165)
							//tempY = 165 - tempY;
						x = pointX + tempX;
						y = pointY + tempY;

						nextByte = din.read();
						nextNextByte = din.read();

//						System.err.println(Integer.toHexString(nextByte) + "  hhe  " 
//								+ Integer.toHexString(nextNextByte));
						temp = (nextByte << 8) | nextNextByte;

						if (temp == STROKE_END) {
							end = true;
							startPoint = true;
							
							memoryCoder.addPoint((short) x, (short) y, end);
//							System.err.printf("笔画的结束点 x : %x y: %x\n", x, y);
//							System.err.println();
							break;
						} else {
							tempX = nextByte;
							tempY = nextNextByte;

							pointX = x;
							pointY = y;
							end = false;

					//		System.err.printf("正常添加点 x : %x y: %x\n", x, y);
							memoryCoder.addPoint((short) x, (short) y, end);
						}
					}
				}
			}
			
			memoryCoder.endImageUnit();
		} catch (ParseException e) {
			MyLog.logPrint(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *TODO
	 *
	 *@param format
	 *@param din
	 */
	public void sectionDecoder(int format, DataInputStream din){
//		System.err.println("working in sectionDecoder : ****** \n");
		int count = 0;
		boolean globalChange = false;
		byte pageStyle = 0;
		
//		System.err.println(Integer.toHexString(format));
		if ((format & 0x08) != 0) {
			globalChange = true;
		}
		
		pageStyle =(byte) (format & 0x07);
		
		int x, y;
		int up, down, right, left;
		int height, width;
		try {
			memoryCoder.startPageUnit(globalChange, pageStyle);
			
			if (globalChange == false) {
				memoryCoder.endPageUnit();
				return;
			}
			
			int pageProperty = din.read();
			count++;

			if (pageStyle == 0x07) {//自定义
				x = din.read();
				y = din.read();
				height = (x << 8) | y;
				
				x = din.read();
				y = din.read();
				width = (x << 8) | y;
				
				count += 4;
				
				memoryCoder.setPageSize((short)height, (short)width);
			}
			
			if((pageProperty & 0x80) != 0) {
				x = din.read();
				y = din.read();
				up = (x << 8) | y;
				
				x = din.read();
				y = din.read();
				down = (x << 8) | y;
				
				x = din.read();
				y = din.read();
				left = (x << 8) | y;
				
				x = din.read();
				y = din.read();
				right = (x << 8) | y;
				
				count += 8;
				
				memoryCoder.setPageMargin((short)up, (short)down, (short)left, (short)right);
			}
			
			if((pageProperty & 0x08) == 0) {
				memoryCoder.setOrient(true);
			} else {
				memoryCoder.setOrient(false);
			}
//			System.err.println(" count : " + count);
			memoryCoder.endPageUnit();
		} catch (ParseException e) {
			MyLog.logPrint(e.getMessage());
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *TODO
	 *
	 *@param format
	 *@param din
	 */
	public void paraDecoder(int format, DataInputStream din) {
//		System.err.println(" wordking in paraDecoder : ***** format + " + Integer.toHexString(format));
		int count = 0;
		boolean left_Indent = true, right_Indent = true, head_Indent = true, globalChange = true;

		int leftIndent = 0, rightIndent = 0, firstRowIndent = 0;
		
		int alignStyle = 0, lineSpaceType = 0, lineSpace = 0, beforeParaSpace = 0, afterParaSpace = 0;
		int x = 0;

		if ((format & 0x08) == 0) globalChange = false;
		if ((format & 0x04) == 0) left_Indent = false;
		if ((format & 0x02) == 0) right_Indent = false;
		if ((format & 0x01) == 0) head_Indent = false;

		try {
			if (globalChange == true) {
				x = din.read();
				count++;
			    alignStyle = (x & 0xC0) >> 6;
				lineSpaceType = (x & 0x20) >> 5;
				lineSpace = x & 0x07;

				x = din.read();
				count++;
				beforeParaSpace = (x & 0xF0) >> 4;
				afterParaSpace = x & 0x0F;
				
				preAlignStyle = alignStyle;
				preLineSpaceType = lineSpaceType;
				preLineSpace = lineSpace;
				preBeforeParaSpace = beforeParaSpace;
				preAfterParaSpace = afterParaSpace;
			} else {
				alignStyle = preAlignStyle;
				lineSpaceType = preLineSpaceType;
				lineSpace = preLineSpace;
				beforeParaSpace = preBeforeParaSpace;
				afterParaSpace = preAfterParaSpace;
			}

			// System.err.println(" beforeParaSpace : " + beforeParaSpace
			// + " afterParaSpace : " +afterParaSpace);
			if (head_Indent == true) {
				firstRowIndent = din.read();
				preFirstRowIndent = firstRowIndent;
				count++;
				// System.err.println(" firstRowIndent : " + firstRowIndent);
			} else {
				firstRowIndent = preFirstRowIndent;
			}

			if (left_Indent == true) {
				leftIndent = din.read();
				preLeftIndent = leftIndent;
				count++;
				// System.err.println(" leftIndent : " + leftIndent);
			} else {
				leftIndent = preLeftIndent;
			}

			if (right_Indent == true) {
				rightIndent = din.read();
				preRightIndent = rightIndent;
				// System.err.println(" rightIndent : " + rightIndent);
			} else {
				rightIndent = preRightIndent;
				count++;
			}
//			System.err.println(" cout : " + count);
			memoryCoder.addParagraph((byte) firstRowIndent, (byte) leftIndent,
					(byte) rightIndent, (byte) alignStyle,
					(byte) lineSpaceType, (byte) lineSpace,
					(byte) beforeParaSpace, (byte) afterParaSpace);

		} catch (ParseException e) {
			MyLog.logPrint(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *TODO
	 *
	 *@param format
	 *@param din
	 */

}
