package layout

import android.content.Context
import android.graphics.Color
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
    private var buttonList : MutableList<ToggleButton> ? = mutableListOf()

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
        buttonList?.clear()
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
            button.setBackgroundDrawable(loadButtonDrawable(i))
            button.isChecked = i == 0
            buttonList?.add(button)
        }
        // load Recyclerview
        loadRecyclerView()
    }

    fun loadButtonDrawable(i : Int): StateListDrawable{

        var pushColor : String = "FF00FF"
        var nopushColor: String = "FFFF00"
        if(colorLists != null){
            nopushColor = colorLists?.materialLists?.get(i)?.get(0)?.code!!
            pushColor = colorLists?.materialLists?.get(i)?.get(8)?.code!!
        }


        var array = IntArray(1)
        array[0] = android.R.attr.state_checked

        var pushDrawable = GradientDrawable()
        with(pushDrawable){
            shape = GradientDrawable.OVAL
            setStroke(resources.getDimensionPixelSize(R.dimen.circle_button_margin),Color.GRAY)
            setColor(Color.parseColor(pushColor))
        }

        var noPushDrawable = GradientDrawable()
        with(noPushDrawable){
            shape = GradientDrawable.OVAL
            setColor(Color.parseColor(nopushColor))
        }

        var stateList = StateListDrawable()
        stateList.addState(intArrayOf(android.R.attr.state_checked), pushDrawable)
        stateList.addState(IntArray(1), noPushDrawable)

        return stateList
    }

    var onClickListener = View.OnClickListener{ view ->
        var id = view?.id
        var btn = view as ToggleButton
        btn.isChecked = true
        buttonControl(btn)
        mList?.clear()
        var flist = colorLists?.materialLists?.get(id as Int)
        if (flist != null)
            mList?.addAll(flist)
        adapter?.notifyDataSetChanged()
    }

    fun  buttonControl(btn: ToggleButton){
        var size = buttonList?.size?.minus(1)
        for (i in 0 ..size as Int){
            buttonList?.get(i)?.isChecked = btn.id == buttonList?.get(i)?.id
        }
    }

    private fun loadRecyclerView() {
        var mRecyclerView = view?.findViewById(R.id.kotlin_materialui_view) as RecyclerView

        var manager = GridLayoutManager(activity.applicationContext, 1)
        with(mRecyclerView){
            layoutManager = manager
            setHasFixedSize(true)
        }
        mList?.clear()
        var flist = colorLists?.materialLists?.get(0) // Default 0
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
