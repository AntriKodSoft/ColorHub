package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;


public class SocialColorFragment extends Fragment implements AdapterView.OnItemLongClickListener   {


    private ColorPicker1.OnColorListener mListener = null;

    public SocialColorFragment() {
        // Required empty public constructor
    }

    public static SocialColorFragment newInstance(ColorPicker1.OnColorListener listener){
        SocialColorFragment fragment = new SocialColorFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public ColorPicker1.OnColorListener getListener() {
        return this.mListener;
    }

    public void setListener(ColorPicker1.OnColorListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_social_color, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        /*ColorArrayController controller  = ColorArrayController.getInstance();
        setListAdapter(new ColorArrayListAdapter(getContext(),R.layout.list_layout,controller.getSocialList()));
        getListView().setOnItemLongClickListener(this);
        */

        GridView gridView = (GridView) getView().findViewById(R.id.gridviewsocial);
        ColorArrayController controller  = ColorArrayController.getInstance();
        //GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,controller.getSocialList());
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,controller.getSocialList(),this.mListener);
        gridView.setAdapter(adapter);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
