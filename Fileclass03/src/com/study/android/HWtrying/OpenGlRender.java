package com.study.android.HWtrying;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Color;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.study.android.ContextState.Context_STATE;
import com.study.android.data.ListTable;

public class OpenGlRender implements Renderer
{
	// protected boolean drawstopflag = false;
	protected boolean drawstiplineflag = false;
	protected boolean touchflag = false;
	float one = 1.0f;
	protected volatile FloatBuffer drawstiplinebuffer;
	protected volatile FloatBuffer drawcharbuffer;
	protected volatile FloatBuffer drawpicbuffer;
	protected volatile FloatBuffer predrawpicbuffer;
	protected volatile FloatBuffer drawinnercharbuffer;
	protected FloatBuffer rectBuffer1;
	protected FloatBuffer rectBuffer2;
	protected FloatBuffer rectBuffer3;
	protected FloatBuffer rectBuffer4;
	int maxpointnum = 10000; 
	protected boolean destoryflag = false;
	protected FloatBuffer circleBuffer;
	protected ArrayList<Float> picturebuf = new ArrayList<Float>();
//	protected ArrayList<Float> redrawpicturebuf = new ArrayList<Float>();
	
	protected static float imagered = 0f;
	protected static float imagegreen = 0f;
	protected static float imageblue = 1f;
	protected static float imagealpha = 1f;

	protected boolean clearflag = true;
	protected boolean drawinnercharflag = false;
	protected boolean drawcharunitflag = false;
	protected boolean drawpictureflag = false;

	protected static float coordinatey;

	protected boolean drawcharbufferlock = true;
	protected boolean drawinnercharbufferlock = true;
	protected boolean draweditcharbufferlock = true;
	protected boolean drawpicturebufferlock = true;
	
	protected static float coordinatex;
	protected boolean draweditflag = false;
	protected boolean backspaceflag = false;
	protected boolean spaceflag = false;
	protected boolean enterflag = false;
	protected boolean timeoutflag = false;
	protected boolean drawinnercharendflag = false;
	int drawtimecount = 0;
	/**
	 * 小圆点的个数
	 */
	int circlepointnum = 24;
	double xarray[] = new double[circlepointnum+1];
	double yarray[] = new double[circlepointnum+1];
	/**
	 * 两条参考线之间的间距
	 */
	float spacebetweentwoline = 0f;
	/**
	 * 最底端的参考线距屏幕底端的距离
	 */
	float linetobottom = 0f;
	float radix = 15f;
	/**
	 * 参考线的点数
	 */
	int pointnum = 80;
	/**
	 * 标记是在写字还是在画图，默认是-1，表示什么也不是。1表示写字或写编辑符，2表示画图，3表示写内嵌字
	 */
	int drawflag = -1;
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		
		destoryflag = false;
		clearflag = true;
		initCirclePI();

		ByteBuffer buf = ByteBuffer.allocateDirect(maxpointnum*2*4);
		buf.order(ByteOrder.nativeOrder());
		drawcharbuffer = buf.asFloatBuffer();
		ByteBuffer buf2 = ByteBuffer.allocateDirect(maxpointnum*2*4);
		buf2.order(ByteOrder.nativeOrder());
		drawpicbuffer = buf2.asFloatBuffer();
		ByteBuffer buf3 = ByteBuffer.allocateDirect(maxpointnum*2*4);
		buf3.order(ByteOrder.nativeOrder());
		drawinnercharbuffer = buf3.asFloatBuffer();
		ByteBuffer buf4 = ByteBuffer.allocateDirect(maxpointnum*2*4);
		buf4.order(ByteOrder.nativeOrder());
		predrawpicbuffer = buf4.asFloatBuffer();

		ByteBuffer buffer = ByteBuffer.allocateDirect(circlepointnum * 8);
		buffer.order(ByteOrder.nativeOrder());
		circleBuffer = buffer.asFloatBuffer();

		spacebetweentwoline = (float) Mview.shuxiejizhun / coordinatey;
		linetobottom = (float) Mview.bottomofshuxiebaseline / coordinatey;
		initStipLineBuffer(-1f, -1f + linetobottom, 1f, -1f	+ linetobottom);
		// Log.i("debug","destoryflag in render create is "+destoryflag);
		 Log.i("debug", "create in render !!!!");
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// opengl 场景的大小为窗口的大小
		clearflag = true;
		gl.glViewport(0, 0, width, height);
		// Log.i("debug", "changed in render !!!!");
	}
	
	
	public void drawInnerRect(GL10 gl)
	{
//		Log.i("drawing rect","!!!!");
		gl.glColor4f(0.5f, 0.0f, 0f, 1f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, rectBuffer1);
		gl.glLineWidth(1);		
		gl.glDrawArrays(GL10.GL_LINES, 0, Mview.pointnumx);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, rectBuffer2);		
		gl.glDrawArrays(GL10.GL_LINES, 0, Mview.pointnumy);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, rectBuffer3);		
		gl.glDrawArrays(GL10.GL_LINES, 0, Mview.pointnumx);
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, rectBuffer4);		
		gl.glDrawArrays(GL10.GL_LINES, 0, Mview.pointnumy);

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		rectBuffer1.clear();
		rectBuffer2.clear();
		rectBuffer3.clear();
		rectBuffer4.clear();
	}
	/**
	 * 准备画小圆的数组，小圆是用12个点画出来的
	 */
	public synchronized void initCirclePI()
	{
		double angle;
		int i = 0;
		for (angle = 0.0d; angle < 2 * Math.PI; angle += 2 * Math.PI / (double)circlepointnum)
		{
			xarray[i] = Math.sin(angle);
			yarray[i] = Math.cos(angle);
			i++;
		}
	}

	/**
	 * 画小圆函数，用来平滑笔画
	 * 
	 * @param gl
	 *            GL10
	 * @param index
	 *            点的索引，用来确定圆心
	 * @param lastwidth
	 *            圆的半径
	 */
	private synchronized void drawLittleCircle(GL10 gl, int index, float lastwidth, int drawflag)
	{
//		Log.i("debug little","width is "+lastwidth);
		float dx, dy;

		float circlepoint[] = new float[circlepointnum];
		lastwidth = lastwidth / 2000.0f;
		for (int i = 0; i < circlepointnum; i++)
		{
			dx = (float) (lastwidth * xarray[i]);
			dy = (float) (2f / 3f * lastwidth * yarray[i]);
			circlepoint[i] = dx;
			i++;
			circlepoint[i] = dy;
		}

		circleBuffer.put(circlepoint);
		circleBuffer.position(0);
		float x = 0;
		float y = 0;
		if(drawflag == 1)
		{
		if (Mview.temparray.size() == 0)
			return;
		x = Mview.temparray.get(index).getX();
		y = Mview.temparray.get(index).getY();		
		}
		else if(drawflag == 2)
		{
			if (Mview.array.size() == 0)
				return;
			x = Mview.array.get(index).getX();
			y = Mview.array.get(index).getY();
		}
		else if(drawflag == 3)
		{
			if (Mview.arrayforinnertext.size() == 0)
				return;
			x = Mview.arrayforinnertext.get(index).getX();
			y = Mview.arrayforinnertext.get(index).getY();
		}
		else 
			return;
		
		x = (x - coordinatex) / coordinatex;
		y = 1.0f - y / coordinatey;

//		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glTranslatef(x, y, 0f);
		gl.glEnable(GL10.GL_POINTS);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, circleBuffer);
		gl.glPointSize(4.0f);
		gl.glDrawArrays(GL10.GL_POINTS, 0, circlepointnum / 2);
		gl.glDisable(GL10.GL_POINTS);
//		gl.glPopMatrix();
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, 0.0f);

		if(drawflag == 1)
		{
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawcharbuffer);
		}
		else if(drawflag == 2)
		{
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawpicbuffer);
		}
		else if(drawflag == 3)
		{
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawinnercharbuffer);
		}
		
	}

	/**
	 * 
	 * @param gl
	 */
	private synchronized void drawRect(GL10 gl)
	{
		float rectpoint[] = { -0.95f, -1f + linetobottom, 0.94f, -1f + linetobottom,
				0.94f, -1f + linetobottom + spacebetweentwoline * 3f, -0.95f,
				-1f + linetobottom + spacebetweentwoline * 3f };

		FloatBuffer rectbuffer;
		ByteBuffer buf = ByteBuffer.allocateDirect(4 * 2 * 4);
		buf.order(ByteOrder.nativeOrder());
		rectbuffer = buf.asFloatBuffer();
		rectbuffer.put(rectpoint);
		rectbuffer.position(0);

//		gl.glLoadIdentity();
		// gl.glTranslatef(0f, count/5f, 0f);
//		gl.glPushMatrix();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColor4f(0.0f, 0.0f, 0.0f, 0.2f);
		gl.glLineWidth(2);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, rectbuffer);
		gl.glEnable(GL10.GL_TRIANGLE_FAN);
//		gl.glHint(GL10.GL_LINE_SMOOTH_HINT, GL10.GL_NICEST);
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4);
		gl.glDisable(GL10.GL_TRIANGLE_FAN);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		rectbuffer.clear();
//		gl.glPopMatrix();
	}
	/**
	 * 初始化参考线buffer
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private void initStipLineBuffer(float x1, float y1,
			float x2, float y2)
	{
		float width = x2 - x1;
		
		float onepart = width / (float) pointnum;
		float[] stiplinepoint = new float[pointnum * 2 * 4];
		for (int i = 0; i < pointnum * 2 * 4; i++)
		{
			stiplinepoint[i] = (i%(pointnum*2)) * onepart + (-0.95f);
			if(stiplinepoint[i]>0.94f)
				stiplinepoint[i] = 0.94f;
			i++;
			stiplinepoint[i] = y2+spacebetweentwoline*(i/(pointnum*2));
		}
		ByteBuffer buf = ByteBuffer.allocateDirect(pointnum * 2 * 4 * 4);
		buf.order(ByteOrder.nativeOrder());
		drawstiplinebuffer = buf.asFloatBuffer();
		drawstiplinebuffer.put(stiplinepoint);
		drawstiplinebuffer.position(0);
	}
	/**
	 * 画参考线（虚线）函数
	 * 
	 * @param gl
	 * @param x1
	 *            起始点横坐标
	 * @param y1
	 *            起始点纵坐标
	 * @param x2
	 *            终止点横坐标
	 * @param y2
	 *            终止点纵坐标
	 */
	private synchronized void drawStipLine(GL10 gl)
	{
//		Log.i("debug in drawstipline","drawing stipline");
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLoadIdentity();
		gl.glLineWidth(2);
		gl.glColor4f(0.5f, 0.0f, 0f, 1f);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawstiplinebuffer);
		gl.glEnable(GL10.GL_LINES);
		gl.glDrawArrays(GL10.GL_LINES, 0, pointnum*4);
		gl.glDisable(GL10.GL_LINES);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glFlush();
	}

	/**
	 * 写大字函数
	 * 
	 * @param gl
	 */
	
	public synchronized void drawCharUnit(GL10 gl)
	{
//		 Log.i("debug", "draw char unit in render !!!");
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		drawRect(gl);
		drawStipLine(gl);
		gl.glColor4f(0.9f, 0.75f, 0.08f, 1f);
		if(drawcharbufferlock)
		{
			drawcharbufferlock = false;
			drawflag = 1;
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLoadIdentity();
		
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawcharbuffer);
		int pointnum = Mview.charpointcount;
//		Log.i("debug in drawchar",""+pointnum+" "+Mview.temparray.size());
		if (pointnum == 0)
		{
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			drawcharbufferlock = true;
		} 
		else
		{
			for (int i = 0; i < pointnum; i++)
			{
				float charwidth = Mview.temparray.get(i).getStrokeWidth();				
				if (charwidth <= 2f)
					charwidth = 4f;				
				charwidth = charwidth * 1.5f;
				float width=charwidth/2f;
				gl.glLineWidth(charwidth);
				if (Mview.temparray.get(i).isEnd())
				{
					drawLittleCircle(gl, i, width, drawflag);
				} else
				{
					if (i >= 1)
					{
						if (Mview.temparray.get(i - 1).isEnd())
						{// 前一个点是一笔的结束点，当前点是另一笔的开始点
							if (i == pointnum - 1)
							{// i点是当前笔画的最后一个点
								drawLittleCircle(gl, i, width, drawflag);
							} else if(i<pointnum-1)
							{
								if(Mview.temparray.get(i).getX()==Mview.temparray.get(i+1).getX()
										&&Mview.temparray.get(i).getY()==Mview.temparray.get(i+1).getY())
									if(Mview.temparray.get(i+1).isEnd())
										drawLittleCircle(gl, i, width, drawflag);
									else
									{
										drawLittleCircle(gl, i, width, drawflag);
										gl.glDrawArrays(GL10.GL_LINES, i, 2);
									}
								else
								{
									drawLittleCircle(gl, i, width, drawflag);
									gl.glDrawArrays(GL10.GL_LINES, i, 2);
								}
							}
						} else
						{
							if (i == pointnum - 1)
							{// i点是当前笔画的最后一个点
								drawLittleCircle(gl, i, width, drawflag);
							} else if(i<pointnum-1)
							{
								if(Mview.temparray.get(i).getX()==Mview.temparray.get(i+1).getX()
										&&Mview.temparray.get(i).getY()==Mview.temparray.get(i+1).getY())
									if(Mview.temparray.get(i+1).isEnd())
										drawLittleCircle(gl, i, width, drawflag);
									else
									{
										drawLittleCircle(gl, i, width, drawflag);
										gl.glDrawArrays(GL10.GL_LINES, i, 2);
									}
								else
								{
									drawLittleCircle(gl, i, width, drawflag);
									gl.glDrawArrays(GL10.GL_LINES, i, 2);
								}
							}
						}
					}
					else// i = 0;
					{
						if(i==pointnum-1)//i 是当前笔画的最后一个点
							drawLittleCircle(gl, i, width, drawflag);
						else if(i<pointnum-1)
						{
							if(Mview.temparray.get(i).getX()==Mview.temparray.get(i+1).getX()
								&&Mview.temparray.get(i).getY()==Mview.temparray.get(i+1).getY()
								&&Mview.temparray.get(i+1).isEnd())
							{
								drawLittleCircle(gl, i, radix, drawflag);
							}
							else
							{
								drawLittleCircle(gl, i, width, drawflag);
								gl.glDrawArrays(GL10.GL_LINES, i, 2);
							}		
						}
					}
				}
			}
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glFlush();
			drawcharbuffer.clear();
			drawflag = -1;
			drawcharbufferlock = true;
		}
		}
	}
	float width=3f,charwidth=4f;
	public synchronized void drawPictureUnit(GL10 gl)
	{
//		Log.i("debug in render ", "pointnum is1111111 "+drawpicturebufferlock);
		if(drawpicturebufferlock)
			{
				drawpicturebufferlock = false;
				drawpictureflag = true;
				drawflag = 2;

				gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
				gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
				gl.glLoadIdentity();
				gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawpicbuffer);
//				 Log.i("debug in render ", "redraw picture !!!");
				int pointnum = Mview.picpointcount;
//				Log.i("debug in render ", "pointnum is "+pointnum+" "+Mview.array.size());
				// float radix = 6f;
				if (pointnum == 0)
				{
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
					drawpicturebufferlock = true;
				} 
				else
				{
					int count = 0;
					float red, green, blue, alpha;
					if(ListTable.globalcolorIndex.size()==0)
						return;
					int color = ListTable.globalcolorIndex.get(0);
					red = ((float) Color.red(color)) / 255f;
					green = ((float) Color.green(color)) / 255f;
					blue = ((float) Color.blue(color)) / 255f;
					alpha = ((float) Color.alpha(color)) / 255f;
//					Log.i("r g b a is",red+" "+green+" "+blue+" "+alpha);
					gl.glColor4f(red, green, blue, alpha);
					for (int i = 0; i < pointnum; i++)
					{
//						Log.i("debug in draw pic","buffer is "+i+" "+drawpicbuffer.get(i*2)+" "+drawpicbuffer.get(i*2+1));
						
						if(i==0||Mview.array.get(i-1).isEnd())
						{
							charwidth = Mview.array.get(i).getStrokeWidth();
							gl.glLineWidth(charwidth);
							width=charwidth/2f;
							Log.i("debug in draw pic ","charwidth is "+charwidth+" "+width);
						}
						
//						float charwidth = Mview.array.get(i).getStrokeWidth();	
//						Log.i("debug in draw pic ","charwidth is "+charwidth);
//						if (charwidth <= 2f)
//							charwidth = 4f;				
//						charwidth = charwidth * 1.5f;
//						float width=charwidth/2f;
//						gl.glLineWidth(charwidth);
						if (Mview.array.get(i).isEnd())
						{
							if(width>=4)
								drawLittleCircle(gl, i, width, drawflag);
							count++;
							if (count < ListTable.globalcolorIndex.size())
							{
//								gl.glDrawArrays(GL10.GL_POINTS, i, 1);
								color = ListTable.globalcolorIndex.get(count);
								red = ((float) Color.red(color)) / 255f;
								green = ((float) Color.green(color)) / 255f;
								blue = ((float) Color.blue(color)) / 255f;
								alpha = ((float) Color.alpha(color)) / 255f;
								gl.glColor4f(red, green, blue, alpha);
							}
						} else
						{
							if (i >= 1)
							{
								if (Mview.array.get(i - 1).isEnd())
								{// 前一个点是一笔的结束点，当前点是另一笔的开始点
									if (i == pointnum - 1)
									{// i点是当前笔画的最后一个点
										if(width>=4)
										drawLittleCircle(gl, i, width, drawflag);
									} else if(i<pointnum-1)
									{
										if(Mview.array.get(i).getX()==Mview.array.get(i+1).getX()
												&&Mview.array.get(i).getY()==Mview.array.get(i+1).getY())
											if(Mview.array.get(i+1).isEnd())
											{
												if(width>=4)
												drawLittleCircle(gl, i, width, drawflag);
											}
											else
											{
												if(width>=4)
												drawLittleCircle(gl, i, width, drawflag);
												gl.glDrawArrays(GL10.GL_LINES, i, 2);
											}
										else
										{
											if(width>=4)
											drawLittleCircle(gl, i, width, drawflag);
											gl.glDrawArrays(GL10.GL_LINES, i, 2);
										}
									}
								} else
								{
									if (i == pointnum - 1)
									{// i点是当前笔画的最后一个点
										if(width>=4)
										drawLittleCircle(gl, i, width, drawflag);
									} else if(i<pointnum-1)
									{
										if(Mview.array.get(i).getX()==Mview.array.get(i+1).getX()
												&&Mview.array.get(i).getY()==Mview.array.get(i+1).getY())
											if(Mview.array.get(i+1).isEnd())
											{
												if(width>=4)
												drawLittleCircle(gl, i, width, drawflag);
											}
											else
											{
												if(width>=4)
												drawLittleCircle(gl, i, width, drawflag);
												gl.glDrawArrays(GL10.GL_LINES, i, 2);
											}
										else
										{
											if(width>=4)
											drawLittleCircle(gl, i, width, drawflag);
											gl.glDrawArrays(GL10.GL_LINES, i, 2);
										}
									}
								}
							}
							else// i = 0;
							{
								if(i==pointnum-1)//i 是当前笔画的最后一个点
								{
									if(width>=4)
									drawLittleCircle(gl, i, width, drawflag);
								}
								else if(i<pointnum-1)
								{
									if(Mview.array.get(i).getX()==Mview.array.get(i+1).getX()
										&&Mview.array.get(i).getY()==Mview.array.get(i+1).getY()
										&&Mview.array.get(i+1).isEnd())
									{
										if(width>=4)
										drawLittleCircle(gl, i, width, drawflag);
									}
									else
									{
										if(width>=4)
										drawLittleCircle(gl, i, width, drawflag);
										gl.glDrawArrays(GL10.GL_LINES, i, 2);
									}		
								}
							}
						}
					}
					gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//					drawpicbuffer.clear();
//					Log.i("debug in pic ","mark1 coornum is "+mark1+" "+picpointcoornum);
				}
				if (drawstiplineflag)
				{
					// Log.i("debug in opengl", "draw stipline !!!");
					drawstiplineflag = false;
					drawRect(gl);
					drawStipLine(gl);
				}
				drawpicturebufferlock = true;
				drawflag = -1;
		}
			
	}
//	}

	/**
	 * 画图片中的内嵌字函数
	 * 
	 * @param gl
	 */
	public synchronized void drawInnerCharUnit(GL10 gl)
	{
		// gl.glPushMatrix();
		// gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		// gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// gl.glColor4f(0.5f, 0f, 0f, 0f);
//		Log.i("debug", "draw inner char unit !!! "+drawinnercharbufferlock);
		if(drawinnercharbufferlock)
		{
			drawinnercharbufferlock = false;
			drawflag = 3;
		drawRect(gl);
		drawStipLine(gl);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glLoadIdentity();
		gl.glColor4f(imagered, imagegreen, imageblue, imagealpha);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawinnercharbuffer);
		gl.glEnable(GL10.GL_LINES);
		// Log.i("debug", "draw inner char unit !!!");
		int innercharpointnum = Mview.innercharpointcount;
//		Log.i("debug in innerchar ", "innercharpointnum is "+innercharpointnum);
//		Log.i("debug in innerchar ", "innercharpointnum is "+innercharpointnum+" "+Mview.innercharpointfloat[innercharpointnum-1]);
//		Log.i("debug in innerchar ","arrayforinnertext"+Mview.arrayforinnertext.size());
		if (innercharpointnum == 0||Mview.arrayforinnertext.size()==0)
		{
			gl.glDisable(GL10.GL_LINES);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			// gl.glPopMatrix();
			gl.glLoadIdentity();
			drawinnercharbufferlock = true;
		}
		else
		{
			for (int i = 0; i < innercharpointnum; i++)
			{
				float charwidth = Mview.arrayforinnertext.get(i).getStrokeWidth();				
				if (charwidth <= 2f)
					charwidth = 4f;				
				charwidth = charwidth * 1.5f;
				float width=charwidth/2f;
				gl.glLineWidth(charwidth);
				if (Mview.arrayforinnertext.get(i).isEnd())
				{
					drawLittleCircle(gl, i, width, drawflag);
				} else
				{
					if (i >= 1)
					{
						if (Mview.arrayforinnertext.get(i - 1).isEnd())
						{// 前一个点是一笔的结束点，当前点是另一笔的开始点
							if (i == innercharpointnum - 1)
							{// i点是当前笔画的最后一个点
								drawLittleCircle(gl, i, width, drawflag);
							} else if(i<innercharpointnum-1)
							{
								if(Mview.arrayforinnertext.get(i).getX()==Mview.arrayforinnertext.get(i+1).getX()
										&&Mview.arrayforinnertext.get(i).getY()==Mview.arrayforinnertext.get(i+1).getY())
									if(Mview.arrayforinnertext.get(i+1).isEnd())
										drawLittleCircle(gl, i, width, drawflag);
									else
									{
										drawLittleCircle(gl, i, width, drawflag);
										gl.glDrawArrays(GL10.GL_LINES, i, 2);
									}
								else
								{
									drawLittleCircle(gl, i, width, drawflag);
									gl.glDrawArrays(GL10.GL_LINES, i, 2);
								}
							}
						} else
						{
							if (i == innercharpointnum - 1)
							{// i点是当前笔画的最后一个点
								drawLittleCircle(gl, i, width, drawflag);
							} else if(i<innercharpointnum-1)
							{
								if(Mview.arrayforinnertext.get(i).getX()==Mview.arrayforinnertext.get(i+1).getX()
										&&Mview.arrayforinnertext.get(i).getY()==Mview.arrayforinnertext.get(i+1).getY())
									if(Mview.arrayforinnertext.get(i+1).isEnd())
										drawLittleCircle(gl, i, width, drawflag);
									else
									{
										drawLittleCircle(gl, i, width, drawflag);
										gl.glDrawArrays(GL10.GL_LINES, i, 2);
									}
								else
								{
									drawLittleCircle(gl, i, width, drawflag);
									gl.glDrawArrays(GL10.GL_LINES, i, 2);
								}
							}
						}
					}
					else// i = 0;
					{
						if(i==innercharpointnum-1||Mview.arrayforinnertext.size()==1)//i 是当前笔画的最后一个点
							drawLittleCircle(gl, i, width, drawflag);
						else if(i<innercharpointnum-1)
						{
							if(Mview.arrayforinnertext.get(i).getX()==Mview.arrayforinnertext.get(i+1).getX()
								&&Mview.arrayforinnertext.get(i).getY()==Mview.arrayforinnertext.get(i+1).getY()
								&&Mview.arrayforinnertext.get(i+1).isEnd())
							{
								drawLittleCircle(gl, i, radix, drawflag);
							}
							else
							{
								drawLittleCircle(gl, i, width, drawflag);
								gl.glDrawArrays(GL10.GL_LINES, i, 2);
							}		
						}
					}
				}
			}
			drawinnercharbuffer.clear();
			gl.glDisable(GL10.GL_LINES);
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			drawinnercharbufferlock = true;
			drawflag = -1;
		}
		}
		gl.glLoadIdentity();
	}

	/**
	 * 编辑函数
	 * 
	 * @param gl
	 */
	public synchronized void drawEditUnit(GL10 gl)
	{
//		 Log.i("debug","draw edit in render !!!");
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		if (draweditflag)
		{
			if(drawcharbufferlock)
			{
				drawcharbufferlock = false;
				drawflag = 1;
			// Log.i("debug","on draw frame in openglrender !!!");
			draweditflag = false;
			gl.glColor4f(0.9f, 0.75f, 0.08f, 1f);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glLoadIdentity();
			gl.glVertexPointer(2, GL10.GL_FLOAT, 0, drawcharbuffer);
			gl.glEnable(GL10.GL_LINE_SMOOTH_HINT);

			int pointnum = Mview.charpointcount;
			if (pointnum == 0)
			{
				gl.glDisable(GL10.GL_LINE_SMOOTH_HINT);
				gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
				drawcharbufferlock = true;
			} else if (pointnum == 1)
			{
				gl.glDisable(GL10.GL_LINE_SMOOTH_HINT);
				gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
				drawLittleCircle(gl, 0, radix, drawflag);
				drawcharbufferlock = true;
			} else
			{
				for (int i = 0; i < pointnum; i++)
				{
					float width = Mview.temparray.get(i).getStrokeWidth();
					if (width <= 2f)
						width = 4f;
					width = width * 1.5f;
					gl.glLineWidth(width);
					
					if (Mview.temparray.get(i).isEnd())
					{
						drawLittleCircle(gl, i, radix, drawflag);
					} else
					{
						if (i > 1 && (Mview.temparray.get(i - 1).isEnd()))
						{// 前一个点是一笔的结束点，当前点是另一笔的开始点
							if (i == pointnum - 1)
							{// i点是当前笔画的最后一个点
								drawLittleCircle(gl, i, radix, drawflag);
							} else
							{
								gl.glDrawArrays(GL10.GL_LINES, i, 2);
							}
						} else
						{
							if (i == pointnum - 1)
							{// i点是当前笔画的最后一个点
								drawLittleCircle(gl, i, radix, drawflag);
								gl.glDrawArrays(GL10.GL_POINTS, i, 1);
							} else
							{
								if (i > 0)
									gl.glDrawArrays(GL10.GL_LINES, i - 1, 2);
							}
						}
					}
				}
				gl.glDisable(GL10.GL_LINE_SMOOTH_HINT);
				gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			}
			drawcharbufferlock = true;
			drawflag = -1;
			}
		}
	}

	@Override
	public synchronized void onDrawFrame(GL10 gl)
	{
//		 Log.i("debug in opengl","ListTable.TypeofUnit is "+ListTable.TypeofUnit);
//		Log.i("debug in ondraw in opengl","clearflag is "+clearflag);
		drawtimecount++;
		if (drawtimecount == 2)
		{
			clearflag = true;
			drawstiplineflag = true;
		}
		if (clearflag)
		{// drawstopflag 在WritebySurfaceView 中的onKeyDown
			// 和Mview中的构造函数中
			// 和WritebySufraceView中的donebutton相应事件中设置
			// manualsubmitflag 在Mview中的manualsubmit按钮相应事件中和计时器超时时设置
			clearflag = false;			
			gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			if (drawstiplineflag)
			{
				drawstiplineflag = false;
				drawRect(gl);
				drawStipLine(gl);
//				Log.i("debug in opengl", "ondraw in render return !!!!!!!!");
			}
		} else
		{
			switch (ListTable.TypeofUnit)
			{
				case Context_STATE.Context_STATE_0:// 书写状态
					if (Mview.innertextstatus)
					{
						if (Mview.locationforinnertext)
						{
							if (timeoutflag)
							{
//								Log.i("debug in drawframe","mouse up !!");
								timeoutflag = false;
								drawstiplineflag = true;
								drawPictureUnit(gl);
								drawInnerRect(gl);
							} else
							{
//								Log.i("debug in drawframe","mouse moving !!");
								drawstiplineflag = true;
								drawPictureUnit(gl);
								drawInnerCharUnit(gl);
								drawInnerRect(gl);
							}
						}
						else//2011.12.06 liqiang
						{
							drawstiplineflag = false;
							drawPictureUnit(gl);
						}
					} else
					{
						drawCharUnit(gl);
					}
					break;
				case Context_STATE.Context_STATE_1:// 画图状态
//					Log.i("debug in ondraw ","drawinnercharendflag is "+drawinnercharendflag);
					if (drawinnercharendflag)
					{
//						Log.i("debug in drawframe","inner picture  !!");
						drawPictureUnit(gl);
						drawinnercharendflag = false;
					} else
					{
//						Log.i("debug in drawframe","formal pic !!");
						drawstiplineflag = false;
						drawPictureUnit(gl);
					}
					break;
				case Context_STATE.Context_STATE_2:// 编辑状态
					drawEditUnit(gl);
					break;
				default:
					break;
			}
		}
	}

	// 2011.02.25 liqiang
	public void surfaceDestroyed(GL10 gl)
	{
		// TODO Auto-generated method stubaddpointtoarray
		destoryflag = true;
		// Log.i("DEBUG--->", "Destroy in openglrender !!!!");
		drawcharbuffer.clear();
		if (ListTable.bitmap != null && !ListTable.bitmap.isRecycled()) // 资源回收，以免占用内存以及良好的编程习惯，android中，图片占用超过6M,即报内存溢出异常
			ListTable.bitmap.recycle();
	}
}
