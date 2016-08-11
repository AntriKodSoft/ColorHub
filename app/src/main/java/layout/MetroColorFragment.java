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

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.ColorArrayListAdapter;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;

public class MetroColorFragment extends Fragment implements AdapterView.OnItemLongClickListener  {


    public MetroColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_metro_color, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        /*ColorArrayController controller  = ColorArrayController.getInstance();
        setListAdapter(new ColorArrayListAdapter(getContext(),R.layout.list_layout,controller.getMetroList()));
        getListView().setOnItemLongClickListener(this);
        */
        GridView gridView = (GridView) getView().findViewById(R.id.gridviewmetro);
        ColorArrayController controller  = ColorArrayController.getInstance();
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,controller.getMetroList());
        gridView.setAdapter(adapter);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
