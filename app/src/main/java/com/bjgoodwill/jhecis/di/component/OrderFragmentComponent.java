package com.bjgoodwill.jhecis.di.component;

import com.bjgoodwill.jhecis.di.anno.ActivityScope;
import com.bjgoodwill.jhecis.fragment.OrderFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface OrderFragmentComponent {
    void inject(OrderFragment fragment);
}
