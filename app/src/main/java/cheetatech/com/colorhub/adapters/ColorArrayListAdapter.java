package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorInfo;


public class ColorArrayListAdapter extends ArrayAdapter<ColorInfo>  {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, parent, false);
        if(position != colorInfos.size()-1 ) {
            TextView textColorName = (TextView) view.findViewById(R.id.colorName);
            TextView textColorCode = (TextView) view.findViewById(R.id.colorCode);
            ImageButton btnCopy = (ImageButton) view.findViewById(R.id.buttonCopy);
            btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "Clicked Image Button " + position);
                    BoardEditor.getInstance().copyToClipBoard(colorInfos.get(position).getColorCode());
                    Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colorInfos.get(position).getColorCode() +
                            " copied to clipboard...", Toast.LENGTH_SHORT).show();
                    //ToolBarController.getInstance().setColor(Color.parseColor(colorInfos.get(position).getColorCode()));

                }
            });
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.rootColorLayout);
            if (layout != null)
                layout.setBackgroundColor(Color.parseColor(colorInfos.get(position).getColorCode()));
            textColorCode.setText(colorInfos.get(position).getColorCode());
            textColorName.setText(colorInfos.get(position).getColorName());


            textColorCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToolBarController.getInstance().setTextColor(Color.parseColor(colorInfos.get(position).getColorCode()));
                    ///tabLayout.setTabTextColors(Color.RED,Color.BLUE);
                }
            });

        }else
            view.setVisibility(View.GONE);
        return view;
    }

}
