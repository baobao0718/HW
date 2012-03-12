package com.study.android.code;


/**
 * 接收数据处理器，所有方法只有三种类型分别为：<br>
 * startXXX() 对应 endXXX() <br>
 * setXXX() <br>
 * addXXX() <br>
 * 方法的调用必须要按照顺序执行，例如setColorTable必须是第一个要调用的方法
 * @author 小强
 *
 */
public interface DataHandler {

	/**
	 * 设置颜色表
	 * @param colors 颜色数组
	 * @throws ParseException
	 */
	public void setColorTable(int[] colors)  throws ParseException;
	
	/**
	 * 开始字符单元
	 * @param style 样式
	 * @param color 颜色
	 * @param formatHeight 字体格式高度
	 * @param strokeWidth 笔画基准宽度
	 * @param charHeight 字体高度
	 * @throws ParseException    解析异常
	 */
	public void startCharUnit(byte style, byte color, byte formatHeight, 
			byte strokeWidth, short charHeght) throws ParseException;
	
	/**
	 * 添加点，当end = true时，<b>x和y无效</b>
	 * @param x
	 * @param y
	 * @param end 是否结束点
	 * @throws ParseException
	 */
	public void addPoint(short x, short y, boolean end)  throws ParseException;
	
	/**
	 * 结束字符单元
	 * @throws ParseException
	 */
	public void endCharUnit() throws ParseException;
	
	/**
	 * 开始层单元，一个嵌入的小文档
	 * @param roundStyle 环绕方式
	 * @param height 高度
	 * @param width 宽度
	 * @throws ParseException
	 */
	public void startLayerUnit(byte roundStyle, int height, int width) throws ParseException;
	
	/**
	 * 结束层单元
	 * @throws ParseException
	 */
	public void endLayerUnit() throws ParseException;
	
	/**
	 * 开始图形单元
	 * @param roundStyle 环绕方式
	 * @param height 高度
	 * @param width 宽度
	 * @throws ParseException
	 * @see 添加开始点请使用 addPoint(short x, short y, byte width, byte color)
	 * @see 添加普通点请使用 addPoint(short x, short y, boolean end)
	 */
	public void startImageUnit(byte roundStyle, int height, int width) throws ParseException;
	
	/**
	 * 添加开始点，适用于图形单元和书法单元
	 * @param x
	 * @param y
	 * @param width ？？
	 * @param color 颜色
	 * @throws ParseException
	 * @see 添加普通点请使用 addPoint(short x, short y, boolean end)
	 */
	public void addPoint(short x, short y, byte width, byte color) throws ParseException;
	
	/**
	 * 结束图形单元
	 * @throws ParseException
	 */
	public void endImageUnit() throws ParseException;
	
	/**
	 * 开始书法单元
	 * @param roundStyle 环绕方式
	 * @param height 高度
	 * @param width 宽度
	 * @throws ParseException
	 * @see 添加开始点请使用 addPoint(short x, short y, byte width, byte color)
	 * @see 添加普通点请使用 addPoint(short x, short y, boolean end)
	 */
	//public void startCalligraphyUnit(byte roundStyle, int height, int width) throws ParseException;
	
	/**
	 * 结束图形单元
	 * @throws ParseException
	 */
	//public void endCalligraphyUnit() throws ParseException; 
	
	/**
	 * 添加位图单元
	 * @param roundStyle 环绕方式
	 * @param height 高度
	 * @param width 宽度
	 * @param pixels 位图数据
	 * @throws ParseException
	 */
	public void addBitmapUnit(byte roundStyle, int height, int width, int[] pixels) throws ParseException;
	
	/**
	 * 添加空格
	 * @param width 空格宽度
	 * @throws ParseException
	 */
	public void addSpaceUnit(short width) throws ParseException;
	
	/**
	 * 添加换行
	 * @param width 换行宽度
	 * @throws ParseException
	 */
	public void addEnterUnit(short width) throws ParseException;
	
	/**
	 * 开始页（节）控制单元，随后设置属性
	 * @throws ParseException
	 */
	public void startPageUnit(boolean changFlag, byte style) throws ParseException;
	
	/**
	 * 设置纸张大小
	 * @param height 纸张高度
	 * @param width 纸张宽度
	 * @throws ParseException
	 */
	public void setPageSize(short height, short width) throws ParseException;
	
	/**
	 * 设置页边距
	 * @param up
	 * @param down
	 * @param left
	 * @param right
	 * @throws ParseException
	 */
	public void setPageMargin(short up, short down, short left, short right) throws ParseException;
	
	/**
	 * 设置页码
	 * @param position 页码位置
	 * @param isShowSum 是否显示总页码
	 * @param style 样式
	 * @param startNumber 起始页码
	 * @throws ParseException
	 */
	public void setPageNumber(byte position, byte isShowSum, byte style, short startNumber) throws ParseException;
	
	/**
	 * 设置背景，当前版本未定义
	 * @throws ParseException
	 */
	public void setBackGround() throws ParseException;
	
	/**
	 * 设置纵横方向
	 * @param orient true纵，false横
	 * @throws ParseException
	 */
	public void setOrient(boolean orient) throws ParseException;
	
	/**
	 * 设置页边距的单位
	 * @param ut
	 * @throws ParseException
	 */
	public void setUnitType(byte ut) throws ParseException;
	
	/**
	 * 设置分栏数
	 * @param snaking
	 * @throws ParseException
	 */
	public void setSnakingNumber(byte snaking) throws ParseException;
	
	/**
	 * 开始页眉
	 * @throws ParseException
	 */
	public void startPageHeader() throws ParseException;
	
	/**
	 * 结束页眉
	 * @throws ParseException
	 */
	
	
	public void setcolor(byte num) throws ParseException;

	public void endPageHeader() throws ParseException;
	
	/**
	 * 开始页脚
	 * @throws ParseException
	 */
	public void startPageFooter() throws ParseException;
	
	/**
	 * 结束页脚
	 * @throws ParseException
	 */
	public void endPageFooter() throws ParseException;
	
	/**
	 * 结束页（节）控制单元
	 * @throws ParseException
	 */
	public void endPageUnit() throws ParseException;
	
	/**
	 * TODO 开始段单元，之后考虑是否设置段的属性
	 * @throws ParseException
	 */
	public void startParagraph() throws ParseException;
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
			byte lineSpaceType, byte lineSpace, byte beforeParaSpace, byte afterParaSpace) throws ParseException;

	public void endCharUnit(short charactersize) throws ParseException;

}
