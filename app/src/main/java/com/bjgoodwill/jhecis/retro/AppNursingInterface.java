package com.bjgoodwill.jhecis.retro;

import com.bjgoodwill.jhecis.bean.CommonResultBean;
import com.bjgoodwill.jhecis.bean.GetNursingOrderList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppNursingInterface {

    @GET("/api/AppNursing/LogIn")
    Call<CommonResultBean> Login(@Query("name") String name, @Query("password") String password);

    @GET("/api/NursingOrder/GetNursingOrdersLists")
    Call<GetNursingOrderList> GetNursingOrdersLists(@Query("pvid") String pvid, @Query("starttime") String startDate, @Query("endtime") String endDate);
}
