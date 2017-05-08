package cheetatech.com.colorhub.controller;


import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.ColorInfo;
import cheetatech.com.colorhub.defines.MaterialColorInfo;
import cheetatech.com.colorhub.drawer.ColorSelect;

public class ColorArrayController {
    private List<ColorInfo> materialList = null;
    private List<ColorInfo> flatList = null;
    private List<ColorInfo> socialList = null;
    private List<ColorInfo> metroList = null;
    private List<ColorInfo> htmlList = null;
    private String[] headerColorList = null;

    private List<MaterialColorInfo> materialColorInfoList = null;

    private Resources resources = null;

    private static ColorArrayController ourInstance = null;

    public static ColorArrayController getInstance()
    {
        if(ourInstance == null)
            ourInstance = new ColorArrayController();
        return ourInstance;
    }

    private ColorArrayController() {
        materialList = new ArrayList<ColorInfo>();
        flatList = new ArrayList<ColorInfo>();
        socialList = new ArrayList<ColorInfo>();
        metroList = new ArrayList<ColorInfo>();
        htmlList = new ArrayList<ColorInfo>();
        materialColorInfoList = new ArrayList<MaterialColorInfo>();
    }

    public void init()
    {
        initMaterial();
        initFlat();
        initMetro();
        initSocial();
        initHtml();
    }

    public void setResource(Resources resource)
    {
        this.resources = resource;
    }

    public void initMaterial()
    {
        if(resources == null)
            return;
        materialColorInfoList.clear();

        int[] arrayId = new int[]{
                R.array.MaterialColorCodeRed,R.array.MaterialColorCodePink ,R.array.MaterialColorCodePurple ,
                R.array.MaterialColorCodeDeepPurple ,R.array.MaterialColorCodeIndigo ,R.array.MaterialColorCodeBlue ,
                R.array.MaterialColorCodeLightBlue ,R.array.MaterialColorCodeCyan ,R.array.MaterialColorCodeTeal ,
                R.array.MaterialColorCodeGreen ,R.array.MaterialColorCodeLightGreen ,R.array.MaterialColorCodeLime ,
                R.array.MaterialColorCodeYellow ,R.array.MaterialColorCodeAmber ,R.array.MaterialColorCodeOrange ,
                R.array.MaterialColorCodeDeepOrange ,R.array.MaterialColorCodeBrown ,R.array.MaterialColorCodeGrey ,
                R.array.MaterialColorCodeBlueGrey
        };

        String[] colorNames = resources.getStringArray(R.array.MaterialColorCodeName);

        for(int i = 0; i < arrayId.length; i++)
        {
            String[] colorCodes = resources.getStringArray(arrayId[i]);
            List<ColorInfo> list  = new ArrayList<ColorInfo>();
            for(int j = 0; j< colorCodes.length;j++)
                list.add(new ColorInfo(colorNames[j],colorCodes[j].toUpperCase()));
            materialColorInfoList.add(new MaterialColorInfo(list));
            list.clear();
        }
    }

    public List<MaterialColorInfo> getMaterialColorInfoList()
    {
        return this.materialColorInfoList;
    }

    public void initFlat()
    {
        if(resources == null)
            return;
        flatList.clear();
        String[] colorCodes = resources.getStringArray(R.array.FlatColorCode);
        String[] colorNames = resources.getStringArray(R.array.FlatColorName);
        for(int i = 0; i< colorCodes.length;i++)
            flatList.add(new ColorInfo(colorNames[i],colorCodes[i].toUpperCase()));
    }
    public void initSocial()
    {
        if(resources == null)
            return;
        socialList.clear();
        String[] colorCodes = resources.getStringArray(R.array.SocialColorCode);
        String[] colorNames = resources.getStringArray(R.array.SocialColorName);
        for(int i = 0; i< colorCodes.length;i++)
            socialList.add(new ColorInfo(colorNames[i],colorCodes[i].toUpperCase()));
    }
    public void initMetro()
    {
        if(resources == null)
            return;
        metroList.clear();
        String[] colorCodes = resources.getStringArray(R.array.MetroColorCode);
        String[] colorNames = resources.getStringArray(R.array.MetroColorName);
        for(int i = 0; i< colorCodes.length;i++)
            metroList.add(new ColorInfo(colorNames[i],colorCodes[i].toUpperCase()));
    }

    public List<String> getMaterialNameList()
    {
        String[] colorNames = resources.getStringArray(R.array.MaterialColorNames);
        List<String> list = Arrays.asList(colorNames);
        return list;
    }

    public void initHtml()
    {
        if(resources == null)
            return;
        htmlList.clear();
        String[] colorCodes = resources.getStringArray(R.array.HtmlColorCode);
        String[] colorNames = resources.getStringArray(R.array.HtmlColorName);
        for(int i = 0; i< colorCodes.length;i++)
            htmlList.add(new ColorInfo(colorNames[i],colorCodes[i].toUpperCase()));
    }



    public List<ColorSelect> getMaterialNameColorSelectList()
    {
        List<ColorSelect> list = new ArrayList<ColorSelect>();
        String[] colorNames = resources.getStringArray(R.array.MaterialColorNames);
        for(int i = 0; i< colorNames.length;i++)
            list.add(new ColorSelect(colorNames[i]));
        return list;
    }

    public void setMaterialList(ArrayList<ColorInfo> list)
    {
        this.materialList = list;
    }
    public List<ColorInfo> getMaterialList()
    {
        return this.materialList;
    }

    public void setFlatList(ArrayList<ColorInfo> list)
    {
        this.flatList = list;
    }
    public List<ColorInfo> getFlatList()
    {
        return this.flatList;
    }
    public void setSocialList(List<ColorInfo> list)
    {
        this.socialList = list;
    }
    public List<ColorInfo> getSocialList()
    {
        return this.socialList;
    }
    public void setMetroList(List<ColorInfo> list)
    {
        this.metroList = list;
    }
    public List<ColorInfo> getMetroList()
    {
        return this.metroList;
    }
    public void setHtmlList(List<ColorInfo> list)
    {
        this.htmlList = list;
    }
    public List<ColorInfo> getHtmlList()
    {
        return this.htmlList;
    }

    public String getHeaderColorName(int index)
    {
        if(resources == null)
            return null;
        if(headerColorList == null)
            headerColorList =  resources.getStringArray(R.array.HeaderColorName);

        if(index > headerColorList.length)
            return null;

        return  headerColorList[index];
    }
}
