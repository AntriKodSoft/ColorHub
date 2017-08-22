package layout

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ToggleButton

import cheetatech.com.colorhub.R

class MaterialUIFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

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

        for(i in 0 .. 19){
            var button = ToggleButton(activity)
            button.setBackgroundResource(R.drawable.toggle_button_selector)
            button.textOn = ""
            button.textOff = ""
            button.text = ""
            var px = resources.getDimensionPixelSize(R.dimen.circle_button)
            var margin = resources.getDimensionPixelSize(R.dimen.circle_button_margin)
            var vParams = LinearLayout.LayoutParams(px, px)
            vParams.setMargins(margin,margin,margin,margin)
            linearLayout.addView(button, vParams)
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
    }
}// Required empty public constructor
