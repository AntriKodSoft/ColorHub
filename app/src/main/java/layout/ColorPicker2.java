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
import android.widget.TextView;
import android.widget.Toast;

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

        for(int j = 0;j<colors.length;j++)
            views[j].setBackgroundColor(colors[j].getColor());



        mIcon = getResources().getDrawable(R.mipmap.ic_launcher);
        mTextView = (TextView) getView().findViewById(R.id.color);

        mTextView.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
        mTop = (GradientView)getView().findViewById(R.id.top);
        mBottom = (GradientView)getView().findViewById(R.id.bottom);
        mTop.setBrightnessGradientView(mBottom);
        mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
            @Override
            public void onColorChanged(GradientView view, int color) {
                colorText = color;
                mTextView.setTextColor(color);
                mTextView.setText("#" + Integer.toHexString(color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setTint(color);
                }

            }
        });

        final int color = 0xFF394572;
        mTop.setColor(color);


        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToView(new ColorItem(colorText));
                BoardEditor.getInstance().copyToClipBoard(mTextView.getText().toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + mTextView.getText().toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });



        views[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[0].toString());
                int coloraa = 0xFF000000;
                //mBottom.setColor(coloraa);
                mTop.setColor(colors[0].getColor());
                mTop.setBrightnessGradientColor(colors[0].getColor());
                mTextView.setTextColor(colors[0].getColor());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setTint(colors[0].getColor());
                }
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + "#" + Integer.toHexString(colors[0].getColor()) +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();

            }
        });
        views[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[1].toString());
                mBottom.setColor(colors[1].getColor());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[1].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });
        views[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[2].toString());
                mBottom.setColor(colors[2].getColor());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[2].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();

            }
        });
        views[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[3].toString());
                mBottom.setColor(colors[3].getColor());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[3].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();

            }
        });
        views[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[4].toString());
                mBottom.setColor(colors[4].getColor());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[4].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
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

}
