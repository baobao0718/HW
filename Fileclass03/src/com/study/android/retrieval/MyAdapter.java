package com.study.android.retrieval;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.study.android.HWtrying.R;
import com.study.android.basicData.DataUnit;
import com.study.android.data.ListTable;

public class MyAdapter extends BaseAdapter 
{
	private Context context;
	private ArrayList<ArrayList<DataUnit>> coll;   
	private LayoutInflater mInflater;
	public static ArrayList<DataUnit> templist = new ArrayList<DataUnit>();
	
	@SuppressWarnings("unchecked")
	public MyAdapter(Context context,ArrayList<ArrayList<DataUnit>> arraylist)
	{
		this.context=context;
		coll = (ArrayList<ArrayList<DataUnit>>) arraylist.clone();
		mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return coll.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return coll.get(arg0);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub 
		//ListTable.templist = (ArrayList<DataUnit>) coll.get(position).clone();
		templist = (ArrayList<DataUnit>) coll.get(position).clone();
		String notitle="";
//        Log.i("debug in getview1",""+templist.size());
        if(templist.size()==0)
        	notitle = "无标题";
//		Log.i("debug in getview2",""+templist.size());
//		Log.i("adapter","position is "+position);
		ViewHolder viewHolder = null;
		if (null == convertView) 
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.whytry, null);
		
	        viewHolder.label = (TextView) convertView.findViewById(R.id.ItemTitle);
	        viewHolder.notitle = (TextView) convertView.findViewById(R.id.NoTitle);
	        String time=ListTable.createdfiletime.get(position);
	        StringBuffer   timebuf   =   new   StringBuffer(time); 
	        timebuf.insert(4,".");
	        timebuf.insert(7,".");
	        
	        StringBuffer notitlebuf = new StringBuffer(notitle);
			//viewHolder.label.setText(ListTable.createdfiletime.get(position));
	        viewHolder.label.setText(timebuf.toString());
		    viewHolder.label.setTextColor(Color.rgb(255,80,0));
		    viewHolder.notitle.setText(notitlebuf.toString());
		    viewHolder.notitle.setTextColor(Color.rgb(255,80,0));
		    convertView.setTag(viewHolder);
//		    convertView.setBackgroundColor(Color.WHITE);
		    //2011.05.18 liqiang
		    if (this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
		    { 
		    	if(position%2 == 0)
			    	convertView.setBackgroundResource(R.drawable.landfilecolorlist1);
			    else if(position%2 == 1)
			    	convertView.setBackgroundResource(R.drawable.landfilecolorlist2);
			} 
			else if (this.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
			{ 
				if(position%2 == 0)
			    	convertView.setBackgroundResource(R.drawable.portfilecolorlist1);
			    else if(position%2 == 1)
			    	convertView.setBackgroundResource(R.drawable.portfilecolorlist2);
			}		    
		  //2011.05.18 liqiang
		    if(ShowRetrieve.locationshowofbutton!=-1&&ShowRetrieve.locationshowofbutton==position)
		    {
		    	convertView.findViewById(R.id.delete).setVisibility(View.VISIBLE);
		    }}
	    
	    return convertView; 
	}
	
	private static class ViewHolder {
		TextView label;
		TextView notitle;//2011.12.21 liqiang
//		ImageView image;
		//	Retrieveview retrieview;
		}


}
