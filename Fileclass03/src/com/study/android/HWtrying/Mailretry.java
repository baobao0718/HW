package com.study.android.HWtrying;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.study.android.Category.Fileclass;
import com.study.android.data.ListTable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Mailretry extends Activity {
	private Button Button_Send;
	private EditText EditText_Reciever;
	private EditText EditText_CC;
	private EditText EditText_Subject;
	private EditText EditText_Body;
	private ProgressBar processbar;
	private TextView processtext;
	private String[] strEmailReciever;
	private String strEmailSubject;
	private String[] strEmailCC;
	private String strEmailBody;
	boolean titleisempty = false;
	boolean availableflag = false;
	public String classname = null; 
	public int pagecount = 1;

	Dialog processdlg;
	String defaultmailsize = "a4";
	String defaultmailname = "email";
	String mailfilename = "";
	String strexportfileformat = "";
    /** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mailretry);
		Button_Send = (Button) findViewById(R.id.myButton_Send);
		EditText_Reciever = (EditText) findViewById(R.id.myEdit_Reciever);
		EditText_CC = (EditText) findViewById(R.id.myEdit_CC);
		EditText_Subject = (EditText) findViewById(R.id.myEdit_Subject);
		EditText_Body = (EditText) findViewById(R.id.myEdit_Body);
		processbar = (ProgressBar)findViewById(R.id.progressBar1);
		processtext = (TextView)findViewById(R.id.processtext);
		processbar.setVisibility(View.GONE);
		processtext.setVisibility(View.GONE);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		classname = bundle.getString("classname");// 获得传输来的类名
		pagecount = bundle.getInt("pagecount");
		mailfilename = bundle.getString("filename");
		strexportfileformat = bundle.getString("format");
		
		Button_Send.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				strEmailReciever = new String[] { EditText_Reciever.getText()
						.toString().trim() };

				strEmailCC = new String[] { EditText_CC.getText().toString()
						.trim() };

				strEmailSubject = EditText_Subject.getText().toString();

				strEmailBody = EditText_Body.getText().toString();

				if (checkAvailable())
				{
					Intent mailIntent = null;
					if(pagecount==1||strexportfileformat.equals("pdf"))
					{
						Log.e("onepage","one page 11111!!");
						mailIntent = new Intent(
								android.content.Intent.ACTION_SEND);
					}
					else
					{
						Log.e("more page ","page multi!!");
						mailIntent = new Intent(
							android.content.Intent.ACTION_SEND_MULTIPLE);
					}
					mailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
							strEmailReciever);

					mailIntent.putExtra(android.content.Intent.EXTRA_CC,
							strEmailCC);

					mailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
							strEmailSubject);

					mailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							strEmailBody);

					Log.e("start to sending mail ","on button listener !!"+strexportfileformat);
					File f = new File("/sdcard/eFinger/" + classname
							+ "/MailImage");
					if (f.exists() == false)
						f.mkdirs();
					ArrayList<Uri> list = new ArrayList<Uri>();
					if(strexportfileformat.trim().equals("png"))
					{
						for(int i=0;i<pagecount;i++)
						{
							File file = new File("/sdcard/eFinger/" + classname
								+  "/Export/"+mailfilename+"_"+i+"."+strexportfileformat);
							Log.i("debug in mailretry file name format is ",mailfilename+" "+strexportfileformat+" "+pagecount);
							
							list.add(Uri.parse("file://" + file.getAbsolutePath()));
							
						}
						mailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, list);
					}
					else if(strexportfileformat.trim().equals("pdf"))
					{
						File file = new File("/sdcard/eFinger/" + classname
								+  "/Export/"+mailfilename+"."+strexportfileformat);
						Log.i("debug in mailretry file name format is ",mailfilename+" "+strexportfileformat+" "+pagecount);
						// File file=new
						// File("/sdcard/eFinger/"+classname+"/MailImage/0.png");
						mailIntent.putExtra(Intent.EXTRA_STREAM,
								Uri.parse("file://" + file.getAbsolutePath()));
					}
					mailIntent.setType("application/octet-stream"); // 其他的均使用流当做二进制数据来发送

					startActivity(Intent.createChooser(mailIntent,
							"Choose Email Client"));
				} else
					return;


			}
		});
        
         
        
    }
    
    public boolean checkAvailable()
    {
    	if(!isEmail(EditText_Reciever.getText().toString().trim()))
		{
			Dialog dialog = new AlertDialog.Builder(Mailretry.this)
			.setTitle("提示")
			.setMessage("您输入的收件人地址格式不合法！")
			.setPositiveButton(	"确定", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					// TODO
					// Auto-generated
					// method stub
				}
			}).create();
			dialog.show();
			return false;
		}
    	else if(EditText_CC.getText().toString().trim().equals(null))
		{
			Dialog dialog = new AlertDialog.Builder(Mailretry.this)
			.setTitle("提示")
			.setMessage("确定没有抄送吗？")
			.setPositiveButton(	"确定", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog,int which)
				{
					// TODO
					// Auto-generated
					// method stub					
				}
			}).create();
			dialog.show();
			return false;
		}
    	else if(EditText_Subject.getText().toString().trim().equals(null))
    	{
    		Dialog dialog = new AlertDialog.Builder(Mailretry.this)
    		.setTitle("提示")
    		.setMessage("确定标题为空吗？")
    		.setPositiveButton(	"确定", new DialogInterface.OnClickListener()
    		{
    				@Override
    			public void onClick(DialogInterface dialog,int which)
    			{
    				// TODO
    				// Auto-generated
    				// method stub
    				titleisempty = true;
    				if(EditText_Body.getText().toString().trim().equals(null))
    	    		{
    	    			Dialog dialogin = new AlertDialog.Builder(Mailretry.this)
    	       			.setTitle("提示")
    	       			.setMessage("邮件内容不能为空！")
    	       			.setPositiveButton(	"确定", new DialogInterface.OnClickListener()
    	       			{
    	       				@Override
    	       				public void onClick(DialogInterface dialog,int which)
    	       				{
    	       					// TODO
    	       					// Auto-generated
    	       					// method stub
    	       				}
    	       			})
    	       			.create();
    	       			dialogin.show();
    	       			availableflag = false;
    	   			}
    				else
    					availableflag = true;
    			}
    		})
    		.setNegativeButton("取消", new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
				}
			}).create();
    		dialog.show();
    		
    		return availableflag;
    	}
    	else if(EditText_Body.getText().toString().trim().equals(null))
    	{
    		Dialog dialogin = new AlertDialog.Builder(Mailretry.this)
   			.setTitle("提示")
   			.setMessage("邮件内容不能为空！")
   			.setPositiveButton(	"确定", new DialogInterface.OnClickListener()
   			{
   				@Override
   				public void onClick(DialogInterface dialog,int which)
   				{
   					// TODO
   					// Auto-generated
   					// method stub
   				}
   			})
   			.create();
   			dialogin.show();
   			return false;
    	}
    	else 
    		return true;
    }
    
    
    short tempwidth,tempheight;
    
    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        
        
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { 
        	
        	tempwidth = Kview.screenwidth>Kview.screenheight?Kview.screenwidth:Kview.screenheight;
			tempheight =Kview.screenwidth<Kview.screenheight?Kview.screenwidth:Kview.screenheight;
			Kview.screenwidth = tempwidth;
			Kview.screenheight = tempheight;
			
			tempwidth=0;
			tempheight=0;
			
			
			tempwidth = PView.screenwidth>PView.screenheight?PView.screenwidth:PView.screenheight;
			tempheight =PView.screenwidth<PView.screenheight?PView.screenwidth:PView.screenheight;
			PView.screenwidth = tempwidth;
			PView.screenheight = tempheight;
			} 
			else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { 
				tempwidth = Kview.screenwidth<Kview.screenheight?Kview.screenwidth:Kview.screenheight;
				tempheight =Kview.screenwidth>Kview.screenheight?Kview.screenwidth:Kview.screenheight;
				Kview.screenwidth = tempwidth;
				Kview.screenheight = tempheight;
				
				
				tempwidth = PView.screenwidth<PView.screenheight?PView.screenwidth:PView.screenheight;
				tempheight =PView.screenwidth>PView.screenheight?PView.screenwidth:PView.screenheight;
				PView.screenwidth = tempwidth;
				PView.screenheight = tempheight;
			}
    }
      


    
    // 检查Email格式
    public static boolean isEmail(String strEmail) {
     String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

     Pattern p = Pattern.compile(strPattern);
     Matcher m = p.matcher(strEmail);
     return m.matches();
    }


    /*
    private OnLongClickListener searhEmail=new OnLongClickListener(){			//myEditText 长按的动作，用来调去contact，从手机的联系人中取得信息（里面包含邮件）！！短按的动作再上面！
    	 public boolean onLongClick(View arg0) {
    		 Uri uri=Uri.parse("content://contacts/people");
    		 Intent intent=new Intent(Intent.ACTION_PICK,uri);
    		 startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
			return false;
			}
    	 ;
    };
    
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	 
    	switch (requestCode) {
		case PICK_CONTACT_SUBACTIVITY:
			final Uri uriRet=data.getData();
			if(uriRet!=null)
			{
				try {
					Cursor c=managedQuery(uriRet, null, null, null, null);
					c.moveToFirst();
					//取得联系人的姓名 
					String strName=c.getString(c.getColumnIndexOrThrow(People.NAME));
					//取得联系人的EMAIL
					String[] PROJECTION=new String[]{
							Contacts.ContactMethods._ID,
							Contacts.ContactMethods.KIND,
							Contacts.ContactMethods.DATA
					};
					//查询指定人的Email
					 Cursor newcur=managedQuery(
							 Contacts.ContactMethods.CONTENT_URI,
							 PROJECTION, 
							 Contacts.ContactMethods.PERSON_ID+"=\'"
							 +c.getLong(c.getColumnIndex(People._ID))+"\'", 
							 null, null);
					startManagingCursor(newcur);
					String email="";
					if(newcur.moveToFirst())
					{
						email=newcur.getString(newcur.getColumnIndex
								(Contacts.ContactMethods.DATA));
						myEditText.setText(email);
					}
					 
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(Mailretry.this, e.toString(), 1000).show();
				}
			}
			break;

		default:
			break;
		}
    	super.onActivityResult(requestCode, resultCode, data);
    };   
    
    */
}