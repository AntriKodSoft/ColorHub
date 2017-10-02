package cheetatech.com.colorhub;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.com.colorhub.adapters.SaveListAdapter;
import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.ads.AdsUtils;
import cheetatech.com.colorhub.controller.AnimControl;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.controller.ToolBarController;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.dialog.SaveDialog;
import cheetatech.com.colorhub.listeners.OnItemSelect;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.realm.RealmX;
import cheetatech.com.colorhub.realm.SavedObject;
import cheetatech.com.colorhub.social.Links;
import cheetatech.com.colorhub.social.Social;
import es.dmoral.toasty.Toasty;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import io.realm.RealmList;
import layout.ColorDetailFragment;
import layout.ColorKotlinFragment;
import layout.ColorPicker1;
import layout.MaterialRootFragment;
import layout.MaterialUIFragment;
import layout.RootFragment;
import layout.RootYourColorFragment;
import layout.YourColorKotlinFragment;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener, TabLayout.OnTabSelectedListener , SaveDialog.OnSaveListener, RootFragment.OnFragmentInteractionListener, MaterialRootFragment.OnFragmentInteractionListener, ColorKotlinFragment.OnFragmentInteractionListener, ColorPicker1.OnColorListener, MaterialUIFragment.OnFragmentInteractionListener, YourColorKotlinFragment.OnFragmentInteractionListener, ColorDetailFragment.OnFragmentInteractionListener, RootYourColorFragment.OnFragmentInteractionListener{

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.image_up_down)
    ImageView upDownImage;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.saved_color_layout)
    RelativeLayout mSavedLayout;

    @BindView(R.id.image_layout)
    RelativeLayout mImageLayout;

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAddButton;

    private SaveListAdapter mAdapter = null;
    private List<Model> listModel = new ArrayList<>();
    private AnimControl animControl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(animControl == null){
            animControl = new AnimControl(getApplicationContext(), mSavedLayout, mImageLayout, upDownImage,fabAddButton);
        }
        // Color init
        ColorArrayController controller = ColorArrayController.getInstance();
        controller.setResource(getResources());
        controller.init();
        // Board
        BoardEditor.getInstance().setContext(getApplicationContext());
        // nav bar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);
        //tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        ToolBarController.getInstance().setToolBar(toolbar);
        ToolBarController.getInstance().setTabLayout(tabLayout);

        loadAdapters();

        RealmX.list();
        loadAds();
        initAppRateDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_rate_this_app:
                Social.Companion.openUrl(Links.APP_URL, MainActivity.this);
                return true;
            case R.id.action_share_this_app:
                Social.Companion.shareApp(MainActivity.this);
                return true;
            case R.id.action_about_us:
                startActivity(new Intent(MainActivity.this, AboutusActivity.class));
                return true;
            case R.id.action_more_app:
                Social.Companion.openUrl(Links.ANTRIKOD_PLAY_STORE, MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initAppRateDialog() {
        AppRate.with(this)
                .setInstallDays(0) // default 10, 0 means install day.
                .setLaunchTimes(2) // default 10
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
        AppRate.showRateDialogIfMeetsConditions(this);
    }

    private void loadAds() {
        AdsUtils.getInstance().increaseInteraction();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AdsUtils.getInstance().showAdsWithRunnable();
            }
        },3000);
    }

    private void loadAdapters() {
        listModel.clear();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SaveListAdapter(listModel);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @OnClick(R.id.fabAdd) void fabAddClick(){
        if(this.listModel.size() != 0)
            (SaveDialog.newInstance(this)).show(getSupportFragmentManager(),getString(R.string.save_dialog));
    }

    @OnClick(R.id.image_layout) void updownImageClick(){
        if(!isOpen()) {
            animControl.openLayout();
            return;
        }
        if(isOpen()){
            animControl.closeLayout();
        }
    }
    OnItemSelect listener = new OnItemSelect() {
        @Override
        public void onItemSelected(int position) {

        }

        @Override
        public void onAddColor(@NotNull String color) {
        }
    };

    public  void setUpViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(RootFragment.Companion.newInstance(listener),"", getResources().getDrawable(R.drawable.ic_action_home_dark));
        adapter.addFragment(RootYourColorFragment.Companion.newInstance(),"",getResources().getDrawable(R.drawable.ic_favorite_dark));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    private boolean isOpen(){
        int height = mSavedLayout.getMeasuredHeight();
        return height > 150;
    }

    private boolean isInList(List<Model> models,String color){
        for (Model m: models) {
            if(m.getColorCode().equals(color))
                return true;
        }
        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int currentPosition = tab.getPosition();
        tabLayout.getTabAt(currentPosition).select();
        viewPager.setCurrentItem(currentPosition);
        if(currentPosition == 1){ // your color position
            android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentByTag("ROOT_YOUR_COLOR_TAG");

            if(fragment != null){
                ((YourColorKotlinFragment)fragment).refreshValues();
            }

        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        }else {
            if (isOpen()) {
                animControl.closeLayout();
                return;
            }
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            // set title
            alertDialogBuilder.setTitle(getString(R.string.exit_app));
            alertDialogBuilder
                    .setMessage(getString(R.string.exit_app_ask))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.answer_yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.answer_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onSavedName(String name) {
        SavedObject object = new SavedObject();
        object.setName(name);
        RealmList<Model> mList = new RealmList<Model>();
        mList.addAll(this.listModel);
        object.setList(mList);
        RealmX.save(object);
        this.listModel.clear();
        this.mAdapter.notifyDataSetChanged();
        animControl.closeLayout();
        AdsUtils.getInstance().increaseInteraction();
        Toasty.success(MainActivity.this,getString(R.string.success_add_palette),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(@NotNull Uri uri) {
    }

    @Override
    public void onAddColor(String color) {
        if(!isInList(listModel,color)){
            AdsUtils.getInstance().increaseInteraction();
            listModel.add(new Model(color));
            mAdapter.notifyDataSetChanged();
            Toasty.success(MainActivity.this,"",Toast.LENGTH_SHORT).show();
        }else{
            Toasty.warning(MainActivity.this,"",Toast.LENGTH_SHORT).show();
        }
    }
}
