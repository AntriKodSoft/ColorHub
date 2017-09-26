package cheetatech.com.colorhub.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.Util
import cheetatech.com.colorhub.defines.ColorData
import cheetatech.com.colorhub.listeners.OnItemSelect

/**
 * Created by coderkan on 03.07.2017.
 */
class MaterialKotlinAdapter(dataset: MutableList<ColorData>, itemSelectedListener: OnItemSelect?) : RecyclerView.Adapter<MaterialKotlinAdapter.ViewHolder>() {

    private var mDataset: MutableList<ColorData> = dataset
    private var itemSelectListener : OnItemSelect? = itemSelectedListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layout = R.layout.grid_list2
        val v: View = LayoutInflater.from(parent?.context).inflate(layout, parent, false)
        return ViewHolder(v)
    }

    fun add(position: Int, item: ColorData) {
        mDataset.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(item: ColorData) {
        val position = mDataset.indexOf(item)
        mDataset.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var model = mDataset.get(position)
        holder?.mLayout?.setBackgroundColor(Color.parseColor(model.code))
        holder?.mColorName?.text = model?.name
        holder?.mColorName?.setTextColor(Color.parseColor(model.code))

        var gd4 = holder?.mBorderLayout?.background?.current as GradientDrawable
        gd4.setStroke(Util.dpToPx(2), Color.parseColor(model.code))

        var gradientDrawable = ((holder.mTriangleLayout.background as LayerDrawable).findDrawableByLayerId(R.id.triangle_shape) as RotateDrawable).drawable as GradientDrawable
        with(gradientDrawable){
            setColor(Color.parseColor(model.code))
            setStroke(Util.dpToPx(2), Color.parseColor(model.code))
        }

        holder.mClickLayout.setOnClickListener {
            itemSelectListener?.onItemSelected(position)
            println("Clicked me " + position)
        }

    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var mColorName = view.findViewById(R.id.colorName) as TextView
        var mLayout = view.findViewById(R.id.rootColorLayout) as RelativeLayout
        var mBorderLayout = view.findViewById(R.id.borderLayout) as RelativeLayout
        var mTriangleLayout = view.findViewById(R.id.triangle_item_image_view) as ImageView
        var mClickLayout = view.findViewById(R.id.click_layout) as RelativeLayout
    }

}