package com.example.weeklygrind;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderFragmentAdapter extends FragmentStateAdapter {
    public OrderFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 1: return new ViewOrderFragment();
        }

        return new AddOrderFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}