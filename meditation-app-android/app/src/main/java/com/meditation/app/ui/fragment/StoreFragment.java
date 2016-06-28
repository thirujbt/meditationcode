package com.meditation.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.amazonaws.services.s3.AmazonS3Client;
import com.meditation.app.R;
import com.meditation.app.ui.adapter.StoreAdapter;


/**
 * Created by Catherin on 12/1/2015.
 */
public class StoreFragment extends Fragment {
    ListView infolist;
    StoreAdapter adapter;
    GridView grid;
    String[] web = {
            "Google",
            "Github",
            "Instagram",
            "Facebook",
            "Flickr",
            "Pinterest",
            "Quora",
            "Twitter",
            "Vimeo",
            "WordPress",
            "Youtube",
            "Stumbleupon",
            "SoundCloud",
            "Reddit",
            "Blogger"

    };
    int[] imageId = {
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal,
            R.drawable.album_image_normal

    };
    AmazonS3Client s3Client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_store, container, false);
//        adapter = new StoreAdapter(getActivity(), web, imageId, DownloadSelectionActivity.transferRecordMaps);
        grid = (GridView) rootView.findViewById(R.id.gridview);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();
            }
        });

    /*  *//*  AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        TransferUtility transferUtility = new TransferUtility(s3, getActivity());

        TransferObserver observer;
        observer = transferUtility.download(
                "mp3-meditation-club-demotemp",     *//**//* The bucket to download from *//**//*
                OBJECT_KEY,    *//**//* The key for the object to download *//**//*
                MY_FILE        *//**//* The file to download the object to *//**//*
        );*//*

        File f=null;
        try{
            File dir= Environment.getExternalStorageDirectory();
            f= new File(dir,"test.jpg");
        }
        catch(Exception e)
        {
            Toast.makeText(getActivity(), "Exception", Toast.LENGTH_SHORT).show();
        }
        AWSCredentials creden=new BasicAWSCredentials("mikeb@mp3-meditation-club.com","3mP4$w!2sha");
         s3Client=new AmazonS3Client(creden);


      //  ObjectMetadata obj= s3Client.getObject(new GetObjectRequest("adj-temp","funflick_1.jpg"),f);
                   //downloadFile();

        new DownloadFeedPreviewFileFromURL().execute();

        // Inflate the layout for this fragment
        return rootView;
    }


    public void downloadFile(){

        try{
            String str_FilePathInDevice =Environment.getExternalStorageDirectory()+"/"
                    + "RestoreFolderName" + "/" + "meditaion.mp3";

            File file = new File(str_FilePathInDevice);

            String str_Path = file.getPath().replace(file.getName(), "");
            File filedir = new File(str_Path);

            try {
                filedir.mkdirs();
            } catch (Exception ex1) {
            }
            S3Object object = s3Client.getObject(new GetObjectRequest(
                    "mp3-meditation-club-full-sounds", "aura_healing_ioa4R.mp3"));

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    object.getObjectContent()));
            Writer writer = new OutputStreamWriter(new FileOutputStream(file));

            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                writer.write(line + "\n");
            }
            writer.flush();
            writer.close();
            reader.close();

        }catch (Exception e)
        {
           System.out.println("Exception" +e);
            e.printStackTrace();
        }

    }



    *//*private void beginDownload(String key) {
        // Location to download files from S3 to. You can choose any accessible
        // file.
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + key);

        // Initiate the download
        TransferObserver observer = transferUtility.download(Constants.BUCKET_NAME, key, file);

        // Add the new download to our list of TransferObservers
        observers.add(observer);
        HashMap<String, Object> map = new HashMap<String, Object>();
        // Fill the map with the observers data
        Util.fillMap(map, observer, false);
        // Add the filled map to our list of maps which the simple adapter uses
        transferRecordMaps.add(map);
        observer.setTransferListener(new DownloadListener());

    }*//*

    class DownloadFeedPreviewFileFromURL extends AsyncTask<Void, String, String> {



        *//**
         * Before starting background thread
         * Show Progress Bar Dialog
         *//*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showDialog(progress_bar_type);
        }

        *//**
         * Downloading file in background thread
         *//*
        @Override
        protected String doInBackground(Void... params) {
            try{
                String str_FilePathInDevice =Environment.getExternalStorageDirectory()+"/"
                        + "RestoreFolderName" + "/" + "meditaion.mp3";

                File file = new File(str_FilePathInDevice);

                String str_Path = file.getPath().replace(file.getName(), "");
                File filedir = new File(str_Path);

                try {
                    filedir.mkdirs();
                } catch (Exception ex1) {
                }
                S3Object object = s3Client.getObject(new GetObjectRequest(
                        "mp3-meditation-club-full-sounds", "aura_healing_ioa4R.mp3"));

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        object.getObjectContent()));
                Writer writer = new OutputStreamWriter(new FileOutputStream(file));

                while (true) {
                    String line = reader.readLine();
                    if (line == null)
                        break;
                    writer.write(line + "\n");
                }
                writer.flush();
                writer.close();
                reader.close();

            }catch (Exception e)
            {
                System.out.println("Exception" +e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

          //  copyFeedPreviewDownloadPath = Environment.getExternalStorageDirectory().toString() + fileName;
          //  System.out.println("checking media downloads...." + copyFeedPreviewDownloadPath);

        }

    }*/
        return rootView;
    }
}
