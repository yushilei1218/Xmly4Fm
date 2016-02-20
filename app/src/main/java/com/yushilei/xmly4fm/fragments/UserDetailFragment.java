package com.yushilei.xmly4fm.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yushilei.xmly4fm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {


    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(long uId) {
        Bundle bundle = new Bundle();
        bundle.putLong("uId", uId);
        UserDetailFragment fragment = new UserDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

}
