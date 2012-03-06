package com.study.android.configure;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.Toast;

import com.study.android.HWtrying.ColorPickerDialog;
import com.study.android.HWtrying.R;
import com.study.android.data.ListTable;

public  class InputBgColorConfigure extends Activity implements ColorPickerDialog.OnColorChangedListener,
OnItemClickListener{
	private Paint paint = new Paint();
	public Drawable backgrounddrawable;
	public boolean jumpflag = false;
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bgcolorconfigure);
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
//	        paint.setColor(Color.BLUE);//2011.12.20 liqiang
//	        ColorPicker();  
	      //取得GridView对象
			GridView gridview = (GridView) findViewById(R.id.gridview);
			//添加元素给gridview
			gridview.setAdapter(new ImageAdapter(this));

			// 设置Gallery的背景
			gridview.setBackgroundResource(R.drawable.portbground);

			//事件监听
			gridview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position, long id)
				{
					ListTable.backgroundimgid = position;
					Toast.makeText(InputBgColorConfigure.this, "你选择了" + (position + 1) + " 号图片", Toast.LENGTH_SHORT).show();
				}
			});


	}

	
	
	
	  public void ColorPicker()
	    {
	    	//调色板监听！！
	    	new ColorPickerDialog(this, this,paint.getColor()).show();
	    }
	    
 		@Override
 		public void colorChanged(int color) {
 			// TODO Auto-generated method stub
 			paint.setColor(color);
 			ListTable.bgcolor= color;
 			this.backgrounddrawable = null;
        	Intent fanhui = new Intent(InputBgColorConfigure.this,Configure.class);        //打开网页链接
  			startActivity(fanhui);
  			InputBgColorConfigure.this.finish();
// 			Log.i("DEBUG--->","COLOR CHANGED");	
 		}
// 	Thread waitthread = new Thread()
// 	{
// 		public void run()
// 		{
// 			while(!jumpflag)
// 				;
// 			jump();
// 		}
// 	};
 	public void jump()
 	{
 		this.backgrounddrawable = null;
    	Intent fanhui = new Intent(InputBgColorConfigure.this,Configure.class);        //打开网页链接
		startActivity(fanhui);
		InputBgColorConfigure.this.finish();
 	}
 	//2011.11.07 liqiang
 	@Override  
 	public boolean onKeyDown(int keyCode, KeyEvent event) 
 	{ 
 		if(keyCode == KeyEvent.KEYCODE_BACK)
 		{   
 			Log.i("click on keydown ","on keydown is clicking !!!!");
//        	this.backgrounddrawable = null;
//        	Intent fanhui = new Intent(InputBgColorConfigure.this,Configure.class);        //打开网页链接
//  			startActivity(fanhui);
//  			InputBgColorConfigure.this.finish();
 			jump();
        	return true;
        }else{  
            return super.onKeyDown(keyCode, event);   
        }  	
 	}




	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		// TODO Auto-generated method stub
		
	}
}
