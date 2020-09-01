package com.bjgoodwill.jhecis.di.module;

import com.bjgoodwill.jhecis.di.anno.ActivityScope;
import com.bjgoodwill.jhecis.fragment.OrderFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderFragmentModule {
    private OrderFragment fragment;

    public OrderFragmentModule(OrderFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ActivityScope
    OrderFragment providerOrderFragment() {
        return fragment;
    }
}
