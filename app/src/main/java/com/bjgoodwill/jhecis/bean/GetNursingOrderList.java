package com.bjgoodwill.jhecis.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNursingOrderList extends CommonResultBean {

    public List<NursingOrderListBean> getData() {
        return data;
    }

    public void setData(List<NursingOrderListBean> data) {
        this.data = data;
    }

    private List<NursingOrderListBean> data;
}
