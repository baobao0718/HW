package com.study.android.HWtrying;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.study.android.ContextState.Context_STATE;
import com.study.android.VerticalSeekbar.VerticalSeekBar;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.CharUnit;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.code.DocumentDecoder;
import com.study.android.configure.Configure;
import com.study.android.configure.CreatePath;
import com.study.android.configure.DefaultConfigure;
import com.study.android.configure.Configure.PThread;
import com.study.android.data.ListTable;
import com.study.android.edit.EditDFA;
import com.study.android.retrieval.ShowRetrieve;
import com.study.android.retrieval.Transform;
import com.study.android.ui.HwFunctionWindow;
import com.study.android.zoom.Zoom;

public class WritebySurfaceView extends Activity implements
		ColorPickerDialog.OnColorChangedListener
{
	/** Called when the activity is first created. */

	private Button button_title;
	private TextView textview_tips;
	static Button button_done;
	public static Button button_graph;
	public static Button button_text;
	public static Button button_edit;
	public static Button button_color;
	public static Button button_space;
	public static Button button_enter;
	public static Button button_backspace;
	public static Button button_autospace;
	public static Button button_eraser;
	public static Button button_innertext;
	public static Button button_backtodraw;
	public static TextView textview_paintstrokesizetext;
	public static TextView textview_innertextsizetext;
	public static VerticalSeekBar vSeekBar;
	public static VerticalSeekBar InnertextsizeBar;
	public static Button button_upslow; // 一行一行的回退
	public static Button button_downslow;
	public static Button button_upquick; // 一次多行回退
	public static Button button_downquick;
	public static Button button_largeeraser;
	public static Button button_middleeraser;
	public static Button button_smalleraser;
	public static Button button_manualsubmit;

//	public static Spinner spinner_email;// 2011.06.20 liqiang
	public Button button_export;//2011.07.20 liqiang
	private EditText exportfilename;
	private RadioGroup formatgroup,sizegroup;
	private RadioButton pdfformat, a4size,b5size,pngformat;
	private String strexportfilename,strexportfileformat="png",strexportfilesize="a4";
	private Button button_exportfilesure,button_exportfilecancel;

	public static LinearLayout barview;
	public static float paintstrokesize = 3; // 初始画笔的宽度为3
	public static float innertextsize = 40; // 初始内嵌字的高度为40
	public static boolean titleisclicked = false;
	public static boolean qiehuan = false;
	private short quicklinenumpertime = 3; // 编辑状态下控制快速前进、回退的行数
	public static RelativeLayout  bootombar;
	private float version = (float) 1.14;
	protected static Drawable huatuimage,huihuaimageselected,huatuimageselected,biimageselected,bianjiimage,wenziimage,huihuaimage,eraserimage;
	private Drawable landviewbackgroundimage, portviewbackgroundimage;
	private Drawable UpslowImage,
	DownslowImage, UpQuickImage, DownQuickImage, exportimage,biimage,
	konggeimage, huicheimage,huituiimage,doneimage,okimage,spacectrlimage,
	yanseimage,largeeraserimage,middleeraserimage,smalleraserimage,
	bianjiimageselected, konggeimageselected, huicheimageselected,
	huituiimageselected,doneimageselected,spacectrlimageselected,
	wenziimageselected,eraserimageselected,largeeraserimageselected,
	middleeraserimageselected,smalleraserimageselected;
	private Drawable UpslowImage_Selected, DownslowImage_Selected,
			UpQuickImage_Selected, DownQuickImage_Selected,nullimage = null;

	public static String Typeofoper;
	public static Kview kview;
	public static int pos=0;//zhuxiaoqing 2011.11.17
	public Mview mview;
	// 2011.01.07 liqiang
	private LinearLayout layout01,layout02,layout03,layout04,layout05;
	private Dialog alertdialog;
	private static String classname = "";
	public static short BASEHEIGHT = 0;// 2011.06.09 liqiang
	public int clickbrowsenumber = 0;
	public int serionumber = 0;
	public String filename2="";//zhuxiaoqing 2011.08.21
	private static DocumentDecoder documentDecoder;
	public static boolean getpdfflag = false;
	public static boolean getmailflag = false;
	public static int pdfpagecount = -1;
	
	public static int pdfpageheight = (int)PageSize.A4.getHeight(),
	pdfpagewidth = (int)PageSize.A4.getWidth();

	OnClickListener editbuttonlistener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
//			Log.i("debug in writebysurfaceview ","click edit button !!!");
//			View mview = (View) findViewById(R.id.mviewid);

			if (mview.getVisibility() == View.GONE)
			{
				mview.setVisibility(View.VISIBLE);
				WritebySurfaceView.kview
						.setBackgroundColor(Color.TRANSPARENT);
				WritebySurfaceView.button_backspace
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_graph
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_enter
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_space
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_enter
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_autospace
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_done
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_text
						.setBackgroundDrawable(biimageselected);
			}

			mview.SaveImage();

			// location=0;
			mview.islast.clear();// zhuxiaoqing
			Mview.temparray.clear();
			Mview.array.clear();
			Mview.editarray.clear();
			mview.charpointbuf.clear();
			mview.openglrender.drawcharbuffer.clear();
			Mview.charpointcount = 0;
			mview.charpointfloat = null;
			mview.charpointfloat = new float[mview.maxpointnum];
			mview.openglrender.circleBuffer.clear();
			// if(v.getClass().getName()=="Mview")
			mview.clearBuffer();
			WritebySurfaceView.button_text
					.setBackgroundDrawable(biimageselected);
			WritebySurfaceView.button_edit
					.setBackgroundDrawable(bianjiimageselected);
			mview.clear = true;
			mview.controlbybutton = false;
			ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
			ListTable.fillcolor = false; // 切换到编辑状态，则fillcolor置为false
			mview.editdfa.clearDFA(); // 切换到编辑状态，需要对状态机进行清空
			EditDFA.pastePanel.clear(); // 清除粘贴板内容
			Kview.selectedleftindex = -1;
			Kview.selectedrightindex = -1;

//			new Thread(mythread).start();
			mview.paint();
			kview.postInvalidate();//2011.12.26 liqiang
			mview.redrawkviewflag = true;
		}};
	OnClickListener textbuttonlistener = new OnClickListener(){

		// Text钮触发事件！！把这个控制按钮从WritebySurfaceView移动到Mview
		// 里面来的原因是可能有些情况下我这个图片没画完，没有点击imagesave按钮，
		// 而直接点击TEXT按钮，所以必须要考虑这样的情况，下面的edit按钮的原因同样是如此！！
		public void onClick(View v)
		{
//			View mview = (View) findViewById(R.id.mviewid);

			if (mview.getVisibility() == View.GONE) // 说明处在浏览状态
			{

				mview.setVisibility(View.VISIBLE);
				WritebySurfaceView.kview
						.setBackgroundColor(Color.TRANSPARENT);

				WritebySurfaceView.button_backspace
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_graph
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_enter
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_space
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_enter
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_autospace
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_done
						.setVisibility(View.VISIBLE);
				WritebySurfaceView.button_text
						.setBackgroundDrawable(biimage);
			}

			mview.SaveImage();

			WritebySurfaceView.button_text
					.setBackgroundDrawable(biimage);
			WritebySurfaceView.button_edit
					.setBackgroundDrawable(bianjiimage);
			mview.clear = true;
			mview.controlbybutton = false;
			ListTable.fillcolor = false;
			ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
			// 2011.03.22 liqiang
			mview.openglrender.drawstiplineflag = true;
			mview.openglrender.clearflag = true;
			mview.openglrender.drawtimecount = 0;
			mview.clearBuffer();
			mview.drawRender();
			// thisview.requestRender();
			// new Thread(mythread).start();
			//postInvalidate();
			kview.postInvalidate();
			mview.redrawkviewflag = true;
//			Log.i("debug","click in edit !!!");
		}};
	OnClickListener graphbuttonlistener = new OnClickListener(){

		// Graph钮触发事件！！,把这个控制按钮从WritebySurfaceView移动到Mview
		// 里面来的原因是为了把在Kview上的几个画笔的颜色更改掉！更重要的原因是，
		// 可以时时更新，得到我们在点击graph后想要的空白画面的效果！！
		// 因为只有在Mview和Kview里面才能用postinvalidate（）
		public void onClick(View v)
		{
//			Log.e("haha","hah ");
			
			ListTable.TypeofUnit = Context_STATE.Context_STATE_1;
			ListTable.TypeofGraph = Context_STATE.Graph_STATE_0;
			
			WritebySurfaceView.vSeekBar.setVisibility(View.VISIBLE);
			WritebySurfaceView.textview_paintstrokesizetext.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_color
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_eraser
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_innertext
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_backtodraw
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_backtodraw
					.setBackgroundDrawable(huihuaimageselected);
			WritebySurfaceView.button_autospace
					.setEnabled(true);
			WritebySurfaceView.button_innertext
					.setEnabled(true);
			WritebySurfaceView.button_graph.setEnabled(false);
			WritebySurfaceView.button_graph
					.setBackgroundDrawable(huatuimageselected);
			WritebySurfaceView.button_text
					.setBackgroundDrawable(biimageselected);
			WritebySurfaceView.button_edit
					.setBackgroundDrawable(bianjiimage);
			WritebySurfaceView.button_backspace
					.setVisibility(View.INVISIBLE);				/*2012.3.10 by zjc*/
			WritebySurfaceView.button_upslow
					.setVisibility(View.GONE);
			WritebySurfaceView.button_downslow
					.setVisibility(View.GONE);
			WritebySurfaceView.button_upquick
					.setVisibility(View.GONE);
			WritebySurfaceView.button_downquick
					.setVisibility(View.GONE);
			WritebySurfaceView.button_manualsubmit
					.setVisibility(View.GONE);
			Kview.mpaint.setColor(Color.TRANSPARENT);
			Kview.pat.setColor(Color.TRANSPARENT);
			Mview.patchangefordrawing = true;
			
			Mview.locationforinnertext = false;
			Mview.innertextstatus = false;
			mview.innerx = 0;
			mview.innery = 0;
			mview.copyofinnerx = 0;
			mview.copyofinnery = 0;
			mview.maxofinnery = -1;

			// 2011.03.10 liqiang
			mview.clear=true;
			mview.paint();
			mview.clearBuffer();
			mview.openglrender.clearflag = true;
			mview.openglrender.drawstiplineflag = false;
			mview.drawRender();
			kview.postInvalidate();
		}};
		OnTouchListener enterbuttonlistener = new OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if (event.getAction() == MotionEvent.ACTION_DOWN)
			{
				switch (ListTable.TypeofUnit)
				{
					case Context_STATE.Context_STATE_0:
						mview.openglrender.enterflag = true;
						if (Mview.innertextstatus)
						{
							mview.innerx = mview.copyofinnerx;
							mview.baselineheight = mview.maxofinnery;
							mview.innery += mview.baselineheight;
							mview.maxYofinnertext += mview.maxofinnery;//2011.10.18 liqiang
//							Log.i("debug in enterbutton","maxofinnery is "+mview.maxofinnery);
							mview.initRectBuffer(false);
							mview.drawRender();
						} else
						{
							WritebySurfaceView.button_enter
									.setBackgroundDrawable(huicheimageselected);
							ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
							ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_1;
							mview.clear = false;
							mview.controlbybutton = true;
							ListTable.fillcolor = false;
							mview.addtolist();
							WritebySurfaceView.button_graph
									.setEnabled(true);
							// //使状态恢复到文字状态
							ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
							ListTable.TypeofCtrl = -1;
						}
						break;
					case Context_STATE.Context_STATE_1:
//						Log.i("debug in enterbutton","array.size is "+Mview.array.size());
						mview.SaveImage();

						mview.controlbybutton = true;
						ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
						ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_1;
						mview.clear = false;
						mview.addtolist();
						WritebySurfaceView.button_graph
								.setEnabled(true);
						ListTable.TypeofUnit = Context_STATE.Context_STATE_0; // 使状态恢复到文字状态
						ListTable.TypeofCtrl = -1;
//						WritebySurfaceView.graphbar
//								.setVisibility(View.GONE);
						WritebySurfaceView.button_text
								.setBackgroundDrawable(biimage);
						// mBitmap.eraseColor(bgcolor);
						break;
						//2011.04.15 liqiang
					case Context_STATE.Context_STATE_2:
						WritebySurfaceView.button_enter
						.setBackgroundDrawable(huicheimageselected);
//						ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
						ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_1;
						mview.clear = false;
						mview.controlbybutton = true;
						ListTable.fillcolor = false;
						mview.addtolist();
						WritebySurfaceView.button_graph
						.setEnabled(true);
//						// //使状态恢复到文字状态
//						ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
						ListTable.TypeofCtrl = -1;
						break;
						//2011.04.15 liqiang
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP)
				// 改为抬起时的图片
				WritebySurfaceView.button_enter
						.setBackgroundDrawable(huicheimage);

			return false;	
		}};
	OnTouchListener spacebuttonlistener = new OnTouchListener(){
		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if (event.getAction() == MotionEvent.ACTION_DOWN)
			{
//				if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0)
					WritebySurfaceView.button_space
							.setBackgroundDrawable(konggeimageselected);
				// else if (ListTable.TypeofUnit ==
				// Context_STATE.Context_STATE_0)
				// WritebySurfaceView.button_space.setBackgroundDrawable(konggebg1);

				switch (ListTable.TypeofUnit)
				{
					case Context_STATE.Context_STATE_0:
						mview.openglrender.spaceflag = true;
						if (Mview.innertextstatus)
						{
							if (mview.innerx + mview.charactersize / 2 > Kview.screenwidth) // 因为空格的宽度值=其高度值/2
							{
								mview.innerx = mview.copyofinnerx;
								mview.innery += mview.baselineheight;
							} else
								mview.innerx += mview.charactersize;
						} else
						{
							mview.controlbybutton = true;
							ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
							ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_3;
							mview.clear = false;
							ListTable.fillcolor = false;
							mview.addtolist();
							WritebySurfaceView.button_graph
									.setEnabled(true);
							// ListTable.TypeofUnit = 0;
							// //使状态恢复到文字状态
							ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
							ListTable.TypeofCtrl = -1;
						}
						break;

					case Context_STATE.Context_STATE_1:

						mview.SaveImage();

						mview.controlbybutton = true;
						ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
						ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_3;
						mview.clear = false;
						mview.addtolist();
//						WritebySurfaceView.graphbar
//								.setVisibility(View.GONE);

						WritebySurfaceView.button_graph
								.setEnabled(true);
						ListTable.TypeofUnit = Context_STATE.Context_STATE_0; // 使状态恢复到文字状态
						ListTable.TypeofCtrl = -1;
						mview.maxofinnery = -1;
						WritebySurfaceView.button_text
								.setBackgroundDrawable(biimage);
						// mBitmap.eraseColor(bgcolor);
						break;
					case Context_STATE.Context_STATE_2:
						mview.controlbybutton = true;
						WritebySurfaceView.button_space
						.setBackgroundDrawable(konggeimageselected);
//						ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
						ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_3;
						mview.clear = false;
						ListTable.fillcolor = false;
						mview.addtolist();
						WritebySurfaceView.button_graph
								.setEnabled(true);
						// ListTable.TypeofUnit = 0;
						// //使状态恢复到文字状态
//						ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
						ListTable.TypeofCtrl = -1;
						break;
				}

			} else if (event.getAction() == MotionEvent.ACTION_UP)
			{
				// 改为抬起时的图片
//				if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0)
					WritebySurfaceView.button_space
							.setBackgroundDrawable(konggeimage);
//				 else if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0)
//				 WritebySurfaceView.button_space.setBackgroundDrawable(mview.konggebg);
			}
			return false;
		}};
	OnTouchListener backspacebuttonlistener = new OnTouchListener(){

		public boolean onTouch(View v, MotionEvent event)
		{

			if (event.getAction() == MotionEvent.ACTION_DOWN )
			{
				WritebySurfaceView.button_backspace
				.setBackgroundDrawable(huituiimageselected);
				switch (ListTable.TypeofUnit)
				{
					case Context_STATE.Context_STATE_0:
						mview.openglrender.backspaceflag = true;
						mview.controlbybutton = true;
						ListTable.TypeofUnit = Context_STATE.Context_STATE_2;
						ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_8;
						mview.clear = false;
						ListTable.fillcolor = false;
						mview.addtolist();
						ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
						ListTable.TypeofCtrl = -1;
						WritebySurfaceView.button_graph
								.setEnabled(true);
						break;
					case Context_STATE.Context_STATE_2:
						mview.controlbybutton = true;
						// ListTable.TypeofUnit =
				 		// Context_STATE.Context_STATE_2;
						ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_8;
						mview.clear = false;
						ListTable.fillcolor = false;
						mview.addtolist();
						// ListTable.TypeofUnit =
						// Context_STATE.Context_STATE_0;
						ListTable.TypeofCtrl = -1;
						WritebySurfaceView.button_graph
								.setEnabled(true);
						break;
				}
				

			} else if (event.getAction() == MotionEvent.ACTION_UP)

				// 改为抬起时的图片
				WritebySurfaceView.button_backspace
				.setBackgroundDrawable(huituiimage);
				
			return false;
		}};
	OnClickListener manualsubmitbuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			mview.location = 0;
			mview.islast.clear();
			
			mview.addtolist();
			// 2011.02.24 清空drawcharbuffer
			Mview.temparray.clear();
			Mview.array.clear();
			mview.openglrender.clearflag = true;
			mview.openglrender.drawstiplineflag = true;
			mview.clearBuffer();
			mview.charpointarray.clear();
			
			mview.drawRender();
		}};
	OnClickListener eraserbuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			ListTable.TypeofUnit = Context_STATE.Context_STATE_1;
			ListTable.TypeofGraph = Context_STATE.Graph_STATE_1; // 画图擦除子状态
			WritebySurfaceView.button_eraser
					.setBackgroundDrawable(eraserimageselected);
			WritebySurfaceView.button_innertext
					.setBackgroundDrawable(wenziimage);
			WritebySurfaceView.button_backtodraw
					.setBackgroundDrawable(huihuaimage);
			mview.scopex = 0;
			mview.scopey = 0;

			if (mview.UnitforInnerText.size() != 0)
			{
				mview.UnitforInnerText.clear();
				mview.innerx = 80;
				mview.minX = mview.minXofinnertext < mview.copyofminX ? mview.minXofinnertext
						: mview.copyofminX;
				mview.minY = mview.minYofinnertext < mview.copyofminY ? mview.minYofinnertext
						: mview.copyofminY;
				mview.maxX = mview.maxXofinnertext > mview.copyofmaxX ? mview.maxXofinnertext
						: mview.copyofmaxX;
				mview.maxY = mview.maxYofinnertext > mview.copyofmaxY ? mview.maxYofinnertext
						: mview.copyofmaxY;

				mview.copyofminX = 0;
				mview.copyofminY = 0;
				mview.copyofmaxX = 0;
				mview.copyofmaxY = 0;
				mview.minXofinnertext = 1000;
				mview.minYofinnertext = 1000;
				mview.maxXofinnertext = 0;
				mview.maxYofinnertext = 0;

				mview.x = -1; // 这里为何把x，和y弄成0，为了是防止 下面minx的初始值再被破坏
				mview.y = -1;

			} else if (Mview.innertextstatus == true)
			{
				mview.minX = mview.copyofminX;
				mview.minY = mview.copyofminY;
				mview.maxX = mview.copyofmaxX;
				mview.maxY = mview.copyofmaxY;
			}

			Mview.innertextstatus = false;
			Mview.locationforinnertext = false;
			mview.maxofinnery = -1;
			ListTable.TypeofUnit = Context_STATE.Context_STATE_1;
			WritebySurfaceView.button_innertext
					.setEnabled(true);
			WritebySurfaceView.InnertextsizeBar
					.setVisibility(View.GONE);
			WritebySurfaceView.textview_innertextsizetext
					.setVisibility(View.GONE);

			WritebySurfaceView.button_backtodraw
					.setEnabled(true);
			WritebySurfaceView.vSeekBar
					.setVisibility(View.GONE);
			WritebySurfaceView.textview_paintstrokesizetext
					.setVisibility(View.GONE);

			WritebySurfaceView.button_smalleraser
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_largeeraser
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.button_middleeraser
					.setVisibility(View.VISIBLE);

//			new Thread(mythread).start();
			mview.openglrender.clearflag = false;
			mview.openglrender.drawstiplineflag = false;
			mview.drawRender();
			mview.paint();
		}};
	OnClickListener innertextbuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			ListTable.TypeofGraph = 2;
			Mview.innertextstatus = true;
			Mview.locationforinnertext = false;
			WritebySurfaceView.button_innertext
					.setBackgroundDrawable(wenziimageselected);
			WritebySurfaceView.button_backtodraw
					.setBackgroundDrawable(huihuaimage);
			WritebySurfaceView.button_eraser
					.setBackgroundDrawable(eraserimage);
			WritebySurfaceView.InnertextsizeBar
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.vSeekBar
					.setVisibility(View.GONE);
			WritebySurfaceView.textview_innertextsizetext
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.textview_paintstrokesizetext
					.setVisibility(View.GONE);
			WritebySurfaceView.button_innertext
					.setEnabled(false);
			WritebySurfaceView.button_graph.setEnabled(false);

			WritebySurfaceView.button_smalleraser
					.setVisibility(View.GONE);
			WritebySurfaceView.button_largeeraser
					.setVisibility(View.GONE);
			WritebySurfaceView.button_middleeraser
					.setVisibility(View.GONE);

			// Log.i("DEBUG IN button_innertext   --->","MINX  MINY  MAXX  MAXY   "
			// + " " + minX+ " " + minY + " " + maxX + " "+
			// maxY);
			mview.copyofminX = mview.minX;
			mview.copyofminY = mview.minY;
			mview.copyofmaxX = mview.maxX;
			mview.copyofmaxY = mview.maxY;
			mview.minX = 1000;
			mview.minY = 1000;
			mview.maxX = 0;
			mview.maxY = 0;
			mview.x = -1; // 这里为何把x，和y弄成0，为了是防止 下面minx的初始值再被破坏
			mview.y = -1;
			mview.maxofinnery = -1;
			mview.scopex = 0;
			mview.scopey = 0;
			// ListTable.TypeofUnit =0;
			// //欺骗模拟器，暂时让状态变回到文字状态（charunit），因为是内嵌文字，暂时看作是文字状态的一种衍生体
			ListTable.TypeofUnit = Context_STATE.Context_STATE_0;
//			new Thread(mythread).start();
			mview.openglrender.clearflag = false;
			mview.openglrender.drawstiplineflag = false;
			mview.drawRender();
			mview.paint();
		}};
	OnClickListener backtodrawbuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
//			Log.i("debug in backtodraw1","lock is "+mview.openglrender.drawpicturebufferlock);
			ListTable.TypeofGraph = 0;
			WritebySurfaceView.button_backtodraw
					.setBackgroundDrawable(huihuaimageselected);
			WritebySurfaceView.button_innertext
					.setBackgroundDrawable(wenziimage);
			WritebySurfaceView.button_eraser
					.setBackgroundDrawable(eraserimage);
			WritebySurfaceView.textview_paintstrokesizetext
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.textview_innertextsizetext
					.setVisibility(View.GONE);
			WritebySurfaceView.button_smalleraser
					.setVisibility(View.GONE);
			WritebySurfaceView.button_largeeraser
					.setVisibility(View.GONE);
			WritebySurfaceView.button_middleeraser
					.setVisibility(View.GONE);

			if (mview.UnitforInnerText.size() != 0)
			{
				mview.UnitforInnerText.clear();
				mview.innerx = 80;
				mview.minX = mview.minXofinnertext < mview.copyofminX ? mview.minXofinnertext
						: mview.copyofminX;
				mview.minY = mview.minYofinnertext < mview.copyofminY ? mview.minYofinnertext
						: mview.copyofminY;
				mview.maxX = mview.maxXofinnertext > mview.copyofmaxX ? mview.maxXofinnertext
						: mview.copyofmaxX;
				mview.maxY = mview.maxYofinnertext > mview.copyofmaxY ? mview.maxYofinnertext
						: mview.copyofmaxY;

				mview.copyofminX = 0;
				mview.copyofminY = 0;
				mview.copyofmaxX = 0;
				mview.copyofmaxY = 0;
				mview.minXofinnertext = 1000;
				mview.minYofinnertext = 1000;
				mview.maxXofinnertext = 0;
				mview.maxYofinnertext = 0;

				mview.x = -1; // 这里为何把x，和y弄成0，为了是防止 下面minx的初始值再被破坏
				mview.y = -1;

			} else if (Mview.innertextstatus == true)
			{
				mview.minX = mview.copyofminX;
				mview.minY = mview.copyofminY;
				mview.maxX = mview.copyofmaxX;
				mview.maxY = mview.copyofmaxY;
			}

			Mview.innertextstatus = false;
			Mview.locationforinnertext = false;
			mview.maxofinnery = -1;
			mview.scopex = 0;
			mview.scopey = 0;
			ListTable.TypeofUnit = Context_STATE.Context_STATE_1;
			WritebySurfaceView.button_innertext
					.setEnabled(true);
			WritebySurfaceView.vSeekBar
					.setVisibility(View.VISIBLE);
			WritebySurfaceView.InnertextsizeBar
					.setVisibility(View.GONE);
			mview.openglrender.clearflag = false;
			mview.openglrender.drawstiplineflag = false;
			mview.drawRender();
			mview.paint();
//			Log.i("debug in backtodraw2","lock is "+mview.openglrender.drawpicturebufferlock);
		}};
	/*OnClickListener largeeraserbuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			mview.eraserkind = mview.LARGEERASER;
			WritebySurfaceView.button_largeeraser
					.setBackgroundDrawable(mview.LargeEraserImage_Selected);
			WritebySurfaceView.button_middleeraser
					.setBackgroundDrawable(mview.MiddleEraserImage);
			WritebySurfaceView.button_smalleraser
					.setBackgroundDrawable(mview.SmallEraserImage);
		}};*/
	OnClickListener middleeraserbuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			mview.eraserkind = mview.MIDDLEERASER;
			WritebySurfaceView.button_largeeraser
					.setBackgroundDrawable(largeeraserimage);
			WritebySurfaceView.button_middleeraser
					.setBackgroundDrawable(middleeraserimageselected);
			WritebySurfaceView.button_smalleraser
					.setBackgroundDrawable(smalleraserimage);
		}};
	OnClickListener smalleraserbuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			mview.eraserkind = mview.SMALLERASER;
			WritebySurfaceView.button_largeeraser
					.setBackgroundDrawable(largeeraserimage);
			WritebySurfaceView.button_middleeraser
					.setBackgroundDrawable(middleeraserimage);
			WritebySurfaceView.button_smalleraser
					.setBackgroundDrawable(smalleraserimageselected);
		}};
	OnClickListener autospacebuttonlistener = new OnClickListener(){

		@Override
		public void onClick(View v)
		{
			if (ListTable.isAutoSpace == true)
			{
				WritebySurfaceView.button_autospace
						.setBackgroundDrawable(spacectrlimageselected);
				WritebySurfaceView.button_space
						.setBackgroundDrawable(konggeimage);
				ListTable.isAutoSpace = false;
				ListTable.isAutoSpaceValue = 0;
			} else if (ListTable.isAutoSpace == false)
			{
				WritebySurfaceView.button_autospace
						.setBackgroundDrawable(spacectrlimage);
				WritebySurfaceView.button_space
						.setBackgroundDrawable(konggeimage);
				ListTable.isAutoSpace = true;
				ListTable.isAutoSpaceValue = 1;
			}
		}};
	OnTouchListener donebuttonlistener = new OnTouchListener(){

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if (event.getAction() == MotionEvent.ACTION_DOWN)
			{
//				Log.v("debug in done save image","array.size is "+mview.array.size());
				WritebySurfaceView.button_done
						.setBackgroundDrawable(doneimageselected);
				mview.editdfa.clearDFA(); // 切换到done状态时候，需要对状态机进行清空
				EditDFA.pastePanel.clear(); // 清除粘贴板内容
//				Log.v("debug in done save image","array.size is "+mview.array.size());
				mview.charpointarray.clear();//2011.06.24 liqiang
				if (ListTable.TypeofUnit == Context_STATE.Context_STATE_1) // 处于图片状态或者图片内资字状态
				{
//					Log.i("debug in done save image","save image !!!"+" "+mview.array.get(mview.array.size()-2).isEnd());
//					Log.i("debug in done save image","save image !!!"+" "+mview.array.get(mview.array.size()-1).isEnd()+
//							" "+mview.array.get(mview.array.size()-1).getX()+" "+mview.array.get(mview.array.size()-1).getY());
					for(int i=0;i<Mview.paintstrokesizelist.size();i++)
					{
						Log.e("debug in write","size is "+Mview.paintstrokesizelist.get(i));
					}
					mview.SaveImage();
					
				}

				if (ListTable.TypeofUnit == Context_STATE.Context_STATE_1)// zhuxiaoqing
				{// // 画图、编辑状态下点击“DONE”到文字状态

					// 2011.03.11 liqiang
					mview.openglrender.drawpictureflag = false;
					Mview.picpointcount = 0;
					mview.picturepointfloat = null;
					mview.picturepointfloat = new float[mview.maxpointnum];
					// //清除Mview 上的残留字迹，好像只用postInvalidate
					// 不能清楚残留，这个问题以待后期解决,创建新的线程对象new
					// MyThread()，会耗费大量的cpu和时间
					mview.openglrender.drawstiplineflag = true;// zhuxiaoqing
					mview.openglrender.clearflag = true;
					mview.drawRender();
					WritebySurfaceView.button_text
							.setBackgroundDrawable(biimage);
					WritebySurfaceView.button_edit
							.setBackgroundDrawable(bianjiimage);
					ListTable.TypeofUnit = Context_STATE.Context_STATE_0;// zhuxiaoqing
					// new
					// Thread(mythread).start();//zhuxiaoqing

				}

				else if ((ListTable.TypeofUnit == Context_STATE.Context_STATE_0 && Mview.innertextstatus == true))
				{// 写内嵌字状态
					ListTable.TypeofGraph = 0;
					WritebySurfaceView.button_backtodraw
							.setBackgroundDrawable(huihuaimageselected);
					WritebySurfaceView.button_innertext
							.setBackgroundDrawable(wenziimage);
					WritebySurfaceView.button_eraser
							.setBackgroundDrawable(eraserimage);
					WritebySurfaceView.textview_paintstrokesizetext
							.setVisibility(View.VISIBLE);
					WritebySurfaceView.textview_innertextsizetext
							.setVisibility(View.GONE);
					WritebySurfaceView.button_smalleraser
							.setVisibility(View.GONE);
					WritebySurfaceView.button_largeeraser
							.setVisibility(View.GONE);
					WritebySurfaceView.button_middleeraser
							.setVisibility(View.GONE);

					if (mview.UnitforInnerText.size() != 0)
					{
						mview.UnitforInnerText.clear();
						mview.innerx = 80;
						mview.minX = mview.minXofinnertext < mview.copyofminX ? mview.minXofinnertext
								: mview.copyofminX;
						mview.minY = mview.minYofinnertext < mview.copyofminY ? mview.minYofinnertext
								: mview.copyofminY;
						mview.maxX = mview.maxXofinnertext > mview.copyofmaxX ? mview.maxXofinnertext
								: mview.copyofmaxX;
						mview.maxY = mview.maxYofinnertext > mview.copyofmaxY ? mview.maxYofinnertext
								: mview.copyofmaxY;

						mview.copyofminX = 0;
						mview.copyofminY = 0;
						mview.copyofmaxX = 0;
						mview.copyofmaxY = 0;
						mview.minXofinnertext = 1000;
						mview.minYofinnertext = 1000;
						mview.maxXofinnertext = 0;
						mview.maxYofinnertext = 0;

						mview.x = -1; // 这里为何把x，和y弄成0，为了是防止
						// 下面minx的初始值再被破坏
						mview.y = -1;

					} else if (Mview.innertextstatus == true)
					{
						mview.minX = mview.copyofminX;
						mview.minY = mview.copyofminY;
						mview.maxX = mview.copyofmaxX;
						mview.maxY = mview.copyofmaxY;
					}

					Mview.innertextstatus = false;
					Mview.locationforinnertext = false;
					mview.maxofinnery = -1;
					mview.scopex = 0;
					mview.scopey = 0;
					ListTable.TypeofUnit = Context_STATE.Context_STATE_1;
					WritebySurfaceView.button_innertext
							.setEnabled(true);
					WritebySurfaceView.vSeekBar
							.setVisibility(View.VISIBLE);
					WritebySurfaceView.InnertextsizeBar
							.setVisibility(View.GONE);
					mview.openglrender.drawinnercharendflag = true;
					mview.paint();

					// 2011.03.14 liqiang
					if (mview.openglrender.drawinnercharflag)
					{
						mview.openglrender.drawinnercharbuffer
								.clear();
						mview.openglrender.drawstiplineflag = false;
//						openglrender.clearflag = true;
						mview.clearBuffer();
						mview.drawRender();
						// thisview.requestRender();
					}
				} else if (ListTable.TypeofUnit == Context_STATE.Context_STATE_0) // 进入浏览状态
				{
//					Log.i("debug in done button ","done button is click !");
					mview.islast.clear();// zhuxiaoqing
					// addtolist();
					// 2011.02.24 清空drawcharbuffer
					// manualsubmitflag = true;
					Mview.temparray.clear();
					Mview.array.clear();
					mview.charpointbuf.clear();
					// 2011.03.03 liqiang
					mview.openglrender.drawcharbuffer.clear();
					mview.charpointfloat = null;
					mview.charpointfloat = new float[mview.maxpointnum];
					Mview.charpointcount = 0;
					mview.openglrender.circleBuffer.clear();
					mview.clearBuffer();
//					WritebySurfaceView.kview
//							.setBackgroundColor(mview.bgcolor);
					WritebySurfaceView.kview
							.setVerticalScrollBarEnabled(true);
					mview.setVisibility(View.GONE);

					// WritebySurfaceView.scrollviewonkview.setVisibility(View.VISIBLE);
					// WritebySurfaceView.scrollviewonkview.addView(WritebySurfaceView.kview);

					WritebySurfaceView.button_backspace
							.setVisibility(View.GONE);
					WritebySurfaceView.button_graph
							.setVisibility(View.GONE);
					WritebySurfaceView.button_enter
							.setVisibility(View.GONE);
					WritebySurfaceView.button_space
							.setVisibility(View.GONE);
					WritebySurfaceView.button_enter
							.setVisibility(View.GONE);
					WritebySurfaceView.button_autospace
							.setVisibility(View.GONE);
					WritebySurfaceView.button_done
							.setVisibility(View.GONE);
					WritebySurfaceView.button_text
							.setBackgroundDrawable(biimageselected);
					WritebySurfaceView.button_edit
					.setBackgroundDrawable(bianjiimage);

					// canvastobitmap();

				}

			} else if (ListTable.TypeofUnit == Context_STATE.Context_STATE_2)
			{// zhuxiaoqing
				mview.islast.clear();// zhuxiaoqing
				// addtolist();
				// 2011.02.24 清空drawcharbuffer
				// manualsubmitflag = true;
				Mview.temparray.clear();
				Mview.array.clear();
				mview.charpointbuf.clear();
				// 2011.03.03 liqiang
				mview.openglrender.drawcharbuffer.clear();
				mview.charpointfloat = null;
				mview.charpointfloat = new float[mview.maxpointnum];
				Mview.charpointcount = 0;
				mview.openglrender.circleBuffer.clear();
				mview.clearBuffer();
//				WritebySurfaceView.kview
//						.setBackgroundColor(mview.bgcolor);
				WritebySurfaceView.kview
						.setVerticalScrollBarEnabled(true);
				mview.setVisibility(View.GONE);
				WritebySurfaceView.button_backspace
						.setVisibility(View.GONE);
				WritebySurfaceView.button_graph
						.setVisibility(View.GONE);
				WritebySurfaceView.button_enter
						.setVisibility(View.GONE);
				WritebySurfaceView.button_space
						.setVisibility(View.GONE);
				WritebySurfaceView.button_enter
						.setVisibility(View.GONE);
				WritebySurfaceView.button_autospace
						.setVisibility(View.GONE);
				WritebySurfaceView.button_done
						.setVisibility(View.GONE);
				WritebySurfaceView.button_text
						.setBackgroundDrawable(biimageselected);
			} else if (event.getAction() == MotionEvent.ACTION_UP)
				WritebySurfaceView.button_done
						.setBackgroundDrawable(okimage);
			return false;
		}
		
		};
		Handler handler = new Handler()
		{
			 public void handleMessage(Message msg) {  
		            switch (msg.what) {  
		            case 1:  
		                updateDialog();  
		                break;  
		            case 2:
		            	updateDialog();
		            	Intent mailintent = new Intent();
						mailintent.setClass(WritebySurfaceView.this,Mailretry.class);
	 					Bundle bundleinbrowse = new Bundle();
						bundleinbrowse.putString("classname",classname);
						bundleinbrowse.putInt("pagecount", pdfpagecount);
						bundleinbrowse.putString("filename", strexportfilename);
						bundleinbrowse.putString("format", strexportfileformat);
						mailintent.putExtras(bundleinbrowse);
						startActivity(mailintent);// 2011.05.26 liqiang
						Log.e("debug in handler in writebysurfaceview","pagecount is "+pdfpagecount);
						clearDrawable();
		            	break;
		            default:
		            	break;
		            }  
		        };  
		};
		private void updateDialog()
		{
			alertdialog.show();
			Log.i("file name format is ",strexportfilesize+" "+strexportfileformat+" "+pdfpagecount);
			exportFile(strexportfilename,strexportfileformat,strexportfilesize);
			alertdialog.dismiss();
		}
		Handler mailhandler = new Handler()
		{
			 public void handleMessage(Message msg) {  
		            switch (msg.what) {  
		            case 1:  
		            	processdlg.show();
		            	getPDF(defaultmailname, defaultmailsize);
						processdlg.dismiss();
		                break;  
		            }  
		        };  
		};
		Dialog processdlg;
		//2011.07.20 liqiang 
		OnClickListener exportbuttonlistener = new OnClickListener()
		{ 
			public void onClick(View v)
			{
				
				new AlertDialog.Builder(WritebySurfaceView.this)
				.setTitle("文件处理")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(new String[] { "发送邮件", "导出文件" }, 0,
				 new DialogInterface.OnClickListener() 
				{
					  public void onClick(DialogInterface dialog, int which) 
					  {
						  switch(which)
						  {
							  case 0://发送邮件
//								  Intent mailintent = new Intent();
//								  mailintent.setClass(WritebySurfaceView.this,Mailretry.class);
//			 					  Bundle bundleinbrowse = new Bundle();
//								  bundleinbrowse.putString("classname",classname);
//								  bundleinbrowse.putInt("pagecount", pdfpagecount);
//								  mailintent.putExtras(bundleinbrowse);
//								  startActivity(mailintent);// 2011.05.26 liqiang
//								  clearDrawable();
								  
								  LayoutInflater inflater = getLayoutInflater();
								    View layout = inflater.inflate(R.layout.exportfile,
								     (ViewGroup) findViewById(R.id.exportdialog));
								   layout01 = (LinearLayout)layout.findViewById(R.id.exportlayout01);
									layout02 = (LinearLayout)layout.findViewById(R.id.exportlayout02);
									layout03 = (LinearLayout)layout.findViewById(R.id.exportlayout03);
									layout04 = (LinearLayout)layout.findViewById(R.id.exportlayout04);	
									layout05 = (LinearLayout)layout.findViewById(R.id.exportlayout05);
								  exportfilename = (EditText)layout.findViewById(R.id.exportfilename);
									formatgroup = (RadioGroup)layout.findViewById(R.id.formatgroup);
								    pngformat = (RadioButton)layout.findViewById(R.id.pngid);
									pdfformat = (RadioButton)layout.findViewById(R.id.pdfid);
									sizegroup = (RadioGroup)layout.findViewById(R.id.sizegroup);
									a4size = (RadioButton)layout.findViewById(R.id.a4id);
									b5size = (RadioButton)layout.findViewById(R.id.b5id);
									button_exportfilesure = (Button)layout.findViewById(R.id.exportfilebuttonsure);
									button_exportfilecancel = (Button)layout.findViewById(R.id.exportfilebuttoncancel);
									alertdialog = new Dialog(WritebySurfaceView.this);
									alertdialog.setContentView(layout);
									alertdialog.setTitle("选择附件格式");
									alertdialog.show();
									
									button_exportfilecancel.setOnClickListener(new OnClickListener()
									{
										
										@Override
										public void onClick(View v)
										{
											// TODO Auto-generated method stub
//											Log.i("debug in export ","cancel !!"+strexportfilename);
											alertdialog.dismiss();
										}
									});
									button_exportfilesure.setOnClickListener(new OnClickListener()
									{
										
										@Override
										public void onClick(View v)
										{
											// TODO Auto-generated method stub
											ListTable.isexport=true;//zhuxiaoqing 2011.11.01
											
											strexportfilename = exportfilename.getText().toString().trim();
//											Log.i("debug in export ","ok !!"+strexportfilename+strexportfileformat+strexportfilesize);
											if(strexportfilename.equals(""))
											{
												strexportfilename = exportfilename.getHint().toString().trim();
											}
											/**/
											
											alertdialog.setTitle("正在导出文件");
											layout01.setVisibility(View.GONE);
											layout02.setVisibility(View.GONE);
											layout03.setVisibility(View.GONE);
											layout04.setVisibility(View.GONE);
											layout05.setVisibility(View.VISIBLE);
											alertdialog.show();
											
											Message mes = new Message();
											mes.what = 2;
											handler.sendMessage(mes);
											
//											Intent mailintent = new Intent();
//											mailintent.setClass(WritebySurfaceView.this,Mailretry.class);
//						 					Bundle bundleinbrowse = new Bundle();
//											bundleinbrowse.putString("classname",classname);
//											bundleinbrowse.putInt("pagecount", pdfpagecount);
//											bundleinbrowse.putString("filename", strexportfilename);
//											bundleinbrowse.putString("format", strexportfileformat);
//											mailintent.putExtras(bundleinbrowse);
//											startActivity(mailintent);// 2011.05.26 liqiang
//											clearDrawable();
										}
									});
									SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddhhmmss"); 
									String datetime = tempDate.format(new java.util.Date());
									exportfilename.setHint(classname+datetime);
									Log.e("debug in export ","formatgroup is "+classname+datetime);
								   formatgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
								   {

									@Override
									public void onCheckedChanged(
											RadioGroup group, int checkedId)
									{
										// TODO Auto-generated method stub
										if(checkedId == pngformat.getId())
										{
//											Log.i("debug in export ","png !!");
											strexportfileformat = "png";
										}
										else if(checkedId == pdfformat.getId())
										{
//											Log.i("debug in export ","pdf !!");
											strexportfileformat = "pdf";
										}
									}
									   
								   });
								   sizegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
								   {

									@Override
									public void onCheckedChanged(
											RadioGroup group, int checkedId)
									{
										// TODO Auto-generated method stub
										if(checkedId == a4size.getId())
										{
//											Log.i("debug in export ","a4size !!");
											strexportfilesize = "a4";
										}
										else if(checkedId == b5size.getId())
										{
//											Log.i("debug in export ","b5size !!");
											strexportfilesize = "b5";
										}
									}
									   
								   });
								  break;
								  
							  case 1://导出文件
								  LayoutInflater fileinflater = getLayoutInflater();
								    View filelayout = fileinflater.inflate(R.layout.exportfile,
								     (ViewGroup) findViewById(R.id.exportdialog));
								   layout01 = (LinearLayout)filelayout.findViewById(R.id.exportlayout01);
									layout02 = (LinearLayout)filelayout.findViewById(R.id.exportlayout02);
									layout03 = (LinearLayout)filelayout.findViewById(R.id.exportlayout03);
									layout04 = (LinearLayout)filelayout.findViewById(R.id.exportlayout04);	
									layout05 = (LinearLayout)filelayout.findViewById(R.id.exportlayout05);
								  exportfilename = (EditText)filelayout.findViewById(R.id.exportfilename);
									formatgroup = (RadioGroup)filelayout.findViewById(R.id.formatgroup);
								    pngformat = (RadioButton)filelayout.findViewById(R.id.pngid);
									pdfformat = (RadioButton)filelayout.findViewById(R.id.pdfid);
									sizegroup = (RadioGroup)filelayout.findViewById(R.id.sizegroup);
									a4size = (RadioButton)filelayout.findViewById(R.id.a4id);
									b5size = (RadioButton)filelayout.findViewById(R.id.b5id);
									button_exportfilesure = (Button)filelayout.findViewById(R.id.exportfilebuttonsure);
									button_exportfilecancel = (Button)filelayout.findViewById(R.id.exportfilebuttoncancel);
									alertdialog = new Dialog(WritebySurfaceView.this);
									alertdialog.setContentView(filelayout);
									alertdialog.setTitle("导出文件");
									alertdialog.show();
									
									button_exportfilecancel.setOnClickListener(new OnClickListener()
									{
										
										@Override
										public void onClick(View v)
										{
											// TODO Auto-generated method stub
//											Log.i("debug in export ","cancel !!"+strexportfilename);
											alertdialog.dismiss();
										}
									});
									button_exportfilesure.setOnClickListener(new OnClickListener()
									{
										
										@Override
										public void onClick(View v)
										{
											// TODO Auto-generated method stub
											ListTable.isexport=true;//zhuxiaoqing 2011.11.01
											
											strexportfilename = exportfilename.getText().toString().trim();
//											Log.i("debug in export ","ok !!"+strexportfilename+strexportfileformat+strexportfilesize);
											if(strexportfilename.equals(""))
											{
												strexportfilename = exportfilename.getHint().toString().trim();
											}
											/**/
											
											alertdialog.setTitle("正在导出文件");
											layout01.setVisibility(View.GONE);
											layout02.setVisibility(View.GONE);
											layout03.setVisibility(View.GONE);
											layout04.setVisibility(View.GONE);
											layout05.setVisibility(View.VISIBLE);
											alertdialog.show();
											
											Message mes = new Message();
											mes.what = 1;
											handler.sendMessage(mes);
										}
									});
									SimpleDateFormat filetempDate = new SimpleDateFormat("yyyyMMddhhmmss"); 
									String filedatetime = filetempDate.format(new java.util.Date());
									exportfilename.setHint(classname+filedatetime);
//									Log.i("debug in export ","formatgroup is "+formatgroup.toString());
								   formatgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
								   {

									@Override
									public void onCheckedChanged(
											RadioGroup group, int checkedId)
									{
										// TODO Auto-generated method stub
										if(checkedId == pngformat.getId())
										{
//											Log.i("debug in export ","png !!");
											strexportfileformat = "png";
										}
										else if(checkedId == pdfformat.getId())
										{
//											Log.i("debug in export ","pdf !!");
											strexportfileformat = "pdf";
										}
									}
									   
								   });
								   sizegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
								   {

									@Override
									public void onCheckedChanged(
											RadioGroup group, int checkedId)
									{
										// TODO Auto-generated method stub
										if(checkedId == a4size.getId())
										{
//											Log.i("debug in export ","a4size !!");
											strexportfilesize = "a4";
										}
										else if(checkedId == b5size.getId())
										{
//											Log.i("debug in export ","b5size !!");
											strexportfilesize = "b5";
										}
									}
									   
								   });
								  break;
							  default:
								  break;
						  }
					       dialog.dismiss();
					  }
					    
				})
			    .setNegativeButton("取消", null)
			    .show();
			}
		};
		// 2011.06.20 liqiang
		OnClickListener colorbuttonlistener = new OnClickListener()
		{ // 按钮触发图片画笔颜色事件！！

			public void onClick(View v)
			{
				ColorPicker();
			}
		};
		
		VerticalSeekBar.OnSeekBarChangeListener vseekbarlistener = new VerticalSeekBar.OnSeekBarChangeListener()
		{ // 滑动条触发事件!

			@Override
			public void onStopTrackingTouch(VerticalSeekBar seekBar)
			{
			}

			@Override
			public void onStartTrackingTouch(VerticalSeekBar seekBar)
			{

			}

			@Override
			public void onProgressChanged(VerticalSeekBar seekBar,
					int progress, boolean fromUser)
			{
//				Log.i("DEBUG in onProgressChanged--->", " " + progress);
				paintstrokesize = (float) (progress * 0.1);
//				Log.i("DEBUG in onProgressChanged--->",
//									"paintstrokesize  " + paintstrokesize);
				textview_paintstrokesizetext.setText("粗细 \n    "
									+ (short) paintstrokesize);
				}
		};
		VerticalSeekBar.OnSeekBarChangeListener innerseekbarlistener = new VerticalSeekBar.OnSeekBarChangeListener()
		{ // 滑动条触发事件!

			@Override
			public void onStopTrackingTouch(VerticalSeekBar seekBar)
			{
			}

			@Override
			public void onStartTrackingTouch(VerticalSeekBar seekBar)
			{

			}

			@Override
			public void onProgressChanged(VerticalSeekBar seekBar,
					int progress, boolean fromUser)
			{
//				Log
//						.i(
//								"DEBUG in onProgressChanged  InnertextsizeBar--->",
//								" " + progress);
				innertextsize = progress;
				textview_innertextsizetext
						.setText("字号\n   " + progress);
			}
		};
		OnTouchListener upslowbuttonlistener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				short baselineheight = (short) (ListTable.charactersize + 16);
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					button_upslow.setBackgroundDrawable(UpslowImage_Selected);
					View view = (View) findViewById(R.id.kviewid);
					Kview.scrollstatus = true;
					if (Kview.maxscrollvice - baselineheight >= 0)
					{
						Kview.maxscrollvice -= baselineheight;
						view.scrollTo(0, Kview.maxscrollvice);
					}

				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					// 改为抬起时的图片
					button_upslow.setBackgroundDrawable(UpslowImage);
				}
				return false;
			}

		};

		OnTouchListener downslowbuttonlistener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				short baselineheight = (short) (ListTable.charactersize + 16);
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					button_downslow
							.setBackgroundDrawable(DownslowImage_Selected);
					View view = (View) findViewById(R.id.kviewid);
					Kview.maxscrollvice += baselineheight;
					Kview.scrollstatus = true;
					view.scrollTo(0, Kview.maxscrollvice);
//					Log.i("debug in downslowbuttonlistener","kview.maxsvice "+Kview.maxscrollvice);
				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					// 改为抬起时的图片
					button_downslow.setBackgroundDrawable(DownslowImage);
				}
				return false;
			}

		};

		OnTouchListener upquickbuttonlistener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				short baselineheight = (short) (ListTable.charactersize + 16);
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					button_upquick.setBackgroundDrawable(UpQuickImage_Selected);
					View view = (View) findViewById(R.id.kviewid);
					Kview.scrollstatus = true;
					if (Kview.maxscrollvice - quicklinenumpertime
							* baselineheight >= 0)
					{
						Kview.maxscrollvice -= quicklinenumpertime
								* baselineheight;
						view.scrollTo(0, Kview.maxscrollvice);
					} else
					{
						Kview.maxscrollvice -= Kview.maxscrollvice;
						view.scrollTo(0, Kview.maxscrollvice);
					}

				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					// 改为抬起时的图片
					button_upquick.setBackgroundDrawable(UpQuickImage);
				}
				return false;
			}

		};

		OnTouchListener downquickbuttonlistener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				short baselineheight = (short) (ListTable.charactersize + 16);
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					button_downquick
							.setBackgroundDrawable(DownQuickImage_Selected);
					View view = (View) findViewById(R.id.kviewid);
					Kview.maxscrollvice += quicklinenumpertime * baselineheight;
					Kview.scrollstatus = true;
					view.scrollTo(0, Kview.maxscrollvice);

				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					// 改为抬起时的图片
					button_downquick.setBackgroundDrawable(DownQuickImage);
				}
				return false;
			}

		};

		OnTouchListener largeeraserbuttonlistener = new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				mview.eraserkind = mview.LARGEERASER;
				WritebySurfaceView.button_largeeraser
						.setBackgroundDrawable(largeeraserimageselected);
				WritebySurfaceView.button_middleeraser
						.setBackgroundDrawable(middleeraserimage);
				WritebySurfaceView.button_smalleraser
						.setBackgroundDrawable(smalleraserimage);
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					button_downquick
							.setBackgroundDrawable(DownQuickImage_Selected);

				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					// 改为抬起时的图片
					button_downquick.setBackgroundDrawable(DownQuickImage);
				}
				return false;
			}

		};
		OnClickListener titlebuttonlistener = new OnClickListener()
		{ // 按钮触发事件！！

					public void onClick(View v)
					{

						titleisclicked = true;
						ListTable.titleok=true;
						ListTable.globalTitle.clear();
						if (ListTable.globalIndexTable.size() > 0)
						{
							for (int i = 0; i != ListTable.globalIndexTable
									.size(); i++) // 必须得把前面的空格单元去除，因为索引文件中并没有对空格单元的情况进行处理
							{
								DataUnit dataunit = (DataUnit) ListTable.globalIndexTable
										.get(i);
								if (dataunit.getDataType() == DataType.TYPE_CHAR)
								{
									// CharUnit charunitnew
									// =HWtrying.compression(dataunit,(short)40);
									// //不管我们设定的字的高度是多少，但对于索引里面的字的高度统一为40
									CharUnit charunitnew = Zoom
											.compression(dataunit,
													(short) 40);
									ListTable.globalTitle.add(charunitnew);
								} else if (dataunit.getCtrlType() == DataType.TYPE_CTRL_SPACE)
								{
									// ControlUnit controlunit = new
									// ControlUnit(DataType.TYPE_CTRL_SPACE,
									// CharFormat.getDefaultCharFormat());
									// ListTable.globalTitle.add(controlunit);
									;
								}

							}
						} else
							// 如果是无标题的话，并且点击了Title按钮，应该让它的标题为我们设置的默认的“无标题”
							;
						ControlUnit controlUnit = new ControlUnit(
								DataType.TYPE_CTRL_ENTER, CharFormat
										.getDefaultCharFormat());
						ListTable.globalIndexTable.add(
								ListTable.temppositionofaddcharunit,
								controlUnit);
//						Log.i("zhuxiaoqing ----","+++"+ListTable.temppositionofaddcharunit);
						ListTable.temppositionofaddcharunit++;
						button_title.setVisibility(View.GONE);
						textview_tips.setVisibility(View.GONE);
						button_graph.setVisibility(View.VISIBLE);
						button_enter.setVisibility(View.VISIBLE);

//						spinner_email.setVisibility(View.VISIBLE);// 2011.06.20
//																	// liqiang

//						Log.i("zhuxiaoqing-->>globalTitle", ""
//								+ ListTable.globalTitle.size());
						ListTable.numTitle = ListTable.globalTitle.size();// zhuxiaoqing
																			// 2011.06.13
						
					}
				};
	private void initBGdrawable()
	{
		switch(ListTable.backgroundimgid)
		{
			case 0:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg0);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg0);
				break;
			case 1:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg1);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg1);
				break;
			case 2:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg2);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg0);
				break;
			case 3:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg3);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg3);
				break;
			case 4:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg4);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg4);
				break;
			case 5:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg5);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg5);
				break;
			case 6:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg6);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg6);
				break;
			case 7:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg7);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg7);
				break;
			case 8:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg8);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg8);
				break;
			case 9:
//				Log.i("DEBUG--->","BGCOLOR  "+ListTable.backgroundimgid);
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg9);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg9);
				break;
			case 10:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg10);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg10);
				break;
			case 11:
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portbgimg11);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landbgimg11);
				break;
			default:
//				Log.e("DEBUG--->","BGCOLOR  "+ListTable.backgroundimgid);
				portviewbackgroundimage = this.getResources().getDrawable(R.drawable.portkviewbackground);
				landviewbackgroundimage = this.getResources().getDrawable(R.drawable.landkviewbackground);
				break;
		}
		
	}
	private void initDrawable()
	{
		initBGdrawable();
		
		UpslowImage = this.getResources().getDrawable(R.drawable.up);
		DownslowImage = this.getResources().getDrawable(R.drawable.down);
		UpslowImage_Selected = this.getResources().getDrawable(
				R.drawable.up_select);
		DownslowImage_Selected = this.getResources().getDrawable(
				R.drawable.down_select);

		UpQuickImage = this.getResources().getDrawable(R.drawable.upquick);
		DownQuickImage = this.getResources().getDrawable(R.drawable.downquick);
		UpQuickImage_Selected = this.getResources().getDrawable(
				R.drawable.upquick_select);
		DownQuickImage_Selected = this.getResources().getDrawable(
				R.drawable.downquick_select);
		exportimage = this.getResources().getDrawable(R.drawable.export);
		biimage = this.getResources().getDrawable(R.drawable.bi);
		huatuimage = this.getResources().getDrawable(R.drawable.huatu);
		bianjiimage = this.getResources().getDrawable(R.drawable.bianji);
		konggeimage = this.getResources().getDrawable(R.drawable.kongge);
		huicheimage = this.getResources().getDrawable(R.drawable.huiche);
		huituiimage = this.getResources().getDrawable(R.drawable.huitui);
		doneimage = this.getResources().getDrawable(R.drawable.done);
		okimage=this.getResources().getDrawable(R.drawable.ok);
		spacectrlimage = this.getResources().getDrawable(R.drawable.spacectrl);
		eraserimage = this.getResources().getDrawable(R.drawable.eraser);
		wenziimage = this.getResources().getDrawable(R.drawable.wenzi);
		yanseimage = this.getResources().getDrawable(R.drawable.yanse);
		huihuaimage = this.getResources().getDrawable(R.drawable.huihua);
		largeeraserimage = this.getResources().getDrawable(R.drawable.largeeraser);
		middleeraserimage = this.getResources().getDrawable(R.drawable.middleeraser);
		smalleraserimage = this.getResources().getDrawable(R.drawable.smalleraser);
		biimageselected = this.getResources().getDrawable(R.drawable.bi1);
		huatuimageselected = this.getResources().getDrawable(R.drawable.huatu1);
		bianjiimageselected = this.getResources().getDrawable(R.drawable.bianji1);
		konggeimageselected = this.getResources().getDrawable(R.drawable.kongge1);
		huicheimageselected = this.getResources().getDrawable(R.drawable.huiche1);
		huituiimageselected = this.getResources().getDrawable(R.drawable.huitui1);
		doneimageselected = this.getResources().getDrawable(R.drawable.done1);
		spacectrlimageselected = this.getResources().getDrawable(R.drawable.spacectrl1);
		wenziimageselected = this.getResources().getDrawable(R.drawable.wenzi_select);
		huihuaimageselected = this.getResources().getDrawable(R.drawable.huihua_select);
		eraserimageselected = this.getResources().getDrawable(R.drawable.eraser_select); 
		largeeraserimageselected = this.getResources().getDrawable(R.drawable.largeeraser_select);
		middleeraserimageselected = this.getResources().getDrawable(R.drawable.middleeraser_select);
		smalleraserimageselected = this.getResources().getDrawable(R.drawable.smalleraser_select);
	}
	private void clearDrawable()
	{
//		portviewbackgroundimage = nullimage;
//		landviewbackgroundimage = nullimage;
		UpslowImage = nullimage;
		DownslowImage = nullimage;
		UpslowImage_Selected = nullimage;
		DownslowImage_Selected = nullimage;
		UpQuickImage = nullimage;
		DownQuickImage = nullimage;
		UpQuickImage_Selected = nullimage;
		DownQuickImage_Selected = nullimage;
		exportimage = nullimage;
		biimage = nullimage;
		huatuimage = nullimage;
		bianjiimage = nullimage;
		konggeimage = nullimage;
		huicheimage = nullimage;
		huituiimage = nullimage;
		doneimage = nullimage;
		spacectrlimage = nullimage;
		eraserimage = nullimage;
		wenziimage = nullimage;
		yanseimage = nullimage;
		huihuaimage = nullimage;
		largeeraserimage = nullimage;
		middleeraserimage = nullimage;
		smalleraserimage = nullimage;
		biimageselected = nullimage;
		huatuimageselected = nullimage;
		bianjiimageselected = nullimage;
		konggeimageselected = nullimage;
		huicheimageselected = nullimage;
		huituiimageselected = nullimage;
		eraserimageselected = nullimage;
		doneimageselected = nullimage;
		spacectrlimageselected = nullimage;
		wenziimageselected = nullimage;
		huihuaimageselected = nullimage;
		largeeraserimageselected = nullimage;
		middleeraserimageselected = nullimage;
		smalleraserimageselected = nullimage;
		System.gc();////2011.12.01 liqiang
		System.gc();////2011.12.01 liqiang
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 2011.01.07 liqiang
		Intent newintent = getIntent();
		Bundle newbundle = newintent.getExtras();
		Typeofoper = newbundle.getString("Type");
		classname = newbundle.getString("classname");
		clickbrowsenumber = newbundle.getInt("clickbrowsenumber");
		serionumber = newbundle.getInt("serionumber");
		filename2=newbundle.getString("filebody");
		pos=newbundle.getInt("indexoftitle");//zhuxiaoqing 2011.11.17
//		Log.i("zhuxiaoqing writesurfaceview filename2",""+filename2);
		CreatePath.MakeFileDir(classname);
		
		if (ListTable.bitmap != null && !ListTable.bitmap.isRecycled()) // 资源回收，以免占用内存以及良好的编程习惯，android中，图片占用超过6M,即报内存溢出异常
			ListTable.bitmap.recycle();
		ListTable.timesofoncreat = 0;
		titleisclicked = false;
		
		/*********************************************************************************/
		/******************************* 读取配置文件信息 *************************************/
		/*********************************************************************************/

		// 读取配置文件信息
		// 2011.01.10 liqiang
		File filedir = new File("/sdcard/eFinger/ConFigure");

		if (filedir.exists() == false)
			filedir.mkdirs();

		String InfFilePath = "/sdcard/eFinger/ConFigure/ConfigInfFile.inf";
		File Infofile = new File(InfFilePath);

		BufferedInputStream inputStream = null;

		if (ListTable.configinfread == false)
		{
			if (Infofile.exists() == true) // 文件存在,且未读取过
			{
				try
				{
					inputStream = new BufferedInputStream(new FileInputStream(
							Infofile));
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				readConfigureinf(inputStream);

				if (ListTable.version != version)
					DefaultConfigure.defaultconfigure(version);
			} else if (Infofile.exists() == false)
				DefaultConfigure.defaultconfigure(version);

			ListTable.configinfread = true;

		}

		if (ListTable.isAutoSpaceValue == 1)
			ListTable.isAutoSpace = true;
		else
			ListTable.isAutoSpace = false;
		
		clearDrawable();
		initDrawable();
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{			
			Log.v("debug in find view of writebysurfaceview","port width and height of view is port "+ListTable.ScreenWidth+" "+ListTable.ScreenHeight);
			int tempwidth = ListTable.ScreenWidth,tempheight = ListTable.ScreenHeight;
			ListTable.ScreenHeight = (short) (tempwidth>tempheight?tempwidth:tempheight); 
			ListTable.ScreenWidth = (short) (tempwidth<tempheight?tempwidth:tempheight);
			Mview.portxmaxlimit = (int) (ListTable.ScreenWidth*0.85);
			Mview.portymaxlimit = (int) (ListTable.ScreenHeight*0.9); 
			Mview.portxminlimit = (int) (ListTable.ScreenWidth*0.05); 
			Mview.portyminlimit = (int) (ListTable.ScreenHeight*0.1);
			this.getWindow().setBackgroundDrawable(portviewbackgroundimage);
		}
		else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			Log.v("debug in find view of writebysurfaceview","land width and height of view is land"+ListTable.ScreenWidth+" "+ListTable.ScreenHeight);
			int tempwidth = ListTable.ScreenWidth,tempheight = ListTable.ScreenHeight;
			ListTable.ScreenHeight = (short) (tempwidth<tempheight?tempwidth:tempheight); 
			ListTable.ScreenWidth = (short) (tempwidth>tempheight?tempwidth:tempheight);
			Mview.landxmaxlimit = (int) (ListTable.ScreenWidth*0.9);
			Mview.landymaxlimit = (int) (ListTable.ScreenHeight*0.9); 
			Mview.landyminlimit = (int) (ListTable.ScreenHeight*0.1); 
			Mview.landxminlimit = (int) (ListTable.ScreenWidth*0.05);
			this.getWindow().setBackgroundDrawable(landviewbackgroundimage);
		}
//		Log.v("debug in find view of writebysurfaceview","width and height of view is "+ListTable.ScreenWidth+" "+ListTable.ScreenHeight);
//		Log.i("debug in writeoncreate ","landxmaxlimit is "+Mview.landxmaxlimit);
		
		if (Typeofoper.equals("Edit"))
		{
			ListTable.edittype = true;
			ListTable.editserionumber = -1;
			ListTable.editserionumber = newbundle.getInt("serionumber");

		} else if (Typeofoper.equals("New"))
		{
			ListTable.edittype = false;
			ListTable.globalTitle.clear();
			/* 这快是我新改的为了能够添加颜色！！ */
			HwFunctionWindow.colortable.clear();
			HwFunctionWindow.Initcolortable(); // 现在这里面只有蓝色的RGB值！！
		}

		

		//
		/*********************************************/

		// 2011.01.11 liqiang
		//String file = "/sdcard/eFinger/" + classname + "/AhweFile/"
		//		+ serionumber;
		//file += ".hwep";
		String file="/sdcard/eFinger/" + classname + "/AhweFile/"+filename2;//zhuxiaoqing 2011.08.21
		String sub = null;//zhuxiaoqing 2011.11.17
		if(filename2!=null)
		{sub=filename2.substring(0, 3);
//		System.out.println("**sub  "+sub);
		if(sub.equals("ipd")||sub.equals("iph"))
		{
//			System.out.println("**ipdfile");
			ListTable.ipdfile=true;
		}else{
			ListTable.ipdfile=false;
		}
		}
		
		File fhwe = new File(file);
//		Log.i("debug zhuxiaoqing", "file path is " + file);
//		Log.v("DEBUG!-->", "WENJIANCHANGDUfhwe:" + fhwe.length());
		if(filename2!=null)//zhuxiaoqing 2011.08.21
			readDataFromFile(fhwe);// 读日志信息
		/***********************************************/

		setContentView(R.layout.vice);// 加载mview,kview 2011.02.15
		
//		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//		{
//			this.getWindow().setBackgroundDrawable(portviewbackgroundimage);
//		}
//		else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//		{
//			this.getWindow().setBackgroundDrawable(landviewbackgroundimage);
//		}
		findView();
		/*********************************************************************************/
		/******************************* Button 初始化设置 ***********************************/
		/*********************************************************************************/

		// button_autospace.setEnabled(false);

		if (Typeofoper.equals("Edit"))
		{
			button_graph.setVisibility(View.VISIBLE);
			button_enter.setVisibility(View.VISIBLE);
//			spinner_email.setVisibility(View.VISIBLE);
		} else
			button_graph.setVisibility(View.GONE);

		if (ListTable.isAutoSpace)
		{
			button_space.setBackgroundDrawable(konggeimage);
			button_autospace.setBackgroundDrawable(spacectrlimage);
		} else
		{
			button_space.setBackgroundDrawable(konggeimage);
			button_autospace.setBackgroundDrawable(spacectrlimageselected);
		}

		if (ListTable.isAutoSubmit == 1)
			button_manualsubmit.setVisibility(View.GONE);
		else
			button_manualsubmit.setVisibility(View.VISIBLE);
		
		
		setButtonOnClickListener();//2011.07.18

		if (titleisclicked == false && ListTable.edittype == false)
		{
			button_graph.setVisibility(View.GONE);
			button_title.setVisibility(View.VISIBLE);
			textview_tips.setVisibility(View.VISIBLE);
			button_title.setOnClickListener(titlebuttonlistener);
		} else
		{
			button_graph.setVisibility(View.VISIBLE);
			button_title.setVisibility(View.GONE);
			textview_tips.setVisibility(View.GONE);
			button_enter.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 2011.07.19 liqiang
	 */
	private void setButtonOnClickListener()
	{
		button_edit.setOnClickListener(editbuttonlistener);
		button_graph.setOnClickListener(graphbuttonlistener);
		button_text.setOnClickListener(textbuttonlistener);
		button_enter.setOnTouchListener(enterbuttonlistener);
		button_space.setOnTouchListener(spacebuttonlistener);
		button_backspace.setOnTouchListener(backspacebuttonlistener);
		button_done.setOnTouchListener(donebuttonlistener);
		button_manualsubmit.setOnClickListener(manualsubmitbuttonlistener);
		button_eraser.setOnClickListener(eraserbuttonlistener);
		button_innertext.setOnClickListener(innertextbuttonlistener);
		button_backtodraw.setOnClickListener(backtodrawbuttonlistener);
		button_largeeraser.setOnTouchListener(largeeraserbuttonlistener);
		button_middleeraser.setOnClickListener(middleeraserbuttonlistener);
		button_smalleraser.setOnClickListener(smalleraserbuttonlistener);
		button_autospace.setOnClickListener(autospacebuttonlistener);
		button_export.setOnClickListener(exportbuttonlistener);
		button_color.setOnClickListener(colorbuttonlistener);
		button_upslow.setOnTouchListener(upslowbuttonlistener);
		button_upquick.setOnTouchListener(upquickbuttonlistener);
		button_downquick.setOnTouchListener(downquickbuttonlistener);
		button_downslow.setOnTouchListener(downslowbuttonlistener);
		vSeekBar.setOnSeekBarChangeListener(vseekbarlistener);
		InnertextsizeBar.setOnSeekBarChangeListener(innerseekbarlistener);
	}
//	@Override
//	public void onPause()
//	{
//		processdlg.dismiss();
//	}
	static int export = 0;
	public static  void exportFile(String filename,String fileformat,String filesize)
	{
		if(fileformat == "png")
		{//png图片
			getPng(filename,filesize);
		}
		else if(fileformat =="pdf")
		{//pdf文件
			getPDF(filename,filesize);
		}
		export = 1;
	}
	static int a4sizewidth = 1487,a4sizeheight = 2150+ListTable.topmargin+ListTable.bottommargin,
	b5sizewidth=1290,b5sizeheight = 1823+ListTable.topmargin+ListTable.bottommargin;

	private static boolean getPng(String filename,String filesize)
	{
		int pngwidth = 0,pngheight = 0;
		if(filesize == "a4")
		{
			//pngwidth = (int) PageSize.A4.getWidth();
			//System.out.println("!!pdf"+pngwidth);
			pngwidth=a4sizewidth;
			pngheight = a4sizeheight;
		}
		else if(filesize == "b5") 
		{
			//pngwidth = (int) PageSize.B5.getWidth();
			//System.out.println("!!pdf"+pngwidth);
			pngwidth=b5sizewidth;
			pngheight = b5sizeheight;
		}
		int height = (kview.locationofy+2)*kview.baselineheight;
//		Log.i("debug in getpdf ","locationofy is "+kview.locationofy);
		if(height ==-1)
			return false;
		int pagenum = -1;
		if(height%kview.getHeight()==0)
			pagenum = height/pngheight;
		else
			pagenum = height/pngheight+1;
		Bitmap temp = null;
		FileOutputStream fOut = null;
//		kview.setBackgroundColor(0xffffffff);//2011.10.26 liqiang
		try
		{
//			Log.i("debug in getpdf ","pagenum is "+pagenum+" "+pdfpagewidth+" "+pdfpageheight);
			for(pdfpagecount = 0;pdfpagecount<pagenum;pdfpagecount++)
			{
				File f = new File("/sdcard/eFinger/" + classname + "/Export/"+filename+"_"+pdfpagecount+".png");		
				f.createNewFile();
				fOut = null;
				fOut = new FileOutputStream(f);
				temp = Bitmap.createBitmap(pngwidth,pngheight, Bitmap.Config.RGB_565);
				//Bitmap.cre
				Canvas newcanvas = new Canvas(temp);
				ListTable.pngwidth=pngwidth;//zhuxiaoqing 2011.11.01
				ListTable.isexport=true;
				getpdfflag = true;	
				//postInvalidate();
				kview.setBackgroundColor(Color.WHITE);
				kview.draw(newcanvas);
				kview.setBackgroundColor(Color.TRANSPARENT);
				getpdfflag = false;
				
				ListTable.isexport=false;//zhuxiaoqing 2011.11.01
				ListTable.pngwidth=0;
				if (temp != null && !temp.isRecycled())
				{
					temp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
				}
				
				if (temp != null && !temp.isRecycled()) 
				{
					temp.recycle();
					temp = null;
					System.gc();
				}
//				Log.i("debug in pdf","???");
				System.gc();
			}
			if(fOut!=null)
				fOut.close();
			
			
			
				
		} catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
//		kview.setBackgroundColor(0x00ffffff);
		if (temp != null && !temp.isRecycled()) 
		{
			temp.recycle();
			temp = null;
		}
		return true;
	}
	public static boolean getPDF(String filename,String filesize)
	{		
		int pdfheight = kview.locationofy*kview.baselineheight;
		if(pdfheight ==-1)
			return false;
		if(filesize == "a4")
		{
			pdfpageheight = a4sizeheight;
			//pdfpagewidth = (int) PageSize.A4.getWidth();
			pdfpagewidth=a4sizewidth;
		}
		else if(filesize == "b5")
		{
			pdfpageheight = b5sizeheight;
			//pdfpagewidth = (int) PageSize.B5.getWidth();
			pdfpagewidth=b5sizewidth;
		}
		int pagenum = -1;
		if(pdfheight%kview.getHeight()==0)
			pagenum = pdfheight/pdfpageheight;
		else
			pagenum = pdfheight/pdfpageheight+1;
		try
		{
			getPng(filename,filesize);
			
			File f1 = new File("/sdcard/eFinger/" + classname + "/Export/"+filename+".pdf");
			Rectangle temprect = new Rectangle(pdfpagewidth,pdfpageheight);
			Document document=new Document(temprect,0,0,0,0);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(f1));
			writer.setLinearPageMode();
			writer.setPageEmpty(true);
			writer.setStrictImageSequence(true);
			document.open();
			
			for(pdfpagecount = 0;pdfpagecount<pagenum;pdfpagecount++)
			{
//				Log.i("page num is ",""+pagenum);
				Image image = Image.getInstance ("/sdcard/eFinger/" + classname + "/Export/"+filename+"_"+pdfpagecount+".png");
				document.add(image);
				
			}
			
			document.close();
			writer.close();
				
		} catch (FileNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	String defaultmailsize = "a4";
	String defaultmailname = "email";
	public  void sendmail(String classname)// 2011.01.10 liqiang
	{
//		Log.i("debug in sendmail in wsview","start function classname is "+classname);
		processdlg = new Dialog(WritebySurfaceView.this);
		LayoutInflater mailinflater = getLayoutInflater();
		View maillayout = mailinflater.inflate(R.layout.processdlg,
				(ViewGroup) findViewById(R.id.proglayout));
		processdlg.setContentView(maillayout);
		processdlg.setTitle("正在生成文件");
		maillayout.setVisibility(View.VISIBLE);
		processdlg.show();
		Message mes = new Message();
		mes.what = 1;
		mailhandler.sendMessage(mes);
		/*File f = new File("/sdcard/eFinger/" + classname + "/MailImage/0.png");
		try
		{
			f.createNewFile();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		FileOutputStream fOut = null;
		try
		{
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e)
		{
			
			e.printStackTrace();
		}
		Bitmap temp = Bitmap.createBitmap(kview.getWidth(), 
				(kview.locationofy+2)*kview.baselineheight, Bitmap.Config.RGB_565);
		Canvas newcanvas = new Canvas(temp);
		getmailflag = true;
		kview.setBackgroundColor(0xffffffff);
		kview.draw(newcanvas);
		kview.setBackgroundColor(0x00ffffff);
		getmailflag = false;
		if (temp != null && !temp.isRecycled())
		{
			temp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		}
		
		if (temp != null && !temp.isRecycled()) 
		{
			temp.recycle();
			temp = null;
		}
		try
		{
			fOut.flush();
			fOut.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}*/
	}
	public void findView()
	{
		
		
		button_title = (Button) findViewById(R.id.button);
		button_title.setVisibility(View.INVISIBLE);
		textview_tips = (TextView) findViewById(R.id.tipsid);
		button_color = (Button) findViewById(R.id.colorofimagepaint);
		button_color.setBackgroundDrawable(yanseimage);
		button_color.setVisibility(View.INVISIBLE);

		bootombar = (RelativeLayout) findViewById(R.id.AbsoluteLayout000);
//		graphbar = (RelativeLayout) findViewById(R.id.AbsoluteLayout001);
//		graphbar.setBackgroundDrawable(GraphBarBgImage);
//		graphbar.setVisibility(View.GONE);
		mview = null;
		kview = null;
		System.gc();//2011.12.01 liqiang
		mview = (Mview) findViewById(R.id.mviewid);
		kview = (Kview) findViewById(R.id.kviewid);
//		Log.i("debug in findview ","mviewwidth is "+mview.getWidth());
		/*********************************************************************************/
		/********************** 菜单Button **************************************************/
		/*********************************************************************************/

		button_text = (Button) findViewById(R.id.textid);
		button_text.setBackgroundDrawable(biimage);
		button_graph = (Button) findViewById(R.id.graphid);
		button_graph.setBackgroundDrawable(huatuimage);
		button_edit = (Button) findViewById(R.id.editid);
		button_edit.setBackgroundDrawable(bianjiimage);
		button_done = (Button) findViewById(R.id.doneid);
		button_done.setBackgroundDrawable(okimage);

		/*********************************************************************************/
		/********************** 控制Button **************************************************/
		/*********************************************************************************/

		button_enter = (Button) findViewById(R.id.enterid);
		button_enter.setBackgroundDrawable(huicheimage);
		button_space = (Button) findViewById(R.id.spaceid);
		button_space.setBackgroundDrawable(konggeimage);
		button_backspace = (Button) findViewById(R.id.backspaceid);
		button_backspace.setBackgroundDrawable(huituiimage);
		button_eraser = (Button) findViewById(R.id.eraserid);
		button_eraser.setBackgroundDrawable(eraserimage);
		button_autospace = (Button) findViewById(R.id.autospace);
		button_autospace.setBackgroundDrawable(spacectrlimage);
		button_innertext = (Button) findViewById(R.id.innertextid);
		button_innertext.setBackgroundDrawable(wenziimage);
		button_backtodraw = (Button) findViewById(R.id.backtodraw);
		button_backtodraw.setBackgroundDrawable(huihuaimage);
		textview_paintstrokesizetext = (TextView) findViewById(R.id.paintstrokesizetext);
		textview_innertextsizetext = (TextView) findViewById(R.id.innertextsizetext);

		vSeekBar = (VerticalSeekBar) findViewById(R.id.SeekBar01);
		InnertextsizeBar = (VerticalSeekBar) findViewById(R.id.SeekBar02);

		button_upslow = (Button) findViewById(R.id.upslow);
		button_upslow.setBackgroundDrawable(UpslowImage);
		button_downslow = (Button) findViewById(R.id.downslow);
		button_downslow.setBackgroundDrawable(DownslowImage);
		button_upquick = (Button) findViewById(R.id.upquickid);
		button_upquick.setBackgroundDrawable(UpQuickImage);
		button_downquick = (Button) findViewById(R.id.downquickid);
		button_downquick.setBackgroundDrawable(DownQuickImage);

		button_largeeraser = (Button) findViewById(R.id.largeeraser); // 三个不同大小的橡皮擦按钮
		button_largeeraser.setBackgroundDrawable(largeeraserimage);
		button_middleeraser = (Button) findViewById(R.id.middleeraser);
		button_middleeraser.setBackgroundDrawable(middleeraserimage);
		button_smalleraser = (Button) findViewById(R.id.smalleraser);
		button_smalleraser.setBackgroundDrawable(smalleraserimage);

		button_manualsubmit = (Button) findViewById(R.id.manualsubmit);
		button_manualsubmit.setBackgroundDrawable(doneimage);
		button_export = (Button) findViewById(R.id.exportbutton);
		button_export.setBackgroundDrawable(exportimage);
//		spinner_email = (Spinner) findViewById(R.id.emailspinner);
//		spinner_email.setVisibility(View.GONE);
		// 2011.06.20 liqiang
		textview_paintstrokesizetext.setText("粗细 \n    "
				+ (short) paintstrokesize);
		textview_innertextsizetext.setText("字号\n   " + (short) innertextsize);
	}


	/*********** 2011.06.09 liqiang ************************/
	/**
	 * 读日志信息
	 * 
	 * @param abstractFile
	 *            文件路径
	 */
	public static void readDataFromFile(File abstractFile)
	{
		BufferedInputStream inputStream;
		ListTable.sectionTable.clear();
		ListTable.globalIndexTable.clear();
		ListTable.paragraphTable.clear();
		HwFunctionWindow.colortable.clear();
		HwFunctionWindow.Initcolortable();
		ListTable.Sectionnum = 0;
		ListTable.temppositionofaddcharunit = 0; // 这里之所以要用到这个，主要是为了对读取的当前文件做编辑处理的时候作铺垫，因为编辑的时候需要确定下一个单元应该添加的位置，就由该变量来表示

		// Log.v("DEBUG--->","colortable.size"+HwFunctionWindow.colortable.size());
		try
		{
			inputStream = new BufferedInputStream(new FileInputStream(abstractFile));
			// inputStream =new BufferedInputStream(abstractFile);
			// GZIPInputStream gin = new GZIPInputStream(inputStream);
			// //文件是按一定格式压缩了的，所以读出时候必须用GZIPInputStream解压，压缩用GZIPOutputStream
			// documentDecoder = new DocumentDecoder(gin);
			documentDecoder = new DocumentDecoder(inputStream);
			documentDecoder.headDecoder(); // 读取文件的头文件
			documentDecoder.dataDecoder(); // 读取文件的数据文件
			// gin.close();
			inputStream.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/********************** 2011.06.09 liqiang **********************/
	short tempwidth, tempheight;
	public static boolean hascopyedarray = false; // 用来标识在切屏的时候是否已经拷贝了array的数据

	@SuppressWarnings("unchecked")
	@Override
	public void onConfigurationChanged(Configuration config)
	{
//		Log.i("debug in config1","array size is "+mview.array.size());
		mview.SaveImage();//2011.12.09 liqiang
		ListTable.TypeofUnit = Context_STATE.Context_STATE_0;//2011.12.09 liqiang
		super.onConfigurationChanged(config);
		setContentView(R.layout.vice); // 横竖屏切换后需要重新加载相应的vice.xml文件,控件也需要重新重载
		// if (this.getResources().getConfiguration().orientation ==
		// Configuration.ORIENTATION_LANDSCAPE&&Kview.screenwidth<Kview.screenheight)
		// {
		// //加上这句是因为。android下如果从横屏切换到竖屏，会两次调用onconfig，加上这个之后，如果是横屏切换到竖屏，因为它首先会执行这个ORIENTATION_LANDSCAPE，而此时Kview.screenwidth应该>Kview.screenheight,所以不满足条件，它只会执行到下面那个判断，正确的判断
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{//从竖屏切换到横屏，经过这个条件
			tempwidth = Kview.screenwidth > Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			tempheight = Kview.screenwidth < Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			Kview.screenwidth = tempwidth;
			Kview.screenheight = tempheight;

			Mview.landxmaxlimit = (int) (tempwidth*0.9);
			Mview.landymaxlimit = (int) (tempheight*0.9); 
			Mview.landyminlimit = (int) (tempheight*0.1); 
			Mview.landxminlimit = (int) (tempwidth*0.05);
			
			tempwidth = 0;
			tempheight = 0;
//			Log.i("DEBUG--->", "change direction in landscape of mview ");

			tempwidth = PView.screenwidth > PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			tempheight = PView.screenwidth < PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			PView.screenwidth = tempwidth;
			PView.screenheight = tempheight;

			// 2011.03.03 liqiang
			
			OpenGlRender.coordinatey = (float) Mview.screenwidth / 2;
			OpenGlRender.coordinatex = (float) Mview.screenheight / 2;
			// Log.i("DEBUG--->","Mview.array.size land  **   "+Mview.array.size());

			if (ListTable.TypeofUnit == 1
					|| (ListTable.TypeofUnit == 0 && Mview.innertextstatus == true)
					&& hascopyedarray == false)
			{
				ListTable.CopyofArray.clear();
				ListTable.CopyofArray = (ArrayList<BasePoint>) Mview.array
						.clone();

				ListTable.globalminX = mview.minX;
				ListTable.globalminY = mview.minY;
				ListTable.globalmaxX = mview.maxX;
				ListTable.globalmaxY = mview.maxY;

				if (Mview.innertextstatus == true)
				{
					ListTable.globalcopyofminX = mview.copyofminX;
					ListTable.globalcopyofminY = mview.copyofminY;
					ListTable.globalcopyofmaxX = mview.copyofmaxX;
					ListTable.globalcopyofmaxY = mview.copyofmaxY;

					ListTable.globalminXofinnertext = mview.minXofinnertext;
					ListTable.globalminYofinnertext = mview.minYofinnertext;
					ListTable.globalmaxXofinnertext = mview.maxXofinnertext;
					ListTable.globalmaxYofinnertext = mview.maxYofinnertext;
					ListTable.innerx = mview.innerx;
					ListTable.innery = mview.innery;
					ListTable.copyofinnerx = mview.copyofinnerx;
					ListTable.copyofinnery = mview.copyofinnery;
					ListTable.baselineheightofinnertext = mview.baselineheight;
					ListTable.maxofinnery = mview.maxofinnery;
				}
				
				hascopyedarray = true;
//				Log.i("debug in config4","array size is "+mview.array.size());
			}
			this.getWindow().setBackgroundDrawable(nullimage);
			this.getWindow().setBackgroundDrawable(landviewbackgroundimage);
		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			tempwidth = Kview.screenwidth < Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			tempheight = Kview.screenwidth > Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			Kview.screenwidth = tempwidth;
			Kview.screenheight = tempheight;
			Mview.portxmaxlimit = (int) (tempwidth*0.85);
			Mview.portymaxlimit = (int) (tempheight*0.9); 
			Mview.portxminlimit = (int) (tempwidth*0.05); 
			Mview.portyminlimit = (int) (tempwidth*0.1);
			// Log.i("DEBUG--->","Kview width and height is "+tempwidth+" "+tempheight);
//			Log.i("DEBUG--->", "change direction in portrait of mview ");
			tempwidth = 0;
			tempheight = 0;

			tempwidth = PView.screenwidth < PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			tempheight = PView.screenwidth > PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			PView.screenwidth = tempwidth;
			PView.screenheight = tempheight;
			// Log.i("DEBUG--->","Pview width and height is "+tempwidth+" "+tempheight);
			// 2011.03.03 liqiang
			OpenGlRender.coordinatey = Mview.screenwidth / 2;
			OpenGlRender.coordinatex = Mview.screenheight / 2;

			if ((ListTable.TypeofUnit == 1 || (ListTable.TypeofUnit == 0 && Mview.innertextstatus == true))
					&& hascopyedarray == false) // 在横屏切换到竖屏的时候，大部分时候是先到land里面再到port里面，而有的时候又是直接到port里面
			{
				ListTable.CopyofArray.clear();
				ListTable.CopyofArray = (ArrayList<BasePoint>) Mview.array
						.clone();

				// Log.i("DEBUG--->","Mview.array.size port  if **   "+Mview.array.size());
				ListTable.globalminX = mview.minX;
				ListTable.globalminY = mview.minY;
				ListTable.globalmaxX = mview.maxX;
				ListTable.globalmaxY = mview.maxY;

				if (Mview.innertextstatus == true)
				{
					ListTable.globalcopyofminX = mview.copyofminX;
					ListTable.globalcopyofminY = mview.copyofminY;
					ListTable.globalcopyofmaxX = mview.copyofmaxX;
					ListTable.globalcopyofmaxY = mview.copyofmaxY;

					ListTable.globalminXofinnertext = mview.minXofinnertext;
					ListTable.globalminYofinnertext = mview.minYofinnertext;
					ListTable.globalmaxXofinnertext = mview.maxXofinnertext;
					ListTable.globalmaxYofinnertext = mview.maxYofinnertext;
					ListTable.innerx = mview.innerx;
					ListTable.innery = mview.innery;
					ListTable.copyofinnerx = mview.copyofinnerx;
					ListTable.copyofinnery = mview.copyofinnery;
					ListTable.baselineheightofinnertext = mview.baselineheight;
					ListTable.maxofinnery = mview.maxofinnery;
				}
				hascopyedarray = true;
			}
			this.getWindow().setBackgroundDrawable(nullimage);
			this.getWindow().setBackgroundDrawable(portviewbackgroundimage);
//			Log.i("debug in config5","array size is "+mview.array.size());
		}

//		Log.i("debug in config2","array size is "+mview.array.size());

		qiehuan = true;
		
		findView();

		/*********************************************************************************/
		/******************************* Button 初始化设置 ***********************************/
		/*********************************************************************************/

		if (titleisclicked == false && ListTable.edittype == false)
		{
			// button_autospace.setEnabled(false);
			button_graph.setVisibility(View.GONE);
			button_title.setVisibility(View.VISIBLE);
			textview_tips.setVisibility(View.VISIBLE);
			button_title.setOnClickListener(titlebuttonlistener);
		} else
		{
			button_graph.setVisibility(View.VISIBLE);
			button_title.setVisibility(View.GONE);
			textview_tips.setVisibility(View.GONE);
			button_enter.setVisibility(View.VISIBLE);
		}

		if (ListTable.isAutoSpace)
		{
			button_space.setBackgroundDrawable(konggeimage);
			button_autospace.setBackgroundDrawable(spacectrlimage);
		} else
		{
			button_space.setBackgroundDrawable(konggeimage);
			button_autospace.setBackgroundDrawable(spacectrlimageselected);
		}

		if (ListTable.isAutoSubmit == 1)
			button_manualsubmit.setVisibility(View.GONE);
		else
			button_manualsubmit.setVisibility(View.VISIBLE);
//		Log.i("debug in config3","array size is "+mview.array.size());

		setButtonOnClickListener();
	}

	public void ColorPicker()
	{
		// 调色板监听！！
		new ColorPickerDialog(this, this, ListTable.imagepaint.getColor())
				.show();
	}

	@Override
	public void colorChanged(int color)
	{
		// TODO Auto-generated method stub
		// 2011.03.23 liqiang
		OpenGlRender.imagered = (float) Color.red(color) / 255f;
		OpenGlRender.imagegreen = (float) Color.green(color) / 255f;
		OpenGlRender.imageblue = (float) Color.blue(color) / 255f;
		OpenGlRender.imagealpha = 1f;

		ListTable.imagepaint.setColor(color);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			mview.SaveImage();//2011.10.18 liqiang
			ListTable.titleok=true;//zhuxiaoqing 2011.09.27
			// 2011.02.26
			mview.openglrender.clearflag = true;// 结束OpenGlRender中的ondrawframe
			mview.openglrender.destoryflag = true;
			mview.openglrender.drawcharbuffer.clear();
			mview.charpointfloat = null;
			mview.charpointfloat = new float[mview.maxpointnum];
			Mview.charpointcount = 0;
			mview.openglrender.circleBuffer.clear();
			mview.charpointarray.clear();// 2011.06.24 liqiang
			// 2011.03.09 liqiang
			if (mview.openglrender.drawpictureflag)
			{
//				Mview.picpointcount = 0;
//				mview.picturepointfloat = null;
//				mview.picturepointfloat = new float[mview.maxpointnum];
				mview.openglrender.drawpicbuffer.clear();
			}
			if (mview.openglrender.drawinnercharflag)
			{
//				Mview.innercharpointcount = 0;
//				mview.innercharpointfloat = null;
//				mview.innercharpointfloat = new float[mview.maxpointnum];
				mview.openglrender.drawinnercharbuffer.clear();
			}

//			Log.i("DEBUG-->", "KEYBACK!!!");
//			Log.i("DEBUG-->", "KEYBACK!!! ListTable.globalIndexTable.size()"
//					+ ListTable.globalIndexTable.size());
			if (ListTable.globalIndexTable.size() > 0)// zhuxiaoqing 2011.06.23
			{
				if (ListTable.globalTitle.size() == 0
						&& titleisclicked == false && Typeofoper.equals("New"))
				{
					for (int i = 0; i != ListTable.globalIndexTable.size(); i++) 
						// 必须得把前面的空格单元去除，因为索引文件中并没有对空格单元的情况进行处理
					{

						DataUnit dataunit = (DataUnit) ListTable.globalIndexTable
								.get(i);

						if (dataunit.getDataType() != DataType.TYPE_CONTROL)
						{
							// CharUnit charunitnew
							// =HWtrying.compression(dataunit,(short)40);
							// //不管我们设定的字的高度是多少，但对于索引里面的字的高度统一为40
							CharUnit charunitnew = Zoom.compression(dataunit,
									(short) 40);
							ListTable.globalTitle.add(charunitnew);
//							Log.i("zhuxiaoqing ----","+++");
						} else if (dataunit.getCtrlType() == DataType.TYPE_CTRL_SPACE)
						{
							;
						}
					}

					ControlUnit controlUnit = new ControlUnit(
							DataType.TYPE_CTRL_ENTER, CharFormat
									.getDefaultCharFormat());
					ListTable.globalIndexTable.add(
							ListTable.temppositionofaddcharunit, controlUnit);
					ListTable.temppositionofaddcharunit++;
				}
//				Log.i("zhuxiaoqing ----","daozheli");
				if(titleisclicked==false)//zhuxiaoqing 2011.11.17
				{
					ListTable.numTitle=ListTable.titlenum.get(pos);
//					System.out.println("&&numTetle"+ListTable.numTitle);
				}
				Save.SavetoFile(classname); // 2011.01.10 liqiang
			} else
				;

			// 加上下面这一段的目的只是为了随便给个宽度和高度值来重新初始化ListTable.bitmap,
			// 这有这样之后再编辑页面点击back键回到读取页面的时候，才能正常生成bitmap
			if (ListTable.bitmap != null && !ListTable.bitmap.isRecycled())
				// 资源回收，以免占用内存以及良好的编程习惯，android中，图片占用超过6M,即报内存溢出异常
				ListTable.bitmap.recycle();

//			ListTable.bitmap = Bitmap.createBitmap(100, 200,
//					Bitmap.Config.ARGB_8888); // 随便给个宽度和高度
//			Canvas newcanvas = new Canvas(ListTable.bitmap);
//			kview.draw(newcanvas);

			// Log.i("DEBUG--->","FDAFDFD F ");

			Bundle bundle = new Bundle();
			bundle.putInt("numberoffile", (int) ListTable.presentfilenum);
//			Log.i("debug", "presentfilenum " + ListTable.presentfilenum);
			bundle.putInt("clickbrowsenumber", clickbrowsenumber);
			bundle.putString("classname", classname);// 2011.01.10 liqiang
			Intent intent = new Intent(WritebySurfaceView.this,
					ShowRetrieve.class);
			intent.putExtras(bundle);
			startActivity(intent);
			clearDrawable();
			WritebySurfaceView.this.finish();// 2011.05.26 liqiang

			return true;
		}

		else
		{
			return super.onKeyDown(keyCode, event);
		}

	}

	public static void readConfigureinf(BufferedInputStream inputStream) // 从参数配置文件中读取相应的参数
	{
		byte[] byteversion = new byte[4];
		byte[] bytewritespeed = new byte[4];
		byte[] bytecharacterstrokewidth = new byte[4];
		byte[] byteshuxiejizhun = new byte[4];
		byte[] bytecharactersize = new byte[4];
		byte[] bytespacewidth = new byte[4];
		byte[] bytecharactercolor = new byte[4];//2011.10.19 liqiang
		byte[] bytebackgroundimgid = new byte[4];//2011.12.20 liqiang
		byte[] bytebgcolor = new byte[4];
		byte[] byteisautospacevalue = new byte[4];
		byte[] byteisautosubmit = new byte[4];

		try
		{

			inputStream.read(byteversion);
			inputStream.read(bytewritespeed);
			inputStream.read(bytecharacterstrokewidth);
			inputStream.read(byteshuxiejizhun);
			inputStream.read(bytecharactersize);
			inputStream.read(bytespacewidth);
			inputStream.read(bytecharactercolor);
			inputStream.read(bytebackgroundimgid);
			inputStream.read(bytebgcolor);
			inputStream.read(byteisautospacevalue);
			inputStream.read(byteisautosubmit);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ListTable.version = Transform.Bytestofloat(byteversion);
		ListTable.writespeed = Transform.BytestoInt(bytewritespeed);
		ListTable.characterstrokewidth = Transform
				.BytestoInt(bytecharacterstrokewidth);
		ListTable.shuxiejizhun = Transform.BytestoInt(byteshuxiejizhun);
		ListTable.charactersize = Transform.BytestoInt(bytecharactersize);
		ListTable.spacewidth = Transform.BytestoInt(bytespacewidth);
		ListTable.charactercolor = Transform.BytestoInt(bytecharactercolor);
		ListTable.backgroundimgid = Transform.BytestoInt(bytebackgroundimgid);
		ListTable.bgcolor = Transform.BytestoInt(bytebgcolor);
		ListTable.isAutoSpaceValue = Transform.BytestoInt(byteisautospacevalue);
		ListTable.isAutoSubmit = Transform.BytestoInt(byteisautosubmit);

		// Log.i("DEBUG--->","READ  ");
		// Log.i("DEBUG--->","VERSION  "+ListTable.version);
		// Log.i("DEBUG--->","SPEED  "+ListTable.writespeed);
		// Log.i("DEBUG--->","STROKEWIDTH  "+ListTable.characterstrokewidth);
		// Log.i("DEBUG--->","JIZHUN  "+ListTable.shuxiejizhun);
		// Log.i("DEBUG--->","CHARSIZE  "+ListTable.charactersize);
		// Log.i("DEBUG--->","SPACEWIDTH  "+ListTable.spacewidth);
//		 Log.i("DEBUG--->","BGCOLOR  "+ListTable.bgcolor);
//		 Log.i("DEBUG--->","BGCOLOR  "+ListTable.backgroundimgid);
		// Log.i("DEBUG--->","AUTOSPACE  "+ListTable.isAutoSpaceValue);//默认为英文
//		Log.i("DEBUG--->", "isAutoSubmit  " + ListTable.isAutoSubmit);

	}

}