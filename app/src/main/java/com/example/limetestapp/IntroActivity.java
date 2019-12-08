package com.example.limetestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IntroActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Disposable disposable = Completable.complete()
                .delay(3, TimeUnit.SECONDS)
                .subscribe(this::startMainActivity);
        compositeDisposable.add(disposable);
    }

    private void startMainActivity() {
        MainActivity.startActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }
}
