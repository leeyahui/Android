package com.bjgoodwill.jhecis.di.component;

import com.bjgoodwill.jhecis.Activity.MainActivity;
import com.bjgoodwill.jhecis.Presenter.MainActivityPresenter;
import com.bjgoodwill.jhecis.di.anno.ActivityScope;
import com.bjgoodwill.jhecis.di.module.MainModule;

import dagger.Component;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);

    MainActivityPresenter presenter();
}
