package com.meditation.app.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meditation.app.R;

/**
 * Created by Catherin on 12/9/2015.
 */
public class RateFragment  extends Fragment implements View.OnClickListener{
  ImageView backArrow;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.activity_rate,container,false);
        init(rootView);
        return rootView;
    }
    public void init(View rootView)
    {
        backArrow=(ImageView)rootView.findViewById(R.id.rate_back);
        backArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.rate_back:

//               final String appPackageName =getActivity(). getPackageName(); // getPackageName() from Context or Activity object
              final String appPackageName = "com.missme.kissme"; // getPackageName() from Context or Activity object

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

                //Utility.go(getActivity(), "settingsTag");
                break;
        }

    }
}
