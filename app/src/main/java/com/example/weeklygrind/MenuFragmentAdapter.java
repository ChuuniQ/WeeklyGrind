package com.example.weeklygrind;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MenuFragmentAdapter extends FragmentStateAdapter {
    public MenuFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 1: return new MainsFragment();
            case 2: return new DessertFragment();
            case 3: return new DrinksFragment();

        }

        return new AppetizerFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
