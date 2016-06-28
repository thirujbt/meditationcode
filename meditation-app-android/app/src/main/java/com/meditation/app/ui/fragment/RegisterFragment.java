package com.meditation.app.ui.fragment;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Catherin on 12/9/2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener,OnWebServiceResponse{

    private Button btnSignUp;

    private EditText editSignUpEmailId;
    private EditText editSignUpPwd;
    private EditText editSignUpConfirmPwd;

    private ImageView backArrow;
    private ImageView imgSignUpLogo;

    private TextView textSignUpHeader;
    private TextView textSignInMsg;
    private TextView txtSignIn;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sign_up, container, false);
        findViews(rootView);
        return rootView;
    }

    private void findViews(View rootView) {

        backArrow=(ImageView)rootView.findViewById(R.id.back);
        textSignUpHeader = (TextView)rootView.findViewById( R.id.text_sign_up_header );
        imgSignUpLogo = (ImageView)rootView.findViewById(R.id.img_sign_up_logo);
        editSignUpEmailId = (EditText)rootView.findViewById(R.id.edit_sign_up_email_id);
        editSignUpPwd = (EditText)rootView.findViewById(R.id.edit_sign_up_pwd);
        editSignUpConfirmPwd = (EditText)rootView.findViewById(R.id.edit_sign_up_confirm_pwd);
        btnSignUp = (Button)rootView.findViewById(R.id.btn_sign_up);
        textSignInMsg = (TextView)rootView.findViewById(R.id.text_sign_in_msg);
        txtSignIn = (TextView)rootView.findViewById(R.id.txt_sign_in);
        //Click Listener
        txtSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener( this );
        backArrow.setOnClickListener(this);
}
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.back:
                Utility.go(getActivity(), "homePageTag");
                break;
            case R.id.btn_sign_up:
                validate_Fields();
               // Utility.go(getActivity(),"homeTag");
                break;
            case R.id.txt_sign_in:
                Utility.go(getActivity(),"loginTag");
                break;
        }
    }
    private void validate_Fields() {
        if(Utility.isBlank(editSignUpEmailId.getText().toString())) {
            Utility.showAlert(getActivity(), AppConstants.ENTER_EMAIL, false);
        } else if(!Utility.validateEmailId(editSignUpEmailId.getText().toString())){
            Utility.showAlert(getActivity(), AppConstants.ENTER_VALID_EMAIL, false);
        }else if(Utility.isBlank(editSignUpPwd.getText().toString())) {
            Utility.showAlert(getActivity(), AppConstants.ENTER_PASSWORD, false);
        } else if(Utility.isBlank(editSignUpConfirmPwd.getText().toString())) {
            Utility.showAlert(getActivity(), AppConstants.ENTER_CONFIRM_PASSWORD, false);
        } else if(!editSignUpPwd.getText().toString().equals(editSignUpConfirmPwd.getText().toString())) {
            Utility.showAlert(getActivity(), AppConstants.PASSWORD_NOT_MATCH, false);
        } else {
            try {
                /*JSONObject jsonObj = new JSONObject();
                jsonObj.put(JsonConstants.ACTION,JsonConstants.ACTION_VALUE);
                jsonObj.put(JsonConstants.EMAIL, editSignUpEmailId.getText().toString());
                jsonObj.put(JsonConstants.PASSWORD, editSignUpPwd.getText().toString());
                jsonObj.put(JsonConstants.SECRET_KEY,JsonConstants.SECRET_KEY_VALUE);
*/
                BasicNameValuePair action = new BasicNameValuePair(JsonConstants.ACTION, JsonConstants.ACTION_VALUE_CREATE);
                BasicNameValuePair email = new BasicNameValuePair(JsonConstants.EMAIL, editSignUpEmailId.getText().toString());
                BasicNameValuePair pwd = new BasicNameValuePair(JsonConstants.PASSWORD, editSignUpPwd.getText().toString());
                BasicNameValuePair secretKey = new BasicNameValuePair(JsonConstants.SECRET_KEY, JsonConstants.SECRET_KEY_VALUE);

                List<NameValuePair> loginPageList = new ArrayList<NameValuePair>();
                loginPageList.add(action);
                loginPageList.add(email);
                loginPageList.add(pwd);
                loginPageList.add(secretKey);


                System.out.println("checking register request.." + loginPageList);

                Utility.showPDialog(getActivity());

               /* if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                    new JsonAsync(this, jsonObj.toString(), AppConstants.REGISTER_CALL).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, AppUrls.BASE_URL);
                else
                    new JsonAsync(this, jsonObj.toString(), AppConstants.REGISTER_CALL).execute(AppUrls.BASE_URL);*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    new GpsAsyncTask(getActivity(), AppUrls.BASE_URL, RegisterFragment.this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loginPageList);
                else
                    new GpsAsyncTask(getActivity(), AppUrls.BASE_URL,RegisterFragment.this).execute(loginPageList);



            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onResponseReceived(String response, String methodName) {

        Utility.dismissPDialog(getActivity());
        String error="";
        String result="";
       // boolean result=false;
        if(!Utility.isBlank(response))
        {
        System.out.println("Register response" + response);
        try {
            JSONArray mJsonObject = new JSONArray(response);

            for(int i=0;i<mJsonObject.length();i++)
            {
                JSONObject child= mJsonObject.getJSONObject(i);
                error = child.getString(JsonConstants.ERROR);
                result = child.getString(JsonConstants.RESULT);
            }

   if(result.equalsIgnoreCase("true"))

       Utility.showAlert(getActivity(),"Successfully Registered", "loginTag");
   else

    Utility.showAlert(getActivity(),error,false);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    }


}
