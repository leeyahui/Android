package com.bjgoodwill.jhecis.di.component;

import com.bjgoodwill.jhecis.di.module.AppModule;
import com.bjgoodwill.jhecis.retro.AppNursingInterface;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    AppNursingInterface providerAppNursing();
}
