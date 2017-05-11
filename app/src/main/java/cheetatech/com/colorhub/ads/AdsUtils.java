package cheetatech.com.colorhub.ads;


import android.content.Context;
import android.os.Handler;
import android.util.Log;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import cheetatech.com.colorhub.BuildConfig;
import cheetatech.com.colorhub.R;

public class AdsUtils {

    public static boolean TEST = true;
    private Context context = null;
    private static InterstitialAd mInterstitialAd = null;
    private int mInteractionValue = 0;
    private static int INTERACT_THRESHOLD = 12;
    private int DELAY = 2000;


    private static AdsUtils instance = null;

    public static AdsUtils getInstance(){
        if(instance == null)
            instance = new AdsUtils();
        return instance;
    }

    public boolean showAds(){
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
            return true;
        }else{
            requestNewInterstitial();
            return false;
        }
    }

    public void showAdsWithRunnable(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                    handler.removeCallbacks(this);
                }else{
                    requestNewInterstitial();
                    handler.postDelayed(this,DELAY);
                }
            }
        },DELAY);
    }



    private void load(){
        if(mInterstitialAd == null){
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(context.getString(R.string.admob_inter_app_pub));
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    Log.e("TAG","onAdClosed and new Request Interstitial");
                    requestNewInterstitial();
                }
            });
            requestNewInterstitial();
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        load();
    }

    private void requestNewInterstitial() {
        AdRequest ret = null;
        TEST = BuildConfig.DEBUG;
        if (TEST) {
            ret = new AdRequest.Builder()
                    .addTestDevice("0A02E72208689385EF8EE5F0CCCFE947")
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("9552A433781FF6F1766BC1BDF72022E5")
                    .build();
        } else {
            ret = new AdRequest.Builder().build();
        }
        mInterstitialAd.loadAd(ret);
    }

    public void increaseInteraction(){
        mInteractionValue++;
        controlInteraction();
    }

    private void controlInteraction() {
        if(mInteractionValue % INTERACT_THRESHOLD == 0) //  if removed ads, ads will not show on screen
            showAds();
    }

}
