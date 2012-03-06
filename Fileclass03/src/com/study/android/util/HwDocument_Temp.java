package com.study.android.util;

import android.util.Log;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.type.CharFormat;
import com.study.android.basicData.type.ControlUnit;
import com.study.android.code.DocumentDecoder;
import com.study.android.code.MemoryDecoder;
import com.study.android.data.ListTable;
import com.study.android.data.PageIndex;
import com.study.android.data.RowIndex;
import com.study.android.model.ParagraphFormat;
import com.study.android.model.ParagraphUnit;
import com.study.android.model.SectionFormat;
import com.study.android.model.SectionUnit;
//import com.study.android.tool.TableSearcher;
//import com.study.android.ui.HwEditPanel;
//import com.study.android.view.LayoutManager;

//*import hitsz.handwriting.util.PDFSaver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;

//import javax.swing.JFileChooser;
//import javax.swing.JOptionPane;
//import javax.swing.filechooser.FileFilter;

/*******************************************************************************
 * Handwritten Edit System Edit--д�ļ�����ʱ���� @ ���˳�
 * @version 1.00 07/10/03 08/01/14 ���������ļ�ʱ�����Զ���Ӻ�׺�Ĳ���@���Ĳ� 08/01/29 ���Ӻ�׺���˹��� ���Ĳ�
 * 
 ******************************************************************************/

public class HwDocument_Temp {
	/** �ļ��ľ���·�� */
	private String absFile;
	private String nameOfFile;// �ļ�����
	private boolean isHavaChanged; // ����ڴ��е�ȫ�������Ƿ��޸�
	private boolean isHaveCorrespondingFile; // ��ǵ�ǰȫ�������Ƿ���һ����Ӧ���ļ���Ӳ���ϣ�����ǰ�Ƿ񱻱����
	private File abstractFile;
	//private HwEditPanel editPanelRef;// ָ��EditPanel�����ã���ʾ������HwEditExecuter�����ڸ�Panel��

	private MemoryDecoder memoryDecoder;
	private DocumentDecoder documentDecoder;
	
	/**
	 * ���캯��
	 */
	public HwDocument_Temp() {
		isHavaChanged = false;
		isHaveCorrespondingFile = false;
		absFile = new File("").getAbsolutePath();
		nameOfFile = "δ����.hwep";
	//	editPanelRef = hwEditPanel;
	}

	/**
	 * ����ڴ��е�ȫ�������ѱ��޸�
	 */
	public void setHaveChangedFlag() {
		isHavaChanged = true;
	}
	public void setPath(String st)
	{
		this.absFile = st;
	}
	public String getPath()
	{
		return absFile;
	}

	/**
	 * ����ǰ�ڴ���ȫ�������е�����д��ָ�����ļ���
	 */
	private void writeDataToFile(File abstractFile) {
		
		//SectionFormat sectionFormat = ListTable.sectionTable.get(0).getSectionFormat();
		
//		System.err.println("width : " + sectionFormat.getPageWidth());
		
		BufferedOutputStream outputStream;
		try {
			outputStream = new BufferedOutputStream(
					new FileOutputStream(abstractFile));
			
			//GZIPOutputStream gzout = new GZIPOutputStream(outputStream);
			//memoryDecoder = new MemoryDecoder(gzout);
			memoryDecoder = new MemoryDecoder(outputStream);//zhuxiaoqing 
			//if(Typeofoper.equals("New"))
			//if(ListTable.isnew==true)
			
			memoryDecoder.headCoder();
			memoryDecoder.dataCoder();		
			
			//gzout.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * TODO ��ָ�����ļ� abstractFile �ж�ȡ���ݵ��ڴ�
	 * @param abstractFile
	 */
	//private void readDataFromFile(File abstractFile) {        //�� ���Ĺ�,���β���ԭ����File����Ϊ��InputStream���ͣ������û��ǰ��һģһ���ģ�����
	private void readDataFromFile(InputStream  abstractFile) {
		BufferedInputStream inputStream;
		//InputStream ft 
		ListTable.sectionTable.clear();
		ListTable.globalIndexTable.clear();
		ListTable.paragraphTable.clear();
		
		try {
			//inputStream = new BufferedInputStream(new FileInputStream(		//�� ���Ĺ�
			//	abstractFile));
			inputStream = new BufferedInputStream(
						abstractFile);
			System.err.println("wenjianchangdu:"+abstractFile.available());
			//GZIPInputStream gin = new GZIPInputStream(inputStream);//zhuxiaoqing 2011.06.13
			//documentDecoder = new DocumentDecoder(gin);
			documentDecoder = new DocumentDecoder(inputStream);
			documentDecoder.headDecoder();				//��ȡ�����ļ���ͷ�ļ�
			documentDecoder.dataDecoder();				//��ȡ�����ļ��������ļ�

			//gin.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ļ�
	 * @return: �����ļ��Ƿ�ɹ�
	 */
	public boolean saveDocument() {
		boolean isSaveSucc = true;
		if (true == isHavaChanged) {// ��ǰȫ���������޸Ĺ�
			if (true == isHaveCorrespondingFile) {// Ӳ��������һ���ļ���Ӧ����ǰ��ȫ������
				abstractFile = new File(absFile);         //absFile Ϊ�ļ��ľ���·�������˵õ��ļ�abstractFile 
				if (abstractFile.exists() == true) {
					if (true == abstractFile.delete()) {
						String str = nameOfFile.toLowerCase();
						if (str.endsWith(".hwep"))// ��д�༭�ļ�
						{
							writeDataToFile(abstractFile);
							
						}
//						else if (str.endsWith(".pdf"))// pdf�ļ�
//							new PDFSaver().save(abstractFile);
						/* �޸Ŀ���λ */
						isHaveCorrespondingFile = true;
						isHavaChanged = false;
					} else {
						System.err.println("ɾ���ļ� " + absFile
							+ "ʧ�� in HwDocument_Temp.saveDocument()");
						isSaveSucc = false;
					}
				} else {
					System.err.println("�ļ� " + absFile
						+ " ������ in HwDocument_Temp.saveDocument()");
					isSaveSucc = false;
				}
			} else {// ��û�ж�Ӧ�ļ�
				this.saveFile("�����ļ�");
			}
		} else {// ��ǰ�ڴ��е�ȫ������û�б��޸Ĺ�
			if (false == isHaveCorrespondingFile) {// Ӳ����û��һ���ļ���Ӧ����ǰ��ȫ������
//				Log.v("DEBUG----","CONG ZHE LI LAI");
				abstractFile = new File(absFile); 
				this.saveFile("�����ļ�");
			} else {// �����ļ�ʱ�������˲���
				isSaveSucc = false;
			}
		}
		return isSaveSucc;
	}

	/**
	 * ����ļ�
	 * 
	 * @return��true �����ļ��Ƿ�ɹ���false �����ļ�ʧ��
	 */
	public boolean saveDocumentAs() {
		//*return this.saveFile("�ļ����Ϊ");
		return true;
	}

	/**
	 * �����ļ�
	 * @param title���ļ�����Ի���ı���
	 * @return�������ļ��Ƿ�ɹ�
	 */
/**** ��savefile ȥ���ˣ��������� */
	
	private boolean saveFile(String title) {
		//JFileChooser fileChooser = new JFileChooser(".");              
		//fileChooser.setDialogTitle(title);
		
		boolean isSaveSucc = true;
		// �ļ�������֮hwe�ļ�������
		FileFilter hweFilter = new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.toString().toLowerCase().endsWith(".hwep")) {
					return true;
				} else {
					return false;
				}
			}

			public String getDescription() {
				// TODO Auto-generated method stub
				return "*.hwep��hwep��д�༭�ĵ���";
			}
		};
		

		// �ļ�������֮pdf�ļ�������
		FileFilter pdfFilter = new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.toString().toLowerCase().endsWith(".pdf")) {
					return true;
				} else {
					return false;
				}
			}

			public String getDescription() {
				// TODO Auto-generated method stub
				return "*.pdf��pdf�ĵ���";
			}
		};
	
	
			//String ext = abstractFile.toString().toLowerCase();
			String ext = absFile;
			FileFilter ff = hweFilter;
			//*if (ff == hweFilter) {// ����Ϊ��д�༭�ļ�
			
	//			if (!ext.endsWith(".hwe")) {// û�к�׺�����Զ���Ӻ�׺
	//				String ns = ext + ".hwe";
	//				abstractFile = new File(ns);
	//			}
			//*}
			//*	else if (ff == pdfFilter) {// ����Ϊpdf�ĵ�
			//*	if (!ext.endsWith(".pdf")) {// û�к�׺�����Զ���Ӻ�׺
			//*		String ns = ext + ".pdf";
			//*		abstractFile = new File(ns);
			//*	}
			//*}
				
				//try {
				//	abstractFile.createNewFile();                                           //��һ���Ǹ����Ѹ�·���������ļ�
				//} catch (IOException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
			//}  
			
		//	abstractFile = new File("/sdcard/new.hwe");
			
			abstractFile = new File(ext);
			if (abstractFile.exists() == true) {// �ļ���������ʾ�Ƿ񸲸������ļ�
				int answer = 0;
				 //answer = JOptionPane.showConfirmDialog(editPanelRef.getFrame(), "�ļ� "
				//	+ abstractFile.getName() + "�Ѿ����ڣ�Ҫ���ǵ���", "ȷ��",
				//	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == 0) {
					if (true == abstractFile.delete()) {
						if (ff == hweFilter) {
							this.writeDataToFile(this.abstractFile);
						} else if (ff == pdfFilter) {
						//	new PDFSaver().save(this.abstractFile);
							;
						}
						/* �޸Ŀ���λ */
						isSaveSucc = true;
						isHaveCorrespondingFile = true;
						isHavaChanged = false;
						absFile = abstractFile.getAbsolutePath();
						nameOfFile = abstractFile.getName();
						//editPanelRef.resetEditFrameTitle(nameOfFile
						//	+ "   ---��дͼ��༭ϵͳ");
					} else {
					//*	JOptionPane.showMessageDialog(editPanelRef.getFrame(), "����ʧ��",
					//*		"����", JOptionPane.ERROR_MESSAGE);
						isSaveSucc = false;
					}
				}
			} else {
				if (ff == hweFilter) {
					this.writeDataToFile(this.abstractFile);
				} else if (ff == pdfFilter) {
				//	new PDFSaver().save(this.abstractFile);
					;
				}
				//*JOptionPane.showMessageDialog(editPanelRef.getFrame(), "�����ļ��ɹ�", "�����ļ��ɹ�",
				//*	JOptionPane.INFORMATION_MESSAGE);

				/* �޸Ŀ���λ */
				isHaveCorrespondingFile = true;
				isHavaChanged = false;
				absFile = abstractFile.getAbsolutePath();
				nameOfFile = abstractFile.getName();
				//*editPanelRef.resetEditFrameTitle(nameOfFile + "   ---��дͼ��༭ϵͳ");
			}
		//*} else {// �����ļ�ʱ�������˲���
		//*	isSaveSucc = false;
		//}
		return isSaveSucc;
	}
	/***************************************************************************
	 * �½��ļ�
	 **************************************************************************/
	public void newDocument() {
		boolean isContinueFlag = true;

		if (true == isHavaChanged) {// ��ǰȫ���������ڴ����Ѿ��޸�
			int answer = 0;
		//*	answer = JOptionPane.showConfirmDialog(editPanelRef.getFrame(), "�ļ�--"
		//*		+ nameOfFile + " �Ѿ��޸ģ�Ҫ������", "ȷ��",
		//*		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (0 == answer) {
				isContinueFlag = saveDocument();
			} else if ((2 == answer) || (answer == -1)) {
				isContinueFlag = false;
			}
		}
		/* ǰ��һ���ļ�����޸��ˣ����뱣��ɹ�������½��ļ� */
		if (true == isContinueFlag) {
			/* ��ʼ�� */
			absFile = new File("").getAbsolutePath();
			nameOfFile = "δ����";
			isHaveCorrespondingFile = false;
			isHavaChanged = false;
			ListTable.globalIndexTable.clear();
			ListTable.paragraphTable.clear();
			ListTable.sectionTable.clear();
			ListTable.paragraphTable.clear();
			ListTable.pageGlobalIndexTable.clear();
			
			ListTable.globalIndexTable.add(new ControlUnit(DataType.TYPE_CTRL_ENTER,
					CharFormat.getDefaultCharFormat()));
			ListTable.sectionTable.add(SectionUnit.getDefaultSection());
			ListTable.paragraphTable.add(ParagraphUnit.getDefaultParagraphUnit());
			ListTable.rowGlobalIndexTable.add(new RowIndex(0, 
					SectionFormat.defaultUpMargin + ParagraphFormat.DefaultIntervalBeforePara));
			ListTable.pageGlobalIndexTable.add(new PageIndex(0, 0));
			
			//*TableSearcher.getInstance().resetchangeRangeGlobalIndex(0, 0);
			//*TableSearcher.getInstance().resetSlideWinStartPage(0);
			//*editPanelRef.setCursorIndex(0);
			//*editPanelRef.resetEditFrameTitle(nameOfFile + "   ---��дͼ��༭ϵͳ");
			//*manageTool.setSectionFormat(SectionFormat.getDefaultSectionformat());
			//*editPanelRef.setCursorIndex(0);
		}
	}

	/**
	 * ȡ���ļ�������
	 */
//*	public void getFileProperty() {
//*		JOptionPane.showMessageDialog(editPanelRef.getFrame(), "�ļ����ڵ�λ�ã�"
//*			+ absFile, "����", JOptionPane.INFORMATION_MESSAGE);
//*	}

	/**
	 * �˳�����
	 * @return: �Ƿ��˳�����
	 */
	public boolean program_exit() {
		boolean isContinueFlag = true;
		if (true == isHavaChanged) {// ��ǰȫ���������ڴ����Ѿ��޸�
			int answer = 0;
		//	answer = JOptionPane.showConfirmDialog(editPanelRef.getFrame(), "�ļ�--"
		//		+ nameOfFile + " �Ѿ��޸ģ�Ҫ������", "ȷ��",
		//		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (0 == answer) {
				isContinueFlag = this.saveDocument();
			} else if ((2 == answer) || (answer == -1)) {
				isContinueFlag = false;
			}
		}
		/* ��ǰ�ļ�����޸��ˣ����뱣��ɹ�������˳����� */
		if (true == isContinueFlag) {
			System.exit(0);
		}
		/* ���Ϊfalse��ʾ�����򻹲��ܽ��� */
		return isContinueFlag;
	}
}