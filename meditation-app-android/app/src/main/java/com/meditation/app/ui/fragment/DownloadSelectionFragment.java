/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.meditation.app.ui.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.meditation.app.R;
import com.meditation.app.ui.adapter.GridAdapter;
import com.meditation.app.ui.utility.Constants;
import com.meditation.app.ui.utility.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.GridViewItem;


/**
 * DownloadSelectionActivity displays a list of files in the bucket. Users can
 * select a file to download.
 */
public class DownloadSelectionFragment extends Fragment {

    // The S3 client used for getting the list of objects in the bucket
    private AmazonS3Client s3;
    // An adapter to show the objects
    // private SimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String, Object>> transferRecordMaps;
    public static int RESULT_OK = 002;
    GridView grid;
    GridViewItem mGridViewItem;
    GridAdapter madapter;
    private List<GridViewItem> mList;
    private List<GridViewItem> Copy_mList;
    File file;
    private TransferUtility transferUtility;
    ArrayList<String> myList;
    int i = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_store, container, false);
        initData();
        initUI(rootView);
        timmer();
        new GetFileListTask().execute();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("key", (String) transferRecordMaps.get(position).get("key"));
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
            }
        });

        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try{
                    int vH = view.getHeight();
                    int topPos = view.getChildAt(0).getTop();
                    int bottomPos = view.getChildAt(visibleItemCount - 1).getBottom();
                    switch(view.getId()) {
                        case R.id.gridview:
                            if(firstVisibleItem == 0 && topPos == 0) {

                            }
                            if(firstVisibleItem + visibleItemCount == totalItemCount && vH >= bottomPos) {

                            }
                            break;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();

        // Refresh the file list.
        timmer();


    }

    private void initData() {
        // Gets the default S3 client.
        s3 = Util.getS3Client(getActivity());
        transferRecordMaps = new ArrayList<HashMap<String, Object>>();
    }

    private void initUI(View rootView) {
        grid = (GridView) rootView.findViewById(R.id.gridview);
        transferUtility = Util.getTransferUtility(getActivity());
       /* madapter = new GridAdapter(getActivity(), Copy_mList);
        grid.setAdapter(madapter);*/
    }


    /**
     * This async task queries S3 for all files in the given bucket so that they
     * can be displayed on the screen
     */
    private class GetFileListTask extends AsyncTask<Void, Void, Void> {
        // The list of objects we find in the S3 bucket
        private List<S3ObjectSummary> s3ObjList;

        // A dialog to let the user know we are retrieving the files
        private ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(getActivity(),
                    getString(R.string.refreshing),
                    getString(R.string.please_wait));
        }

        @Override
        protected Void doInBackground(Void... inputs) {
            // Queries files in the bucket from S3.
            s3ObjList = s3.listObjects(Constants.DEMO_BUCKET_NAME).getObjectSummaries();
            mList = new ArrayList<>();
            Copy_mList = new ArrayList<GridViewItem>();
            transferRecordMaps.clear();
            for (S3ObjectSummary summary : s3ObjList) {
                mGridViewItem = new GridViewItem();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("key", summary.getKey());

                mGridViewItem.setLink(summary.getKey());
                mGridViewItem.setName(summary.getKey());

                mList.add(mGridViewItem);

                transferRecordMaps.add(map);

            }
            beginDownload();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            // simpleAdapter.notifyDataSetChanged();

           /* madapter = new GridAdapter(getActivity(), mList);
            grid.setAdapter(madapter);*/
            dialog.dismiss();

            madapter = new GridAdapter(getActivity(), Copy_mList);
            grid.setAdapter(madapter);

            load();

        }
    }

    private void beginDownload() {
        // Location to download files from S3 to. You can choose any accessible
        // file.
        for (int i = 0; i < mList.size(); i++) {
            file = new File(Environment.getExternalStorageDirectory().toString() + "/Meditation" + "/" + mList.get(i).getName());

            // Initiate the download
            if (!file.exists()) {
                TransferObserver observer = transferUtility.download(Constants.DEMO_BUCKET_NAME, mList.get(i).getName(), file);
            }
        }

    }

    public void load() {
        myList = new ArrayList<String>();
        Copy_mList = new ArrayList<>();
        File file_list = new File(Environment.getExternalStorageDirectory().toString() + "/Meditation/");
        if (file_list.exists()) {
            File list[] = file_list.listFiles();
            for (int i = 0; i < list.length; i++) {
                myList.add(list[i].getName());
            }
        }
        if (myList.size() > 0) {

            for (int i = 0; i < myList.size(); i++) {
                for (int j = 0; j < myList.size(); j++) {
                    if (((myList.get(i))).equals(mList.get(j).getName()))
                        Copy_mList.add(mList.get(j));
                }
            }
            madapter = new GridAdapter(getActivity(), Copy_mList);
            grid.setAdapter(madapter);
        }
    }

    void timmer() {

        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load();
                    }
                });


                start();
            }
        }.start();
    }




}
