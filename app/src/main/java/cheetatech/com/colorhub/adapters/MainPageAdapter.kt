package cheetatech.com.colorhub.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RelativeLayout
import android.widget.TextView
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.listeners.OnItemSelect
import cheetatech.com.colorhub.models.MainPageModel

/**
 * Created by coderkan on 18.06.2017.
// */

class MainPageAdapter : RecyclerView.Adapter<MainPageAdapter.ViewHolder>{

    private var mDataset: MutableList<MainPageModel>? = null
    private var itemSelectListener : OnItemSelect? = null

    constructor(dataset: MutableList<MainPageModel>?, itemSelectedListener : OnItemSelect? ){
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
    }
}