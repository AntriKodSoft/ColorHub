package cheetatech.com.colorhub.controller;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cheetatech.com.colorhub.R;

/**
 * Created by root on 11.05.2017.
 */

public class AnimControl {

    private Context context = null;
    private int width, height, imageHeight;
    private RelativeLayout savedLayout = null;
    private RelativeLayout imageLayout = null;
    private ViewTreeObserver observer = null;
    private ImageView upDownImage = null;
    private FloatingActionButton fabAdButton = null;

    private Animation slideUp, slideDown;

    public AnimControl(Context context,RelativeLayout savedLayout, RelativeLayout imageLayout, ImageView updownImage,FloatingActionButton fab){
        this.context = context;
        this.savedLayout = savedLayout;
        this.imageLayout = imageLayout;
        this.upDownImage = updownImage;
        this.fabAdButton = fab;
        init(context);
    }

    public void init(Context context){
        loadAnimations(context);
        if(fabAdButton.getVisibility() == View.VISIBLE)
            fabAdButton.setVisibility(View.INVISIBLE);
        observer = this.savedLayout.getViewTreeObserver();
        if(observer != null && observer.isAlive())
            observer.addOnGlobalLayoutListener(globalLayoutListener);
    }

    private void loadAnimations(Context context) {
        slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(context,R.anim.slide_down);
    }

    public void openLayout(){
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            upDownImage.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_down));
        } else {
            upDownImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_action_down));
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                width, height);
        lp.setMargins(0, width, 0, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        savedLayout.setLayoutParams(lp);

        if(fabAdButton.getVisibility() == View.INVISIBLE)
            fabAdButton.setVisibility(View.VISIBLE);

        savedLayout.clearAnimation();
        savedLayout.startAnimation(slideUp);;
        slideUp.setFillAfter(true);
        slideUp.setFillEnabled(true);
        slideUp.setFillBefore(true);
        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        width, height);
                lp.setMargins(0, width, 0, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                savedLayout.setLayoutParams(lp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void closeLayout(){
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            upDownImage.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_up));
        } else {
            upDownImage.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_action_up));
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                width, height);
        lp.setMargins(0, width, 0, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        if(fabAdButton.getVisibility() == View.VISIBLE)
            fabAdButton.setVisibility(View.INVISIBLE);

        savedLayout.setLayoutParams(lp);
        savedLayout.clearAnimation();
        savedLayout.startAnimation(slideDown);
        slideDown.setFillAfter(true);
        slideDown.setFillEnabled(true);
        slideDown.setFillBefore(true);
        slideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                savedLayout.clearAnimation();
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        width, imageHeight);
                lp.setMargins(0, width, 0, 0);
                Log.e("TAG","Width: " + width +  " : : " + imageHeight);
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                savedLayout.setLayoutParams(lp);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }




    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                savedLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
                savedLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
            width = savedLayout.getMeasuredWidth();
            height = savedLayout.getMeasuredHeight();

            if((imageHeight = imageLayout.getMeasuredHeight()) == -1 )
                imageHeight = 100;

            Log.e("TAG","Width ::::: "  + imageHeight);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    width, imageHeight);
            lp.setMargins(0, width, 0, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            savedLayout.setLayoutParams(lp);
        }
    };
}