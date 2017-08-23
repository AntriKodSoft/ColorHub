package cheetatech.com.colorhub.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.Util
import cheetatech.com.colorhub.defines.BoardEditor
import cheetatech.com.colorhub.defines.ColorData
import cheetatech.com.colorhub.listeners.OnItemSelect

class MaterialUIAdapter : RecyclerView.Adapter<MaterialUIAdapter.ViewHolder>{

    private var mDataset: MutableList<ColorData> = mutableListOf()
    private var itemSelectListener : OnItemSelect? = null

    constructor(dataset: MutableList<ColorData>, itemSelectedListener : OnItemSelect? ){
        this.mDataset = dataset
        this.itemSelectListener = itemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layout = R.layout.grid_list
        val v: View = LayoutInflater.from(parent?.context).inflate(layout, parent, false)
        return ViewHolder(v);
    }

    fun add(position: Int, item: ColorData) {
        mDataset?.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(item: ColorData) {
        val position = mDataset!!.indexOf(item)
        mDataset?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        var model = mDataset?.get(position)

        holder?.mButtonCopy?.setOnClickListener(View.OnClickListener {
            BoardEditor.getInstance().copyToClipBoard(model?.code)
            Toast.makeText(BoardEditor.getInstance().context, "Color " + model?.code +
                    " copied to clipboard...", Toast.LENGTH_SHORT).show()
        })

        holder?.mLayout?.setBackgroundColor(Color.parseColor(model?.code))

        holder?.mColorCode?.setText(model?.code)
        holder?.mColorName?.setText(model?.name)

        holder?.mColorAddButton?.setOnClickListener({
            var dataSet = mDataset?.get(position)
            itemSelectListener?.onAddColor(dataSet!!.code)
        })

        var gd4 = holder?.mBorderLayout?.background?.current as GradientDrawable
        gd4.setStroke(Util.dpToPx(2), Color.parseColor(model?.code))

        var gradientDrawable = ((holder?.mTriangleLayout.background as LayerDrawable).findDrawableByLayerId(R.id.triangle_shape) as RotateDrawable).drawable as GradientDrawable
        with(gradientDrawable){
            setColor(Color.parseColor(model?.code))
            setStroke(Util.dpToPx(2), Color.parseColor(model?.code))
        }
    }

    override fun getItemCount(): Int {
        return mDataset!!?.size;
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var mColorName = view.findViewById(R.id.colorName) as TextView
        var mColorCode = view.findViewById(R.id.colorCode) as TextView
        var mButtonCopy = view.findViewById(R.id.buttonCopy) as ImageButton
        var mLayout = view.findViewById(R.id.rootColorLayout) as RelativeLayout
        var mBorderLayout = view.findViewById(R.id.borderLayout) as RelativeLayout
        var mColorAddButton = view.findViewById(R.id.add_color_image_button) as ImageView
        var mTriangleLayout = view.findViewById(R.id.triangle_item_image_view) as ImageView
    }
}