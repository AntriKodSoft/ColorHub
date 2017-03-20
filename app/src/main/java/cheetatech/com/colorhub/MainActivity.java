package cheetatech.com.colorhub;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.com.colorhub.adapters.DrawerListAdapter;
import cheetatech.com.colorhub.adapters.NavigationBarAdapter;
import cheetatech.com.colorhub.adapters.SaveListAdapter;
import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.controller.DrawerListController;
import cheetatech.com.colorhub.controller.ToolBarController;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.dialog.SaveDialog;
import cheetatech.com.colorhub.drawer.ColorSelect;
import cheetatech.com.colorhub.listeners.ListenerModel;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.realm.RealmX;
import cheetatech.com.colorhub.realm.SavedObject;
import io.realm.RealmList;
import layout.ColorPicker1;
import layout.FlatColorFragment;
import layout.HtmlColorFragment;
import layout.MaterialColorFragment;
import layout.MetroColorFragment;
import layout.SocialColorFragment;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener, TabLayout.OnTabSelectedListener, ColorPicker1.OnColorListener , SaveDialog.OnSaveListener{

    private Toolbar toolbar = null;
    ArrayList<ColorSelect> cselect = null;

    @BindView(value = R.id.fab)
    FloatingActionButton fab;

    private DrawerListAdapter drawerListAdapter = null;

    private int currentPosition = 0;
    Animation slideUp, slideDown, fadeIn, fadeOut ;

    private String facebook = "https://www.facebook.com/cheetatech/?fref=ts&ref=br_tf";
    private String twitter = "https://twitter.com/cheeta_tech";
    private String instagram = "https://www.instagram.com/cheetatechofficial/";
    private String web = "https://cheetatech.wordpress.com/";

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
    private int layoutStatus = 1; // close;
    private int width, height, imageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        slideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        fadeIn = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);

        if(fab.getVisibility() == View.INVISIBLE)
            fab.setVisibility(View.VISIBLE);

        if(fabAddButton.getVisibility() == View.VISIBLE)
            fabAddButton.setVisibility(View.INVISIBLE);

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

                    imageHeight = mImageLayout.getMeasuredHeight();
                    if(imageHeight == -1)
                        imageHeight = 100;
                    Log.e("TAG","ImageLayout :  " + imageHeight);

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            width, imageHeight);
                    lp.setMargins(0, width, 0, 0);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    mSavedLayout.setLayoutParams(lp);
                }
            });
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

        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#E64A19")));

        loadAdapters();

        RealmX.list();
    }

    private void loadAdapters() {
        listModel.clear();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new SaveListAdapter(listModel);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab) void fabClick(){
        startActivity(new Intent(MainActivity.this, ColorPickerActivity.class));
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
        if(layoutStatus == 1){ // Will Open
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                upDownImage.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_action_down));
            } else {
                upDownImage.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_action_down));
            }

            if(fabAddButton.getVisibility() == View.INVISIBLE)
                fabAddButton.setVisibility(View.VISIBLE);

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
            slideUp.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Log.e("TAG", "onAnimationEnd: Click");
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            width, height);
                    lp.setMargins(0, width, 0, 0);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    mSavedLayout.setLayoutParams(lp);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

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

            if(fabAddButton.getVisibility() == View.VISIBLE)
                fabAddButton.setVisibility(View.INVISIBLE);


            mSavedLayout.setLayoutParams(lp);

            mSavedLayout.clearAnimation();
            mSavedLayout.startAnimation(slideDown);
            slideDown.setFillAfter(true);
            slideDown.setFillEnabled(true);
            slideDown.setFillBefore(true);
            slideDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    mSavedLayout.clearAnimation();
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            width, imageHeight);
                    lp.setMargins(0, width, 0, 0);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    mSavedLayout.setLayoutParams(lp);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            layoutStatus = 1;
            return;
        }
    }

    public  void setUpViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FlatColorFragment.newInstance(this),"Flat");
        adapter.addFragment(MaterialColorFragment.newInstance(this),"Material");
        adapter.addFragment(SocialColorFragment.newInstance(this),"Social");
        adapter.addFragment(MetroColorFragment.newInstance(this),"Metro");
        adapter.addFragment(HtmlColorFragment.newInstance(this),"Html");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDrawer.closeDrawer(relativeDrawer);

        if(currentPosition != 1 )
        {
            switch (i)
            {
                case 2:
                    startActivity(new Intent(MainActivity.this, ColorPickerActivity.class));
                    break;
                case 3:
                    openUrl("https://play.google.com/store/apps/details?id=cheetatech.com.colorhub");
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
                "Hey check out Color Hub app at: https://play.google.com/store/apps/details?id=cheetatech.com.colorhub");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onAddColor(String color) {
        if(!isInList(this.listModel,color)){
            this.listModel.add(new Model(color));
            mAdapter.notifyDataSetChanged();
        }
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
        Log.e("TAG", "onSavedName: " + name );

        SavedObject object = new SavedObject();

        object.setName(name);
        RealmList<Model> mList = new RealmList<Model>();
        mList.addAll(this.listModel);
        object.setList(mList);

        RealmX.save(object);
    }
}
