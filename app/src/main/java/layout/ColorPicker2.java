package layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.colorpicker.GradientView;

public class ColorPicker2 extends Fragment {

    private GradientView mTop = null;
    private GradientView mBottom = null;
    private TextView mTextView = null;
    private Drawable mIcon = null;

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

        mIcon = getResources().getDrawable(R.mipmap.ic_launcher);
        mTextView = (TextView) getView().findViewById(R.id.color);

        mTextView.setCompoundDrawablesWithIntrinsicBounds(mIcon, null, null, null);
        mTop = (GradientView)getView().findViewById(R.id.top);
        mBottom = (GradientView)getView().findViewById(R.id.bottom);
        mTop.setBrightnessGradientView(mBottom);
        mBottom.setOnColorChangedListener(new GradientView.OnColorChangedListener() {
            @Override
            public void onColorChanged(GradientView view, int color) {
                mTextView.setTextColor(color);
                mTextView.setText("#" + Integer.toHexString(color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mIcon.setTint(color);
                }
            }
        });

        int color = 0xFF394572;
        mTop.setColor(color);

    }

}
