package layout

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.MainPageAdapter
import cheetatech.com.colorhub.controller.ColorLists
import cheetatech.com.colorhub.listeners.OnItemSelect
import cheetatech.com.colorhub.listeners.RecyclerItemClickListener
import cheetatech.com.colorhub.models.MainPageModel

class MainFragment : Fragment(){

    private var mColorListener: ColorPicker1.OnColorListener? = null

    fun setListener(mColorListener: ColorPicker1.OnColorListener?){
        this.mColorListener = mColorListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var lists = ColorLists(activity.resources)
        var mRecyclerView = view?.findViewById(R.id.main_page_recycler_view) as RecyclerView
        var mlist : MutableList<MainPageModel> = mutableListOf(
                MainPageModel("Color Generate","#29D23B"),
                MainPageModel("Flat","#2980B9"),
                MainPageModel("Material","#F44336"),
                MainPageModel("Social","#3AAF85"),
                MainPageModel("Metro","#FA6800"),
                MainPageModel("Html","#FF1493"),
                MainPageModel("ColorPalette X","#ADFF2F"),
                MainPageModel("ColorPalette Y","#AA00FF")
                )

        var manager = GridLayoutManager(activity.applicationContext, 1)
        with(mRecyclerView){
            layoutManager = manager as RecyclerView.LayoutManager?
            setHasFixedSize(true)
        }

        var adapter = MainPageAdapter(context, mlist, object: OnItemSelect{
            override fun onAddColor(color: String) {

            }

            override fun onItemSelected(position: Int) {
            }
        })

        mRecyclerView.adapter = adapter

        mRecyclerView.addOnItemTouchListener(object : RecyclerItemClickListener(context, OnItemClickListener { view, position ->
            var fragment : Fragment? = null
            fragment = when(position){
                0 -> GenerateColorFragment.newInstance();
                1 ->  ColorKotlinFragment.newInstance(lists.flatList!!, itemListener)
                2 -> MaterialUIFragment.newInstance(lists, itemListener)
                3 -> ColorKotlinFragment.newInstance(lists.socialList!!, itemListener)
                4 -> ColorKotlinFragment.newInstance(lists.metroList!!, itemListener)
                5 -> ColorKotlinFragment.newInstance(lists.htmlList!!, itemListener)
                6 -> ColorPicker1.newInstance(itemListener)
                7 -> ColorPicker3.newInstance (itemListener)
                else -> {
                    null
                }
            }

            val transaction = fragmentManager.beginTransaction()
            with(transaction){
                add(R.id.root_frame, fragment)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
                commit()
            }
        }){})


    }

    var itemListener = object : OnItemSelect{
        override fun onAddColor(color: String) {
            mColorListener?.onAddColor(color)
        }

        override fun onItemSelected(position: Int) {
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ColorPicker1.OnColorListener) {
            mColorListener = context as ColorPicker1.OnColorListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mColorListener = null
    }

    companion object {
        fun newInstance(mColorListener: ColorPicker1.OnColorListener?): MainFragment {
            val fragment = MainFragment()
            fragment.setListener(mColorListener)
            return fragment
        }
    }
}
