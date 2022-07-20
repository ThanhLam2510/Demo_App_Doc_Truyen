package com.example.app_doc_truyen.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.app_doc_truyen.Fragment.ListBaiViet_Fragment;
import com.example.app_doc_truyen.Fragment.ThemBaiViet_Fragment;

public class TapAdapter extends FragmentStatePagerAdapter {
    public TapAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ThemBaiViet_Fragment();
            default:
                return new ListBaiViet_Fragment();
//            default:
//                return new ThemBaiViet_Fragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tile ="";
        switch (position){
            case 0:
            tile="Bai Viet";
            break;
            case 1:
                tile="List";
                break;
        }
        return tile;
    }
}
