package cheetatech.com.colorhub.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.LayoutInflater;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.ColorInfo;

public class LazyAdapter extends BaseAdapter {

    private ColorInfo[] colorInfos = null;
    private Context context = null;

    public LazyAdapter() {}
    public LazyAdapter(Context context,ColorInfo[] colorInfos)
    {
        this.context = context;
        this.colorInfos = colorInfos;
    }


    @Override
    public int getCount() {
        return colorInfos.length;
    }

    @Override
    public Object getItem(int i) {
        return colorInfos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_layout, null);

        TextView textColorName = (TextView) view.findViewById(R.id.colorName);
        TextView textColorCode = (TextView) view.findViewById(R.id.colorCode);
        ImageButton btnCopy = (ImageButton) view.findViewById(R.id.buttonCopy);

        textColorCode.setText("Color");
        textColorName.setText("Name");

        return view;
    }
}
