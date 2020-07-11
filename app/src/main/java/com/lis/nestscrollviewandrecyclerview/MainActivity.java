package com.lis.nestscrollviewandrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lis.nestscrollviewandrecyclerview.view.fragment.RecyclerViewFragment;
import com.lis.nestscrollviewandrecyclerview.view.viewpager.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 mViewPager2;
    com.google.android.material.tabs.TabLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager2 = findViewById(R.id.viewpager_view);
        mTableLayout = findViewById(R.id.tablayout);
        //设置Viewpager
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, getPageFragments());
        mViewPager2.setAdapter(pagerAdapter);

        //
        final String[] labels = new String[]{"linear", "scroll", "recycler"};
        new TabLayoutMediator(mTableLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(labels[position]);
            }
        }).attach(); // 不要忘记，否则没效果
    }


    private List<Fragment> getPageFragments() {
        List<Fragment> data = new ArrayList<>();
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        data.add(new RecyclerViewFragment());
        return data;
    }
}
