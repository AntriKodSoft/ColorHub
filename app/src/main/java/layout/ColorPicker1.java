package layout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.adapters.GridViewArrayAdapter;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorItem;
import cheetatech.com.colorhub.listeners.IOnFocusListenable;


public class ColorPicker1 extends Fragment implements IOnFocusListenable {


    View colorView;
    View[] views = null;
    int[] viewIds = new int[]{R.id.color1,R.id.color2,R.id.color3,R.id.color4,R.id.color5};
    SeekBar redSeekBar, greenSeekBar, blueSeekBar,opacitySeekBar;
    TextView redToolTip, greenToolTip, blueToolTip,opacityToolTip;
    Button buttonSelector;
    ClipboardManager clipBoard;
    ClipData clip;
    Window window;
    Display display;
    int red, green, blue, seekBarLeft,opacity;
    Rect thumbRect;

    ColorItem[] colors = new ColorItem[]{
            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
            new ColorItem(0,0,0,0),
    };


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
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        red = green = blue = 120;
        opacity = 255;

        clipBoard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        colorView = getView().findViewById(R.id.colorView);
        window = getActivity().getWindow();

        redSeekBar = (SeekBar)getView().findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar)getView().findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar)getView().findViewById(R.id.blueSeekBar);

        opacitySeekBar = (SeekBar)getView().findViewById(R.id.opacitySeekBar);

        seekBarLeft = redSeekBar.getPaddingLeft();

        redToolTip = (TextView)getView().findViewById(R.id.redToolTip);
        greenToolTip = (TextView)getView().findViewById(R.id.greenToolTip);
        blueToolTip = (TextView)getView().findViewById(R.id.blueToolTip);

        opacityToolTip = (TextView)getView().findViewById(R.id.opacityToolTip);

        buttonSelector = (Button)getView().findViewById(R.id.buttonSelector);


        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);
        opacitySeekBar.setProgress(opacity);

        colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));

        buttonSelector.setText(String.format("#%02x%02x%02x%02x", red, green, blue,opacity));

        views = new View[5];
        for (int i = 0;i<viewIds.length;i++)
        {
            views[i] = (View)getView().findViewById(viewIds[i]);
            //views[i].setOnClickListener(this);
        }

        for(int j = 0;j<colors.length;j++)
            views[j].setBackgroundColor(Color.argb(colors[j].getOpacity(),colors[j].getRed(),colors[j].getGreen(),colors[j].getBlue()));


        buttonSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToView(new ColorItem(red,green,blue,opacity));
                BoardEditor.getInstance().copyToClipBoard(buttonSelector.getText().toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + buttonSelector.getText().toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
            }
        });

        views[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[0].toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[0].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
                red = colors[0].getRed(); green = colors[0].getGreen(); blue = colors[0].getBlue(); opacity = colors[0].getOpacity();
                setProgressBar(red,green,blue,opacity);
                onWindowFocusChanged(true);
            }
        });
        views[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[1].toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[1].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
                red = colors[1].getRed(); green = colors[1].getGreen(); blue = colors[1].getBlue(); opacity = colors[1].getOpacity();
                setProgressBar(red,green,blue,opacity);
                onWindowFocusChanged(true);
            }
        });
        views[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[2].toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[2].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
                red = colors[2].getRed(); green = colors[2].getGreen(); blue = colors[2].getBlue(); opacity = colors[2].getOpacity();
                setProgressBar(red,green,blue,opacity);
                onWindowFocusChanged(true);
            }
        });
        views[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[3].toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[3].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
                red = colors[3].getRed(); green = colors[3].getGreen(); blue = colors[3].getBlue(); opacity = colors[3].getOpacity();
                setProgressBar(red,green,blue,opacity);
                onWindowFocusChanged(true);
            }
        });
        views[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BoardEditor.getInstance().copyToClipBoard(colors[4].toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colors[4].toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
                red = colors[4].getRed(); green = colors[4].getGreen(); blue = colors[4].getBlue(); opacity = colors[4].getOpacity();
                setProgressBar(red,green,blue,opacity);
                onWindowFocusChanged(true);
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

                colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                buttonSelector.setText(String.format("#%02x%02x%02x%02x", red, green, blue,opacity));
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
                thumbRect = seekBar.getThumb().getBounds();

                greenToolTip.setX(seekBar.getPaddingLeft()+thumbRect.left);
                if (progress<10)
                    greenToolTip.setText("  "+green);
                else if (progress<100)
                    greenToolTip.setText(" "+green);
                else
                    greenToolTip.setText(green+"");

                colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                buttonSelector.setText(String.format("#%02x%02x%02x%02x", red, green, blue,opacity));
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

                colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                buttonSelector.setText(String.format("#%02x%02x%02x%02x", red, green, blue,opacity));
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


                colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    if (display.getRotation() == Surface.ROTATION_0)
                        window.setStatusBarColor(Color.argb(opacity,red, green, blue));
                }
                buttonSelector.setText(String.format("#%02x%02x%02x%02x", red, green, blue,opacity));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
            views[0].setBackgroundColor(Color.argb(colorItem.getOpacity(), colorItem.getRed(), colorItem.getGreen(), colorItem.getBlue()));
            for (i = 0; i < colors.length - 1; i++)
                views[i + 1].setBackgroundColor(Color.argb(refColors[i].getOpacity(), refColors[i].getRed(), refColors[i].getGreen(), refColors[i].getBlue()));

            colors[0] = new ColorItem(colorItem.getRed(), colorItem.getGreen(), colorItem.getBlue(), colorItem.getOpacity());

            for (i = 0; i < colors.length - 1; i++)
                colors[i + 1] = new ColorItem(refColors[i].getRed(), refColors[i].getGreen(), refColors[i].getBlue(), refColors[i].getOpacity());
        }
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
