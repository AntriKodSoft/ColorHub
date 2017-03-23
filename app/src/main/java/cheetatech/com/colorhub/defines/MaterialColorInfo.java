package cheetatech.com.colorhub.defines;


import java.util.ArrayList;
import java.util.List;

public class MaterialColorInfo {
    private List<ColorInfo> colorInfoList;

    public MaterialColorInfo()
    {
        colorInfoList = new ArrayList<ColorInfo>();
    }

    public MaterialColorInfo(List<ColorInfo> colorInfos)
    {
        if(this.colorInfoList == null)
            this.colorInfoList = new ArrayList<ColorInfo>();
        for(int i = 0; i < colorInfos.size(); i++)
        this.colorInfoList.add(colorInfos.get(i));
    }
    public List<ColorInfo> getColorInfoList()
    {
        return this.colorInfoList;
    }
    public ColorInfo getColorInfo(int index)
    {
        if(index > this.colorInfoList.size())
            return null;
        return this.colorInfoList.get(index);
    }

    public void setColorInfoList(List<ColorInfo> colorInfoList)
    {
        this.colorInfoList = colorInfoList;
    }

}
