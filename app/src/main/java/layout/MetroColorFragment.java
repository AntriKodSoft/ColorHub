package layout;

import android.graphics.Color;
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


    private ColorPicker1.OnColorListener mListener = null;

    public MetroColorFragment() {
        // Required empty public constructor
    }

    public static MetroColorFragment newInstance(ColorPicker1.OnColorListener listener){
        MetroColorFragment fragment = new MetroColorFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public ColorPicker1.OnColorListener getListener() {
        return mListener;
    }

    public void setListener(ColorPicker1.OnColorListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_metro_color, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        GridView gridView = (GridView) getView().findViewById(R.id.gridviewmetro);
        ColorArrayController controller  = ColorArrayController.getInstance();
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,controller.getMetroList(),this.mListener);
        gridView.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
