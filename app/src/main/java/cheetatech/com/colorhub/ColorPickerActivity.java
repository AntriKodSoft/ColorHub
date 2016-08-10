package cheetatech.com.colorhub;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import cheetatech.com.colorhub.defines.BoardEditor;

public class ColorPickerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    public static final String MIXPANEL_TOKEN = "8df466c6bae9ae3c3117de78e1af819d";

    View colorView;
    SeekBar redSeekBar, greenSeekBar, blueSeekBar,opacitySeekBar;
    TextView redToolTip, greenToolTip, blueToolTip,opacityToolTip;
    Button buttonSelector;
    ClipboardManager clipBoard;
    ClipData clip;
    Window window;
    Display display;
    int red, green, blue, seekBarLeft,opacity;
    Rect thumbRect;
    AlertDialog alertDialog;

    JSONObject props;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_back_button);
        getSupportActionBar().setTitle("ColorHub");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        red = green = blue = 0;
        opacity = 255;

        clipBoard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        colorView = findViewById(R.id.colorView);
        window = getWindow();

        redSeekBar = (SeekBar)findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar)findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar)findViewById(R.id.blueSeekBar);

        opacitySeekBar = (SeekBar)findViewById(R.id.opacitySeekBar);

        seekBarLeft = redSeekBar.getPaddingLeft();

        redToolTip = (TextView)findViewById(R.id.redToolTip);
        greenToolTip = (TextView)findViewById(R.id.greenToolTip);
        blueToolTip = (TextView)findViewById(R.id.blueToolTip);

        opacityToolTip = (TextView)findViewById(R.id.opacityToolTip);

        buttonSelector = (Button)findViewById(R.id.buttonSelector);
        buttonSelector.setOnClickListener(this);

        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);
        opacitySeekBar.setOnSeekBarChangeListener(this);


        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);
        opacitySeekBar.setProgress(opacity);


        //Setting View, Status bar & button color & hex codes

        //colorView.setBackgroundColor(Color.rgb(red, green, blue));
        colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));

        //Set's color hex on Button
        buttonSelector.setText(String.format("#%02x%02x%02x%02x", opacity,red, green, blue));
        //buttonSelector.setText(String.format("#%02x%02x%02x", red, green, blue));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {


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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar.getId() == R.id.redSeekBar) {

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

        }
        else if (seekBar.getId() == R.id.greenSeekBar) {

            green = progress;
            thumbRect = seekBar.getThumb().getBounds();

            greenToolTip.setX(seekBar.getPaddingLeft()+thumbRect.left);
            if (progress<10)
                greenToolTip.setText("  "+green);
            else if (progress<100)
                greenToolTip.setText(" "+green);
            else
                greenToolTip.setText(green+"");

        }
        else if (seekBar.getId() == R.id.blueSeekBar) {

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

        }
        else if (seekBar.getId() == R.id.opacitySeekBar) {

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

        }

        colorView.setBackgroundColor(Color.argb(opacity,red, green, blue));

        //colorView.setBackgroundColor(Color.rgb(red, green, blue));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (display.getRotation() == Surface.ROTATION_0)
                window.setStatusBarColor(Color.argb(opacity,red, green, blue));
            //window.setStatusBarColor(Color.rgb(red, green, blue));

        }

        //Setting the button hex color
        //buttonSelector.setText(String.format("#%02x%02x%02x", red, green, blue));
        buttonSelector.setText(String.format("#%02x%02x%02x%02x", opacity, red, green, blue));
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.buttonSelector:
                BoardEditor.getInstance().copyToClipBoard(buttonSelector.getText().toString());
                Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + buttonSelector.getText().toString() +
                        " copied to clipboard...", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}