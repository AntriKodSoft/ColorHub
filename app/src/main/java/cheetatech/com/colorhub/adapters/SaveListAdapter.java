package cheetatech.com.colorhub.adapters;

import android.graphics.Color;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.models.Model;

public class SaveListAdapter extends RecyclerView.Adapter<SaveListAdapter.ViewHolder>{

    private List<Model> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color_layout)
        RelativeLayout mColorLayout;

        @BindView(R.id.color_text_view)
        TextView mColorTextView;

        @BindView(R.id.delete_saved_list)
        ImageView mDeleteImageView;



        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void add(int position, Model item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public SaveListAdapter(List<Model> myDataset){
        this.mDataset = myDataset;
    }

    @Override
    public SaveListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.saved_list_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        Context context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SaveListAdapter.ViewHolder holder, final int position) {
        holder.mColorTextView.setText(this.mDataset.get(position).getColorCode());
        holder.mColorLayout.setBackgroundColor(Color.parseColor(this.mDataset.get(position).getColorCode()));
        holder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mDataset.remove(position);
                    notifyDataSetChanged();
                }catch (Exception e){}
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

