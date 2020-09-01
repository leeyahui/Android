package com.bjgoodwill.jhecis.di.module;

import com.bjgoodwill.jhecis.AppUtis;
import com.bjgoodwill.jhecis.retro.AppNursingInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        return new Retrofit
                .Builder()
                .baseUrl(AppUtis.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Singleton
    @Provides
    AppNursingInterface providerAppNursing(Retrofit retrofit) {
        return retrofit.create(AppNursingInterface.class);
    }
}
