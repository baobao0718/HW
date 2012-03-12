package com.study.android.data;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Paint;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataUnit;
import com.study.android.model.ParagraphUnit;
import com.study.android.model.SectionUnit;

/**
 * 索引表类。
 * 记录全局索引表、行页索引表、滑动窗口索引表及若干常用静态变量
 * 
 */
public class ListTable {
	
	/** 节索引表 */
	public static List<SectionUnit> sectionTable = new ArrayList<SectionUnit>();
	
	public static List<ParagraphUnit> paragraphTable = new ArrayList<ParagraphUnit>();
	
	/**  滑动窗口索引表 */
	public static List<List<ViewUnit>> slideWinIndexTable = new ArrayList<List<ViewUnit>>();

	/**  全局索引表 */
	//public static List<DataUnit> globalIndexTable = new ArrayList<DataUnit>();
	public static ArrayList<DataUnit> globalIndexTable = new ArrayList<DataUnit>();
	public static boolean titleok=true;//zhuxiaoqing 2011.09.27
	/**
	 * 记录启动的Activity
	 */
	public static ArrayList<Activity> activityTable = new ArrayList<Activity>();
	/**  行索引表 */
	public static List<RowIndex> rowGlobalIndexTable = new ArrayList<RowIndex>();
	/**  页索引表 */
	public static List<PageIndex> pageGlobalIndexTable = new ArrayList<PageIndex>();

	/**  图像的显示风格，独占一行型(0)或者是文字环绕型(1),初始化为0 */
	public static int imageDisplayStyle = 0;

	/**  如果全局索引表因编辑而发生改变，则标记被改变的第一个文字/图像的全局索引号码,初始为-1 */
	public static int changeStartGlobalIndex = 0;
	
	/**  如果全局索引表因编辑而发生改变，则标记被改变的最后一个文字/图像的全局索引号码,初始为-1 */
	public static int changeEndGlobalIndex = -1; 

	/**  滑动窗口开始页号 */
	public static int slideWindowStartPageNo=0; 
	
	/**已作废！！(谭强兄记得拆啊)*/
	public static int sizeOfLeftPageIndexTable=0;
	
	/**  存放切分后的单个的文字单元或者图片单元 */
//	public static List<Picpixel> SCbufferpix = new ArrayList<Picpixel>();
	
	/** 用来控制输入的单元的类型，0 为 CharUnit，1 为 ImageUnit,2 为控制符*/
	public static short TypeofUnit;
	
	public static int TypeofCtrl=-1;
	
	public static short TypeofGraph=-1;
	
	/** templocationofmouse 先用来标志显示光标所在的全局变量中的位置，单击鼠标时候会重新定位光标的位置，而在重绘图时候，转换为实际的坐标。temppositionofaddcharunit 用来标志 实际光标所在位置所处的单元位置*/
	public static int templocationofmouse = 0;				
	
	public static int temppositionofaddcharunit = 0;
	
	public static BasePoint cursor = new BasePoint((short)0,(short)0);
	
	public static int Sectionnum =0;
	
	public static boolean kviewdrawflag = true;
	
	public static int numTitle=0;//zhuxiaoqing 2011.06.07
	
	public static String filename="";//zhuxiaoqing 2011.08.21
	
	public static ArrayList<String> filebody = new ArrayList<String>();//zhuxiaoqing 2011.08.21
	
	
	public static String editfilename="";//zhuxiaoqing 2011.08.21
	public static Boolean isexport=false;//zhuxiaoqing 2011.11.01
	public static int pngwidth=0;       //zhuxiaoqing 2011.11.01 导出文件的宽度
	public static int datetmp=0;//zhuxiaoqing 2011.12.27
	
	public static int titlenumber=0;//zhuxiaoqing 2011.11.17
	public static boolean xiufu=false;
	public static boolean ipdfile=false;
	public static boolean isfirst=false;
	public static ArrayList<Integer> repairflag=new ArrayList<Integer>();//zhuxiaoqing 2011.11.17
	public static boolean isnew=false;
	public static ArrayList<Integer> titlenum=new ArrayList<Integer>();
	
	
	public static short ScreenWidth = 0;
	
	public static short ScreenHeight = 0;
	
	public static boolean fillcolor = false;													//用颜色标记编辑操作时候被选中的区域
	
	
	/** 下面这部分是用于存储文件的索引结构的*/
	public static ArrayList<DataUnit> globalTitle = new ArrayList<DataUnit>();					//在点击写标题完成时候，把当前globalIndexTable 里的单元利用clone 全部拷贝给该结构
	
	public static boolean modify = false;
	
	public static int numoffile =0;
	
	public static ArrayList<String> createdfiletime = new ArrayList<String>();
	
	public static ArrayList<Integer> serianumberoffilelistofretrieve = new ArrayList<Integer>();				//这个是读取索引文件存储当月所有文件的编号的
	
	public static int serianumberoffilelistoffile =0;					//这个是用来存储日志文件时候记录当前最大的文件编号用的
		
	public static ArrayList<Integer> addressinbodyofrecord= new ArrayList<Integer>();			//这个是用来跟踪索引显示的那个月的每一条记录的地址
	
	public static boolean boolflag =false;								//
	
	public static boolean edittype = false;								//这个是为了在跳转到WritebySurfaceView时候区分 “创建新文件”和“编辑已经存在文件”， false 表示“新创建”
	
	public static int editserionumber = 0;								//这个变量是用来记录当前重新编辑的文件的序号！！
	
	public static BasePoint leftSelectGlobalIndexPoint =new BasePoint((short)0,(short)0);
	
	public static ArrayList<BasePoint> ArraySelectGlobal =new ArrayList<BasePoint>();
	
	public static BasePoint rightSelectGlobalIndexPoint =new BasePoint((short)0,(short)0);
	
	public static int selectedMargin[]= new int[2];
	
	
	
	
	/******************************************************************************************************/
	
	/******************************************************************************************************/
	
	public static Bitmap bitmap;
	
	public static Paint imagepaint=new Paint();
	
	public static ArrayList<Integer> globalcolorIndex = new ArrayList<Integer>();			//用来记录单个图片单元的内部所有笔画颜色
	
	public static short timesofoncreat =0;
	
	public static ArrayList<BasePoint> CopyofArray = new ArrayList<BasePoint>();
	
	public static float globalminX=1000,globalminY=1000,globalmaxX=0,globalmaxY=0;		//这几个全局变量时为了用来记录在画图状态下的各个x、y的最大最小值的，因为在画图状态下切换屏幕，这些数据会全部丢失，所以用来记录保存，跟上面CopyofArray的作用一样
	
	public static float globalcopyofminX=1000,globalcopyofminY=1000,globalcopyofmaxX=0,globalcopyofmaxY=0;		
	
	public static short innerx=0,innery=0,copyofinnerx=0,copyofinnery=0;
	
	public static short baselineheightofinnertext=0,maxofinnery=0;
	
	public static float globalminXofinnertext=1000,globalminYofinnertext=1000,globalmaxXofinnertext=0,globalmaxYofinnertext=0;		
	
	public static int writespeed =0;												//用于记录书写速度
	
	public static int characterstrokewidth =0;										//文字笔画宽度
	
	public static int charactersize = 0	;											//文字大小
	
	public static int spacewidth =0;												//文字间空格大小
	
	public static int bgcolor =0;													//画图背景
	
	public static int charactercolor = 0;                                           //文字颜色
	
	public static int shuxiejizhun =0;												//基准线基本高度
	
	public static float version =0;
	/**
	 * 背景图片id
	 */
	public static int backgroundimgid = -1;
	/**
	 * 左边距  default:20
	 */
	public static int leftmargin = 20;                                              
	/**
	 * 右边距  default:20
	 */
	public static int rightmargin = 20;                                             
	/**
	 * 上边距  default:20
	 */
	public static int topmargin = 20;                                               
	/**
	 * 下边距  default:20
	 */
	public static int bottommargin = 20;                                            
	
	public static boolean isAutoSpace = true;										//默认为true，默认为写英文状态
	
	public static boolean configinfread = false;			    					//用于记录是否读取了配置信息
	
	public static int isAutoSpaceValue =1;											//如果isAutoSpace = true，isAutoSpaceValue =1;否则isAutoSpaceValue =0;
	
	public static int isAutoSubmit = 0;												//如果isAutoSubmit = 1,则为自动提交；isAutoSubmit = 0,则为手动提交。默认为手动提交
	
	public static int presentfilenum=-1;
	
	public static short SpaceUnitWidth=20;
	/******************************************************************************************************/
	
	/******************************************************************************************************/
	
}