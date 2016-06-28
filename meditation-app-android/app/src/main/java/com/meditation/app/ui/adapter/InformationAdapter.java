package com.meditation.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.meditation.app.R;


public class InformationAdapter extends BaseAdapter implements ListAdapter {
    private Context mContext;
    private Integer[] mThumbIds;
    private String[] themename;

    public InformationAdapter(Context c, Integer[] mThumbIds, String[] themename) {
        this.mContext = c;
        this.themename = themename;
        this.mThumbIds = mThumbIds;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

   static class ViewHolderItem {
       ImageView imageItem;
       TextView title;
   }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ViewHolderItem viewHolder;
        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.info_list_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.imageItem = (ImageView) row.findViewById(R.id.imag);
            viewHolder.title = (TextView) row.findViewById(R.id.title);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) row.getTag();
        }

        viewHolder.imageItem.setImageResource(mThumbIds[position]);
        viewHolder.title.setText(themename[position]);
        return row;
    }
}


