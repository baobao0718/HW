package com.study.android.edit;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.study.android.ContextState.Context_STATE;
import com.study.android.CollectionduringWriting.addtoListTable;
import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.Mview;
import com.study.android.HWtrying.R;
import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.basicData.MyPoint;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.CharUnit;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.basicData.type.ImageUnit;
//import com.study.android.data.FlavorSelection;
import com.study.android.data.ListTable;
import com.study.android.data.ViewUnit;
import com.study.android.model.ParagraphFormat;
import com.study.android.model.ParagraphUnit;
import com.study.android.model.SectionFormat;
//import com.study.android.tool.TableSearcher;
//import com.study.android.ui.HwEditPanel;
import com.study.android.ui.HwFunctionWindow;
//import com.study.android.ui.OperatorSelectDialog;
//import com.study.android.view.LayoutManager;
//import com.study.android.view.UpdateView;

//import java.awt.Dimension;
//import java.awt.Point;
//import java.awt.Toolkit;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.Transferable;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//import javax.swing.JOptionPane;

/**
 * 编辑状态下的自动机
 * 
 * @author: tanqiang
 * @create-time: 2008-7-18
 */
public class EditDFA {
	/* 对编辑状态进行编码 */
	/**
	 * 编辑状态编码：<b>0</b>状态<p/>既是<b>开始状态</b>也是<b>结束状态</b>，该状态接受<b>任何编辑操作</b>
	 */
	public static final int STATE_0 = 0;
	/**
	 * 编辑状态编码：<b>1</b>状态 <br/><b>0</b> 状态接受<b>左选择符</b>后到达该状态，该状态接受<b>右选择符</b>到达
	 * <b>3</b> 状态
	 */
	public static final int STATE_1 = 1;
	/**
	 * 编辑状态编码：<b>2</b>状态 <br/><b>0</b> 状态接受<b>右选择符</b>后到达该状态，该状态接受<b>左选择符</b>到达
	 * <b>3</b> 状态
	 */
	public static final int STATE_2 = 2;
	/**
	 * 编辑状态编码：<b>3</b>状态 <br/><b>1</b> 状态接受<b>右选择符</b>、<b>2</b> 状态接受</b>左选择符</b>到达该状态，
	 * 改状态接受<b>复制符后</b>状态不变，接受<b>左剪切符</b>后到达 <b>5</b>状态， 接受<b>右剪切符</b>到达
	 * <b>6</b> 状态
	 */
	public static final int STATE_3 = 3;
	/**
	 * 编辑状态编码：<b>4</b>状态： <br/><b>3</b> 状态接受<b>删除符一半</b>到底打该状态，该状态接<b>受删除符另一半</b>到达
	 * <b>0</b> 状态
	 */
	public static final int STATE_4 = 4;
	/**
	 * 编辑状态编码：<b>5</b>状态： <br/><b>3</b> 状态接受<b>左剪切符</b>到达该状态，该状态接受<b>右剪切符</b>到达
	 * <b>0</b> 状态
	 */
	public static final int STATE_5 = 5;
	/**
	 * 编辑状态编码：<b>6</b>状态： <br/><b>3</b> 状态接受<b>右剪切符</b>到达该状态，该状态接受<b>左剪切符</b>到达
	 * <b>0</b> 状态
	 */
	public static final int STATE_6 = 6;
	/**
	 * 编辑状态编码：<b>7</b>状态： <br/><b>0</b> 状态接受<b>删除符一半</b>到达该状态，该状态接受<b>删除符另一半</b>到达
	 * <b>0</b> 状态
	 */
	public static final int STATE_7 = 7;
	/**
	 * 编辑状态编码：<b>8</b>状态： <br/><b>0</b> 状态接受<b>插入符</b>到达该状态，该状态<b>不接受任何状态</b>
	 */
	public static final int STATE_8 = 8;
	/**
	 * 编辑状态编码：<b>9</b>状态： <br/><b>0</b> 状态接受<b>选中图片操作</b>到达该状态
	 */
	public static final int STATE_9 = 9;

	/** 无效的索引单元 */
	public final static int INVALID_UNIT_INDEX = -1;

	/** 选择状态下<b><i>左</i>选择符</b>选中字符的 <i>全局索引<i> */
	private volatile int leftSelectGlobalIndex;

	/** 选择状态下<b><i>右</i>选择符</b>选中字符的 <i>全局索引<i> */
	private volatile int rightSelectGlobalIndex;

	/** 当前光标所在位置的全局索引 */
	private volatile int curCursorGlobalIndex;

	
	private int[] imageIndex_0;
	private int[] imageIndex_1;
	private int[] imageIndex_2;
	private int[] imageIndex_3;
	private int[] tempIndex=new int[4];
	
	private int numtitle=0;
	
	private CharFormat curCharFormat = null;
	
	private ParagraphFormat curParaFormat = null;
	
	private static short screenwidth=ListTable.ScreenWidth,screenheight=ListTable.ScreenHeight;
	
	

	/** 当前编辑状态 */
	private int editState = STATE_0;

	// 两种操作符识别算法
	/** 操作符识别：语法模式识别 */
	private HwOperRecog_SyntaxPat operRecognizer_SyntaxPat;
	/** 操作符识别：基元结构识别 */
	private HwOperRecog_Structure operRecognizer_Strcuture;

	/** 复制或者剪切的数据 */
	public static ArrayList<DataUnit> pastePanel = new ArrayList<DataUnit>();

	/** 编辑面板 */
//	private HwEditPanel editPanel;

	/** 编辑符事件队列，用于绘制编辑符 */
	private final List<OperatorEvent> eventQueue = new LinkedList<OperatorEvent>();
	
	private static int temppositionofaddcharunit=0;

	/** 布局管理器 */
//	private final LayoutManager layoutManager = LayoutManager.getInstance();

//	public EditDFA(HwEditPanel panel) {
	public EditDFA() {
		//this.editPanel = panel;
		// 初始化两种操作符识别器
		this.operRecognizer_SyntaxPat = new HwOperRecog_SyntaxPat();
		this.operRecognizer_Strcuture = new HwOperRecog_Structure();
		// 编辑状态初始化为 起始状态：STATE_0
		this.editState = STATE_0;
		this.leftSelectGlobalIndex = INVALID_UNIT_INDEX;
		this.rightSelectGlobalIndex = INVALID_UNIT_INDEX;
		curCharFormat = CharFormat.getDefaultCharFormat();
		curParaFormat = ParagraphFormat.getDefaultParaFormat();
	}

	public int getCursorIndex() {
		return this.curCursorGlobalIndex;
	}

	public void setCursorIndex(int globalIndex) {
		this.curCursorGlobalIndex = globalIndex;
	}

	public void plusCursorIndex() {
		++this.curCursorGlobalIndex;
	}

	int cursorlocatex = 0;
	int cursorlocatey = 0;
	/**
	 * 接受操作符的点集，并且调用私有方法 <b>processDFT() </b>运行自动机
	 * 
	 * @param points：MyPoints类型的操作符点集，用于识别出操作符类型
	 */
	public void startDFA(ArrayList<MyPoint> array) {
		
		short baselineheight=  (short) (ListTable.charactersize+16);
				
		temppositionofaddcharunit=0;
		
		if (array.size()<=5 && (array.get(0).getY()+Kview.maxscrollvice)>baselineheight) 
		{
			short tempx =array.get(0).getX();
	  		short tempy =array.get(0).getY();
	  		cursorlocatex = tempx;//2011.07.01 liqiang
	  		cursorlocatey = tempy;//2011.07.01 liqiang
	  		Log.i("debug in start dfa ","cursor x and y is "+cursorlocatex+" "+cursorlocatey);
	  		cursloate(tempx,tempy);
	  		ListTable.temppositionofaddcharunit = temppositionofaddcharunit;
	 		return;
		}
		
		
		
		if (array.size()<=5 )
			return;

		processDFA(array);
	}

	/** 清空状态机 */
	public void clearDFA() {
		this.editState = STATE_0;
		if (this.imageIndex_0 != null) {
			this.imageIndex_0[0] = INVALID_UNIT_INDEX;
			this.imageIndex_0[1] = INVALID_UNIT_INDEX;
			this.imageIndex_0[2] = INVALID_UNIT_INDEX;			// 序号2 和序号3 元素分别放置 左选择的 均衡点坐标！！见findOperatorSlideIndex（）
			this.imageIndex_0[3] = INVALID_UNIT_INDEX;
		}
		if (this.imageIndex_1 != null) {
			this.imageIndex_1[0] = INVALID_UNIT_INDEX;
			this.imageIndex_1[1] = INVALID_UNIT_INDEX;
			this.imageIndex_0[2] = INVALID_UNIT_INDEX;			// 序号2 和序号3 元素分别放置 右选择的 均衡点坐标！！见findOperatorSlideIndex（)
			this.imageIndex_0[3] = INVALID_UNIT_INDEX;
		}
		if (this.imageIndex_2 != null) {
			this.imageIndex_2[0] = INVALID_UNIT_INDEX;
			this.imageIndex_2[1] = INVALID_UNIT_INDEX;
		}
		if (this.imageIndex_3 != null) {
			this.imageIndex_3[0] = INVALID_UNIT_INDEX;
			this.imageIndex_3[1] = INVALID_UNIT_INDEX;
		}
		this.leftSelectGlobalIndex = INVALID_UNIT_INDEX;
		this.rightSelectGlobalIndex = INVALID_UNIT_INDEX;
		this.eventQueue.clear();
		
		
		ListTable.selectedMargin[0]= INVALID_UNIT_INDEX;
		ListTable.selectedMargin[1]= INVALID_UNIT_INDEX;
		ListTable.TypeofCtrl=INVALID_UNIT_INDEX;
	}

	/**
	 * 识别出操作符并且运行状态机
	 * 
	 * @param points：MyPoints类型的操作符点集，用于识别出操作符类型
	 */
	private void processDFA(List<MyPoint> list) {
		List<MyPoint> points = new ArrayList<MyPoint>(list);
		OperatorType operatorID = operRecognizer_Strcuture.recognize(points);
		
		if (OperatorType.OP_ID_INVALID == operatorID) {
			operatorID = operRecognizer_SyntaxPat.recognize(points);
		}
		Log.i("DEBUG IN processDFA --- :","operatorID  "+operatorID);

	
		// 用户取消操作选择的机会说明不想进行操作
		if (operatorID == OperatorType.OP_ID_INVALID) {
			this.clearDFA();
			return;
		}
		
		this.curCursorGlobalIndex = ListTable.temppositionofaddcharunit;
		// 根据识别出的操作符类型运行DFT
		switch (editState) {
		case STATE_0:// 接受任何操作
			switch (operatorID) {

			case OP_ID_LEFTSELECT:// 左选择
				this.imageIndex_0 = this.findOperatorSlideIndex(points, operatorID);
				if (this.imageIndex_0 == null
					|| this.imageIndex_0[1] == INVALID_UNIT_INDEX) {
					//editPanel.repaint();
				} else {
					this.leftSelectGlobalIndex = this.imageIndex_0[1];
					Log.i("DEBUG--->","this.leftSelectGlobalIndex  "+this.leftSelectGlobalIndex);
					if (this.leftSelectGlobalIndex == INVALID_UNIT_INDEX) {
				//		editPanel.repaint();
						;
					} else {
						
						this.editState = STATE_1;
						OperatorEvent.leftSelectEvent.setIndex(this.leftSelectGlobalIndex);
						this.eventQueue.add(OperatorEvent.leftSelectEvent);
						Log.i("debug","add left message to quene !!!!");
					}
				}
				break;

			case OP_ID_RIGHTSELECT:// 右选择
				this.imageIndex_1 = this.findOperatorSlideIndex(points, operatorID);
				if (this.imageIndex_1 == null
					|| this.imageIndex_1[1] == INVALID_UNIT_INDEX) {
		//				editPanel.repaint();
				} else {
					this.rightSelectGlobalIndex= this.imageIndex_1[1]-1;	
					if (this.rightSelectGlobalIndex == INVALID_UNIT_INDEX) {
	   //				editPanel.repaint();
					} else {
						this.editState = STATE_2;
						OperatorEvent.rightSelectEvent.setIndex(this.rightSelectGlobalIndex);
						this.eventQueue.add(OperatorEvent.rightSelectEvent);
						Log.i("debug","add right message to quene !!!!");
					}
				}
				break;

			case OP_ID_PASTE:// 粘贴
	//			this.curCursorGlobalIndex = ListTable.temppositionofaddcharunit;
				this.processPaste(this.curCursorGlobalIndex);
				//if(ListTable.TypeofCtrl !=6) ListTable.TypeofCtrl = 6;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_6) 
					ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_6;
				
				this.clearDFA();
				break;

			case OP_ID_HALFDEL:// 删除：仅仅是找到删除单元的起始和结束单元的全局索引
				//this.imageIndex_0 = this.findOperatorSlideIndex(points, operatorID);
				this.imageIndex_0 = this.findOperatorSlideIndex(points, operatorID, points.size()-1);
				if (this.imageIndex_0 == null) {// 找不到滑动索引
	//			editPanel.repaint();
					break;
				}
				// --this.imageIndex_0[1];
				//--this.imageIndex_1[1];// 减1
				//this.setDeleteGlobalIndex(this.imageIndex_0, this.imageIndex_1);
				
				if (this.imageIndex_0[1] == INVALID_UNIT_INDEX ) {
						//editPanel.repaint();
					} else {
						this.leftSelectGlobalIndex = this.imageIndex_0[1];		
							this.editState = STATE_7;
							OperatorEvent.leftSelectEvent.setIndex(this.leftSelectGlobalIndex);
							this.eventQueue.add(OperatorEvent.leftSelectEvent);
							Log.i("DEBUG--->","DEL  imageindex_0  "+this.leftSelectGlobalIndex);

					}
				
				break;
				/*
				if ((imageIndex_0[1] == INVALID_UNIT_INDEX)
					|| (imageIndex_1[1] == INVALID_UNIT_INDEX)
					|| (imageIndex_0[1] > imageIndex_1[1])) {// "该编辑符的位置无效！
					this.imageIndex_0[1] = INVALID_UNIT_INDEX;
					this.imageIndex_1[1] = INVALID_UNIT_INDEX;
	//				editPanel.repaint();
				} else {
					editState = STATE_7;
					OperatorEvent.firstDeleteEvent.setIndex(this.imageIndex_0[1]);
					OperatorEvent.firstDeleteEvent.setNextIndex(this.imageIndex_1[1]);
					this.eventQueue.add(OperatorEvent.firstDeleteEvent);
				}
				break;
				
			*/	
	

			case OP_ID_SPACE:// 空格
				//if(ListTable.TypeofCtrl != 3) ListTable.TypeofCtrl = 3;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_3) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_3;
				this.processSpace(this.curCursorGlobalIndex);
				break;

			case OP_ID_BACKSPACE:// 回退
				//if(ListTable.TypeofCtrl != 8) ListTable.TypeofCtrl = 8;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_8) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_8;
				this.processBackspace(curCursorGlobalIndex);
				break;

			case OP_ID_ENTER:// 回车换行
				//if(ListTable.TypeofCtrl != 1) ListTable.TypeofCtrl = 1;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_1) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_1;
				this.processEnter(curCursorGlobalIndex);
				break;
			default:
				String msg = "STATE_0 XIA BU NENG JIE SHOU " + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				break;
			}
			break;
		case STATE_1:// 接受右选择、重新左选择操作到达STATE_3
			switch (operatorID) {
			case OP_ID_RIGHTSELECT:
				this.imageIndex_1 = this.findOperatorSlideIndex(points, operatorID);

				if (this.imageIndex_1 == null
						|| this.imageIndex_1[1] == INVALID_UNIT_INDEX
						||this.imageIndex_1[1] < imageIndex_0[1])		// 编辑符无效
				{
					Log.i("debug in Editdfa","right select is illegal !!!");
					//this.imageIndex_1[1] = INVALID_UNIT_INDEX;
					//this.editState = STATE_0;						//无效恢复到0状态
					ListTable.fillcolor=false;
					this.clearDFA();
				} else 
				{
				
					this.rightSelectGlobalIndex= this.imageIndex_1[1]-1;			//注意，根据我的计算坐标的规则，这里应该要减1
					Log.i("DEBUG--->","this.rightSelectGlobalIndex   "+this.rightSelectGlobalIndex);
					Log.i("DEBUG--->","this.leftSelectGlobalIndex   "+this.leftSelectGlobalIndex);
					if (this.rightSelectGlobalIndex != INVALID_UNIT_INDEX
						&& this.rightSelectGlobalIndex >= leftSelectGlobalIndex) {
						this.editState = STATE_3;
						OperatorEvent.rightSelectEvent.setIndex(rightSelectGlobalIndex);
						this.eventQueue.add(OperatorEvent.rightSelectEvent);
						
						//if(ListTable.TypeofCtrl != 4) 
						if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_4) 
							{
							ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_4;
							ListTable.selectedMargin[0]=this.leftSelectGlobalIndex;
							ListTable.selectedMargin[1]=this.rightSelectGlobalIndex;
							//Log.i("DEBUG--->","EDIT  ListTable.selectedMargin   "+ListTable.selectedMargin[0] +"   "+ListTable.selectedMargin[1]);
							}
						// 将选定的区域填充背景颜色
					//	 this.markSelectDomain(leftSelectGlobalIndex,
					//	 rightSelectGlobalIndex);
					} else {
						//this.editState = STATE_0;						//无效恢复到0状态
						//this.imageIndex_1[1] = INVALID_UNIT_INDEX;
						ListTable.fillcolor=false;
						this.clearDFA();
					}
				}
				break;
				
			case OP_ID_LEFTSELECT:							//重新左选择,这里的左选择意思是已经坐选择了，状态机期待右选择，而用户想重新选定左选择的坐标位置，应该让状态机接受这种情况
				this.imageIndex_0 = this.findOperatorSlideIndex(points, operatorID);
				if (this.imageIndex_0 == null
					|| this.imageIndex_0[1] == INVALID_UNIT_INDEX) {
					//editPanel.repaint();
				} else {
					this.leftSelectGlobalIndex = this.imageIndex_0[1];
					if (this.leftSelectGlobalIndex == INVALID_UNIT_INDEX) {
				//		editPanel.repaint();
						;
					} else {
						this.editState = STATE_1;
						this.eventQueue.remove(OperatorEvent.leftSelectEvent);
						OperatorEvent.leftSelectEvent.setIndex(this.leftSelectGlobalIndex);
						ListTable.selectedMargin[0]=this.leftSelectGlobalIndex;
						this.eventQueue.add(OperatorEvent.leftSelectEvent);
					}
				}
				break;
				
				
			default:
				this.editState = STATE_0;						//无效恢复到0状态
				String msg = "STATE_1  BU NENG JIE SHOU " + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				break;
			}
			break;
		case STATE_2:// 接受左选择、重新右选择操作到达STATE_3
			switch (operatorID) {
			case OP_ID_LEFTSELECT:
				this.imageIndex_0 = this.findOperatorSlideIndex(points, operatorID);
				/*
				if (this.imageIndex_0 == null
					|| this.imageIndex_0[1] == INVALID_UNIT_INDEX
					|| this.imageIndex_1[1] < imageIndex_0[1])
					*/
				
				if (this.imageIndex_0 == null
						|| this.imageIndex_0[1] == INVALID_UNIT_INDEX
						|| this.imageIndex_1[1] < imageIndex_0[1]){// 编辑符无效
					this.imageIndex_0[1] = INVALID_UNIT_INDEX;					
					
					ListTable.fillcolor=false;
					this.editState = STATE_0;						//无效恢复到0状态		
					this.clearDFA();
					
				} else {
					this.leftSelectGlobalIndex = this.imageIndex_0[1];
					if (this.leftSelectGlobalIndex != INVALID_UNIT_INDEX
						|| this.rightSelectGlobalIndex > leftSelectGlobalIndex) {
						this.editState = STATE_3;
						OperatorEvent.leftSelectEvent.setIndex(this.leftSelectGlobalIndex);
						this.eventQueue.add(OperatorEvent.leftSelectEvent);
						if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_4) 
						{
								ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_4;
								ListTable.selectedMargin[0]=this.leftSelectGlobalIndex;
								ListTable.selectedMargin[1]=this.rightSelectGlobalIndex;
							 
							 }
					} else {
						this.imageIndex_0[1] = INVALID_UNIT_INDEX;
					}
				}
	//			 this.editPanel.repaint();
				break;
				
			case OP_ID_RIGHTSELECT:// 重新右选择
				this.imageIndex_1 = this.findOperatorSlideIndex(points, operatorID);
				if (this.imageIndex_1 == null
					|| this.imageIndex_1[1] == INVALID_UNIT_INDEX) {
		//				editPanel.repaint();
				} else {
					this.rightSelectGlobalIndex= this.imageIndex_1[1]-1;	
					if (this.rightSelectGlobalIndex == INVALID_UNIT_INDEX) {
	   //				editPanel.repaint();
					} else {
						this.eventQueue.remove(OperatorEvent.rightSelectEvent);
						OperatorEvent.rightSelectEvent.setIndex(this.rightSelectGlobalIndex);
						ListTable.selectedMargin[1]=this.rightSelectGlobalIndex;
						this.eventQueue.add(OperatorEvent.rightSelectEvent);
					}
				}
				break;
				
				
			default:
	//			this.editPanel.repaint();
				String msg = "STATE_2 XIA BU NENG JIN XING" + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				break;
			}
			break;
		case STATE_3:// 接受复制操作状态不变，接受左剪切操作到达STATE_5
			// 接受右剪切操作到达STATE_6，接受删除操作到达状态STATE_4
		
			switch (operatorID) {
			

			case OP_ID_COPY:// 复制操作
				this.processCopy(this.leftSelectGlobalIndex,
					this.rightSelectGlobalIndex);
				Log.i("DEBUG-->","pastePanel :"+pastePanel.size());
				this.editState = STATE_0;
				this.eventQueue.remove(OperatorEvent.leftSelectEvent);
				this.eventQueue.remove(OperatorEvent.rightSelectEvent);
				//if(ListTable.TypeofCtrl != 5) ListTable.TypeofCtrl = 5;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_5) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_5;
				break;

			case OP_ID_HALFDEL:
				this.editState = STATE_4;
				break;

			case OP_ID_LEFTCUT:
				this.editState = STATE_5;
				break;

			case OP_ID_RIGHTCUT:
				this.editState = STATE_6;
				break;
			
			case OP_ID_LEFTSELECT:

				this.tempIndex = this.findOperatorSlideIndex(points, operatorID);
				if (this.imageIndex_0 == null
					|| this.tempIndex[1] == INVALID_UNIT_INDEX
					||this.tempIndex[1] > imageIndex_1[1]) {
					//editPanel.repaint();
					this.editState = STATE_0;					//一旦发现在3状态下的重新选择不合法，即把状态重置为0，清除内容
					this.eventQueue.remove(OperatorEvent.leftSelectEvent);
					this.eventQueue.remove(OperatorEvent.rightSelectEvent);
					
					ListTable.fillcolor=false;
					this.clearDFA();
					ListTable.fillcolor=false;
					
					ListTable.TypeofCtrl =-1;
					pastePanel.clear();
				} else {
					this.leftSelectGlobalIndex =this.tempIndex[1];
					if (this.leftSelectGlobalIndex == INVALID_UNIT_INDEX) {
				//		editPanel.repaint();
						;
					} else {
						this.editState = STATE_3;
						this.eventQueue.remove(OperatorEvent.leftSelectEvent);
						OperatorEvent.leftSelectEvent.setIndex(this.leftSelectGlobalIndex);
						ListTable.selectedMargin[0]=this.leftSelectGlobalIndex;
						ListTable.selectedMargin[1]=this.rightSelectGlobalIndex;		
						this.eventQueue.add(OperatorEvent.leftSelectEvent);
					}
				}
				break;	
				
				
				
			case OP_ID_RIGHTSELECT:

				this.tempIndex = this.findOperatorSlideIndex(points, operatorID);
				if (this.imageIndex_0 == null
					|| this.tempIndex[1] == INVALID_UNIT_INDEX
					||this.tempIndex[1] <imageIndex_0[1]) {
					//editPanel.repaint();
					this.editState = STATE_0;					//一旦发现在3状态下的重新选择不合法，即把状态重置为0，清除内容
					this.eventQueue.remove(OperatorEvent.leftSelectEvent);
					this.eventQueue.remove(OperatorEvent.rightSelectEvent);
					
					ListTable.fillcolor=false;
					this.clearDFA();
					ListTable.fillcolor=false;
					
					ListTable.TypeofCtrl =-1;
					pastePanel.clear();
				} else {
					this.rightSelectGlobalIndex =this.tempIndex[1]-1;
					if (this.rightSelectGlobalIndex == INVALID_UNIT_INDEX) {
				//		editPanel.repaint();
						;
					} else {
						this.editState = STATE_3;
						this.eventQueue.remove(OperatorEvent.rightSelectEvent);
						OperatorEvent.rightSelectEvent.setIndex(this.rightSelectGlobalIndex);
						ListTable.selectedMargin[0]=this.leftSelectGlobalIndex;
						ListTable.selectedMargin[1]=this.rightSelectGlobalIndex;
						this.eventQueue.add(OperatorEvent.rightSelectEvent);
					}
				}
				break;	
			
			default:
	//			this.editPanel.repaint();
				String msg = "STATE_3  XIA BU NENG JIN XING" + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				break;
			}
			break;
		case STATE_4:// 接受删除操作到达STATE_0
			switch (operatorID) {
			case OP_ID_HALFDEL:
				int answer=0;            //这里我修改成这样的！！！
//				int answer = JOptionPane.showConfirmDialog(this.editPanel.getFrame(),
//					"确定要删除这段文字吗？", "确认", JOptionPane.YES_NO_OPTION,
//					JOptionPane.QUESTION_MESSAGE);
				if (answer == 0) {
					this.editState = STATE_0;
					this.processDelete(this.leftSelectGlobalIndex,
						this.rightSelectGlobalIndex);
					Log.i("DEBUG--->","OP_ID_HALFDEL");
					if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_2) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_2;
					this.eventQueue.remove(OperatorEvent.leftSelectEvent);
					this.eventQueue.remove(OperatorEvent.rightSelectEvent);
					this.clearDFA();
					// 局部重画
//					this.editPanel.LocallyRepaint();
				} else {
					this.editState = STATE_3;
//					this.editPanel.repaint();
					this.clearDFA();
				}
				break;
			default:
//				this.editPanel.repaint();
				String msg = "STATE_4 XIA BU NENG JIN XING" + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				
				this.clearDFA();
				ListTable.fillcolor=false;
				break;
			}
		case STATE_5:// 接受右剪切操作到达STATE_0
			switch (operatorID) {
			case OP_ID_RIGHTCUT:
				this.processCut();// 和STATE_5状态下接受左选择符一样
			//	ListTable.fillcolor=false;
				//if(ListTable.TypeofCtrl != 7) ListTable.TypeofCtrl = 7;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_7) 	ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_7;
				this.eventQueue.remove(OperatorEvent.leftSelectEvent);
				this.eventQueue.remove(OperatorEvent.rightSelectEvent);
				break;

			default:
//				this.editPanel.repaint();
				String msg = "STATE_5 XIA BU NENG JIN XING " + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				this.clearDFA();
				ListTable.fillcolor=false;
				break;
			}
			break;
		case STATE_6:// 接受左剪切操作到达STATE_0
			switch (operatorID) {
			case OP_ID_LEFTCUT:
				this.processCut();// // 和STATE_5状态下接受左选择符一样
				//if(ListTable.TypeofCtrl != 7) ListTable.TypeofCtrl = 7;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_7) 	ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_7;
				this.eventQueue.remove(OperatorEvent.leftSelectEvent);
				this.eventQueue.remove(OperatorEvent.rightSelectEvent);
				break;

			default:
//				this.editPanel.repaint();
				String msg = "STATE_6 XIA BU NENG JIN XING " + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				this.clearDFA();
				ListTable.fillcolor=false;
				break;
			}
			break;
		case STATE_7:// 接受删除操作一半到达STATE_0
			switch (operatorID) {
			
			
			case OP_ID_HALFDEL:
				
				
				this.imageIndex_1 = this.findOperatorSlideIndex(points, operatorID,points.size()-1);
				if (this.imageIndex_1 == null
					|| this.imageIndex_1[1] == INVALID_UNIT_INDEX) {
		//				editPanel.repaint();
				} else {
					this.rightSelectGlobalIndex= this.imageIndex_1[1]-1;	
					if (this.rightSelectGlobalIndex == INVALID_UNIT_INDEX) {
	   //				editPanel.repaint();
					} else {
						//this.editState = STATE_2;
						OperatorEvent.rightSelectEvent.setIndex(this.rightSelectGlobalIndex);
						this.eventQueue.add(OperatorEvent.rightSelectEvent);
						
						//Log.i("DEBUG--->","DEL  imageindex_1  "+this.rightSelectGlobalIndex);
					}
				}
				
				int answer=0;
				if (answer == 0) {
					this.editState = STATE_0;
					this.processDelete(this.leftSelectGlobalIndex,
						this.rightSelectGlobalIndex);
					Log.i("DEBUG--->","OP_ID_HALFDEL");
					if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_2) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_2;
					this.eventQueue.remove(OperatorEvent.leftSelectEvent);
					this.eventQueue.remove(OperatorEvent.rightSelectEvent);
					this.clearDFA();
					// 局部重画
				} else {
					this.editState = STATE_0;
					this.clearDFA();
				}
				break;
	
			default:
//				this.editPanel.repaint();
				String msg = "STATE_7 XIA BU NENG JIN XING " + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				this.clearDFA();
				ListTable.fillcolor=false;
				break;
			}
			break;
		case STATE_8: {// 原来为插入操作，现在为选中图片操作，未实现
//			this.editPanel.repaint();
			//String msg = editState + "状态错误，不能进行 " + operatorID.getType();
			String msg = editState + "ZHUANG TAI CUO WU, BU NENG JIN XING " + operatorID.getType();
			Logger.logger.log(new Exception(msg));
			this.clearDFA();
			ListTable.fillcolor=false;
			break;
		}
		default:
//			this.editPanel.repaint();
			//String msg = editState + "状态错误，不能进行 " + operatorID.getType();
			String msg = editState + "ZHUANG TAI CUO WU, BU NENG JIN XING " + operatorID.getType();
			Logger.logger.log(new Exception(msg));
			this.clearDFA();
			ListTable.fillcolor=false;
			break;
		}
		if (points.size() != 0) {
			points.clear();
		}
	}

	/**
	 * 处理左选择操作：修改左选择区域的起始全局索引
	 */
	private void processLeftSelect() {
		ViewUnit unit0 = ListTable.slideWinIndexTable.get(imageIndex_0[0]).get(
		imageIndex_0[1]);
		leftSelectGlobalIndex = unit0.getGlobalIndex();
	}

	/**
	 * 处理右选择操作：修改右选择区域的起始全局索引
	 */
	private void processRightSelect() {
		ViewUnit unit0 = ListTable.slideWinIndexTable.get(imageIndex_1[0]).get(
		imageIndex_1[1]);
		rightSelectGlobalIndex = unit0.getGlobalIndex();
	}

	/**
	 * 处理复制操作：根据给出的索引firstIndex和lastIndex，完成复制操作
	 * 
	 * @param firstGlobalIndex：起始单元的索引，该索引值为全局索引
	 * @param lastGlobalIndex：结束单元的索引，该索引值为全局索引
	 */
	private void processCopy(int firstGlobalIndex, int lastGlobalIndex) {
		DataUnit memGlobalData;
//		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 先将粘贴板中的原有内容清除掉
		pastePanel.clear();
		if (firstGlobalIndex < 0 || lastGlobalIndex < 0) {
		} 
		else if (firstGlobalIndex <= ListTable.globalIndexTable.size()
			&& lastGlobalIndex <= ListTable.globalIndexTable.size()) {
			for (int index = firstGlobalIndex; index <= lastGlobalIndex; index++) {
				memGlobalData = ListTable.globalIndexTable.get(index);
				DataUnit a = memGlobalData.clone();
				pastePanel.add(a);
			}
		}
//		FlavorSelection selection = new FlavorSelection(pastePanel);
//		clipboard.setContents(selection, null);
	}

	/**
	 * 处理粘贴操作：根据给出的索引unitIndex，完成粘贴操作
	 * 
	 * @param unitGlobalIndex：粘贴的位置的全局索引
	 */
	private void processPaste(int unitGlobalIndex) {
		DataUnit copyData;
		DataUnit memGlobalData;

		ArrayList<DataUnit> myPastePanel = null;

		myPastePanel = (ArrayList<DataUnit>) pastePanel.clone();
		if (myPastePanel == null) {
			this.curCursorGlobalIndex = unitGlobalIndex;
			return;
		}

		if (unitGlobalIndex >= 0
			&& unitGlobalIndex <= ListTable.globalIndexTable.size()) {
			// 将粘贴板中的内容添加到索引，然后显示出来
			for (int index = 0; index < myPastePanel.size(); index++) {
				copyData = myPastePanel.get(index);// 取出粘贴板中的内容

				memGlobalData = copyData.clone();				
				ListTable.globalIndexTable.add(unitGlobalIndex + index,
					memGlobalData);// 存储索引和图片			
			}
			// 重置光标
		//	curCursorGlobalIndex = unitGlobalIndex + myPastePanel.size();
			ListTable.temppositionofaddcharunit = ListTable.temppositionofaddcharunit+myPastePanel.size();
		}
		this.leftSelectGlobalIndex = INVALID_UNIT_INDEX;
		this.rightSelectGlobalIndex = INVALID_UNIT_INDEX;
		myPastePanel.clear();
	}

	/**
	 * 处理插入操作：根据给出的索引unitIndex，完成插入
	 * 
	 * @param slideWinIndex：插入位置的全局索引,
	 * <br>slideWinIndex[0]表示第几个滑动窗口，slideWinIndex[1]表示滑动窗口的第几个单元
	 */
	public void setInsertGlobalIndex(int[] slideWinIndex) {
		if (slideWinIndex == null || slideWinIndex.length != 2) {
			Logger.logger.log(new Exception("插入操作输入参数错误"));
			return;
		}
		int globalIndex = -1;
		List<ViewUnit> list = ListTable.slideWinIndexTable
			.get(slideWinIndex[0]);
		int slideSize = list.size();
		// 首先：通过滑动窗口索引查找全局索引
		if (slideWinIndex[1] >= 0 && slideWinIndex[1] < slideSize) {
				globalIndex = list.get(slideWinIndex[1]).getGlobalIndex();
		} else if (slideWinIndex[1] == slideSize) {
			globalIndex = list.get(slideSize - 1).getGlobalIndex() + 1;
		} else {
			Logger.logger.log(new Exception("插入错误：插入位置的滑动索引为" + slideSize
				+ "滑动窗口索引大小为：" + slideSize));
		}
		// 修正
		if (globalIndex > ListTable.globalIndexTable.size()) {
			globalIndex = ListTable.globalIndexTable.size();
		} else if (globalIndex < 0) {
			globalIndex = 0;
		}
		// 其次：修改当前的光标索引
		this.curCursorGlobalIndex = globalIndex;
//		this.editPanel.setCursorIndex(curCursorGlobalIndex);
		// 最后：插入操作
		if (globalIndex >= 0
			&& globalIndex <= ListTable.globalIndexTable.size()) {
//			TableSearcher.getInstance().resetchangeRangeGlobalIndex(
//				globalIndex, globalIndex);
//			editPanel.LocallyRepaint();
		}
	}
	
	public void processInsert(List<MyPoint> points) {
//		int[] index = this.findOperatorSlideIndex(points, OperatorType.OP_ID_INSERT);
//		if (index == null
//			|| index[1] == INVALID_UNIT_INDEX) {
//			editPanel.repaint();
//		} else {
//			this.setInsertGlobalIndex(index);
//		}
	}

	/**
	 * 设定删除操作作用的数据单元的滑动窗口索引，包括起始全局索引和结束全局索引
	 * 
	 * @param first[]：滑动窗口索引，该数组第一个元素为删除符的起始单元滑动窗口索引，第二个为结束单元滑动窗口索引
	 * @return：删除符作用区域的 <b>起始全局索引</b> 和 <b>结束全局索引</b>
	 */
	private void setDeleteGlobalIndex(int[] first, int[] last) {
		if (null == first || 2 != first.length || null == last
			|| 2 != last.length) {
			Logger.logger.log(new Exception("参数错误：删除符的滑动窗口索引必须为开始和结束两个"));
			return;
		}
		if ((first[1] == INVALID_UNIT_INDEX) || (last[1] == INVALID_UNIT_INDEX)
			|| (first[0] > last[1])) {// 该编辑符的位置无效！
			return;
		} else {
			List<ViewUnit> firstList = ListTable.slideWinIndexTable.get(first[0]);
			List<ViewUnit> lastList = ListTable.slideWinIndexTable.get(last[0]);
			if(first[1] == firstList.size()) {
				first[1] = firstList.get(first[1]-1).getGlobalIndex();
			} else {
				first[1] = firstList.get(first[1]).getGlobalIndex();
			}
			if(last[1] == lastList.size()) {
				last[1] = lastList.get(last[1]-1).getGlobalIndex();
			} else {
				last[1] = lastList.get(last[1]).getGlobalIndex();
			}
		}
		return;
	}

	/**
	 * 处理删除操作：根据给出的索引firstIndex和lastIndex，完成删除操作
	 * 
	 * @param firstGlobalIndex：删除数据单元的起始全局索引
	 * @param lastGlobalIndex：删除数据单元的末尾全局索引
	 */
	private void processDelete(int firstGlobalIndex, int lastGlobalIndex) {
		int changeGlobalIndex;
		if (firstGlobalIndex < 0 || lastGlobalIndex < 0) {
			Logger.logger.log(new Exception("删除操作错误：起始索引为" + firstGlobalIndex
				+ "，结束索引为" + lastGlobalIndex));
		} else if (firstGlobalIndex < ListTable.globalIndexTable.size()
			&& lastGlobalIndex < ListTable.globalIndexTable.size()) {
			for (int index = firstGlobalIndex; index <= lastGlobalIndex; index++) {
				// 从索引中删除该文字
				ListTable.globalIndexTable.remove(firstGlobalIndex);
			}
			// 重置光标
	//		curCursorGlobalIndex = firstGlobalIndex;
			ListTable.temppositionofaddcharunit = firstGlobalIndex;
//			this.editPanel.setCursorIndex(curCursorGlobalIndex);

			if (firstGlobalIndex - 1 >= 0) {
				changeGlobalIndex = firstGlobalIndex - 1;
			} else {
				changeGlobalIndex = 0;
			}
//			TableSearcher.getInstance().resetchangeRangeGlobalIndex(
//				changeGlobalIndex, changeGlobalIndex);
		}
	}

	/**
	 * 处理剪切操作，剪切操作必须先经过选择操作后才能进行
	 */
	private void processCut() {
		int answer =0;
//		int answer = JOptionPane.showConfirmDialog(this.editPanel.getFrame(),
//			"确定要删除这段文字吗？", "确认", JOptionPane.YES_NO_OPTION,
//			JOptionPane.QUESTION_MESSAGE);
		if (answer == 0) {
			this.processCopy(this.leftSelectGlobalIndex,// 先复制
				this.rightSelectGlobalIndex);
			// 取消选中区域的背景
	//		 this.undoSelectDomain(leftSelectGlobalIndex,
	//		 rightSelectGlobalIndex);
			this.editState = STATE_0;
			this.processDelete(leftSelectGlobalIndex,// 然后删除
				rightSelectGlobalIndex);
//			this.editPanel.LocallyRepaint();
		} else {
			this.editState = STATE_3;
//			this.editPanel.repaint();
		}
		if (this.imageIndex_2 != null) {
			this.imageIndex_2[1] = INVALID_UNIT_INDEX;
		}
		if (this.imageIndex_3 != null) {
			this.imageIndex_3[1] = INVALID_UNIT_INDEX;
		}
	}

	/**
	 * 处理空格操作：根据给出的索引unitIndex，完成缩进
	 * 
	 * @param unitGlobalIndex：插入空格符的全局索引
	 */
	private void processSpace(int unitGlobalIndex) {
		ControlUnit memGlobalData;
		if (unitGlobalIndex >= 0
			&& unitGlobalIndex <= ListTable.globalIndexTable.size()) {
			memGlobalData = new ControlUnit(DataType.TYPE_CTRL_SPACE, curCharFormat);
	//		memGlobalData.setParaFormat(curParaFormat);
	//		ListTable.globalIndexTable.add(unitGlobalIndex, memGlobalData);// 存储索引和图片
			// 重置光标
	//		curCursorGlobalIndex = unitGlobalIndex + 1;
			ListTable.globalIndexTable.add(unitGlobalIndex,memGlobalData);
			ListTable.temppositionofaddcharunit++;
			// CompressPicture.Indexpno = ListTable.globalIndexTable.size();
//			TableSearcher.getInstance().resetchangeRangeGlobalIndex(
//				unitGlobalIndex, unitGlobalIndex + 1);
		}
	}

	/**
	 * 处理退格操作：根据给出的索引unitIndex，完成回格
	 * 
	 * @param unitGlobalIndex：退格操作发生的位置的全局索引
	 */
	private void processBackspace(int unitGlobalIndex) {

		numtitle=ListTable.globalTitle.size();//zhuxiaoqing 2011.09.28
		if (unitGlobalIndex > 0
			&& unitGlobalIndex <= ListTable.globalIndexTable.size()&&ListTable.temppositionofaddcharunit>numtitle+1) {
			ListTable.globalIndexTable.remove(unitGlobalIndex - 1);
			// 重置光标
	//		curCursorGlobalIndex = unitGlobalIndex - 1;
			ListTable.temppositionofaddcharunit = unitGlobalIndex - 1;

		}
	}

	/**
	 * 处理回车操作：根据给出的索引unitIndex，完成换行
	 * 
	 * @param unitGlobalIndex：回车操作发生的位置的全局索引
	 */
	public void processEnter(int unitGlobalIndex) {
		ControlUnit memGlobalData;
		if (unitGlobalIndex >= 0
			&& unitGlobalIndex <= ListTable.globalIndexTable.size()) {
			memGlobalData = new ControlUnit(DataType.TYPE_CTRL_ENTER, curCharFormat);
			ListTable.globalIndexTable.add(unitGlobalIndex, memGlobalData);// 存储索引和图片
//			int paraIndex = TableSearcher.getInstance().getParaNoForThisUnit(unitGlobalIndex);
//			ListTable.paragraphTable.add(paraIndex, new ParagraphUnit(unitGlobalIndex, curParaFormat));
			// 重置光标
	//		curCursorGlobalIndex = unitGlobalIndex + 1;
			ListTable.temppositionofaddcharunit++;
//			TableSearcher.getInstance().resetchangeRangeGlobalIndex(
//				unitGlobalIndex, unitGlobalIndex + 1);
		}
		
//		editPanel.documentControl.setHaveChangedFlag();
//		editPanel.LocallyRepaint();
//		editPanel.getMyCursor().setPosition(curCursorGlobalIndex);
	}

	/**
	 * 通过坐标值，找到该坐标在当前滑动窗口中的页号
	 * 
	 * @param x：x坐标
	 * @param y：y坐标
	 * @return：页号，若没有找到则返回 -1
	 */
	
/*
	private int getPageIdByCoordinate(int x, int y) {
		int numOfPage = layoutManager.getPageNumInScreen();
		int pageId = -1;
		int w = layoutManager.getPageWidth();
		int h = layoutManager.getPageHeight();
		for (int n = 0; n < numOfPage; ++n) {
			Point p = layoutManager.getPageDisplaceInView(n);
			if (x >= p.x && y >= p.y && x < (p.x + w) && y < (p.y + h)) {
				pageId = n;
				break;
			}
		}
		return pageId;
	}
*/

/*
	/**
	 * 找出编辑符作用单元的滑动索引
	 * 
	 * @param points：编辑符点集
	 * @param id：编辑符类型
	 * @param subscript：可变形参编辑符点集的下表
	 * @return：数组：【编辑符作用单元的逻辑页号】和【逻辑页中的第几个单元】
	 */
	
	public int[] findOperatorSlideIndex(List<MyPoint> points, OperatorType id, int... subscript) {
		if (null == points || 0 == points.size()) {
			Logger.logger.log(new Exception("操作符点集为空"));
			return null;
		}

		temppositionofaddcharunit=0;
		MyPoint first = points.get(0);// 第一点
		MyPoint last = points.get(points.size() - 1);// 最后一点
		
		//下面这个if判断只有在0状态下画删除符时候才会进行执行，因为只有那个时候subscript.length != 0
		if(subscript != null && subscript.length != 0) {
			
			
			if(this.imageIndex_0==null||this.imageIndex_0[1] == INVALID_UNIT_INDEX)
			{
				first = points.get(0);
				last = first;
				Log.i("DEBUG--->","this.imageIndex_0==null");
			}
			else
			{
				first = last;
				Log.i("DEBUG--->","this.imageIndex_0!=null");
			}
			
			
			
		}
		if(subscript == null)
			Log.i("DEBUG--->","subscript == null");
		if(subscript.length == 0)			
			Log.i("DEBUG--->"," subscript.length== 0");
		
		
		
		// x和y是操作符的坐标
//		int tempx = first.getX();
//		int tempy = (first.getY() + last.getY()) / 2;
		Log.i("debug in find index ","cursor x and y is "+cursorlocatex+" "+cursorlocatey);
		int tempx = cursorlocatex;//2011.07.01 liqiang
		int tempy = cursorlocatey;//2011.07.01 liqiang
		cursloate(tempx,tempy);
  		return new int[] {0, temppositionofaddcharunit,tempx,tempy};
  		
	
	}
	
 public static void cursloate(int tempx,int tempy)									//编辑状态下，左右选择时候光标定位
 {
	 	short baselineheight=  (short) (ListTable.charactersize+16);
		DataType PreUnitType=DataType.TYPE_CALLIGRAPHY;
		short screenwidth = Kview.screenwidth;
		short spacewidth = (short) ListTable.spacewidth;
		DataType datatype;
  		int tempwidth = 0,tempsum = 0,tempcursofy=baselineheight;
  		int locat =0;
  		int rowcount =1;
  	    int tempheight =0;								//用来记录一横条范围内的所有图片单元的最高高度
  	    int width =0;									//用来记录一横条图片单元的累计宽度
  	    boolean firstimageunitperline = true;
		int size= ListTable.globalIndexTable.size();
		PreUnitType=DataType.TYPE_CALLIGRAPHY;	
  		Log.v("tempX--->",""+tempx);
  		Log.v("tempY--->",""+tempy);
  		
  		tempy +=Kview.maxscrollvice;					//这个是为了得到相对于视图的坐标
	for(locat=ListTable.globalTitle.size(); locat!= ListTable.globalIndexTable.size();locat++)
		{
			DataUnit dataunit = ListTable.globalIndexTable.get(locat);
			datatype =dataunit.getCtrlType();

			
			if(datatype!= DataType.TYPE_CTRL_ENTER)
			{
				tempsum += ListTable.globalIndexTable.get(locat).getWidth();
				tempsum += spacewidth;
				tempwidth+= ListTable.globalIndexTable.get(locat).getWidth()/2.0;

				if(datatype==null)
	  				datatype = ListTable.globalIndexTable.get(locat).getDataType();
				
				if((datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)&&PreUnitType==DataType.TYPE_IMAGE)			//碰到这种情况应该让tempsum＝0；
				{
					tempwidth = 0;
					tempsum = 0;			
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
				}
				if((PreUnitType == DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)&&datatype==DataType.TYPE_IMAGE)			//碰到这种情况应该让tempsum＝0；
				{
					tempwidth = 0;
					tempsum = 0;			
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
				}
			}
			
			if(datatype==null)
				datatype = ListTable.globalIndexTable.get(locat).getDataType();
		
			if(tempsum>screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改
			{	
				if(PreUnitType == DataType.TYPE_IMAGE&&datatype==DataType.TYPE_IMAGE)
				{
					if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
					tempheight=0;
				}
				else 
					rowcount++;
				tempcursofy	= rowcount*baselineheight;
				tempwidth = 0;
				tempsum = 0;
				tempheight =0;
				width =0;
				firstimageunitperline = true;
				
				//if(datatype!= DataType.TYPE_CONTROL)				//这是在干什么？
				if(datatype!= DataType.TYPE_CTRL_ENTER)
				{
				tempsum = ListTable.globalIndexTable.get(locat).getWidth();
				tempsum += spacewidth;
	  			tempwidth+= ListTable.globalIndexTable.get(locat).getWidth()/2.0;

				}

				if(tempwidth<tempx&&(tempcursofy-tempy)>=baselineheight&&datatype!= DataType.TYPE_CTRL_ENTER)
	  			{
	  				Log.v("DEBUG---.","jue ding chu lai");
	  				Log.i("DEBUG--->","tempcursofy-tempy  "+tempcursofy+ "  -  "+tempy);
	  				Log.i("DEBUG--->","  locat "+locat);
	  				ListTable.temppositionofaddcharunit = locat;
	  				break;
	  			}

			}
			
			
			if(datatype == DataType.TYPE_IMAGE)
			{
				int height = dataunit.getHeight();
				if(PreUnitType==DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)
				{
					tempwidth = 0;
					tempsum = 0;
					firstimageunitperline =true;		
					tempsum += dataunit.getWidth();
					tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
		  			width =0;
				}
				else if(PreUnitType==DataType.TYPE_CTRL_ENTER)
				{
					tempwidth = 0;
					tempsum = 0;
					firstimageunitperline =true;
					tempsum += dataunit.getWidth();
					tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
					rowcount--;
					width =0;
				}
	
				int templocat= locat+1;

				DataType tempdatatype=DataType.TYPE_CALLIGRAPHY;
				if(templocat!=size && firstimageunitperline ==true)
				{
					width = dataunit.getWidth();
					width+= spacewidth;
					tempdatatype = ListTable.globalIndexTable.get(templocat).getDataType();
					tempheight = dataunit.getHeight();
					
 				width += ListTable.globalIndexTable.get(templocat).getWidth();
					width+=spacewidth;
					
					//用于统计同一条上的所有图片单元的最高高度，每一条上就只要在第一个图片单元处统计即可，后面同一条上的单元不需要统计
					
					while(tempdatatype==DataType.TYPE_IMAGE && templocat!=size&&width<screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang 添加页边距之后的修改
					{
					short heigh = ListTable.globalIndexTable.get(templocat).getHeight();
					tempheight=tempheight>heigh?tempheight:heigh;
					templocat++;
					if(templocat!=size)
					{
						tempdatatype = ListTable.globalIndexTable.get(templocat).getDataType();
						if(tempdatatype==DataType.TYPE_IMAGE)
						{
						width += ListTable.globalIndexTable.get(templocat).getWidth();
						width+=spacewidth;
						}
						else
							break;
					}
					else
						break;			
					}
					firstimageunitperline=false;
				}
				else if(templocat==size && firstimageunitperline ==true)		//最后一个单元为图片单元
					tempheight = dataunit.getHeight();
				
				height =tempheight;
			tempcursofy	= rowcount*baselineheight;
				


				//似乎可以屏蔽掉这个判断
  		//	if(tempwidth<tempx&&(tempcursofy-tempy)>=0)
  		//	{	
  		//		ListTable.temppositionofaddcharunit = locat+1;
  		//		Log.v("DEBUG---.","jue ding chu lai  ListTable.temppositionofaddcharunit   "+ListTable.temppositionofaddcharunit);	
  		//		
  		//		break;
  		//	}
  			
			
				
  		//	else if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)	
			if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)//着事我根据今天重新显示规则后生成的确定坐标方式
  			{	

  				temppositionofaddcharunit = locat;
  	//			Log.i("DEBUG--->","^^^^^^^^^^^^^^   locat "+locat );
  	//			Log.i("DEBUG--->","^^^^^^^^^^^^^^^^^  height   "+height );
  	//			Log.i("DEBUG--->","^^^^^^^^^^^^^^   tempy-tempcursofy   "+tempy+"   "+tempcursofy );
  				break;		//在一串图片单元中发现添加位置
  			}
  			
  			else if(tempwidth<tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)		//表示点到某一条的所有图片单元的最后面
  			{
  				

  				temppositionofaddcharunit = locat+1;
  				if((locat+1)!=size&&ListTable.globalIndexTable.get(locat+1).getDataType()!=DataType.TYPE_IMAGE)
  				{	
  					temppositionofaddcharunit = locat+1;
  					Log.i("DEBUG---->","rowcount  "+rowcount);
  					break;
  				}
  				
  				else if((locat+1)!=size&&ListTable.globalIndexTable.get(locat+1).getDataType()==DataType.TYPE_IMAGE)
  				{
  					int sumvice = tempsum;
  					DataUnit dataunittemp = ListTable.globalIndexTable.get(locat+1);
  					sumvice += dataunittemp.getWidth();
  					sumvice += spacewidth;

  					if(sumvice/(screenwidth-ListTable.leftmargin-ListTable.rightmargin)>=1)//2011.07.14 liqiang 添加页边距之后的修改
  					{
  						temppositionofaddcharunit = locat+1;
	  					break;
  					}
  					else
		  			{
		  				tempwidth=tempsum;
		  				temppositionofaddcharunit = locat+1;
		  			}
  				}
  					
  			}
			temppositionofaddcharunit = locat+1;				//只要在没有判断退出之前，都必须对光标位置进行实时定位
		//	Log.i("DEBUG--->","tempy-tempcursofy  8888   "+tempy+"   "+tempcursofy );
			}
			
			else if(datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)			//在图片单元下方如果是字符单元
			{
				if(PreUnitType==DataType.TYPE_IMAGE)
				{
					tempwidth = 0;
					tempsum = 0;				
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
		  			if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
	  				rowcount+=(tempheight/baselineheight+1);
	  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
	  				rowcount+=tempheight/baselineheight;
	  			else if(tempheight/baselineheight<1)
	  				rowcount+=1;
		  			rowcount++;				//本身需要换行
		  			
		  			
		  			tempcursofy	= rowcount*baselineheight;
		  		//Log.i("DEBUG--->","rowcount  "+rowcount+"tempheight  "+tempheight );
		  			Log.i("DEBUG--->","rowcount "+rowcount);
				}

				 //下面这两个if判断其实是用来定位CHARUNIT 和SPACEUNIT 的，对于图片单元的定位，在图片单元本身那块
					if(tempx>=tempwidth)	
	  				{
						
						tempwidth = tempsum;
						temppositionofaddcharunit = locat+1;
						
						DataUnit tempdata;
						DataType tempdatatype = null;
						if((locat+1)!=size)
						{
							tempdata =  ListTable.globalIndexTable.get(locat+1);
							tempdatatype= tempdata.getDataType();				//用来记录前一个单元的类型
							if(tempdatatype==DataType.TYPE_CONTROL)
			            	tempdatatype=tempdata.getCtrlType();  
						}
						else
							break;
						
						
						if((locat+1)!=size&&(tempdatatype==DataType.TYPE_IMAGE||tempdatatype==DataType.TYPE_CTRL_ENTER )&&(tempy-tempcursofy)<=0&&(datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE))
						{
	  					Log.i("DEBUG--->","rowcount  ffddfdf" );
	  					break;
	  				
						}
	  				}	
					else if((tempy-tempcursofy)<=0)						//说明这是我要添加的位置
	  				{	
						temppositionofaddcharunit = locat;
						Log.i("DEBUG--->","tempcursofy  **********" );
						Log.i("DEBUG--->","tempy-tempcursofy   "+tempy+"--"+tempcursofy );
						break;
	  				}
					
					 temppositionofaddcharunit = locat+1;       //只要在没有判断退出之前，都必须对光标位置进行实时定位
			}
			else if(datatype == DataType.TYPE_CTRL_ENTER )
			{
				
			
				if(PreUnitType==DataType.TYPE_IMAGE)
			{
					if(tempheight/baselineheight>=1&&tempheight%baselineheight!=0)
		  				rowcount+=(tempheight/baselineheight+1);
		  			else if(tempheight/baselineheight>=1&&tempheight%baselineheight==0)
		  				rowcount+=tempheight/baselineheight;
		  			else if(tempheight/baselineheight<1)
		  				rowcount+=1;
					tempheight=0;
					rowcount++;							
			}
				else 
					rowcount++;	
				
				tempcursofy	= rowcount*baselineheight;
			tempwidth = 0;
			tempsum = 0;
			tempheight =0;
			width =0;
			firstimageunitperline = true;
					
			}
			

			 PreUnitType = dataunit.getDataType();				//用来记录前一个单元的类型
         if(PreUnitType==DataType.TYPE_CONTROL)
         	 PreUnitType=dataunit.getCtrlType();    
		}
		Log.v("ListTable.charunit---> is",""+temppositionofaddcharunit);
		return;
}


	public List<OperatorEvent> getEventQueue() {
		return eventQueue;
	}
	
	/**
	 *TODO <b>将选定的区域填充背景颜色</b>
	 *
	 *@param leftIndex 左索引
	 *@param rightIndex 右索引
	 */
	public void markSelectDomain (int leftIndex, int rightIndex) {
		int rgbValue = 0;
//		BufferedImage bufferImage = this.editPanel.getBufferImage();
//		Color color = new Color(210,210,210);
//		int offset = color.getRGB();
		ViewUnit viewUnit = null;
		
		int x, y, width, height;
		
		for (int index = leftIndex; index <= rightIndex; index++) {
//			viewUnit = TableSearcher.getInstance().globalToSlide(index);
			
			x = viewUnit.getX();
			y = viewUnit.getY();
			width = viewUnit.getWidth();
			height = viewUnit.getHeight();

			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {
//					rgbValue = bufferImage.getRGB(x + i, y + j);
					if (rgbValue == -1) {
//						rgbValue = offset;
					}
//					bufferImage.setRGB(x + i, y + j, rgbValue);
				}
//			this.editPanel.repaint();
		}
	}
	
	/**
	 *TODO <b> 取消选中区域的背景颜色 </b>
	 *
	 *@param leftIndex 左索引
	 *@param rightIndex 右索引
	 */
	public void undoSelectDomain(int leftIndex, int rightIndex){
		int rgbValue;
//		BufferedImage bufferImage = this.editPanel.getBufferImage();
//		Color color = new Color(210,210,210);
//		int offset = color.getRGB();
		ViewUnit viewUnit = null;
		
		int x, y, width, height;
		
		for (int index = leftIndex; index <= rightIndex; index++) {
//			viewUnit = TableSearcher.getInstance().globalToSlide(index);
			
			x = viewUnit.getX();
			y = viewUnit.getY();
			width = viewUnit.getWidth();
			height = viewUnit.getHeight();

			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {
//					rgbValue = bufferImage.getRGB(x + i, y + j);
//					if (rgbValue == offset) {
//						rgbValue = -1;
//					}
//					bufferImage.setRGB(x + i, y + j, rgbValue);
				}
//			this.editPanel.repaint();
		}
	}
	
	/**
	 *TODO 根据左右索引，设置文字单元的字体格式
	 *
	 *@param cf
	 */
	public void setCharFormat(CharFormat cf) {
		if (leftSelectGlobalIndex != INVALID_UNIT_INDEX
				&& rightSelectGlobalIndex != INVALID_UNIT_INDEX) {
			for (int index = leftSelectGlobalIndex; index <= rightSelectGlobalIndex; ++index) {
				DataUnit dataUnit = ListTable.globalIndexTable.get(index);
				if (dataUnit.getDataType() == DataType.TYPE_CHAR
						|| dataUnit.getCtrlType() == DataType.TYPE_CTRL_SPACE) {
					System.err.println("leftSelectGlobalIndex : "
							+ leftSelectGlobalIndex
							+ "  rightSelectGlobalIndex : "
							+ rightSelectGlobalIndex);
//					System.err.println("\nin editDFA.java  serCharFormat() : " + new Color(cf.getColor() )+
//							"   高度 ： " + cf.getHeight() + "   笔划粗细 ： " + cf.getStrokeWidth());
					dataUnit.setCharFormat(cf);
				}
			}
			ListTable.changeStartGlobalIndex = this.leftSelectGlobalIndex;
//			this.editPanel.LocallyRepaint();
		}
	}
	
	/**
	 *TODO 根据左右索引,以及鼠标的位置索引，设置段落格式
	 *<br> 本方法仅在设置段落后才调用
	 *
	 *@param paraFormat
	 */
	
/*
	public void setParaFormat(ParagraphFormat paraFormat) {
		int paraSize = ListTable.paragraphTable.size();
		int end, nextPara;
		if (leftSelectGlobalIndex != INVALID_UNIT_INDEX
				&& rightSelectGlobalIndex != INVALID_UNIT_INDEX) {
			System.err.println("leftSelectGlobalIndex ： " + leftSelectGlobalIndex 
					+ "  rightSelectGlobalIndex : " + rightSelectGlobalIndex);
			int beginParaIndex = TableSearcher.getInstance().getParaNoForThisUnit(leftSelectGlobalIndex);
			int endParaIndex = TableSearcher.getInstance().getParaNoForThisUnit(rightSelectGlobalIndex);
			for(int index = beginParaIndex; index <= endParaIndex; ++index) {
				nextPara = index + 1;
				if(nextPara <= (paraSize -1)) {
					end = ListTable.paragraphTable.get(nextPara).getGlobalIndex() - 1;
				} else {
					end = ListTable.globalIndexTable.size() - 1;
				}
				
				DataUnit dataUnit = ListTable.globalIndexTable.get(end);
				
				if(dataUnit.getDataType() != DataType.TYPE_CONTROL &&
						dataUnit.getCtrlType() != DataType.TYPE_CTRL_ENTER) {
					System.err.println(" there is mistake in setParaFormat() editDFA");
				} else {
					ControlUnit controlUnit = (ControlUnit)dataUnit;
					controlUnit.setParaFormat(paraFormat);
				}
				
				ListTable.paragraphTable.get(index).setParagraphFormat(paraFormat);
			}
			ListTable.changeStartGlobalIndex = this.leftSelectGlobalIndex;
		} else {
			System.err.println(" paraFormat.getRowSpaceValue  " + paraFormat.getRowSpaceValue()
					+ " paraFormat.getLeftIndent : " + paraFormat.getLeftIndent());
			
			int paraIndex = TableSearcher.getInstance().getParaNoForThisUnit(curCursorGlobalIndex);
			System.err.println(" paraIndex : " + paraIndex + "  第一个索引 ： " 
					+ ListTable.paragraphTable.get(paraIndex).getGlobalIndex());
			nextPara = paraIndex + 1;
			if(nextPara <= (paraSize -1)) {
				end = ListTable.paragraphTable.get(nextPara).getGlobalIndex() - 1;
			} else {
				end = ListTable.globalIndexTable.size() - 1;
			}
			
			DataUnit dataUnit = ListTable.globalIndexTable.get(end);
			
			if(dataUnit.getDataType() != DataType.TYPE_CONTROL &&
					dataUnit.getCtrlType() != DataType.TYPE_CTRL_ENTER) {
				System.err.println(" there is mistake in setParaFormat() editDFA");
			} else {
				ControlUnit controlUnit = (ControlUnit)dataUnit;
				controlUnit.setParaFormat(paraFormat);
			}
			
			System.err.println("paraIndex : " + paraIndex + "  in 编辑器里");
			ListTable.paragraphTable.get(paraIndex).setParagraphFormat(paraFormat);
			ListTable.changeStartGlobalIndex = curCursorGlobalIndex;
		}
		curParaFormat = paraFormat;
		this.editPanel.LocallyRepaint();
	}
	
*/
	
	/**
	 *TODO
	 *
	 *@param sectionFormat
	 */
/*
	public void setSectionFormat(SectionFormat sectionFormat) {
		int sectionIndex;
		int pageNum;
		
//		pageNum = TableSearcher.getInstance().getPageNoForThisUnit(curCursorGlobalIndex);
//		sectionIndex = TableSearcher.getInstance().getSectionNumForPage(pageNum);
		
//		System.err.println("\n 节的属性 ： " + sectionFormat.getUpMargin());
		ListTable.sectionTable.get(sectionIndex).setSectionFormat(sectionFormat);
//		layoutManager.setSectionFormat(sectionFormat);
		
		pageNum = ListTable.sectionTable.get(sectionIndex).getPageIndex();
		int rowNum = ListTable.pageGlobalIndexTable.get(pageNum).getRowNo();
		int index = ListTable.rowGlobalIndexTable.get(rowNum).getGlobalIndex();
		ListTable.changeStartGlobalIndex = index;
		this.editPanel.LocallyRepaint();
	}
*/
	/**
	 *TODO 设置当前的段落格式，在新建段单元的时候会用得上
	 *
	 *@param paraFormat
	 */
	public void setCurParaFormat(ParagraphFormat paraFormat) {
		curParaFormat = paraFormat;
	}
	
	public void setCurCharFormat(CharFormat charFormat) {
		curCharFormat = charFormat;
	}
}
