package com.wecan.xhin.studio.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.baselib.rx.RxNetworking;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.adapter.LibrariesAdapter;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.GitRepository;
import com.wecan.xhin.studio.bean.down.BaseData;
import com.wecan.xhin.studio.databinding.ActivityAboutBinding;

import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xhinliang on 15-11-23.
 * xhinliang@gmail.com
 */
public class AboutActivity extends BaseActivity implements LibrariesAdapter.Listener {

    private List<GitRepository> libraries = new LinkedList<>();
    private Api api;
    private Observable.Transformer<BaseData, BaseData> networkingIndicator;

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
                        composeEmail(getString(R.string.author_email));
                    }
                });

        RxView.clickEvents(binding.toolbar)
                .elementAt(5)
                .compose(this.<ViewClickEvent>bindToLifecycle())
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        addCode();
                    }
                });

        setupRecyclerView(binding);
        initData();
    }

    private void initData() {
        api = App.from(this).createApi(Api.class);
        ProgressDialog pd = new ProgressDialog(this);
        networkingIndicator = RxNetworking.bindConnecting(pd);
    }

    private void addCodeToServer(final String code) {
        Observable
                .defer(new Func0<Observable<BaseData>>() {
                    @Override
                    public Observable<BaseData> call() {
                        return api.addCode(code);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator)
                .compose(AboutActivity.this.<BaseData>bindToLifecycle())
                .subscribe(new Action1<BaseData>() {
                    @Override
                    public void call(BaseData baseData) {
                        showSimpleDialog(R.string.add_code_result, R.string.succeed);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        showSimpleDialog(R.string.add_code_result, R.string.fail);
                    }
                });
    }

    private void addCode() {
        final EditText editText = new EditText(this);
        editText.setHint(R.string.add_code);
        new AlertDialog.Builder(this)
                .setTitle(R.string.add_code)
                .setView(editText)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCodeToServer(editText.getText().toString());
                    }
                })
                .create()
                .show();
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


    public void composeEmail(String address) {
        final Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        Observable.just(intent)
                .subscribeOn(Schedulers.io())
                .map(new Func1<Intent, List<ResolveInfo>>() {
                    @Override
                    public List<ResolveInfo> call(Intent intent) {
                        return getPackageManager().queryIntentActivities(intent, MODE_PRIVATE);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<List<ResolveInfo>, Boolean>() {
                    @Override
                    public Boolean call(List<ResolveInfo> resolveInfo) {
                        if (resolveInfo.size() == 0) {
                            showSimpleDialog(R.string.no_email_app);
                            return false;
                        }
                        return true;
                    }
                })
                .compose(this.<List<ResolveInfo>>bindToLifecycle())
                .subscribe(new Action1<List<ResolveInfo>>() {
                    @Override
                    public void call(List<ResolveInfo> resolveInfo) {
                        startActivity(intent);
                    }
                });
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
