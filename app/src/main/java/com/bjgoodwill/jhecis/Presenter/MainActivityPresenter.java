package com.bjgoodwill.jhecis.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.bjgoodwill.jhecis.Activity.MainActivity;
import com.bjgoodwill.jhecis.AppUtis;
import com.bjgoodwill.jhecis.bean.CommonResultBean;
import com.bjgoodwill.jhecis.retro.AppNursingInterface;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {
    private MainActivity activity;

    private AppNursingInterface nursingInterface;

    @Inject
    public MainActivityPresenter(MainActivity activity, AppNursingInterface nursingInterface) {
        this.activity = activity;
        this.nursingInterface = nursingInterface;
    }

    public void login() {
        Toast.makeText(activity, "点击了登录", Toast.LENGTH_SHORT).show();
        try {
            if (nursingInterface == null) {
                Toast.makeText(activity, "nursingInterface为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            Call<CommonResultBean> login = nursingInterface.Login("admin", AppUtis.md5("123").toUpperCase());
            login.enqueue(new Callback<CommonResultBean>() {
                @Override
                public void onResponse(Call<CommonResultBean> call, Response<CommonResultBean> response) {
                    Toast.makeText(activity, "登录成功：", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CommonResultBean> call, Throwable t) {
                    String message = t.getMessage();
                    Toast.makeText(activity, "登录失败：" + message, Toast.LENGTH_SHORT).show();
                    Log.d(AppUtis.TAG, "" + message);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
