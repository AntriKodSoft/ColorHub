package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.ColorArrayListAdapter;
import cheetatech.com.colorhub.adapters.ColorListAdapter;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.defines.ColorInfo;

/**
 *     <color name="colorDrawerBackground">#616161</color>
 <color name="colorDrawerText">#EEEEEE</color>
 * */

public class FlatColorFragment extends Fragment implements AdapterView.OnItemLongClickListener  {

    public FlatColorFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flat_color, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


        /*
        ColorArrayController controller  = ColorArrayController.getInstance();
        setListAdapter(new ColorArrayListAdapter(getContext(),R.layout.list_layout,controller.getFlatList()));
        getListView().setOnItemLongClickListener(this);
        */
        ColorArrayController controller  = ColorArrayController.getInstance();
        GridView gridView = (GridView) getView().findViewById(R.id.gridviewflat);
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,controller.getFlatList());

        gridView.setAdapter(adapter);



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
