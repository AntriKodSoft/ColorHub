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
import butterknife.ButterKnife
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.MainPageAdapter
import cheetatech.com.colorhub.listeners.OnItemSelect
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


        var mlist : MutableList<MainPageModel> = mutableListOf(MainPageModel("Flat"),
                MainPageModel("Material"),
                MainPageModel("Social"),
                MainPageModel("Metro"),
                MainPageModel("Html"),
                MainPageModel("ColorPalette X"),
                MainPageModel("ColorPalette Y")
                )

        var manager = GridLayoutManager(activity.applicationContext, 2)
        mRecyclerView.layoutManager = manager
        mRecyclerView.setHasFixedSize(true)

        var adapter = MainPageAdapter(mlist, object: OnItemSelect{
            override fun onItemSelected(position: Int) {
                println("PositionXXXX: " + position)

                var fragment = FlatColorFragment.newInstance(object :ColorPicker1.OnColorListener{
                    override fun onAddColor(color: String?) {

                    }
                })
                val transaction = fragmentManager.beginTransaction()
                transaction.add(R.id.root_frame, fragment)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.addToBackStack(null)
                transaction.commit()

            }
        } );

        mRecyclerView.adapter = adapter
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
