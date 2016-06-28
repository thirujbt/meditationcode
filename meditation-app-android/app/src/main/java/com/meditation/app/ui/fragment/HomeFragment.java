package com.meditation.app.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meditation.app.R;
import com.meditation.app.ui.adapter.ExpandableListAdapter;
import com.meditation.app.ui.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView textHeader;
    private TextView textLogin;
    private TextView textRegister;
    private TextView textMeditationClub;
    private ImageView meditationArrow;
    private TextView textMediationClubContent;
    private TextView textBrainEntertainment;
    private ImageView imgBrainArrow;
    private TextView textBrainEntertainmentContent;
    private TextView textBrainWave;
    private ImageView imgBrainWaveArrow;
    private TextView textBrainWaveContent;
    private TextView textIsochronic;
    private ImageView imgIsochronicArrow;
    private TextView textIsochronicContent;
    private TextView textNeural;
    private ImageView imgNeuralArrow;
    private TextView textNeuralContent;
    private TextView textMood;
    private ImageView imgMoodArrow;
    private TextView textMoodContent;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
private boolean mp3Clicked=false,brainWaveClicked=false,brainFrequencyClicked=false,isochronicClicked=false,neuralClicked=false,moodClicked=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_intro, container, false);
        findViews(rootView);
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);


        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expListView.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });


        return rootView;
    }



    private void findViews(View rootView) {

        expListView = (ExpandableListView) rootView.findViewById(R.id.lvExp);

        textHeader = (TextView)rootView.findViewById(R.id.text_header);
        textLogin = (TextView)rootView.findViewById(R.id.text_login);
        textRegister = (TextView)rootView.findViewById(R.id.text_register);
        textLogin.setOnClickListener(this);
        textRegister.setOnClickListener(this);
       /* textMeditationClub = (TextView)rootView.findViewById(R.id.text_meditation_club);
        meditationArrow = (ImageView)rootView.findViewById(R.id.meditation_arrow);
        textMediationClubContent = (TextView)rootView.findViewById(R.id.text_mediation_club_Content);

        textBrainEntertainment = (TextView)rootView.findViewById(R.id.text_brain_entertainment);
        imgBrainArrow = (ImageView)rootView.findViewById(R.id.img_brain_arrow);
        textBrainEntertainmentContent = (TextView)rootView.findViewById(R.id.text_brain_entertainment_content);

        textBrainWave = (TextView)rootView.findViewById(R.id.text_brain_wave);
        imgBrainWaveArrow = (ImageView)rootView.findViewById(R.id.img_brain_wave_arrow);
        textBrainWaveContent = (TextView)rootView.findViewById(R.id.text_brain_wave_content);

        textIsochronic = (TextView)rootView.findViewById(R.id.text_isochronic);
        imgIsochronicArrow = (ImageView)rootView.findViewById(R.id.img_isochronic_arrow);
        textIsochronicContent = (TextView)rootView.findViewById(R.id.text_isochronic_content);

        textNeural = (TextView)rootView.findViewById(R.id.text_neural);
        imgNeuralArrow = (ImageView)rootView.findViewById(R.id.img_neural_arrow);
        textNeuralContent = (TextView)rootView.findViewById(R.id.text_neural_content);

        textMood = (TextView)rootView.findViewById(R.id.text_mood);
        imgMoodArrow = (ImageView)rootView.findViewById(R.id.img_mood_arrow);
        textMoodContent = (TextView)rootView.findViewById(R.id.text_mood_content);

        //ClickListener

        textMeditationClub.setOnClickListener(this);
        textBrainEntertainment.setOnClickListener(this);
        textBrainWave.setOnClickListener(this);
        textIsochronic.setOnClickListener(this);
        textNeural.setOnClickListener(this);
        textMood.setOnClickListener(this);*/
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(getActivity().getResources().getString (R.string.mediation_club));
        listDataHeader.add(getActivity().getResources().getString(R.string.brain_wave_header));
        listDataHeader.add(getActivity().getResources().getString(R.string.brain_frequencies_header));
        listDataHeader.add(getActivity().getResources().getString (R.string.isochronic_header));
        listDataHeader.add(getActivity().getResources().getString (R.string.neural_simulation));
        listDataHeader.add(getActivity().getResources().getString (R.string.mood));
//        listDataHeader.add(getActivity().getResources().getString (R.string.mediation_club));

        // Adding child data
        List<String> meditaion_club = new ArrayList<String>();
        meditaion_club.add(getActivity().getResources().getString (R.string.intro_mp3));

        List<String> brain_wave_content = new ArrayList<String>();
        brain_wave_content.add(getActivity().getResources().getString(R.string.intro_brain_wave_content));

        List<String>  brain_frequencies_content= new ArrayList<String>();
        brain_frequencies_content.add(getActivity().getResources().getString(R.string.intro_brain_wave_content));

        List<String> isochronic_content = new ArrayList<String>();
        isochronic_content.add(getActivity().getResources().getString(R.string.intro_brain_wave_content));

        List<String> neural_simulation = new ArrayList<String>();
        neural_simulation.add(getActivity().getResources().getString(R.string.intro_brain_wave_content));

        List<String> mood_content = new ArrayList<String>();
        mood_content.add(getActivity().getResources().getString(R.string.intro_brain_wave_content));
        List<String> sample_content= new ArrayList<String>();
        sample_content.add(getActivity().getResources().getString(R.string.intro_brain_frequencies_content));

        listDataChild.put(listDataHeader.get(0),meditaion_club ); // Header, Child data
        listDataChild.put(listDataHeader.get(1), brain_wave_content);
        listDataChild.put(listDataHeader.get(2), brain_frequencies_content);
        listDataChild.put(listDataHeader.get(3), isochronic_content);
        listDataChild.put(listDataHeader.get(4), neural_simulation);
        listDataChild.put(listDataHeader.get(5), mood_content);

    }

   /* @Override
    public void onClick(View v) {
        int id=v.getId();*//*
        switch (id)*//*
        *//*{case R.id.text_meditation_club:
            if(!mp3Clicked) {
                textMediationClubContent.setVisibility(View.VISIBLE);
                meditationArrow.setImageResource(R.drawable.arrow_down_index);
                mp3Clicked=true;
            }else
            {
                textMediationClubContent.setVisibility(View.GONE);
                meditationArrow.setImageResource(R.drawable.arrow_up_index);
                mp3Clicked=false;
            }
            break;
            case R.id.text_brain_entertainment:
                if(!brainWaveClicked) {

                    textBrainEntertainmentContent.setVisibility(View.VISIBLE);
                    imgBrainArrow.setImageResource(R.drawable.arrow_down_index);
                    brainWaveClicked=true;
                }else
                {
                    textBrainEntertainmentContent.setVisibility(View.GONE);
                    imgBrainArrow.setImageResource(R.drawable.arrow_up_index);
                    brainWaveClicked=false;
                }
                break;
            case R.id.text_brain_wave:
                if(!brainFrequencyClicked) {

                    textBrainWaveContent.setVisibility(View.VISIBLE);
                    imgBrainWaveArrow.setImageResource(R.drawable.arrow_down_index);
                    brainFrequencyClicked=true;
                }else
                {
                    textBrainWaveContent.setVisibility(View.GONE);
                    imgBrainWaveArrow.setImageResource(R.drawable.arrow_up_index);
                    brainFrequencyClicked=false;
                }
                break;


            case R.id.text_isochronic:
                if(!isochronicClicked) {

                    textIsochronicContent.setVisibility(View.VISIBLE);
                    imgIsochronicArrow.setImageResource(R.drawable.arrow_down_index);
                    isochronicClicked=true;
                }else
                {
                    textIsochronicContent.setVisibility(View.GONE);
                    imgIsochronicArrow.setImageResource(R.drawable.arrow_up_index);
                    isochronicClicked=false;
                }
                break;


            case R.id.text_neural:
                if(!neuralClicked) {

                    textNeuralContent.setVisibility(View.VISIBLE);
                    imgNeuralArrow.setImageResource(R.drawable.arrow_down_index);
                    neuralClicked=true;
                }else
                {
                    textNeuralContent.setVisibility(View.GONE);
                    imgNeuralArrow.setImageResource(R.drawable.arrow_up_index);
                    neuralClicked=false;
                }
                break;
            case R.id.text_mood:
                if(!moodClicked) {

                    textMoodContent.setVisibility(View.VISIBLE);
                    imgMoodArrow.setImageResource(R.drawable.arrow_down_index);
                    moodClicked=true;
                }else
                {
                    textMoodContent.setVisibility(View.GONE);
                    imgMoodArrow.setImageResource(R.drawable.arrow_up_index);
                    moodClicked=false;
                }
                break;


            case R.id.text_login:
                go("loginTag");
               break;

            case R.id.text_register:
               go("registerTag");
                break;
        }*//*
    }
*/
    public void showContent(TextView textView,ImageView arrow,int visibility,int indicator,boolean clickedStatus)
    {
        textMediationClubContent.setVisibility(View.VISIBLE);
        meditationArrow.setImageResource(R.drawable.arrow_down_index);
        //clicked=true;
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.text_login:
               Utility.go(getActivity(),"loginTag");
                break;

            case R.id.text_register:
               Utility.go(getActivity(),"registerTag");
                break;
        }
    }
}



