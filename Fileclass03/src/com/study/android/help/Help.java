package com.study.android.help;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.study.android.Category.Fileclass;
import com.study.android.HWtrying.R;

public class Help extends ListActivity
{
	private Drawable backgrounddrawable;
	private String classname ;
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.help);	
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
	        HelpAdapter myadapter = new HelpAdapter(this);
			this.setListAdapter(myadapter);
	}
	protected void onListItemClick(ListView l,View v,int position,long id)
	{
		super.onListItemClick(l, v, position, id);
//		Log.i("DEBUG-->"," "+position);
		Intent todointent = null;
		boolean startflag = false;
		switch (position)
		{
			case 0:
				todointent = new Intent(Help.this,QuickStart.class); 
				Bundle qsbundle = new Bundle();
	  			qsbundle.putString("classname", classname);
	  			todointent.putExtras(qsbundle);
				startflag = true;
				break;
			case 1:
//				Log.i("debug in help ","functiondetail !!!");
				todointent= new Intent(Help.this,FunctionDetail.class); 
				Bundle fdbundle = new Bundle();
	  			fdbundle.putString("classname", classname);
	  			todointent.putExtras(fdbundle);
				startflag = true;
				break;
			case 2:
				todointent= new Intent(Help.this,EditFunction.class); 
				Bundle efbundle = new Bundle();
	  			efbundle.putString("classname", classname);
	  			todointent.putExtras(efbundle);
				startflag = true;
				break;
			default:
				break;
		}
		if(startflag)
		{
			startActivity(todointent);
			Help.this.finish();
		}
	}
	@Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        
        this.getWindow().setBackgroundDrawable(backgrounddrawable);       
        setContentView(R.layout.help);	  
        
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		 {
			backgrounddrawable = null;
			Intent fanhui = new Intent(Help.this,Fileclass.class);        //´ò¿ªÍøÒ³Á´½Ó
			Bundle bundle = new Bundle();
  			bundle.putString("classname", classname);
  			fanhui.putExtras(bundle);
	  		startActivity(fanhui);
	  		Help.this.finish();
			return true;
		 }
		 else
			 return super.onKeyDown(keyCode,event);
	}
}
