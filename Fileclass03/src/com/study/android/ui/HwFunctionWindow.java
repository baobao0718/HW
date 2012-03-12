package com.study.android.ui;

//import hitsz.handwriting.data.MyPoint;
import android.graphics.Color;

import com.study.android.basicData.MyPoint;
import com.study.android.basicData.type.CharFormat;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 功能提示窗口
 * 
 * @author: tanqiang
 * @create-time: 2007-12-21
 */
public class HwFunctionWindow  {

	private static final long serialVersionUID = 1L;

	/** ****************@参数**************** */
	private static int frameWidth = 738;

	private static int frameHeight = 80;

	private static int panelWidth = 730;

	private static int panelHeight = 140;// 输入面板的高度

	/** *************@组件******************** */
	
	/** *****************@变量**************** */
	private CharFormat curCharFormat;
	
	private byte charHeight = CharFormat.defaultHeight;
	
	//private final ArrayList<MyPoint> points;// 保存点的信息

	private float strokeWidth = 2.0f;// 笔划的宽度

//	private Color color = Color.black;// 字体颜色，默认为黑色

	private static int state = 1;// 功能按钮选择状态：1表示作为文字保存，2表示作为图片保存，3表示作为编辑符

	private boolean strokeOver = false;// 一笔的结束标志，初始化一定是false

	private boolean hasWritePanel = false;// 是否存在输入板

	private boolean writeInPanel = false;// 是否在当前的输入面板中输入

	//private Timer saveSchedule;// 定时保存

	public static List<Integer> colortable = new ArrayList<Integer>();// 调色板

	//public static int GlobalIndex = 0;// 全局索引, 这个值貌似没有用啊

	public static boolean bMandatoryEnter = false;// 强制换行标记

	/** ***************@常量****************** */
	private final static String[] colorName = { "蓝色", "红色", "黑色", "黄色", "灰色",
			"绿色", "橙色", "粉红","紫红" };             //加了个紫色出现了BUG，应该整体增加，全局更改！！找到了，就在下面

	//private final static Color[] colors = { Color.blue, Color.red, Color.black,
	//		Color.yellow, Color.darkGray, Color.green, Color.orange, Color.pink ,Color.MAGENTA};

	//private final static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);// 手形光标

	//private final static Color buttonColor = new Color(204, 204, 255);     //按钮颜色？？不理解-->对，分别是R，G，B，的值

	//public static int defaultColor = Color.BLUE.getRGB();      //默认写字面板手写体颜色为蓝色
	public static int defaultColor =Color.rgb(0,0,255);      //默认写字面板手写体颜色为蓝色的RGB值

	//private static Color drawColor = Color.rgb(0,0,255);

	private static final int delay = 1000;// 定时保存周期    

	public static final int iTwoSpace = 80;// 缩进段首空两格间距

	//public static final DrawingTool drawingTool = DrawingTool.getInstance();	
	private boolean enlargeFlag = false;//标志面板是否加长了，初始时刻没有加长
	
//	private boolean startEnlarge = false;//标志面板加长的时机
	
	private boolean popOut = false;//标志面板是否现在先屏幕中，初始时刻没有显示
	
	private static final int enlarge = 120;//扩展面板的时候加长的长度
	

	/**
	 * 构造方法
	 * 
	 * @param panel
	 * @param frame
	 * @param editExecuter
	 */
	/**
	 * 初始化工具条
	 */
	public static void Initcolortable()
	{
		//if(HwFunctionWindow.colortable.contains(Color.rgb(0, 0, 255))==false)//zhuxiaoqing 2011.06.14
		colortable.add(Color.rgb(0,0,255));             //添加蓝色的RGB值
	
		
		/*这快是为了能够添加让图片显示颜色所新更改的！！！*/
//		colortable.add(Color.rgb(255,0,0));				   //红色RGB
//		colortable.add(Color.rgb(0,0,0));
//		colortable.add(Color.rgb(255,255,0));
//		colortable.add(Color.rgb(192,192,192));
		//colortable.add(Color.rgb(192,192,192));
//		colortable.add(Color.rgb(0,255,0));
		//colortable.add(Color.rgb(192,192,192));                      
//		colortable.add(Color.rgb(255,0,255));                         //粉红和橙色没找到，所以先不添加
	}

	/**
	 * 初始化功能按钮面板
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
	 * 画板：可以在此画板中输入
	 * 
	 * @author: tanqiang
	 * @create-time: 2008-1-9
	 */

	
	
	public CharFormat getCharFormat() {
		
		//int color = drawColor.getRGB();
		int color = Color.rgb(0,0,255);                 //这里先设成定值为蓝色
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