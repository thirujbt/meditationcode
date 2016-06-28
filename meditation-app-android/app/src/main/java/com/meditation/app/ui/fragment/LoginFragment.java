package com.meditation.app.ui.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.meditation.app.R;
import com.meditation.app.ui.adapter.GridAdapter;
import com.meditation.app.ui.async.GpsAsyncTask;
import com.meditation.app.ui.constants.AppConstants;
import com.meditation.app.ui.constants.AppUrls;
import com.meditation.app.ui.constants.JsonConstants;
import com.meditation.app.ui.listener.OnWebServiceResponse;
import com.meditation.app.ui.utility.Constants;
import com.meditation.app.ui.utility.Util;
import com.meditation.app.ui.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.GridViewItem;

/**
 * Created by Catherin on 12/9/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, OnWebServiceResponse {


    private ImageView backArrow;

    private EditText editSignInEmail;
    private EditText editSignInPassword;

    private Button btnSignIn;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_signin, container, false);
        findViews(rootView);
        return rootView;
    }

    private void findViews(View rootView) {

        backArrow = (ImageView) rootView.findViewById(R.id.img_sign_in_back);
        editSignInEmail = (EditText) rootView.findViewById(R.id.edit_sign_in_email);
        editSignInPassword = (EditText) rootView.findViewById(R.id.edit_sign_in_password);
        btnSignIn = (Button) rootView.findViewById(R.id.btn_sign_in);

        //Click Listener
        backArrow.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_sign_in_back:
                Utility.go(getActivity(), "homePageTag");
                break;
            case R.id.btn_sign_in:
                validate_Fields();
                // Utility.go(getActivity(),"homeTag");
                break;
        }

    }

    private void validate_Fields() {
        if (Utility.isBlank(editSignInEmail.getText().toString())) {
            Utility.showAlert(getActivity(), AppConstants.ENTER_EMAIL, false);
        } else if (!Utility.validateEmailId(editSignInEmail.getText().toString())) {
            Utility.showAlert(getActivity(), AppConstants.ENTER_VALID_EMAIL, false);
        } else if (Utility.isBlank(editSignInPassword.getText().toString())) {
            Utility.showAlert(getActivity(), AppConstants.ENTER_PASSWORD, false);
        } else {
            try {

                /*JSONObject jsonObj = new JSONObject();
                jsonObj.put(JsonConstants.ACTION,JsonConstants.ACTION_VALUE);
                jsonObj.put(JsonConstants.EMAIL, editSignInEmail.getText().toString());
                jsonObj.put(JsonConstants.PASSWORD, editSignInPassword.getText().toString());
                jsonObj.put(JsonConstants.SECRET_KEY,JsonConstants.SECRET_KEY_VALUE);*/

                BasicNameValuePair action = new BasicNameValuePair(JsonConstants.ACTION, JsonConstants.ACTION_VALUE_LOGIN);
                BasicNameValuePair email = new BasicNameValuePair(JsonConstants.EMAIL, editSignInEmail.getText().toString());
                BasicNameValuePair pwd = new BasicNameValuePair(JsonConstants.PASSWORD, editSignInPassword.getText().toString());
                BasicNameValuePair secretKey = new BasicNameValuePair(JsonConstants.SECRET_KEY, JsonConstants.SECRET_KEY_VALUE);

                List<NameValuePair> loginPageList = new ArrayList<NameValuePair>();
                loginPageList.add(action);
                loginPageList.add(email);
                loginPageList.add(pwd);
                loginPageList.add(secretKey);


                System.out.println("checking  login request.." + loginPageList);

                Utility.showPDialog(getActivity());

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new JsonAsync(getActivity(), jsonObj.toString(), AppConstants.LOGIN_CALL).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, AppUrls.BASE_URL);
                else
                    new JsonAsync(getActivity(), jsonObj.toString(), AppConstants.LOGIN_CALL).execute(AppUrls.BASE_URL);
*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new GpsAsyncTask(getActivity(), AppUrls.BASE_URL, LoginFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loginPageList);
                else
                    new GpsAsyncTask(getActivity(), AppUrls.BASE_URL, LoginFragment.this).execute(loginPageList);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResponseReceived(String response, String methodName) {

        System.out.println("Register response" + response);
        Utility.dismissPDialog(getActivity());
        String error="";
        String result="";

//        boolean result = false;
        if (!Utility.isBlank(response)) {
            System.out.println("Register response" + response);
            try {
                JSONArray mJsonObject = new JSONArray(response);

                for (int i = 0; i < mJsonObject.length(); i++) {
                    JSONObject child = mJsonObject.getJSONObject(i);
                    error = child.getString(JsonConstants.ERROR);
                    result = child.getString(JsonConstants.RESULT);
                }
                if (result.equalsIgnoreCase("true")) {
//                    Utility.showAlert(getActivity(),"Successfully Logged",false);
                    SharedPreferences loginPreference= Utility.getSharedPreferences(getActivity(), AppConstants.SHARED_PREFERENCE_LOGIN_DETAILS);
                    SharedPreferences.Editor editor =loginPreference.edit();
                    editor.putString(AppConstants.EMAIL_ID,editSignInEmail.getText().toString());
                    editor.putString(AppConstants.PASSWORD,editSignInPassword.getText().toString());
                    editor.commit();


                    initData();
                    initUI();
                    new GetFileListTask().execute();

                    Utility.go(getActivity(),"storeTag");
                }
                else
                {
                    Utility.showAlert(getActivity(),error,false);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println(e);
            }

        }


    }






    private void initData() {
        // Gets the default S3 client.
        s3 = Util.getS3Client(getActivity());
        transferRecordMaps = new ArrayList<HashMap<String, Object>>();
    }

    private void initUI() {

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
}






