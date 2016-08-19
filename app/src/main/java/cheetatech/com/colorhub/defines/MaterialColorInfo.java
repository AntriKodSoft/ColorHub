package cheetatech.com.colorhub.defines;


import java.util.ArrayList;

public class MaterialColorInfo {
    private ArrayList<ColorInfo> colorInfoList;

    public MaterialColorInfo()
    {
        colorInfoList = new ArrayList<ColorInfo>();
    }

    public MaterialColorInfo(ArrayList<ColorInfo> colorInfos)
    {
        if(this.colorInfoList == null)
            this.colorInfoList = new ArrayList<ColorInfo>();
        for(int i = 0; i < colorInfos.size(); i++)
        this.colorInfoList.add(colorInfos.get(i));
    }
    public ArrayList<ColorInfo> getColorInfoList()
    {
        return this.colorInfoList;
    }
    public ColorInfo getColorInfo(int index)
    {
        if(index > this.colorInfoList.size())
            return null;
        return this.colorInfoList.get(index);
    }

    public void setColorInfoList(ArrayList<ColorInfo> colorInfoList)
    {
        this.colorInfoList = colorInfoList;
    }

}
