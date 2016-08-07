package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.ColorInfo;


public class ColorArrayListAdapter extends ArrayAdapter<ColorInfo> {

    private Context context = null;
    private int resource;
    private ArrayList<ColorInfo> colorInfos;


    public ColorArrayListAdapter(Context context, int resource, ColorInfo[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    public ColorArrayListAdapter(Context context, int resource, ArrayList<ColorInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.colorInfos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, parent, false);
        TextView textColorName = (TextView) view.findViewById(R.id.colorName);
        TextView textColorCode = (TextView) view.findViewById(R.id.colorCode);
        ImageButton btnCopy = (ImageButton) view.findViewById(R.id.buttonCopy);

        RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.rootColorLayout);
        if(layout != null)
            layout.setBackgroundColor(Color.parseColor(colorInfos.get(position).getColorCode()));
        textColorCode.setText(colorInfos.get(position).getColorCode());
        textColorName.setText(colorInfos.get(position).getColorName());
        return view;
    }
}
