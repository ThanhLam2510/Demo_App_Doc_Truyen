package com.example.app_doc_truyen.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.app_doc_truyen.Fragment.CaNhan_Fragment;
import com.example.app_doc_truyen.Fragment.ChuDe_Fragment;
import com.example.app_doc_truyen.Fragment.Trangchu_Fragment;
import com.example.app_doc_truyen.Fragment.YeuThich_Fragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Trangchu_Fragment();
            case 1:
                return new YeuThich_Fragment();
            case 2:
                return new ChuDe_Fragment();
            case 3:
                return new CaNhan_Fragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
