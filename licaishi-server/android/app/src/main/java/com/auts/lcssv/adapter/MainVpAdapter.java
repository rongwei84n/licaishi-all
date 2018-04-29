package com.auts.lcssv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.auts.lcssv.fragment.HomepageFragment;
import com.auts.lcssv.fragment.ProductsFragment;
import com.auts.lcssv.fragment.MineFragment;
import com.auts.lcssv.fragment.WorkroomFragment;

/**
 *
 */
public class MainVpAdapter extends FragmentPagerAdapter {
    private SparseArray<Fragment> mSa = new SparseArray<>();

    public MainVpAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mSa.get(position);
        if (fragment != null) {
            return fragment;
        }

        if (position == 0) {
            fragment = new HomepageFragment();
        } else if (position == 1) {
            fragment = new ProductsFragment();
        }else if (position == 2) {
            fragment = new WorkroomFragment();
        } else {
            fragment = new MineFragment();
        }
        mSa.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public Fragment getFragment(int position) {
        return getItem(position);
    }

}
