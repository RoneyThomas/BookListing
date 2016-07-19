package me.roneythomas.booklisting;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.roneythomas.booklisting.fragments.ErrorFragment;
import me.roneythomas.booklisting.fragments.InfoFragment;
import me.roneythomas.booklisting.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    ConnectivityManager mConnectivityManager;
    NetworkInfo networkInfo;
    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = mConnectivityManager.getActiveNetworkInfo();
        isConnected = networkInfo != null &&
                networkInfo.isConnectedOrConnecting();
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.contianer_main);
        if (!isConnected && fragment == null) {
            fragment = ErrorFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.contianer_main, fragment).commit();
        } else if (isConnected && fragment == null) {
            fragment = InfoFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.contianer_main, fragment).commit();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, false);
            searchQuery(query);


        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String query = intent.getDataString();
            searchQuery(query);
        }
    }

    private void searchQuery(String query) {
        isConnected = networkInfo != null &&
                networkInfo.isConnectedOrConnecting();
        if (isConnected) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contianer_main, SearchFragment.newInstance(query), "TAGY GAH").commit();
        } else {
            Toast.makeText(MainActivity.this, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
