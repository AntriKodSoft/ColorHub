package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.color_layout)
        RelativeLayout mColorLayout;

        @BindView(R.id.color_text_view)
        TextView mColorTextView;

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
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(SaveListAdapter.ViewHolder holder, int position) {
        holder.mColorTextView.setText(this.mDataset.get(position).getColorCode());
        holder.mColorLayout.setBackgroundColor(Color.parseColor(this.mDataset.get(position).getColorCode()));
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

