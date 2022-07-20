package com.example.app_doc_truyen.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.app_doc_truyen.Adapter.ViewPagerAdapter;
import com.example.demo_app_doc_truyen.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //testadadadsasdadadadadadasd

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNaVi);
        viewPager= findViewById(R.id.viewPa);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.Theme_Dark);
            bottomNavigationView.setBackgroundColor(getColor(R.color.black));
        }else {
            setTheme(R.style.Theme_Light);
            bottomNavigationView.setBackgroundColor(getColor(R.color.white));
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bot_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bot_Yeu:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.bot_chude:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.bot_canhan:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        setViewPager();
    }
    public void setViewPager(){
        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.bot_home).setChecked(true);
                        break;

                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.bot_Yeu).setChecked(true);

                        break;

                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.bot_chude).setChecked(true);
                        break;

                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.bot_canhan).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}