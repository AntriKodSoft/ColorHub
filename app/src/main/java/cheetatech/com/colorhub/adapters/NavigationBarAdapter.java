package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.drawer.ColorSelect;


public class NavigationBarAdapter extends ArrayAdapter<ColorSelect> {

    private Context context = null;
    private int resource;
    private List<ColorSelect> colorSelect;


    public NavigationBarAdapter(Context context, int resource, ColorSelect[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    public NavigationBarAdapter(Context context, int resource, List<ColorSelect> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.colorSelect = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.navigation_row, parent, false);
        TextView textColorName = (TextView) view.findViewById(R.id.colorName);
        textColorName.setText(colorSelect.get(position).getTitle());
        return view;
    }
}

