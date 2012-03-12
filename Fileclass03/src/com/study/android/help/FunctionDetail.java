package com.study.android.help;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.study.android.HWtrying.R;

public class FunctionDetail extends Activity
{ 
	private Drawable backgrounddrawable,func;
	private String classname ;
	private ImageView background;
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        Intent conf = getIntent();
	        Bundle bundle = conf.getExtras();
	        classname = bundle.getString("classname");
	        setContentView(R.layout.functiondetail);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
	        background = (ImageView)findViewById(R.id.img);
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        func = this.getResources().getDrawable(R.drawable.help_cn_full);
	        background.setBackgroundDrawable(func);
//	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
//	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
	        
	}
	@Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        
        this.getWindow().setBackgroundDrawable(backgrounddrawable);       
        setContentView(R.layout.functiondetail);	  
        background = (ImageView)findViewById(R.id.img);
        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
        func = this.getResources().getDrawable(R.drawable.help_cn_full);
        background.setBackgroundDrawable(func);
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		 {
			backgrounddrawable = null;
			Intent fanhui = new Intent(FunctionDetail.this,Help.class);        //´ò¿ªÍøÒ³Á´½Ó
			Bundle bundle = new Bundle();
  			bundle.putString("classname", classname);
  			fanhui.putExtras(bundle);
	  		startActivity(fanhui);
	  		FunctionDetail.this.finish();
			return true;
		 }
		 else
			 return super.onKeyDown(keyCode,event);
	}
}
