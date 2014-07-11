package com.och.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.och.bean.MapImageLocation;
import com.och.oraclehackathon.R;
 
 
public class ListAdapter extends ArrayAdapter {
 
    int resource;
    String response;
    Context context;
    private LayoutInflater mInflater;
 
    public ListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resource = resource;
        mInflater = LayoutInflater.from(context);
    }
 
    public static class ViewHolder {
        TextView title;
        TextView description;
    }
 
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        //Get the current location object
        MapImageLocation lm = (MapImageLocation) getItem(position);
 
        //Inflate the view
        if(convertView==null)
        {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView
                    .findViewById(R.id.it_location_title);
            holder.description = (TextView) convertView
                    .findViewById(R.id.it_location_description);
 
            convertView.setTag(holder);
 
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.title.setText(lm.getName());
        holder.description.setText(lm.getDescription());
 
        return convertView;
    }
 
}