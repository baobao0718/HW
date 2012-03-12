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
 * Handwritten Edit System Edit--写文件的临时方案 @ 梁乃臣
 * @version 1.00 07/10/03 08/01/14 修正保存文件时不能自动添加后缀的不足@武文博 08/01/29 增加后缀过滤功能 武文博
 * 
 ******************************************************************************/

public class HwDocument_Temp {
	/** 文件的绝对路径 */
	private String absFile;
	private String nameOfFile;// 文件名字
	private boolean isHavaChanged; // 标记内存中的全局索引是否被修改
	private boolean isHaveCorrespondingFile; // 标记当前全局索引是否有一个对应的文件在硬盘上，即以前是否被保存过
	private File abstractFile;
	//private HwEditPanel editPanelRef;// 指向EditPanel的引用，表示操作类HwEditExecuter作用在该Panel上

	private MemoryDecoder memoryDecoder;
	private DocumentDecoder documentDecoder;
	
	/**
	 * 构造函数
	 */
	public HwDocument_Temp() {
		isHavaChanged = false;
		isHaveCorrespondingFile = false;
		absFile = new File("").getAbsolutePath();
		nameOfFile = "未命名.hwep";
	//	editPanelRef = hwEditPanel;
	}

	/**
	 * 标记内存中的全局索引已被修改
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
	 * 将当前内存中全局索引中的数据写到指定的文件中
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
	 * TODO 从指定的文件 abstractFile 中读取数据到内存
	 * @param abstractFile
	 */
	//private void readDataFromFile(File abstractFile) {        //我 更改过,把形参由原来的File更改为了InputStream类型，结果跟没改前是一模一样的！！！
	private void readDataFromFile(InputStream  abstractFile) {
		BufferedInputStream inputStream;
		//InputStream ft 
		ListTable.sectionTable.clear();
		ListTable.globalIndexTable.clear();
		ListTable.paragraphTable.clear();
		
		try {
			//inputStream = new BufferedInputStream(new FileInputStream(		//我 更改过
			//	abstractFile));
			inputStream = new BufferedInputStream(
						abstractFile);
			System.err.println("wenjianchangdu:"+abstractFile.available());
			//GZIPInputStream gin = new GZIPInputStream(inputStream);//zhuxiaoqing 2011.06.13
			//documentDecoder = new DocumentDecoder(gin);
			documentDecoder = new DocumentDecoder(inputStream);
			documentDecoder.headDecoder();				//读取索引文件的头文件
			documentDecoder.dataDecoder();				//读取索引文件的数据文件

			//gin.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存文件
	 * @return: 保存文件是否成功
	 */
	public boolean saveDocument() {
		boolean isSaveSucc = true;
		if (true == isHavaChanged) {// 当前全局索引被修改过
			if (true == isHaveCorrespondingFile) {// 硬盘上已有一个文件对应到当前的全局索引
				abstractFile = new File(absFile);         //absFile 为文件的绝对路径，至此得到文件abstractFile 
				if (abstractFile.exists() == true) {
					if (true == abstractFile.delete()) {
						String str = nameOfFile.toLowerCase();
						if (str.endsWith(".hwep"))// 手写编辑文件
						{
							writeDataToFile(abstractFile);
							
						}
//						else if (str.endsWith(".pdf"))// pdf文件
//							new PDFSaver().save(abstractFile);
						/* 修改控制位 */
						isHaveCorrespondingFile = true;
						isHavaChanged = false;
					} else {
						System.err.println("删除文件 " + absFile
							+ "失败 in HwDocument_Temp.saveDocument()");
						isSaveSucc = false;
					}
				} else {
					System.err.println("文件 " + absFile
						+ " 不存在 in HwDocument_Temp.saveDocument()");
					isSaveSucc = false;
				}
			} else {// 还没有对应文件
				this.saveFile("保存文件");
			}
		} else {// 当前内存中的全局索引没有被修改过
			if (false == isHaveCorrespondingFile) {// 硬盘上没有一个文件对应到当前的全局索引
//				Log.v("DEBUG----","CONG ZHE LI LAI");
				abstractFile = new File(absFile); 
				this.saveFile("保存文件");
			} else {// 保存文件时，撤销了操作
				isSaveSucc = false;
			}
		}
		return isSaveSucc;
	}

	/**
	 * 另存文件
	 * 
	 * @return：true 保存文件是否成功，false 保存文件失败
	 */
	public boolean saveDocumentAs() {
		//*return this.saveFile("文件另存为");
		return true;
	}

	/**
	 * 保存文件
	 * @param title：文件保存对话框的标题
	 * @return：保存文件是否成功
	 */
/**** 把savefile 去掉了！！！！！ */
	
	private boolean saveFile(String title) {
		//JFileChooser fileChooser = new JFileChooser(".");              
		//fileChooser.setDialogTitle(title);
		
		boolean isSaveSucc = true;
		// 文件过滤器之hwe文件过滤器
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
				return "*.hwep（hwep手写编辑文档）";
			}
		};
		

		// 文件过滤器之pdf文件过滤器
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
				return "*.pdf（pdf文档）";
			}
		};
	
	
			//String ext = abstractFile.toString().toLowerCase();
			String ext = absFile;
			FileFilter ff = hweFilter;
			//*if (ff == hweFilter) {// 保存为手写编辑文件
			
	//			if (!ext.endsWith(".hwe")) {// 没有后缀，则自动添加后缀
	//				String ns = ext + ".hwe";
	//				abstractFile = new File(ns);
	//			}
			//*}
			//*	else if (ff == pdfFilter) {// 保存为pdf文档
			//*	if (!ext.endsWith(".pdf")) {// 没有后缀，则自动添加后缀
			//*		String ns = ext + ".pdf";
			//*		abstractFile = new File(ns);
			//*	}
			//*}
				
				//try {
				//	abstractFile.createNewFile();                                           //这一步是跟据已给路径创造新文件
				//} catch (IOException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
			//}  
			
		//	abstractFile = new File("/sdcard/new.hwe");
			
			abstractFile = new File(ext);
			if (abstractFile.exists() == true) {// 文件存在则提示是否覆盖已有文件
				int answer = 0;
				 //answer = JOptionPane.showConfirmDialog(editPanelRef.getFrame(), "文件 "
				//	+ abstractFile.getName() + "已经存在，要覆盖掉吗", "确认",
				//	JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (answer == 0) {
					if (true == abstractFile.delete()) {
						if (ff == hweFilter) {
							this.writeDataToFile(this.abstractFile);
						} else if (ff == pdfFilter) {
						//	new PDFSaver().save(this.abstractFile);
							;
						}
						/* 修改控制位 */
						isSaveSucc = true;
						isHaveCorrespondingFile = true;
						isHavaChanged = false;
						absFile = abstractFile.getAbsolutePath();
						nameOfFile = abstractFile.getName();
						//editPanelRef.resetEditFrameTitle(nameOfFile
						//	+ "   ---手写图像编辑系统");
					} else {
					//*	JOptionPane.showMessageDialog(editPanelRef.getFrame(), "覆盖失败",
					//*		"错误", JOptionPane.ERROR_MESSAGE);
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
				//*JOptionPane.showMessageDialog(editPanelRef.getFrame(), "保存文件成功", "保存文件成功",
				//*	JOptionPane.INFORMATION_MESSAGE);

				/* 修改控制位 */
				isHaveCorrespondingFile = true;
				isHavaChanged = false;
				absFile = abstractFile.getAbsolutePath();
				nameOfFile = abstractFile.getName();
				//*editPanelRef.resetEditFrameTitle(nameOfFile + "   ---手写图像编辑系统");
			}
		//*} else {// 保存文件时，撤销了操作
		//*	isSaveSucc = false;
		//}
		return isSaveSucc;
	}
	/***************************************************************************
	 * 新建文件
	 **************************************************************************/
	public void newDocument() {
		boolean isContinueFlag = true;

		if (true == isHavaChanged) {// 当前全局索引在内存中已经修改
			int answer = 0;
		//*	answer = JOptionPane.showConfirmDialog(editPanelRef.getFrame(), "文件--"
		//*		+ nameOfFile + " 已经修改，要保存吗？", "确认",
		//*		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (0 == answer) {
				isContinueFlag = saveDocument();
			} else if ((2 == answer) || (answer == -1)) {
				isContinueFlag = false;
			}
		}
		/* 前面一个文件如果修改了，必须保存成功后才能新建文件 */
		if (true == isContinueFlag) {
			/* 初始化 */
			absFile = new File("").getAbsolutePath();
			nameOfFile = "未命名";
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
			//*editPanelRef.resetEditFrameTitle(nameOfFile + "   ---手写图像编辑系统");
			//*manageTool.setSectionFormat(SectionFormat.getDefaultSectionformat());
			//*editPanelRef.setCursorIndex(0);
		}
	}

	/**
	 * 取得文件的属性
	 */
//*	public void getFileProperty() {
//*		JOptionPane.showMessageDialog(editPanelRef.getFrame(), "文件所在的位置："
//*			+ absFile, "属性", JOptionPane.INFORMATION_MESSAGE);
//*	}

	/**
	 * 退出程序
	 * @return: 是否退出程序
	 */
	public boolean program_exit() {
		boolean isContinueFlag = true;
		if (true == isHavaChanged) {// 当前全局索引在内存中已经修改
			int answer = 0;
		//	answer = JOptionPane.showConfirmDialog(editPanelRef.getFrame(), "文件--"
		//		+ nameOfFile + " 已经修改，要保存吗？", "确认",
		//		JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (0 == answer) {
				isContinueFlag = this.saveDocument();
			} else if ((2 == answer) || (answer == -1)) {
				isContinueFlag = false;
			}
		}
		/* 当前文件如果修改了，必须保存成功后才能退出程序 */
		if (true == isContinueFlag) {
			System.exit(0);
		}
		/* 结果为false表示，程序还不能结束 */
		return isContinueFlag;
	}
}