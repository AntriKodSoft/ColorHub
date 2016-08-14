package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.ColorArrayListAdapter;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.apprater.AppRater;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.defines.ColorInfo;
import cheetatech.com.colorhub.listeners.ListenerModel;


public class MaterialColorFragment extends Fragment implements AdapterView.OnItemLongClickListener , ListenerModel.OnModelStateListener {

    private GridView gridView = null;
    private ArrayList<ColorInfo> colorInfoArrayList = null;

    public MaterialColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_material_color, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(colorInfoArrayList == null)
            colorInfoArrayList = new ArrayList<ColorInfo>();

        ListenerModel.getInstance().setListener(this);




        /*
        ColorArrayController controller  = ColorArrayController.getInstance();
        colorInfoArrayList = controller.getMaterialColorInfoList().get(0).getColorInfoList();
        ColorArrayListAdapter adapter = new ColorArrayListAdapter(getContext(),R.layout.list_layout,colorInfoArrayList);
        setListAdapter(adapter);
        getListView().setOnItemLongClickListener(this);
        */

        ColorArrayController controller  = ColorArrayController.getInstance();
        colorInfoArrayList = controller.getMaterialColorInfoList().get(0).getColorInfoList();
        //ColorArrayListAdapter adapter = new ColorArrayListAdapter(getContext(),R.layout.list_layout,colorInfoArrayList);

        gridView = (GridView) getView().findViewById(R.id.gridviewmaterial);
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,colorInfoArrayList);

        gridView.setAdapter(adapter);



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("TAGG", "SelectedLongListItem : " + i + " : ");
        return false;
    }

    @Override
    public void onSelectedColorIndex(int index) {
        ColorArrayController controller  = ColorArrayController.getInstance();
        colorInfoArrayList = controller.getMaterialColorInfoList().get(index).getColorInfoList();
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,colorInfoArrayList);
        gridView.setAdapter(adapter);
    }
}
