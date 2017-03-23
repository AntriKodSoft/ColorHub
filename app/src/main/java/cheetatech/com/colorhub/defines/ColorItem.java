package cheetatech.com.colorhub.defines;


public class ColorItem {

    private int red,green,blue,opacity;
    private int colour = 0;
    private boolean hasColor = false;
    public ColorItem(){
        hasColor = false;
    }
    public ColorItem(int colour)
    {
        this.colour = colour;this.hasColor = true;
    }

    public ColorItem(int red,int green,int blue, int opacity)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
        this.hasColor = true;
    }
    public void setRed(int red){this.red = red;}
    public void setGreen(int green){this.green = green;}
    public void setBlue(int blue){this.blue = blue;}
    public void setOpacity(int opacity){this.opacity = opacity;}
    public void setColor(int colour){this.colour = colour; this.hasColor = true;}
    public void setColors(int red,int green,int blue,int opacity)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
        this.hasColor = true;
    }

    public void setHasColor(boolean hasColor)
    {
        this.hasColor = hasColor;
    }
    public boolean getHasColor()
    {
        return this.hasColor;
    }

    public int getRed(){return  this.red;}
    public int getGreen(){return this.green;}
    public int getBlue(){return this.blue;}
    public int getOpacity(){return this.opacity;}
    public int getColor(){return this.colour;}
    public String toString()
    {
        return (hasColor) ? String.format("#%02x%02x%02x%02x", opacity, red, green, blue) : "#FFFFFF";
    }
    public String toString2()
    {
        String str = "#" + Integer.toHexString(this.colour);
        return str;
    }
    
}
