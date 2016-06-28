package com.meditation.app.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.meditation.app.R;
import com.meditation.app.ui.fragment.ArticlesFragment;
import com.meditation.app.ui.fragment.ContactFragment;
import com.meditation.app.ui.fragment.DownloadSelectionFragment;
import com.meditation.app.ui.fragment.FAQFragment;
import com.meditation.app.ui.fragment.HomeFragment;
import com.meditation.app.ui.fragment.LoginFragment;
import com.meditation.app.ui.fragment.PlaylistFragment;
import com.meditation.app.ui.fragment.ProfileFragment;
import com.meditation.app.ui.fragment.RateFragment;
import com.meditation.app.ui.fragment.RegisterFragment;
import com.meditation.app.ui.fragment.SetttingsFragment;
import com.meditation.app.ui.fragment.StoreDetailFragment;
import com.meditation.app.ui.fragment.TestimonialFragment;
import com.meditation.app.ui.fragment.WebsiteFragment;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private FrameLayout fragmentContainer;
    private LinearLayout footer;
    private ImageView icon_play;
    private ImageView icon_home;
    private ImageView icon_store;
    private ImageView icon_settings;

    Fragment mainFragment;
    Fragment settingsFragment;
    Fragment storeFragment;
    Fragment storeDetailFragment;
    Fragment loginFragment;
    Fragment registerFragment;
    Fragment rateFragment;
    Fragment contactFragment;
    Fragment profileFragment;
    Fragment faqFragment;
    Fragment articlesFragment;
    Fragment testimonialFragment;
    Fragment websiteFragment;
    PlaylistFragment playListFragment;
    String getDetails = "";

    public static final String LOGINTAG = "loginTag";
    public static final String REGISTERTAG = "registerTag";


    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initInstance();
        if (mainFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, mainFragment).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void findViews() {
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        footer = (LinearLayout) findViewById(R.id.footer);
        icon_play = (ImageView) findViewById(R.id.play);
        icon_home = (ImageView) findViewById(R.id.home);
        icon_store = (ImageView) findViewById(R.id.store);
        icon_settings = (ImageView) findViewById(R.id.setting);
        icon_home.setOnClickListener(this);
        icon_play.setOnClickListener(this);
        icon_store.setOnClickListener(this);
        icon_settings.setOnClickListener(this);

    }

    private void initInstance() {
        mainFragment = new HomeFragment();
        settingsFragment = new SetttingsFragment();
        storeFragment = new DownloadSelectionFragment();
        storeDetailFragment = new StoreDetailFragment();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        rateFragment = new RateFragment();
        contactFragment = new ContactFragment();

        profileFragment = new ProfileFragment();
        faqFragment = new FAQFragment();
        playListFragment=new PlaylistFragment();
        articlesFragment=new ArticlesFragment();
        testimonialFragment=new TestimonialFragment();
        websiteFragment=new WebsiteFragment();

    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = v.getId();
        switch (id) {
            case R.id.home:
                setImageIC(R.id.home);
                fragment = mainFragment;
                break;

            case R.id.play:
                setImageIC(R.id.play);
                fragment =playListFragment;
                break;

            case R.id.store:
               /* DownloadActivity obj=new DownloadActivity();
                DownloadSelectionActivity obj1= new DownloadSelectionActivity();*/
               /* Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);*/
                setImageIC(R.id.store);
                fragment = storeFragment;

               /* Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity(intent);*/
                break;

            case R.id.setting:
                setImageIC(R.id.setting);
                fragment = settingsFragment;
                break;

        }

        if (fragment != null) {

            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle b;
        b = getIntent().getExtras();
        if (b != null) {
            getDetails = getIntent().getExtras().getString("MainActivity");

            System.out.println("Checking current frag on resume..." + getDetails);

            if (getDetails.equalsIgnoreCase("loginTag")) {
                replaceFrag(loginFragment);
                setImageIC(R.id.home);
            } else if (getDetails.equalsIgnoreCase("registerTag")) {
                replaceFrag(registerFragment);
                setImageIC(R.id.home);
            } else if (getDetails.equalsIgnoreCase("rateTag")) {
                replaceFrag(rateFragment);
                setImageIC(R.id.setting);
            } else if (getDetails.equalsIgnoreCase("contactTag")) {
                replaceFrag(contactFragment);
                setImageIC(R.id.setting);
            } else if (getDetails.equalsIgnoreCase("profileTag")) {
                replaceFrag(profileFragment);
                setImageIC(R.id.setting);
            } else if (getDetails.equalsIgnoreCase("faqTag")) {
                replaceFrag(faqFragment);
                setImageIC(R.id.setting);
            }else if (getDetails.equalsIgnoreCase("storeDetailTag")) {
                replaceFrag(storeDetailFragment);
                setImageIC(R.id.store);
            }else if (getDetails.equalsIgnoreCase("settingsTag")) {
                replaceFrag(settingsFragment);
                setImageIC(R.id.setting);
            }else if (getDetails.equalsIgnoreCase("storeTag")) {
                replaceFrag(storeFragment);
                setImageIC(R.id.store);
            }else if (getDetails.equalsIgnoreCase("homePageTag")) {
                replaceFrag(mainFragment);
                setImageIC(R.id.home);
            }else if (getDetails.equalsIgnoreCase("loginTag")) {
                replaceFrag(loginFragment);
                setImageIC(R.id.home);
            }else if (getDetails.equalsIgnoreCase("articlesTag")) {
                replaceFrag(articlesFragment);
                setImageIC(R.id.setting);
            }else if (getDetails.equalsIgnoreCase("testimonialTag")) {
                replaceFrag(testimonialFragment);
                setImageIC(R.id.setting);
            }else if (getDetails.equalsIgnoreCase("websiteTag")) {
                replaceFrag(websiteFragment);
                setImageIC(R.id.setting);
            }
        }
    }

    public void replaceFrag(Fragment replaceFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, replaceFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void setImageIC(int id) {
        if (id == R.id.home) {
            icon_home.setImageResource(R.drawable.home_selected);
            icon_store.setImageResource(R.drawable.store);
            icon_settings.setImageResource(R.drawable.setting);
            icon_play.setImageResource(R.drawable.playlist);
        } else if (id == R.id.play) {
            icon_home.setImageResource(R.drawable.home);
            icon_store.setImageResource(R.drawable.store);
            icon_settings.setImageResource(R.drawable.setting);
            icon_play.setImageResource(R.drawable.playlist_selected);
        } else if (id == R.id.setting) {
            icon_home.setImageResource(R.drawable.home);
            icon_store.setImageResource(R.drawable.store);
            icon_settings.setImageResource(R.drawable.information_selected);
            icon_play.setImageResource(R.drawable.playlist);
        } else if (id == R.id.store) {
            icon_home.setImageResource(R.drawable.home);
            icon_store.setImageResource(R.drawable.store_selected);
            icon_settings.setImageResource(R.drawable.setting);
            icon_play.setImageResource(R.drawable.playlist);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
/*        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);*/
        finish();
    }

}
