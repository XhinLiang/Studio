package com.wecan.xhin.studio.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.jakewharton.rxbinding.support.design.widget.RxNavigationView;
import com.jakewharton.rxbinding.support.v4.widget.RxDrawerLayout;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityMainBinding;
import com.wecan.xhin.studio.databinding.IncludeNavHeaderMainBinding;
import com.wecan.xhin.studio.fragment.AllUserFragment;
import com.wecan.xhin.studio.fragment.SignedUserFragment;
import com.wecan.xhin.studio.fragment.BooksFragment;

import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends BaseActivity {

    public static final String KEY_USER = "user";
    public static final int REQUEST_MY_DETAIL = 0;

    private ActivityMainBinding binding;
    private IncludeNavHeaderMainBinding headerBinding;
    private User user;
    private Fragment signedUserFragment, allFellowFragment, booksFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        user = getIntent().getParcelableExtra(KEY_USER);
        binding.setUser(user);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        headerBinding = DataBindingUtil
                .inflate(getLayoutInflater(), R.layout.include_nav_header_main, null, false);
        headerBinding.setUser(user);
        binding.navView.addHeaderView(headerBinding.getRoot());


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
        initNavigationSelection();
    }

    private void initNavigationSelection() {
        binding.navView.setCheckedItem(R.id.signed_user);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        signedUserFragment = SignedUserFragment.newInstance(user);
        transaction.add(R.id.fl_content, signedUserFragment);
        transaction.commit();
    }

    private void setTabSelection(int itemId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.signed_user:
                hideFragments(transaction);
                if (signedUserFragment == null) {
                    signedUserFragment = SignedUserFragment.newInstance(user);
                    transaction.add(R.id.fl_content, signedUserFragment);
                    break;
                }
                transaction.show(signedUserFragment);
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
            case R.id.all_user:
                hideFragments(transaction);
                if (allFellowFragment == null) {
                    allFellowFragment = AllUserFragment.newInstance();
                    transaction.add(R.id.fl_content, allFellowFragment);
                    break;
                }
                transaction.show(allFellowFragment);
                break;
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.me:
                startActivityForResult(new Intent(this, MyDetailsActivity.class)
                        .putExtra(UserDetailsActivity.KEY_USER, user), REQUEST_MY_DETAIL);
                break;
        }
        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (signedUserFragment != null) {
            transaction.hide(signedUserFragment);
        }
        if (allFellowFragment != null) {
            transaction.hide(allFellowFragment);
        }
        if (booksFragment != null) {
            transaction.hide(booksFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case REQUEST_MY_DETAIL:
                user = data.getParcelableExtra(KEY_USER);
                binding.setUser(user);
                headerBinding.setUser(user);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

}
