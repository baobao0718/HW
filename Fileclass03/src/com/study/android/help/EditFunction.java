package com.study.android.help;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;

import com.study.android.HWtrying.R;

public class EditFunction extends Activity
{
	private Drawable backgrounddrawable;
	private String classname ;
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        Intent conf = getIntent();
	        Bundle bundle = conf.getExtras();
	        classname = bundle.getString("classname");
	        setContentView(R.layout.editfunction);
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
	}
	@Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        
        this.getWindow().setBackgroundDrawable(backgrounddrawable);       
        setContentView(R.layout.editfunction);	  
        
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		 {
			backgrounddrawable = null;
			Intent fanhui = new Intent(EditFunction.this,Help.class);        //´ò¿ªÍøÒ³Á´½Ó
			Bundle bundle = new Bundle();
  			bundle.putString("classname", classname);
  			fanhui.putExtras(bundle);
	  		startActivity(fanhui);
	  		EditFunction.this.finish();
			return true;
		 }
		 else
			 return super.onKeyDown(keyCode,event);
	}
}
