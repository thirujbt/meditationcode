package com.meditation.app.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.meditation.app.R;
import com.meditation.app.ui.utility.Utility;

import java.util.List;

/**
 * Created by Catherin on 12/9/2015.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    ImageView backArrow;
    private Button btn_submit;
    private EditText from;
    private EditText comment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_contact, container, false);
        init(rootView);
        return rootView;
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {from.getText().toString()};

        String comments = comment.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.toString();
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        emailIntent.putExtra(Intent.EXTRA_TEXT,TO);
        emailIntent.putExtra(Intent.EXTRA_TEXT, comments);
        final PackageManager pm = getActivity().getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for (final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                    info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        startActivity(emailIntent);


       /* try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
           getActivity(). finish();
            Log.i("Finished sending email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }*/
    }


    public void sendMail() {
       /* Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + from.getText().toString()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, comment.getText().toString());
       // emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        //emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

        startActivity(Intent.createChooser(emailIntent, "Chooser Title"));*/


    }


    public void init(View rootView) {
        from = (EditText) rootView.findViewById(R.id.from);
        comment = (EditText) rootView.findViewById(R.id.comment);

        btn_submit = (Button) rootView.findViewById(R.id.info_contact);
        btn_submit.setOnClickListener(this);
        backArrow = (ImageView) rootView.findViewById(R.id.back);
        backArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Utility.go(getActivity(), "settingsTag");
                break;
            case R.id.info_contact:
                sendEmail();
                break;


        }
    }
}
