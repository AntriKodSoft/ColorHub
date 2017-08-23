package layout

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ToggleButton
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.ColorKotlinAdapter
import cheetatech.com.colorhub.controller.ColorLists
import cheetatech.com.colorhub.defines.ColorData
import cheetatech.com.colorhub.listeners.OnItemSelect

class MaterialUIFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    private var mColorListener: OnItemSelect? = null
    private var mList: MutableList<ColorData>? = mutableListOf()
    private var colorLists: ColorLists? = null
    private var adapter :ColorKotlinAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_material_ui, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var linearLayout = view?.findViewById(R.id.buttons_layout) as LinearLayout
        var buttonList : MutableList<Button> ? = mutableListOf()
        for(i in 0 .. 18){
            var button = ToggleButton(activity.applicationContext)
            with(button){
                setBackgroundResource(R.drawable.toggle_button_selector)
                textOn = ""
                textOff = ""
                text = ""
                id = i
                setOnClickListener(onClickListener)
            }
            var px = resources.getDimensionPixelSize(R.dimen.circle_button)
            var margin = resources.getDimensionPixelSize(R.dimen.circle_button_margin)
            var vParams = LinearLayout.LayoutParams(px, px)
            vParams.setMargins(margin,margin,margin,margin)
            linearLayout.addView(button, vParams)
            buttonList?.add(button)
        }

        var btn = buttonList?.get(0)
        var drawable = btn?.background?.current as StateListDrawable

        var array = IntArray(1)
        array.set(0, android.R.attr.state_pressed)

        var stateList = StateListDrawable()
        stateList.addState(array, ColorDrawable(Color.BLACK))
        stateList.addState(IntArray(1), ColorDrawable(Color.GREEN))
        btn.setBackgroundDrawable(stateList)



        // load Recyclerview
        loadRecyclerView();
    }

    var onClickListener = View.OnClickListener{ view ->
        var id = view?.id
        mList?.clear();
        var flist = colorLists?.materialLists?.get(id as Int);
        if (flist != null)
            mList?.addAll(flist)
        adapter?.notifyDataSetChanged()
    }

    private fun loadRecyclerView() {
        var mRecyclerView = view?.findViewById(R.id.kotlin_materialui_view) as RecyclerView

        var manager = GridLayoutManager(activity.applicationContext, 2)
        with(mRecyclerView){
            layoutManager = manager
            setHasFixedSize(true)
        }
        mList?.clear();
        var flist = colorLists?.materialLists?.get(0); // Default 0
        if (flist != null) mList?.addAll(flist)
        adapter = ColorKotlinAdapter(this.mList!!, object : OnItemSelect {
            override fun onAddColor(color: String) {
                mColorListener?.onAddColor(color)
            }
            override fun onItemSelected(position: Int) {
            }
        })
        mRecyclerView.adapter = adapter
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
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance(): MaterialUIFragment {
            val fragment = MaterialUIFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(colorList: ColorLists ,listener: OnItemSelect ): MaterialUIFragment {
            val fragment = MaterialUIFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mColorListener = listener
            fragment.colorLists = colorList
            return fragment
        }
    }
}// Required empty public constructor
