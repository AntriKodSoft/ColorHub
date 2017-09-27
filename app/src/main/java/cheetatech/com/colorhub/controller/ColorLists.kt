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


    private var resources: Resources = res

    init {
        // init flat colors
        this.flatList = generateList(R.array.FlatColorCode, R.array.FlatColorName)
        this.socialList = generateList(R.array.SocialColorCode, R.array.SocialColorName)
        this.metroList = generateList(R.array.MetroColorCode, R.array.MetroColorName)
        this.htmlList = generateList(R.array.HtmlColorCode, R.array.HtmlColorName)
        //this.materialLists = generateMaterialList(R.array.MaterialColorNames)
        //this.materialLists = generateList()
        generateList()
        println("sadfasdf")
    }

    fun generateList(){
        this.materialLists = mutableListOf()
        val arrayId = intArrayOf(R.array.MaterialColorCodeRed, R.array.MaterialColorCodePink, R.array.MaterialColorCodePurple, R.array.MaterialColorCodeDeepPurple, R.array.MaterialColorCodeIndigo, R.array.MaterialColorCodeBlue, R.array.MaterialColorCodeLightBlue, R.array.MaterialColorCodeCyan, R.array.MaterialColorCodeTeal, R.array.MaterialColorCodeGreen, R.array.MaterialColorCodeLightGreen, R.array.MaterialColorCodeLime, R.array.MaterialColorCodeYellow, R.array.MaterialColorCodeAmber, R.array.MaterialColorCodeOrange, R.array.MaterialColorCodeDeepOrange, R.array.MaterialColorCodeBrown, R.array.MaterialColorCodeGrey, R.array.MaterialColorCodeBlueGrey)
        var nameList = resources.getStringArray(R.array.MaterialColorNames)?.toMutableList() // Red, Pink color names
        var detailNameList = resources.getStringArray(R.array.MaterialColorCodeName)?.toMutableList() // 50 - 100 - 200 color names
        for(i in 0 .. arrayId.size.minus(1)){
            var arrId = arrayId[i]
            var list = resources.getStringArray(arrId)?.toMutableList()
            var colorDataList: MutableList<ColorData>? = mutableListOf()
            for(j in 0 ..list?.size?.minus(1)!!){
                if(j == 0){
                    var name = nameList?.get(i) ?: ""
                    colorDataList?.add(ColorData(name, list[j].toUpperCase()))
                }else{
                    var name = detailNameList?.get(j) ?: ""
                    colorDataList?.add(ColorData(name, list[j].toUpperCase()))
                }
            }
            if(colorDataList != null){
                this.materialLists?.add(colorDataList)
            }
        }
    }

    fun generateList(arrCode: Int, arrName: Int) : MutableList<ColorData>? {
        var list: MutableList<ColorData>? = mutableListOf()
        var codeList = resources.getStringArray(arrCode)?.toMutableList()
        var nameList = resources.getStringArray(arrName)?.toMutableList()
        for (i in 0..codeList!!.size - 1)
            list?.add(ColorData(nameList!![i], codeList[i].toUpperCase()))
        return list
    }

}