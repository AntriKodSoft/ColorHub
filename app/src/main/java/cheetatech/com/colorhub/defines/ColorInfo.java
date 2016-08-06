package cheetatech.com.colorhub.defines;

public class ColorInfo {

    private String colorName = null;
    private String colorCode = null;
    public ColorInfo(){}
    public ColorInfo(String colorName,String colorCode)
    {
        this.colorName = colorName;
        this.colorCode = colorCode;
    }
    public void setColorName(String colorName)
    {
        this.colorName = colorName;
    }
    public String getColorName()
    {
        return this.colorName;
    }
    public void setColorCode(String colorCode)
    {
        this.colorCode = colorCode;
    }
    public String getColorCode()
    {
        return this.colorCode;
    }
}
