package com.study.android.help;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

import com.study.android.HWtrying.R;

public class QuickStart extends Activity implements MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener
{
	private Drawable backgrounddrawable;
	private String classname ;
	private VideoView mVideoView = null; 
	private int mPositionWhenPaused = -1;
	public void onCreate(Bundle savedInstanceState)
	{
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.quickstart);
	        Intent conf = getIntent();
	        Bundle bundle = conf.getExtras();
	        classname = bundle.getString("classname");
	        backgrounddrawable = this.getResources().getDrawable(R.drawable.portbground);
	        this.getWindow().setBackgroundDrawable(backgrounddrawable);
	        String MEDIA_PATH = new String("/sdcard/movieplay.mp4");
	        mVideoView = (VideoView) this.findViewById(R.id.quickstartvideoviewid);   
	        MediaController mc = new MediaController(QuickStart.this); 
	        mVideoView.setVideoPath(MEDIA_PATH);
	        mVideoView.setMediaController(mc);  
	        mVideoView.requestFocus();   
	}
	@Override
	public void onStart() {
    	mVideoView.start();

    	super.onStart();
    }
	@Override
    public void onPause() {
    	mPositionWhenPaused = mVideoView.getCurrentPosition();
    	mVideoView.stopPlayback();
    	super.onPause();
    }
    @Override
    public void onResume() {
    	if(mPositionWhenPaused >= 0) {
    		mVideoView.seekTo(mPositionWhenPaused);
    		mPositionWhenPaused = -1;
    	}

    	super.onResume();
    }
    @Override
	public boolean onError(MediaPlayer mp, int what, int extra)
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onCompletion(MediaPlayer mp)
	{
		// TODO Auto-generated method stub
		this.finish();
	}
	@Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        
        this.getWindow().setBackgroundDrawable(backgrounddrawable);       
        setContentView(R.layout.quickstart);	  
        String MEDIA_PATH = new String("/sdcard/movieplay.mp4");
        mVideoView = (VideoView) this.findViewById(R.id.quickstartvideoviewid);   
        MediaController mc = new MediaController(QuickStart.this); 
        mVideoView.setVideoPath(MEDIA_PATH);
        mVideoView.setMediaController(mc);  
        mVideoView.requestFocus();
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		 {
			backgrounddrawable = null;
			Intent fanhui = new Intent(QuickStart.this,Help.class);        //´ò¿ªÍøÒ³Á´½Ó
			Bundle bundle = new Bundle();
  			bundle.putString("classname", classname);
  			fanhui.putExtras(bundle);
	  		startActivity(fanhui);
	  		QuickStart.this.finish();
			return true;
		 }
		 else
			 return super.onKeyDown(keyCode,event);
	}
}
