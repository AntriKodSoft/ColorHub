package layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.defines.ColorInfo;

public class HtmlColorFragment extends Fragment {

    private ColorPicker1.OnColorListener mListener = null;
    private List<ColorInfo> colorInfoArrayList = null;

    public HtmlColorFragment() {
    }

    public static HtmlColorFragment newInstance(ColorPicker1.OnColorListener listener) {
        HtmlColorFragment fragment = new HtmlColorFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public ColorPicker1.OnColorListener getListener() {
        return mListener;
    }

    public void setListener(ColorPicker1.OnColorListener mListener) {
        this.mListener = mListener;
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
        colorInfoArrayList = controller.getHtmlList();
        GridViewArrayAdapter adapter = new GridViewArrayAdapter(getContext(),R.layout.grid_list,colorInfoArrayList,this.mListener);
        gridView.setAdapter(adapter);
    }
}
