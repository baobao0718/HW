package com.study.android.code;

import android.util.Log;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.MyStroke;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.CharUnit;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.basicData.type.ImageUnit;
import com.study.android.data.ListTable;
import com.study.android.model.ParagraphFormat;
import com.study.android.model.SectionFormat;
import com.study.android.model.ParagraphFormat.AlignMode;
import com.study.android.model.ParagraphFormat.RowSpaceUnit;
import com.study.android.model.SectionFormat.PaperSize;
import com.study.android.retrieval.Operationonrretrievalfile;
import com.study.android.retrieval.Transform;
import com.study.android.ui.HwFunctionWindow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*******************************************************************************
 * 
 *内存解码器，实现文件的编码，解码，将内存中的数据写到文件中
 *@create-time: 2008-7-26
 *
 ******************************************************************************/

public class MemoryDecoder {
	 
	/** 实现了DataHandler的一个文件编码器，利用编码器中的方法将数据写入文件 */
	private DataHandler documentCoder;
	
	/** 文件的输出流，将数据写入到此流中 */
	private OutputStream outputStream;
	
	private static final byte A4_Size = 0x00;// A4 纸张掩码
	
	private static final byte B5_Size = 0x01;// B5 纸张掩码
	
	private static final byte Other_Size = 0x07;// 自定义纸张掩码
	
     /** 手写文件扩展名 "hwe" */
    /** 手写文件扩展名 "hwe" */
	public static final byte[] fileExtension = {'h', 'w', 'e', 'p'};  
	
	/** 手写文件当前版本号 1 */
	public static final float version = 8.0f;
	/**手写笔画宽度*/
	public static final float stroke=1.5f;
	
	
	public static int sectionnum = 0;
	
	/**保留字段，4个字节,这里我修改过，将以前8个BYTE 的保留字段拿出后面4个BYTE 存储页面数信息*/
	public static final byte[] reserve = new byte[4];
	
	private static ParagraphFormat preParaFormat = null;
	
	public MemoryDecoder(OutputStream  out){
		outputStream = out;
		documentCoder = new DocumentCoder(outputStream);
	//	documentCoder.setOutputStream(out);
	}
	
	/**
	 * 主要是版本信息 2008-7-26 下午07:48:01
	 * 
	 */
	public void headCoder() {
		try {
			SimpleDateFormat   formatter   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss +0800");   //zhuyixia   
			Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间      
			String   str   =   formatter.format(curDate);      
//			Log.i("zhuxiaoqing--->>>>时间：",str);
			int m=str.length();
			String   sum=String.valueOf(m);
//			Log.i("zhuxiaoqing--->>>>长度；",sum);//
			byte[]strTime=str.getBytes();//25
			//Title1 = (ArrayList<DataUnit>) MyAdapter.templist.clone();
			short numoftitle=(short)ListTable.numTitle;
			sectionnum= ListTable.Sectionnum;       
			Log.v("***WRITING--->zhuxiaoqing-->>","ListTable.Sectionnum"+numoftitle);
			String mk="hwep";
			byte[]sk=mk.getBytes();
			//outputStream.write(fileExtension);//4
			outputStream.write(sk);//4
			//outputStream.write((byte)(100 * Math.random()));
			outputStream.write(Transform.floattoBytes(version));//4
			outputStream.write(reserve);//4
			outputStream.write(Transform.InttoBytes(sectionnum));//4		//将section转换成对应的byte数组存入文件
			outputStream.write(Transform.floattoBytes(stroke));//4
			outputStream.write(strTime);//25
			byte byte1= (byte) (numoftitle);
			byte byte2= (byte) ((numoftitle >> 8) & 0xff);
			outputStream.write(byte1);//1
			outputStream.write(byte2);//1
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.v("DEBUG--->","HEADCoder SHUNLI JIE SHU");                //到这里搞定前14个字节，第15个字节装的是颜色书ColorNum
	}
	
	/**
	 * 数据编码 2008-8-5 下午 02:54:10
	 * 
	 */
	public void dataCoder() {
		
		/*这快是为了能够添加让图片显示颜色所新更改的！！！*/
		
//		HwFunctionWindow hwfunc = new HwFunctionWindow();
//		hwfunc.colortable.clear();
//		hwfunc.Initcolortable();
		
//		int[] colors = new int[hwfunc.colortable.size()];
//		for (int i = 0; i < hwfunc.colortable.size(); i++) {
//			colors[i] = hwfunc.colortable.get(i);
//		}
		
/*		int[] colors = new int[HwFunctionWindow.colortable.size()];
		for (int i = 0; i < HwFunctionWindow.colortable.size(); i++) {
			colors[i] = HwFunctionWindow.colortable.get(i);										//获得颜色索引表中的所有颜色的RGB值！！
		}*/
	
		int maxX, minX;
		byte colorIndex;
		int pointSize;
		BasePoint point;
		
		try {
//			documentCoder.setColorTable(colors);                                         //搞定头文件中的颜色索引表部分（头文件的末尾部分）
			byte style = 0;// 这个值只是暂时的，style表示的是字体样式，下划线等等

			/** 总共有多少节 */
			int sectionSize = ListTable.sectionTable.size();                   //sectionTable.size 是指页的数目
			int dataSize = ListTable.globalIndexTable.size();
			
			int globalIndex = 0;//zhuxiaoqing 2011.06.23
		//	for (int sectionIndex = 0; sectionIndex < sectionSize; sectionIndex++) {
		//		sectionCoder(sectionIndex);// 处理节的相关信息，除了段表外
		//		System.err.println("DEBUG----ListTable.sectionTable.size()"+sectionSize);
				Log.v("DEBUG---->hahahahaha","dataSize"+dataSize);
				for (; globalIndex < dataSize; ++globalIndex) {
					//Log.i("DEBUG----->","????????????????????????????DAO ZHE MEI0000");
					if(globalIndex==ListTable.numTitle&&ListTable.ipdfile==true)
						continue;
					DataUnit dataUnit = ListTable.globalIndexTable.get(globalIndex);
					DataType dataType = dataUnit.getDataType();
					byte roundStyle = 0;// 环绕方式
					int height;
					short width;
					minX = Integer.MAX_VALUE;
					maxX = Integer.MIN_VALUE;
					switch (dataType) {
					case TYPE_CHAR:// 文字单元	
						//Log.v("DEBUG---->","dataType111:TYPE_CHAR");
						charUnitCoder(dataUnit, style);
						break;

					case TYPE_IMAGE:// 图片单元
						//Log.v("DEBUG---->","dataType111:TYPE_image");
						
						imageUnitCoder(dataUnit, roundStyle);
						break;

					case TYPE_CONTROL:// 控制单元
						width = dataUnit.getWidth();
						switch (dataUnit.getCtrlType()) {
						case TYPE_CTRL_ENTER:
							//Log.v("DEBUG---->","dataType111:TYPE_Enter");
							//paragraphCoder(dataUnit);// 回车就是新建一个段，其实可以不保存回车单元的，
							short widthenter = 32;
							documentCoder.addEnterUnit(widthenter);
							break;
						case TYPE_CTRL_SPACE:
							//width = dataUnit.getWidth();
							documentCoder.addSpaceUnit(width);
							break;
						}

						break;

					case TYPE_BITMAP:// 位图单元

						break;

					default:
						System.err
								.println("there are some mistake in MemoryDecoder.java.dataCoder() ");
					}
				}
	//		}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Log.i("DEBUG----->","????????????????????????????DAO ZHE MEI");
	}
	
	/**
	 * 下面这部分是我为了在索引文件中能够存储标题而对上面函数的修改，用作标题部分的存储
	 * 
	 * 
	 * 
	 */
	
	public void dataCoderForTitle(ArrayList<DataUnit> title) {
	
		int maxX, minX;
		byte colorIndex;
		int pointSize;
		BasePoint point;

		byte style = 0;// 这个值只是暂时的，style表示的是字体样式，下划线等等

		/** 总共有多少节 */
		//int sectionSize = ListTable.sectionTable.size();                   //sectionTable.size 是指页的数目
		int sectionSize = 1;
		int dataSize = title.size();
		 //Log.v("zai wang wenj xie ru qian shu ju dan yuan ge shu--->",""+dataSize);//问问题在这！！！！！！！！！！！
		
		int globalIndex = 0;
		//for (int sectionIndex = 0; sectionIndex < sectionSize; sectionIndex++) {

			for (; globalIndex < dataSize; ++globalIndex) {
				DataUnit dataUnit = title.get(globalIndex);
				DataType dataType = dataUnit.getDataType();
				byte roundStyle = 0;// 环绕方式
				int height;
				int width;
				minX = Integer.MAX_VALUE;
				maxX = Integer.MIN_VALUE;
				switch (dataType) {
				case TYPE_CHAR:// 文字单元	
					//Log.v("DEBUG---->","dataType111:TYPE_CHAR");
					charUnitCoder(dataUnit, style);
					break;

				case TYPE_IMAGE:// 图片单元
					
					imageUnitCoder(dataUnit, roundStyle);
					break;

				case TYPE_CONTROL:// 控制单元
					width = dataUnit.getWidth();
					switch (dataUnit.getCtrlType()) {
					case TYPE_CTRL_ENTER:
						// width = dataUnit.getWidth();
						//paragraphCoder(dataUnit);// 回车就是新建一个段，其实可以不保存回车单元的，
						try {
							documentCoder.addEnterUnit((short)width);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;

					case TYPE_CTRL_SPACE:
						 //width = dataUnit.getWidth();
						try {
//							Log.i("DEBUG--->","SPACE UNIT WIDTH--->"+width);
							documentCoder.addSpaceUnit((short)width);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 break;
					}

					break;

				case TYPE_BITMAP:// 位图单元

					break;

				default:
					System.err
							.println("there are some mistake in MemoryDecoder.java.dataCoder() ");
				}
			}
		}
//	}
	
	/**
	 *TODO 对文字单元dataUnit解码，写入文件
	 * 
	 *@param dataUnit :  文字单元
	 *@param style : 样式（下划线等等的啊）
	 */
	private void charUnitCoder(DataUnit dataUnit, byte style) {
		CharUnit charUnit = (CharUnit) dataUnit;
		CharFormat charFormat = charUnit.getCharFormat();
		BasePoint point;

		byte colorIndex = (byte) HwFunctionWindow.colortable.indexOf(charFormat.getColor());// 文字单元的颜色索引
		byte formatHeight = charFormat.getHeight();// 字体高度
		byte strokeWidth = charFormat.getStrokeWidth();
		short charHeight = charUnit.getHeight();	
		if(charUnit.getPoints().size()>0)
		{
		try {
			documentCoder.startCharUnit(style, colorIndex, formatHeight,
				strokeWidth, charHeight);

//			writeStrokeWidth(charUnit.getPoints());
			
			int pointSize = charUnit.getPoints().size();
			for (int j = 0; j < pointSize - 1; j++) {
				point = charUnit.getPoints().get(j);
				documentCoder.addPoint(point.getX(), point.getY(), point.isEnd());
			}
			point = charUnit.getPoints().get(pointSize - 1);// 每个字的最后一个点单独处理
			documentCoder.addPoint(point.getX(), point.getY(), true);
			documentCoder.endCharUnit();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		

	}

	/**
	 *TODO 对图像单元解码编码，写入文件
	 *
	 *@param dataUnit 图像单元
	 *@param roundStyle 环绕方式 ：<code>	000 嵌入式, </code> 
	 *<code>001 独占一行, </code>
	 *<code>010 左右环绕, </code>
	 *<code>011 覆盖文字, </code>
	 *<code>100 作为背景，被文字覆盖, </code>
	 *<code>其他未定义</code>
	 */
	private void imageUnitCoder(DataUnit dataUnit, byte roundStyle) {
		ImageUnit imageUnit = (ImageUnit) dataUnit;
		int height = imageUnit.getHeight();
		int width = imageUnit.getWidth();
		int count =0;
		int pointSize;
		BasePoint point;
		byte colorIndex;
		Log.v("DEBUG---->","IMAGE UNIT STROKE SIZE BEFORE STORE:"+imageUnit.getStrokes().size());
		if(imageUnit.getStrokes().size()>0)                                      //防止出现空白单元情况，空白单元不会写入文件
		{
		try {
			documentCoder.startImageUnit(roundStyle, height, width);             //写入文件的图像单元起始部分
            
			List<MyStroke> stroke = imageUnit.getStrokes();
			int strokeSize = stroke.size();

			for (int j = 0; j < strokeSize; j++) {
				count++;
				pointSize = stroke.get(j).getPoints().size();
	//			Log.v("DEBUG--->","DI "+ count +"BI HUA IN BAO CUN de dianshu"+pointSize);
				point = stroke.get(j).getPoints().get(0);

				colorIndex = (byte) HwFunctionWindow.colortable.indexOf(stroke
						.get(j).getColor());
	//			Log.i("DEBUG--->",j+"BI HUA colorIndex in SavetoFile  "+colorIndex);
                 
				documentCoder.addPoint(point.getX(), point.getY(),
						(byte) stroke.get(j).getStrokeWidth(),colorIndex);  
				
//				 添加每一笔画的第一点,添加笔画的第一点和添加笔画的其它点的函数是不一样的

				for (int index = 1; index < pointSize; index++) {
					point = stroke.get(j).getPoints().get(index);
					//Log.v("DEBUG-->","point "+point.getX()+","+point.getY()+ " is or not end:"+point.isEnd());
					if (j == (strokeSize - 1) && index == (pointSize - 1)) {
						documentCoder.addPoint(point.getX(), point.getY(), true);
						documentCoder.endImageUnit();
					} else {
						documentCoder.addPoint(point.getX(), point.getY(), point.isEnd());
					}

//					 if (minX > point.getX()) {
//					 minX = point.getX();
//					 }
//					 if (maxX < point.getX()) {
//					 maxX = point.getX();
//					 }
				}
				//Log.v("DEBUG--->","DI "+ count +"BI HUA IN BAO CUN");
			//	documentCoder.setcolor((byte) -1);zhuxiaoqing 2011.11.28
			}
		}  catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}
	
	
	/**
	 * TODO 对节进行编码，处理节的相关信息
	 * <br><br><b> 这个方法写的很烂，以后需要做很大的改动 </b>
	 * <br><br><b> 目前只处理了一个节单元的情况 </b>
	 * @param sectionUnit
	 * @param index
	 */
	private void sectionCoder(int index) {

		/** 前一个节单元 */
		SectionFormat preSectionFormat;

		/** 当前节单元 */
		SectionFormat sectionFormat;

		byte pageStyle;
		boolean changeFlag = true;
		
		/** temp 的值 是1000 0000，即表示页码有改变 */
		int temp = 0x80;
		
		boolean vertical ;
		int up, down, left, right;
		int height, width;
		try {
			if (index == 0) {// 第一个节的情况
				sectionFormat = ListTable.sectionTable.get(index).getSectionFormat();
//				System.out.println("\nsection is ok: in memoryDecoder.java");
				if (sectionFormat.getPaperSize() == PaperSize.A4) {
					pageStyle = A4_Size;
				} else if (sectionFormat.getPaperSize() == PaperSize.B5) {
					pageStyle = B5_Size;
				} else {
					pageStyle = Other_Size;
				}
				
				documentCoder.startPageUnit(changeFlag, pageStyle);
				
				vertical = sectionFormat.isVertically();
				if(vertical == false) {
					temp = temp | 0x04;
				}
				try {
					outputStream.write(temp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(pageStyle == Other_Size) {
					height = sectionFormat.getPageHeight();
					width = sectionFormat.getPageWidth();
					documentCoder.setPageSize((short)height, (short)width);
				}
				
				up = sectionFormat.getUpMargin();
				down = sectionFormat.getDownMargin();
				left = sectionFormat.getLeftPageMargin();
				right = sectionFormat.getRightPageMargin();
				documentCoder.setPageMargin((short)up, (short)down, (short)left, (short)right);
			} else {
				preSectionFormat = ListTable.sectionTable.get(index - 1).getSectionFormat();
				sectionFormat = ListTable.sectionTable.get(index).getSectionFormat();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO 处理段的相关信息
	 */
	private void paragraphCoder(DataUnit dataUnit) {
		ControlUnit controlUnit = (ControlUnit) dataUnit;
		ParagraphFormat curParaFormat = controlUnit.getParaFormat();
	
		byte rowType;
		byte alignModel = 0;
		try {
			documentCoder.startParagraph();
			if(curParaFormat.getRowSpaceUnit() == RowSpaceUnit.BaseSpce) {
				rowType = 0;
			} else {
				rowType = 1;
			}
			
			switch (curParaFormat.getAlignMode()) {
			case LeftAlign:
				alignModel = 2;
				break;

			case RightAlign:
				alignModel = 1;
				break;

			case MinddleAlign:
				alignModel = 0;
				break;

			case BothSideAlign:
				alignModel = 3;
				break;

			default:
				System.err.println("there is some mistake in ");
			}
			documentCoder.addParagraph((byte)curParaFormat.getRowIndent(), (byte)curParaFormat.getLeftIndent(),
					(byte)curParaFormat.getRightIndent(), alignModel,
					rowType, (byte)curParaFormat.getRowSpace(),
					(byte)curParaFormat.getIntervalBeforePara(), (byte)curParaFormat.getIntervalAfterPara());
		
//			System.out.println("\nParagraph is ok: in memoryDecoder.java ");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO 调试代码
	 * 
	 * @param list 
	 * @2009-11-13 : 下午07:39:03
	 */
	private void writeFile(List<MyStroke> list) {
		try {
			File file = new File("d://in.txt");
			FileWriter fileWriter = new FileWriter(file);
			for(int i = 0; i<list.size(); i++) {
				for (int j = 0; j < list.get(i).getPoints().size(); j++)
					fileWriter.write(list.get(i).getPoints().get(j).getX()
							+ "   " + list.get(i).getPoints().get(j).getY()
							+ "\n");

					fileWriter.write("\n");
				}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * TODO 将文字的点及点宽度写到文件中  调试用
	 * 
	 * @param list 
	 * @2009-11-13 : 下午07:36:29
	 */
	private void writeStrokeWidth(List<BasePoint> list) {              //这与MemoryCoder.java中的writeStrokeWidth有什么区别？？不理解
		try {
			File file = new File("d://calligraphy_in.txt");
			FileWriter fileWriter = new FileWriter(file);
			for (int i = 0; i < list.size(); i++) {
				fileWriter.write(list.get(i).getX() + "  "+list.get(i).getY() + "  " +
						list.get(i).getStrokeWidth() + "\n");
				if (list.get(i).isEnd() == true)
					fileWriter.write("\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}


