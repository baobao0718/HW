package com.study.android.model;
import com.study.android.basicData.DataUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义节的数据类型，构建一个节表，存储该节第一单元对应的全局索引
 *@author 付兴刚
 *@version 创建日期 2008-9-2 下午10:35:58
 */
public class SectionFormat {
	
//	/** 默认页的宽度 595 */
//	public static int defaulePageWidth = 595;
//	
//	/** 默认页的高度 842 */
//	public static int defaultPageHeight = 842;
//	
//	/** 默认页心的宽度 451*/
//	public static int defaultCoreWidth = 451;
//	
//	/** 默认页心的高度 698*/
//	public static int defautCoreHeight = 698;
//	
//	/** 默认页左边距 72*/
//	public static int defaultLeftPageMargin = 72;
//	
//	/** 默认页右边距 72*/
//	public static int defaultRightPageMargin = 72;
//	
//	/** 默认上边距 72*/
//	public static int defaultUpMargin = 72;
//	
//	/** 默认下边距 72*/
//	public static int defaultDownMargin = 72;
	
	/** 默认 B5 页的宽度 665*/
	public static int defaultPageWidth_B5 = 665;
	
	/** 默认 B5 页的高度 945*/
	public static int defaultPageHeight_B5 = 945;
	
	/** 默认 B5 页心的宽度 553*/
	public static int defaultCoreWidth_B5 = 425;
	
	/** 默认 B5 页心的高度 930*/
	public static int defautCoreHeight_B5 = 753;
	
	/** 默认页的宽度 793 */
	public static int defaulePageWidth = 793;
	
	/** 默认页的高度 1122 */
	public static int defaultPageHeight = 1122;
	
	/** 默认页心的宽度 553*/
	public static int defaultCoreWidth = 553;
	
	/** 默认页心的高度 930*/
	public static int defautCoreHeight = 930;
	
	/** 默认页左边距 120*/
	public static int defaultLeftPageMargin = 120;
	
	/** 默认页右边距 120*/
	public static int defaultRightPageMargin = 120;
	
	/** 默认上边距 96*/
	public static int defaultUpMargin = 96;
	
	/** 默认下边距 96*/
	public static int defaultDownMargin = 96;
	
	/** 默认单元之间的间隔， 好像不应该放在这里的 */
	public static int defaultInitInterval = 8;
	
	//public static int defaultBackColor = Color.WHITE.getRGB();                     //先暂时屏蔽这句
	
	/** 默认的纸张格式 <b>A4大小</b>*/
	public static PaperSize defaultPaperSize = PaperSize.A4;
	
	/**<b>纵横标志， 默认为 纵向 </b>*/
	public static boolean defaultVertically = true;
	
	/**<b>默认是没有页眉</b>*/
	public static boolean defaultHeader = false;
	
	/**<b>默认是没有页脚</b>*/
	public static boolean defaultFooter = false;
	
	/**<b>默认是没有页码</b>*/
	public static boolean defaultPageNum = false;
	
	private static SectionFormat defaultSectionFormat = null;
	
	/** 纸张大小 A4, B5, 自定义 */
	private PaperSize paperSize;
	
	/** 纵横标志,true表示纵向 false表示横向 */
	private boolean vertically;

	/** 页眉 */
	private boolean header;
	
	/** 页脚 */
	private boolean footer;
	
	/** 页码位置 */
	private PageNumLocation pageNumLocation;
	
	/** 页码 */
	private boolean pageNumber;
	
	/** 页面背景颜色 */
	private int backColor;
	
	/** 页的宽度 */
	private int pageWidth;
	
	/** 页的高度 */
	private int pageHeight;
	
	/** 页心的宽度 */
	private int coreWidth;
	
	/** 页心的高度 */
	private int coreHeight;
	
	/** 页左边距 */
	private int leftPageMargin;
	
	/** 页右边距 */
	private int rightPageMargin;
	
	/** 上边距 */
	private int upMargin;
	
	/** 下边距 */
	private int downMargin;
	
	/** 单元之间的间隔， 好像不应该放在这里的 */
	private int unitInterval;
	
	/** 页眉的数据 */
	private List<DataUnit> headData;
	
	/** 页脚的数据 */
	private List<DataUnit> footData;
	
	public static boolean showLine = true;
	
    public SectionFormat() {
		
	}
	
	public static SectionFormat getDefaultSectionformat() {
		if (defaultSectionFormat == null) {
			defaultSectionFormat = new SectionFormat(defaultCoreWidth,
					defautCoreHeight, defaultLeftPageMargin,
					defaultRightPageMargin, defaultUpMargin, defaultDownMargin,
					defaultVertically, defaultPaperSize);
		}
		return defaultSectionFormat;
	}
	
	/**
	 * TODO <b>目前只用了7个参数，参数太多，感觉这个类设计的不好，有待优化<br>
	 * @param coreWidth 页心的宽度
	 * @param coreHeight 页心的高度
	 * @param leftPageMargin 页左边距
	 * @param rightPageMargin 页右边距
	 * @param upMargin 页上边距
	 * @param downMargin 页下边距
	 * @param vertically 页的纵横标志
	 * @param paperSize 纸张大小
	 */
	public SectionFormat(int coreWidth, int coreHeight, int leftPageMargin,
			int rightPageMargin, int upMargin, int downMargin, boolean vertically, PaperSize paperSize) {
		this.coreWidth = coreWidth;
		this.coreHeight = coreHeight;
		this.leftPageMargin = leftPageMargin;
		this.rightPageMargin = rightPageMargin;
		this.upMargin = upMargin;
		this.downMargin = downMargin;
		this.vertically = vertically;
		this.paperSize = paperSize;
		
		pageHeight = this.coreHeight + this.upMargin + this.downMargin;
		pageWidth = this.coreWidth + this.leftPageMargin + this.rightPageMargin;
	}
	

	/** 获得纸张大小*/
	public PaperSize getPaperSize() {
		return this.paperSize;
	}
	
	/** 设置纸张大小 */
	public void setPaperSize(PaperSize paperSize) {
		this.paperSize = paperSize;
	}
	
	/** 返回纵横标记，true 为纵，false 为横 */
	public boolean isVertically() {
		return vertically;
	}

	/** 设置纵横标记，true 为纵，false 为横 */
	public void setVertically(boolean vertically) {
		this.vertically = vertically;
	}

	/** 是否显示页眉 */
	public boolean isHeader() {
		return header;
	}

	/** 设置是否显示页眉 */
	public void setHeader(boolean header) {
		this.header = header;
	}

	/** 是否显示页脚 */
	public boolean isFooter() {
		return footer;
	}

	/** 设置是否显示页脚 */
	public void setFooter(boolean footer) {
		this.footer = footer;
	}

	/** 获得页码位置 */
	public PageNumLocation getPageNumLocation() {
		return pageNumLocation;
	}

	/** 设置页码位置 */
	public void setPageNumLocation(PageNumLocation pageNumLocation) {
		this.pageNumLocation = pageNumLocation;
	}

	/** 是否显示页码 */
	public boolean isPageNumber() {
		return pageNumber;
	}

	/** 设置是否显示页码 */
	public void setPageNumber(boolean pageNumber) {
		this.pageNumber = pageNumber;
	}

	/** 获得背景颜色 */
	public int getBackColor() {
		return backColor;
	}

	/** 设置背景颜色 */
	public void setBackColor(int backColor) {
		this.backColor = backColor;
	}

	/**
	 *TODO 返回纸张的宽度，像素值
	 *
	 *@return
	 */
	public int getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}
	/**
	 *TODO 返回纸张的高度，像素值
	 *
	 *@return
	 */
	public int getPageHeight() {
		return pageHeight;
	}
	
	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}

	public int getCoreWidth() {
		this.coreWidth = this.pageWidth - this.leftPageMargin - this.rightPageMargin;
		return this.coreWidth;
	}
	
	public void setCoreWidth(int coreWidth) {
		this.coreWidth = coreWidth;
	}

	public int getCoreHeight() {
		this.coreHeight = this.pageHeight - this.upMargin - this.downMargin;
		return this.coreHeight;
	}
	
	public void setCoreHeight(int coreHeight) {
		this.coreHeight = coreHeight;
	}
	/** 获得页的左边距 */
	public int getLeftPageMargin() {
		return leftPageMargin;
	}

	/** 设置页的左边距 */
	public void setLeftPageMargin(int leftPageMargin) {
		this.leftPageMargin = leftPageMargin;
	}

	/** 获得页的右边距 */
	public int getRightPageMargin() {
		return rightPageMargin;
	}

	/** 设置页的右边距 */
	public void setRightPageMargin(int rightPageMargin) {
		this.rightPageMargin = rightPageMargin;
	}

	/** 获得页的上边距 */
	public int getUpMargin() {
		return upMargin;
	}

	/** 设置页的上边距 */
	public void setUpMargin(int upMargin) {
		this.upMargin = upMargin;
	}

	/** 获得页的下边距 */
	public int getDownMargin() {
		return downMargin;
	}

	/** 设置页的下边距 */
	public void setDownMargin(int downMargin) {
		this.downMargin = downMargin;
	}

	/** 获得字符单元之间间距 */
	public int getUnitInterval() {
		return unitInterval;
	}

	/** 设置字符单元之间间距 */
	public void setUnitInterval(int unitInterval) {
		this.unitInterval = unitInterval;
	}
	
	/** 获得页眉数据 */
	public List<DataUnit> getHeadData() {
		return headData;
	}

	/** 添加页眉数据 */
	public void addHeadData(DataUnit data) {
		this.headData.add(data);
	}

	/** 获得页脚数据 */
	public List<DataUnit> getFootData() {
		return footData;
	}

	/** 添加页脚数据 */
	public void setFootData(DataUnit data) {
		this.footData.add(data);
	}

	/** 页码位置 */
	public enum PageNumLocation {
		/** 页眉左边 */
		LeftOfHeader,
		/** 页眉右边 */
		RightOfHeader,
		/** 页眉居中 */
		MiddleOfHeader,
		/** 页脚左边 */
		LeftOfFooter,
		/** 页脚右边 */
		RightOfFooter,
		/** 页脚居中 */
		MiddleOfFooter
	}
	
	/** 页边距的单位 有 毫米、像素、厘米， 磅 三种,最后都转换为像素为单位的值 */
	public enum UnitOfInterval {
		/** 毫米 */
		Millimetre(3.7795), 
		/** 像素 */
		Pixel(1),
		/** 英寸 */
		Inch(96),
		/** 磅 */
		Bound(1.333);
		
		UnitOfInterval(double value){
			this.unit = value;
		}
		
		public double getUnit() {
			return this.unit;
		}
		private double unit;
	}

	/** 纸张大小的样式 */
	public enum PaperSize {
		/** A4 纸*/
		A4(1),
		/** B5 纸*/
		B5(2),
		/** 自定义 */
		Other(3);
		
		PaperSize(int tag){
			this.tag = tag;
		}
		
		public int getVaule() {
			return tag;
		}
		private int tag;
	}
}
