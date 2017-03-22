package cheetatech.com.colorhub.paletteitem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.ColorAdapter;
import cheetatech.com.colorhub.ads.AdsUtils;
import cheetatech.com.colorhub.dialog.EditDialog;
import cheetatech.com.colorhub.listeners.RecyclerItemClickListener;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.realm.SavedObject;
import cheetatech.com.colorhub.yourcolors.YourColorActivity;
import layout.ColorPickerAdd;
import layout.ColorPickerArrange;

public class ColorActivity extends AppCompatActivity implements ColorPickerArrange.OnColorListener, EditDialog.OnEditListener, ColorAdapter.OnColorAdapterListener, ColorPickerAdd.OnColorPickerAddListener{

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_menu)
    FloatingActionsMenu fabMenu;

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
    }

    @OnClick(R.id.fab) void fabClick(){
        (EditDialog.newInstance(object.getName(),this)).show(getSupportFragmentManager(),"EditDialog");
    }

    @OnClick(R.id.fab_edit) void fabEditClick(){
        (EditDialog.newInstance(object.getName(),this)).show(getSupportFragmentManager(),"EditDialog");
        collapse();
    }

    @OnClick(R.id.fab_add_palette) void fabAddColorClick(){
        ColorPickerAdd colorPicker1 = ColorPickerAdd.newInstance(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,colorPicker1).addToBackStack(null).commit();
        collapse();
    }

    private void loadAdapters() {

        colorList.clear();
        colorList.addAll(object.getList());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ColorAdapter(colorList,this);
        mRecyclerView.setAdapter(mAdapter);
        final ColorPickerArrange.OnColorListener listener = this;
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                collapse();
//                Model model = colorList.get(position);
//                ColorPickerArrange colorPicker1 = ColorPickerArrange.newInstance(model,position,listener);
//                getSupportFragmentManager().beginTransaction().replace(R.id.container,colorPicker1).addToBackStack(null).commit();
//                ColorBus.(getInstance).setSavedObject(modelList.get(position));
//                startActivity(new Intent(YourColorActivity.this, ColorActivity.class));
            }
        }));
    }


    private void collapse(){
        if(fabMenu.isExpanded())
            fabMenu.collapse();
    }

    @Override
    public void onAddColor(String color) {

    }

    @Override
    public void onChangeColor(int position, Model model) {
        this.colorList.set(position,model);
        /// /this.colorList.remove(position);
        //this.colorList.add(model);
        //RealmX.getObject();
        this.mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditedName(String name) {
        Log.e("TAG", "onEditedName: " + name);
        object.setNameQuery(name);
        getSupportActionBar().setTitle(object.getName());
        AdsUtils.getInstance().increaseInteraction();
    }

    @Override
    public void onItemClicked(int position) {
        collapse();
        Model model = colorList.get(position);
        ColorPickerArrange colorPicker1 = ColorPickerArrange.newInstance(model,position,this);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,colorPicker1).addToBackStack(null).commit();
        AdsUtils.getInstance().increaseInteraction();
    }

    @Override
    public void onDeleteItemFromList(int position) {
        colorList.remove(position);
        object.addList(colorList);
        colorList.clear();
        colorList.addAll(object.getList());
        mAdapter.notifyDataSetChanged();
        AdsUtils.getInstance().increaseInteraction();
    }

    @Override
    public void onAskDeleteItem(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ColorActivity.this);

        // set title
        alertDialogBuilder.setTitle(getString(R.string.delete_saved_object));
        alertDialogBuilder
                .setMessage(getString(R.string.delete_this_color))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.answer_yes),new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        onDeleteItemFromList(position);
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

    @Override
    public void onAddNewColor(String color) {
        for (Model m: colorList) {
            if(m.getColorCode().equals(color)){
                onMessage(getString(R.string.allready_added_color));
                return;
            }
        }
        colorList.add(new Model(color));
        object.addList(colorList);
        colorList.clear();
        colorList.addAll(object.getList());
        mAdapter.notifyDataSetChanged();
        onMessage(getString(R.string.success_add_color));
        AdsUtils.getInstance().increaseInteraction();
    }

    private void onMessage(String msg){
        Toast.makeText(ColorActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

}
