package com.bjgoodwill.jhecis.di.module;

import com.bjgoodwill.jhecis.Activity.MainActivity;
import com.bjgoodwill.jhecis.di.anno.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    MainActivity providerMainActivity() {
        return activity;
    }
}
