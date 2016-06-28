package com.meditation.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meditation.app.R;
import com.meditation.app.ui.utility.Utility;

/**
 * Created by Catherin on 12/9/2015.
 */
public class StoreDetailFragment extends Fragment implements View.OnClickListener{
ImageView backArrow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_store_detail, container, false);
        // Inflate the layout for this fragment
        init(rootView);
        return rootView;
    }

    public void init(View rootView)
    {
        backArrow=(ImageView)rootView.findViewById(R.id.back);
        backArrow.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.back:
                Utility.go(getActivity(), "storeTag");
                break;

        }
    }
}
