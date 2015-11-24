package com.wecan.xhin.studio.activity;

import android.content.Intent;
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
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityMainBinding;
import com.wecan.xhin.studio.databinding.IncludeNavHeaderMainBinding;
import com.wecan.xhin.studio.fragment.AllUserFragment;
import com.wecan.xhin.studio.fragment.SignedUserFragment;
import com.wecan.xhin.studio.fragment.UsersFragment;

import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends RxAppCompatActivity {

    public static final String KEY_USER = "user";

    private ActivityMainBinding binding;
    private UsersFragment signedUserFragment, allFellowFragment;
    private UsersFragment booksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        IncludeNavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.getBinding(binding.navView);
        User user = getIntent().getParcelableExtra(KEY_USER);
        navHeaderMainBinding.setUser(user);

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
        initNavigationSelection();
    }

    private void initNavigationSelection() {
        binding.navView.setCheckedItem(R.id.signed_user);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        signedUserFragment = SignedUserFragment.newInstance();
        transaction.add(R.id.fl_content, signedUserFragment);
        transaction.commit();
    }

    private void setTabSelection(int itemId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.signed_user:
                hideFragments(transaction);
                if (signedUserFragment == null) {
                    signedUserFragment = SignedUserFragment.newInstance();
                    transaction.add(R.id.fl_content, signedUserFragment);
                    break;
                }
                transaction.show(signedUserFragment);
                break;
            case R.id.book:
                hideFragments(transaction);
                if (booksFragment == null) {
                    booksFragment = SignedUserFragment.newInstance();
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
                startActivity(new Intent(this, UserDetailsActivity.class));
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
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
