package layout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

import cheetatech.com.colorhub.ColorPickerActivity;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.colorpicker.GradientView;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorItem;

public class ColorPicker2 extends Fragment {

    private GradientView mTop = null;
    private GradientView mBottom = null;
    private TextView mTextView = null;
    private Drawable mIcon = null;
    private int colorText = 0;
    private RelativeLayout colorView = null;
    private int _f = 0;

    View[] views = null;
    int[] viewIds = new int[]{R.id.color1,R.id.color2,R.id.color3,R.id.color4,R.id.color5};
    ColorItem[] colors = new ColorItem[]{
            new ColorItem(0),
            new ColorItem(0),
            new ColorItem(0),
            new ColorItem(0),
            new ColorItem(0),
            /*

            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
             */

    };

    ColorItem[] selectedColors = new ColorItem[]{
            new ColorItem(),
            new ColorItem(),
            new ColorItem(),
            new ColorItem(),
            new ColorItem(),
    };

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        views = new View[5];
        for (int i = 0;i<viewIds.length;i++)
        {
            views[i] = (View)getView().findViewById(viewIds[i]);
            //views[i].setOnClickListener(this);
        }

        //for(int j = 0;j<colors.length;j++)
        //    views[j].setBackgroundColor(colors[j].getColor());

        colorView = (RelativeLayout) getView().findViewById(R.id.colorView);


        //mIcon = getResources().getDrawable(R.mipmap.ic_launcher);
        mTextView = (TextView) getView().findViewById(R.id.color);

        //mTextView.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
        mTop = (GradientView)getView().findViewById(R.id.top);
        mBottom = (GradientView)getView().findViewById(R.id.bottom);
        mTop.setBrightnessGradientView(mBottom);
        mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
            @Override
            public void onColorChanged(GradientView view, int color) {
                colorText = color;
                mTextView.setText("#" + Integer.toHexString(color));
                mTextView.setTextColor(Color.parseColor(inverseColor(Integer.toHexString(color))));
                colorView.setBackgroundColor(colorText);
            }
        });

        final int color = 0xFFE12109;//0xFF394572;
        // 0xFFE12109
        mTop.setColor(color);
        colorView.setBackgroundColor(color);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(mTextView.getText().toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + mTextView.getText().toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });



        views[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeState(0);
            }
        });
        //getSlotFromBufferLocked: unknown buffer: 0xd87045a0
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

        //mBottom.setColor(selectedColors[viewValue].getColor());
        mTop.setColor(selectedColors[viewValue].getColor());
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

}
