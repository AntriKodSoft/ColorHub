package cheetatech.com.colorhub.adapters;


import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();
    private final List<Drawable> fragmentDrawableList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment,String title, Drawable drawable)
    {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
        fragmentDrawableList.add(drawable);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        SpannableStringBuilder sb;
        ImageSpan imageSpan;

        Drawable mDrawable = this.fragmentDrawableList.get(position);
        sb = new SpannableStringBuilder("  " + fragmentTitleList.get(position)); // space added before text for convenience

        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        imageSpan = new ImageSpan(mDrawable, ImageSpan.ALIGN_BASELINE);
        sb.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

    }

}
