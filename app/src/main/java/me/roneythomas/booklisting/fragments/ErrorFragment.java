package me.roneythomas.booklisting.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.roneythomas.booklisting.R;

public class ErrorFragment extends Fragment {
    public static String ERROR_INFO = "ERROR_INFO";

    public static ErrorFragment newInstance(int errorInfo) {

        Bundle args = new Bundle();

        ErrorFragment fragment = new ErrorFragment();
        fragment.setArguments(args);
        args.putInt(ERROR_INFO, errorInfo);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.error_layout, container, false);
        TextView errorTextView = (TextView) view.findViewById(R.id.error_info);
        errorTextView.setText(getArguments().getInt(ERROR_INFO));
        return view;
    }
}
