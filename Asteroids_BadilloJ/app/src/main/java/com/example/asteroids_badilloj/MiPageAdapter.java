package com.example.asteroids_badilloj;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MiPageAdapter  extends FragmentPagerAdapter {

    public MiPageAdapter(FragmentManager fm){ super(fm);}


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0: fragment= new Tab1(); break;

            case 1: fragment= new Tab2(); break;
            case 2: fragment= new Tab3(); break;
            case 3: fragment= new Tab4(); break;

        }
        return fragment;


    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override public CharSequence getPageTitle(int Position){
        switch (Position){
            case 0 : return "Asteroids";
            case 1 : return "Calc";
            case 2 : return "Grafics";
            case 3 : return "Conversor";
        }
        return null;
    }

}
