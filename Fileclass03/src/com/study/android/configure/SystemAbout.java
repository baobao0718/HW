package com.study.android.configure;

import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.PView;
import com.study.android.HWtrying.R;
import com.study.android.retrieval.ShowRetrieve;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SystemAbout extends Activity{
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        setTheme(R.style.Transparent);   
	        setContentView(R.layout.systemabout);
	 
	         Window window=getWindow();													//用于设置activity透明
	         WindowManager.LayoutParams wl = window.getAttributes();
	         wl.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	         wl.alpha=0.8f;    //这句就是设置窗口里崆件的透明度的．０.０全透明．１.０不透明．
	         window.setAttributes(wl);
	   
	         ImageView image = (ImageView) findViewById(R.id.imageid);
	         image.setAlpha(100);
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
				
				//PView用于读取，Kview用于写入
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
					
					tempwidth=0;
					tempheight=0;
					
					tempwidth = PView.screenwidth<PView.screenheight?PView.screenwidth:PView.screenheight;
					tempheight =PView.screenwidth>PView.screenheight?PView.screenwidth:PView.screenheight;
					PView.screenwidth = tempwidth;
					PView.screenheight = tempheight;
					
				}
	        
	        
	        setContentView(R.layout.systemabout);
	   	 
	      //   Window window=getWindow();													//用于设置activity透明
	      //   WindowManager.LayoutParams wl = window.getAttributes();
	      //   wl.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
	      //   wl.alpha=0.8f;    //这句就是设置窗口里崆件的透明度的．０.０全透明．１.０不透明．
	     //    window.setAttributes(wl);
	   
	         ImageView image = (ImageView) findViewById(R.id.imageid);
	         image.setAlpha(100);
	        
	        
	    }
	
}
