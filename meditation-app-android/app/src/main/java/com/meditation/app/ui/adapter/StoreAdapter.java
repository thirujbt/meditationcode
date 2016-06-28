package com.meditation.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meditation.app.R;
import com.meditation.app.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Catherin on 12/8/2015.
 */
public class StoreAdapter extends BaseAdapter {



       /* private Context mContext;

        // Constructor
        public StoreAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;

            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else
            {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // Keep all Images in array
        public Integer[] mThumbIds = {
                R.drawable.album_image, R.drawable.album_image,
                R.drawable.album_image
        };*/


        private Context mContext;
        private final String[] web;
        private final int[] Imageid;
    private ArrayList<HashMap<String, Object>> transferRecordMaps;

        public StoreAdapter(Context c,String[] web,int[] Imageid ,ArrayList<HashMap<String,Object>> records) {
            mContext = c;
            this.Imageid = Imageid;
            this.web = web;
            this.transferRecordMaps=records;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return web.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
    static class ViewHolderItem {
        ImageView imageItem;
        TextView title;
    }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;

            ViewHolderItem viewHolder;
            if (row == null) {

                LayoutInflater inflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.store_grid_single, parent, false);
                viewHolder = new ViewHolderItem();
                viewHolder.imageItem = (ImageView) row.findViewById(R.id.grid_image);

                row.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderItem) row.getTag();
            }

            viewHolder.imageItem.setImageResource(Imageid[position]);
//        viewHolder.title.setText(themename[position]);
            viewHolder.imageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "You Clicked at ", Toast.LENGTH_SHORT).show();
                    Intent intentProf = new Intent(mContext, MainActivity.class);
                    intentProf.putExtra("MainActivity", "storeDetailTag");
               /* getActivity().overridePendingTransition(0, 0);
                intentProf.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);*/
                    mContext.startActivity(intentProf);

                }
            });
            return row;
        }
    }











