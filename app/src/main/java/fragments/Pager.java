package fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by viper on 12/09/16.
 */
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int pos) {
        switch(pos % 3) {

            case 0: return FirstFragment.newInstance("FirstFragment, Instance 1");

            case 1: return SecondFragment.newInstance("SecondFragment, Instance 1");

            case 2: return ThirdFragment.newInstance("ThirdFragment, Instance 1");

            default: return FirstFragment.newInstance("FirstFragment, Instance 1");
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
