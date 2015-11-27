package com.wecan.xhin.studio.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jakewharton.rxbinding.view.ViewClickEvent;
import com.wecan.xhin.baselib.activity.BaseActivity;
import com.wecan.xhin.baselib.rx.RxNetworking;
import com.wecan.xhin.studio.App;
import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.api.Api;
import com.wecan.xhin.studio.bean.common.User;
import com.wecan.xhin.studio.databinding.ActivityUserDetailsBinding;

import java.io.IOException;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MyDetailsActivity extends BaseActivity {

    public static final String KEY_USER = "user";

    private ActivityUserDetailsBinding binding;
    private RequestManager requestManager;
    private Observable<User> observableUpdate;
    private Observer<User> observerUser;
    private User user;
    private Api api;

    private void setAsCurrentUser() {
        api = App.from(this).createApi(Api.class);

        ProgressDialog pd = new ProgressDialog(this);
        Observable.Transformer<User, User> networkingIndicator = RxNetworking.bindConnecting(pd);

        observableUpdate = Observable
                .defer(new Func0<Observable<User>>() {
                    @Override
                    public Observable<User> call() {
                        return api.updateUser(user);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator);

        observerUser = new Observer<User>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                showSimpleDialog(e.getMessage());
            }

            @Override
            public void onNext(User user) {
                showSimpleDialog(R.string.succeed);
                MyDetailsActivity.this.user = user;
                binding.setUser(user);
                setupImage(binding.ivPicture, binding.getUser().imgurl);
            }
        };

        binding.fab.setImageResource(android.R.drawable.ic_menu_camera);

        setRxClick(binding.fab)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        PhotoPickerIntent intent = new PhotoPickerIntent(MyDetailsActivity.this);
                        intent.setPhotoCount(1);
                        intent.setShowCamera(true);
                        startActivityForResult(intent, 1);
                    }
                });

        setRxClick(binding.mrlPhone)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        setTextArg("name", user.phone, new SetTextArgCallback() {
                            @Override
                            public void onConfirm(CharSequence update) {
                                user.phone = update.toString();
                                observableUpdate.subscribe(observerUser);
                            }
                        });
                    }
                });

        setRxClick(binding.mrlGroup)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        setIntArg("Group", R.array.group_name, user.group_name, new SetIntArgCallback() {
                            @Override
                            public void onConfirm(int update) {
                                user.group_name = update;
                                observableUpdate.subscribe(observerUser);
                            }
                        });
                    }
                });

        setRxClick(binding.mrlPosition)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        setIntArg("Position", R.array.position_name, user.position, new SetIntArgCallback() {
                            @Override
                            public void onConfirm(int update) {
                                user.position = update;
                                observableUpdate.subscribe(observerUser);
                            }
                        });
                    }
                });

        setRxClick(binding.mrlDescription)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        setTextArg("description", user.description, new SetTextArgCallback() {
                            @Override
                            public void onConfirm(CharSequence update) {
                                user.description = update.toString();
                                observableUpdate.subscribe(observerUser);
                            }
                        });
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_OK, new Intent().putExtra(MainActivity.KEY_USER, user));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestManager = Glide.with(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_details);
        setSupportActionBar(binding.toolbar);
        setHasHomeButton();
        user = getIntent().getParcelableExtra(KEY_USER);
        binding.setUser(user);
        setupImage(binding.ivPicture, binding.getUser().imgurl);
        setAsCurrentUser();
        setRxClick(binding.ivPicture)
                .subscribe(new Action1<ViewClickEvent>() {
                    @Override
                    public void call(ViewClickEvent viewClickEvent) {
                        startActivity(new Intent(MyDetailsActivity.this, PictureActivity.class)
                                .putExtra(PictureActivity.KEY_IMAGE_URL, user.imgurl));
                    }
                });

    }

    private void setupImage(ImageView image, String imageUrl) {
        if (imageUrl == null || imageUrl.length() == 0)
            return;
        requestManager.load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.defimgs)
                .into(image);
    }


    private interface SetTextArgCallback {
        void onConfirm(CharSequence update);
    }

    private interface SetIntArgCallback {
        void onConfirm(int update);
    }

    private void setTextArg(CharSequence title, CharSequence source, final SetTextArgCallback callback) {
        final EditText editText = new EditText(this);
        editText.setText(source);
        new AlertDialog.Builder(this)
                .setView(editText)
                .setTitle(title)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onConfirm(editText.getText());
                    }
                })
                .create()
                .show();
    }

    private void setIntArg(CharSequence title, int resID, int source, final SetIntArgCallback callback) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setSingleChoiceItems(resID, source, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onConfirm(which);
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || requestCode != 1 || data == null)
            return;
        String photo = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS).get(0);
        Observable.just(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, AVFile>() {
                    @Override
                    public AVFile call(String s) {
                        try {
                            return AVFile.withAbsoluteLocalPath(String.format("avatar_%s_%d.jpg", user.name, System.currentTimeMillis()), s);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .filter(new Func1<AVFile, Boolean>() {
                    @Override
                    public Boolean call(AVFile avFile) {
                        if (avFile == null) {
                            showSimpleDialog(R.string.can_not_find_file);
                            return false;
                        }
                        return true;
                    }
                })
                .compose(this.<AVFile>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AVFile>() {
                    @Override
                    public void call(final AVFile file) {
                        final ProgressDialog pd = new ProgressDialog(MyDetailsActivity.this);
                        pd.setMax(100);
                        pd.show();
                        file.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                pd.hide();
                                if (e != null) {
                                    showSimpleDialog(R.string.error);
                                    return;
                                }
                                user.imgurl = file.getUrl();
                                observableUpdate.subscribe(observerUser);
                            }
                        }, new ProgressCallback() {
                            @Override
                            public void done(Integer integer) {
                                pd.setProgress(integer);
                            }
                        });
                    }
                });
    }
}
