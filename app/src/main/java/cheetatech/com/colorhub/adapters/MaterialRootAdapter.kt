package cheetatech.com.colorhub.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.listeners.OnItemSelect
import cheetatech.com.colorhub.models.MainPageModel
import cheetatech.com.colorhub.models.MaterialRootModel

/**
 * Created by coderkan on 18.06.2017.
// */

class MaterialRootAdapter : RecyclerView.Adapter<MaterialRootAdapter.ViewHolder>{

    private var mDataset: MutableList<MaterialRootModel>? = null
    private var itemSelectListener : OnItemSelect? = null

    constructor(dataset: MutableList<MaterialRootModel>?, itemSelectedListener : OnItemSelect? ){
        this.mDataset = dataset
        this.itemSelectListener = itemSelectedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layout = R.layout.material_root_item;
        val v: View = LayoutInflater.from(parent?.context).inflate(layout, parent, false)
        return ViewHolder(v);
    }


    fun add(position: Int, item: MaterialRootModel) {
        mDataset?.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(item: MaterialRootModel) {
        val position = mDataset!!?.indexOf(item)
        mDataset?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var model = this.mDataset?.get(position)

        holder?.mColorText?.setText(model?.name)
        holder?.mRelativeLayout?.setBackgroundColor(Color.parseColor(model?.code))
        holder?.mRelativeLayout?.setOnClickListener { v ->
            this.itemSelectListener?.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int {
        return mDataset!!?.size;
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        var mColorText = view.findViewById(R.id.color_name_text_material_root) as TextView
        var mRelativeLayout = view.findViewById(R.id.relative_layout_material_root) as RelativeLayout
    }
}