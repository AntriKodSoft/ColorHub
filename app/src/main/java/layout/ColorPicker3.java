package layout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

import cheetatech.com.colorhub.ColorPickerActivity;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorItem;
import cheetatech.com.colorhub.view.ColorPanelView;
import cheetatech.com.colorhub.view.ColorPickerView;

public class ColorPicker3 extends Fragment implements ColorPickerView.OnColorChangedListener {

    RelativeLayout colorView = null;
    private View[] views = null;
    int[] viewIds = new int[]{R.id.color1,R.id.color2,R.id.color3,R.id.color4,R.id.color5};
    private TextView colorTextView = null;
    private int colorText = 0;

    ColorItem[] selectedColors = new ColorItem[]{
            new ColorItem(),
            new ColorItem(),
            new ColorItem(),
            new ColorItem(),
            new ColorItem(),
    };


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

        views = new View[5];
        for (int i = 0;i<viewIds.length;i++)
            views[i] = (View)getView().findViewById(viewIds[i]);

        colorView = (RelativeLayout) getView().findViewById(R.id.colorView);
        colorTextView = (TextView)getView().findViewById(R.id.color);

        mColorPickerView = (ColorPickerView) getView().findViewById(R.id.colorpickerview__color_picker_view1);

        final int initialColor = 0xFFE12109;;

        mColorPickerView.setOnColorChangedListener(this);
        mColorPickerView.setColor(initialColor, true);
        colorView.setBackgroundColor(initialColor);

        colorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colorTextView.getText().toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colorTextView.getText().toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });



        views[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(0);
            }
        });
        views[0].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return setValue(0);
            }
        });


        views[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(1);
            }
        });
        views[1].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return setValue(1);
            }
        });

        views[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(2);
            }
        });
        views[2].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return setValue(2);
            }
        });


        views[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(3);
            }
        });
        views[3].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return setValue(3);
            }
        });

        views[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(4);
            }
        });

        views[4].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return setValue(4);
            }
        });

    }

    private boolean setValue(int viewValue)
    {
        if(!selectedColors[viewValue].getHasColor())
            return true;
        mColorPickerView.setColor(selectedColors[viewValue].getColor(), true);
        colorText = selectedColors[viewValue].getColor();
        return false;
    }
    public void changeState(int viewValue)
    {
        if (!ColorPickerActivity.erase)
        {
            selectedColors[viewValue].setColor(colorText);
            views[viewValue].setBackgroundColor(selectedColors[viewValue].getColor());
        }
        else
        {
            selectedColors[viewValue].setColors(255, 255, 255, 255);
            selectedColors[viewValue].setHasColor(false);
            views[viewValue].setBackgroundColor(Color.parseColor(selectedColors[viewValue].toString()));
        }
    }

    private String inverseColor(String hexColor)
    {
        String ff = "FF";
        String r = hexColor.substring(2,4);
        String g = hexColor.substring(4,6);
        String b = hexColor.substring(6,8);


        BigInteger ri = new BigInteger(r,16);
        BigInteger gi = new BigInteger(g,16);
        BigInteger bi = new BigInteger(b,16);


        BigInteger fi = new BigInteger(ff,16);

        BigInteger rs = ri.xor(fi);
        BigInteger gs = gi.xor(fi);
        BigInteger bs = bi.xor(fi);

        String res = String.format("#%02x%02x%02x",rs,gs,bs);
        return res;
    }

    @Override
    public void onColorChanged(int newColor) {

        colorText = newColor;
        colorTextView.setText("#" + Integer.toHexString(newColor));
        colorTextView.setTextColor(Color.parseColor(inverseColor(Integer.toHexString(newColor))));
        colorView.setBackgroundColor(colorText);
    }
}
