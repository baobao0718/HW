package com.study.android.ui;

//import hitsz.handwriting.data.MyPoint;
import android.graphics.Color;

import com.study.android.basicData.MyPoint;
import com.study.android.basicData.type.CharFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * ������ʾ����
 * 
 * @author: tanqiang
 * @create-time: 2007-12-21
 */
public class HwFunctionWindow  {

	private static final long serialVersionUID = 1L;

	/** ****************@����**************** */
	private static int frameWidth = 738;

	private static int frameHeight = 80;

	private static int panelWidth = 730;

	private static int panelHeight = 140;// �������ĸ߶�

	/** *************@���******************** */
	
	/** *****************@����**************** */
	private CharFormat curCharFormat;
	
	private byte charHeight = CharFormat.defaultHeight;
	
	//private final ArrayList<MyPoint> points;// ��������Ϣ

	private float strokeWidth = 2.0f;// �ʻ��Ŀ��

//	private Color color = Color.black;// ������ɫ��Ĭ��Ϊ��ɫ

	private static int state = 1;// ���ܰ�ťѡ��״̬��1��ʾ��Ϊ���ֱ��棬2��ʾ��ΪͼƬ���棬3��ʾ��Ϊ�༭��

	private boolean strokeOver = false;// һ�ʵĽ�����־����ʼ��һ����false

	private boolean hasWritePanel = false;// �Ƿ���������

	private boolean writeInPanel = false;// �Ƿ��ڵ�ǰ���������������

	//private Timer saveSchedule;// ��ʱ����

	public static List<Integer> colortable = new ArrayList<Integer>();// ��ɫ��

	//public static int GlobalIndex = 0;// ȫ������, ���ֵò��û���ð�

	public static boolean bMandatoryEnter = false;// ǿ�ƻ��б��

	/** ***************@����****************** */
	private final static String[] colorName = { "��ɫ", "��ɫ", "��ɫ", "��ɫ", "��ɫ",
			"��ɫ", "��ɫ", "�ۺ�","�Ϻ�" };             //���˸���ɫ������BUG��Ӧ���������ӣ�ȫ�ָ��ģ����ҵ��ˣ���������

	//private final static Color[] colors = { Color.blue, Color.red, Color.black,
	//		Color.yellow, Color.darkGray, Color.green, Color.orange, Color.pink ,Color.MAGENTA};

	//private final static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);// ���ι��

	//private final static Color buttonColor = new Color(204, 204, 255);     //��ť��ɫ���������-->�ԣ��ֱ���R��G��B����ֵ

	//public static int defaultColor = Color.BLUE.getRGB();      //Ĭ��д�������д����ɫΪ��ɫ
	public static int defaultColor =Color.rgb(0,0,255);      //Ĭ��д�������д����ɫΪ��ɫ��RGBֵ

	//private static Color drawColor = Color.rgb(0,0,255);

	private static final int delay = 1000;// ��ʱ��������    

	public static final int iTwoSpace = 80;// �������׿�������

	//public static final DrawingTool drawingTool = DrawingTool.getInstance();	
	private boolean enlargeFlag = false;//��־����Ƿ�ӳ��ˣ���ʼʱ��û�мӳ�
	
//	private boolean startEnlarge = false;//��־���ӳ���ʱ��
	
	private boolean popOut = false;//��־����Ƿ���������Ļ�У���ʼʱ��û����ʾ
	
	private static final int enlarge = 120;//��չ����ʱ��ӳ��ĳ���
	

	/**
	 * ���췽��
	 * 
	 * @param panel
	 * @param frame
	 * @param editExecuter
	 */
	/**
	 * ��ʼ��������
	 */
	public static void Initcolortable()
	{
		//if(HwFunctionWindow.colortable.contains(Color.rgb(0, 0, 255))==false)//zhuxiaoqing 2011.06.14
		colortable.add(Color.rgb(0,0,255));             //�����ɫ��RGBֵ
	
		
		/*�����Ϊ���ܹ������ͼƬ��ʾ��ɫ���¸��ĵģ�����*/
//		colortable.add(Color.rgb(255,0,0));				   //��ɫRGB
//		colortable.add(Color.rgb(0,0,0));
//		colortable.add(Color.rgb(255,255,0));
//		colortable.add(Color.rgb(192,192,192));
		//colortable.add(Color.rgb(192,192,192));
//		colortable.add(Color.rgb(0,255,0));
		//colortable.add(Color.rgb(192,192,192));                      
//		colortable.add(Color.rgb(255,0,255));                         //�ۺ�ͳ�ɫû�ҵ��������Ȳ����
	}

	/**
	 * ��ʼ�����ܰ�ť���
	 */


	public float getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(float strokeWidth) {
		this.strokeWidth = strokeWidth;
		//drawingTool.setStrokeWidth(strokeWidth);
	}

	public boolean getWriteInPanel() {
		return writeInPanel;
	}

	public void setWriteInPanel(boolean writeInPanel) {
		this.writeInPanel = writeInPanel;
	}

	public boolean isHasWritePanel() {
		return hasWritePanel;
	}

	public void setHasWritePanel(boolean hasWritePanel) {
		this.hasWritePanel = hasWritePanel;
	}

	public byte getCharHeight() {
		return this.charHeight;
	}
	
	public void setCharHeight(String value) {
		this.charHeight = (byte) Integer.parseInt(value);
	}

	/**
	 * ���壺�����ڴ˻���������
	 * 
	 * @author: tanqiang
	 * @create-time: 2008-1-9
	 */

	
	
	public CharFormat getCharFormat() {
		
		//int color = drawColor.getRGB();
		int color = Color.rgb(0,0,255);                 //��������ɶ�ֵΪ��ɫ
		byte height = getCharHeight();
		byte strokeWidth = (byte) getStrokeWidth();
		byte style = 0;
//		System.err.println("\n height : " + height);
		CharFormat cf = CharFormat.getCharFormat(color, height, strokeWidth, style);
//		System.err.println("1111 height : " + cf.getHeight() +
//				"  color : " + cf.getColor() + " strokeWidth : " + cf.getStrokeWidth());
		return cf;
	}
	
	public void setCharFormat(CharFormat cf) {
		curCharFormat = cf;
//		System.err.println("2222 height : " + curCharFormat.getHeight() +
//				"  color : " + curCharFormat.getColor() +
//				" strokeWidth : " + curCharFormat.getStrokeWidth());	
		//editDFA.setCurCharFormat(cf);
	}
}