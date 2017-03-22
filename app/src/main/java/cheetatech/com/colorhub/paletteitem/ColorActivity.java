package cheetatech.com.colorhub.paletteitem;

import android.content.Intent;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.ColorAdapter;
import cheetatech.com.colorhub.adapters.YourColorAdapter;
import cheetatech.com.colorhub.listeners.RecyclerItemClickListener;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.realm.RealmX;
import cheetatech.com.colorhub.realm.SavedObject;
import cheetatech.com.colorhub.yourcolors.YourColorActivity;
import layout.ColorPicker1;
import layout.ColorPickerArrange;

public class ColorActivity extends AppCompatActivity implements ColorPickerArrange.OnColorListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private ColorAdapter mAdapter = null;
    private List<Model> colorList = new ArrayList<>();
    private SavedObject object = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        object = ColorBus.getInstance().getSavedObject();
        toolbar.setNavigationIcon(R.drawable.ic_action_back_button);
        getSupportActionBar().setTitle(object.getName());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        loadAdapters();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.INVISIBLE);
    }

    private void loadAdapters() {

        colorList.clear();
        colorList.addAll(object.getList());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ColorAdapter(colorList);
        mRecyclerView.setAdapter(mAdapter);
        final ColorPickerArrange.OnColorListener listener = this;
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e("TAG","Clicked item position " + position);

                Model model = colorList.get(position);

                ColorPickerArrange colorPicker1 = ColorPickerArrange.newInstance(model,position,listener);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,colorPicker1).addToBackStack(null).commit();


//                ColorBus.(getInstance).setSavedObject(modelList.get(position));
//                startActivity(new Intent(YourColorActivity.this, ColorActivity.class));
            }
        }));
    }


    @Override
    public void onAddColor(String color) {

    }

    @Override
    public void onChangeColor(int position, Model model) {
        this.colorList.remove(position);
        this.colorList.add(model);
        //RealmX.getObject();
        this.mAdapter.notifyDataSetChanged();
    }
}
