package cheetatech.com.colorhub.database;

public class SqlType {

    private String name = null;
    private int color1,color2,color3,color4,color5;
    public SqlType(String name, int c1,int c2,int c3, int c4, int c5)
    {
        this.name = name;
        this.color1 = c1;
        this.color2 = c2;
        this.color3 = c3;
        this.color4 = c4;
        this.color5 = c5;
    }

    public String getName()
    {
        return this.name;
    }
    public String getColor1()
    {
        return String.valueOf(color1);
    }
    public String getColor2()
    {
        return String.valueOf(color2);
    }
    public String getColor3()
    {
        return String.valueOf(color3);
    }
    public String getColor4()
    {
        return String.valueOf(color4);
    }
    public String getColor5()
    {
        return String.valueOf(color5);
    }

}
