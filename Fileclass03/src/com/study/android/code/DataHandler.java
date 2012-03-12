package com.study.android.code;


/**
 * �������ݴ����������з���ֻ���������ͷֱ�Ϊ��<br>
 * startXXX() ��Ӧ endXXX() <br>
 * setXXX() <br>
 * addXXX() <br>
 * �����ĵ��ñ���Ҫ����˳��ִ�У�����setColorTable�����ǵ�һ��Ҫ���õķ���
 * @author Сǿ
 *
 */
public interface DataHandler {

	/**
	 * ������ɫ��
	 * @param colors ��ɫ����
	 * @throws ParseException
	 */
	public void setColorTable(int[] colors)  throws ParseException;
	
	/**
	 * ��ʼ�ַ���Ԫ
	 * @param style ��ʽ
	 * @param color ��ɫ
	 * @param formatHeight �����ʽ�߶�
	 * @param strokeWidth �ʻ���׼���
	 * @param charHeight ����߶�
	 * @throws ParseException    �����쳣
	 */
	public void startCharUnit(byte style, byte color, byte formatHeight, 
			byte strokeWidth, short charHeght) throws ParseException;
	
	/**
	 * ��ӵ㣬��end = trueʱ��<b>x��y��Ч</b>
	 * @param x
	 * @param y
	 * @param end �Ƿ������
	 * @throws ParseException
	 */
	public void addPoint(short x, short y, boolean end)  throws ParseException;
	
	/**
	 * �����ַ���Ԫ
	 * @throws ParseException
	 */
	public void endCharUnit() throws ParseException;
	
	/**
	 * ��ʼ�㵥Ԫ��һ��Ƕ���С�ĵ�
	 * @param roundStyle ���Ʒ�ʽ
	 * @param height �߶�
	 * @param width ���
	 * @throws ParseException
	 */
	public void startLayerUnit(byte roundStyle, int height, int width) throws ParseException;
	
	/**
	 * �����㵥Ԫ
	 * @throws ParseException
	 */
	public void endLayerUnit() throws ParseException;
	
	/**
	 * ��ʼͼ�ε�Ԫ
	 * @param roundStyle ���Ʒ�ʽ
	 * @param height �߶�
	 * @param width ���
	 * @throws ParseException
	 * @see ��ӿ�ʼ����ʹ�� addPoint(short x, short y, byte width, byte color)
	 * @see �����ͨ����ʹ�� addPoint(short x, short y, boolean end)
	 */
	public void startImageUnit(byte roundStyle, int height, int width) throws ParseException;
	
	/**
	 * ��ӿ�ʼ�㣬������ͼ�ε�Ԫ���鷨��Ԫ
	 * @param x
	 * @param y
	 * @param width ����
	 * @param color ��ɫ
	 * @throws ParseException
	 * @see �����ͨ����ʹ�� addPoint(short x, short y, boolean end)
	 */
	public void addPoint(short x, short y, byte width, byte color) throws ParseException;
	
	/**
	 * ����ͼ�ε�Ԫ
	 * @throws ParseException
	 */
	public void endImageUnit() throws ParseException;
	
	/**
	 * ��ʼ�鷨��Ԫ
	 * @param roundStyle ���Ʒ�ʽ
	 * @param height �߶�
	 * @param width ���
	 * @throws ParseException
	 * @see ��ӿ�ʼ����ʹ�� addPoint(short x, short y, byte width, byte color)
	 * @see �����ͨ����ʹ�� addPoint(short x, short y, boolean end)
	 */
	//public void startCalligraphyUnit(byte roundStyle, int height, int width) throws ParseException;
	
	/**
	 * ����ͼ�ε�Ԫ
	 * @throws ParseException
	 */
	//public void endCalligraphyUnit() throws ParseException; 
	
	/**
	 * ���λͼ��Ԫ
	 * @param roundStyle ���Ʒ�ʽ
	 * @param height �߶�
	 * @param width ���
	 * @param pixels λͼ����
	 * @throws ParseException
	 */
	public void addBitmapUnit(byte roundStyle, int height, int width, int[] pixels) throws ParseException;
	
	/**
	 * ��ӿո�
	 * @param width �ո���
	 * @throws ParseException
	 */
	public void addSpaceUnit(short width) throws ParseException;
	
	/**
	 * ��ӻ���
	 * @param width ���п��
	 * @throws ParseException
	 */
	public void addEnterUnit(short width) throws ParseException;
	
	/**
	 * ��ʼҳ���ڣ����Ƶ�Ԫ�������������
	 * @throws ParseException
	 */
	public void startPageUnit(boolean changFlag, byte style) throws ParseException;
	
	/**
	 * ����ֽ�Ŵ�С
	 * @param height ֽ�Ÿ߶�
	 * @param width ֽ�ſ��
	 * @throws ParseException
	 */
	public void setPageSize(short height, short width) throws ParseException;
	
	/**
	 * ����ҳ�߾�
	 * @param up
	 * @param down
	 * @param left
	 * @param right
	 * @throws ParseException
	 */
	public void setPageMargin(short up, short down, short left, short right) throws ParseException;
	
	/**
	 * ����ҳ��
	 * @param position ҳ��λ��
	 * @param isShowSum �Ƿ���ʾ��ҳ��
	 * @param style ��ʽ
	 * @param startNumber ��ʼҳ��
	 * @throws ParseException
	 */
	public void setPageNumber(byte position, byte isShowSum, byte style, short startNumber) throws ParseException;
	
	/**
	 * ���ñ�������ǰ�汾δ����
	 * @throws ParseException
	 */
	public void setBackGround() throws ParseException;
	
	/**
	 * �����ݺ᷽��
	 * @param orient true�ݣ�false��
	 * @throws ParseException
	 */
	public void setOrient(boolean orient) throws ParseException;
	
	/**
	 * ����ҳ�߾�ĵ�λ
	 * @param ut
	 * @throws ParseException
	 */
	public void setUnitType(byte ut) throws ParseException;
	
	/**
	 * ���÷�����
	 * @param snaking
	 * @throws ParseException
	 */
	public void setSnakingNumber(byte snaking) throws ParseException;
	
	/**
	 * ��ʼҳü
	 * @throws ParseException
	 */
	public void startPageHeader() throws ParseException;
	
	/**
	 * ����ҳü
	 * @throws ParseException
	 */
	
	
	public void setcolor(byte num) throws ParseException;

	public void endPageHeader() throws ParseException;
	
	/**
	 * ��ʼҳ��
	 * @throws ParseException
	 */
	public void startPageFooter() throws ParseException;
	
	/**
	 * ����ҳ��
	 * @throws ParseException
	 */
	public void endPageFooter() throws ParseException;
	
	/**
	 * ����ҳ���ڣ����Ƶ�Ԫ
	 * @throws ParseException
	 */
	public void endPageUnit() throws ParseException;
	
	/**
	 * TODO ��ʼ�ε�Ԫ��֮�����Ƿ����öε�����
	 * @throws ParseException
	 */
	public void startParagraph() throws ParseException;
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
			byte lineSpaceType, byte lineSpace, byte beforeParaSpace, byte afterParaSpace) throws ParseException;

	public void endCharUnit(short charactersize) throws ParseException;

}
