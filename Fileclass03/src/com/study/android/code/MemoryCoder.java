
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
 * 内存编码器，实现DataHandler接口
 *@version 创建日期 2008-7-26 下午08:12:47
 */
public class MemoryCoder implements DataHandler {
	
	private CharUnit charUnit;
	
	/**
	 * 字体格式
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
	 * 设置颜色表, 将文件的颜色信息存入<b>HwFunctionWindow.colortable</b>中
	 * @param colors 颜色数组
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
	 * 开始字符单元
	 * @param style 样式
	 * @param color 颜色
	 * @param formatHeight 字体格式高度
	 * @param strokeWidth 笔画基准宽度
	 * @param charHeight 字体高度
	 * @throws ParseException
	 */
	
	//MemoryCoder 类的startCharUnit，endCharUnit方法的作用就是往ListTable.globalIndexTable添加charUnit
	public void startCharUnit(byte style, byte colorIndex, byte formatHeight, 
			byte strokeWidth, short charHeight) throws ParseException {
		try {
			  //Log.v("DEBUG","jinru--startCharUnit !!");
			// System.err.println("开始构造字符单元 ： \n");
			//int color = HwFunctionWindow.colortable.get(colorIndex);
			//.indexOf(charFormat.getColor());
			//int color= 8;
			//int color =HwFunctionWindow.colortable.get(colorIndex);
			//HwFunctionWindow.Initcolortable();
			//Log.v("DEBUG--->","colortable.size"+HwFunctionWindow.colortable.size());
			int color =HwFunctionWindow.colortable.get(colorIndex);
			CharFormat charFormat = CharFormat.getCharFormat(color,
					formatHeight, strokeWidth, style);                //设定字体格式
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
	 * 添加点，当end = true时，<b>x和y无效</b>
	 * @param x
	 * @param y
	 * @param end 是否结束点
	 * @throws ParseException
	 */
	public void addPoint(short x, short y, boolean end) throws ParseException {
		try {
			BasePoint point;
			switch (dataType) {
			case TYPE_CHAR:
				if (minX > x)                            //如果是charUnit 类型则对坐标进行修正，规范化
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
				stroke.addPoint(point);                          //为什么图像添加用stroke,而字符加用charUnit???图像由stroke构成？！
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  addPoint(short x, short y, boolean end)" +
					" " + e.getMessage());
		}
	}
	
	/**
	 * 结束字符单元
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
			
			maxX = Integer.MIN_VALUE;              //这意味着什么？？
			minX = Integer.MAX_VALUE;
			
			maxY = Integer.MIN_VALUE;
			minY = Integer.MAX_VALUE;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  endCharUnit() " + e.getMessage());
		}
	}
	
//为读取索引标题而建立	
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
			
			maxX = Integer.MIN_VALUE;              //这意味着什么？？
			minX = Integer.MAX_VALUE;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  endCharUnit() " + e.getMessage());
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
	 * 添加开始点，适用于图形单元和书法单元
	 * @param x
	 * @param y
	 * @param width ？？
	 * @param color 颜色
	 * @throws ParseException
	 * @see 添加普通点请使用 addPoint(short x, short y, boolean end)
	 */
	public void addPoint(short x, short y, byte width, byte colorIndex) throws ParseException{
		try {
		//	Log.i("DEBUG--->","colorindex in memorycoder DU QU--  "+colorIndex);
			int color = HwFunctionWindow.colortable.get(colorIndex);       //根据colortable中的颜色索引位置值得到其真正颜色的RGB值
			switch (dataType) {
			case TYPE_IMAGE:
				stroke = new MyStroke();
				stroke.setColor(color);            //根据colortable中的颜色索引位置值得到其真正颜色的RGB值，并付给stroke
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
	 * 结束图形单元
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
			ListTable.globalIndexTable.add(imageUnit);                 //结束时候，才真正添加到全局索引中去
//			Log.v("DEBUG--->","IN ENDIMAGEUNIT OF READING stroke size:"+imageUnit.getStrokes().size());
//			writeFile(imageUnit.getStrokes());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParseException(" MemoryCoder  endImageUnit() " + e.getMessage());
		}
	}
	
	/**
	 * 添加位图单元                                            //位图单元用来干嘛的到底是？
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
	 * 添加换行
	 * @param width 换行单元宽度
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
	 * 开始页（节）控制单元，随后设置属性
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
	 * 设置纸张大小
	 * @param height 纸张高度
	 * @param width 纸张宽度
	 * @throws ParseException
	 */
	public void setPageSize(short height, short width) throws ParseException{
		curSectionFormat.setPageHeight(height);
		curSectionFormat.setPageWidth(width);
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
		curSectionFormat.setUpMargin(up);
		curSectionFormat.setDownMargin(down);
		curSectionFormat.setLeftPageMargin(left);
		curSectionFormat.setRightPageMargin(right);
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
	 * 设置纵横方向
	 * @param orient true纵，false横
	 * @throws ParseException
	 */
	public void setOrient(boolean orient) throws ParseException{
		curSectionFormat.setVertically(orient);
	}
	
	/**
	 * 设置页边距的单位
	 * @param ut
	 * @throws ParseException
	 */
	public void setUnitType(byte ut) throws ParseException{
		
	}
	
	/**
	 * 设置背景，当前版本未定义
	 * @throws ParseException
	 */
	public void setBackGround() throws ParseException{
		
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
//		System.err.println("解码出了一个节单元\n");
		ListTable.sectionTable.add(new SectionUnit(ListTable.globalIndexTable.size(), curSectionFormat));
	}
	
	/**
	 * TODO 开始段单元，之后考虑是否设置段的属性
	 * @throws ParseException
	 */
	public void startParagraph() {
		
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
	public void addParagraph(byte firstRowIndent, byte leftIndent,
			byte rightIndent, byte alignStyle, byte lineSpaceType,
			byte lineSpace, byte beforeParaSpace, byte afterParaSpace)
			throws ParseException {
		try {
			// System.err.println(" 开始构造段单元 ：alignStyle : " + alignStyle);
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
			// System.err.println(" lineSpaceType ： " + lineSpaceType);
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
			// System.err.println(" 构造段单元 ：成功 \n");
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
	 *TODO 还原点的原始宽度
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
	 * TODO 调试代码
	 * 
	 * @param list 
	 * @2009-11-13 : 下午07:40:03
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
	 * TODO 将文字的点及点的宽度写到文件中，调试用的
	 * 
	 * @param list 
	 * @2009-11-13 : 下午07:34:54
	 */
	private void writeStrokeWidth(List<BasePoint> list) {            //列出一笔中所有点的信息，X，Y，还有宽度
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
