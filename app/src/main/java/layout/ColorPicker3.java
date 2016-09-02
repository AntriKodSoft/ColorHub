package layout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.view.ColorPanelView;
import cheetatech.com.colorhub.view.ColorPickerView;

public class ColorPicker3 extends Fragment implements ColorPickerView.OnColorChangedListener {



    private ColorPickerView mColorPickerView;
    private ColorPanelView mOldColorPanelView;
    private ColorPanelView mNewColorPanelView;

    public ColorPicker3() {
        // Required empty public constructor
    }

    public static ColorPicker3 newInstance(String param1, String param2) {
        ColorPicker3 fragment = new ColorPicker3();
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
        return inflater.inflate(R.layout.fragment_color_picker3, container, false);


    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int initialColor = prefs.getInt("color_3", 0xFFFF0000);

        mColorPickerView = (ColorPickerView) getView().findViewById(R.id.colorpickerview__color_picker_view1);
        mOldColorPanelView = (ColorPanelView) getView().findViewById(R.id.colorpickerview__color_panel_old1);
        mNewColorPanelView = (ColorPanelView) getView().findViewById(R.id.colorpickerview__color_panel_new1);




        ((LinearLayout) mOldColorPanelView.getParent()).setPadding(
                mColorPickerView.getPaddingLeft(), 0,
                mColorPickerView.getPaddingRight(), 0);


        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        mOldColorPanelView.setColor(initialColor);

    }


    @Override
    public void onColorChanged(int newColor) {
        //mNewColorPanelView.setColor(mColorPickerView.getColor());
        Log.e("OnColorChanged "," : "+mColorPickerView.getColor() + " :: " + Integer.toHexString(mColorPickerView.getColor()) );
    }
}
