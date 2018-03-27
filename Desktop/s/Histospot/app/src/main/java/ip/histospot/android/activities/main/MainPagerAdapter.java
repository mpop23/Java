package ip.histospot.android.activities.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ip.histospot.android.fragments.ProfileFragment;

/**
 * Created by alex on 23.03.2018.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES = {"Învată", "Duel", "Profil"};

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return this.TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.TITLES[position];
    }
}
