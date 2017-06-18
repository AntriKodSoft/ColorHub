package layout

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.MainPageAdapter
import cheetatech.com.colorhub.models.MainPageModel

class MainFragment : Fragment(){


    private var mColorListener: ColorPicker1.OnColorListener? = null

    fun setListener(mColorListener: ColorPicker1.OnColorListener?){
        this.mColorListener = mColorListener;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v = inflater!!.inflate(R.layout.fragment_main, container, false)
        ButterKnife.bind(this,v)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var mRecyclerView = view?.findViewById(R.id.main_page_recycler_view) as RecyclerView


        var mlist : MutableList<MainPageModel> = mutableListOf(MainPageModel("Page 1"), MainPageModel("Page 2"), MainPageModel("Page 3"))//mutableListOf()<MainPageModel>()

        var manager = LinearLayoutManager(activity.applicationContext);
        mRecyclerView?.layoutManager = manager
        mRecyclerView?.setHasFixedSize(true)

        var adapter = MainPageAdapter(mlist);
        mRecyclerView?.adapter = adapter

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
}// Required empty public constructor
