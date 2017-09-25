package layout

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.ColorKotlinAdapter
import cheetatech.com.colorhub.adapters.YourColorAdapter
import cheetatech.com.colorhub.ads.AdsUtils
import cheetatech.com.colorhub.listeners.OnItemSelect
import cheetatech.com.colorhub.paletteitem.ColorActivity
import cheetatech.com.colorhub.paletteitem.ColorBus
import cheetatech.com.colorhub.realm.RealmX
import cheetatech.com.colorhub.realm.SavedObject

class YourColorKotlinFragment : Fragment() , YourColorAdapter.OnItemDelete {


    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: YourColorAdapter? = null
    private var modelList: MutableList<SavedObject> = mutableListOf();

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_your_color_kotlin, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRecyclerView = view?.findViewById(R.id.recyclerview) as RecyclerView

        loadAdapters();

    }

    override fun onResume() {
        super.onResume()
        if(mAdapter != null){
            var list = RealmX.getObject()
            modelList.clear()
            modelList.addAll(list)
            mAdapter?.notifyDataSetChanged()
        }
    }

    fun refreshValues() {
        if(mAdapter != null){
            var list = RealmX.getObject()
            modelList.clear()
            modelList.addAll(list)
            mAdapter?.notifyDataSetChanged()
        }
    }

    private fun loadAdapters(){
        var list = RealmX.getObject();
        modelList.addAll(list)
        var manager = LinearLayoutManager(activity)
        mAdapter = YourColorAdapter(modelList, this as YourColorAdapter.OnItemDelete)
        mRecyclerView?.layoutManager = manager
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.adapter = mAdapter
    }


    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            println("onAttach")
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        println("onDetach")
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance(): YourColorKotlinFragment {
            println("onNewInstance")
            val fragment = YourColorKotlinFragment()
            return fragment
        }
    }

    override fun onItemDelete(name: String?) {

    }

    override fun onItemDelete(name: String?, position: Int) {
        try {
            RealmX.deleteObject(name)
            var list = RealmX.getObject()
            modelList.clear()
            modelList.addAll(list)
            mAdapter = YourColorAdapter(modelList, this as YourColorAdapter.OnItemDelete)
            mRecyclerView?.adapter = mAdapter
        }catch (ex: IllegalStateException ){
            ex.printStackTrace();
        }
        AdsUtils.getInstance().increaseInteraction()
    }

    override fun onClickedPosition(position: Int) {
        ColorBus.getInstance().setSavedObject(modelList.get(position))
        //startActivity(Intent(activity, ColorActivity::class.java))
        activity.supportFragmentManager
                .beginTransaction()
                .add(R.id.root_your_color_frame, ColorDetailFragment.newInstance(), "ColorDetailFragment")
                .addToBackStack(null)
                .commit()
    }

    override fun onDeleteAlert(name: String?, position: Int) {

        val alertDialogBuilder = AlertDialog.Builder(activity)
        // set title
        alertDialogBuilder.setTitle(getString(R.string.delete_saved_object))
        alertDialogBuilder
                .setMessage(getString(R.string.delete_file_ask))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.answer_yes)) { dialog, id ->
                    onItemDelete(name, position)
                    dialog.dismiss()
                }
                .setNegativeButton(getString(R.string.answer_no)) { dialog, id -> dialog.cancel() }
        // create alert dialog
        val alertDialog = alertDialogBuilder.create()
        // show it
        alertDialog.show()
    }

}// Required empty public constructor
