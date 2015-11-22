package com.mahmoud.mahmoudapp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoud.mahmoudapp.R;

public class BiographyFragment extends Fragment  {
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bio, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getStringArray(R.array.drawer_titles)[2]);

        return view;
    }
}