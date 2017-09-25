package layout

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.ColorAdapter
import cheetatech.com.colorhub.ads.AdsUtils
import cheetatech.com.colorhub.dialog.EditDialog
import cheetatech.com.colorhub.models.Model
import cheetatech.com.colorhub.paletteitem.ColorBus
import cheetatech.com.colorhub.realm.SavedObject
import com.getbase.floatingactionbutton.FloatingActionsMenu
import es.dmoral.toasty.Toasty


class ColorDetailFragment : Fragment() ,ColorPickerArrange.OnColorListener, EditDialog.OnEditListener, ColorAdapter.OnColorAdapterListener, ColorPickerAdd.OnColorPickerAddListener {


    private var mListener: OnFragmentInteractionListener? = null
    private var mObject: SavedObject? = null
    private var colorList: MutableList<Model> = mutableListOf()
    private var mAdapter: ColorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_color_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRecyclerView = view?.findViewById(R.id.recyclerview) as RecyclerView
        fabMenu = view?.findViewById(R.id.fab_menu) as FloatingActionsMenu


        mObject = ColorBus.getInstance().savedObject

        loadAdapters();

        fabEdit = view?.findViewById(R.id.fab_edit) as com.getbase.floatingactionbutton.FloatingActionButton
        fabAddPalette = view?.findViewById(R.id.fab_add_palette) as com.getbase.floatingactionbutton.FloatingActionButton

        fabEdit?.setOnClickListener(View.OnClickListener { v ->
            (EditDialog.newInstance(mObject?.name, this as EditDialog.OnEditListener)).show(activity.supportFragmentManager, "EditDialog")
            collapse()
        })

        fabAddPalette?.setOnClickListener(View.OnClickListener { v ->
            activity.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.root_your_color_frame, ColorPickerAdd.newInstance(this as ColorPickerAdd.OnColorPickerAddListener))
                    .addToBackStack(null)
                    .commit()

            collapse()

        })

    }

    private fun loadAdapters() {
        colorList.clear()
        mObject?.list?.let { colorList.addAll(it) }

        val manager = LinearLayoutManager(activity)
        mRecyclerView?.setLayoutManager(manager)
        mRecyclerView?.setHasFixedSize(true)
        mAdapter = ColorAdapter(colorList, this)
        mRecyclerView?.setAdapter(mAdapter)
    }

    private fun collapse() {
        if(fabMenu != null && (fabMenu as FloatingActionsMenu).isExpanded){
            fabMenu?.collapse()
        }
    }

    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
        val fragment = activity.supportFragmentManager.findFragmentByTag("ROOT_YOUR_COLOR_TAG")

        if (fragment != null) {
            (fragment as YourColorKotlinFragment).refreshValues()
        }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private var fabEdit : com.getbase.floatingactionbutton.FloatingActionButton? = null
        private var fabAddPalette : com.getbase.floatingactionbutton.FloatingActionButton? = null
        private var fabMenu : com.getbase.floatingactionbutton.FloatingActionsMenu? = null
        private var mRecyclerView : RecyclerView? = null

        fun newInstance(): ColorDetailFragment {
            val fragment = ColorDetailFragment()
            return fragment
        }
    }

    override fun onItemClicked(position: Int) {
        collapse()
        activity.supportFragmentManager
                .beginTransaction()
                .add(R.id.root_your_color_frame, ColorPickerArrange.newInstance(colorList[position], position, this as ColorPickerArrange.OnColorListener))
                .addToBackStack(null)
                .commit()
        AdsUtils.getInstance().increaseInteraction()
    }

    override fun onEditedName(name: String?) {
        mObject?.setNameQuery(name)
        //getSupportActionBar()!!.setTitle(mObject?.getName())
        AdsUtils.getInstance().increaseInteraction()
        Toasty.success(activity, getString(R.string.success_edit_name), Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteItemFromList(position: Int) {

        colorList.removeAt(position)
        mObject?.addList(colorList)
        colorList.clear()
        mObject?.getList()?.let { colorList.addAll(it) }
        mAdapter?.notifyDataSetChanged()
        AdsUtils.getInstance().increaseInteraction()
        Toasty.success(activity, getString(R.string.success_delete_item), Toast.LENGTH_SHORT).show()

    }

    override fun onAskDeleteItem(position: Int) {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle(getString(R.string.delete_saved_object))
        alertDialogBuilder
                .setMessage(getString(R.string.delete_this_color))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.answer_yes)) { dialog, id ->
                    onDeleteItemFromList(position)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.answer_no)) { dialog, id -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onAddNewColor(color: String?) {
        for (m in colorList) {
            if (m.colorCode == color) {
                Toasty.info(activity, getString(R.string.allready_added_color), Toast.LENGTH_SHORT).show()
                return
            }
        }
        colorList.add(Model(color))
        mObject?.addList(colorList)
        colorList.clear()
        mObject?.getList()?.let { colorList.addAll(it) }
        mAdapter?.notifyDataSetChanged()
        Toasty.success(activity, getString(R.string.success_add_color), Toast.LENGTH_SHORT).show()
        AdsUtils.getInstance().increaseInteraction()
    }

    override fun onAddColor(color: String?) {
    }

    override fun onChangeColor(position: Int, model: Model?) {
        if (model != null) {
            this.colorList?.set(position, model)
            this.mAdapter?.notifyDataSetChanged()
        }
    }

}// Required empty public constructor
