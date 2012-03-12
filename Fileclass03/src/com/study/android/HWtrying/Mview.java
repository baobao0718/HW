package com.study.android.HWtrying;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.Paint.Style;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.study.android.CollectionduringWriting.addtoListTable;
import com.study.android.ContextState.Context_STATE;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.CharPoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.MyPoint;
import com.study.android.basicData.MyStroke;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.CharUnit;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.basicData.type.ImageUnit;
import com.study.android.data.ListTable;
import com.study.android.edit.EditDFA;
import com.study.android.ink.InkPen;
import com.study.android.ui.HwFunctionWindow;
import com.study.android.zoom.Zoom;

public class Mview extends GLSurfaceView
{

	// <chenzou 2010.11.06 GLSurfaceView>
	// public class Mview extends GLSurfaceView implements
	// SurfaceHolder.Callback{
	// </chenzou>
	protected SurfaceHolder surfacehold;
	protected float x = 0;
	protected float y = 0;
	protected int location = 0;
	public short innerx = 80;
	public short innery = 80;
	public short copyofinnerx = 0, copyofinnery = 0;
	protected int bgcolor = ListTable.bgcolor;
	private short characterstrokewidth = (short) ListTable.characterstrokewidth;
	protected short charactersize = (short) ListTable.charactersize;
	protected static short screenwidth = Kview.screenwidth,
			screenheight = Kview.screenheight;
	private short innertextwidthspace = 10, innertextheightspace = 10;
	/**
	 * 最低的一条参考线距离视图底部的距离
	 */
	public static short bottomofshuxiebaseline = (short) (ListTable.ScreenHeight/5);
	protected static short shuxiejizhun = (short) ListTable.shuxiejizhun;

	public float minX = 1000, minY = 1000, maxX = 0, maxY = 0;
	public float copyofminX = 0, copyofminY = 0, copyofmaxX = 0,
			copyofmaxY = 0; // 用来在点击“图片内嵌字符的时候备份当前的坐标范围”
	public float minXofinnertext = 1000, minYofinnertext = 1000,
			maxXofinnertext = 0, maxYofinnertext = 0;
	protected short eraserkind = -1;
	protected final short LARGEERASER = 2;
	protected final short MIDDLEERASER = 1;
	protected final short SMALLERASER = 0;
	protected final short LARGEERASERRadius = 13;
	protected final short MIDDLEERASERRadius = 9;
	protected final short SMALLERASERRadius = 5;
	public short baselineheight = (short) (WritebySurfaceView.innertextsize + 10); // Mview中的baselineheight为用来记录内嵌字的一行的最高的高度（内嵌字高度+10）
	public short maxofinnery = -1;
	private boolean isdoubleclick = false; // 用来标注是否双击了鼠标
	protected boolean clear = false; // 标记是否已经下笔
	protected boolean controlbybutton = false; // 标记是否处于button控制编辑符状态下
	public static boolean patchangefordrawing = false; // 用来 标志是否处于画图状态
	public static boolean innertextstatus = false; // 标记是否处于“图片内嵌文字状态”
	public static boolean locationforinnertext = false; // 在点击“内嵌文字时候，应该先定位这些文字的坐标，用该boolean类型变量标志”
	// private boolean enterwheninnertext = false; //
	// 用于标识当前的enter动作是否发生在内嵌字状态下，如果是内嵌字状态下的话，内嵌字的虚线方框应该增高一些
	protected ArrayList<Integer> islast = new ArrayList<Integer>();
	public static volatile ArrayList<BasePoint> array = new ArrayList<BasePoint>();
	protected static ArrayList<BasePoint> temparray = new ArrayList<BasePoint>();
	// private static ArrayList<BasePoint> temparrayforeraser = new
	// ArrayList<BasePoint>();
	public static ArrayList<BasePoint> arrayforinnertext = new ArrayList<BasePoint>();
	protected ArrayList<CharUnit> UnitforInnerText = new ArrayList<CharUnit>();
	public static ArrayList<Integer> paintstrokesizelist = new ArrayList<Integer>(); // 用来存储图片的各笔的笔画
	private volatile boolean strokeend = false;
	private BasePoint point = null;
	private BasePoint editpoint = null;
	private Timecount timecount;
	private TimecountforCtrlUnit timecountforctrlunit;
//	private TimecountforAddpoint timecountaddpoint;
//	private int addpointsecond = 80;
	protected EditDFA editdfa = new EditDFA();
	/**
	 * 终止controltimer标记
	 */
	boolean stopcontroltimerflag = false;
	private long firClick = 0;

	// 两次点击时间间隔
	private long distanceTime;
	// 第二次点击时间
	private long secClick = 0;

	private short count = 0;

	private BasePoint mousefirstpoint, mousesecpoint;

	/***********************************************************************/
	/* 下面的这几条是为了采用darwpath代替drawline而新引进的变量！！ */
	/***********************************************************************/
	private static Path mPath = new Path();
	// private Paint mBitmapPaint;
	private Paint pat = new Paint();
	Path p = new Path();
	public static ArrayList<BasePoint> editarray = new ArrayList<BasePoint>();
	// /////////////////////////////////////liqiang/////////////////////////
	/**
	 * 内嵌字不要被右侧的各个按钮覆盖，所以减了一个量：leftmarginforinnertext
	 */
	private int leftmarginforinnertext = 60;
	public static int pointnumx = 0, pointnumy = 0, charpointcount = 0,
			innercharpointcount = 0, picpointcount = 0;
	boolean firsttouchflag = false;
	int firstx = 0, firsty = 0;
	public boolean redrawkviewflag = false;
	public static int landxmaxlimit = 0, landymaxlimit = 0, portxmaxlimit = 0,
			portymaxlimit = 0, landyminlimit = 0, landxminlimit = 0,
			portxminlimit = 0, portyminlimit = 0;
	/**
	 * 标记鼠标是否移动到限定区域外，如果移动到限定区域外则为true，否则为false。
	 */
	private boolean movingoutflag = false;
	/**
	 * OpenGLTest 2011.02.21
	 */
	/*****************************************************/
	protected OpenGlRender openglrender;
	protected ArrayList<BasePoint> charpointarray = new ArrayList<BasePoint>();

	protected ArrayList<Float> charpointbuf = new ArrayList<Float>();
	protected ArrayList<Float> picturebuf = new ArrayList<Float>();
	protected ArrayList<Float> innercharbuf = new ArrayList<Float>();

	int maxpointnum = 10000;
	public  float[] charpointfloat = new float[maxpointnum * 2];
	public  float[] innercharpointfloat = new float[maxpointnum * 2];
	public  float[] picturepointfloat = new float[maxpointnum * 2];

	private void setCoornatexandy()
	{
		OpenGlRender.coordinatex = (float) Mview.screenwidth / 2f;
		OpenGlRender.coordinatey = (float) Mview.screenheight / 2f;
	}

	public void initRectBuffer(boolean flag)
	{
//		Log.i("drawing initrectbuffer", "!!!!");
		float x1, x2, y1, y2, tempx, tempy;

		if (flag)
		{
			x1 = firstx;
			y1 = firsty;
			x2 = firstx + 40;
			y2 = firsty + 40;
		} else
		{
			x1 = copyofinnerx - 5;
			y1 = copyofinnery - 5;
			x2 = maxXofinnertext + 8;
			y2 = maxYofinnertext + 8;
//			x2 = innerx + 8;
//			y2 = innery + 8;
		}
//		Log.i("debug in drawrect for inner ","y is "+y2+" "+copyofmaxY+" "+maxofinnery );
		x1 = ((x1 - OpenGlRender.coordinatex) / OpenGlRender.coordinatex);
		x2 = ((x2 - OpenGlRender.coordinatex) / OpenGlRender.coordinatex);
		y1 = (1.0f - y1 / OpenGlRender.coordinatey);
		y2 = (1.0f - y2 / OpenGlRender.coordinatey);
		tempx = (float) ((x2 - x1) % 0.02);
		tempy = (float) ((y1 - y2) % 0.01);
		if (tempx > 0)
			pointnumx = (int) ((x2 - x1) / 0.02) + 1;
		else
			pointnumx = (int) ((x2 - x1) / 0.02);
		if (tempy > 0)
			pointnumy = (int) ((y1 - y2) / 0.01) + 1;
		else
			pointnumy = (int) ((x2 - x1) / 0.01);
//		Log.i("pointnum is ", "" + pointnumx + " " + pointnumy);
		float rectpoint1[] = new float[pointnumx * 2];
		float rectpoint2[] = new float[pointnumy * 2];
		float rectpoint3[] = new float[pointnumx * 2];
		float rectpoint4[] = new float[pointnumy * 2];
		for (int i = 0; i < pointnumx * 2; i++)
		{
			rectpoint1[i] = (float) (x1 + i * 0.01);
			rectpoint3[i] = (float) (x1 + i * 0.01);
			i++;
			rectpoint1[i] = y1;
			rectpoint3[i] = y2;
		}
		for (int i = 0; i < pointnumy * 2; i++)
		{
			rectpoint2[i] = x2;
			rectpoint4[i] = x1;
			i++;
			rectpoint2[i] = (float) (y1 - i * 0.005);
			rectpoint4[i] = (float) (y1 - i * 0.005);
		}
		ByteBuffer buf = ByteBuffer.allocateDirect(pointnumx * 2 * 4);
		buf.order(ByteOrder.nativeOrder());
		openglrender.rectBuffer1 = buf.asFloatBuffer();
		openglrender.rectBuffer1.put(rectpoint1);
		openglrender.rectBuffer1.position(0);
		ByteBuffer buf3 = ByteBuffer.allocateDirect(pointnumx * 2 * 4);
		buf3.order(ByteOrder.nativeOrder());
		openglrender.rectBuffer3 = buf3.asFloatBuffer();
		openglrender.rectBuffer3.put(rectpoint3);
		openglrender.rectBuffer3.position(0);

		ByteBuffer buf1 = ByteBuffer.allocateDirect(pointnumy * 2 * 4);
		buf1.order(ByteOrder.nativeOrder());
		openglrender.rectBuffer2 = buf1.asFloatBuffer();
		openglrender.rectBuffer2.put(rectpoint2);
		openglrender.rectBuffer2.position(0);
		ByteBuffer buf2 = ByteBuffer.allocateDirect(pointnumy * 2 * 4);
		buf2.order(ByteOrder.nativeOrder());
		openglrender.rectBuffer4 = buf2.asFloatBuffer();
		openglrender.rectBuffer4.put(rectpoint4);
		openglrender.rectBuffer4.position(0);
	}

	/**
	 * 初始化drawinnercharbuffer，将innercharbuf中的点放到drawinnercharbuffer中，
	 * 在此之前先将传入的点的坐标变换成opengl下的坐标
	 * 
	 * @param index
	 *            temparray中点的索引
	 */
	private void initdrawinnercharbuffer(int i)
	{
		float x = arrayforinnertext.get(i).getX();
		float y = arrayforinnertext.get(i).getY();
		x = (x - OpenGlRender.coordinatex) / OpenGlRender.coordinatex;
		innercharpointfloat[i * 2] = x;
		y = 1.0f - y / OpenGlRender.coordinatey;
		innercharpointfloat[i * 2 + 1] = y;

		openglrender.drawinnercharflag = true;
		if (openglrender.drawinnercharbufferlock)
		{
			openglrender.drawinnercharbufferlock = false;
			innercharpointcount = i + 1;
			openglrender.drawinnercharbuffer.put(innercharpointfloat, 0,
					innercharpointcount * 2);
			openglrender.drawinnercharbuffer.position(0);
			
			openglrender.drawinnercharbufferlock = true;
		}
		this.requestRender();
	}
	/**
	 * 初始化drawpicbuffer，将array中的点放到drawpicbuffer中，
	 * 在此之前先将传入的点的坐标变换成opengl下的坐标
	 * 
	 * @param i array中点的索引
	 *            
	 */
	private  void initdrawpicbuffer(final int i)
	{
		// 画图时点坐标的个数，是点个数的两倍
		
		float x = array.get(i).getX();
		float y = array.get(i).getY();
		x = (x - OpenGlRender.coordinatex) / OpenGlRender.coordinatex;
		picturepointfloat[i * 2] = x;
		y = 1.0f - y / OpenGlRender.coordinatey;
		picturepointfloat[i * 2 + 1] = y;
		openglrender.drawpictureflag = true;
		if (openglrender.drawpicturebufferlock)
		{
			openglrender.drawpicturebufferlock = false;
			this.queueEvent(new Runnable() 
			{ 
				@Override
				public void run()
				{
			synchronized (this) {
				picpointcount = i + 1;
			
			openglrender.drawpicbuffer.put(picturepointfloat, 0, picpointcount * 2);
			openglrender.drawpicbuffer.position(0);
			}
				}});
			openglrender.drawpicturebufferlock = true;
		}
	}
	/**
	 * 将array中的所有点放入drawpicbuffer中
	 * @param array ArrayList<BasePoint> 要放入buffer中的点的集合
	 */
	private void initdrawpicbuffer(ArrayList<BasePoint> array)
	{
		// 画图时点坐标的个数，是点个数的两倍
		float x = 0;
		float y = 0;
		for(int i = 0;i<array.size();i++)
		{
			x = array.get(i).getX();
			y = array.get(i).getY();
			x = (x - OpenGlRender.coordinatex) / OpenGlRender.coordinatex;
			picturepointfloat[i * 2] = x;
			y = 1.0f - y / OpenGlRender.coordinatey;
			picturepointfloat[i * 2 + 1] = y;
		}
		openglrender.drawpictureflag = true;
		if (openglrender.drawpicturebufferlock)
		{
			openglrender.drawpicturebufferlock = false;
//			Log.v("debug in initpicbuffer22",""+openglrender.drawpicturebufferlock);
			picpointcount = array.size();
			
			openglrender.drawpicbuffer.put(picturepointfloat, 0, picpointcount * 2);
			openglrender.drawpicbuffer.position(0);
			openglrender.drawpicturebufferlock = true;
//			Log.v("debug in initpicbuffer22",""+openglrender.drawpicturebufferlock);
		}
	}

	public void drawRender()
	{
		// Log.i("debug", "this is " + this.getClass());
		this.requestRender();
		postInvalidate();//2011.12.26 liqiang
//		Log.i("debug", "after draw in render !!!");
	}

	public void clearBuffer()
	{
		openglrender.circleBuffer.clear();
		charpointcount = 0;
		charpointfloat = null;
		charpointfloat = new float[maxpointnum];
//		innercharpointfloat = null;
//		innercharpointfloat = new float[maxpointnum];
//		picturepointfloat = null;
//		picturepointfloat = new float[maxpointnum];
		openglrender.drawcharbuffer.clear();
		openglrender.drawinnercharbuffer.clear();
		openglrender.drawpicbuffer.clear();
	}

	/**
	 * 初始化drawcharbuffer，将charpointbuf中的点放到drawcharbuf中，
	 * 在此之前先将传入的点的坐标变换成opengl下的坐标
	 * 
	 * @param i
	 *            添加的点在temparray中的下标
	 */
	private void initdrawcharbuffer(int i)
	{
		// 写大字时点坐标的个数，是点个数的两倍
		// Log.i("debug","init draw char buffer !!!!");
//		if(i>980)
//		{//申请内存的时候，申请了能存储1000个点的内存，
//		//当点的数目超过980的时候，有可能要超过内存的大小了，所以要增加内存的大小
//			maxpointnum = 2000;
//		}
		float x = charpointarray.get(i).getX();
		float y = charpointarray.get(i).getY();
		x = (x - OpenGlRender.coordinatex) / OpenGlRender.coordinatex;
		charpointfloat[i * 2] = x;
		y = 1.0f - y / OpenGlRender.coordinatey;
		charpointfloat[i * 2 + 1] = y;
		openglrender.drawcharunitflag = true;
		openglrender.draweditflag = true;
		if (openglrender.drawcharbufferlock)
		{
			openglrender.drawcharbufferlock = false;
//			openglrender.drawcharbuffer.clear();//2011.12.01 liqiang
//			System.gc();//2011.12.01 liqiang
			openglrender.drawcharbuffer.put(charpointfloat, 0, (i + 1) * 2);
			openglrender.drawcharbuffer.position(0);
			charpointcount = i + 1;
			openglrender.drawcharbufferlock = true;
		}
		this.requestRender();
		
	}

	/*****************************************************/
	/*
	 * public static Paint mnwepaint = new Paint(); short temp ,screenwidth;
	 * Path p = new Path(); PathEffect mEffects ;
	 */

	/**
	 * @param context
	 * @param attr
	 *            可以用holder.setType(SurfaceHolder.SURFACE_TYPE_GPU); 来让程序可能变快一些
	 *            SURFACE_TYPE_NORMAL：用RAM缓存原生数据的普通Surface
	 *            SURFACE_TYPE_HARDWARE：适用于DMA(Direct memory access
	 *            )引擎和硬件加速的Surface SURFACE_TYPE_GPU：适用于GPU加速的Surface
	 *            SURFACE_TYPE_PUSH_BUFFERS
	 *            ：表明该Surface不包含原生数据，Surface用到的数据由其他对象提供
	 *            ，在Camera图像预览中就使用该类型的Surface，有Camera
	 *            负责提供给预览Surface数据，这样图像预览会比较流畅
	 *            。如果设置这种类型则就不能调用lockCanvas来获取Canvas对象了
	 */
	public Mview(Context context, AttributeSet attr)
	{
		super(context, attr);
		setZOrderOnTop(true);
		setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		openglrender = new OpenGlRender();
		setRenderer(openglrender);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);//2011.12.06 liqiang
		setFocusable(true);
//		Log.i("debug", " render create in mview !!!!!!!");
		openglrender.clearflag = true;
		openglrender.drawstiplineflag = true;
		setCoornatexandy();
		// Log.i("debug", "destoryflag in mview construct is "
		// + openglrender.destoryflag);
		// }
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			if (Mview.screenheight > Mview.screenwidth)
			{
				OpenGlRender.coordinatey = Mview.screenwidth / 2;
				OpenGlRender.coordinatex = Mview.screenheight / 2;
			} else
			{
				OpenGlRender.coordinatex = Mview.screenwidth / 2;
				OpenGlRender.coordinatey = Mview.screenheight / 2;
			}
			openglrender.pointnum = 150;
//			 Log.i("debug", "width and height is in landscape "
//			 + Mview.screenwidth + " " + Mview.screenheight);
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			openglrender.pointnum = 80;
			if (Mview.screenheight > Mview.screenwidth)
			{
				OpenGlRender.coordinatex = Mview.screenwidth / 2;
				OpenGlRender.coordinatey = Mview.screenheight / 2;
			} else
			{
				OpenGlRender.coordinatey = Mview.screenwidth / 2;
				OpenGlRender.coordinatex = Mview.screenheight / 2;
			}
//			 Log.i("debug", "width and height is in portrait "
//			 + Mview.screenwidth + " " + Mview.screenheight);
		}
		// Log.i("debug",
		// "mview construct x and y is "+openglrender.coordinatex+" "+openglrender.coordinatey);

		ListTable.timesofoncreat++;
		baselineheight = (short) (WritebySurfaceView.innertextsize + 10);

		// Log.i("DEBUG--->", "Mview ListTable.writespeed  "
		// + ListTable.writespeed);
		if (ListTable.isAutoSubmit == 0)
		{
			short speedforctrlunit = (short) (ListTable.writespeed - 380);
			speedforctrlunit = speedforctrlunit > 0 ? speedforctrlunit : 400;
			timecount = new Timecount(speedforctrlunit);
			Log.i("debug in mview construct ", "speedforctrlunit is "
					+ speedforctrlunit);
			Log.i("debug in mview construct ", "listtable.writespeed is "
					+ ListTable.writespeed);
		} else
			timecount = new Timecount(ListTable.writespeed);
		short speedforctrlunit = (short) (ListTable.writespeed - 380);
		Log.i("debug in mview construct ", "speedforctrlunit is "
				+ speedforctrlunit);
		speedforctrlunit = speedforctrlunit > 0 ? speedforctrlunit : 400;
		timecountforctrlunit = new TimecountforCtrlUnit(speedforctrlunit);
//		timecountforctrlunit = new TimecountforCtrlUnit(2000);
//		timecountaddpoint = new TimecountforAddpoint(addpointsecond);
		charactersize = (short) ListTable.charactersize;
		characterstrokewidth = (short) ListTable.characterstrokewidth;
		shuxiejizhun = (short) ListTable.shuxiejizhun;

		// mColor = bgcolor;
		array.clear();
		temparray.clear();
		// 2011.03.06
		charpointcount = 0;
		charpointfloat = null;
		charpointfloat = new float[maxpointnum];
//		innercharpointcount = 0;
//		innercharpointfloat = null;
//		innercharpointfloat = new float[maxpointnum];
//		picpointcount = 0;
//		picturepointfloat = null;
//		picturepointfloat = new float[maxpointnum];
		// clearBuffer();
		// Log.i("DEBUG--->", "width in Mview " + Kview.screenwidth);
		// Log.i("DEBUG--->", "height in Mview " + Kview.screenheight);

		screenwidth = Kview.screenwidth;
		screenheight = Kview.screenheight;

		/***********************************************************************/
		/* 下面的这几条是为了采用darwpath代替drawline而新引进的变量！！ */
		/***********************************************************************/

		mPath = new Path();
		// mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		pat.setAntiAlias(true); // 去锯齿
		pat.setStrokeWidth(2);
		pat.setStyle(Paint.Style.STROKE);
		pat.setStrokeJoin(Paint.Join.ROUND);
		pat.setStrokeCap(Paint.Cap.ROUND);

		if (ListTable.timesofoncreat == 1)
		{
			openglrender.clearflag = true;
			openglrender.drawstiplineflag = true;
			this.requestRender();
			ListTable.templocationofmouse = 0;
			// ListTable.TypeofCtrl = -1;
			// ListTable.TypeofUnit = 0;

			ListTable.TypeofCtrl = -1;
			ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
			ListTable.numoffile = 0;

			// ListTable.isAutoSpace = true;

			ListTable.globalcolorIndex.clear(); // 用来存储当前图形单元每一笔画的颜色
			paintstrokesizelist.clear(); // 用来存储当前图形单元每一笔画的宽度
			UnitforInnerText.clear(); // 用来存储内嵌字符单元的结构
			ListTable.imagepaint.reset();
			ListTable.imagepaint.setColor(Color.BLACK);
			WritebySurfaceView.paintstrokesize = 3F;
			ListTable.ArraySelectGlobal.clear();

			if (ListTable.edittype == false)
			{
				ListTable.globalIndexTable.clear();
				ListTable.temppositionofaddcharunit = 0;
				ListTable.edittype = false;
			}

			innertextstatus = false;
		}

		array.clear();
		temparray.clear();
		// 2011.03.06
		charpointcount = 0;
		charpointfloat = null;
		charpointfloat = new float[maxpointnum];
//		innercharpointcount = 0;
//		innercharpointfloat = null;
//		innercharpointfloat = new float[maxpointnum];
//		picpointcount = 0;
//		picturepointfloat = null;
//		picturepointfloat = new float[maxpointnum];
		// clearBuffer();
	}

	@SuppressWarnings("unchecked")
	public void paint() // 用paint 方法取代onDraw方法
	{
		Log.i("DEBUG--->", "paint in mview !!!+clear" + " " + clear);
		if (clear == true)
		{
			// Log.i("debug", "clear array in paint !!");
			array.clear();
			temparray.clear();
			// 2011.03.06
			charpointbuf.clear();
			charpointbuf.clear();
			openglrender.drawcharbuffer.clear();
			charpointcount = 0;
			charpointfloat = null;
			charpointfloat = new float[maxpointnum];
			openglrender.circleBuffer.clear();
			ListTable.globalcolorIndex.clear();
			paintstrokesizelist.clear();
			islast.clear();
			mPath.reset();
			location = 0;
			minX = 1000;
			minY = 1000;
			maxX = 0;
			maxY = 0;
			if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0
					|| openglrender.backspaceflag || openglrender.spaceflag
					|| openglrender.enterflag)
			{
				openglrender.drawstiplineflag = true;
				openglrender.backspaceflag = false;
				openglrender.spaceflag = false;
				openglrender.enterflag = false;
			} else
				openglrender.drawstiplineflag = false;
			openglrender.clearflag = true;
			this.requestRender();
//			postInvalidate();//2011.12.26 liqiang
			// canvas.drawColor(bgcolor);//2011.03.24 liqiang
			clear = false;
		} else
		{
			// //只有在切换的一瞬间进行画图时候才需要此动作，以保证笔画传到切换后的屏幕上去
			if (ListTable.timesofoncreat != 1
					&& WritebySurfaceView.qiehuan == true
					&& (ListTable.TypeofUnit == Context_STATE.Context_STATE_1 || (ListTable.TypeofUnit == Context_STATE.Context_STATE_0 && innertextstatus == true)))
			{
				Log.i("debug in paint ","false first !!!! 1111");
				Kview.mpaint.setColor(Color.TRANSPARENT);
				Kview.pat.setColor(Color.TRANSPARENT);
				ArrayList<BasePoint> temparrayofimageunit = (ArrayList<BasePoint>) ListTable.CopyofArray
						.clone();

				if (WritebySurfaceView.hascopyedarray)
				{
					minX = ListTable.globalminX;
					minY = ListTable.globalminY;
					maxX = ListTable.globalmaxX;
					maxY = ListTable.globalmaxY;

					if (innertextstatus == true)
					{
						if (locationforinnertext)
						{
							if (maxXofinnertext == 0 || maxYofinnertext == 0)
							{
								maxXofinnertext = copyofinnerx + 20;
								maxYofinnertext = copyofinnery + 20;
							}
						}

						copyofminX = ListTable.globalcopyofminX;
						copyofminY = ListTable.globalcopyofminY;
						copyofmaxX = ListTable.globalcopyofmaxX;
						copyofmaxY = ListTable.globalcopyofmaxY;

						minXofinnertext = ListTable.globalminXofinnertext;
						minYofinnertext = ListTable.globalminYofinnertext;
						maxXofinnertext = ListTable.globalmaxXofinnertext;
						maxYofinnertext = ListTable.globalmaxYofinnertext;

						innerx = ListTable.innerx;
						innery = ListTable.innery;

						copyofinnerx = ListTable.copyofinnerx;
						copyofinnery = ListTable.copyofinnery;

						baselineheight = ListTable.baselineheightofinnertext;
						maxofinnery = ListTable.maxofinnery;

					}

					WritebySurfaceView.hascopyedarray = false;
				}
//				Log.i("debug in mview paint ","array size is "+array.size());
				array = (ArrayList<BasePoint>) temparrayofimageunit.clone();
//				Log.i("debug in mview paint ","array size is "+array.size());
				// drawimageinmview(canvas);
				WritebySurfaceView.qiehuan = false;
				ListTable.CopyofArray.clear();
				temparrayofimageunit.clear();
			} else
			{
				// canvas.drawColor(mColor);//2011.03.24
				Log.i("debug in paint ","false second !!!! 2222 "+ListTable.TypeofUnit);
				switch (ListTable.TypeofUnit)
				{
					case Context_STATE.Context_STATE_0:
						if (innertextstatus)
						{

							if (locationforinnertext)
							{
								if (maxXofinnertext == 0
										|| maxYofinnertext == 0)
								{
									maxXofinnertext = copyofinnerx + 20;
									maxYofinnertext = copyofinnery + 20;
								}
								openglrender.drawstiplineflag = true;
								this.requestRender();
							}
						} else
						{
						}
						break;
					case Context_STATE.Context_STATE_1:

						if (ListTable.TypeofGraph == Context_STATE.Graph_STATE_1
								&& scopex != 0 && scopey != 0)
						{
							switch (eraserkind)
							{
								case LARGEERASER:
									// canvas
									// .drawRect(scopex - 12, scopey - 12,
									// scopex + 12, scopey + 12,
									// eraerrecg);
									break;
								case MIDDLEERASER:
									// canvas.drawRect(scopex - 9, scopey - 9,
									// scopex + 9, scopey + 9, eraerrecg);
									break;

								case SMALLERASER:
								default:
									// canvas.drawRect(scopex - 7, scopey - 7,
									// scopex + 7, scopey + 7, eraerrecg);
									break;

							}
						}
						break;
					case Context_STATE.Context_STATE_2:
						Log.i("debug in paint ","false third !!!! 3333");
						break;
					default:
						break;
				}
			}
		}
	}

	public class waitThread extends Thread
	{
		public void run()
		{
			try
			{
				Thread.sleep(1000);
				// Log.i("thread","over!!");
				// addpointtoarray(point,editpoint, true);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean onTouchEvent(final MotionEvent event)
	{ // 触摸事件，ACTION_DOWN表示下触的点，
//		this.queueEvent(new Runnable() 
//		{ 
//			@Override
//			public void run()
//			{
				// TODO Auto-generated method stub
		x = event.getX();
		y = event.getY();

		point = new BasePoint((short) x, (short) y);
		editpoint = new BasePoint((short) (x - ListTable.leftmargin),
				(short) (y - (ListTable.charactersize - 16) * 2));// 2011.07.01
		// liqiang
		// 添加工具栏后，文字下移，因此要加上偏移量
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			strokeend = true;
			// 2011.02.21
			// getpoint(event);
			openglrender.touchflag = false;
			movingoutflag = false;
			// 2011.02.21
			switch (ListTable.TypeofUnit)
			{
				case Context_STATE.Context_STATE_0:
//					 Log.i("mouse up","add point to array !");
					addpointtoarray(point, editpoint, true);
//					timecountaddpoint.start();
					if (innertextstatus == true
							&& locationforinnertext == false)
					{// 点击添加内嵌字按钮，还未定位内嵌字位置
						// Log.i("debug","dian ji nei qian zi an niu wei ding wei wei zhi ");
						innerx = (short) x;
						innery = (short) y;
						copyofinnerx = (short) x;
						copyofinnery = (short) y;
						firsttouchflag = true;
						firstx = (int) x;
						firsty = (int) y;
						initRectBuffer(firsttouchflag);
						firsttouchflag = false;
						// Log.i("DEBUG--->", "innerx  " + innerx + "  innery  "
						// + innery);
						locationforinnertext = true;
						arrayforinnertext.clear(); // 确定内嵌文字位置前的所有该数组里的数据全部清空，因为没用！
//						Log.i("debug in ontouch", "arrayforinnertext size is "
//								+ arrayforinnertext.size());
						// 2011.03.09 liqiang
//						innercharpointcount = 0;
//						innercharpointfloat = null;
//						innercharpointfloat = new float[maxpointnum];
						// clearBuffer();
						charpointbuf.clear();
						openglrender.drawcharbuffer.clear();
						charpointfloat = null;
						charpointfloat = new float[maxpointnum];
						charpointcount = 0;
						openglrender.drawinnercharbuffer.clear();
						openglrender.drawstiplineflag = true;
						requestRender();

						minX = 1000; // 进入到这个判断里面表示，点击“内嵌字”状态后，再点击一块空地方，以用来放内签字，所以必须也对minX,minY,maxX,maxY清零
						minY = 1000;
						maxX = 0;
						maxY = 0;
						x = -1; // 这么设置时为了再下面的判断时候不让内嵌字的位置定位坐标影响到minX，minY ....
						y = -1;
						location = 0;
						islast.clear();
						// 2011.02.23
						paint();
						// new Thread(mythread).start();
					}
					break;
				case Context_STATE.Context_STATE_1:

					switch (ListTable.TypeofGraph)
					{
						case Context_STATE.Graph_STATE_1: // 擦除状态

							switch (eraserkind)
							{
								case LARGEERASER:
									// Log.i("debug in eraser","large eraser !!!");
									EraseImage(LARGEERASERRadius);

									break;
								case MIDDLEERASER:
									// Log.i("debug in eraser","middle eraser !!!");
									EraseImage(MIDDLEERASERRadius);
									break;

								case SMALLERASER:
									// Log.i("debug in eraser","smalle eraser !!!");
									EraseImage(SMALLERASERRadius);
									break;
								default:
									EraseImage(LARGEERASERRadius);
									break;

							}
							// EraseImage();
							// new Thread(mythread).start();
							paint();
							break;
						default:

							if (!isdoubleclick)//2011.10.17 liqiang
							{
								Log.i("debug in mouse touch up ",
										"draw pic !!!");
								boolean addpointflag = true;
								if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
								{
									if (point.getX() > landxmaxlimit
											|| point.getX() < landxminlimit
											|| point.getY() > landymaxlimit
											|| point.getY() < landyminlimit)
									{
										if (array.size() > 0)
											array.get(array.size() - 1).setEnd(
													true);
										addpointflag = false;
									}
								} else if (getResources()
										.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
								{
									if (point.getX() > portxmaxlimit
											|| point.getX() < portxminlimit
											|| point.getY() > portymaxlimit
											|| point.getY() < portyminlimit)
									{
										if (array.size() > 0)
											array.get(array.size() - 1).setEnd(
													true);
										addpointflag = false;
									}
								}
								if (addpointflag)
								{
									if(array.get(array.size()-1).isEnd()==false)//2011.12.13
									{
									point.setEnd(true);
//									Log.i("debug in touch end ","set true !!!");
									array.add(point);

									
//									this.queueEvent(new Runnable() 
//									{ 
//										@Override
//										public void run()
//										{
//											
//										}});
									}
									initdrawpicbuffer(array.size() - 1);
									openglrender.drawstiplineflag = false;
									requestRender();
//									timecountaddpoint.start();
								}
							}
							else
								isdoubleclick = false;
							break;
					}

					break;
				case Context_STATE.Context_STATE_2:
					if (!isdoubleclick)
					{
//						Log.i("mouse up","add point to array 222!");
						addpointtoarray(point, editpoint, true);
					}
					break;
			}

		} else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			switch (ListTable.TypeofUnit)
			{
				case Context_STATE.Context_STATE_0:// 写文字
//					Log.i("mouse moveing ","add point to array !");
					addpointtoarray(point, editpoint, false);
//					Log.i("debug in moving ","point.gety is "+point.getX());
//					Log.i("debug in ontouch moving ","moving "+minX+" "+minY+" "+maxX+" "+maxY);
					// // initdrawcharbuffer();//2011.02.23 liqiang
					// this.requestRender();
					// 大字数字墨水
					if (temparray.size() >= 2)
					{
						InkPen.modifyStrokePoints(temparray.get(temparray
								.size() - 2), point, characterstrokewidth);
						// Log.i("DEBUG------>","ONTOUCH MOVE size and width "+temparray.size()+" "+characterstrokewidth);
					}
					break;

				case Context_STATE.Context_STATE_1:// 画图

					switch (ListTable.TypeofGraph)
					{
						case Context_STATE.Graph_STATE_1: // 擦除状态,在MOVE时候不添入点
							break;
						default:
							boolean addpointflag = true;
							if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
							{
								if (point.getX() > landxmaxlimit
										|| point.getX() < landxminlimit
										|| point.getY() > landymaxlimit
										|| point.getY() < landyminlimit)
								{
									if (array.size() > 0&&array.get(array.size()-1).isEnd()==false)
									{
										array.get(array.size() - 1)
												.setEnd(true);
//										Log.i("","set  true!!!");
									}
									addpointflag = false;
									movingoutflag = true;
								} else
								{
									if (movingoutflag)
									{
										int color = ListTable.imagepaint
												.getColor();
										if (HwFunctionWindow.colortable
												.contains(color) == false) // 索引表中不含有该颜色，故添加！！
											HwFunctionWindow.colortable
													.add(color);
										ListTable.globalcolorIndex.add(color);
										int strokesize = (int) WritebySurfaceView.paintstrokesize;
										Log.e("","stroke is "+strokesize);
										point.setStrokeWidth(strokesize);
										paintstrokesizelist.add(strokesize);
										movingoutflag = false;
									}
								}
							} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
							{
								if (point.getX() > portxmaxlimit
										|| point.getX() < portxminlimit
										|| point.getY() > portymaxlimit
										|| point.getY() < portyminlimit)
								{
									if (array.size() > 0&&array.get(array.size()-1).isEnd()==false)
									{
										array.get(array.size() - 1)
												.setEnd(true);
//										Log.i("","set  true!!!");
									}
									addpointflag = false;
									movingoutflag = true;
								} else
								{
									if (movingoutflag)
									{
										int color = ListTable.imagepaint
												.getColor();
										if (HwFunctionWindow.colortable
												.contains(color) == false) // 索引表中不含有该颜色，故添加！！
											HwFunctionWindow.colortable
													.add(color);
										ListTable.globalcolorIndex.add(color);
										int strokesize = (int) WritebySurfaceView.paintstrokesize;
										Log.e("move ","stroke is "+strokesize);
										paintstrokesizelist.add(strokesize);
										movingoutflag = false;
									}
								}
							}
							if (addpointflag)
							{
								point.setEnd(false);
//								Log.i("debug","add point to array in mouse move of draw !!!!");
								if(!isdoubleclick)
								{
								array.add(point);
								
//								this.queueEvent(new Runnable() 
//								{ 
//									@Override
//									public void run()
//									{
//										
//									}});
								}
								initdrawpicbuffer(array.size() - 1);
								openglrender.drawstiplineflag = false;
								requestRender();
							}
							break;
					}

					break;
				case Context_STATE.Context_STATE_2:// 编辑
//					 Log.i("mouse moving ","add point to array in edit !!");
					if(!isdoubleclick)//2011.12.02 liqiang 
					{
						addpointtoarray(point, editpoint, false); // 为了手势识别而添加的
					}
						// touch_move(x, y);
					break;
			}

		} else
		// DOWN 事件！！
		{
			// 2011.02.21 liqiang
			openglrender.touchflag = true;
			movingoutflag = false;
			switch (ListTable.TypeofUnit)
			{
				case Context_STATE.Context_STATE_0:
					clear = false;

					if (islast.size() >= 1 && isdoubleclick == false)
						islast.set(islast.size() - 1, 0); // 把上一笔画修改成//
					// 0（意思是：上一笔不是最后一笔）
					else
						// 说明这是第一笔
						point.setStrokeWidth(2);
					if (isdoubleclick == false)
					{
//						Log.i("mouse mousedown","add point to array !");
						addpointtoarray(point, editpoint, false);
					}
					if (innertextstatus == true && isdoubleclick == false) // 这快是为了对内嵌文字记录颜色～～
					{

						if (locationforinnertext == true) // 内嵌字坐标定位的时候不用添加颜色和笔画宽度
						{
							boolean addpointflag = true;
							if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
							{
								if (point.getX() > landxmaxlimit
										|| point.getX() < landxminlimit
										|| point.getY() > landymaxlimit
										|| point.getY() < landyminlimit)
								{
									changeEnd();
									addpointflag = false;
								}
							} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
							{
								if (point.getX() > portxmaxlimit
										|| point.getX() < portxminlimit
										|| point.getY() > portymaxlimit
										|| point.getY() < portyminlimit)
								{
									changeEnd();
									addpointflag = false;
								}
							}
							if (addpointflag)
							{
								int color = ListTable.imagepaint.getColor();
								if (HwFunctionWindow.colortable.contains(color) == false) // 索引表中不含有该颜色，故添加！！
									HwFunctionWindow.colortable.add(color);
								ListTable.globalcolorIndex.add(color);
								int strokesize = 3; // 因为是内嵌字，所以设置其宽度为3,而不是接着上面的笔画宽度
								paintstrokesizelist.add(strokesize);
							}
						}
					}
					break;
				case Context_STATE.Context_STATE_1:
					clear = false;

					switch (ListTable.TypeofGraph)
					{
						case Context_STATE.Graph_STATE_1:
							// point.setEnd(false);
							// array.add(point);
							// Log.i("DEBUG--->", "GRAPH_STATE_1");
							break;
						default:
//							if (!isdoubleclick)//2011.10.17 liqiang
//							{
								// Log.i("DEBUG-->","Down "+array.size());

								boolean addpointflag = true;
								if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
								{
									if (point.getX() > landxmaxlimit
											|| point.getX() < landxminlimit
											|| point.getY() > landymaxlimit
											|| point.getY() < landyminlimit)
									{
										if (array.size() > 0)
											array.get(array.size() - 1).setEnd(
													true);
										addpointflag = false;
									}
								} else if (getResources()
										.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
								{
									if (point.getX() > portxmaxlimit
											|| point.getX() < portxminlimit
											|| point.getY() > portymaxlimit
											|| point.getY() < portyminlimit)
									{
										if (array.size() > 0)
											array.get(array.size() - 1).setEnd(
													true);
										addpointflag = false;
									}
								}
								if (addpointflag)
								{
									int color = ListTable.imagepaint.getColor();
									if (HwFunctionWindow.colortable
											.contains(color) == false) // 索引表中不含有该颜色，故添加！！
										HwFunctionWindow.colortable.add(color);
									ListTable.globalcolorIndex.add(color);
									float strokesize = WritebySurfaceView.paintstrokesize;
									Log.e("debug in down","strokesize is "+strokesize);
									paintstrokesizelist.add((int)strokesize);
									point.setStrokeWidth(strokesize);
									point.setEnd(false);
//									Log.i("debug in down ","add point to array !!!"+array.size());
									array.add(point);
									
									for(int i=0;i<Mview.paintstrokesizelist.size();i++)
									{
										Log.e("debug in down","size is "+Mview.paintstrokesizelist.get(i));
									}
//									this.queueEvent(new Runnable() 
//									{ 
//										@Override
//										public void run()
//										{
//											
//										}});
									initdrawpicbuffer(array.size() - 1);
									openglrender.drawstiplineflag = false;
									requestRender();
								}
//							}
							break;
					}
					// touch_start(x, y);
					break;
				case Context_STATE.Context_STATE_2:
					clear = false;

					// 编辑状态下，检测双击事件
					// Log.i("DEBUG--->","DOWN EVENT");
					isdoubleclick(point);

					if (islast.size() >= 1 && isdoubleclick == false) // 为了手势识别而添加的
						islast.set(islast.size() - 1, 0); // 把上一笔画修改成
					// 0（意思是：上一笔不是最后一笔）
					if (isdoubleclick == false)
					{
//						Log.i("debug in down add ","adddddddddddddddddddd!!!");
						addpointtoarray(point, editpoint, false);
					}
					// touch_start(x, y);
					break;
			} 
		}

		switch (ListTable.TypeofGraph)
		{
			case Context_STATE.Graph_STATE_1: // 橡皮擦状态下的点击坐标不应该计入minx等
//				Log.i("DEBUG-->", "FF Graph_STATE_1");
				break;
			default:
				
				if (minX > x && x >= 0 && y >= 0)
					minX = x;
				if (minY > y && x >= 0 && y >= 0)
					minY = y;
				if (maxX < x && x >= 0 && y >= 0)
					maxX = x;
				if (maxY < y && x >= 0 && y >= 0)
					maxY = y;
				break;
		}

//			}});
			
		
//		 Log.i("DEBUG DOWN --->","minX  minY  maxX  maxY  "+minX+"  "+
//		 minY+"   "+ maxX+"  "+ maxY );
		return true;
	}

	/**
	 * 将点添加到temparray和array中 如果是一笔的最后一个点，则将end设置为true，否则设置为false
	 * 
	 * @param point
	 *            要添加的点
	 * @param end
	 *            标记该点是否是一笔的最后一个点
	 */
	public void addpointtoarray(BasePoint point, BasePoint editpoint,
			boolean end)
	{
//		Log.i("debug in addpointto","add !!!");
		if(isdoubleclick)//2011.12.02 liqiang
			return;//2011.12.02 liqiang
		boolean addpointflag = true;

		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			if (point.getX() > landxmaxlimit || point.getX() < landxminlimit
					|| point.getY() > landymaxlimit
					|| point.getY() < landyminlimit)
			{
				addpointflag = false;
				if (!movingoutflag)
					changeEnd();
				movingoutflag = true;
//				Log.i("debug in addpoint ","landxmaxlimit is "+landxmaxlimit );
			} else
			{
				if (movingoutflag && locationforinnertext)
				{
					int color = ListTable.imagepaint.getColor();
					if (HwFunctionWindow.colortable.contains(color) == false) // 索引表中不含有该颜色，故添加！！
						HwFunctionWindow.colortable.add(color);
					ListTable.globalcolorIndex.add(color);
					int strokesize = (int) WritebySurfaceView.paintstrokesize;
					paintstrokesizelist.add(strokesize);
					
//					Log.i("debug in addpoint", "color size "
//							+ ListTable.globalcolorIndex.size());
				}
				movingoutflag = false;
			}
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			if (point.getX() > portxmaxlimit || point.getX() < portxminlimit
					|| point.getY() > portymaxlimit
					|| point.getY() < portyminlimit)
			{
				addpointflag = false;
				if (!movingoutflag)
					changeEnd();
				movingoutflag = true;
//				Log.i("debug in addpoint ","portxmaxlimit is "+portxmaxlimit );
			} else
			{
				if (movingoutflag && locationforinnertext)
				{
					int color = ListTable.imagepaint.getColor();
					if (HwFunctionWindow.colortable.contains(color) == false) // 索引表中不含有该颜色，故添加！！
						HwFunctionWindow.colortable.add(color);
					ListTable.globalcolorIndex.add(color);
					int strokesize = (int) WritebySurfaceView.paintstrokesize;
					paintstrokesizelist.add(strokesize);
					
//					Log.i("debug in addpoint", "color size "
//							+ ListTable.globalcolorIndex.size());
				}
				movingoutflag = false;
			}
		}
		point.setEnd(end);
//		Log.i("debug in addpointtoarray func ","add point to array !!!"+addpointflag);
		if (addpointflag)
		{
			// 对笔画进行修饰处理
			if (innertextstatus == false)
			{// 不是写内嵌字的时候，画图，写大字，画编辑符号的时候
//				Log.i("debug in addpointtoarray func ","add point to array !!!");
				array.add(point);
				temparray.add(point);
				charpointarray.add(point);
				editarray.add(editpoint);
				if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0
						|| ListTable.TypeofUnit == Context_STATE.Context_STATE_2)
				{
					if (end && temparray.size() == 2)
					{// 只有一个点时，是一笔的结束并且temparray中只有两个点时（mousedown和mouseup时各添加一个点）
						if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0)
						{
							initdrawcharbuffer(temparray.size() - 1);
							openglrender.drawstiplineflag = true;
						} else
						{
							temparray.clear();
							openglrender.drawcharbuffer.clear();
							charpointfloat = null;
							charpointfloat = new float[maxpointnum];
							charpointcount = 0;
							clearBuffer();
							openglrender.drawstiplineflag = false;
							this.requestRender();
						}
					} else
					{
						initdrawcharbuffer(temparray.size() - 1);
						openglrender.drawstiplineflag = true;
					}
				}
			} else
			{// 写内嵌字的时候
//				Log.i("debug in addpointto","add inner point !!!"+locationforinnertext);
				if (locationforinnertext)//2011.12.13
				{
//					Log.i("debug","arrayforinnertext add point !!!"+arrayforinnertext.size());
					arrayforinnertext.add(point);
					initdrawinnercharbuffer(arrayforinnertext.size() - 1);
					openglrender.drawstiplineflag = true;
//					Log.i("debug","arrayforinnertext add point !!!"+arrayforinnertext.size());
					
					
				}
			}
			// this.requestRender();
		}

		if (end == true)
		{// 采集到的当前点是一笔的结束点

			switch (ListTable.TypeofUnit)
			{
				case Context_STATE.Context_STATE_0:
					// Log.i("DEBUG--->","ZHELI???????????????1111");
					strokeend = true;
					islast.add(1);
//					 Log.i("debug in addpoint to array111","start111 timer !!!"+islast.size());
					timecount.start();
					break;
				case Context_STATE.Context_STATE_2:
					strokeend = true;
					islast.add(1);
//					Log.i("debug in addpoint to array",
//							"start control timer !!!");
					timecountforctrlunit.start();
					break;
			}
		} else
			strokeend = false;
	}

	/**
	 * 
	 */
	private void changeEnd()
	{
		Log.e("","changeend");
		int i = 0;
		if (array.size() > 0)
		{
			array.get(array.size() - 1).setEnd(true);
			i++;
		}
		if (temparray.size() > 0)
		{
			temparray.get(temparray.size() - 1).setEnd(true);
			i++;
		}
		if (charpointarray.size() > 0)
		{
			charpointarray.get(charpointarray.size() - 1).setEnd(true);
			i++;
		}
		if (editarray.size() > 0)
		{
			editarray.get(editarray.size() - 1).setEnd(true);
			i++;
		}
		if (arrayforinnertext.size() > 0)
		{
			arrayforinnertext.get(arrayforinnertext.size() - 1).setEnd(true);
			i++;
		}
		Log.i("debug in change end ","i is "+i);
		if(i>0)
		{
		switch (ListTable.TypeofUnit)
		{
			case Context_STATE.Context_STATE_0:
				// Log.i("DEBUG--->","ZHELI???????????????1111");
				strokeend = true;
				islast.add(1);
//				Log.i("debug in change end ", "change end start timer !!!");
				timecount.start();
				break;
			case Context_STATE.Context_STATE_2:
				strokeend = true;
				islast.add(1);
//				Log.i("debug in change end ", "start changeend timer !!!");
				timecountforctrlunit.start();
				break;
		}
		}
	}

	public void addtolist()
	{
		// 这里面实现了对ListTable.globalIndexTable添加单元的作用
		// Log.i("debug in mview","in the addtolist");
		switch (ListTable.TypeofUnit)
		{
			case Context_STATE.Context_STATE_0:
//				Log.i("debug in add to list ","add to list "+minX+" "+minY+" "+maxX+" "+maxY);
				addtoListTable.addcharunittoListTable(array, (short) minX,
						(short) minY, (short) maxX, (short) maxY);
				Log.i("debug in addto list state 0","array size is "+array.size());
				break;
			case Context_STATE.Context_STATE_1:
				addtoListTable.addimageunittoListTable(array, (short) minX,
						(short) minY, (short) maxX, (short) maxY);
				Log.i("debug in addto list state 1","array size is "+array.size());
				break;
			case Context_STATE.Context_STATE_2:
				Kview.scrollstatus = false;
				if (controlbybutton == false)// 手写的编辑符
				{
					ArrayList<MyPoint> myarray = new ArrayList<MyPoint>();
					Log.i("debug in addto list state 2","array size is "+array.size());
					if (editarray == null || editarray.size() == 0)
						return;
					for (int i = 0; i != editarray.size(); i++)
					{
						// Log.i("debug in addtolist","x and y is "+editarray.get(i).getX()+" "+editarray.get(i).getY());
						myarray.add(new MyPoint(
								(short) editarray.get(i).getX(),
								(short) editarray.get(i).getY(), editarray.get(
										i).isEnd()));
					}
					this.editdfa.startDFA(myarray); // 实现了对手势的识别！！
					Log.i("debug in addto list state 2","myarray size is "+myarray.size());
					myarray.clear();
					// 2011.03.23 liqiang
					array.clear();
					editarray.clear();
					temparray.clear();
					charpointarray.clear();
					clearBuffer();
					// 2011.03.23 liqiang

				} else
				// 按button的编辑符（回车，空格，删除）
				{
					switch (ListTable.TypeofCtrl)
					{
						case Context_STATE.Ctrl_STATE_1: // 换行
//							Log.v("DEBUG---->", "PAN DUAN WEI HUAN HANG!!!");
							ControlUnit controlUnit = new ControlUnit(
									DataType.TYPE_CTRL_ENTER, CharFormat
											.getDefaultCharFormat());
							ListTable.globalIndexTable.add(
									ListTable.temppositionofaddcharunit,
									controlUnit);
							ListTable.temppositionofaddcharunit++;
							break;

						case Context_STATE.Ctrl_STATE_2: // 删除
							break;
						case Context_STATE.Ctrl_STATE_3: // 空格
							ControlUnit controlunit = new ControlUnit(
									DataType.TYPE_CTRL_SPACE, CharFormat
											.getDefaultCharFormat());
							ListTable.globalIndexTable.add(
									ListTable.temppositionofaddcharunit,
									controlunit);
							ListTable.temppositionofaddcharunit++;

							break;
						case Context_STATE.Ctrl_STATE_4: // 左选择 和右选择都完成！！填充背景
//							Log.i("debug", "color in edit !!!");
							ListTable.fillcolor = true;
							break;
						case Context_STATE.Ctrl_STATE_5: // 复制
							break;
						case Context_STATE.Ctrl_STATE_6: // 粘贴
							break;
						case Context_STATE.Ctrl_STATE_7: // 剪切
							break;
						case Context_STATE.Ctrl_STATE_8: // 回退
							int unitGlobalIndex = ListTable.temppositionofaddcharunit;
							if (unitGlobalIndex > ListTable.globalTitle.size()  + 1//zhuxiaoqing 2011.09.28
									&& WritebySurfaceView.titleisclicked)
							{// 点击标题按钮后，即删除正文内容
								ListTable.globalIndexTable
										.remove(unitGlobalIndex - 1);
								ListTable.temppositionofaddcharunit--;
							} else if ((unitGlobalIndex > ListTable.globalTitle
									.size() && !WritebySurfaceView.titleisclicked))//zhuxiaoqing 2011.09.28
							{// 点击标题按钮之前，即删除标题内容
								ListTable.globalIndexTable
										.remove(unitGlobalIndex - 1);
								ListTable.temppositionofaddcharunit--;
							}
							break;
						default:
							break;
					}
				}

				switch (ListTable.TypeofCtrl)
				{
					case Context_STATE.Ctrl_STATE_1: // 换行
						break;
					case Context_STATE.Ctrl_STATE_2: // 删除
						ListTable.fillcolor = false;
						break;
					case Context_STATE.Ctrl_STATE_3: // 空格
						break;
					case Context_STATE.Ctrl_STATE_4: // 左选择 和右选择都完成！！填充背景
						ListTable.fillcolor = true;
//						Log.i("DEBUG-->", "STATE_4  " + ListTable.fillcolor);
						break;
					case Context_STATE.Ctrl_STATE_5: // 复制
						break;
					case Context_STATE.Ctrl_STATE_6: // 粘贴
						ListTable.fillcolor = false;
						break;
					case Context_STATE.Ctrl_STATE_7: // 剪切
						ListTable.fillcolor = false;
						break;
					case Context_STATE.Ctrl_STATE_8: // 回退
						break;
				}
				break;
		}

		clear = true;
		controlbybutton = false;
		// 2011.02.23
		// Log.i("debug", "start thread to paint !!!");
		redrawkviewflag = true;
		postInvalidate(); // 在多线程事件里好像必须得用postInvalidate（） 来刷新view
		// new Thread(mythread).start(); // 清除Mview上的残留字迹，好像只用postInvalidate
		paint();

	}

	private Timer timer1;

	class Timecount
	{
		private int milseconds = 0;

		public Timecount(int second)
		{
			this.milseconds = second;
		}

		public void start()
		{
			timer1 = new Timer();
			timer1.schedule(new TimerTask()
			{
				public void run()
				{
					if(islast.size()>0)
					{
					if (innertextstatus == false)
					{
						if (strokeend == true && islast.get(location) == 1
								&& isdoubleclick == false)
						{
							if (ListTable.isAutoSubmit == 1
									|| (ListTable.isAutoSubmit == 0 && array
											.size() <= 5))
							{
								Log.v("debug in noinner char", "time out !!!");
								// 2011.02.23 liqiang
								// index = 0;
								// 清空drawcharbuffer
								islast.clear();// 2011.04.12 liqiang
								location = 0;
								addtolist();
								openglrender.clearflag = true;
								temparray.clear();
								array.clear();
								charpointarray.clear();
								openglrender.drawstiplineflag = true;
								clearBuffer();
								drawRender();
								// thisview.requestRender();
								// 2011.02.23 liqiang
							}
						} else
							location++;
					} else
					{
//						Log.i("debug in timer","islast is "+islast.size()+" "+location+" "+strokeend+arrayforinnertext.size());
						
						
						if (strokeend == true && islast.get(location) == 1)
						{
							if (arrayforinnertext.size() > 5)
							{
								// Log.v("SHI JIAN DAO LA !!!!!",
								// "innertextstatus == true");
								CharUnit charunit = AddastoCharunit(
										arrayforinnertext, (short) minX,
										(short) minY, (short) maxX,
										(short) maxY);
								UnitforInnerText.add(charunit);
								StoretoImageUnit(charunit, array);

								arrayforinnertext.clear();
//								innercharpointcount = 0;
//								innercharpointfloat = null;
//								innercharpointfloat = new float[maxpointnum];
								openglrender.drawinnercharbuffer.clear();
//								Log.i("debug in timer ", "" + copyofinnerx
//										+ " " + copyofinnery + " "
//										+ maxXofinnertext + " "
//										+ maxYofinnertext);
								initRectBuffer(false);
								islast.clear();
								mPath.reset();
								location = 0;
								minX = 1000;
								minY = 1000;
								maxX = 0;
								maxY = 0;
								x = -1; // 这里为何把x，和y弄成0，为了是防止 下面minx的初始值再被破坏
								y = -1;
								// new Thread(mythread).start();
								// 2011.03.12
								openglrender.drawstiplineflag = true;
								openglrender.timeoutflag = true;
								drawRender();
								// thisview.requestRender();

							} else
							{
								arrayforinnertext.clear();
//								innercharpointcount = 0;
//								innercharpointfloat = null;
//								innercharpointfloat = new float[maxpointnum];
								openglrender.drawinnercharbuffer.clear();

								islast.clear();
								mPath.reset();
								location = 0;
								minX = 1000;
								minY = 1000;
								maxX = 0;
								maxY = 0;
								x = -1; // 这里为何把x，和y弄成0，为了是防止 下面minx的初始值再被破坏
								y = -1;
								// 2011.03.12 liqiang
								openglrender.drawstiplineflag = true;
								openglrender.timeoutflag = true;
								drawRender();
								// thisview.requestRender();
							}
						} else 
							// zhuxiaoqing
							location++;
					}
					}
				}
			}, milseconds);
		}
	}

	private Timer timer2;

	class TimecountforCtrlUnit
	{
		private int milseconds = 0;

		public TimecountforCtrlUnit(int second)
		{
			this.milseconds = second;
		}

		public void start()
		{
			timer2 = new Timer();
			timer2.schedule(new TimerTask()
			{
				public void run()
				{
					Log.v("debug in control timer", "before !!!");
					if (strokeend == true && isdoubleclick == false&&!stopcontroltimerflag)
					{
						Log.v("debug in control timer", "!!!");
						stopcontroltimerflag = false;
						location = 0;
						openglrender.clearflag = true;
						openglrender.draweditflag = false;
						openglrender.drawstiplineflag = false;
						clearBuffer();
						drawRender();
						// thisview.requestRender();
						addtolist();
					} else
						// zhuxiaoqing
						location++;
				}
			}, milseconds);
		}
	}

	private Timer timer3;

	class TimecountforAddpoint
	{
		private int milseconds = 0;

		public TimecountforAddpoint(int second)
		{
			this.milseconds = second;
		}

		public void start()
		{
			timer3 = new Timer();
			timer3.schedule(new TimerTask()
			{
				public void run()
				{
					if (openglrender.touchflag == false)
					{
						if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0)
						{
							if (openglrender.drawcharbufferlock&& temparray.size() > charpointcount)
							{
//								Log.v("SHI JIAN DAO LA !!!!!", "draw char ");
								openglrender.drawcharbufferlock = false;
								charpointcount = temparray.size();
								openglrender.drawcharbuffer.put(charpointfloat,
										0, charpointcount * 2);
								openglrender.drawcharbuffer.position(0);
								openglrender.drawcharbufferlock = true;
							}
							else if (openglrender.drawinnercharbufferlock && arrayforinnertext.size()>innercharpointcount)
							{
								Log.v("SHI JIAN DAO LA !!!!!", "draw innerchar ");
								openglrender.drawinnercharbufferlock = false;
								innercharpointcount = arrayforinnertext.size();
								openglrender.drawinnercharbuffer.put(innercharpointfloat, 0,
										innercharpointcount * 2);
								openglrender.drawinnercharbuffer.position(0);
								
								openglrender.drawinnercharbufferlock = true;
							}
							drawRender();
						} else if (ListTable.TypeofUnit == Context_STATE.Context_STATE_1)
						{
							
							if (openglrender.drawpicturebufferlock&&picpointcount<array.size())
							{
//								Log.v("SHI JIAN DAO LA !!!!!", "draw pic ");
								openglrender.drawpicturebufferlock = false;
//								Log.v("debug in timecount",""+openglrender.drawpicturebufferlock);
								picpointcount = array.size();
								openglrender.drawpicbuffer.put(picturepointfloat, 0, picpointcount * 2);
								openglrender.drawpicbuffer.position(0);
								openglrender.drawpicturebufferlock = true;
//								Log.v("debug in timecount",""+openglrender.drawpicturebufferlock);
							}
							drawRender();
						}
					}

				}
			}, milseconds);
		}
	}

	// 将在Mview上画的内嵌字单元压缩到我们指定的大小
	private CharUnit AddastoCharunit(ArrayList<BasePoint> array, short minX,
			short minY, short maxX, short maxY)
	{

		CharUnit charunit1 = new CharUnit((short) (maxX - minX),
				(short) (maxY - minY), Color.BLUE); // 见MemoryCoder.java 的
		// startCharUnit ()部分
		charunit1.setCharFormat(CharFormat.getDefaultCharFormat());
		charunit1
				.setWidth((short) (charunit1.getWidth() + (CharUnit.ANCHOR << 1)));

		for (int i = 0; i != array.size(); i++)
		{
			BasePoint point = new CharPoint(
					(short) (array.get(i).getX() - minX), (short) (array.get(i)
							.getY() - minY));
			point.setEnd(array.get(i).isEnd());
			charunit1.addPoint(point);
		}

		CharUnit charunit;

		if ((maxX - minX) < 20 && (maxY - minY) < 20)
			charunit = (CharUnit) charunit1.clone(); // 这个是为了判断标点符号而作的判断，标点符号不用压缩！！
		else
		{
			// 至此我们得到了经过压缩后的所有的点集
			// charunit = HWtrying.compression(charunit1,(short)
			// WritebySurfaceView.innertextsize);
			charunit = (CharUnit) Zoom.compressionForInnerText(array,
					(short) (maxY - minY), minY, maxY,
					(short) WritebySurfaceView.innertextsize);
		}

		maxofinnery = (short) (maxofinnery > WritebySurfaceView.innertextsize ? maxofinnery
				: WritebySurfaceView.innertextsize);

		if (innerx + charunit.getWidth() > Kview.screenwidth
				- leftmarginforinnertext)
		{// 内嵌字不要被右侧的各个按钮覆盖，所以减了一个量：leftmarginforinnertext
			innerx = copyofinnerx;
			baselineheight = (short) (maxofinnery + innertextheightspace);// innertextheightspace
			// 为内嵌字上排和下排之间的空白间隔
			innery += baselineheight;
		}

		for (int j = 0; j != charunit.getPoints().size(); j++)
		{
			short x, y;
			boolean end;
			x = (short) (charunit.getPoints().get(j).getX() + innerx);
			y = (short) (charunit.getPoints().get(j).getY() + innery);
			end = charunit.getPoints().get(j).isEnd();
			charunit.getPoints().get(j).setX(x);
			charunit.getPoints().get(j).setY(y);
			charunit.getPoints().get(j).setEnd(end);
			minXofinnertext = minXofinnertext < x ? minXofinnertext : x;
			minYofinnertext = minYofinnertext < y ? minYofinnertext : y;
			maxXofinnertext = maxXofinnertext < x ? x : maxXofinnertext;
			maxYofinnertext = maxYofinnertext < y ? y : maxYofinnertext; // 至此得到了压缩后的内嵌字单元的最大最小x、y
		}
		innerx += charunit.getWidth();
		innerx += innertextwidthspace;
		return charunit;

	}

	// 把内嵌字的点添加到图片单元当中
	private void StoretoImageUnit(CharUnit charunit, ArrayList<BasePoint> array)
	{
		// clearBuffer();
//		innercharpointcount = 0;
//		innercharpointfloat = null;
//		innercharpointfloat = new float[maxpointnum];
		openglrender.drawinnercharbuffer.clear();
		// lastendpoint = 0;
		// currentpoint = 0;
		ArrayList<BasePoint> arr = (ArrayList<BasePoint>) charunit.getPoints();
		for (int j = 0; j != arr.size(); j++)
		{
//			Log.i("debug in store to image ","add point to array !!!");
			array.add(arr.get(j));
			// 2011.03.12 liqiang
			initdrawpicbuffer(array.size() - 1);
		}
	}

	// 在绘图状态下，点击按钮时候保存当前图片单元
	protected void SaveImage()
	{
		 if (ListTable.TypeofUnit == Context_STATE.Context_STATE_1||
		 (ListTable.TypeofUnit == Context_STATE.Context_STATE_0 &&
		 innertextstatus == true)) // 处于图片状态或者图片内资字状态
//		if (ListTable.TypeofUnit == Context_STATE.Context_STATE_1) // 处于图片状态或者图片内资字状态
		{
			patchangefordrawing = false;
			Kview.mpaint.setColor(Color.GRAY);
			for(int i=0;i<Mview.paintstrokesizelist.size();i++)
			{
				Log.e("debug in saveimage","size is "+Mview.paintstrokesizelist.get(i));
			}
			if (UnitforInnerText.size() != 0)
			{
				// Log.i("DEBUG--->","UnitforInnerText.size()  "+
				// UnitforInnerText.size());
				UnitforInnerText.clear();
				innerx = 80;
				minX = minXofinnertext < copyofminX ? minXofinnertext
						: copyofminX;
				minY = minYofinnertext < copyofminY ? minYofinnertext
						: copyofminY;
				maxX = maxXofinnertext > copyofmaxX ? maxXofinnertext
						: copyofmaxX;
				maxY = maxYofinnertext > copyofmaxY ? maxYofinnertext
						: copyofmaxY;
				UnitforInnerText.clear();
			} else if (UnitforInnerText.size() == 0 && innertextstatus == true)
			{
				minX = copyofminX;
				minY = copyofminY;
				maxX = copyofmaxX;
				maxY = copyofmaxY;
			}
//			Log.i("debug in mview save image","array.size is "+array.size()+" "+array.get(array.size()-1).isEnd());
			if (array.size() != 0)
				addtolist();
			for(int i=0;i<Mview.paintstrokesizelist.size();i++)
			{
				Log.e("debug in after addtolist","size is "+Mview.paintstrokesizelist.get(i));
			}
//			WritebySurfaceView.graphbar.setVisibility(View.GONE);
			array.clear();
			temparray.clear();
			// 2011.03.06
			charpointfloat = null;
			charpointfloat = new float[maxpointnum*2];
			charpointcount = 0;
			// 2011.03.09 liqiang
			if (openglrender.drawpictureflag)
			{
				picpointcount = 0;
				picturepointfloat = null;
				picturepointfloat = new float[maxpointnum*2];
				openglrender.drawpicbuffer.clear();
			}
			if (openglrender.drawinnercharflag)
			{
//				innercharpointcount = 0;
//				innercharpointfloat = null;
//				innercharpointfloat = new float[maxpointnum];
				openglrender.drawinnercharbuffer.clear();
			}
			clearBuffer();
			openglrender.clearflag = true;
			if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0)
				openglrender.drawstiplineflag = true;// zhuxiaoqing
			else
				openglrender.drawstiplineflag = false;
			this.requestRender();
			// 2011.03.10 liqiang
			x = -1; // 这里为何把x，和y弄成0，为了是防止 下面minx的初始值再被破坏
			y = -1;
			copyofminX = 0;
			copyofminY = 0;
			copyofmaxX = 0;
			copyofmaxY = 0;
			minXofinnertext = 1000;
			minYofinnertext = 1000;
			maxXofinnertext = 0;
			maxYofinnertext = 0;
			maxofinnery = -1;
			ListTable.TypeofGraph = Context_STATE.Graph_STATE_Default;
			WritebySurfaceView.button_color.setVisibility(View.GONE);
			WritebySurfaceView.button_eraser.setVisibility(View.GONE);
			WritebySurfaceView.button_innertext.setVisibility(View.GONE);
			WritebySurfaceView.button_backtodraw.setVisibility(View.GONE);
			WritebySurfaceView.vSeekBar.setVisibility(View.GONE);
			WritebySurfaceView.InnertextsizeBar.setVisibility(View.GONE);
			WritebySurfaceView.textview_paintstrokesizetext
					.setVisibility(View.GONE);
			WritebySurfaceView.textview_innertextsizetext
					.setVisibility(View.GONE);

			WritebySurfaceView.button_smalleraser.setVisibility(View.GONE);
			WritebySurfaceView.button_largeeraser.setVisibility(View.GONE);
			WritebySurfaceView.button_middleeraser.setVisibility(View.GONE);

			WritebySurfaceView.button_backtodraw
					.setBackgroundDrawable(WritebySurfaceView.huihuaimage);
			WritebySurfaceView.button_eraser
					.setBackgroundDrawable(WritebySurfaceView.eraserimage);
			WritebySurfaceView.button_innertext
					.setBackgroundDrawable(WritebySurfaceView.wenziimage);

			WritebySurfaceView.button_backspace.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_graph.setEnabled(true);
			WritebySurfaceView.button_upslow.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_downslow.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_upquick.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_downquick.setVisibility(View.VISIBLE);
			if (ListTable.isAutoSubmit == 0)
				WritebySurfaceView.button_manualsubmit
						.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_graph
					.setBackgroundDrawable(WritebySurfaceView.huatuimage);

			ListTable.globalcolorIndex.clear(); // 这里为什么要加这两句对图片单元数据清空的动作，是为了防止出现在画图时候，突然放弃了，直接点击Text类型，继续写字，所以必须废除以前的信息
			paintstrokesizelist.clear();

			patchangefordrawing = false;
			Kview.mpaint.setColor(Color.GRAY);
		}
	}

	short scopex, scopey; // 用来记录在橡皮擦状态下删除笔画时候点击的位置

	private void EraseImage(short radius)
	{
		short tempposition = -1;
		short x = 0, y = 0;
		scopex = point.getX();
		scopey = point.getY();
		for (int m = 0; m != array.size(); m++)
		{
			x = array.get(m).getX();
			y = array.get(m).getY();
			if (x <= scopex + radius && x >= scopex - radius
					&& y <= scopey + radius && y >= scopey - radius)
			{// 如果array 中的点在擦除的范围内

				tempposition = (short) m;

				break;
			}
		}
		if (tempposition != -1)
		{
			int colorindexsize = ListTable.globalcolorIndex.size() - 1;
			int strokesize = paintstrokesizelist.size() - 1;

			// 这里应该对array里去除最后一笔的所有点！并且对path里的路径也需要作修改，然后刷新～～
			int k = array.size();
			int n = 0, locationofstroke = -1;
			for (n = tempposition; n != k; n++)
			{
				if (array.get(n).isEnd()) // 找到那一笔的最后一个点
					break;
			}
			k = n - 1; // 需要删除的那一笔的最后一个点（true）前的一个点
			int m = k + 2;
			while (k >= 0 && array.get(k).isEnd() != true)
				// 
				k--;

			for (int t = 0; t != tempposition + 1; t++)
			{
				if (array.get(t).isEnd())
					locationofstroke++; // “改点”所在的笔画数
			}
			if (k > 0)
			{
				array.subList(k + 1, m).clear();
				ListTable.globalcolorIndex.remove(locationofstroke + 1);
				paintstrokesizelist.remove(locationofstroke + 1);

			} else if (k == -1)
			{
				if (colorindexsize == 0)
				{
					array.clear();
					ListTable.globalcolorIndex.remove(colorindexsize);
					paintstrokesizelist.remove(strokesize);
				} else
				{
					array.subList(k + 1, m).clear();
					ListTable.globalcolorIndex.remove(locationofstroke + 1);
					paintstrokesizelist.remove(locationofstroke + 1);
				}

			}

			minX = 1000;
			minY = 1000;
			maxX = 0;
			maxY = 0;
			x = -1; // 这里为何把x，和y弄成0，为了是防止 下面minx的初始值再被破坏
			y = -1;

			for (int j = 0; j != array.size(); j++)
			{
				x = array.get(j).getX();
				y = array.get(j).getY();

				if (minX > x && x >= 0 && y >= 0)
					minX = x;
				if (minY > y && x >= 0 && y >= 0)
					minY = y;
				if (maxX < x && x >= 0 && y >= 0)
					maxX = x;
				if (maxY < y && x >= 0 && y >= 0)
					maxY = y;
			}
		}
		// 20110419 liqiang
		if (array.size() == 0)
		{
			openglrender.clearflag = true;
			openglrender.drawstiplineflag = false;
//			Log.v("debug in eraser1","lock is "+openglrender.drawpicturebufferlock);
			changedrawpicbuffer();
			this.requestRender();
		} else
		{
//			Log.v("debug in eraser2","lock is "+openglrender.drawpicturebufferlock);
			changedrawpicbuffer();
//			Log.v("debug in eraser3","lock is "+openglrender.drawpicturebufferlock);
			this.requestRender();
		}
	}

	public void changedrawpicbuffer()
	{
		picturepointfloat = null;
		picturepointfloat = new float[maxpointnum*2];
		for (int i = 0; i < array.size(); i++)
		{
			float x = array.get(i).getX();
			float y = array.get(i).getY();
			x = (x - OpenGlRender.coordinatex) / OpenGlRender.coordinatex;
			picturepointfloat[i * 2] = x;
			y = 1.0f - y / OpenGlRender.coordinatey;
			picturepointfloat[i * 2 + 1] = y;
		}

		openglrender.drawpictureflag = true;
		if (openglrender.drawpicturebufferlock)
		{
			openglrender.drawpicturebufferlock = false;
//			Log.v("debug in changedrawpic",""+openglrender.drawpicturebufferlock);
			picpointcount = array.size();
			openglrender.drawpicbuffer.put(picturepointfloat, 0,
					picpointcount * 2);
			openglrender.drawpicbuffer.position(0);
			openglrender.drawpicturebufferlock = true;
//			Log.v("debug in changedrawpic",""+openglrender.drawpicturebufferlock);
		}
	}

	private void isdoubleclick(BasePoint point)
	{
		// 检测双击事件
		count++;
		if (count == 1)
		{
			firClick = System.currentTimeMillis();
			distanceTime = 0l;
			mousefirstpoint = point;

		} else if (count == 2)
		{
			secClick = System.currentTimeMillis();
			distanceTime = secClick - firClick;
			mousesecpoint = point;
//			 Log.i("DEBUG--->","ff  secClick - firClick   "+distanceTime);
//			 Log.i("DEBUG--->","ff  secClick - firClick   "+(mousefirstpoint.getX() - mousesecpoint.getX())
//					 +" "+(mousefirstpoint.getY() - mousesecpoint.getY()));
			if (distanceTime < 500
					&& (Math.abs(mousefirstpoint.getX() - mousesecpoint.getX())) < 25
					&& (Math.abs(mousefirstpoint.getY() - mousesecpoint.getY())) < 25)//2011.12.01 liqiang 15
			{
				stopcontroltimerflag = true;//2011.10.17 liqiang
				isdoubleclick = true;
				addtoListTable.cursorloacteinaddtolist((short)(point.getX()-ListTable.leftmargin), (short)(point.getY()-ListTable.charactersize-16), true);//2011.10.17 liqiang
				if (islast.size() >= 1)
					islast.set(islast.size() - 1, 0);
				count = 0;
				firClick = 0;
				secClick = 0;
				
				array.clear();
				openglrender.drawcharbuffer.clear();
				openglrender.clearflag = false;
				openglrender.drawstiplineflag = false;
				drawRender();
				
				short temp = (short) (ListTable.temppositionofaddcharunit);
				Log.i("debug in doubleclick ","temp is "+temp);
				if (temp < ListTable.globalIndexTable.size()
						&& ListTable.globalIndexTable.get(temp).getDataType() == DataType.TYPE_IMAGE)
				{
					ListTable.globalcolorIndex.clear();
					paintstrokesizelist.clear();

					ImageUnit dataunit = (ImageUnit) ListTable.globalIndexTable
							.get(temp);
					List<MyStroke> stroke = dataunit.getStrokes();
					ArrayList<BasePoint> tempstroke = new ArrayList<BasePoint>();
					BasePoint temppoint;
					int strokesize = stroke.size();
					int color, strokewidth;

					minX = 1000; // 进入到这个判断里面表示，点击“内嵌字”状态后，再点击一块空地方，以用来放内签字，所以必须也对minX,minY,maxX,maxY清零
					minY = 1000;
					maxX = 0;
					maxY = 0;
					x = -1; // 这么设置时为了再下面的判断时候不让内嵌字的位置定位坐标影响到minX，minY ....
					y = -1;
					short pointx = 0,pointy = 0;
//					Log.i("debug in isdoubleclick ","strokesize is "+strokesize);
					int pointcount = 0,strokecount = 0;
					for (int j = 0; j != strokesize; j++)
					{
						tempstroke = (ArrayList<BasePoint>) stroke.get(j)
								.getPoints();
						color = stroke.get(j).getColor();
						ListTable.globalcolorIndex.add(color);

						strokewidth = (int) stroke.get(j).getStrokeWidth();
						paintstrokesizelist.add(strokewidth);
						for (int i = 0; i < tempstroke.size(); i++)
						{
							temppoint = tempstroke.get(i);
							//2011.10.17 liqiang
							pointx = (short) (temppoint.getX()+ListTable.leftmargin*3);
							pointy = (short) (temppoint.getY()+ListTable.topmargin*3);
							temppoint.setX(pointx);
							temppoint.setY(pointy);
							//2011.10.17 liqiang
//							Log.i("debug in isdouble ","is end or not "+temppoint.isEnd());
							if(i+1==tempstroke.size())
							{	
								strokecount++;
								temppoint.setEnd(true);
							}
							else
								temppoint.setEnd(false);
							array.add(temppoint);
							pointcount++;
//							initdrawpicbuffer(array.size() - 1);//2011.10.17 liqiang
							
							x = temppoint.getX();
							y = temppoint.getY();

							if (minX > x && x >= 0 && y >= 0)
								minX = x;
							if (minY > y && x >= 0 && y >= 0)
								minY = y;
							if (maxX < x && x >= 0 && y >= 0)
								maxX = x;
							if (maxY < y && x >= 0 && y >= 0)
								maxY = y;
						}

					}
					initdrawpicbuffer(array);//2011.12.05 liqiang
//					Log.i("debug in doubleclick","pointcount is "+pointcount+" "+strokecount);
//					Log.i("debug in doubleclick ","array is end "+array.size()+" "+array.get(array.size()-1).isEnd());
					
					ListTable.globalIndexTable.remove(temp);

					// 双击事件
					ListTable.TypeofUnit = Context_STATE.Context_STATE_1;
					ListTable.TypeofGraph = Context_STATE.Graph_STATE_0;
//					WritebySurfaceView.graphbar.setVisibility(View.VISIBLE);
					WritebySurfaceView.button_color.setVisibility(View.VISIBLE);
					WritebySurfaceView.button_eraser
							.setVisibility(View.VISIBLE);
					WritebySurfaceView.button_innertext
							.setVisibility(View.VISIBLE);
					WritebySurfaceView.button_backtodraw
							.setVisibility(View.VISIBLE);
					WritebySurfaceView.button_backtodraw
					.setBackgroundDrawable(WritebySurfaceView.huihuaimageselected);
					WritebySurfaceView.button_autospace.setEnabled(true);
					WritebySurfaceView.button_innertext.setEnabled(true);
					WritebySurfaceView.button_graph.setEnabled(false);
					WritebySurfaceView.button_graph
							.setBackgroundDrawable(WritebySurfaceView.huatuimageselected);
					WritebySurfaceView.button_text
							.setBackgroundDrawable(WritebySurfaceView.biimageselected);
					WritebySurfaceView.button_edit
							.setBackgroundDrawable(WritebySurfaceView.bianjiimage);
					WritebySurfaceView.button_backspace
							.setVisibility(View.GONE);
					WritebySurfaceView.button_upslow.setVisibility(View.GONE);
					WritebySurfaceView.button_downslow.setVisibility(View.GONE);
					WritebySurfaceView.button_upquick.setVisibility(View.GONE);
					WritebySurfaceView.button_downquick
							.setVisibility(View.GONE);
					WritebySurfaceView.button_manualsubmit
							.setVisibility(View.GONE);
					Kview.mpaint.setColor(Color.TRANSPARENT);
					Kview.pat.setColor(Color.TRANSPARENT);
					patchangefordrawing = true;
					locationforinnertext = false;
					innertextstatus = false;
					innerx = 0;
					innery = 0;
					copyofinnerx = 0;
					copyofinnery = 0;
					maxofinnery = -1;
					clear = false;
					paint();
					openglrender.drawstiplineflag = false;//2011.10.17 liqiang
					this.requestRender();//2011.10.17 liqiang
					postInvalidate();//2011.12.26 liqiang
//					Log.i("debug in doubuleclick ","array is end "+array.size()+" "+array.get(array.size()-1).isEnd());
				} else
				{
					if (ListTable.globalIndexTable.get(temp).getDataType() != DataType.TYPE_IMAGE)
					{
						Log.i("debug in isdoubleclick ","this is not a pic !!!");
						array.clear();
						openglrender.drawcharbuffer.clear();
						openglrender.clearflag = false;
						openglrender.drawstiplineflag = false;
						drawRender();
					}
				}
			} else
			{
				count = 1;
				firClick = secClick;
				secClick = 0;
				isdoubleclick = false;
				mousefirstpoint = point;
//				Log.i("debug in doubleclick ","this is not doubleclick !!!");
			}
		}
	}

}
