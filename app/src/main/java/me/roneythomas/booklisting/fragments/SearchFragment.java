package me.roneythomas.booklisting.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import me.roneythomas.booklisting.model.Book;
import me.roneythomas.booklisting.BookSearchTask;
import me.roneythomas.booklisting.R;

public class SearchFragment extends ListFragment {
    private static String URL_KEY = "query";
    ArrayList<Book> booksArrayList;

    public static SearchFragment newInstance(String URL) {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        args.putString(URL_KEY, URL);
        Log.d("SearchFragment", "newInstance: " + URL);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_list_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Log.d("SearchFragment", getArguments().getString(URL_KEY));
            booksArrayList = new BookSearchTask().execute(getArguments().getString(URL_KEY)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        SearchAdapter searchAdapter = new SearchAdapter(getContext(), booksArrayList);
        setListAdapter(searchAdapter);
    }

    private class SearchAdapter extends ArrayAdapter<Book> {

        public SearchAdapter(Context context, ArrayList<Book> mBooks) {
            super(context, 0, mBooks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
            }
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText(booksArrayList.get(position).getTitle());

            TextView author = (TextView) convertView.findViewById(R.id.author);
            author.setText(booksArrayList.get(position).getAuthor());
            return convertView;
        }
    }
}
