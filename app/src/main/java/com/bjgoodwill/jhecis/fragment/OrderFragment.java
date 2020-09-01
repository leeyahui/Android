package com.bjgoodwill.jhecis.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjgoodwill.jhecis.MyApplication;
import com.bjgoodwill.jhecis.Presenter.OrderFragmentPresenter;
import com.bjgoodwill.jhecis.R;
import com.bjgoodwill.jhecis.adapter.OrderItemAdapter;
import com.bjgoodwill.jhecis.bean.NursingOrderListBean;
import com.bjgoodwill.jhecis.di.component.DaggerOrderFragmentComponent;
import com.bjgoodwill.jhecis.di.module.OrderFragmentModule;
import com.bjgoodwill.jhecis.retro.AppNursingInterface;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PVID = "pvid";
    @BindView(R.id.rv_frag_order)
    RecyclerView rvFragOrder;

    @Inject
    AppNursingInterface appNursingInterface;

    @Inject
    OrderFragmentPresenter presenter;

    private String pvid;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pvid Parameter 1.
     * @return A new instance of fragment OrderFragment.
     */
    public static OrderFragment newInstance(String pvid) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(PVID, pvid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerOrderFragmentComponent
                .builder()
                .appComponent(((MyApplication) getActivity().getApplication()).getAppComponent())
                .orderFragmentModule(new OrderFragmentModule(this))
                .build()
                .inject(this);
        if (getArguments() != null) {
            pvid = getArguments().getString(PVID);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        initRecyclerview();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initRecyclerview() {
        presenter.loadData(pvid, new OrderFragmentPresenter.Load<List<NursingOrderListBean>>() {
            @Override
            public void onLoadFinish(List<NursingOrderListBean> result) {
                //为 RecyclerView 设置布局管理器
                rvFragOrder.setLayoutManager(new LinearLayoutManager(getContext()));
                //为 RecyclerView 设置分割线(这个可以对 DividerItemDecoration 进行修改，自定义)
                rvFragOrder.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
                //动画
                rvFragOrder.setItemAnimator(new DefaultItemAnimator());
                OrderItemAdapter adapter = new OrderItemAdapter(result);
                rvFragOrder.setAdapter(adapter);
            }
        });
    }
}