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
 *�ڴ��������ʵ���ļ��ı��룬���룬���ڴ��е�����д���ļ���
 *@create-time: 2008-7-26
 *
 ******************************************************************************/

public class MemoryDecoder {
	 
	/** ʵ����DataHandler��һ���ļ������������ñ������еķ���������д���ļ� */
	private DataHandler documentCoder;
	
	/** �ļ����������������д�뵽������ */
	private OutputStream outputStream;
	
	private static final byte A4_Size = 0x00;// A4 ֽ������
	
	private static final byte B5_Size = 0x01;// B5 ֽ������
	
	private static final byte Other_Size = 0x07;// �Զ���ֽ������
	
     /** ��д�ļ���չ�� "hwe" */
    /** ��д�ļ���չ�� "hwe" */
	public static final byte[] fileExtension = {'h', 'w', 'e', 'p'};  
	
	/** ��д�ļ���ǰ�汾�� 1 */
	public static final float version = 8.0f;
	/**��д�ʻ����*/
	public static final float stroke=1.5f;
	
	
	public static int sectionnum = 0;
	
	/**�����ֶΣ�4���ֽ�,�������޸Ĺ�������ǰ8��BYTE �ı����ֶ��ó�����4��BYTE �洢ҳ������Ϣ*/
	public static final byte[] reserve = new byte[4];
	
	private static ParagraphFormat preParaFormat = null;
	
	public MemoryDecoder(OutputStream  out){
		outputStream = out;
		documentCoder = new DocumentCoder(outputStream);
	//	documentCoder.setOutputStream(out);
	}
	
	/**
	 * ��Ҫ�ǰ汾��Ϣ 2008-7-26 ����07:48:01
	 * 
	 */
	public void headCoder() {
		try {
			SimpleDateFormat   formatter   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss +0800");   //zhuyixia   
			Date   curDate   =   new   Date(System.currentTimeMillis());//��ȡ��ǰʱ��      
			String   str   =   formatter.format(curDate);      
//			Log.i("zhuxiaoqing--->>>>ʱ�䣺",str);
			int m=str.length();
			String   sum=String.valueOf(m);
//			Log.i("zhuxiaoqing--->>>>���ȣ�",sum);//
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
			outputStream.write(Transform.InttoBytes(sectionnum));//4		//��sectionת���ɶ�Ӧ��byte��������ļ�
			outputStream.write(Transform.floattoBytes(stroke));//4
			outputStream.write(strTime);//25
			byte byte1= (byte) (numoftitle);
			byte byte2= (byte) ((numoftitle >> 8) & 0xff);
			outputStream.write(byte1);//1
			outputStream.write(byte2);//1
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.v("DEBUG--->","HEADCoder SHUNLI JIE SHU");                //������㶨ǰ14���ֽڣ���15���ֽ�װ������ɫ��ColorNum
	}
	
	/**
	 * ���ݱ��� 2008-8-5 ���� 02:54:10
	 * 
	 */
	public void dataCoder() {
		
		/*�����Ϊ���ܹ������ͼƬ��ʾ��ɫ���¸��ĵģ�����*/
		
//		HwFunctionWindow hwfunc = new HwFunctionWindow();
//		hwfunc.colortable.clear();
//		hwfunc.Initcolortable();
		
//		int[] colors = new int[hwfunc.colortable.size()];
//		for (int i = 0; i < hwfunc.colortable.size(); i++) {
//			colors[i] = hwfunc.colortable.get(i);
//		}
		
/*		int[] colors = new int[HwFunctionWindow.colortable.size()];
		for (int i = 0; i < HwFunctionWindow.colortable.size(); i++) {
			colors[i] = HwFunctionWindow.colortable.get(i);										//�����ɫ�������е�������ɫ��RGBֵ����
		}*/
	
		int maxX, minX;
		byte colorIndex;
		int pointSize;
		BasePoint point;
		
		try {
//			documentCoder.setColorTable(colors);                                         //�㶨ͷ�ļ��е���ɫ�������֣�ͷ�ļ���ĩβ���֣�
			byte style = 0;// ���ֵֻ����ʱ�ģ�style��ʾ����������ʽ���»��ߵȵ�

			/** �ܹ��ж��ٽ� */
			int sectionSize = ListTable.sectionTable.size();                   //sectionTable.size ��ָҳ����Ŀ
			int dataSize = ListTable.globalIndexTable.size();
			
			int globalIndex = 0;//zhuxiaoqing 2011.06.23
		//	for (int sectionIndex = 0; sectionIndex < sectionSize; sectionIndex++) {
		//		sectionCoder(sectionIndex);// ����ڵ������Ϣ�����˶α���
		//		System.err.println("DEBUG----ListTable.sectionTable.size()"+sectionSize);
				Log.v("DEBUG---->hahahahaha","dataSize"+dataSize);
				for (; globalIndex < dataSize; ++globalIndex) {
					//Log.i("DEBUG----->","????????????????????????????DAO ZHE MEI0000");
					if(globalIndex==ListTable.numTitle&&ListTable.ipdfile==true)
						continue;
					DataUnit dataUnit = ListTable.globalIndexTable.get(globalIndex);
					DataType dataType = dataUnit.getDataType();
					byte roundStyle = 0;// ���Ʒ�ʽ
					int height;
					short width;
					minX = Integer.MAX_VALUE;
					maxX = Integer.MIN_VALUE;
					switch (dataType) {
					case TYPE_CHAR:// ���ֵ�Ԫ	
						//Log.v("DEBUG---->","dataType111:TYPE_CHAR");
						charUnitCoder(dataUnit, style);
						break;

					case TYPE_IMAGE:// ͼƬ��Ԫ
						//Log.v("DEBUG---->","dataType111:TYPE_image");
						
						imageUnitCoder(dataUnit, roundStyle);
						break;

					case TYPE_CONTROL:// ���Ƶ�Ԫ
						width = dataUnit.getWidth();
						switch (dataUnit.getCtrlType()) {
						case TYPE_CTRL_ENTER:
							//Log.v("DEBUG---->","dataType111:TYPE_Enter");
							//paragraphCoder(dataUnit);// �س������½�һ���Σ���ʵ���Բ�����س���Ԫ�ģ�
							short widthenter = 32;
							documentCoder.addEnterUnit(widthenter);
							break;
						case TYPE_CTRL_SPACE:
							//width = dataUnit.getWidth();
							documentCoder.addSpaceUnit(width);
							break;
						}

						break;

					case TYPE_BITMAP:// λͼ��Ԫ

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
	 * �����ⲿ������Ϊ���������ļ����ܹ��洢����������溯�����޸ģ��������ⲿ�ֵĴ洢
	 * 
	 * 
	 * 
	 */
	
	public void dataCoderForTitle(ArrayList<DataUnit> title) {
	
		int maxX, minX;
		byte colorIndex;
		int pointSize;
		BasePoint point;

		byte style = 0;// ���ֵֻ����ʱ�ģ�style��ʾ����������ʽ���»��ߵȵ�

		/** �ܹ��ж��ٽ� */
		//int sectionSize = ListTable.sectionTable.size();                   //sectionTable.size ��ָҳ����Ŀ
		int sectionSize = 1;
		int dataSize = title.size();
		 //Log.v("zai wang wenj xie ru qian shu ju dan yuan ge shu--->",""+dataSize);//���������⣡��������������������
		
		int globalIndex = 0;
		//for (int sectionIndex = 0; sectionIndex < sectionSize; sectionIndex++) {

			for (; globalIndex < dataSize; ++globalIndex) {
				DataUnit dataUnit = title.get(globalIndex);
				DataType dataType = dataUnit.getDataType();
				byte roundStyle = 0;// ���Ʒ�ʽ
				int height;
				int width;
				minX = Integer.MAX_VALUE;
				maxX = Integer.MIN_VALUE;
				switch (dataType) {
				case TYPE_CHAR:// ���ֵ�Ԫ	
					//Log.v("DEBUG---->","dataType111:TYPE_CHAR");
					charUnitCoder(dataUnit, style);
					break;

				case TYPE_IMAGE:// ͼƬ��Ԫ
					
					imageUnitCoder(dataUnit, roundStyle);
					break;

				case TYPE_CONTROL:// ���Ƶ�Ԫ
					width = dataUnit.getWidth();
					switch (dataUnit.getCtrlType()) {
					case TYPE_CTRL_ENTER:
						// width = dataUnit.getWidth();
						//paragraphCoder(dataUnit);// �س������½�һ���Σ���ʵ���Բ�����س���Ԫ�ģ�
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

				case TYPE_BITMAP:// λͼ��Ԫ

					break;

				default:
					System.err
							.println("there are some mistake in MemoryDecoder.java.dataCoder() ");
				}
			}
		}
//	}
	
	/**
	 *TODO �����ֵ�ԪdataUnit���룬д���ļ�
	 * 
	 *@param dataUnit :  ���ֵ�Ԫ
	 *@param style : ��ʽ���»��ߵȵȵİ���
	 */
	private void charUnitCoder(DataUnit dataUnit, byte style) {
		CharUnit charUnit = (CharUnit) dataUnit;
		CharFormat charFormat = charUnit.getCharFormat();
		BasePoint point;

		byte colorIndex = (byte) HwFunctionWindow.colortable.indexOf(charFormat.getColor());// ���ֵ�Ԫ����ɫ����
		byte formatHeight = charFormat.getHeight();// ����߶�
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
			point = charUnit.getPoints().get(pointSize - 1);// ÿ���ֵ����һ���㵥������
			documentCoder.addPoint(point.getX(), point.getY(), true);
			documentCoder.endCharUnit();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		

	}

	/**
	 *TODO ��ͼ��Ԫ������룬д���ļ�
	 *
	 *@param dataUnit ͼ��Ԫ
	 *@param roundStyle ���Ʒ�ʽ ��<code>	000 Ƕ��ʽ, </code> 
	 *<code>001 ��ռһ��, </code>
	 *<code>010 ���һ���, </code>
	 *<code>011 ��������, </code>
	 *<code>100 ��Ϊ�����������ָ���, </code>
	 *<code>����δ����</code>
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
		if(imageUnit.getStrokes().size()>0)                                      //��ֹ���ֿհ׵�Ԫ������հ׵�Ԫ����д���ļ�
		{
		try {
			documentCoder.startImageUnit(roundStyle, height, width);             //д���ļ���ͼ��Ԫ��ʼ����
            
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
				
//				 ���ÿһ�ʻ��ĵ�һ��,��ӱʻ��ĵ�һ�����ӱʻ���������ĺ����ǲ�һ����

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
	 * TODO �Խڽ��б��룬����ڵ������Ϣ
	 * <br><br><b> �������д�ĺ��ã��Ժ���Ҫ���ܴ�ĸĶ� </b>
	 * <br><br><b> Ŀǰֻ������һ���ڵ�Ԫ����� </b>
	 * @param sectionUnit
	 * @param index
	 */
	private void sectionCoder(int index) {

		/** ǰһ���ڵ�Ԫ */
		SectionFormat preSectionFormat;

		/** ��ǰ�ڵ�Ԫ */
		SectionFormat sectionFormat;

		byte pageStyle;
		boolean changeFlag = true;
		
		/** temp ��ֵ ��1000 0000������ʾҳ���иı� */
		int temp = 0x80;
		
		boolean vertical ;
		int up, down, left, right;
		int height, width;
		try {
			if (index == 0) {// ��һ���ڵ����
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
	 * TODO ����ε������Ϣ
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
	 * TODO ���Դ���
	 * 
	 * @param list 
	 * @2009-11-13 : ����07:39:03
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
	 * TODO �����ֵĵ㼰����д���ļ���  ������
	 * 
	 * @param list 
	 * @2009-11-13 : ����07:36:29
	 */
	private void writeStrokeWidth(List<BasePoint> list) {              //����MemoryCoder.java�е�writeStrokeWidth��ʲô���𣿣������
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


