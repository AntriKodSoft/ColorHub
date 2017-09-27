package layout

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.listeners.OnItemSelect


class RootFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    private var mItemListener: OnItemSelect? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var v = inflater!!.inflate(R.layout.fragment_root, container, false)
        var transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.root_frame, MainFragment.Companion.newInstance(ColorPicker1.OnColorListener { color -> mItemListener?.onAddColor(color!!) }), "ROOT_TAG")
        transaction.commit()
        return v
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
        fun newInstance(listener: OnItemSelect): RootFragment {
            val fragment = RootFragment()
            val args = Bundle()
            fragment.arguments = args
            fragment.mItemListener = listener
            return fragment
        }
    }
}// Required empty public constructor
