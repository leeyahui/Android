package com.bjgoodwill.jhecis.fragment;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjgoodwill.jhecis.AppUtis;
import com.bjgoodwill.jhecis.MyApplication;
import com.bjgoodwill.jhecis.R;
import com.bjgoodwill.jhecis.adapter.OrderItemAdapter;
import com.bjgoodwill.jhecis.bean.GetNursingOrderList;
import com.bjgoodwill.jhecis.bean.NursingOrderListBean;
import com.bjgoodwill.jhecis.di.component.DaggerOrderFragmentComponent;
import com.bjgoodwill.jhecis.retro.AppNursingInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = sdf.format(cal.getTime());
        pvid = "83f9139b-89a7-46f3-8b0d-1de85611b547";
        Call<GetNursingOrderList> getOrders = appNursingInterface.GetNursingOrdersLists(pvid, startDate, sdf.format(new Date()));
        getOrders.enqueue(new Callback<GetNursingOrderList>() {
            @Override
            public void onResponse(Call<GetNursingOrderList> call, Response<GetNursingOrderList> response) {
                List<NursingOrderListBean> orderListBeans = response.body().getData();
                if (orderListBeans != null) {
                    Toast.makeText(OrderFragment.this.getContext(), "获取医嘱成功", Toast.LENGTH_SHORT).show();

                    //为 RecyclerView 设置布局管理器
                    rvFragOrder.setLayoutManager(new LinearLayoutManager(getContext()));
                    //为 RecyclerView 设置分割线(这个可以对 DividerItemDecoration 进行修改，自定义)
                    rvFragOrder.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                    //动画
                    rvFragOrder.setItemAnimator(new DefaultItemAnimator());
                    List<String> list = new ArrayList<>();
//                    for (int i = 0; i < 100; i++) {
//                        list.add("医嘱内容测试" + i);
//                    }

                    for (NursingOrderListBean order : orderListBeans) {
                        list.add(order.getITEMNAME());
                    }
                    OrderItemAdapter adapter = new OrderItemAdapter(list);
                    rvFragOrder.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GetNursingOrderList> call, Throwable t) {
                Toast.makeText(OrderFragment.this.getContext(), "获取医嘱失败" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(AppUtis.TAG, "onFailure: " + t.getMessage());
            }
        });

    }
}