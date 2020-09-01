package com.bjgoodwill.jhecis.di.component;

import com.bjgoodwill.jhecis.Presenter.OrderFragmentPresenter;
import com.bjgoodwill.jhecis.di.anno.ActivityScope;
import com.bjgoodwill.jhecis.di.module.OrderFragmentModule;
import com.bjgoodwill.jhecis.fragment.OrderFragment;

import dagger.Component;

@ActivityScope
@Component(modules = OrderFragmentModule.class, dependencies = AppComponent.class)
public interface OrderFragmentComponent {
    void inject(OrderFragment fragment);

    OrderFragmentPresenter presenter();
}
