package com.bjgoodwill.jhecis.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bjgoodwill.jhecis.MyApplication;
import com.bjgoodwill.jhecis.Presenter.MainActivityPresenter;
import com.bjgoodwill.jhecis.R;
import com.bjgoodwill.jhecis.adapter.MainPagerAdapter;
import com.bjgoodwill.jhecis.di.component.DaggerMainComponent;
import com.bjgoodwill.jhecis.di.module.MainModule;
import com.bjgoodwill.jhecis.fragment.BloodFragment;
import com.bjgoodwill.jhecis.fragment.InfusionFragment;
import com.bjgoodwill.jhecis.fragment.OrderFragment;
import com.bjgoodwill.jhecis.fragment.VitalSignFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Inject
    MainActivityPresenter mainPresenter;
    @BindView(R.id.txt_main_order)
    TextView txtMainOrder;
    @BindView(R.id.txt_main_vital)
    TextView txtMainVital;
    @BindView(R.id.txt_main_blood)
    TextView txtMainBlood;
    @BindView(R.id.txt_main_infusion)
    TextView txtMainInfusion;
    @BindView(R.id.vp_main_content)
    ViewPager2 vpMainContent;
    @BindView(R.id.txt_main_title)
    TextView txtMainTitle;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerMainComponent
                .builder()
                .appComponent(((MyApplication) getApplication()).getAppComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);
        initData();

        vpMainContent.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                clearColor();
                setNav(position);
            }
        });
    }

    @OnClick({R.id.txt_main_blood, R.id.txt_main_infusion, R.id.txt_main_order, R.id.txt_main_vital})
    void click(View view) {
        clearColor();
        switch (view.getId()) {
            case R.id.txt_main_order:
                setNav(0);
                vpMainContent.setCurrentItem(0);
                break;
            case R.id.txt_main_vital:
                setNav(1);
                vpMainContent.setCurrentItem(1);
                break;
            case R.id.txt_main_blood:
                setNav(2);
                vpMainContent.setCurrentItem(2);
                break;
            case R.id.txt_main_infusion:
                setNav(3);
                vpMainContent.setCurrentItem(3);
                break;
        }
    }

    private void setNav(int position) {
        switch (position) {
            case 0:
                txtMainOrder.setBackgroundResource(R.color.colorPrimary);
                txtMainOrder.setTextColor(Color.WHITE);
                setMainTitle(txtMainOrder.getText().toString());
                break;
            case 1:
                txtMainVital.setBackgroundResource(R.color.colorPrimary);
                txtMainVital.setTextColor(Color.WHITE);
                setMainTitle(txtMainVital.getText().toString());
                break;
            case 2:
                txtMainBlood.setBackgroundResource(R.color.colorPrimary);
                txtMainBlood.setTextColor(Color.WHITE);
                setMainTitle(txtMainBlood.getText().toString());
                break;
            case 3:
                txtMainInfusion.setBackgroundResource(R.color.colorPrimary);
                txtMainInfusion.setTextColor(Color.WHITE);
                setMainTitle(txtMainInfusion.getText().toString());
                break;
        }
    }

    void clearColor() {
        txtMainBlood.setBackgroundColor(Color.WHITE);
        txtMainBlood.setTextColor(Color.BLACK);
        txtMainInfusion.setBackgroundColor(Color.WHITE);
        txtMainInfusion.setTextColor(Color.BLACK);
        txtMainOrder.setBackgroundColor(Color.WHITE);
        txtMainOrder.setTextColor(Color.BLACK);
        txtMainVital.setBackgroundColor(Color.WHITE);
        txtMainVital.setTextColor(Color.BLACK);
    }

    void initData() {
//        List<String> list = new ArrayList<>();
//        list.add(txtMainOrder.getText().toString());
//        list.add(txtMainVital.getText().toString());
//        list.add(txtMainBlood.getText().toString());
//        list.add(txtMainInfusion.getText().toString());
//
//        vpMainContent.setAdapter(new ViewPagerAdapter(this, list, vpMainContent));

        mFragments = new ArrayList<>();
        mFragments.add(new OrderFragment());
        mFragments.add(new VitalSignFragment());
        mFragments.add(new BloodFragment());
        mFragments.add(new InfusionFragment());
        vpMainContent.setAdapter(new MainPagerAdapter(this, mFragments));
    }

    void setMainTitle(String title) {
        txtMainTitle.setText(title);
    }
}