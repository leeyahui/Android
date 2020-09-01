package com.bjgoodwill.jhecis;

import android.app.Application;

import com.bjgoodwill.jhecis.di.component.AppComponent;
import com.bjgoodwill.jhecis.di.component.DaggerAppComponent;

public class MyApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
