package com.bjgoodwill.jhecis.di.module;

import com.bjgoodwill.jhecis.AppUtis;
import com.bjgoodwill.jhecis.retro.AppNursingInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Singleton
    @Provides
    Retrofit providerRetrofit() {
        return new Retrofit
                .Builder()
                .baseUrl(AppUtis.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    AppNursingInterface providerAppNursing(Retrofit retrofit) {
        return retrofit.create(AppNursingInterface.class);
    }
}
