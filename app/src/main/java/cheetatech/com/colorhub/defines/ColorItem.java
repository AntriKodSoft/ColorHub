package cheetatech.com.colorhub.defines;


public class ColorItem {

    private int red,green,blue,opacity;

    public ColorItem(){

    }
    public ColorItem(int red,int green,int blue, int opacity)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }
    public int setRed(int red){return  this.red = red;}
    public int setGreen(int green){return this.green = green;}
    public int setBlue(int blue){return this.blue = blue;}
    public int setOpacity(int opacity){return this.opacity = opacity;}

    public int getRed(){return  this.red;}
    public int getGreen(){return this.green;}
    public int getBlue(){return this.blue;}
    public int getOpacity(){return this.opacity;}
    public String toString()
    {
        return String.format("#%02x%02x%02x%02x", red, green, blue,opacity);
    }

}
