package com.study.android.HWtrying;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EmailAdapter extends BaseAdapter
{
private final LayoutInflater mInflater;
    
    private final ArrayList<ListItem> mItems = new ArrayList<ListItem>();
    
    public static final int ITEM_FUC1 = 0;
    public static final int ITEM_FUC2 = 1;
    public static final int ITEM_FUC3 = 2;
    public static final int ITEM_FUC4 = 3;
    
    /**
     * Specific item in our list.
     * 
     */
    public class ListItem {
        public final CharSequence text;
        public final String function;
        public final int actionTag;
        
        public ListItem(Resources res, int textResourceId, String functionname, int actionTag) {
            text = res.getString(textResourceId);
            function = functionname;
            this.actionTag = actionTag;
        }
    }
    
    public EmailAdapter(WritebySurfaceView wsv) {
        super(); 
        mInflater = (LayoutInflater) wsv.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        // Create default actions
        Resources res = wsv.getResources();
        
        mItems.add(new ListItem(res, R.string.function1,
                wsv.getString(R.string.function1), ITEM_FUC1));
        mItems.add(new ListItem(res, R.string.function2,
                wsv.getString(R.string.function2), ITEM_FUC2));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = (ListItem) getItem(position);
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.add_list_item, parent, false);
        }
        
        TextView textView = (TextView) convertView;
        textView.setTag(item);
        //.........................
        textView.setText(item.function);
        
        
        return convertView;// 
    }

    public int getCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ListItem item = (ListItem) getItem(position);
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drop_list_item, parent, false);
        }
        
        TextView textView = (TextView) convertView;
        textView.setTag(item);
        
        textView.setText(item.function);
        
        return textView;
	}
    
}
