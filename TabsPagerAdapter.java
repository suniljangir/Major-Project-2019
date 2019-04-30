package com.login_register;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Kshitij on 9/21/2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    TabsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }
    @Override
    public Fragment getItem(int index)
    {
        switch (index)
        {
            case 0:
                return new Login_fragment();
            case 1:
                return new Registration_fragment();

        }
     return null;
    }
    @Override
    public int getCount()
    {
        return 2;
    }
}
