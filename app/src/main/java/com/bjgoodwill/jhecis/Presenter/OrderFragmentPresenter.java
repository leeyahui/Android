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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        pvid = "83f9139b-89a7-46f3-8b0d-1de85611b547";
        Call<GetNursingOrderList> getOrders = appNursingInterface.GetNursingOrdersLists(pvid, startDate, sdf.format(new Date()));
        getOrders.enqueue(new Callback<GetNursingOrderList>() {
            @Override
            public void onResponse(Call<GetNursingOrderList> call, Response<GetNursingOrderList> response) {
                List<NursingOrderListBean> orderListBeans = response.body().getData();
                if (orderListBeans != null) {
                    //Toast.makeText(OrderFragment.this.getContext(), "获取医嘱成功", Toast.LENGTH_SHORT).show();
                    //分组
                    Map<String, List<NursingOrderListBean>> groupMap = new HashMap<String, List<NursingOrderListBean>>();
                    for (int i = 0; i < orderListBeans.size(); i++) {
                        NursingOrderListBean order = orderListBeans.get(i);
                        String groupId = order.getITEMCLASS() + "|" + sdf.format(order.getPLANUSETIME()) + "|" + order.getGROUPNO() + "|" + order.getORDERID();
                        if (groupMap.containsKey(groupId)) {
                            Objects.requireNonNull(groupMap.get(groupId)).add(order);
                        } else {
                            List<NursingOrderListBean> list = new ArrayList<>();
                            list.add(order);
                            groupMap.put(groupId, list);
                        }
                    }
                    List<NursingOrderListBean> newList = new ArrayList<>();
                    for (Map.Entry<String, List<NursingOrderListBean>> entry : groupMap.entrySet()) {
                        List<NursingOrderListBean> list = entry.getValue();
                        NursingOrderListBean order = list.get(0);
                        if (list.size() > 1) {
                            String itemName = "";
                            Comparator<NursingOrderListBean> subNoComparator = new Comparator<NursingOrderListBean>() {
                                @Override
                                public int compare(NursingOrderListBean o1, NursingOrderListBean o2) {
                                    int a = o1.getORDERSUBNO() - o2.getORDERSUBNO();
                                    if (a != 0) {
                                        return a > 0 ? -1 : 1;
                                    }
                                    return 0;
                                }
                            };
                            int minSubNo = Collections.min(list, subNoComparator).getORDERSUBNO();
                            int maxSubNo = Collections.max(list, subNoComparator).getORDERSUBNO();
                            Collections.sort(list, subNoComparator);
                            for (NursingOrderListBean o1 : list) {
                                String tempItemName = o1.getITEMNAME();
                                if (minSubNo != maxSubNo) {
                                    int orderSubNo = o1.getORDERSUBNO();
                                    if (orderSubNo == minSubNo)
                                        tempItemName = "┏ " + tempItemName;
                                    else if (orderSubNo == maxSubNo)
                                        tempItemName = "┗ " + tempItemName;
                                    else
                                        tempItemName = "┣ " + tempItemName;
                                }
                                if (itemName.isEmpty()) {
                                    itemName = tempItemName;
                                } else {
                                    itemName = itemName + "\n" + tempItemName;
                                }
                            }
                            order.setITEMNAME(itemName);
                        }
                        newList.add(order);
                    }
                    //排序
                    Collections.sort(newList, new Comparator<NursingOrderListBean>() {
                        @Override
                        public int compare(NursingOrderListBean o1, NursingOrderListBean o2) {
                            int a = o2.getAPPLYTIME().compareTo(o1.getAPPLYTIME());
                            if (a != 0) {
                                return a > 0 ? -1 : 1;
                            }
                            a = o1.getITEMCLASS() - o2.getITEMCLASS();
                            if (a != 0) {
                                return a > 0 ? -1 : 1;
                            }
                            a = o2.getPLANUSETIME().compareTo(o1.getPLANUSETIME());
                            if (a != 0) {
                                return a > 0 ? -1 : 1;
                            }
                            a = o1.getGROUPNO() - o2.getGROUPNO();
                            if (a != 0) {
                                return a > 0 ? -1 : 1;
                            }
                            a = o1.getORDERSUBNO() - o2.getORDERSUBNO();
                            if (a != 0) {
                                return a > 0 ? -1 : 1;
                            }
                            return 0;
                        }
                    });
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
