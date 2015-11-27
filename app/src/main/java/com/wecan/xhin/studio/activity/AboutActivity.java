package com.wecan.xhin.studio.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.adapter.LibrariesAdapter;
import com.wecan.xhin.studio.bean.GitRepository;
import com.wecan.xhin.studio.databinding.ActivityAboutBinding;

import java.util.LinkedList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class AboutActivity extends BaseActivity implements LibrariesAdapter.Listener {

    private List<GitRepository> libraries = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        setSupportActionBar(binding.toolbar);
        setHasHomeButton();

        setRxClick(binding.fab)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        sendEmail();
                    }
                });

        setupRecyclerView(binding);
    }

    private void setupRecyclerView(ActivityAboutBinding binding) {
        libraries.add(new GitRepository(null, "Visit Our Website", null));
        libraries.add(new GitRepository("Xidian", "Wecan Studio", "http://www.wecanstudio.me"));
        libraries.add(new GitRepository(null, "Visit GitHub of This App", null));
        libraries.add(new GitRepository("XhinLiang", "Studio", "https://github.com/XhinLiang/Studio"));
        libraries.add(new GitRepository(null, "Thanks to", null));
        libraries.add(new GitRepository("Square", "Okhttp", "https://square.github.io/okhttp"));
        libraries.add(new GitRepository("Square", "Retrofit", "https://square.github.io/retrofit"));
        libraries.add(new GitRepository("Google", "Gson", "https://github.com/google/gson"));
        libraries.add(new GitRepository("ReactiveX", "RxJava", "https://github.com/ReactiveX/RxJava"));
        libraries.add(new GitRepository("ReactiveX", "RxAndroid", "https://github.com/ReactiveX/RxAndroid"));
        libraries.add(new GitRepository("JakeWharton", "RxBinding", "https://github.com/JakeWharton/RxBinding"));
        libraries.add(new GitRepository("JakeWharton", "nineoldandroids", "https://github.com/JakeWharton/NineOldAndroids"));
        libraries.add(new GitRepository("Trello", "RxLifecycle", "https://github.com/trello/RxLifecycle"));
        libraries.add(new GitRepository("oxoooo", "mr-mantou-android", "https://github.com/oxoooo/mr-mantou-android"));
        libraries.add(new GitRepository("drakeet", "Meizhi", "https://github.com/drakeet/Meizhi"));
        libraries.add(new GitRepository("donglua", "PhotoPicker", "https://github.com/donglua/PhotoPicker"));
        libraries.add(new GitRepository("balysv", "material-ripple", "https://github.com/balysv/material-ripple"));
        binding.libraries.setAdapter(new LibrariesAdapter(libraries, getLayoutInflater(), this));
    }

    private void sendEmail() {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse(getString(R.string.auther_email)));
        data.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.key_title));
        data.putExtra(Intent.EXTRA_TEXT, getString(R.string.key_content));
        List<ResolveInfo> rInfo = getPackageManager().queryIntentActivities(data, MODE_PRIVATE);
        if (rInfo.size() == 0) {
            showSimpleDialog(R.string.error, R.string.no_email_app);
            return;
        }
        startActivity(data);
    }

    private void viewGitHub(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onLibraryClick(LibrariesAdapter.ItemViewHolder holder) {
        int position = holder.getAdapterPosition();
        viewGitHub(libraries.get(position).url);
    }


}
