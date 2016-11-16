package com.jxchexie.signin;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jxchexie.adapter.ListHolidayRecordAdapter;
import com.jxchexie.api.Api;
import com.jxchexie.app.App;
import com.jxchexie.bean.Holiday;
import com.jxchexie.bean.ResponseHoliday;
import com.jxchexie.bean.ResponseHolidayRecord;
import com.jxchexie.bean.SignIn;
import com.jxchexie.utils.Constant;
import com.jxchexie.utils.JsonUtils;
import com.jxchexie.widget.EndLessOnScrollListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class HolidayRecordActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{


    private RecyclerView recycler_view;
    private SwipeRefreshLayout refresh_view;

    private ArrayList<Holiday> datas;
    private Toolbar holidayRecordToolbar;
    private ResponseHolidayRecord responseHolidayRecord;
    private ListHolidayRecordAdapter adapter;
    private MaterialDialog md;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_holiday_record);
        initView();

        initData();
        initEvent();
    }

    private void initView(){
        /*显示提示正在登录对话框*/
        md=new MaterialDialog.Builder(HolidayRecordActivity.this)
                .title("提示")
                .content("数据加载中...")
                .progress(true, 0)
                .show();

        recycler_view=(RecyclerView) findViewById(R.id.recycler_view);
        refresh_view=(SwipeRefreshLayout)findViewById(R.id.refresh_view);
        refresh_view.setOnRefreshListener(this);

        holidayRecordToolbar=(Toolbar)this.findViewById(R.id.HolidayRecordToolbar);
        holidayRecordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolidayRecordActivity.this.finish();/*关闭当前页*/
            }
        });

        datas=new ArrayList<Holiday>();


    }

    private void initData(){
         /*获得当前登录用户的用户名*/
        String user_name= App.getInstance().getUser(HolidayRecordActivity.this);
        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("page_num", "1");
        JSONObject jsonObject = new JSONObject(params);
        /*获取请假记录*/
        OkGo.post(Api.GET_HOLIDAYS)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        /*关闭提示框*/
                        responseHolidayRecord=new ResponseHolidayRecord();
                        responseHolidayRecord= JsonUtils.fromJson(s,ResponseHolidayRecord.class);
                        if(responseHolidayRecord.getStatus().equals(Constant.SUCCESS)){
                            datas.addAll(responseHolidayRecord.getMsg());
                            if(datas==null || datas.size()<=0){
                                Toast.makeText(HolidayRecordActivity.this,"无请假记录...",Toast.LENGTH_SHORT).show();
                            }else{
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(HolidayRecordActivity.this,"系统错误,请稍后再试...",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }
    private void initEvent() {
        md.dismiss();
        adapter=new ListHolidayRecordAdapter(datas,HolidayRecordActivity.this);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setAdapter(adapter);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addOnScrollListener(new EndLessOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {

                loadMoreData(currentPage);
            }
        });
    }

    public void loadMoreData(int currentPage){
        /*获得当前登录用户的用户名*/
        String user_name= App.getInstance().getUser(HolidayRecordActivity.this);
        /*构造请求体*/
        HashMap<String, String> params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("page_num", currentPage+1+"");
        JSONObject jsonObject = new JSONObject(params);
        /*获取请假记录*/
        OkGo.post(Api.GET_HOLIDAYS)//
                .tag(this)//
                .upJson(jsonObject.toString())//
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        /*关闭提示框*/
                        responseHolidayRecord=new ResponseHolidayRecord();
                        responseHolidayRecord= JsonUtils.fromJson(s,ResponseHolidayRecord.class);
                        if(responseHolidayRecord.getStatus().equals(Constant.SUCCESS)){
                            datas.addAll(responseHolidayRecord.getMsg());
                            if(datas==null || datas.size()<=0){
                                Toast.makeText(HolidayRecordActivity.this,"无请假记录...",Toast.LENGTH_SHORT).show();
                            }else{
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(HolidayRecordActivity.this,"系统错误,请稍后再试...",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });

    }

    private Handler mHandler = new Handler();
    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            refresh_view.setRefreshing(false);
                            Toast.makeText(HolidayRecordActivity.this,"刷新成功，没有新数据...",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
