package com.wecan.xhin.studio.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.design.widget.RxNavigationView;
import com.jakewharton.rxbinding.support.v4.widget.RxDrawerLayout;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.FellowData;
import com.wecan.xhin.studio.databinding.ActivityMainBinding;
import com.wecan.xhin.studio.fragment.BooksFragment;
import com.wecan.xhin.studio.fragment.FellowsFragment;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    private FellowsFragment inRoomFellowsFragment, allFellowFragment;
    private BooksFragment booksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        RxNavigationView.itemSelections(binding.navView)
                .compose(this.<MenuItem>bindToLifecycle())
                .map(new Func1<MenuItem, Integer>() {
                    @Override
                    public Integer call(MenuItem menuItem) {
                        return menuItem.getItemId();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer menuItem) {
                        setTabSelection(menuItem);
                        RxDrawerLayout.open(binding.drawerLayout, GravityCompat.START)
                                .call(false);
                    }
                });

        binding.navView.setCheckedItem(R.id.in_room);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        inRoomFellowsFragment = new FellowsFragment();
        transaction.add(R.id.fl_content, inRoomFellowsFragment);
        transaction.commit();

    }

    private void getFellows(){
        Api api = App.from(this).createApi(Api.class);
        api.getAllFellow()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<FellowData>bindToLifecycle())
                .subscribe(new Action1<FellowData>() {
                    @Override
                    public void call(FellowData fellowData) {

                    }
                });
    }

    private void setTabSelection(int itemId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.in_room:
                hideFragments(transaction);
                if (inRoomFellowsFragment == null) {
                    inRoomFellowsFragment = new FellowsFragment();
                    transaction.add(R.id.fl_content, inRoomFellowsFragment);
                    break;
                }
                transaction.show(inRoomFellowsFragment);
                break;
            case R.id.book:
                hideFragments(transaction);
                if (booksFragment == null) {
                    booksFragment = new BooksFragment();
                    transaction.add(R.id.fl_content, booksFragment);
                    break;
                }
                transaction.show(booksFragment);
                break;
            case R.id.all_fellow:
                hideFragments(transaction);
                if (allFellowFragment == null) {
                    allFellowFragment = new FellowsFragment();
                    transaction.add(R.id.fl_content, allFellowFragment);
                    break;
                }
                transaction.show(allFellowFragment);
                break;
            case R.id.about:
                break;
            case R.id.me:
                break;
        }
        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (inRoomFellowsFragment != null) {
            transaction.hide(inRoomFellowsFragment);
        }
        if (allFellowFragment != null) {
            transaction.hide(allFellowFragment);
        }
        if (booksFragment != null) {
            transaction.hide(booksFragment);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
