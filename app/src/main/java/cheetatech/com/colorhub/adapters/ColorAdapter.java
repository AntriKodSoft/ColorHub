package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    private List<Model> mDataset;
    private Context context;
    private OnColorAdapterListener mListener = null;

    public interface OnColorAdapterListener{
        void onItemClicked(int position);
        void onDeleteItemFromList(int position);
        void onAskDeleteItem(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color_layout)
        RelativeLayout mLayout;

        @BindView(R.id.color_text)
        TextView mColorText;

        @BindView(R.id.delete_list_item)
        ImageButton mDeleteItemButton;

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

    public ColorAdapter(List<Model> myDataset,OnColorAdapterListener mListener){
        this.mDataset = myDataset;
        this.mListener = mListener;
    }


    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.color_list_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ColorAdapter.ViewHolder holder, final int position) {
        Log.e("TAG","_init_ " + 2);
        if(context != null){
            Model model = this.mDataset.get(position);
            String color = model.getColorCode();
            holder.mLayout.setBackgroundColor(Color.parseColor(color));
            holder.mColorText.setText(color);
            holder.mColorText.setTextColor(Color.WHITE);

            holder.mDeleteItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null)
                        mListener.onAskDeleteItem(position);
                }
            });

            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null)
                        mListener.onItemClicked(position);
                }
            });

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
}
