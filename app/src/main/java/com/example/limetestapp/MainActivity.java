package com.example.limetestapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    ValutesViewModel valutesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valutesViewModel = ViewModelProviders.of(this).get(ValutesViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
                    valutesViewModel.updateData();
                    valutesViewModel.getIsQueryInProcess().observe(this,
                            it -> swipeRefreshLayout.setRefreshing(it));
                }
        );
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            navigationViewItemSelected(menuItem);
            drawerLayout.closeDrawer(GravityCompat.START);
            menuItem.setChecked(false);
            return true;
        });
        valutesViewModel.updateData();
        valutesViewModel.getIsQuerySuccessful().observe(this, it -> {
            if (it) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new RecyclerViewFragment())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ConnectionErrorFragment())
                        .commit();
            }
        });

        Disposable disposable = Observable.interval(30, 30, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((l) -> valutesViewModel.updateData());

        compositeDisposable.add(disposable);
    }

    private void navigationViewItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.renew:
                valutesViewModel.updateData();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void onBackStackChanged() {
        super.onBackPressed();
    }

    public static void startActivity(Activity introActivity) {
        Intent intent = new Intent(introActivity, MainActivity.class);
        introActivity.startActivity(intent);
        introActivity.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
