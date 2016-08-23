package cheetatech.com.colorhub;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cheetatech.com.colorhub.adapters.DrawerListAdapter;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.adapters.NavigationBarAdapter;
import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.apprater.AppRater;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.controller.DrawerListController;
import cheetatech.com.colorhub.controller.ToolBarController;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.drawer.ColorSelect;
import cheetatech.com.colorhub.listeners.FloatButtonListener;
import cheetatech.com.colorhub.listeners.ListenerModel;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import hotchemi.android.rate.StoreType;
import layout.FlatColorFragment;
import layout.HomeFragment;
import layout.HtmlColorFragment;
import layout.MaterialColorFragment;
import layout.MetroColorFragment;
import layout.SocialColorFragment;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener, TabLayout.OnTabSelectedListener ,View.OnClickListener {

    private Toolbar toolbar = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private RelativeLayout relativeDrawer = null;

    private DrawerLayout mDrawer = null;
    private ListView drawerList = null;
    ArrayList<ColorSelect> cselect = null;

    private FloatingActionButton fab = null;

    private DrawerListAdapter drawerListAdapter = null;

    private int currentPosition = 0;
    private AdView mAdView = null;

    private String facebook = "https://www.facebook.com/cheetatech/?fref=ts&ref=br_tf";
    private String twitter = "https://twitter.com/cheeta_tech";
    private String instagram = "https://www.instagram.com/cheetatechofficial/";
    private String web = "https://cheetatech.wordpress.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();
        MobileAds.initialize(getApplicationContext(), getString(R.string.banner_commercial));
        mAdView = (AdView) findViewById(R.id.adView);
        // Color init
        ColorArrayController controller = ColorArrayController.getInstance();
        controller.setResource(getResources());
        controller.init();

        // Board
        BoardEditor.getInstance().setContext(getApplicationContext());
        // nav bar

        drawerListAdapter = new DrawerListAdapter(getApplicationContext(),1, DrawerListController.getInstance().getNavList());

        //cselect = new ArrayList<ColorSelect>();

        cselect = controller.getMaterialNameColorSelectList();
        NavigationBarAdapter adapter = new NavigationBarAdapter(getApplicationContext(),1,cselect);

        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        relativeDrawer = (RelativeLayout) findViewById(R.id.relative_layout);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_action_menu_);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentPosition != 1) // Material List
                {
                    cselect.clear();
                    drawerList.setAdapter(drawerListAdapter);
                }
                else{

                    cselect = ColorArrayController.getInstance().getMaterialNameColorSelectList();
                    NavigationBarAdapter adapter = new NavigationBarAdapter(getApplicationContext(),1,cselect);
                    drawerList.setAdapter(adapter);
                }
                //mDrawer.openDrawer(drawerList);
                mDrawer.openDrawer(relativeDrawer);
            }
        });
        viewPager = (ViewPager)findViewById(R.id.pager);
        setUpViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(this);

        ToolBarController.getInstance().setToolBar(toolbar);
        ToolBarController.getInstance().setTabLayout(tabLayout);



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, ColorPickerActivity.class));

                //startActivity(new Intent(MainActivity.this, AboutusActivity.class));
            }
        });


        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E64A19")));
        ((ImageButton) findViewById(R.id.icon_facebook)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.icon_twitter)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.icon_browser)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.icon_instagram)).setOnClickListener(this);


        /*
        AppRate.with(this)
                .setStoreType(StoreType.GOOGLEPLAY) //default is Google, other option is Amazon
                .setInstallDays(3) // default 10, 0 means install day.
                .setLaunchTimes(10) // default 10 times.
                .setRemindInterval(2) // default 1 day.
                .setShowLaterButton(true) // default true.
                .setDebug(true) // default false.
                .setCancelable(false) // default false.
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(MainActivity.class.getName(), Integer.toString(which));
                        Log.e(MainActivity.class.getName(), Integer.toString(which) + " :: Ekooo");
                    }
                })
                .setMessage(R.string.new_rate_dialog_message)
                .setTitle(R.string.new_rate_dialog_title)
                .setTextLater(R.string.new_rate_dialog_later)
                .setTextNever(R.string.new_rate_dialog_never)
                .setTextRateNow(R.string.new_rate_dialog_ok)
                .monitor();
        */
        //AppRate.with(this).showRateDialog(this);
        ////AppRate.showRateDialogIfMeetsConditions(this);
        //new AppRater(this).show();
        /* AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(3) // default 10
                .setRemindInterval(2) // default 1
                .setShowLaterButton(true) // default true
                .setDebug(false) // default false
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(MainActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        // Show a dialog if meets conditions
        //AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(this).showRateDialog(this);
        */
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadAdv();
    }

    public  void setUpViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new HomeFragment(),"Home");
        adapter.addFragment(new FlatColorFragment(),"Flat");
        adapter.addFragment(new MaterialColorFragment(),"Material");
        adapter.addFragment(new SocialColorFragment(),"Social");
        adapter.addFragment(new MetroColorFragment(),"Metro");
        adapter.addFragment(new HtmlColorFragment(),"Html");
        viewPager.setAdapter(adapter);
    }






    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // mDrawer.closeDrawer(drawerList);
        mDrawer.closeDrawer(relativeDrawer);


        if(currentPosition != 1 )
        {
            switch (i)
            {

                case 2:
                    startActivity(new Intent(MainActivity.this, ColorPickerActivity.class));
                    break;
                case 3:

                    break;
                case 4:
                    shareApp();
                    break;
                case 5:
                    startActivity(new Intent(MainActivity.this, AboutusActivity.class));
                    break;
            }
        }
        else
            ListenerModel.getInstance().setListenerIndex(i);
    }

    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out Color Hub app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
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

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.icon_facebook :
                openUrl(facebook);
                break;
            case R.id.icon_browser :
                openUrl(web);
                break;
            case R.id.icon_instagram :
                openUrl(instagram);
                break;
            case R.id.icon_twitter :
                openUrl(twitter);
                break;

        }
    }

    private void openUrl(String url) {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }


    public AdRequest getAdRequest() {
        AdRequest ret = null;
        if (Util.TEST) {
            ret = new AdRequest.Builder()
                    .addTestDevice(getPhoneId())
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            //AdRequest.Builder.addTestDevice("0A02E72208689385EF8EE5F0CCCFE947")
        } else {
            ret = new AdRequest.Builder().build();
        }
        return ret;
    }

    private boolean checkPermission(String readPhoneState) {
        boolean ret = ContextCompat.checkSelfPermission(this, readPhoneState) == PackageManager.PERMISSION_GRANTED;
        return ret;
    }


    public String getPhoneId() {
        String ret = "";
        //final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        //String deviceid = tm.getDeviceId();
        return "0A02E72208689385EF8EE5F0CCCFE947";
        //return "9552A433781FF6F1766BC1BDF72022E5";
    }

    private void loadAdv() {
        if (getAdRequest() != null) {
            AdRequest adRequest = getAdRequest();
            mAdView.loadAd(adRequest);
        }
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Util.REQUEST_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadAdv();
                }
                break;
        }
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("TAG", "Permission callback called-------");
        switch (requestCode) {
            case Util.REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                //perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);*/
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                            //&& perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d("TAG", "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("TAG", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)
                                // || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                             ) {
                            showDialogOK("Internet and Phone State Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private  boolean checkAndRequestPermissions() {

        if(Build.VERSION.SDK_INT >= 23) {
            int permissionInternet = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET);
            int permissionAccessNetworkState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
            int permissionReadPhoneState = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
            List<String> listPermissionsNeeded = new ArrayList<>();
            if (permissionInternet != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
            }
            if (permissionAccessNetworkState != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }
            if (permissionReadPhoneState != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), Util.REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
        }
        return true;
    }


}
