package com.jxchexie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jxchexie.bean.Holiday;
import com.jxchexie.signin.R;

import java.util.ArrayList;


/**
 * Created by linkmax on 2016/8/3.
 */
public class ListHolidayRecordAdapter extends RecyclerView.Adapter<ListHolidayRecordAdapter.ListViewHolder> {


    private ArrayList<Holiday> mdatas;

    private Context mContext;

    public ListHolidayRecordAdapter(ArrayList<Holiday> datas, Context context) {
        mdatas = datas;
        mContext = context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListViewHolder holder = new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_holiday_record, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.begin_time.setText(mdatas.get(position).getBegin_time());
        holder.end_time.setText(mdatas.get(position).getEnd_time());
        holder.reason.setText(mdatas.get(position).getReason());
    }

    @Override
    public int getItemCount() {
        if(mdatas==null || mdatas.size()<=0){
            return 0;
        }
        return mdatas.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView begin_time;
        TextView end_time;
        TextView reason;

        public ListViewHolder(View itemView) {
            super(itemView);
            begin_time=(TextView) itemView.findViewById(R.id.holiday_record_begin_time);
            end_time=(TextView) itemView.findViewById(R.id.holiday_record_end_time);
            reason=(TextView) itemView.findViewById(R.id.holiday_record_reason);
        }
    }

}
