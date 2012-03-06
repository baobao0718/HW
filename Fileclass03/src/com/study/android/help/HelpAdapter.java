package com.study.android.help;

import com.study.android.HWtrying.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpAdapter extends ArrayAdapter<String>
{

	private Context context;
	private LayoutInflater mInflater;
	private static String items[] ={"快速入门","详细功能","编辑功能"};
	
	public HelpAdapter(Context context) {
		super(context,R.layout.row, items);
		// TODO Auto-generated constructor stub
		this.context=context; 
		mInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{ 
		View row=mInflater.inflate(R.layout.row, null); 
		TextView label=(TextView)row.findViewById(R.id.label); 
		label.setText(items[position]); 
		ImageView icon=(ImageView)row.findViewById(R.id.icon); 
		icon.setImageResource(R.drawable.icon); 
		return(row); 
	} 

}
