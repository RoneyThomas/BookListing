package me.roneythomas.booklisting.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.roneythomas.booklisting.R;

public class ErrorFragment extends Fragment {
    public static ErrorFragment newInstance() {

        Bundle args = new Bundle();

        ErrorFragment fragment = new ErrorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.error_layout, container, false);
        return view;
    }
}
