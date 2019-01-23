package com.example.zul.moviesmade.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.zul.moviesmade.R;
import com.example.zul.moviesmade.adapter.TabAdapter;
import com.example.zul.moviesmade.fragment.FavoriteFragment;
import com.example.zul.moviesmade.fragment.NowPlayingFragment;
import com.example.zul.moviesmade.fragment.UpComingFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context mContext;
    private TabAdapter tabAdapter;
    @BindView(R.id.toolbar_tab)
    Toolbar toolbar;
    @BindView(R.id.tab_main)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_main)
    ViewPager viewPagerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: called");

        ButterKnife.bind(this);
        mContext = this;

        tabAdapter = new TabAdapter(getSupportFragmentManager());

        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_main);

        setViewPagerMain(viewPagerMain);
        tabLayout.setupWithViewPager(viewPagerMain);

    }

    private void setViewPagerMain(ViewPager viewPagerMain) {
        Log.d(TAG, "setViewPagerMain: called");
        tabAdapter.addNewFragment(new NowPlayingFragment(), getResources().getString(R.string.now_playing));
        tabAdapter.addNewFragment(new UpComingFragment(), getResources().getString(R.string.up_coming));
        tabAdapter.addNewFragment(new FavoriteFragment(), getResources().getString(R.string.favorite));
        viewPagerMain.setAdapter(tabAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: called");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: called");

        switch (item.getItemId()) {
            case R.id.search_main:
                Intent intentSearch = new Intent(mContext, SearchActivity.class);
                startActivity(intentSearch);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.settings_menu:
                Intent intentLanguage = new Intent(mContext, SettingsActivity.class);
                startActivity(intentLanguage);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}