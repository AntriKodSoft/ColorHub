package cheetatech.com.colorhub;

import android.app.ActionBar;
import android.app.FragmentTransaction;
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
import android.widget.ListView;

import java.util.ArrayList;

import cheetatech.com.colorhub.adapters.NavigationBarAdapter;
import cheetatech.com.colorhub.adapters.ViewPagerAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.drawer.ColorSelect;
import layout.FlatColorFragment;
import layout.HomeFragment;
import layout.HtmlColorFragment;
import layout.MaterialColorFragment;
import layout.MetroColorFragment;
import layout.SocialColorFragment;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private  Toolbar toolbar = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;

    private DrawerLayout mDrawer = null;
    private ListView drawerList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // nav bar
        int f = 1;
        ArrayList<ColorSelect> cselect = new ArrayList<ColorSelect>();
        for(int i = 0; i < 5 ; i++ )
            cselect.add(new ColorSelect("Erkan "+f++));

        NavigationBarAdapter adapter = new NavigationBarAdapter(getApplicationContext(),1,cselect);

        mDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(this);



        // Color init

        ColorArrayController controller = ColorArrayController.getInstance();
        controller.setResource(getResources());
        controller.initMaterial();
        controller.initFlat();
        controller.initMetro();
        controller.initSocial();



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.pager);
        setUpViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        mDrawer.closeDrawer(drawerList);
        Log.e("DRAWER",""+i);
    }
}
