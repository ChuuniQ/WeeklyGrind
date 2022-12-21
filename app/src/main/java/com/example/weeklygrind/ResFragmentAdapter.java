package com.example.weeklygrind;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ResFragmentAdapter extends FragmentStateAdapter {
    public ResFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 1: return new ViewResFragment();
        }

        return new AddResFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
