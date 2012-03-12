package com.study.android.configure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.study.android.HWtrying.ColorPickerDialog;
import com.study.android.HWtrying.Kview;
import com.study.android.HWtrying.PView;
import com.study.android.HWtrying.R;
import com.study.android.data.ListTable;
import com.study.android.retrieval.ShowRetrieve;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class InputCharacterConfigure extends Activity implements OnSeekBarChangeListener,
ColorPickerDialog.OnColorChangedListener{
	
	private TextView characterText,spacewidthtext,spacewidthtext1;
	private int progresssize=0,progressspace=0;
	SeekBar mcharacterSeekBar;
    SeekBar mspaceSeekBar;
    Button charactercolorbutton;
    public static View sampleoftextview;
    public static boolean onprogressstopflag=false;
    private static final int REFRESH  = 0x000001;
    public static ByteArrayInputStream fhwe;
    Drawable backgrounddrawable;
    /**
    	*ByteArrayInputStream 包含一个内部缓冲区，该缓冲区存储从流中读取的字节。内部计数器跟踪 read 方法要提供的下一个字节。 
		*关闭 ByteArrayInputStream 无效。此类中的方法在关闭此流后仍可被调用，而不会产生任何 IOException
     **/

	
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);	 
	        byte[] bufferin= new byte[6000];
	        AssetManager am = this.getAssets();  
	        try {
	        	InputStream fhwestream = am.open("sample.hwep");
	        	int length = fhwestream.read(bufferin);
	        	fhwe = new ByteArrayInputStream(bufferin, 0, length);
	        	fhwe.mark(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	        setContentView(R.layout.characterconfigure);

	        mcharacterSeekBar =(SeekBar)findViewById(R.id.charactersize);
	        mspaceSeekBar=(SeekBar)findViewById(R.id.spacewidth);		//这里的笔画宽度是指示在Mview上面显示的笔迹的宽度
	        
	        characterText = (TextView) findViewById(R.id.charactertext);
	        spacewidthtext = (TextView) findViewById(R.id.spacewidthtext);
	        sampleoftextview=(View)findViewById(R.id.sampleoftextview);
	        charactercolorbutton = (Button)findViewById(R.id.charactercolor);
	        
	        charactercolorbutton.setOnClickListener(charactercolorbuttonlistener);
	        int size = ListTable.charactersize;
	        int space = ListTable.spacewidth ;
	        mcharacterSeekBar.setProgress(size);
	        mspaceSeekBar.setProgress((int) (space/0.16));
	        characterText.setText("当前值:"+size);
	        spacewidthtext.setText("当前值:"+space);
	        mcharacterSeekBar.setOnSeekBarChangeListener(this);
	        mspaceSeekBar.setOnSeekBarChangeListener(this);
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
	}
	OnClickListener charactercolorbuttonlistener = new OnClickListener()//2011.10.19 liqiang
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Log.i("debug in inputcolor ","inputcolor is "+ListTable.charactercolor);
			new ColorPickerDialog(InputCharacterConfigure.this, InputCharacterConfigure.this, ListTable.charactercolor)
			.show();
		}
	};
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean afromTouch) {
		// TODO Auto-generated method stub
		
		if(R.id.charactersize== seekBar.getId())
		{
			characterText.setText("当前值:"+progress);
			progresssize=(int) (progress);

		}
		else if(R.id.spacewidth== seekBar.getId())
		{

			progressspace=(int) (progress*0.16);
			spacewidthtext.setText("当前值:"+progressspace);
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub															
		if(R.id.charactersize== seekBar.getId())										//之所以没有放到onProgressChanged是为了减少运算的时间复杂度
		{
			ListTable.charactersize = (int) (progresssize);
		//	Log.i("ListTable.charactersize   "," "+ListTable.charactersize);
		}
		else if(R.id.spacewidth== seekBar.getId())
		{
			ListTable.spacewidth = (int) (progressspace);
			//Log.i("ListTable.spacewidth  "," "+ListTable.spacewidth);
		}

		
		
		Message message = new Message();   
        message.what =this.REFRESH;   
        this.myHandler.sendMessage(message);
		
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
	        
	        
	        setContentView(R.layout.characterconfigure);
	        backgrounddrawable = null;
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
	        characterText = (TextView) findViewById(R.id.charactertext);
	        mcharacterSeekBar =(SeekBar)findViewById(R.id.charactersize);
	        mspaceSeekBar=(SeekBar)findViewById(R.id.spacewidth);	
	        sampleoftextview=(View)findViewById(R.id.sampleoftextview);
	        
	        int size = ListTable.charactersize;
	        int space = ListTable.spacewidth ;
	        mcharacterSeekBar.setProgress(size);
	        mspaceSeekBar.setProgress((int) (space/0.16));
	        
	        characterText.setText(""+size);
	        charactercolorbutton = (Button)findViewById(R.id.charactercolor);
	        charactercolorbutton.setOnClickListener(charactercolorbuttonlistener);
	        mcharacterSeekBar.setOnSeekBarChangeListener(this);
	        mspaceSeekBar.setOnSeekBarChangeListener(this);

	    }
	    
	    
	    Handler myHandler = new Handler() {   
	        //接收到消息后处理   
	        public void handleMessage(Message msg) {   
	  
	            switch (msg.what) {   
	            case InputCharacterConfigure.REFRESH:   
	            	//注意这里的刷新界面实际上是在UI 线程中执行的 不是另外开启一个线程这里要搞清楚   

	                sampleoftextview.invalidate();
	                break;   
	            }   
	            super.handleMessage(msg);   
	        }   
	    };
		@Override
		public void colorChanged(int color)//2011.10.17 liqiang
		{
			// TODO Auto-generated method stub
			Log.i("debug in color changed","changed color is "+color);
//			Kview.pat.setColor(color);
			ListTable.charactercolor = color;
			Message message = new Message();   
	        message.what =this.REFRESH;   
	        this.myHandler.sendMessage(message);
		}   

}
