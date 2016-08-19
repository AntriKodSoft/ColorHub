package cheetatech.com.colorhub.drawer;


public class NavigationSelect {
    private String title;
    private int drawableId ;

    public NavigationSelect(){}
    public NavigationSelect(String title,int id)
    {
        this.title = title;
        this.drawableId = id;
    }
    public String getTitle()
    {
        return this.title;
    }
    public int getDrawableId()
    {
        return this.drawableId;
    }

}
