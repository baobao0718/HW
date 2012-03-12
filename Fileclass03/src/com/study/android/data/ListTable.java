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
 * �������ࡣ
 * ��¼ȫ����������ҳ�����������������������ɳ��þ�̬����
 * 
 */
public class ListTable {
	
	/** �������� */
	public static List<SectionUnit> sectionTable = new ArrayList<SectionUnit>();
	
	public static List<ParagraphUnit> paragraphTable = new ArrayList<ParagraphUnit>();
	
	/**  �������������� */
	public static List<List<ViewUnit>> slideWinIndexTable = new ArrayList<List<ViewUnit>>();

	/**  ȫ�������� */
	//public static List<DataUnit> globalIndexTable = new ArrayList<DataUnit>();
	public static ArrayList<DataUnit> globalIndexTable = new ArrayList<DataUnit>();
	public static boolean titleok=true;//zhuxiaoqing 2011.09.27
	/**
	 * ��¼������Activity
	 */
	public static ArrayList<Activity> activityTable = new ArrayList<Activity>();
	/**  �������� */
	public static List<RowIndex> rowGlobalIndexTable = new ArrayList<RowIndex>();
	/**  ҳ������ */
	public static List<PageIndex> pageGlobalIndexTable = new ArrayList<PageIndex>();

	/**  ͼ�����ʾ��񣬶�ռһ����(0)���������ֻ�����(1),��ʼ��Ϊ0 */
	public static int imageDisplayStyle = 0;

	/**  ���ȫ����������༭�������ı䣬���Ǳ��ı�ĵ�һ������/ͼ���ȫ����������,��ʼΪ-1 */
	public static int changeStartGlobalIndex = 0;
	
	/**  ���ȫ����������༭�������ı䣬���Ǳ��ı�����һ������/ͼ���ȫ����������,��ʼΪ-1 */
	public static int changeEndGlobalIndex = -1; 

	/**  �������ڿ�ʼҳ�� */
	public static int slideWindowStartPageNo=0; 
	
	/**�����ϣ���(̷ǿ�ּǵò�)*/
	public static int sizeOfLeftPageIndexTable=0;
	
	/**  ����зֺ�ĵ��������ֵ�Ԫ����ͼƬ��Ԫ */
//	public static List<Picpixel> SCbufferpix = new ArrayList<Picpixel>();
	
	/** ������������ĵ�Ԫ�����ͣ�0 Ϊ CharUnit��1 Ϊ ImageUnit,2 Ϊ���Ʒ�*/
	public static short TypeofUnit;
	
	public static int TypeofCtrl=-1;
	
	public static short TypeofGraph=-1;
	
	/** templocationofmouse ��������־��ʾ������ڵ�ȫ�ֱ����е�λ�ã��������ʱ������¶�λ����λ�ã������ػ�ͼʱ��ת��Ϊʵ�ʵ����ꡣtemppositionofaddcharunit ������־ ʵ�ʹ������λ�������ĵ�Ԫλ��*/
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
	public static int pngwidth=0;       //zhuxiaoqing 2011.11.01 �����ļ��Ŀ��
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
	
	public static boolean fillcolor = false;													//����ɫ��Ǳ༭����ʱ��ѡ�е�����
	
	
	/** �����ⲿ�������ڴ洢�ļ��������ṹ��*/
	public static ArrayList<DataUnit> globalTitle = new ArrayList<DataUnit>();					//�ڵ��д�������ʱ�򣬰ѵ�ǰglobalIndexTable ��ĵ�Ԫ����clone ȫ���������ýṹ
	
	public static boolean modify = false;
	
	public static int numoffile =0;
	
	public static ArrayList<String> createdfiletime = new ArrayList<String>();
	
	public static ArrayList<Integer> serianumberoffilelistofretrieve = new ArrayList<Integer>();				//����Ƕ�ȡ�����ļ��洢���������ļ��ı�ŵ�
	
	public static int serianumberoffilelistoffile =0;					//����������洢��־�ļ�ʱ���¼��ǰ�����ļ�����õ�
		
	public static ArrayList<Integer> addressinbodyofrecord= new ArrayList<Integer>();			//�������������������ʾ���Ǹ��µ�ÿһ����¼�ĵ�ַ
	
	public static boolean boolflag =false;								//
	
	public static boolean edittype = false;								//�����Ϊ������ת��WritebySurfaceViewʱ������ ���������ļ����͡��༭�Ѿ������ļ����� false ��ʾ���´�����
	
	public static int editserionumber = 0;								//���������������¼��ǰ���±༭���ļ�����ţ���
	
	public static BasePoint leftSelectGlobalIndexPoint =new BasePoint((short)0,(short)0);
	
	public static ArrayList<BasePoint> ArraySelectGlobal =new ArrayList<BasePoint>();
	
	public static BasePoint rightSelectGlobalIndexPoint =new BasePoint((short)0,(short)0);
	
	public static int selectedMargin[]= new int[2];
	
	
	
	
	/******************************************************************************************************/
	
	/******************************************************************************************************/
	
	public static Bitmap bitmap;
	
	public static Paint imagepaint=new Paint();
	
	public static ArrayList<Integer> globalcolorIndex = new ArrayList<Integer>();			//������¼����ͼƬ��Ԫ���ڲ����бʻ���ɫ
	
	public static short timesofoncreat =0;
	
	public static ArrayList<BasePoint> CopyofArray = new ArrayList<BasePoint>();
	
	public static float globalminX=1000,globalminY=1000,globalmaxX=0,globalmaxY=0;		//�⼸��ȫ�ֱ���ʱΪ��������¼�ڻ�ͼ״̬�µĸ���x��y�������Сֵ�ģ���Ϊ�ڻ�ͼ״̬���л���Ļ����Щ���ݻ�ȫ����ʧ������������¼���棬������CopyofArray������һ��
	
	public static float globalcopyofminX=1000,globalcopyofminY=1000,globalcopyofmaxX=0,globalcopyofmaxY=0;		
	
	public static short innerx=0,innery=0,copyofinnerx=0,copyofinnery=0;
	
	public static short baselineheightofinnertext=0,maxofinnery=0;
	
	public static float globalminXofinnertext=1000,globalminYofinnertext=1000,globalmaxXofinnertext=0,globalmaxYofinnertext=0;		
	
	public static int writespeed =0;												//���ڼ�¼��д�ٶ�
	
	public static int characterstrokewidth =0;										//���ֱʻ����
	
	public static int charactersize = 0	;											//���ִ�С
	
	public static int spacewidth =0;												//���ּ�ո��С
	
	public static int bgcolor =0;													//��ͼ����
	
	public static int charactercolor = 0;                                           //������ɫ
	
	public static int shuxiejizhun =0;												//��׼�߻����߶�
	
	public static float version =0;
	/**
	 * ����ͼƬid
	 */
	public static int backgroundimgid = -1;
	/**
	 * ��߾�  default:20
	 */
	public static int leftmargin = 20;                                              
	/**
	 * �ұ߾�  default:20
	 */
	public static int rightmargin = 20;                                             
	/**
	 * �ϱ߾�  default:20
	 */
	public static int topmargin = 20;                                               
	/**
	 * �±߾�  default:20
	 */
	public static int bottommargin = 20;                                            
	
	public static boolean isAutoSpace = true;										//Ĭ��Ϊtrue��Ĭ��ΪдӢ��״̬
	
	public static boolean configinfread = false;			    					//���ڼ�¼�Ƿ��ȡ��������Ϣ
	
	public static int isAutoSpaceValue =1;											//���isAutoSpace = true��isAutoSpaceValue =1;����isAutoSpaceValue =0;
	
	public static int isAutoSubmit = 0;												//���isAutoSubmit = 1,��Ϊ�Զ��ύ��isAutoSubmit = 0,��Ϊ�ֶ��ύ��Ĭ��Ϊ�ֶ��ύ
	
	public static int presentfilenum=-1;
	
	public static short SpaceUnitWidth=20;
	/******************************************************************************************************/
	
	/******************************************************************************************************/
	
}