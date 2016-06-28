package com.meditation.app.ui.fragment;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.meditation.app.R;
import com.meditation.app.ui.adapter.InformationAdapter;
import com.meditation.app.ui.utility.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Catherin on 12/1/2015.
 */
public class SetttingsFragment extends Fragment {
    ListView infolist;
    InformationAdapter mImInformationAdapter;

    String text[] = {"Profile", "Articles", "Testimonials", "FAQ", "Visit Our Website", "Sync & ReStore", "Share With Friends", "Rate & Review", "Contact Support"};
    Integer image[] = {R.drawable.information_profile, R.drawable.information_articles, R.drawable.information_testimonial, R.drawable.information_faq, R.drawable.information_visit,
            R.drawable.information_share_sync, R.drawable.information_share, R.drawable.information_star, R.drawable.information_customer_service};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_information, container, false);
        infolist = (ListView) rootView.findViewById(R.id.info_listview);
        mImInformationAdapter = new InformationAdapter(getActivity(), image, text);
        infolist.setAdapter(mImInformationAdapter);

        infolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Utility.go(getActivity(), "profileTag");
                        break;
                    case 1:
                        Utility.go(getActivity(), "articlesTag");
                        break;
                    case 2:
                        Utility.go(getActivity(), "testimonialTag");
                        break;
                    case 3:
                        Utility.go(getActivity(), "FAQTag");
                        break;
                    case 4:
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.mp3-meditation-club.com")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=")));
                        }
                        //   Utility. go(getActivity(), "websiteTag");
                        break;
                    case 5:
                        Utility.go(getActivity(), "restoreTag");
                        break;
                    case 6:
                        String message = "Check out this great App - MP3 Meditation Club ";
                        String appName = "Binaural Beats";
                        shareDocument(getActivity(), message, appName);

                        break;
                    case 7:
                        final String appPackageName = "com.missme.kissme"; // getPackageName() from Context or Activity object

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        // Utility.go(getActivity(), "rateTag");
                        break;
                    case 8:
                        Utility.go(getActivity(), "contactTag");
                }

            }
        });


        return rootView;
    }

    public static void shareDocument(Context context, String body, String subject) {

        String appUrl = "http://www.mp3-meditation-club.com";

        PackageManager packageManager = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List<ResolveInfo> resolveInfoList = packageManager
                .queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();

        for (int j = 0; j < resolveInfoList.size(); j++) {
            ResolveInfo resolveInfo = resolveInfoList.get(j);
            String packageName = resolveInfo.activityInfo.packageName;
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setComponent(new ComponentName(packageName,
                    resolveInfo.activityInfo.name));
            intent.setType("text/plain");

            if (packageName.contains("twitter")) {
                intent.putExtra(Intent.EXTRA_TEXT, "\n"
                        + appUrl
                        + context.getPackageName());
            } else if (packageName.contains("facecook")) {
                intent.putExtra(Intent.EXTRA_TEXT, "\n"
                        + appUrl
                        + context.getPackageName());
            } else if (packageName.contains("android.email")
                    || packageName.contains("android.gm")) {
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);

            } else {

                intent.putExtra(Intent.EXTRA_TEXT, body + appUrl);
            }

            intentList
                    .add(new LabeledIntent(intent, packageName, resolveInfo
                            .loadLabel(packageManager), resolveInfo.icon));
        }

        context.startActivity(Intent
                .createChooser(sendIntent, "Binaural Beats").putExtra(
                        Intent.EXTRA_INITIAL_INTENTS,
                        intentList.toArray(new LabeledIntent[intentList
                                .size()])));
    }


    public void popup(){

        final Dialog dialog = new Dialog(getActivity());
       // dialog.setContentView(R.layout.);
        dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_launcher);

      //  Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        /*dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        dialog.show();
    }


        }



