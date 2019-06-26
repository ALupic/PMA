package com.example.news24;

import android.content.Intent;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {
    Button btnAddCategory;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    String[] categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnAddCategory = (Button)findViewById(R.id.addTopicButton);
        tabLayout =(TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarId);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        ViewPagerAdapterAdmin adapter = new ViewPagerAdapterAdmin(getSupportFragmentManager());
        adapter.addFragment(new FragmentTopicsAdmin(),"TOPICS");
        adapter.addFragment(new FragmentArticlesAdmin(),"ARTICLES");
        adapter.addFragment(new FragmentCommentsAdmin(),"COMMENTS");
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFragmentTopic.class);
                startActivity(intent);
            }
        });
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
