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
    var materialLists : MutableList<MutableList<ColorData>> ? = null


    private var resources: Resources

    init {
        // init flat colors
        this.resources = res
        this.flatList = generateList(R.array.FlatColorCode, R.array.FlatColorName)
        this.socialList = generateList(R.array.SocialColorCode, R.array.SocialColorName)
        this.metroList = generateList(R.array.MetroColorCode, R.array.MetroColorName)
        this.htmlList = generateList(R.array.HtmlColorCode, R.array.HtmlColorName)
        this.materialLists = generateMaterialList(R.array.MaterialColorCodeName)
    }
    fun generateMaterialList(id: Int) : MutableList<MutableList<ColorData>> {
        var mlist: MutableList<MutableList<ColorData>> = mutableListOf()

        val arrayId = intArrayOf(R.array.MaterialColorCodeRed, R.array.MaterialColorCodePink, R.array.MaterialColorCodePurple, R.array.MaterialColorCodeDeepPurple, R.array.MaterialColorCodeIndigo, R.array.MaterialColorCodeBlue, R.array.MaterialColorCodeLightBlue, R.array.MaterialColorCodeCyan, R.array.MaterialColorCodeTeal, R.array.MaterialColorCodeGreen, R.array.MaterialColorCodeLightGreen, R.array.MaterialColorCodeLime, R.array.MaterialColorCodeYellow, R.array.MaterialColorCodeAmber, R.array.MaterialColorCodeOrange, R.array.MaterialColorCodeDeepOrange, R.array.MaterialColorCodeBrown, R.array.MaterialColorCodeGrey, R.array.MaterialColorCodeBlueGrey)
        var nameList = resources?.getStringArray(id)?.toMutableList()
        var j: Int = 0
        for(i in 0..arrayId.size-1){
            var codeList = resources?.getStringArray(arrayId[i])?.toMutableList()
            var list: MutableList<ColorData>? = mutableListOf()
            var name = nameList?.get(j) ?: ""
            for (f in 0..codeList!!.size - 1)
                list?.add(ColorData(name, codeList!![f].toUpperCase()))
            if (list != null) {
                mlist?.add(list)
            }
        }
        return mlist
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