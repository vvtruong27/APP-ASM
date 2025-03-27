package com.example.expensesmanagement.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.expensesmanagement.Fragments.BudgetFragment;
import com.example.expensesmanagement.Fragments.ExpenseFragment;
import com.example.expensesmanagement.Fragments.HomeFragment;
import com.example.expensesmanagement.Fragments.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentList=new ArrayList<>();

    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ExpenseFragment());
        fragmentList.add(new BudgetFragment());
        fragmentList.add(new SettingFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
