package com.xuan.fontgradienttab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PagerActivity extends AppCompatActivity {

    private String[] items={"直播","推荐","视频","图片","段子","精华"};
    private ArrayList<Fragment> fragments;
    private ArrayList<FontGradientTab> fontGradientTabs;

    private ViewPager view_pager;
    private LinearLayout linear_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        linear_tab=findViewById(R.id.linear_tab);
        view_pager=findViewById(R.id.view_pager);

        initTabLayout();
        initViewPager();
    }

    @SuppressLint("ResourceAsColor")
    private void initTabLayout() {
        fontGradientTabs=new ArrayList<>();

        for (String item : items) {
            FontGradientTab tab=new FontGradientTab(this);
            tab.setText(item);
            tab.setDefaultFontSize(20);
            tab.setGradientFontSize(20);
            tab.setDefaultColor(ContextCompat.getColor(this,R.color.red));
            tab.setGradientColor(ContextCompat.getColor(this,R.color.green));
            tab.setGradientPercentage(1f);

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight=1;

            tab.setLayoutParams(params);

            linear_tab.addView(tab);
            fontGradientTabs.add(tab);
        }
    }

    private void initViewPager() {
        fragments=new ArrayList<>();

        for (String item : items) {
            Bundle args=new Bundle();
            args.putString("text",item);
            fragments.add(DefaultFragment.newInstant(args));
        }

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        view_pager.setAdapter(adapter);

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                FontGradientTab left = fontGradientTabs.get(position);
                left.setOrientation(FontGradientTab.ORIENTATION.ORIENTATION_LEFT);
                left.startPerform(1-positionOffset);

                if(position+1<fontGradientTabs.size()){
                    FontGradientTab right = fontGradientTabs.get(position+1);
                    right.setOrientation(FontGradientTab.ORIENTATION.ORIENTATION_RIGHT);
                    right.startPerform(positionOffset);
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
