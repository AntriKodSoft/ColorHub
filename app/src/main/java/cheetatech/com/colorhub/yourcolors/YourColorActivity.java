package cheetatech.com.colorhub.yourcolors;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import cheetatech.com.colorhub.MainActivity;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.YourColorAdapter;
import cheetatech.com.colorhub.ads.AdsUtils;
import cheetatech.com.colorhub.listeners.RecyclerItemClickListener;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.paletteitem.ColorActivity;
import cheetatech.com.colorhub.paletteitem.ColorBus;
import cheetatech.com.colorhub.realm.RealmX;
import cheetatech.com.colorhub.realm.SavedObject;
import io.realm.Realm;
import io.realm.RealmResults;

import static cheetatech.com.colorhub.realm.RealmX.getObject;

public class YourColorActivity extends AppCompatActivity implements YourColorAdapter.OnItemDelete{


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
        mAdapter = new YourColorAdapter(modelList,this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemDelete(String name) {
    }

    @Override
    public void onItemDelete(String name, int position) {
        try {
            RealmX.deleteObject(name);
            RealmResults<SavedObject> list = RealmX.getObject();
            modelList.clear();
            modelList.addAll(list);
            mAdapter = new YourColorAdapter(modelList,this);
            mRecyclerView.setAdapter(mAdapter);
        }catch (IllegalStateException ex){
            ex.printStackTrace();
        }
        AdsUtils.getInstance().increaseInteraction();
    }

    @Override
    public void onClickedPosition(int position) {
        ColorBus.getInstance().setSavedObject(modelList.get(position));
        startActivity(new Intent(YourColorActivity.this, ColorActivity.class));
    }

    @Override
    public void onDeleteAlert(final String name, final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(YourColorActivity.this);
        // set title
        alertDialogBuilder.setTitle(getString(R.string.delete_saved_object));
        alertDialogBuilder
                .setMessage(getString(R.string.delete_file_ask))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.answer_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        onItemDelete(name,position);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.answer_no),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}