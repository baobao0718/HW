package com.study.android.Category;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.PView;
import com.study.android.HWtrying.R;
import com.study.android.configure.Configure;
import com.study.android.configure.CreatePath;
import com.study.android.data.ListTable;
import com.study.android.help.Help;
import com.study.android.retrieval.ShowRetrieve;
import com.study.android.retrieval.Transform;


import dalvik.system.VMRuntime;

/*************************************************/
/**
 * 2011.02.16 ��ǿ ���ഴ���ļ� һ���������id����������button��λ�ã��ఴť����ɫ�ĸ����� ���ļ�����Ϣ������id.info��
 * ���δ洢�������ȣ���������id����λ�ã�����ɫ ��classnum.num�д洢�����Ŀ
 */

public class Fileclass extends Activity implements OnClickListener
{
	/** Called when the activity is first created. */
	// public Button button1;
	private Button createbutton; // create button
	private Button donebutton; // done button
	private Button editbutton;// editbutton 
	private Button configbutton; //config
	private Button helpbutton; //help
	private EditText classname; // ��������
	private TextView classnameview;
	private TextView backgroundview;
	private Spinner colorspinner;
	int[] classbackindex = { 1, 2, 3, 4 };
	int buttonid; // �ఴť��id
	/**
	 * ��ĸ���
	 */
	int fileclassnum;
	final int delaytime = 80;// ˢ���ӳ�ʱ��
	final double landratio = 31d / 15d;
	final double portratio = 53d / 21d;
	int editflag = 0;// ����Ƿ��ڱ༭״̬��Ϊ1��ʾ�༭
	boolean rotateflag = true;
	int moveflag = 0; // Ϊ0ʱ���û���ƶ���Ϊ1ʱ��ʾ����ƶ���
	int buttontextcolor = 0xffff5500;
	int classbgcolorindex;
	int deleteid;
	int deleteclassbuttonindex;
	public static Drawable portfileclassbackground, landfileclassbackground,
			createbuttonimage, donebuttonimage, editbuttonimage, configbuttonimage,
			helpbuttonimage, mobcentbuttonimage, nullimage = null;
	int screenwidth = 0, screenheight = 0;

	View layoutview;

	private Map<Integer,Map<String,ArrayList<Integer>>> classinfomap = new HashMap<Integer,Map<String,ArrayList<Integer>>>();
	/**
	 * 2011.10.25 ��¼down��upʱ�������㣬��������֮��ľ��룬����С��һ��ֵ��Ϊû���ƶ�
	 */
	int x1=0,x2=0,y1=0,y2=0;
	/**
	 * button �ƶ��ľ���
	 */
	int buttondistance = 6;
	Handler handler = new Handler();
	
	private String classnamestr[] = {"�ʼ�","�ռ�","����¼","���"};
	private int defaultclassid[] = {0,1,2,3};

	private void initDrawable()
	{
		portfileclassbackground = this.getResources().getDrawable(
				R.drawable.portbground);
		landfileclassbackground = this.getResources().getDrawable(
				R.drawable.landbground);
		createbuttonimage = this.getResources().getDrawable(
				R.drawable.createnewcategory);
		donebuttonimage = this.getResources().getDrawable(
				R.drawable.categorydone);
		editbuttonimage = this.getResources().getDrawable(R.drawable.bianji);
		configbuttonimage = this.getResources().getDrawable(R.drawable.shezhi);
		helpbuttonimage = this.getResources().getDrawable(R.drawable.help);
	}

	private void clearDrawable()
	{
		portfileclassbackground = nullimage;
		landfileclassbackground = nullimage;
		createbuttonimage = nullimage;
		donebuttonimage = nullimage;
		editbuttonimage = nullimage;
		configbuttonimage = nullimage;
		System.gc();//2011.12.01 liqiang
	}

	/**
	 * ���ؼ��ĳ�ʼ��
	 */
	public void findView()
	{
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{			
			screenheight = display.getWidth()>display.getHeight()?display.getWidth():display.getHeight(); 
			screenwidth = display.getWidth()<display.getHeight()?display.getWidth():display.getHeight();
			ListTable.ScreenWidth = (short) screenwidth;
			ListTable.ScreenHeight = (short) screenheight;
		}
		else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			screenheight = display.getWidth()<display.getHeight()?display.getWidth():display.getHeight(); 
			screenwidth = display.getWidth()>display.getHeight()?display.getWidth():display.getHeight();
			ListTable.ScreenWidth = (short) screenwidth;
			ListTable.ScreenHeight = (short) screenheight;
		}
//		Log.i("debug in find view of fileclass","width and height of view is "+ListTable.ScreenWidth+" "+ListTable.ScreenHeight);
		createbutton = (Button) findViewById(R.id.createclass);
		createbutton.setBackgroundDrawable(createbuttonimage);
		donebutton = (Button) findViewById(R.id.doneclass);
		donebutton.setBackgroundDrawable(donebuttonimage);
		editbutton = (Button) findViewById(R.id.editclass);
		editbutton.setBackgroundDrawable(editbuttonimage);
		configbutton = (Button)findViewById(R.id.configclassbutton);
		configbutton.setBackgroundDrawable(configbuttonimage);
		helpbutton = (Button)findViewById(R.id.helpbutton);
		helpbutton.setBackgroundDrawable(helpbuttonimage);
		classname = (EditText) findViewById(R.id.classname);
		colorspinner = (Spinner) findViewById(R.id.spinner);
		classnameview = (TextView) findViewById(R.id.textviewclassname);
		backgroundview = (TextView) findViewById(R.id.textviewbackground);

		classname.setVisibility(View.GONE);
		colorspinner.setVisibility(View.GONE);
		classnameview.setVisibility(View.GONE);
		backgroundview.setVisibility(View.GONE);
	}

	/**
	 * ������ı�����ɫ
	 */
	public void changeColor()
	{
		AddAdapter adaptertest = new AddAdapter(this);
		colorspinner.setAdapter(adaptertest);

		colorspinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
				{
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3)
					{// ��¼��ɫ��Ϣ��ˢ�����λ��
						classbgcolorindex = arg2;
						Log.i("debug in changecolor ","colorindex is "+classbgcolorindex);
//						handler.postDelayed(task, delaytime);
					}

					public void onNothingSelected(AdapterView<?> arg0)
					{
					}
				});
	}

	/**
	 * ��Ӽ�����
	 */
	public void addListener()
	{
		createbutton.setOnClickListener(this);
		donebutton.setOnClickListener(this);
		editbutton.setOnClickListener(this);
		configbutton.setOnClickListener(this);
		helpbutton.setOnClickListener(this);
	}

	private final static int CWJ_HEAP_SIZE = 6* 1024* 1024 ;//2011.12.01 liqiang
	private final static float TARGET_HEAP_UTILIZATION = 0.75f;//2011.12.01 liqiang
	private ClassAdapter classadapter;
	GridView gridview;
	private int deleteposition = -1;
	private String deleteclassname = "";
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//2011.12.01 liqiang
		VMRuntime.getRuntime().setTargetHeapUtilization(TARGET_HEAP_UTILIZATION);//2011.12.01 liqiang
		VMRuntime.getRuntime().setMinimumHeapSize(CWJ_HEAP_SIZE); //������Сheap�ڴ�Ϊ6MB��С
		setContentView(R.layout.category);
		clearDrawable();
		initDrawable();
		CreatePath.MakeConfigDir();
		this.getWindow().setBackgroundDrawable(portfileclassbackground);
		LayoutInflater inflater = LayoutInflater.from(this);
		layoutview = inflater.inflate(R.layout.category, null);
		this.getIntent().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
//		this.getIntent().setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		findView();
		changeColor();
		addListener();
		checkDefaultClassInfo();
		Log.i("debug in fileclass ", "oncreate !!!");
		// ������Ϣ�ļ����������ݽ���������ʾ����
		readClassFileInfo();
//		initPosition();
//		handler.postDelayed(task, delaytime);//2012.01.09 liqiang
		
		//ȡ��GridView����
		gridview = (GridView) findViewById(R.id.gridview);
		//���Ԫ�ظ�gridview
		classadapter = new ClassAdapter(this);
		gridview.setAdapter(classadapter);
		
		// ����Gallery�ı���
//		gridview.setBackgroundResource(R.drawable.portbground);

		//�¼�����
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				deleteposition = position;
				deleteclassname = classinfomap.get(position).keySet().iterator().next().trim();
				deleteid = classinfomap.get(position).get(deleteclassname).get(0);
				if(editflag == 1&&position>3)
				{//�༭��ɾ����:ɾ��classinfomap�е���Ϣ��ɾ�����ļ����޸�����Ŀ��id
					
					Dialog dialog = new AlertDialog.Builder(Fileclass.this).setTitle("��ʾ")
					.setMessage("ȷ��Ҫɾ����" + deleteclassname + "�����𣿣�").setPositiveButton(
							"ȷ��", new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO
									// Auto-generated
									// method stub
									// �ڶ��ξ���Ի���
									Dialog dialogin = new AlertDialog.Builder(
											Fileclass.this)
											.setTitle("��ʾ")
											.setMessage("ɾ�������ݽ��޷��ָ������ٴ�ȷ��Ҫɾ���𣿣�")
											.setPositiveButton(
													"ȷ��",
													new DialogInterface.OnClickListener()
													{
														@Override
														public void onClick(
																DialogInterface dialog,
																int which)
														{
															// TODO
															// Auto-generated
															// method
															// stub
															classinfomap.remove(deleteposition);
															Map<Integer,Map<String,ArrayList<Integer>>> tempmap = 
																new HashMap<Integer,Map<String,ArrayList<Integer>>>();
															Set<Integer> tempset = classinfomap.keySet();
															int tempposition = 0;
															ArrayList<Integer> templist = new ArrayList<Integer>();
															Iterator<Integer> it = tempset.iterator();
															while(it.hasNext())
															{
																tempposition = it.next();
																Log.i("debug in while 1","fuck 11!!"+tempposition+" "+deleteposition);
																if(tempposition>deleteposition)
																{
																	tempmap.put(tempposition-1, classinfomap.get(tempposition));
																	templist.add(tempposition);
//																	classinfomap.remove(tempposition);
																	String file = "/sdcard/eFinger/ClassInfo/"+tempposition+
																	".info";
																	try
																	{
																		deletefile(file);
																	} catch (FileNotFoundException e)
																	{
																		// TODO Auto-generated catch block
																		e.printStackTrace();
																	} catch (IOException e)
																	{
																		// TODO Auto-generated catch block
																		e.printStackTrace();
																	}
																	Log.i("debug in while 1","fuck 22!!");
//																	 break;
																}
															}
															for(int i = 0;i<templist.size();i++)
																classinfomap.remove(templist.get(i));
															Iterator<Integer> ittemp = tempmap.keySet().iterator();
															while(ittemp.hasNext())
															{
																Log.i("debug in while 2","fuck !!");
																int tempint = ittemp.next();
																classinfomap.put(tempint, tempmap.get(tempint));
																String classname = tempmap.get(tempint).keySet().iterator().next();
																int id = tempmap.get(tempint).get(classname).get(0);
																int color = tempmap.get(tempint).get(classname).get(1);
																writeClassFileInfo(classname, id, tempint, color);
															}
															
															showClassinfomap();
															try
															{
																String file = "/sdcard/eFinger/ClassInfo/"+deleteid+
																".info";
																deletefile(file);
																String file2 = "/sdcard/eFinger/"+deleteclassname;
																deletefile(file2);
																fileclassnum--;
																int classname[] = {fileclassnum,buttonid};
																writeClassNum(classname);
																
															} catch (FileNotFoundException e)
															{
																// TODO Auto-generated catch block
																e.printStackTrace();
															} catch (IOException e)
															{
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
															gridview.setAdapter(classadapter);
														}

														
													})
											.setNegativeButton(
													"ȡ��",
													new DialogInterface.OnClickListener()
													{

														@Override
														public void onClick(
																DialogInterface dialog,
																int which)
														{
															// TODO
															// Auto-generated
															// method
															// stub

														}
													}).create();
									dialogin.show();
								}
							}).setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener()
							{

								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									// TODO
									// Auto-generated
									// method stub

								}
							}).create();
			dialog.show();
				}
				else
				{//�������
					Map<String,ArrayList<Integer>> tempmap = classinfomap.get(position);
					String classname = tempmap.keySet().iterator().next().trim();
					Intent openBrowserIntent = new Intent(
							Fileclass.this, ShowRetrieve.class);
					Bundle bundleinbrowse = new Bundle();
					bundleinbrowse.putString("classname", classname);
					openBrowserIntent.putExtras(bundleinbrowse);
					startActivity(openBrowserIntent);
					clearDrawable();
				}
				
				
				Log.e("debug in onitemclick ","position is "+position+" "+deleteclassname);
//				Toast.makeText(Fileclass.this, "��ѡ����" + (position + 1) + " ��ͼƬ", Toast.LENGTH_SHORT).show();
			}
		});
	}
	private void showClassinfomap()
	{
		// TODO Auto-generated method stub
		Iterator<Integer> it = classinfomap.keySet().iterator();
		while(it.hasNext())
		{
			int i = (Integer) it.next();
			String str = classinfomap.get(i).keySet().iterator().next().trim();
			Log.e("fileclassinfo",""+i+" "+str
					+" "+classinfomap.get(i).get(str).get(0)
					+" "+classinfomap.get(i).get(str).get(1));
		}
	}
	public class ClassAdapter extends BaseAdapter
	{
		ImageView imageView;
		TextView tv;
		
		// ����Context
		@SuppressWarnings("unused")
		private Context		mContext;
		
		public TextView getTextView()
		{
			return tv;
		}
		public ClassAdapter(Context c)
		{
			mContext = c;
		}

		// ��ȡͼƬ�ĸ���
		public int getCount()
		{
			return fileclassnum;
		}

		// ��ȡͼƬ�ڿ��е�λ��
		public Object getItem(int position)
		{
			return position;
		}


		// ��ȡͼƬID
		public long getItemId(int position)
		{
			return position;
		}


		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view;
			ViewHolder viewholder = null;
			if (convertView == null)
			{
				view = getLayoutInflater().inflate(R.layout.categoryitem, null);
				
				viewholder = new ViewHolder();//
				viewholder.label = (TextView)view.findViewById(R.id.icon_text);//
				    
//	            tv = (TextView)view.findViewById(R.id.icon_text);
	            Map<String, ArrayList<Integer>> tempmap = classinfomap.get(position);
	            if(tempmap!=null)
	            {
	            	Set<String> tempset = tempmap.keySet();
	            	String classname = tempset.iterator().next();
//	            	tv.setText(classname.trim()); 
	            	
	            	viewholder.label.setText(classname.trim());///
//	            	viewholder.label.set
//	            	viewholder.label.setText("#ffcc00");
//	            	viewholder.label.setText(Color.argb(1, 1, 1, 0));
//	            	viewholder.label.setTextColor(Color.rgb(255, 230, 0));
	            	viewholder.label.setTextColor(Color.rgb(0, 0, 255));
	            	viewholder.classlabel = (ImageView)view.findViewById(R.id.icon_image);//
	            	viewholder.classlabel.setScaleType(ImageView.ScaleType.FIT_CENTER);//
//	            	Log.e("classlabel ","viewholder.classlabel.getWidth() "+viewholder.classlabel.getWidth()+" "+viewholder.classlabel.getHeight());
	            	
//	            	imageView = (ImageView)view.findViewById(R.id.icon_image); 
//	            	imageView.setLayoutParams(new ViewGroup.LayoutParams(40, 60));
//		            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
	            	ArrayList<Integer> temparraylist = tempmap.get(classname);
	            	int tempindex = temparraylist.get(1);
	            	
	            	Log.e("getview position name color id ",""+fileclassnum+" "+position+" "+classname+" "+tempindex+" "+temparraylist.get(0));
	            	if(editflag==0)
	            	{
	            	switch(tempindex%4)
	            	{
	            		case 0:
	            			viewholder.classlabel.setImageResource(R.drawable.color1);
//	            			imageView.setImageResource(R.drawable.color1);
	            			break;
	            		case 1:
	            			viewholder.classlabel.setImageResource(R.drawable.color2);
//	            			imageView.setImageResource(R.drawable.color2);
	            			break;
	            		case 2:
	            			viewholder.classlabel.setImageResource(R.drawable.color3);
//	            			imageView.setImageResource(R.drawable.color3);
	            			break;
	            		case 3:
	            			viewholder.classlabel.setImageResource(R.drawable.color4);
//	            			imageView.setImageResource(R.drawable.color4);
	            			break;
	            		default:
	            			viewholder.classlabel.setImageResource(R.drawable.color1);
//	            			imageView.setImageResource(R.drawable.color1);
	            			break;
	            	};
	            	}
	            	else
	            	{//�༭��Ĭ�ϵ��ĸ��಻�ܱ༭
	            		if(position>3)
	            		{
	            		switch(tempindex%4)
		            	{
		            		case 0:
		            			viewholder.classlabel.setImageResource(R.drawable.color1edit);
//		            			imageView.setImageResource(R.drawable.color1edit);
		            			break;
		            		case 1:
		            			viewholder.classlabel.setImageResource(R.drawable.color2edit);
//		            			imageView.setImageResource(R.drawable.color2edit);
		            			break;
		            		case 2:
		            			viewholder.classlabel.setImageResource(R.drawable.color3edit);
//		            			imageView.setImageResource(R.drawable.color3edit);
		            			break;
		            		case 3:
		            			viewholder.classlabel.setImageResource(R.drawable.color4edit);
//		            			imageView.setImageResource(R.drawable.color4edit);
		            			break;
		            		default:
		            			viewholder.classlabel.setImageResource(R.drawable.color1edit);
//		            			imageView.setImageResource(R.drawable.color1edit);
		            			break;
		            	};
	            		}
		            	else
		            	{
		            		switch(tempindex%4)
			            	{
			            		case 0:
			            			viewholder.classlabel.setImageResource(R.drawable.color1);
//			            			imageView.setImageResource(R.drawable.color1);
			            			break;
			            		case 1:
			            			viewholder.classlabel.setImageResource(R.drawable.color2);
//			            			imageView.setImageResource(R.drawable.color2);
			            			break;
			            		case 2:
			            			viewholder.classlabel.setImageResource(R.drawable.color3);
//			            			imageView.setImageResource(R.drawable.color3);
			            			break;
			            		case 3:
			            			viewholder.classlabel.setImageResource(R.drawable.color4);
//			            			imageView.setImageResource(R.drawable.color4);
			            			break;
			            		default:
			            			viewholder.classlabel.setImageResource(R.drawable.color1);
//			            			imageView.setImageResource(R.drawable.color1);
			            			break;
			            	};
		            	}
	            	}		             
	            }
	            view.setTag(viewholder);
			}
			else
			{
				view = convertView;
			}
			return view;
		}
		private  class ViewHolder {
			TextView label;
			ImageView classlabel;
			}
	}
	/**
	 * Ĭ����ĸ���
	 */
	int defaultclassnum =4;
	private void checkDefaultClassInfo()
	{
		// TODO Auto-generated method stub
		String strfilepath = "/sdcard/eFinger/ClassInfo";
		File filepath = new File(strfilepath);
		if (filepath.exists() == false)// ���ļ��в������򴴽���
		{
			filepath.mkdirs();
		
		int i = 0;
		
		while(i<defaultclassnum)
		{
			Log.e("","create default class !!");
			String defaultclassfilepath = strfilepath+"/default"+i+".info";
			File tempfile = new File(defaultclassfilepath);
			int tempposition[] = {0,1,2,3};
			if(tempfile.exists() == false)
			{
				
				// ������Ϣд���ļ���ע��д��˳���������ȡ���������id��λ�á�������ɫ
				writeDefaultClassInfo(tempfile,classnamestr[i],defaultclassid[i],
						tempposition[i],i);
			}
			i++;
		}
		int [] classnum = {defaultclassnum,defaultclassnum};
		writeClassNum(classnum);
		}
	}

	private void writeDefaultClassInfo(File file, String string,
			int buttonid, int position, int color)
	{
		// TODO Auto-generated method stub
		byte classfilename[] = new byte[30]; // ***
		classfilename = string.getBytes();
		int classnamesize = classfilename.length;
		byte classfilenamelength[] = new byte[4];
		classfilenamelength = Transform.InttoBytes(classnamesize);
		byte classid[] = new byte[4];
		classid = Transform.InttoBytes(buttonid);
		byte classposition [] = new byte[4];
		classposition = Transform.InttoBytes(position);		
		byte colorbyte[] = new byte[4];
		colorbyte = Transform.InttoBytes(color);
		
		BufferedOutputStream outputstream = null;
		try
		{
			outputstream = new BufferedOutputStream(new FileOutputStream(
					file));

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			// ������Ϣд���ļ���ע��д��˳���������ȡ���������id��λ�á�������ɫ

			outputstream.write(classfilenamelength);
			outputstream.write(classfilename);
			outputstream.write(classid);
			outputstream.write(classposition);
			outputstream.write(colorbyte);
			outputstream.flush();
			outputstream.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
//		Log.i("debug", "rotate is happen !");
		setContentView(R.layout.category);
		// setContentView(layoutview);
		findView();

		changeColor();
		addListener();

		short tempwidth = 0, tempheight = 0;

		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
		{
			tempwidth = Kview.screenwidth > Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			tempheight = Kview.screenwidth < Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			Kview.screenwidth = tempwidth;
			Kview.screenheight = tempheight;

			tempwidth = 0;
			tempheight = 0;

			// PView���ڶ�ȡ��Kview����д��
			tempwidth = PView.screenwidth > PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			tempheight = PView.screenwidth < PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			PView.screenwidth = tempwidth;
			PView.screenheight = tempheight;
			this.getWindow().setBackgroundDrawable(nullimage);
			this.getWindow().setBackgroundDrawable(landfileclassbackground);

		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			tempwidth = Kview.screenwidth < Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			tempheight = Kview.screenwidth > Kview.screenheight ? Kview.screenwidth
					: Kview.screenheight;
			Kview.screenwidth = tempwidth;
			Kview.screenheight = tempheight;

			tempwidth = 0;
			tempheight = 0;

			tempwidth = PView.screenwidth < PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			tempheight = PView.screenwidth > PView.screenheight ? PView.screenwidth
					: PView.screenheight;
			PView.screenwidth = tempwidth;
			PView.screenheight = tempheight;
			this.getWindow().setBackgroundDrawable(nullimage);
			this.getWindow().setBackgroundDrawable(portfileclassbackground);
		}
	}

	/**
	 * ��ȡ�����Ŀ�����һ�����id
	 * 
	 * @return ��һ��������Ŀ���ڶ�����id
	 */
	public int[] readClassNum()
	{
		String classnumpath = "/sdcard/eFinger/ClassInfo/classnum.num";
		File classnumfile = new File(classnumpath);
		BufferedInputStream inputstream = null;
		try
		{
			inputstream = new BufferedInputStream(new FileInputStream(
					classnumfile));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte byteclassnum[] = new byte[4];
		byte bytelastclassid[] = new byte[4];
		try
		{
			inputstream.read(byteclassnum);
			inputstream.read(bytelastclassid);
			inputstream.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] classnuminfo = new int[2];
		classnuminfo[0] = Transform.BytestoInt(byteclassnum);
		classnuminfo[1] = Transform.BytestoInt(bytelastclassid);
		return classnuminfo;
	}


	public  boolean deletefile(String delpath)
			throws FileNotFoundException, IOException
	{
		try
		{
			File file = new File(delpath);
			if (!file.isDirectory())
			{
				file.delete();
			} else if (file.isDirectory())
			{
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++)
				{
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory())
						delfile.delete();
					else if (delfile.isDirectory())
						deletefile(delpath + "\\" + filelist[i]);
				}
				file.delete();
			}
		} catch (FileNotFoundException e)
		{
			Log.i("deletefile()   Exception: " + e.getMessage(), "");
		}
		return true;
	}
	/**
	 * ������Ϣ�ļ����������ݽ���������ʾ����
	 */
	public void readClassFileInfo()
	{

		String strfilepath = "/sdcard/eFinger/ClassInfo";
		File filepath = new File(strfilepath + "/classnum.num");
		if (filepath.exists() == false)// ��һ�δ������û�м�¼���������ļ�ʱ
		{
			buttonid = 0;
			return;
		}
		int classnuminfo[] = readClassNum();
		int classnum = classnuminfo[0];// �����Ŀ
		fileclassnum = classnum;
		buttonid = classnuminfo[1];// 
		Log.i("debug in readclassfileinfo", "classnum and buttonid is "
				+ classnum + " " + buttonid);
		int fileid = 0,defaultid = 0;
		while (classnum > 0)
		{
			File classinfofile;
			
			if(defaultid<defaultclassnum)//ǰdefaultclassnum��ѭ����ȡĬ�������Ϣ
			{
				classinfofile = new File(strfilepath + "/default" + defaultid + ".info");
				defaultid++;
			}
			else
			{	
				fileid++;
				classinfofile = new File(strfilepath + "/" + fileid + ".info");
			}
			if(fileid>1000)
				break;
			Log.i("debug in fileclass readclassfile ","classinfofile is "+classinfofile);
			BufferedInputStream inputstream = null;
			if (classinfofile.exists())
			{
				classnum--;

			try
			{
				inputstream = new BufferedInputStream(new FileInputStream(
						classinfofile));
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				continue;
			}
			try
			{
				// ��ȡ����Ϣ�ļ�
				int classnamesize = 0;
				String strfilename = "";
				int intclassid = 0, position = 0, classcolor = 0;
				byte classfilenamelength[] = new byte[4];

				byte classid[] = new byte[4];
				byte classposition[] = new byte[4];
				byte classbgcolor[] = new byte[4];

				// ���ζ�ȡ�������ȡ���������id��λ�á��౳��ɫ
				inputstream.read(classfilenamelength);
				classnamesize = Transform.BytestoInt(classfilenamelength);
				byte classfilename[] = new byte[classnamesize];
				inputstream.read(classfilename, 0, classnamesize);
				inputstream.read(classid);
				inputstream.read(classposition);
				inputstream.read(classbgcolor);
				inputstream.close();

				strfilename = new String(classfilename);
				intclassid = Transform.BytestoInt(classid);
				position = Transform.BytestoInt(classposition);
				classcolor = Transform.BytestoInt(classbgcolor);
				
				ArrayList<Integer> templist = new ArrayList<Integer>();
				templist.add(intclassid);
				templist.add(classcolor);
				Map<String,ArrayList<Integer>> tempmap = new HashMap<String,ArrayList<Integer>>();
				tempmap.put(strfilename, templist);
				classinfomap.put(position, tempmap);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
		showClassinfomap();
	}

	/**
	 * ��¼����ܸ����͵�һ�����õ�id
	 * 
	 * @param classinfo
	 */
	synchronized public void writeClassNum(int[] classinfo)
	{
		String strpath = "/sdcard/eFinger";
		File eFingerfile = new File(strpath);
		if (!eFingerfile.exists())
			eFingerfile.mkdirs();
		String strclassinfopath = "/sdcard/eFinger/ClassInfo";
		File classinfofile = new File(strclassinfopath);
		if (!classinfofile.exists())
			classinfofile.mkdirs();
		String strclassnumpath = "/sdcard/eFinger/ClassInfo/classnum.num";
		File classnumfile = new File(strclassnumpath);

		BufferedOutputStream outputnumstream = null;
		try
		{
			outputnumstream = new BufferedOutputStream(new FileOutputStream(
					classnumfile));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte byteclassnum[] = new byte[4];
		byteclassnum = Transform.InttoBytes(classinfo[0]);
		byte bytelastclassid[] = new byte[4];
		bytelastclassid = Transform.InttoBytes(classinfo[1]);
		try
		{
			outputnumstream.write(byteclassnum);
			outputnumstream.write(bytelastclassid);
			outputnumstream.flush();
			outputnumstream.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ������Ϣд������Ϣ�ļ�
	 * @param name ����
	 * @param buttonid ��id
	 * @param position ��λ��
	 * @param color �౳����ɫ
	 */
	synchronized public void writeClassFileInfo(String name, int buttonid,int position,int color)
	{
		byte classfilename[] = new byte[30]; // ***
		classfilename = name.getBytes();
		int classnamesize = classfilename.length;
		byte classfilenamelength[] = new byte[4];
		classfilenamelength = Transform.InttoBytes(classnamesize);
		byte classid[] = new byte[4];
		classid = Transform.InttoBytes(buttonid);
		byte byteposition[] = new byte[4];
		byteposition = Transform.InttoBytes(position);
		byte colorbyte[] = new byte[4];
		colorbyte = Transform.InttoBytes(color);


		// �½���¼����Ϣ���ļ�
		String strfilepath = "/sdcard/eFinger/ClassInfo";
		File filepath = new File(strfilepath);
		if (filepath.exists() == false)// ���ļ��в������򴴽���
			filepath.mkdirs();

		File classinfofile = new File(strfilepath + "/" + buttonid + ".info");
		BufferedOutputStream outputstream = null;
		try
		{
			outputstream = new BufferedOutputStream(new FileOutputStream(
					classinfofile));

		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try
		{
			// ������Ϣд���ļ���ע��д��˳���������ȡ���������id��λ�á�������ɫ

			outputstream.write(classfilenamelength);
			outputstream.write(classfilename);
			outputstream.write(classid);
			outputstream.write(byteposition);
			outputstream.write(colorbyte);
			outputstream.flush();
			outputstream.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �������ǩ�ı���ɫ
	 * 
	 * @param btn
	 *            Ҫ���õ����ǩ
	 * @param classbgcolorindex
	 *            ������������
	 */
	public void setClassBGColor(Button btn, int classbgcolorindex)
	{
		if (editflag == 1)// ���ñ༭ʱ�ı���ɫ
			switch (classbgcolorindex)
			{
				case 0:
					btn.setBackgroundResource(R.drawable.color1edit);
					break;
				case 1:
					btn.setBackgroundResource(R.drawable.color2edit);
					break;
				case 2:
					btn.setBackgroundResource(R.drawable.color3edit);
					break;
				case 3:
					btn.setBackgroundResource(R.drawable.color4edit);
					break;
			}
		else
			// ���÷Ǳ༭״̬�ı���ɫ
			switch (classbgcolorindex)
			{
				case 0:
					btn.setBackgroundResource(R.drawable.color1);
					break;
				case 1:
					btn.setBackgroundResource(R.drawable.color2);
					break;
				case 2:
					btn.setBackgroundResource(R.drawable.color3);
					break;
				case 3:
					btn.setBackgroundResource(R.drawable.color4);
					break;
			}
	}

	/**
	 * �ж��Ƿ�����
	 * 
	 * @param name
	 *            Ҫ�жϵ�����
	 * @return ��������true�����򷵻�false��
	 */
	public boolean classNameIsExist(String name)
	{
		Set<Integer> tempset = classinfomap.keySet();
		Iterator<Integer> positionit = tempset.iterator();
		while(positionit.hasNext())
		{
			if(classinfomap.get(positionit.next()).keySet().iterator().next()
					.trim().equals(name.trim()))
				return true;
		}
		return false;
	}

	/**
	 * �༭ʱҪ����ͼ��
	 */
//	public void changeicon()
//	{
//		Set<Integer> idset = fileclassmap.keySet();
//		Iterator<Integer> idit = idset.iterator();
//		while (idit.hasNext())
//		{
//			Integer id = idit.next();
//			int tempid = id;
//			if(tempid==65535||tempid==65534||tempid==65533||tempid==65532)
//				continue;
//			else
//			{
//			Button tempbtn = (Button) findViewById(id);
//			Map<String, ArrayList<Integer>> tempmap = new HashMap<String, ArrayList<Integer>>();
//			tempmap = fileclassmap.get(id);
//			Set<String> strset = tempmap.keySet();
//			Iterator<String> strit = strset.iterator();
//			String tempstr = strit.next();
//			ArrayList<Integer> array = tempmap.get(tempstr);
//			int color = array.get(4);
//			setClassBGColor(tempbtn, color);
//			}
//		}
//
//	}

	@Override
	public void onClick(View v)
	{

		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.helpbutton:
	  			Intent help = new Intent(Fileclass.this,Help.class);        //����ҳ���� 
	  			startActivity(help);
	  			clearDrawable();
				break;
			case R.id.configclassbutton:
				Intent shezhi = new Intent(Fileclass.this,Configure.class);        //����ҳ����
	  			startActivity(shezhi);
	  			clearDrawable();
//	  			finish();
				break;
			case R.id.createclass:// create button
				classname.setVisibility(View.VISIBLE);
				colorspinner.setVisibility(View.VISIBLE);
				classnameview.setVisibility(View.VISIBLE);
				backgroundview.setVisibility(View.VISIBLE);
				break;
			case R.id.editclass:// edit button
				editflag++;
				editflag = editflag % 2;
				if (editflag == 1)// Ϊ1 ʱ��ʾ���ڱ༭״̬
					editbutton.setBackgroundResource(R.drawable.bianji1);
				else
					editbutton.setBackgroundResource(R.drawable.bianji);
				gridview.setAdapter(classadapter);
//				changeicon();
//				Log.i("debug", "call changeicon");
				break;
			case R.id.doneclass:// done button
				String name = classname.getText().toString();
				name = name.trim();
				classname.setText("");
				classname.setVisibility(View.GONE);
				colorspinner.setVisibility(View.GONE);
				classnameview.setVisibility(View.GONE);
				backgroundview.setVisibility(View.GONE);
				if (classNameIsExist(name))
				{// ����������ͬ
//					Log.i("the class ", name + " has exist !");
					Dialog dialog = new AlertDialog.Builder(Fileclass.this)
							.setTitle("��ʾ").setMessage(
									"�� " + name + " �Ѿ����� !")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener()
									{

										@Override
										public void onClick(
												DialogInterface dialog,
												int which)
										{
											// TODO Auto-generated method stub

										}
									}).create();
					dialog.show();
				} else
				{
					if (name.length() == 0)
					{
					} else
					{
						fileclassnum++;
						if(fileclassnum==13)
						{
							Dialog dialog = new AlertDialog.Builder(Fileclass.this)
							.setTitle("��ʾ").setMessage(
									"���Ѿ�������12�����ˣ��ǲ���Ҫɾ��һЩ�أ�")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener()
									{

										@Override
										public void onClick(
												DialogInterface dialog,
												int which)
										{
											// TODO Auto-generated method stub

										}
									}).create();
							dialog.show();
							return;
						}
						ArrayList<Integer> templist = new ArrayList<Integer>();
						templist.add(buttonid);
						templist.add(classbgcolorindex);
						Map<String,ArrayList<Integer>> tempmap = new HashMap<String,ArrayList<Integer>>();
						tempmap.put(name, templist);
						int tempposition = getEmptyPosition();
						classinfomap.put(tempposition, tempmap);
						
						Log.e("debug name id color position",""+name+" "+buttonid+" "+classbgcolorindex+" "+tempposition);
						gridview.setAdapter(classadapter);

						// ���½��������Ϣд�����ļ�
						int[] tempint = new int[2];
						tempint[0] = fileclassnum;
						buttonid++;
						tempint[1] = buttonid;
						writeClassNum(tempint);// ��ĸ���
						int tempid = buttonid -1;
						writeClassFileInfo(name,tempid,tempposition,classbgcolorindex);
						File filedir = new File("/sdcard/eFinger/ConFigure");

						if (filedir.exists() == false)
							filedir.mkdirs();

						String InfFilePath = "/sdcard/eFinger/ConFigure/ConfigInfFile.inf";
						File Infofile = new File(InfFilePath);
						try
						{
							Infofile.createNewFile();
						} catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
				break;
			default:
				break;
		}
	}

	private int getEmptyPosition()
	{
		// TODO Auto-generated method stub
		int tempposition = 0;
		Set<Integer> tempset = classinfomap.keySet();
		
		while(true)
		{
			if(tempset.contains(tempposition))
				tempposition++;
			else
				return tempposition;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (!ListTable.activityTable.isEmpty())
			{
				int activitySize = ListTable.activityTable.size();
//				Log.i("debug in onkeydown of fileclass", "actsize is "
//						+ activitySize);
				for (int i = 0; i < activitySize; i++)
				{
					ListTable.activityTable.get(i).finish();
				}
			}
			finish();

			android.os.Process.killProcess(android.os.Process.myPid());
			
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);

	}

}
