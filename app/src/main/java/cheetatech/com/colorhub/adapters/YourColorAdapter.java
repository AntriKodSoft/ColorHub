package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.realm.RealmX;
import cheetatech.com.colorhub.realm.SavedObject;

/**
 * Created by erkan on 21.03.2017.
 */

public class YourColorAdapter extends RecyclerView.Adapter<YourColorAdapter.ViewHolder>{

    private int sayac = 0;
    private List<SavedObject> mDataset;
    private Context context;
    private boolean isDeleted = false;

    private OnItemDelete mListener = null;

    public interface OnItemDelete{
        void onItemDelete(String name);
        void onItemDelete(String name,int position);
        void onClickedPosition(int position);
        void onDeleteAlert(String name,int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dynamic_linear_layout)
        LinearLayout mLayout;

        @BindView(R.id.color_palette_name)
        TextView mPaletteName;

        @BindView(R.id.delete_saved_query)
        ImageButton mDeleteButton;

        @BindView(R.id.palette_share)
        ImageButton mShareButton;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void add(int position, SavedObject item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public YourColorAdapter(List<SavedObject> myDataset){
        this.mDataset = myDataset;
    }

    public YourColorAdapter(List<SavedObject> myDataset, OnItemDelete listener){
        this.mDataset = myDataset;
        this.mListener = listener;
    }

    @Override
    public YourColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.your_color_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(YourColorAdapter.ViewHolder holder, final int position) {
        if(context != null){
            if(!isDeleted){
                final SavedObject object = this.mDataset.get(position);
                holder.mPaletteName.setText(object.getName());
                holder.mLayout.removeAllViews();
                for (Model m: object.getList()) {
                    LinearLayout l1 = new LinearLayout(context);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    params.weight = 1.0f;
                    l1.setLayoutParams(params);
                    l1.setBackgroundColor(Color.parseColor(m.getColorCode()));
                    holder.mLayout.addView(l1);
                }

                holder.mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mListener != null)
                            mListener.onClickedPosition(position);
                    }
                });

                holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("TAG", "onClick: Deleted" );
                        if(mListener != null){
                            //isDeleted = true;
                            mListener.onDeleteAlert(object.getName(), position);
                            //mListener.onItemDelete(object.getName(), position);
                            //notifyDataSetChanged();
                        }
                    }
                });

                holder.mShareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }


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