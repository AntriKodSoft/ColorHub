package cheetatech.com.colorhub.defines;


import java.util.ArrayList;
import java.util.List;

public class MaterialColorInfo {
    private List<ColorInfo> colorInfoList = new ArrayList<ColorInfo>();

    public MaterialColorInfo(List<ColorInfo> colorInfos)
    {
        this.colorInfoList.addAll(colorInfos);
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
