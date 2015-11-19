package com.wecan.xhin.studio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wecan.xhin.studio.R;
import com.wecan.xhin.studio.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public class FellowsFragment extends Fragment {

    private static String KEY_FELLOWS = "fellow";

    public static FellowsFragment newInstance(List<User> users) {
        FellowsFragment fragment = new FellowsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_FELLOWS, new ArrayList<>(users));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.drawable.defimgs);
        return imageView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
