package com.bjgoodwill.jhecis.Presenter;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bjgoodwill.jhecis.AppUtis;
import com.bjgoodwill.jhecis.bean.GetNursingOrderList;
import com.bjgoodwill.jhecis.bean.NursingOrderListBean;
import com.bjgoodwill.jhecis.fragment.OrderFragment;
import com.bjgoodwill.jhecis.retro.AppNursingInterface;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.stream.Collectors.toList;

public class OrderFragmentPresenter {
    private OrderFragment fragment;
    private AppNursingInterface appNursingInterface;

    @Inject
    public OrderFragmentPresenter(OrderFragment fragment, AppNursingInterface nursingInterface) {
        this.fragment = fragment;
        this.appNursingInterface = nursingInterface;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadData(String pvid, Load<List<NursingOrderListBean>> load) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate = sdf.format(cal.getTime());
        if (pvid == null || pvid.isEmpty()) {
            pvid = "83f9139b-89a7-46f3-8b0d-1de85611b547";
        }
        Call<GetNursingOrderList> getOrders = appNursingInterface.GetNursingOrdersLists(pvid, startDate, sdf.format(new Date()));
        getOrders.enqueue(new Callback<GetNursingOrderList>() {
            @Override
            public void onResponse(Call<GetNursingOrderList> call, Response<GetNursingOrderList> response) {
                List<NursingOrderListBean> orderListBeans = response.body().getData();
                if (orderListBeans != null) {
                    //Toast.makeText(OrderFragment.this.getContext(), "获取医嘱成功", Toast.LENGTH_SHORT).show();
                    //分组
                    Map<String, List<NursingOrderListBean>> groupMap = orderListBeans.stream().collect(Collectors.groupingBy(a -> {
                        return (a.getITEMCLASS() + "|" + sdf.format(a.getPLANUSETIME()) + "|" + a.getGROUPNO() + "|" + a.getORDERID());
                    }));

                    //合并分组信息（医嘱内容、剂量）
                    List<NursingOrderListBean> newList = new ArrayList<>();
                    for (Map.Entry<String, List<NursingOrderListBean>> entry : groupMap.entrySet()) {
                        List<NursingOrderListBean> list = entry.getValue();
                        NursingOrderListBean order = list.get(0);
                        if (list.size() > 1) {
                            int minSubNo = list.stream().mapToInt(a -> a.getORDERSUBNO()).min().getAsInt();
                            int maxSubNo = list.stream().mapToInt(a -> a.getORDERSUBNO()).max().getAsInt();
                            list.sort(Comparator.comparing(a -> a.getORDERSUBNO()));
                            for (NursingOrderListBean o1 : list) {
                                if (minSubNo != maxSubNo) {
                                    int orderSubNo = o1.getORDERSUBNO();
                                    if (orderSubNo == minSubNo)
                                        o1.setITEMNAME("┏ " + o1.getITEMNAME());
                                    else if (orderSubNo == maxSubNo)
                                        o1.setITEMNAME("┗ " + o1.getITEMNAME());
                                    else
                                        o1.setITEMNAME("┣ " + o1.getITEMNAME());
                                }
                            }
                            List<String> itemnames = list.stream().map(a -> a.getITEMNAME()).collect(toList());
                            List<String> dosages = list.stream().map(a -> a.getDOSAGEUNIT()).collect(toList());
                            order.setITEMNAME(StringUtils.join(itemnames, System.lineSeparator()));
                            order.setDOSAGEUNIT(StringUtils.join(dosages, System.lineSeparator()));
                        }
                        newList.add(order);
                    }
                    //排序
                    newList.sort(Comparator
                            .comparing(NursingOrderListBean::getAPPLYTIME)
                            .thenComparing(NursingOrderListBean::getITEMCLASS)
                            .thenComparing(NursingOrderListBean::getPLANUSETIME)
                            .thenComparing(NursingOrderListBean::getGROUPNO)
                            .thenComparing(NursingOrderListBean::getORDERSUBNO));

                    load.onLoadFinish(newList);
                }
            }

            @Override
            public void onFailure(Call<GetNursingOrderList> call, Throwable t) {
                Log.d(AppUtis.TAG, "onFailure: " + t.getMessage());
                Toast.makeText(fragment.getContext(), "获取医嘱失败" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public interface Load<T> {
        void onLoadFinish(T result);
    }
}
