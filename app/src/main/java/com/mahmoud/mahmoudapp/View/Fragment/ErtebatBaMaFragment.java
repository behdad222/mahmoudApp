package com.mahmoud.mahmoudapp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahmoud.mahmoudapp.R;

/**
 * Created by behdad on 10/15/15.
 */
public class ErtebatBaMaFragment extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ertebat, container, false);


        return view;
    }
}