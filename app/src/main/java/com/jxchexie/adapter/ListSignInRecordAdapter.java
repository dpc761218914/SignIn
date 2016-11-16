package com.jxchexie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jxchexie.bean.Holiday;
import com.jxchexie.bean.SignIn;
import com.jxchexie.signin.R;

import java.util.ArrayList;


/**
 * Created by linkmax on 2016/8/3.
 */
public class ListSignInRecordAdapter extends RecyclerView.Adapter<ListSignInRecordAdapter.ListViewHolder> {


    private ArrayList<SignIn> mdatas;

    private Context mContext;

    public ListSignInRecordAdapter(ArrayList<SignIn> datas, Context context) {
        mdatas = datas;
        mContext = context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListViewHolder holder = new ListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_signin_record, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.am_signin.setText(mdatas.get(position).getAm_signin());
        holder.am_signout.setText(mdatas.get(position).getAm_signout());
        holder.pm_signin.setText(mdatas.get(position).getPm_signin());
        holder.pm_signout.setText(mdatas.get(position).getPm_signout());
        holder.night_signin.setText(mdatas.get(position).getNight_signin());
        holder.night_signout.setText(mdatas.get(position).getNight_signout());
        if(mdatas.get(position).getCreate_date()!=null){
            holder.signin_date.setText(mdatas.get(position).getCreate_date().substring(0,10));
        }
    }

    @Override
    public int getItemCount() {
        if(mdatas==null || mdatas.size()<=0){
            return 0;
        }
        return mdatas.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView am_signin;
        TextView am_signout;
        TextView pm_signin;
        TextView pm_signout;
        TextView night_signin;
        TextView night_signout;
        TextView signin_date;



        public ListViewHolder(View itemView) {
            super(itemView);
            am_signin=(TextView) itemView.findViewById(R.id.am_signin);
            am_signout=(TextView) itemView.findViewById(R.id.am_signout);
            pm_signin=(TextView) itemView.findViewById(R.id.pm_signin);
            pm_signout=(TextView) itemView.findViewById(R.id.pm_signout);
            night_signin=(TextView) itemView.findViewById(R.id.night_signin);
            night_signout=(TextView) itemView.findViewById(R.id.night_signout);
            signin_date=(TextView) itemView.findViewById(R.id.signin_date);
        }
    }

}
