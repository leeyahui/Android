package com.bjgoodwill.jhecis.adapter;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bjgoodwill.jhecis.R;
import com.bjgoodwill.jhecis.bean.NursingOrderListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.N)
public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private List<NursingOrderListBean> myList;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public OrderItemAdapter(List<NursingOrderListBean> myList) {
        this.myList = myList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvorder_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NursingOrderListBean order = myList.get(position);
        holder.txtOrderName.setText(order.getITEMNAME());
        if (order.getDOSAGEUNIT() != null) {
            holder.txtOrderDosage.setText(order.getDOSAGEUNIT());
        }
        holder.txtOrderOperator.setText(order.getOPDOCTORNAME());
        holder.txtOrderOptime.setText(sdf.format(order.getAPPLYTIME()));
        holder.txtOrderPlantime.setText(sdf.format(order.getPLANUSETIME()));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_rvorder_name)
        TextView txtOrderName;
        @BindView(R.id.txt_rvorder_dosage)
        TextView txtOrderDosage;
        @BindView(R.id.txt_rvorder_operator)
        TextView txtOrderOperator;
        @BindView(R.id.txt_rvorder_optime)
        TextView txtOrderOptime;
        @BindView(R.id.txt_rvorder_plantime)
        TextView txtOrderPlantime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
