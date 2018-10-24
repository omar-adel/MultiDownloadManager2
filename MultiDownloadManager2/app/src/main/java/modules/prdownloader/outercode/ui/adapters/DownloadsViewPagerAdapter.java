package modules.prdownloader.outercode.ui.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import modules.prdownloader.outercode.ui.fragments.AllDownloads;
import modules.prdownloader.outercode.ui.fragments.CompletedDownloads;
import modules.prdownloader.outercode.ui.fragments.PausedDownloads;


public class DownloadsViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when DownloadsViewPagerAdapter is created

    Fragment mCurrentPrimaryFrgItem;
    private int lastPosition = -1;

    public DownloadsViewPagerAdapter(FragmentManager fm, CharSequence mTitles[]) {
        super(fm);

        this.Titles = mTitles;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new AllDownloads();

            case 1:
                return new PausedDownloads();

            case 2:
                return new CompletedDownloads();

        }


        return null;

    }


    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    @Override
    public int getCount() {
        return Titles.length;
    }

    public Fragment getCurrentFragment() {
        return mCurrentPrimaryFrgItem;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (lastPosition != position) {
            lastPosition = position;
            mCurrentPrimaryFrgItem = (Fragment) object;
        }
    }

}
