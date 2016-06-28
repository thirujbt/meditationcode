package com.meditation.app.ui.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.meditation.app.R;
import com.meditation.app.ui.activity.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Catherin on 12/10/2015.
 */
public  final class Utility {

    private static ProgressDialog mDialog = null;

public static void go(Activity activity,String value)
{
    Intent intent = new Intent(activity, MainActivity.class);
    activity.overridePendingTransition(0, 0);
    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra("MainActivity", value);
    activity.startActivity(intent);
    activity.finish();
}
    public static boolean isBlank(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
    public static final boolean validateEmailId(String email) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void showAlert(final Activity activity, final String msg, final boolean finish) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity, R.style.CustomAlertDialogStyle);
                    mBuilder
                            .setMessage(msg)
                            .setCancelable(false)
                            .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (finish) {
                                        activity.finish();
                                    }
                                }
                            });

                    AlertDialog mErrorDialog = mBuilder.create();
                    mErrorDialog.show();
                }

            });
        }
    }
    public static SharedPreferences getSharedPreferences(Context activity, String prefesName) {
        return activity.getSharedPreferences(prefesName, Context.MODE_PRIVATE);
    }


    public static void showAlert(final Activity activity, final String msg, final String tag) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity, R.style.CustomAlertDialogStyle);
                    mBuilder
                            .setMessage(msg)
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   go(activity,tag);
                                }
                            });

                    AlertDialog mErrorDialog = mBuilder.create();
                    mErrorDialog.show();
                }

            });
        }
    }
    public static void showPDialog(final Activity activity) {
        if (activity != null)
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (mDialog == null) {
                        mDialog = new ProgressDialog(activity, R.style.CustomAlertDialogStyle);
                        mDialog.setCancelable(false);
                        mDialog.setIndeterminate(false);
                        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                mDialog = null;
                            }
                        });
                    }
                    if (!mDialog.isShowing()) {
                        Log.e(getClass().getSimpleName(), "Hello, Showing");
                        mDialog.show();

                    }

                }
            });
    }
    public static boolean isOnline(final Activity activity) {

        if(activity != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null &&
                    cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
                return true;
            } else {
                showAlert(activity, "Check your Internet Connection", false);
                return false;
            }
        }
        return false;

    }

    public static void dismissPDialog(final Activity activity) {
        if (activity != null)
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (mDialog != null && mDialog.isShowing()) {
                            Log.e("Dismiss", "Hello, Dismiss");
                            mDialog.dismiss();
                            mDialog = null;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

    }
public static void loadWebPage(final Activity activity,WebView webView, String url , final String screenTitle)
{
    WebSettings settings = webView.getSettings();
    settings.setJavaScriptEnabled(true);
    webView.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);

    final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();

  final ProgressDialog progressBar = ProgressDialog.show(activity, screenTitle, "Loading...");

    webView.setWebViewClient(new WebViewClient() {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
//            Log.i(TAG, "Finished loading URL: " + url);
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            Log.e(TAG, "Error: " + description);
            Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(description);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
        }
    });
}

}
