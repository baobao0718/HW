package com.study.android.configure;

import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.PView;
import com.study.android.HWtrying.R;
import com.study.android.data.ListTable;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class InputformatConfigure extends Activity implements SeekBar.OnSeekBarChangeListener{
	
	private TextView mspeedText,mStrokeWidthText,BaseLineHeightText;
	private short progressspeed=0;
	private short progressstrokewidth=0;
	private short baselineheight=0;
	private short speedcontral=500;		//500这里为计时器的计时间隔，单位为毫秒
	Drawable backgrounddrawable;//2011.09.29 liqiang
    SeekBar mspeedSeekBar;
    SeekBar mstrokeSeekBar;
    SeekBar BaseLineBar;
    private RadioGroup m_RadioGroup;
    private RadioButton AutoSubmit,ManualSubmit;
	
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.inputconfigure);

	        backgrounddrawable = this.getResources().getDrawable(R.drawable.landbground);//2011.09.29 liqiang
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);//2011.09.29 liqiang
	        mspeedSeekBar =(SeekBar)findViewById(R.id.seekspeed);
	        mstrokeSeekBar=(SeekBar)findViewById(R.id.strokewidth);		//这里的笔画宽度是指示在Mview上面显示的笔迹的宽度
	        BaseLineBar =(SeekBar)findViewById(R.id.baselineheight);
	        m_RadioGroup =(RadioGroup)findViewById(R.id.submit_type);
	        AutoSubmit =(RadioButton)findViewById(R.id.autosubmit);
	        ManualSubmit =(RadioButton)findViewById(R.id.manualsubmit);
	        
	        mspeedText = (TextView) findViewById(R.id.speedtext);
	        mStrokeWidthText = (TextView) findViewById(R.id.strokewidthtext);
	        BaseLineHeightText= (TextView) findViewById(R.id.baselineheighttext);
	        
	        int speed = ListTable.writespeed;
	        int strokewidth = ListTable.characterstrokewidth ;
	        int shuxiejizhun = ListTable.shuxiejizhun;
	        int isautosubmit = ListTable.isAutoSubmit;
	        
	        speed = speed /100;			//y=（0.16*x+4）*100的公式转换
	        speed-=4;
	        speed*=25;
	        speed/=4;
	       // strokewidth = (int)strokewidth;
	       // Log.i("DEBUG--->","speed   "+speed);
	      //  Log.i("DEBUG--->","strokewidth  "+strokewidth*10);
	        mspeedSeekBar.setProgress(speed);
	        mstrokeSeekBar.setProgress(strokewidth*10);
	        BaseLineBar.setProgress(shuxiejizhun);
	        
	        mStrokeWidthText.setText("当前值:"+strokewidth);
	        BaseLineHeightText.setText("当前值:"+shuxiejizhun);
	        
	        
	        mspeedSeekBar.setOnSeekBarChangeListener(this);
	        mstrokeSeekBar.setOnSeekBarChangeListener(this);
	        BaseLineBar.setOnSeekBarChangeListener(this);
	        
	        if(isautosubmit == 1)
	        	AutoSubmit.setChecked(true);
	        else
	        	ManualSubmit.setChecked(true);
	        
	    	m_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
	    	{

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if(checkedId == AutoSubmit.getId())
					{
						ListTable.isAutoSubmit =1;
//						Log.i("DEBUG-------->","自动提交");
					}
					else if(checkedId == ManualSubmit.getId())
					{
						ListTable.isAutoSubmit =0;
//						Log.i("DEBUG-------->","手动提交");
					}
				}
	    		
	    	});
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean afromTouch) {
		// TODO Auto-generated method stub
	//	String id=arg0.getTag();
		if(R.id.seekspeed== seekBar.getId())
		{
			
			//Log.i("DEBUG--->","seekspeedbar"+progress);			
			//short m= (short) (progress/10);
			//progressspeed=(short) progress;
			
			double m= (double)(0.16*progress+4);			//把0~100 映射到4~20
			progressspeed=(short)Math.ceil(m);

		}
		else if(R.id.strokewidth== seekBar.getId())
		{
		//	Log.i("DEBUG--->","seekstrokewidth   ");
			short n= (short) (progress/10);
			mStrokeWidthText.setText("当前值:"+n);
			progressstrokewidth=n;
		}
		else if(R.id.baselineheight==seekBar.getId())
		{
			BaseLineHeightText.setText("当前值:"+progress);
			baselineheight=(short) progress;
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
		if(R.id.seekspeed== seekBar.getId())										//之所以没有放到onProgressChanged是为了减少运算的时间复杂度
		{
			ListTable.writespeed = (int)progressspeed*100;
			//ListTable.writespeed=ListTable.writespeed >speedcontral?ListTable.writespeed:speedcontral;
		}
		else if(R.id.strokewidth== seekBar.getId())
			ListTable.characterstrokewidth= (int) (progressstrokewidth);
		else if(R.id.baselineheight== seekBar.getId())
			ListTable.shuxiejizhun= baselineheight;
		
		//Log.i("DEBUG--->","ListTable.shuxiejizhun  "+ListTable.shuxiejizhun);
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
	        
	        
	        
	        setContentView(R.layout.inputconfigure);
	        backgrounddrawable = null;
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);//2011.09.29 liqiang
	        mspeedSeekBar =(SeekBar)findViewById(R.id.seekspeed);
	        mstrokeSeekBar=(SeekBar)findViewById(R.id.strokewidth);		//这里的笔画宽度是指示在Mview上面显示的笔迹的宽度
	        BaseLineBar =(SeekBar)findViewById(R.id.baselineheight);
	        m_RadioGroup =(RadioGroup)findViewById(R.id.submit_type);
	        AutoSubmit =(RadioButton)findViewById(R.id.autosubmit);
	        ManualSubmit =(RadioButton)findViewById(R.id.manualsubmit);
	        
	        mspeedText = (TextView) findViewById(R.id.speedtext);
	        mStrokeWidthText = (TextView) findViewById(R.id.strokewidthtext);
	        BaseLineHeightText= (TextView) findViewById(R.id.baselineheighttext);
	        
	        int speed = ListTable.writespeed;
	        int strokewidth = ListTable.characterstrokewidth ;
	        int shuxiejizhun = ListTable.shuxiejizhun;
	        int isautosubmit = ListTable.isAutoSubmit;
	        
	        speed = speed /20;
	       // strokewidth = (int) (strokewidth);
	        mspeedSeekBar.setProgress(speed);
	        mstrokeSeekBar.setProgress(strokewidth*10);
	        BaseLineBar.setProgress(shuxiejizhun);
	        
	        mStrokeWidthText.setText("当前值:"+strokewidth);
	        BaseLineHeightText.setText("当前值:"+shuxiejizhun);
	        
	        mspeedSeekBar.setOnSeekBarChangeListener(this);
	        mstrokeSeekBar.setOnSeekBarChangeListener(this);
	        BaseLineBar.setOnSeekBarChangeListener(this);
	        
	        
	        if(isautosubmit == 1)
	        	AutoSubmit.setChecked(true);
	        else
	        	ManualSubmit.setChecked(true);
	        
	        m_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
	    	{

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					if(checkedId == AutoSubmit.getId())
					{
						ListTable.isAutoSubmit =1;
					}
					else if(checkedId == ManualSubmit.getId())
					{
						ListTable.isAutoSubmit =0;
					}
				}
	    		
	    	});
	    }
}
