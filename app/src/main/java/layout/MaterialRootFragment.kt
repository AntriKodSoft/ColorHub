package layout

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.adapters.MainPageAdapter
import cheetatech.com.colorhub.adapters.MaterialRootAdapter
import cheetatech.com.colorhub.listeners.OnItemSelect
import cheetatech.com.colorhub.models.MaterialRootModel

class MaterialRootFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

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

        var materialList: MutableList<MaterialRootModel> = mutableListOf<MaterialRootModel>(
                MaterialRootModel(name = "Red", code = "#f44336"),
                MaterialRootModel(name = "Pink", code = "#E91E63"),
                MaterialRootModel(name = "Purple", code = "#9C27B0"),
                MaterialRootModel(name = "Deep Purple", code = "#673AB7"),
                MaterialRootModel(name = "Indigo", code = "#3F51B5"),
                MaterialRootModel(name = "Blue", code = "#2196F3"),
                MaterialRootModel(name = "Light Blue", code = "#03A9F4"),
                MaterialRootModel(name = "Cyan", code = "#00BCD4"),
                MaterialRootModel(name = "Teal", code = "#009688"),
                MaterialRootModel(name = "Green", code = "#4CAF50"),
                MaterialRootModel(name = "Light Green", code = "#8BC34A"),
                MaterialRootModel(name = "Lime", code = "#CDDC39"),
                MaterialRootModel(name = "Yellow", code = "#FFEB3B"),
                MaterialRootModel(name = "Amber", code = "#FFC107"),
                MaterialRootModel(name = "Orange", code = "#FF9800"),
                MaterialRootModel(name = "Deep Orange", code = "#FF5722"),
                MaterialRootModel(name = "Brown", code = "#795548"),
                MaterialRootModel(name = "Grey", code = "#9E9E9E"),
                MaterialRootModel(name = "Blue Grey", code = "#607D8B")
                )

        var manager = LinearLayoutManager(activity.applicationContext)
        with(mRecyclerView){
            layoutManager = manager
            setHasFixedSize(true)
        }

        var adapter = MaterialRootAdapter(materialList, object : OnItemSelect{
            override fun onAddColor(color: String) {
            }

            override fun onItemSelected(position: Int) {
                println("OnItem SelectedXYXY");
            }
        })

        mRecyclerView.adapter = adapter



    }

    // TODO: Rename method, update argument and hook method into UI event
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
        fun newInstance(): MaterialRootFragment {
            val fragment = MaterialRootFragment()
            return fragment
        }
    }
}
