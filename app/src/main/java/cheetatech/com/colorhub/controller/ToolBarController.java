package cheetatech.com.colorhub.controller;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;


public class ToolBarController {
    private Toolbar toolbar = null;
    private TabLayout tabLayout = null;
    private static ToolBarController ourInstance = new ToolBarController();

    public static ToolBarController getInstance() {
        return ourInstance;
    }

    private ToolBarController() {
    }

    public void setToolBar(Toolbar toolBar)
    {
        this.toolbar = toolBar;
    }
    public void setTabLayout(TabLayout tabLayout)
    {
        this.tabLayout = tabLayout;
    }
    public Toolbar getToolbar()
    {
        return this.toolbar;
    }

    public void setColor(int color)
    {
        this.tabLayout.setBackgroundColor(color);
        this.toolbar.setBackgroundColor(color);
    }
    public void setTextColor(int color)
    {
        this.tabLayout.setTabTextColors(color,color);
        toolbar.setTitleTextColor(color);
    }
}
