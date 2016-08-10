package cheetatech.com.colorhub;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cheetatech.com.colorhub.adapters.DrawerListAdapter;
import cheetatech.com.colorhub.adapters.NavigationBarAdapter;
import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.controller.DrawerListController;
import cheetatech.com.colorhub.controller.ToolBarController;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.drawer.ColorSelect;
import cheetatech.com.colorhub.listeners.ListenerModel;
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

    private String facebook = "https://www.facebook.com/cheetatech/?fref=ts&ref=br_tf";
    private String twitter = "https://twitter.com/cheeta_tech";
    private String instagram = "https://www.instagram.com/cheetatechofficial/";
    private String web = "https://cheetatech.wordpress.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        ((ImageButton) findViewById(R.id.icon_facebook)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.icon_twitter)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.icon_browser)).setOnClickListener(this);
        ((ImageButton) findViewById(R.id.icon_instagram)).setOnClickListener(this);

    }


    public  void setUpViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(),"Home");
        adapter.addFragment(new MaterialColorFragment(),"Material");
        adapter.addFragment(new FlatColorFragment(),"Flat");
        adapter.addFragment(new SocialColorFragment(),"Social");
        adapter.addFragment(new MetroColorFragment(),"Metro");
        adapter.addFragment(new HtmlColorFragment(),"Html");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

                case 4:
                    startActivity(new Intent(MainActivity.this, AboutusActivity.class));
                    break;
            }
        }
        else
            ListenerModel.getInstance().setListenerIndex(i);
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
}
