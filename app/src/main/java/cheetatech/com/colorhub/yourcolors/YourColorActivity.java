package cheetatech.com.colorhub.yourcolors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.YourColorAdapter;
import cheetatech.com.colorhub.listeners.RecyclerItemClickListener;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.paletteitem.ColorActivity;
import cheetatech.com.colorhub.paletteitem.ColorBus;
import cheetatech.com.colorhub.realm.RealmX;
import cheetatech.com.colorhub.realm.SavedObject;
import io.realm.Realm;
import io.realm.RealmResults;

import static cheetatech.com.colorhub.realm.RealmX.getObject;

public class YourColorActivity extends AppCompatActivity {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private YourColorAdapter mAdapter;

    private List<SavedObject> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_color);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back_button);
        getSupportActionBar().setTitle("Your Colors");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadAdapters();
    }


    @Override
    protected void onResume() {
        if(mAdapter != null){
            RealmResults<SavedObject> list = RealmX.getObject();
            modelList.clear();
            modelList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    private void loadAdapters() {


        RealmResults<SavedObject> list = RealmX.getObject();
        modelList.addAll(list);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new YourColorAdapter(modelList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("TAG","Clicked item position " + position);
                ColorBus.getInstance().setSavedObject(modelList.get(position));
                startActivity(new Intent(YourColorActivity.this, ColorActivity.class));
            }
        }));
    }

}
