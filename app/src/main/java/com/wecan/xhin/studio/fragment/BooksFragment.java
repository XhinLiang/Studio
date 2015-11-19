package com.wecan.xhin.studio.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wecan.xhin.studio.R;

/**
 * Created by xhinliang on 15-11-18.
 * xhinliang@gmail.com
 */
public class BooksFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.drawable.header);
        return imageView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }
}
