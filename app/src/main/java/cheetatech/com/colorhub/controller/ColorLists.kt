package cheetatech.com.colorhub.controller

import android.content.res.Resources
import cheetatech.com.colorhub.R
import cheetatech.com.colorhub.defines.ColorData

/**
 * Created by coderkan on 29.06.2017.
 */
class ColorLists (res: Resources){
    var flatList: MutableList<ColorData>? = null
    var socialList: MutableList<ColorData>? = null
    var metroList: MutableList<ColorData>? = null
    var htmlList: MutableList<ColorData>? = null

    private var resources: Resources

    init {
        // init flat colors
        this.resources = res
        this.flatList = generateList(R.array.FlatColorCode, R.array.FlatColorName)
        this.socialList = generateList(R.array.SocialColorCode, R.array.SocialColorName)
        this.metroList = generateList(R.array.MetroColorCode, R.array.MetroColorName)
        this.htmlList = generateList(R.array.HtmlColorCode, R.array.HtmlColorName)
    }
    fun generateList(arrCode: Int, arrName: Int) : MutableList<ColorData>? {
        var list: MutableList<ColorData>? = mutableListOf()
        var codeList = resources?.getStringArray(arrCode)?.toMutableList()
        var nameList = resources?.getStringArray(arrName)?.toMutableList()
        for (i in 0..codeList!!.size - 1)
            list?.add(ColorData(nameList!![i], codeList!![i].toUpperCase()))
        return list
    }

}