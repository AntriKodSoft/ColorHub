package cheetatech.com.colorhub.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.RotateDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.Util
import cheetatech.com.colorhub.controller.ColorArrayController
import cheetatech.com.colorhub.defines.BoardEditor
import cheetatech.com.colorhub.defines.ColorData
import cheetatech.com.colorhub.listeners.OnItemSelect
import cheetatech.com.colorhub.models.MainPageModel
import org.w3c.dom.Text

/**
 * Created by coderkan on 28.06.2017.
 */
class ColorKotlinAdapter : RecyclerView.Adapter<ColorKotlinAdapter.ViewHolder>{

    private var mDataset: MutableList<ColorData>? = null
    private var itemSelectListener : OnItemSelect? = null
    private var mContext: Context? = null

    constructor(context: Context, dataset: MutableList<ColorData>?, itemSelectedListener : OnItemSelect? ){
        this.mContext = context;
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
        val position = mDataset!!?.indexOf(item)
        mDataset?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.mButtonCopy?.setOnClickListener(View.OnClickListener {
            BoardEditor.getInstance().copyToClipBoard(mDataset?.get(position)?.code)
            Toast.makeText(BoardEditor.getInstance().context, "Color " + mDataset?.get(position)?.code +
                    " copied to clipboard...", Toast.LENGTH_SHORT).show()
        })

        holder?.mLayout?.setBackgroundColor(Color.parseColor(mDataset?.get(position)?.code))
        var gd = holder?.mLayout?.background?.current as GradientDrawable
        gd?.setColor(Color.parseColor(mDataset?.get(position)?.code))


        var gd2 = holder?.mLayout?.background?.current as GradientDrawable
        gd2?.setColor(Color.parseColor(mDataset?.get(position)?.code))

        var gd3 = holder?.mBorderLayout?.background?.current as GradientDrawable
        gd3?.setColor(Color.parseColor(mDataset?.get(position)?.code))


        holder?.mColorCode.setText(mDataset?.get(position)?.code)

//        val holder: ViewHolder
//        var view: View? = convertView
//        if (view != null) {
//            holder = view.tag as ViewHolder
//        } else {
//            view = inflater.inflate(this.resource, parent, false)
//            holder = ViewHolder(view)
//            view!!.tag = holder
//        }
//        holder.btnCopy.setOnClickListener(View.OnClickListener {
//            BoardEditor.getInstance().copyToClipBoard(colorInfos.get(position).getColorCode())
//            Toast.makeText(BoardEditor.getInstance().context, "Color " + colorInfos.get(position).getColorCode() +
//                    " copied to clipboard...", Toast.LENGTH_SHORT).show()
//        })
//
////        if (holder.mLayout != null)
////            holder.mLayout.setBackgroundColor(Color.parseColor(colorInfos.get(position).getColorCode()));
//
//        if (holder.mLayout != null) {
//            val gd = holder.mLayout.getBackground().getCurrent()
//            gd?.setColor(Color.parseColor(colorInfos.get(position).getColorCode()))
//        }
//
//
//        if (holder.mBorderLayout != null) {
//            val gd = holder.mBorderLayout.getBackground().getCurrent()
//            gd?.setStroke(Util.dpToPx(2), Color.parseColor(colorInfos.get(position).getColorCode()))
//        }
//
//        holder.textColorCode.setText(colorInfos.get(position).getColorCode())
//

        // ????? 
//        var colorName = ""
//        colorName = if (position == 0) ColorArrayController.getInstance().getHeaderColorName(HeaderIndex) else colorInfos.get(position).getColorName()
//        holder.textColorName.setText(colorName)
//
//        holder.mColorAddButton.setOnClickListener(View.OnClickListener {
//            val colorInfo = colorInfos.get(position)
//            if (mListener != null) {
//                mListener.onAddColor(colorInfo.getColorCode())
//            }
//        })

//        var model = this.mDataset?.get(position)
//        holder?.mColorText?.setText(model?.name)
//        holder?.mColorText?.setTextColor(Color.parseColor(model?.colorCode))
//
//        var gd = holder?.mBorderLayout?.background?.current as GradientDrawable
//        gd.setStroke(Util.dpToPx(2), Color.parseColor(model?.colorCode))
//
//        holder?.mMainColorLayout.setBackgroundColor(Color.parseColor(model?.colorCode))
//
//        var gradientDrawable = ((holder?.mTriangleLayout.background as LayerDrawable).findDrawableByLayerId(R.id.triangle_shape) as RotateDrawable).drawable as GradientDrawable
//        with(gradientDrawable){
//            setColor(Color.parseColor(model?.colorCode))
//            setStroke(Util.dpToPx(2), Color.parseColor(model?.colorCode))
//        }
//        holder?.mRelativeLayout?.setOnClickListener { v ->
//            this.itemSelectListener?.onItemSelected(position)
//        }
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


//        var mColorText = view.findViewById(R.id.text_view_page) as TextView
//        var mRelativeLayout = view.findViewById(R.id.relative_layout_main_page) as RelativeLayout
//        var mBorderLayout = view.findViewById(R.id.border_relative_layout) as RelativeLayout
//        var mMainColorLayout = view.findViewById(R.id.relative_layout_main_page) as RelativeLayout
//        var mTriangleLayout = view.findViewById(R.id.triangle_image_view) as ImageView
    }
}