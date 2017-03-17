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
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.internal.zzf;
import com.google.android.gms.clearcut.LogEventParcelable;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorItem;
import cheetatech.com.colorhub.listeners.IOnFocusListenable;
import layout.ColorPicker1;
import layout.ColorPicker2;
import layout.ColorPicker3;
import layout.FlatColorFragment;
import layout.HtmlColorFragment;
import layout.MaterialColorFragment;
import layout.MetroColorFragment;
import layout.SocialColorFragment;

public class ColorPickerActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private ColorPicker1 ColorPicker_1 = null;

    private int currentPosition = 0;
    public static boolean erase = false;
    @BindView(R.id.saved_color_layout)
    RelativeLayout mSavedLayout;

    @BindView(R.id.image_up_down)
    ImageView upDownImage;



    Animation slideUp, slideDown ;
    private int i = 0;
    private int layoutStatus = 1; // close;
    private int width, height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        ButterKnife.bind(this);

        slideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        mSavedLayout.setVisibility(View.VISIBLE);
        ViewTreeObserver observer = mSavedLayout.getViewTreeObserver();
        if(observer.isAlive()){
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mSavedLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mSavedLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    width = mSavedLayout.getMeasuredWidth();
                    height = mSavedLayout.getMeasuredHeight();
                    Log.e("TAG", "onCreate: widht: " + width + " height : " + height);
                }
            });
        }


//        width = mSavedLayout.getWidth();
//        height = mSavedLayout.getHeight();
        //mSavedLayout.setVisibility(View.INVISIBLE);
        //Log.e("TAG", "onCreate: widht: " + width + " height : " + height);
        Animation slideDownFast = AnimationUtils.loadAnimation(this,R.anim.slide_down_fast);
        //slideDown(slideDownFast);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @OnClick(R.id.fabAdd) void fabAddClick(){

        i++;
        if(i%2 == 1){
            //mSavedLayout.setVisibility(View.VISIBLE);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                upDownImage.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_down));
            } else {
                upDownImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_action_down));
            }
            mSavedLayout.clearAnimation();
            //mSavedLayout.setAnimation(slideUp);
            mSavedLayout.startAnimation(slideUp);
//            long duration = slideUp.getDuration();
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if(!slideUp.hasEnded()){
//                        Log.e("TAG","Has Ended False");
//                    }else{
//                        Log.e("TAG","Has Ended");
//                        mSavedLayout.setVisibility(View.VISIBLE);
//                    }
//                }
//            },duration + 10);


//            mSavedLayout.invalidate();
            slideUp.setFillAfter(true);
            mSavedLayout.setLayoutAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("TAG","onAnimationEnd VISIBLE");
                    mSavedLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
            //SlideToAbove();
            //mSavedLayout.setVisibility(View.VISIBLE);
        if(i%2 == 0) {

            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                upDownImage.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_up));
            } else {
                upDownImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_action_up));
            }
            mSavedLayout.clearAnimation();
            //mSavedLayout.setAnimation(slideDown);
            mSavedLayout.startAnimation(slideDown);
//            long duration = slideDown.getDuration();
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if(!slideDown.hasEnded()){
//                        Log.e("TAG","Has Ended False");
//                    }else{
//                        Log.e("TAG","Has Ended");
//                        mSavedLayout.setVisibility(View.INVISIBLE);
//                    }
//                }
//            },duration + 10);

//            mSavedLayout.invalidate();
            //mSavedLayout.setVisibility(View.INVISIBLE);
            slideDown.setFillAfter(true);
            mSavedLayout.setLayoutAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("TAG","onAnimationEnd INVISIBLE");
                    mSavedLayout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
            //SlideToDown();
        //mSavedLayout.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.image_layout) void updownImageClick(){

        Log.e("TAG","ImageUpdown Clicked!!!");


        if(layoutStatus == 1){ // Will Open
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                upDownImage.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_down));
            } else {
                upDownImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_action_down));
            }

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    width, height);
            lp.setMargins(0, width, 0, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mSavedLayout.setLayoutParams(lp);

            mSavedLayout.clearAnimation();
            mSavedLayout.startAnimation(slideUp);;
            slideUp.setFillAfter(true);
            slideUp.setFillEnabled(true);
            slideUp.setFillBefore(true);

            mSavedLayout.setLayoutAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    Log.e("TAG","onAnimated Start");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("TAG","onAnimated End ");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    Log.e("TAG","onAnimation Repeat");
                }
            });
            layoutStatus = 2;
            return;
        }
        if(layoutStatus == 2){
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                upDownImage.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_up));
            } else {
                upDownImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_action_up));
            }
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    width, height);
            lp.setMargins(0, width, 0, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mSavedLayout.setLayoutParams(lp);

            mSavedLayout.clearAnimation();
            mSavedLayout.startAnimation(slideDown);
            slideDown.setFillAfter(true);
            slideDown.setFillEnabled(true);
            slideDown.setFillBefore(true);
            long duration = slideDown.getDuration();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(slideDown.hasEnded()){
                        Log.e("TAG","Slide Down Ended");
                       mSavedLayout.clearAnimation();

                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                width, 100);
                        lp.setMargins(0, width, 0, 0);
                        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                        mSavedLayout.setLayoutParams(lp);

                    }else{
                        Log.e("TAG","Slide Down Not Ended");
                    }
                }
            },(long)(duration +100));
            layoutStatus = 1;
            return;
        }
    }



    public void slideDown(Animation animation){
        mSavedLayout.clearAnimation();
        mSavedLayout.startAnimation(animation);
        animation.setFillAfter(false);
//        mSavedLayout.setLayoutAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Log.e("TAG","onAnimationEnd INVISIBLE");
//                mSavedLayout.setVisibility(View.INVISIBLE);
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
    }


    public void SlideToAbove() {
        Animation slide = null;
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -5.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        mSavedLayout.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mSavedLayout.clearAnimation();

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        mSavedLayout.getWidth(), mSavedLayout.getHeight());
                // lp.setMargins(0, 0, 0, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                mSavedLayout.setLayoutParams(lp);

            }

        });

    }

    public void SlideToDown() {
        Animation slide = null;
        slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 5.2f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        mSavedLayout.startAnimation(slide);

        slide.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                mSavedLayout.clearAnimation();

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        mSavedLayout.getWidth(), mSavedLayout.getHeight());
                lp.setMargins(0, mSavedLayout.getWidth(), 0, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                mSavedLayout.setLayoutParams(lp);
            }
        });

    }



    public  void setUpViewPager(ViewPager viewPager)
    {
        if(ColorPicker_1 == null)
            ColorPicker_1 = new ColorPicker1();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(ColorPicker_1,"ColorPicker1");
        //adapter.addFragment(new ColorPicker2(),"ColorPicker2");
        adapter.addFragment(new ColorPicker3(),"ColorPicker3");
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