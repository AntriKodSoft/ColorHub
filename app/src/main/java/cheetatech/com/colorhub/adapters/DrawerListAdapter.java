package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.drawer.NavigationSelect;


public class DrawerListAdapter extends ArrayAdapter<NavigationSelect> {

    private Context context = null;
    private ArrayList<NavigationSelect> navigationSelects;

    public DrawerListAdapter(Context context, int resource, ArrayList<NavigationSelect> objects) {
        super(context, resource, objects);
        this.context = context;
        this.navigationSelects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        if(position == 0)
        {
            view = inflater.inflate(R.layout.drawer_text_item, parent, false);
        }
        else if(position == 1)
        {
            view = inflater.inflate(R.layout.drawer_item_logo, parent, false);

        }else
        {
            view = inflater.inflate(R.layout.drawer_item, parent, false);
            TextView textTitle = (TextView) view.findViewById(R.id.drawer_text);
            textTitle.setText(navigationSelects.get(position).getTitle());
            ImageView imageLogo = (ImageView) view.findViewById(R.id.drawer_image);
            imageLogo.setBackgroundResource(navigationSelects.get(position).getDrawableId());
        }
        return view;
    }
}

