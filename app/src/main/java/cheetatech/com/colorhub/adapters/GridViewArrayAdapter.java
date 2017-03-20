package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.controller.ColorArrayController;
import cheetatech.com.colorhub.controller.ToolBarController;
import cheetatech.com.colorhub.defines.BoardEditor;
import cheetatech.com.colorhub.defines.ColorInfo;
import layout.ColorPicker1;


public class GridViewArrayAdapter extends ArrayAdapter<ColorInfo>  {
    private Context context = null;
    private int resource;
    private ArrayList<ColorInfo> colorInfos;
    public static int HeaderIndex = -1;
    public static boolean isMaterial = false;
    private ColorPicker1.OnColorListener mListener = null;

    public GridViewArrayAdapter(Context context, int resource, ColorInfo[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    public GridViewArrayAdapter(Context context, int resource, ArrayList<ColorInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.colorInfos = objects;
    }

    public GridViewArrayAdapter(Context context, int resource, ArrayList<ColorInfo> objects, ColorPicker1.OnColorListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.colorInfos = objects;
        this.mListener = listener;
    }

    static class ViewHolder{
        @BindView(R.id.colorName)
        TextView textColorName;
        @BindView(R.id.colorCode)
        TextView textColorCode;
        @BindView(R.id.buttonCopy)
        ImageButton btnCopy;

        @BindView(R.id.rootColorLayout)
        RelativeLayout mLayout;

        @BindView(R.id.add_color_image_button)
        ImageView mColorAddButton;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;        View view = convertView;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(this.resource, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }


        if(position != colorInfos.size()-1 ) {

            holder.btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG", "Clicked Image Button Erkan Erkan " + position);
                    BoardEditor.getInstance().copyToClipBoard(colorInfos.get(position).getColorCode());
                    Toast.makeText(BoardEditor.getInstance().getContext(), "Color " + colorInfos.get(position).getColorCode() +
                            " copied to clipboard...", Toast.LENGTH_SHORT).show();
                }
            });
            if (holder.mLayout != null)
                holder.mLayout.setBackgroundColor(Color.parseColor(colorInfos.get(position).getColorCode()));
            holder.textColorCode.setText(colorInfos.get(position).getColorCode());
            holder.textColorName.setText(colorInfos.get(position).getColorName());
            holder.mColorAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ColorInfo colorInfo = colorInfos.get(position);
                    Log.e("TAG", "onClick: colorInfos " + colorInfo.getColorCode() +  " : " + colorInfo.getColorName());
                    if(mListener != null){
                        mListener.onAddColor(colorInfo.getColorCode());
                    }
                }
            });

        }else
            view.setVisibility(View.GONE);
        return view;
    }

}
