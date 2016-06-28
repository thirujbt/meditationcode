package com.meditation.app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.meditation.app.R;
import com.meditation.app.ui.activity.MainActivity;
import com.meditation.app.ui.fragment.DownloadSelectionFragment;
import com.meditation.app.ui.utility.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bean.GridViewItem;


/**
 * Created by vino on 21-Dec-15.
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<GridViewItem> mItems;
    DownloadSelectionFragment dm;
    private TransferUtility transferUtility;
    private List<String> myList;
    File file_list = new File(Environment.getExternalStorageDirectory().toString() + "/NATPUApp/");
    File file;
    ArrayList<String> path;

    public GridAdapter(Context context, List<GridViewItem> items) {
        mContext = context;
        mItems = items;
        dm = new DownloadSelectionFragment();
        transferUtility = Util.getTransferUtility(mContext);
       // load();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.store_grid_single, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();

            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.grid_image);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.download = (Button) convertView.findViewById(R.id.downlaod);
            viewHolder.play = (Button) convertView.findViewById(R.id.play);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        GridViewItem item = mItems.get(position);
        viewHolder.tvTitle.setText(item.getName());
        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // beginDownload(mItems.get(position).getName(),position);
            }
        });

        viewHolder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"TEST",Toast.LENGTH_SHORT).show();
                beginDownload(mItems.get(position).getName(), position);
               // load();
            }
        });
        viewHolder.ivIcon.setOnClickListener(new View.OnClickListener() {
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

        return convertView;
    }


    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        Button download, play;

    }

    private void beginDownload(String key, int posi) {
        // Location to download files from S3 to. You can choose any accessible
        // file.
        file = new File(Environment.getExternalStorageDirectory().toString() + "/Meditation" + "/" + key);

        // Initiate the download

      //  TransferObserver observer = transferUtility.download(Constants.BUCKET_NAME, key, file);



        if (file.getName().equals(key)) {

            try {
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                mContext.startActivity(intent);
            }catch (Exception e)
            {

            }

        }



    }



}



