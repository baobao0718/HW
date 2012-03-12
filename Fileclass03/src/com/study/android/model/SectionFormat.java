package com.study.android.model;
import com.study.android.basicData.DataUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * ����ڵ��������ͣ�����һ���ڱ��洢�ýڵ�һ��Ԫ��Ӧ��ȫ������
 *@author ���˸�
 *@version �������� 2008-9-2 ����10:35:58
 */
public class SectionFormat {
	
//	/** Ĭ��ҳ�Ŀ�� 595 */
//	public static int defaulePageWidth = 595;
//	
//	/** Ĭ��ҳ�ĸ߶� 842 */
//	public static int defaultPageHeight = 842;
//	
//	/** Ĭ��ҳ�ĵĿ�� 451*/
//	public static int defaultCoreWidth = 451;
//	
//	/** Ĭ��ҳ�ĵĸ߶� 698*/
//	public static int defautCoreHeight = 698;
//	
//	/** Ĭ��ҳ��߾� 72*/
//	public static int defaultLeftPageMargin = 72;
//	
//	/** Ĭ��ҳ�ұ߾� 72*/
//	public static int defaultRightPageMargin = 72;
//	
//	/** Ĭ���ϱ߾� 72*/
//	public static int defaultUpMargin = 72;
//	
//	/** Ĭ���±߾� 72*/
//	public static int defaultDownMargin = 72;
	
	/** Ĭ�� B5 ҳ�Ŀ�� 665*/
	public static int defaultPageWidth_B5 = 665;
	
	/** Ĭ�� B5 ҳ�ĸ߶� 945*/
	public static int defaultPageHeight_B5 = 945;
	
	/** Ĭ�� B5 ҳ�ĵĿ�� 553*/
	public static int defaultCoreWidth_B5 = 425;
	
	/** Ĭ�� B5 ҳ�ĵĸ߶� 930*/
	public static int defautCoreHeight_B5 = 753;
	
	/** Ĭ��ҳ�Ŀ�� 793 */
	public static int defaulePageWidth = 793;
	
	/** Ĭ��ҳ�ĸ߶� 1122 */
	public static int defaultPageHeight = 1122;
	
	/** Ĭ��ҳ�ĵĿ�� 553*/
	public static int defaultCoreWidth = 553;
	
	/** Ĭ��ҳ�ĵĸ߶� 930*/
	public static int defautCoreHeight = 930;
	
	/** Ĭ��ҳ��߾� 120*/
	public static int defaultLeftPageMargin = 120;
	
	/** Ĭ��ҳ�ұ߾� 120*/
	public static int defaultRightPageMargin = 120;
	
	/** Ĭ���ϱ߾� 96*/
	public static int defaultUpMargin = 96;
	
	/** Ĭ���±߾� 96*/
	public static int defaultDownMargin = 96;
	
	/** Ĭ�ϵ�Ԫ֮��ļ���� ����Ӧ�÷�������� */
	public static int defaultInitInterval = 8;
	
	//public static int defaultBackColor = Color.WHITE.getRGB();                     //����ʱ�������
	
	/** Ĭ�ϵ�ֽ�Ÿ�ʽ <b>A4��С</b>*/
	public static PaperSize defaultPaperSize = PaperSize.A4;
	
	/**<b>�ݺ��־�� Ĭ��Ϊ ���� </b>*/
	public static boolean defaultVertically = true;
	
	/**<b>Ĭ����û��ҳü</b>*/
	public static boolean defaultHeader = false;
	
	/**<b>Ĭ����û��ҳ��</b>*/
	public static boolean defaultFooter = false;
	
	/**<b>Ĭ����û��ҳ��</b>*/
	public static boolean defaultPageNum = false;
	
	private static SectionFormat defaultSectionFormat = null;
	
	/** ֽ�Ŵ�С A4, B5, �Զ��� */
	private PaperSize paperSize;
	
	/** �ݺ��־,true��ʾ���� false��ʾ���� */
	private boolean vertically;

	/** ҳü */
	private boolean header;
	
	/** ҳ�� */
	private boolean footer;
	
	/** ҳ��λ�� */
	private PageNumLocation pageNumLocation;
	
	/** ҳ�� */
	private boolean pageNumber;
	
	/** ҳ�汳����ɫ */
	private int backColor;
	
	/** ҳ�Ŀ�� */
	private int pageWidth;
	
	/** ҳ�ĸ߶� */
	private int pageHeight;
	
	/** ҳ�ĵĿ�� */
	private int coreWidth;
	
	/** ҳ�ĵĸ߶� */
	private int coreHeight;
	
	/** ҳ��߾� */
	private int leftPageMargin;
	
	/** ҳ�ұ߾� */
	private int rightPageMargin;
	
	/** �ϱ߾� */
	private int upMargin;
	
	/** �±߾� */
	private int downMargin;
	
	/** ��Ԫ֮��ļ���� ����Ӧ�÷�������� */
	private int unitInterval;
	
	/** ҳü������ */
	private List<DataUnit> headData;
	
	/** ҳ�ŵ����� */
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
	 * TODO <b>Ŀǰֻ����7������������̫�࣬�о��������ƵĲ��ã��д��Ż�<br>
	 * @param coreWidth ҳ�ĵĿ��
	 * @param coreHeight ҳ�ĵĸ߶�
	 * @param leftPageMargin ҳ��߾�
	 * @param rightPageMargin ҳ�ұ߾�
	 * @param upMargin ҳ�ϱ߾�
	 * @param downMargin ҳ�±߾�
	 * @param vertically ҳ���ݺ��־
	 * @param paperSize ֽ�Ŵ�С
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
	

	/** ���ֽ�Ŵ�С*/
	public PaperSize getPaperSize() {
		return this.paperSize;
	}
	
	/** ����ֽ�Ŵ�С */
	public void setPaperSize(PaperSize paperSize) {
		this.paperSize = paperSize;
	}
	
	/** �����ݺ��ǣ�true Ϊ�ݣ�false Ϊ�� */
	public boolean isVertically() {
		return vertically;
	}

	/** �����ݺ��ǣ�true Ϊ�ݣ�false Ϊ�� */
	public void setVertically(boolean vertically) {
		this.vertically = vertically;
	}

	/** �Ƿ���ʾҳü */
	public boolean isHeader() {
		return header;
	}

	/** �����Ƿ���ʾҳü */
	public void setHeader(boolean header) {
		this.header = header;
	}

	/** �Ƿ���ʾҳ�� */
	public boolean isFooter() {
		return footer;
	}

	/** �����Ƿ���ʾҳ�� */
	public void setFooter(boolean footer) {
		this.footer = footer;
	}

	/** ���ҳ��λ�� */
	public PageNumLocation getPageNumLocation() {
		return pageNumLocation;
	}

	/** ����ҳ��λ�� */
	public void setPageNumLocation(PageNumLocation pageNumLocation) {
		this.pageNumLocation = pageNumLocation;
	}

	/** �Ƿ���ʾҳ�� */
	public boolean isPageNumber() {
		return pageNumber;
	}

	/** �����Ƿ���ʾҳ�� */
	public void setPageNumber(boolean pageNumber) {
		this.pageNumber = pageNumber;
	}

	/** ��ñ�����ɫ */
	public int getBackColor() {
		return backColor;
	}

	/** ���ñ�����ɫ */
	public void setBackColor(int backColor) {
		this.backColor = backColor;
	}

	/**
	 *TODO ����ֽ�ŵĿ�ȣ�����ֵ
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
	 *TODO ����ֽ�ŵĸ߶ȣ�����ֵ
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
	/** ���ҳ����߾� */
	public int getLeftPageMargin() {
		return leftPageMargin;
	}

	/** ����ҳ����߾� */
	public void setLeftPageMargin(int leftPageMargin) {
		this.leftPageMargin = leftPageMargin;
	}

	/** ���ҳ���ұ߾� */
	public int getRightPageMargin() {
		return rightPageMargin;
	}

	/** ����ҳ���ұ߾� */
	public void setRightPageMargin(int rightPageMargin) {
		this.rightPageMargin = rightPageMargin;
	}

	/** ���ҳ���ϱ߾� */
	public int getUpMargin() {
		return upMargin;
	}

	/** ����ҳ���ϱ߾� */
	public void setUpMargin(int upMargin) {
		this.upMargin = upMargin;
	}

	/** ���ҳ���±߾� */
	public int getDownMargin() {
		return downMargin;
	}

	/** ����ҳ���±߾� */
	public void setDownMargin(int downMargin) {
		this.downMargin = downMargin;
	}

	/** ����ַ���Ԫ֮���� */
	public int getUnitInterval() {
		return unitInterval;
	}

	/** �����ַ���Ԫ֮���� */
	public void setUnitInterval(int unitInterval) {
		this.unitInterval = unitInterval;
	}
	
	/** ���ҳü���� */
	public List<DataUnit> getHeadData() {
		return headData;
	}

	/** ���ҳü���� */
	public void addHeadData(DataUnit data) {
		this.headData.add(data);
	}

	/** ���ҳ������ */
	public List<DataUnit> getFootData() {
		return footData;
	}

	/** ���ҳ������ */
	public void setFootData(DataUnit data) {
		this.footData.add(data);
	}

	/** ҳ��λ�� */
	public enum PageNumLocation {
		/** ҳü��� */
		LeftOfHeader,
		/** ҳü�ұ� */
		RightOfHeader,
		/** ҳü���� */
		MiddleOfHeader,
		/** ҳ����� */
		LeftOfFooter,
		/** ҳ���ұ� */
		RightOfFooter,
		/** ҳ�ž��� */
		MiddleOfFooter
	}
	
	/** ҳ�߾�ĵ�λ �� ���ס����ء����ף� �� ����,���ת��Ϊ����Ϊ��λ��ֵ */
	public enum UnitOfInterval {
		/** ���� */
		Millimetre(3.7795), 
		/** ���� */
		Pixel(1),
		/** Ӣ�� */
		Inch(96),
		/** �� */
		Bound(1.333);
		
		UnitOfInterval(double value){
			this.unit = value;
		}
		
		public double getUnit() {
			return this.unit;
		}
		private double unit;
	}

	/** ֽ�Ŵ�С����ʽ */
	public enum PaperSize {
		/** A4 ֽ*/
		A4(1),
		/** B5 ֽ*/
		B5(2),
		/** �Զ��� */
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
