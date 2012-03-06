package com.study.android.retrieval;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import android.util.Log;

import com.study.android.basicData.BasePoint;
import com.study.android.basicData.DataType;
import com.study.android.basicData.DataUnit;
import com.study.android.code.MemoryDecoder;
import com.study.android.data.ListTable;
import com.study.android.model.SectionFormat;
import com.study.android.ui.HwFunctionWindow;
import com.study.android.code.DocumentDecoder;

public class Operationonrretrievalfile {
	
	private MemoryDecoder memoryDecoder;
	private DocumentDecoder documentDecoder;
	private int bytecount=0;
	
	Head0Datastruct head0struct = new Head0Datastruct();
	
	BodyDatastruct bodystruct = new BodyDatastruct();
	
	ArrayList<Head1Datastruct> head1list = new ArrayList<Head1Datastruct>();
	
	//Map<Integer,Vector<Head2Datastruct>> head2map = new HashMap<Integer,Vector<Head2Datastruct>>();
	Map<Integer,Vector<Head2Datastruct>> head2map = new LinkedHashMap<Integer,Vector<Head2Datastruct>>();          //由于hashmap不能保证迭代出的顺序与插入的顺序完全一致，故将其替换为LinkedHashMap
	
	ArrayList<BodyDatastruct> bodylist = new ArrayList<BodyDatastruct>();
	
	
	
	
	//2011.01.10 liqiang
	//对索引文件进行读取操作
	public void readfromretrievalfile(String classname)
	{
		head1list.clear();
		head2map.clear();
		bodylist.clear();
		int number=0;
		File retrievalfile = new File("/sdcard/eFinger/"+classname+"/Retrieve/retrievalfile.retrie");
		BufferedInputStream inputstream = null;
		if(retrievalfile.exists()==false)
		{
			//Log.v("DEBUG--->","MEIYOU JI LU CUN ZAI");	
		}
		else
		{
		
		try 
		{
			inputstream = new BufferedInputStream(new FileInputStream(retrievalfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try 
		{
			// 读取Head0 部分
			
			byte[] bytecreatednumoffile = new byte[4];
			byte[] bytepresentmonth= new byte[4];
			byte[] bytelocationofnewrecord = new byte[4];
			
			inputstream.read(bytecreatednumoffile);
			inputstream.read(bytepresentmonth);
			inputstream.read(bytelocationofnewrecord);
			head0struct.setcreatednumoffile(Transform.BytestoInt(bytecreatednumoffile));
			head0struct.setpresentmonth(Transform.BytestoInt(bytepresentmonth));
			head0struct.setlocationofnewrecord(Transform.BytestoInt(bytelocationofnewrecord));
			
			Log.v("DEBUG----->","head0struct.getcreatednumoffile  "+head0struct.getcreatednumoffile());
			
			number+=12;
			
			bytecount+=12;
			
			// 读取Head1 部分
			
			for(int i=0;i!=head0struct.getpresentmonth();i++)
			{
				byte[] bytemonthnumber = new byte[4];
				byte[] byterecordnumoffile= new byte[4];
				byte[] byteaddressinHead2 = new byte[4];
				
				inputstream.read(bytemonthnumber);
				inputstream.read(byterecordnumoffile);
				inputstream.read(byteaddressinHead2);
				
				Head1Datastruct head1struct = new Head1Datastruct(0,0,0);
				head1struct.setmonthnumber(Transform.BytestoInt(bytemonthnumber));
				head1struct.setrecordnumoffile(Transform.BytestoInt(byterecordnumoffile));
				head1struct.setaddressinHead2(Transform.BytestoInt(byteaddressinHead2));
				
				head1list.add(head1struct);
				number+=12;
				
				bytecount+=12;
				
			}
			
	//		Log.v("DEBUG--->","bytecount after head1 "+bytecount);
			// 读取Head2 部分
			
			
			for(int i=0;i!=head1list.size();i++)
			{
				Vector<Head2Datastruct> vector = new Vector<Head2Datastruct>();
				
				for(int j=0;j!=head1list.get(i).getrecordnumoffile();j++)
				{
					
					Head2Datastruct head2struct = new Head2Datastruct();
					byte[] byteaddressinbody = new byte[4];
					inputstream.read(byteaddressinbody);
					//Log.v("DEBUG--->","addressinbody"+Transform.BytestoInt(byteaddressinbody));
					head2struct.setlocation(Transform.BytestoInt(byteaddressinbody));
					vector.add(head2struct);
					number += 4;
					bytecount+=4;
				}
				
				 head2map.put((Integer)head1list.get(i).getmonthnumber(),vector);	
			}
			
			
			
			// 读取Body 部分

			Iterator keyvalue = head2map.entrySet().iterator();
			//Iterator key= head2map.keySet().iterator();				//得到所有关键字的集合	
			//Iterator value = head2map.values().iterator();			//得到所有值的集合
			

			while(keyvalue.hasNext())									//遍历Map 结构
			{
				Map.Entry<Integer,Vector<Head2Datastruct>>  entry = (Entry<Integer, Vector<Head2Datastruct>>) keyvalue.next();
				//Log.v("DEBUG--->","vector KEY"+entry.getKey());
				//Log.v("DEBUG--->","vector VALUE"+entry.getValue().size());
				for(int j=0;j!=entry.getValue().size();j++)
				{	
					BodyDatastruct bodystruct = new BodyDatastruct();
					byte[] bytecharacternumoftitle = new byte[4];
					byte[] byteexistflag= new byte[4];
					byte[] byteserianumberoffile = new byte[4];
					byte[] bytedate = new byte[4];
					byte[] bytevectoroffile=new byte[4];//zhuxiaoqing 2011.08.21
					ArrayList<DataUnit> temptitle = new ArrayList<DataUnit>();
					
					inputstream.read(bytecharacternumoftitle);
					inputstream.read(byteexistflag);
					inputstream.read(byteserianumberoffile);
					inputstream.read(bytedate);
					inputstream.read(bytevectoroffile);//zhuxiaoqing 2011.08.21
					int voffile=0;
					voffile=Transform.BytestoInt(bytevectoroffile);
					byte[] bytefilename=new byte[voffile];
					inputstream.read(bytefilename);
					String filename1=new String(bytefilename);
					int numofcharacter=0;
					numofcharacter=Transform.BytestoInt(bytecharacternumoftitle);
					//Log.v("DEBUG--->","numofcharacter  "+numofcharacter);
					readDataFromFileForTitle(inputstream,numofcharacter);					//问题在这！！！！因为只要调用该函数，就会把数据全部读完，～～～～～～
					temptitle= (ArrayList<DataUnit>)ListTable.globalIndexTable.clone();				
					bodystruct.setcharacternumoftitle(Transform.BytestoInt(bytecharacternumoftitle));
					bodystruct.setexistflag(Transform.BytestoInt(byteexistflag));
					bodystruct.setserianumberoffile(Transform.BytestoInt(byteserianumberoffile));
					bodystruct.setdate(Transform.BytestoInt(bytedate));
					bodystruct.setVectoroffile(voffile);
					bodystruct.setFilename(filename1);
					bodystruct.settitle(temptitle);
					int size=computeTitleSize(temptitle);
					bytecount+=16;
					bytecount+=size;
					bodylist.add(bodystruct);	
				}
			}
			//Log.v("DEBUG","DU QU JIE SHU");
			inputstream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
	}
	

	// 对索引文件进行写操作
	//2011.01.10  liqiang
	public void savetoretrievalfile(String classname) throws IOException								    //把最新的信息写道索引表中，更新索引表
	{
		
		File filedir =new File("/sdcard/eFinger/"+classname+"/Retrieve");
		 
		 if(filedir.exists()==false)
			 filedir.mkdirs();
		 
		File retrievalfile = new File("/sdcard/eFinger/"+classname+"/Retrieve/retrievalfile.retrie");
		
		if(retrievalfile.exists()==true)							//文件存在
		{
			//Log.v("EXIST--->&&&&&&&&&&","TRUE");
			if(ListTable.modify == true)							//索引文件被修改了，则删除原来的，重新写入新的该文件
			{
				
				//System.out.println("############zouzheli");
				readfromretrievalfile(classname);//2011.01.10 liqiang	 														//先从索引文件中读取数据，再进行写操作
				
				if(true == retrievalfile.delete())
				{
					BufferedOutputStream outputstream = null ;
					
					try 
					{
						outputstream = new BufferedOutputStream(new FileOutputStream(retrievalfile));
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					Log.v("DELETE--->xxxxxxxx","KAI SHI XIE WEN JIAN");
					
					//对Head0 部分进行修订
					int  numoffile =0,presentnumofmonth=0,location=100,addrecordlocation=-1;
					numoffile = head0struct.getcreatednumoffile();
					
					java.util.Calendar c = java.util.Calendar.getInstance();
					int year=c.get(java.util.Calendar.YEAR);
					int mon=c.get(java.util.Calendar.MONTH)+1;			//日历中月初始值是0
					int day=c.get(java.util.Calendar.DAY_OF_MONTH);
					//System.out.println("zhushijian ####### calendar"+" "+year+" "+mon+" "+day);
					String date="";
					
					if(mon/10==0)
						{
						date=year+"0"+mon;
						
						}
					else
						date=year+""+mon;
					int datetime;
//					if(false==ListTable.xiufu)	
//						datetime = Integer.parseInt(date, 10);
//					else
//					{
//						datetime=ListTable.datetmp/100;
//						//datetime=201112;
//						System.out.println("%%%%% head0 datetime"+datetime);
//					}
					datetime = Integer.parseInt(date, 10);
					//datetime=ListTable.datetmp/100;//zhuxiaoqing 2011.12.27
					for(int i=0;i!=head1list.size();i++)
					{
						if(datetime==head1list.get(i).getmonthnumber())
						{
							addrecordlocation=i;
							break;
						}
					}
					
					
					
					if(addrecordlocation==-1)							//head1list 中 并不存在该月记录
						
						{
						presentnumofmonth =head0struct.getpresentmonth()+1;
						}
					else
						presentnumofmonth =head0struct.getpresentmonth();
					
					
					
					
					head0struct.setcreatednumoffile(++numoffile);
					head0struct.setpresentmonth(presentnumofmonth);
					//head0struct.setlocationofnewrecord(location);						//？这里有错，至此，Head0 部分修订完毕
					
					
					//对Head1 部分进行修订
					if(addrecordlocation==-1)											//在head1list中添加新的记录
					{
						int totalnumofrecord=12,tempaddressinHead2=0;										//前12个字节已经被HEAD0占据了
						totalnumofrecord+=(head1list.size()+1)*12;							//至此，把HEAD1部分的数据占用全算出了,加1是因为HEAD1新添加记录时候会占据12字节！！！
						
						for(int i=0; i!= head1list.size();i++)
						{
							tempaddressinHead2=0;
							tempaddressinHead2 =head1list.get(i).getaddressinHead2();
							tempaddressinHead2 +=12;									//这里加12就够了
							head1list.get(i).setaddressinHead2(tempaddressinHead2);
							for(int j=0;j!=head1list.get(i).getrecordnumoffile();j++)
							{
								totalnumofrecord+=4;
							}
						}										//计算出当前Head2 数据部分所占的总字节数
						Head1Datastruct head1struct = new Head1Datastruct(datetime,1,totalnumofrecord);
						
						head1list.add(head1struct);
					}
					else																//若当前月份已经有记录在案的记录，则不用添加，只需修改那个月的记录数目
					{
						int number =head1list.get(addrecordlocation).getrecordnumoffile();
						head1list.get(addrecordlocation).setrecordnumoffile(++number);
					}
					
				
					
					
					//对Head2 部分进行修订
					
					int head2location =0,key=0,templocation=0;
					Vector<Head2Datastruct> vector = new Vector<Head2Datastruct>();
					Head2Datastruct head2struct = new Head2Datastruct();
					Iterator keyvalueentry = head2map.entrySet().iterator();
					if(addrecordlocation==-1)											//在head1list中添加了新的记录
					{
						
						head2location =0;
						head2location = head0struct.getlocationofnewrecord();
						head0struct.setlocationofnewrecord((head2location+16));			//16=12+4,12指的是新的HEAD1记录占用空间，而4指的是Head2中增加记录所需空间
						head2location+=16;												//增加了一条HEAD1记录,还有一个HEAD2的小记录块
						
						//head2location= head1list.get((head1list.size()-1)).getaddressinHead2();
								
						while(keyvalueentry.hasNext())									//遍历Map 结构
						{
							Map.Entry<Integer,Vector<Head2Datastruct>> entry = (Entry<Integer, Vector<Head2Datastruct>>) keyvalueentry.next();
							Vector<Head2Datastruct> vector0 = entry.getValue();
							Integer keyvice = entry.getKey();
							for(int i=0;i!=vector0.size();i++)
							{
								templocation = vector0.get(i).getlocation();
								templocation+=16;											
								vector0.get(i).setlocation(templocation);
							}
						}

						key = head1list.get((head1list.size()-1)).getmonthnumber();
						
						head2struct.setlocation((head2location));						//这个是由于前面HEAD2中增加了一个小记录快，暂时新地址必须后移4
						vector.add(head2struct);
						head2map.put((Integer)key,vector);
						
					}
					else																//若当前月份已经有记录在案的记录，则不用添加，只需修改那个月的记录数目
					{
						key =head1list.get((head1list.size()-1)).getmonthnumber();
						head2location=head0struct.getlocationofnewrecord();
						head0struct.setlocationofnewrecord((head2location+4));			//这个是由于前面HEAD2中增加了一个小记录快，暂时新地址必须后移4
	//					Log.v("DEBUG--->","HEAD2MAP XIU DING location  "+head2location);	
						while(keyvalueentry.hasNext())									//遍历Map 结构
						{
							Map.Entry<Integer,Vector<Head2Datastruct>> entry = (Entry<Integer, Vector<Head2Datastruct>>) keyvalueentry.next();
							Vector<Head2Datastruct> vector0 = entry.getValue();
							Integer keyvice = entry.getKey();
							for(int i=0;i!=vector0.size();i++)
							{
								templocation = vector0.get(i).getlocation();
								templocation+=4;											
								vector0.get(i).setlocation(templocation);
							}
						}
						
						/*
						Vector<Head2Datastruct> vector1 = head2map.get(key);
						for(int i=0;i!=vector1.size();i++)
						{
							templocation =head2map.get(key).get(i).getlocation();
							templocation+=4;											//因为本身在HEAD2中增加了一个小记录快
							head2map.get(key).get(i).setlocation(templocation);
						}
						*/
						
						head2struct.setlocation(head2location+4);
						head2map.get(key).add(head2struct);
					}
					
					
					//对Body 部分进行修订
					
					//对于Body 部分似乎可以直接插入记录即可，因为插入的记录总是在最后
					int titlesize=0;
					int numberoffile =0 ;
					numberoffile =head0struct.getcreatednumoffile();
					ArrayList<DataUnit> title =(ArrayList<DataUnit>) ListTable.globalTitle.clone();				//全局存储标题信息的变量，在保存文件点击标题完成的时候对其进行修改
//					Log.v("DEBUG--->wenjiancunzai","title  size---+++  "+title.size());
					bodystruct.setcharacternumoftitle((short)title.size());
					bodystruct.setexistflag(1);										//1 表示存在
					
					bodystruct.setserianumberoffile(numberoffile);								
					
					//Log.v("DEBUG--->","date  "+date);
					if(day/10==0)
						date=date+"0"+day;
					else
						date=date+""+day;
		//			date+=""+day;
					int datetime1;
					if(ListTable.xiufu==false)//zhuxiaoqing 2011.12.27
					{
						datetime1 =Integer.parseInt(date, 10);
						System.out.println("############zouzheli wenjiancunzai false"+datetime1);
					}
					else
						{
						datetime1=0;
						datetime1=ListTable.datetmp;	
						//datetime1=20111226;
						System.out.println("$$$$$$$$$$$$$wenjian cunzai datetime true"+datetime1);
						}
					//datetime1=ListTable.datetmp;//zhuxiaoqing 2011.12.27
					//Log.v("DEBUG-->&&&&&&&&&&&&&&&&&&&&&&&&&&&&&","datetime1  "+datetime1);
					bodystruct.setdate(datetime1);
					bodystruct.setVectoroffile(ListTable.filename.length());//zhuxiaoqing 2011.08.21
					bodystruct.setFilename(ListTable.filename);//zhuxiaoqing 2011.08.21
					bodystruct.settitle(title);
					
					bodylist.add(bodystruct);
					titlesize=computeTitleSize(title);
					
					location=0;
					location=head0struct.getlocationofnewrecord();
			                                                      
					location+=16;
					
					location+=titlesize;
					//Log.i("DEBUG--->","JIANG XIE RU DE TITLE DE ZIJIE DA XIAO  "+titlesize);
					head0struct.setlocationofnewrecord(location);						//直到这里，Head0 的最后一项数据才得到
					//ListTable.globalTitle.clear();
					
					//开始写文件
					
					//开始把这些结构里的数据写入文件
					
					//Head0 的数据写入
					outputstream.write(Transform.InttoBytes(head0struct.getcreatednumoffile()));
					outputstream.write(Transform.InttoBytes(head0struct.getpresentmonth()));
					outputstream.write(Transform.InttoBytes(head0struct.getlocationofnewrecord()));
					

					//Log.v("DEBUG--->","HEAD0 -->locationofnewrecord  "+head0struct.getlocationofnewrecord());
					
					//Head1 的数据写入
					for(int i=0; i!= head1list.size();i++)
					{
					Head1Datastruct head1read = head1list.get(i);
					outputstream.write(Transform.InttoBytes(head1read.getmonthnumber()));
					outputstream.write(Transform.InttoBytes(head1read.getrecordnumoffile()));
					outputstream.write(Transform.InttoBytes(head1read.getaddressinHead2()));
//					Log.v("DEBUG--->","monthnumber  "+head1read.getmonthnumber());
//					Log.v("DEBUG--->","recordnumoffile  "+head1read.getrecordnumoffile());
//					Log.v("DEBUG--->","HEAD1-->addressinHead2  "+i+" "+head1read.getaddressinHead2());
					}
					
				
					//Head2 的数据写入
					Iterator keyvalueentryvice = head2map.entrySet().iterator();		
					while(keyvalueentryvice.hasNext())									//遍历Map 结构
					{
						Map.Entry<Integer,Vector<Head2Datastruct>>  entry = (Entry<Integer, Vector<Head2Datastruct>>) keyvalueentryvice.next();
						Vector<Head2Datastruct> vectorwrite = entry.getValue();
						for(int j=0;j!=vectorwrite.size();j++)
							{
							outputstream.write(Transform.InttoBytes(vectorwrite.get(j).getlocation()));
						//	Log.v("DEBUG--->","HEAD2--->locationinBody "+j+" "+vectorwrite.get(j).getlocation());
							}
					}					

					
					//Body 部分数据写入
					
					for(int i=0; i!= bodylist.size();i++)
					{
					BodyDatastruct bodystructread =bodylist.get(i);

					outputstream.write(Transform.InttoBytes(bodystructread.getcharacternumoftitle()));
					outputstream.write(Transform.InttoBytes(bodystructread.getexistflag()));
					outputstream.write(Transform.InttoBytes(bodystructread.getserianumberoffile()));
					outputstream.write(Transform.InttoBytes(bodystructread.getdate()));	
					outputstream.write(Transform.InttoBytes(bodystructread.getVectoroffile()));
					outputstream.write(bodystructread.getFilename().getBytes());//zhuxiaoqing 2011.08.21
					writeDataToFileForTitle(outputstream,bodystructread.gettitle());				//至此，标题部分的信息完全写入，未压缩格式，存储一个一个的DataUnit 单元
					outputstream.flush();
					
					}
					
					outputstream.close();
//					Log.v("DEBUG--->","SHUN LI JIE SHU HA HA");
				}
				else
				{
//					Log.v("DEBUG-->","SHAN CHU WEN JIAN SHI BAI");
				}
			}
		}
		else																 //文件不存在
		{
			//System.out.println("wenjian bucunzai #################");
			int temptotalbyte=0;
			try 
			{
				retrievalfile.createNewFile();                                           //这一步是跟据已给路径创造新文件
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedOutputStream outputstream = null ;
			try 
			{
				outputstream = new BufferedOutputStream(new FileOutputStream(retrievalfile));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				
				// 为写入文件作准备，先组织数据结构，和填充初始化数据
				ListTable.numoffile =0;
				Head0Datastruct head0struct = new Head0Datastruct();
				Head1Datastruct head1struct = new Head1Datastruct(0,(short)0,0);
				BodyDatastruct bodystruct = new BodyDatastruct();
				
				ArrayList<Head1Datastruct> head1list = new ArrayList<Head1Datastruct>();
				//head1list.clear();//zhuxiaoqing 2011.12.28
				Map<Integer,Vector<Head2Datastruct>> head2map = new LinkedHashMap<Integer,Vector<Head2Datastruct>>();
				
				ArrayList<BodyDatastruct> bodylist = new ArrayList<BodyDatastruct>();
				java.util.Calendar c = java.util.Calendar.getInstance();
				int year=c.get(java.util.Calendar.YEAR);
				int mon=c.get(java.util.Calendar.MONTH)+1;			//日历中月初始值是0
				String date="";
				
				if(mon/10==0)
					date=year+"0"+mon;
				else
					date=year+""+mon;
				int datetime;
//				if(false==ListTable.xiufu)
//					datetime = Integer.parseInt(date,10);
//				else
//				{
//					datetime=ListTable.datetmp/100;
//					//datetime=201112;
//				}
				datetime = Integer.parseInt(date,10);
				//datetime=ListTable.datetmp/100;//zhuxiaoqing 2011.12.27
				//Head0 的数据构造
				head0struct.setcreatednumoffile(1);
				head0struct.setpresentmonth(1);						//表示为当前记录在案的月份的数目为1
				head0struct.setlocationofnewrecord(100);			//200*1024 表示是200KB
				
				//Head1 的数据构造
				head1struct.setmonthnumber(datetime);
				head1struct.setrecordnumoffile(1);
				head1struct.setaddressinHead2(24);
				head1list.add(head1struct);
				
				temptotalbyte+=24;									//加上head0部分的占用空间
				
				//int head2size =24;
				for(int i=0; i!= head1list.size();i++)
				{
					for(int j=0;j!=head1list.get(i).getrecordnumoffile();j++)
					{
						temptotalbyte+=4;
					}
				}
				//Head2 的数据和数据结构构造
				for(int i=0; i!= head1list.size();i++)
				{
					int number=0;
					Vector<Head2Datastruct> vector = new Vector<Head2Datastruct>();
					for(int j=0;j!=head1list.get(i).getrecordnumoffile();j++)
					{
						Head2Datastruct head2struct = new Head2Datastruct();
						
						head2struct.setlocation(temptotalbyte);
						temptotalbyte+=4;
						vector.add(head2struct);
					}
					 head2map.put((Integer)head1list.get(i).getmonthnumber(),vector);	
				}																			//至此，Head2 的结构和数据填充已经完全完成
				
				
				//Body 的数据构造
				
				int titlesize=0,lastlocation=0;
				int day=c.get(java.util.Calendar.DAY_OF_MONTH);
				System.out.println("zhushijian ####### calendar"+" "+year+" "+mon+" "+day);
				if(day/10==0)
					date=date+"0"+day;
				else
					date=date+""+day;
				
				
					//date=date+"01";
	//			//date="";
	//			//date=year+""+mon+""+day;
				datetime =0;
				if(false==ListTable.xiufu)
				{
					datetime = Integer.parseInt(date, 10);
					System.out.println("^^^^^^^^^^^^^^^^^^^^ wenjian bucunzai false"+datetime);
				}
				else
				{
					datetime=ListTable.datetmp;
					//datetime=20111226;
					System.out.println("$$$$$$$$$$$$$wenjian bucunzai datetime true"+datetime);
				}
				//datetime=ListTable.datetmp;//zhuxiaoqing 2011.12.27
				ArrayList<DataUnit> title =(ArrayList<DataUnit>) ListTable.globalTitle.clone();				//全局存储标题信息的变量，在保存文件点击标题完成的时候对其进行修改
//				Log.v("DEBUG--->bucunzai","title  size---+++  "+title.size());
				bodystruct.setcharacternumoftitle((short)title.size());
				bodystruct.setexistflag(1);										//1 表示存在
				bodystruct.setserianumberoffile(1);
				bodystruct.setdate(datetime);
				
				bodystruct.setVectoroffile(ListTable.filename.length());//zhuxiaoqing 2011.08.21
				bodystruct.setFilename(ListTable.filename);//zhuxiaoqing 2011.08.21
				
				bodystruct.settitle(title);
				bodylist.add(bodystruct);
				titlesize=computeTitleSize(title);
				lastlocation=temptotalbyte+16+titlesize-4;							//循环退出时候多加了4，在这里恢复
				head0struct.setlocationofnewrecord(lastlocation);					//这才是Head0 的下一条新纪录应该写入的正确位置
//				Log.v("DEBUG--->","temptotalbyte:-->"+temptotalbyte);
//				Log.v("DEBUG--->","titlesize:-->"+titlesize);
				//ListTable.globalTitle.clear();
				
				
				//开始把这些结构里的数据写入文件
				
				//Head0 的数据写入
				outputstream.write(Transform.InttoBytes(head0struct.getcreatednumoffile()));
				outputstream.write(Transform.InttoBytes(head0struct.getpresentmonth()));
				outputstream.write(Transform.InttoBytes(head0struct.getlocationofnewrecord()));
				
				//Head1 的数据写入
				for(int i=0; i!= head1list.size();i++)
				{
				Head1Datastruct head1read = head1list.get(i);
				outputstream.write(Transform.InttoBytes(head1read.getmonthnumber()));
				outputstream.write(Transform.InttoBytes(head1read.getrecordnumoffile()));
				outputstream.write(Transform.InttoBytes(head1read.getaddressinHead2()));
				}
				
				
				//Head2 的数据写入
				
//				Log.v("DEBUG--->","HEAD2MAPSIZE:-->"+head2map.size());
				Iterator keyvalue = head2map.entrySet().iterator();
				//Iterator key= head2map.keySet().iterator();				//得到所有关键字的集合	
				//Iterator value = head2map.values().iterator();			//得到所有值的集合
				
				while(keyvalue.hasNext())									//遍历Map 结构
				{
					Map.Entry<Integer,Vector<Head2Datastruct>>  entry = (Entry<Integer, Vector<Head2Datastruct>>) keyvalue.next();
					Vector<Head2Datastruct> vector = entry.getValue();
//					Log.v("DEBUG--->","vectorSIZE:-->"+vector.size());
					for(int j=0;j!=vector.size();j++)
						outputstream.write(Transform.InttoBytes(vector.get(j).getlocation()));
					vector.clear();	
				}	
				
				
				//Body 部分数据写入
				
				for(int i=0; i!= bodylist.size();i++)
				{
				BodyDatastruct bodystructread =bodylist.get(i);
				outputstream.write(Transform.InttoBytes(bodystructread.getcharacternumoftitle()));
				outputstream.write(Transform.InttoBytes(bodystructread.getexistflag()));
				outputstream.write(Transform.InttoBytes(bodystructread.getserianumberoffile()));
				outputstream.write(Transform.InttoBytes(bodystructread.getdate()));	
				
				outputstream.write(Transform.InttoBytes(bodystructread.getVectoroffile()));//zhuxiaoqing 2011.08.21
				outputstream.write(bodystructread.getFilename().getBytes());//zhuxiaoqing 2011.08.21
//				Log.v("DEBUG--->zzzzz","bodystruct.getFilename   "+bodystructread.getFilename());
				//Log.v("DEBUG--->","bodystruct.gettitle().size   "+bodystruct.gettitle().size());
				writeDataToFileForTitle(outputstream,bodystruct.gettitle());				//至此，标题部分的信息完全写入，未压缩格式，存储一个一个的DataUnit 单元
				
				outputstream.flush();
				outputstream.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ListTable.numoffile++;                                //应当在文件保存成功后才对这个全局变量值进行修改
				
		}
		
		ListTable.modify =false;
	}
	
	public static int computeTitleSize(ArrayList<DataUnit> Title)
	{
		int titlesize=0;
		for(int i=0;i!=Title.size();i++)
		{
			short endpointnum=0;
			short startunitbyte=0;
			DataUnit dataunit = Title.get(i);
			DataType datatype = dataunit.getDataType();
			
			if(datatype!=DataType.TYPE_CONTROL)
			{
				ArrayList<BasePoint> datalist= (ArrayList)dataunit.getPoints();
				for(int j=0;j!=datalist.size();j++)
				{
					if(datalist.get(j).isEnd()==true)
						endpointnum++;
				}
//				if(i==0)
//					startunitbyte=6;					//注意，在一串Dataunit中，只有第一个dataunit的 startunit里添加6个字节，而后面的则在startunit中只添加3（暂时不考虑延续的复杂情况）个字节
//				else
					startunitbyte=3;
				titlesize+= (datalist.size()-2*endpointnum)*2+endpointnum*3+endpointnum*4+startunitbyte+2;		//一个字的起始点和结束点的个数是相等的
			}
			else
			{
				DataType typectrl = dataunit.getCtrlType();
				if(typectrl==DataType.TYPE_CTRL_ENTER)
					titlesize+=1;									//对于换行单元的文件处理还没实现，一切以文件实现的方式为基准
				else if(typectrl==DataType.TYPE_CTRL_SPACE)
					titlesize+=2;
			}
		}
		return titlesize;
	}
	
private void writeDataToFileForTitle(BufferedOutputStream outputStream,ArrayList<DataUnit> title) {
		
		//GZIPOutputStream gzout = new GZIPOutputStream(outputStream);          //为了与上面保持统一，决定标题部分不用压缩流
		memoryDecoder = new MemoryDecoder(outputStream);
		HwFunctionWindow.colortable.clear();
		HwFunctionWindow.Initcolortable();
		memoryDecoder.dataCoderForTitle(title);			
		//gzout.close();
		//outputStream.close();													//为了实现连续写入标题，先屏蔽这条
	}


private void readDataFromFileForTitle(BufferedInputStream inputStream,int characternum) {
	ListTable.sectionTable.clear();
	ListTable.globalIndexTable.clear();
	ListTable.paragraphTable.clear();
	HwFunctionWindow.colortable.clear();
	HwFunctionWindow.Initcolortable();
	
		//inputStream = new BufferedInputStream(new FileInputStream(		//我 更改过
		//	abstractFile));
		//inputStream = new BufferedInputStream(
		//			abstractFile);
		//System.err.println("wenjianchangdu:"+abstractFile.available());
		//GZIPInputStream gin = new GZIPInputStream(inputStream);
		documentDecoder = new DocumentDecoder(inputStream);
		//documentDecoder.headDecoder();						//读取索引文件的头文件
		documentDecoder.dataDecoderForTitle(characternum);					//读取索引文件的数据文件,这里必须注意的一点是，我在读取索引文件的时候仍然是用ListTable.globalIndexTable 来承载标题信息的，之所以这样是为了减少大量的冗余代码

		//gin.close();	
		//inputStream.close();									//为了实现连续读出标题，先屏蔽这条

}
}
