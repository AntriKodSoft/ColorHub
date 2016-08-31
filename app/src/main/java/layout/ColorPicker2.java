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

        final int color = 0xFF394572;
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
                selectedColors[0].setColor(colorText);
                views[0].setBackgroundColor(selectedColors[0].getColor());
            }
        });
        views[0].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!selectedColors[0].getHasColor())
                    return true;
                Log.e("kkkakkad","dsfkjişasdkf");
                mBottom.setColor(selectedColors[0].getColor());
                mTop.setColor(selectedColors[0].getColor());
                colorText = selectedColors[0].getColor();
                return false;
            }
        });


        views[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColors[1].setColor(colorText);
                views[1].setBackgroundColor(selectedColors[1].getColor());
            }
        });
        views[1].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!selectedColors[1].getHasColor())
                    return true;
                mBottom.setColor(selectedColors[1].getColor());
                mTop.setColor(selectedColors[1].getColor());
                colorText = selectedColors[1].getColor();
                return false;
            }
        });

        views[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColors[2].setColor(colorText);
                views[2].setBackgroundColor(selectedColors[2].getColor());
            }
        });
        views[2].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!selectedColors[2].getHasColor())
                    return true;
                mBottom.setColor(selectedColors[2].getColor());
                mTop.setColor(selectedColors[2].getColor());
                colorText = selectedColors[2].getColor();
                return false;
            }
        });


        views[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColors[3].setColor(colorText);
                views[3].setBackgroundColor(selectedColors[3].getColor());

            }
        });
        views[3].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!selectedColors[3].getHasColor())
                    return true;
                mBottom.setColor(selectedColors[3].getColor());
                colorText = selectedColors[3].getColor();
                mTop.setColor(selectedColors[3].getColor());
                return false;
            }
        });

        views[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedColors[4].setColor(colorText);
                views[4].setBackgroundColor(selectedColors[4].getColor());
            }
        });

        views[4].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!selectedColors[3].getHasColor())
                    return true;
                mBottom.setColor(selectedColors[4].getColor());
                colorText = selectedColors[4].getColor();
                mTop.setColor(selectedColors[4].getColor());
                return false;
            }
        });

    }
    private void addToView(ColorItem colorItem) {

        if(views != null) {
            ColorItem[] refColors = new ColorItem[6];
            int i;
            for(i = 0;i<colors.length;i++)
                refColors[i] = colors[i];
            views[0].setBackgroundColor(Color.BLUE);
            //views[0].setBackgroundColor(Color.argb(colorItem.getOpacity(), colorItem.getRed(), colorItem.getGreen(), colorItem.getBlue()));
            views[0].setBackgroundColor(colorItem.getColor());
            for (i = 0; i < colors.length - 1; i++)
                views[i + 1].setBackgroundColor(refColors[i].getColor());

            colors[0] = new ColorItem(colorItem.getColor());

            for (i = 0; i < colors.length - 1; i++)
                colors[i + 1] = new ColorItem(refColors[i].getColor());
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
