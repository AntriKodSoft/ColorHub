package cheetatech.com.colorhub.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.Util
import cheetatech.com.colorhub.listeners.OnItemSelect
import cheetatech.com.colorhub.models.MainPageModel

/**
 * Created by coderkan on 18.06.2017.
// */

class MainPageAdapter : RecyclerView.Adapter<MainPageAdapter.ViewHolder>{

    private var mDataset: MutableList<MainPageModel>? = null
    private var itemSelectListener : OnItemSelect? = null
    private var mContext: Context? = null;

    constructor(context: Context, dataset: MutableList<MainPageModel>?, itemSelectedListener : OnItemSelect? ){
        this.mContext = context;
        this.mDataset = dataset
        this.itemSelectListener = itemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layout = R.layout.main_page_item;
        val v: View = LayoutInflater.from(parent?.context).inflate(layout, parent, false)
        return ViewHolder(v);
    }


    fun add(position: Int, item: MainPageModel) {
        mDataset?.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(item: MainPageModel) {
        val position = mDataset!!?.indexOf(item)
        mDataset?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var model = this.mDataset?.get(position)
        holder?.mColorText?.setText(model?.name)
        holder?.mColorText?.setTextColor(Color.parseColor(model?.colorCode))

        var gd = holder?.mBorderLayout?.background?.current as GradientDrawable
        gd.setStroke(Util.dpToPx(2), Color.parseColor(model?.colorCode))

        holder?.mMainColorLayout.setBackgroundColor(Color.parseColor(model?.colorCode))

        var gradientDrawable = ((holder?.mTriangleLayout.background as LayerDrawable).findDrawableByLayerId(R.id.triangle_shape) as RotateDrawable).drawable as GradientDrawable
        with(gradientDrawable){
            setColor(Color.parseColor(model?.colorCode))
            setStroke(Util.dpToPx(2),Color.parseColor(model?.colorCode))
        }
        holder?.mRelativeLayout?.setOnClickListener { v ->
            this.itemSelectListener?.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int {
        return mDataset!!?.size;
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var mColorText = view.findViewById(R.id.text_view_page) as TextView
        var mRelativeLayout = view.findViewById(R.id.relative_layout_main_page) as RelativeLayout
        var mBorderLayout = view.findViewById(R.id.border_relative_layout) as RelativeLayout
        var mMainColorLayout = view.findViewById(R.id.relative_layout_main_page) as RelativeLayout
        var mTriangleLayout = view.findViewById(R.id.triangle_image_view) as ImageView
    }
}