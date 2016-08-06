package cheetatech.com.colorhub.controller;


import android.content.res.Resources;

import java.util.ArrayList;

import cheetatech.com.colorhub.R;
import cheetatech.com.colorhub.defines.ColorInfo;

public class ColorArrayController {
    private ArrayList<ColorInfo> materialList = null;
    private ArrayList<ColorInfo> flatList = null;
    private ArrayList<ColorInfo> socialList = null;
    private ArrayList<ColorInfo> metroList = null;

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
    }

    public void setResource(Resources resource)
    {
        this.resources = resource;
    }

    public void initMaterial()
    {
        if(resources == null)
            return;
        materialList.clear();

        String[] colorCodes = resources.getStringArray(R.array.MaterialColorCodeRed);
        String[] colorNames = resources.getStringArray(R.array.MaterialColorNameRed);
        for(int i = 0; i< colorCodes.length;i++)
            materialList.add(new ColorInfo(colorNames[i],colorCodes[i]));

    }
    public void initFlat()
    {
        if(resources == null)
            return;
        flatList.clear();
        String[] colorCodes = resources.getStringArray(R.array.FlatColorCode);
        String[] colorNames = resources.getStringArray(R.array.FlatColorName);
        for(int i = 0; i< colorCodes.length;i++)
            flatList.add(new ColorInfo(colorNames[i],colorCodes[i]));

    }
    public void initSocial()
    {
        if(resources == null)
            return;
        socialList.clear();
        String[] colorCodes = resources.getStringArray(R.array.SocialColorCode);
        String[] colorNames = resources.getStringArray(R.array.SocialColorName);
        for(int i = 0; i< colorCodes.length;i++)
            socialList.add(new ColorInfo(colorNames[i],colorCodes[i]));

    }
    public void initMetro()
    {
        if(resources == null)
            return;
        metroList.clear();
        String[] colorCodes = resources.getStringArray(R.array.MetroColorCode);
        String[] colorNames = resources.getStringArray(R.array.MetroColorName);
        for(int i = 0; i< colorCodes.length;i++)
            metroList.add(new ColorInfo(colorNames[i],colorCodes[i]));


    }

    public void setMaterialList(ArrayList<ColorInfo> list)
    {
        this.materialList = list;
    }
    public ArrayList<ColorInfo> getMaterialList()
    {
        return this.materialList;
    }

    public void setFlatList(ArrayList<ColorInfo> list)
    {
        this.flatList = list;
    }
    public ArrayList<ColorInfo> getFlatList()
    {
        return this.flatList;
    }
    public void setSocialList(ArrayList<ColorInfo> list)
    {
        this.socialList = list;
    }
    public ArrayList<ColorInfo> getSocialList()
    {
        return this.socialList;
    }
    public void setMetroList(ArrayList<ColorInfo> list)
    {
        this.metroList = list;
    }
    public ArrayList<ColorInfo> getMetroList()
    {
        return this.metroList;
    }


}
