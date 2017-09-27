package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;

public class FlatColorFragment extends Fragment  {

    private ColorPicker1.OnColorListener mListener = null;
    public FlatColorFragment() {

    }

    public static FlatColorFragment newInstance(ColorPicker1.OnColorListener listener) {
        FlatColorFragment fragment = new FlatColorFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(ColorPicker1.OnColorListener listener) {
        this.mListener = listener;
    }

    public ColorPicker1.OnColorListener getListener() {
        return this.mListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flat_color, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ColorArrayController controller  = ColorArrayController.getInstance();
        GridView gridView = (GridView) getView().findViewById(R.id.gridviewflat);
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,controller.getFlatList(),this.mListener);
        gridView.setAdapter(adapter);
    }

}
