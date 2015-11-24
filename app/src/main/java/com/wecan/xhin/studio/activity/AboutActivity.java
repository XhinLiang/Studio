package com.wecan.xhin.studio.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;

import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.adapter.LibrariesAdapter;
import com.wecan.xhin.studio.bean.GitRepository;
import com.wecan.xhin.studio.databinding.ActivityAboutBinding;

import java.util.LinkedList;
import java.util.List;

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        libraries.add(new GitRepository("XhinLiang", "Studio", "https://github.com/XhinLiang/Studio"));
        libraries.add(new GitRepository("XhinLiang", "Studio", "https://github.com/XhinLiang/Studio"));
        libraries.add(new GitRepository("XhinLiang", "Studio", "https://github.com/XhinLiang/Studio"));

        libraries.add(new GitRepository("Square", "Okhttp", "https://square.github.io/okhttp"));
        libraries.add(new GitRepository("Square", "Retrofit", "https://square.github.io/retrofit"));
        libraries.add(new GitRepository("Google", "Gson", "https://github.com/google/gson"));
        libraries.add(new GitRepository("ReactiveX", "RxJava", "https://github.com/ReactiveX/RxJava"));
        libraries.add(new GitRepository("ReactiveX", "RxAndroid", "https://github.com/ReactiveX/RxAndroid"));
        libraries.add(new GitRepository("JakeWharton", "RxBinding", "https://github.com/JakeWharton/RxBinding"));
        libraries.add(new GitRepository("Trello", "RxLifecycle", "https://github.com/trello/RxLifecycle"));
        libraries.add(new GitRepository("oxoooo", "mr-mantou-android", "https://github.com/trello/RxLifecycle"));

        binding.libraries.setAdapter(new LibrariesAdapter(libraries, getLayoutInflater(), this));
    }

    private void open(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onLibraryClick(LibrariesAdapter.ItemViewHolder holder) {
        int position = holder.getAdapterPosition();
        open(libraries.get(position).url);
    }
}
