package layout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.Util;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.listeners.IOnFocusListenable;
import cheetatech.com.colorhub.models.Model;

/**
 * Created by erkan on 22.03.2017.
 */

public class ColorPickerAdd extends Fragment implements IOnFocusListenable {


    @BindView(R.id.colorView2)
    RelativeLayout colorViewLayout;

    @BindView(R.id.textViewColor)
    TextView textViewColor ;

    @BindView(R.id.redSeekBar)
    SeekBar redSeekBar;

    @BindView(R.id.greenSeekBar)
    SeekBar greenSeekBar;

    @BindView(R.id.blueSeekBar)
    SeekBar blueSeekBar;

    @BindView(R.id.opacitySeekBar)
    SeekBar opacitySeekBar;

    @BindView(R.id.redToolTip)
    TextView redToolTip;

    @BindView(R.id.greenToolTip)
    TextView greenToolTip;

    @BindView(R.id.blueToolTip)
    TextView blueToolTip;

    @BindView(R.id.opacityToolTip)
    TextView opacityToolTip;


    ClipboardManager clipBoard;
    ClipData clip;
    Window window;
    Display display;
    int red, green, blue, seekBarLeft,opacity;
    Rect thumbRect;

    private int mPosition;
    private Model mModel = null;


    public interface OnColorPickerAddListener{
        void onAddNewColor(String color);
    }

    private OnColorPickerAddListener mListener;

    public ColorPickerAdd() {
        // Required empty public constructor
    }

    public OnColorPickerAddListener getListener() {
        return mListener;
    }

    public void setListener(OnColorPickerAddListener mListener) {
        this.mListener = mListener;
    }

    public static ColorPickerAdd newInstance(OnColorPickerAddListener listener) {
        ColorPickerAdd fragment = new ColorPickerAdd();
        fragment.setListener(listener);
        return fragment;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public Model getModel() {
        return mModel;
    }

    public void setModel(Model mModel) {
        this.mModel = mModel;
    }

    @OnClick(R.id.add_color_button) void addColorClick(){
        String color = textViewColor.getText().toString();
        //this.mModel.setColorCode(color);
        this.mListener.onAddNewColor(color);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_picker1, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        red = green = blue = 120;
        opacity = 255;

        // initialize;
//        if(mModel != null){
//            Util.ColorX colorX = Util.stringToDec(mModel.getColorCode());
//            red = colorX.getRed();
//            green = colorX.getGreen();
//            blue = colorX.getBlue();
//            opacity = colorX.getOpacity();
//            //colorX.toString();
//        }else{
//            red = green = blue = 120;
//            opacity = 255;
//        }

        //
        clipBoard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        window = getActivity().getWindow();

        seekBarLeft = redSeekBar.getPaddingLeft();

        textViewColor.setTextColor(Color.parseColor(inverseColor(red,green,blue)));


        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);
        opacitySeekBar.setProgress(opacity);

        colorViewLayout.setBackgroundColor(Color.argb(opacity,red, green, blue));

        textViewColor.setText(String.format("#%02x%02x%02x%02x", opacity, red, green, blue));
        textViewColor.setTextColor(Color.parseColor(inverseColor(red,green,blue)));

        textViewColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addToView(new ColorItem(red,green,blue,opacity));
                BoardEditor.getInstance().copyToClipBoard(textViewColor.getText().toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + textViewColor.getText().toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });


        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                red = progress;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    thumbRect = seekBar.getThumb().getBounds();
                }

                redToolTip.setX(seekBarLeft + thumbRect.left);

                if (progress<10)
                    redToolTip.setText("  " + red);
                else if (progress<100)
                    redToolTip.setText(" "+red);
                else
                    redToolTip.setText(red+"");

                colorViewLayout.setBackgroundColor(Color.argb(opacity,red, green, blue));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                textViewColor.setText(String.format("#%02x%02x%02x%02x", opacity,red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                green = progress;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    thumbRect = seekBar.getThumb().getBounds();
                }

                greenToolTip.setX(seekBar.getPaddingLeft()+thumbRect.left);
                if (progress<10)
                    greenToolTip.setText("  "+green);
                else if (progress<100)
                    greenToolTip.setText(" "+green);
                else
                    greenToolTip.setText(green+"");

                //colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));
                colorViewLayout.setBackgroundColor(Color.argb(opacity,red, green, blue));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                textViewColor.setText(String.format("#%02x%02x%02x%02x", opacity,red, green, blue));
                textViewColor.setTextColor(Color.parseColor(inverseColor(red,green,blue)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                blue = progress;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    thumbRect = seekBar.getThumb().getBounds();
                }

                blueToolTip.setX(seekBarLeft + thumbRect.left);
                if (progress<10)
                    blueToolTip.setText("  "+blue);
                else if (progress<100)
                    blueToolTip.setText(" "+blue);
                else
                    blueToolTip.setText(blue+"");

                //colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));
                colorViewLayout.setBackgroundColor(Color.argb(opacity,red, green, blue));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                textViewColor.setText(String.format("#%02x%02x%02x%02x", opacity,red, green, blue));
                textViewColor.setTextColor(Color.parseColor(inverseColor(red,green,blue)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        opacitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                opacity = progress;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    thumbRect = seekBar.getThumb().getBounds();
                }
                opacityToolTip.setX(seekBarLeft + thumbRect.left);
                if (progress<10)
                    opacityToolTip.setText("  "+opacity);
                else if (progress<100)
                    opacityToolTip.setText(" "+opacity);
                else
                    opacityToolTip.setText(opacity+"");


                //colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));
                colorViewLayout.setBackgroundColor(Color.argb(opacity,red, green, blue));


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                textViewColor.setText(String.format("#%02x%02x%02x%02x", opacity,red, green, blue));
                textViewColor.setTextColor(Color.parseColor(inverseColor(red,green,blue)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }


    private boolean setValue(int viewValue)
    {
        setProgressBar(red,green,blue,opacity);
        onWindowFocusChanged(true);
        return false;
    }

    private String inverseColor(int red, int green, int blue)
    {
        String ff = "FF";
        String r = String.format("%02x", red);
        String g = String.format("%02x", green);
        String b = String.format("%02x", blue);

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


    private void setProgressBar(int r,int g,int b,int o)
    {

        redSeekBar.setProgress(r);
        greenSeekBar.setProgress(g);
        blueSeekBar.setProgress(b);
        opacitySeekBar.setProgress(o);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            thumbRect = redSeekBar.getThumb().getBounds();
        }

        redToolTip.setX(seekBarLeft + thumbRect.left);
        if (red<10)
            redToolTip.setText("  "+red);
        else if (red<100)
            redToolTip.setText(" "+red);
        else
            redToolTip.setText(red+"");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            thumbRect = greenSeekBar.getThumb().getBounds();
        }

        greenToolTip.setX(seekBarLeft + thumbRect.left);
        if (green<10)
            greenToolTip.setText("  "+green);
        else if (red<100)
            greenToolTip.setText(" "+green);
        else
            greenToolTip.setText(green+"");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            thumbRect = blueSeekBar.getThumb().getBounds();
        }

        blueToolTip.setX(seekBarLeft + thumbRect.left);
        if (blue<10)
            blueToolTip.setText("  "+blue);
        else if (blue<100)
            blueToolTip.setText(" "+blue);
        else
            blueToolTip.setText(blue+"");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            thumbRect = opacitySeekBar.getThumb().getBounds();
        }

        opacityToolTip.setX(seekBarLeft + thumbRect.left);
        if (opacity<10)
            opacityToolTip.setText("  "+opacity);
        else if (opacity<100)
            opacityToolTip.setText(" "+opacity);
        else
            opacityToolTip.setText(opacity+"");
    }

}
