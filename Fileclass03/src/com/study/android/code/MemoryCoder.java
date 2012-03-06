
package com.study.android.code;

import android.util.Log;

import com.study.android.HWtrying.HWtrying;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.CharPoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.MyStroke;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.CharUnit;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.basicData.type.ImageUnit;
import com.study.android.data.ListTable;
import com.study.android.ink.InkPen;
import com.study.android.model.ParagraphFormat;
import com.study.android.model.ParagraphUnit;
import com.study.android.model.SectionFormat;
import com.study.android.model.SectionUnit;
import com.study.android.model.ParagraphFormat.AlignMode;
import com.study.android.model.ParagraphFormat.RowSpaceUnit;
import com.study.android.model.SectionFormat.PaperSize;
import com.study.android.ui.HwFunctionWindow;
import com.study.android.zoom.Zoom;
//import com.study.android.ui.HwFunctionWindow;
//import com.study.android.ui.HwFunctionWindow;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * �ڴ��������ʵ��DataHandler�ӿ�
 *@version �������� 2008-7-26 ����08:12:47
 */
public class MemoryCoder implements DataHandler {
	
	private CharUnit charUnit;
	
	/**
	 * �����ʽ
	 */
	private CharFormat preCharFormat;
	
	private SectionFormat preSectionFormat;
	
	private SectionFormat curSectionFormat;
	
	private ParagraphFormat paraFormat;
	
	private ParagraphFormat preParaFormat;
	
	private ImageUnit imageUnit;
	
	private MyStroke stroke;
	
//	private CalligraphyUnit calligraphyUnit;
	
	private ControlUnit controlUnit;
	
	private SectionUnit sectionUnit;
	
	private static int paraIndexTag = 0;
	
	private short charactersize =(short) ListTable.charactersize;
	
	private int minX = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int charWidth;
	
	private DataType dataType;
	
	public MemoryCoder() {
		preCharFormat = CharFormat.getDefaultCharFormat();
		preSectionFormat = SectionFormat.getDefaultSectionformat();
		preParaFormat= ParagraphFormat.getDefaultParaFormat();
		charactersize =(short) ListTable.charactersize;
	}

	/**
	 * ������ɫ��, ���ļ�����ɫ��Ϣ����<b>HwFunctionWindow.colortable</b>��
	 * @param colors ��ɫ����
	 * @throws ParseException
	 */
	public void setColorTable(int[] colors)  throws ParseException{
		try {
			HwFunctionWindow.colortable.clear();
			for (int i = 0; i < colors.length; i++) {
				HwFunctionWindow.colortable.add(colors[i]);                     
						System.err.printf("colors : %x\n", colors[i]);
						System.err.printf("color : %x\n",HwFunctionWindow.colortable.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  setColorTable() " + e.getMessage());
		}
	}
	
	/**
	 * ��ʼ�ַ���Ԫ
	 * @param style ��ʽ
	 * @param color ��ɫ
	 * @param formatHeight �����ʽ�߶�
	 * @param strokeWidth �ʻ���׼���
	 * @param charHeight ����߶�
	 * @throws ParseException
	 */
	
	//MemoryCoder ���startCharUnit��endCharUnit���������þ�����ListTable.globalIndexTable���charUnit
	public void startCharUnit(byte style, byte colorIndex, byte formatHeight, 
			byte strokeWidth, short charHeight) throws ParseException {
		try {
			  //Log.v("DEBUG","jinru--startCharUnit !!");
			// System.err.println("��ʼ�����ַ���Ԫ �� \n");
			//int color = HwFunctionWindow.colortable.get(colorIndex);
			//.indexOf(charFormat.getColor());
			//int color= 8;
			//int color =HwFunctionWindow.colortable.get(colorIndex);
			//HwFunctionWindow.Initcolortable();
			//Log.v("DEBUG--->","colortable.size"+HwFunctionWindow.colortable.size());
			int color =HwFunctionWindow.colortable.get(colorIndex);
			CharFormat charFormat = CharFormat.getCharFormat(color,
					formatHeight, strokeWidth, style);                //�趨�����ʽ
			//Log.v("DEBUG","mashangchulai1111--startCharUnit !!");
			
			if (!preCharFormat.equals(charFormat)) {
				preCharFormat = charFormat;
			}
			/**
			 * 
			 * if (!preCharFormat.equals(charFormat)) {
				preCharFormat = charFormat;
			}
			 */
			
			dataType = DataType.TYPE_CHAR;
			charUnit = new CharUnit((short) 0, charHeight);
		
			charUnit.setCharFormat(charFormat);
			  
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  startCharUnit() " + e.getMessage());
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
		try {
			BasePoint point;
			switch (dataType) {
			case TYPE_CHAR:
				if (minX > x)                            //�����charUnit ���������������������淶��
					minX = x;
				if (maxX < x)
					maxX = x;
				
				if(minY>y)
					minY=y;
				if(maxY<y)
					maxY=y;
				point = new CharPoint(x, y);
				point.setEnd(end);
				charUnit.addPoint(point);
				break;

			case TYPE_IMAGE:
				point = new BasePoint(x, y);
				point.setEnd(end);
				stroke.addPoint(point);                          //Ϊʲôͼ�������stroke,���ַ�����charUnit???ͼ����stroke���ɣ���
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  addPoint(short x, short y, boolean end)" +
					" " + e.getMessage());
		}
	}
	
	/**
	 * �����ַ���Ԫ
	 * @throws ParseException
	 */
	public void endCharUnit() throws ParseException{
		try {
			if (charUnit == null)
				System.err.println("charUnit is null");
			charWidth = maxX - minX + 1;
			charUnit.setWidth((short) (charWidth + (CharUnit.ANCHOR << 1)));
			
			//CharUnit charunitnew =HWtrying.compressionWithDiffHeightread(charUnit,charUnit.getHeight(),(short)minY,(short)maxY);
			CharUnit charunitnew =Zoom.compressionWithDiffHeightread(charUnit,charUnit.getHeight(),(short)minY,(short)maxY);
			modifyStroke(charunitnew.getPoints(),2);
			ListTable.globalIndexTable.add(charunitnew);
			
			maxX = Integer.MIN_VALUE;              //����ζ��ʲô����
			minX = Integer.MAX_VALUE;
			
			maxY = Integer.MIN_VALUE;
			minY = Integer.MAX_VALUE;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  endCharUnit() " + e.getMessage());
		}
	}
	
//Ϊ��ȡ�������������	
	public void endCharUnit(short characterheight) throws ParseException{
		try {
			if (charUnit == null)
				System.err.println("charUnit is null");
			charWidth = maxX - minX + 1;
			charUnit.setWidth((short) (charWidth + (CharUnit.ANCHOR << 1)));
			modifyStroke(charUnit.getPoints(), 2);
			//CharUnit charunitnew =HWtrying.compressionWithDiffHeightreadForTitle(charUnit,charUnit.getHeight(),(short)minY,(short)maxY,(short)40);
			CharUnit charunitnew =Zoom.compressionWithDiffHeightreadForTitle(charUnit,charUnit.getHeight(),(short)minY,(short)maxY,(short)40);
			ListTable.globalIndexTable.add(charunitnew);
			
			maxX = Integer.MIN_VALUE;              //����ζ��ʲô����
			minX = Integer.MAX_VALUE;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  endCharUnit() " + e.getMessage());
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
		try {
			dataType = DataType.TYPE_IMAGE;
			imageUnit = new ImageUnit((short) (width + (CharUnit.ANCHOR << 1)),
					(short) (height + (CharUnit.ANCHOR << 1)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  startImageUnit() " + e.getMessage());
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
	public void addPoint(short x, short y, byte width, byte colorIndex) throws ParseException{
		try {
		//	Log.i("DEBUG--->","colorindex in memorycoder DU QU--  "+colorIndex);
			int color = HwFunctionWindow.colortable.get(colorIndex);       //����colortable�е���ɫ����λ��ֵ�õ���������ɫ��RGBֵ
			switch (dataType) {
			case TYPE_IMAGE:
				stroke = new MyStroke();
				stroke.setColor(color);            //����colortable�е���ɫ����λ��ֵ�õ���������ɫ��RGBֵ��������stroke
				stroke.setStrokeWidth(width);
				imageUnit.addStrokes(stroke);
				BasePoint point = new BasePoint(x, y);
				stroke.addPoint(point);
				
				//			System.err.println("read out the first point of each stroke : " +
				//			x + " " + y);
				break;
//			case TYPE_CALLIGRAPHY:
//				point = new CharPoint(x, y);
//				point.setStrokeWidth(width);
//				calligraphyUnit.setColor(color);
//				calligraphyUnit.addPoint(point);
//				break;
			default:
				System.err.println("there are some mistake in MemoryCoder.java.addPoint() ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder addPoint(short x, short y, " +
					"byte width, byte colorIndex) " + e.getMessage());
		}
	}
	
	/**
	 * ����ͼ�ε�Ԫ
	 * @throws ParseException
	 */
	public void endImageUnit() throws ParseException {
		try {
			// System.out.println("\nstroke size : " +
			// imageUnit.getStrokes().size());
			// for(int i = 0; i < imageUnit.getStrokes().size(); i++){
			// System.out.println("point size : " +
			// imageUnit.getStrokes().get(i).getPoints().size());
			// // System.out.println("color index : " +
			// imageUnit.getStrokes().get(i).getColorIndex() + "\n");
			// }
			ListTable.globalIndexTable.add(imageUnit);                 //����ʱ�򣬲�������ӵ�ȫ��������ȥ
//			Log.v("DEBUG--->","IN ENDIMAGEUNIT OF READING stroke size:"+imageUnit.getStrokes().size());
//			writeFile(imageUnit.getStrokes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  endImageUnit() " + e.getMessage());
		}
	}
	
	/**
	 * ���λͼ��Ԫ                                            //λͼ��Ԫ��������ĵ����ǣ�
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
	public void addSpaceUnit(short width) throws ParseException{
		try {
			controlUnit = new ControlUnit(DataType.TYPE_CTRL_SPACE,
					preCharFormat);
			ListTable.globalIndexTable.add(controlUnit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  addSpaceUnit() " + e.getMessage());
		}
	}
	
	
	/**
	 * ��ӻ���
	 * @param width ���е�Ԫ���
	 * @throws ParseException
	 */
	public void addEnterUnit(short width) throws ParseException{
		try {
			//controlUnit = new ControlUnit(DataType.TYPE_CTRL_ENTER,
			//		preCharFormat);
			controlUnit = new ControlUnit(DataType.TYPE_CTRL_ENTER,
					CharFormat.getDefaultCharFormat());
//			System.out.println("**add enter!!");
			ListTable.globalIndexTable.add(controlUnit);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  addSpaceUnit() " + e.getMessage());
		}
	}
	
	/**
	 * ��ʼҳ���ڣ����Ƶ�Ԫ�������������
	 * @throws ParseException
	 */
	public void startPageUnit(boolean changFlag, byte style) throws ParseException{
		try {
			if (changFlag == true) {
				curSectionFormat = new SectionFormat();
				int pageStyle = style & 0x07;
				switch (pageStyle) {
				case 0:
					curSectionFormat.setPaperSize(PaperSize.A4);
					setPageSize((short) SectionFormat.defaultPageHeight,
							(short) SectionFormat.defaulePageWidth);
					break;
				case 1:
					setPageSize((short) SectionFormat.defaultPageHeight_B5,
							(short) SectionFormat.defaultCoreWidth_B5);
					curSectionFormat.setPaperSize(PaperSize.B5);
					break;
				case 7:
					curSectionFormat.setPaperSize(PaperSize.Other);
					break;
				default:
					System.err.println();
				}
			} else {
				curSectionFormat = preSectionFormat;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  startPageUnit() " + e.getMessage());
		}
	}
	
	/**
	 * ����ֽ�Ŵ�С
	 * @param height ֽ�Ÿ߶�
	 * @param width ֽ�ſ��
	 * @throws ParseException
	 */
	public void setPageSize(short height, short width) throws ParseException{
		curSectionFormat.setPageHeight(height);
		curSectionFormat.setPageWidth(width);
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
		curSectionFormat.setUpMargin(up);
		curSectionFormat.setDownMargin(down);
		curSectionFormat.setLeftPageMargin(left);
		curSectionFormat.setRightPageMargin(right);
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
	 * �����ݺ᷽��
	 * @param orient true�ݣ�false��
	 * @throws ParseException
	 */
	public void setOrient(boolean orient) throws ParseException{
		curSectionFormat.setVertically(orient);
	}
	
	/**
	 * ����ҳ�߾�ĵ�λ
	 * @param ut
	 * @throws ParseException
	 */
	public void setUnitType(byte ut) throws ParseException{
		
	}
	
	/**
	 * ���ñ�������ǰ�汾δ����
	 * @throws ParseException
	 */
	public void setBackGround() throws ParseException{
		
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
//		System.err.println("�������һ���ڵ�Ԫ\n");
		ListTable.sectionTable.add(new SectionUnit(ListTable.globalIndexTable.size(), curSectionFormat));
	}
	
	/**
	 * TODO ��ʼ�ε�Ԫ��֮�����Ƿ����öε�����
	 * @throws ParseException
	 */
	public void startParagraph() {
		
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
	public void addParagraph(byte firstRowIndent, byte leftIndent,
			byte rightIndent, byte alignStyle, byte lineSpaceType,
			byte lineSpace, byte beforeParaSpace, byte afterParaSpace)
			throws ParseException {
		try {
			// System.err.println(" ��ʼ����ε�Ԫ ��alignStyle : " + alignStyle);
			AlignMode alignMode = AlignMode.BothSideAlign;
			RowSpaceUnit rowSpaceUnit;
			switch (alignStyle) {
			case 0:
				alignMode = AlignMode.MinddleAlign;
				break;

			case 1:
				alignMode = AlignMode.RightAlign;
				break;

			case 2:
				alignMode = AlignMode.LeftAlign;
				break;

			case 3:
				alignMode = AlignMode.BothSideAlign;
				break;
			default:
				System.err.println();
			}
			// System.err.println(" lineSpaceType �� " + lineSpaceType);
			if (lineSpaceType == 0) {
				rowSpaceUnit = RowSpaceUnit.BaseSpce;
			} else {
				rowSpaceUnit = RowSpaceUnit.Pound;
			}
			paraFormat = ParagraphFormat.getParagraphFormat(leftIndent,
					rightIndent, firstRowIndent, afterParaSpace,
					beforeParaSpace, alignMode, rowSpaceUnit, lineSpace);
			if (!preParaFormat.equals(paraFormat)) {
				preParaFormat = paraFormat;
			}
			//controlUnit = new ControlUnit(DataType.TYPE_CTRL_ENTER,
			//		preCharFormat);
			controlUnit.setParaFormat(paraFormat);
			ListTable.globalIndexTable.add(controlUnit);
			// System.err.println(" ����ε�Ԫ ���ɹ� \n");
			if (ListTable.paragraphTable.size() == 0) {
				ListTable.paragraphTable.add(new ParagraphUnit(0, paraFormat));
				paraIndexTag = ListTable.globalIndexTable.size();
			} else {
				ListTable.paragraphTable.add(new ParagraphUnit(paraIndexTag,
						paraFormat));
				paraIndexTag = ListTable.globalIndexTable.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  addParagraph() "
					+ e.getMessage());
		}
	}

	/**
	 *TODO ��ԭ���ԭʼ���
	 *
	 *@param list
	 *@param strokeWidth
	 */
	private void modifyStroke(List<BasePoint> list, float strokeWidth) {
		InkPen inkPen = InkPen.getInstance();
		inkPen.setStrokeWidth(strokeWidth);
		inkPen.modifyStroke(list);
	}

	/**
	 * TODO ���Դ���
	 * 
	 * @param list 
	 * @2009-11-13 : ����07:40:03
	 */
	private void writeFile(List<MyStroke> list) {
		try {
			File file = new File("d://out.txt");
			FileWriter fileWriter = new FileWriter(file);
			for(int i = 0; i<list.size(); i++){
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
	 * TODO �����ֵĵ㼰��Ŀ��д���ļ��У������õ�
	 * 
	 * @param list 
	 * @2009-11-13 : ����07:34:54
	 */
	private void writeStrokeWidth(List<BasePoint> list) {            //�г�һ�������е����Ϣ��X��Y�����п��
		try {
			File file = new File("d://char_out.txt");
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

	@Override
	public void setcolor(byte num) throws ParseException {
		// TODO Auto-generated method stub
		
	}

}
