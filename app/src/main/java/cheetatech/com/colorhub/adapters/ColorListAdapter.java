package cheetatech.com.colorhub.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.ColorInfo;

public class ColorListAdapter extends ArrayAdapter<ColorInfo> {

    private Context context = null;
    private ColorInfo[] colorInfos = null;
    private int resource;
    private ArrayList<ColorInfo> colorInfo;


    public ColorListAdapter(Context context, int resource, ColorInfo[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.colorInfos = objects;
    }

    public ColorListAdapter(Context context, int resource, ArrayList<ColorInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.colorInfo = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, parent, false);
        TextView textColorName = (TextView) view.findViewById(R.id.colorName);
        TextView textColorCode = (TextView) view.findViewById(R.id.colorCode);
        Button btnCopy = (Button) view.findViewById(R.id.buttonCopy);

        RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.rootColorLayout);
        if(layout != null)
            layout.setBackgroundColor(Color.RED);

        textColorCode.setText("Color");
        textColorName.setText("Name");


        /*textColorCode.setText(colorInfos[position].getColorCode());
        textColorName.setText(colorInfos[position].getColorName());*/
        return view;
    }
}
