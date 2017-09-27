package layout

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.MaterialKotlinAdapter
import cheetatech.com.colorhub.defines.ColorData
import cheetatech.com.colorhub.listeners.OnItemSelect

class MaterialRootFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    private var materialLists : MutableList<MutableList<ColorData>> ? = null
    private var mItemSelectListener: OnItemSelect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_material_root, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var mRecyclerView = view?.findViewById(R.id.material_root_recycler_view) as RecyclerView


        var materialList: MutableList<ColorData> = mutableListOf<ColorData>(
                ColorData(name = "Red", code = "#f44336"),
                ColorData(name = "Pink", code = "#E91E63"),
                ColorData(name = "Purple", code = "#9C27B0"),
                ColorData(name = "Deep Purple", code = "#673AB7"),
                ColorData(name = "Indigo", code = "#3F51B5"),
                ColorData(name = "Blue", code = "#2196F3"),
                ColorData(name = "Light Blue", code = "#03A9F4"),
                ColorData(name = "Cyan", code = "#00BCD4"),
                ColorData(name = "Teal", code = "#009688"),
                ColorData(name = "Green", code = "#4CAF50"),
                ColorData(name = "Light Green", code = "#8BC34A"),
                ColorData(name = "Lime", code = "#CDDC39"),
                ColorData(name = "Yellow", code = "#FFEB3B"),
                ColorData(name = "Amber", code = "#FFC107"),
                ColorData(name = "Orange", code = "#FF9800"),
                ColorData(name = "Deep Orange", code = "#FF5722"),
                ColorData(name = "Brown", code = "#795548"),
                ColorData(name = "Grey", code = "#9E9E9E"),
                ColorData(name = "Blue Grey", code = "#607D8B")
                )

        var manager = LinearLayoutManager(activity.applicationContext)
        with(mRecyclerView){
            layoutManager = manager
            setHasFixedSize(true)
        }

        var adapter = MaterialKotlinAdapter(materialList, object : OnItemSelect{
            override fun onAddColor(color: String) {
            }

            override fun onItemSelected(position: Int) {
                var fragment = ColorKotlinFragment.newInstance(materialLists?.get(position) as MutableList<ColorData>, object : OnItemSelect{
                    override fun onItemSelected(position: Int) {

                    }

                    override fun onAddColor(color: String) {
                        mItemSelectListener?.onAddColor(color)
                    }

                })
                val transaction = fragmentManager.beginTransaction()
                with(transaction){
                    add(R.id.root_frame, fragment)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    addToBackStack(null)
                    commit()
                }
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
        fun newInstance(matLists : MutableList<MutableList<ColorData>> ? , mlistener: OnItemSelect): MaterialRootFragment {
            val fragment = MaterialRootFragment()
            fragment.materialLists = matLists
            fragment.mItemSelectListener = mlistener
            return fragment
        }
    }
}
