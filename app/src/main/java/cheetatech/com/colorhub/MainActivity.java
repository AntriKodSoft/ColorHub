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
import cheetatech.com.colorhub.adapters.DrawerListAdapter;
import cheetatech.com.colorhub.adapters.NavigationBarAdapter;
import cheetatech.com.colorhub.adapters.SaveListAdapter;
import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.ads.AdsUtils;
import cheetatech.com.colorhub.controller.AnimControl;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.controller.DrawerListController;
import cheetatech.com.colorhub.controller.ToolBarController;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.dialog.SaveDialog;
import cheetatech.com.colorhub.drawer.ColorSelect;
import cheetatech.com.colorhub.listeners.ListenerModel;
import cheetatech.com.colorhub.listeners.OnItemSelect;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.realm.RealmX;
import cheetatech.com.colorhub.realm.SavedObject;
import cheetatech.com.colorhub.yourcolors.YourColorActivity;
import es.dmoral.toasty.Toasty;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import io.realm.RealmList;
import layout.ColorKotlinFragment;
import layout.ColorPicker1;
import layout.FlatColorFragment;
import layout.MaterialRootFragment;
import layout.RootFragment;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener, TabLayout.OnTabSelectedListener , SaveDialog.OnSaveListener, RootFragment.OnFragmentInteractionListener, MaterialRootFragment.OnFragmentInteractionListener, ColorKotlinFragment.OnFragmentInteractionListener, ColorPicker1.OnColorListener{

    private Toolbar toolbar = null;
    List<ColorSelect> cselect = null;
    private DrawerListAdapter drawerListAdapter = null;
    private int currentPosition = 0;
    private String facebook = "https://www.facebook.com/cheetatech/?fref=ts&ref=br_tf";
    private String twitter = "https://twitter.com/antri_kod";
    private String instagram = "https://www.instagram.com/cheetatechofficial/";
    private String web = "http://www.antrikod.com";

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.relative_layout)
    RelativeLayout relativeDrawer;

    @BindView(R.id.left_drawer)
    ListView drawerList;

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

        drawerListAdapter = new DrawerListAdapter(getApplicationContext(),1, DrawerListController.getInstance().getNavList());

        cselect = controller.getMaterialNameColorSelectList();
        NavigationBarAdapter adapter = new NavigationBarAdapter(getApplicationContext(),1,cselect);

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
                mDrawer.openDrawer(relativeDrawer);
            }
        });
        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(this);

        ToolBarController.getInstance().setToolBar(toolbar);
        ToolBarController.getInstance().setTabLayout(tabLayout);

        loadAdapters();

        RealmX.list();
        loadAds();
        initAppRateDialog();
    }

    OnItemSelect listener = new OnItemSelect() {
        @Override
        public void onItemSelected(int position) {

        }

        @Override
        public void onAddColor(@NotNull String color) {

//            Log.e("TAG","onAddColor MainActivity " + color );
//            if(!isInList(listModel,color)){
//                AdsUtils.getInstance().increaseInteraction();
//                listModel.add(new Model(color));
//                mAdapter.notifyDataSetChanged();
//                Toasty.success(MainActivity.this,"",Toast.LENGTH_SHORT).show();
//            }else{
//                Toasty.warning(MainActivity.this,"",Toast.LENGTH_SHORT).show();
//            }
        }
    };

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
            return;
        }
    }

    public  void setUpViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(RootFragment.Companion.newInstance(listener),"Root");
//        adapter.addFragment(FlatColorFragment.newInstance(listener),"Flat");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDrawer.closeDrawer(relativeDrawer);

        if(currentPosition != 1 )
        {
            AdsUtils.getInstance().increaseInteraction();
            switch (i)
            {
                case 2:
                    startActivity(new Intent(MainActivity.this, YourColorActivity.class));
                    break;
                case 3:
                    currentPosition = 5;
                    viewPager.setCurrentItem(5);
                    break;
                case 4:
                    openUrl("https://play.google.com/store/apps/details?id=cheetatech.com.colorhub");
                    break;
                case 5:
                    shareApp();
                    break;
                case 6:
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
                "Hey check out Color Hub app at: https://play.google.com/store/apps/details?id=cheetatech.com.colorhub");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

//    @Override
//    public void onAddColor(String color) {
//        if(!isInList(this.listModel,color)){
//            AdsUtils.getInstance().increaseInteraction();
//            this.listModel.add(new Model(color));
//            mAdapter.notifyDataSetChanged();
//            Toasty.success(MainActivity.this,"",Toast.LENGTH_SHORT).show();
//        }else{
//            Toasty.warning(MainActivity.this,"",Toast.LENGTH_SHORT).show();
//        }
//    }

    private boolean isOpen(){
        int height = mSavedLayout.getMeasuredHeight();
        if(height > 150)
            return true;
        else
            return false;
    }

    private boolean isInList(List<Model> models,String color){
        boolean hold = false;
        for (Model m: models) {
            if(m.getColorCode().equals(color))
                return true;
        }
        return hold;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        currentPosition =  tab.getPosition();
        tabLayout.getTabAt(currentPosition).select();
        viewPager.setCurrentItem(currentPosition);
    }

    @Override
    public void onBackPressed() {

        //
        if(viewPager.getCurrentItem() == 0 && getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        }else {


            if (mDrawer.isDrawerOpen(relativeDrawer)) {
                Log.e("TAG", "onBackPressed: Opennnn ");
                mDrawer.closeDrawer(relativeDrawer);
                return;
            }
            if (isOpen()) {
                animControl.closeLayout();
                //closeLayout();
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

    @OnClick(R.id.icon_browser) void clickBrowser(){
        openUrl(web);
    }

    @OnClick(R.id.icon_facebook) void clickFacebook(){
        openUrl(facebook);
    }

    @OnClick(R.id.icon_instagram) void clickInstagram(){
        openUrl(instagram);
    }

    @OnClick(R.id.icon_twitter) void clickTwitter(){
        openUrl(twitter);
    }

    private void openUrl(String url) {
        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
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
        //
        Log.e("TAG","onAddColor MainActivity " + color );
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
