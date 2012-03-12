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
 * �༭״̬�µ��Զ���
 * 
 * @author: tanqiang
 * @create-time: 2008-7-18
 */
public class EditDFA {
	/* �Ա༭״̬���б��� */
	/**
	 * �༭״̬���룺<b>0</b>״̬<p/>����<b>��ʼ״̬</b>Ҳ��<b>����״̬</b>����״̬����<b>�κα༭����</b>
	 */
	public static final int STATE_0 = 0;
	/**
	 * �༭״̬���룺<b>1</b>״̬ <br/><b>0</b> ״̬����<b>��ѡ���</b>�󵽴��״̬����״̬����<b>��ѡ���</b>����
	 * <b>3</b> ״̬
	 */
	public static final int STATE_1 = 1;
	/**
	 * �༭״̬���룺<b>2</b>״̬ <br/><b>0</b> ״̬����<b>��ѡ���</b>�󵽴��״̬����״̬����<b>��ѡ���</b>����
	 * <b>3</b> ״̬
	 */
	public static final int STATE_2 = 2;
	/**
	 * �༭״̬���룺<b>3</b>״̬ <br/><b>1</b> ״̬����<b>��ѡ���</b>��<b>2</b> ״̬����</b>��ѡ���</b>�����״̬��
	 * ��״̬����<b>���Ʒ���</b>״̬���䣬����<b>����з�</b>�󵽴� <b>5</b>״̬�� ����<b>�Ҽ��з�</b>����
	 * <b>6</b> ״̬
	 */
	public static final int STATE_3 = 3;
	/**
	 * �༭״̬���룺<b>4</b>״̬�� <br/><b>3</b> ״̬����<b>ɾ����һ��</b>���״��״̬����״̬��<b>��ɾ������һ��</b>����
	 * <b>0</b> ״̬
	 */
	public static final int STATE_4 = 4;
	/**
	 * �༭״̬���룺<b>5</b>״̬�� <br/><b>3</b> ״̬����<b>����з�</b>�����״̬����״̬����<b>�Ҽ��з�</b>����
	 * <b>0</b> ״̬
	 */
	public static final int STATE_5 = 5;
	/**
	 * �༭״̬���룺<b>6</b>״̬�� <br/><b>3</b> ״̬����<b>�Ҽ��з�</b>�����״̬����״̬����<b>����з�</b>����
	 * <b>0</b> ״̬
	 */
	public static final int STATE_6 = 6;
	/**
	 * �༭״̬���룺<b>7</b>״̬�� <br/><b>0</b> ״̬����<b>ɾ����һ��</b>�����״̬����״̬����<b>ɾ������һ��</b>����
	 * <b>0</b> ״̬
	 */
	public static final int STATE_7 = 7;
	/**
	 * �༭״̬���룺<b>8</b>״̬�� <br/><b>0</b> ״̬����<b>�����</b>�����״̬����״̬<b>�������κ�״̬</b>
	 */
	public static final int STATE_8 = 8;
	/**
	 * �༭״̬���룺<b>9</b>״̬�� <br/><b>0</b> ״̬����<b>ѡ��ͼƬ����</b>�����״̬
	 */
	public static final int STATE_9 = 9;

	/** ��Ч��������Ԫ */
	public final static int INVALID_UNIT_INDEX = -1;

	/** ѡ��״̬��<b><i>��</i>ѡ���</b>ѡ���ַ��� <i>ȫ������<i> */
	private volatile int leftSelectGlobalIndex;

	/** ѡ��״̬��<b><i>��</i>ѡ���</b>ѡ���ַ��� <i>ȫ������<i> */
	private volatile int rightSelectGlobalIndex;

	/** ��ǰ�������λ�õ�ȫ������ */
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
	
	

	/** ��ǰ�༭״̬ */
	private int editState = STATE_0;

	// ���ֲ�����ʶ���㷨
	/** ������ʶ���﷨ģʽʶ�� */
	private HwOperRecog_SyntaxPat operRecognizer_SyntaxPat;
	/** ������ʶ�𣺻�Ԫ�ṹʶ�� */
	private HwOperRecog_Structure operRecognizer_Strcuture;

	/** ���ƻ��߼��е����� */
	public static ArrayList<DataUnit> pastePanel = new ArrayList<DataUnit>();

	/** �༭��� */
//	private HwEditPanel editPanel;

	/** �༭���¼����У����ڻ��Ʊ༭�� */
	private final List<OperatorEvent> eventQueue = new LinkedList<OperatorEvent>();
	
	private static int temppositionofaddcharunit=0;

	/** ���ֹ����� */
//	private final LayoutManager layoutManager = LayoutManager.getInstance();

//	public EditDFA(HwEditPanel panel) {
	public EditDFA() {
		//this.editPanel = panel;
		// ��ʼ�����ֲ�����ʶ����
		this.operRecognizer_SyntaxPat = new HwOperRecog_SyntaxPat();
		this.operRecognizer_Strcuture = new HwOperRecog_Structure();
		// �༭״̬��ʼ��Ϊ ��ʼ״̬��STATE_0
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
	 * ���ܲ������ĵ㼯�����ҵ���˽�з��� <b>processDFT() </b>�����Զ���
	 * 
	 * @param points��MyPoints���͵Ĳ������㼯������ʶ�������������
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

	/** ���״̬�� */
	public void clearDFA() {
		this.editState = STATE_0;
		if (this.imageIndex_0 != null) {
			this.imageIndex_0[0] = INVALID_UNIT_INDEX;
			this.imageIndex_0[1] = INVALID_UNIT_INDEX;
			this.imageIndex_0[2] = INVALID_UNIT_INDEX;			// ���2 �����3 Ԫ�طֱ���� ��ѡ��� ��������꣡����findOperatorSlideIndex����
			this.imageIndex_0[3] = INVALID_UNIT_INDEX;
		}
		if (this.imageIndex_1 != null) {
			this.imageIndex_1[0] = INVALID_UNIT_INDEX;
			this.imageIndex_1[1] = INVALID_UNIT_INDEX;
			this.imageIndex_0[2] = INVALID_UNIT_INDEX;			// ���2 �����3 Ԫ�طֱ���� ��ѡ��� ��������꣡����findOperatorSlideIndex��)
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
	 * ʶ�����������������״̬��
	 * 
	 * @param points��MyPoints���͵Ĳ������㼯������ʶ�������������
	 */
	private void processDFA(List<MyPoint> list) {
		List<MyPoint> points = new ArrayList<MyPoint>(list);
		OperatorType operatorID = operRecognizer_Strcuture.recognize(points);
		
		if (OperatorType.OP_ID_INVALID == operatorID) {
			operatorID = operRecognizer_SyntaxPat.recognize(points);
		}
		Log.i("DEBUG IN processDFA --- :","operatorID  "+operatorID);

	
		// �û�ȡ������ѡ��Ļ���˵��������в���
		if (operatorID == OperatorType.OP_ID_INVALID) {
			this.clearDFA();
			return;
		}
		
		this.curCursorGlobalIndex = ListTable.temppositionofaddcharunit;
		// ����ʶ����Ĳ�������������DFT
		switch (editState) {
		case STATE_0:// �����κβ���
			switch (operatorID) {

			case OP_ID_LEFTSELECT:// ��ѡ��
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

			case OP_ID_RIGHTSELECT:// ��ѡ��
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

			case OP_ID_PASTE:// ճ��
	//			this.curCursorGlobalIndex = ListTable.temppositionofaddcharunit;
				this.processPaste(this.curCursorGlobalIndex);
				//if(ListTable.TypeofCtrl !=6) ListTable.TypeofCtrl = 6;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_6) 
					ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_6;
				
				this.clearDFA();
				break;

			case OP_ID_HALFDEL:// ɾ�����������ҵ�ɾ����Ԫ����ʼ�ͽ�����Ԫ��ȫ������
				//this.imageIndex_0 = this.findOperatorSlideIndex(points, operatorID);
				this.imageIndex_0 = this.findOperatorSlideIndex(points, operatorID, points.size()-1);
				if (this.imageIndex_0 == null) {// �Ҳ�����������
	//			editPanel.repaint();
					break;
				}
				// --this.imageIndex_0[1];
				//--this.imageIndex_1[1];// ��1
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
					|| (imageIndex_0[1] > imageIndex_1[1])) {// "�ñ༭����λ����Ч��
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
	

			case OP_ID_SPACE:// �ո�
				//if(ListTable.TypeofCtrl != 3) ListTable.TypeofCtrl = 3;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_3) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_3;
				this.processSpace(this.curCursorGlobalIndex);
				break;

			case OP_ID_BACKSPACE:// ����
				//if(ListTable.TypeofCtrl != 8) ListTable.TypeofCtrl = 8;
				if(ListTable.TypeofCtrl !=Context_STATE.Ctrl_STATE_8) ListTable.TypeofCtrl = Context_STATE.Ctrl_STATE_8;
				this.processBackspace(curCursorGlobalIndex);
				break;

			case OP_ID_ENTER:// �س�����
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
		case STATE_1:// ������ѡ��������ѡ���������STATE_3
			switch (operatorID) {
			case OP_ID_RIGHTSELECT:
				this.imageIndex_1 = this.findOperatorSlideIndex(points, operatorID);

				if (this.imageIndex_1 == null
						|| this.imageIndex_1[1] == INVALID_UNIT_INDEX
						||this.imageIndex_1[1] < imageIndex_0[1])		// �༭����Ч
				{
					Log.i("debug in Editdfa","right select is illegal !!!");
					//this.imageIndex_1[1] = INVALID_UNIT_INDEX;
					//this.editState = STATE_0;						//��Ч�ָ���0״̬
					ListTable.fillcolor=false;
					this.clearDFA();
				} else 
				{
				
					this.rightSelectGlobalIndex= this.imageIndex_1[1]-1;			//ע�⣬�����ҵļ�������Ĺ�������Ӧ��Ҫ��1
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
						// ��ѡ����������䱳����ɫ
					//	 this.markSelectDomain(leftSelectGlobalIndex,
					//	 rightSelectGlobalIndex);
					} else {
						//this.editState = STATE_0;						//��Ч�ָ���0״̬
						//this.imageIndex_1[1] = INVALID_UNIT_INDEX;
						ListTable.fillcolor=false;
						this.clearDFA();
					}
				}
				break;
				
			case OP_ID_LEFTSELECT:							//������ѡ��,�������ѡ����˼���Ѿ���ѡ���ˣ�״̬���ڴ���ѡ�񣬶��û�������ѡ����ѡ�������λ�ã�Ӧ����״̬�������������
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
				this.editState = STATE_0;						//��Ч�ָ���0״̬
				String msg = "STATE_1  BU NENG JIE SHOU " + operatorID.getType();
				Logger.logger.log(new Exception(msg));
				break;
			}
			break;
		case STATE_2:// ������ѡ��������ѡ���������STATE_3
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
						|| this.imageIndex_1[1] < imageIndex_0[1]){// �༭����Ч
					this.imageIndex_0[1] = INVALID_UNIT_INDEX;					
					
					ListTable.fillcolor=false;
					this.editState = STATE_0;						//��Ч�ָ���0״̬		
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
				
			case OP_ID_RIGHTSELECT:// ������ѡ��
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
		case STATE_3:// ���ܸ��Ʋ���״̬���䣬��������в�������STATE_5
			// �����Ҽ��в�������STATE_6������ɾ����������״̬STATE_4
		
			switch (operatorID) {
			

			case OP_ID_COPY:// ���Ʋ���
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
					this.editState = STATE_0;					//һ��������3״̬�µ�����ѡ�񲻺Ϸ�������״̬����Ϊ0���������
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
					this.editState = STATE_0;					//һ��������3״̬�µ�����ѡ�񲻺Ϸ�������״̬����Ϊ0���������
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
		case STATE_4:// ����ɾ����������STATE_0
			switch (operatorID) {
			case OP_ID_HALFDEL:
				int answer=0;            //�������޸ĳ������ģ�����
//				int answer = JOptionPane.showConfirmDialog(this.editPanel.getFrame(),
//					"ȷ��Ҫɾ�����������", "ȷ��", JOptionPane.YES_NO_OPTION,
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
					// �ֲ��ػ�
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
		case STATE_5:// �����Ҽ��в�������STATE_0
			switch (operatorID) {
			case OP_ID_RIGHTCUT:
				this.processCut();// ��STATE_5״̬�½�����ѡ���һ��
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
		case STATE_6:// ��������в�������STATE_0
			switch (operatorID) {
			case OP_ID_LEFTCUT:
				this.processCut();// // ��STATE_5״̬�½�����ѡ���һ��
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
		case STATE_7:// ����ɾ������һ�뵽��STATE_0
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
					// �ֲ��ػ�
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
		case STATE_8: {// ԭ��Ϊ�������������Ϊѡ��ͼƬ������δʵ��
//			this.editPanel.repaint();
			//String msg = editState + "״̬���󣬲��ܽ��� " + operatorID.getType();
			String msg = editState + "ZHUANG TAI CUO WU, BU NENG JIN XING " + operatorID.getType();
			Logger.logger.log(new Exception(msg));
			this.clearDFA();
			ListTable.fillcolor=false;
			break;
		}
		default:
//			this.editPanel.repaint();
			//String msg = editState + "״̬���󣬲��ܽ��� " + operatorID.getType();
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
	 * ������ѡ��������޸���ѡ���������ʼȫ������
	 */
	private void processLeftSelect() {
		ViewUnit unit0 = ListTable.slideWinIndexTable.get(imageIndex_0[0]).get(
		imageIndex_0[1]);
		leftSelectGlobalIndex = unit0.getGlobalIndex();
	}

	/**
	 * ������ѡ��������޸���ѡ���������ʼȫ������
	 */
	private void processRightSelect() {
		ViewUnit unit0 = ListTable.slideWinIndexTable.get(imageIndex_1[0]).get(
		imageIndex_1[1]);
		rightSelectGlobalIndex = unit0.getGlobalIndex();
	}

	/**
	 * �����Ʋ��������ݸ���������firstIndex��lastIndex����ɸ��Ʋ���
	 * 
	 * @param firstGlobalIndex����ʼ��Ԫ��������������ֵΪȫ������
	 * @param lastGlobalIndex��������Ԫ��������������ֵΪȫ������
	 */
	private void processCopy(int firstGlobalIndex, int lastGlobalIndex) {
		DataUnit memGlobalData;
//		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// �Ƚ�ճ�����е�ԭ�����������
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
	 * ����ճ�����������ݸ���������unitIndex�����ճ������
	 * 
	 * @param unitGlobalIndex��ճ����λ�õ�ȫ������
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
			// ��ճ�����е�������ӵ�������Ȼ����ʾ����
			for (int index = 0; index < myPastePanel.size(); index++) {
				copyData = myPastePanel.get(index);// ȡ��ճ�����е�����

				memGlobalData = copyData.clone();				
				ListTable.globalIndexTable.add(unitGlobalIndex + index,
					memGlobalData);// �洢������ͼƬ			
			}
			// ���ù��
		//	curCursorGlobalIndex = unitGlobalIndex + myPastePanel.size();
			ListTable.temppositionofaddcharunit = ListTable.temppositionofaddcharunit+myPastePanel.size();
		}
		this.leftSelectGlobalIndex = INVALID_UNIT_INDEX;
		this.rightSelectGlobalIndex = INVALID_UNIT_INDEX;
		myPastePanel.clear();
	}

	/**
	 * ���������������ݸ���������unitIndex����ɲ���
	 * 
	 * @param slideWinIndex������λ�õ�ȫ������,
	 * <br>slideWinIndex[0]��ʾ�ڼ����������ڣ�slideWinIndex[1]��ʾ�������ڵĵڼ�����Ԫ
	 */
	public void setInsertGlobalIndex(int[] slideWinIndex) {
		if (slideWinIndex == null || slideWinIndex.length != 2) {
			Logger.logger.log(new Exception("������������������"));
			return;
		}
		int globalIndex = -1;
		List<ViewUnit> list = ListTable.slideWinIndexTable
			.get(slideWinIndex[0]);
		int slideSize = list.size();
		// ���ȣ�ͨ������������������ȫ������
		if (slideWinIndex[1] >= 0 && slideWinIndex[1] < slideSize) {
				globalIndex = list.get(slideWinIndex[1]).getGlobalIndex();
		} else if (slideWinIndex[1] == slideSize) {
			globalIndex = list.get(slideSize - 1).getGlobalIndex() + 1;
		} else {
			Logger.logger.log(new Exception("������󣺲���λ�õĻ�������Ϊ" + slideSize
				+ "��������������СΪ��" + slideSize));
		}
		// ����
		if (globalIndex > ListTable.globalIndexTable.size()) {
			globalIndex = ListTable.globalIndexTable.size();
		} else if (globalIndex < 0) {
			globalIndex = 0;
		}
		// ��Σ��޸ĵ�ǰ�Ĺ������
		this.curCursorGlobalIndex = globalIndex;
//		this.editPanel.setCursorIndex(curCursorGlobalIndex);
		// ��󣺲������
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
	 * �趨ɾ���������õ����ݵ�Ԫ�Ļ�������������������ʼȫ�������ͽ���ȫ������
	 * 
	 * @param first[]�����������������������һ��Ԫ��Ϊɾ��������ʼ��Ԫ���������������ڶ���Ϊ������Ԫ������������
	 * @return��ɾ������������� <b>��ʼȫ������</b> �� <b>����ȫ������</b>
	 */
	private void setDeleteGlobalIndex(int[] first, int[] last) {
		if (null == first || 2 != first.length || null == last
			|| 2 != last.length) {
			Logger.logger.log(new Exception("��������ɾ�����Ļ���������������Ϊ��ʼ�ͽ�������"));
			return;
		}
		if ((first[1] == INVALID_UNIT_INDEX) || (last[1] == INVALID_UNIT_INDEX)
			|| (first[0] > last[1])) {// �ñ༭����λ����Ч��
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
	 * ����ɾ�����������ݸ���������firstIndex��lastIndex�����ɾ������
	 * 
	 * @param firstGlobalIndex��ɾ�����ݵ�Ԫ����ʼȫ������
	 * @param lastGlobalIndex��ɾ�����ݵ�Ԫ��ĩβȫ������
	 */
	private void processDelete(int firstGlobalIndex, int lastGlobalIndex) {
		int changeGlobalIndex;
		if (firstGlobalIndex < 0 || lastGlobalIndex < 0) {
			Logger.logger.log(new Exception("ɾ������������ʼ����Ϊ" + firstGlobalIndex
				+ "����������Ϊ" + lastGlobalIndex));
		} else if (firstGlobalIndex < ListTable.globalIndexTable.size()
			&& lastGlobalIndex < ListTable.globalIndexTable.size()) {
			for (int index = firstGlobalIndex; index <= lastGlobalIndex; index++) {
				// ��������ɾ��������
				ListTable.globalIndexTable.remove(firstGlobalIndex);
			}
			// ���ù��
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
	 * ������в��������в��������Ⱦ���ѡ���������ܽ���
	 */
	private void processCut() {
		int answer =0;
//		int answer = JOptionPane.showConfirmDialog(this.editPanel.getFrame(),
//			"ȷ��Ҫɾ�����������", "ȷ��", JOptionPane.YES_NO_OPTION,
//			JOptionPane.QUESTION_MESSAGE);
		if (answer == 0) {
			this.processCopy(this.leftSelectGlobalIndex,// �ȸ���
				this.rightSelectGlobalIndex);
			// ȡ��ѡ������ı���
	//		 this.undoSelectDomain(leftSelectGlobalIndex,
	//		 rightSelectGlobalIndex);
			this.editState = STATE_0;
			this.processDelete(leftSelectGlobalIndex,// Ȼ��ɾ��
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
	 * ����ո���������ݸ���������unitIndex���������
	 * 
	 * @param unitGlobalIndex������ո����ȫ������
	 */
	private void processSpace(int unitGlobalIndex) {
		ControlUnit memGlobalData;
		if (unitGlobalIndex >= 0
			&& unitGlobalIndex <= ListTable.globalIndexTable.size()) {
			memGlobalData = new ControlUnit(DataType.TYPE_CTRL_SPACE, curCharFormat);
	//		memGlobalData.setParaFormat(curParaFormat);
	//		ListTable.globalIndexTable.add(unitGlobalIndex, memGlobalData);// �洢������ͼƬ
			// ���ù��
	//		curCursorGlobalIndex = unitGlobalIndex + 1;
			ListTable.globalIndexTable.add(unitGlobalIndex,memGlobalData);
			ListTable.temppositionofaddcharunit++;
			// CompressPicture.Indexpno = ListTable.globalIndexTable.size();
//			TableSearcher.getInstance().resetchangeRangeGlobalIndex(
//				unitGlobalIndex, unitGlobalIndex + 1);
		}
	}

	/**
	 * �����˸���������ݸ���������unitIndex����ɻظ�
	 * 
	 * @param unitGlobalIndex���˸����������λ�õ�ȫ������
	 */
	private void processBackspace(int unitGlobalIndex) {

		numtitle=ListTable.globalTitle.size();//zhuxiaoqing 2011.09.28
		if (unitGlobalIndex > 0
			&& unitGlobalIndex <= ListTable.globalIndexTable.size()&&ListTable.temppositionofaddcharunit>numtitle+1) {
			ListTable.globalIndexTable.remove(unitGlobalIndex - 1);
			// ���ù��
	//		curCursorGlobalIndex = unitGlobalIndex - 1;
			ListTable.temppositionofaddcharunit = unitGlobalIndex - 1;

		}
	}

	/**
	 * ����س����������ݸ���������unitIndex����ɻ���
	 * 
	 * @param unitGlobalIndex���س�����������λ�õ�ȫ������
	 */
	public void processEnter(int unitGlobalIndex) {
		ControlUnit memGlobalData;
		if (unitGlobalIndex >= 0
			&& unitGlobalIndex <= ListTable.globalIndexTable.size()) {
			memGlobalData = new ControlUnit(DataType.TYPE_CTRL_ENTER, curCharFormat);
			ListTable.globalIndexTable.add(unitGlobalIndex, memGlobalData);// �洢������ͼƬ
//			int paraIndex = TableSearcher.getInstance().getParaNoForThisUnit(unitGlobalIndex);
//			ListTable.paragraphTable.add(paraIndex, new ParagraphUnit(unitGlobalIndex, curParaFormat));
			// ���ù��
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
	 * ͨ������ֵ���ҵ��������ڵ�ǰ���������е�ҳ��
	 * 
	 * @param x��x����
	 * @param y��y����
	 * @return��ҳ�ţ���û���ҵ��򷵻� -1
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
	 * �ҳ��༭�����õ�Ԫ�Ļ�������
	 * 
	 * @param points���༭���㼯
	 * @param id���༭������
	 * @param subscript���ɱ��βα༭���㼯���±�
	 * @return�����飺���༭�����õ�Ԫ���߼�ҳ�š��͡��߼�ҳ�еĵڼ�����Ԫ��
	 */
	
	public int[] findOperatorSlideIndex(List<MyPoint> points, OperatorType id, int... subscript) {
		if (null == points || 0 == points.size()) {
			Logger.logger.log(new Exception("�������㼯Ϊ��"));
			return null;
		}

		temppositionofaddcharunit=0;
		MyPoint first = points.get(0);// ��һ��
		MyPoint last = points.get(points.size() - 1);// ���һ��
		
		//�������if�ж�ֻ����0״̬�»�ɾ����ʱ��Ż����ִ�У���Ϊֻ���Ǹ�ʱ��subscript.length != 0
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
		
		
		
		// x��y�ǲ�����������
//		int tempx = first.getX();
//		int tempy = (first.getY() + last.getY()) / 2;
		Log.i("debug in find index ","cursor x and y is "+cursorlocatex+" "+cursorlocatey);
		int tempx = cursorlocatex;//2011.07.01 liqiang
		int tempy = cursorlocatey;//2011.07.01 liqiang
		cursloate(tempx,tempy);
  		return new int[] {0, temppositionofaddcharunit,tempx,tempy};
  		
	
	}
	
 public static void cursloate(int tempx,int tempy)									//�༭״̬�£�����ѡ��ʱ���궨λ
 {
	 	short baselineheight=  (short) (ListTable.charactersize+16);
		DataType PreUnitType=DataType.TYPE_CALLIGRAPHY;
		short screenwidth = Kview.screenwidth;
		short spacewidth = (short) ListTable.spacewidth;
		DataType datatype;
  		int tempwidth = 0,tempsum = 0,tempcursofy=baselineheight;
  		int locat =0;
  		int rowcount =1;
  	    int tempheight =0;								//������¼һ������Χ�ڵ�����ͼƬ��Ԫ����߸߶�
  	    int width =0;									//������¼һ����ͼƬ��Ԫ���ۼƿ��
  	    boolean firstimageunitperline = true;
		int size= ListTable.globalIndexTable.size();
		PreUnitType=DataType.TYPE_CALLIGRAPHY;	
  		Log.v("tempX--->",""+tempx);
  		Log.v("tempY--->",""+tempy);
  		
  		tempy +=Kview.maxscrollvice;					//�����Ϊ�˵õ��������ͼ������
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
				
				if((datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)&&PreUnitType==DataType.TYPE_IMAGE)			//�����������Ӧ����tempsum��0��
				{
					tempwidth = 0;
					tempsum = 0;			
					tempsum += dataunit.getWidth();
		  			tempsum += spacewidth;
		  			tempwidth+= dataunit.getWidth()/2.0;
				}
				if((PreUnitType == DataType.TYPE_CHAR||PreUnitType==DataType.TYPE_CTRL_SPACE)&&datatype==DataType.TYPE_IMAGE)			//�����������Ӧ����tempsum��0��
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
		
			if(tempsum>screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang ���ҳ�߾�֮����޸�
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
				
				//if(datatype!= DataType.TYPE_CONTROL)				//�����ڸ�ʲô��
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
					
					//����ͳ��ͬһ���ϵ�����ͼƬ��Ԫ����߸߶ȣ�ÿһ���Ͼ�ֻҪ�ڵ�һ��ͼƬ��Ԫ��ͳ�Ƽ��ɣ�����ͬһ���ϵĵ�Ԫ����Ҫͳ��
					
					while(tempdatatype==DataType.TYPE_IMAGE && templocat!=size&&width<screenwidth-ListTable.leftmargin-ListTable.rightmargin)//2011.07.14 liqiang ���ҳ�߾�֮����޸�
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
				else if(templocat==size && firstimageunitperline ==true)		//���һ����ԪΪͼƬ��Ԫ
					tempheight = dataunit.getHeight();
				
				height =tempheight;
			tempcursofy	= rowcount*baselineheight;
				


				//�ƺ��������ε�����ж�
  		//	if(tempwidth<tempx&&(tempcursofy-tempy)>=0)
  		//	{	
  		//		ListTable.temppositionofaddcharunit = locat+1;
  		//		Log.v("DEBUG---.","jue ding chu lai  ListTable.temppositionofaddcharunit   "+ListTable.temppositionofaddcharunit);	
  		//		
  		//		break;
  		//	}
  			
			
				
  		//	else if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)	
			if(tempwidth>tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)//�����Ҹ��ݽ���������ʾ��������ɵ�ȷ�����귽ʽ
  			{	

  				temppositionofaddcharunit = locat;
  	//			Log.i("DEBUG--->","^^^^^^^^^^^^^^   locat "+locat );
  	//			Log.i("DEBUG--->","^^^^^^^^^^^^^^^^^  height   "+height );
  	//			Log.i("DEBUG--->","^^^^^^^^^^^^^^   tempy-tempcursofy   "+tempy+"   "+tempcursofy );
  				break;		//��һ��ͼƬ��Ԫ�з������λ��
  			}
  			
  			else if(tempwidth<tempx&&(tempcursofy-tempy)<0&&(tempy-tempcursofy-height)<0)		//��ʾ�㵽ĳһ��������ͼƬ��Ԫ�������
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

  					if(sumvice/(screenwidth-ListTable.leftmargin-ListTable.rightmargin)>=1)//2011.07.14 liqiang ���ҳ�߾�֮����޸�
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
			temppositionofaddcharunit = locat+1;				//ֻҪ��û���ж��˳�֮ǰ��������Թ��λ�ý���ʵʱ��λ
		//	Log.i("DEBUG--->","tempy-tempcursofy  8888   "+tempy+"   "+tempcursofy );
			}
			
			else if(datatype == DataType.TYPE_CHAR||datatype==DataType.TYPE_CTRL_SPACE)			//��ͼƬ��Ԫ�·�������ַ���Ԫ
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
		  			rowcount++;				//������Ҫ����
		  			
		  			
		  			tempcursofy	= rowcount*baselineheight;
		  		//Log.i("DEBUG--->","rowcount  "+rowcount+"tempheight  "+tempheight );
		  			Log.i("DEBUG--->","rowcount "+rowcount);
				}

				 //����������if�ж���ʵ��������λCHARUNIT ��SPACEUNIT �ģ�����ͼƬ��Ԫ�Ķ�λ����ͼƬ��Ԫ�����ǿ�
					if(tempx>=tempwidth)	
	  				{
						
						tempwidth = tempsum;
						temppositionofaddcharunit = locat+1;
						
						DataUnit tempdata;
						DataType tempdatatype = null;
						if((locat+1)!=size)
						{
							tempdata =  ListTable.globalIndexTable.get(locat+1);
							tempdatatype= tempdata.getDataType();				//������¼ǰһ����Ԫ������
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
					else if((tempy-tempcursofy)<=0)						//˵��������Ҫ��ӵ�λ��
	  				{	
						temppositionofaddcharunit = locat;
						Log.i("DEBUG--->","tempcursofy  **********" );
						Log.i("DEBUG--->","tempy-tempcursofy   "+tempy+"--"+tempcursofy );
						break;
	  				}
					
					 temppositionofaddcharunit = locat+1;       //ֻҪ��û���ж��˳�֮ǰ��������Թ��λ�ý���ʵʱ��λ
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
			

			 PreUnitType = dataunit.getDataType();				//������¼ǰһ����Ԫ������
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
	 *TODO <b>��ѡ����������䱳����ɫ</b>
	 *
	 *@param leftIndex ������
	 *@param rightIndex ������
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
	 *TODO <b> ȡ��ѡ������ı�����ɫ </b>
	 *
	 *@param leftIndex ������
	 *@param rightIndex ������
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
	 *TODO ���������������������ֵ�Ԫ�������ʽ
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
//							"   �߶� �� " + cf.getHeight() + "   �ʻ���ϸ �� " + cf.getStrokeWidth());
					dataUnit.setCharFormat(cf);
				}
			}
			ListTable.changeStartGlobalIndex = this.leftSelectGlobalIndex;
//			this.editPanel.LocallyRepaint();
		}
	}
	
	/**
	 *TODO ������������,�Լ�����λ�����������ö����ʽ
	 *<br> �������������ö����ŵ���
	 *
	 *@param paraFormat
	 */
	
/*
	public void setParaFormat(ParagraphFormat paraFormat) {
		int paraSize = ListTable.paragraphTable.size();
		int end, nextPara;
		if (leftSelectGlobalIndex != INVALID_UNIT_INDEX
				&& rightSelectGlobalIndex != INVALID_UNIT_INDEX) {
			System.err.println("leftSelectGlobalIndex �� " + leftSelectGlobalIndex 
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
			System.err.println(" paraIndex : " + paraIndex + "  ��һ������ �� " 
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
			
			System.err.println("paraIndex : " + paraIndex + "  in �༭����");
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
		
//		System.err.println("\n �ڵ����� �� " + sectionFormat.getUpMargin());
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
	 *TODO ���õ�ǰ�Ķ����ʽ�����½��ε�Ԫ��ʱ����õ���
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
