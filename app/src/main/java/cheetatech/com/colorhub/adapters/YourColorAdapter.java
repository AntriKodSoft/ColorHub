package cheetatech.com.colorhub.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.models.Model;
import cheetatech.com.colorhub.realm.SavedObject;

/**
 * Created by erkan on 21.03.2017.
 */

public class YourColorAdapter extends RecyclerView.Adapter<YourColorAdapter.ViewHolder>{

    private int sayac = 0;
    private List<SavedObject> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dynamic_linear_layout)
        LinearLayout mLayout;

        @BindView(R.id.color_palette_name)
        TextView mPaletteName;

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

    @Override
    public YourColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("TAG","viewType " + viewType + " sayac : " + sayac++);
        Log.e("TAG","_init_ " + 1);
        int layout = R.layout.your_color_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(YourColorAdapter.ViewHolder holder, int position) {
        Log.e("TAG","_init_ " + 2);
        if(context != null){

            SavedObject object = this.mDataset.get(position);
            holder.mPaletteName.setText(object.getName());

            for (Model m: object.getList()) {
                LinearLayout l1 = new LinearLayout(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1.0f;
                l1.setLayoutParams(params);
                l1.setBackgroundColor(Color.parseColor(m.getColorCode()));
                holder.mLayout.addView(l1);
            }
//            LinearLayout l1 = new LinearLayout(context);
//            l1.setBackgroundColor(Color.GREEN);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//            params.weight = 1.0f;
//            l1.setLayoutParams(params);
//            holder.mLayout.addView(l1);
//            LinearLayout l2 = new LinearLayout(context);
//            l2.setBackgroundColor(Color.GRAY);
//            l2.setLayoutParams(params);
//            holder.mLayout.addView(l2);

            //holder.
//            LinearLayout l1 = new LinearLayout(context);
//            l1.setBackgroundColor(Color.GREEN);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//            params.weight = 1.0f;
//            l1.setLayoutParams(params);
//            holder.mLayout.addView(l1);
//            LinearLayout l2 = new LinearLayout(context);
//            l2.setBackgroundColor(Color.GRAY);
//            l2.setLayoutParams(params);
//            holder.mLayout.addView(l2);
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