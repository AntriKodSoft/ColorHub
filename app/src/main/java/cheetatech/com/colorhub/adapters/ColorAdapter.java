package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.models.Model;

/**
 * Created by erkan on 22.03.2017.
 */

public class ColorAdapter  extends RecyclerView.Adapter<ColorAdapter.ViewHolder>{

    private int sayac = 0;
    private List<Model> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color_layout)
        RelativeLayout mLayout;

        @BindView(R.id.color_text)
        TextView mColorText;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void add(int position, Model item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Model item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public ColorAdapter(List<Model> myDataset){
        this.mDataset = myDataset;
    }

    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("TAG","viewType " + viewType + " sayac : " + sayac++);
        Log.e("TAG","_init_ " + 1);
        int layout = R.layout.color_list_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ColorAdapter.ViewHolder vh = new ColorAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ColorAdapter.ViewHolder holder, int position) {
        Log.e("TAG","_init_ " + 2);
        if(context != null){
            Model model = this.mDataset.get(position);
            String color = model.getColorCode();
            holder.mLayout.setBackgroundColor(Color.parseColor(color));
            holder.mColorText.setText(color);
            holder.mColorText.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 10 ? 1 : 0;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private String inverseColor(String color)
    {
        //String color = "#FF34FBCC";

        String R = color.substring(3,5);
        String G = color.substring(5,7);
        String B = color.substring(7);
        int red, green, blue;
        try {
            red = Integer.parseInt(R);
            green = Integer.parseInt(G);
            blue = Integer.parseInt(B);
        }catch (NumberFormatException e){
            red = green = blue = 0;
            e.printStackTrace();
        }

        Log.e("TAG","R: " + R + " G: " + G + " B: " + B);

        String ff = "FF";
//        String r = String.format("%02x", R);
//        String g = String.format("%02x", G);
//        String b = String.format("%02x", B);
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
        res = res.toUpperCase();
        return res;
    }
}
