package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.ColorArrayListAdapter;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.defines.ColorInfo;
import cheetatech.com.colorhub.listeners.ListenerModel;

public class HtmlColorFragment extends Fragment {

    private ArrayList<ColorInfo> colorInfoArrayList = null;

    public HtmlColorFragment() {
        // Required empty public constructor
    }

    public static HtmlColorFragment newInstance(String param1, String param2) {
        HtmlColorFragment fragment = new HtmlColorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_html_color, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(colorInfoArrayList == null)
            colorInfoArrayList = new ArrayList<ColorInfo>();

        GridView gridView = (GridView) getView().findViewById(R.id.gridview);

        ColorArrayController controller  = ColorArrayController.getInstance();
        colorInfoArrayList = controller.getHtmlList(); // getMaterialColorInfoList().get(0).getColorInfoList();
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,colorInfoArrayList);

        gridView.setAdapter(adapter);

        //setListAdapter(adapter);
        //getListView().setOnItemLongClickListener(this);
    }
}
