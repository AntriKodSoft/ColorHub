package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cheetatech.com.colorhub.R;


public class ColorPicker1 extends Fragment {

    public ColorPicker1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ColorPicker1 newInstance(String param1, String param2) {
        ColorPicker1 fragment = new ColorPicker1();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_picker1, container, false);
    }

}
