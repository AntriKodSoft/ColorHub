package cheetatech.com.colorhub;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONObject;

import java.util.List;

import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorItem;
import cheetatech.com.colorhub.listeners.IOnFocusListenable;
import layout.ColorPicker1;
import layout.ColorPicker2;
import layout.FlatColorFragment;
import layout.HtmlColorFragment;
import layout.MaterialColorFragment;
import layout.MetroColorFragment;
import layout.SocialColorFragment;

public class ColorPickerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private ColorPicker1 ColorPicker_1 = null;
    private AdView mAdView = null;
    private int currentPosition = 0;
    public static boolean erase = false;
    FloatingActionButton fabErase = null, fabAdd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_commercial));
        mAdView = (AdView) findViewById(R.id.adView);

        toolbar.setNavigationIcon(R.drawable.ic_action_back_button);
        getSupportActionBar().setTitle("ColorHub");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        viewPager = (ViewPager) findViewById(R.id.pager);
        setUpViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(this);


        fabErase = (FloatingActionButton) findViewById(R.id.fabErase);
        fabErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!erase)
                {
                    fabErase.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#508764")));
                    erase = true;
                }else{
                    fabErase.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E64A19")));
                    erase = false;
                }
            }
        });

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //#508764


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAdv();
    }

    private void loadAdv() {
        if (getAdRequest() != null) {
            AdRequest adRequest = getAdRequest();
            mAdView.loadAd(adRequest);
        }
    }
    public AdRequest getAdRequest() {
        AdRequest ret = null;
        if (Util.TEST) {
            ret = new AdRequest.Builder()
                    .addTestDevice(getPhoneId())
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("9552A433781FF6F1766BC1BDF72022E5")
                    .build();
        } else {
            ret = new AdRequest.Builder().build();
        }
        return ret;
    }
    public String getPhoneId() {
        return "0A02E72208689385EF8EE5F0CCCFE947";
    }
    public  void setUpViewPager(ViewPager viewPager)
    {
        if(ColorPicker_1 == null)
            ColorPicker_1 = new ColorPicker1();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ColorPicker_1,"ColorPicker1");
        adapter.addFragment(new ColorPicker2(),"ColorPicker2");
        viewPager.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        Fragment currentFragment = getVisibleFragment();
        if(currentFragment != null)
        {
            if(currentFragment instanceof  IOnFocusListenable)
                ((IOnFocusListenable)currentFragment).onWindowFocusChanged(hasFocus);
        }

    }
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = ColorPickerActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        currentPosition =  tab.getPosition();

        tabLayout.getTabAt(currentPosition).select();
        viewPager.setCurrentItem(currentPosition);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}