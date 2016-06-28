package com.meditation.app.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.meditation.app.R;
import com.meditation.app.ui.async.GpsAsyncTask;
import com.meditation.app.ui.constants.AppConstants;
import com.meditation.app.ui.constants.AppUrls;
import com.meditation.app.ui.constants.JsonConstants;
import com.meditation.app.ui.listener.OnWebServiceResponse;
import com.meditation.app.ui.utility.Utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Catherin on 12/10/2015.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener,OnWebServiceResponse

{
    ImageView backArrow;
    private TextView txt_email_id;
    private TextView txt_change_password;
    private EditText edit_email_id;
    private EditText edit_change_password;

    Point p;
    View promptsView;

    public ProfileFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        init(rootView);
        return rootView;
    }

    public void init(View rootView) {
        txt_email_id = (TextView) rootView.findViewById(R.id.text_email_id);
        txt_change_password = (TextView) rootView.findViewById(R.id.text_change_password);
        edit_email_id = (EditText) rootView.findViewById(R.id.edit_email_id);
        edit_change_password = (EditText) rootView.findViewById(R.id.edit_password);

        txt_change_password.setOnClickListener(this);

        backArrow = (ImageView) rootView.findViewById(R.id.back);
        backArrow.setOnClickListener(this);

       if(isPreferenceAvailable(getActivity(),AppConstants.EMAIL_ID))
       {
           String email_id=getContentFromSharedPreference(getActivity(),AppConstants.EMAIL_ID);
           String password = getContentFromSharedPreference(getActivity(),AppConstants.PASSWORD);
           edit_email_id.setText(email_id);
           edit_change_password.setText(password);
       }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Utility.go(getActivity(), "settingsTag");
                break;
            case R.id.text_change_password:
                changePassword();
                break;
        }
    }

    public void changePassword(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View promptsView = inflater.inflate(R.layout.test_layout, null);
       alertDialog.setView(promptsView);
       final EditText old_pass=(EditText)promptsView.findViewById(R.id.edit_old_password);
       final  EditText new_pass=(EditText)promptsView.findViewById(R.id.edit_new_password);
       final EditText confirm_pass=(EditText)promptsView.findViewById(R.id.edit_confirm_new_password);
        TextView  save=(TextView)promptsView.findViewById(R.id.save);
        TextView  cancel=(TextView)promptsView.findViewById(R.id.cancel);

         save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preference=Utility.getSharedPreferences(getActivity(),AppConstants.SHARED_PREFERENCE_LOGIN_DETAILS);
                SharedPreferences.Editor login_editor= preference.edit();
                if(isPreferenceAvailable(getActivity(),AppConstants.PASSWORD)) {

                    String currentPassword=getContentFromPreference(AppConstants.PASSWORD);
                    if(!Utility.isBlank(old_pass.getText().toString()))
                       Utility.showAlert(getActivity(),"Enter Current Password",false);
                    else if(!Utility.isBlank(new_pass.getText().toString())){
                        Utility.showAlert(getActivity(),"Enter New Password",false);
                    } else if(!Utility.isBlank(confirm_pass.getText().toString())) {
                        Utility.showAlert(getActivity(), "Enter New Password", false);
                    }else if(!currentPassword.equals(old_pass.getText().toString()) )
                        Utility.showAlert(getActivity(),"Invalid Current Password ",false);
                }else if(new_pass.getText().toString().equals(confirm_pass.getText().toString())){
                    Utility.showAlert(getActivity(),AppConstants.PASSWORD_NOT_MATCH,false);
                }else {
                    try {
                        BasicNameValuePair action = new BasicNameValuePair(JsonConstants.ACTION, JsonConstants.ACTION_VALUE_CHANGE_PASSWORD);
                        BasicNameValuePair email = new BasicNameValuePair(JsonConstants.EMAIL, preference.getString(AppConstants.EMAIL_ID, ""));
                        BasicNameValuePair pwd = new BasicNameValuePair(JsonConstants.PASSWORD, old_pass.getText().toString());
                        BasicNameValuePair secretKey = new BasicNameValuePair(JsonConstants.SECRET_KEY, JsonConstants.SECRET_KEY_VALUE);
                        BasicNameValuePair newPassword = new BasicNameValuePair(JsonConstants.NEWPASSWORD, new_pass.getText().toString());

                        List<NameValuePair> changePasswordList = new ArrayList<NameValuePair>();
                        changePasswordList.add(action);
                        changePasswordList.add(email);
                        changePasswordList.add(pwd);
                        changePasswordList.add(secretKey);
                        changePasswordList.add(newPassword);

                        Utility.showPDialog(getActivity());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                            new GpsAsyncTask(getActivity(), AppUrls.CHANGE_PASSWORD_URL, ProfileFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, changePasswordList);
                        else
                            new GpsAsyncTask(getActivity(), AppUrls.CHANGE_PASSWORD_URL, ProfileFragment.this).execute(changePasswordList);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
         }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });

            }
        });


/*
/*/
/** When positive (yes/ok) is clicked *//*
*/
/*
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {





            }
        });

*/
/* When negative (No/cancel) button is clicked*//*
*/
/*
        alertDialog.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // Your custom code
            }
        });*//*


*/

        alertDialog.show();
    }
    public void parseJson()
    {


    }

    public boolean isPreferenceAvaliable(Activity activity, String sharedPreferenceFile,String key) {

        SharedPreferences preference =  Utility.getSharedPreferences(activity,sharedPreferenceFile);
        if(preference.contains(key))
        {
            return true;
        }
        return false;

    }


    public String getContentFromPreference(String key)

    {
        String result="";
        SharedPreferences preferences= Utility.getSharedPreferences(getActivity(),AppConstants.SHARED_PREFERENCE_LOGIN_DETAILS);
        if(preferences.contains(key))
        {
            result= preferences.getString(key,"");
        }
        return result ;
    }


    public static boolean isPreferenceAvailable(Activity activity, String key) {

        SharedPreferences preferences = Utility.getSharedPreferences(activity, AppConstants.SHARED_PREFERENCE_LOGIN_DETAILS);
        if (preferences.contains(key)) {
            return true;
        }
        return false;
    }


    public static String getContentFromSharedPreference(Activity activity, String key) {
        SharedPreferences preferences = Utility.getSharedPreferences(activity, AppConstants.SHARED_PREFERENCE_LOGIN_DETAILS);
        return preferences.getString(key, "");
    }

    @Override
    public void onResponseReceived(String response, String methodName) {

        if(response!=null)
        {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String statuscode=jsonObject.getString(JsonConstants.STATUSCODE);
                if(statuscode.equalsIgnoreCase("200"))
                Utility.showAlert(getActivity(), "Password has been Changed Successfully", false);
            }catch (Exception e)
            {

            }
        }


    }

    /*private void showPopup(final Activity context) {
        int popupWidth = 200;
        int popupHeight = 150;

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup_password);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.test_layout, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setHeight(popupHeight);
        popup.setWidth(popupWidth);
        popup.setFocusable(true);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY,500 ,500 );


        // Getting a reference to Close button, and close the popup when clicked.
       *//* Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });*//*
    }
*/

}





