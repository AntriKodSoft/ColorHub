package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cheetatech.com.colorhub.R;

public class ColorPicker2 extends Fragment {

    public ColorPicker2() {
        // Required empty public constructor
    }

    public static ColorPicker2 newInstance(String param1, String param2) {
        ColorPicker2 fragment = new ColorPicker2();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_color_picker2, container, false);
    }

}
